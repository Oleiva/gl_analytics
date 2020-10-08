package io.github.oleiva.analytics.service.output;

import io.github.oleiva.analytics.model.Products;
import io.github.oleiva.analytics.model.ProductsPriceStatistics;

import java.util.List;
import java.util.Map;

public interface OutputService {
    void printStatistics(List<ProductsPriceStatistics> priceStatistics);
    void printWrongFieldStatistics( Map<String, List<Products>> data);
}
