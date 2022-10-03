package com.skaria.aws.security.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Jwt configuration properties model.
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "aws")
public class JwtConfiguration {

    private String jwkUrl;
    private String userNameField;
    private String groupsField;
    private int connectionTimeout;
    private int readTimeout;
    @Value(value = "${spring.security.oauth2.client.provider.cognito.issuerUri}")
    private String cognitoIdentityPoolUrl;

    /**
     * Gets jwk url.
     *
     * @return the jwk url
     */
    public String getJwkUrl() {
        return jwkUrl;
    }
}
