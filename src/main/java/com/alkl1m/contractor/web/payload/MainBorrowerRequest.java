package com.alkl1m.contractor.web.payload;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

/**
 * DTO реквеста для изменения статуса основного заемщика.
 *
 * @param contractorId уникальный идентификатор контрагента, у которого меняем статус.
 * @param main статус основного заемщика.
 */
public record MainBorrowerRequest(
        @NotNull(message = "{contractor.borrower.errors.contractor_id_is_null}")
        @Schema(description = "Уникальный идентификатор контрагента", nullable = false)
        String contractorId,
        @NotNull(message = "{contractor.borrower.errors.main_is_null}")
        @Schema(description = "Статус основного заемщика", nullable = false)
        boolean main
) {
}
