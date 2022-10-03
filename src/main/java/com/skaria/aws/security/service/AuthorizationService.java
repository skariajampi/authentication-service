package com.skaria.aws.security.service;

import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;

/**
 * Service providing {@link Authentication} by {@link HttpServletRequest}.
 */
public interface AuthorizationService {

    /**
     * Get {@link Authentication} by {@link HttpServletRequest}.
     *
     * @param authToken the request
     * @return the authentication
     */
    Authentication authorize(String authToken) throws Exception;
}
