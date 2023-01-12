package com.immovable.propertyservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartUpdateRequestDto {

    public String cartId;
    public String customerId;
    public List<String> cartItemsIdsList;
}
