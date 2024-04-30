package com.p20241061.management.core.repositories.relations;

import com.p20241061.management.core.entities.relations.CampusCategory;
import com.p20241061.management.api.model.response.relations.GetCategoriesByCampusResponse;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.UUID;

@Repository
public interface CampusCategoryRepository extends ReactiveCrudRepository<CampusCategory, UUID> {
}
