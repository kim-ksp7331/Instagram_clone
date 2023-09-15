package wanted.structure.Instagram_clone.api.post.controller;

import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import wanted.structure.Instagram_clone.api.post.dto.request.CreatePostRequest;
import wanted.structure.Instagram_clone.api.post.dto.request.UpdatePostRequest;
import wanted.structure.Instagram_clone.api.post.dto.response.PostResponse;
import wanted.structure.Instagram_clone.api.post.service.PostQueryService;
import wanted.structure.Instagram_clone.global.dto.ListResult;
import wanted.structure.Instagram_clone.global.dto.ResponseDto;
import wanted.structure.Instagram_clone.global.dto.SingleResult;
import wanted.structure.Instagram_clone.api.post.service.PostService;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
@Validated
public class PostController {
    private final PostService postService;
    private final PostQueryService postQueryService;
    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<SingleResult> createPost(@RequestPart MultipartFile file,
                                                   @RequestPart(required = false) String text) {
        CreatePostRequest request = CreatePostRequest.builder().file(file).text(text).build();
        PostResponse response = postService.createPost(request);
        return ResponseDto.of(HttpStatus.CREATED, response);
    }

    @GetMapping("/{post-id}")
    public ResponseEntity<SingleResult> getPost(@Positive @PathVariable("post-id") Long postId) {
        PostResponse response = postQueryService.findPost(postId);
        return ResponseDto.of(HttpStatus.OK, response);
    }

    @GetMapping
    public ResponseEntity<ListResult<PostResponse>> getPostPagination(@Positive @RequestParam(defaultValue = "1") int page,
                                                                      @Positive @RequestParam(defaultValue = "10") int size) {
        Page<PostResponse> responses = postQueryService.findPostPage(page, size);
        return ResponseDto.of(HttpStatus.OK, responses);
    }

    @PatchMapping(path = "/{post-id}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<SingleResult> updatePost(@RequestPart(required = false) MultipartFile file,
                                                   @RequestPart(required = false) String text,
                                                   @Positive @PathVariable("post-id")Long postId) {
        UpdatePostRequest request = UpdatePostRequest.builder().id(postId).file(file).text(text).build();
        PostResponse response = postService.updatePost(request);
        return ResponseDto.of(HttpStatus.OK, response);
    }
}
