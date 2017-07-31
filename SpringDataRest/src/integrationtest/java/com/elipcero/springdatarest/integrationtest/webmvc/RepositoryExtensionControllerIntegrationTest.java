package com.elipcero.springdatarest.integrationtest.webmvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
	public void dataRest_UpdateDomain_ShouldReturnDomain() throws Exception {
		
		MongoDomain domain = new MongoDomain("1", "name");
		
		mockMvc.perform(put("/domains/update/{id}", "1").content(SerializationUtil.asJsonString(domain)))
			.andExpect(status().isCreated());
			
	}
}
