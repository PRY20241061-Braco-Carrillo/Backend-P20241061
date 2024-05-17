package com.p20241061.management.core.entities.complement;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Table("complement")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Complement {
    @Id
    private UUID complementId;
    private String name;
    private Double amountPrice;
    private String currencyPrice;
    private Boolean isSauce;
    private String urlImage;
}
