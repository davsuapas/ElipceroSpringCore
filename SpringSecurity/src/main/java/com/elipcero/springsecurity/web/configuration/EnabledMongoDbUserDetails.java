package com.elipcero.springsecurity.web.configuration;

import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Configure mongo user details
 *
 * Use:
 *
 * <p>
 *      @EnabledMongoDbUserDetails
 *      public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
 *      }
 *
 *      @Autowired
 *      private UserDetailsService mongoDbUserDetails;
 *
 *      @Override
 *      protected void configure(AuthenticationManagerBuilder auth) throws Exception {
 *           auth.userDetailsService(mongoDbUserDetails);
 *      }
 * </p>
 *
 * @author dav.sua.pas@gmail.com
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import(MongoDbUserDetailsConfiguration.class)
public @interface EnabledMongoDbUserDetails {
}
