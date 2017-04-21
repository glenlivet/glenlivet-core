package org.glenlivet.core;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils; 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Simple CROS(Cross-Origin Resource Sharing) Filter implementation.
 * 
 * @author glenlivet
 *
 */
public class SimpleCROSFilter implements Filter {
	
	static Logger logger = LoggerFactory.getLogger(SimpleCROSFilter.class);
	
	private String maxAge = "3600";
	
	private String allowOrigin = "*";
	
	private String allowMethods = "POST, GET, OPTIONS, DELETE, PUT";
	
	private String allowHeaders = "*";

	@Override
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		HttpServletResponse response = (HttpServletResponse) res;
		response.setHeader(ACCESS_CONTROL_ALLOW_ORIGIN, allowOrigin);
		response.setHeader(ACCESS_CONTROL_ALLOW_METHODS, allowMethods);
		response.setHeader(ACCESS_CONTROL_MAX_AGE, maxAge);
		response.setHeader(ACCESS_CONTROL_ALLOW_HEADERS, allowHeaders);
		chain.doFilter(req, res);
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		String paramAllowOrigin = config.getInitParameter(ACCESS_CONTROL_ALLOW_ORIGIN);
		if(StringUtils.isNotBlank(paramAllowOrigin)){
			allowOrigin = paramAllowOrigin;
		}
		
		String paramAllowMethods = config.getInitParameter(ACCESS_CONTROL_ALLOW_METHODS);
		if(StringUtils.isNotBlank(paramAllowMethods)){
			allowMethods = paramAllowMethods;
		}
		
		String paramMaxAge = config.getInitParameter(ACCESS_CONTROL_MAX_AGE);
		if(StringUtils.isNotBlank(paramMaxAge)){
			try {
				Integer.parseInt(paramMaxAge);
				maxAge = paramMaxAge;
			} catch (NumberFormatException e) {
				logger.warn("Filter init param: " + ACCESS_CONTROL_MAX_AGE + " should be an integer. Default value: " + maxAge + " is used.");
			}
		}
		
		String paramAllowHeaders = config.getInitParameter(ACCESS_CONTROL_ALLOW_HEADERS);
		if(StringUtils.isNotBlank(paramAllowHeaders)){
			allowHeaders = paramAllowHeaders;
		}
		
	}

	@Override
	public void destroy() {
		
	}
	
	private static final String ACCESS_CONTROL_ALLOW_ORIGIN = "Access-Control-Allow-Origin";
	private static final String ACCESS_CONTROL_ALLOW_METHODS = "Access-Control-Allow-Methods";
	private static final String ACCESS_CONTROL_MAX_AGE = "Access-Control-Max-Age";
	private static final String ACCESS_CONTROL_ALLOW_HEADERS = "Access-Control-Allow-Headers";
	
}
