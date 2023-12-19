package es.dtr.job.interview.inditex.ms.domain.service.price;

import es.dtr.job.interview.commons.hexagonal.domain.service.crud.CrudService;
import es.dtr.job.interview.commons.hexagonal.domain.service.querydsl.QueryDslFilter;
import es.dtr.job.interview.commons.hexagonal.domain.service.querydsl.QueryDslOperators;
import es.dtr.job.interview.inditex.ms.domain.entity.PriceEntity;
import es.dtr.job.interview.inditex.ms.domain.repository.PriceDomainRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.time.Instant;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class PriceService implements CrudService<PriceEntity, Long> {

    // Dependencies
    private final PriceDomainRepository repository;

    public PriceEntity getWith(final Long productId, final Long brandId, final Instant date) {
        PriceEntity result;

        // Generate filters with received information
        final List<QueryDslFilter> filters = List.of(
                QueryDslFilter.builder().key("product.id").operator(QueryDslOperators.EQUALS).value(productId).build(),
                QueryDslFilter.builder().key("brand.id").operator(QueryDslOperators.EQUALS).value(brandId).build(),
                QueryDslFilter.builder().key("startDate").operator(QueryDslOperators.LOWER_THAN_OR_EQUALS).value(date).build(),
                QueryDslFilter.builder().key("endDate").operator(QueryDslOperators.GREATER_THAN_OR_EQUALS).value(date).build()
        );

        // Will use sorting from pagination to get price with highest priority value
        final Pageable pageable = PageRequest.of(0, 1, Sort.by(Sort.Direction.DESC, "priority"));

        // Search using filters
        final List<PriceEntity> mayResults = findBy(filters, pageable);

        if (CollectionUtils.isNotEmpty(mayResults)) {
            result = mayResults.get(0);
        } else {
            throw NOT_FOUND;
        }

        return result;
    }
}
