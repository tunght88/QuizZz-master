package com.evn.web.controller.utils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class Utils {

	public static Map<String, Object> getMap(Object object){
	    Map<String, Object> map = new HashMap<>();
	    Field[] fields = object.getClass().getDeclaredFields();

	    try {
			for (Field field: fields) {
			    field.setAccessible(true);
			    if(field.getType().equals(Integer.class))
			    	map.put(field.getName() + "_" + field.get(object), field.get(object));
			    else {
			    	if("v_4_5_2".equals(field.getName()) && field.get(object) != null) {
				    	map.put("{" +field.getName() +"}" , field.get(object) + " lần mức lương cơ sở");
			    	}else if("v_4_5_1".equals(field.getName()) && field.get(object) != null) {
				    	map.put("{" +field.getName() +"}" , field.get(object) + " %");
			    	}else if("v_4_6".equals(field.getName()) && field.get(object) != null) {
				    	map.put("{" +field.getName() +"}" , field.get(object) + " %");
			    	}else {
			    		map.put("{" +field.getName() +"}" , field.get(object));
			    	}
			    }
			}
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	    return map;
	}
}
