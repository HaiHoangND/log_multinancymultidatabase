//package com.viettel.multitenantmultidatabasedemo.configuration;
//
//import com.viettel.multitenantmultidatabasedemo.interceptor.MultiTenancyInterceptor;
//import com.viettel.multitenantmultidatabasedemo.interceptor.TenantFilter;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.web.servlet.FilterRegistrationBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//@Configuration
//public class WebMvcConfig implements WebMvcConfigurer {
//
//    @Autowired
//    private MultiTenancyInterceptor multiTenancyInterceptor;
//
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(multiTenancyInterceptor);
//    }
//
//    @Bean
//    public FilterRegistrationBean<TenantFilter> tenantFilter() {
//        FilterRegistrationBean<TenantFilter> registrationBean = new FilterRegistrationBean<>();
//        registrationBean.setFilter(new TenantFilter());
//        registrationBean.addUrlPatterns("/api/v1/*");
//        return registrationBean;
//    }
//}
