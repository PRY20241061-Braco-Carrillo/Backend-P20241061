package com.p20241061.management.infrastructure.services;


import com.p20241061.management.api.mapping.ProductVariantMapper;
import com.p20241061.management.api.model.request.create.CreateProductVariantRequest;
import com.p20241061.management.api.model.request.update.UpdateProductVariantRequest;
import com.p20241061.management.api.model.response.ProductVariantResponse;
import com.p20241061.management.core.entities.ProductVariant;
import com.p20241061.management.core.repositories.CookingTypeRepository;
import com.p20241061.management.core.repositories.ProductRepository;
import com.p20241061.management.core.repositories.ProductVariantRepository;
import com.p20241061.management.core.repositories.SizeRepository;
import com.p20241061.management.infrastructure.interfaces.IProductVariantService;
import com.p20241061.shared.exceptions.CustomException;
import com.p20241061.shared.models.enums.ErrorCode;
import com.p20241061.shared.models.enums.SuccessCode;
import com.p20241061.shared.models.response.GeneralResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

import static com.p20241061.shared.models.enums.CampusName.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductVariantService implements IProductVariantService {

    private final ProductVariantRepository productVariantRepository;
    private final CookingTypeRepository cookingTypeRepository;
    private final SizeRepository sizeRepository;
    private final ProductRepository productRepository;
    private final ProductVariantMapper productVariantMapper;


    @Override
    public Mono<GeneralResponse<String>> create(CreateProductVariantRequest request) {
        return cookingTypeRepository.findById(request.getCookingTypeId())
                .switchIfEmpty(Mono.error(new CustomException(ErrorCode.NOT_FOUND.name(), COOKING_TYPE_ENTITY)))
                .flatMap(cookingType -> sizeRepository.findById(request.getSizeId())
                        .switchIfEmpty(Mono.error(new CustomException(ErrorCode.NOT_FOUND.name(), SIZE_ENTITY)))
                        .flatMap(size -> productRepository.findById(request.getProductId())
                                .switchIfEmpty(Mono.error(new CustomException(ErrorCode.NOT_FOUND.name(), PRODUCT_ENTITY)))
                                .flatMap(product -> productVariantRepository.save(productVariantMapper.createRequestToModel(request, cookingType.getCookingTypeId(), size.getSizeId(), product.getProductId()))
                                        .flatMap(createdProductVariant -> Mono.just(GeneralResponse.<String>builder()
                                                .code(SuccessCode.CREATED.name())
                                                .data(PRODUCT_VARIANT_ENTITY)
                                                .build())))));
    }

    @Override
    public Mono<GeneralResponse<String>> update(UpdateProductVariantRequest request, UUID productVariantId) {
        return productVariantRepository.findById(productVariantId)
                .switchIfEmpty(Mono.error(new CustomException(ErrorCode.NOT_FOUND.name(), PRODUCT_VARIANT_ENTITY)))
                .flatMap(productVariant -> cookingTypeRepository.findById(request.getCookingTypeId())
                        .switchIfEmpty(Mono.error(new CustomException(ErrorCode.NOT_FOUND.name(), COOKING_TYPE_ENTITY)))
                        .flatMap(cookingType -> sizeRepository.findById(request.getSizeId())
                                .switchIfEmpty(Mono.error(new CustomException(ErrorCode.NOT_FOUND.name(), SIZE_ENTITY)))
                                .flatMap(size -> productRepository.findById(request.getProductId())
                                        .switchIfEmpty(Mono.error(new CustomException(ErrorCode.NOT_FOUND.name(), PRODUCT_ENTITY)))
                                        .flatMap(product -> {
                                            productVariant.setCookingTypeId(cookingType.getCookingTypeId());
                                            productVariant.setSizeId(size.getSizeId());
                                            productVariant.setProductId(product.getProductId());
                                            productVariant.setPrice(request.getPrice());

                                            return productVariantRepository.save(productVariant).flatMap(updatedProductVariant -> Mono.just(GeneralResponse.<String>builder()
                                                    .code(SuccessCode.UPDATED.name())
                                                    .data(PRODUCT_VARIANT_ENTITY)
                                                    .build()));
                                        })
                                )));
    }

    @Override
    public Mono<GeneralResponse<String>> delete(UUID productVariantId) {
        return productVariantRepository.findById(productVariantId)
                .switchIfEmpty(Mono.error(new CustomException(ErrorCode.NOT_FOUND.name(), PRODUCT_VARIANT_ENTITY)))
                .flatMap(productVariant -> productVariantRepository.delete(productVariant)
                        .then(Mono.just(GeneralResponse.<String>builder()
                                .code(SuccessCode.DELETED.name())
                                .data(PRODUCT_VARIANT_ENTITY)
                                .build())));
    }
}
