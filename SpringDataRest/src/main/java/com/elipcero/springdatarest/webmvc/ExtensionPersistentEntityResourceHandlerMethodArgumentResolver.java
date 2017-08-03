package com.elipcero.springdatarest.webmvc;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.MethodParameter;
import org.springframework.data.mapping.PersistentEntity;
import org.springframework.data.rest.webmvc.IncomingRequest;
import org.springframework.data.rest.webmvc.PersistentEntityResource;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.data.rest.webmvc.RootResourceInformation;
import org.springframework.data.rest.webmvc.PersistentEntityResource.Builder;
import org.springframework.data.rest.webmvc.config.PersistentEntityResourceHandlerMethodArgumentResolver;
import org.springframework.data.rest.webmvc.config.RootResourceInformationHandlerMethodArgumentResolver;
import org.springframework.data.rest.webmvc.json.DomainObjectReader;
import org.springframework.data.rest.webmvc.support.BackendIdHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.elipcero.springdata.repositories.base.RepositoryExtensionInvoker;

/**
 * Override resolveArgument for getting more features of others repositories.
 *
 * @author dav.sua.pas@gmail.com
 */
public class ExtensionPersistentEntityResourceHandlerMethodArgumentResolver extends PersistentEntityResourceHandlerMethodArgumentResolver {
	
	private static final String ERROR_MESSAGE = "Could not read an object of type %s from the request!";
	private static final String NO_CONVERTER_FOUND = "No suitable HttpMessageConverter found to read request body into object of type %s from request with content type of %s!";	
	
	private final BackendIdHandlerMethodArgumentResolver idResolver;
	private final RootResourceInformationHandlerMethodArgumentResolver resourceInformationResolver;
	private final List<HttpMessageConverter<?>> messageConverters;

	public ExtensionPersistentEntityResourceHandlerMethodArgumentResolver(
			List<HttpMessageConverter<?>> messageConverters,
			RootResourceInformationHandlerMethodArgumentResolver resourceInformationResolver,
			BackendIdHandlerMethodArgumentResolver idResolver, DomainObjectReader reader) {
		super(messageConverters, resourceInformationResolver, idResolver, reader);
		
		this.idResolver= idResolver;
		this.resourceInformationResolver = resourceInformationResolver;
		this.messageConverters = messageConverters;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.springframework.web.method.support.HandlerMethodArgumentResolver#resolveArgument(org.springframework.core.MethodParameter, org.springframework.web.method.support.ModelAndViewContainer, org.springframework.web.context.request.NativeWebRequest, org.springframework.web.bind.support.WebDataBinderFactory)
	 * 
	 * If update-no-merge exists in head and patch method is used 
	 * then the class avoids reading in the repository for a better yield
	 * In addition, checks the resource if exists, without return then domain
	 */
	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })	
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		
		HttpServletRequest nativeRequest = webRequest.getNativeRequest(HttpServletRequest.class);
		ServletServerHttpRequest request = new ServletServerHttpRequest(nativeRequest);
		IncomingRequest incoming = new IncomingRequest(request);
		
		if (updateNoMerge(webRequest, incoming)) {

			RootResourceInformation resourceInformation = resourceInformationResolver.resolveArgument(parameter, mavContainer,
					webRequest, binderFactory);

			Class<?> domainType = resourceInformation.getDomainType();
			MediaType contentType = request.getHeaders().getContentType();
	
			for (HttpMessageConverter converter : messageConverters) {
	
				if (!converter.canRead(PersistentEntityResource.class, contentType)) {
					continue;
				}
	
				Serializable id = idResolver.resolveArgument(parameter, mavContainer, webRequest, binderFactory);
				
				CheckIfExistsResource(id, resourceInformation);
	
				Object obj = readContent(incoming, converter, resourceInformation);
	
				if (obj == null) {
					throw new HttpMessageNotReadableException(String.format(ERROR_MESSAGE, domainType));
				}
				
				PersistentEntity<?, ?> entity = resourceInformation.getPersistentEntity();
				Builder build = PersistentEntityResource.build(obj, entity);
				return build.build(); 
			}
			
			throw new HttpMessageNotReadableException(String.format(NO_CONVERTER_FOUND, domainType, contentType));
		}
		else { 
			return super.resolveArgument(parameter, mavContainer, webRequest, binderFactory);
		}
	}
	
	private static void CheckIfExistsResource(Serializable id, RootResourceInformation information) {
		
		RepositoryExtensionInvoker invokerExtension;
		
		if (information.getInvoker() instanceof RepositoryExtensionInvoker) {
			invokerExtension = (RepositoryExtensionInvoker)information.getInvoker();
		}
		else {
			throw new NotFoundRepositoryExtensionInvokerException(RepositoryExtensionInvoker.class.getName());
		}
		
		if (id == null || !invokerExtension.invokeExists(id)) {
			throw new ResourceNotFoundException();
		}
	}

	private static Boolean updateNoMerge(NativeWebRequest webRequest, IncomingRequest incomingRequest) {
		return incomingRequest.isPatchRequest() &&
				webRequest.getHeader(RepositoryRestMvcHeader.updateNoMerge) != null; 
	}
	
	private static Object readContent(IncomingRequest request, HttpMessageConverter<Object> converter, RootResourceInformation information) {

		try {
			return converter.read(information.getDomainType(), request.getServerHttpRequest());
		} catch (IOException o_O) {
			throw new HttpMessageNotReadableException(String.format(ERROR_MESSAGE, information.getDomainType()), o_O);
		}
	}
}
