package com.sapient.promoengine.entity;

import java.io.Serializable;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection="pe_promotion")
public class PEPromotion {

	public final static String PROMO_CODE = "promo_code";
	public final static String PROMO_TYPE = "promo_type";
	public final static String PROMO_RULES = "promo_rules";
	public final static String PROMO_OFFER_VALUE = "promo_offer_value";
	public final static String IS_AT_ITEM_LEVEL = "is_item_level";
	
	@Id
	private String id;
	
	@Field(PROMO_CODE)
	private String promoCode;
	
	@Field(IS_AT_ITEM_LEVEL)
	private boolean isAtItemLevel;
	
	@Field(PROMO_TYPE)
	private String promoType;
	
	@Field(PROMO_RULES)
	private Map<String, Serializable> ruleMap;
	
	@Field(PROMO_OFFER_VALUE)
	private Map<String, Serializable> offerValueMap;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPromoCode() {
		return promoCode;
	}

	public void setPromoCode(String promoCode) {
		this.promoCode = promoCode;
	}

	public String getPromoType() {
		return promoType;
	}

	public void setPromoType(String promoType) {
		this.promoType = promoType;
	}

	public Map<String, Serializable> getRuleMap() {
		return ruleMap;
	}

	public void setRuleMap(Map<String, Serializable> ruleMap) {
		this.ruleMap = ruleMap;
	}

	public Map<String, Serializable> getOfferValueMap() {
		return offerValueMap;
	}

	public void setOfferValueMap(Map<String, Serializable> offerValueMap) {
		this.offerValueMap = offerValueMap;
	}

	public boolean isAtItemLevel() {
		return isAtItemLevel;
	}

	public void setAtItemLevel(boolean isAtItemLevel) {
		this.isAtItemLevel = isAtItemLevel;
	}
} 
