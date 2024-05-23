package pe.edu.vallegrande.as221s5t03be.service;

import pe.edu.vallegrande.as221s5t03be.model.dto.TranslationRequestDto;
import pe.edu.vallegrande.as221s5t03be.model.dto.TranslationResponseDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TranslationService {

    Flux<TranslationResponseDto> listarTodos();

    Flux<TranslationResponseDto> listarActivos();

    Flux<TranslationResponseDto> listarInactivos();

    Mono<TranslationResponseDto> buscarPorId(Long id);

    Mono<TranslationResponseDto> insertar(TranslationRequestDto translation, String key, String location);

    Mono<TranslationResponseDto> editar(Long id, TranslationRequestDto translation, String key, String location);

    Mono<Void> remover(Long id);

    Mono<Void> restaurar(Long id);

    Mono<Void> eliminar(Long id);
}
