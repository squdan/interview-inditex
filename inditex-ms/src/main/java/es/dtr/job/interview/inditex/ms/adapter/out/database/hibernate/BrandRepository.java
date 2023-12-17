package es.dtr.job.interview.inditex.ms.adapter.out.database.hibernate;

import es.dtr.job.interview.commons.data.CrudRepository;
import es.dtr.job.interview.inditex.ms.domain.entity.BrandEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandRepository extends CrudRepository<BrandEntity, Long> {

}
