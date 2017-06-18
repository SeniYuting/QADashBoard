<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.opensymphony.xwork2.ActionContext"%>

<script src="<%=request.getContextPath()%>/jslib/highcharts.js"></script>
<script src="<%=request.getContextPath()%>/jslib/modules/no-data-to-display.js"></script>
<script src="<%=request.getContextPath()%>/js/QAQuality/OpeningQABugs.js"></script>

<div class="row-fluid" style="width: 97%;">
	<div class="span12">
		<div class="box">
			<div class="box-head tabs">
			    <div style="width: 90%;">
				    <h3>QA Opening Bug</h3>
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
			<div id="container" class="box-content" style="width: 95%;"></div>
			<div class="box-content">
				<div class="span12">
					<div class="alert alert-block alert-success">
						<h4 class="alert-heading">Opening Bug</h4>
						Opening Bug include the bugs : status in (Open, In Progress, Reopened) <br />
						Not including the bugs in resolved but not closed
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<jsp:include page="../common/table.jsp"></jsp:include>
