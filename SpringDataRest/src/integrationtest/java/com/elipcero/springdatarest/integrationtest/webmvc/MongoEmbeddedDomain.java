package com.elipcero.springdatarest.integrationtest.webmvc;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor()
@Builder
@Getter
@Setter
public class MongoEmbeddedDomain {
	
	@Id
	private String id;
	
	private String embeddedName;
}
