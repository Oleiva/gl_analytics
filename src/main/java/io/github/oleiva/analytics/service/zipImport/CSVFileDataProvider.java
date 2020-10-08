
package io.github.oleiva.analytics.service.zipImport;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import io.github.oleiva.analytics.service.configuration.FileDataProviderProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


//import io.github.oleiva.analytics.model.City;
//import io.github.oleiva.analytics.model.Coordinates;
import io.github.oleiva.analytics.model.Products;


import java.io.File;
import java.io.FileInputStream;
import java.util.*;
import java.util.function.Consumer;

@Slf4j
@RequiredArgsConstructor
@Component
public class CSVFileDataProvider implements DataProvider {

    private final FileDataProviderProperties properties;
    private static final boolean SKIP_HEADER = true;

    @Override
    public Map<String, List<Products>> getDataFromCSV(Consumer<Products> consumer) {
        log.info("Reading file {}", properties.getEntryName());
        Map<String, List<Products>> weakDataMap = new HashMap<>();

        try (var inputStream = new FileInputStream(new File(properties.getEntryName()))) {

            try (var scanner = new Scanner(inputStream, StandardCharsets.UTF_8)) {
                scanner.useDelimiter("\\n");
                if (SKIP_HEADER) {
                    scanner.next();
                }

                while (scanner.hasNext()) {
                    var line = scanner.next();
                    String[] cells = line.split(",");

                    String originCountry = parseStr(cells, cells.length - 14);
                    Double price = parseDouble(parseStr(cells, 2));
                    int ratingCount = parseInt(parseStr(cells, 8));
                    int ratingFiveCount = parseInt(parseStr(cells, 9));
                    String productId = parseStr(cells, cells.length - 3);

                    var products = new Products(productId, originCountry, price, ratingCount, ratingFiveCount);

                    if (productId == null) {
                        log.warn("CASE NULL : " + productId + " COUNTRY: " + originCountry + " PRICE: " + price);
                    } else {
                        if (weakDataMap.containsKey(productId)) {
                            List<Products> updatedData = new ArrayList<>(weakDataMap.get(productId));
                            updatedData.add(products);
                            weakDataMap.put(productId, updatedData);
                        } else {
                            weakDataMap.put(productId, Collections.singletonList(products));
                        }
                    }
                    consumer.accept(products);
                }
            }
            return weakDataMap;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String parseStr(String[] cells, int index) {
        try {
            return cells[index];
        } catch (ArrayIndexOutOfBoundsException e) {
            log.warn("Wrong double format{}", (Object) cells);
        }
        return "";
    }

    private Double parseDouble(String str) {
        try {
            return Double.parseDouble(str);
        } catch (Exception e) {
            log.warn("Wrong double format{}", str);
        }
        return 0.0;
    }

    private int parseInt(String str) {
        try {
            return Integer.parseInt(str);
        } catch (Exception e) {
            log.warn("Wrong Int format: {}", str == "" ? str : "empty space");
        }
        return 0;
    }

}
