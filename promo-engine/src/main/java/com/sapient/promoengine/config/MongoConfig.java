package com.sapient.promoengine.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.MongoClient;

@Configuration
@EnableMongoRepositories(basePackages = "com.sapient.promoengine.repository")
public class MongoConfig extends AbstractMongoConfiguration {

	@Value( "${mongodb.name}" )
	private String dbName;
	
	@Value( "${mongodb.hosts}" )
	private String hostName;
	
	@Value( "${mongodb.port}" )
	private int portNumber;
	
	@Override
	public MongoClient mongoClient() {
		return new MongoClient(hostName, portNumber);
	}

	@Override
	protected String getDatabaseName() {
		return dbName;
	}
	
	public @Bean MongoTemplate mongoTemplate() throws Exception {
		MongoTemplate mongoTemplate = new MongoTemplate(mongoClient(), dbName);
		MappingMongoConverter converter = (MappingMongoConverter) mongoTemplate.getConverter();
		converter.setTypeMapper(new DefaultMongoTypeMapper(null));
		return mongoTemplate;
			
	  }

}
