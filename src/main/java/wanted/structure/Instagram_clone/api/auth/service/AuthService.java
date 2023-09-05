package wanted.structure.Instagram_clone.api.auth.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wanted.structure.Instagram_clone.api.auth.dto.request.SignUpRequest;
import wanted.structure.Instagram_clone.api.auth.mapper.AuthMapper;
import wanted.structure.Instagram_clone.api.auth.repository.AuthRepository;
import wanted.structure.Instagram_clone.api.user.entity.User;
import wanted.structure.Instagram_clone.api.user.mapper.UserMapper;
import wanted.structure.Instagram_clone.api.user.repository.UserRepository;
import wanted.structure.Instagram_clone.global.code.AuthCode;
import wanted.structure.Instagram_clone.global.exception.ApiException;
import wanted.structure.Instagram_clone.global.exception.ErrorCode;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthRepository authRepository;

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final AuthMapper authMapper;

    @Transactional
    public void signUp(SignUpRequest request) {
        userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new ApiException(ErrorCode.CONFLICT));
        userRepository.save(userMapper.dtoToEntity(request));
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow();
        try{
            authRepository.findAuthByUserId(user.getId());
        }catch (Exception e){
            authRepository.save(authMapper.dtoToEntity(user.getId(), AuthCode.USER));
        }
    }

}
