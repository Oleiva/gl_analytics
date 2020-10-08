

package io.github.oleiva.analytics.service.configuration;

import javax.annotation.PostConstruct;

import io.github.oleiva.analytics.service.csvImport.ImportDatabaseServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class ImportInitializer {
    private final ImportInitializerProperties properties;
    private final ImportDatabaseServiceImpl importService;

    @PostConstruct
    public void init() {
        if (properties.isEnabled()) {
            importService.csvImport();
        } else {
            log.info("Skipping reimport");
        }
    }
}
