package com.p20241061.management.infrastructure.services.product_variant;


import com.p20241061.management.api.mapping.ComplementMapper;
import com.p20241061.management.api.mapping.NutritionalInformationMapper;
import com.p20241061.management.api.mapping.ProductMapper;
import com.p20241061.management.api.mapping.ProductVariantMapper;
import com.p20241061.management.api.model.request.product_variant.create.CreateProductVariantRequest;
import com.p20241061.management.api.model.request.product_variant.update.UpdateProductVariantRequest;
import com.p20241061.management.api.model.response.product.GetProductDetailResponse;
import com.p20241061.management.core.repositories.complement.ComplementRepository;
import com.p20241061.management.core.repositories.product.NutritionalInformationRepository;
import com.p20241061.management.core.repositories.product.ProductRepository;
import com.p20241061.management.core.repositories.product_variant.ProductVariantRepository;
import com.p20241061.management.infrastructure.interfaces.product_variant.IProductVariantService;
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
    private final ProductRepository productRepository;
    private final ProductVariantMapper productVariantMapper;
    private final ProductMapper productMapper;
    private final NutritionalInformationRepository nutritionalInformationRepository;
    private final NutritionalInformationMapper nutritionalInformationMapper;
    private final ComplementRepository complementRepository;
    private final ComplementMapper complementMapper;


    @Override
    public Mono<GeneralResponse<GetProductDetailResponse>> getProductDetailResponse(UUID productId) {
        return productRepository.findById(productId)
                .switchIfEmpty(Mono.error(new CustomException(HttpStatus.NOT_FOUND, ErrorCode.NOT_FOUND.name(), PRODUCT_ENTITY)))
                .flatMap(product -> nutritionalInformationRepository.findById(product.getNutritionalInformationId())
                        .switchIfEmpty(Mono.error(new CustomException(HttpStatus.NOT_FOUND, ErrorCode.NOT_FOUND.name(), NUTRITIONAL_INFORMATION_ENTITY)))
                .flatMap(nutritionalInformation -> complementRepository.getComplementByProductId(product.getProductId())
                        .collectList()
                        .flatMap(complements -> productVariantRepository.getProductVariantByProductId(productId)
                                .collectList()
                                .flatMap(productVariant -> Mono.just(GeneralResponse.<GetProductDetailResponse>builder()
                                        .code(SuccessCode.SUCCESS.name())
                                        .data(GetProductDetailResponse.builder()
                                                .productVariants(productVariant)
                                                .product(productMapper.modelToResponse(product, nutritionalInformationMapper.modelToResponse(nutritionalInformation)))
                                                .complements(complementMapper.modelToListResponse(complements))
                                                .build())
                                        .build())))
                ));
    }

    @Override
    public Mono<GeneralResponse<String>> create(CreateProductVariantRequest request) {
        return productRepository.findById(request.getProductId())
                                .switchIfEmpty(Mono.error(new CustomException(HttpStatus.NOT_FOUND, ErrorCode.NOT_FOUND.name(), PRODUCT_ENTITY)))
                                .flatMap(product -> productVariantRepository.save(productVariantMapper.createRequestToModel(request,product.getProductId()))
                                        .flatMap(createdProductVariant -> Mono.just(GeneralResponse.<String>builder()
                                                .code(SuccessCode.CREATED.name())
                                                .data(PRODUCT_VARIANT_ENTITY)
                                                .build())));
    }

    @Override
    public Mono<GeneralResponse<String>> update(UpdateProductVariantRequest request, UUID productVariantId) {
        return productVariantRepository.findById(productVariantId)
                .switchIfEmpty(Mono.error(new CustomException(HttpStatus.NOT_FOUND, ErrorCode.NOT_FOUND.name(), PRODUCT_VARIANT_ENTITY)))
                                .flatMap(productVariant -> productRepository.findById(request.getProductId())
                                        .switchIfEmpty(Mono.error(new CustomException(HttpStatus.NOT_FOUND, ErrorCode.NOT_FOUND.name(), PRODUCT_ENTITY)))
                                        .flatMap(product -> {
                                            productVariant.setProductId(product.getProductId());

                                            return productVariantRepository.save(productVariant).flatMap(updatedProductVariant -> Mono.just(GeneralResponse.<String>builder()
                                                    .code(SuccessCode.UPDATED.name())
                                                    .data(PRODUCT_VARIANT_ENTITY)
                                                    .build()));
                                        })
                                );
    }

    @Override
    public Mono<GeneralResponse<String>> delete(UUID productVariantId) {
        return productVariantRepository.findById(productVariantId)
                .switchIfEmpty(Mono.error(new CustomException(HttpStatus.NOT_FOUND, ErrorCode.NOT_FOUND.name(), PRODUCT_VARIANT_ENTITY)))
                .flatMap(productVariant -> productVariantRepository.delete(productVariant)
                        .then(Mono.just(GeneralResponse.<String>builder()
                                .code(SuccessCode.DELETED.name())
                                .data(PRODUCT_VARIANT_ENTITY)
                                .build())));
    }
}
