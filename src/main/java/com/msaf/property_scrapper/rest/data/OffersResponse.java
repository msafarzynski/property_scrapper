package com.msaf.property_scrapper.rest.data;

import com.msaf.property_scrapper.data.OfferDto;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder(setterPrefix = "with")
@NoArgsConstructor
@AllArgsConstructor
public class OffersResponse {
    List<OfferDto> offers;
    String errorMessage;
}
