package br.com.manager.patrulhapet.service;


import br.com.manager.patrulhapet.domain.User;
import br.com.manager.patrulhapet.facade.dto.user.UserLoginResponseDTO;

import java.util.Optional;

public interface UserService {

    UserLoginResponseDTO login(String email, String password);

    Optional<User> findUser(String email);

    UserLoginResponseDTO registerUser(User user);

    User getMe();
}
