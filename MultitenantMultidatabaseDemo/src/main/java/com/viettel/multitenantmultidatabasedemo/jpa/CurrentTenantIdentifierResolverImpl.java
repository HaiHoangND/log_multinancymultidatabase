package com.viettel.multitenantmultidatabasedemo.jpa;

import com.viettel.multitenantmultidatabasedemo.constant.SpringDataJPAMultitenantConstants;
import com.viettel.multitenantmultidatabasedemo.context.TenantContext;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.stereotype.Component;

@Component
public class CurrentTenantIdentifierResolverImpl implements CurrentTenantIdentifierResolver {

    public final static String BEAN_ID = "currentTenantIdentifier";

    @Override
    public String resolveCurrentTenantIdentifier() {
        String currentDataSourceKey  =  TenantContext.getCurrentTenant();

        if(currentDataSourceKey==null) {
            currentDataSourceKey = SpringDataJPAMultitenantConstants.DEFAULT_TENANT_ID;
        }
        return currentDataSourceKey;
    }

    @Override
    public boolean validateExistingCurrentSessions() {
        return true;
    }
}
