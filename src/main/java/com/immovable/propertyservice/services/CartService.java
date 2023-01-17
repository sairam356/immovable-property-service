package com.immovable.propertyservice.services;


import java.util.Map;

import com.immovable.propertyservice.dto.CartItemAmountDTO;
import com.immovable.propertyservice.dto.CartRequestDto;
import com.immovable.propertyservice.dto.CartResponseDto;
import com.immovable.propertyservice.dto.CartUpdateRequestDto;

public interface CartService {

    public Map<String,String> saveCartDetails(CartRequestDto cartRequestDto);

    public CartResponseDto getCartDetails(String customerId);

    public Map<String, String> getCartUpdate(CartUpdateRequestDto cartUpdateRequestDto);
    
    public Map<String, String> getCartUpdateAmount(CartItemAmountDTO cartItemAmountDTO);

    public String cartUpdateOnPaymentStatus(CartUpdateRequestDto cartUpdateRequestDto,String status);

    public Map<String, Long> getCartsItems(String customerId);
}
