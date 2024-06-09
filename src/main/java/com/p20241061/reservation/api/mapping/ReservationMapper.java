package com.p20241061.reservation.api.mapping;

import com.p20241061.order.api.model.response.CreateOrderRequestResponse;
import com.p20241061.reservation.api.model.request.CreateReservationRequest;
import com.p20241061.reservation.api.model.response.GetReservationResponseByCampus;
import com.p20241061.reservation.core.entities.Reservation;
import com.p20241061.security.core.entities.User;
import com.p20241061.shared.models.enums.ReservationStatus;
import com.p20241061.shared.models.enums.UserReservationQualification;
import com.p20241061.shared.utils.EnhancedModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


public class ReservationMapper {
    @Autowired
    EnhancedModelMapper mapper;

    public Reservation createRequestToModel(CreateReservationRequest request, CreateOrderRequestResponse response, User user) {
        Reservation reservation = mapper.map(request, Reservation.class);
        reservation.setReservationStatus(ReservationStatus.POR_CONFIRMAR.name());
        reservation.setOrderRequestId(response.getOrderRequestId());
        reservation.setMessage(null);
        reservation.setUserQualification(userQualification(user));

        return reservation;
    }

    public List<GetReservationResponseByCampus> modelListToResponseList(List<Reservation> modelList){
        return mapper.mapList(modelList, GetReservationResponseByCampus.class);
    }

    private String userQualification(User user) {

        if(user.getAcceptReservation() == 0) return UserReservationQualification.REGULAR.name();

        double percentageQualification = (user.getCancelReservation() * 100) / (double)user.getAcceptReservation();

        if(percentageQualification < 30) return UserReservationQualification.BUENO.name();
        else if(percentageQualification < 70) return UserReservationQualification.REGULAR.name();
        else return UserReservationQualification.MALO.name();

    }
}
