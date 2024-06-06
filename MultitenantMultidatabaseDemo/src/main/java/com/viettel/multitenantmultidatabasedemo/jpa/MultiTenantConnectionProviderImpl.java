package com.viettel.multitenantmultidatabasedemo.jpa;

import com.viettel.multitenantmultidatabasedemo.constant.SpringDataJPAMultitenantConstants;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Priority;
import org.hibernate.engine.jdbc.connections.spi.AbstractDataSourceBasedMultiTenantConnectionProviderImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Component
public class MultiTenantConnectionProviderImpl extends AbstractDataSourceBasedMultiTenantConnectionProviderImpl {

    /**
     *
     */
    private static final long serialVersionUID = 7395318315512114572L;

    public final static String BEAN_ID = "multiTenantConnectionProvider";

    @Autowired
    private TenantDataSource tenantDataSource;

    @Override
    public DataSource selectAnyDataSource() {
        return tenantDataSource.getDataSource(SpringDataJPAMultitenantConstants.DEFAULT_TENANT_ID);
    }

    @Override
    protected DataSource selectDataSource(Object tenantIdentifier) {
        return  tenantDataSource.getDataSource((String)tenantIdentifier);
    }
}