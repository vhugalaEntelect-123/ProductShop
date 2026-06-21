package za.co.entelect.java_devcamp_product_shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.co.entelect.java_devcamp_product_shop.entity.UserProfile;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {

    UserProfile findByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByIdNumber(String idNumber);
}
