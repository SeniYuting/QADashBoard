package com.ebay.cs.qadb.dao.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import com.ebay.cs.qadb.dao.BaseTempleteDao;
import com.mongodb.DB;

@Repository("baseDAO")
public class BaseMongoTempleteDaoImpl implements BaseTempleteDao{
	
	private final Log log = (Log) LogFactory.getLog(BaseMongoTempleteDaoImpl.class);
	
		@Autowired
	    protected MongoTemplate mongoTemplate;  
	    
	    protected DB db;
		
	    /* (non-Javadoc)
		 * @see com.ebay.cs.qadb.dao.impl.BaseTemplete#setMongoTemplate(org.springframework.data.mongodb.core.MongoTemplate)
		 */  
	    @Override
		public void setMongoTemplate(MongoTemplate mongoTemplate) {  
	        this.mongoTemplate = mongoTemplate;  
	    }  
	      
	    /* (non-Javadoc)
		 * @see com.ebay.cs.qadb.dao.impl.BaseTemplete#setApplicationContext(org.springframework.context.ApplicationContext)
		 */
	    @Override
		public void setApplicationContext(ApplicationContext applicationContext)  
	            throws BeansException {  
	    	log.info("ApplicationContextAware executive ---setApplicationContext--- Method");
	        mongoTemplate = applicationContext.getBean("mongoTemplate", MongoTemplate.class);
	        setMongoTemplate(mongoTemplate);
	        db=mongoTemplate.getDb();
	        log.info(db.toString());
	    }  

}