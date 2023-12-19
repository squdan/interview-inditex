package es.dtr.job.interview.inditex.ms.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductEntity {

	private Long id;
	private String name;
	private List<PriceEntity> prices;
	private Instant createdOn;
	private Instant lastUpdatedOn;
}
