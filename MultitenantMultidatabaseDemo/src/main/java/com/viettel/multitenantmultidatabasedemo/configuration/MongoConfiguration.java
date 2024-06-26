package com.viettel.multitenantmultidatabasedemo.configuration;

import com.mongodb.ConnectionString;
import com.viettel.multitenantmultidatabasedemo.context.ConnectionStorage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
public class MongoConfiguration {

    @Bean
    @Lazy
    public MongoTemplate mongoTemplate() {
        ConnectionString connectionString = new ConnectionString(ConnectionStorage.getConnection());
        return new MongoTemplate(new DatabaseConfiguration(connectionString));
    }
}
