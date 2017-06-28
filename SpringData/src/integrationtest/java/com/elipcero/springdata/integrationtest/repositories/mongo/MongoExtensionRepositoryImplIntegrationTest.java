package com.elipcero.springdata.integrationtest.repositories.mongo;

import java.lang.reflect.InvocationTargetException;

import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationContextLoader;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.elipcero.springdata.integrationtest.configuration.mongo.MongoDataConfiguration;

import static com.elipcero.springdata.integrationtest.repositories.mongo.TestMongoExtensionEntityAssert.assertThat;

@SuppressWarnings("deprecation")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = MongoDataConfiguration.class, loader = SpringApplicationContextLoader.class)
public class MongoExtensionRepositoryImplIntegrationTest {
	
	private static final String TESTMONGO_NAME_ONE = "nameEmbedded1";
	private static final String TESTMONGO_NAME_TWO = "nameEmbedded2";
	
	@Autowired
	private TestMongoExtensionRepository testMongoExtendedRepository;
	
	@Test
	public void mergeEmbeddedRelation_UpdateOneItemInEmbeddedRelation_ShouldReturnVoid() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		
		TestMongoEntity testMongoEntity = 
				TestMongoEntity.CreateTestMongoEntity(TestMongoExtensionEntity.TESTMONGO_EMBEDDEDID, TESTMONGO_NAME_TWO);
		
		this.createSample();
				
		this.testMongoExtendedRepository.mergeEmbeddedRelation(testMongoEntity, "embeddedEntities", TestMongoExtensionEntity.class);
	
		testMongoEntity = this.testMongoExtendedRepository.findOne(TestMongoEntity.TESTMONGO_ID);
		
		assertThat(testMongoEntity.getEmbeddedEntities().stream().findFirst().get())
			.hasName(TESTMONGO_NAME_TWO);
		
		this.deleteSample();
	}
	
	@Test
	public void mergeEmbeddedRelation_InsertOneItemInEmbeddedRelation_ShouldReturnVoid() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		
		String addId = ObjectId.get().toString();
		
		TestMongoEntity testMongoEntity = TestMongoEntity.CreateTestMongoEntity(addId, TESTMONGO_NAME_TWO);
		
		this.createSample();
				
		this.testMongoExtendedRepository.mergeEmbeddedRelation(testMongoEntity, "embeddedEntities", TestMongoExtensionEntity.class);
		
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
