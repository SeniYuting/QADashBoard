package com.ebay.cs.qadb.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.stereotype.Controller;

import com.ebay.cs.qadb.service.CountService;
import com.ebay.cs.qadb.service.EffectivityService;
import com.mongodb.BasicDBObject;

@ParentPackage("publicPackage")
@Action("Effective")
@Results({
	@Result(name = "json", type="json",params = { "root", "dataMap" })
})
@Controller
public class EffectivityAction extends BaseAction {
	
	@Resource
	private EffectivityService EffectivityService;
	@Resource
	private CountService counterService;
	
	public String effectivity(){
		dataMap=new HashMap<String, Object>();
		BasicDBObject query=new BasicDBObject();
		query.put("domain",domain);
		List<Object> list=counterService.counterEffec(datarange, from, to, query);
		dataMap.put("data", list);
		if(domain.equals("CS Agent")) {
			domain = "AD";
		}
		if(domain.equals("CS Siebel")) {
			domain = "Siebel";
		}
		dataMap.put("domain",domain);
		return "json";
	}
	
	public String reopen() {
		dataMap = new HashMap<String, Object>();
		BasicDBObject query = new BasicDBObject();
		query.put("domain", domain);
		query.put("fields.customfield_11902", new BasicDBObject("$ne", null));
		List<Object> counter = counterService.counter(datarange, from, to, query);
		dataMap.put("data", counter);
		if(domain.equals("CS Agent")) {
			domain = "AD";
		}
		if(domain.equals("CS Siebel")) {
			domain = "Siebel";
		}
		dataMap.put("domain",domain);
		return "json";
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5939282938573477650L;

	private String domain;
	private String datarange;
	private String from;
	private String to;
	
	private Map<String,Object> dataMap;
	
	public Map<String, Object> getDataMap() {
		return dataMap;
	}

	public void setDataMap(Map<String, Object> dataMap) {
		this.dataMap = dataMap;
	}
	
	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getDatarange() {
		return datarange;
	}

	public void setDatarange(String datarange) {
		this.datarange = datarange;
	}

	public String getFrom() {
		return from;
	}
	
	public void setFrom(String from) {
		this.from = from;
	}
	
	public String getTo() {
		return to;
	}
	
	public void setTo(String to) {
		this.to = to;
	}
	
}
