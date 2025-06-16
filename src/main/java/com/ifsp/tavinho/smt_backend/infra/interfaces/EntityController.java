package com.ifsp.tavinho.smt_backend.infra.interfaces;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.ifsp.tavinho.smt_backend.shared.responses.ApiResponse;

import static com.ifsp.tavinho.smt_backend.infra.routes.Routes.BY_ID;

public interface EntityController<I, O> {

    @PostMapping
    public ResponseEntity<O> create(@RequestBody I input);

    @PutMapping(BY_ID)
    public ResponseEntity<O> update(@RequestBody I input, @PathVariable String id);

    @DeleteMapping(BY_ID)
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable String id);

    @GetMapping(BY_ID)
    public ResponseEntity<O> find(@PathVariable String id);

    @GetMapping
    public ResponseEntity<List<O>> list();

}
