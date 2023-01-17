package com.immovable.propertyservice.controller;

import java.math.BigDecimal;
import java.util.List;


import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.immovable.propertyservice.dto.PropertyResponseDTO;
import com.immovable.propertyservice.dto.PropertyStakeReqDTO;
import com.immovable.propertyservice.entities.Property;
import com.immovable.propertyservice.services.PropertyService;

@RestController
@RequestMapping("/properties")
public class PropertyController {

	@Autowired
	PropertyService propertyService;

	@GetMapping("/{id}")
	public PropertyResponseDTO getPropertyById(@PathVariable String id) {
		return propertyService.getPropertyById(id);

	}

	@PostMapping
    @ResponseStatus(HttpStatus.CREATED)
	public Property saveProperty(@RequestBody Property property) {
		return propertyService.savePropertyData(property);

	}

	@GetMapping
	public List<Property> getPropertyData() {
		return propertyService.getPropertyData();

	}

	@PostMapping("/allocateStake")
	public Map<String, BigDecimal> updateStakeInfo(@RequestBody PropertyStakeReqDTO propertyStakeReqDTO) {

		return propertyService.updateStakeInfo(propertyStakeReqDTO);

	}

	@GetMapping("/dummyApiPropCreate")
	@ResponseStatus(HttpStatus.CREATED)
	public void dummyApiToCreateAProperty() {
		propertyService.dummyApiToCreateAProperty();
	}

}
