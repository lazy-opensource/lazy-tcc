package com.lazy.tcc.core.repository.jdbc;

import com.lazy.tcc.common.utils.DateUtils;
import com.lazy.tcc.core.SpiConfiguration;
import com.lazy.tcc.core.Transaction;
import com.lazy.tcc.core.exception.ConnectionIOException;
import com.lazy.tcc.core.exception.CrudIOException;
import com.lazy.tcc.core.serializer.Serialization;
import com.lazy.tcc.core.serializer.SerializationFactory;
import com.lazy.tcc.core.support.AbstractCacheTransactionRepository;

import javax.sql.DataSource;
import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * <p>
 * JdbcTransactionRepository Definition
 * </p>
 *
 * @author laizhiyuan
 * @since 2018/12/13.
 */
public class JdbcCacheTransactionRepository extends AbstractCacheTransactionRepository {

    private DataSource dataSource;
    private Serialization serialization = SerializationFactory.newInstance();

    public DataSource getDataSource() {
        return dataSource;
    }

    public JdbcCacheTransactionRepository setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        return this;
    }

    protected Connection getConnection() {
        try {
            return this.dataSource.getConnection();
        } catch (SQLException e) {
            throw new ConnectionIOException(e);
        }
    }

    @Override
    public int doInsert(Transaction transaction) {
        Connection connection = null;
        PreparedStatement stmt = null;

        try {
            connection = this.getConnection();

            String builder = "insert into " + SpiConfiguration.getInstance().getTxTableName() +
                    " (tx_id,tx_byte,tx_status,retry_count,create_time,last_update_time,version) VALUES (?,?,?,?,?,?,?)";

            stmt = connection.prepareStatement(builder);

            stmt.setLong(1, transaction.getTxId());

            ByteArrayOutputStream bos = new ByteArrayOutputStream(512);
            serialization.serialize(bos).writeObject(transaction);
            stmt.setBytes(2, bos.toByteArray());

            stmt.setInt(3, transaction.getTxStatus().getVal());
            stmt.setInt(4, transaction.getRetryCount());
            stmt.setTimestamp(5, DateUtils.getCurrentTimestamp(DateUtils.YYYY_MM_DD_HH_MM_SS));
            stmt.setTimestamp(6, DateUtils.getCurrentTimestamp(DateUtils.YYYY_MM_DD_HH_MM_SS));
            stmt.setLong(7, transaction.getVersion());

            return stmt.executeUpdate();

        } catch (Exception e) {
            throw new CrudIOException(e);
        } finally {
            closeStatement(stmt);
            this.releaseConnection(connection);
        }
    }

    private void releaseConnection(Connection con) {
        try {
            if (con != null && !con.isClosed()) {
                con.close();
            }
        } catch (SQLException e) {
            throw new CrudIOException(e);
        }
    }

    private void closeStatement(Statement stmt) {
        try {
            if (stmt != null && !stmt.isClosed()) {
                stmt.close();
            }
        } catch (Exception ex) {
            throw new CrudIOException(ex);
        }
    }

    @Override
    public int doUpdate(Transaction transaction) {
        return 0;
    }

    @Override
    public int doDelete(Long id) {
        return 0;
    }

    @Override
    public Transaction doFindById(Long id) {
        return null;
    }
}
