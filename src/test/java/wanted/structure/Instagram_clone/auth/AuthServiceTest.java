package wanted.structure.Instagram_clone.auth;

import static org.mockito.BDDMockito.given;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import wanted.structure.Instagram_clone.api.auth.dto.request.VerifyMailRequest;
import wanted.structure.Instagram_clone.api.auth.dto.response.VerifyMailResponse;
import wanted.structure.Instagram_clone.api.auth.service.AuthService;
import wanted.structure.Instagram_clone.global.code.RedisCode;
import wanted.structure.Instagram_clone.global.utils.RedisUtils;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @Mock
    private RedisUtils redisUtils;

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
}
