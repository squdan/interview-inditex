package es.dtr.job.interview.inditex.ms.domain.entity;

import lombok.*;

import java.time.Instant;
import java.util.List;

/**
 * Entity to represent configured brands.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BrandEntity {

    private Long id;
    private String name;
    private List<PriceEntity> prices;
    private Instant createdOn;
    private Instant lastUpdatedOn;
}
