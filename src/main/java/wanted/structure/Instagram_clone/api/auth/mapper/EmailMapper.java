package wanted.structure.Instagram_clone.api.auth.mapper;

import org.springframework.stereotype.Component;
import wanted.structure.Instagram_clone.api.auth.dto.EmailMessage;
import wanted.structure.Instagram_clone.api.auth.dto.request.EmailRequest;

@Component
public class EmailMapper {

    public EmailMessage dtoToVo(EmailRequest dto) {
        return EmailMessage.builder()
            .to(dto.getEmail())
            .subject("이메일 인증")
            .build();
    }
}
