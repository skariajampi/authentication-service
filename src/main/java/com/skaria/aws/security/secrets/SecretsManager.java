package com.skaria.aws.security.secrets;

import com.amazonaws.services.secretsmanager.AWSSecretsManager;
import com.amazonaws.services.secretsmanager.model.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/*@Component
public class SecretsManager {

    private AWSSecretsManager secretsClientManager;

    private String secretName;

    private String region;

    public SecretsManager(AWSSecretsManager secretsClientManager, String secretName,
                          @Qualifier("region") String region) {

        this.secretsClientManager = secretsClientManager;
        this.secretName = secretName;
        this.region = region;
    }

    public String getSecret() {

        // In this sample we only handle the specific exceptions for the 'GetSecretValue' API.
        // See https://docs.aws.amazon.com/secretsmanager/latest/apireference/API_GetSecretValue.html
        // We rethrow the exception by default.

        String secret, decodedBinarySecret;
        GetSecretValueRequest getSecretValueRequest = new GetSecretValueRequest()
                .withSecretId(secretName);
        GetSecretValueResult getSecretValueResult = null;

        try {
            getSecretValueResult = secretsClientManager.getSecretValue(getSecretValueRequest);
        } catch (DecryptionFailureException e) {
            // Secrets Manager can't decrypt the protected secret text using the provided KMS key.
            // Deal with the exception here, and/or rethrow at your discretion.
            throw e;
        } catch (InternalServiceErrorException e) {
            // An error occurred on the server side.
            // Deal with the exception here, and/or rethrow at your discretion.
            throw e;
        } catch (InvalidParameterException e) {
            // You provided an invalid value for a parameter.
            // Deal with the exception here, and/or rethrow at your discretion.
            throw e;
        } catch (InvalidRequestException e) {
            // You provided a parameter value that is not valid for the current state of the resource.
            // Deal with the exception here, and/or rethrow at your discretion.
            throw e;
        } catch (ResourceNotFoundException e) {
            // We can't find the resource that you asked for.
            // Deal with the exception here, and/or rethrow at your discretion.
            throw e;
        }

        // Decrypts secret using the associated KMS key.
        // Depending on whether the secret is a string or binary, one of these fields will be populated.
        if (getSecretValueResult.getSecretString() != null) {
            secret = getSecretValueResult.getSecretString();
            System.out.println("secret = " + secret);
            return secret;
        }
        else {
            decodedBinarySecret =
                    new String(Base64.getDecoder().decode(getSecretValueResult.getSecretBinary()).array());
            System.out.println("decodedBinarySecret = " + decodedBinarySecret);
            return decodedBinarySecret;
        }

    }

    public Map<String, String> secretsToMap(String secret) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();
        final Map<String, String> secrets = objectMapper.readValue(secret, new TypeReference<HashMap<String, String>>() {
        });

        return secrets;
    }

}*/
