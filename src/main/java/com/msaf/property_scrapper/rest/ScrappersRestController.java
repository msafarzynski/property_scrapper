package com.msaf.property_scrapper.rest;

import com.msaf.property_scrapper.data.OfferDto;
import com.msaf.property_scrapper.rest.data.OffersResponse;
import com.msaf.property_scrapper.scrappers.otodom.OtoDomScrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/scrapper")
public class ScrappersRestController {

    final OtoDomScrapper otoDomScrapper;

    @Autowired
    public ScrappersRestController(OtoDomScrapper otoDomScrapper) {
        this.otoDomScrapper = otoDomScrapper;
    }

    @GetMapping("/scrape")
    public OffersResponse scrape(@RequestParam(value = "city", defaultValue = "Warszawa") String city) {

        try {
            List<OfferDto> offers = otoDomScrapper.getOffers(city);
            return OffersResponse.builder().withOffers(offers).build();
        } catch (IOException e) {
            e.printStackTrace();
            return OffersResponse.builder().withErrorMessage(e.getMessage()).build();
        }
    }
}
