
package io.github.oleiva.analytics.service.zipImport;

import io.github.oleiva.analytics.model.Products;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public interface DataProvider {
    Map<String, List<Products>> getDataFromCSV(Consumer<Products> consumer);
}
