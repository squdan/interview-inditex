package es.dtr.job.interview.inditex.ms.domain.entity;

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
public class ProductEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(nullable = false)
	private String name;

	@OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
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
