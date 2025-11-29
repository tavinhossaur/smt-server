package com.ifsp.tavinho.smt_backend.infra.repositories.implementations;

import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;

import com.ifsp.tavinho.smt_backend.application.dtos.output.*;
import com.ifsp.tavinho.smt_backend.domain.enums.Weekday;
import com.ifsp.tavinho.smt_backend.domain.repositories.EventAggregationRepository;

import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MongoEventRepositoryImpl implements EventAggregationRepository {

    private final MongoTemplate mongoTemplate;

    @Override
    public List<EventDetailsResponseSimplifiedDTO> findAllWithMinimalDetails() {

        AggregationOperation stage1 = context -> Document.parse("""
        { "$lookup": { "from": "classrooms", "let": { "classroomId": { "$cond": { "if": { "$eq": [ { "$type": "$classroom_id" }, "objectId" ] }, "then": "$classroom_id", "else": { "$toObjectId": "$classroom_id" } } } }, "pipeline": [ { "$match": { "$expr": { "$eq": ["$_id", "$$classroomId"] } } } ], "as": "classroom" } }
        """);
        AggregationOperation stage2 = context -> Document.parse("""
        { "$unwind": { "path": "$classroom", "preserveNullAndEmptyArrays": true } }
        """);
        AggregationOperation stage3 = context -> Document.parse("""
        { "$lookup": { "from": "professors", "let": { "professorId": { "$cond": { "if": { "$eq": [ { "$type": "$professor_id" }, "objectId" ] }, "then": "$professor_id", "else": { "$toObjectId": "$professor_id" } } } }, "pipeline": [ { "$match": { "$expr": { "$eq": ["$_id", "$$professorId"] } } } ], "as": "professor" } }
        """);
        AggregationOperation stage4 = context -> Document.parse("""
        { "$unwind": { "path": "$professor", "preserveNullAndEmptyArrays": true } }
        """);
        AggregationOperation stage5 = context -> Document.parse("""
        { "$lookup": { "from": "courses", "let": { "courseId": { "$cond": { "if": { "$eq": [ { "$type": "$course_id" }, "objectId" ] }, "then": "$course_id", "else": { "$toObjectId": "$course_id" } } } }, "pipeline": [ { "$match": { "$expr": { "$eq": ["$_id", "$$courseId"] } } } ], "as": "course" } }
        """);
        AggregationOperation stage6 = context -> Document.parse("""
        { "$unwind": { "path": "$course", "preserveNullAndEmptyArrays": true } }
        """);
        AggregationOperation stage7 = context -> Document.parse("""
        { "$lookup": { "from": "disciplines", "let": { "disciplineId": { "$cond": { "if": { "$eq": [ { "$type": "$discipline_id" }, "objectId" ] }, "then": "$discipline_id", "else": { "$toObjectId": "$discipline_id" } } } }, "pipeline": [ { "$match": { "$expr": { "$eq": ["$_id", "$$disciplineId"] } } } ], "as": "discipline" } }
        """);
        AggregationOperation stage8 = context -> Document.parse("""
        { "$unwind": { "path": "$discipline", "preserveNullAndEmptyArrays": true } }
        """);

        AggregationOperation stage9 = context -> Document.parse("""
        {
          "$project": {
            "_id": 0,
            "id": { "$toString": "$_id" },
            "description": 1,
            "weekday": 1,
            "startTime": "$start_time",
            "endTime": "$end_time",
            "classroom": { "id": "$classroom_id", "description": "$classroom.description" },
            "professor": { "id": "$professor_id", "description": "$professor.name" },
            "course": { "id": "$course_id", "description": "$course.abbreviation" },
            "discipline": { "id": "$discipline_id", "description": "$discipline.abbreviation" }
          }
        }
        """);

        Aggregation aggregation = Aggregation.newAggregation(
            stage1, stage2, stage3, stage4, stage5, stage6, stage7, stage8, stage9
        );

        List<Document> results = mongoTemplate.aggregate(
            aggregation,
            "events",
            Document.class
        ).getMappedResults();

        if (results == null || results.isEmpty()) return Collections.emptyList();

        return results.stream().map(this::mapDocumentToDto).toList();
    }

    private EventDetailsResponseSimplifiedDTO mapDocumentToDto(Document doc) {
        if (doc == null) return null;

        Document classroomDoc = doc.get("classroom", Document.class);
        Document profDoc = doc.get("professor", Document.class);
        Document courseDoc = doc.get("course", Document.class);
        Document disciplineDoc = doc.get("discipline", Document.class);

        SimplifiedEntityDTO classroom = (classroomDoc != null)
            ? new SimplifiedEntityDTO(classroomDoc.getString("id"), classroomDoc.getString("description"))
            : new SimplifiedEntityDTO(null, null);

        SimplifiedEntityDTO professor = (profDoc != null)
            ? new SimplifiedEntityDTO(profDoc.getString("id"), profDoc.getString("description"))
            : new SimplifiedEntityDTO(null, null);

        SimplifiedEntityDTO course = (courseDoc != null)
            ? new SimplifiedEntityDTO(courseDoc.getString("id"), courseDoc.getString("description"))
            : new SimplifiedEntityDTO(null, null);

        SimplifiedEntityDTO discipline = (disciplineDoc != null)
            ? new SimplifiedEntityDTO(disciplineDoc.getString("id"), disciplineDoc.getString("description"))
            : new SimplifiedEntityDTO(null, null);

        LocalTime startTime = doc.getDate("startTime").toInstant()
            .atZone(ZoneId.of("UTC"))
            .toLocalTime();

        LocalTime endTime = doc.getDate("endTime").toInstant()
            .atZone(ZoneId.of("UTC"))
            .toLocalTime();

        return new EventDetailsResponseSimplifiedDTO(
            doc.getString("id"),
            doc.getString("description"),
            Enum.valueOf(Weekday.class, doc.getString("weekday")),
            startTime,
            endTime,
            classroom,
            professor,
            course,
            discipline
        );
    }
}
