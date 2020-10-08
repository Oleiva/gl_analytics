package io.github.oleiva.analytics.service.analytics;


import io.github.oleiva.analytics.Variables;
import io.github.oleiva.analytics.model.ProductsPriceStatistics;
import io.github.oleiva.analytics.repository.ProductsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class AnalyticsServiceImpl implements AnalyticsService {
    private final ProductsRepository productsRepository;

    @CacheEvict(Variables.TABLE_CACHE_NAME)
    @Transactional
    public List<ProductsPriceStatistics> calculateProductsPriceStatistics() {
        return productsRepository.calculateProductPriceStatistics();
    }
}
