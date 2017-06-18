package com.ebay.cs.qadb.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.ebay.cs.qadb.dao.MongoDao;
import com.ebay.cs.qadb.util.Page;
import com.mongodb.CommandResult;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.opensymphony.xwork2.ActionContext;


@Repository("MongoDao")
public class MongoDaoImpl extends BaseMongoTempleteDaoImpl implements MongoDao {
	protected static Logger log = LoggerFactory.getLogger(MongoDaoImpl.class);
	private DBCollection issues;
	
	private DBCursor cursor;
	
	List<DBObject> list;
	
	@Override
	public DBCollection getCollection(String name) {
		return db.getCollection(name);
	}
	
	public DBCollection getCollection(){
		String range = (String) ActionContext.getContext().getSession().get("range");
		issues=db.getCollection("issues");
		
		if(range!=null && range.equals("global")) {
			issues=db.getCollection("issues_global");
		}
		
		return issues;
	}
	@Override
	public CommandResult command(DBObject cmd){
		return db.command(cmd);
	}
	
	@Override
	public long count(DBObject count){
		return getCollection().getCount(count);
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public List distinct(String key){
		return getCollection().distinct(key);
	}
	
	@Override
	public DBObject group(DBObject key,DBObject cond,DBObject initial,String reduce,String finalize){
		return getCollection().group(key, cond, initial, reduce, finalize);
	}
	@Override
	public DBCursor sort(DBObject orderBy){
		return cursor.sort(orderBy);
	}
	@Override
	public List<DBObject> findAndsort(DBObject query,DBObject sort){
		cursor =find(query);
		if(sort!=null){
			cursor.sort(sort);
		}
		return Cursor2List(cursor);
	}
	
	private List<DBObject> Cursor2List(DBCursor cur) {
		list=new ArrayList<DBObject>();
		if(cur!=null){
			list=cur.toArray();
		}
		return list;
	}
	@Override
	public DBCursor find(DBObject query){
		//log.info(query.toString());
		return (query!=null)?(getCollection().find(query)):(getCollection().find());
	}
	@Override
	public DBCursor find(DBObject query,DBObject keys){
		//log.info(query.toString());
		return  getCollection().find(query, keys);
	}
	@Override
	public DBCursor Skip(Page page){
		return cursor.skip((page.getPageNumber()-1)*page.getPageSize()).limit(page.getPageSize());
	}
}
