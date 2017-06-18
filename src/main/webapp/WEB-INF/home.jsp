<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<script src="<%=request.getContextPath()%>/js/home.js"></script>
<script src="<%=request.getContextPath()%>/jslib/highcharts.js"></script>
<script src="<%=request.getContextPath()%>/jslib/modules/no-data-to-display.js"></script>
<script src="<%=request.getContextPath()%>/jslib/modules/exporting.js"></script>

<div class="row-fluid">
	<div class="span6">
		<div class="box">
			<div class="box-head">
				<div style="width: 80%;">
					<h3>Production OOSLA View</h3>
				</div>
			</div>
			<div id="container1" class="box-content"></div>
		</div>
	</div>

	<div class="span6">
		<div class="box">
			<div class="box-head">
				<div style="width: 80%;">
					<h3>Production Opening Bug</h3>
				</div>
			</div>
			<div id="container2" class="box-content"></div>
		</div>
	</div>
</div>

<div class="row-fluid">
	<div class="span6">
		<div class="box">
			<div class="box-head">
				<div style="width: 90%;">
					<h3>QA OOSLA View</h3>
				</div>
			</div>
			<div id="container3" class="box-content"></div>
		</div>
	</div>

	<div class="span6">
		<div class="box">
			<div class="box-head">
				<div style="width: 90%;">
					<h3>QA Opening Bug</h3>
				</div>
			</div>
			<div id="container4" class="box-content"></div>
		</div>
	</div>
</div>

<jsp:include page="./webcontent/common/table.jsp"></jsp:include>
