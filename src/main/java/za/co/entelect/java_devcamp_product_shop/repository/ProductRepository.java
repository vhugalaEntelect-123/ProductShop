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

    @Query("SELECT p FROM Product p LEFT JOIN FETCH p.fulfilmentType WHERE p.productId = :productId")
    Product findProductWithFulfilment(@Param("productId") Long productId);

    @Query("SELECT p FROM Product p " +
            "JOIN FETCH p.fulfilmentType f " +
            "WHERE LOWER(f.name) = LOWER(:fulfilmentName)")
    List<Product> findProductsByFulfilmentTypeName(@Param("fulfilmentName") String fulfilmentName);
}
