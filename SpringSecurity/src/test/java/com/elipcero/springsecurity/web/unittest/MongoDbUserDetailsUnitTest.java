package com.elipcero.springsecurity.web.unittest;

import com.elipcero.springsecurity.web.MongoDbUserDetails;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
public class MongoDbUserDetailsUnitTest {

    private static final String USER_NAME = "userName";
    private static final String PASSWORD = "Password";

    @Test
    public void userDetails_CreateConstructor_shouldReturnMongoDbUserDetails() {

        MongoDbUserDetails mongoDbUserDetails = new MongoDbUserDetails(USER_NAME, PASSWORD);

        assertThat(mongoDbUserDetails.getUsername()).isEqualTo(USER_NAME);
        assertThat(mongoDbUserDetails.getPassword()).isEqualTo(PASSWORD);
        assertThat(mongoDbUserDetails.getAuthorities()).isNull();
        assertThat(mongoDbUserDetails.isAccountNonExpired()).isTrue();
        assertThat(mongoDbUserDetails.isAccountNonLocked()).isTrue();
        assertThat(mongoDbUserDetails.isCredentialsNonExpired()).isTrue();
        assertThat(mongoDbUserDetails.isEnabled()).isTrue();
    }
}
