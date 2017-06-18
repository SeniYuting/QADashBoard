<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.opensymphony.xwork2.ActionContext"%>

<script src="<%=request.getContextPath()%>/jslib/highstock.js"></script>
<script src="<%=request.getContextPath()%>/js/Effectivity/effectivity.js"></script>

<div class="row-fluid" style="width: 97%;">

	<jsp:include page="../common/filter.jsp"></jsp:include>

	<div class="span12">
		<div class="box">
			<div class="box-head tabs">
			    <div style="width: 90%;">
				    <h3>Test Effectiveness</h3>
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
			
			<jsp:include page="../common/datarange.jsp"></jsp:include>
			
			<div id="container" class="box-content" style="width: 95%;"></div>
			<div class="box-content">
				<div class="span12">
					<div class="alert alert-block alert-success">
						<h4 class="alert-heading">Effectiveness</h4>
						TE = (all bugs - production bugs)/all bugs<br/>
						<% if(range=="local") { %>
						    production bugs include the bugs : where found in (Production), fields.resolution.name not in (Not an issue, Duplicate, Cannot Reproduce, Won't Fix), fields.labels in (CodeIssue) 
						<% } else { %>
						    production bugs include the bugs : when found in (Post-Release), fields.resolution.name not in (Not an issue, Duplicate, Cannot Reproduce, Won't Fix)
						<% } %>
					</div>
				</div>
			</div>
		</div>
	</div>