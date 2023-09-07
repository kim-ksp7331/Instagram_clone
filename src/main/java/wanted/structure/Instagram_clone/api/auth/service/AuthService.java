package wanted.structure.Instagram_clone.api.auth.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wanted.structure.Instagram_clone.api.auth.dto.request.SignUpRequest;
import wanted.structure.Instagram_clone.api.auth.dto.request.VerifyMailRequest;
import wanted.structure.Instagram_clone.api.auth.dto.response.VerifyMailResponse;
import wanted.structure.Instagram_clone.api.auth.mapper.AuthMapper;
import wanted.structure.Instagram_clone.api.auth.repository.AuthRepository;
import wanted.structure.Instagram_clone.api.user.mapper.UserMapper;
import wanted.structure.Instagram_clone.api.user.repository.UserRepository;
import wanted.structure.Instagram_clone.global.code.AuthCode;
import wanted.structure.Instagram_clone.global.code.RedisCode;
import wanted.structure.Instagram_clone.global.exception.ApiException;
import wanted.structure.Instagram_clone.global.exception.ErrorCode;
import wanted.structure.Instagram_clone.global.utils.RedisUtils;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthRepository authRepository;

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final AuthMapper authMapper;

    private final RedisUtils redisUtils;

    @Transactional
    public Long signUp(SignUpRequest request) {
        boolean exists = userRepository.existsByEmail(request.getEmail());
        if (exists) {
            throw new ApiException(ErrorCode.CONFLICT);
        }

        Long userId = userRepository.save(userMapper.dtoToEntity(request)).getId();
        authRepository.save(authMapper.dtoToEntity(userId, AuthCode.USER));

        return userId;
    }

    @Transactional
    public VerifyMailResponse verifyMail(VerifyMailRequest request) {
        String authNum = redisUtils.getData(RedisCode.AUTH_NUM.getCode() + request.getEmail());
        return VerifyMailResponse.builder().verify(authNum.equals(request.getAuthNum())).build();
    }

}
