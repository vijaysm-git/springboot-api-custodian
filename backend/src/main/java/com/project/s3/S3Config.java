package com.project.s3;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Configuration
public class S3Config {

    private static final Logger log = LoggerFactory.getLogger(S3Config.class);

    @Value("${aws.region}")
    private String awsRegion;

    @Value("${aws.s3.mock}")
    private boolean isS3Mocked;

    @Bean
    public S3Client s3Client() {
        if (isS3Mocked) {
            log.warn("AWS S3 client is configured to use a mock implementation. No actual S3 calls will be made.");
            // TODO: Provide a proper Mock S3Client implementation here for testing
            // For example, using Mockito:
            // import static org.mockito.Mockito.mock;
            // return mock(S3Client.class);
            throw new IllegalStateException("Mock S3Client not implemented. Set aws.s3.mock=false or provide a mock bean.");
            // Alternatively, return a no-op client or a simple mock if you have one
        } else {
            log.info("Initializing AWS S3 client for region: {}", awsRegion);
            try {
                return S3Client.builder()
                        .region(Region.of(awsRegion))
                        .credentialsProvider(DefaultCredentialsProvider.create()) // Uses default AWS credential chain
                        .build();
            } catch (Exception e) {
                log.error("Failed to initialize AWS S3 Client. Check AWS region and credentials.", e);
                throw new RuntimeException("Failed to initialize AWS S3 Client.", e);
            }
        }
    }
}