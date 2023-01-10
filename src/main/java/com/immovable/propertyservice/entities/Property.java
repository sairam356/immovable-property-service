package com.immovable.propertyservice.entities;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "property_tbl")
public class Property {

	@Id
	private String id;
	private String name;
	private String country;
	private String state;
	private String imageUrl;
	private String currency;
	private BigDecimal occupancyRate;
	private BigDecimal actualpropertyPrice;
	private BigDecimal transcationCostPrice;
    private BigDecimal totalInvestmentCost;//  (actualpropertyPrice+transcationCostPrice)
	private String status;
	@DBRef
	private PropertyMetaData propertyMetaData;
	@DBRef
	private List<PropertyStakeInfo> propertyStakeInfo;
	@DBRef
	private List<PropertyRevenueInfo> propertyRevenueInfo;
	
	

}
