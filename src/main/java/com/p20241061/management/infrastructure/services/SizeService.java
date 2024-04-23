package com.p20241061.management.infrastructure.services;

import com.p20241061.management.api.mapping.SizeMapper;
import com.p20241061.management.api.model.request.create.CreateSizeRequest;
import com.p20241061.management.api.model.request.update.UpdateSizeRequest;
import com.p20241061.management.api.model.response.SizeResponse;
import com.p20241061.management.core.repositories.SizeRepository;
import com.p20241061.management.infrastructure.interfaces.ISizeService;
import com.p20241061.shared.exceptions.CustomException;
import com.p20241061.shared.models.enums.SuccessCode;
import com.p20241061.shared.models.response.GeneralResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class SizeService implements ISizeService {

    private final SizeRepository sizeRepository;
    private final SizeMapper sizeMapper;

    @Override
    public Mono<GeneralResponse<SizeResponse>> create(CreateSizeRequest request) {
        return sizeRepository.save(sizeMapper.createRequestToModel(request))
                .flatMap(createdSize -> Mono.just(GeneralResponse.<SizeResponse>builder()
                        .code(SuccessCode.CREATED.name())
                        .data(sizeMapper.modelToResponse(createdSize))
                        .build()));
    }

    @Override
    public Mono<GeneralResponse<SizeResponse>> update(UpdateSizeRequest request, UUID sizeId) {
        return sizeRepository.findById(sizeId)
                .switchIfEmpty(Mono.error(new CustomException(HttpStatus.BAD_REQUEST, "Size with id " + sizeId + " not found")))
                .flatMap(size -> {

                    size.setName(request.getName());
                    size.setIsAvailable(request.getIsAvailable());

                    return sizeRepository.save(size)
                            .flatMap(updatedSize -> Mono.just(GeneralResponse.<SizeResponse>builder()
                                    .code(SuccessCode.UPDATED.name())
                                    .data(sizeMapper.modelToResponse(updatedSize))
                                    .build()));
                });
    }

    @Override
    public Mono<GeneralResponse<String>> delete(UUID sizeId) {
        return sizeRepository.findById(sizeId)
                .switchIfEmpty(Mono.error(new CustomException(HttpStatus.BAD_REQUEST, "Size with id " + sizeId + " not found")))
                .flatMap(size -> sizeRepository.delete(size)
                        .then(Mono.just(GeneralResponse.<String>builder()
                                .code(SuccessCode.DELETED.name())
                                .data("Size with id " + sizeId + " deleted successfully")
                                .build())));
    }
}
