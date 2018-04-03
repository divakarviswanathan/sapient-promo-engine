package com.sapient.promoengine.service;

import java.util.List;

import com.sapient.promoengine.entity.PEOrderPromotion;

public interface PEOrderPromotionService {
	
	PEOrderPromotion saveOrderPromotion(PEOrderPromotion peOrderPromotion);
	
	List<PEOrderPromotion> getAppliedPromotionsByOrder(String orderId);
}
