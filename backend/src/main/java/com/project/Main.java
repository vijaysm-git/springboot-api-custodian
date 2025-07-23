package com.project;

import com.project.customer.Customer;
import com.project.customer.CustomerRepository;
import com.project.customer.Gender;
import com.project.s3.S3Buckets;
import com.project.s3.S3Service;
import com.github.javafaker.Faker;
import com.github.javafaker.Name;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.beans.factory.annotation.Value; // Import this
import jakarta.annotation.PostConstruct; // Import this for printAwsConfig()

import java.util.Random;

@SpringBootApplication
public class Main {

    // These will capture what Spring's Environment is resolving from application.yml
    @Value("${aws.s3.bucket:DEFAULT_BUCKET_VALUE_FROM_YML}") // Add a default just in case
    private String configuredS3Bucket;

    @Value("${aws.region:DEFAULT_REGION_VALUE_FROM_YML}")
    private String configuredAwsRegion;

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    // This method will run after dependency injection is complete, allowing
    // us to inspect the resolved property values.
    @PostConstruct
    public void printAwsConfig() {
        System.out.println("\n--- AWS Configuration Debug ---");
        System.out.println("System Environment Variable AWS_ACCESS_KEY_ID: " + System.getenv("AWS_ACCESS_KEY_ID"));
        System.out.println("System Environment Variable AWS_SECRET_ACCESS_KEY: " + System.getenv("AWS_SECRET_ACCESS_KEY"));
        System.out.println("System Environment Variable AWS_REGION: " + System.getenv("AWS_REGION"));
        System.out.println("System Environment Variable AWS_S3_BUCKET: " + System.getenv("AWS_S3_BUCKET"));

        System.out.println("Spring @Value for aws.s3.bucket (from application.yml): " + configuredS3Bucket);
        System.out.println("Spring @Value for aws.region (from application.yml): " + configuredAwsRegion);
        System.out.println("------------------------------\n");
    }


    @Bean
    CommandLineRunner runner(
            CustomerRepository customerRepository,
            PasswordEncoder passwordEncoder,
            S3Service s3Service, // Inject S3Service
            S3Buckets s3Buckets) { // Inject S3Buckets
        return args -> {
            createRandomCustomer(customerRepository, passwordEncoder);
            testBucketUploadAndDownload(s3Service, s3Buckets); // Uncommented this line
        };
    }

    private static void testBucketUploadAndDownload(S3Service s3Service,
                                                    S3Buckets s3Buckets) {
        System.out.println("Attempting S3 bucket operations...");
        try {
            s3Service.putObject(
                    s3Buckets.getCustomer(), // This should be 'project-customer' if resolved correctly
                    "foo/bar/object",
                    "Hello World".getBytes()
            );

            byte[] obj = s3Service.getObject(
                    s3Buckets.getCustomer(),
                    "foo/bar/object"
            );

            System.out.println("S3 Operation Successful: " + new String(obj));
        } catch (Exception e) {
            System.err.println("S3 Operation Failed during CommandLineRunner: " + e.getMessage());
            e.printStackTrace(); // Print stack trace for full details
        }
    }

    private static void createRandomCustomer(CustomerRepository customerRepository, PasswordEncoder passwordEncoder) {
        var faker = new Faker();
        Random random = new Random();
        Name name = faker.name();
        String firstName = name.firstName();
        String lastName = name.lastName();
        int age = random.nextInt(16, 99);
        Gender gender = age % 2 == 0 ? Gender.MALE : Gender.FEMALE;
        String email = firstName.toLowerCase() + "." + lastName.toLowerCase() + "@project.com";
        Customer customer = new Customer(
                firstName +  " " + lastName,
                email,
                passwordEncoder.encode("password"),
                age,
                gender);
        customerRepository.save(customer);
        System.out.println("Created random customer: " + email);
    }
}