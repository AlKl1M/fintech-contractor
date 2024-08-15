package com.alkl1m.contractor.web.payload;

public record UpdateContractorMessage(
        String id,
        String name,
        String inn
) {
}
