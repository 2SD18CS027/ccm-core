package com.thefreshlystore.services;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.inject.Inject;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thefreshlystore.daos.UserAddressesDAO;
import com.thefreshlystore.daos.UserDAO;
import com.thefreshlystore.daos.UserSessionsDAO;
import com.thefreshlystore.daos.UserTempSessionsDAO;
import com.thefreshlystore.dtos.request.AddAddressRequestDTO;
import com.thefreshlystore.dtos.request.CaptureUserDetailsRequest;
import com.thefreshlystore.dtos.request.DeleteUserAddressRequest;
import com.thefreshlystore.dtos.request.EditUserAddressRequest;
import com.thefreshlystore.dtos.request.EditUserDetailsRequest;
import com.thefreshlystore.dtos.request.LoginOrCreateUserRequest;
import com.thefreshlystore.dtos.response.AddAddressResponse;
import com.thefreshlystore.dtos.response.LoginOrCreateUserResponse;
import com.thefreshlystore.dtos.response.MyAddressesResponse;
import com.thefreshlystore.exceptions.TheFreshlyStoreException;
import com.thefreshlystore.models.ServiceableAreas;
import com.thefreshlystore.models.UserAddresses;
import com.thefreshlystore.models.UserSessions;
import com.thefreshlystore.models.UserTempSessions;
import com.thefreshlystore.models.Users;
import com.thefreshlystore.utilities.CustomObjectMapper;
import com.thefreshlystore.utilities.FbAccountKitUtility;
import com.thefreshlystore.utilities.SesUtility;
import com.thefreshlystore.utilities.TheFreshlyStoreConstants.ApiFailureMessages;

import io.ebean.SqlRow;

public class UsersServiceImpl extends BaseServiceImpl<Users> implements UsersService {
	private final UserDAO userDAO;
	private final UserSessionsDAO userSessionsDAO;
	private final UserAddressesDAO userAddressesDAO;
	private final CustomObjectMapper objectMapper;
	private final FbAccountKitUtility fbAccountKitUtility;
	private final UserTempSessionsDAO userTempSessionsDAO;
	private final SesUtility sesUtility;
	private final ServiceableAreasService serviceableAreasService;
	
	@Inject
	public UsersServiceImpl(UserDAO userDAO, UserSessionsDAO userSessionsDAO, 
			UserAddressesDAO userAddressesDAO, CustomObjectMapper objectMapper,
			FbAccountKitUtility fbAccountKitUtility, UserTempSessionsDAO userTempSessionsDAO,
			SesUtility sesUtility, ServiceableAreasService serviceableAreasService) {
		super(userDAO);
		this.userDAO = userDAO;
		this.userSessionsDAO = userSessionsDAO;
		this.userAddressesDAO = userAddressesDAO;
		this.objectMapper = objectMapper;
		this.fbAccountKitUtility = fbAccountKitUtility;
		this.userTempSessionsDAO = userTempSessionsDAO;
		this.sesUtility = sesUtility;
		this.serviceableAreasService = serviceableAreasService;
	}

	@Override
	public LoginOrCreateUserResponse loginOrCreateUser(LoginOrCreateUserRequest request) throws TheFreshlyStoreException {
		Users user = userDAO.getByFieldName("mobile_number", request.mobileNumber);
		//fbAccountKitUtility.authenticateUserWithFbAccountKit(request.mobileNumber, request.authCode);
		String token = UUID.randomUUID().toString().replaceAll("-", "");
		if(user == null) {
			UserTempSessions userTempSession = userTempSessionsDAO.getByFieldName("mobile_number", request.mobileNumber);
			if(userTempSession == null) {
				userTempSession = new UserTempSessions(request.mobileNumber, token);
				userTempSessionsDAO.save(userTempSession);
			} else {
				userTempSession.setToken(token);
				userTempSessionsDAO.update(userTempSession);
			}
			return new LoginOrCreateUserResponse(token, false);
		} else {
			UserSessions session = userSessionsDAO.getUserSessionByUserIdAndDeviceTypeId(user.getId(), request.deviceTypeId);
			if(session == null) {
				session = new UserSessions(user.getId(), token, request.deviceTypeId);
				userSessionsDAO.save(session);
			} else {
				session.setToken(token);
				userSessionsDAO.update(session);
			}
			return new LoginOrCreateUserResponse(session.getToken(), true);
		}
	}

	@Override
	public void deleteUserSessionByToken(String token) {
		userSessionsDAO.deleteUserSession(token);
	}

	@Override
	public UserSessions getUserSessionByToken(String token) {
		return userSessionsDAO.getByFieldName("token", token);
	}

	@Override
	public AddAddressResponse addAddress(AddAddressRequestDTO request, BigInteger userId) {
		ServiceableAreas serviceableArea = serviceableAreasService.getByFieldName("area_id", request.areaId, true);
		UserAddresses userAddress = new UserAddresses(userId, request.address, serviceableArea.getId());
		userAddressesDAO.save(userAddress);
		userAddressesDAO.makeOtherAddressesInactive(userAddress.getId(), userId);
		return new AddAddressResponse(userAddress.getAddressId());
	}

	@Override
	public String getAddressFromAddressId(String addressId, BigInteger userId) {
		SqlRow row = userAddressesDAO.getAddressIdFromAddressId(addressId, userId);
		return (row == null) ? null : row.getString("address");
	}

	@Override
	public MyAddressesResponse getMyAddresses(BigInteger userId) {
		MyAddressesResponse response = new MyAddressesResponse();
		ObjectMapper mapper = objectMapper.getInstance();
		List<UserAddresses> userAddresses = userAddressesDAO.getByFieldName("user_id", userId, null, null, "-created_time");
		Set<BigInteger> areaIds = new HashSet<>();
		for(UserAddresses address : userAddresses) {
			areaIds.add( address.getAreaId() );
		}
		Map<BigInteger, ServiceableAreas> serviceableAreas = serviceableAreasService.getServiceableAreasById(new ArrayList<>(areaIds));
		for(UserAddresses address : userAddresses) {
			MyAddressesResponse.Addresses addresses = mapper.convertValue(address, MyAddressesResponse.Addresses.class);
			addresses.area = serviceableAreas.get(address.getAreaId()).getArea();
			addresses.areaId = serviceableAreas.get(address.getAreaId()).getAreaId();
			response.addresses.add( addresses );
		}
		return response;
	}

	@Override
	public Map<BigInteger, String> getAddressIdFromAddressId(List<BigInteger> addressIds) {
		Map<BigInteger, String> response = new HashMap<>();
		userAddressesDAO.getByFieldName("id", addressIds, null, null).forEach((userAddress) -> {
			response.put(userAddress.getId(), userAddress.getAddress());
		});
		return response;
	}

	@Override
	public void deleteUserAddress(BigInteger userId, DeleteUserAddressRequest request) {
		userAddressesDAO.deleteUserAddress(request.addressId, userId);
	}

	@Override
	public void editUserDetails(BigInteger userId, EditUserDetailsRequest request) {
		Users user = getById(userId, true);
		boolean isUpdate = false;
		if(request.name != null && request.name.length() > 2) {
			user.setName(request.name);
			isUpdate = true;
		}
		if(request.email != null && request.email.length() > 5) {
			user.setEmail(request.email);
			isUpdate = true;
		}
		if(isUpdate) {
			update(user);
		}
	}

	@Override
	public Map<BigInteger,Users> getUserDetailsFromUserIds(List<BigInteger> userIds) {
		Map<BigInteger,Users> response = new HashMap<>();
		getByFieldName("id", userIds, null, null).forEach((user) -> {
			response.put(user.getId(), user);
		});
		return response;
	}

	@Override
	public LoginOrCreateUserResponse captureUserDetails(CaptureUserDetailsRequest request) {
		if(getByFieldName("email", request.email, false) != null) {
			throw new TheFreshlyStoreException(ApiFailureMessages.Users.EMAIL_ALREADY_EXISTS);
		}
		UserTempSessions userTempSession = userTempSessionsDAO.getByFieldName("token", request.tempToken);
		if(userTempSession == null) {
			throw new TheFreshlyStoreException(ApiFailureMessages.Users.USER_NOT_FOUND);
		}
		Users user = getByFieldName("mobile_number", userTempSession.getMobileNumber(), false);
		if(user != null) {
			throw new TheFreshlyStoreException(ApiFailureMessages.Users.PHONE_NUMBER_ALREADY_EXISTS);
		}
		user = new Users(request.name, userTempSession.getMobileNumber(), request.email);
		save(user);
		String token = UUID.randomUUID().toString().replaceAll("-", "");
		UserSessions session = new UserSessions(user.getId(), token, request.deviceTypeId);
		userSessionsDAO.save(session);
		try {
			sesUtility.sendWelcomeEmail(request.email, request.name);
		} catch(Exception e) {}
		try {
			userTempSessionsDAO.delete(userTempSession);
		} catch(Exception e) {}
		return new LoginOrCreateUserResponse(session.getToken(), true);
	}

	@Override
	public void editUserAddress(BigInteger userId, EditUserAddressRequest request) {
		UserAddresses userAddress = userAddressesDAO.getByFieldName("address_id", request.addressId);
		if(userAddress == null) {
			throw new TheFreshlyStoreException(ApiFailureMessages.UsersAddresses.USER_ADDRESS_NOT_FOUND);
		}
		if(!(userAddress.getUserId().equals(userId))) {
			throw new TheFreshlyStoreException(ApiFailureMessages.CANNOT_PERFORM_OPERATION);
		}
		userAddress.setAddress(request.address);
		userAddressesDAO.save(userAddress);
	}
}
