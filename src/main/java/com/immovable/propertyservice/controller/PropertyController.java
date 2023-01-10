package com.immovable.propertyservice.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.immovable.propertyservice.entities.Property;
import com.immovable.propertyservice.services.PropertyService;

@RestController
@RequestMapping("/properties")
public class PropertyController {

	@Autowired
	PropertyService propertyService;

	@GetMapping("/{id}")
	public Map<String, Property> getPropertyById(@PathVariable String id) {
		return propertyService.getPropertyById(id);

	}

	@PostMapping
	public Property saveProperty(@RequestBody Property property) {
		return propertyService.savePropertyData(property);

	}

	@GetMapping
	public List<Property> getPropertyData() {
		return propertyService.getPropertyData();

	}

	@GetMapping("/dummyApiPropCreate")
	public void dummyApiToCreateAProperty() {
		propertyService.dummyApiToCreateAProperty();
	}

}
