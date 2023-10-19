package com.junioroffers.domain.offer;

import com.junioroffers.domain.offer.dto.JobOfferResponse;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class InMemoryFetcherTestImpl implements OfferFetchable {

    List<JobOfferResponse> jobOffers;

    @Override
    public List<JobOfferResponse> fetchOffers() {
        return jobOffers;
    }
}
