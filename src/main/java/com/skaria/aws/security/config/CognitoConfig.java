package com.skaria.aws.security.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProviderClientBuilder;
import com.amazonaws.services.cognitoidp.model.AuthFlowType;
import com.amazonaws.services.cognitoidp.model.InitiateAuthRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



@Configuration
public class CognitoConfig {

    @Value(value = "${aws.region}")
    private String region;

    @Value(value = "${aws.access-key}")
    private String accessKey;

    @Value(value = "${aws.access-secret}")
    private String secretKey;

    @Value(value = "${clientId}")
    private String clientId;

    @Value(value = "${userPoolId}")
    private String userPoolId;

    @Value(value = "${clientSecret}")
    private String clientSecret;


    @Bean
    public AWSCognitoIdentityProvider awsCognitoIdentityProvider() {
        BasicAWSCredentials awsCreds = new BasicAWSCredentials(accessKey, secretKey);

        return AWSCognitoIdentityProviderClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsCreds)).withRegion(region).build();
    }

    @Bean
    public InitiateAuthRequest initiateAuthRequest() {
        final InitiateAuthRequest authRequest = new InitiateAuthRequest();
        authRequest.withAuthFlow(AuthFlowType.USER_PASSWORD_AUTH).withClientId(clientId);
        return authRequest;
    }

    @Bean
    public String region() {
        return this.region;
    }

    @Bean
    public String clientId() {
        return this.clientId;
    }

    @Bean
    public String clientSecret() {
        return this.clientSecret;
    }

}
