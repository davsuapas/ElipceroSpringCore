package com.elipcero.springgeneral.unittest.validation;

import com.elipcero.springgeneral.validations.EmailValidator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnit4.class)
public class EmailValidatorUnitTest {

	@Test
	public void validator_SetEmail1Right_ShouldReturnOk() {
		
		EmailValidator validator = new EmailValidator();
		assertThat(validator.isValid("pepe@domain.es", null)).isTrue();
	}
	
	@Test
	public void validator_SetEmail2Right_ShouldReturnOk() {
		
		EmailValidator validator = new EmailValidator();
		assertThat(validator.isValid("pepe@domain.edu", null)).isTrue();
	}
	
	@Test
	public void validator_SetEmail1Wrogn_ShouldReturnFalse() {
		
		EmailValidator validator = new EmailValidator();
		assertThat(validator.isValid("pepe@domain", null)).isFalse();
	}

	@Test
	public void validator_SetEmail2Wrogn_ShouldReturnFalse() {
		
		EmailValidator validator = new EmailValidator();
		assertThat(validator.isValid("pepedomain.es", null)).isFalse();
	}
}
