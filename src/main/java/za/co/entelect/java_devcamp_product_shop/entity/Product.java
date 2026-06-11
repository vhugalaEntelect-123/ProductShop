package za.co.entelect.java_devcamp_product_shop.entity;


import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long productId;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "image_url")
    private String imageUrl;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<QualifyingCustomerType> qualifyingCustomerTypes;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<QualifyingAccount> qualifyingAccounts;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fulfilment_type_id")
    private FulfilmentType fulfilmentType;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems;
    //
}
