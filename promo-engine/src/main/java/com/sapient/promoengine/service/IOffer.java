package com.sapient.promoengine.service;

import java.util.List;

import com.sapient.promoengine.entity.PEOrderPromotion;
import com.sapient.promoengine.entity.PEPromotion;
import com.sapient.promoengine.rest.entity.OrderDTO;

public interface IOffer {
	
	public PEOrderPromotion applyOffer(OrderDTO order, PEPromotion promo);
	
	static double applyPercentage(double value, double rate) {
		return (value*rate/100);
	}
	
	static double fetchTheLowest(List<Double> itemPrices) {
		double low = 0d;
		for(double price : itemPrices) {
			if(low < price) {
				low = price;
			}
		}
		return low;
	}
}
