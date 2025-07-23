package com.project.s3;

import com.project.exceptions.S3OperationException; // Import the new exception
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.NoSuchKeyException; // Import specific S3 exception
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.io.IOException;

@Service
public class S3Service {

    private static final Logger log = LoggerFactory.getLogger(S3Service.class);

    private final S3Client s3;

    public S3Service(S3Client s3) {
        this.s3 = s3;
    }

    public void putObject(String bucketName, String key, byte[] file) {
        PutObjectRequest objectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();
        try {
            s3.putObject(objectRequest, RequestBody.fromBytes(file));
            log.info("Successfully uploaded object to S3: {}/{}", bucketName, key);
        } catch (Exception e) {
            log.error("Failed to upload object to S3: {}/{}", bucketName, key, e);
            throw new S3OperationException("Failed to upload file to S3", e);
        }
    }

    public byte[] getObject(String bucketName, String key) {
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();

        try {
            ResponseInputStream<GetObjectResponse> res = s3.getObject(getObjectRequest);
            byte[] bytes = res.readAllBytes();
            log.info("Successfully retrieved object from S3: {}/{}", bucketName, key);
            return bytes;
        } catch (NoSuchKeyException e) {
            log.warn("Object not found in S3: {}/{}", bucketName, key);
            // This is crucial: Convert NoSuchKeyException into a more specific, handled exception
            throw new S3OperationException("S3 object not found: " + key, e);
        } catch (IOException e) {
            log.error("IO error reading object from S3: {}/{}", bucketName, key, e);
            throw new S3OperationException("IO error reading S3 object", e);
        } catch (Exception e) { // Catch any other S3 related exceptions
            log.error("Generic error retrieving object from S3: {}/{}", bucketName, key, e);
            throw new S3OperationException("Failed to retrieve file from S3", e);
        }
    }
}