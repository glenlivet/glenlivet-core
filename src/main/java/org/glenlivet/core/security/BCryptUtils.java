package org.glenlivet.core.security;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class BCryptUtils {
	
	public static String hashPassword(String plainPassword){
		String salt = BCrypt.gensalt();
		String hashedPassword = BCrypt.hashpw(plainPassword, salt);
		return hashedPassword;
	}
	
	public static boolean checkPassword(String plainPassword, String storedHash){
		boolean verified = false;
		
		if(storedHash == null || !storedHash.startsWith("$2a$")){
			throw new java.lang.IllegalArgumentException("Invalid hash provided for comparison");
		}
		
		verified = BCrypt.checkpw(plainPassword, storedHash);
		return verified;
	}

}
