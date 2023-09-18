package wanted.structure.Instagram_clone.global.media;

import org.mapstruct.Named;
import org.springframework.web.multipart.MultipartFile;

public interface StorageService {

    /**
     * 파일을 저장하는 메서드
     *
     * @param file  이미지 파일
     * @param directory 디렉토리명
     * @return key - 디렉토리명과 파일명이 합쳐져 있습니다.
     */
    String store(MultipartFile file, String directory);

    /**
     * 파일을 삭제하는 메서드
     *
     * @param key - 디렉토리명과 파일명이 합쳐져 있습니다.
     */
    void delete(String key);

    /**
     * 파일의 pre-signed url을 가져오는 메서드
     *
     * @param key - 디렉토리명과 파일명이 합쳐져 있습니다.
     * @return pre-signed url. key가 null이면 null을 반환합니다.
     */
    @Named("getPreSignedUrl")
    String getPreSignedUrl(String key);

    boolean isEmptyFile(MultipartFile file);
}
