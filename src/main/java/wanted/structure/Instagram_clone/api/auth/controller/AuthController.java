package wanted.structure.Instagram_clone.api.auth.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wanted.structure.Instagram_clone.api.auth.dto.request.EmailRequest;
import wanted.structure.Instagram_clone.api.auth.dto.request.SignUpRequest;
import wanted.structure.Instagram_clone.api.auth.dto.request.VerifyMailRequest;
import wanted.structure.Instagram_clone.api.auth.dto.response.VerifyMailResponse;
import wanted.structure.Instagram_clone.api.auth.service.AuthService;
import wanted.structure.Instagram_clone.api.auth.service.EmailService;
import wanted.structure.Instagram_clone.global.dto.EmptyResult;
import wanted.structure.Instagram_clone.global.dto.ResponseDto;
import wanted.structure.Instagram_clone.global.dto.SingleResult;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final EmailService emailService;

    private final AuthService authService;

    @PostMapping("/send-mail")
    public ResponseEntity<EmptyResult> sendMail(@RequestBody @Valid EmailRequest emailRequest) {
        emailService.sendMail(emailRequest, "email");
        log.info("sendMail code : {}, message : {}", HttpStatus.OK, HttpStatus.OK.getReasonPhrase());
        return ResponseDto.of(HttpStatus.OK, HttpStatus.OK.getReasonPhrase());
    }

    @PostMapping("/sign-up")
    public ResponseEntity<EmptyResult> signUp(@RequestBody @Valid SignUpRequest signUpRequest) {
        authService.signUp(signUpRequest);
        log.info("signUp code : {}, message : {}", HttpStatus.OK, HttpStatus.OK.getReasonPhrase());
        return ResponseDto.of(HttpStatus.OK, HttpStatus.OK.getReasonPhrase());
    }

    @PostMapping("/verify/mail")
    public ResponseEntity<SingleResult> verifyMail(@RequestBody VerifyMailRequest verifyMailRequest) {
        VerifyMailResponse response = authService.verifyMail(verifyMailRequest);
        log.info("verifyMail code : {}, message : {}", HttpStatus.OK, HttpStatus.OK.getReasonPhrase());
        return ResponseDto.of(HttpStatus.OK, HttpStatus.OK.getReasonPhrase(), response);
    }
}