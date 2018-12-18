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
    public int doInsert(TransactionEntity transaction) {
        Connection connection = null;
        PreparedStatement stmt = null;

        try {
            connection = this.getConnection();

            String builder = "insert into " + SpiConfiguration.getInstance().getTxTableName() +
                    " (tx_id,content_byte,retry_count,create_time,last_update_time,version) VALUES (?,?,?,?,?,?,?)";

            stmt = connection.prepareStatement(builder);

            stmt.setLong(1, transaction.getTxId());

            ByteArrayOutputStream bos = new ByteArrayOutputStream(512);
            serialization.serialize(bos).writeObject(transaction.getParticipants());
            stmt.setBytes(2, bos.toByteArray());

            stmt.setInt(3, transaction.getRetryCount());
            stmt.setString(4, DateUtils.getCurrentDateStr(DateUtils.YYYY_MM_DD_HH_MM_SS));
            stmt.setString(5, DateUtils.getCurrentDateStr(DateUtils.YYYY_MM_DD_HH_MM_SS));
            stmt.setLong(7, transaction.getVersion());

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
                    " set " + "content_type = ?,tx_phase = ?,last_update_time = ?, retry_count = ?," +
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
                transaction = new TransactionEntity();
                transaction.setLastUpdateTime(resultSet.getString("last_update_time"));
                transaction.setCreateTime(resultSet.getString("create_time"));
                transaction.setVersion(resultSet.getLong("version"));
                transaction.setTxPhase(TransactionPhase.valueOf(resultSet.getInt("tx_phase")));


                try {
                    transaction.setParticipants(serialization.deserialize(
                            new ByteArrayInputStream(resultSet.getBytes("content_byte")))
                            .readObject(List.class));
                } catch (Exception e) {

                    throw new TransactionCrudException(e);
                }
                transaction.setRetryCount(resultSet.getInt("retry_count"));
                transaction.setTxId(resultSet.getLong("tx_id"));
            }

        } catch (SQLException e) {

            throw new TransactionCrudException(e);
        } finally {

            closeResultSet(resultSet);
            closeStatement(stmt);
            this.releaseConnection(connection);
        }

        return transaction;
    }

    @Override
    public int createTable() {
        return 0;
    }

    @Override
    public boolean exists(Long aLong) {
        return false;
    }
}
