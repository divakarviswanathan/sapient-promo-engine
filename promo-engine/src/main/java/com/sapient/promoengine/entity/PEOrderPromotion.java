package com.sapient.promoengine.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection="pe_order_promotion")
public class PEOrderPromotion {

	@Id
	private String id;
	
	@Field("order_id")
	private String orderId;
	
	@Field("promo_code")
	private String promoCode;
	
	@Field("promo_description")
	private String promoDescription;
	
	@Field("offer_value")
	private double offerValue;
	
	@Field("promo_type")
	private String promoType;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getPromoDescription() {
		return promoDescription;
	}

	public void setPromoDescription(String promoDescription) {
		this.promoDescription = promoDescription;
	}

	public double getOfferValue() {
		return offerValue;
	}

	public void setOfferValue(double offerValue) {
		this.offerValue = offerValue;
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

	public String buildDescription(String offer) {
		StringBuilder builder = new StringBuilder();
		builder.append("Offer Code ").append(this.promoCode).append(" applied for order ").append(this.orderId).append(". You get ")
			.append(offer).append(" of Rs ").append(this.offerValue);
		return builder.toString();
	}
}
