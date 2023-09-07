package wanted.structure.Instagram_clone.user;

import static org.mockito.BDDMockito.given;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import wanted.structure.Instagram_clone.api.user.repository.UserRepository;
import wanted.structure.Instagram_clone.api.user.service.UserService;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void checkEmailDuplicateTest() {
        //given
        Boolean exists = true;

        given(userRepository.existsByEmail("test1@test.com")).willReturn(exists);

        //when
        Boolean test = userService.checkEmailDuplicate("test1@test.com").isDuplicate();
        log.info("checkEmailDuplicateTest - {}", test);

        //then
        Assertions.assertEquals(exists, test);
    }

    @Test
    void checkNicknameDuplicate() {
        //given
        Boolean exists = true;

        given(userRepository.existsByEmail("test_nickname1")).willReturn(exists);

        //when
        Boolean test = userService.checkEmailDuplicate("test_nickname1").isDuplicate();
        log.info("checkNicknameDuplicate - {}", test);

        //then
        Assertions.assertEquals(exists, test);
    }

}
