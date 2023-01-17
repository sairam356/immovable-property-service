package com.immovable.propertyservice.entities;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "customer_property_stack_tbl")
public class CustomerPropertiesStack {

	@Id
	private String id;
	private String customerId;
	private String propertyId;
	private BigDecimal stakepercentage;
	private BigDecimal investedamount;
	private Date createdDate;

}
