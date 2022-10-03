package com.skaria.aws.security.service.cognito;

import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.model.AuthenticationResultType;
import com.amazonaws.services.cognitoidp.model.InitiateAuthRequest;
import com.amazonaws.services.cognitoidp.model.InitiateAuthResult;
import com.amazonaws.services.cognitoidp.model.InvalidParameterException;
import com.skaria.aws.security.model.UserLoginRequestPayload;
import com.skaria.aws.security.model.UserLoginResponsePayload;
import com.skaria.aws.security.service.AuthenticationService;
import com.skaria.aws.security.util.cognito.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthenticationServiceCognitoImpl implements AuthenticationService {


    private AWSCognitoIdentityProvider awsCognitoIdentityProvider;


    private InitiateAuthRequest initiateAuthRequest;


    private String clientId;


    private String clientSecret;

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);


    @Autowired
    public AuthenticationServiceCognitoImpl(AWSCognitoIdentityProvider awsCognitoIdentityProvider,
                                            InitiateAuthRequest initiateAuthRequest,
                                            String clientId,
                                            String clientSecret) {

        this.awsCognitoIdentityProvider = awsCognitoIdentityProvider;
        this.initiateAuthRequest = initiateAuthRequest;
        this.clientId = clientId;
        this.clientSecret = clientSecret;

    }

    public UserLoginResponsePayload login(UserLoginRequestPayload userLoginRequestPayload)
            throws Exception {

        logger.info("Login request received...");


        String clientSecretHashed = Util.calculateSecretHash(clientId,
                clientSecret,
                userLoginRequestPayload.getUserName());

        final Map<String, String> authParams = buildAuthParams(userLoginRequestPayload, clientSecretHashed);

        initiateAuthRequest.withAuthParameters(authParams);

        return authenticateWithCognito();

    }

    private UserLoginResponsePayload authenticateWithCognito() throws Exception {
        try {
            logger.info("Contacting AWS Cognito to authenticate...");

            InitiateAuthResult result = awsCognitoIdentityProvider.initiateAuth(initiateAuthRequest);
            logger.info("Received response from  AWS Cognito ...");
            AuthenticationResultType authenticationResult = null;


            authenticationResult = result.getAuthenticationResult();

            UserLoginResponsePayload userLoginResponsePayload =
                    buildUserLoginResponsePayload(authenticationResult);
            //awsCognitoIdentityProvider.shutdown();

            return userLoginResponsePayload;

        } catch (InvalidParameterException e) {
            logger.error("InvalidParameterException from AWS Cognito ...{}", e.getErrorMessage());
            //awsCognitoIdentityProvider.shutdown();
            throw new Exception(e.getErrorMessage());
        } catch (Exception e) {
            logger.error("Exception from AWS Cognito ...{}", e.getMessage());
            //awsCognitoIdentityProvider.shutdown();
            throw new Exception(e.getMessage());
        }
    }

    private UserLoginResponsePayload buildUserLoginResponsePayload(AuthenticationResultType authenticationResult) {
        UserLoginResponsePayload userLoginResponsePayload = new UserLoginResponsePayload();
        userLoginResponsePayload.setAccessToken(authenticationResult.getAccessToken());
        userLoginResponsePayload.setRefreshToken(authenticationResult.getRefreshToken());
        userLoginResponsePayload.setIdToken(authenticationResult.getIdToken());
        return userLoginResponsePayload;
    }

    private Map<String, String> buildAuthParams(UserLoginRequestPayload userLoginRequestPayload, String clientSecretHashed) {
        final Map<String, String> authParams = new HashMap<>();
        authParams.put("USERNAME", userLoginRequestPayload.getUserName());
        authParams.put("PASSWORD", userLoginRequestPayload.getPassword());
        authParams.put("SECRET_HASH", clientSecretHashed);
        return authParams;
    }

}