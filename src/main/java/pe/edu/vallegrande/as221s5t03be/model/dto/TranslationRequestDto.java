package pe.edu.vallegrande.as221s5t03be.model.dto;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TranslationRequestDto {

    private String translator;
    private String toTranslation;

}
