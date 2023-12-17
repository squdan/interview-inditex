package es.dtr.job.interview.inditex.ms.adapter.out.database.hibernate;

import es.dtr.job.interview.commons.data.CrudRepository;
import es.dtr.job.interview.inditex.ms.domain.entity.PriceEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface PriceRepository extends CrudRepository<PriceEntity, Long> {

}
