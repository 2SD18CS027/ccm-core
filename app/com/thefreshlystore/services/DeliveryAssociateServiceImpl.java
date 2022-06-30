package com.thefreshlystore.services;

import java.util.UUID;

import javax.inject.Inject;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thefreshlystore.daos.DeliveryAssociateDAO;
import com.thefreshlystore.dtos.request.AssociateLoginRequest;
import com.thefreshlystore.dtos.request.ChangeOrderStatusRequest;
import com.thefreshlystore.dtos.response.AssociateLoginResponse;
import com.thefreshlystore.exceptions.TheFreshlyStoreException;
import com.thefreshlystore.models.DeliveryAssociate;
import com.thefreshlystore.models.Orders;
import com.thefreshlystore.models.Users;
import com.thefreshlystore.utilities.CustomObjectMapper;
import com.thefreshlystore.utilities.PasswordUtility;
import com.thefreshlystore.utilities.TheFreshlyStoreConstants.ApiFailureMessages;
import com.thefreshlystore.utilities.TheFreshlyStoreConstants.OrderStatus;

public class DeliveryAssociateServiceImpl extends BaseServiceImpl<DeliveryAssociate> implements DeliveryAssociateService {
	private final DeliveryAssociateDAO deliveryAssociateDAO;
	private final CustomObjectMapper objectMapper;
	private final PasswordUtility passwordUtility;
	private final OrdersService ordersService;
	private final UsersService usersService;
	
	@Inject
	public DeliveryAssociateServiceImpl(DeliveryAssociateDAO deliveryAssociateDAO, CustomObjectMapper objectMapper,
			PasswordUtility passwordUtility, OrdersService ordersService, UsersService usersService) {
		super(deliveryAssociateDAO);
		this.deliveryAssociateDAO = deliveryAssociateDAO;
		this.objectMapper = objectMapper;
		this.passwordUtility = passwordUtility;
		this.ordersService = ordersService;
		this.usersService = usersService;
	}

	@Override
	public AssociateLoginResponse login(AssociateLoginRequest request) {
		DeliveryAssociate deliveryAssociate = getByFieldName("mobile_number", request.mobileNumber, false);
		if(deliveryAssociate == null) {
			throw new TheFreshlyStoreException(ApiFailureMessages.DeliveryAssociate.DELIVERY_ASSOCIATE_NOT_FOUND);
		}
		if(!(passwordUtility.isPasswordSame(request.password, deliveryAssociate.getPassword()))) {
			throw new TheFreshlyStoreException(ApiFailureMessages.DeliveryAssociate.INVALID_PASSWORD);
		}
		deliveryAssociate.setToken(UUID.randomUUID().toString().replaceAll("-", ""));
		update(deliveryAssociate);
		ObjectMapper mapper = objectMapper.getInstance();
		return mapper.convertValue(deliveryAssociate, AssociateLoginResponse.class);
	}

	@Override
	public void updateOrderStatus(ChangeOrderStatusRequest request) {
		Orders order = ordersService.getByFieldName("order_id", request.orderId, true);
		if(order.getStatus() != OrderStatus.ORDER_ON_THE_WAY_TO_CUSTOMER) {
			throw new TheFreshlyStoreException(ApiFailureMessages.CANNOT_PERFORM_OPERATION);
		}
		Users user = usersService.getByFieldName("id", order.getUserId(), true);
		ordersService.changeOrderStatus(request, order, user);
	}
}
