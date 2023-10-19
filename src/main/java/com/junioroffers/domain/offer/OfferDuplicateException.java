package com.junioroffers.domain.offer;

import lombok.Getter;

import java.util.List;

@Getter
public class OfferDuplicateException extends RuntimeException {

    private final List<String> offersUrls;

    public OfferDuplicateException(String offerUrl) {
        super(String.format("Offer with url [%s] already exists", offerUrl));
        this.offersUrls = List.of(offerUrl);
    }

}
