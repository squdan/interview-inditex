package es.dtr.job.interview.inditex.ms.infrastructure.database.product;

import es.dtr.job.interview.commons.hexagonal.infrastructure.database.MergeableEntity;
import es.dtr.job.interview.inditex.ms.infrastructure.database.price.PriceInfraEntity;
import io.micrometer.common.util.StringUtils;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SourceType;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.List;

/**
 * Entity to represent configured products.
 */
@Data
@Entity
@Builder
@Table(name = "products")
@NoArgsConstructor
@AllArgsConstructor
public class ProductInfraEntity implements MergeableEntity<ProductInfraEntity> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<PriceInfraEntity> prices;

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
    public ProductInfraEntity merge(ProductInfraEntity changes) {
        if (StringUtils.isNotBlank(changes.getName())) {
            this.name = changes.getName();
        }

        return this;
    }
}
