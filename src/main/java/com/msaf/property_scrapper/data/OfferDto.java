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
    private String rooms;
    private String ditrict;
    private String city;
}
