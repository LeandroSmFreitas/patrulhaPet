package br.com.manager.patrulhapet.facade;

import br.com.manager.patrulhapet.domain.User;
import br.com.manager.patrulhapet.facade.dto.user.UserDTO;
import br.com.manager.patrulhapet.facade.dto.user.UserLoginDTO;
import br.com.manager.patrulhapet.facade.dto.user.UserLoginResponseDTO;
import br.com.manager.patrulhapet.facade.dto.user.UserRegisterDTO;
import br.com.manager.patrulhapet.facade.mapper.UserMapper;
import br.com.manager.patrulhapet.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class UserFacadeTest {

    @Mock
    private UserService userService;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserFacade userFacade;

    private UserLoginDTO userLoginDTO;
    private UserRegisterDTO userRegisterDTO;
    private UserLoginResponseDTO userLoginResponseDTO;
    private UserDTO userDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        userLoginDTO = new UserLoginDTO();
        userLoginDTO.setEmail("test@example.com");
        userLoginDTO.setPassword("password");

        userRegisterDTO = new UserRegisterDTO();
        userRegisterDTO.setEmail("test@example.com");
        userRegisterDTO.setPassword("password");
        userRegisterDTO.setUsername("John Doe");

        userLoginResponseDTO = new UserLoginResponseDTO();
        userLoginResponseDTO.setToken("fake-jwt-token");

        userDTO = new UserDTO();
        userDTO.setUsername("John Doe");
        userDTO.setEmail("test@example.com");
    }

    @Test
    void login_ShouldReturnUserLoginResponseDTO() {
        when(userService.login(userLoginDTO.getEmail(), userLoginDTO.getPassword())).thenReturn(userLoginResponseDTO);

        UserLoginResponseDTO response = userFacade.login(userLoginDTO);

        assertEquals("fake-jwt-token", response.getToken());
        verify(userService, times(1)).login(userLoginDTO.getEmail(), userLoginDTO.getPassword());
    }

    @Test
    void register_ShouldReturnUserLoginResponseDTO() {
        when(userMapper.toEntity(userRegisterDTO)).thenReturn(new User());
        when(userService.registerUser(any())).thenReturn(userLoginResponseDTO);

        UserLoginResponseDTO response = userFacade.register(userRegisterDTO);

        assertEquals("fake-jwt-token", response.getToken());
        verify(userService, times(1)).registerUser(any());
        verify(userMapper, times(1)).toEntity(userRegisterDTO);
    }

    @Test
    void getMe_ShouldReturnUserDTO() {
        when(userService.getMe()).thenReturn(new User());
        when(userMapper.toDto(any(User.class))).thenReturn(userDTO);

        UserDTO response = userFacade.getMe();

        assertEquals("John Doe", response.getUsername());
        assertEquals("test@example.com", response.getEmail());
        verify(userService, times(1)).getMe();
        verify(userMapper, times(1)).toDto(any(User.class));
    }
}