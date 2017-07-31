package com.elipcero.springcore.test.serialization;

import org.junit.Test;

import com.elipcero.springcore.serialization.SerializationUtil;

import static org.assertj.core.api.Assertions.assertThat;

import lombok.Getter;
import lombok.AllArgsConstructor;

public class SerializationUtilTest {
	
	@Test
	public void serialization_ObjectToJson_ShouldReturnStringAsJson() {
		
		String objectAsString = SerializationUtil.asJsonString(new AnyObject("string"));
		
		assertThat(objectAsString).contains("{\"property\":\"string\"}");
	}
	
	@AllArgsConstructor
	@Getter
	private static class AnyObject {
		private String property;
	}
}
