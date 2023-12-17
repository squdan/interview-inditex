package es.dtr.job.interview.inditex.ms.domain.entity;

import jakarta.persistence.*;
import lombok.*;
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
public class BrandEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="BRAND_SEQUENCE_ID")
    private Long id;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "brand", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<PriceEntity> prices;

    /**
     * Creation date: managed by hibernate
     * NOTE: in pure hexagonal architecture, this management should be placed into the adapter.
     */
    @CreationTimestamp(source = SourceType.DB)
    private Instant createdOn;

    /**
     * Last update date: managed by hibernate
     * NOTE: in pure hexagonal architecture, this management should be placed into the adapter.
     */
    @UpdateTimestamp(source = SourceType.DB)
    private Instant lastUpdatedOn;
}
