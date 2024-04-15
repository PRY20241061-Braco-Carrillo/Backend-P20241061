package com.p20241061.security.infrastructure.services;

import com.p20241061.security.api.model.request.CreateUserRequest;
import com.p20241061.security.api.model.request.LoginRequest;
import com.p20241061.security.api.model.response.LoginResponse;
import com.p20241061.security.core.entities.User;
import com.p20241061.security.core.repositories.UserRepository;
import com.p20241061.security.infrastructure.interfaces.IUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService implements IUserService {


}
