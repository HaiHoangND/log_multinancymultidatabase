package com.viettel.multitenantmultidatabasedemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(exclude = {
        MongoAutoConfiguration.class,
        MongoDataAutoConfiguration.class
})
@EnableTransactionManagement
public class MultitenantMultidatabaseDemoApplication {

    public static void main(String[] args) {
        try {
            SpringApplication.run(MultitenantMultidatabaseDemoApplication.class, args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
