package com.sapient.promoengine.service.impl;

import static org.junit.Assert.assertNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import com.sapient.promoengine.commons.PromotionConstants;
import com.sapient.promoengine.entity.PEPromotion;
import com.sapient.promoengine.rest.entity.OrderDTO;
import com.sapient.promoengine.service.PEOrderPromotionService;
import com.sapient.promoengine.service.PEPromotionService;
import com.sapient.promoengine.service.PromotionEngineService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PromotionEngineServiceTest {
	
	@TestConfiguration
    static class PromotionEngineServiceTestContextConfiguration {
		
        @Bean
        public PEPromotionService pePromotionService() {
            return new PEPromotionServiceImpl();
        }
        
        @Bean
        public PEOrderPromotionService peOrderPromotionService() {
            return new PEOrderPromotionServiceImpl();
        }
    }
	
	@MockBean(name="pePromotionService")
	PEPromotionService pePromotionService;
	
	@MockBean(name="peOrderPromotionService")
	PEOrderPromotionService peOrderPromotionService;

	@Autowired
	PromotionEngineServiceImpl promotionEngineService;
	
	@Test
	public void testApplyPromotionWithNull() {
		assertNull(promotionEngineService.applyPromotion(null));
	}
	
	@Test
	public void testApplyPromotionWithEmptyOrder() {
		assertNull(promotionEngineService.applyPromotion(new OrderDTO()));
	}
	
	@Test
	public void testApplyPromotionWithInvalidOrder() {
		OrderDTO order = new OrderDTO();
		order.setOrderId("o2");
		assertNull(promotionEngineService.applyPromotion(order));
	}
	
	@Test
	public void testApplyPromotionWithValidOrder_CBOffer() {
		OrderDTO order = promotionEngineService.getORDER_MAP().get("o1_4a");
		
		List<PEPromotion> applicablePromotions = new ArrayList<>();
		PEPromotion pePromotion = new PEPromotion();
		pePromotion.setAtItemLevel(false);
		pePromotion.setId("1");
		pePromotion.setPromoCode("CASH_BACK_DUMMY");
		pePromotion.setPromoType(PromotionConstants.CASH_BACK);
		
		Map<String, Serializable> ruleMap = new HashMap<>();
		ruleMap.put(PromotionConstants.PAYMENT_MODE, "CREDIT");
		ruleMap.put(PromotionConstants.BANK_CODE, "ICICI");
		
		Map<String, Serializable> offerValueMap = new HashMap<>();
		offerValueMap.put(PromotionConstants.OFFER_TYPE, PromotionConstants.PERCENTAGE);
		offerValueMap.put(PromotionConstants.OFFER_VALUE, 2);
		pePromotion.setRuleMap(ruleMap);
		pePromotion.setOfferValueMap(offerValueMap);
		
		applicablePromotions.add(pePromotion);
		Mockito.when(pePromotionService.getApplicablePromotionsForOrder(order))
	      .thenReturn(applicablePromotions);
		
		assertNull(promotionEngineService.applyPromotion(order));
	}

}
