package com.junioroffers.domain.offer;

import com.junioroffers.domain.offer.dto.JobOfferResponse;
import com.junioroffers.domain.offer.dto.OfferRequestDto;
import com.junioroffers.domain.offer.dto.OfferResponseDto;

public class OfferMapper {

    static Offer mapOfferRequestDtoToOffer(OfferRequestDto offerRequestDto) {
        return Offer.builder()
                .position(offerRequestDto.position())
                .salary(offerRequestDto.salary())
                .companyName(offerRequestDto.companyName())
                .url(offerRequestDto.url())
                .build();
    }

    static OfferResponseDto mapOfferToOfferResponseDto(Offer offer) {
        return OfferResponseDto.builder()
                .id(offer.id())
                .position(offer.position())
                .companyName(offer.companyName())
                .salary(offer.salary())
                .url(offer.url())
                .build();
    }

    static Offer mapJobOfferResponseToOffer(JobOfferResponse jobOfferResponse) {
        return Offer.builder()
                .url(jobOfferResponse.offerUrl())
                .companyName(jobOfferResponse.company())
                .salary(jobOfferResponse.salary())
                .position(jobOfferResponse.title())
                .build();
    }
}
