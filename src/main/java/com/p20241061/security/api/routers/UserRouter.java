package com.p20241061.security.api.routers;

import com.p20241061.security.api.handlers.UserHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
@Slf4j
public class UserRouter {

    private static final String PATH_USER = "/api/user";

    @Bean
    RouterFunction<ServerResponse> userRtr(UserHandler handler) {
        return RouterFunctions.route()
                .POST(PATH_USER + "/auth/login", handler::login)
                .POST(PATH_USER + "/auth/register", handler::create)
                .build();
    }
}
