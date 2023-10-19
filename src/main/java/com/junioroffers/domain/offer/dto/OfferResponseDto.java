package com.junioroffers.domain.offer.dto;

import lombok.Builder;

@Builder
public record OfferResponseDto(
        String id,
        String url,
        String position,
        String companyName,
        String salary
) {
}
