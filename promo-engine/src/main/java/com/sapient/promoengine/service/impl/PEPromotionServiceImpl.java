package com.sapient.promoengine.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.sapient.promoengine.commons.PromotionConstants;
import com.sapient.promoengine.entity.PEPromotion;
import com.sapient.promoengine.rest.entity.ItemDTO;
import com.sapient.promoengine.rest.entity.OrderDTO;
import com.sapient.promoengine.service.PEPromotionService;

@Service
public class PEPromotionServiceImpl implements PEPromotionService {

	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Override
	public List<PEPromotion> getApplicablePromotionsForOrder(OrderDTO order) {
		if(order == null || StringUtils.isEmpty(order.getOrderId())) {
			return null;
		}
		Criteria criteria = new Criteria();
        Criteria orderValue = Criteria.where(PromotionConstants.PROMO_RULES+"."+PromotionConstants.MIN_TOTAL_VALUE).lte(order.getTotalValue());
        Criteria paymentMode = Criteria.where(PromotionConstants.PROMO_RULES+"."+PromotionConstants.PAYMENT_MODE).is(order.getPaymentMode());
        Criteria bankCode = Criteria.where(PromotionConstants.PROMO_RULES+"."+PromotionConstants.BANK_CODE).is(order.getBankCode());
        List<String> itemValues = new ArrayList<>();
        List<String> brandValues = new ArrayList<>();
        for(ItemDTO item : order.getItems()) {
        	brandValues.add(item.getBrand());
        	itemValues.add(item.getName());
        }
        Criteria itemCrit = Criteria.where(PromotionConstants.PROMO_RULES+"."+PromotionConstants.ITEM).in(itemValues);
        Criteria brandCrit = Criteria.where(PromotionConstants.PROMO_RULES+"."+PromotionConstants.BRAND).in(brandValues);
        criteria.orOperator(orderValue, paymentMode, bankCode, itemCrit, brandCrit);
		Query query = new Query(criteria);
		return mongoTemplate.find(query, PEPromotion.class);
	}

}
