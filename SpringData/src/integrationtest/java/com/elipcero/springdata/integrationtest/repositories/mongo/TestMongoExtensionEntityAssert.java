package com.elipcero.springdata.integrationtest.repositories.mongo;

import org.assertj.core.api.AbstractAssert;
import org.assertj.core.util.Objects;

public class TestMongoExtensionEntityAssert extends AbstractAssert<TestMongoExtensionEntityAssert, TestMongoExtensionEntity> {

	private TestMongoExtensionEntityAssert(TestMongoExtensionEntity actual) {
		super(actual, TestMongoExtensionEntityAssert.class);
	}
    
	public static TestMongoExtensionEntityAssert assertThat(TestMongoExtensionEntity actual) {
		return new TestMongoExtensionEntityAssert(actual);
    }

    public TestMongoExtensionEntityAssert hasName(String name) {
    	
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

