package com.msaf.property_scrapper.rest;

import com.msaf.property_scrapper.scrappers.otodom.OtoDomScrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class ScrappersRestController {

    @Autowired
    OtoDomScrapper otoDomScrapper;

    @GetMapping("/scrape")
    public String hello(@RequestParam(value = "city", defaultValue = "Warszawa") String city) {

        try {
            return otoDomScrapper.getOffers(city).toString();
        } catch (IOException e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }
}
