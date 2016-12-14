package com.elipcero.springdata.repositories.mongo;

public class MongoEmbeddedPropertyIdNotFoundException extends RuntimeException {
		
		private static final long serialVersionUID = 532539054205001298L;

		public MongoEmbeddedPropertyIdNotFoundException() {
			super("The property Id (ObjectId) not found");
		}

}
