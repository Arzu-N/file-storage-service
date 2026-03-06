package com.example.filestorage.security;

import com.example.filestorage.service.ApiKeyService;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ApiKeyFilter implements Filter {
    private static final List<String> WHITELIST=List.of("/api-keys/create");
    private final ApiKeyService apiKeyService;


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        String path = httpServletRequest.getRequestURI();
       if(WHITELIST.stream().anyMatch(path::startsWith)){
           filterChain.doFilter(servletRequest,servletResponse);
           return;
       }

        String apiKeyHeader = httpServletRequest.getHeader("X-API-Key");
        if (apiKeyHeader == null ) {
            httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        UUID apiKeyId = apiKeyService.validateAndGetId(apiKeyHeader);
        if(apiKeyId==null){
            httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;}
        httpServletRequest.setAttribute("apiKeyId",apiKeyId);

        filterChain.doFilter(servletRequest, servletResponse);

    }
}
