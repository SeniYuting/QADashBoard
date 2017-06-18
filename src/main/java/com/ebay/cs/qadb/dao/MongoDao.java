package com.ebay.cs.qadb.dao;

import java.util.List;

import com.ebay.cs.qadb.util.Page;
import com.mongodb.CommandResult;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;


public interface MongoDao{

	public DBCollection getCollection(String name);

	public CommandResult command(DBObject cmd);

	@SuppressWarnings("rawtypes")
	public List distinct(String key);

	public long count(DBObject count);

	//public DBCursor sort(DBObject orderBy);

	public DBObject group(DBObject key, DBObject cond, DBObject initial,
			String reduce, String finalize);

	public DBCursor find(DBObject query, DBObject keys);

	public DBCursor find(DBObject query);

	public DBCursor Skip(Page page);

	public List<DBObject> findAndsort(DBObject query, DBObject sort);

	public DBCursor sort(DBObject orderBy);
		
}
