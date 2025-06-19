package com.ifsp.tavinho.smt_backend.shared.responses;

import com.ifsp.tavinho.smt_backend.domain.enums.Status;

import lombok.Getter;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import com.fasterxml.jackson.annotation.JsonInclude;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ServerApiResponse<T> {

    private Status status;
    private String error;
    private String message;
    private T data;
    
}