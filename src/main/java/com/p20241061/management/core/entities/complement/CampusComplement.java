package com.p20241061.management.core.entities.complement;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Table("campus_complement")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CampusComplement {
    @Id
    private UUID campusComplementId;
    private Boolean isAvailable;
    private UUID complementId;
    private UUID campusId;
}
