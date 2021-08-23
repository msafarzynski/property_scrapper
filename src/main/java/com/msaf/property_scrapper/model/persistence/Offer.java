package com.msaf.property_scrapper.model.persistence;




import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Offer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
