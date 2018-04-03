package com.sapient.promoengine.utils;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.sapient.promoengine.commons.PromotionConstants;
import com.sapient.promoengine.entity.PEOrderPromotion;
import com.sapient.promoengine.entity.PEPromotion;
import com.sapient.promoengine.rest.entity.ItemDTO;
import com.sapient.promoengine.rest.entity.OrderDTO;
import com.sapient.promoengine.service.impl.RuleEngine;

@RunWith(MockitoJUnitRunner.class)
public class OfferHelperTest {

	static OrderDTO o1 = new OrderDTO();
	static PEPromotion pePromotion = new PEPromotion();
	static PEPromotion pePromotion2 = new PEPromotion();
	
	@BeforeClass
	public static void setUp() {
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
		
		
		Map<String, Serializable> criteriaMap = new HashMap<>();
		criteriaMap.put(PromotionConstants.PAYMENT_MODE, "CREDIT");
		criteriaMap.put(PromotionConstants.BANK_CODE, "ICICI");
		
		Map<String, Serializable> valueMap = new HashMap<>();
		valueMap.put(PromotionConstants.OFFER_TYPE, "PERCENTAGE");
		valueMap.put(PromotionConstants.OFFER_VALUE, 2.0);
		
		pePromotion.setAtItemLevel(false);
		pePromotion.setId("1");
		pePromotion.setPromoCode("CASH_BACK_ICICI_CREDIT");
		pePromotion.setPromoType("CASH_BACK");
		pePromotion.setRuleMap(criteriaMap);
		pePromotion.setOfferValueMap(valueMap);
		
		Map<String, Serializable> criteriaMap2 = new HashMap<>();
		criteriaMap2.put(PromotionConstants.PAYMENT_MODE, "CREDIT");
		criteriaMap2.put(PromotionConstants.BANK_CODE, "ICICI");
		
		Map<String, Serializable> valueMap2 = new HashMap<>();
		valueMap2.put(PromotionConstants.OFFER_TYPE, "HARD");
		valueMap2.put(PromotionConstants.OFFER_VALUE, 50.0);
		
		pePromotion2.setAtItemLevel(false);
		pePromotion2.setId("2");
		pePromotion2.setPromoCode("DISCOUNT_ABOVE_1000");
		pePromotion2.setPromoType("DISCOUNT");
		pePromotion2.setRuleMap(criteriaMap2);
		pePromotion2.setOfferValueMap(valueMap2);
	}
	
	@InjectMocks
	OfferHelper offerHelper;
	
	@Mock
	RuleEngine ruleEngine;

	@Mock
	ObjectConverterUtil objectConverterUtil;
	
	@Test
	public void test_applyOfferAtOrderLevel() {
		assertNull(offerHelper.applyOfferAtOrderLevel(null, null, null));
	}
	
	@Test
	public void test_AtOrderLevel_Percentage() {
		Map<String, Serializable> orderMap = new HashMap<>();
		orderMap.put(PromotionConstants.MIN_TOTAL_VALUE, o1.getTotalValue());
		orderMap.put(PromotionConstants.BANK_CODE, o1.getBankCode());
		orderMap.put(PromotionConstants.PAYMENT_MODE, o1.getPaymentMode());
		
		when(objectConverterUtil.getOrderValueMap(o1)).thenReturn(orderMap);
		when(ruleEngine.checkIfOrderEligible(pePromotion.getRuleMap(), orderMap)).thenReturn(true);
		PEOrderPromotion peOrderPromotion = offerHelper.applyOfferAtOrderLevel(o1, pePromotion, null);
		assertTrue(peOrderPromotion.getOfferValue() == 20.0);
	}
	
	@Test
	public void test_AtOrderLevel_Hard() {
		Map<String, Serializable> orderMap = new HashMap<>();
		orderMap.put(PromotionConstants.MIN_TOTAL_VALUE, o1.getTotalValue());
		orderMap.put(PromotionConstants.BANK_CODE, o1.getBankCode());
		orderMap.put(PromotionConstants.PAYMENT_MODE, o1.getPaymentMode());
		
		when(objectConverterUtil.getOrderValueMap(o1)).thenReturn(orderMap);
		when(ruleEngine.checkIfOrderEligible(pePromotion2.getRuleMap(), orderMap)).thenReturn(true);
		PEOrderPromotion peOrderPromotion = offerHelper.applyOfferAtOrderLevel(o1, pePromotion2, null);
		assertTrue(peOrderPromotion.getOfferValue() == 50.0);
	}
}
