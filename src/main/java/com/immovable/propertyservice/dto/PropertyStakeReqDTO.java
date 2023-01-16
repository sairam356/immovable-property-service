package com.immovable.propertyservice.dto;

import java.math.BigDecimal;
import java.util.List;

import com.immovable.propertyservice.entities.Property;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PropertyStakeReqDTO {

	private String customerId;
	private String propertyId;
	private BigDecimal investmentAmount;

}
