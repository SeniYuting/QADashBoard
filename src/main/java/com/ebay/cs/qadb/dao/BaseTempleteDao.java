package com.ebay.cs.qadb.dao;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.data.mongodb.core.MongoTemplate;

public interface BaseTempleteDao extends ApplicationContextAware{

	/** 
	 * set mongoTemplate 
	 * @param mongoTemplate the mongoTemplate to set 
	 */
	public abstract void setMongoTemplate(MongoTemplate mongoTemplate);

	public abstract void setApplicationContext(
			ApplicationContext applicationContext) throws BeansException;

}