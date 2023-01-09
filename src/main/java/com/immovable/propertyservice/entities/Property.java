package com.immovable.propertyservice.entities;

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
	private String occupancyRate;
	private Double actualpropertyPrice;
	private Double transcationCostPrice;
    private Double totalInvestmentCost;//  (actualpropertyPrice+transcationCostPrice)
	private String status;
	@DBRef
	private PropertyMetaData propertyMetaData;
	@DBRef
	private List<PropertyStakeInfo> propertyStakeInfo;
	@DBRef
	private List<PropertyRevenueInfo> propertyRevenueInfo;
	

}
