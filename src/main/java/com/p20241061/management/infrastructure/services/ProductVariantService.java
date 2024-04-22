package com.p20241061.management.infrastructure.services;


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
public class ProductVariantService implements IProductVariantService {

    private final ProductVariantRepository productVariantRepository;
    private final CookingTypeRepository cookingTypeRepository;
    private final SizeRepository sizeRepository;
    private final ProductRepository productRepository;


    @Override
    public Mono<GeneralResponse<ProductVariantResponse>> create(CreateProductVariantRequest request) {
        return cookingTypeRepository.findById(request.getCookingTypeId())
                .switchIfEmpty(Mono.error(new CustomException(HttpStatus.BAD_REQUEST, "Cooking type with id " + request.getCookingTypeId() + " not found")))
                .flatMap(cookingType -> sizeRepository.findById(request.getSizeId())
                        .switchIfEmpty(Mono.error(new CustomException(HttpStatus.BAD_REQUEST, "Size with id " + request.getSizeId() + " not found")))
                        .flatMap(size -> productRepository.findById(request.getProductId())
                                .switchIfEmpty(Mono.error(new CustomException(HttpStatus.BAD_REQUEST, "Product with id " + request.getProductId() + " not found")))
                                .flatMap(product -> {
                                    ProductVariant productVariant = ProductVariant.builder()
                                            .cookingTypeId(cookingType.getCookingTypeId())
                                            .sizeId(size.getSizeId())
                                            .productId(product.getProductId())
                                            .price(request.getPrice())
                                            .build();

                                    return productVariantRepository.save(productVariant).flatMap(createdProductVariant -> {

                                        ProductVariantResponse productVariantResponse = ProductVariantResponse.builder()
                                                .productVariantId(createdProductVariant.getProductVariantId())
                                                .cookingType(cookingType)
                                                .size(size)
                                                .product(product)
                                                .price(createdProductVariant.getPrice())
                                                .build();

                                        return Mono.just(GeneralResponse.<ProductVariantResponse>builder()
                                                .code(SuccessCode.CREATED.name())
                                                .data(productVariantResponse)
                                                .build());
                                    });
                                })));
    }

    @Override
    public Mono<GeneralResponse<ProductVariantResponse>> update(UpdateProductVariantRequest request, UUID productVariantId) {
        return productVariantRepository.findById(productVariantId)
                .switchIfEmpty(Mono.error(new CustomException(HttpStatus.BAD_REQUEST, "Product variant with id " + productVariantId + " not found")))
                .flatMap(productVariant -> cookingTypeRepository.findById(request.getCookingTypeId())
                        .switchIfEmpty(Mono.error(new CustomException(HttpStatus.BAD_REQUEST, "Cooking type with id " + request.getCookingTypeId() + " not found")))
                        .flatMap(cookingType -> sizeRepository.findById(request.getSizeId())
                                .switchIfEmpty(Mono.error(new CustomException(HttpStatus.BAD_REQUEST, "Size with id " + request.getSizeId() + " not found")))
                                .flatMap(size -> productRepository.findById(request.getProductId())
                                        .switchIfEmpty(Mono.error(new CustomException(HttpStatus.BAD_REQUEST, "Product with id " + request.getProductId() + " not found")))
                                        .flatMap(product -> {
                                            productVariant.setCookingTypeId(cookingType.getCookingTypeId());
                                            productVariant.setSizeId(size.getSizeId());
                                            productVariant.setProductId(product.getProductId());
                                            productVariant.setPrice(request.getPrice());

                                            return productVariantRepository.save(productVariant).flatMap(updatedProductVariant -> {

                                                ProductVariantResponse productVariantResponse = ProductVariantResponse.builder()
                                                        .productVariantId(updatedProductVariant.getProductVariantId())
                                                        .cookingType(cookingType)
                                                        .size(size)
                                                        .product(product)
                                                        .price(updatedProductVariant.getPrice())
                                                        .build();

                                                return Mono.just(GeneralResponse.<ProductVariantResponse>builder()
                                                        .code(SuccessCode.UPDATED.name())
                                                        .data(productVariantResponse)
                                                        .build());
                                            });
                                        })
                                )));
    }

    @Override
    public Mono<GeneralResponse<String>> delete(UUID productVariantId) {
        return productVariantRepository.findById(productVariantId)
                .switchIfEmpty(Mono.error(new CustomException(HttpStatus.BAD_REQUEST, "Product variant with id " + productVariantId + " not found")))
                .flatMap(productVariant -> productVariantRepository.delete(productVariant)
                        .then(Mono.just(GeneralResponse.<String>builder()
                                .code(SuccessCode.DELETED.name())
                                .data("Product variant with id " + productVariantId + " deleted successfully")
                                .build())));
    }
}
