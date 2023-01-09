package com.immovable.propertyservice.services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.immovable.propertyservice.entities.Property;
import com.immovable.propertyservice.entities.PropertyMetaData;
import com.immovable.propertyservice.entities.PropertyRevenueInfo;
import com.immovable.propertyservice.entities.PropertyStakeInfo;
import com.immovable.propertyservice.repo.PropertyMetaDataRepoistory;
import com.immovable.propertyservice.repo.PropertyRepoistory;
import com.immovable.propertyservice.repo.PropertyRevenueInfoRepository;
import com.immovable.propertyservice.repo.PropertyStakeInfoRepository;

@Service
public class PropertyServiceImpl implements PropertyService {

	@Autowired
	PropertyRepoistory repository;

	@Autowired
	PropertyMetaDataRepoistory propertyMetaDataRepoistory;

	@Autowired
	PropertyRevenueInfoRepository propertyRevenueInfoRepository;

	@Autowired
	PropertyStakeInfoRepository propertyStakeInfoRepository;

	@Override
	public Map<String, Property> getPropertyById(String id) {
		
	    Map<String ,Property> map = new HashMap<String, Property>();
		Optional<Property> propertyById = repository.findById(id);
		
		if(propertyById.isPresent()) {
			map.put(id,propertyById.get());
			return map;
		}
		map.put(id, new Property());
		return  map ;
		
	}

	@Override
	public List<Property> getPropertyData() {
		// TODO Auto-generated method stub
		return repository.findAll();
	}

	@Override
	public Property savePropertyData(Property property) {

		PropertyMetaData pm = property.getPropertyMetaData();

		property.setPropertyMetaData(propertyMetaDataRepoistory.save(pm));

		List<PropertyRevenueInfo> propertyRevenueInfo = savePropertyRevenueInfo(property);

		List<PropertyStakeInfo> propertyStakeInfo = property.getPropertyStakeInfo();
		if (propertyRevenueInfo.size() > 0) {
			property.setPropertyRevenueInfo(propertyRevenueInfo);
		}
		if (propertyStakeInfo.size() > 0) {
			property.setPropertyStakeInfo(propertyStakeInfo);
		}

		return repository.save(property);

	}

	private List<PropertyRevenueInfo> savePropertyRevenueInfo(Property property) {
		List<PropertyRevenueInfo> list = new ArrayList<>();
		if (!property.getPropertyRevenueInfo().isEmpty() && property.getPropertyRevenueInfo().size() > 0) {
			property.getPropertyRevenueInfo().forEach(x -> {
				list.add(propertyRevenueInfoRepository.save(x));

			});
		}

		return list;

	}

	private List<PropertyStakeInfo> savePropertyStakeInfo(Property property) {
		List<PropertyStakeInfo> list = new ArrayList<>();
		if (!property.getPropertyStakeInfo().isEmpty() && property.getPropertyStakeInfo().size() > 0) {
			property.getPropertyStakeInfo().forEach(x -> {
				list.add(propertyStakeInfoRepository.save(x));

			});
		}
		return list;

	}
	
	
	public void test() {
		

		Property p = new Property();
		p.setName("RentedFlat");
		p.setCountry("India");
		p.setCurrency("Rupees");
		p.setImageUrl("http://drive.google.com/uc?export=view&id=13V4GL1fev3j0E2sy6ZdkMlvSQY4ZkbJf");
		p.setActualpropertyPrice(new BigDecimal("1000000"));
		p.setTranscationCostPrice(new BigDecimal("12312"));        
	    p.setTotalInvestmentCost(p.getActualpropertyPrice().add(p.getTranscationCostPrice()));
		p.setOccupancyRate("80%");
		p.setStatus("Active");
		p.setState("Pune");
		
		
		
		
		
		PropertyMetaData pm = new PropertyMetaData();
		pm.setDescription1("1 Bed in Index Tower DIFC :  1 BedRoom | 2 BathRoom | 1,333 sqft | DIFC : Pune India  : It has more occupany rate and fully furinished Apartment ");
		pm.setDescription2("Residental Area : Professionally managed  rental for families"); 
	    pm.setAnnualGrossRent(new BigDecimal("120000"));
	    pm.setAnnualNetIncome(new BigDecimal("100000"));
	    pm.setMaintainceCharges(new BigDecimal("1000"));
	    pm.setSerivceCharges(new BigDecimal("1000"));
		p.setPropertyMetaData(propertyMetaDataRepoistory.save(pm));
		
		
		

		List<PropertyRevenueInfo> liPr = new ArrayList<>();
		
		PropertyRevenueInfo pr = new PropertyRevenueInfo();
		pr.setMonth("Dec");
		pr.setYear("2022");
		pr.setActualAmount(new BigDecimal("12000"));
		pr.setReceivedAmount(new BigDecimal("12000"));
		pr.setReceivedDate(new Date());
		
		liPr.add(propertyRevenueInfoRepository.save(pr));
		p.setPropertyRevenueInfo(liPr);
		
		
		List<PropertyStakeInfo> liSt = new ArrayList<>();
		
		PropertyStakeInfo psi = new PropertyStakeInfo();
	    psi.setStake_avaliable(100.00);
	    psi.setStake_funded(0.00);
	    psi.setCreatedDt(new Date());
		liSt.add(propertyStakeInfoRepository.save(psi));
		p.setPropertyStakeInfo(liSt);
		
		System.out.println(p);
		
		repository.save(p);
		

	}

}
