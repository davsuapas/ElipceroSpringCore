package com.elipcero.springdata.integrationtest.repositories.mongo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
	
	private Optional<String> name;
	
	private Optional<Integer> number;
	
	@Field("ebde")
	private List<TestMongoExtensionEntity> embeddedEntities = new ArrayList<TestMongoExtensionEntity>();
	
	public List<TestMongoExtensionEntity> getEmbeddedEntities() {
		return embeddedEntities;
	}

	public String getId() {
		return id;
	}

	public Optional<String> getName() {
		return name;
	}

	public Optional<Integer> getNumber() {
		return number;
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
		
		public Builder withName(Optional<String> value) {
			this.testMongoEntity.name = value;
			return this;
		}		

		public Builder withNumber(Optional<Integer> value) {
			this.testMongoEntity.number = value;
			return this;
		}		

		public TestMongoExtensionEntity.Builder addEmbedded() {
			TestMongoExtensionEntity.Builder buildEmbedded =	TestMongoExtensionEntity.getBuilder().setParent(this);
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
					.withName(Optional.of("name"))
					.withNumber(Optional.of(3))
					.addEmbedded()
						.withId(embeddedId)
						.withName(name)
						.parent()
					.build();
	}
	
	public static TestMongoEntity CreateTestMongoEntity() {
		
		return TestMongoEntity.getBuilder()
					.withId(TestMongoEntity.TESTMONGO_ID)
					.withName(Optional.of("name"))
					.withNumber(Optional.of(3))
					.build();
	}

	public static TestMongoEntity CreateTestMongoEntityWithNull() {
		
		return TestMongoEntity.getBuilder()
					.withId(TestMongoEntity.TESTMONGO_ID)
					.withName(Optional.of("name1"))
					.withNumber(Optional.empty())
					.build();
	}

}
