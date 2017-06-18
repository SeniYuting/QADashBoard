<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.opensymphony.xwork2.ActionContext"%>  

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<title>QADashBoard</title>

<link rel="stylesheet" href="<%=request.getContextPath()%>/css/bootstrap.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/bootstrap-responsive.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/bootstrap-datetimepicker.min.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/uniform.default.css" >
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/jquery.fancybox.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/style.css">

<script src="<%=request.getContextPath()%>/jslib/jquery.min.js"></script>
<script src="<%=request.getContextPath()%>/js/jquery.js"></script>
<script src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>
<script src="<%=request.getContextPath()%>/js/bootstrap-datetimepicker.js"></script>
<script src="<%=request.getContextPath()%>/js/jquery.fancybox.js"></script>
<script src="<%=request.getContextPath()%>/js/jquery.cookie.js"></script>
<script src="<%=request.getContextPath()%>/js/jquery.uniform.min.js"></script>
<script src="<%=request.getContextPath()%>/js/custom.js"></script>
<script src="<%=request.getContextPath()%>/js/demo.js"></script>
<script src="<%=request.getContextPath()%>/js/jquery.dataTables.min.js"></script>
<script src="<%=request.getContextPath()%>/js/jquery.dataTables.bootstrap.js"></script>


 <script type="text/javascript">
 jQuery(document).ready(function(){
	
		$('.collapsed-nav a').click(function(e){
			$('.collapsed-nav a').css("color","black");
			e.preventDefault();
			var href=$(this).attr('href');
			var cataglog = $(this).text();
			
			var title = '';
			if(href.indexOf("Production")!=-1||href.indexOf("ToWhenFoundBugs")!=-1||href.indexOf("ToHowFoundBugs")!=-1) {
				title = 'Production Quality';
			} 
			if(href.indexOf("QA")!=-1) {
				title = 'QA Quality';
			}
			if(href.indexOf("Effectivity")!=-1||href.indexOf("Reopen")!=-1) {
				title = 'Effectiveness';
			}
			
			$(this).css("color","#1E90FF");
			
			<% 
    	    // default global
	        if (ActionContext.getContext().getSession().get("range") == null) {
		       ActionContext.getContext().getSession().put("range", "global");
	        }
			String range = (String)ActionContext.getContext().getSession().get("range");
    	    
			if(range=="local") {
		    %>
		    $('.first-calalog a').html('CDC');
		    <%
			} else {
		    %>
		    $('.first-catalog a').html('Global');
		    <%
			} 
		    %> 
			
			$('.second-catalog a').html(title);
			$('.third-catalog a').html(cataglog);
			//alert(href);
			$.ajax({
				url:href,
				success:function(data){
					$('.content').html(data);
				}
			});
		});
			
 }); 
 </script>
