package com.immovable.investmentplatform.services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.immovable.investmentplatform.dto.CartItemAmountDTO;
import com.immovable.investmentplatform.dto.CartItemsResponseDto;
import com.immovable.investmentplatform.dto.CartRequestDto;
import com.immovable.investmentplatform.dto.CartResponseDto;
import com.immovable.investmentplatform.dto.CartUpdateRequestDto;
import com.immovable.investmentplatform.entities.Cart;
import com.immovable.investmentplatform.entities.CartItems;
import com.immovable.investmentplatform.repos.CartItemsRepository;
import com.immovable.investmentplatform.repos.CartRepository;
import com.immovable.investmentplatform.repos.PropertyRepoistory;

@Service
public class CartServiceImpl implements CartService {

	@Autowired
	PropertyRepoistory propertyRepoistory;

	@Autowired
	CartItemsRepository cartItemsRepository;

	@Autowired
	CartRepository cartRepository;

	@Override
	public Map<String, String> saveCartDetails(CartRequestDto cartRequestDto) {
		Map<String, String> responseMap = new HashMap<>();

		String customerId = cartRequestDto.getCustomerId();

		Optional<Cart> cartObj = cartRepository.findByCustomerIdAndStatus(customerId, "ACTIVE");

		if (cartObj.isPresent()) {

			Cart cart = cartObj.get();

			CartItems cartItem = new CartItems();

			cartItem.setPropertyId(cartRequestDto.getPropertyId());
			cartItem.setPrice(cartRequestDto.getPrice());
			cartItem.setStatus("ACTIVE");

			List<CartItems> cartItems = cartObj.get().getCartItems();
			cartItems.add(cartItemsRepository.save(cartItem));
			cart.setStatus("ACTIVE");
			cart.setCartItems(cartItems);
			cartRepository.save(cart);
			responseMap.put("status", "Success");

		} else {

			Cart cart = new Cart();
			cart.setCustomerId(cartRequestDto.getCustomerId());

			CartItems cartItem = new CartItems();
			cartItem.setPropertyId(cartRequestDto.getPropertyId());
			cartItem.setPrice(cartRequestDto.getPrice());
			cartItem.setStatus("ACTIVE");

			List<CartItems> cartItemsList = new ArrayList<>();

			cartItemsList.add(cartItemsRepository.save(cartItem));
			cart.setCartItems(cartItemsList);
			cart.setStatus("ACTIVE");
			cartRepository.save(cart);
			responseMap.put("status", "Success");
		}

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
	public Map<String, String> getCartUpdate(CartUpdateRequestDto cartUpdateRequestDto) {
		Map<String, String> responseMap = new HashMap<>();
		Optional<Cart> cart = cartRepository.findByCustomerIdAndStatus(cartUpdateRequestDto.getCustomerId(), "ACTIVE");
		List<CartItems> cartItemsList = cart.get().getCartItems().stream()
				.filter(y -> y.getStatus().equalsIgnoreCase("ACTIVE")).collect(Collectors.toList());
		if (cartItemsList.size() == cartUpdateRequestDto.getCartItemsIdsList().size()) {
			setCartItemsStatus(cartUpdateRequestDto);
			cart.get().setStatus("INACTIVE");
			cartRepository.save(cart.get());
		} else {
			setCartItemsStatus(cartUpdateRequestDto);
		}
		verifyCartItemsAndUpdateCart(cartUpdateRequestDto.getCustomerId());
		responseMap.put("status", "Success");
		return responseMap;
	}

	private void setCartItemsStatus(CartUpdateRequestDto cartUpdateRequestDto) {
		for (String cartItemId : cartUpdateRequestDto.getCartItemsIdsList()) {
			Optional<CartItems> cartItem = cartItemsRepository.findById(cartItemId);
			cartItem.get().setStatus("INACTIVE");
			cartItemsRepository.save(cartItem.get());
		}
	}

	private void verifyCartItemsAndUpdateCart(String customerId) {
		Optional<Cart> cart = cartRepository.findByCustomerIdAndStatus(customerId, "ACTIVE");
		if (cart.isPresent()) {

			int cartItemsList = cart.get().getCartItems().size();
			int cartActualSize = cartItemsRepository.findByStatus("INACTIVE").size();
			if (cartItemsList == cartActualSize) {
				cart.get().setStatus("INACTIVE");
				cartRepository.save(cart.get());
			}

		}

	}

	@Override
	public String cartUpdateOnPaymentStatus(CartUpdateRequestDto cartUpdateRequestDto, String status) {
		Optional<Cart> cart = null;
		if (status.equalsIgnoreCase("INACTIVE")) {
			cart = cartRepository.findByCustomerIdAndStatus(cartUpdateRequestDto.getCustomerId(), "ACTIVE");
		    if(cart.isPresent()) {
					for (CartItems cartItem : cart.get().getCartItems()) {
						cartItem.setStatus("INACTIVE");
						cartItemsRepository.save(cartItem);
					}
					cart.get().setStatus("INACTIVE");
					cartRepository.save(cart.get());
		     }
		
		}
		return "cart and cartItems updated successfully";
	}

	@Override
	public Map<String, Long> getCartsItems(String customerId) {
		Optional<Cart> cart = cartRepository.findByCustomerIdAndStatus(customerId, "ACTIVE");
		Map<String, Long> cartItemsMap = new HashMap<>();
		if (cart.get().getStatus().equals("ACTIVE")) {
			Long cartItemsCount = cart.get().getCartItems().stream()
					.filter(cartItem -> cartItem.getStatus().equals("ACTIVE")).count();
			// List<CartItems> itemsList =
			// cart.get().getCartItems().stream().filter(cartItem ->
			// cartItem.getStatus().equals("ACTIVE")).collect(Collectors.toList());
			cartItemsMap.put("count", cartItemsCount);
		}
		return cartItemsMap;
	}

	// input customerId cartId
	// return map (count,cartItems)
	private CartResponseDto mapCartToCartRsponeDto(CartResponseDto dto, Cart cart) {

		List<CartItemsResponseDto> itemList = new ArrayList<>();

		BigDecimal totalCartValue = cart.getCartItems().stream().filter(y -> y.getStatus().equalsIgnoreCase("ACTIVE"))
				.map(x -> x.getPrice()).reduce(BigDecimal.ZERO, BigDecimal::add);

		cart.getCartItems().stream().filter(y -> y.getStatus().equalsIgnoreCase("ACTIVE")).forEach(x -> {
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

	@Override
	public Map<String, String> getCartUpdateAmount(CartItemAmountDTO cartItemAmountDTO) {
		Map<String, String> responseMap = new HashMap<>();
		Optional<CartItems> findById = cartItemsRepository.findById(cartItemAmountDTO.getCartItemId());
		if (findById.isPresent()) {
			CartItems cartItems = findById.get();
			cartItems.setPrice(new BigDecimal(cartItemAmountDTO.getAmount()));
			cartItemsRepository.save(cartItems);
			responseMap.put("status", "Success");
		}else {
			responseMap.put("status", "Failure");
		}
		return responseMap;
	}

}
