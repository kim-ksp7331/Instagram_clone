package wanted.structure.Instagram_clone.post.controller;

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
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import wanted.structure.Instagram_clone.post.dto.request.CreatePostRequest;
import wanted.structure.Instagram_clone.post.dto.response.PostResponse;
import wanted.structure.Instagram_clone.post.service.PostService;


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

    @Test
    void createPost() throws Exception {
        // given
        String name = "file";
        MockMultipartFile file = new MockMultipartFile(name, new byte[0]);
        Long id = 2L;
        String mediaUrl = "/image.png";

        String urlTemplate = "/post";
        PostResponse response = PostResponse.builder().id(id).mediaUrl(mediaUrl).build();

        BDDMockito.given(postService.createPost(Mockito.any(CreatePostRequest.class))).willReturn(response);

        // when
        ResultActions actions = mockMvc.perform(
                multipart(HttpMethod.POST, urlTemplate)
                        .file(file)
        );

        // then
        actions
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.content.id").value(id))
                .andExpect(jsonPath("$.content.mediaUrl").value(mediaUrl));
    }
}