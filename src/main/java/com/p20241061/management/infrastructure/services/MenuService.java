package com.p20241061.management.infrastructure.services;

import com.p20241061.management.api.mapping.MenuMapper;
import com.p20241061.management.api.model.request.create.CreateMenuRequest;
import com.p20241061.management.api.model.request.update.UpdateMenuRequest;
import com.p20241061.management.api.model.response.MenuResponse;
import com.p20241061.management.core.entities.Menu;
import com.p20241061.management.core.repositories.MenuRepository;
import com.p20241061.management.infrastructure.interfaces.IMenuService;
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
public class MenuService implements IMenuService {

    private final MenuRepository menuRepository;
    private final MenuMapper menuMapper;

    @Override
    public Mono<GeneralResponse<MenuResponse>> create(CreateMenuRequest request) {

        return menuRepository.save(menuMapper.createRequestToModel(request)).flatMap(createdMenu -> Mono.just(GeneralResponse.<MenuResponse>builder()
                .code(SuccessCode.CREATED.name())
                .data(menuMapper.modelToResponse(createdMenu))
                .build()));
    }

    @Override
    public Mono<GeneralResponse<MenuResponse>> update(UpdateMenuRequest request, UUID menuId) {
        return menuRepository.findById(menuId)
                .switchIfEmpty(Mono.error(new CustomException(HttpStatus.BAD_REQUEST, "Menu with id " + menuId + " not found")))
                .flatMap(menu -> {
                    menu.setName(request.getName());
                    menu.setPrice(request.getPrice());
                    menu.setCookingTime(request.getCookingTime());
                    menu.setUrlImage(request.getUrlImage());

                    return menuRepository.save(menu).flatMap(updatedMenu -> Mono.just(GeneralResponse.<MenuResponse>builder()
                            .code(SuccessCode.UPDATED.name())
                            .data(menuMapper.modelToResponse(updatedMenu))
                            .build()));
                }
        );
    }

    @Override
    public Mono<GeneralResponse<String>> delete(UUID menuId) {
        return menuRepository.findById(menuId)
                .switchIfEmpty(Mono.error(new CustomException(HttpStatus.BAD_REQUEST, "Menu with id " + menuId + " not found")))
                .flatMap(menu -> menuRepository.delete(menu).then(Mono.just(GeneralResponse.<String>builder()
                        .code(SuccessCode.DELETED.name())
                        .data("Menu with id " + menuId + " deleted successfully")
                        .build()))
        );
    }
}
