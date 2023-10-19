package com.junioroffers.domain.offer;

import lombok.Builder;

@Builder
record Offer(
        String id,
        String url,
        String position,
        String companyName,
        String salary) {
    Offer createOfferWithId(String id) {
        return new Offer(id,
                url(),
                position(),
                companyName(),
                salary());
    }
}
