package es.dtr.job.interview.inditex.ms.domain.entity;

import es.dtr.job.interview.commons.data.Currencies;
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

/**
 * Entity to represent configured prices for brand-product relationship.
 */
@Data
@Entity
@Builder
@Table(name = "prices")
@NoArgsConstructor
@AllArgsConstructor
public class PriceEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="PRICE_SEQUENCE_ID")
	private Long priceList;

	@ManyToOne
	@JoinColumn(name = "brand_id", nullable = false)
	private BrandEntity brand;

	@ManyToOne
	@JoinColumn(name = "product_id", nullable = false)
	private ProductEntity product;

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
