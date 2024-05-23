package pe.edu.vallegrande.as221s5t03be.repository;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import pe.edu.vallegrande.as221s5t03be.model.entity.Translation;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface TranslationRepository extends ReactiveCrudRepository<Translation, Long> {

    Flux<Translation> findByState(Boolean state);

    @Query("UPDATE translations SET state=false WHERE id=:id")
    Mono<Void> removedById(Long id);

    @Query("UPDATE translations SET state=true WHERE id=:id")
    Mono<Void> restoreById(Long id);

}
