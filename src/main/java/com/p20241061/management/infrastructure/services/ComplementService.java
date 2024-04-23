package com.p20241061.management.infrastructure.services;

import com.p20241061.management.api.mapping.ComplementMapper;
import com.p20241061.management.api.model.request.create.CreateComplementRequest;
import com.p20241061.management.api.model.request.update.UpdateComplementRequest;
import com.p20241061.management.api.model.response.ComplementResponse;
import com.p20241061.management.core.entities.Complement;
import com.p20241061.management.core.repositories.ComplementRepository;
import com.p20241061.management.infrastructure.interfaces.IComplementService;
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
public class ComplementService implements IComplementService {

    private final ComplementRepository complementRepository;
    private final ComplementMapper complementMapper;

    @Override
    public Mono<GeneralResponse<ComplementResponse>> create(CreateComplementRequest request) {

        return complementRepository.save(complementMapper.createRequestToModel(request))
                .flatMap(createdComplement -> Mono.just(GeneralResponse.<ComplementResponse>builder()
                        .code(SuccessCode.CREATED.name())
                        .data(complementMapper.modelToResponse(createdComplement))
                        .build()));
    }

    @Override
    public Mono<GeneralResponse<ComplementResponse>> update(UpdateComplementRequest request, UUID complementId) {
        return complementRepository.findById(complementId)
                .switchIfEmpty(Mono.error(new CustomException(HttpStatus.BAD_REQUEST, "Complement with id " + complementId + " not found")))
                .flatMap(complement -> {

                    complement.setName(request.getName());
                    complement.setPrice(request.getPrice());
                    complement.setIsSauce(request.getIsSauce());
                    complement.setIsAvailable(request.getIsAvailable());

                    return complementRepository.save(complement)
                            .flatMap(updatedComplement -> Mono.just(GeneralResponse.<ComplementResponse>builder()
                                    .code(SuccessCode.UPDATED.name())
                                    .data(complementMapper.modelToResponse(updatedComplement))
                                    .build()));
                });

    }

    @Override
    public Mono<GeneralResponse<String>> delete(UUID complementId) {
        return complementRepository.findById(complementId)
                .switchIfEmpty(Mono.error(new CustomException(HttpStatus.BAD_REQUEST, "Complement with id " + complementId + " not found")))
                .flatMap(complement -> complementRepository.delete(complement)
                        .then(Mono.just(GeneralResponse.<String>builder()
                                .code(SuccessCode.DELETED.name())
                                .data("Complement with id " + complementId + " deleted successfully")
                                .build())));
    }
}
