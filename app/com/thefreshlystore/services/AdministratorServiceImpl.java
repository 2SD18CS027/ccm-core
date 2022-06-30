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

import com.google.common.util.concurrent.AtomicDouble;
import com.thefreshlystore.daos.AdministratorDAO;
import com.thefreshlystore.dtos.request.AddDeliveryAssociateRequest;
import com.thefreshlystore.dtos.request.AdministratorLoginRequest;
import com.thefreshlystore.dtos.request.ChangeOrderStatusRequest;
import com.thefreshlystore.dtos.response.AdministratorLoginResponse;
import com.thefreshlystore.dtos.response.TodaysReportResponse;
import com.thefreshlystore.exceptions.TheFreshlyStoreException;
import com.thefreshlystore.models.Administrator;
import com.thefreshlystore.models.DeliveryAssociate;
import com.thefreshlystore.models.Items;
import com.thefreshlystore.models.OrderItems;
import com.thefreshlystore.models.Orders;
import com.thefreshlystore.models.ServiceableAreas;
import com.thefreshlystore.models.Users;
import com.thefreshlystore.utilities.PasswordUtility;
import com.thefreshlystore.utilities.TheFreshlyStoreConstants.ApiFailureMessages;
import com.thefreshlystore.utilities.TheFreshlyStoreConstants.OrderStatus;

public class AdministratorServiceImpl extends BaseServiceImpl<Administrator> implements AdministratorService {
	private final AdministratorDAO administratorDAO;
	private final OrdersService ordersService;
	private final ItemsService itemsService;
	private final UsersService usersService;
	private final DeliveryAssociateService deliveryAssociateService;
	private final PasswordUtility passwordUtility;
	private final ServiceableAreasService serviceableAreasService;
	
	@Inject
	public AdministratorServiceImpl(AdministratorDAO administratorDAO, OrdersService ordersService,
			ItemsService itemsService, UsersService usersService, PasswordUtility passwordUtility,
			DeliveryAssociateService deliveryAssociateService, ServiceableAreasService serviceableAreasService) {
		super(administratorDAO);
		this.administratorDAO = administratorDAO;
		this.ordersService = ordersService;
		this.itemsService = itemsService;
		this.usersService = usersService;
		this.deliveryAssociateService = deliveryAssociateService;
		this.passwordUtility = passwordUtility;
		this.serviceableAreasService = serviceableAreasService;
	}

	@Override
	public AdministratorLoginResponse login(AdministratorLoginRequest request, PasswordUtility passwordUtility) {
		Administrator administrator = getByFieldName("username", request.username, true);
		if(!(passwordUtility.isPasswordSame(request.password, administrator.getPassword()))) {
			throw new TheFreshlyStoreException(ApiFailureMessages.Administrator.ADMINISTRATOR_NOT_FOUND);
		}
		String token = UUID.randomUUID().toString().replaceAll("-", "");
		administrator.setToken(token);
		update(administrator);
		return new AdministratorLoginResponse(token);
	}

	@Override
	public TodaysReportResponse getTodaysReport() {
		TodaysReportResponse response = new TodaysReportResponse();
		List<Orders> orders = ordersService.getTodaysOrdersForDelivery();
		Set<BigInteger> orderIds = new HashSet<>();
		Set<BigInteger> userIds = new HashSet<>();
		Set<BigInteger> areaIds = new HashSet<>();
		orders.forEach((order) -> {
			userIds.add(order.getUserId());
			orderIds.add(order.getId());
			areaIds.add(order.getAreaId());
		});
		Map<BigInteger, List<OrderItems>> orderItems = ordersService.getOrderItems(new ArrayList<>(orderIds));
		Set<BigInteger> itemIds = new HashSet<>();
		orderItems.entrySet().forEach((map) -> {
			map.getValue().forEach((orderItem) -> {
				itemIds.add(orderItem.getItemId());
			});
		});
		Map<BigInteger, Items> itemDetails = itemsService.getItemDetailsFromItemId(new ArrayList<>(itemIds));
		Map<BigInteger,Users> userDetails = usersService.getUserDetailsFromUserIds(new ArrayList<>(userIds));
		Map<BigInteger, ServiceableAreas> serviceableAreasDetails = serviceableAreasService.getServiceableAreasById(new ArrayList<>(areaIds));
		
		Map<BigInteger, Integer> itemsToBuyCount = new HashMap<>();
		orders.forEach((order) -> {
			Users user = userDetails.get(order.getUserId());
			TodaysReportResponse.OrderDetails orderDetails = new TodaysReportResponse.OrderDetails(order.getOrderId(), order.getStatus(), order.getFromSlotTime().getTime(), order.getToSlotTime().getTime(), order.getAddress(), user.getName(), user.getMobileNumber(), serviceableAreasDetails.get(order.getAreaId()).getArea(), order.getId());
			AtomicDouble totalPrice = new AtomicDouble(0d);
			orderItems.get(order.getId()).forEach((orderItem) -> {
				Items item = itemDetails.get( orderItem.getItemId() );
				orderDetails.items.add( new TodaysReportResponse.ItemDetails(item.getItemId(), item.getName(), item.getImageUrl(), orderItem.getPrice(), orderItem.getQuantity(), item.getUom()));
				totalPrice.set( (totalPrice.get()) + ( orderItem.getPrice() * orderItem.getQuantity() ) );
				if(itemsToBuyCount.containsKey(item.getId())) {
					itemsToBuyCount.put(item.getId(), (itemsToBuyCount.get(item.getId())+orderItem.getQuantity()));
				} else {
					itemsToBuyCount.put(item.getId(), orderItem.getQuantity());
				}
			});
			orderDetails.totalPrice = totalPrice.get();
			response.orders.add( orderDetails );
		});
		
		itemsToBuyCount.entrySet().forEach((map) -> {
			Items item = itemDetails.get(map.getKey());
			response.itemsToBuy.add( new TodaysReportResponse.ItemsToBuy(item.getItemId(), item.getName(), item.getImageUrl(), map.getValue(), item.getUom()) );
		});
		return response;
	}

	@Override
	public void addDeliveryAssociate(AddDeliveryAssociateRequest request) {
		deliveryAssociateService.save( new DeliveryAssociate(request.name, (passwordUtility.generatePasswordHash(request.password)), request.mobileNumber) );
	}

	@Override
	public void updateOrderStatus(ChangeOrderStatusRequest request) {
		Orders order = ordersService.getByFieldName("order_id", request.orderId, true);
		if(order.getStatus()+1 != request.newOrderStatus) {
			throw new TheFreshlyStoreException(ApiFailureMessages.CANNOT_PERFORM_OPERATION);
		}
		Users user = usersService.getByFieldName("id", order.getUserId(), true);
		ordersService.changeOrderStatus(request, order, user);
	}
}
