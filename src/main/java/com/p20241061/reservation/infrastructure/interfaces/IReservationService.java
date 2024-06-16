package com.p20241061.reservation.infrastructure.interfaces;

import com.p20241061.reservation.api.model.request.ChangeReservationStatusRequest;
import com.p20241061.reservation.api.model.request.CreateReservationRequest;
import com.p20241061.reservation.api.model.response.GetReservationDetailResponse;
import com.p20241061.reservation.api.model.response.GetReservationResponseByCampus;
import com.p20241061.shared.models.response.GeneralResponse;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

public interface IReservationService {

    Mono<GeneralResponse<String>> createReservation(CreateReservationRequest createReservationRequest);
    Mono<GeneralResponse<List<GetReservationResponseByCampus>>> getReservationByCampus(UUID campusId);
    Mono<GeneralResponse<List<GetReservationResponseByCampus>>> getReservationByUser(UUID campusId);
    Mono<GeneralResponse<GetReservationDetailResponse>> getReservationDetail(UUID reservationId);
    Mono<GeneralResponse<String>> changeReservationStatus(ChangeReservationStatusRequest changeReservationStatusRequest);
    Mono<GeneralResponse<String>> deleteReservation(UUID reservationId);
}
