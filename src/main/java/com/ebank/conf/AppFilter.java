package com.ebank.conf;

import com.google.inject.Singleton;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Vahap Gencdal
 * @email avahap19@gmail.com
 * @date 15.09.2018
 * @description :Allow Cor Requests
 */
@Singleton
public class AppFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException { }

    @Override
    public void destroy() { }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if (servletResponse instanceof HttpServletResponse) {
            HttpServletResponse alteredResponse = ((HttpServletResponse) servletResponse);
            addHeadersFor200Response(alteredResponse);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    private void addHeadersFor200Response(HttpServletResponse response) {
       response.addHeader("Access-Control-Allow-Origin", "*");
       response.addHeader("Access-Control-Allow-Methods", "*, Cache-Control, Pragma, Origin, Authorization, X-Requested-With, PUT, DELETE");
       response.addHeader("Access-Control-Allow-Headers", "*, Content-Type, GET, OPTIONS, X-XSRF-TOKEN");
    }
}
