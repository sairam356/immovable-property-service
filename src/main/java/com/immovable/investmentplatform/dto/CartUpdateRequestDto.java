package com.immovable.investmentplatform.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartUpdateRequestDto {

    public String cartId;
    public String customerId;
    public List<String> cartItemsIdsList;
}
