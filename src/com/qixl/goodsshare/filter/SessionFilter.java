package com.qixl.goodsshare.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.qixl.goodsshare.Constants;
import com.qixl.goodsshare.model.User;
import com.qixl.goodsshare.util.StringUtils;

/**
 * Servlet Filter implementation class SessionFilter
 */
public class SessionFilter implements Filter {

    private String notNeedLoginPages="";
    protected FilterConfig filterConfig;

    /**
     * @see Filter#init(FilterConfig)
     */
    public void init(FilterConfig fConfig) throws ServletException {
       this.filterConfig = fConfig;
       if(fConfig.getInitParameter("notNeedLoginPages") != null){
           notNeedLoginPages = filterConfig.getInitParameter("notNeedLoginPages");
       }
    }
	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
	    HttpServletResponse response = (HttpServletResponse) servletResponse;
		String uri = request.getRequestURI();
		String requestedUri = uri.substring(request.getContextPath().length()+1);
		
		System.out.println("requestedUri:"+requestedUri);
		String [] pages = notNeedLoginPages.split(",");
		boolean isAllow = false;
		for(String page:pages){
		    System.out.println("page:"+page);
		    if(page.equals(requestedUri)){
		        isAllow =true;
		        break;
		    }
//		    if(requestedUri.endsWith(".css") ||requestedUri.endsWith(".js")||requestedUri.endsWith(".png")||requestedUri.endsWith(".jpg") ){
//		        isAllow=true;
//		        break;
//		    }
		}
		
		if(isAllow){
		    chain.doFilter(request, response);
		}else{
		    HttpSession session = request.getSession();
		    User user = (User) session.getAttribute(Constants.USER);
		    if(user == null){
		        if(request.getMethod().toLowerCase().equals("get")){
		            String queryString = request.getQueryString();
		            String go = requestedUri;
		            if(!StringUtils.isEmpty(queryString)){
		                go = go + "#" + request.getQueryString();
		            }
		            response.sendRedirect(request.getContextPath()+"/login.action?go="+go);
		        }else{
		            response.sendRedirect(request.getContextPath()+"/login.action"); 
		        }
		        
		    }else{
		        chain.doFilter(request, response);
		    }
		}
	}
}
