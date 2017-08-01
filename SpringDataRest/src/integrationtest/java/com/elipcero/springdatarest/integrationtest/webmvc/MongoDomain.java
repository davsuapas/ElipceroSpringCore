package com.elipcero.springdatarest.integrationtest.webmvc;

import java.util.Optional;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@Document(collection = "sdt_testmongodomain")
public class MongoDomain {

	@Id
	private String id;
	
	private Optional<String> name;
}
