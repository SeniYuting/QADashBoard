package com.ebay.cs.qadb.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ebay.cs.qadb.dao.MongoDao;
import com.ebay.cs.qadb.service.EffectivityService;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

@Service("EffectivityService")
public class EffectivityServiceImpl implements EffectivityService {
	
	protected static Logger LOG = LoggerFactory.getLogger(EffectivityServiceImpl.class);
	
	@Autowired
	private MongoDao mongoDao;
	
	@Override
	public Map<String, Object> effectiveBug(String domain) {
		Map<String,Object> map=new HashMap<String,Object>();
		Set<String> dateSet=new TreeSet<String>();//时间去重并有序
		List<DBObject> list=new ArrayList<DBObject>();//查询对象集合
		
		BasicDBObject query=new BasicDBObject();;
		query.put("domain", domain);
		query.put("fields.customfield_11206.value","Production");
		
		BasicDBList value=new BasicDBList();
		value.add("Fixed");
		value.add("Done");
		value.add("Deferred");
		query.put("fields.resolution.value",new BasicDBObject("in",value));// to remove invalid production bugs
		
		
		BasicDBObject keys=new BasicDBObject();
		keys.put("fields.created",true);
		keys.put("domain", true);
		keys.put("fields.customfield_11206.value",true);
		
		DBCursor cursor=mongoDao.find(query,keys).sort(new BasicDBObject("fields.created",1));
		DBObject dbo;
		while(cursor.hasNext()){
			dbo=cursor.next();
			String date=((BasicDBObject)dbo.get("fields")).get("created").toString().split("T")[0];
			dateSet.add(date);
			list.add(dbo);
		}

		map.put("Date", dateSet);
		map.put("DBOlist", list);
		
		return map;
	}
	
}
