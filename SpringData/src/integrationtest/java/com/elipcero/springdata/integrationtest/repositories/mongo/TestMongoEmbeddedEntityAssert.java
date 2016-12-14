package com.elipcero.springdata.integrationtest.repositories.mongo;

import org.assertj.core.api.AbstractAssert;
import org.assertj.core.util.Objects;

public class TestMongoEmbeddedEntityAssert extends AbstractAssert<TestMongoEmbeddedEntityAssert, TestMongoEmbeddedEntity> {

	private TestMongoEmbeddedEntityAssert(TestMongoEmbeddedEntity actual) {
		super(actual, TestMongoEmbeddedEntityAssert.class);
	}
    
	public static TestMongoEmbeddedEntityAssert assertThat(TestMongoEmbeddedEntity actual) {
		return new TestMongoEmbeddedEntityAssert(actual);
    }

    public TestMongoEmbeddedEntityAssert hasName(String name) {
    	
    	// check that actual AquariumEntity we want to make assertions on is not null.
        isNotNull();

        // overrides the default error message with a more explicit one
        String assertjErrorMessage = "\nExpecting name of:\n  <%s>\nto be:\n  <%s>\nbut was:\n  <%s>";
        
        // null safe check
        String actualName = actual.getName();
        if (!Objects.areEqual(actualName, name)) {
          failWithMessage(assertjErrorMessage, actual, name, actualName);
        }

        // return the current assertion for method chaining
        return this;
    }
}

