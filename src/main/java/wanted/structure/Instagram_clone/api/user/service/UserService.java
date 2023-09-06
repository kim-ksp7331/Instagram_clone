package wanted.structure.Instagram_clone.api.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wanted.structure.Instagram_clone.api.user.dto.response.CheckEmailResponse;
import wanted.structure.Instagram_clone.api.user.repository.UserRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public CheckEmailResponse checkEmailDuplicate(String email) {
        boolean exists = userRepository.existsByEmail(email);
        log.info("checkEmailDuplicate email - {}, duplicate - {} ", email, exists);
        return CheckEmailResponse.builder().exists(exists).build();
    }

}
