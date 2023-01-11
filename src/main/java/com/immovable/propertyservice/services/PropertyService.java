package com.immovable.propertyservice.services;

import java.util.List;
import java.util.Map;

import com.immovable.propertyservice.dto.PropertyResponseDTO;
import com.immovable.propertyservice.dto.PropertyStakeReqDTO;
import com.immovable.propertyservice.entities.Property;

public interface PropertyService {

	public Property  savePropertyData(Property property);
	
	public List<Property> getPropertyData();
	
	public  PropertyResponseDTO getPropertyById(String id);
	
	public Map<String, String> updateStakeInfo(PropertyStakeReqDTO propertyStakeReqDTO);
	
	public void dummyApiToCreateAProperty();
	
}
