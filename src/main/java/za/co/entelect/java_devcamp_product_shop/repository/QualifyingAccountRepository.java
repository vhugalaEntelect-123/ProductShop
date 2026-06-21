package za.co.entelect.java_devcamp_product_shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.entelect.java_devcamp_product_shop.entity.QualifyingAccount;

import java.util.List;

public interface QualifyingAccountRepository extends JpaRepository<QualifyingAccount, Long> {

    List<QualifyingAccount> findByProduct_ProductId(Long productId);

    boolean existsByProduct_ProductIdAndAccountId(Long productId, Long accountId);
}