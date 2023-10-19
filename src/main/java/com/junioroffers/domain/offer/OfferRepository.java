package com.junioroffers.domain.offer;

import java.util.List;
import java.util.Optional;

public interface OfferRepository {

    boolean existByUrl(String url);

    List<Offer> findAllOffers();

    Optional<Offer> findOfferById(String id);

    Offer save(Offer offer);

    List<Offer> saveAll(List<Offer> offers);
}
