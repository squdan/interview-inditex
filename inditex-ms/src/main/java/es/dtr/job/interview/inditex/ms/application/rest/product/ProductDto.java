package es.dtr.job.interview.inditex.ms.application.rest.product;

import es.dtr.job.interview.commons.hexagonal.application.rest.crud.CrudElementDto;
import es.dtr.job.interview.inditex.ms.application.rest.price.PriceDto;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.time.Instant;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ProductDto extends CrudElementDto<ProductDto, Long> {

    private Long id;
    private String name;
    private List<PriceDto> prices;
    private Instant createdOn;
    private Instant lastUpdatedOn;

}
