package com.elipcero.springdatarest.integrationtest.webmvc;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Document(collection = "sdt_testmongodomain")
public class MongoDomain {

	@Id
	private String id;
	
	private String name;
}
