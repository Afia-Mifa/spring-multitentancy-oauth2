package com.example.tm.system.config;

import com.example.tm.context.ActiveContextHolder;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;

@Component
public class CurrentTenantIdentifierResolverImpl implements CurrentTenantIdentifierResolver<ClientDatabase>, HibernatePropertiesCustomizer {
    @Override
    public ClientDatabase resolveCurrentTenantIdentifier() {
        return Objects.isNull(ActiveContextHolder.getClientDatabase()) ? ClientDatabase.m : ActiveContextHolder.getClientDatabase();
    }

    @Override
    public boolean validateExistingCurrentSessions() {
        return false;
    }

    @Override
    public void customize(Map<String, Object> hibernateProperties) {
        hibernateProperties.put(AvailableSettings.MULTI_TENANT_IDENTIFIER_RESOLVER, this);
    }
}
