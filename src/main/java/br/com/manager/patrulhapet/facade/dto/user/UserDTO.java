package br.com.manager.patrulhapet.facade.dto.user;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Builder
@Data
@AllArgsConstructor
@ToString
@NoArgsConstructor
public class UserDTO {

    @NotNull
    @ToString.Include
    private String email;

    @NotNull
    @ToString.Include
    private String username;

    @NotNull
    @ToString.Include
    private String imageUrl;
}
