package com.sapient.promoengine.service.impl;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;

import com.sapient.promoengine.commons.PromotionConstants;

public class RuleEngineTest {

	private static RuleEngine ruleEngine = new RuleEngine();
	
	@BeforeClass
	public static void setUp() {
		ruleEngine.init();
	}
	
	@Test
	public void test_ForEmptyCriteriaAndOrderValue() {
		Map<String, Serializable> criteriaMap = new HashMap<>();
		Map<String, Serializable> orderValueMap = new HashMap<>();
		assertFalse(ruleEngine.checkIfOrderEligible(criteriaMap, orderValueMap));
	}
	
	@Test
	public void test_ForEmptyCriteria() {
		Map<String, Serializable> criteriaMap = new HashMap<>();
		Map<String, Serializable> orderValueMap = new HashMap<>();
		orderValueMap.put("dummy", "dummy");
		assertTrue(ruleEngine.checkIfOrderEligible(criteriaMap, orderValueMap));
	}
	
	@Test
	public void test_ForEmptyOrder() {
		Map<String, Serializable> criteriaMap = new HashMap<>();
		criteriaMap.put("dummy", "dummy");
		Map<String, Serializable> orderValueMap = new HashMap<>();
		assertFalse(ruleEngine.checkIfOrderEligible(criteriaMap, orderValueMap));
	}
	
	@Test
	public void test_EligibleOrder_PaymentAndBank() {
		Map<String, Serializable> criteriaMap = new HashMap<>();
		criteriaMap.put(PromotionConstants.PAYMENT_MODE, "CREDIT");
		criteriaMap.put(PromotionConstants.BANK_CODE, "ICICI");
		Map<String, Serializable> orderValueMap = new HashMap<>();
		orderValueMap.put(PromotionConstants.PAYMENT_MODE, "CREDIT");
		orderValueMap.put(PromotionConstants.BANK_CODE, "ICICI");
		assertTrue(ruleEngine.checkIfOrderEligible(criteriaMap, orderValueMap));
	}
	
	@Test
	public void test_NotEligibleOrder_PaymentAndBank() {
		Map<String, Serializable> criteriaMap = new HashMap<>();
		criteriaMap.put(PromotionConstants.PAYMENT_MODE, "CREDIT");
		criteriaMap.put(PromotionConstants.BANK_CODE, "ICICI");
		Map<String, Serializable> orderValueMap = new HashMap<>();
		orderValueMap.put(PromotionConstants.PAYMENT_MODE, "DEBIT");
		orderValueMap.put(PromotionConstants.BANK_CODE, "ICICI");
		assertFalse(ruleEngine.checkIfOrderEligible(criteriaMap, orderValueMap));
	}
	
	@Test
	public void test_ForUnPresentValue() {
		Map<String, Serializable> criteriaMap = new HashMap<>();
		criteriaMap.put(PromotionConstants.MIN_TOTAL_VALUE, 1500.0);
		Map<String, Serializable> orderValueMap = new HashMap<>();
		orderValueMap.put(PromotionConstants.PAYMENT_MODE, "DEBIT");
		orderValueMap.put(PromotionConstants.BANK_CODE, "ICICI");
		assertFalse(ruleEngine.checkIfOrderEligible(criteriaMap, orderValueMap));
	}
	
	/*
	 * 
	 public boolean checkIfOrderEligible(Map<String, Serializable> criteriaMap, Map<String, Serializable> orderValueMap) {
	 */
}
