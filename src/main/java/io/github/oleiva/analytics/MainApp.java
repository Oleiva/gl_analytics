
package io.github.oleiva.analytics;

import io.github.oleiva.analytics.service.analytics.AnalyticsService;
import io.github.oleiva.analytics.service.output.OutputService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class MainApp implements CommandLineRunner {
    private final AnalyticsService analyticsService;
    private final OutputService outputService;

    public MainApp(AnalyticsService analyticsService, OutputService outputService) {
        this.analyticsService = analyticsService;
        this.outputService = outputService;
    }

    public static void main(String[] args) {
        SpringApplication.run(MainApp.class, args);
    }

    @Override
    public void run(String... args) {
        var output = analyticsService.calculateProductsPriceStatistics();
        outputService.printStatistics(output);
    }
}
