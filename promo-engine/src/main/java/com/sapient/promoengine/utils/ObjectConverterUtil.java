package com.sapient.promoengine.utils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.sapient.promoengine.commons.PromotionConstants;
import com.sapient.promoengine.entity.PEOrderPromotion;
import com.sapient.promoengine.rest.entity.ItemDTO;
import com.sapient.promoengine.rest.entity.OrderDTO;
import com.sapient.promoengine.rest.entity.PromotionDTO;

@Component
public class ObjectConverterUtil {
	
	public PromotionDTO convertOrderPromotion(PEOrderPromotion orderPromo) {
		PromotionDTO promotionDTO = new PromotionDTO();
		promotionDTO.setDescription(orderPromo.getPromoDescription());
		promotionDTO.setPromoCode(orderPromo.getPromoCode());
		promotionDTO.setOfferValue(orderPromo.getOfferValue());
		return promotionDTO;
	}
	
	public Map<String, Serializable> getOrderValueMap(OrderDTO order) {
		Map<String, Serializable> orderMap = new HashMap<>();
		orderMap.put(PromotionConstants.MIN_TOTAL_VALUE, order.getTotalValue());
		orderMap.put(PromotionConstants.BANK_CODE, order.getBankCode());
		orderMap.put(PromotionConstants.PAYMENT_MODE, order.getPaymentMode());
		return orderMap;
	}
	
	public Map<String, Serializable> getItemValueMap(OrderDTO order, ItemDTO item) {
		Map<String, Serializable> orderMap = new HashMap<>();
		orderMap.put(PromotionConstants.MIN_TOTAL_VALUE, item.getPrice());
		orderMap.put(PromotionConstants.BANK_CODE, order.getBankCode());
		orderMap.put(PromotionConstants.PAYMENT_MODE, order.getPaymentMode());
		orderMap.put(PromotionConstants.BRAND, item.getBrand());
		orderMap.put(PromotionConstants.ITEM, item.getName());
		return orderMap;
	}

}
