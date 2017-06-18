package com.ebay.cs.qadb.action;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.stereotype.Controller;

import com.ebay.cs.qadb.service.CountService;
import com.ebay.cs.qadb.service.DetailService;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.QueryOperators;

@ParentPackage("publicPackage")
@Action("Detail")
@Results({
	@Result(name = "json", type="json",params = { "root", "dataMap" })
})
@Controller
public class Detail extends BaseAction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8904634678442757314L;
	@Resource
	private DetailService detailService;
	@Resource
	private CountService countservice;
	
	public String getProductionDetail(){
		BasicDBList value;
		dataMap=new HashMap<String, Object>();
		System.out.println(isOOSLA);
		System.out.println(debuger());
		BasicDBObject query=new BasicDBObject();
		query.put("fields.customfield_11206.value","Production");
		if(domain!=null){
			query.put("domain",domain);
		}
		if(priority!=null){
			if(!priority.equals("")){
			    query.put("fields.priority.name",priority );
			}
		}
		if((status!=null)&&status.equals("Opening")){
			query.put("fields.status.name", new BasicDBObject(QueryOperators.NIN, new String[]{ "Closed", "Resolved" }));
		}
		if((status!=null)&&status.equals("NotClosed")){
			query.put("fields.status.name", new BasicDBObject("$ne","Closed"));
		}
		if(how!=null){
			query.put("fields.customfield_11207.value",how);
		}
		if(severity!=null) {
		    if(!severity.equals("")){
			    query.put("fields.customfield_11205.value",severity);
		    }
		}
		if(when!=null){
			query.put("fields.customfield_11208.value",when);
			if(when.equals("Before-Release")&&(when!=null)){
				value=new BasicDBList();
				value.add("Testing");
				value.add("Post-Merge");
				value.add("Pre-Merge");
				value.add("Development");
				query.put("fields.customfield_11208.value",new BasicDBObject("$in",value));
			}
		}
		if(effect!=null){
			value=new BasicDBList();
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
			query.put("fields.resolution.name",new BasicDBObject("$in",value));
		}
		SimpleDateFormat sdf;
		BasicDBObject timerange;
        Calendar cal = Calendar.getInstance();
		if(datarange!=null&&time!=null){
			if(datarange.equals("week")){
				sdf=new SimpleDateFormat("yyyy-MM-dd");
				timerange=new BasicDBObject();
				cal.setTimeInMillis(Long.parseLong(time)-cal.get(Calendar.ZONE_OFFSET)+28800000L);

				timerange.put("$gte", sdf.format(cal.getTime()));
				System.out.println("detail gte: "+sdf.format(cal.getTime()));
				
				cal.add(Calendar.DATE, 7);
				timerange.put("$lt",sdf.format(cal.getTime()));
				System.out.println("detail lt: "+sdf.format(cal.getTime()));
				
				query.put("fields.created", timerange);
				
			}else if(datarange.equals("month")){
				timerange=new BasicDBObject();
				cal.setTimeInMillis(Long.parseLong(time)-cal.get(Calendar.ZONE_OFFSET)+28800000L);
				sdf=new SimpleDateFormat("yyyy-MM");
				query.put("fields.created", Pattern.compile("^"+sdf.format(cal.getTime())+".*$",Pattern.CASE_INSENSITIVE));
				
			}else if(datarange.equals("quater")){
				sdf=new SimpleDateFormat("yyyy-MM-dd");
				timerange=new BasicDBObject();
				cal.setTimeInMillis(Long.parseLong(time)-cal.get(Calendar.ZONE_OFFSET)+28800000L);
				
				timerange.put("$gte", sdf.format(cal.getTime()));
				System.out.println("detail gte: "+sdf.format(cal.getTime()));
				
				cal.add(Calendar.MONTH, 3);
				timerange.put("$lt",sdf.format(cal.getTime()));
				System.out.println("detail lt: "+sdf.format(cal.getTime()));
					
				query.put("fields.created", timerange);
				
			}else if(datarange.equals("year")){
				timerange=new BasicDBObject();
				sdf=new SimpleDateFormat("yyyy");
				cal.setTimeInMillis(Long.parseLong(time)-cal.get(Calendar.ZONE_OFFSET)+28800000L);
				query.put("fields.created", Pattern.compile("^"+sdf.format(cal.getTime())+".*$",Pattern.CASE_INSENSITIVE));

			}
		}
			
		List<List<String>> list = null;
		if("total".equals(isOOSLA)&&isOOSLA!=null){
			 list = countservice.calculateOOSLAObjectProductionQualityBySeverity(query);
		} else if(isOOSLA==null){
			list=detailService.getDetails(query);
		}
			dataMap.put("detail",list);
		return "json";
	}
	
	public String getQADetail(){
		BasicDBList value;
		dataMap=new HashMap<String, Object>();
		BasicDBObject query=new BasicDBObject();
		if(domain!=null){
			query.put("domain",domain);
		}
		if(priority!=null){
			if(!priority.equals("")){
			    query.put("fields.priority.name",priority );
			}
		}
		if((status!=null)&&status.equals("Opening")){
			query.put("fields.status.name", new BasicDBObject(QueryOperators.NIN, new String[]{ "Closed", "Resolved" }));
		}
		if((status!=null)&&status.equals("NotClosed")){
			query.put("fields.status.name", new BasicDBObject("$ne","Closed"));
		}
		if(how!=null){
			query.put("fields.customfield_11207.value",how);
		}
		if(severity!=null) {
		    if(!severity.equals("")){
			    query.put("fields.customfield_11205.value",severity);
		    }
		}
		if(when!=null){
			query.put("fields.customfield_11208.value",when);
			if(when.equals("Before-Release")&&(when!=null)){
				value=new BasicDBList();
				value.add("Testing");
				value.add("Post-Merge");
				value.add("Pre-Merge");
				value.add("Development");
				query.put("fields.customfield_11208.value",new BasicDBObject("$in",value));
			}
		}
		if(effect!=null){
			value=new BasicDBList();
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
			query.put("fields.resolution.name",new BasicDBObject("$in",value));
		}
		SimpleDateFormat sdf;
		BasicDBObject timerange;
		Calendar cal=Calendar.getInstance();
		if(datarange!=null&&time!=null){
			if(datarange.equals("week")){
				sdf=new SimpleDateFormat("yyyy-MM-dd");
				timerange=new BasicDBObject();
				cal.setTimeInMillis(Long.parseLong(time)-cal.get(Calendar.ZONE_OFFSET)+28800000L);

				timerange.put("$gte", sdf.format(cal.getTime()));
				System.out.println("detail gte: "+sdf.format(cal.getTime()));
				
				cal.add(Calendar.DATE, 7);
				timerange.put("$lt",sdf.format(cal.getTime()));
				System.out.println("detail lt: "+sdf.format(cal.getTime()));
				
				query.put("fields.created", timerange);

			}else if(datarange.equals("month")){
				timerange=new BasicDBObject();
				cal.setTimeInMillis(Long.parseLong(time)-cal.get(Calendar.ZONE_OFFSET)+28800000L);
				sdf=new SimpleDateFormat("yyyy-MM");
				query.put("fields.created", Pattern.compile("^"+sdf.format(cal.getTime())+".*$",Pattern.CASE_INSENSITIVE));
			
			}else if(datarange.equals("quater")){
				sdf=new SimpleDateFormat("yyyy-MM-dd");
				timerange=new BasicDBObject();
				cal.setTimeInMillis(Long.parseLong(time)-cal.get(Calendar.ZONE_OFFSET)+28800000L);

				timerange.put("$gte", sdf.format(cal.getTime()));
				System.out.println("detail gte: "+sdf.format(cal.getTime()));

				cal.add(Calendar.MONTH, 3);
				timerange.put("$lt",sdf.format(cal.getTime()));
				System.out.println("detail lt: "+sdf.format(cal.getTime()));
				
				query.put("fields.created", timerange);
		
			}else if(datarange.equals("year")){
				timerange=new BasicDBObject();
				sdf=new SimpleDateFormat("yyyy");
				cal.setTimeInMillis(Long.parseLong(time)-cal.get(Calendar.ZONE_OFFSET)+28800000L);
				query.put("fields.created", Pattern.compile("^"+sdf.format(cal.getTime())+".*$",Pattern.CASE_INSENSITIVE));

			}
		}
		
		List<List<String>> list = null;
		System.out.println("the prameter isOOSLA is "+isOOSLA);
		if("total".equals(isOOSLA)&&isOOSLA!=null) {
			list = countservice.calculateOOSLAObjectQAQualityBySeverity(query);
		} else if(isOOSLA==null){
			list=detailService.getDetails(query);
		}
		dataMap.put("detail",list);
		
		return "json";
	}
	
	public String getReopenDetail(){
		dataMap=new HashMap<String, Object>();
		BasicDBObject query=new BasicDBObject();
	
		if(domain!=null){
			query.put("domain",domain);
		}
		if(reopen!=null) {
			query.put("fields.customfield_11902", new BasicDBObject("$ne",null));
		}
		
		SimpleDateFormat sdf;
		BasicDBObject timerange;
        Calendar cal = Calendar.getInstance();
		if(datarange!=null&&time!=null){
			if(datarange.equals("week")){
				sdf=new SimpleDateFormat("yyyy-MM-dd");
				timerange=new BasicDBObject();
				cal.setTimeInMillis(Long.parseLong(time)-cal.get(Calendar.ZONE_OFFSET)+28800000L);
				
				timerange.put("$gte", sdf.format(cal.getTime()));
				System.out.println("detail gte: "+sdf.format(cal.getTime()));
				
				cal.add(Calendar.DATE, 7);
				timerange.put("$lt",sdf.format(cal.getTime()));
				System.out.println("detail lt: "+sdf.format(cal.getTime()));
				
				query.put("fields.created", timerange);
				
			}else if(datarange.equals("month")){
				timerange=new BasicDBObject();
				cal.setTimeInMillis(Long.parseLong(time)-cal.get(Calendar.ZONE_OFFSET)+28800000L);
				sdf=new SimpleDateFormat("yyyy-MM");
				query.put("fields.created", Pattern.compile("^"+sdf.format(cal.getTime())+".*$",Pattern.CASE_INSENSITIVE));
				
			}else if(datarange.equals("quater")){
				sdf=new SimpleDateFormat("yyyy-MM-dd");
				timerange=new BasicDBObject();
				cal.setTimeInMillis(Long.parseLong(time)-cal.get(Calendar.ZONE_OFFSET)+28800000L);

				timerange.put("$gte", sdf.format(cal.getTime()));
				System.out.println("detail gte: "+sdf.format(cal.getTime()));

				cal.add(Calendar.MONTH, 3);
				timerange.put("$lt",sdf.format(cal.getTime()));
				System.out.println("detail lt: "+sdf.format(cal.getTime()));
				
				query.put("fields.created", timerange);
				
			}else if(datarange.equals("year")){
				timerange=new BasicDBObject();
				sdf=new SimpleDateFormat("yyyy");
				cal.setTimeInMillis(Long.parseLong(time)-cal.get(Calendar.ZONE_OFFSET)+28800000L);
				query.put("fields.created", Pattern.compile("^"+sdf.format(cal.getTime())+".*$",Pattern.CASE_INSENSITIVE));

			}
		}
			
		List<List<String>> list = null;
		list=detailService.getDetails(query);

		dataMap.put("detail",list);
		
		return "json";
	}
	
	private String priority;
	private String domain;
	private String status;
	private String datarange;
	private String when;
	private String how;
	private String effect;
	private String time;
	private String isOOSLA;
	private String severity;
	private String reopen;
	
	public String getReopen() {
		return reopen;
	}
	
	public void setReopen(String reopen) {
		this.reopen = reopen;
	}
	
	public String getSeverity() {
		return severity;
	}
	
	public void setSeverity(String severity) {
		this.severity = severity;
	}
	
	public String getIsOOSLA() {
		return isOOSLA;
	}

	public void setIsOOSLA(String isOOSLA) {
		this.isOOSLA = isOOSLA;
	}

	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	private Map<String,Object> dataMap;
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getPriority() {
		return priority;
	}
	public void setPriority(String priority) {
		this.priority = priority;
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
	public Map<String, Object> getDataMap() {
		return dataMap;
	}
	public void setDataMap(Map<String, Object> dataMap) {
		this.dataMap = dataMap;
	}
	
	public String debuger(){
		return "Detail [priority=" + priority + ",time="+time+", domain=" + domain
		+ ", datarange=" + datarange + ", when=" + when + ", how="
		+ how + ", severity="+ severity + "]";
	}
}
