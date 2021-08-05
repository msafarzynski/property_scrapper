package com.msaf.property_scrapper.scrappers.otodom;

import com.msaf.property_scrapper.data.OfferDto;
import com.msaf.property_scrapper.scrappers.PropertyScrapper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class OtoDomScrapper implements PropertyScrapper {

    private final static String offerItemDetails = "offer-item-details";
    private final static String h3Tag = "h3";
    private final static String aTag = "a";
    private final static String offerItemTitle = "offer-item-title";
    private final static String href = "href";
    private final static String offersClassName = "col-md-content section-listing__row-content";

    @Override
    public List<OfferDto> getOffers(String city) throws IOException {

        Document doc = Jsoup.connect(String.format("https://www.otodom.pl/sprzedaz/mieszkanie/%1$s", city)).get();

        Elements offerElmnts = doc.getElementsByClass(offersClassName);

        List<OfferDto> offerDtos = new ArrayList<>();

        for (Element offerElmnt : offerElmnts){
            OfferDto offerDto = new OfferDto();

            offerDto.setCity(city);

            String offerTitle = offerElmnt.getElementsByClass(offerItemDetails).get(0).getElementsByTag(h3Tag).get(0).getElementsByTag(aTag).get(0).getElementsByClass(offerItemTitle).text();
            offerDto.setTitle(offerTitle);

            String offerUrl = offerElmnt.getElementsByClass(offerItemDetails).get(0).getElementsByTag("h3").get(0).getElementsByTag("a").get(0).attr(href);
             offerDto.setUrl(offerUrl);

            String offerPrice = offerElmnt.getElementsByClass(offerItemDetails).get(0).getElementsByTag("h3").get(0).getElementsByTag("a").get(0).attr(href);
            offerDto.setUrl(offerPrice);

            offerDtos.add(offerDto);
        }

        return offerDtos;
    }
}
