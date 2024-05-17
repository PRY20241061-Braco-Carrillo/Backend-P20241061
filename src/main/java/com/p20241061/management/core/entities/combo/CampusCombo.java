package com.p20241061.management.core.entities.combo;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Table("campus_combo")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CampusCombo {
    @Id
    private UUID campusComboId;
    private Boolean isAvailable;
    private UUID comboId;
    private UUID campusId;

}
