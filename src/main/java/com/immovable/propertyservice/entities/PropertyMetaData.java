package com.immovable.propertyservice.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "property_metadata_tbl")
public class PropertyMetaData {

	@Id
	private String id;
	private String description1;
	private String description2;
	private Double annualGrossRent;
	private Double serivceCharges;
	private Double maintainceCharges;
	private Double annualNetIncome;
}
