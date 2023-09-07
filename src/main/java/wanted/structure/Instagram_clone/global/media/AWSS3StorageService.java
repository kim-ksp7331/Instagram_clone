package wanted.structure.Instagram_clone.global.media;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;

import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.util.UUID;

@Slf4j
@Service
public class AWSS3StorageService implements StorageService{
    @Value("${aws-s3.access-key}")
    private String accessKey = "";
    @Value("${aws-s3.secret-access-key}")
    private String secretKey = "";
    private S3Client s3Client;
    private S3Presigner s3Presigner;
    private Region clientRegion = Region.AP_NORTHEAST_2;
    private String bucket = "ksp7331-instagram-clone";
    @PostConstruct
    private void createS3Client() {
        AwsCredentials awsCredentials = AwsBasicCredentials.create(accessKey, secretKey);
        StaticCredentialsProvider credentialsProvider = StaticCredentialsProvider.create(awsCredentials);
        this.s3Client = S3Client.builder()
                .credentialsProvider(credentialsProvider)
                .region(clientRegion)
                .build();

        this.s3Presigner = S3Presigner.builder()
                .credentialsProvider(credentialsProvider)
                .region(clientRegion)
                .build();
    }
    private String verifyType(MultipartFile image) {
        if(image == null || image.getContentType() == null) return null;

        return image.getContentType();
    }
    @Override
    public String store(MultipartFile file, String directory) {
        if (verifyType(file) == null) return null;
        UUID uuid = UUID.randomUUID();
        String uploadImageName = directory + "/" + uuid + "_" + file.getOriginalFilename();
        try {
            uploadToS3(file.getBytes(), uploadImageName, file.getContentType(), file.getSize(), this.bucket);
            return uploadImageName;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private String uploadToS3(byte[] bytes, String key, String contentType, long contentLength, String bucket) {
        PutObjectRequest objectRequest = PutObjectRequest.builder()
                .bucket(bucket)
                .key(key)
                .contentType(contentType)
                .contentLength(contentLength)
                .build();
        try {
            this.s3Client.putObject(objectRequest, RequestBody.fromBytes(bytes));
            log.info("{} upload complete in bucket {}", objectRequest.key(), bucket);
            return objectRequest.key();
        } catch (AwsServiceException e) {
            log.error("# AWS S3 error", e);
        } catch (SdkClientException e) {
            log.error("# AWS S3 error", e);
        } catch (Exception e) {
            log.error("# AWS S3 error", e);
        }
        return null;
    }

    @Override
    public void delete(String key) {
        if(key != null){
            try {
                DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest
                        .builder()
                        .bucket(this.bucket)
                        .key(key)
                        .build();
                this.s3Client.deleteObject(deleteObjectRequest);
                log.info("{}, deletion complete", key);
            } catch (AwsServiceException e) {
                log.error("# AWS S3 error", e);
            } catch (SdkClientException e) {
                log.error("# AWS S3 error", e);
            }
        }
    }

    @Override
    public String getPreSignedUrl(String key) {
        if(key != null){
            try {
                GetObjectRequest request = GetObjectRequest.builder()
                        .bucket(this.bucket)
                        .key(key)
                        .build();
                GetObjectPresignRequest presignRequest = GetObjectPresignRequest.builder()
                        .getObjectRequest(request)
                        .signatureDuration(Duration.ofMinutes(10))
                        .build();

                PresignedGetObjectRequest presignedGetObjectRequest = s3Presigner.presignGetObject(presignRequest);
                URL url = presignedGetObjectRequest.url();
                log.info("pre-signed url : {}", url);
                return url.toString();

            } catch (S3Exception e) {
                log.error("# AWS S3 error", e);
            }
        }
        return null;
    }
}
