package wanted.structure.Instagram_clone.api.auth.mapper;

import org.springframework.stereotype.Component;
import wanted.structure.Instagram_clone.api.auth.entity.Auth;
import wanted.structure.Instagram_clone.global.code.AuthCode;

@Component
public class AuthMapper {

    public Auth dtoToEntity(Long id, AuthCode code) {
        return Auth.builder()
            .userId(id)
            .role(code.getCode())
            .build();
    }
}
