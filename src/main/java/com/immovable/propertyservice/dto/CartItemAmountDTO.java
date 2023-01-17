
package com.immovable.propertyservice.dto;

import java.math.BigDecimal;

import com.immovable.propertyservice.entities.Property;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItemAmountDTO {

	String amount;
	String cartItemId;
}
