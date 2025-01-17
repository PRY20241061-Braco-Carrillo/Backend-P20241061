package com.p20241061.management.infrastructure.services.menu;

import com.p20241061.management.api.mapping.MenuMapper;
import com.p20241061.management.api.model.request.menu.create.CreateMenuRequest;
import com.p20241061.management.api.model.request.menu.update.UpdateMenuRequest;
import com.p20241061.management.api.model.response.menu.GetMenuDetailsResponse;
import com.p20241061.management.api.model.response.menu.MenuResponse;
import com.p20241061.management.core.repositories.menu.MenuRepository;
import com.p20241061.management.infrastructure.interfaces.menu.IMenuService;
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

import static com.p20241061.shared.models.enums.CampusName.MENU_ENTITY;

@Service
@Slf4j
@RequiredArgsConstructor
public class MenuService implements IMenuService {

    private final MenuRepository menuRepository;
    private final MenuMapper menuMapper;

    @Override
    public Mono<GeneralResponse<List<MenuResponse>>> getAllByCampus(UUID campusId) {
        return menuRepository.getMenuByCampusId(campusId)
                .collectList()
                .flatMap(menuResponses -> Mono.just(GeneralResponse.<List<MenuResponse>>builder()
                        .code(SuccessCode.SUCCESS.name())
                        .data(menuMapper.modelToListResponse(menuResponses))
                        .build())
                );
    }

    @Override
    public Mono<GeneralResponse<GetMenuDetailsResponse>> getMenuDetailById(UUID menuId) {
        return menuRepository.findById(menuId)
                .flatMap(menu -> menuRepository.getDessertToMenuDetail(menuId)
                        .flatMap(dessert -> menuRepository.getProductVariantByProductToMenu(dessert.getProductMenuId())
                                .collectList()
                                .map(productVariants -> {
                                    dessert.setVariants(productVariants);
                                    return dessert;
                                }))
                        .collectList()
                        .flatMap(finalDessert -> menuRepository.getDrinkToMenuDetail(menuId)
                                .flatMap(drink -> menuRepository.getProductVariantByProductToMenu(drink.getProductMenuId())
                                        .collectList()
                                        .map(productVariants -> {
                                            drink.setVariants(productVariants);
                                            return drink;
                                        }))
                                .collectList()
                                .flatMap(finalDrink -> menuRepository.getInitialDishToMenuDetail(menuId)
                                        .flatMap(initialDish -> menuRepository.getProductVariantByProductToMenu(initialDish.getProductMenuId())
                                                .collectList()
                                                .map(productVariants -> {
                                                    initialDish.setVariants(productVariants);
                                                    return initialDish;
                                                }))
                                        .collectList()
                                        .flatMap(finalInitialDishes -> menuRepository.getPrincipalDishToMenuDetail(menuId)
                                                .flatMap(principalDish -> menuRepository.getProductVariantByProductToMenu(principalDish.getProductMenuId())
                                                        .collectList()
                                                        .map(productVariants -> {
                                                            principalDish.setVariants(productVariants);
                                                            return principalDish;
                                                        }))
                                                .collectList()
                                                .flatMap(finalPrincipalDish -> Mono.just(GeneralResponse.<GetMenuDetailsResponse>builder()
                                                        .code(SuccessCode.SUCCESS.name())
                                                        .data(GetMenuDetailsResponse
                                                                .builder()
                                                                .menuId(menu.getMenuId())
                                                                .name(menu.getName())
                                                                .amountPrice(menu.getAmountPrice())
                                                                .currencyPrice(menu.getCurrencyPrice())
                                                                .minCookingTime(menu.getMinCookingTime())
                                                                .maxCookingTime(menu.getMaxCookingTime())
                                                                .unitOfTimeCookingTime(menu.getUnitOfTimeCookingTime())
                                                                .urlImage(menu.getUrlImage())
                                                                .desserts(finalDessert)
                                                                .drinks(finalDrink)
                                                                .initialDishes(finalInitialDishes)
                                                                .principalDishes(finalPrincipalDish)
                                                                .build())
                                                        .build()))
                                        ))));
    }

    @Override
    public Mono<GeneralResponse<String>> create(CreateMenuRequest request) {

        return menuRepository.save(menuMapper.createRequestToModel(request)).flatMap(createdMenu -> Mono.just(GeneralResponse.<String>builder()
                .code(SuccessCode.CREATED.name())
                .data(MENU_ENTITY)
                .build()));
    }

    @Override
    public Mono<GeneralResponse<String>> update(UpdateMenuRequest request, UUID menuId) {
        return menuRepository.findById(menuId)
                .switchIfEmpty(Mono.error(new CustomException(HttpStatus.NOT_FOUND, ErrorCode.NOT_FOUND.name(), MENU_ENTITY)))
                .flatMap(menu -> {
                            menu.setName(request.getName());
                            menu.setUrlImage(request.getUrlImage());

                            return menuRepository.save(menu).flatMap(updatedMenu -> Mono.just(GeneralResponse.<String>builder()
                                    .code(SuccessCode.UPDATED.name())
                                    .data(MENU_ENTITY)
                                    .build()));
                        }
                );
    }

    @Override
    public Mono<GeneralResponse<String>> delete(UUID menuId) {
        return menuRepository.findById(menuId)
                .switchIfEmpty(Mono.error(new CustomException(HttpStatus.NOT_FOUND, ErrorCode.NOT_FOUND.name(), MENU_ENTITY)))
                .flatMap(menu -> menuRepository.delete(menu).then(Mono.just(GeneralResponse.<String>builder()
                        .code(SuccessCode.DELETED.name())
                        .data(MENU_ENTITY)
                        .build()))
                );
    }
}
