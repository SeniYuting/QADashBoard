package com.ebay.cs.qadb.action;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.stereotype.Controller;

import com.ebay.cs.qadb.service.CountService;
import com.ebay.cs.qadb.service.ProductionBugService;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.opensymphony.xwork2.ActionContext;

@ParentPackage("publicPackage")
@Action("ProductionQuality")
@Results({
	@Result(name = "json", type="json",params = { "root", "dataMap" })
})
@Controller
public class ProductionQualityAction extends BaseAction {
	
	@Resource
	private ProductionBugService ProductionBugService;
	@Resource
	private CountService counterService;
	
	public String OOSLAAll(){
		
		// default global
		if (ActionContext.getContext().getSession().get("range") == null) {
			ActionContext.getContext().getSession().put("range", "global");
		}
				
		dataMap=new HashMap<String, Object>();
		
		String range = (String) ActionContext.getContext().getSession().get("range");
		String[] domain = {"CS Platform", "CS Agent", "CS Genesys", "CS Siebel", "OCS", "Communicator"};
		
		if(range.equals("local")) {
			domain = new String[4];
			domain[0] = "CS Platform";
			domain[1] = "CBT";
			domain[2] = "Communicator";
			domain[3] = "ASAC";
		} 
		
		BasicDBObject query=new BasicDBObject();
		query.put("fields.customfield_11205.value",severity);
		query.put("fields.status.name", new BasicDBObject("$ne","Closed"));
		query.put("fields.customfield_11206.value","Production");
		List<Long> data=new ArrayList<Long>();
		for(String d:domain){
			query.put("domain",d);
			long l=counterService.calculateOOSLAProductionQualityBySeverity(query);
			data.add(l);
		}
		dataMap.put("severity",severity);
		dataMap.put("data",data);
		return "json";
	}
	
	public String OpeningProductionBugAll(){
		
		String range = (String) ActionContext.getContext().getSession().get("range");
        String[] domain = {"CS Platform", "CS Agent", "CS Genesys", "CS Siebel", "OCS", "Communicator"};
		
		if(range.equals("local")) {
			domain = new String[4];
			domain[0] = "CS Platform";
			domain[1] = "CBT";
			domain[2] = "Communicator";
			domain[3] = "ASAC";
		}
		
		dataMap=new HashMap<String, Object>();
		List<Long> data=new ArrayList<Long>();
		for(String d:domain){
			 Long Count=ProductionBugService.getOpeningCountBySeverity(d, severity);
			 data.add(Count);
		}
		dataMap.put("severity", severity);
		dataMap.put("data", data);
		return "json";
	}
	
	public String OOSLA(){
		dataMap=new HashMap<String, Object>();
		String[] severity = {"1-Critical","2-Major","3-Average","4-Minor"};
		BasicDBObject query=new BasicDBObject();
		query.put("domain", domain);
		if(!priority.equals("")) {
			query.put("fields.priority.name", priority);
		}
		query.put("fields.status.name", new BasicDBObject("$ne","Closed"));
		query.put("fields.customfield_11206.value","Production");
		List<Long> data=new ArrayList<Long>();
		for(String s:severity) {
			query.put("fields.customfield_11205.value", s);
			long l=counterService.calculateOOSLAProductionQualityBySeverity(query);
			data.add(l);
		}
		if(domain.equals("CS Agent")) {
			domain = "AD";
		}
		if(domain.equals("CS Siebel")) {
			domain = "Siebel";
		}
		dataMap.put("domain", domain);
		dataMap.put("data",data);
		return "json";
	}
	
	public String ProductionBugTrend(){
		dataMap=new HashMap<String, Object>();
		BasicDBObject query=new BasicDBObject();
		query.put("domain",domain);
		query.put("fields.customfield_11205.value", severity);
		query.put("fields.customfield_11206.value","Production");
		List<Object> counter=counterService.counter(datarange, from, to, query);
		dataMap.put("data", counter);
		dataMap.put("severity",severity);
		return "json";
	}
	
	@SuppressWarnings("unchecked")
	@Deprecated
	public String ProductionBugTrends(){
		dataMap=new HashMap<String, Object>();
		String[] severity = {"1-Critical","2-Major","3-Average","4-Minor"};
		Map<String,Object> map;
		List<Object> value;
		ArrayList<List<Object>> time;
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal=Calendar.getInstance();
		for(String s:severity){
			time=new ArrayList<List<Object>>();
			map =new HashMap<String,Object>();
			map = ProductionBugService.getTrendsBySeverity(domain,s);			
			Set<String> dateSet=(Set<String>) map.get("Date");
			List<DBObject> list=(List<DBObject>) map.get("DBOlist");
			for(String date:dateSet){
				value=new ArrayList<Object>();
				try {
					value.add(sdf.parse(date).getTime()-cal.get(Calendar.ZONE_OFFSET)+28800000L);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				value.add(count(list,date));
				time.add(value);
			}
			dataMap.put(s,time);
		}		
		
		return "json";
	}
	
	private int count(List<DBObject> list,String start){
		int i=0;
		String date;
		Iterator<DBObject> iter=list.iterator();
		while(iter.hasNext()){
			date=((BasicDBObject)iter.next().get("fields")).get("created").toString();
			if(date.startsWith(start)){
				i+=1;
			}
		}
		return i;
	}
	
    public String OpeningProductionBug(){
		
		String[] severity = {"1-Critical","2-Major","3-Average","4-Minor"};
		dataMap=new HashMap<String, Object>();
		List<Long> data=new ArrayList<Long>();
		for(String s:severity) {
			Long Count=ProductionBugService.getOpeningCountBySeverity(domain, s);
			 data.add(Count);
		}
		if(domain.equals("CS Agent")) {
			domain = "AD";
		}
		if(domain.equals("CS Siebel")) {
			domain = "Siebel";
		}
		dataMap.put("domain", domain );
		dataMap.put("data",data);
		return "json";
	}
    
	public String WhenFoundBug(){
		dataMap=new HashMap<String, Object>();
		BasicDBObject query=new BasicDBObject();
		query.put("domain",domain);
		query.put("fields.customfield_11208.value",when);
		query.put("fields.customfield_11206.value", "Production");
		if(when.equals("Before-Release")){
			BasicDBList value=new BasicDBList();
			value.add("Testing");
			value.add("Post-Merge");
			value.add("Pre-Merge");
			value.add("Development");
			query.put("fields.customfield_11208.value",new BasicDBObject("$in",value));
		}
		List<Object> counter=counterService.counter(datarange, from, to, query);
		dataMap.put("data", counter);
		dataMap.put("when",when);
		return "json";
	}
	
	public String HowFoundBug(){
		dataMap=new HashMap<String, Object>();
		BasicDBObject query=new BasicDBObject();;
		query.put("domain",domain);
		query.put("fields.customfield_11207.value",how);
		query.put("fields.customfield_11206.value", "Production");
		List<Object> counter=counterService.counter(datarange, from, to, query);
		dataMap.put("data", counter);
		dataMap.put("how",how);
		return "json";
	}
	
	public String Analysis(){
		dataMap=new HashMap<String, Object>();
		BasicDBObject query=new BasicDBObject();
		BasicDBList value=new BasicDBList();
		if(effect.equals("valid")){
			value.add("Fixed");
			value.add("Done");
			value.add("Deferred");
		}else{
			value.add("Not an issue");
			value.add("Duplicate");
			value.add("Cannot Reproduce");
			value.add("Won't Fix");
		}
		query.put("domain",domain);
		query.put("fields.resolution.name",new BasicDBObject("$in",value));
		query.put("fields.customfield_11206.value", "Production");
		List<Object> counter=counterService.counter(datarange, from, to, query);
		dataMap.put("data", counter);
		dataMap.put("effec",effect);
		return "json";
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7909163233722122756L;

	private String priority;
	private String domain;
	private String datarange;
	private String when;
	private String how;
	private String effect;
	private String from;
	private String to;
	private String severity;
	
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

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}
	public String getDatarange() {
		return datarange;
	}
	public void setDatarange(String datarange) {
		this.datarange = datarange;
	}
	public String getWhen() {
		return when;
	}
	public void setWhen(String when) {
		this.when = when;
	}
	public String getHow() {
		return how;
	}
	public void setHow(String how) {
		this.how = how;
	}
	public String getEffect() {
		return effect;
	}
	public void setEffect(String effect) {
		this.effect = effect;
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
	
	public String getSeverity() {
		return severity;
	}
	
	public void setSeverity(String severity) {
		this.severity = severity;
	}
	
}
