db.pe_promotion.drop()
db.pe_promotion.insert({
    promo_code:"DISCOUNT_ABOVE_1000",
    promo_type:"DISCOUNT",
    is_item_level:false,
    promo_rules:{"min_total_value":1000},
    promo_offer_value:{"type":"PERCENTAGE","value":2},
    create_ts:new ISODate()
    })


db.pe_promotion.insert({
    promo_code:"CASH_BACK_ICICI_CREDIT",
    promo_type:"CASH_BACK",
    is_item_level:false,
    promo_rules:{"payment_mode":"CREDIT","bank_code":"ICICI"},
    promo_offer_value:{"type":"HARD","value":50},
    create_ts:new ISODate()
    })


db.pe_promotion.insert({
    promo_code:"HIGH_PRICE_TEAMSPIRIT",
    promo_type:"HIGH_PRICE",
    is_item_level:true,
    promo_rules:{"brand":"TEAMSPIRIT","item":"SHIRTS"},
    promo_offer_value:{"type":"LOW_VALUE"},
    create_ts:new ISODate()
    })
    
db.pe_promotion_history.insert({
    promo_code:"DISCOUNT_ABOVE_1000",
    promo_type:"DISCOUNT",
    is_item_level:false,
    promo_rules:{"min_total_value":1000},
    promo_offer_value:{"type":"PERCENTAGE","value":2},
    create_ts:new ISODate()
    })


db.pe_promotion_history.insert({
    promo_code:"CASH_BACK_ICICI_CREDIT",
    promo_type:"CASH_BACK",
    is_item_level:false,
    promo_rules:{"payment_mode":"CREDIT","bank_code":"ICICI"},
    promo_offer_value:{"type":"HARD","value":50},
    create_ts:new ISODate()
    })


db.pe_promotion_history.insert({
    promo_code:"HIGH_PRICE_TEAMSPIRIT",
    promo_type:"HIGH_PRICE",
    is_item_level:true,
    promo_rules:{"brand":"TEAMSPIRIT","item":"SHIRTS"},
    promo_offer_value:{"type":"LOW_VALUE"},
    create_ts:new ISODate()
    })
db.pe_promotion.createIndex( { "create_ts": 1 }, { expireAfterSeconds: 86400 } )