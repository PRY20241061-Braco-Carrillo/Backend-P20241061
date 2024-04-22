package com.p20241061.management.infrastructure.services;

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

    @Override
    public Mono<GeneralResponse<MenuResponse>> create(CreateMenuRequest request) {

        Menu menu = Menu.builder()
                .name(request.getName())
                .price(request.getPrice())
                .cookingTime(request.getCookingTime())
                .urlImage(request.getUrlImage())
                .build();

        return menuRepository.save(menu).flatMap(createdMenu -> {
            MenuResponse menuResponse = MenuResponse.builder()
                    .menuId(createdMenu.getMenuId())
                    .name(createdMenu.getName())
                    .price(createdMenu.getPrice())
                    .cookingTime(createdMenu.getCookingTime())
                    .urlImage(createdMenu.getUrlImage())
                    .build();

            return Mono.just(GeneralResponse.<MenuResponse>builder()
                    .code(SuccessCode.CREATED.name())
                    .data(menuResponse)
                    .build());
        });
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

                    return menuRepository.save(menu).flatMap(updatedMenu -> {
                        MenuResponse menuResponse = MenuResponse.builder()
                                .menuId(updatedMenu.getMenuId())
                                .name(updatedMenu.getName())
                                .price(updatedMenu.getPrice())
                                .cookingTime(updatedMenu.getCookingTime())
                                .urlImage(updatedMenu.getUrlImage())
                                .build();

                        return Mono.just(GeneralResponse.<MenuResponse>builder()
                                .code(SuccessCode.UPDATED.name())
                                .data(menuResponse)
                                .build());
                    });
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
