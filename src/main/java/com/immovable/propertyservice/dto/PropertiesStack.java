package com.immovable.propertyservice.dto;

import java.math.BigDecimal;
import java.util.Date;

import com.immovable.propertyservice.entities.Property;

import lombok.Data;

@Data
public class PropertiesStack {

	private String id;
	private Property property;
	private BigDecimal stakepercentage;
	private BigDecimal investedamount;
	private Date createdDate;
}
