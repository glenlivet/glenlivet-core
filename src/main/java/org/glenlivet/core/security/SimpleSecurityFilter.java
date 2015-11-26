package org.glenlivet.core.security;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class SimpleSecurityFilter implements Filter {

	static Logger logger = LoggerFactory.getLogger(SimpleSecurityFilter.class);

	private String tokenHeader = "Glenlivet-Token";

	private String tokenServiceBeanName = "tokenServiceImpl";

	private ApplicationContext springContext;

	private TokenService tokenService;

	private static ThreadLocal<ServletRequest> localRequest;

	private static ThreadLocal<ServletResponse> localResponse;

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		handleThreadLocals(req, resp);

		HttpServletRequest request = (HttpServletRequest) req;
		String token = request.getHeader(tokenHeader);

		UserDetails ud = tokenService.getUserDetailsByToken(token);

		request.setAttribute(UserDetails.ATTR_CURRENT_USER, ud);
		// new SimpleHttpServletResponseWrapper(response);
		chain.doFilter(req, resp);

		logger.info("after chain.doFilter()");
		removeThreadLocals();
	}

	private void removeThreadLocals() {
		localRequest.remove();
		localResponse.remove();
	}

	private void handleThreadLocals(ServletRequest req, ServletResponse resp) {
		localRequest.set(req);
		localResponse.set(resp);
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		this.springContext = WebApplicationContextUtils.getWebApplicationContext(config.getServletContext());
		String paramTokenHeader = config.getInitParameter(TOKEN_HEADER);
		if (StringUtils.isNotBlank(paramTokenHeader)) {
			tokenHeader = paramTokenHeader;
		}

		String paramtokenServiceBeanName = config.getInitParameter(TOKEN_SERVICE_NAME);
		if (StringUtils.isNoneBlank(paramtokenServiceBeanName)) {
			tokenServiceBeanName = paramtokenServiceBeanName;
		}

		tokenService = springContext.getBean(tokenServiceBeanName, TokenService.class);

		localRequest = new ThreadLocal<ServletRequest>();
		localResponse = new ThreadLocal<ServletResponse>();

	}

	public static UserDetails getCurrentUserDetails() {
		return (UserDetails) getLocalRequest().getAttribute(UserDetails.ATTR_CURRENT_USER);
	}

	public static HttpServletRequest getLocalRequest() {
		return (HttpServletRequest) localRequest.get();
	}

	public static HttpServletResponse getLocalResponse() {
		return (HttpServletResponse) localResponse.get();
	}

	@Override
	public void destroy() {

	}

	private static final String TOKEN_HEADER = "tokenHeader";

	private static final String TOKEN_SERVICE_NAME = "tokenServiceBeanName";

}
