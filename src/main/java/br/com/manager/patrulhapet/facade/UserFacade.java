package br.com.manager.patrulhapet.facade;

import br.com.manager.patrulhapet.facade.dto.user.UserDTO;
import br.com.manager.patrulhapet.facade.dto.user.UserLoginDTO;
import br.com.manager.patrulhapet.facade.dto.user.UserLoginResponseDTO;
import br.com.manager.patrulhapet.facade.dto.user.UserRegisterDTO;
import br.com.manager.patrulhapet.facade.mapper.UserMapper;
import br.com.manager.patrulhapet.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserFacade {

    private final UserService service;
    private final UserMapper mapper;

    public UserFacade(UserService service, UserMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }


    @Transactional
    public UserLoginResponseDTO login(UserLoginDTO loginDTO) {
        UserLoginResponseDTO userLoginResponseDTO = service.login(loginDTO.getEmail(), loginDTO.getPassword());
        return userLoginResponseDTO;
    }

    @Transactional
    public UserLoginResponseDTO register(UserRegisterDTO userRegisterDTO) {
        UserLoginResponseDTO userLoginResponseDTO = service.registerUser(mapper.toEntity(userRegisterDTO));
        return userLoginResponseDTO;
    }

    @Transactional
    public UserDTO getMe() {
        UserDTO userDTO = mapper.toDto(service.getMe());
        return userDTO;
    }
}
