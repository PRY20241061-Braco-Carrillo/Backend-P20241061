package com.p20241061.security.infrastructure.services;

import com.p20241061.security.api.config.JwtProvider;
import com.p20241061.security.api.model.request.CreateUserRequest;
import com.p20241061.security.api.model.request.LoginRequest;
import com.p20241061.security.api.model.response.CreateUserResponse;
import com.p20241061.security.api.model.response.LoginResponse;
import com.p20241061.security.core.entities.User;
import com.p20241061.security.core.enums.Role;
import com.p20241061.security.core.repositories.UserRepository;
import com.p20241061.security.infrastructure.interfaces.IUserService;
import com.p20241061.shared.exceptions.CustomException;
import com.p20241061.shared.models.enums.ErrorCode;
import com.p20241061.shared.models.enums.SuccessCode;
import com.p20241061.shared.models.response.GeneralResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Mono<GeneralResponse<LoginResponse>> login(LoginRequest request) {
        return userRepository.findByEmail(request.getEmail())
                .filter(user -> passwordEncoder.matches(request.getPassword(), user.getPassword()))
                .flatMap(user -> {
                    String token = jwtProvider.generateToken(user);

                    LoginResponse loginResponse = LoginResponse.builder()
                            .userId(user.getUserId())
                            .roles(user.getRoles())
                            .cancelReservation(user.getCancelReservation())
                            .acceptReservation(user.getAcceptReservation())
                            .token(token)
                            .build();

                    return Mono.just(GeneralResponse.<LoginResponse>builder()
                            .code(SuccessCode.SUCCESS.name())
                            .data(loginResponse)
                            .build());
                })
                .switchIfEmpty(Mono.error(new CustomException(HttpStatus.UNAUTHORIZED, "Invalid credentials")));
    }

    @Override
    public Mono<GeneralResponse<CreateUserResponse>> createUser(CreateUserRequest request) {

        User user = User.builder()
                .names(request.getNames())
                .lastNames(request.getLastNames())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .cancelReservation(0)
                .acceptReservation(0)
                .build();

        switch (request.getRole().toUpperCase()) {
            case "ADMIN":
                user.setRoles(Role.ROLE_ADMIN.name());
                break;
            case "CHEF":
                user.setRoles(Role.ROLE_CHEF.name());
                break;
            case "CLIENT":
                user.setRoles(Role.ROLE_CLIENT.name());
                break;
            case "WAITER":
                user.setRoles(Role.ROLE_WAITER.name());
                break;
            default:
                return Mono.error(new CustomException(HttpStatus.BAD_REQUEST, "Role " + request.getRole() + " is not valid"));
        }

        Mono<Boolean> userExists = userRepository.findByEmail(user.getEmail()).hasElement();

        return userExists.flatMap(exists -> exists
                ? Mono.error(new CustomException(HttpStatus.BAD_REQUEST, "User with email " + request.getEmail()+ " already exists"))
                :  userRepository.save(user).flatMap(
                        u -> {

                            CreateUserResponse response = CreateUserResponse.builder()
                                    .userId(u.getUserId())
                                    .names(u.getNames())
                                    .lastNames(u.getLastNames())
                                    .email(u.getEmail())
                                    .roles(u.getRoles())
                                    .cancelReservation(u.getCancelReservation())
                                    .acceptReservation(u.getAcceptReservation())
                                    .build();

                            return Mono.just( GeneralResponse.<CreateUserResponse>builder()
                                            .code(SuccessCode.CREATED.name())
                                            .data(response)
                                    .build());
                        }));
    }
}
