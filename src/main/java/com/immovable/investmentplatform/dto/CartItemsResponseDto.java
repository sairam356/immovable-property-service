package com.immovable.investmentplatform.dto;

import java.math.BigDecimal;

import com.immovable.investmentplatform.entities.Property;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItemsResponseDto {

    private String id;
    private Property property;
    private BigDecimal price;
    private String status;
}
