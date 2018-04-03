package com.sapient.promoengine.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.sapient.promoengine.entity.PEPromotion;

public interface PEPromotionRepository extends MongoRepository<PEPromotion, String> {

}
