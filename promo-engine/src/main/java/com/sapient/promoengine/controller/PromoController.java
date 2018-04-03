package com.sapient.promoengine.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sapient.promoengine.entity.PEOrderPromotion;
import com.sapient.promoengine.rest.entity.OrderDTO;
import com.sapient.promoengine.rest.entity.PromotionDTO;
import com.sapient.promoengine.service.PromotionEngineService;

@RestController
@RequestMapping("/v1/promos")
public class PromoController {

	private final Logger logger = LoggerFactory.getLogger(PromoController.class);
	
	@Autowired
	private PromotionEngineService promoEngineService;
	
	@RequestMapping(method=RequestMethod.POST)
	public PromotionDTO applyPromotion(@RequestBody OrderDTO orderDTO) {
		logger.info("Entering add products");
        try {
        	return promoEngineService.applyPromotion(orderDTO);
        } catch(Exception e) {
        	logger.error("Error occurred while applying promotion", e) ;
        }
        return null;
	}
	
	@RequestMapping(value= {"/orders/{orderId}"}, method=RequestMethod.GET)
	public List<PEOrderPromotion> getPromotion(@PathVariable("orderId") String orderId) {
		logger.info("Entering get promotions");
        try {
        	return promoEngineService.getAppliedPromotionsForOrder(orderId);
        } catch(Exception e) {
        	logger.error("Error occurred while applying promotion", e) ;
        }
        return null;
	}
}
