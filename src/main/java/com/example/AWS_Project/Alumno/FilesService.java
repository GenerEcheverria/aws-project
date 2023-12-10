package com.example.AWS_Project.Alumno;

import com.example.AWS_Project.Credentials;

import software.amazon.awssdk.auth.credentials.AwsSessionCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

public class FilesService {
    private S3Client s3Client;
    private final String BUCKET_NAME = "aws-project-12003522";

    public FilesService() {
        AwsSessionCredentials awsCredentials = AwsSessionCredentials.create(Credentials.ACCESS_KEY, Credentials.SECRET_KEY, Credentials.SESSION_TOKEN);

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
