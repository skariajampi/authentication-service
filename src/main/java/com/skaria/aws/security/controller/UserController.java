package com.skaria.aws.security.controller;

import com.skaria.aws.security.model.*;
import com.skaria.aws.security.service.AuthenticationService;
import com.skaria.aws.security.service.AuthorizationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/rest/users")
public class UserController {


    AuthenticationService authenticationService;
    AuthorizationService authorizationService;

    @Autowired
    public UserController(AuthenticationService authenticationService,
                          AuthorizationService authorizationService) {
        this.authenticationService = authenticationService;
        this.authorizationService = authorizationService;
    }

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @PostMapping(path = "/login")
    public ResponseEntity<Object> login(@RequestBody UserLoginRequestPayload userLoginRequestPayload)
            throws Exception {

        logger.info("Login request received...");
        try {
            UserLoginResponsePayload userLoginResponsePayload =
                    authenticationService.login(userLoginRequestPayload);
            return new ResponseEntity<>(userLoginResponsePayload, HttpStatus.OK);
        } catch (Exception exception) {
            logger.error("Login failed...due to ...{}", exception.getMessage());
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping(path = "/authorize")
    public ResponseEntity<Object> authorize(@RequestBody UserAuthorizationRequestPayload
                                                        userAuthorizationRequestPayload)
            throws Exception {

        logger.info("authorization request received...");
        try {
            final Authentication authentication =
                    authorizationService.authorize(userAuthorizationRequestPayload.getAuthToken());
            UserAuthorizationResponsePayload userAuthorizationResponsePayload =
                    getUserAuthorizationResponsePayload(userAuthorizationRequestPayload.getAuthToken());
            return new ResponseEntity<>(userAuthorizationResponsePayload, HttpStatus.OK);
        } catch (Exception exception) {
            logger.error("authorization failed...due to ...{}", exception.getMessage());
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    private UserAuthorizationResponsePayload getUserAuthorizationResponsePayload(
            String authToken) {

        UserAuthorizationResponsePayload userAuthorizationResponsePayload =
                new UserAuthorizationResponsePayload();
        userAuthorizationResponsePayload.setAuthToken(authToken);
        return userAuthorizationResponsePayload;
    }

}