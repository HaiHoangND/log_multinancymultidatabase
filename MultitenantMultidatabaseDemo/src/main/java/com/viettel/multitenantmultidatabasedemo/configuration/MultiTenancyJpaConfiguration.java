package com.viettel.multitenantmultidatabasedemo.configuration;

import com.viettel.multitenantmultidatabasedemo.jpa.CurrentTenantIdentifierResolverImpl;
import com.viettel.multitenantmultidatabasedemo.jpa.MultiTenantConnectionProviderImpl;
import org.hibernate.cfg.Environment;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateProperties;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateSettings;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import javax.sql.DataSource;
import java.util.Map;

@Configuration
@EnableConfigurationProperties(JpaProperties.class)
public class MultiTenancyJpaConfiguration {

//    @Autowired
//    private DataSource dataSource;

    @Autowired
    private JpaProperties jpaProperties;

    @Autowired
    private HibernateProperties hibernateProperties;

//    @Autowired
//    @Qualifier(MultiTenantConnectionProviderImpl.BEAN_ID)
//    private MultiTenantConnectionProvider multiTenantConnectionProvider ;
//
//    @Autowired
//    @Qualifier(CurrentTenantIdentifierResolverImpl.BEAN_ID)
//    private CurrentTenantIdentifierResolver currentTenantIdentifierResolver;

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource, MultiTenantConnectionProvider multiTenantConnectionProvider, CurrentTenantIdentifierResolver currentTenantIdentifierResolver,EntityManagerFactoryBuilder builder) {
        Map<String, Object> hibernateProps = hibernateProperties.determineHibernateProperties(
                jpaProperties.getProperties(), new HibernateSettings());
        hibernateProps.put(Environment.MULTI_TENANT_CONNECTION_PROVIDER, multiTenantConnectionProvider);
        hibernateProps.put(Environment.MULTI_TENANT_IDENTIFIER_RESOLVER, currentTenantIdentifierResolver);
        hibernateProps.put(Environment.DIALECT, "org.hibernate.dialect.MariaDBDialect");
        return builder.dataSource(dataSource).packages("com.viettel.multitenantmultidatabasedemo").properties(hibernateProps).jta(false).build();
    }
}