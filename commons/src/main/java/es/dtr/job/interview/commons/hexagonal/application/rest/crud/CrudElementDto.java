package es.dtr.job.interview.commons.hexagonal.application.rest.crud;

import org.springframework.hateoas.RepresentationModel;

public abstract class CrudElementDto<T extends RepresentationModel<? extends T>, ID> extends RepresentationModel<T> {

    public abstract ID getId();
}
