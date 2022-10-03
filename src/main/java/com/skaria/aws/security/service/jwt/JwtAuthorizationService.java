package com.skaria.aws.security.service.jwt;

import com.nimbusds.jwt.JWTClaimsSet;
import com.skaria.aws.security.model.JwtAuthentication;
import com.skaria.aws.security.service.AuthorizationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

/**
 * Jwt based implementation of {@link AuthorizationService}.
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class JwtAuthorizationService implements AuthorizationService {

    private final JwtUserService jwtUserService;
    private final JwtProcessor jwtProcessor;

    @Override
    public Authentication authorize(final String authToken) {

        final Optional<JWTClaimsSet> tokenClaims = parseToken(authToken);

        return tokenClaims.map(this::getAuthenticationFromTokenClaims).orElse(null);
    }

    private Optional<JWTClaimsSet> parseToken(final String token) {
        if (Objects.nonNull(token)) {
            return Optional.ofNullable(jwtProcessor.processJwt(token));
        } else {
            return Optional.empty();
        }
    }

    private JwtAuthentication getAuthenticationFromTokenClaims(final JWTClaimsSet tokenClaims) {
        final User user = jwtUserService.getUser(tokenClaims);
        return new JwtAuthentication(user, tokenClaims, user.getAuthorities());
    }
}
