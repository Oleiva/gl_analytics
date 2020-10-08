package io.github.oleiva.analytics.model;

import lombok.Value;

@Value
public class ProductsPriceStatistics {
    double price;
    double five_percentage;
    String origin_country;
}

