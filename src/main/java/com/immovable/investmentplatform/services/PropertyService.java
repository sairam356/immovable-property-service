package com.immovable.investmentplatform.services;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.immovable.investmentplatform.dto.PropertyResponseDTO;
import com.immovable.investmentplatform.dto.PropertyStakeReqDTO;
import com.immovable.investmentplatform.entities.Property;

public interface PropertyService {

	public Property  savePropertyData(Property property);
	
	public List<Property> getPropertyData();
	
	public  PropertyResponseDTO getPropertyById(String id);
	
	public Map<String, BigDecimal> updateStakeInfo(PropertyStakeReqDTO propertyStakeReqDTO);
	
	public void dummyApiToCreateAProperty();
	
}
