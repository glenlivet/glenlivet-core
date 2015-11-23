package org.glenlivet.core.ember;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReturnValueBuilder {
	
	public static <T> Map<String, T> buildSingleFind(String key, T obj){
		Map<String, T> retValue = new HashMap<String, T>();
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

}
