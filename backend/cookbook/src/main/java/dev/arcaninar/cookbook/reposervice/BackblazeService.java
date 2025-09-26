package dev.arcaninar.cookbook.reposervice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;

import java.util.Base64;

@Service
public class BackblazeService {

    @Autowired
    private S3Client s3Client;

    @Value("${backblaze.s3.bucket.name}")
    private String bucketName;

    public String getFile(String fileName) {
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(fileName)
                .build();

        ResponseBytes<GetObjectResponse> responseBytes = s3Client.getObjectAsBytes(getObjectRequest);
        byte[] fileBytes = responseBytes.asByteArray();
        return Base64.getEncoder().encodeToString(fileBytes);
    }
}
