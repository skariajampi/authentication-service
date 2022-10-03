package com.skaria.aws.security.service.jwt;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.proc.BadJOSEException;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.proc.ConfigurableJWTProcessor;
import com.skaria.aws.security.config.JwtConfiguration;
import com.skaria.aws.security.exception.InvalidTokenIssuerException;
import com.skaria.aws.security.exception.InvalidTokenTypeException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;

/**
 * Implementation of {@link JwtProcessor} based on AWS jwt.
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AwsJwtProcessor implements JwtProcessor {

    private final JwtConfiguration configuration;

    private final ConfigurableJWTProcessor configurableJWTProcessor;

    @Override
    public JWTClaimsSet processJwt(final String token) throws SecurityException{
        try {
            final JWTClaimsSet tokenClaims = configurableJWTProcessor.process(token, null);

            if (!isIssuedCorrectly(tokenClaims)) {
                final String errorMessage = String.format("Issuer %s in JWT token doesn't match cognito idp %s",
                        tokenClaims.getIssuer(),
                        configuration.getCognitoIdentityPoolUrl());

                throw new InvalidTokenIssuerException(errorMessage);
            }

            if (!isIdToken(tokenClaims)) {
                throw new InvalidTokenTypeException("JWT Token doesn't seem to be an ID Token");
            }

            return tokenClaims;
        } catch (ParseException | BadJOSEException | JOSEException e) {
            log.error("Error during parsing token claims: {}", e.getMessage());
            throw new SecurityException("Error during processing of token");
        }

    }

    private boolean isIssuedCorrectly(final JWTClaimsSet claimsSet) {
        return claimsSet.getIssuer().equals(configuration.getCognitoIdentityPoolUrl());
    }

    private boolean isIdToken(final JWTClaimsSet claimsSet) {
        return claimsSet.getClaim("token_use").equals("access");
    }
}
