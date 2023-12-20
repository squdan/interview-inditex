package es.dtr.job.interview.commons.hexagonal.infrastructure.database;

import com.querydsl.core.types.dsl.EntityPathBase;
import es.dtr.job.interview.commons.hexagonal.domain.repository.querydsl.QueryDslRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface CrudInfraRepository<T, ID> extends JpaRepository<T, ID>, QueryDslRepository<T, EntityPathBase<T>> {

}
