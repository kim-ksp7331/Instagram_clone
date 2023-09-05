package wanted.structure.Instagram_clone.api.auth.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wanted.structure.Instagram_clone.api.auth.dto.request.EmailRequest;
import wanted.structure.Instagram_clone.api.auth.service.EmailService;
import wanted.structure.Instagram_clone.global.dto.EmptyResult;
import wanted.structure.Instagram_clone.global.dto.ResponseDto;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final EmailService emailService;

    @PostMapping("/mail")
    public ResponseEntity<EmptyResult> mail(@RequestBody EmailRequest emailRequest) {
        emailService.sendMail(emailRequest, "email");
        return ResponseDto.of(HttpStatus.OK, HttpStatus.OK.getReasonPhrase());

    }

}