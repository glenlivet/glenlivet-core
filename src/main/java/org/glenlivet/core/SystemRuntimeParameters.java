package org.glenlivet.core;

import java.util.HashMap;
import java.util.Map;

import org.glenlivet.core.security.UserDetails;
import org.glenlivet.core.security.UserRole;

/**
 * A place to hold regularly-used system runtime parameters.
 * 
 * @author glenlivet
 *
 */
public class SystemRuntimeParameters {
	
	private Map<String, UserRole> roleMap = new HashMap<String, UserRole>();
	
	private Map<String, UserDetails> userMap = new HashMap<String, UserDetails>();

	public Map<String, UserRole> getRoleMap() {
		return roleMap;
	}

	public void putRole(String roleName, UserRole role){
		this.roleMap.put(roleName, role);
	}
	
	public UserRole getRole(String roleName) {
		return this.roleMap.get(roleName);
	}

	public UserDetails getUserDetailsByToken(String token){
		return this.userMap.get(token);
	}

	public void putToken(String token, UserDetails user) {
		this.userMap.put(token, user);
	}
	
}
