package com.junioroffers.domain.offer;

import com.junioroffers.domain.offer.dto.OfferRequestDto;
import com.junioroffers.domain.offer.dto.OfferResponseDto;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class OfferFacade {

    private final OfferRepository offerRepository;
    private final OfferService offerService;

    public List<OfferResponseDto> findAllOffers() {
        return offerRepository.findAllOffers()
                .stream()
                .map(OfferMapper::mapOfferToOfferResponseDto)
                .toList();
    }

    public OfferResponseDto findOfferById(String id) {
        return offerRepository.findOfferById(id)
                .map(OfferMapper::mapOfferToOfferResponseDto)
                .orElseThrow(() -> new OfferNotFoundException(String.format("Offer with id: %s is not found", id)));
    }

    public OfferResponseDto saveOffer(OfferRequestDto offerRequestDto) {
        final Offer toSave = OfferMapper.mapOfferRequestDtoToOffer(offerRequestDto);
        final Offer savedOffer = offerRepository.save(toSave);
        return OfferMapper.mapOfferToOfferResponseDto(savedOffer);
    }

    public List<OfferResponseDto> fetchAllOffersAndSaveAllIfNotExists() {
        return offerService.fetchAllOffersAndSaveAllIfNotExists()
                .stream()
                .map(OfferMapper::mapOfferToOfferResponseDto)
                .toList();
    }
}
