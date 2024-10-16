package br.com.manager.patrulhapet.facade.dto.user;

import lombok.*;

@Builder
@Data
@AllArgsConstructor
@ToString
@NoArgsConstructor
@Getter
@Setter
public class UserLoginResponseDTO {
    private String token;
    private String name;
}
