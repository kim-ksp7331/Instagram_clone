package wanted.structure.Instagram_clone.api.auth.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wanted.structure.Instagram_clone.api.auth.dto.request.SignInRequest;
import wanted.structure.Instagram_clone.api.auth.dto.request.SignUpRequest;
import wanted.structure.Instagram_clone.api.auth.dto.request.VerifyMailRequest;
import wanted.structure.Instagram_clone.api.auth.dto.response.SignInResponse;
import wanted.structure.Instagram_clone.api.auth.dto.response.VerifyMailResponse;
import wanted.structure.Instagram_clone.api.auth.mapper.AuthMapper;
import wanted.structure.Instagram_clone.api.auth.repository.AuthRepository;
import wanted.structure.Instagram_clone.api.user.entity.User;
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

    private final AuthMapper authMapper;

    private final UserMapper userMapper;

    private final RedisUtils redisUtils;

    private final PasswordEncoder passwordEncoder;

    private final JwtProvider jwtProvider;

    @Transactional
    public Long signUp(SignUpRequest request) {
        boolean exists = userRepository.existsByEmail(request.getEmail());
        if (exists) {
            throw new ApiException(ErrorCode.CONFLICT);
        }

        User user = userRepository.save(userMapper.dtoToEntity(request));

        authRepository.save(authMapper.dtoToEntity(user.getId(), AuthCode.USER));

        return user.getId();
    }

    @Transactional
    public VerifyMailResponse verifyMail(VerifyMailRequest request) {
        String authNum = redisUtils.getData(RedisCode.AUTH_NUM.getCode() + request.getEmail());
        return VerifyMailResponse.builder().verify(authNum.equals(request.getAuthNum())).build();
    }


    @Transactional
    public SignInResponse signIn(SignInRequest request) {
        log.info("UserFindByEmail - {} ", request.getEmail());
        User user = userRepository.findByEmail(request.getEmail())
            .orElseThrow(() -> new ApiException(ErrorCode.BAD_REQUEST));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            log.error("Password Match Fail");
            throw new ApiException(ErrorCode.BAD_REQUEST);
        }

        log.info("Password Match Success");

        String accessToken = jwtProvider.generateToken(user);
        String refreshToken = jwtProvider.generateRefreshToken(user);

        redisUtils.setDataExpire(RedisCode.TOKEN.getCode() + request.getEmail(), refreshToken,
            redisUtils.EXPIRE_PERIOD * 2L); // 2일

        return SignInResponse.builder().accessToken(accessToken).refreshToken(refreshToken).build();

    }

}
