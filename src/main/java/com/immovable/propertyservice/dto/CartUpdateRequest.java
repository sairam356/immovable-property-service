package com.immovable.propertyservice.dto;

import java.util.List;

public class CartUpdateRequest {

	String cartid;
	
	String customerId;
	
	List<String> cartItemId;
}
