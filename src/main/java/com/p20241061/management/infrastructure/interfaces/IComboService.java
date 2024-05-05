package com.p20241061.management.infrastructure.interfaces;

import com.p20241061.management.api.model.response.ComboResponse;
import com.p20241061.shared.models.response.GeneralResponse;
import reactor.core.publisher.Mono;

public interface IComboService {

    Mono<GeneralResponse<ComboResponse>> getAll();
}
