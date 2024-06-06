package com.viettel.multitenantmultidatabasedemo.jpa;

//import com.mongodb.client.MongoClient;
//import com.mongodb.client.MongoClients;
//import com.mongodb.client.MongoCollection;
//import com.mongodb.client.MongoDatabase;
//import com.mongodb.client.model.IndexOptions;
//import com.mongodb.client.model.Indexes;

import com.viettel.multitenantmultidatabasedemo.constant.SpringDataJPAMultitenantConstants;
import com.viettel.multitenantmultidatabasedemo.entity.master.MultitenantcyMasterDatasourceEntity;
import com.viettel.multitenantmultidatabasedemo.repository.master.IMultitenancyMasterDatasourceRepository;
import jakarta.annotation.PostConstruct;
//import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.io.Serializable;

@Component
public class TenantDataSource implements Serializable {
    @Autowired
    private JdbcTemplate jdbcTemplateObject;
    @Autowired
    private DataSource dataSource;

    private Map<Object, Object> tenantDataSourcesMap;

    @Autowired
    public TenantDataSource() {
        tenantDataSourcesMap = new LinkedHashMap<Object, Object>();
    }

    @PostConstruct
    void addDefaultDatasourceToMap() {
        tenantDataSourcesMap.put(SpringDataJPAMultitenantConstants.DEFAULT_TENANT_ID, dataSource);
    }

    public DataSource getDataSource(String dataSourceName) {
        DataSource dataSource = null;
        if (tenantDataSourcesMap.containsKey(dataSourceName)) {
            dataSource = (DataSource) tenantDataSourcesMap.get(dataSourceName);
        }
        return dataSource;
    }

    public void loadTenant(MultitenantcyMasterDatasourceEntity tenantDatasource) {
        if (!tenantDataSourcesMap.containsKey(tenantDatasource.getDataSourceKey())) {
            tenantDataSourcesMap.put(tenantDatasource.getDataSourceKey(), createDataSource(tenantDatasource));
        }
    }

    public void loadAllTenants(List<MultitenantcyMasterDatasourceEntity> tenantDatasourcesList) {
        tenantDatasourcesList.stream().forEach(tenatDataSource -> {
            if (!tenantDataSourcesMap.containsKey(tenatDataSource.getDataSourceKey())) {
                tenantDataSourcesMap.put(tenatDataSource.getDataSourceKey(), createDataSource(tenatDataSource));
            }
        });

    }

    private DataSource createDataSource(MultitenantcyMasterDatasourceEntity tenatDataSource) {
        DataSource dataSource = null;

        if (tenatDataSource != null) {
            dataSource = new org.springframework.jdbc.datasource.DriverManagerDataSource();
            DataSourceBuilder factory = DataSourceBuilder
                    .create().driverClassName(tenatDataSource.getDriverClassName())
                    .username(tenatDataSource.getDataSourceUserName())
                    .password(tenatDataSource.getDataSourcePassword())
                    .url(tenatDataSource.getUrl());
            dataSource = factory.build();
        }
        return dataSource;
    }

    public void createSchema(String dataSourceName) throws SQLException {
        if (tenantDataSourcesMap.containsKey(dataSourceName)) {
            jdbcTemplateObject.execute(SpringDataJPAMultitenantConstants.CREATE_SCHEMA + " " + dataSourceName);
            jdbcTemplateObject.execute(SpringDataJPAMultitenantConstants.USE_SCHEMA + " " + dataSourceName);
            DataSource currentDataSource = (DataSource) tenantDataSourcesMap.get(dataSourceName);
            ClassPathResource resource = new ClassPathResource("dbscripts/create_customer.sql");
            ScriptUtils.executeSqlScript(currentDataSource.getConnection(), new EncodedResource(resource, "UTF-8"));
            jdbcTemplateObject.execute(SpringDataJPAMultitenantConstants.USE_SCHEMA + " " + SpringDataJPAMultitenantConstants.DEFAULT_TENANT_ID);
        }
    }

//    public void createMongoSchema(String dataSourceName) {
//        MultitenantcyMasterDatasourceEntity config = iMultitenancyMasterDatasourceRepository.findByDataSourceKey(dataSourceName);
//        if (config != null) {
//            String connectionString = config.getUrl(); // Assumes URL is the MongoDB connection string
//            try (MongoClient mongoClient = MongoClients.create(connectionString)) {
//                MongoDatabase database = mongoClient.getDatabase(dataSourceName);
//
//                // Create collection
//                MongoCollection<Document> collection = database.getCollection("CUSTOMER");
//
//                // Create unique index on FIRST_NAME, LAST_NAME, EMAIL (if needed)
//                collection.createIndex(
//                        Indexes.ascending("FIRST_NAME", "LAST_NAME"),
//                        new IndexOptions().unique(true)
//                );
//
//                log.info("MongoDB schema created successfully for tenant: {}", dataSourceName);
//            } catch (Exception e) {
//                log.error("Error creating MongoDB schema for tenant: {}", dataSourceName, e);
//            }
//        }
//    }


    public void dropSchema(String dataSourceName) {
        if (tenantDataSourcesMap.containsKey(dataSourceName)) {
            jdbcTemplateObject.execute(SpringDataJPAMultitenantConstants.DROP_SCHEMA + " " + dataSourceName);
        }
    }

//    public void dropMongoSchema(String dataSourceName) {
//        MultitenantcyMasterDatasourceEntity config = iMultitenancyMasterDatasourceRepository.findByDataSourceKey(dataSourceName);
//        if (config != null) {
//            String connectionString = config.getUrl(); // Assumes URL is the MongoDB connection string
//            try (MongoClient mongoClient = MongoClients.create(connectionString)) {
//                MongoDatabase database = mongoClient.getDatabase(dataSourceName);
//                database.drop();
//                log.info("MongoDB schema dropped successfully for tenant: {}", dataSourceName);
//            } catch (Exception e) {
//                log.error("Error dropping MongoDB schema for tenant: {}", dataSourceName, e);
//            }
//        }
//    }
}
