package wanted.structure.Instagram_clone.post.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import wanted.structure.Instagram_clone.global.media.StorageService;
import wanted.structure.Instagram_clone.post.dto.response.PostResponse;
import wanted.structure.Instagram_clone.post.entity.Post;

@Mapper(componentModel = "spring", uses = {StorageService.class})
public interface PostMapper {
    @Mapping(source = "mediaKey", target = "mediaUrl", qualifiedByName = "getPreSignedUrl")
    PostResponse entityToDto(Post post);
}
