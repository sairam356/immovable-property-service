package com.immovable.propertyservice.services;


import java.util.Map;

import com.immovable.propertyservice.dto.CartRequestDto;
import com.immovable.propertyservice.dto.CartResponseDto;
import com.immovable.propertyservice.dto.CartUpdateRequestDto;

public interface CartService {

    public Map<String,String> saveCartDetails(CartRequestDto cartRequestDto);

    public CartResponseDto getCartDetails(String customerId);

    public String getCartUpdate(CartUpdateRequestDto cartUpdateRequestDto);

    public String cartUpdateOnPaymentStatus(CartUpdateRequestDto cartUpdateRequestDto,String status);

    public Map<String, Long> getCartsItems(String customerId);
}
