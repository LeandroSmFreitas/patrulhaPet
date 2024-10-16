package br.com.manager.patrulhapet.facade.dto.user;

import br.com.manager.patrulhapet.domain.enumeration.RoleUser;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Builder
@Data
@AllArgsConstructor
@ToString
@NoArgsConstructor
public class UserRegisterDTO {

    private String username;

    @NotNull
    @ToString.Include
    private String password;

    @NotNull
    @ToString.Include
    private String email;

    @NotNull
    @ToString.Include
    private String imageUrl;

    @ToString.Include
    @Builder.Default
    private RoleUser role = RoleUser.ROLE_USER;
}
