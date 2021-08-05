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

    private final static String HREF = "href";
    private final static String OFFER_LISTING = "listing-item-link";
    private final String LISTING_ATTR = "data-cy";
    private final String LISTING_TITLE = "listing-item-title";
    private final String TITLE = "title";
    private final String ARTICLE = "article";
    private final String MIESZKANIE_NA_SPRZEDAZ = "Mieszkanie na sprzedaż";
    private final String SPAN = "span";
    private final String SRC = "src";
    private final String PICTURE = "picture";
    private final String SITE_URI = "https://www.otodom.pl";
    private final String P = "p";

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
                    .withCity(city)
                    .withTitle(offerTitle)
                    .withUrl(offerUrl)
                    .withPrice(offerPrice)
                    .withPricePerMeter(pricePerMeter)
                    .withLocation(offerLocation)
                    .withRooms(rooms)
                    .withSize(size)
                    .withPictureUrl(pictureUrl)
                    .build();

            offerDtos.add(offerDto);
        }

        return offerDtos;
    }
}
