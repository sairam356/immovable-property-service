package com.immovable.propertyservice.services;

import com.immovable.propertyservice.dto.CartItemsResponseDto;
import com.immovable.propertyservice.dto.CartResponseDto;
import com.immovable.propertyservice.entities.Cart;
import com.immovable.propertyservice.entities.CartItems;
import com.immovable.propertyservice.repo.CartItemsRepository;
import com.immovable.propertyservice.repo.CartRepository;
import com.immovable.propertyservice.repo.PropertyRepoistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {

	@Autowired
	PropertyRepoistory propertyRepoistory;

	@Autowired
	CartItemsRepository cartItemsRepository;

	@Autowired
	CartRepository cartRepository;

	@Override
	public String saveCartDetails(String customerId, BigDecimal price, String propertyId) {
		System.out.println("customerId==> " + customerId + "price==> " + price + "propertyId===>  " + propertyId);

		CartItems cartItem = new CartItems();
		List<CartItems> cartItemsList;
		cartItem.setPropertyId(propertyId);
		cartItem.setPrice(price);
		cartItem.setStatus("ACTIVE");

		Cart cart = new Cart();
		cart.setCustomerId(customerId);
		cartItemsList = new ArrayList<>();
		cartItemsList.add(cartItemsRepository.save(cartItem));
		cart.setCartItems(cartItemsList);
		cart.setStatus("ACTIVE");
		cartRepository.save(cart);
		return "cart details save to DB successfully";
	}

	@Override
	public CartResponseDto getCartDetails(String id) {
		CartResponseDto cartDto = new CartResponseDto();

		Optional<Cart> cart = cartRepository.findByCustomerIdAndStatus(id, "ACTIVE");
		System.out.println("cart items==>" + cart.get().getCartItems());
		System.out.println("status " + cart.get().getStatus());
		System.out.println("id" + cart.get().getId());

		if (cart.isPresent()) {

			Cart cartObj = cart.get();
			return mapCartToCartRsponeDto(cartDto, cartObj);

		}
		cartDto.setStatus("INACTIVE");
		return cartDto;
	}

	private CartResponseDto mapCartToCartRsponeDto(CartResponseDto dto, Cart cart) {

		List<CartItemsResponseDto> itemList = new ArrayList<>();

		BigDecimal totalCartValue = new BigDecimal("0.00");

		cart.getCartItems().forEach(x -> {
			totalCartValue.add(x.getPrice());
			CartItemsResponseDto itemDto = new CartItemsResponseDto();
			itemDto.setId(x.getId());
			itemDto.setPrice(x.getPrice());
			itemDto.setProperty(propertyRepoistory.findById(x.getPropertyId()).get());
			itemDto.setStatus(x.getStatus());
			itemList.add(itemDto);

		});
		
		dto.setCartItems(itemList);
		dto.setTotalAmount(totalCartValue);
		dto.setCustomerId(cart.getCustomerId());
		dto.setId(cart.getId());
		dto.setStatus(cart.getStatus());
		return dto;
	}

}
