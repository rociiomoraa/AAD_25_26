package com.rocio.aad.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Esta clase es la encargada de gestionar las conexiones con la base de datos PostgreSQL y
 * el control manual de transacciones. Es la que usaremos desde los repositorios cuando
 * necesitemos iniciar, confirmar o deshacer una operación que afecte a varias consultas.
 */
@Slf4j
@Component
public class PostgresqlDriver {

    private final DataSource dataSource;
    private Connection txconnection;

    public PostgresqlDriver(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * Este método devuelve una conexión JDBC. Si estamos dentro de una transacción,
     * se utiliza la conexión transaccional; sino, se abre una nueva conexión.
     */
    public Connection getConnection() throws SQLException {
        if (txconnection != null) {
            return txconnection;
        }
        return dataSource.getConnection();
    }

    /**
     * Inicia una transacción manual desactivando el autocommit de la conexión.
     */
    public void beginTransaction() throws SQLException {
        if (txconnection != null) {
            throw new SQLException("There is already an active transaction");
        }
        txconnection = dataSource.getConnection();
        txconnection.setAutoCommit(false);
        log.info("Transaction started");
    }

    /**
     * Confirma la transacción actual y cierra la conexión asociada.
     */
    public void commit() {
        try {
            if (txconnection != null) {
                txconnection.commit();
                log.info("Transaction committed");
            }
        } catch (SQLException e) {
            log.error("Error committing transaction", e);
        } finally {
            closeTransactionConnection();
        }
    }

    /**
     * Revierte la transacción actual y cierra la conexión asociada.
     */
    public void rollback() {
        try {
            if (txconnection != null) {
                txconnection.rollback();
                log.warn("Transaction rolled back");
            }
        } catch (SQLException e) {
            log.error("Error rolling back transaction", e);
        } finally {
            closeTransactionConnection();
        }
    }

    /**
     * Cierra la conexión asociada a la transacción, si existe.
     */
    private void closeTransactionConnection() {
        try {
            if (txconnection != null) {
                txconnection.close();
                log.info("Transaction connection closed");
            }
        } catch (SQLException e) {
            log.error("Error closing transactional connection", e);
        } finally {
            txconnection = null;
        }
    }
}
