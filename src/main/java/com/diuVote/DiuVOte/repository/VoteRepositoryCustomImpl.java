package com.diuVote.DiuVOte.repository;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@Component
public class VoteRepositoryCustomImpl implements VoteRepositoryCustom {

    private final MongoTemplate mongoTemplate;

    @Autowired
    public VoteRepositoryCustomImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Map<String, Long> countVotesGroupedByCandidate(String eventId) {
        MatchOperation matchEvent =
                match(org.springframework.data.mongodb.core.query.Criteria.where("eventId").is(eventId));
        GroupOperation groupByCandidate =
                group("candidateId").count().as("count");
        ProjectionOperation project =
                project("count").and("_id").as("candidateId");

        Aggregation aggregation = newAggregation(matchEvent, groupByCandidate, project);

        AggregationResults<Document> results =
                mongoTemplate.aggregate(aggregation, "votes", Document.class);

        Map<String, Long> counts = new HashMap<>();
        List<Document> mapped = results.getMappedResults();
        for (Document doc : mapped) {
            String candidateId = doc.getString("candidateId");
            Number countNum = (Number) doc.get("count");
            counts.put(candidateId, countNum == null ? 0L : countNum.longValue());
        }
        return counts;
    }
}