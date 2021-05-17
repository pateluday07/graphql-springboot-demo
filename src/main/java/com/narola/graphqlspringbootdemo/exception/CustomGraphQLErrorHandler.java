package com.narola.graphqlspringbootdemo.exception;

import graphql.ExceptionWhileDataFetching;
import graphql.GraphQLError;
import graphql.kickstart.execution.error.GraphQLErrorHandler;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static com.narola.graphqlspringbootdemo.constant.Constants.ACCESS_DENIED_ERROR_CODE;
import static com.narola.graphqlspringbootdemo.constant.Constants.UNAUTHORIZED_ERROR_CODE;

@Component
public class CustomGraphQLErrorHandler implements GraphQLErrorHandler {

    @Override
    public List<GraphQLError> processErrors(List<GraphQLError> list) {
        return list.stream().map(this::getNested).collect(Collectors.toList());
    }

    private GraphQLError getNested(GraphQLError error) {
        if (error instanceof ExceptionWhileDataFetching) {
            ExceptionWhileDataFetching exceptionError = (ExceptionWhileDataFetching) error;
            if (exceptionError.getException() instanceof GraphQLError) {
                return (GraphQLError) exceptionError.getException();
            } else if (exceptionError.getException() instanceof AuthenticationException) {
                return new CustomException(UNAUTHORIZED_ERROR_CODE, exceptionError.getException().getMessage());
            } else if (exceptionError.getException() instanceof AccessDeniedException) {
                return new CustomException(ACCESS_DENIED_ERROR_CODE, exceptionError.getException().getMessage());
            }
        }
        return error;
    }

}
