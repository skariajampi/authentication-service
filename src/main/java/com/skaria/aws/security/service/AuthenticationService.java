package com.skaria.aws.security.service;

import com.skaria.aws.security.model.UserLoginRequestPayload;
import com.skaria.aws.security.model.UserLoginResponsePayload;

public interface AuthenticationService {


    UserLoginResponsePayload login(UserLoginRequestPayload userLoginRequestPayload)
            throws Exception;


}