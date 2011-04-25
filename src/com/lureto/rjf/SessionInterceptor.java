package com.lureto.rjf;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class SessionInterceptor extends HandlerInterceptorAdapter {

    public boolean preHandle( HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    	User user = (User)request.getSession().getAttribute( Constants.SESSION_USER );
    	String uri = request.getRequestURI();
    	String base = request.getContextPath();
    	uri = uri.substring( base.length() );
    	if( user == null && !uri.startsWith("/api/login") ) {
        	request.getSession().invalidate();
        	response.setContentType("application/json");
            response.getWriter().write("{\"error\":\"SESSION-EXPIRED\"}");
            return false;
    	}
    	return true;
    }
	
}
