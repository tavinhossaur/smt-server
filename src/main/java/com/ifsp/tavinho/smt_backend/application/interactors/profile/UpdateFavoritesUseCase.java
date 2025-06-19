package com.ifsp.tavinho.smt_backend.application.interactors.profile;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ifsp.tavinho.smt_backend.domain.entities.Favorite;
import com.ifsp.tavinho.smt_backend.domain.repositories.FavoriteRepository;
import com.ifsp.tavinho.smt_backend.infra.interfaces.UseCase;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UpdateFavoritesUseCase implements UseCase<String, Boolean> {

    private final FavoriteRepository favoriteRepository;

    @Override
    public Boolean execute(String professorId, String userId) {
        Optional<Favorite> favorite = this.favoriteRepository.findByUserIdAndProfessorId(userId, professorId);

        if (favorite.isPresent()) this.favoriteRepository.delete(favorite.get());
        else this.favoriteRepository.save(new Favorite(userId, professorId));

        return true;
    }

    @Override
    public Boolean execute(String _unused) {
        throw new UnsupportedOperationException("User ID is required for updating the favorites.");
    }
    
}
