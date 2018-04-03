package com.sapient.promoengine.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sapient.promoengine.commons.PromotionConstants;
import com.sapient.promoengine.entity.PEOrderPromotion;
import com.sapient.promoengine.entity.PEPromotion;
import com.sapient.promoengine.rest.entity.ItemDTO;
import com.sapient.promoengine.rest.entity.OrderDTO;
import com.sapient.promoengine.rest.entity.PromotionDTO;
import com.sapient.promoengine.service.IOffer;
import com.sapient.promoengine.service.PEOrderPromotionService;
import com.sapient.promoengine.service.PEPromotionService;
import com.sapient.promoengine.service.PromotionEngineService;
import com.sapient.promoengine.utils.ObjectConverterUtil;

@Service
public class PromotionEngineServiceImpl implements PromotionEngineService {

	private Map<String, OrderDTO> ORDER_MAP = new HashMap<>();
	private Map<String, IOffer> OFFERS_MAP = new HashMap<>();
	
	@Autowired
	private CashBackOffer cashBackOffer;
	
	@Autowired
	private DiscountOffer discountOffer;
	
	@Autowired
	private BuyGetOffer buyGetOffer;
	
	@Autowired
	private HighPriceOffer highPriceOffer;
	
	@Autowired
	private PEPromotionService pePromotionService;
	
	@Autowired
	private PEOrderPromotionService peOrderPromotionService;
	
	@Autowired
	private ObjectConverterUtil objectConverterUtil; 
	
	@Override
	public PromotionDTO applyPromotion(OrderDTO orderDTO) {
		//TODO: Ideally the order value has to come from an external service
		//To keep the scope less of promotion engine. The value is pre-populated here.
		OrderDTO order = ORDER_MAP.get(orderDTO.getOrderId());
		
		List<PEPromotion> applicablePromotions = pePromotionService.getApplicablePromotionsForOrder(order);
		
		List<PEOrderPromotion> peOrderPromotions = new ArrayList<>();
		for(PEPromotion promo : applicablePromotions) {
			PEOrderPromotion orderPromotion = OFFERS_MAP.get(promo.getPromoType()).applyOffer(order, promo);
			if(orderPromotion != null) {
				peOrderPromotions.add(orderPromotion);
			}
		}
		
		PEOrderPromotion bestPromotion = findBestApplicablePromotion(peOrderPromotions);
		
		return objectConverterUtil.convertOrderPromotion(peOrderPromotionService.saveOrderPromotion(bestPromotion));
	}
	
	private PEOrderPromotion findBestApplicablePromotion(List<PEOrderPromotion> peOrderPromotions) {
		PEOrderPromotion bestOffer = peOrderPromotions.get(0);
		peOrderPromotions.remove(0);
		for(PEOrderPromotion orderPromo : peOrderPromotions) {
			if(bestOffer.getOfferValue()<orderPromo.getOfferValue()) {
				bestOffer = orderPromo;
			}
		}
		return bestOffer;
	}
	
	@PostConstruct
	public void init() {
		
		OFFERS_MAP.put(PromotionConstants.CASH_BACK, cashBackOffer);
		OFFERS_MAP.put(PromotionConstants.DISCOUNT, discountOffer);
		OFFERS_MAP.put(PromotionConstants.BUY_GET, buyGetOffer);
		OFFERS_MAP.put(PromotionConstants.BUY_GET, highPriceOffer);
		
		
		OrderDTO o1 = new OrderDTO();
		o1.setBankCode("CITI");
		o1.setPaymentMode("DEBIT");
		o1.setOrderId("o1_4a");
		o1.setTotalValue(1000.0d);
		
		ItemDTO o1i1 = new ItemDTO();
		o1i1.setBrand("TEAMSPIRIT");
		o1i1.setName("SHIRTS");
		o1i1.setPrice(1000.0d);
		
		List<ItemDTO> items1 = new ArrayList<>();
		items1.add(o1i1);
		o1.setItems(items1);
		
		OrderDTO o2 = new OrderDTO();
		o2.setBankCode("ICICI");
		o2.setPaymentMode("CREDIT");
		o2.setOrderId("o2_4a");
		o2.setTotalValue(10000.0d);
		
		ItemDTO o2i1 = new ItemDTO();
		o2i1.setBrand("XIAOMI");
		o2i1.setName("REDMI NOTE 5");
		o2i1.setPrice(10000.0d);
		
		List<ItemDTO> items2 = new ArrayList<>();
		items2.add(o2i1);
		o2.setItems(items2);
		
		
		ORDER_MAP.put("o1_4a", o1);
		ORDER_MAP.put("o2_4a", o2);
	}

}
