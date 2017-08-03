package com.elipcero.springdatarest.integrationtest.webmvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.elipcero.springdatarest.webmvc.RepositoryRestMvcHeader;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = RestWebMvcConfiguration.class)
public class RepositoryExtensionControllerIntegrationTest {
	
	private static final int CONST_DOMAIN_AGE = 5;
	private static final String CONST_DOMAIN_KEY = "1";
	private static final String CONST_DOMAIN_NAME = "myName";
	private static final String CONST_DOMAIN_UPDATE_NAME = "myName1";
	
	@Autowired
	private MongoRepository mongoRepository;

	@Autowired
	private WebApplicationContext context;

	private MockMvc mockMvc;

	@Before
	public void setUp() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
	}	
	
	@Test
	public void dataRest_UpdateDomainNoNullField_ShouldReturnDomainUpdated() throws Exception {
	
		MongoDomain domain = MongoDomain.builder()
			.id(CONST_DOMAIN_KEY)
			.age(Optional.of(CONST_DOMAIN_AGE))
			.name(Optional.of(CONST_DOMAIN_NAME))
				.build();
	
		mongoRepository.save(domain);
		
		mockMvc.perform(patch("/domains/{id}/updatenonull", CONST_DOMAIN_KEY)
				.content(getJsonBaseDomian())
				.header(RepositoryRestMvcHeader.updateNoMerge, ""))
			.andExpect(status().isNoContent());
		
		MongoDomain resultDomain = mongoRepository.findOne(CONST_DOMAIN_KEY);
		
		assertThat(resultDomain.getName().get()).contains(CONST_DOMAIN_UPDATE_NAME);
		assertThat(resultDomain.getAge().get()).isEqualTo(CONST_DOMAIN_AGE);
		
		mongoRepository.delete(domain);
	}

	@Test
	public void dataRest_UpdateDomainNoNullField_DocumentNotExists_ShouldReturn404() throws Exception {
		
		mockMvc.perform(patch("/domains/{id}/updatenonull", CONST_DOMAIN_KEY)
				.content(getJsonBaseDomian())
				.header(RepositoryRestMvcHeader.updateNoMerge, ""))
			.andExpect(status().isNotFound());
	}

	@Test
	public void dataRest_UpdateMongoEmbeddedField_ShouldReturnDomainUpdated() throws Exception {
		
		final String embeddedId = ObjectId.get().toString();
	
		MongoDomain domain = MongoDomain.builder()
			.id(CONST_DOMAIN_KEY)
			.name(Optional.of(CONST_DOMAIN_NAME))
			.embeddedDomains(Arrays.asList(MongoEmbeddedDomain.builder()
					.id(embeddedId)
					.embeddedName("nameembedded")
						.build()))
				.build();
		
		mongoRepository.save(domain);
	
		mockMvc.perform(patch("/domains/{id}/mergeembedded/embeddedDomains", CONST_DOMAIN_KEY)
				.content(String.format("{\"id\":%1$s,\"embeddedDomains\":[{\"id\":\"%3$s\",\"embeddedName\":\"%2$s\"}]}", CONST_DOMAIN_KEY, CONST_DOMAIN_UPDATE_NAME, embeddedId))
				.header(RepositoryRestMvcHeader.updateNoMerge, ""))
			.andExpect(status().isNoContent());
		
		MongoDomain resultDomain = mongoRepository.findOne(CONST_DOMAIN_KEY);
		
		assertThat(resultDomain.getEmbeddedDomains().stream().findFirst().get()
				.getEmbeddedName()).contains(CONST_DOMAIN_UPDATE_NAME);
		
		mongoRepository.delete(domain);
	}
	
	private String getJsonBaseDomian() {
		return String.format("{\"id\":%s,\"name\":\"%s\"}", CONST_DOMAIN_KEY, CONST_DOMAIN_UPDATE_NAME);
	}
}
