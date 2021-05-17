package com.narola.graphqlspringbootdemo.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Constants {

    public static final String AUTHORIZATION_HEADER_KEY = "Authorization";
    public static final String JWT_TOKEN_PREFIX = "Bearer ";
    public static final Integer PASSWORD_LENGTH = 6;

    //error
    public static final Integer UNAUTHORIZED_ERROR_CODE = 401;
    public static final Integer ACCESS_DENIED_ERROR_CODE = 403;
    public static final Integer NOT_FOUND_ERROR_CODE = 404;
    public static final Integer BAD_REQUEST_ERROR_CODE = 400;
    public static final String EXTENSION_ERROR_CODE_KEY = "errorCode";
    public static final String EXTENSION_ERROR_MESSAGE_KEY = "errorMessage";
    public static final String OWNER_NOT_FOUND_ERROR_MSG = "Owner not found";
    public static final String CAR_NOT_FOUND_ERROR_MSG = "Car not found";
    public static final String INVALID_EMAIL_ERROR_MSG = "Email is not valid";
    public static final String EMAIL_ALREADY_EXISTS_ERROR_MSG = "Email already available in the system";
    public static final String CONFIRM_PASSWORD_DOES_NOT_MATCH_ERROR_MSG = "Confirm password doesn't match please re-confirm";
    public static final String INVALID_PASSWORD_LENGTH_ERROR_MSG = "Password length must be 6";
    public static final String USER_DOES_NOT_EXISTS_ERROR_MSG = "User doesn't exists";
    public static final String UNAUTHORIZED = "Unauthorized";
}
