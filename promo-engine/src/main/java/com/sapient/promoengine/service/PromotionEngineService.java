package com.sapient.promoengine.service;

import com.sapient.promoengine.rest.entity.OrderDTO;
import com.sapient.promoengine.rest.entity.PromotionDTO;

public interface PromotionEngineService {

	PromotionDTO applyPromotion(OrderDTO orderDTO);
}
