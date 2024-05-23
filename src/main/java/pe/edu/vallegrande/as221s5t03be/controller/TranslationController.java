package pe.edu.vallegrande.as221s5t03be.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.vallegrande.as221s5t03be.model.dto.TranslationRequestDto;
import pe.edu.vallegrande.as221s5t03be.model.dto.TranslationResponseDto;
import pe.edu.vallegrande.as221s5t03be.service.TranslationService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/translations")
public class TranslationController {

    private final TranslationService translationService;

    public TranslationController(TranslationService translationService) {
        this.translationService = translationService;
    }

    @GetMapping
    public Flux<TranslationResponseDto> listarTodos() {
        return translationService.listarTodos();
    }

    @GetMapping("/activos")
    public Flux<TranslationResponseDto> listarActivos() {
        return translationService.listarActivos();
    }

    @GetMapping("/inactivos")
    public Flux<TranslationResponseDto> listarInactivos() {
        return translationService.listarInactivos();
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<TranslationResponseDto>> buscarPorId(@PathVariable Long id) {
        return translationService.buscarPorId(id)
                .map(translation -> ResponseEntity.ok(translation))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<ResponseEntity<TranslationResponseDto>> insertar(@RequestBody TranslationRequestDto translationDto, @RequestHeader("Ocp-Apim-Subscription-Key") String key, @RequestHeader("Ocp-Apim-Subscription-Region") String location) {
        return translationService.insertar(translationDto, key, location)
                .map(translation -> ResponseEntity.status(HttpStatus.CREATED).body(translation));
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<TranslationResponseDto>> editar(@PathVariable Long id, @RequestBody TranslationRequestDto translationDto, @RequestHeader("Ocp-Apim-Subscription-Key") String key, @RequestHeader("Ocp-Apim-Subscription-Region") String location) {
        return translationService.editar(id, translationDto, key, location)
                .map(translation -> ResponseEntity.ok(translation))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public Mono<Void> eliminar(@PathVariable Long id) {
        return translationService.eliminar(id);
    }

    @PutMapping("/remover/{id}")
    public Mono<Void> remover(@PathVariable Long id) {
        return translationService.remover(id);
    }

    @PutMapping("/restaurar/{id}")
    public Mono<Void> restaurar(@PathVariable Long id) {
        return translationService.restaurar(id);
    }
}