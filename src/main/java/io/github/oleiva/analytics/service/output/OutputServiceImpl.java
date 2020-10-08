package io.github.oleiva.analytics.service.output;

import io.github.oleiva.analytics.model.Products;
import io.github.oleiva.analytics.model.ProductsPriceStatistics;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Component
public class OutputServiceImpl implements OutputService {
    private static final String PATTERN_OUTPUT = "|%20s|%20s|%10s|";
    private static final String PATTERN_OUTPUT2 = "|%25s|%8s|";
    private static final String RESET = "\u001B[0m";
    private static final String GREEN = "\033[0;32m";
    private static final String BLUE = "\u001B[34m";
    private List<Products> synchronizationProblems = new ArrayList<>();

    public void printStatistics(List<ProductsPriceStatistics> priceStatistics) {
        log.info(BLUE + "The result of the calculation:  " + RESET + "\n");

        System.out.printf(GREEN + PATTERN_OUTPUT + RESET, "AVG(PRICE)  ", "FIVE_PERCENTAGE ", "COUNTRY  ");
        System.out.println("\n------------------------------------------------------");
        priceStatistics.forEach(pps -> System.out.printf(PATTERN_OUTPUT + "\n", pps.getPrice(), pps.getFive_percentage(), pps.getOrigin_country()));

    }

    @Override
    public void printWrongFieldStatistics(Map<String, List<Products>> data) {
        log.info("Wrong Fields import Statistics: ");
        log.info("Database import size: " + data.size());
        System.out.printf(GREEN + PATTERN_OUTPUT2 + RESET, "PRODUCT_ID  ", "COUNT ");
        System.out.println("\n------------------------------------------");

        int uniqueCount = 0;
        int totalCount = 0;

        for (Map.Entry<String, List<Products>> entry : data.entrySet()) {
            String id = entry.getKey();
            List<Products> list = entry.getValue();
            if (list.size() > 1) {
                if (checkUnique(list)) {
                    System.out.printf(PATTERN_OUTPUT2 + "\n", list.get(0).getProduct_id(), list.size());
                    uniqueCount++;
                    totalCount = totalCount + list.size();
                }
            }
        }
        log.info("Unique string count is {}, Total string count is {} ", uniqueCount, totalCount);
    }

    private boolean checkUnique(List<Products> data) {
        for (int i = 0; i < data.size() - 1; i++) {
            if (!data.get(i).equals(data.get(i + 1))) {
                synchronizationProblems.addAll(data);
                return false;
            }
        }
        return true;
    }
}
