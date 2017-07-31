package com.elipcero.springdatarest.webmvc;

import java.io.Serializable;

import org.springframework.data.repository.support.RepositoryInvoker;
import org.springframework.data.rest.core.mapping.ResourceType;
import org.springframework.data.rest.webmvc.PersistentEntityResource;
import org.springframework.data.rest.webmvc.PersistentEntityResourceAssembler;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.data.rest.webmvc.RootResourceInformation;
import org.springframework.data.rest.webmvc.support.BackendId;
import org.springframework.data.rest.webmvc.support.ETag;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.elipcero.springdata.repositories.base.RepositoryExtensionInvoker;

@RepositoryRestController
public class RepositoryExtensionController {
	
	private static final String BASE_MAPPING = "/{repository}";
	
	private static final String ACCEPT_HEADER = "Accept";

	@RequestMapping(value = BASE_MAPPING + "/update/{id}", method = RequestMethod.PUT)
	public ResponseEntity<? extends ResourceSupport> putItemResource(
			RootResourceInformation resourceInformation,
			PersistentEntityResource payload,
			@BackendId Serializable id,
			PersistentEntityResourceAssembler assembler,
			ETag eTag,
			@RequestHeader(value = ACCEPT_HEADER, required = false) String acceptHeader) 
					throws HttpRequestMethodNotSupportedException, ResourceNotFoundException {

		resourceInformation.verifySupportedMethod(HttpMethod.PUT, ResourceType.ITEM);
		
		RepositoryInvoker invoker = resourceInformation.getInvoker();
		RepositoryExtensionInvoker invokerExtension = null;
		
		if (invoker instanceof RepositoryExtensionInvoker) {
			invokerExtension = (RepositoryExtensionInvoker)invoker;
		}
		else {
			
		}
		
		Object objectToUpdte = payload.getContent();
		eTag.verify(resourceInformation.getPersistentEntity(), objectToUpdte);
		
		invokerExtension.invokeUpdateNoNulls(objectToUpdte);
		
		return null;
	}
}
