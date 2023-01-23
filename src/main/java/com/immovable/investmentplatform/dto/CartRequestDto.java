package com.immovable.investmentplatform.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartRequestDto {

    public String customerId;
    public BigDecimal price;
    public String propertyId;
}
