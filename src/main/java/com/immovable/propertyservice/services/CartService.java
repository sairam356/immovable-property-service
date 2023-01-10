package com.immovable.propertyservice.services;


import com.immovable.propertyservice.dto.CartResponseDto;

import java.math.BigDecimal;

public interface CartService {

    public String saveCartDetails(String customerId, BigDecimal price, String propertyId);

    public CartResponseDto getCartDetails(String customerId);
}
