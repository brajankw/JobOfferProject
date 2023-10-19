package com.junioroffers.domain.offer;

import com.junioroffers.domain.offer.dto.JobOfferResponse;
import com.junioroffers.domain.offer.dto.OfferRequestDto;
import com.junioroffers.domain.offer.dto.OfferResponseDto;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class OfferFacadeTest {

    @Test
    void should_find_offer_by_id() {
        //given
        OfferFacade offerFacade = new OfferFacadeTestConfiguration(List.of()).offerFacadeForTests();
        OfferResponseDto offerResponseDto = offerFacade.saveOffer(createOfferRequestWithUrl("1"));
        //when
        OfferResponseDto offerById = offerFacade.findOfferById(offerResponseDto.id());
        //then
        assertThat(offerById).isEqualTo(offerResponseDto);
    }

    @Test
    void should_throw_exception_when_offer_not_found() {
        //given
        String invalidId = "test";
        OfferFacade offerFacade = new OfferFacadeTestConfiguration(List.of()).offerFacadeForTests();
        //when
        //then
        assertThrows(OfferNotFoundException.class,
                () -> offerFacade.findOfferById(invalidId),
                String.format("Offer with id: %s is not found", invalidId));
    }

    @Test
    void should_save_and_find_offers() {
        //given
        int sizeOfAllOffers = 5;
        OfferFacade offerFacade = new OfferFacadeTestConfiguration().offerFacadeForTests();
        for (int i = 1; i <= sizeOfAllOffers ; i++) {
            offerFacade.saveOffer(createOfferRequestWithUrl(String.valueOf(i)));
        }
        //when
        List<OfferResponseDto> result = offerFacade.findAllOffers();
        //then
        assertThat(result).hasSize(sizeOfAllOffers);
    }

    @Test
    void should_save_2_not_existing_offers_when_repository_had_4_offers_with_existing_url() {
        //given
        OfferFacade offerFacade = new OfferFacadeTestConfiguration(
                List.of(
                        new JobOfferResponse("id", "id", "asds", "1"),
                        new JobOfferResponse("assd", "id", "asds", "2"),
                        new JobOfferResponse("asddd", "id", "asds", "3"),
                        new JobOfferResponse("asfd", "id", "asds", "4"),
                        new JobOfferResponse("Junior", "Comarch", "1000", "https://someurl.pl/5"),
                        new JobOfferResponse("Mid", "Finanteq", "2000", "https://someother.pl/6")
                )
        ).offerFacadeForTests();
        for (int i = 1; i <= 4 ; i++) {
            offerFacade.saveOffer(createOfferRequestWithUrl(String.valueOf(i)));
        }
        // when
        List<OfferResponseDto> response = offerFacade.fetchAllOffersAndSaveAllIfNotExists();

        // then
        assertThat(List.of(
                        response.get(0).url(),
                        response.get(1).url()
                )
        ).containsExactlyInAnyOrder("https://someurl.pl/5", "https://someother.pl/6");
    }

    @Test
    void should_fetch_and_save_all_jobs_from_remote_when_repository_is_empty() {
        // given
        OfferFacade offerFacade = new OfferFacadeTestConfiguration().offerFacadeForTests();
        assertThat(offerFacade.findAllOffers()).isEmpty();

        // when
        List<OfferResponseDto> result = offerFacade.fetchAllOffersAndSaveAllIfNotExists();

        // then
        assertThat(result).hasSize(6);
    }

    @Test
    void should_throw_duplicate_exception_when_offer_url_exists() {
        // given
        String url = "test.com";
        OfferFacade offerFacade = new OfferFacadeTestConfiguration(List.of()).offerFacadeForTests();
        OfferResponseDto offerResponseDto = offerFacade.saveOffer(createOfferRequestWithUrl(url));
        // when
        // then
        assertThrows(OfferDuplicateException.class,
                () -> offerFacade.saveOffer(createOfferRequestWithUrl(url)),
                String.format("Offer with url [%s] already exists", url));
    }

    private OfferRequestDto createOfferRequestWithUrl(String url) {
       return new OfferRequestDto(url, "asds", "asdasd", "asdas");
    }

}