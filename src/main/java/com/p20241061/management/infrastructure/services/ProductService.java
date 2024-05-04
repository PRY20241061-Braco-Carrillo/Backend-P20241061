package com.p20241061.management.infrastructure.services;

import com.p20241061.management.api.mapping.NutritionalInformationMapper;
import com.p20241061.management.api.mapping.ProductMapper;
import com.p20241061.management.api.model.request.create.CreateProductRequest;
import com.p20241061.management.api.model.request.update.UpdateProductRequest;
import com.p20241061.management.api.model.response.GetProductByCategoryResponse;
import com.p20241061.management.core.repositories.NutritionalInformationRepository;
import com.p20241061.management.core.repositories.ProductRepository;
import com.p20241061.management.core.repositories.relations.CampusCategoryRepository;
import com.p20241061.management.infrastructure.interfaces.IProductService;
import com.p20241061.shared.exceptions.CustomException;
import com.p20241061.shared.models.enums.ErrorCode;
import com.p20241061.shared.models.enums.SuccessCode;
import com.p20241061.shared.models.response.GeneralResponse;
import com.p20241061.shared.utils.PaginatedRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

import static com.p20241061.shared.models.enums.CampusName.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService implements IProductService {

    private final ProductRepository productRepository;
    private final NutritionalInformationRepository nutritionalInformationRepository;
    private final CampusCategoryRepository campusCategoryRepository;
    private final ProductMapper productMapper;
    private final NutritionalInformationMapper nutritionalInformationMapper;

    @Override
    public Mono<GeneralResponse<List<GetProductByCategoryResponse>>> getAllByCampusCategory(PaginatedRequest paginatedRequest, UUID campusCategoryId, Boolean available) {
        return paginatedRequest.paginateData(productRepository.getProductByCampusCategoryIdAndIsAvailable(campusCategoryId, available))
                .collectList()
                .map(products -> GeneralResponse.<List<GetProductByCategoryResponse>>builder()
                        .code(SuccessCode.SUCCESS.name())
                        .data(products)
                        .build());
    }

    @Override
    public Mono<GeneralResponse<String>> create(CreateProductRequest request) {
        return campusCategoryRepository.findById(request.getCampusCategoryId())
                .switchIfEmpty(Mono.error(new CustomException(HttpStatus.NOT_FOUND, ErrorCode.NOT_FOUND.name(), CAMPUS_CATEGORY_ENTITY)))
                .flatMap(campusCategory -> nutritionalInformationRepository.save(nutritionalInformationMapper.createRequestToModel(request.getNutritionalInformation()))
                        .flatMap(createdNutritionalInformation -> productRepository.save(productMapper.createRequestToModel(request, createdNutritionalInformation.getNutritionalInformationId()))
                                .flatMap(createdProduct -> Mono.just(GeneralResponse.<String>builder()
                                        .code(SuccessCode.CREATED.name())
                                        .data(PRODUCT_ENTITY)
                                        .build()))));
    }

    @Override
    public Mono<GeneralResponse<String>> update(UpdateProductRequest request, UUID productId) {
        return productRepository.findById(productId)
                .switchIfEmpty(Mono.error(new CustomException(HttpStatus.NOT_FOUND, ErrorCode.NOT_FOUND.name(), PRODUCT_ENTITY)))
                .flatMap(product -> campusCategoryRepository.findById(request.getCampusCategoryId())
                        .switchIfEmpty(Mono.error(new CustomException(HttpStatus.NOT_FOUND, ErrorCode.NOT_FOUND.name(), CAMPUS_CATEGORY_ENTITY)))
                        .flatMap(campusCategory -> {

                            product.setName(request.getName());
                            product.setDescription(request.getDescription());
                            product.setIsBreakfast(request.getIsBreakfast());
                            product.setIsLunch(request.getIsLunch());
                            product.setIsDinner(request.getIsDinner());
                            product.setUrlImage(request.getUrlImage());
                            product.setFreeSauce(request.getFreeSauce());
                            product.setIsAvailable(request.getIsAvailable());

                            return productRepository.save(product)
                                .flatMap(updatedProduct -> nutritionalInformationRepository.findById(updatedProduct.getNutritionalInformationId())
                                        .switchIfEmpty(Mono.error(new CustomException(HttpStatus.NOT_FOUND, ErrorCode.NOT_FOUND.name(), NUTRITIONAL_INFORMATION_ENTITY)))
                                        .flatMap(nutritionalInformation -> Mono.just(GeneralResponse.<String>builder()
                                                .code(SuccessCode.UPDATED.name())
                                                .data(PRODUCT_ENTITY)
                                                .build())));

                        }));
    }

    @Override
    public Mono<GeneralResponse<String>> delete(UUID productId) {
        return productRepository.findById(productId)
                .switchIfEmpty(Mono.error(new CustomException(HttpStatus.NOT_FOUND, ErrorCode.NOT_FOUND.name(), PRODUCT_ENTITY)))
                .flatMap(product -> productRepository.delete(product)
                        .then(Mono.defer(() -> nutritionalInformationRepository.findById(product.getNutritionalInformationId())
                                .switchIfEmpty(Mono.error(new CustomException(HttpStatus.NOT_FOUND, ErrorCode.NOT_FOUND.name(), NUTRITIONAL_INFORMATION_ENTITY)))
                                .flatMap(nutritionalInformationRepository::delete)
                                .then(Mono.just(GeneralResponse.<String>builder()
                                        .code(SuccessCode.DELETED.name())
                                        .data(PRODUCT_ENTITY)
                                        .build()))))
                );
    }
}
