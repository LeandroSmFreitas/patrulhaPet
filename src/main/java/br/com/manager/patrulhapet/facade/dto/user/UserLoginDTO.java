package br.com.manager.patrulhapet.facade.dto.user;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Builder
@Data
@AllArgsConstructor
@ToString
@NoArgsConstructor
public class UserLoginDTO {

    @NotNull
    @ToString.Include
    private String email;

    @NotNull
    @ToString.Include
    private String password;
}

