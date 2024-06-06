//package com.viettel.multitenantmultidatabasedemo.interceptor;
//
//import com.viettel.multitenantmultidatabasedemo.constant.SpringDataJPAMultitenantConstants;
//import com.viettel.multitenantmultidatabasedemo.context.TenantContext;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.extern.log4j.Log4j2;
//import org.springframework.stereotype.Component;
//import org.springframework.ui.ModelMap;
//import org.springframework.util.StringUtils;
//import org.springframework.web.context.request.WebRequest;
//import org.springframework.web.context.request.WebRequestInterceptor;
//import org.springframework.web.servlet.HandlerMapping;
//import org.springframework.web.servlet.HandlerInterceptor;
//import org.springframework.web.servlet.ModelAndView;
//
//import java.util.Map;
//
//@Component
//@Log4j2
//public class MultiTenancyInterceptor implements HandlerInterceptor {
//    private final String X_TENANT_ID = "X-TenantID";
//    private final String TENANT_NOT_FOUND_ERROR = "X-TenantID not present in the Request Header";
//    private final int BAD_REQUEST_HTTP_CODE_ERROR = 400;
//
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        String requestURI = request.getRequestURI();
//        String tenantId = request.getHeader(X_TENANT_ID);
//
//        System.out.println("RequestURI::" + requestURI + " || Search for X-TenantID  :: " + tenantId);
//
//        if (tenantId == null) {
//            response.getWriter().write(TENANT_NOT_FOUND_ERROR);
//            response.setStatus(BAD_REQUEST_HTTP_CODE_ERROR);
//            return false;
//        }
//
//        TenantContext.setCurrentTenant(tenantId);
//        return true;
//    }
//
//    @Override
//    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
//        TenantContext.clear();
//    }
//}
