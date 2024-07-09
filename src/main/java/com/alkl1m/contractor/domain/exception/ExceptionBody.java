package com.alkl1m.contractor.domain.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 * Класс, который содержит информацию об исключении или ошибке.
 *
 * @author alkl1m
 */
@Getter
@Setter
@AllArgsConstructor
public class ExceptionBody {

    @Schema(description = "Список сообщений (текст исключения)")
    private String message;
    @Schema(description = "Мапа ошибок")
    private Map<String, String> errors;

    public ExceptionBody(
            final String message
    ) {
        this.message = message;
    }

}
