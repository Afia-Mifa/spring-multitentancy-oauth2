package com.example.tm.system.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class MultitenantConfiguration {

    @Value("${tenant.default.name}")
    private String defaultTenant;

    @Bean(name = "masterDataSource")
    public DataSource masterDataSource() {
        return DataSourceBuilder.create()
                .url("jdbc:mysql://localhost:3306/master_db")
                .username("root")
                .password("root")
                .build();
    }

//    @Bean
//    public DataSource dataSource() {
//        Map<Object, Object> resolvedDataSources = new HashMap<>();
//
//        try {
//            PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
//            Resource[] resources = resolver.getResources("classpath:allTenants/*.yml");
//
//            for (Resource resource : resources) {
//                YamlPropertiesFactoryBean yamlFactory = new YamlPropertiesFactoryBean();
//                yamlFactory.setResources(resource);
//                DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
//
//
//                Properties tenantProperties = yamlFactory.getObject();
//                assert tenantProperties != null;
//                String tenantId = tenantProperties.getProperty("name");
//                dataSourceBuilder.username(tenantProperties.getProperty("datasource.username"));
//                dataSourceBuilder.password(tenantProperties.getProperty("datasource.password"));
//                dataSourceBuilder.url(tenantProperties.getProperty("datasource.url"));
//                resolvedDataSources.put(tenantId, dataSourceBuilder.build());
//            }
//        } catch (Exception exp) {
//            throw new RuntimeException("Problem in tenant datasource:" + exp);
//        }
//
//        AbstractRoutingDataSource dataSource = new ClientDataSourceRouter();
//        dataSource.setDefaultTargetDataSource(resolvedDataSources.get(defaultTenant));
//        dataSource.setTargetDataSources(resolvedDataSources);
//        dataSource.afterPropertiesSet();
//        return dataSource;
//    }
}
