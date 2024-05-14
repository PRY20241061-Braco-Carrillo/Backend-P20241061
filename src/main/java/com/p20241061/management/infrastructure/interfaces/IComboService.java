package com.p20241061.management.infrastructure.interfaces;

import com.p20241061.management.api.model.response.ComboResponse;
import com.p20241061.management.api.model.response.get.GetComboDetailResponse;
import com.p20241061.shared.models.response.GeneralResponse;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

public interface IComboService {

    Mono<GeneralResponse<List<ComboResponse>>> getAll();
    Mono<GeneralResponse<GetComboDetailResponse>> getComboDetailById(UUID comboId);
}
