package es.dtr.job.interview.inditex.ms.domain.service.brand;

import es.dtr.job.interview.commons.api.querydsl.QueryDslFilter;
import es.dtr.job.interview.commons.api.querydsl.QueryDslUtils;
import es.dtr.job.interview.commons.service.crud.CrudService;
import es.dtr.job.interview.inditex.ms.adapter.out.database.hibernate.BrandRepository;
import es.dtr.job.interview.inditex.ms.domain.entity.BrandEntity;
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
public class BrandService implements CrudService<BrandDto, BrandEntity, Long> {

    // Dependencias
    private final BrandMapper mapper;
    private final BrandRepository repository;
    private final QueryDslUtils queryDslUtils;

    @Override
    public List<BrandDto> findBy(final List<String> filters) {
        return findBy(filters, null);
    }

    @Override
    public List<BrandDto> findBy(final List<String> filters, final Pageable pageable) {
        List<BrandDto> result = new ArrayList<>();

        // Prepare received queryDslFilters
        final List<QueryDslFilter> queryDslFilters = queryDslUtils.prepareFilters(filters);

        // Execute search
        final List<BrandEntity> mayElements = repository.findAll(BrandEntity.class, queryDslFilters, pageable);

        if (CollectionUtils.isNotEmpty(mayElements)) {
            result = mapper.entityToDto(mayElements);
        }

        return result;
    }

}
