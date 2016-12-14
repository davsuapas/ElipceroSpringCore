package com.elipcero.springdata.integrationtest.repositories.mongo;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@TypeAlias("tme")
@Document(collection = "testmongoentity")
public class TestMongoEntity {
	
	public static final String TESTMONGO_ID = ObjectId.get().toString();	
	
	@Id
	private String id;
	
	@Field("n")
	private String name;
	
	@Field("ebde")
	private List<TestMongoEmbeddedEntity> embeddedEntities = new ArrayList<TestMongoEmbeddedEntity>();
	
	public List<TestMongoEmbeddedEntity> getEmbeddedEntities() {
		return embeddedEntities;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public static Builder getBuilder() {
        return new Builder();
    }	
	
	public static class Builder {
		
		private final TestMongoEntity testMongoEntity = new TestMongoEntity();
		  
		public Builder withId(String value) {
			this.testMongoEntity.id = value;
			return this;
		}
		
		public Builder withName(String value) {
			this.testMongoEntity.name = value;
			return this;
		}		
		
		public TestMongoEmbeddedEntity.Builder addEmbedded() {
			TestMongoEmbeddedEntity.Builder buildEmbedded =	TestMongoEmbeddedEntity.getBuilder().setParent(this);
			this.testMongoEntity.embeddedEntities.add(buildEmbedded.build());
			return buildEmbedded;
		}				
				
		public TestMongoEntity build() {
			return this.testMongoEntity;
		}			
	}
	
	public static TestMongoEntity CreateTestMongoEntity(String embeddedId, String name) {
		
		return TestMongoEntity.getBuilder()
					.withId(TestMongoEntity.TESTMONGO_ID)
					.withName("name")
					.addEmbedded()
						.withId(embeddedId)
						.withName(name)
						.parent()
					.build();
	}
}
