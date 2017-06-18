package com.ebay.cs.qadb.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ebay.cs.qadb.dao.MongoDao;
import com.ebay.cs.qadb.service.QABugService;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.QueryOperators;

@Service(value="QABugService")
public class QABugServiceImpl implements QABugService {
	
	@Autowired
	private MongoDao mongoDao;
	private List<DBObject> list;
	
	@Override
	public long openingBugsBySeverity(String domain,String severity){
		BasicDBObject count=new BasicDBObject();
		count.put("domain", domain);
		count.put("fields.status.name", new BasicDBObject(QueryOperators.NIN, new String[]{ "Closed", "Resolved" }));
		count.put("fields.customfield_11205.value",severity);
		long c=mongoDao.count(count);
		return c;
	}

	@Override
	public Map<String, Object> getTrendsBySeverity(String domain, String severity) {
		Map<String,Object> map=new HashMap<String,Object>();
		Set<String> dateSet=new TreeSet<String>();//时间去重并有序
		list=new ArrayList<DBObject>();//查询对象集合
		
		BasicDBObject query=new BasicDBObject();
		query.put("domain", domain);
		query.put("fields.customfield_11205.value", severity);
		
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
	public Map<String, Object> HowFound(String domain, String how) {
		Map<String,Object> map=new HashMap<String,Object>();
		Set<String> dateSet=new TreeSet<String>();//时间去重并有序
		list=new ArrayList<DBObject>();//查询对象集合
		
		BasicDBObject query=new BasicDBObject();
		query.put("domain", domain);
		query.put("fields.customfield_11207.value",how);
		//query.put("fields.created", Pattern.compile("^"+created+".*$",Pattern.CASE_INSENSITIVE));
		
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
