package com.ebay.cs.qadb.service;

import java.util.Map;

public interface ProductionBugService {

	public Long getOpeningCountBySeverity(String domain, String severity);

	public Map<String, Object> getTrendsBySeverity(String domain,String severity);
	
	public Map<String, Object> WhenFound(String domain, String when);

	public Map<String, Object> HowFound(String domain, String how);

}
