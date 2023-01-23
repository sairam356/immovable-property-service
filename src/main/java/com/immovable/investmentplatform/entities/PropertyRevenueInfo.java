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
@Document(collection = "property_revenue_info_tbl")
public class PropertyRevenueInfo {

	@Id
	private String  id;
	private String  year;
	private String  month;
	private BigDecimal  actualAmount;
	private BigDecimal  receivedAmount ;
	private Date    receivedDate;
}
