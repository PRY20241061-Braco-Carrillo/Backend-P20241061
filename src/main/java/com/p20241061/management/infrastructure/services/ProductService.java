package com.p20241061.management.infrastructure.services;

import com.p20241061.management.api.mapping.NutritionalInformationMapper;
import com.p20241061.management.api.mapping.ProductMapper;
import com.p20241061.management.api.model.request.create.CreateProductRequest;
import com.p20241061.management.api.model.request.update.UpdateProductRequest;
import com.p20241061.management.api.model.response.ProductResponse;
import com.p20241061.management.core.repositories.NutritionalInformationRepository;
import com.p20241061.management.core.repositories.ProductRepository;
import com.p20241061.management.core.repositories.relations.CampusCategoryRepository;
import com.p20241061.management.infrastructure.interfaces.IProductService;
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
public class ProductService implements IProductService {

    private final ProductRepository productRepository;
    private final NutritionalInformationRepository nutritionalInformationRepository;
    private final CampusCategoryRepository campusCategoryRepository;
    private final ProductMapper productMapper;
    private final NutritionalInformationMapper nutritionalInformationMapper;

    @Override
    public Mono<GeneralResponse<ProductResponse>> create(CreateProductRequest request) {
        return campusCategoryRepository.findById(request.getCampusCategoryId())
                .switchIfEmpty(Mono.error(new CustomException(HttpStatus.BAD_REQUEST, "Campus Category with id " + request.getCampusCategoryId() + " not found")))
                .flatMap(campusCategory -> nutritionalInformationRepository.save(nutritionalInformationMapper.createRequestToModel(request.getNutritionalInformation()))
                        .flatMap(createdNutritionalInformation -> productRepository.save(productMapper.createRequestToModel(request, createdNutritionalInformation.getNutritionalInformationId()))
                                .flatMap(createdProduct -> {
                                    ProductResponse productResponse = productMapper.modelToResponse(createdProduct);
                                    productResponse.setNutritionalInformation(createdNutritionalInformation);
                                    productResponse.setCampusCategory(campusCategory);

                                    return Mono.just(GeneralResponse.<ProductResponse>builder()
                                            .code(SuccessCode.CREATED.name())
                                            .data(productResponse)
                                            .build());
                                })));
    }

    @Override
    public Mono<GeneralResponse<ProductResponse>> update(UpdateProductRequest request, UUID productId) {
        return productRepository.findById(productId)
                .switchIfEmpty(Mono.error(new CustomException(HttpStatus.BAD_REQUEST, "Product with id " + productId + " not found")))
                .flatMap(product -> campusCategoryRepository.findById(request.getCampusCategoryId())
                        .switchIfEmpty(Mono.error(new CustomException(HttpStatus.BAD_REQUEST, "Campus Category with id " + request.getCampusCategoryId() + " not found")))
                        .flatMap(campusCategory -> {

                            product.setName(request.getName());
                            product.setCookingTime(request.getCookingTime());
                            product.setDescription(request.getDescription());
                            product.setIsBreakfast(request.getIsBreakfast());
                            product.setIsLunch(request.getIsLunch());
                            product.setIsDinner(request.getIsDinner());
                            product.setUrlImage(request.getUrlImage());
                            product.setFreeSauce(request.getFreeSauce());
                            product.setCampusCategoryId(request.getCampusCategoryId());
                            product.setIsAvailable(request.getIsAvailable());

                            return productRepository.save(product)
                                .flatMap(updatedProduct -> nutritionalInformationRepository.findById(updatedProduct.getNutritionalInformationId())
                                        .switchIfEmpty(Mono.error(new CustomException(HttpStatus.BAD_REQUEST, "Nutritional information with id " + updatedProduct.getNutritionalInformationId() + " not found")))
                                        .flatMap(nutritionalInformation -> {

                                            ProductResponse productResponse = productMapper.modelToResponse(updatedProduct);
                                            productResponse.setNutritionalInformation(nutritionalInformation);
                                            productResponse.setCampusCategory(campusCategory);

                                            return Mono.just(GeneralResponse.<ProductResponse>builder()
                                                    .code(SuccessCode.UPDATED.name())
                                                    .data(productResponse)
                                                    .build());
                                        }));

                        }));
    }

    @Override
    public Mono<GeneralResponse<String>> delete(UUID productId) {
        return productRepository.findById(productId)
                .switchIfEmpty(Mono.error(new CustomException(HttpStatus.BAD_REQUEST, "Product with id " + productId + " not found")))
                .flatMap(product -> productRepository.delete(product)
                        .then(Mono.defer(() -> nutritionalInformationRepository.findById(product.getNutritionalInformationId())
                                .switchIfEmpty(Mono.error(new CustomException(HttpStatus.BAD_REQUEST, "Nutritional information with id " + product.getNutritionalInformationId() + " not found")))
                                .flatMap(nutritionalInformationRepository::delete)
                                .then(Mono.just(GeneralResponse.<String>builder()
                                        .code(SuccessCode.DELETED.name())
                                        .data("Product with id " + productId + " deleted successfully")
                                        .build()))))
                );
    }
}
