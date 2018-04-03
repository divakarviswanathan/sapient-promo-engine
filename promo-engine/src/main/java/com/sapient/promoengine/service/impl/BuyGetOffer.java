package com.sapient.promoengine.service.impl;

import org.springframework.stereotype.Service;

import com.sapient.promoengine.entity.PEOrderPromotion;
import com.sapient.promoengine.entity.PEPromotion;
import com.sapient.promoengine.rest.entity.OrderDTO;
import com.sapient.promoengine.service.IOffer;

@Service
public class BuyGetOffer implements IOffer {
	
	@Override
	public PEOrderPromotion applyOffer(OrderDTO order, PEPromotion pePromo) {
		return null;
	}

}
