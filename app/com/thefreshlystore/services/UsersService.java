package com.thefreshlystore.services;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import com.google.inject.ImplementedBy;
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
import com.thefreshlystore.models.UserSessions;
import com.thefreshlystore.models.Users;

@ImplementedBy(UsersServiceImpl.class)
public interface UsersService extends BaseService<Users> {
	public LoginOrCreateUserResponse loginOrCreateUser(LoginOrCreateUserRequest convertValue) throws TheFreshlyStoreException;
	public UserSessions getUserSessionByToken(String token);
	public void deleteUserSessionByToken(String token);
	public AddAddressResponse addAddress(AddAddressRequestDTO request, BigInteger userId);
	public String getAddressFromAddressId(String addressId, BigInteger userId);
	public Map<BigInteger, String> getAddressIdFromAddressId(List<BigInteger> addressIds);
	public MyAddressesResponse getMyAddresses(BigInteger userId);
	public void deleteUserAddress(BigInteger userId, DeleteUserAddressRequest request);
	public void editUserDetails(BigInteger userId, EditUserDetailsRequest request);
	public Map<BigInteger,Users> getUserDetailsFromUserIds(List<BigInteger> userIds);
	public LoginOrCreateUserResponse captureUserDetails(CaptureUserDetailsRequest request);
	public void editUserAddress(BigInteger userId, EditUserAddressRequest request);
}
