package com.p20241061.management.core.entities;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Table("size")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Size {

    @Id
    private UUID sizeId;
    private String name;
    private Boolean isAvailable;
}
