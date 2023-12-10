package com.example.AWS_Project.Alumno;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import software.amazon.awssdk.auth.credentials.AwsSessionCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

public class FilesService {
    private S3Client s3Client;
    private final String BUCKET_NAME = "aws-project-120035";
    @Value("${amazon.dynamodb.access-key}")
    private String amazonAWSAccessKey;
    @Value("${amazon.dynamodb.secret-key}")
    private String amazonAWSSecretKey;
    @Value("${amazon.dynamodb.session-token}")
    private String amazonAWSessionToken;

    public FilesService() {
        AwsSessionCredentials awsCredentials = AwsSessionCredentials.create(amazonAWSAccessKey, amazonAWSSecretKey, amazonAWSessionToken);

        s3Client = S3Client.builder()
            .region(Region.US_EAST_1)
            .credentialsProvider(StaticCredentialsProvider.create(awsCredentials))
            .build();
    }

    public S3Client getS3Client(){
        return s3Client;
    }

    public String getBucketName(){
        return BUCKET_NAME;
    }
}
