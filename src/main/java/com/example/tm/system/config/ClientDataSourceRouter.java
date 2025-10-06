//package com.example.tm.system.config;
//
//import com.zaxxer.hikari.HikariDataSource;
//import lombok.Data;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.jdbc.DataSourceBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
//import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
//import org.springframework.stereotype.Component;
//
//import javax.sql.DataSource;
//import java.util.HashMap;
//import java.util.Map;
//
//public class ClientDataSourceRouter extends AbstractRoutingDataSource {
//
////    ClientDataSourceRouter() {
////        setDefaultTargetDataSource(masterDataSource());
////        HashMap<Object, Object> targetDataSources = new HashMap<>();
////        targetDataSources.put("t1", t1());
////        targetDataSources.put("t2", t1());
////        setTargetDataSources(targetDataSources);
////    }
//
//    @Override
//    protected ClientDatabase determineCurrentLookupKey() {
//        return ClientDatabase.t1;
//    }
//}
