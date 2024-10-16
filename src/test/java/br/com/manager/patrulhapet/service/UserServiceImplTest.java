package br.com.manager.patrulhapet.service;

import br.com.manager.patrulhapet.domain.User;
import br.com.manager.patrulhapet.exceptions.RestNotFound;
import br.com.manager.patrulhapet.facade.dto.user.UserLoginResponseDTO;
import br.com.manager.patrulhapet.repository.UserRepository;
import br.com.manager.patrulhapet.security.jwt.TokenProvider;
import br.com.manager.patrulhapet.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private TokenProvider tokenProvider;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void makeLogin_ShouldReturnSuccess() {
        User user = User.builder()
                .email("test@example.com")
                .password("encodedPassword")
                .username("testUser")
                .build();

        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("password", user.getPassword())).thenReturn(true);
        when(tokenProvider.generateToken(user)).thenReturn("generatedToken");

        UserLoginResponseDTO response = userService.login("test@example.com", "password");

        assertNotNull(response);
        assertEquals("generatedToken", response.getToken());
        assertEquals("testUser", response.getName());
    }

    @Test
    void makeLogin_ShouldReturnUserNotFound() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        assertThrows(RestNotFound.class, () -> userService.login("test@example.com", "password"));
    }

    @Test
    void nakeLogin_ShouldReturnInvalidPassword() {
        User user = User.builder()
                .email("test@example.com")
                .password("encodedPassword")
                .build();

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(false);

        assertThrows(RestNotFound.class, () -> userService.login("test@example.com", "wrongPassword"));
    }

    @Test
    void makeRegister_ShouldReturnUserSuccess() {
        User newUser = User.builder()
                .email("new@example.com")
                .password("password")
                .username("newUser")
                .build();

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(tokenProvider.generateToken(any(User.class))).thenReturn("generatedToken");

        UserLoginResponseDTO response = userService.registerUser(newUser);

        assertNotNull(response);
        assertEquals("generatedToken", response.getToken());
        assertEquals("newUser", response.getName());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void makeRegister_ShouldReturnUserAlreadyExists() {
        User existingUser = User.builder()
                .email("existing@example.com")
                .build();

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(existingUser));

        assertThrows(RestNotFound.class, () -> userService.registerUser(existingUser));
    }

    @Test
    void getMe_ShouldReturnSuccess() {
        User user = User.builder().email("test@example.com").build();
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("test@example.com");
        when(authentication.isAuthenticated()).thenReturn(true);
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));

        SecurityContextHolder.setContext(securityContext);

        User result = userService.getMe();

        assertNotNull(result);
        assertEquals("test@example.com", result.getEmail());
    }

    @Test
    void getMe_ShouldReturnAuthenticationNotFound() {
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.isAuthenticated()).thenReturn(false);

        SecurityContextHolder.setContext(securityContext);

        assertThrows(RestNotFound.class, () -> userService.getMe());
    }
}
