package com.sapient.promoengine.service;

import java.util.List;

import com.sapient.promoengine.entity.PEPromotion;
import com.sapient.promoengine.rest.entity.OrderDTO;

public interface PEPromotionService {
	
	List<PEPromotion> getApplicablePromotionsForOrder(OrderDTO order);

}
