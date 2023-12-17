package es.dtr.job.interview.inditex.ms.domain.service.price;

import es.dtr.job.interview.commons.api.querydsl.QueryDslFilter;
import es.dtr.job.interview.commons.api.querydsl.QueryDslOperators;
import es.dtr.job.interview.commons.api.querydsl.QueryDslUtils;
import es.dtr.job.interview.commons.service.crud.CrudService;
import es.dtr.job.interview.inditex.ms.adapter.out.database.hibernate.PriceRepository;
import es.dtr.job.interview.inditex.ms.domain.entity.PriceEntity;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Getter
@Service
@RequiredArgsConstructor
public class PriceService implements CrudService<PriceDto, PriceEntity, Long> {

    // Dependencias
    private final PriceMapper mapper;
    private final PriceRepository repository;
    private final QueryDslUtils queryDslUtils;

    @Override
    public List<PriceDto> findBy(final List<String> filters) {
        return findBy(filters, null);
    }

    @Override
    public List<PriceDto> findBy(final List<String> filters, final Pageable pageable) {
        List<PriceDto> result = new ArrayList<>();

        // Prepare received queryDslFilters
        final List<QueryDslFilter> queryDslFilters = queryDslUtils.prepareFilters(filters);

        // Execute search
        final List<PriceEntity> mayElements = repository.findAll(PriceEntity.class, queryDslFilters, pageable);

        if (CollectionUtils.isNotEmpty(mayElements)) {
            result = mapper.entityToDto(mayElements);
        }

        return result;
    }

    public PriceDto getWith(@NotNull final Long productId, @NotNull final Long brandId, @NotNull final Instant date) {
        PriceDto result;

        // Generate filters with received information
        final List<QueryDslFilter> filters = List.of(
                QueryDslFilter.builder().key("product.id").operator(QueryDslOperators.EQUALS).value(productId).build(),
                QueryDslFilter.builder().key("brand.id").operator(QueryDslOperators.EQUALS).value(brandId).build(),
                QueryDslFilter.builder().key("startDate").operator(QueryDslOperators.LOWER_THAN_OR_EQUALS).value(date).build(),
                QueryDslFilter.builder().key("endDate").operator(QueryDslOperators.GREATER_THAN_OR_EQUALS).value(date).build()
        );

        // Will use sorting from pagination to get price with highest priority value
        final Pageable pageable = PageRequest.of(0, 2, Sort.by(Sort.Direction.DESC, "priority"));

        // Search using filters
        final List<PriceDto> mayResults = findBy(filters.stream().map(QueryDslFilter::toString).toList(), pageable);

        if (CollectionUtils.isNotEmpty(mayResults)) {
            result = mayResults.get(0);
        } else {
            throw NOT_FOUND;
        }

        return result;
    }
}
