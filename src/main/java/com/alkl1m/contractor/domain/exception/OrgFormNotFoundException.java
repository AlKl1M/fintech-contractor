package com.alkl1m.contractor.domain.exception;

/**
 * Исключение, для случая, когда организационная форма не может быть найдена.
 *
 * @author alkl1m
 */
public class OrgFormNotFoundException extends RuntimeException {

    public OrgFormNotFoundException(final String message) {
        super(message);
    }

}
