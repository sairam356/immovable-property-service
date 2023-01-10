package com.immovable.propertyservice.dto;

import com.immovable.propertyservice.entities.Cart;
import com.immovable.propertyservice.entities.CartItems;
import com.immovable.propertyservice.entities.Property;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.math.BigDecimal;
import java.util.List;

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
