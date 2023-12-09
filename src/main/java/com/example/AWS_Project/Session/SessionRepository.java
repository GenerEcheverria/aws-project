package com.example.AWS_Project.Session;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;

@Repository
public class SessionRepository {
    @Autowired
    private DynamoDBMapper dynamoDBMapper;

    public Session save(Session session) {
        dynamoDBMapper.save(session);
        return session;
    }

    public Optional<Session> findByAlumnoId(int alumnoId) {
        Map<String, AttributeValue> eav = new HashMap<>();
        eav.put(":val1", new AttributeValue().withN(Integer.toString(alumnoId)));
        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
            .withFilterExpression("alumnoId = :val1")
            .withExpressionAttributeValues(eav);
        List<Session> sessions = dynamoDBMapper.scan(Session.class, scanExpression);
        return sessions.isEmpty() ? Optional.empty() : Optional.of(sessions.get(sessions.size() - 1));
    }

    public Optional<Session> logout(int alumnoId, String sessionString) {
        Map<String, AttributeValue> eav = new HashMap<>();
        eav.put(":val1", new AttributeValue().withN(Integer.toString(alumnoId)));
    
        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
            .withFilterExpression("alumnoId = :val1")
            .withExpressionAttributeValues(eav);
    
        List<Session> sessions = dynamoDBMapper.scan(Session.class, scanExpression);
    
        if (!sessions.isEmpty()) {
            Session sessionToUpdate = sessions.get(sessions.size() - 1);
            System.out.println(sessionToUpdate);
            System.out.println(sessionString);
            if (sessionToUpdate.getSessionString().equals(sessionString)) {
                sessionToUpdate.setActive(false);
                dynamoDBMapper.save(sessionToUpdate);
                return Optional.of(sessionToUpdate);
            }
        }
        return Optional.empty();
    }
    
}
