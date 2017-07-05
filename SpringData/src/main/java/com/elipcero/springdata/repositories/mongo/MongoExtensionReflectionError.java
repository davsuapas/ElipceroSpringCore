package com.elipcero.springdata.repositories.mongo;

public class MongoExtensionReflectionError extends RuntimeException {
	
	private static final long serialVersionUID = 794012932736438074L;

	public MongoExtensionReflectionError() {
		super("Reflection error");
	}

}
