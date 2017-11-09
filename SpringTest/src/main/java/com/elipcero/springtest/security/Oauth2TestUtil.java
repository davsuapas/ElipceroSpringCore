package com.elipcero.springtest.security;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public class Oauth2TestUtil {

	public static HttpHeaders getHeaderForClientCredentials(MockMvc mockMvc, String clientId, String clientSecret) throws Exception {
		
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
	    params.add("grant_type", "client_credentials");
	    params.add("client_id", clientId);
	    params.add("client_secret", clientSecret);
	 
	    ResultActions result 
	      = mockMvc.perform(post("/oauth/token")
	        .params(params)
	        .accept(MediaType.APPLICATION_JSON_UTF8))
	        .andExpect(status().isOk())
	        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
	 
	    String resultString = result.andReturn().getResponse().getContentAsString();
	 
	    JacksonJsonParser jsonParser = new JacksonJsonParser();
	    HttpHeaders headers = new HttpHeaders();
	    headers.add("Authorization", "Bearer " + jsonParser.parseMap(resultString).get("access_token").toString());
	    return headers; 		
	}
}
