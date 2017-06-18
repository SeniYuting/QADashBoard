package com.ebay.cs.qadb.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ebay.cs.qadb.dao.MongoDao;
import com.ebay.cs.qadb.service.CountService;
import com.ebay.cs.qadb.service.DetailService;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.QueryOperators;
import com.opensymphony.xwork2.ActionContext;

@Service("CountService")
public class CountServiceImpl implements CountService {
	
	@Autowired
	private MongoDao mongoDao;
	@Resource
	private DetailService detailService;
	
	private Calendar curr=Calendar.getInstance();
	
	@Override
	public List<Object> counter(String datarange, String from, String to, BasicDBObject query){

		List<Object> retur=new ArrayList<Object>();
		List<Long> result = new ArrayList<Long>();
		long [] time;
		Calendar cal = Calendar.getInstance();	
//		cal.set(2014, 0, 1);
		cal.setTimeInMillis(1388505600000L);
		
		if(datarange.equals("week")){
			
			if(from.equals("")) {
			    // one year before, monday
			    cal=Calendar.getInstance();
			    cal.add(Calendar.MONTH, -12);
			    cal.set(Calendar.DATE, 1);
			    result=countByWeek(query);
			} else {
				if(to.equals("")) {
					Date today = new Date();   
				    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");   
				    to = sdf.format(today); 
				}
				cal = strToCalendar(from);
				result = countByWeekFromTo(query, from, to);
			}
			
			cal.add(Calendar.DATE, -cal.get(Calendar.DAY_OF_WEEK)+2);
		    cal.set(Calendar.HOUR_OF_DAY,0);
		    cal.set(Calendar.MINUTE, 0);
		    cal.set(Calendar.SECOND, 0);
		    cal.set(Calendar.MILLISECOND, 000);
			
			for(Long l:result){
				time=new long[2];
				time[0]=cal.getTimeInMillis();
				time[1]=l;
				retur.add(time);
				cal.add(Calendar.DATE,7);
			}

		}else if(datarange.equals("month")){
			
			if(from.equals("")) {
			    cal=Calendar.getInstance();
			    cal.add(Calendar.MONTH, -12);
			    result=countByMonth(query);
			} else {	
				if(to.equals("")) {
					Date today = new Date();   
				    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");   
				    to = sdf.format(today); 
				}
				cal = strToCalendar(from);
				result=countByMonthFromTo(query, from, to);
			}
			cal.set(Calendar.DATE, 1);
		    cal.set(Calendar.HOUR_OF_DAY,0);
		    cal.set(Calendar.MINUTE, 0);
		    cal.set(Calendar.SECOND, 0);
		    cal.set(Calendar.MILLISECOND, 000);
		    
			for(Long l:result){
				time=new long[2];
				time[0]=cal.getTimeInMillis();
				time[1]=l;
				retur.add(time);
				cal.add(Calendar.MONTH, 1);
			}

		}else if(datarange.equals("quater")){
			
			if(from.equals("")) {
				cal = Calendar.getInstance();
				cal.add(Calendar.YEAR, -1);
				cal.set(Calendar.MONTH, 0);
				result=countByQuater(query);
			} else {
				if(to.equals("")) {
					Date today = new Date();   
				    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");   
				    to = sdf.format(today); 
				}
				cal = strToCalendar(from);
				int month = cal.get(Calendar.MONTH);
				int beginMonth = getQuarterBeginMonth(month);
				cal.set(Calendar.MONTH, beginMonth);
				result=countByQuaterFromTo(query, from, to);
			}
					
			cal.set(Calendar.DATE, 1);
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 000);
			
			
			for(Long l:result){
				time=new long[2];
				time[0]=cal.getTimeInMillis();
				time[1]=l;
				retur.add(time);
				cal.add(Calendar.MONTH,3);
			}

		}else if(datarange.equals("year")){
			
			cal.set(2015, 0, 1);
			
			if(from.equals("")) {
				result=countByYear(query);
			} else {
				if(to.equals("")) {
					Date today = new Date();   
				    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");   
				    to = sdf.format(today); 
				}
				cal = strToCalendar(from);
				cal.set(Calendar.MONTH, 0);
				cal.set(Calendar.DATE, 1);
				result=countByYearFromTo(query, from, to);
			}

			for(Long l:result){
				time=new long[2];
				time[0]=cal.getTimeInMillis();
				time[1]=l;
				retur.add(time);
				cal.add(Calendar.YEAR, 1);
			}
		}

		return retur;
	}
	
	public List<Object> counterEffec(String datarange, String from, String to, BasicDBObject query){
		List<Object> retur=new ArrayList<Object>();
		List<Long> total=new ArrayList<Long>();
		List<Long> effec=new ArrayList<Long>();
		List<Float> percent=new ArrayList<Float>();
		Object [] time;
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(1388505600000L);
		
		String range = (String) ActionContext.getContext().getSession().get("range");

		if(datarange.equals("week")){
			
			if(from.equals("")) {
				// one year before, monday
				cal=Calendar.getInstance();
				cal.add(Calendar.MONTH, -12);
				cal.set(Calendar.DATE, 1);
				total=countByWeek(query);
				if(range!=null && range.equals("global")) {
					query.put("fields.customfield_11208.value", new BasicDBObject(QueryOperators.IN, new String[]{"Post-Release"}));	
					query.put("fields.resolution.name",new BasicDBObject(QueryOperators.NIN, new String[]{ "Not an issue", "Duplicate", "Cannot Reproduce", "Won't Fix" }));
				} else {
				    query.put("fields.customfield_11206.value", new BasicDBObject(QueryOperators.IN, new String[]{"Production"}));	
				    query.put("fields.resolution.name",new BasicDBObject(QueryOperators.NIN, new String[]{ "Not an issue", "Duplicate", "Cannot Reproduce", "Won't Fix" }));
				    query.put("fields.labels", new BasicDBObject(QueryOperators.IN, new String[]{"CodeIssue"}));
				}
				effec=countByWeek(query);
			} else {
				if(to.equals("")) {
					Date today = new Date();   
				    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");   
				    to = sdf.format(today); 
				}
				cal = strToCalendar(from);
				total = countByWeekFromTo(query, from, to);
				if (range != null && range.equals("global")) {
					query.put("fields.customfield_11208.value", new BasicDBObject(QueryOperators.IN, new String[]{"Post-Release"}));	
					query.put("fields.resolution.name",new BasicDBObject(QueryOperators.NIN, new String[]{ "Not an issue", "Duplicate", "Cannot Reproduce", "Won't Fix" }));
				} else {
					query.put("fields.customfield_11206.value", new BasicDBObject(QueryOperators.IN, new String[] { "Production" }));
					query.put("fields.resolution.name", new BasicDBObject(QueryOperators.NIN, new String[] { "Not an issue", "Duplicate", "Cannot Reproduce", "Won't Fix" }));
					query.put("fields.labels", new BasicDBObject(QueryOperators.IN, new String[] { "CodeIssue" }));
				}
				effec = countByWeekFromTo(query, from, to);
			}
					
			cal.add(Calendar.DATE, -cal.get(Calendar.DAY_OF_WEEK)+2);
			cal.set(Calendar.HOUR_OF_DAY,0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 000);
					
			percent=pack(total, effec);
	
			for(Float f:percent){
				time=new Object[2];
				time[0]=cal.getTimeInMillis();
				time[1]=f;
				retur.add(time);
				cal.add(Calendar.DATE,7);
			}
			
		}else if(datarange.equals("month")){
			
			if(from.equals("")) {
				cal = Calendar.getInstance();
				cal.add(Calendar.MONTH, -12);
				total=countByMonth(query);
				if (range != null && range.equals("global")) {
					query.put("fields.customfield_11208.value", new BasicDBObject(QueryOperators.IN, new String[]{"Post-Release"}));	
					query.put("fields.resolution.name",new BasicDBObject(QueryOperators.NIN, new String[]{ "Not an issue", "Duplicate", "Cannot Reproduce", "Won't Fix" }));
				} else {
					query.put("fields.customfield_11206.value", new BasicDBObject(QueryOperators.IN, new String[] { "Production" }));
					query.put("fields.resolution.name", new BasicDBObject(QueryOperators.NIN, new String[] { "Not an issue", "Duplicate", "Cannot Reproduce", "Won't Fix" }));
					query.put("fields.labels", new BasicDBObject(QueryOperators.IN, new String[] { "CodeIssue" }));
				}
				effec=countByMonth(query);
			} else {
				if(to.equals("")) {
					Date today = new Date();   
				    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");   
				    to = sdf.format(today); 
				}
				cal = strToCalendar(from);
				total=countByMonthFromTo(query, from, to);
				if (range != null && range.equals("global")) {
					query.put("fields.customfield_11208.value", new BasicDBObject(QueryOperators.IN, new String[]{"Post-Release"}));	
					query.put("fields.resolution.name",new BasicDBObject(QueryOperators.NIN, new String[]{ "Not an issue", "Duplicate", "Cannot Reproduce", "Won't Fix" }));
				} else {
					query.put("fields.customfield_11206.value", new BasicDBObject(QueryOperators.IN, new String[] { "Production" }));
					query.put("fields.resolution.name", new BasicDBObject(QueryOperators.NIN, new String[] { "Not an issue", "Duplicate", "Cannot Reproduce", "Won't Fix" }));
					query.put("fields.labels", new BasicDBObject(QueryOperators.IN, new String[] { "CodeIssue" }));
				}
				effec=countByMonthFromTo(query, from, to);
			}
							
			percent=pack(total, effec);			
			
			cal.set(Calendar.DATE, 1);
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 000);
			
			for(Float f:percent){
				time=new Object[2];
				time[0]=cal.getTimeInMillis();
				time[1]=f;
				retur.add(time);
				cal.add(Calendar.MONTH, 1);
			}

		}else if(datarange.equals("quater")){
			
			if(from.equals("")) {
				cal = Calendar.getInstance();
				cal.add(Calendar.YEAR, -1);
				cal.set(Calendar.MONTH, 0);
				total=countByQuater(query);
				if (range != null && range.equals("global")) {
					query.put("fields.customfield_11208.value", new BasicDBObject(QueryOperators.IN, new String[]{"Post-Release"}));	
					query.put("fields.resolution.name",new BasicDBObject(QueryOperators.NIN, new String[]{ "Not an issue", "Duplicate", "Cannot Reproduce", "Won't Fix" }));
				} else {
					query.put("fields.customfield_11206.value", new BasicDBObject(QueryOperators.IN, new String[] { "Production" }));
					query.put("fields.resolution.name", new BasicDBObject(QueryOperators.NIN, new String[] { "Not an issue", "Duplicate", "Cannot Reproduce", "Won't Fix" }));
					query.put("fields.labels", new BasicDBObject(QueryOperators.IN, new String[] { "CodeIssue" }));
				}
				effec=countByQuater(query);
			} else {
				if(to.equals("")) {
					Date today = new Date();   
				    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");   
				    to = sdf.format(today); 
				}
				cal = strToCalendar(from);
				int month = cal.get(Calendar.MONTH);
				int beginMonth = getQuarterBeginMonth(month);
				cal.set(Calendar.MONTH, beginMonth);
				total = countByQuaterFromTo(query, from, to);
				if (range != null && range.equals("global")) {
					query.put("fields.customfield_11208.value", new BasicDBObject(QueryOperators.IN, new String[]{"Post-Release"}));	
					query.put("fields.resolution.name",new BasicDBObject(QueryOperators.NIN, new String[]{ "Not an issue", "Duplicate", "Cannot Reproduce", "Won't Fix" }));
				} else {
					query.put("fields.customfield_11206.value", new BasicDBObject(QueryOperators.IN, new String[] { "Production" }));
					query.put("fields.resolution.name", new BasicDBObject(QueryOperators.NIN, new String[] { "Not an issue", "Duplicate", "Cannot Reproduce", "Won't Fix" }));
					query.put("fields.labels", new BasicDBObject(QueryOperators.IN, new String[] { "CodeIssue" }));
				}
				effec = countByQuaterFromTo(query, from, to);
			}
			
			cal.set(Calendar.DATE, 1);
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 000);
					
        	percent=pack(total, effec);
        	
			for(Float f:percent){
				time=new Object[2];
				time[0]=cal.getTimeInMillis();
				time[1]=f;
				retur.add(time);
				cal.add(Calendar.MONTH, 3);
			}
			
		}else if(datarange.equals("year")){
			
			cal.set(2015, 0, 1);
			
			if(from.equals("")) {
				total=countByYear(query);
				if (range != null && range.equals("global")) {
					query.put("fields.customfield_11208.value", new BasicDBObject(QueryOperators.IN, new String[]{"Post-Release"}));	
					query.put("fields.resolution.name",new BasicDBObject(QueryOperators.NIN, new String[]{ "Not an issue", "Duplicate", "Cannot Reproduce", "Won't Fix" }));
				} else {
					query.put("fields.customfield_11206.value", new BasicDBObject(QueryOperators.IN, new String[] { "Production" }));
					query.put("fields.resolution.name", new BasicDBObject(QueryOperators.NIN, new String[] { "Not an issue", "Duplicate", "Cannot Reproduce", "Won't Fix" }));
					query.put("fields.labels", new BasicDBObject(QueryOperators.IN, new String[] { "CodeIssue" }));
				}
				effec=countByYear(query);
			} else {
				if(to.equals("")) {
					Date today = new Date();   
				    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");   
				    to = sdf.format(today); 
				}
				cal = strToCalendar(from);
				cal.set(Calendar.MONTH, 0);
				cal.set(Calendar.DATE, 1);
				total=countByYearFromTo(query, from, to);
				if (range != null && range.equals("global")) {
					query.put("fields.customfield_11208.value", new BasicDBObject(QueryOperators.IN, new String[]{"Post-Release"}));	
					query.put("fields.resolution.name",new BasicDBObject(QueryOperators.NIN, new String[]{ "Not an issue", "Duplicate", "Cannot Reproduce", "Won't Fix" }));
				} else {
					query.put("fields.customfield_11206.value", new BasicDBObject(QueryOperators.IN, new String[] { "Production" }));
					query.put("fields.resolution.name", new BasicDBObject(QueryOperators.NIN, new String[] { "Not an issue", "Duplicate", "Cannot Reproduce", "Won't Fix" }));
					query.put("fields.labels", new BasicDBObject(QueryOperators.IN, new String[] { "CodeIssue" }));
				}
				effec=countByYearFromTo(query, from, to);
			}
								
			percent=pack(total, effec);
			
			for(Float f:percent){
				time=new Object[2];
				time[0]=cal.getTimeInMillis();
				time[1]=f;
				retur.add(time);
				cal.add(Calendar.YEAR, 1);
			}
		}
		return retur;
	}
	
	private float Percent(Long total,Long effec){
		return total==0?100:(float)(total-effec)*100/total;
	}
	
	private List<Float> pack(List<Long> total,List<Long> effec){
		List<Float> retur=new ArrayList<Float>();
		if(total.size()==effec.size()){
			for(int i=0;i<total.size();i++){
				float percent=Percent(total.get(i),effec.get(i));
				retur.add(percent);
			}
		}else{
			System.out.println("total.size != effec.size");
		}
		return retur;
	}
		
	/* (non-Javadoc)
	 * @see com.ebay.cs.qadb.service.impl.CountService#CountByWeek(com.mongodb.BasicDBObject)
	 */
	@Override
	public List<Long> countByWeek(BasicDBObject query){
		List<Long> count=new ArrayList<Long>();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		BasicDBObject week=new BasicDBObject();	//按周查询
		long l=0;
		
		Calendar cal=Calendar.getInstance();
		Calendar calend=Calendar.getInstance();
		cal.add(Calendar.MILLISECOND, (int) (-cal.get(Calendar.ZONE_OFFSET)+28800000L));
		calend.add(Calendar.MILLISECOND, (int) (-calend.get(Calendar.ZONE_OFFSET)+28800000L));
		
		cal.add(Calendar.MONTH, -12);
		cal.set(Calendar.DATE, 1);

		week.put("$gte", sdf.format(cal.getTime()));	
		cal.add(Calendar.DATE,9-(cal.get(Calendar.DAY_OF_WEEK)));//	<周日
		week.put("$lt", sdf.format(cal.getTime()));
		
		query.put("fields.created",week);	//第一周数据（不满一周）
		l=mongoDao.find(query).count();
		count.add(l);		

		//满周循环计算
		//before() <=相等时间 返回true，after()	返回false
		while(calend.after(cal)){			
			week=new BasicDBObject();		
			week.put("$gte", sdf.format(cal.getTime()));
			cal.add(Calendar.DATE, 7);//before next Monday
			week.put("$lt", sdf.format(cal.getTime()));
			query.put("fields.created",week);//一周
			l=mongoDao.find(query).count();
			count.add(l);
		}
		return count;
	}
	
	@Override
	public List<Long> countByWeekFromTo(BasicDBObject query, String from, String to){
		List<Long> count=new ArrayList<Long>();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		BasicDBObject week=new BasicDBObject();	//按周查询
		long l=0;
		
		Calendar cal = strToCalendar(from);   //开始月
		Calendar calend = strToCalendar(to);   //结束月

		week.put("$gte", sdf.format(cal.getTime()));
		cal.add(Calendar.DATE,9-(cal.get(Calendar.DAY_OF_WEEK)));//	<周日
		week.put("$lt", sdf.format(cal.getTime()));
		
		query.put("fields.created",week);	//第一周数据（不满一周）
		l=mongoDao.find(query).count();
		count.add(l);		

		//满周循环计算
		//before() <=相等时间 返回true，after()	返回false
		while(calend.after(cal)){			
			week=new BasicDBObject();		
			week.put("$gte", sdf.format(cal.getTime()));
			cal.add(Calendar.DATE, 7);//before next Monday
			week.put("$lt", sdf.format(cal.getTime()));
			query.put("fields.created",week);//一周
			l=mongoDao.find(query).count();
			count.add(l);
		}
		return count;
	}
	
	/* (non-Javadoc)
	 * @see com.ebay.cs.qadb.service.impl.CountService#CountByMonth(com.mongodb.BasicDBObject)
	 */
	@Override
	public List<Long> countByMonth(BasicDBObject query){
		List<Long> count=new ArrayList<Long>();
		long l=0;
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM");
		Calendar cal=Calendar.getInstance();
		cal.add(Calendar.MILLISECOND, (int) (-cal.get(Calendar.ZONE_OFFSET)+28800000L));
		cal.add(Calendar.MONTH, -12);
		cal.set(Calendar.DATE, 1);
		String month=sdf.format(cal.getTime());
		
		while(cal.before(curr)){
			query.put("fields.created", Pattern.compile("^"+month+".*$",Pattern.CASE_INSENSITIVE));
			l=mongoDao.find(query).count();
			count.add(l);
			cal.add(Calendar.MONTH, 1);
			month=sdf.format(cal.getTime());
		}
		return count;
	}
	
	@Override
	public List<Long> countByMonthFromTo(BasicDBObject query, String from, String to){
		List<Long> count=new ArrayList<Long>();
		long l=0;
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM");
		Calendar cal = strToCalendar(from);   //开始月
		cal.set(Calendar.DATE, 1);
		Calendar calend = strToCalendar(to);   //结束月
		calend.set(Calendar.DATE, 1);
		
		String month=sdf.format(cal.getTime());

		while(cal.before(calend)||cal.equals(calend)){
			query.put("fields.created", Pattern.compile("^"+month+".*$",Pattern.CASE_INSENSITIVE));
			l=mongoDao.find(query).count();
			count.add(l);
			cal.add(Calendar.MONTH, 1);
			month=sdf.format(cal.getTime());
		}
		return count;
	}
	
	/* (non-Javadoc)
	 * @see com.ebay.cs.qadb.service.impl.CountService#CountByQuater(com.mongodb.BasicDBObject)
	 */
	@Override
	public List<Long> countByQuater(BasicDBObject query){
		List<Long> count=new ArrayList<Long>();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM");
		long l=0;
		Calendar cal=Calendar.getInstance();
		cal.add(Calendar.MILLISECOND, (int) (-cal.get(Calendar.ZONE_OFFSET)+28800000L));
		cal.add(Calendar.YEAR, -1);
		cal.set(Calendar.MONTH, 0);
		cal.set(Calendar.DATE, 1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 000);
		long Quater=0;
		String month=sdf.format(cal.getTime());
		while(cal.before(curr)){
			query.put("fields.created", Pattern.compile("^"+month+".*$",Pattern.CASE_INSENSITIVE));
			l=mongoDao.find(query).count();
			Quater+=l;
			if((cal.get(Calendar.MONTH)+1)%3==0){
				count.add(Quater);
				Quater=0;
			}
			cal.add(Calendar.MONTH, 1);
			month=sdf.format(cal.getTime());
		}

		return count;
	}
	
	@Override
	public List<Long> countByQuaterFromTo(BasicDBObject query, String from, String to){
		List<Long> count=new ArrayList<Long>();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM");
		long l=0;
		
		Calendar cal = strToCalendar(from);   //开始月
		int calMonth = cal.get(Calendar.MONTH);
		int calBeginMonth = getQuarterBeginMonth(calMonth);
		cal.set(Calendar.MONTH, calBeginMonth);
		cal.set(Calendar.DATE, 1);
		
		Calendar calend = strToCalendar(to);   //结束月
		int calendMonth = calend.get(Calendar.MONTH);
		int calendBeginMonth = getQuarterBeginMonth(calendMonth);
		calend.set(Calendar.MONTH, calendBeginMonth);
		calend.set(Calendar.DATE, 1);

		long Quater=0;
		String month=sdf.format(cal.getTime());
		
		while(cal.before(calend)||cal.equals(calend)){
			query.put("fields.created", Pattern.compile("^"+month+".*$",Pattern.CASE_INSENSITIVE));
			l=mongoDao.find(query).count();
			Quater+=l;
			if((cal.get(Calendar.MONTH)+1)%3==0){
				count.add(Quater);
				Quater=0;
			}
			cal.add(Calendar.MONTH, 1);
			month=sdf.format(cal.getTime());
		}

		return count;
	}
	
	/* (non-Javadoc)
	 * @see com.ebay.cs.qadb.service.impl.CountService#CountByYear(com.mongodb.BasicDBObject)
	 */
	@Override
	public List<Long> countByYear(BasicDBObject query){
		List<Long> count=new ArrayList<Long>();
		long l=0;
		Calendar calendar=Calendar.getInstance();
		calendar.set(2015, 0, 1);
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy");
		long year=curr.get(Calendar.YEAR);
		calendar.add(Calendar.MILLISECOND, (int) (-calendar.get(Calendar.ZONE_OFFSET)+28800000L));
		while(calendar.get(Calendar.YEAR)<=year){
			query.put("fields.created", Pattern.compile("^"+sdf.format(calendar.getTime())+".*$",Pattern.CASE_INSENSITIVE));
			l=mongoDao.find(query).count();
			count.add(l);
			calendar.add(Calendar.YEAR, 1);
		}
		return count;
	}
	
	@Override
	public List<Long> countByYearFromTo(BasicDBObject query, String from, String to){
		List<Long> count=new ArrayList<Long>();
		long l=0;
		
		Calendar cal = strToCalendar(from);   //开始月
		cal.set(Calendar.MONTH, 0);
		cal.set(Calendar.DATE, 1);
		
		Calendar calend = strToCalendar(to);   //结束月
		calend.set(Calendar.MONTH, 0);
		calend.set(Calendar.DATE, 1);
		
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy");
		long year=calend.get(Calendar.YEAR);
		while(cal.get(Calendar.YEAR)<=year){
			query.put("fields.created", Pattern.compile("^"+sdf.format(cal.getTime())+".*$",Pattern.CASE_INSENSITIVE));
			l=mongoDao.find(query).count();
			count.add(l);
			cal.add(Calendar.YEAR, 1);
		}
		return count;
	}
	
	@Override
	public long calculateOOSLAProductionQualityBySeverity(BasicDBObject query){
		long l=0;
		DBCursor cursor=mongoDao.find(query);
		while(cursor.hasNext()){
			cursor.next();
			l+=1;
		}
		return l;
	}
	
	@Override
	public long calculateOOSLAQAQualityBySeverity(BasicDBObject query){
		long l=0;
		DBCursor cursor=mongoDao.find(query);
		while(cursor.hasNext()){
			cursor.next();
			l+=1;
		}
		return l;
	}
	
	public List<List<String>> calculateOOSLAObjectProductionQualityBySeverity(BasicDBObject query){
		DBCursor cursor=mongoDao.find(query);
		DBObject dbo;
		List<List<String>> list = new ArrayList<List<String>>();
		while(cursor.hasNext()){
			dbo=cursor.next();
			list.add(detailService.bsonToArray(dbo));		
		}
		return list;
	}
	
	public List<List<String>> calculateOOSLAObjectQAQualityBySeverity(BasicDBObject query){
		DBCursor cursor=mongoDao.find(query);
		DBObject dbo;
		List<List<String>> list = new ArrayList<List<String>>();
		while(cursor.hasNext()){
			dbo=cursor.next();
			list.add(detailService.bsonToArray(dbo));
		}
		return list;
	}
	
	public Calendar strToCalendar(String str) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
		try {
			date = formatter.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date.getTime()-calendar.get(Calendar.ZONE_OFFSET)+28800000L);
        return calendar;
	}
	
	public int getQuarterBeginMonth(int month) {
		int beginMonth = 0;
		switch(month) {
		case 0: 
		case 1: 
		case 2: 
			beginMonth = 0; break;
		case 3:
		case 4:
		case 5:
			beginMonth = 3; break;
		case 6:
		case 7:
		case 8:
			beginMonth = 6; break;
		case 9:
		case 10:
		case 11:
			beginMonth = 9; break;
		}
		return beginMonth;
	}

}
