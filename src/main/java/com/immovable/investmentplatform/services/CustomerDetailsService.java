package com.immovable.investmentplatform.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.immovable.investmentplatform.dto.CustomUser;
import com.immovable.investmentplatform.entities.UserEntity;
import com.immovable.investmentplatform.repos.UserRepository;

@Service
public class CustomerDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository repository;

	@Override
	public CustomUser loadUserByUsername(final String username) throws UsernameNotFoundException {
		List<GrantedAuthority> grantedAuthoritiesList = new ArrayList<>();
		UserEntity userEntity = repository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

		GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("ROLE_SYSTEMADMIN");
		grantedAuthoritiesList.add(grantedAuthority);
		userEntity.setGrantedAuthoritiesList(grantedAuthoritiesList);
		return new CustomUser(userEntity);

	}
}