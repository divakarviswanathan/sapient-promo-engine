package com.sapient.promoengine.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.sapient.promoengine.entity.PEOrderPromotion;

public interface PEOrderPromotionRepository extends MongoRepository<PEOrderPromotion, String>{

	List<PEOrderPromotion> findAllByOrderId(String orderId);

}
