package com.ifsp.tavinho.smt_backend.application.interactors.profile;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ifsp.tavinho.smt_backend.domain.entities.Favorite;
import com.ifsp.tavinho.smt_backend.domain.repositories.FavoriteRepository;
import com.ifsp.tavinho.smt_backend.infra.interfaces.UseCase;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ListFavoritesUseCase implements UseCase<String, List<Favorite>> {

    private final FavoriteRepository repository;

    @Override
    public List<Favorite> execute(String id) {
        return this.repository.findAllByUserId(id);
    }
}
