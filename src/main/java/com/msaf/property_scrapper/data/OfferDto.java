package com.msaf.property_scrapper.data;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OfferDto {
    private String title;
    private String url;
    private String price;
    private String size;
    private Integer rooms;
    private String district;
    private String city;
    private String location;
    private String pictureUrl;
    private String pricePerMeter;
}
