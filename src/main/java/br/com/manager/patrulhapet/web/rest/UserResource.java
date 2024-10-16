package br.com.manager.patrulhapet.web.rest;

import br.com.manager.patrulhapet.domain.User;
import br.com.manager.patrulhapet.facade.UserFacade;
import br.com.manager.patrulhapet.facade.dto.user.UserDTO;
import br.com.manager.patrulhapet.facade.dto.user.UserLoginDTO;
import br.com.manager.patrulhapet.facade.dto.user.UserLoginResponseDTO;
import br.com.manager.patrulhapet.facade.dto.user.UserRegisterDTO;
import br.com.manager.patrulhapet.repository.UserRepository;
import br.com.manager.patrulhapet.security.jwt.TokenProvider;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class UserResource {

    private final UserFacade userFacade;

    @PostMapping("/login")
    @Operation(description = "login the user")
    public ResponseEntity login(@Valid @RequestBody UserLoginDTO userLoginDTO) {
        UserLoginResponseDTO userLoginResponseDTO = userFacade.login(userLoginDTO);
        return ResponseEntity.ok(userLoginResponseDTO);
    }

    @PostMapping("/register")
    @Operation(description = "register new user")
    public ResponseEntity register(@Valid @RequestBody UserRegisterDTO userRegisterDTO) {
        UserLoginResponseDTO userLoginResponseDTO = userFacade.register(userRegisterDTO);
        return ResponseEntity.ok(userLoginResponseDTO);
    }

    @GetMapping("/me")
    @Operation(description = "get profile")
    public ResponseEntity loggedUser() {
        UserDTO userDTO = userFacade.getMe();
        return ResponseEntity.ok(userDTO);
    }
}
