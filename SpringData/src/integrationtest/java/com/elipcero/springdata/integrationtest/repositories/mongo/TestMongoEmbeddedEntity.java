package com.elipcero.springdata.integrationtest.repositories.mongo;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

public class TestMongoEmbeddedEntity {
	
	public static final String TESTMONGO_EMBEDDEDID = ObjectId.get().toString();	

	@Id
	private String id;
	
	@Field("n")
	private String name;

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
		
		private final TestMongoEmbeddedEntity testMongoEmbeddedEntity = new TestMongoEmbeddedEntity();
		
		private TestMongoEntity.Builder parent;
		
		public Builder setParent(TestMongoEntity.Builder parent) {
			this.parent = parent;
			return this;
		}		
		
		public TestMongoEntity.Builder parent() {
			return this.parent;
		}
		  
		public Builder withId(String value) {
			this.testMongoEmbeddedEntity.id = value;
			return this;
		}
		
		public Builder withName(String value) {
			this.testMongoEmbeddedEntity.name = value;
			return this;
		}		
		
		public TestMongoEmbeddedEntity build() {
			return this.testMongoEmbeddedEntity;
		}			
	}
}
