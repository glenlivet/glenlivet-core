package org.glenlivet.core.ember;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.glenlivet.core.BaseDomain;

public class ReturnValueBuilder {
	
	public static <T> Map<String, Object> buildSingleFind(String key, T obj){
		Map<String, Object> retValue = new HashMap<String, Object>();
		retValue.put(key, obj);
		return retValue;
	}
	
	public static Map<String, Object> buildFindAll(String[] keys, @SuppressWarnings("rawtypes") List... lists){
		if(lists.length != keys.length){
			throw new IllegalArgumentException("The numbers of keys and values are not match.");
		}
		Map<String, Object> retVal = new HashMap<String, Object>();
		for(int i = 0; i < keys.length; i++){
			retVal.put(keys[i], lists[i]);
		}
		return retVal;
	}

	public static Map<String, Object> buildReturnId(String key, BaseDomain domain) {
		Map<String, Object> retVal = new HashMap<String, Object>();
		Map<String, String> idMap = new HashMap<String, String>();
		idMap.put("id", domain.getId());
		retVal.put(key, idMap);
		return retVal;
	}

}
