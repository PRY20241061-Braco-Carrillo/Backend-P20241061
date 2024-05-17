package com.p20241061.management.infrastructure.interfaces.complement;

import com.p20241061.management.api.model.request.complement.create.CreateComplementRequest;
import com.p20241061.management.api.model.request.complement.update.UpdateComplementRequest;
import com.p20241061.management.api.model.response.complement.ComplementResponse;
import com.p20241061.shared.models.response.GeneralResponse;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

public interface IComplementService {
    Mono<GeneralResponse<List<ComplementResponse>>> getComplementsByCampusId(UUID campusId);
    Mono<GeneralResponse<String>> create(CreateComplementRequest request);
    Mono<GeneralResponse<String>> update(UpdateComplementRequest request, UUID complementId);
    Mono<GeneralResponse<String>> delete(UUID complementId);
}
