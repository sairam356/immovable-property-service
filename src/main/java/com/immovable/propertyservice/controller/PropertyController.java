package com.immovable.propertyservice.controller;

import java.util.ArrayList;
import java.util.List;

import javax.crypto.spec.PSource;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.immovable.propertyservice.entities.Property;
import com.immovable.propertyservice.entities.PropertyMetaData;
import com.immovable.propertyservice.entities.PropertyRevenueInfo;
import com.immovable.propertyservice.entities.PropertyStakeInfo;
import com.immovable.propertyservice.repo.PropertyMetaDataRepoistory;
import com.immovable.propertyservice.repo.PropertyRepoistory;
import com.immovable.propertyservice.repo.PropertyRevenueInfoRepository;
import com.immovable.propertyservice.repo.PropertyStakeInfoRepository;

@RestController
public class PropertyController {
	
	@Autowired
	PropertyRepoistory repository;
	
	@Autowired
	PropertyMetaDataRepoistory  propertyMetaDataRepoistory;
	
	
	@Autowired
	PropertyRevenueInfoRepository propertyRevenueInfoRepository;
	
	
	
	@Autowired
	PropertyStakeInfoRepository  propertyStakeInfoRepository;

	@GetMapping("/{id}")
	public void test(@PathVariable String id) {
		
		
		Property p = new Property();
		p.setName("test");
		
		
		
		
		PropertyMetaData pm = new PropertyMetaData();
		pm.setDescription2("123");
	
		p.setPropertyMetaData(propertyMetaDataRepoistory.save(pm));
		
	
		
		if(id!=null) {
		List<PropertyRevenueInfo> liPr = new ArrayList<>();
		
		PropertyRevenueInfo pr = new PropertyRevenueInfo();
		pr.setMonth("May");
		
		liPr.add(propertyRevenueInfoRepository.save(pr));
		p.setPropertyRevenueInfo(liPr);
		
		}
		List<PropertyStakeInfo> liSt = new ArrayList<>();
		
		PropertyStakeInfo psi = new PropertyStakeInfo();
	    psi.setStake_avaliable("50.00");
		liSt.add(propertyStakeInfoRepository.save(psi));
		p.setPropertyStakeInfo(liSt);
		
		System.out.println(p);
		
		repository.save(p);
		
		
		
	}
	
}
