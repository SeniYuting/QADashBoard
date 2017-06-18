<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.opensymphony.xwork2.ActionContext"%>

<script src="<%=request.getContextPath()%>/jslib/highstock.js"></script>
<script src="<%=request.getContextPath()%>/jslib/modules/no-data-to-display.js"></script>
<script src="<%=request.getContextPath()%>/js/QAQuality/QABugTrends.js"></script>

<div class="row-fluid" style="width: 97%;">

	<jsp:include page="../common/filter.jsp"></jsp:include>

	<div class="span12">
		<div class="box">
			<div class="box-head tabs">
			    <div style="width: 90%;">
				    <h3>QA Bug Trends</h3>
				</div>
				
				<% 
				String range = (String)ActionContext.getContext().getSession().get("range");
				if(range=="local") {
				%>	
				    <jsp:include page="../common/domain.jsp"></jsp:include>		
				<%
				} else {
				%>
				    <jsp:include page="../common/globaldomain.jsp"></jsp:include>				    
				<%
				} 
				%>
				
			</div>
			
			<jsp:include page="../common/weekdatarange.jsp"></jsp:include>
			
			<div id="container" class="box-content" style="width: 95%;"></div>
		</div>
	</div>
</div>

<jsp:include page="../common/table.jsp"></jsp:include>