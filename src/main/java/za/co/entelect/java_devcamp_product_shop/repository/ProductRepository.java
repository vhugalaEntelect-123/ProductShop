package za.co.entelect.java_devcamp_product_shop.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import za.co.entelect.java_devcamp_product_shop.entity.Product;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @EntityGraph(attributePaths = {"fulfilmentType"})
    Product findByProductId(Long productId);

    @EntityGraph(attributePaths = {"fulfilmentType"})
    List<Product> findByFulfilmentTypeNameIgnoreCase(String fulfilmentName);
}
