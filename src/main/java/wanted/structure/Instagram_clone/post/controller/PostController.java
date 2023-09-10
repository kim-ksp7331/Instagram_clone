package wanted.structure.Instagram_clone.post.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import wanted.structure.Instagram_clone.global.dto.ResponseDto;
import wanted.structure.Instagram_clone.global.dto.SingleResult;
import wanted.structure.Instagram_clone.post.dto.request.CreatePostRequest;
import wanted.structure.Instagram_clone.post.dto.response.PostResponse;
import wanted.structure.Instagram_clone.post.service.PostService;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
@Validated
public class PostController {
    private final PostService postService;
    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<SingleResult> createPost(@RequestPart MultipartFile file,
                                                   @RequestPart(required = false) String text) {
        CreatePostRequest request = CreatePostRequest.builder().file(file).text(text).build();
        PostResponse response = postService.createPost(request);
        return ResponseDto.of(HttpStatus.CREATED, response);
    }
}
