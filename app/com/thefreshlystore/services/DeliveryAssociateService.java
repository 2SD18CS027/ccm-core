package com.thefreshlystore.services;

import com.google.inject.ImplementedBy;
import com.thefreshlystore.dtos.request.AssociateLoginRequest;
import com.thefreshlystore.dtos.request.ChangeOrderStatusRequest;
import com.thefreshlystore.dtos.response.AssociateLoginResponse;
import com.thefreshlystore.models.DeliveryAssociate;

@ImplementedBy(DeliveryAssociateServiceImpl.class)
public interface DeliveryAssociateService extends BaseService<DeliveryAssociate> {
	public AssociateLoginResponse login(AssociateLoginRequest request);
	public void updateOrderStatus(ChangeOrderStatusRequest request);
}
