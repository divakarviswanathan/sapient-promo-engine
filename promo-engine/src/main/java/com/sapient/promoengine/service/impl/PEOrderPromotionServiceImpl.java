package com.sapient.promoengine.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sapient.promoengine.entity.PEOrderPromotion;
import com.sapient.promoengine.repository.PEOrderPromotionRepository;
import com.sapient.promoengine.service.PEOrderPromotionService;

@Service
public class PEOrderPromotionServiceImpl implements PEOrderPromotionService {

	@Autowired
	private PEOrderPromotionRepository peOrderPromotionRepository;
	
	@Override
	public PEOrderPromotion saveOrderPromotion(PEOrderPromotion peOrderPromotion) {
		return peOrderPromotionRepository.save(peOrderPromotion);
	}

	@Override
	public List<PEOrderPromotion> getAppliedPromotionsByOrder(String orderId) {
		return peOrderPromotionRepository.findAllByOrderId(orderId);
	}
	

}
