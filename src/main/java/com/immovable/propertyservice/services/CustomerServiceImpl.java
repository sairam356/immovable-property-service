package com.immovable.propertyservice.services;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.immovable.propertyservice.entities.Customer;
import com.immovable.propertyservice.entities.Wallet;
import com.immovable.propertyservice.repo.CustomerRepoistory;
@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	CustomerRepoistory custRepository;

	@Autowired
	private WalletService walletService;

	@Override
	@Transactional
	public Map<String, String> saveCustomer(Customer customer) {
		Map<String, String> respMap = new HashMap<>();
		try {

			Customer saveCustomerObj = saveCustomerObj(customer);

			Wallet walletObj = getWalletObj(saveCustomerObj);
			
			saveCustomerObj.setWallet(walletObj);
			
			custRepository.save(saveCustomerObj);
			
			respMap.put("status", "success");

		} catch (Exception e) {
			respMap.put("status", "failure");
		}
		return respMap;
	}

	public Wallet getWalletObj(Customer customer) {
		Wallet wallet = new Wallet();
		wallet.setCustomerId(customer.getId());
		return walletService.save(wallet);
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public Customer saveCustomerObj(Customer customer) {
		Customer custobj = custRepository.save(customer);

		return custobj;
	}

}
