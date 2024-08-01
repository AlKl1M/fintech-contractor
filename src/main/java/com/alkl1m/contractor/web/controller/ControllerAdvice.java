package com.alkl1m.contractor.web.controller;

import com.alkl1m.contractor.domain.exception.ContractorNotFoundException;
import com.alkl1m.contractor.domain.exception.CountryNotFoundException;
import com.alkl1m.contractor.domain.exception.ExceptionBody;
import com.alkl1m.contractor.domain.exception.IndustryNotFoundException;
import com.alkl1m.contractor.domain.exception.OrgFormNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ControllerAdvice для обработки исключений.
 *
 * @author alkl1m
 */
@RestControllerAdvice
public class ControllerAdvice {

    /**
     * Метод для обработки IllegalStateException.
     *
     * @param e исключение IllegalStateException.
     * @return объект ExceptionBody с сообщением об ошибке.
     */
    @ExceptionHandler(IllegalStateException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionBody handleIllegalState(
            final IllegalStateException e
    ) {
        return new ExceptionBody(e.getMessage());
    }

    /**
     * Метод для обработки AccessDeniedException.
     *
     * @param e исключение AccessDeniedException.
     * @return объект ExceptionBody с сообщением об ошибке и деталями валидации.
     */
    @ExceptionHandler({
            AccessDeniedException.class,
            org.springframework.security.access.AccessDeniedException.class
    })
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ExceptionBody handleAccessDenied() {
        return new ExceptionBody("Access denied.");
    }

    /**
     * Метод для обработки MethodArgumentNotValidException.
     *
     * @param e исключение MethodArgumentNotValidException.
     * @return объект ExceptionBody с сообщением об ошибке и деталями валидации.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionBody handleMethodArgumentNotValid(
            final MethodArgumentNotValidException e
    ) {
        ExceptionBody exceptionBody = new ExceptionBody("Validation failed.");
        List<FieldError> errors = e.getBindingResult().getFieldErrors();
        exceptionBody.setErrors(errors.stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        FieldError::getDefaultMessage,
                        (existingMessage, newMessage) ->
                                existingMessage + " " + newMessage
                )));
        return exceptionBody;
    }

    /**
     * Метод для обработки ContractorNotFoundException.
     *
     * @param e исключение ContractorNotFoundException.
     * @return объект ExceptionBody с сообщением об ошибке.
     */
    @ExceptionHandler(ContractorNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionBody handleContractorNotFound(
            final ContractorNotFoundException e
    ) {
        return new ExceptionBody(e.getMessage());
    }

    /**
     * Метод для обработки CountryNotFoundException.
     *
     * @param e исключение CountryNotFoundException.
     * @return объект ExceptionBody с сообщением об ошибке.
     */
    @ExceptionHandler(CountryNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionBody handleCountryNotFound(
            final CountryNotFoundException e
    ) {
        return new ExceptionBody(e.getMessage());
    }

    /**
     * Метод для обработки IndustryNotFoundException.
     *
     * @param e исключение IndustryNotFoundException.
     * @return объект ExceptionBody с сообщением об ошибке.
     */
    @ExceptionHandler(IndustryNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionBody handleIndustryNotFound(
            final IndustryNotFoundException e
    ) {
        return new ExceptionBody(e.getMessage());
    }

    /**
     * Метод для обработки OrgFormNotFoundException.
     *
     * @param e исключение OrgFormNotFoundException.
     * @return объект ExceptionBody с сообщением об ошибке.
     */
    @ExceptionHandler(OrgFormNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionBody handleOrgFormNotFound(
            final OrgFormNotFoundException e
    ) {
        return new ExceptionBody(e.getMessage());
    }

}
