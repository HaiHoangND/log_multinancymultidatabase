package com.viettel.multitenantmultidatabasedemo.security.jwt;

import com.viettel.multitenantmultidatabasedemo.context.ConnectionStorage;
import com.viettel.multitenantmultidatabasedemo.context.TenantContext;
import com.viettel.multitenantmultidatabasedemo.entity.master.MultitenantcyMasterDatasourceEntity;
import com.viettel.multitenantmultidatabasedemo.repository.master.IMultitenancyMasterDatasourceRepository;
import com.viettel.multitenantmultidatabasedemo.security.services.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class AuthTokenFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private IMultitenancyMasterDatasourceRepository iMultitenancyMasterDatasourceRepository;
    private static final String CONNECTION_STRING = "mongodb://localhost:27017/TENANT?readPreference=primary";
    private static final String TENANT_REPLACEMENT = "TENANT";
    private final String X_TENANT_ID = "X-TenantID";

    private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String jwt = parseJwt(request);
            if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
                String username = jwtUtils.getUserNameFromJwtToken(jwt);
                String audience = jwtUtils.getAudienceFromToken(jwt);
                MultitenantcyMasterDatasourceEntity multitenantcyMasterDatasourceEntity = iMultitenancyMasterDatasourceRepository.findByDataSourceKey(audience);
                if(null == multitenantcyMasterDatasourceEntity){
                    logger.error("An error during getting tenant name");
                    throw new BadCredentialsException("Invalid tenant and user.");
                }
                TenantContext.setCurrentTenant(audience);
                String dbConnectionString = CONNECTION_STRING.replace(TENANT_REPLACEMENT, audience);
                ConnectionStorage.setConnection(dbConnectionString);
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            logger.error("Cannot set user authentication: {}", e);
        }
        if(request.getHeader(X_TENANT_ID) != null) {
            String tenantId = request.getHeader(X_TENANT_ID);
            TenantContext.setCurrentTenant(tenantId);
        }
        filterChain.doFilter(request, response);
        ConnectionStorage.clear();
        TenantContext.clear();
    }

    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");

        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7);
        }

        return null;
    }
}
