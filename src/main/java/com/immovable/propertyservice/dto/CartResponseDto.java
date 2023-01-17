package com.immovable.propertyservice.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartResponseDto {

    public BigDecimal totalAmount;

    private String id;
    private String customerId;
    private List<CartItemsResponseDto> cartItems;
    private String status;


}
