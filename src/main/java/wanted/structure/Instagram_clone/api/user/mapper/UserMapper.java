package wanted.structure.Instagram_clone.api.user.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import wanted.structure.Instagram_clone.api.auth.dto.request.SignUpRequest;
import wanted.structure.Instagram_clone.api.user.entity.User;


@Mapper(componentModel="spring")
public interface UserMapper {
    @Mapping(target = "id", ignore = true)
    User dtoToEntity(SignUpRequest dto);
}
