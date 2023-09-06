package wanted.structure.Instagram_clone.api.user.mapper;

import org.mapstruct.Mapper;
import wanted.structure.Instagram_clone.api.auth.dto.request.SignUpRequest;
import wanted.structure.Instagram_clone.api.user.entity.User;


@Mapper(componentModel="spring")
public interface UserMapper {
    User dtoToEntity(SignUpRequest dto);
}
