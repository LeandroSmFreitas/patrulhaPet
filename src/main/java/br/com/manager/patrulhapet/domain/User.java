package br.com.manager.patrulhapet.domain;

import br.com.manager.patrulhapet.domain.enumeration.RoleUser;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;

@Entity
@Table(name = "users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ToString.Include
    @EqualsAndHashCode.Include
    @Column
    private String username;

    @NotNull
    @ToString.Include
    @EqualsAndHashCode.Include
    @Column
    private String password;

    @NotNull
    @ToString.Include
    @EqualsAndHashCode.Include
    @Column
    private String email;

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
    private RoleUser role;


}
