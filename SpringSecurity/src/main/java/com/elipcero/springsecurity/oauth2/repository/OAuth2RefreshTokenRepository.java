package com.elipcero.springsecurity.oauth2.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.elipcero.springsecurity.oauth2.OAuth2AuthenticationRefreshToken;

/**
 * Repository for refresh access token. This repository is used by @see MongoDBTokenStore
 * 
 * @author David Suárez Pascual
 */
public interface OAuth2RefreshTokenRepository extends MongoRepository<OAuth2AuthenticationRefreshToken, String> {

    /**
     * Find by token id.
     *
     * @param tokenId the token id
     * @return the authentication refresh token
     */
    public OAuth2AuthenticationRefreshToken findByTokenId(String tokenId);
}