package com.example.tm.system.config;

import com.zaxxer.hikari.HikariDataSource;
import lombok.AllArgsConstructor;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.engine.jdbc.connections.spi.AbstractDataSourceBasedMultiTenantConnectionProviderImpl;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@AllArgsConstructor
public class DataSourceBasedMultiTenantConnectionProviderImpl implements MultiTenantConnectionProvider<ClientDatabase>, HibernatePropertiesCustomizer {

    private final DataSource dataSource;

    private final Map<ClientDatabase, DataSource> tenantDataSources = new ConcurrentHashMap<>();

    @Override
    public Connection getAnyConnection() throws SQLException {
        return dataSource.getConnection();
    }

    @Override
    public void releaseAnyConnection(Connection connection) throws SQLException {
        connection.close();
    }

    @Override
    public Connection getConnection(ClientDatabase tenantIdentifier) throws SQLException {
        return null;
    }

    @Override
    public void releaseConnection(ClientDatabase tenantIdentifier, Connection connection) throws SQLException {

    }

    @Override
    public boolean supportsAggressiveRelease() {
        return false;
    }

    @Override
    public void customize(Map<String, Object> hibernateProperties) {
        hibernateProperties.put(AvailableSettings.MULTI_TENANT_CONNECTION_PROVIDER, this);
    }


    @Override
    public boolean isUnwrappableAs(Class<?> unwrapType) {
        return false;
    }

    @Override
    public <T> T unwrap(Class<T> unwrapType) {
        return null;
    }
}