package com.p20241061.management.infrastructure.services.restaurant;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.p20241061.management.api.mapping.CampusMapper;
import com.p20241061.management.api.model.request.restaurant.create.CreateCampusRequest;
import com.p20241061.management.api.model.request.restaurant.update.UpdateCampusRequest;
import com.p20241061.management.api.model.response.restaurant.CampusResponse;
import com.p20241061.management.core.entities.restaurant.Campus;
import com.p20241061.management.core.entities.restaurant.Restaurant;
import com.p20241061.management.core.repositories.restaurant.CampusRepository;
import com.p20241061.management.core.repositories.restaurant.RestaurantRepository;
import com.p20241061.management.infrastructure.interfaces.restaurant.ICampusService;
import com.p20241061.shared.exceptions.CustomException;
import com.p20241061.shared.models.enums.ErrorCode;
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

import static com.p20241061.shared.models.enums.CampusName.CAMPUS_ENTITY;
import static com.p20241061.shared.models.enums.CampusName.RESTAURANT_ENTITY;

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
                .switchIfEmpty(Mono.error(new CustomException(HttpStatus.NOT_FOUND, ErrorCode.NOT_FOUND.name(), CAMPUS_ENTITY)))
                .flatMap(campus -> Mono.zip(Mono.just(campus), restaurantRepository.findById(campus.getRestaurantId())))
                .flatMap(campus -> {
                    try {

                        CampusResponse response = campusMapper.modelToResponse(campus.getT1());
                        response.setRestaurant(campus.getT2());

                        return Mono.just(GeneralResponse.<CampusResponse>builder()
                                .code(SuccessCode.SUCCESS.name())
                                .data(response)
                                .build());
                    } catch (JsonProcessingException e) {
                        return Mono.error(new CustomException(HttpStatus.CONFLICT, ErrorCode.CONFLICT.name(), e.getMessage()));
                    }
                });
    }

    @Override
    public Mono<GeneralResponse<List<CampusResponse>>> getByRestaurantId(PaginatedRequest paginatedRequest, Boolean available, UUID restaurantId) {

        return restaurantRepository.findById(restaurantId)
                .switchIfEmpty(Mono.error(new CustomException(HttpStatus.NOT_FOUND, ErrorCode.NOT_FOUND.name(), RESTAURANT_ENTITY)))
                .flatMap(restaurant -> paginatedRequest.paginateData(campusRepository.findByRestaurantIdAndIsAvailable(restaurantId, available))
                        .flatMap(campuses -> Mono.zip(Mono.just(campuses), restaurantRepository.findById(campuses.getRestaurantId())))
                        .map(tuple -> {
                            Campus campus = tuple.getT1();
                            Restaurant fetchRestaurant = tuple.getT2();

                            try {
                                CampusResponse campusResponse = campusMapper.modelToResponse(campus);
                                campusResponse.setRestaurant(fetchRestaurant);
                                return campusResponse;
                            } catch (JsonProcessingException e) {
                                throw new RuntimeException(e);
                            }
                        }).collectList())
                .flatMap(campusResponse -> Mono.just(GeneralResponse.<List<CampusResponse>>builder()
                        .code(SuccessCode.SUCCESS.name())
                        .data(campusResponse)
                        .build()));
    }

    @Override
    public Mono<GeneralResponse<String>> create(CreateCampusRequest request) {

        return restaurantRepository.findById(request.getRestaurantId())
                .switchIfEmpty(Mono.error(new CustomException(HttpStatus.NOT_FOUND, ErrorCode.NOT_FOUND.name(), RESTAURANT_ENTITY)))
                .flatMap(restaurant -> {
                    try {
                        return campusRepository.save(campusMapper.createRequestToModel(request)).flatMap(createdCampus -> Mono.just(GeneralResponse.<String>builder()
                                .code(SuccessCode.CREATED.name())
                                .data(CAMPUS_ENTITY)
                                .build()));
                    } catch (JsonProcessingException e) {
                        return Mono.error(new CustomException(HttpStatus.CONFLICT, ErrorCode.CONFLICT.name(), e.getMessage()));
                    }
                });
    }

    @Override
    public Mono<GeneralResponse<String>> update(UpdateCampusRequest request, UUID campusId) {

        return campusRepository.findById(campusId)
                .switchIfEmpty(Mono.error(new CustomException(HttpStatus.NOT_FOUND, ErrorCode.NOT_FOUND.name(), CAMPUS_ENTITY)))
                .flatMap(findedCampus -> restaurantRepository.findById(request.getRestaurantId())
                        .switchIfEmpty(Mono.error(new CustomException(HttpStatus.NOT_FOUND, ErrorCode.NOT_FOUND.name(), RESTAURANT_ENTITY)))
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
                                return Mono.error(new CustomException(HttpStatus.CONFLICT, ErrorCode.CONFLICT.name(), e.getMessage()));
                            }

                            return campusRepository.save(findedCampus).flatMap(updatedCampus -> Mono.just(GeneralResponse.<String>builder()
                                    .code(SuccessCode.UPDATED.name())
                                    .data(CAMPUS_ENTITY)
                                    .build()));
                        }));

    }

    @Override
    public Mono<GeneralResponse<String>> delete(UUID campusId) {
        return campusRepository.findById(campusId)
                .switchIfEmpty(Mono.error(new CustomException(HttpStatus.NOT_FOUND, ErrorCode.NOT_FOUND.name(), CAMPUS_ENTITY)))
                .flatMap(campus -> campusRepository.delete(campus)
                        .then(Mono.just(GeneralResponse.<String>builder()
                                .code(SuccessCode.DELETED.name())
                                .data(CAMPUS_ENTITY)
                                .build())));
    }
}
