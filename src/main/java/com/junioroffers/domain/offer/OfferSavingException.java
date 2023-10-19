package com.junioroffers.domain.offer;

import lombok.Getter;

import java.util.List;

@Getter
public class OfferSavingException extends RuntimeException {

    private final List<String> offersUrls;

    public OfferSavingException(String message, List<Offer> offers) {
        super(String.format("error" + message + offers.toString()));
        this.offersUrls = offers
                .stream()
                .map(Offer::url)
                .toList();
    }
}
