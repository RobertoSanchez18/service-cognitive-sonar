package pe.edu.vallegrande.as221s5t03be.service.impl;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.stereotype.Service;
import pe.edu.vallegrande.as221s5t03be.config.TranslatorText;
import pe.edu.vallegrande.as221s5t03be.model.dto.TranslationRequestDto;
import pe.edu.vallegrande.as221s5t03be.model.dto.TranslationResponseDto;
import pe.edu.vallegrande.as221s5t03be.model.entity.Translation;
import pe.edu.vallegrande.as221s5t03be.repository.TranslationRepository;
import pe.edu.vallegrande.as221s5t03be.service.TranslationService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;

@Service
public class TranslationServiceImpl implements TranslationService {

    private final TranslationRepository translationRepository;
    private final TranslatorText translatorText;

    public TranslationServiceImpl(TranslationRepository translationRepository, TranslatorText translatorText) {
        this.translationRepository = translationRepository;
        this.translatorText = translatorText;
    }

    @Override
    public Flux<TranslationResponseDto> listarTodos() {
        return translationRepository.findAll()
                .map(this::mapResponse);
    }

    @Override
    public Flux<TranslationResponseDto> listarActivos() {
        return translationRepository.findByState(true)
                .map(this::mapResponse);
    }

    @Override
    public Flux<TranslationResponseDto> listarInactivos() {
        return translationRepository.findByState(false)
                .map(this::mapResponse);
    }

    @Override
    public Mono<TranslationResponseDto> buscarPorId(Long id) {
        return translationRepository.findById(id)
                .map(this::mapResponse);
    }

    @Override
    public Mono<TranslationResponseDto> insertar(TranslationRequestDto translationDto, String key, String location) {
        try {
            String translatedResponse = translatorText.translate(translationDto, key, location);
            JsonElement element = JsonParser.parseString(translatedResponse);
            JsonObject jsonObject = element.getAsJsonObject();
            String toTranslator = jsonObject.get("language").getAsString();
            String translatedText = jsonObject.get("text").getAsString();
            Translation translation1 = new Translation();
            translation1.setTranslator(translationDto.getTranslator());
            translation1.setToTranslator(toTranslator);
            translation1.setTranslation(translatedText);
            translation1.setToTranslation(translationDto.getToTranslation());
            return translationRepository.save(translation1)
                    .map(this::mapResponse);
        } catch (IOException e) {
            e.printStackTrace();
            return Mono.error(e);
        }
    }

    @Override
    public Mono<TranslationResponseDto> editar(Long id, TranslationRequestDto translationDto, String key, String location) {
        return translationRepository.findById(id)
                .flatMap(translation -> {
                    try {
                        String translatedResponse = translatorText.translate(translationDto, key, location);
                        JsonElement element = JsonParser.parseString(translatedResponse);
                        JsonObject jsonObject = element.getAsJsonObject();
                        String toTranslator = jsonObject.get("language").getAsString();
                        String translatedText = jsonObject.get("text").getAsString();
                        translation.setTranslator(translationDto.getTranslator());
                        translation.setToTranslator(toTranslator);
                        translation.setTranslation(translatedText);
                        translation.setToTranslation(translationDto.getToTranslation());

                        return translationRepository.save(translation)
                                .map(this::mapResponse);
                    } catch (IOException e) {
                        e.printStackTrace();
                        return Mono.error(e);
                    }
                });
    }

    @Override
    public Mono<Void> remover(Long id) {
        return translationRepository.removedById(id);
    }

    @Override
    public Mono<Void> restaurar(Long id) {
        return translationRepository.restoreById(id);
    }

    @Override
    public Mono<Void> eliminar(Long id) {
        return translationRepository.deleteById(id);
    }

    private TranslationResponseDto mapResponse(Translation translation) {
        return new TranslationResponseDto(
                translation.getId(),
                translation.getTranslator(),
                translation.getToTranslator(),
                translation.getTranslation(),
                translation.getToTranslation()
        );
    }

    private Translation mapRequest(TranslationRequestDto translationDto) {
        Translation translation = new Translation();
        translation.setTranslator(translationDto.getTranslator());
        translation.setToTranslation(translationDto.getToTranslation());
        return translation;
    }
}
