package com.elipcero.springdata.integrationtest.repositories.mongo;

import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.Assertions;

public class TestMongoEntityAssert extends AbstractAssert<TestMongoEntityAssert, TestMongoEntity> {

	private TestMongoEntityAssert(TestMongoEntity actual) {
		super(actual, TestMongoEntityAssert.class);
	}
    
	public static TestMongoEntityAssert assertThat(TestMongoEntity actual) {
		return new TestMongoEntityAssert(actual);
    }

    public TestMongoEntityAssert hasEmbddedEntityById(String id) {
    	
    	Assertions.assertThat(this.actual.getEmbeddedEntities())
       				.filteredOn("id", id)
       				.overridingErrorMessage("There is not exist the embdded entity by id: %s ", id)       				
    				.isNotEmpty();
    				
    	return this;
    }
}

