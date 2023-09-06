package wanted.structure.Instagram_clone.api.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import wanted.structure.Instagram_clone.api.user.dto.response.CheckEmailResponse;
import wanted.structure.Instagram_clone.api.user.service.UserService;
import wanted.structure.Instagram_clone.global.dto.ResponseDto;
import wanted.structure.Instagram_clone.global.dto.SingleResult;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/email/exists")
    public ResponseEntity<SingleResult> checkEmailDuplicate(@RequestParam String email) {
        CheckEmailResponse response = userService.checkEmailDuplicate(email);
        return ResponseDto.of(HttpStatus.OK, response);
    }
}
