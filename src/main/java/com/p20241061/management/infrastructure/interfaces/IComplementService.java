package com.p20241061.management.infrastructure.interfaces;

import com.p20241061.management.api.model.request.create.CreateComplementRequest;
import com.p20241061.management.api.model.request.update.UpdateComplementRequest;
import com.p20241061.management.api.model.response.ComplementResponse;
import com.p20241061.shared.models.response.GeneralResponse;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface IComplementService {
    Mono<GeneralResponse<ComplementResponse>> create(CreateComplementRequest request);
    Mono<GeneralResponse<ComplementResponse>> update(UpdateComplementRequest request, UUID complementId);
    Mono<GeneralResponse<String>> delete(UUID complementId);
}
