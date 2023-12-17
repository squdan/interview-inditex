package es.dtr.job.interview.inditex.ms.domain.service.product;

import es.dtr.job.interview.commons.api.querydsl.QueryDslFilter;
import es.dtr.job.interview.commons.api.querydsl.QueryDslUtils;
import es.dtr.job.interview.commons.service.crud.CrudService;
import es.dtr.job.interview.inditex.ms.adapter.out.database.hibernate.ProductRepository;
import es.dtr.job.interview.inditex.ms.domain.entity.ProductEntity;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Getter
@Service
@RequiredArgsConstructor
public class ProductService implements CrudService<ProductDto, ProductEntity, Long> {

    // Dependencias
    private final ProductMapper mapper;
    private final ProductRepository repository;
    private final QueryDslUtils queryDslUtils;

    @Override
    public List<ProductDto> findBy(final List<String> filters) {
        return findBy(filters, null);
    }

    @Override
    public List<ProductDto> findBy(final List<String> filters, final Pageable pageable) {
        List<ProductDto> result = new ArrayList<>();

        // Prepare received queryDslFilters
        final List<QueryDslFilter> queryDslFilters = queryDslUtils.prepareFilters(filters);

        // Execute search
        final List<ProductEntity> mayElements = repository.findAll(ProductEntity.class, queryDslFilters, pageable);

        if (CollectionUtils.isNotEmpty(mayElements)) {
            result = mapper.entityToDto(mayElements);
        }

        return result;
    }

}
