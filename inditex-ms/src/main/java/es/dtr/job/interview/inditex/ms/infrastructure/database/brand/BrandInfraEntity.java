package es.dtr.job.interview.inditex.ms.infrastructure.database.brand;

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
 * Entity to represent configured brands.
 */
@Data
@Entity
@Builder
@Table(name = "brands")
@NoArgsConstructor
@AllArgsConstructor
public class BrandInfraEntity implements MergeableEntity<BrandInfraEntity> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BRAND_SEQUENCE_ID")
    private Long id;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "brand", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
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
    public BrandInfraEntity merge(BrandInfraEntity changes) {
        if (StringUtils.isNotBlank(changes.getName())) {
            this.name = changes.getName();
        }

        return this;
    }
}
