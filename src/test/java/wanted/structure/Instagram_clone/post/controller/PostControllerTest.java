package wanted.structure.Instagram_clone.post.controller;

import jakarta.servlet.http.Part;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockPart;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import wanted.structure.Instagram_clone.api.post.controller.PostController;
import wanted.structure.Instagram_clone.api.post.dto.request.CreatePostRequest;
import wanted.structure.Instagram_clone.api.post.dto.response.PostResponse;
import wanted.structure.Instagram_clone.api.post.service.PostQueryService;
import wanted.structure.Instagram_clone.api.post.service.PostService;


import java.nio.charset.StandardCharsets;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = PostController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
@MockBean({JpaMetamodelMappingContext.class})
@AutoConfigureMockMvc
class PostControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private PostService postService;
    @MockBean
    private PostQueryService postQueryService;

    @Test
    void createPost() throws Exception {
        // given
        String name = "file";
        String text = "abcd";
        MockMultipartFile file = new MockMultipartFile(name, new byte[0]);
        Part part = new MockPart("text", text.getBytes(StandardCharsets.UTF_8));
        Long id = 2L;
        String mediaUrl = "/image.png";

        String urlTemplate = "/post";
        PostResponse response = PostResponse.builder().id(id).mediaUrl(mediaUrl).text(text).build();

        BDDMockito.given(postService.createPost(Mockito.any(CreatePostRequest.class))).willReturn(response);

        // when
        ResultActions actions = mockMvc.perform(
                multipart(HttpMethod.POST, urlTemplate)
                        .file(file)
                        .part(part)

        );

        // then
        actions
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.content.id").value(id))
                .andExpect(jsonPath("$.content.mediaUrl").value(mediaUrl))
                .andExpect(jsonPath("$.content.text").value(text));
    }

    @Test
    void getPost() throws Exception {
        // given
        Long postId = 1L;
        String mediaUrl = "/image.png";
        String text = "abcd";
        PostResponse response = PostResponse.builder().id(postId).mediaUrl(mediaUrl).text(text).build();

        BDDMockito.given(postQueryService.findPost(Mockito.anyLong())).willReturn(response);
        String urlTemplate = "/post/{post-id}";

        // when
        ResultActions actions = mockMvc.perform(
                get(urlTemplate, postId)
                        .accept(MediaType.APPLICATION_JSON)
        );

        // then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.id").value(postId))
                .andExpect(jsonPath("$.content.mediaUrl").value(mediaUrl))
                .andExpect(jsonPath("$.content.text").value(text));
    }
}