package com.p20241061.management.core.repositories.relations;

import com.p20241061.management.core.entities.relations.CampusCategory;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CampusCategoryRepository extends ReactiveCrudRepository<CampusCategory, UUID> {
}