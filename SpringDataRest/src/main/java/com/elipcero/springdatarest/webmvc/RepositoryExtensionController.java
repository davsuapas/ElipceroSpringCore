package com.elipcero.springdatarest.webmvc;

import java.io.Serializable;

import org.springframework.data.repository.support.RepositoryInvoker;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.core.mapping.ResourceType;
import org.springframework.data.rest.webmvc.ControllerUtils;
import org.springframework.data.rest.webmvc.HttpHeadersPreparer;
import org.springframework.data.rest.webmvc.PersistentEntityResource;
import org.springframework.data.rest.webmvc.PersistentEntityResourceAssembler;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.data.rest.webmvc.RootResourceInformation;
import org.springframework.data.rest.webmvc.support.BackendId;
import org.springframework.data.rest.webmvc.support.ETag;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.elipcero.springdata.repositories.base.RepositoryExtensionInvoker;
import com.elipcero.springdata.repositories.mongo.MongoRepositoryExtensionInvoker;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;



/**
 * The class RepositoryExtensionController adds more resources 
 * 
 * @author dav.sua.pas@gmail.com
 */
@RepositoryRestController
@RequiredArgsConstructor
public class RepositoryExtensionController {
	
	private static final String BASE_MAPPING = "/{repository}";
	private static final String ACCEPT_HEADER = "Accept";
	
	private @NonNull final HttpHeadersPreparer headersPreparer;
	private @NonNull final RepositoryRestConfiguration config;

	/**
	 * Patch item for updatenonull from extension repository. Look {@link com.elipcero.springdata}
	 *
	 */
	@RequestMapping(value = BASE_MAPPING + "/{id}/updatenonull", method = RequestMethod.PATCH)
	public ResponseEntity<? extends ResourceSupport> patchUpdateResource(
			RootResourceInformation resourceInformation,
			PersistentEntityResource payload,
			@BackendId Serializable id,
			PersistentEntityResourceAssembler assembler,
			ETag eTag,
			@RequestHeader(value = ACCEPT_HEADER, required = false) String acceptHeader) 
					throws HttpRequestMethodNotSupportedException, ResourceNotFoundException {

		resourceInformation.verifySupportedMethod(HttpMethod.PATCH, ResourceType.ITEM);
		
		RepositoryInvoker invoker = resourceInformation.getInvoker();
		RepositoryExtensionInvoker invokerExtension = null;
		
		if (invoker instanceof RepositoryExtensionInvoker) {
			invokerExtension = (RepositoryExtensionInvoker)invoker;
		}
		else {
			throw new NotFoundRepositoryExtensionInvokerException(RepositoryExtensionInvoker.class.getName());
		}
		
		Object objectToUpdate = payload.getContent();
		
		eTag.verify(resourceInformation.getPersistentEntity(), objectToUpdate);
		
		Object resultUpdating = invokerExtension.invokeUpdateNoNulls(objectToUpdate);
		
		PersistentEntityResource resource = assembler.toFullResource(resultUpdating);
		HttpHeaders headers = headersPreparer.prepareHeaders(resource);

		if (config.returnBodyOnUpdate(acceptHeader)) {
			return ControllerUtils.toResponseEntity(HttpStatus.OK, headers, resource);
		} else {
			return ControllerUtils.toEmptyResponse(HttpStatus.NO_CONTENT, headers);
		}		
	}
	
	/**
	 * Patch item for mergeEmbedded from mongo extension repository. Look {@link com.elipcero.springdata}
	 *
	 */
	@RequestMapping(value = BASE_MAPPING + "/{id}/mergeembedded/{embeddedProperty}", method = RequestMethod.PATCH)
	public ResponseEntity<? extends ResourceSupport> patchMergeEmbeddedResource(
			RootResourceInformation resourceInformation,
			PersistentEntityResource payload,
			@BackendId Serializable id,
			ETag eTag,
			@PathVariable String embeddedProperty) throws HttpRequestMethodNotSupportedException, ResourceNotFoundException {

		resourceInformation.verifySupportedMethod(HttpMethod.PATCH, ResourceType.ITEM);
		
		RepositoryInvoker invoker = resourceInformation.getInvoker();
		MongoRepositoryExtensionInvoker invokerExtension = null;
		
		if (invoker instanceof MongoRepositoryExtensionInvoker) {
			invokerExtension = (MongoRepositoryExtensionInvoker)invoker;
		}
		else {
			throw new NotFoundRepositoryExtensionInvokerException(MongoRepositoryExtensionInvoker.class.getName());
		}
		
		Object objectToUpdate = payload.getContent();
		
		eTag.verify(resourceInformation.getPersistentEntity(), objectToUpdate);
		
		invokerExtension.invokeMergeEmbedded(objectToUpdate, embeddedProperty);
		
		return ControllerUtils.toEmptyResponse(HttpStatus.NO_CONTENT);
	}
}
