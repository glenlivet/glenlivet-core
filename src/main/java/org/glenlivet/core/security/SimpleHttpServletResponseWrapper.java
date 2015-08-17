package org.glenlivet.core.security;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

public class SimpleHttpServletResponseWrapper extends HttpServletResponseWrapper{

	public SimpleHttpServletResponseWrapper(HttpServletResponse response) {
		super(response);
	}
	

}
