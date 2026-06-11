package za.co.entelect.java_devcamp_product_shop.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import za.co.entelect.java_devcamp_product_shop.entity.Product;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    // 1. Search for products containing a specific keyword in their name (Case-Insensitive)
    List<Product> findByNameContainingIgnoreCase(String name);


    //JPQL Query: Fetch a product and eagerly load its fulfillment details to avoid N+1 query problems
    @Query("SELECT p FROM Product p LEFT JOIN FETCH p.fulfilmentType WHERE p.productId = :productId")
    Product findProductWithFulfilment(@Param("productId") Long productId);

    @Query("SELECT p FROM Product p " +
            "JOIN FETCH p.fulfilmentType f " +
            "WHERE LOWER(f.name) = LOWER(:fulfilmentName)")
    List<Product> findProductsByFulfilmentTypeName(@Param("fulfilmentName") String fulfilmentName);
}
