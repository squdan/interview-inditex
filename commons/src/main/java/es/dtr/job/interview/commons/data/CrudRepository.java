package es.dtr.job.interview.commons.data;

import com.querydsl.core.types.dsl.EntityPathBase;
import es.dtr.job.interview.commons.data.querydsl.QueryDslRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface CrudRepository<T, ID> extends JpaRepository<T, ID>, QueryDslRepository<T, EntityPathBase<T>> {
}
