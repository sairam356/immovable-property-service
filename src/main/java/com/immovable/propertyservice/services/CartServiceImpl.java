package com.immovable.propertyservice.services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.immovable.propertyservice.dto.CartItemsResponseDto;
import com.immovable.propertyservice.dto.CartRequestDto;
import com.immovable.propertyservice.dto.CartResponseDto;
import com.immovable.propertyservice.dto.CartUpdateRequestDto;
import com.immovable.propertyservice.entities.Cart;
import com.immovable.propertyservice.entities.CartItems;
import com.immovable.propertyservice.repo.CartItemsRepository;
import com.immovable.propertyservice.repo.CartRepository;
import com.immovable.propertyservice.repo.PropertyRepoistory;

@Service
public class CartServiceImpl implements CartService {

	@Autowired
	PropertyRepoistory propertyRepoistory;

	@Autowired
	CartItemsRepository cartItemsRepository;

	@Autowired
	CartRepository cartRepository;

	@Override
	public Map<String,String> saveCartDetails(CartRequestDto cartRequestDto) {
		Map<String , String> responseMap = new HashMap<>();
		CartItems cartItem = new CartItems();
		List<CartItems> cartItemsList;
		cartItem.setPropertyId(cartRequestDto.getPropertyId());
		cartItem.setPrice(cartRequestDto.getPrice());
		cartItem.setStatus("ACTIVE");

		Cart cart = new Cart();
		cart.setCustomerId(cartRequestDto.getCustomerId());
		cartItemsList = new ArrayList<>();
		cartItemsList.add(cartItemsRepository.save(cartItem));
		cart.setCartItems(cartItemsList);
		cart.setStatus("ACTIVE");
		cartRepository.save(cart);
		responseMap.put("status","Success");
		return responseMap;
	}

	@Override
	public CartResponseDto getCartDetails(String id) {
		CartResponseDto cartDto = new CartResponseDto();
		Optional<Cart> cart = cartRepository.findByCustomerIdAndStatus(id, "ACTIVE");
		if (cart.isPresent()) {
			Cart cartObj = cart.get();
			return mapCartToCartRsponeDto(cartDto, cartObj);
		}
		cartDto.setStatus("INACTIVE");
		return cartDto;
	}

	@Override
	public String getCartUpdate(CartUpdateRequestDto cartUpdateRequestDto) {
		Optional<Cart> cart = cartRepository.findByCustomerIdAndStatus(cartUpdateRequestDto.getCustomerId(), "ACTIVE");
		List<CartItems> cartItemsList = cart.get().getCartItems();
		if (cartItemsList.size() == cartUpdateRequestDto.getCartItemsIdsList().size()) {
			setCartItemsStatus(cartUpdateRequestDto);
			cart.get().setStatus("INACTIVE");
			cartRepository.save(cart.get());
		} else {
			setCartItemsStatus(cartUpdateRequestDto);
		}
		return "cart updated successfully ";
	}

	private void setCartItemsStatus(CartUpdateRequestDto cartUpdateRequestDto) {
		for (String cartItemId : cartUpdateRequestDto.getCartItemsIdsList()) {
			Optional<CartItems> cartItem = cartItemsRepository.findById(cartItemId);
			cartItem.get().setStatus("INACTIVE");
			cartItemsRepository.save(cartItem.get());
		}
	}

	@Override
	public String cartUpdateOnPaymentStatus(CartUpdateRequestDto cartUpdateRequestDto, String status) {
		Optional<Cart> cart = null;
		if (status.equalsIgnoreCase("INACTIVE")) {
			cart = cartRepository.findByCustomerId(cartUpdateRequestDto.getCustomerId());
			for (CartItems cartItem : cart.get().getCartItems()) {
				cartItem.setStatus("INACTIVE");
				cartItemsRepository.save(cartItem);
			}
		}
		cart.get().setStatus("INACTIVE");
		cartRepository.save(cart.get());
		return "cart and cartItems updated successfully";
	}

	@Override
	public Map<String, Long> getCartsItems(String customerId) {
		Optional<Cart> cart = cartRepository.findByCustomerIdAndStatus(customerId,"ACTIVE");
		Map<String, Long> cartItemsMap = new HashMap<>();
		if (cart.get().getStatus().equals("ACTIVE")) {
			Long cartItemsCount = cart.get().getCartItems().stream().filter(cartItem -> cartItem.getStatus().equals("ACTIVE")).count();
			//List<CartItems> itemsList = cart.get().getCartItems().stream().filter(cartItem -> cartItem.getStatus().equals("ACTIVE")).collect(Collectors.toList());
			cartItemsMap.put("count", cartItemsCount);
		}
		return cartItemsMap;
	}


	// input customerId cartId
	//return map (count,cartItems)
	private CartResponseDto mapCartToCartRsponeDto(CartResponseDto dto, Cart cart) {

		List<CartItemsResponseDto> itemList = new ArrayList<>();

		BigDecimal totalCartValue = cart.getCartItems().stream()
				.map(x -> x.getPrice())
				.reduce(BigDecimal.ZERO, BigDecimal::add);

		cart.getCartItems().forEach(x -> {
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
