package pe.edu.vallegrande.as221s5t03be.model.dto;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TranslationResponseDto {

    private Long id;
    private String translator;
    private String toTranslator;
    private String translation;
    private String toTranslation;

}
