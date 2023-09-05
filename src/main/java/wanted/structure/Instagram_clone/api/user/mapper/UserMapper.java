package wanted.structure.Instagram_clone.api.user.mapper;

import org.springframework.stereotype.Component;
import wanted.structure.Instagram_clone.api.auth.dto.request.SignUpRequest;
import wanted.structure.Instagram_clone.api.user.entity.User;

@Component
public class UserMapper {

    public User dtoToEntity(SignUpRequest dto) {
        return User.builder()
            .email(dto.getEmail())
            .password(dto.getPassword())
            .build();
    }
}
