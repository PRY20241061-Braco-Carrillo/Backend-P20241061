package com.p20241061.management.infrastructure.services;

import com.p20241061.management.api.model.request.create.CreateProductRequest;
import com.p20241061.management.api.model.request.update.UpdateProductRequest;
import com.p20241061.management.api.model.response.ProductResponse;
import com.p20241061.management.core.entities.NutritionalInformation;
import com.p20241061.management.core.entities.Product;
import com.p20241061.management.core.repositories.CategoryRepository;
import com.p20241061.management.core.repositories.NutritionalInformationRepository;
import com.p20241061.management.core.repositories.ProductRepository;
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
    private final CategoryRepository categoryRepository;

    @Override
    public Mono<GeneralResponse<ProductResponse>> create(CreateProductRequest request) {
        return categoryRepository.findById(request.getCategoryId())
                .switchIfEmpty(Mono.error(new CustomException(HttpStatus.BAD_REQUEST, "Category with id " + request.getCategoryId() + " not found")))
                .flatMap(category -> {
                    NutritionalInformation nutritionalInformation = NutritionalInformation.builder()
                            .calories(request.getNutritionalInformation().getCalories())
                            .proteins(request.getNutritionalInformation().getProteins())
                            .totalFat(request.getNutritionalInformation().getTotalFat())
                            .carbohydrates(request.getNutritionalInformation().getCarbohydrates())
                            .isVegan(request.getNutritionalInformation().getIsVegan())
                            .isVegetarian(request.getNutritionalInformation().getIsVegetarian())
                            .isLowCalories(request.getNutritionalInformation().getIsLowCalories())
                            .isHighProtein(request.getNutritionalInformation().getIsHighProtein())
                            .isWithoutGluten(request.getNutritionalInformation().getIsWithoutGluten())
                            .isWithoutNut(request.getNutritionalInformation().getIsWithoutNut())
                            .isWithoutLactose(request.getNutritionalInformation().getIsWithoutLactose())
                            .isWithoutEggs(request.getNutritionalInformation().getIsWithoutEggs())
                            .isWithoutSeafood(request.getNutritionalInformation().getIsWithoutSeafood())
                            .isWithoutPig(request.getNutritionalInformation().getIsWithoutPig())
                            .build();

                    return nutritionalInformationRepository.save(nutritionalInformation)
                            .flatMap(createdNutritionalInformation -> {
                                Product product = Product.builder()
                                        .name(request.getName())
                                        .cookingTime(request.getCookingTime())
                                        .description(request.getDescription())
                                        .isBreakfast(request.getIsBreakfast())
                                        .isLunch(request.getIsLunch())
                                        .isDinner(request.getIsDinner())
                                        .urlImage(request.getUrlImage())
                                        .freeSauce(request.getFreeSauce())
                                        .nutritionalInformationId(createdNutritionalInformation.getNutritionalInformationId())
                                        .categoryId(request.getCategoryId())
                                        .isAvailable(true)
                                        .build();

                                return productRepository.save(product).flatMap(createdProduct -> {
                                    ProductResponse productResponse = ProductResponse.builder()
                                            .productId(createdProduct.getProductId())
                                            .name(createdProduct.getName())
                                            .cookingTime(createdProduct.getCookingTime())
                                            .description(createdProduct.getDescription())
                                            .isBreakfast(createdProduct.getIsBreakfast())
                                            .isLunch(createdProduct.getIsLunch())
                                            .isDinner(createdProduct.getIsDinner())
                                            .urlImage(createdProduct.getUrlImage())
                                            .freeSauce(createdProduct.getFreeSauce())
                                            .nutritionalInformation(createdNutritionalInformation)
                                            .category(category)
                                            .isAvailable(createdProduct.getIsAvailable())
                                            .build();

                                    return Mono.just(GeneralResponse.<ProductResponse>builder()
                                            .code(SuccessCode.CREATED.name())
                                            .data(productResponse)
                                            .build());
                                });
                            });
                });

    }

    @Override
    public Mono<GeneralResponse<ProductResponse>> update(UpdateProductRequest request, UUID productId) {
        return productRepository.findById(productId)
                .switchIfEmpty(Mono.error(new CustomException(HttpStatus.BAD_REQUEST, "Product with id " + productId + " not found")))
                .flatMap(product -> categoryRepository.findById(request.getCategoryId())
                        .switchIfEmpty(Mono.error(new CustomException(HttpStatus.BAD_REQUEST, "Category with id " + request.getCategoryId() + " not found")))
                        .flatMap(category -> {

                            product.setName(request.getName());
                            product.setCookingTime(request.getCookingTime());
                            product.setDescription(request.getDescription());
                            product.setIsBreakfast(request.getIsBreakfast());
                            product.setIsLunch(request.getIsLunch());
                            product.setIsDinner(request.getIsDinner());
                            product.setUrlImage(request.getUrlImage());
                            product.setFreeSauce(request.getFreeSauce());
                            product.setCategoryId(request.getCategoryId());
                            product.setIsAvailable(request.getIsAvailable());

                            return productRepository.save(product)
                                .flatMap(updatedProduct -> nutritionalInformationRepository.findById(updatedProduct.getNutritionalInformationId())
                                        .switchIfEmpty(Mono.error(new CustomException(HttpStatus.BAD_REQUEST, "Nutritional information with id " + updatedProduct.getNutritionalInformationId() + " not found")))
                                        .flatMap(nutritionalInformation -> {
                                            ProductResponse productResponse = ProductResponse.builder()
                                                    .productId(updatedProduct.getProductId())
                                                    .name(updatedProduct.getName())
                                                    .cookingTime(updatedProduct.getCookingTime())
                                                    .description(updatedProduct.getDescription())
                                                    .isBreakfast(updatedProduct.getIsBreakfast())
                                                    .isLunch(updatedProduct.getIsLunch())
                                                    .isDinner(updatedProduct.getIsDinner())
                                                    .urlImage(updatedProduct.getUrlImage())
                                                    .freeSauce(updatedProduct.getFreeSauce())
                                                    .nutritionalInformation(nutritionalInformation)
                                                    .category(category)
                                                    .isAvailable(updatedProduct.getIsAvailable())
                                                    .build();

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
