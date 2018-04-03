package com.sapient.promoengine.service;

import java.util.List;

import com.sapient.promoengine.entity.PEOrderPromotion;
import com.sapient.promoengine.rest.entity.OrderDTO;
import com.sapient.promoengine.rest.entity.PromotionDTO;

public interface PromotionEngineService {

	PromotionDTO applyPromotion(OrderDTO orderDTO);
	
	List<PEOrderPromotion> getAppliedPromotionsForOrder(String orderId);
}
