package com.msaf.property_scrapper.scrappers.otodom;

import com.msaf.property_scrapper.PropertyScrapperApplication;
import com.msaf.property_scrapper.data.OfferDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {PropertyScrapperApplication.class})
public class OtoDomScrapperTest {

    @Autowired
    protected OtoDomScrapper otoDomScrapper;

    @BeforeEach
    void setUp() {
    }

    @Test
    void getOffers() throws IOException {
        List<OfferDto> offers = otoDomScrapper.getOffers("Warszawa");

        assertThat(offers).isNotNull();
    }
}