package com.viettel.multitenantmultidatabasedemo.context;

import com.viettel.multitenantmultidatabasedemo.constant.SpringDataJPAMultitenantConstants;

public class TenantContext {

    private static final ThreadLocal<String> currentTenant = new ThreadLocal<String>(){
        @Override
        protected String initialValue() {
            return SpringDataJPAMultitenantConstants.DEFAULT_TENANT_ID;
        }
    };

    public static void setCurrentTenant(String tenant) {
        currentTenant.set(tenant);
    }

    public static String getCurrentTenant() {
        return currentTenant.get();
    }

    public static void clear() {
        currentTenant.set(null);;
    }
}
