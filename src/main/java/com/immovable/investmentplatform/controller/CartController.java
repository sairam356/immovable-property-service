package com.immovable.investmentplatform.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.immovable.investmentplatform.dto.CartItemAmountDTO;
import com.immovable.investmentplatform.dto.CartRequestDto;
import com.immovable.investmentplatform.dto.CartResponseDto;
import com.immovable.investmentplatform.dto.CartUpdateRequestDto;
import com.immovable.investmentplatform.services.CartService;



@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    CartService cartService;


    @PostMapping()
    public Map<String,String> saveCart(@RequestBody CartRequestDto cartRequestDto) {
        return cartService.saveCartDetails(cartRequestDto);

    }


    @GetMapping("/{id}")
    public CartResponseDto getCartsInfo(@PathVariable String id) {
        return cartService.getCartDetails(id);

    }


    @PutMapping("/update")
    public  Map<String,String> getCartUpdate(@RequestBody CartUpdateRequestDto cartUpdateRequestDto) {
        return cartService.getCartUpdate(cartUpdateRequestDto);

    }

    
    @PutMapping("/updateAmount")
    public  Map<String,String> getCartUpdateAmount(@RequestBody CartItemAmountDTO cartItemAmountDTO) {
        return cartService.getCartUpdateAmount(cartItemAmountDTO);

    }

    @PutMapping("/updateOnPayment/{status}")
    public String cartUpdateOnPaymentStatus(@RequestBody CartUpdateRequestDto cartUpdateRequestDto, @PathVariable String status) {
        return cartService.cartUpdateOnPaymentStatus(cartUpdateRequestDto,status);

    }

    @GetMapping("/count/{customerId}")
    public Map<String, Long> getCartsItemsAndCount(@PathVariable String customerId) {
        return cartService.getCartsItems(customerId);

    }

}
