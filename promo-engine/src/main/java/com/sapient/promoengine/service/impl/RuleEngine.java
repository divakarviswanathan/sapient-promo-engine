package com.sapient.promoengine.service.impl;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.sapient.promoengine.commons.PromotionConstants;

@Service
public class RuleEngine {
	
	private static Logger logger = LoggerFactory.getLogger(RuleEngine.class);

	private Map<String, Method> RULE_ENGINE = new HashMap<>();

	public static final String MIN_TOTAL_VALUE = "min_total_value";
	public static final String OFFER_TYPE = "type";
	public static final String OFFER_VALUE = "value";
	public static final String PAYMENT_MODE = "payment_mode";
	public static final String BANK_CODE = "bank_code";
	public static final String BRAND = "brand";
	public static final String ITEM = "item";
	
	@PostConstruct
	public void init() {
		try {
			Method isTotalValueSatisfied = RuleEngine.class.getDeclaredMethod("isTotalValueSatisfied", Serializable.class,
					Serializable.class);

			RULE_ENGINE.put(PromotionConstants.MIN_TOTAL_VALUE, isTotalValueSatisfied);

			Method isPaymentModeSame = RuleEngine.class.getDeclaredMethod("isPaymentModeSame", Serializable.class,
					Serializable.class);
			RULE_ENGINE.put(PromotionConstants.PAYMENT_MODE, isPaymentModeSame);

			Method isBankCodeSame = RuleEngine.class.getDeclaredMethod("isBankCodeSame", Serializable.class, Serializable.class);
			RULE_ENGINE.put(PromotionConstants.BANK_CODE, isBankCodeSame);

			Method isBrandSame = RuleEngine.class.getDeclaredMethod("isBrandSame", Serializable.class, Serializable.class);
			RULE_ENGINE.put(PromotionConstants.BRAND, isBrandSame);

			Method isItemSame = RuleEngine.class.getDeclaredMethod("isItemSame", Serializable.class, Serializable.class);
			RULE_ENGINE.put(PromotionConstants.ITEM, isItemSame);
			
		} catch (NoSuchMethodException | SecurityException e) {
			logger.error("Error occurred while initializing rule engine", e);
		}
		
	}
	private boolean isTotalValueSatisfied(Serializable expected, Serializable actual) {
		double expectedValue = (double) expected;
		double actualValue = (double) actual;
		return expectedValue <= actualValue;
	}
	
	private boolean isPaymentModeSame(Serializable expected, Serializable actual) {
		String expectedValue = (String) expected;
		String actualValue = (String) actual;
		return expectedValue.equals(actualValue);
	}
	
	private boolean isBankCodeSame(Serializable expected, Serializable actual) {
		String expectedValue = (String) expected;
		String actualValue = (String) actual;
		return expectedValue.equals(actualValue);
	}
	
	private boolean isBrandSame(Serializable expected, Serializable actual) {
		String expectedValue = (String) expected;
		String actualValue = (String) actual;
		return expectedValue.equals(actualValue);
	}
	
	private boolean isItemSame(Serializable expected, Serializable actual) {
		String expectedValue = (String) expected;
		String actualValue = (String) actual;
		return expectedValue.equals(actualValue);
	}
	
	public boolean checkIfOrderEligible(Map<String, Serializable> criteriaMap, Map<String, Serializable> orderValueMap) {
		for(String key : criteriaMap.keySet()) {
			try {
				boolean criteriaPassed = (boolean) RULE_ENGINE.get(key).invoke(this, criteriaMap.get(key), orderValueMap.get(key));
				if(!criteriaPassed) {
					return false;
				}
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				logger.error("Error occurred while checking rule map", e);
			}
		}
		return true;
	}
	
}
