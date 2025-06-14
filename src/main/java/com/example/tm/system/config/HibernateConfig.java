package com.example.tm.system.config;

import lombok.AllArgsConstructor;
import org.hibernate.cfg.Environment;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableTransactionManagement
public class HibernateConfig {

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            DataSourceBasedMultiTenantConnectionProviderImpl multiTenantConnectionProvider,
            CurrentTenantIdentifierResolverImpl tenantResolver) {

        Map<String, Object> props = new HashMap<>();
        props.put(Environment.MULTI_TENANT_CONNECTION_PROVIDER, multiTenantConnectionProvider);
        props.put(Environment.MULTI_TENANT_IDENTIFIER_RESOLVER, tenantResolver);
        props.put(Environment.HBM2DDL_AUTO, "update");
        props.put(Environment.SHOW_SQL, true);

        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        emf.setPackagesToScan("com.example.tm.domain");
        emf.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        emf.setJpaPropertyMap(props);
        return emf;
    }
}
