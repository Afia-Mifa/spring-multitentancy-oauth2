package com.example.tm.system.config;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.HashMap;

@Component
public class ClientDataSourceRouter extends AbstractRoutingDataSource {
    @Autowired
    private CurrentTenantIdentifierResolverImpl currentTenantIdentifierResolver;


    ClientDataSourceRouter() {
        setDefaultTargetDataSource(masterDataSource());
        HashMap<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put("tenant1", masterDataSource());
        setTargetDataSources(targetDataSources);
    }

    public DataSource masterDataSource() {
        return DataSourceBuilder.create()
                .url("jdbc:mysql://localhost:3306/master_db")
                .username("root")
                .password("root")
                .build();
    }


    @Override
    protected ClientDatabase determineCurrentLookupKey() {
        return currentTenantIdentifierResolver.resolveCurrentTenantIdentifier();
    }
}
