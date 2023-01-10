package com.immovable.propertyservice.controller;

import com.immovable.propertyservice.dto.CartResponseDto;
import com.immovable.propertyservice.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    CartService cartService;


    @PostMapping("/{customerId}/{price}/{propertyId}")
    public String saveCart(@PathVariable String customerId ,@PathVariable BigDecimal price , @PathVariable String propertyId) {
        return cartService.saveCartDetails(customerId,price,propertyId);

    }


    @GetMapping("/{id}")
    public CartResponseDto getCartsInfo(@PathVariable String id) {
        return cartService.getCartDetails(id);

    }

}
