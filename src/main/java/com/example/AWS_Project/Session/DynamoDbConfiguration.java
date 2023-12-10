package com.example.AWS_Project.Session;

import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicSessionCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.example.AWS_Project.Credentials;

@Configuration
@EnableDynamoDBRepositories(basePackages = "com.example.AWS_Project.Session")
public class DynamoDbConfiguration {
        @Value("${amazon.dynamodb.endpoint}")
        private String amazonDynamoDBEndpoint;

        @Value("${amazon.dynamodb.region}")
        private String amazonAWSRegion;

        @Bean
        public DynamoDBMapper dynamoDBMapper() {
                return new DynamoDBMapper(buildAmazonDynamoDB());
        }

        private AmazonDynamoDB buildAmazonDynamoDB() {
                return AmazonDynamoDBClientBuilder
                                .standard()
                                .withEndpointConfiguration(
                                                new AwsClientBuilder.EndpointConfiguration(
                                                                amazonDynamoDBEndpoint, amazonAWSRegion))
                                .withCredentials(
                                                new AWSStaticCredentialsProvider(
                                                                new BasicSessionCredentials(Credentials.ACCESS_KEY,
                                                                                Credentials.SECRET_KEY,
                                                                                Credentials.SESSION_TOKEN)))
                                .build();
        }
}