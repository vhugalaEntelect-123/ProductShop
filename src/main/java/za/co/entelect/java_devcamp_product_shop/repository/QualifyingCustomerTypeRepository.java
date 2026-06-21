package za.co.entelect.java_devcamp_product_shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.entelect.java_devcamp_product_shop.entity.QualifyingCustomerType;

import java.util.List;

public interface QualifyingCustomerTypeRepository extends JpaRepository<QualifyingCustomerType, Long> {

    List<QualifyingCustomerType> findByProduct_ProductId(Long productId);

    boolean existsByProduct_ProductIdAndCustomerTypesId(Long productId, Long customerTypesId);
}