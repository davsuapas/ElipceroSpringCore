package com.elipcero.springdatarest.webmvc;

public class NotFoundRepositoryExtensionInvokerException extends RuntimeException {

	private static final long serialVersionUID = 6589874818315638988L;
	
	public NotFoundRepositoryExtensionInvokerException() {
		super("Not found repository extension");
	}
}
