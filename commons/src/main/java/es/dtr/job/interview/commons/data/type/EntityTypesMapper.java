package es.dtr.job.interview.commons.data.type;

import es.dtr.job.interview.commons.data.type.email.Email;
import es.dtr.job.interview.commons.data.type.phone.PhoneNumber;
import es.dtr.job.interview.commons.data.type.phone.PhoneNumberDatabase;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.Mapper;

import java.util.Objects;

@Mapper(componentModel = "spring")
public abstract class EntityTypesMapper {

    public Email stringToEmail(String value) {
        return StringUtils.isNotBlank(value) ? new Email(value) : null;
    }

    public String emailToString(Email value) {
        return Objects.nonNull(value) ? value.getAddress() : null;
    }

    public PhoneNumber stringToPhoneNumber(String value) {
        return StringUtils.isNotBlank(value) ? new PhoneNumber(value) : null;
    }

    public String phoneNumberToString(PhoneNumberDatabase value) {
        return Objects.nonNull(value) ? value.toString() : null;
    }

}
