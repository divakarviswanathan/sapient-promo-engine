package com.sapient.promoengine.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sapient.promoengine.commons.PromotionConstants;
import com.sapient.promoengine.entity.PEOrderPromotion;
import com.sapient.promoengine.entity.PEPromotion;
import com.sapient.promoengine.rest.entity.ItemDTO;
import com.sapient.promoengine.rest.entity.OrderDTO;
import com.sapient.promoengine.service.IOffer;
import com.sapient.promoengine.service.impl.RuleEngine;

@Component
public class OfferHelper {

	
	@Autowired
	private RuleEngine ruleEngine;
	
	@Autowired
	private ObjectConverterUtil objectConverterUtil; 
	
	public PEOrderPromotion applyOfferAtOrderLevel(OrderDTO order, PEPromotion pePromo, String desciptionString) {
		Map<String, Serializable> actualValueMap = objectConverterUtil.getOrderValueMap(order);
		
		double value = 0d;
		if(ruleEngine.checkIfOrderEligible(pePromo.getRuleMap(), actualValueMap)) {
			if(pePromo.getOfferValueMap().get(PromotionConstants.OFFER_TYPE).equals(PromotionConstants.PERCENTAGE)) {
				double rate = (double) pePromo.getOfferValueMap().get(PromotionConstants.OFFER_VALUE);
				value += IOffer.applyPercentage(order.getTotalValue(), rate);
			} else if(pePromo.getOfferValueMap().get(PromotionConstants.OFFER_TYPE).equals(PromotionConstants.HARD)) {
				double offerValue = (double) pePromo.getOfferValueMap().get(PromotionConstants.OFFER_VALUE);
				value += offerValue;
			}
		}
		
		return constructOrderPromotion(value, order.getOrderId(), pePromo.getPromoCode(), pePromo.getPromoType(), desciptionString);
	}
	
	private PEOrderPromotion constructOrderPromotion(double value, String orderId, String promoCode, String promoType, String desciptionString) {
		if (value >= 0) {
			PEOrderPromotion peOrderPromotion = new PEOrderPromotion();
			peOrderPromotion.setOrderId(orderId);
			peOrderPromotion.setPromoCode(promoCode);
			peOrderPromotion.setPromoType(promoType);
			peOrderPromotion.setOfferValue(value);
			peOrderPromotion.setPromoDescription(peOrderPromotion.buildDescription(desciptionString));
			return peOrderPromotion;
		}
		return null;
	}
	
	public PEOrderPromotion applyOfferAtItemLevel(OrderDTO order, PEPromotion pePromo, String desciptionString) {
		double value = 0d;
		List<Double> itemValues = new ArrayList<>();
		for(ItemDTO item : order.getItems()) {
			Map<String, Serializable> actualValueMap = objectConverterUtil.getItemValueMap(order, item);
			if (ruleEngine.checkIfOrderEligible(pePromo.getRuleMap(), actualValueMap)) {
				if (pePromo.getOfferValueMap().get(PromotionConstants.OFFER_TYPE)
						.equals(PromotionConstants.PERCENTAGE)) {
					double rate = (double) pePromo.getOfferValueMap().get(PromotionConstants.OFFER_VALUE);
					value += IOffer.applyPercentage(item.getPrice(), rate);
				} else if (pePromo.getOfferValueMap().get(PromotionConstants.OFFER_TYPE)
						.equals(PromotionConstants.HARD)) {
					double offerValue = (double) pePromo.getOfferValueMap().get(PromotionConstants.OFFER_VALUE);
					value += offerValue;
				} else if (pePromo.getOfferValueMap().get(PromotionConstants.OFFER_TYPE)
						.equals(PromotionConstants.LOW_VALUE)) {
					itemValues.add(item.getPrice());
				}
			}
		}
		if(itemValues.size() > 0) {
			value += IOffer.fetchTheLowest(itemValues);
		}
		return constructOrderPromotion(value, order.getOrderId(), pePromo.getPromoCode(), pePromo.getPromoType(), desciptionString);
	}

}
