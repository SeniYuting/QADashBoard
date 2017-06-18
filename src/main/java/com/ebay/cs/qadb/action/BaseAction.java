package com.ebay.cs.qadb.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ApplicationAware;
import org.apache.struts2.interceptor.RequestAware;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

public class BaseAction extends ActionSupport implements RequestAware,
		SessionAware, ApplicationAware,ServletResponseAware, ServletRequestAware  {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8787366185643946123L;
	protected Map<String, Object> application;
	protected Map<String, Object> session;
	protected Map<String, Object> request;
	protected HttpServletRequest httpRequest;
	protected HttpServletResponse httpResponse;
	

	@Override
	public void setApplication(Map<String, Object> application) {
		this.application = application;
	}

	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;

	}

	@Override
	public void setRequest(Map<String, Object> request) {
		this.request = request;

	}

	public Map<String, Object> getApplication() {
		return application;
	}

	public Map<String, Object> getSession() {
		return session;
	}

	public Map<String, Object> getRequest() {
		return request;
	}

	public HttpServletRequest getHttpRequest() {
		return httpRequest;
	}

	public void setHttpRequest(HttpServletRequest httpRequest) {
		this.httpRequest = httpRequest;
	}

	public HttpServletResponse getHttpResponse() {
		return httpResponse;
	}

	public void setHttpResponse(HttpServletResponse httpResponse) {
		this.httpResponse = httpResponse;
	}

	@Override
	public void setServletResponse(HttpServletResponse response) {
		
		httpResponse=response;
	}

	@Override
	public void setServletRequest(HttpServletRequest request) {
		httpRequest=request;
		
	}
	//
	//ajax
	/**
	 * AJAX传输的通用方法
	 * AJAX方法
	 * @param 要发送的数据（字符串）
	 */
	public void Information(String s) {
		// 乱码问题
		getHttpResponse().setContentType("text/html;charset=utf-8");
		PrintWriter out;
		try {
			out = getHttpResponse().getWriter();
			out.write(s);
			out.flush();
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
