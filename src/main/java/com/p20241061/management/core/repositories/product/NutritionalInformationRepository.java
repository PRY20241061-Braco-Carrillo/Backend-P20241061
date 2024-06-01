package com.p20241061.management.core.repositories.product;

import com.p20241061.management.core.entities.product.NutritionalInformation;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface NutritionalInformationRepository extends ReactiveCrudRepository<NutritionalInformation, UUID> {
}
