package com.thefreshlystore.services;

import com.google.inject.ImplementedBy;
import com.thefreshlystore.dtos.request.AddDeliveryAssociateRequest;
import com.thefreshlystore.dtos.request.AdministratorLoginRequest;
import com.thefreshlystore.dtos.request.ChangeOrderStatusRequest;
import com.thefreshlystore.dtos.response.AdministratorLoginResponse;
import com.thefreshlystore.dtos.response.TodaysReportResponse;
import com.thefreshlystore.models.Administrator;
import com.thefreshlystore.utilities.PasswordUtility;

@ImplementedBy(AdministratorServiceImpl.class)
public interface AdministratorService extends BaseService<Administrator> {
	public AdministratorLoginResponse login(AdministratorLoginRequest request, PasswordUtility passwordUtility);
	public TodaysReportResponse getTodaysReport();
	public void addDeliveryAssociate(AddDeliveryAssociateRequest request);
	public void updateOrderStatus(ChangeOrderStatusRequest request);
}
