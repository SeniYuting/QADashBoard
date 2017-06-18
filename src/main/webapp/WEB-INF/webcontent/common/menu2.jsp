<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.opensymphony.xwork2.ActionContext"%>    

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<jsp:include page='../common/common.jsp'></jsp:include>

</head>
<body>
<div class="style-toggler">
	<img src="img/icons/fugue/color.png" alt="" class='tip' title="Toggle style-switcher" data-placement="right">
</div>					
<div class="style-switcher">
	<h3>Pattern-switcher</h3>
	<ul class='pattern'>
		<li><a href="#" class='default'>Default</a></li>
		<li><a href="#" class='dark'>Dark wood</a></li>
		<li><a href="#" class='light'>Light</a></li>
		<li><a href="#" class='wood'>Wood</a></li>
		<li><a href="#" class='retina-wood'>Retina-wood</a></li>
		<li><a href="#" class='linen'>Linen</a></li>
		<li><a href="#" class='paper'>Paper</a></li>
	</ul>
</div>
<div class="topbar">
	<div class="container-fluid">
		<a href="index.jsp" class='company' >QA DashBoard V2.0 </a><span class='company' style="font-size:14px; float: right;"> updated 2016/01/07 </span>
		<ul class='mini'>
		</ul>
	</div>
</div>
<div class="breadcrumbs">
	<div class="container-fluid">
		<ul id="MyRouter" class="bread pull-left">
			<li>
				<a href="index.jsp"><i class="icon-home icon-white"></i></a>
			</li>
			<% 
	    	    // default global
		        if (ActionContext.getContext().getSession().get("range") == null) {
			       ActionContext.getContext().getSession().put("range", "global");
		        }
				String range = (String)ActionContext.getContext().getSession().get("range");
	    	    
				if(range=="local") {
			%>
			<li class="first-calalog">
				<a href="index.jsp">
					CDC
				</a>
			</li>
			<%
				} else {
			%>
			<li class="first-calalog">
				<a href="index.jsp">
					Global
				</a>
			</li>
			<%
				}
			%>
			
			<li class="second-catalog">
				<a href="#">
				    Dashboard
				</a>
			</li>
			<li class="third-catalog">
				<a href="#">
				    Home
				</a>
			</li>
		</ul>

	</div>
</div>
<div class="main">
	<div class="container-fluid">
	<div class="navi">
		<ul class='main-nav'>
			<li class='active'>
				<a href="index.jsp" class='light'>
					<div class="ico"><i class="icon-home icon-white"></i></div>
					Dashboard
				</a>
			</li>
			<li>
				<a href="#" class='light toggle-collapsed'>
					<div class="ico"><i class="icon-th-large icon-white"></i></div>
					Production Quality
					<img src="img/toggle_subnav_down.png" alt="">
				</a>
				<ul class='collapsed-nav closed'>
					<li>
						<a href="home!ToProductionBugsOOSLAView.action">
							OOSLA View
						</a>
					</li>
					<li>
						<a href="home!ToProductionBugTrends.action">
							Bug Trends
						</a>
					</li>
					<li>
						<a href="home!ToOpeningProductionBugs.action">
							Opening Bug
						</a>
					</li>
					<li>
						<a href="home!ToWhenFoundBugs.action">
							When Found Bug
						</a>
					</li>
					<li>
						<a href="home!ToHowFoundBugs.action">
							How Found Bug
						</a>
					</li>
										<li>
						<a href="home!ToProductionAnalysis.action">
							Bug Analysis
						</a>
					</li>
				</ul>
			</li>
			<li>
				<a href="#" class='light toggle-collapsed'>
					<div class="ico"><i class="icon-tasks icon-white"></i></div>
					QA Quality
					<img src="img/toggle_subnav_down.png" alt="">
				</a>
				<ul class='collapsed-nav closed'>
					<li>
						<a href="home!ToQABugsOOSLAView.action">
							OOSLA View
						</a>
					</li>
					<li>
						<a href="home!ToQABugsTrends.action">
							Bug Trends
						</a>
					</li>
					<li>
						<a href="home!ToOpeningQABugs.action">
							Opening Bug
						</a>
					</li>
					<li>
						<a href="home!ToHowFoundQABugs.action">
							How Found Bug
						</a>
					</li>
					<li>
						<a href="home!ToQAAnalysis.action">
							Bug Analysis
						</a>
					</li>
				</ul>
			</li>
			<li>
				<a href="#" class='light toggle-collapsed'>
					<div class="ico"><i class="icon-signal icon-white"></i></div>
					Effectiveness
					<img src="img/toggle_subnav_down.png" alt="">
				</a>
				<ul class='collapsed-nav closed'>
					<li>
						<a href="home!ToBugEffectivity.action">
							Test Effectiveness
						</a>
					</li>
					<li>
						<a href="home!ToReopen.action">
							Reopen Bug
						</a>
					</li>
				</ul>
			</li>			
		</ul>
	</div>
	
	<div class="content-tab">
	    <ul class="nav nav-tabs">
	    	<% 	    	    
				if(range=="local") {
			%>	
	            <li class="" id="global"><a href="range!ToGlobal.action"><i class="icon-globe"></i> Global</a></li>
	            <li class="active" id="local"><a href="range!ToLocal.action"><i class="icon-bookmark"></i> CDC</a></li> 
	        <%
				} else {
			%>
	            <li class="active" id="global"><a href="range!ToGlobal.action"><i class="icon-globe"></i> Global</a></li>
	            <li class="" id="local"><a href="range!ToLocal.action"><i class="icon-bookmark"></i> CDC</a></li> 
			<%
				} 
			%>  
        </ul>
    </div>
			
		<div class="content">
			<jsp:include page="../../home.jsp"></jsp:include>
		</div>
	</div>	
</div>  
</body>
</html>
