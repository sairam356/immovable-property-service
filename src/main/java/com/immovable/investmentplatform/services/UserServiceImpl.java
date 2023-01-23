package com.immovable.investmentplatform.services;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.immovable.investmentplatform.dto.CustomUser;
import com.immovable.investmentplatform.dto.Mail;
import com.immovable.investmentplatform.dto.TranscationRequest;
import com.immovable.investmentplatform.dto.UserRequest;
import com.immovable.investmentplatform.dto.UserResponse;
import com.immovable.investmentplatform.entities.Customer;
import com.immovable.investmentplatform.entities.UserEntity;
import com.immovable.investmentplatform.repos.CustomerRepoistory;
import com.immovable.investmentplatform.repos.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository repository;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private EmailService emailService;

	@Autowired
	private CustomerRepoistory custRepo;

	@Value("${app.banking-service.url}")
	private String baseUrl;
	@Autowired
	private RestTemplate restTemplate;

	@Override
	public UserResponse findUserByUserName(String userName) {

		Optional<UserEntity> userEntity = repository.findByUsername(userName);

		if (userEntity.isPresent()) {
			UserResponse response = new UserResponse();
			UserEntity entity = userEntity.get();
			response.setActive(entity.getActive() == 1 ? true : false);
			response.setCustomerId(entity.getCustomerId());
			response.setEmail(entity.getEmail());
			response.setFirstName(entity.getFirstName());
			response.setAddress(entity.getAddress());
			response.setId(entity.getId());
			response.setPassport(entity.getPassport());
			response.setLastName(entity.getLastName());
			response.setPhone(entity.getPhone());
			response.setUsername(entity.getUsername());
			return response;
		}
		return null;
	}

	@Override
	public boolean loginUser(String username, String password) {

		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(username, password));

		SecurityContextHolder.getContext().setAuthentication(authentication);

		CustomUser userDetails = (CustomUser) authentication.getPrincipal();
		return userDetails.isAccountNonExpired();
	}

	@Autowired
	private PasswordEncoder encoder;

	@Override
	public UserEntity createUser(UserRequest request) {

		UserEntity entity = new UserEntity();

		entity.setCustomerId("IM" + RandomUtils.nextInt());
		entity.setEmail(request.getEmail());
		entity.setFirstName(request.getFirstName());
		entity.setLastName(request.getLastName());
		entity.setAddress(request.getAddress());
		entity.setPassword(encoder.encode(request.getPassword()));
		entity.setPhone(request.getPhone());
		entity.setPassport(request.getPassport());
		entity.setRole("ROLE_USER");
		entity.setActive(1);
		entity.setUsername(request.getUsername());

		UserEntity userObj = repository.save(entity);
		
		callBankService(request);

		return userObj;
	}

	private void callBankService(UserRequest request) {

		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<UserRequest> req = new HttpEntity<UserRequest>(request, headers);
		restTemplate.exchange(baseUrl + "/create", HttpMethod.POST, req, String.class).getBody();
	}

	@Override
	public boolean checkUserExists(String userName, String email) {

		Optional<UserEntity> entity = repository.findByUsername(userName);

		Optional<UserEntity> emailEntity = repository.findByEmail(email);

		return (entity.isPresent() && emailEntity.isPresent()) ? true : false;
	}

	@Override
	public String resetPassword(String email) {
		Optional<UserEntity> entity = repository.findByEmail(email);
		if (!entity.isPresent()) {
			return "USER_NOT_FOUND";
		}

		Mail mail = new Mail();
		mail.setFrom("jbansode23@gmail.com");
		mail.setTo(entity.get().getEmail());
		mail.setSubject("Password reset request");

		Map<String, Object> model = new HashMap<>();
		model.put("token", "123");
		model.put("user", entity.get());
		model.put("signature", "https://www.google.com/");
		model.put("resetUrl",
				"https://immovable-property-service-kushalbajji-dev.apps.sandbox.x8i5.p1.openshiftapps.com/swagger-ui/index.html");
		mail.setModel(model);

		emailService.sendEmail(mail);

		return "Password reset email sent !!";
	}

	@Override
	public UserResponse updateUser(UserEntity request) {

		Optional<UserEntity> findById = repository.findById(request.getId());
		UserEntity userEntity = findById.get();

		userEntity.setFirstName(request.getFirstName());
		userEntity.setLastName(request.getLastName());
		userEntity.setAddress(request.getAddress());
		userEntity.setEmail(request.getEmail());
		userEntity.setPhone(request.getPhone());

		UserEntity userObj = repository.save(userEntity);

		List<Customer> custList = custRepo.findByUserId(userObj.getId());
		if (custList.size() > 0) {

			Customer customer = custList.get(0);

			customer.setPassport(userObj.getPassport());
			customer.setAddress(userObj.getPassport());
			customer.setFirstName(userObj.getFirstName());
			customer.setLastName(userObj.getLastName());

			custRepo.save(customer);
		}

		UserResponse response = new UserResponse();

		response.setActive(userObj.getActive() == 1 ? true : false);
		response.setCustomerId(userObj.getCustomerId());
		response.setEmail(userObj.getEmail());
		response.setFirstName(userObj.getFirstName());
		response.setAddress(userObj.getAddress());
		response.setId(userObj.getId());
		response.setPassport(userObj.getPassport());
		response.setLastName(userObj.getLastName());
		response.setPhone(userObj.getPhone());
		response.setUsername(userObj.getUsername());

		return response;
	}

}
