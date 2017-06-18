package com.ebay.cs.qadb.service;

import java.util.List;

import com.mongodb.DBObject;

public interface DetailService {

	public List<List<String>> getDetails(DBObject query, DBObject keys);

	public List<List<String>> getDetails(DBObject query);

	List<String> bsonToArray(DBObject dbo);

}