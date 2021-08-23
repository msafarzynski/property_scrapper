package com.msaf.property_scrapper.scrappers.otodom;

import com.msaf.property_scrapper.data.OfferDto;
import com.msaf.property_scrapper.scrappers.PropertyScrapper;
import org.apache.commons.lang3.StringUtils;
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

    private static final String HREF = "href";
    private static final String OFFER_LISTING = "listing-item-link";
    private static final String LISTING_ATTR = "data-cy";
    private static final String LISTING_TITLE = "listing-item-title";
    private static final String TITLE = "title";
    private static final String ARTICLE = "article";
    private static final String MIESZKANIE_NA_SPRZEDAZ = "Mieszkanie na sprzedaż";
    private static final String SPAN = "span";
    private static final String SRC = "src";
    private static final String PICTURE = "picture";
    private static final String SITE_URI = "https://www.otodom.pl";
    private static final String P = "p";

    @Override
    public List<OfferDto> getOffers(String city) throws IOException {

        Document doc = Jsoup.connect(String.format("https://www.otodom.pl/sprzedaz/mieszkanie/%s", city)).get();

        Elements offerElmnts =  doc.getElementsByAttributeValueContaining(LISTING_ATTR, OFFER_LISTING);

        List<OfferDto> offerDtos = new ArrayList<>();

        for (Element offerElmnt : offerElmnts){
            String offerTitle = offerElmnt.getElementsByAttributeValueContaining(LISTING_ATTR, LISTING_TITLE).get(0).attr(TITLE);
            String offerUrl = SITE_URI + offerElmnt.attr(HREF);
            String offerLocation = offerElmnt.getElementsByAttributeValueContaining(TITLE, MIESZKANIE_NA_SPRZEDAZ).get(0).getElementsByTag(SPAN).get(0).childNode(0).toString();
            Element article = offerElmnt.getElementsByTag(ARTICLE).get(0);
            String offerPrice = article.getElementsByTag(P).get(1).childNode(0).toString().replace("&nbsp;", " ");
            Elements articleDetails = article.getElementsByTag(P).get(2).getElementsByTag(SPAN);

            String roomsString = null;
            String size = null;
            String pricePerMeter = null;

            if(articleDetails.size() > 2) {
                roomsString = articleDetails.get(0).childNode(0).toString();
                size = articleDetails.get(1).childNode(0).toString();
                pricePerMeter = articleDetails.get(2).childNode(0).childNode(0).toString().replace("&nbsp;", " ");
            }

            String roomsStringNumeric = roomsString != null ? roomsString.substring(0, roomsString.indexOf(" ")) : null;
            Integer rooms = StringUtils.isNumeric(roomsStringNumeric) ? Integer.parseInt(roomsStringNumeric) : null;
            String pictureUrl = offerElmnt.getElementsByTag(PICTURE).get(0).getElementsByAttribute(SRC).get(0).attr(SRC);

            OfferDto offerDto = OfferDto.builder()
                    .city(city)
                    .title(offerTitle)
                    .url(offerUrl)
                    .price(offerPrice)
                    .pricePerMeter(pricePerMeter)
                    .location(offerLocation)
                    .rooms(rooms)
                    .size(size)
                    .pictureUrl(pictureUrl)
                    .build();

            offerDtos.add(offerDto);
        }

        return offerDtos;
    }
}
