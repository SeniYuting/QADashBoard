<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.opensymphony.xwork2.ActionContext"%> 

<script src="<%=request.getContextPath()%>/jslib/highcharts.js"></script>
<script src="<%=request.getContextPath()%>/jslib/modules/no-data-to-display.js"></script>
<script src="<%=request.getContextPath()%>/js/ProductQuality/OOSLAview.js"></script>

<div class="span4">
    <span class="control-label" style="font-size: 105%;">Priority</span>
    <select id="priority" style="font-size: 102%;">
        <option value="">ALL</option>
        <option value="P1">P1</option>
        <option value="P2">P2</option>
        <option value="P3">P3</option>
        <option value="P4">P4</option>
        <option value="None">None</option>
    </select>
</div>
<div class="span2">
    <a href="#" class="btn btn-primary btn-flat" id="filter">Refresh</a>
</div>

<div class="row-fluid" style="width: 97%;">
	<div class="span12">
		<div class="box">
			<div class="box-head tabs">
			    <div style="width: 90%;">
				    <h3>Production OOSLA View</h3>
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
					<div class="alert alert-block alert-danger">
						<h4 class="alert-heading">OOSLA View</h4>
						OOSLA View include the bugs : status in (Resolved, Open, In Progress, Reopened) 
					</div>
				</div>
			</div>	

			<div class="box-content" style="margin-top: -30px;">
				<div class="span3">
					<div class="alert alert-block alert-success">
						<h4 class="alert-heading">P1</h4>
						Fix immediately<br/><br/><br/>
					</div>
				</div>

				<div class="span3">
					<div class="alert alert-block alert-success">
						<h4 class="alert-heading">P2</h4>
						Fix next scheduled release, none open more than 2 weeks
					</div>
				</div>
				
				<div class="span3">
					<div class="alert alert-block alert-success">
						<h4 class="alert-heading">P3</h4>
						Fix within next 2 scheduled releases, none open more than 90 days
					</div>
				</div>
				
				<div class="span3">
					<div class="alert alert-block alert-success">
						<h4 class="alert-heading">P4</h4>
						Fix within next 3 scheduled releases, none open more than 180 days
					</div>
				</div>
			</div>
			
		</div>
	</div>
</div>

<jsp:include page="../common/table.jsp"></jsp:include>