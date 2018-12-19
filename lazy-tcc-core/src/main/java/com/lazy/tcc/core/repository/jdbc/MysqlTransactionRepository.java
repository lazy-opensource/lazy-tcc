package com.lazy.tcc.core.repository.jdbc;

import com.lazy.tcc.common.enums.TransactionPhase;
import com.lazy.tcc.common.utils.DateUtils;
import com.lazy.tcc.core.entity.TransactionEntity;
import com.lazy.tcc.core.exception.TransactionCrudException;
import com.lazy.tcc.core.repository.support.AbstractTransactionRepository;
import com.lazy.tcc.core.spi.SpiConfiguration;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * MysqlTransactionRepository Definition
 * </p>
 *
 * @author laizhiyuan
 * @since 2018/12/13.
 */
public class MysqlTransactionRepository extends AbstractTransactionRepository {

    @Override
    public int createTable() {

        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;

        try {
            connection = this.getConnection();

            //checked tx table is exists
            String tableIsExistsSql =
                    "select count(*) as is_exists from information_schema.TABLES t where t.TABLE_SCHEMA = ? and t.TABLE_NAME = ?";
            stmt = connection.prepareStatement(tableIsExistsSql);

            stmt.setString(1, SpiConfiguration.getInstance().getTxDatabaseName());
            stmt.setString(2, SpiConfiguration.getInstance().getTxTableName());
            resultSet = stmt.executeQuery();

            if (resultSet.next()) {
                int isExists = resultSet.getInt("is_exists");
                if (isExists > 0) {
                    logger.info(String.format("transaction table %s exists", SpiConfiguration.getInstance().getIdempotentTableName()));

                    return 1;
                }
            }

            logger.info(String.format("transaction table %s not exists, now create it", SpiConfiguration.getInstance().getTxTableName()));

            String sql = "CREATE TABLE `" + SpiConfiguration.getInstance().getTxTableName() + "` (" +
                    "  `tx_id` bigint(20) NOT NULL COMMENT '主键'," +
                    "  `tx_phase` int(5) NOT NULL COMMENT '事务阶段 try,confirm,cancel'," +
                    "  `retry_count` int(5) NOT NULL COMMENT '重试次数'," +
                    "  `content_byte` varbinary(8000) NOT NULL COMMENT '参与者字节码'," +
                    "  `version` bigint(20) NOT NULL COMMENT '乐观锁版本号'," +
                    "  `create_time` datetime NOT NULL COMMENT '创建时间'," +
                    "  `last_update_time` datetime NOT NULL COMMENT '最后更新时间'," +
                    "  PRIMARY KEY (`tx_id`)" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='lazy-tcc事务日志表'";

            stmt = connection.prepareStatement(sql);
            stmt.execute(sql);

            return 1;
        } catch (Exception e) {
            throw new TransactionCrudException(e);
        } finally {
            this.releaseConnection(connection);
        }
    }

    @Override
    public int doInsert(TransactionEntity transaction) {
        Connection connection = null;
        PreparedStatement stmt = null;

        try {
            connection = this.getConnection();

            String builder = "insert into " + SpiConfiguration.getInstance().getTxTableName() +
                    " (tx_id,content_byte,retry_count,create_time,last_update_time,version,tx_phase) VALUES (?,?,?,?,?,?,?)";

            stmt = connection.prepareStatement(builder);

            stmt.setLong(1, transaction.getTxId());

            ByteArrayOutputStream bos = new ByteArrayOutputStream(512);
            serialization.serialize(bos).writeObject(transaction.getParticipants());
            stmt.setBytes(2, bos.toByteArray());

            stmt.setInt(3, transaction.getRetryCount());
            stmt.setString(4, DateUtils.getCurrentDateStr(DateUtils.YYYY_MM_DD_HH_MM_SS));
            stmt.setString(5, DateUtils.getCurrentDateStr(DateUtils.YYYY_MM_DD_HH_MM_SS));
            stmt.setLong(6, transaction.getVersion());
            stmt.setInt(7, transaction.getTxPhase().getVal());

            return stmt.executeUpdate();

        } catch (Exception e) {
            throw new TransactionCrudException(e);
        } finally {
            closeStatement(stmt);
            this.releaseConnection(connection);
        }
    }

    @Override
    public int doUpdate(TransactionEntity transaction) {
        Connection connection = null;
        PreparedStatement stmt = null;

        String lastUpdateTime = transaction.getLastUpdateTime();
        long currentVersion = transaction.getVersion();

        transaction.updateLastUpdateTime();
        transaction.updateVersion();

        try {
            connection = this.getConnection();

            stmt = connection.prepareStatement("update " + SpiConfiguration.getInstance().getTxTableName() +
                    " set " + "content_byte = ?,tx_phase = ?,last_update_time = ?, retry_count = ?," +
                    "version = version + 1 " + "where tx_id = ? and  version = ? and last_update_time = ?");

            ByteArrayOutputStream bos = new ByteArrayOutputStream(512);
            serialization.serialize(bos).writeObject(transaction.getParticipants());

            stmt.setBytes(1, bos.toByteArray());
            stmt.setInt(2, transaction.getTxPhase().getVal());
            stmt.setString(3, transaction.getLastUpdateTime());

            stmt.setInt(4, transaction.getRetryCount());
            stmt.setLong(5, transaction.getTxId());
            stmt.setLong(6, currentVersion);
            stmt.setString(7, lastUpdateTime);

            return stmt.executeUpdate();

        } catch (Throwable e) {
            transaction.setLastUpdateTime(lastUpdateTime);
            transaction.setVersion(currentVersion);
            throw new TransactionCrudException(e);
        } finally {

            closeStatement(stmt);
            this.releaseConnection(connection);
        }
    }

    @Override
    public int doDelete(Long id) {
        Connection connection = null;
        PreparedStatement stmt = null;

        try {
            connection = this.getConnection();

            String builder = "delete from " + SpiConfiguration.getInstance().getTxTableName() + " where tx_id = ?";
            stmt = connection.prepareStatement(builder);

            stmt.setLong(1, id);

            return stmt.executeUpdate();

        } catch (SQLException e) {

            throw new TransactionCrudException(e);
        } finally {

            closeStatement(stmt);
            this.releaseConnection(connection);
        }
    }

    @Override
    @SuppressWarnings({"unchecked"})
    public TransactionEntity doFindById(Long id) {
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;

        TransactionEntity transaction = null;
        try {
            connection = this.getConnection();

            String builder = "select * from " + SpiConfiguration.getInstance().getTxTableName() + " where tx_id = ?";
            stmt = connection.prepareStatement(builder);

            stmt.setLong(1, id);

            resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                transaction = this.getRow(resultSet);
            }

        } catch (Exception e) {

            throw new TransactionCrudException(e);
        } finally {

            closeResultSet(resultSet);
            closeStatement(stmt);
            this.releaseConnection(connection);
        }

        return transaction;
    }

    @Override
    public boolean exists(Long aLong) {
        return false;
    }

    @SuppressWarnings("unchecked")
    private TransactionEntity getRow(ResultSet resultSet) throws Exception {
        return new TransactionEntity()
                .setLastUpdateTime(resultSet.getString("last_update_time"))
                .setCreateTime(resultSet.getString("create_time"))
                .setVersion(resultSet.getLong("version"))
                .setTxPhase(TransactionPhase.valueOf(resultSet.getInt("tx_phase")))
                .setParticipants(serialization.deserialize(
                        new ByteArrayInputStream(resultSet.getBytes("content_byte")))
                        .readObject(List.class))
                .setRetryCount(resultSet.getInt("retry_count"))
                .setTxId(resultSet.getLong("tx_id"));
    }

    @Override
    public List<TransactionEntity> findAllFailure() {
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        List<TransactionEntity> list = new ArrayList<>();
        TransactionEntity transaction = null;
        try {
            connection = this.getConnection();

            String builder = "select * from " + SpiConfiguration.getInstance().getTxTableName()
                    + " where create_time <= ? and retry_count < ?";
            stmt = connection.prepareStatement(builder);

            stmt.setString(1,
                    DateUtils.getBeforeByMinuteTime(SpiConfiguration.getInstance().getCompensationMinuteInterval(),
                            DateUtils.YYYY_MM_DD_HH_MM_SS));
            stmt.setInt(2, SpiConfiguration.getInstance().getRetryCount());

            resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                list.add(this.getRow(resultSet));
            }

        } catch (Exception e) {

            throw new TransactionCrudException(e);
        } finally {
            closeStatement(stmt);
            this.releaseConnection(connection);
        }

        return list;
    }


}
