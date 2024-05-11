package com.p20241061.order.core.entities;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.UUID;

@Table("order_request")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderRequest {
    @Id
    private UUID orderRequestId;
    private LocalDateTime orderRequestDate;
    private Integer confirmationToken;
    private Double price;
}
