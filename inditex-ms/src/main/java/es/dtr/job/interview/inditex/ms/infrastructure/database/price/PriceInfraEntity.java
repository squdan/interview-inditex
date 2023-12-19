package es.dtr.job.interview.inditex.ms.infrastructure.database.price;

import es.dtr.job.interview.commons.hexagonal.domain.entity.type.Currencies;
import es.dtr.job.interview.commons.hexagonal.infrastructure.database.MergeableEntity;
import es.dtr.job.interview.inditex.ms.infrastructure.database.brand.BrandInfraEntity;
import es.dtr.job.interview.inditex.ms.infrastructure.database.product.ProductInfraEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SourceType;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

/**
 * Entity to represent configured prices for brand-product relationship.
 */
@Data
@Entity
@Builder
@Table(name = "prices")
@NoArgsConstructor
@AllArgsConstructor
public class PriceInfraEntity implements MergeableEntity<PriceInfraEntity> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PRICE_SEQUENCE_ID")
    private Long priceList;

    @ManyToOne
    @JoinColumn(name = "brand_id", nullable = false)
    private BrandInfraEntity brand;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private ProductInfraEntity product;

    @Column(nullable = false)
    private Instant startDate;

    @Column(nullable = false)
    private Instant endDate;

    private Integer priority;

    private BigDecimal price;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Currencies currency;

    /**
     * Creation date: managed by hibernate
     */
    @CreationTimestamp(source = SourceType.DB)
    private Instant createdOn;

    /**
     * Last update date: managed by hibernate
     */
    @UpdateTimestamp(source = SourceType.DB)
    private Instant lastUpdatedOn;

    @Override
    public PriceInfraEntity merge(PriceInfraEntity changes) {
        if (Objects.nonNull(changes.getBrand())) {
            this.brand.setId(changes.getBrand().getId());
        }

        if (Objects.nonNull(changes.getProduct())) {
            this.product.setId(changes.getProduct().getId());
        }

        if (Objects.nonNull(changes.getStartDate())) {
            this.startDate = changes.getStartDate();
        }

        if (Objects.nonNull(changes.getEndDate())) {
            this.endDate = changes.getEndDate();
        }

        if (Objects.nonNull(changes.getPriority())) {
            this.priority = changes.getPriority();
        }

        if (Objects.nonNull(changes.getPrice())) {
            this.price = changes.getPrice();
        }

        if (Objects.nonNull(changes.getCurrency())) {
            this.currency = changes.getCurrency();
        }

        return this;
    }
}
