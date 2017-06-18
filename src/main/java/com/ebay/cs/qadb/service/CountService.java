package com.ebay.cs.qadb.service;

import java.util.List;

import com.mongodb.BasicDBObject;

public interface CountService {

	/**
	 * first day of a week is Sunday and last day is Saturday
	 * @param query
	 * @return
	 */
	public List<Object> counter(String datarange, String from, String to, BasicDBObject query);
	
	public List<Object> counterEffec(String datarange, String from, String to, BasicDBObject query);
	
	public List<Long> countByWeek(BasicDBObject query);
	
	public List<Long> countByWeekFromTo(BasicDBObject query, String from, String to);

	public List<Long> countByMonth(BasicDBObject query);
	
	public List<Long> countByMonthFromTo(BasicDBObject query, String from, String to);

	public List<Long> countByQuater(BasicDBObject query);
	
	public List<Long> countByQuaterFromTo(BasicDBObject query, String from, String to);

	public List<Long> countByYear(BasicDBObject query);
	
	public List<Long> countByYearFromTo(BasicDBObject query, String from, String to);
	
	public long calculateOOSLAProductionQualityBySeverity(BasicDBObject query);
	
	public long calculateOOSLAQAQualityBySeverity(BasicDBObject query);
	
	List<List<String>> calculateOOSLAObjectProductionQualityBySeverity(BasicDBObject query);
	
	List<List<String>> calculateOOSLAObjectQAQualityBySeverity(BasicDBObject query);

}