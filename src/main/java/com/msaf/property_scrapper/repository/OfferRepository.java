package com.msaf.property_scrapper.repository;

import com.msaf.property_scrapper.model.persistence.Offer;
import org.springframework.data.repository.CrudRepository;

public interface OfferRepository extends CrudRepository<Offer, Long> {
}
