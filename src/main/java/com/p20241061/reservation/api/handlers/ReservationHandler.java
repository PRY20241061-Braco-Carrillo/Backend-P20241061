package com.p20241061.reservation.api.handlers;

import com.p20241061.reservation.api.model.request.ChangeReservationStatusRequest;
import com.p20241061.reservation.api.model.request.CreateReservationRequest;
import com.p20241061.reservation.infrastructure.interfaces.IReservationService;
import com.p20241061.shared.validation.ObjectValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
@Slf4j
@RequiredArgsConstructor
public class ReservationHandler {
    private final IReservationService reservationService;
    private final ObjectValidator objectValidator;

    public Mono<ServerResponse> createReservation(ServerRequest request) {
        Mono<CreateReservationRequest> reservationRequest = request.bodyToMono(CreateReservationRequest.class)
                .doOnNext(objectValidator::validate);

        return reservationRequest
                .flatMap(res -> reservationService.createReservation(res)
                        .flatMap(response -> ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(response))
                );
    }

    public Mono<ServerResponse> getReservationByCampus(ServerRequest request) {
        UUID campusId = UUID.fromString(request.pathVariable("campusId"));

        return reservationService.getReservationByCampus(campusId)
                .flatMap(response -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(response));
    }

    public Mono<ServerResponse> getReservationDetail(ServerRequest request) {
        UUID reservationId = UUID.fromString(request.pathVariable("reservationId"));

        return reservationService.getReservationDetail(reservationId)
                .flatMap(response -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(response));
    }

    public Mono<ServerResponse> changeReservationStatus(ServerRequest request) {
        Mono<ChangeReservationStatusRequest> changeReservationStatusRequest = request.bodyToMono(ChangeReservationStatusRequest.class)
                .doOnNext(objectValidator::validate);

        return changeReservationStatusRequest
                .flatMap(res -> reservationService.changeReservationStatus(res)
                        .flatMap(response -> ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(response)));
    }


    public Mono<ServerResponse> deleteReservation(ServerRequest request) {
        UUID reservationId = UUID.fromString(request.pathVariable("reservationId"));
        return reservationService.deleteReservation(reservationId)
                .flatMap(response -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(response));
    }

}
