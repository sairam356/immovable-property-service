package com.immovable.propertyservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartRequestDto {

    public String customerId;
    public BigDecimal price;
    public String propertyId;
}
