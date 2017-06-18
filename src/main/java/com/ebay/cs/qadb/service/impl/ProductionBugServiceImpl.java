package com.ebay.cs.qadb.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ebay.cs.qadb.dao.MongoDao;
import com.ebay.cs.qadb.service.ProductionBugService;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.QueryOperators;

@Service(value="ProductionBugService")
public class ProductionBugServiceImpl implements ProductionBugService {
	protected static Logger LOG = LoggerFactory.getLogger(ProductionBugServiceImpl.class);
	
	@Autowired
	private MongoDao mongoDao;
	private List<DBObject> list;
	
	@Override
	public Map<String,Object> getTrendsBySeverity(String domain,String severity){
		Map<String,Object> map=new HashMap<String,Object>();
		Set<String> dateSet=new TreeSet<String>();//时间去重并有序
		list=new ArrayList<DBObject>();//查询对象集合
		
		BasicDBObject query=new BasicDBObject();
		query.put("domain", domain);
		query.put("fields.customfield_11205.value", severity);
		query.put("fields.created", Pattern.compile("^"+""+".*$",Pattern.CASE_INSENSITIVE));
		query.put("fields.customfield_11206.value","Production");
		
		BasicDBObject keys=new BasicDBObject();
		keys.put("fields.created",true);
		keys.put("domain", true);
		keys.put("fields.customfield_11205.value",true);
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
	
	@Override
	public Long getOpeningCountBySeverity(String domain,String severity){
		BasicDBObject query=new BasicDBObject();
		query.put("domain", domain);
		query.put("fields.status.name", new BasicDBObject(QueryOperators.NIN, new String[]{ "Closed", "Resolved" }));
		query.put("fields.customfield_11205.value",severity);
		query.put("fields.customfield_11206.value","Production");
		return mongoDao.count(query);
	}
	
	@Override
	public Map<String,Object> WhenFound(String domain,String when){
		Map<String,Object> map=new HashMap<String,Object>();
		Set<String> dateSet=new TreeSet<String>();//时间去重并有序
		list=new ArrayList<DBObject>();//查询对象集合
		BasicDBObject query=new BasicDBObject();;
		if(when.equals("Before-Release")){
			BasicDBList value=new BasicDBList();
			value.add("Testing");
			value.add("Post-Merge");
			value.add("Pre-Merge");
			value.add("Development");
			query.put("domain", domain);
			query.put("fields.customfield_11208.value",new BasicDBObject("in",value));
			query.put("fields.customfield_11206.value","Production");
		}else{
			query.put("domain", domain);
			query.put("fields.customfield_11208.value",when);
			query.put("fields.customfield_11206.value","Production");
		}
		BasicDBObject keys=new BasicDBObject();
		keys.put("fields.created",true);
		keys.put("domain", true);
		keys.put("fields.customfield_11208.value",true);
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
	
	@Override
	public Map<String,Object> HowFound(String domain,String how){
		Map<String,Object> map=new HashMap<String,Object>();
		Set<String> dateSet=new TreeSet<String>();//时间去重并有序
		list=new ArrayList<DBObject>();//查询对象集合
		
		BasicDBObject query=new BasicDBObject();
		query.put("domain", domain);
		query.put("fields.customfield_11207.value",how);
		//query.put("fields.created", Pattern.compile("^"+created+".*$",Pattern.CASE_INSENSITIVE));
		query.put("fields.customfield_11206.value","Production");
		
		BasicDBObject keys=new BasicDBObject();
		keys.put("fields.created",true);
		keys.put("domain", true);
		keys.put("fields.customfield_11207.value",true);
		
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
