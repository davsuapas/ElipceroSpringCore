package com.elipcero.springdatarest.integrationtest.webmvc;

import java.util.List;
import java.util.Optional;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor()
@Builder
@Getter
@Setter
@Document(collection = "sdt_testmongodomain")
public class MongoDomain {
	
	@Id
	private String id;
	
	private Optional<String> name;
	private Optional<Integer> age;

	private List<MongoEmbeddedDomain> embeddedDomains; 
}
