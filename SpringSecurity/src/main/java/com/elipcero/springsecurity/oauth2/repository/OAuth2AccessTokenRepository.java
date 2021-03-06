package com.elipcero.springsecurity.oauth2.repository;

import com.elipcero.springsecurity.oauth2.OAuth2AuthenticationAccessToken;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

/**
 * Repository for access token. This repository is used by @see MongoDBTokenStore
 * 
 *  @author David Suárez Pascual
 */
@RepositoryRestResource(exported = false)
public interface OAuth2AccessTokenRepository extends MongoRepository<OAuth2AuthenticationAccessToken, String> {

    /**
     * Find by token id.
     *
     * @param tokenId the token id
     * @return the authentication access token
     */
    OAuth2AuthenticationAccessToken findByTokenId(String tokenId);

    /**
     * Find by refresh token id.
     *
     * @param refreshToken the refresh token id
     * @return the authentication access token
     */
    OAuth2AuthenticationAccessToken findByRefreshTokenId(String refreshToken);

    /**
     * Find by authentication id.
     *
     * @param authenticationId the authentication id
     * @return the authentication access token
     */
    OAuth2AuthenticationAccessToken findByAuthenticationId(String authenticationId);

    /**
     * Find by client id and user name.
     *
     * @param clientId the client id
     * @param userName the user name
     * @return the list all token by client id and user name
     */
    List<OAuth2AuthenticationAccessToken> findByClientIdAndUserName(String clientId, String userName);

    /**
     * Find by client id.
     *
     * @param clientId the client id
     * @return the list all token by client
     */
    List<OAuth2AuthenticationAccessToken> findByClientId(String clientId);
}