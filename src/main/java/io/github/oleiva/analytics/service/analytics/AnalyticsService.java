package io.github.oleiva.analytics.service.analytics;

import io.github.oleiva.analytics.model.ProductsPriceStatistics;

import java.util.List;

public interface AnalyticsService {
    List<ProductsPriceStatistics> calculateProductsPriceStatistics();
}
