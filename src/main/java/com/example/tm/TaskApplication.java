package com.example.tm;

import com.example.tm.system.config.ClientDataSourceRouter;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class TaskApplication {

    public static void main(String[] args) {
        SpringApplication.run(TaskApplication.class, args);
    }

    @Primary
    @Bean(name = "masterDataSource")
    public DataSource masterDataSource() {
        return DataSourceBuilder.create()
                .url("jdbc:mysql://localhost:3306/master_db")
                .username("root")
                .password("root")
                .build();
    }

    @Bean
    public JdbcTemplate jdbcTemplate(@Qualifier("routing") DataSource routingDataSource) {
        return new JdbcTemplate(routingDataSource);
    }

    public DataSource t1() {
        HikariDataSource ds = new HikariDataSource();
        ds.setJdbcUrl("jdbc:mysql://localhost:3306/tm_tenant1");
        ds.setUsername("root");
        ds.setPassword("root");
        return ds;
    }

    @Bean("routing")
    public DataSource routingDataSource() {
        Map<Object, Object> dataSources = new HashMap<>();
        dataSources.put("t1", t1());
        ClientDataSourceRouter router = new ClientDataSourceRouter();
        router.setDefaultTargetDataSource(masterDataSource());
        router.setTargetDataSources(dataSources);
        return router;
    }
}
