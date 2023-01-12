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
@Document(collection = "customer_tbl")
public class Customer {

	@Id
	private String id;
	private String firstName;
	private String lastName;
	private String passport;
	private String address;
	private String userId;
	@DBRef
	private List<CustomerPropertiesStack> propertiesStack;
	@DBRef
	private List<CustomerRevenue> customerRevenue;
	@DBRef
	private Wallet wallet;
	@DBRef
	private Cart cart;

}
