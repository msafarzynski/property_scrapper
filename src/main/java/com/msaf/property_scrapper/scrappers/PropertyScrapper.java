package com.msaf.property_scrapper.scrappers;

import com.msaf.property_scrapper.data.OfferDto;

import java.io.IOException;
import java.util.List;

public interface PropertyScrapper {

    List<OfferDto> getOffers(String city) throws IOException;
}
