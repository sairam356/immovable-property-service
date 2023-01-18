package com.immovable.propertyservice.services;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.immovable.propertyservice.dto.PropertyResponseDTO;
import com.immovable.propertyservice.dto.PropertyStakeReqDTO;
import com.immovable.propertyservice.entities.Property;
import com.immovable.propertyservice.entities.PropertyMetaData;
import com.immovable.propertyservice.entities.PropertyRevenueInfo;
import com.immovable.propertyservice.entities.PropertyStakeInfo;
import com.immovable.propertyservice.enums.TransactionType;
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
	public PropertyResponseDTO getPropertyById(String id) {
		PropertyResponseDTO responseDTO = new PropertyResponseDTO();
		Optional<Property> propertyById = repository.findById(id);
		if (propertyById.isPresent()) {

			responseDTO.setProperty(propertyById.get());
			return responseDTO;
		}
		return responseDTO;

	}

	@Override
	public List<Property> getPropertyData() {
		return repository.findAll();
	}

	@Override
	public Property savePropertyData(Property property) {

		PropertyMetaData pm = property.getPropertyMetaData();

		property.setPropertyMetaData(propertyMetaDataRepoistory.save(pm));
		property.setPropertyStakeInfo(savePropertyStakeInfo(property));

		List<PropertyRevenueInfo> propertyRevenueInfo = savePropertyRevenueInfo(property);
		if (propertyRevenueInfo.size() > 0) {
			property.setPropertyRevenueInfo(propertyRevenueInfo);
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

	private PropertyStakeInfo savePropertyStakeInfo(Property property) {

		return propertyStakeInfoRepository.save(property.getPropertyStakeInfo());

	}

	@Override
	public Map<String, BigDecimal> updateStakeInfo(PropertyStakeReqDTO propertyStakeReqDTO) {

		Map<String, BigDecimal> resultMap = new HashMap<>();

		try {

			Optional<Property> prodId = repository.findById(propertyStakeReqDTO.getPropertyId());
			if (prodId.isPresent()) {
				Property propertyObj = prodId.get();
				BigDecimal propetyInvestAmount = propertyObj.getTotalInvestmentCost();

				BigDecimal custPer = propertyStakeReqDTO.getInvestmentAmount().divide(propetyInvestAmount, 2,
						RoundingMode.HALF_UP);

				PropertyStakeInfo updatePropertyStakeInfo = updatePropertyStakeInfo(propertyObj, propertyStakeReqDTO);

				propertyObj.setPropertyStakeInfo(updatePropertyStakeInfo);

				repository.save(propertyObj);
				resultMap.put("customerStack", custPer);
			}

		} catch (Exception e) {
			resultMap.put("status", new BigDecimal("0.00"));
			e.printStackTrace();

		}
		return resultMap;
	}

	private PropertyStakeInfo updatePropertyStakeInfo(Property propertyObj, PropertyStakeReqDTO propertyStakeReqDTO) {

		BigDecimal propetyInvestAmount = propertyObj.getTotalInvestmentCost();
		PropertyStakeInfo obj = new PropertyStakeInfo();
		PropertyStakeInfo propertyStakeInfo = propertyObj.getPropertyStakeInfo();
		if (propertyStakeReqDTO.getTranscationType().equalsIgnoreCase("BUY")) {
			BigDecimal StackInvestmentAmount = propertyStakeInfo.getTotalInvestmentAmount()
					.add(propertyStakeReqDTO.getInvestmentAmount());
			BigDecimal totalAvaliableAmount = propetyInvestAmount.subtract(StackInvestmentAmount);

			BigDecimal fundedAmtPer = StackInvestmentAmount.divide(propetyInvestAmount, 2, RoundingMode.HALF_UP);

			BigDecimal stackAvaliPer = totalAvaliableAmount.divide(propetyInvestAmount, 2, RoundingMode.HALF_UP);

			BigDecimal avalible = stackAvaliPer.multiply(new BigDecimal("100"));
			BigDecimal multiply = fundedAmtPer.multiply(new BigDecimal("100"));
			obj.setStake_avaliable(avalible);
			obj.setStake_funded(multiply);
			obj.setTotalAvaliableAmount(totalAvaliableAmount);
			obj.setTotalInvestmentAmount(StackInvestmentAmount);
			obj.setCreatedDt(new Date());
			obj.setId(propertyStakeInfo.getId());
		} else {
			BigDecimal StackInvestmentAmount = propertyStakeInfo.getTotalInvestmentAmount()
					.subtract(propertyStakeReqDTO.getInvestmentAmount());
			BigDecimal totalAvaliableAmount = propetyInvestAmount.add(StackInvestmentAmount);

			BigDecimal fundedAmtPer = StackInvestmentAmount.divide(propetyInvestAmount, 2, RoundingMode.HALF_UP);

			BigDecimal stackAvaliPer = totalAvaliableAmount.divide(propetyInvestAmount, 2, RoundingMode.HALF_UP);

			BigDecimal avalible = stackAvaliPer.multiply(new BigDecimal("100"));
			BigDecimal multiply = fundedAmtPer.multiply(new BigDecimal("100"));
			obj.setStake_avaliable(avalible);
			obj.setStake_funded(multiply);
			obj.setTotalAvaliableAmount(totalAvaliableAmount);
			obj.setTotalInvestmentAmount(StackInvestmentAmount);
			obj.setCreatedDt(new Date());
			obj.setId(propertyStakeInfo.getId());
		}
		return propertyStakeInfoRepository.save(obj);
	}

	public void dummyApiToCreateAProperty() {

		Property p = new Property();
		p.setName("RentedProperty");
		p.setCountry("India");
		p.setCurrency("Rupees");
		p.setImageUrl("http://drive.google.com/uc?export=view&id=13V4GL1fev3j0E2sy6ZdkMlvSQY4ZkbJf");
		p.setActualpropertyPrice(new BigDecimal("1000000"));
		p.setTranscationCostPrice(new BigDecimal("100"));
		p.setTotalInvestmentCost(p.getActualpropertyPrice().add(p.getTranscationCostPrice()));
		p.setOccupancyRate(new BigDecimal("70"));
		p.setStatus("Active");
		p.setState("Pune");

		PropertyMetaData pm = new PropertyMetaData();
		pm.setDescription1(
				"1 Bed in Index Tower DIFC :  1 BedRoom | 2 BathRoom | 1,333 sqft | DIFC : Pune India  : It has more occupany rate and fully furinished Apartment ");
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

		PropertyStakeInfo psi = new PropertyStakeInfo();
		psi.setStake_avaliable(new BigDecimal("100.00"));
		psi.setStake_funded(new BigDecimal("0.00"));
		psi.setTotalInvestmentAmount(new BigDecimal("0.00"));
		psi.setTotalAvaliableAmount(p.getTotalInvestmentCost());
		psi.setCreatedDt(new Date());

		p.setPropertyStakeInfo(propertyStakeInfoRepository.save(psi));

		System.out.println(p);

		repository.save(p);

	}

}
