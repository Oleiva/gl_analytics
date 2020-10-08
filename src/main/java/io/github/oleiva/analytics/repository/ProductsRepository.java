

package io.github.oleiva.analytics.repository;

import io.github.oleiva.analytics.model.Products;
import io.github.oleiva.analytics.model.ProductsPriceStatistics;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductsRepository extends CrudRepository<Products, Long> {

    List<Products> findAll();

    @Query("SELECT"
            + " AVG(PRICE) AS PRICE,"
            + " COALESCE((SUM(RATING_FIVE_COUNT) * 100.0 / NULLIF(SUM(RATING_COUNT), 0)), 0 )AS FIVE_PERCENTAGE,"
            + " ORIGIN_COUNTRY"
            + " FROM PRODUCTS"
            + " WHERE PRODUCT_ID IS NOT NULL AND RATING_COUNT  IS NOT NULL "
            + " GROUP BY ORIGIN_COUNTRY"
            + " ORDER BY ORIGIN_COUNTRY;")
    List<ProductsPriceStatistics> calculateProductPriceStatistics();
}
