package com.github.pateluday07.graphqlspringbootdemo.exception;

import graphql.ErrorClassification;
import graphql.GraphQLError;
import graphql.language.SourceLocation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.github.pateluday07.graphqlspringbootdemo.constant.Constants.EXTENSION_ERROR_CODE_KEY;
import static com.github.pateluday07.graphqlspringbootdemo.constant.Constants.EXTENSION_ERROR_MESSAGE_KEY;

public class CustomException extends RuntimeException implements GraphQLError {

    private Integer errorCode;
    private String errorMessage;

    public CustomException(Integer errorCode, String errorMessage) {
        super(errorMessage);
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
    }

    @Override
    public List<SourceLocation> getLocations() {
        return null;
    }

    @Override
    public Map<String, Object> getExtensions() {
        Map<String, Object> extensions = new HashMap<>();
        extensions.put(EXTENSION_ERROR_CODE_KEY, errorCode);
        extensions.put(EXTENSION_ERROR_MESSAGE_KEY, errorMessage);
        return extensions;
    }

    @Override
    public ErrorClassification getErrorType() {
        return null;
    }
}
