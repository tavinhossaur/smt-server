package com.ifsp.tavinho.smt_backend.domain.entities;

import java.time.Instant;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import com.ifsp.tavinho.smt_backend.shared.ApplicationProperties;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@JsonPropertyOrder({ "id" })
public abstract class BaseEntity {

    @Id
    protected String id;

    @CreatedDate
    @Field(name = "created_at")
    @JsonFormat(pattern = ApplicationProperties.TIMESTAMP_PATTERN, timezone = ApplicationProperties.TIMEZONE)
    protected Instant createdAt;

    @LastModifiedDate
    @Field(name = "updated_at")
    @JsonFormat(pattern = ApplicationProperties.TIMESTAMP_PATTERN, timezone = ApplicationProperties.TIMEZONE)
    protected Instant updatedAt;

}
