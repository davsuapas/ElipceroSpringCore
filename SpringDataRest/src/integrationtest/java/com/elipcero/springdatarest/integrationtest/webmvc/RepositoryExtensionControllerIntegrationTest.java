package com.elipcero.springdatarest.integrationtest.webmvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.elipcero.springcore.serialization.SerializationUtil;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = RestWebMvcConfiguration.class)
public class RepositoryExtensionControllerIntegrationTest {
	
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
	public void dataRest_UpdateDomainNoNullField_ShouldReturnDomain() throws Exception {
		
		final String CONST_DOMAIN_KEY = "1";
		final String CONST_DOMAIN_UPDATE_NAME = "name1";
		
		MongoDomain domain = new MongoDomain(CONST_DOMAIN_KEY, Optional.of("name"));
		
		mongoRepository.save(domain);
		
		domain.setName(Optional.of(CONST_DOMAIN_UPDATE_NAME));
	
		mongoRepository.save(domain);
		
		mockMvc.perform(patch("/domains/updatenonull/{id}", CONST_DOMAIN_KEY).content(SerializationUtil.asJsonString(domain)))
			.andExpect(status().isNoContent());
		
		MongoDomain resultDomain = mongoRepository.findOne(CONST_DOMAIN_KEY);
		
		assertThat(resultDomain.getName().get()).contains(CONST_DOMAIN_UPDATE_NAME);
		
		mongoRepository.delete(domain);
	}
}
