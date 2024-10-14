package br.com.manager.patrulhapet.facade.dto.animal;

import br.com.manager.patrulhapet.domain.enumeration.AnimalCategory;
import br.com.manager.patrulhapet.domain.enumeration.AnimalStatus;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.ZonedDateTime;

@Builder
@Data
@AllArgsConstructor
@ToString
@NoArgsConstructor
public class AnimalDTO {

    @ToString.Include
    private Long id;

    @NotNull
    @ToString.Include
    private String name;

    @NotNull
    @ToString.Include
    private String description;

    @NotNull
    @ToString.Include
    private String imageUrl;

    @NotNull
    @ToString.Include
    private AnimalCategory category;

    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @ToString.Include
    private LocalDate birthDate;

    @NotNull
    @ToString.Include
    @Builder.Default
    private AnimalStatus status = AnimalStatus.ENABLED;
}
