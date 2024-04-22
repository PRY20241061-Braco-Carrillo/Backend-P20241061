package com.p20241061.management.infrastructure.interfaces;

import com.p20241061.management.api.model.request.create.CreateSizeRequest;
import com.p20241061.management.api.model.request.update.UpdateSizeRequest;
import com.p20241061.management.api.model.response.SizeResponse;
import com.p20241061.shared.models.response.GeneralResponse;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface ISizeService {
    Mono<GeneralResponse<SizeResponse>> create(CreateSizeRequest request);
    Mono<GeneralResponse<SizeResponse>> update(UpdateSizeRequest request, UUID sizeId);
    Mono<GeneralResponse<String>> delete(UUID sizeId);
}
