package com.p20241061.security.infrastructure.interfaces;

import com.p20241061.security.api.model.request.CreateUserRequest;
import com.p20241061.security.api.model.request.LoginRequest;
import com.p20241061.security.api.model.response.LoginResponse;
import com.p20241061.security.core.entities.User;
import reactor.core.publisher.Mono;

public interface IUserService {

    Mono<LoginResponse> login(LoginRequest request);

    Mono<User> createUser(CreateUserRequest request);
}
