package com.ebay.cs.qadb.service;

import java.util.Map;

public interface QABugService {

	public long openingBugsBySeverity(String domain, String severity);

	public Map<String, Object> getTrendsBySeverity(String domain, String severity);

	public Map<String, Object> HowFound(String domain, String how);

}