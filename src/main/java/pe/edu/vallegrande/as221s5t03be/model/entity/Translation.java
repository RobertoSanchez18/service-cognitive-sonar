package pe.edu.vallegrande.as221s5t03be.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "translations")
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class Translation {

    @Id
    private Long id;

    private String translator;

    @Column("to_translator")
    private String toTranslator;

    private String translation;

    @Column("to_translation")
    private String toTranslation;

    private Boolean state=true;

}
