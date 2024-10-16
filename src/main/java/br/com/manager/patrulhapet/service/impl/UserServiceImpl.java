package br.com.manager.patrulhapet.service.impl;

import br.com.manager.patrulhapet.domain.User;
import br.com.manager.patrulhapet.exceptions.RestNotFound;
import br.com.manager.patrulhapet.facade.dto.user.UserLoginResponseDTO;
import br.com.manager.patrulhapet.repository.UserRepository;
import br.com.manager.patrulhapet.security.jwt.TokenProvider;
import br.com.manager.patrulhapet.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, TokenProvider tokenProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
    }

    @Override
    public UserLoginResponseDTO login(String email, String password) {
        User user = this.findUser(email).orElseThrow(() -> new RestNotFound("User not found"));
        if(!passwordEncoder.matches(password, user.getPassword())) {
            throw new RestNotFound("User not found");
        }
        String token = tokenProvider.generateToken(user);
        return new UserLoginResponseDTO(token, user.getUsername());
    }

    @Override
    public Optional<User> findUser(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        return user;
    }

    @Override
    public UserLoginResponseDTO registerUser(User newUser) {
        Optional<User> user = this.findUser(newUser.getEmail());

        if(user.isPresent()) {
            throw new RestNotFound("User already exists");
        }

        User createUser = User.builder()
                .email(newUser.getEmail())
                .password(passwordEncoder.encode(newUser.getPassword()))
                .username(newUser.getUsername())
                .imageUrl(newUser.getImageUrl())
                .role(newUser.getRole())
                .build();
        this.userRepository.save(createUser);
        String token = tokenProvider.generateToken(createUser);
        return new UserLoginResponseDTO(token, newUser.getUsername());
    }

    @Override
    public User getMe() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication == null || !authentication.isAuthenticated()) {
            throw new RestNotFound("Authentication not found");
        }

        return this.findUser(authentication.getName()).get();
    }
}
