package com.immovable.investmentplatform.entities;

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
@Document(collection = "customer_revenue_tbl")
public class CustomerRevenue {

	@Id
	private String id;
	private String propertyId;
	private String month;
	private String year; 
	private BigDecimal actualAmount;
	private BigDecimal sentAmount ;
	private Date depositedDate;
	private Date createdDt;
}
