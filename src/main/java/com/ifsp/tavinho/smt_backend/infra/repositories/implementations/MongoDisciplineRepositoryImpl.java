package com.ifsp.tavinho.smt_backend.infra.repositories.implementations;

import java.util.Collections;
import java.util.List;

import com.ifsp.tavinho.smt_backend.application.dtos.output.*;
import com.ifsp.tavinho.smt_backend.domain.repositories.DisciplineAggregationRepository;

import org.bson.Document;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@Repository
@Primary
@RequiredArgsConstructor
public class MongoDisciplineRepositoryImpl implements DisciplineAggregationRepository {

    private final MongoTemplate mongoTemplate;

    @Override
    public List<DisciplineDetailsResponseSimplifiedDTO> findAllWithCourses() {

        AggregationOperation stage1 = context -> Document.parse("""
        { "$lookup": { "from": "courses", "let": { "courseId": { "$cond": { "if": { "$eq": [ { "$type": "$course_id" }, "objectId" ] }, "then": "$course_id", "else": { "$toObjectId": "$course_id" } } } }, "pipeline": [ { "$match": { "$expr": { "$eq": ["$_id", "$$courseId"] } } } ], "as": "course" } }
        """);
        AggregationOperation stage2 = context -> Document.parse("""
        { "$unwind": { "path": "$course", "preserveNullAndEmptyArrays": true } }
        """);

        AggregationOperation stage3 = context -> Document.parse("""
        {
          "$project": {
            "_id": 0,
            "id": { "$toString": "$_id" },
            "name": 1,
            "abbreviation": 1,
            "course": { "id": "$course_id", "description": "$course.abbreviation" }
          }
        }
        """);

        Aggregation aggregation = Aggregation.newAggregation(stage1, stage2, stage3);

        List<Document> results = mongoTemplate.aggregate(aggregation, "disciplines", Document.class).getMappedResults();

        if (results == null || results.isEmpty()) return Collections.emptyList();

        return results.stream().map(this::mapDocumentToDto).toList();
    }

    private DisciplineDetailsResponseSimplifiedDTO mapDocumentToDto(Document doc) {
        if (doc == null) return null;

        Document courseDoc = doc.get("course", Document.class);

        SimplifiedEntityDTO course = (courseDoc != null)
            ? new SimplifiedEntityDTO(courseDoc.getString("id"), courseDoc.getString("description"))
            : new SimplifiedEntityDTO(null, null);

        return new DisciplineDetailsResponseSimplifiedDTO(doc.getString("id"), doc.getString("name"), doc.getString("abbreviation"), course);
    }
}
