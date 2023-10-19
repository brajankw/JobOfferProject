package com.junioroffers.domain.offer.dto;

import lombok.Builder;

@Builder
public record OfferRequestDto(
        String url,
        String position,
        String companyName,
        String salary
) {
}
