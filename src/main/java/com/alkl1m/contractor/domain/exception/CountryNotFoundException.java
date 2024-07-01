package com.alkl1m.contractor.domain.exception;

/**
 * Исключение, для случая, когда страна не может быть найдена.
 *
 * @author alkl1m
 */
public class CountryNotFoundException extends RuntimeException {

    public CountryNotFoundException(final String message) {
        super(message);
    }

}
