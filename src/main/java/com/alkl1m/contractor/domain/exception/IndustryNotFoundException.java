package com.alkl1m.contractor.domain.exception;

/**
 * Исключение, для случая, когда индустриальный код не может быть найден.
 *
 * @author alkl1m
 */
public class IndustryNotFoundException extends RuntimeException {

    public IndustryNotFoundException(final String message) {
        super(message);
    }

}
