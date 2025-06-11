package com.example.tm.system.config;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.stereotype.Component;

@Component
public class CurrentTenantIdentifierResolverImpl implements CurrentTenantIdentifierResolver<ClientDatabase> {
    @Override
    public ClientDatabase resolveCurrentTenantIdentifier() {
        ClientDatabase clientDatabase = ClientDatabaseContextHolder.getClientDatabase();
        return clientDatabase;
    }

    @Override
    public boolean validateExistingCurrentSessions() {
        return false;
    }
}
