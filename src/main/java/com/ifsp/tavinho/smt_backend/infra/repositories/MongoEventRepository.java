package com.ifsp.tavinho.smt_backend.infra.repositories;

import com.ifsp.tavinho.smt_backend.domain.entities.Event;
import com.ifsp.tavinho.smt_backend.domain.repositories.EventRepository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MongoEventRepository extends MongoRepository<Event, String>, EventRepository { } 