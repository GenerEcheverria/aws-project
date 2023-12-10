package com.example.AWS_Project.Alumno;

import com.example.AWS_Project.Credentials;
import software.amazon.awssdk.auth.credentials.AwsSessionCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sns.SnsClient;

public class SnsService {
    private final SnsClient snsClient;
    private final String TOPIC_ARN = "arn:aws:sns:us-east-1:678084745549:Calificaciones";

    public SnsService() {
        AwsSessionCredentials awsCredentials = AwsSessionCredentials.create(
                Credentials.ACCESS_KEY,
                Credentials.SECRET_KEY,
                Credentials.SESSION_TOKEN
        );

        snsClient = SnsClient.builder()
                .region(Region.US_EAST_1)
                .credentialsProvider(StaticCredentialsProvider.create(awsCredentials))
                .build();
    }

    public SnsClient getSnsClient() {
        return snsClient;
    }

    public String getTopicARN() {
        return TOPIC_ARN;
    }
}
