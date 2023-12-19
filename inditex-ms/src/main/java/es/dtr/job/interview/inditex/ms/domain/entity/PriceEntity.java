package es.dtr.job.interview.inditex.ms.domain.entity;

import es.dtr.job.interview.commons.hexagonal.domain.entity.type.Currencies;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

/**
 * Entity to represent configured prices for brand-product relationship.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PriceEntity {

	private Long priceList;
	private BrandEntity brand;
	private ProductEntity product;
	private Instant startDate;
	private Instant endDate;
	private Integer priority;
	private BigDecimal price;
	private Currencies currency;
	private Instant createdOn;
	private Instant lastUpdatedOn;

}
