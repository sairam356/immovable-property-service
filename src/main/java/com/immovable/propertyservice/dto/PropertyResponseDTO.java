package com.immovable.propertyservice.dto;

import java.math.BigDecimal;
import java.util.List;

import com.immovable.propertyservice.entities.Property;
import com.immovable.propertyservice.entities.PropertyMetaData;
import com.immovable.propertyservice.entities.PropertyRevenueInfo;
import com.immovable.propertyservice.entities.PropertyStakeInfo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PropertyResponseDTO {

	
	Property property;
}
