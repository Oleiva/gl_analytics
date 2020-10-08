package io.github.oleiva.analytics.model;


import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;

import java.util.Objects;

@Data
@NoArgsConstructor
public class Products {

    @Id
    @Column("id")
    private Integer id;

    @Column("product_id")
    private String product_id;

    @Column("origin_country")
    private String origin_country; //- country of origin for the products

    @Column("price")
    private double price; //- price of the products

    @Column("rating_count")
    private int rating_count; //- how many times the product has been rated by user/consumer

    @Column("rating_five_count")
    private int rating_five_count; //- how many times the product has been rated by user/consumer with five stars


    public Products(String product_id, String origin_country, Double price, int rating_count, int rating_five_count) {
        this.product_id = product_id;
        this.origin_country = origin_country;
        this.price = price;
        this.rating_count = rating_count;
        this.rating_five_count = rating_five_count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Products products = (Products) o;
        return Double.compare(products.price, price) == 0 &&
                rating_count == products.rating_count &&
                rating_five_count == products.rating_five_count &&
                Objects.equals(product_id, products.product_id) &&
                Objects.equals(origin_country, products.origin_country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(product_id, origin_country, price, rating_count, rating_five_count);
    }
}
