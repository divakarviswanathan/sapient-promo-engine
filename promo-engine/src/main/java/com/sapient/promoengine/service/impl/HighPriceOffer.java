package com.sapient.promoengine.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sapient.promoengine.commons.PromotionConstants;
import com.sapient.promoengine.entity.PEOrderPromotion;
import com.sapient.promoengine.entity.PEPromotion;
import com.sapient.promoengine.rest.entity.OrderDTO;
import com.sapient.promoengine.service.IOffer;
import com.sapient.promoengine.utils.OfferHelper;

@Service
public class HighPriceOffer implements IOffer {
	
	@Autowired
	private OfferHelper offerHelper;
	
	@Override
	public PEOrderPromotion applyOffer(OrderDTO order, PEPromotion pePromo) {
		if(pePromo.isAtItemLevel()) {
			return offerHelper.applyOfferAtItemLevel(order, pePromo, PromotionConstants.HIGH_PRICE_STR);
		} else {
			return offerHelper.applyOfferAtOrderLevel(order, pePromo, PromotionConstants.HIGH_PRICE_STR);
		}
	}

}
