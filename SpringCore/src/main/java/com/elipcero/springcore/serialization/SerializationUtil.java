package com.elipcero.springcore.serialization;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;

public class SerializationUtil {
	
	/**
	 * Converts a Java object into JSON representation
	 *
	 * @param obj the obj to serialize
	 * @return the string in json
	 */
	public static String asJsonString(final Object obj) {
        try {
        	ObjectMapper mapper = new ObjectMapper();
        	mapper.registerModule(new Jdk8Module());
        	
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
