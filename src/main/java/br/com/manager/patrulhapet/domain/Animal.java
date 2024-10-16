package br.com.manager.patrulhapet.domain;

import br.com.manager.patrulhapet.domain.enumeration.AnimalCategory;
import br.com.manager.patrulhapet.domain.enumeration.AnimalStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;

@Entity(name = "animals")
@Table(name = "animals")
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
@Builder
@Data
@AllArgsConstructor
@ToString
@NoArgsConstructor
public class Animal implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ToString.Include
    @EqualsAndHashCode.Include
    private Long id;

    @NotNull
    @ToString.Include
    @EqualsAndHashCode.Include
    @Column
    private String name;

    @NotNull
    @ToString.Include
    @EqualsAndHashCode.Include
    @Column
    private String description;

    @NotNull
    @ToString.Include
    @EqualsAndHashCode.Include
    @Column
    private String imageUrl;

    @NotNull
    @ToString.Include
    @EqualsAndHashCode.Include
    @Column
    @Enumerated(EnumType.STRING)
    private AnimalCategory category;

    @NotNull
    @ToString.Include
    @EqualsAndHashCode.Include
    @Column
    private LocalDate birthDate;

    @NotNull
    @ToString.Include
    @EqualsAndHashCode.Include
    @Column
    @Enumerated(EnumType.STRING)
    private AnimalStatus status;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
