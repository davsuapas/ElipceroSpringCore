package com.elipcero.springdatarest.webmvc;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Import;


/**
 * Active rest repository extension
 * Adds news resources
 * 
 * @author dav.sua.pas@gmail.com
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import(RepositoryExtensionRestMvcConfiguration.class)
public @interface EnabledRepositoryExtensionRestMvc {
}
