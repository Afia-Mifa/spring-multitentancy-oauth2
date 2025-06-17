package com.example.tm.system.config;

import com.zaxxer.hikari.HikariDataSource;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.engine.jdbc.connections.spi.AbstractDataSourceBasedMultiTenantConnectionProviderImpl;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class DataSourceBasedMultiTenantConnectionProviderImpl extends AbstractDataSourceBasedMultiTenantConnectionProviderImpl<ClientDatabase> implements HibernatePropertiesCustomizer {

    private final DataSource masterDataSource;

    private final Map<ClientDatabase, DataSource> tenantDataSources = new ConcurrentHashMap<>();

    public DataSourceBasedMultiTenantConnectionProviderImpl(@Qualifier("masterDataSource") DataSource masterDataSource) {
        this.masterDataSource = masterDataSource;
        loadTenantsFromMasterDb();
    }

    @Override
    protected DataSource selectAnyDataSource() {
        return masterDataSource;
    }

    @Override
    protected DataSource selectDataSource(ClientDatabase tenantIdentifier) {
        if (!tenantDataSources.containsKey(tenantIdentifier)) {
            loadAndRegisterTenant(tenantIdentifier);
        }

        return tenantDataSources.getOrDefault(tenantIdentifier, masterDataSource);
    }

    private void loadTenantsFromMasterDb() {
        try (Connection conn = masterDataSource.getConnection()) {
            ResultSet rs = conn.createStatement().executeQuery("SELECT tenant_id, db_url, db_user, db_password FROM tenant");
            while (rs.next()) {
                String id = rs.getString("tenant_id");
                String url = rs.getString("db_url");
                String user = rs.getString("db_user");
                String pass = rs.getString("db_password");

                ClientDatabase clientDatabase = ClientDatabase.valueOf(id);
                tenantDataSources.put(clientDatabase, createDataSource(url, user, pass));
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to load tenants", e);
        }
    }

    private void loadAndRegisterTenant(ClientDatabase tenantId) {
    }

    private DataSource createDataSource(String url, String username, String password) {
        HikariDataSource ds = new HikariDataSource();
        ds.setJdbcUrl(url);
        ds.setUsername(username);
        ds.setPassword(password);
        return ds;
    }

    @Override
    public void customize(Map<String, Object> hibernateProperties) {
        hibernateProperties.put(AvailableSettings.MULTI_TENANT_CONNECTION_PROVIDER, this);
    }
}