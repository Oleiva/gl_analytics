
package io.github.oleiva.analytics.service.csvImport;

import static java.util.stream.Collectors.toMap;

import io.github.oleiva.analytics.Variables;
import io.github.oleiva.analytics.model.Products;
import io.github.oleiva.analytics.repository.ProductsRepository;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.LongAdder;
import java.util.function.Function;

import io.github.oleiva.analytics.service.output.OutputService;
import io.github.oleiva.analytics.service.zipImport.DataProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Component
public class ImportDatabaseServiceImpl implements ImportService {
    private final DataProvider dataProvider;
    private final ProductsRepository productsRepository;
    private final OutputService outputService;

    @CacheEvict(Variables.TABLE_CACHE_NAME)
    @Transactional
    public void csvImport() {
        log.info("Starting reimport...");
        long start = System.currentTimeMillis();

        productsRepository.deleteAll();
        log.info("Deleted all objects");

        var products = productsRepository.findAll()
                .stream()
                .collect(toMap(Products::getProduct_id, Function.identity()));

        var count = new LongAdder();
        Map<String, List<Products>> weakData = dataProvider.getDataFromCSV((Products newProducts) -> {
            Products product = products.computeIfAbsent(newProducts.getProduct_id(), name -> productsRepository.save(newProducts));
            productsRepository.save(product);
            count.increment();
        });

        outputService.printWrongFieldStatistics(weakData);
        long executionTime = System.currentTimeMillis() - start;
        log.info("Successfully imported {} objects in {} ms", count.sum(), executionTime);
    }
}
