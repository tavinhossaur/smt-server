package com.ifsp.tavinho.smt_backend.domain.enums;

import com.fasterxml.jackson.annotation.JsonValue;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Status {
    SUCCESS("success"),
    ERROR("error");

    private final String value;

    @JsonValue
    public String getValue() {
        return value;
    }
}
