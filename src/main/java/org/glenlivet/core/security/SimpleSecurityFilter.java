package org.glenlivet.core.security;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class SimpleSecurityFilter implements Filter {
	
	private String tokenHeader = "Glenlivet-Token";
	
	private String tokenServiceBeanName = "tokenServiceImpl";
	
	private ApplicationContext springContext;
	
	private TokenService tokenService;
	
	@Override
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		String token = request.getHeader(tokenHeader);
		
		UserDetails ud = tokenService.getUserDetailsByToken(token);
		
		request.setAttribute(UserDetails.ATTR_CURRENT_USER, ud);
		
		chain.doFilter(req, resp);
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		this.springContext = WebApplicationContextUtils.getWebApplicationContext(config.getServletContext());
		String paramTokenHeader = config.getInitParameter(TOKEN_HEADER);
		if(StringUtils.isNotBlank(paramTokenHeader)){
			tokenHeader = paramTokenHeader;
		}
		
		String paramtokenServiceBeanName = config.getInitParameter(TOKEN_SERVICE_NAME);
		if(StringUtils.isNoneBlank(paramtokenServiceBeanName)){
			tokenServiceBeanName = paramtokenServiceBeanName;
		}
		
		tokenService = springContext.getBean(tokenServiceBeanName, TokenService.class);
		
	}
	
	@Override
	public void destroy() {
		
	}
	private static final String TOKEN_HEADER = "tokenHeader";
	
	private static final String TOKEN_SERVICE_NAME = "tokenServiceBeanName";

}
