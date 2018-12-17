package com.lazy.tcc.core.repository.jdbc;

import com.lazy.tcc.common.utils.DateUtils;
import com.lazy.tcc.core.entity.IdempotentEntity;
import com.lazy.tcc.core.exception.TransactionCrudException;
import com.lazy.tcc.core.repository.support.AbstractDataSourceRepository;
import com.lazy.tcc.core.spi.SpiConfiguration;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * <p>
 *
 * </p>
 *
 * @author laizhiyuan
 * @since 2018/12/17.
 */
public class MysqlIdempotentRepository extends AbstractDataSourceRepository<IdempotentEntity, IdempotentEntity.IdempotentPk> {

    public int createTable() {

        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;

        try {
            connection = this.getConnection();

            //checked idempotent table is exists
            String tableIsExistsSql = String.format("SELECT count(*) as is_exists FROM information_schema.TABLES WHERE table_name ='%s'", SpiConfiguration.getInstance().getIdempotentTableName());
            stmt = connection.prepareStatement(tableIsExistsSql);
            resultSet = stmt.executeQuery();

            if (resultSet.next()) {
                int isExists = resultSet.getInt("is_exists");
                if (isExists > 0) {
                    logger.info(String.format("idempotent table %s exists", SpiConfiguration.getInstance().getIdempotentTableName()));

                    return 1;
                }
            }

            logger.info(String.format("idempotent table %s not exists, now create it", SpiConfiguration.getInstance().getIdempotentTableName()));

            String sql = "CREATE TABLE `" + SpiConfiguration.getInstance().getIdempotentTableName() + "` (" +
                    "  `req_seril_num` varchar(32) NOT NULL COMMENT '请求序列号'," +
                    "  `app_key` varchar(32) NOT NULL COMMENT '客户端应用标识符'," +
                    "  `business_rec` varchar(45) DEFAULT '' COMMENT '业务记录'," +
                    "  `create_time` varchar(32) NOT NULL COMMENT '创建时间'," +
                    "  PRIMARY KEY (`req_seril_num`,`app_key`)" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4";

            stmt = connection.prepareStatement(sql);
            boolean isSuccess = stmt.execute(sql);

            if (isSuccess) {
                logger.info("create idempotent table sql : " + sql);
            }

            return isSuccess ? 1 : 0;
        } catch (Exception e) {
            throw new TransactionCrudException(e);
        } finally {
            closeResultSet(resultSet);
            closeStatement(stmt);
            this.releaseConnection(connection);
        }
    }

    @Override
    public boolean exists(IdempotentEntity.IdempotentPk idempotentPk) {
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;

        try {
            connection = this.getConnection();

            //checked idempotent table is exists
            String tableIsExistsSql = String.format("SELECT count(*) as is_exists FROM %s WHERE req_serial_num ='%s' and app_key = '%s'",
                    SpiConfiguration.getInstance().getIdempotentTableName(),
                    idempotentPk.getReqSerialNum(),
                    idempotentPk.getAppKey());
            stmt = connection.prepareStatement(tableIsExistsSql);
            resultSet = stmt.executeQuery();

            if (resultSet.next()) {

                int isExists = resultSet.getInt("is_exists");
                return isExists > 0;
            }
        } catch (Exception e) {
            throw new TransactionCrudException(e);
        } finally {
            closeResultSet(resultSet);
            closeStatement(stmt);
            this.releaseConnection(connection);
        }
        return false;
    }

    @Override
    public int insert(IdempotentEntity idempotent) {
        Connection connection = null;
        PreparedStatement stmt = null;

        try {
            connection = this.getConnection();

            String builder = "insert into " + SpiConfiguration.getInstance().getIdempotentTableName() +
                    " (req_seril_num,app_key,business_rec,create_time) VALUES (?,?,?,?)";

            stmt = connection.prepareStatement(builder);

            stmt.setString(1, idempotent.getPk().getReqSerialNum());
            stmt.setString(2, idempotent.getPk().getAppKey());
            stmt.setString(3, idempotent.getBusinessRec());
            stmt.setString(4, DateUtils.getCurrentDateStr(DateUtils.YYYY_MM_DD_HH_MM_SS));

            return stmt.executeUpdate();
        } catch (Exception e) {
            throw new TransactionCrudException(e);
        } finally {
            closeStatement(stmt);
            this.releaseConnection(connection);
        }
    }

    @Override
    public int update(IdempotentEntity idempotent) {

        return 0;
    }

    @Override
    public int delete(IdempotentEntity.IdempotentPk id) {
        return 0;
    }

    @Override
    public IdempotentEntity findById(IdempotentEntity.IdempotentPk id) {
        return null;
    }
}
