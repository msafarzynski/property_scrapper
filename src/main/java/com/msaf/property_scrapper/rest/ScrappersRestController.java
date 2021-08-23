package com.msaf.property_scrapper.rest;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import com.msaf.property_scrapper.data.OfferDto;
import com.msaf.property_scrapper.model.persistence.Offer;
import com.msaf.property_scrapper.repository.OfferRepository;
import com.msaf.property_scrapper.rest.data.OffersResponse;
import com.msaf.property_scrapper.scrappers.otodom.OtoDomScrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/scrapper")
public class ScrappersRestController {

    final OtoDomScrapper otoDomScrapper;
    final OfferRepository offerRepository;

    @Autowired
    public ScrappersRestController(OtoDomScrapper otoDomScrapper, OfferRepository offerRepository) {
        this.otoDomScrapper = otoDomScrapper;
        this.offerRepository = offerRepository;
    }


    @GetMapping("/scrape")
    public OffersResponse scrape(@RequestParam(value = "city", defaultValue = "Warszawa") String city) {

        try {
            List<OfferDto> offers = otoDomScrapper.getOffers(city);

            Mapper mapper = DozerBeanMapperBuilder.buildDefault();

            List<Offer> offerList = new ArrayList<>();
            offers.forEach(offerDto -> {
                Offer offer = mapper.map(offerDto, Offer.class);
                offerList.add(offer);
            });

            offerRepository.saveAll(offerList);

            return OffersResponse.builder().withOffers(offers).build();
        } catch (IOException e) {
            e.printStackTrace();
            return OffersResponse.builder().withErrorMessage(e.getMessage()).build();
        }
    }
}
