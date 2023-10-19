package com.junioroffers.domain.offer;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryOfferRepository implements OfferRepository {

    Map<String, Offer> database = new ConcurrentHashMap<>();

    @Override
    public boolean existByUrl(String url) {
        return database.values()
                .stream()
                .anyMatch(offer -> offer.url().equals(url));
    }

    @Override
    public List<Offer> findAllOffers() {
        return database.values()
                .stream()
                .toList();
    }

    @Override
    public Optional<Offer> findOfferById(String id) {
        return Optional.ofNullable(database.get(id));
    }

    @Override
    public Offer save(Offer entity) {
        if (existByUrl(entity.url())) {
            throw new OfferDuplicateException(entity.url());
        }
        UUID uuid = UUID.randomUUID();
        Offer offer = entity.createOfferWithId(uuid.toString());
        database.put(uuid.toString(), offer);
        return offer;
    }

    @Override
    public List<Offer> saveAll(List<Offer> offers) {
        return offers
                .stream()
                .map(this::save)
                .toList();
    }
}
