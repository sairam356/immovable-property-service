package com.immovable.propertyservice.dto;

import com.immovable.propertyservice.entities.Property;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItemsResponseDto {

    private String id;
    private Property property;
    private BigDecimal price;
    private String status;
}
