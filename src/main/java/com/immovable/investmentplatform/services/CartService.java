package com.immovable.investmentplatform.services;


import java.util.Map;

import com.immovable.investmentplatform.dto.CartItemAmountDTO;
import com.immovable.investmentplatform.dto.CartRequestDto;
import com.immovable.investmentplatform.dto.CartResponseDto;
import com.immovable.investmentplatform.dto.CartUpdateRequestDto;

public interface CartService {

    public Map<String,String> saveCartDetails(CartRequestDto cartRequestDto);

    public CartResponseDto getCartDetails(String customerId);

    public Map<String, String> getCartUpdate(CartUpdateRequestDto cartUpdateRequestDto);
    
    public Map<String, String> getCartUpdateAmount(CartItemAmountDTO cartItemAmountDTO);

    public String cartUpdateOnPaymentStatus(CartUpdateRequestDto cartUpdateRequestDto,String status);

    public Map<String, Long> getCartsItems(String customerId);
}
