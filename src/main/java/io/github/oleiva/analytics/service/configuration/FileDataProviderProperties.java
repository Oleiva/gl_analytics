

package io.github.oleiva.analytics.service.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties("data-import.file")
public class FileDataProviderProperties {
    private String entryName;
}
