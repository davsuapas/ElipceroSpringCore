package com.elipcero.springdata.repositories.mongo;

public class MongoExtensionPropertyIdNotFoundException extends RuntimeException {
		
	private static final long serialVersionUID = 532539054205001298L;

	public MongoExtensionPropertyIdNotFoundException() {
		super("The property Id (ObjectId) not found");
	}

}
