package com.ebay.cs.qadb.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.BasicBSONList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ebay.cs.qadb.dao.MongoDao;
import com.ebay.cs.qadb.service.DetailService;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

@Service("detailService")
public class DetailServiceImpl implements DetailService {
	protected static Logger LOG = LoggerFactory.getLogger(DetailServiceImpl.class);
	
	@Autowired
	private MongoDao mongoDao;
	
	/* (non-Javadoc)
	 * @see com.ebay.cs.qadb.service.impl.DetailService#getDetails(com.mongodb.DBObject, com.mongodb.DBObject)
	 */
	public List<List<String>> getDetails(DBObject query,DBObject keys){
		DBCursor cursor=mongoDao.find(query, keys);
		List<List<String>> list=new ArrayList<List<String>>();
		while(cursor.hasNext()){
			DBObject o=cursor.next();
			list.add(bsonToArray(o));
		}
		return list;
	}
	public List<List<String>> getDetails(DBObject query){
		DBCursor cursor=mongoDao.find(query);
		List<List<String>> list=new ArrayList<List<String>>();
		while(cursor.hasNext()){
			DBObject o=cursor.next();
			list.add(bsonToArray(o));
		}
		return list;
	}
	public List<String> bsonToArray(DBObject dbo){
		List<String> lst=new ArrayList<String>();
		lst.add((String) dbo.get("key"));
		
		String domain = ((BasicBSONList) dbo.get("domain")).get(0).toString();
		if(domain.equals("CS Agent")) {
			domain = "AD";
		}
		if(domain.equals("CS Siebel")) {
			domain = "Siebel";
		}	
		lst.add(domain);
		
		DBObject fields=(DBObject) dbo.get("fields");
		lst.add(((DBObject)fields.get("priority")).get("name").toString());
		lst.add(((DBObject)fields.get("status")).get("name").toString());
		lst.add(((String) fields.get("created")).split("T")[0]);
		if(fields.get("assignee") != null) {
			lst.add(((DBObject)fields.get("assignee")).get("displayName").toString());
		} else {
			lst.add("NA");
		}
		lst.add(((DBObject)fields.get("reporter")).get("displayName").toString());
		lst.add(((DBObject)fields.get("customfield_11205")).get("value").toString());
		lst.add(((DBObject)fields.get("labels")).toString().substring(1, (fields.get("labels")).toString().length()-1));
		return lst;	
	}
	
}
