package com.elipcero.springsecurity.oauth2.repository;

import com.elipcero.springsecurity.oauth2.OAuth2AuthenticationRefreshToken;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Repository for refresh access token. This repository is used by @see MongoDBTokenStore
 * 
 * @author David Suárez Pascual
 */
@RepositoryRestResource(exported = false)
public interface OAuth2RefreshTokenRepository extends MongoRepository<OAuth2AuthenticationRefreshToken, String> {

    /**
     * Find by token id.
     *
     * @param tokenId the token id
     * @return the authentication refresh token
     */
    OAuth2AuthenticationRefreshToken findByTokenId(String tokenId);
}