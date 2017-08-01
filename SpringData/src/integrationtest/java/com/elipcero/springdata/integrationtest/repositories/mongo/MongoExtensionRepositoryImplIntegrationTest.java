package com.elipcero.springdata.integrationtest.repositories.mongo;

import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.elipcero.springdata.integrationtest.configuration.mongo.MongoDataConfiguration;

import org.assertj.core.api.Assertions;

import static com.elipcero.springdata.integrationtest.repositories.mongo.TestMongoExtensionEntityAssert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = MongoDataConfiguration.class)
public class MongoExtensionRepositoryImplIntegrationTest {
	
	private static final String TESTMONGO_NAME_ONE = "nameEmbedded1";
	private static final String TESTMONGO_NAME_TWO = "nameEmbedded2";
	
	@Autowired
	private TestMongoExtensionRepository testMongoExtendedRepository;
	
	@Test
	public void mongoExtension_UpdateNoNulls_ShouldReturnVoid() {
		
		TestMongoEntity testMongoEntity = 
			TestMongoEntity.CreateTestMongoEntityWithNull();
		
		this.testMongoExtendedRepository.save(
			TestMongoEntity.CreateTestMongoEntity()
		);
				
		TestMongoEntity updated = this.testMongoExtendedRepository.updateNoNulls(testMongoEntity);
	
		testMongoEntity = this.testMongoExtendedRepository.findOne(TestMongoEntity.TESTMONGO_ID);
		
		Assertions.assertThat(updated).isNotNull();
		Assertions.assertThat(testMongoEntity.getName().get()).isEqualTo("name1");
		Assertions.assertThat(testMongoEntity.getNumber().get()).isEqualTo(3);
		
		this.deleteSample();
	}
	
	@Test
	public void mongoExtension_UpdateOneItemInEmbeddedRelation_ShouldReturnVoid() {
		
		TestMongoEntity testMongoEntity = 
				TestMongoEntity.CreateTestMongoEntity(TestMongoExtensionEntity.TESTMONGO_EMBEDDEDID, TESTMONGO_NAME_TWO);
		
		this.createSample();
				
		this.testMongoExtendedRepository.mergeEmbeddedRelation(testMongoEntity, "embeddedEntities");
	
		testMongoEntity = this.testMongoExtendedRepository.findOne(TestMongoEntity.TESTMONGO_ID);
		
		assertThat(testMongoEntity.getEmbeddedEntities().stream().findFirst().get())
			.hasName(TESTMONGO_NAME_TWO);
		
		this.deleteSample();
	}
	
	@Test
	public void mongoExtension_InsertOneItemInEmbeddedRelation_ShouldReturnVoid() {
		
		String addId = ObjectId.get().toString();
		
		TestMongoEntity testMongoEntity = TestMongoEntity.CreateTestMongoEntity(addId, TESTMONGO_NAME_TWO);
		
		this.createSample();
				
		this.testMongoExtendedRepository.mergeEmbeddedRelation(testMongoEntity, "embeddedEntities");
		
		testMongoEntity = this.testMongoExtendedRepository.findOne(TestMongoEntity.TESTMONGO_ID);
		
		TestMongoEntityAssert.assertThat(testMongoEntity)
			.hasEmbddedEntityById(addId);
			
		this.deleteSample();
	}
	
	private void createSample() {
		this.testMongoExtendedRepository.save(
				TestMongoEntity.CreateTestMongoEntity(TestMongoExtensionEntity.TESTMONGO_EMBEDDEDID, TESTMONGO_NAME_ONE)
		);
	}
	
	private void deleteSample() {
		this.testMongoExtendedRepository.delete(TestMongoEntity.TESTMONGO_ID);
	}
}
