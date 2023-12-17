package es.dtr.job.interview.inditex.ms.domain.service.user;

import es.dtr.job.interview.commons.data.type.EntityTypesMapper;
import es.dtr.job.interview.commons.service.crud.CrudMapper;
import es.dtr.job.interview.inditex.ms.domain.service.user.dto.UserDto;
import es.dtr.job.interview.inditex.ms.domain.service.user.dto.UserRegisterRequest;
import es.dtr.job.interview.inditex.ms.domain.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {EntityTypesMapper.class})
public interface UserMapper extends CrudMapper<UserEntity, UserDto> {

    UserEntity createDtoToEntity(UserRegisterRequest source);
}
