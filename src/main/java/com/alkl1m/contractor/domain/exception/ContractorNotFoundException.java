package com.alkl1m.contractor.domain.exception;

/**
 * Исключение, для случая, когда контрагент не может быть найден.
 *
 * @author alkl1m
 */
public class ContractorNotFoundException extends RuntimeException {

    public ContractorNotFoundException(final String message) {
        super(message);
    }

}
