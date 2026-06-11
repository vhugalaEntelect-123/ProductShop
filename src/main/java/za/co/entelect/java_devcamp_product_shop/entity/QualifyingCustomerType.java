package za.co.entelect.java_devcamp_product_shop.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "qualifying_customer_types")
public class QualifyingCustomerType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "qualifying_customer_types_id")
    private Long qualifyingCustomerTypesId;

    @Column(name = "customer_types_id", nullable = false)
    private Long customerTypesId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
}
