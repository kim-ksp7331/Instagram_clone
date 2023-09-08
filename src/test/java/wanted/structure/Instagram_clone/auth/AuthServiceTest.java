package wanted.structure.Instagram_clone.auth;

import static org.mockito.BDDMockito.given;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import wanted.structure.Instagram_clone.api.auth.dto.request.SignUpRequest;
import wanted.structure.Instagram_clone.api.auth.dto.request.VerifyMailRequest;
import wanted.structure.Instagram_clone.api.auth.dto.response.VerifyMailResponse;
import wanted.structure.Instagram_clone.api.auth.entity.Auth;
import wanted.structure.Instagram_clone.api.auth.mapper.AuthMapper;
import wanted.structure.Instagram_clone.api.auth.repository.AuthRepository;
import wanted.structure.Instagram_clone.api.auth.service.AuthService;
import wanted.structure.Instagram_clone.api.auth.service.JwtProvider;
import wanted.structure.Instagram_clone.api.user.entity.User;
import wanted.structure.Instagram_clone.api.user.mapper.UserMapper;
import wanted.structure.Instagram_clone.api.user.repository.UserRepository;
import wanted.structure.Instagram_clone.global.code.AuthCode;
import wanted.structure.Instagram_clone.global.code.RedisCode;
import wanted.structure.Instagram_clone.global.utils.RedisUtils;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock

    private AuthRepository authRepository;

    @Mock
    private RedisUtils redisUtils;

    @Mock
    private UserMapper userMapper;

    @Mock
    private AuthMapper authMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtProvider jwtProvider;

    @InjectMocks
    private AuthService authService;

    @Test
    void verifyMail() {
        //given
        VerifyMailRequest request = VerifyMailRequest.builder()
            .email("test1@test.com")
            .authNum("test_auth_num")
            .build();

        VerifyMailResponse response = VerifyMailResponse.builder().verify(true).build();

        given(redisUtils.getData(RedisCode.AUTH_NUM.getCode() + request.getEmail())).willReturn("test_auth_num");

        //when
        VerifyMailResponse test = authService.verifyMail(request);
        log.info("checkEmailDuplicateTest - {}", response);

        //then
        Assertions.assertEquals(response.isVerify(), test.isVerify());

    }

    @Test
    void signUp() {
        //given
        Long userId = 1L;
        Long authId = 1L;

        SignUpRequest request = SignUpRequest.builder()
            .email("test1@test.com")
            .password("test_password")
            .nickname("test_nickname")
            .build();

        User user = User.builder()
            .email("test1@test.com")
            .password("test_password")
            .nickname("test_nickname")
            .build();

        User save = User.builder()
            .email("test1@test.com")
            .password("test_password")
            .nickname("test_nickname")
            .id(userId)
            .build();

        Auth auth = Auth.builder()
            .userId(userId)
            .build();

        Auth authSave = Auth.builder()
            .authId(authId)
            .userId(userId)
            .build();

        given(userRepository.existsByEmail(request.getEmail())).willReturn(false);
        given(userMapper.dtoToEntity(request)).willReturn(user);
        given(userRepository.save(user)).willReturn(save);
        given(authMapper.dtoToEntity(save.getId(), AuthCode.USER)).willReturn(auth);
        given(authRepository.save(auth)).willReturn(authSave);

        //when
        Long testId = authService.signUp(request);

        //then
        Assertions.assertEquals(userId, testId);

    }
}
