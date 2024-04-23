package com.p20241061.management.infrastructure.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.p20241061.management.api.mapping.CampusMapper;
import com.p20241061.management.api.model.request.create.CreateCampusRequest;
import com.p20241061.management.api.model.request.update.UpdateCampusRequest;
import com.p20241061.management.api.model.response.CampusResponse;
import com.p20241061.management.core.repositories.CampusRepository;
import com.p20241061.management.core.repositories.RestaurantRepository;
import com.p20241061.management.infrastructure.interfaces.ICampusService;
import com.p20241061.shared.exceptions.CustomException;
import com.p20241061.shared.models.enums.SuccessCode;
import com.p20241061.shared.models.response.GeneralResponse;
import com.p20241061.shared.utils.PaginatedRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class CampusService implements ICampusService {

    private final CampusRepository campusRepository;
    private final RestaurantRepository restaurantRepository;
    private final ObjectMapper objectMapper;
    private final CampusMapper campusMapper;

    @Override
    public Mono<GeneralResponse<CampusResponse>> getById(UUID campusId) {
        return campusRepository.findById(campusId)
                .switchIfEmpty(Mono.error(new CustomException(HttpStatus.BAD_REQUEST, "Campus with id " + campusId + " not found")))
                .flatMap(campus -> {
                    try {
                        return Mono.just(GeneralResponse.<CampusResponse>builder()
                                .code(SuccessCode.SUCCESS.name())
                                .data(campusMapper.modelToResponse(campus))
                                .build());
                    } catch (JsonProcessingException e) {
                        return Mono.error(new CustomException(HttpStatus.BAD_REQUEST, e.getMessage()));
                    }
                });
    }

    @Override
    public Mono<GeneralResponse<List<CampusResponse>>> getByRestaurantId(PaginatedRequest paginatedRequest, Boolean available, UUID restaurantId) {

        return restaurantRepository.findById(restaurantId)
                .switchIfEmpty(Mono.error(new CustomException(HttpStatus.BAD_REQUEST, "Restaurant with id " + restaurantId + " not found")))
                .flatMap(restaurant -> paginatedRequest.paginateData(campusRepository.findByRestaurantIdAndIsAvailable(restaurantId, available))
                        .collectList()
                        .flatMap(campuses -> {

                            List<CampusResponse> campusResponses = campuses.stream()
                                    .map(campus  -> {
                                        try {
                                            return campusMapper.modelToResponse(campus);
                                        } catch (JsonProcessingException e) {
                                            throw new RuntimeException(e);
                                        }
                                    })
                                    .toList();

                            return Mono.just(GeneralResponse.<List<CampusResponse>>builder()
                                    .code(SuccessCode.SUCCESS.name())
                                    .data(campusResponses)
                                    .build());
                        })
                );
    }

    @Override
    public Mono<GeneralResponse<CampusResponse>> create(CreateCampusRequest request) {

        return restaurantRepository.findById(request.getRestaurantId())
                .switchIfEmpty(Mono.error(new CustomException(HttpStatus.BAD_REQUEST, "Restaurant with id " + request.getRestaurantId() + " not found")))
                .flatMap(restaurant -> {
                    try {
                        return campusRepository.save(campusMapper.createRequestToModel(request)).flatMap(createdCampus -> {
                            try {
                                return Mono.just(GeneralResponse.<CampusResponse>builder()
                                        .code(SuccessCode.CREATED.name())
                                        .data(campusMapper.modelToResponse(createdCampus))
                                        .build());
                            } catch (JsonProcessingException e) {
                                return Mono.error(new CustomException(HttpStatus.BAD_REQUEST, e.getMessage()));
                            }
                        });
                    } catch (JsonProcessingException e) {
                        return Mono.error(new CustomException(HttpStatus.BAD_REQUEST, e.getMessage()));
                    }
                });
    }

    @Override
    public Mono<GeneralResponse<CampusResponse>> update(UpdateCampusRequest request, UUID campusId) {

        return campusRepository.findById(campusId)
                .switchIfEmpty(Mono.error(new CustomException(HttpStatus.BAD_REQUEST, "Campus with id " + campusId + " not found")))
                .flatMap(findedCampus -> restaurantRepository.findById(request.getRestaurantId())
                        .switchIfEmpty(Mono.error(new CustomException(HttpStatus.BAD_REQUEST, "Restaurant with id " + request.getRestaurantId() + " not found")))
                        .flatMap(restaurant -> {

                            try {
                                findedCampus.setName(request.getName());
                                findedCampus.setAddress(request.getAddress());
                                findedCampus.setPhoneNumber(request.getPhoneNumber());
                                findedCampus.setOpenHour(objectMapper.writeValueAsString(request.getOpenHour()));
                                findedCampus.setToTakeHome(request.getToTakeHome());
                                findedCampus.setToDelivery(request.getToDelivery());
                                findedCampus.setRestaurantId(request.getRestaurantId());
                                findedCampus.setIsAvailable(request.getIsAvailable());
                            } catch (JsonProcessingException e) {
                                return Mono.error(new CustomException(HttpStatus.BAD_REQUEST, e.getMessage()));
                            }

                            return campusRepository.save(findedCampus).flatMap(updatedCampus -> {
                                try {
                                    return Mono.just(GeneralResponse.<CampusResponse>builder()
                                            .code(SuccessCode.UPDATED.name())
                                            .data(campusMapper.modelToResponse(updatedCampus))
                                            .build());
                                } catch (JsonProcessingException e) {
                                    return Mono.error(new CustomException(HttpStatus.BAD_REQUEST, e.getMessage()));
                                }
                            });
                        }));

       }

    @Override
    public Mono<GeneralResponse<String>> delete(UUID campusId) {
        return campusRepository.findById(campusId)
                .switchIfEmpty(Mono.error(new CustomException(HttpStatus.BAD_REQUEST, "Campus with id " + campusId + " not found")))
                .flatMap(campus -> campusRepository.delete(campus)
                        .then(Mono.just(GeneralResponse.<String>builder()
                                .code(SuccessCode.DELETED.name())
                                .data("Campus with id " + campusId + " deleted successfully")
                                .build())));
    }
}
