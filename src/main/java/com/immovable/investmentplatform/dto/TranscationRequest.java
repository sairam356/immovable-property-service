package com.immovable.investmentplatform.dto;

import java.math.BigDecimal;

import lombok.Data;
@Data
public class TranscationRequest {

	public String type;
	public BigDecimal amount; // trasncation debit credit please use this field
	public String phoneNumber;
	public String bankName;
	public BigDecimal cartAmount; //verifying cartAmount use this variable 
}
