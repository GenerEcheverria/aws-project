package com.example.AWS_Project.Session;

import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicSessionCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

@Configuration
@EnableDynamoDBRepositories(basePackages = "com.example.AWS_Project.Session")
public class DynamoDbConfiguration {
    private String amazonDynamoDBEndpoint = "dynamodb.us-east-1.amazonaws.com";
    private String amazonAWSAccessKey = "ASIAZ3YIADFGUFSUTA2Y";
    private String amazonAWSSecretKey = "K1n7h4ThN/H61nWTeZ0ZP5JPy8zm2tDL0ClzWEiV";
    private String amazonAWSessionToken = "FwoGZXIvYXdzEIX//////////wEaDOlMsld7qo8f3Vw9AiLPAe6vYhKGNJWESU19MQi3ZQcRx51zLUo9lKh25w0X8ttmDG8tNuhuvZJL7/9O7F8THyrbECxuVqEQTdl+JpKJCSyV28UshpdRVF8VOvydmxe7ffIYLURhQOlHzwsjpl0eqa9wXV9LlCjiX0UPNUkUak8KQ/hd+97V14D945r7RFq3ZqD+wkbjeeymVumOw740Llu4J5Xfj+3lOMAPo9wSQEyHPE88a7d5OWdhqu/4TsUZ3MweyUlX9DR6deW9heWsxkD2hTZVXWGBzPo3tmQkSiiC9tKrBjItdRwfQg2xq5zAZHdiy0AF9q3tDXrpb95VWjIlGbxdVAH5V8vGsbtZApK0oGgb";   
    private String amazonAWSRegion = "us-east-1";
	@Bean
	public DynamoDBMapper dynamoDBMapper() {
		return new DynamoDBMapper(buildAmazonDynamoDB());
	}
	
	private AmazonDynamoDB buildAmazonDynamoDB() {
		return AmazonDynamoDBClientBuilder
                .standard()
                .withEndpointConfiguration(
                        new AwsClientBuilder.EndpointConfiguration(
                                amazonDynamoDBEndpoint,amazonAWSRegion)
                )
                .withCredentials(
                        new AWSStaticCredentialsProvider(
                                new BasicSessionCredentials(amazonAWSAccessKey,amazonAWSSecretKey, amazonAWSessionToken)
                        )
                )
                .build();
	}
}