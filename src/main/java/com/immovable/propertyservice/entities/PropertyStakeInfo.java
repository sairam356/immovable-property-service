package com.immovable.propertyservice.entities;

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
	private String stake_funded;
	private String stake_avaliable;
	private String actionByCustomerId;
	private Date  createdDt;
}
