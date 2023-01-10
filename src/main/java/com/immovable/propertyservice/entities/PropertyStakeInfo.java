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
@Document(collection = "property_stakeinfo_tbl")
public class PropertyStakeInfo {

	@Id
	private String id;
	private BigDecimal stake_funded;
	private BigDecimal stake_avaliable;
	private String actionByCustomerId;
	private Date  createdDt;
}
