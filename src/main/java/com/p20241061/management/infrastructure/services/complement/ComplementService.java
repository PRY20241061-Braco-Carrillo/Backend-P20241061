package com.p20241061.management.infrastructure.services.complement;

import com.p20241061.management.api.mapping.ComplementMapper;
import com.p20241061.management.api.model.request.complement.create.CreateComplementRequest;
import com.p20241061.management.api.model.request.complement.update.UpdateComplementRequest;
import com.p20241061.management.api.model.response.complement.ComplementResponse;
import com.p20241061.management.core.repositories.complement.ComplementRepository;
import com.p20241061.management.infrastructure.interfaces.complement.IComplementService;
import com.p20241061.shared.exceptions.CustomException;
import com.p20241061.shared.models.enums.ErrorCode;
import com.p20241061.shared.models.enums.SuccessCode;
import com.p20241061.shared.models.response.GeneralResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

import static com.p20241061.shared.models.enums.CampusName.COMPLEMENT_ENTITY;

@Service
@Slf4j
@RequiredArgsConstructor
public class ComplementService implements IComplementService {

    private final ComplementRepository complementRepository;
    private final ComplementMapper complementMapper;

    @Override
    public Mono<GeneralResponse<List<ComplementResponse>>> getComplementsByCampusId(UUID campusId) {
        return complementRepository.getAllComplementsByCampusId(campusId)
                .collectList()
                .flatMap(complement -> Mono.just(GeneralResponse.<List<ComplementResponse>>builder()
                        .code(SuccessCode.SUCCESS.name())
                        .data(complementMapper.modelToListResponse(complement))
                        .build())
                );
    }

    @Override
    public Mono<GeneralResponse<String>> create(CreateComplementRequest request) {

        return complementRepository.save(complementMapper.createRequestToModel(request))
                .flatMap(createdComplement -> Mono.just(GeneralResponse.<String>builder()
                        .code(SuccessCode.CREATED.name())
                        .data(COMPLEMENT_ENTITY)
                        .build()));
    }

    @Override
    public Mono<GeneralResponse<String>> update(UpdateComplementRequest request, UUID complementId) {
        return complementRepository.findById(complementId)
                .switchIfEmpty(Mono.error(new CustomException(HttpStatus.NOT_FOUND, ErrorCode.NOT_FOUND.name(), COMPLEMENT_ENTITY)))
                .flatMap(complement -> {

                    complement.setName(request.getName());
                    complement.setIsSauce(request.getIsSauce());

                    return complementRepository.save(complement)
                            .flatMap(updatedComplement -> Mono.just(GeneralResponse.<String>builder()
                                    .code(SuccessCode.UPDATED.name())
                                    .data(COMPLEMENT_ENTITY)
                                    .build()));
                });

    }

    @Override
    public Mono<GeneralResponse<String>> delete(UUID complementId) {
        return complementRepository.findById(complementId)
                .switchIfEmpty(Mono.error(new CustomException(HttpStatus.NOT_FOUND, ErrorCode.NOT_FOUND.name(), COMPLEMENT_ENTITY)))
                .flatMap(complement -> complementRepository.delete(complement)
                        .then(Mono.just(GeneralResponse.<String>builder()
                                .code(SuccessCode.DELETED.name())
                                .data(COMPLEMENT_ENTITY)
                                .build())));
    }
}
