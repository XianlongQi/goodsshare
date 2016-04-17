package com.qixl.goodsshare.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;


public class PageEncodingFilter implements Filter {

    private String encoding = "UTF-8";
    private FilterConfig filterConfig;


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
        if(filterConfig.getInitParameter("encoding") != null){
            encoding = filterConfig.getInitParameter("encoding");
        }
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        request.setCharacterEncoding(encoding);
        
        String uri = request.getRequestURI();
//        System.out.println(uri);
//        System.out.println("getContextPath:"+request.getContextPath().toString());
//        System.out.println(request.getContextPath().length());
        String requestdUri = uri.substring(request.getContextPath().length());
        System.out.println(uri + "=" +requestdUri);
        filterChain.doFilter(request, servletResponse);
        
    }

    
    @Override
    public void destroy() {
        // TODO Auto-generated method stub
        
    }


}
