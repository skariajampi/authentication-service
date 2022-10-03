package com.skaria.aws.security.service.jwt;

import com.nimbusds.jwt.JWTClaimsSet;

/**
 * Service for parsing jwt claims and verifying signature.
 */
public interface JwtProcessor {

    /**
     * Process token and obtain payload.
     *
     * @param token the token
     * @return the jwt claims set
     */
    JWTClaimsSet processJwt(String token) throws SecurityException;
}
