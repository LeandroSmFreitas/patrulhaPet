package br.com.manager.patrulhapet.web.rest;

import br.com.manager.patrulhapet.facade.dto.user.UserLoginDTO;
import br.com.manager.patrulhapet.facade.dto.user.UserLoginResponseDTO;
import br.com.manager.patrulhapet.facade.dto.user.UserRegisterDTO;
import br.com.manager.patrulhapet.facade.dto.user.UserDTO;
import br.com.manager.patrulhapet.facade.UserFacade;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class UserResourceTest {

    @InjectMocks
    private UserResource userResource;

    private MockMvc mockMvc;

    @Mock
    private UserFacade userFacade;

    private ObjectMapper objectMapper;

    private UserLoginResponseDTO userLoginResponseDTO;
    private UserDTO userDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        objectMapper = new ObjectMapper();

        mockMvc = MockMvcBuilders.standaloneSetup(userResource).build();

        userLoginResponseDTO = new UserLoginResponseDTO();
        userLoginResponseDTO.setToken("fake-jwt-token");
        userLoginResponseDTO.setName("fake-name");

        userDTO = new UserDTO();
        userDTO.setEmail("fake-email@email.com");
        userDTO.setUsername("John Doe");
        userDTO.setImageUrl("fake-image-url");
    }

    @Test
    void login_ShouldReturnUserLoginResponse() throws Exception {
        UserLoginDTO userLoginDTO = new UserLoginDTO();
        userLoginDTO.setEmail("test@example.com");
        userLoginDTO.setPassword("password");

        when(userFacade.login(ArgumentMatchers.any(UserLoginDTO.class))).thenReturn(userLoginResponseDTO);

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userLoginDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("fake-jwt-token"))
                .andExpect(jsonPath("$.name").value("fake-name"));

        verify(userFacade, times(1)).login(ArgumentMatchers.any(UserLoginDTO.class));
    }

    @Test
    void register_ShouldReturnUserLoginResponse() throws Exception {
        UserRegisterDTO userRegisterDTO = new UserRegisterDTO();
        userRegisterDTO.setEmail("newuser@example.com");
        userRegisterDTO.setPassword("newpassword");
        userRegisterDTO.setUsername("fake-name");
        userRegisterDTO.setImageUrl("fake-url");

        when(userFacade.register(ArgumentMatchers.any(UserRegisterDTO.class))).thenReturn(userLoginResponseDTO);

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRegisterDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("fake-jwt-token"))
                .andExpect(jsonPath("$.name").value("fake-name"));

        verify(userFacade, times(1)).register(ArgumentMatchers.any(UserRegisterDTO.class));
    }

    @Test
    void loggedUser_ShouldReturnUserDTO() throws Exception {
        when(userFacade.getMe()).thenReturn(userDTO);

        mockMvc.perform(get("/api/auth/me")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("John Doe"));

        verify(userFacade, times(1)).getMe();
    }
}