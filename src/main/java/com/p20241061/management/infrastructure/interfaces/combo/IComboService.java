package com.p20241061.management.infrastructure.interfaces.combo;

import com.p20241061.management.api.model.response.combo.ComboResponse;
import com.p20241061.management.api.model.response.combo.GetComboDetailResponse;
import com.p20241061.shared.models.response.GeneralResponse;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

public interface IComboService {

    Mono<GeneralResponse<List<ComboResponse>>> getAll(UUID campusId);
    Mono<GeneralResponse<GetComboDetailResponse>> getComboDetailById(UUID comboId);
}
