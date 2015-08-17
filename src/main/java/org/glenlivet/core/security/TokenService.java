package org.glenlivet.core.security;

public interface TokenService {
	
	public UserDetails getUserDetailsByToken(String token);

}
