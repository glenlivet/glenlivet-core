package org.glenlivet.core.security;

import java.util.Collection;

public interface UserDetails {
	
	public static final String ATTR_CURRENT_USER = "currentUser"; 
	
	public String getUsername();
	
	public String getPassword();
	
	public boolean isEnabled();
	
	public boolean isLocked();
	
	public Collection<? extends UserRole> getRoles();

}
