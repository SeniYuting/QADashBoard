<%@ page language="java" contentType="text/html; charset=GB18030"
	pageEncoding="GB18030"%>

<script type="text/javascript">
var time = new Date();
var timeStr = time.toDateString();
var month = timeStr.split(" ")[1];
var day = timeStr.split(" ")[2];
var year = timeStr.split(" ")[3];

switch(month) {
case "Jan" : month = "01"; break;
case "Feb" : month = "02"; break;
case "Mar" : month = "03"; break;
case "Apr" : month = "04"; break;
case "May" : month = "05"; break;
case "June" : month = "06"; break;
case "July" : month = "07"; break;
case "Aug" : month = "08"; break;
case "Sep" : month = "09"; break;
case "Oct" : month = "10"; break;
case "Nov" : month = "11"; break;
case "Dec" : month = "12"; break;
}

$('#from').attr("value",year-1 + '-' + month + '-01');

var yesterday = new Date();
yesterday.setDate(yesterday.getDate()-1);
var yesterdayTimeStr = yesterday.toDateString();
var yesterdayMonth = yesterdayTimeStr.split(" ")[1];
var yesterdayDay = yesterdayTimeStr.split(" ")[2];
var yesterdayYear = yesterdayTimeStr.split(" ")[3];

switch(yesterdayMonth) {
case "Jan" : yesterdayMonth = "01"; break;
case "Feb" : yesterdayMonth = "02"; break;
case "Mar" : yesterdayMonth = "03"; break;
case "Apr" : yesterdayMonth = "04"; break;
case "May" : yesterdayMonth = "05"; break;
case "June" : yesterdayMonth = "06"; break;
case "July" : yesterdayMonth = "07"; break;
case "Aug" : yesterdayMonth = "08"; break;
case "Sep" : yesterdayMonth = "09"; break;
case "Oct" : yesterdayMonth = "10"; break;
case "Nov" : yesterdayMonth = "11"; break;
case "Dec" : yesterdayMonth = "12"; break;
}

$('#to').attr("value",yesterdayYear + '-' + yesterdayMonth + '-' + yesterdayDay);

$('.form_date').datetimepicker({
    weekStart: 1,
    todayBtn:  0,
	autoclose: 1,
	todayHighlight: 1,
	startView: 2,
	minView: 2,
	forceParse: 0,
	format: 'yyyy-mm-dd',
});
$('#fromDate').datetimepicker('setEndDate', new Date());
$('#toDate').datetimepicker('setEndDate', new Date());
$('#fromDate').datetimepicker()
.on('changeDate', function(ev){
	if(ev.date!=null){
       var date = new Date(ev.date.valueOf());
       date.setHours(0);
       date.setMinutes(0);
       date.setSeconds(0);
       date.setTime(date.getTime()+86400000);
	   $('#toDate').datetimepicker('setStartDate', date);
	} else {
		$('#toDate').datetimepicker('setStartDate', '');
	}
});
$('#toDate').datetimepicker()
.on('changeDate', function(ev){
	if(ev.date!=null){
	    var date = new Date(ev.date.valueOf());
        date.setHours(0);
        date.setMinutes(0);
        date.setSeconds(0);
        date.setTime(date.getTime()-86400000);
	    $('#fromDate').datetimepicker('setEndDate', date);
	} else {
		$('#fromDate').datetimepicker('setEndDate', '');
	}
});
</script>

<div class="span12 form-horizontal" style="width: 111%;">
	<div class="span4">
		<label for="from" class="control-label" style="font-size: 105%;">From</label>
		<div class="controls date form_date" data-date="" id="fromDate">
			<span class="add-on"><i class="icon-th"
				style="cursor: pointer;"></i></span> <input size="16" type="text" value=""
				readonly id="from" style="font-weight: bold; text-align: center;">
			<span class="add-on"><i class="icon-remove"
				style="cursor: pointer;"></i></span>
		</div>
	</div>
	<div class="span2">
	    <label style="font-size: 105%; margin-top: 3%; color: red; visibility: hidden;" id="noFrom">Please select From Date</label>
	</div>
	<div class="span4">
		<label for="to" class="control-label" style="font-size: 105%;">To</label>
		<div class="controls date form_date" data-date="" id="toDate">
			<span class="add-on"><i class="icon-th"
				style="cursor: pointer;"></i></span> <input size="16" type="text" value=""
				readonly id="to" style="font-weight: bold; text-align: center;">
			<span class="add-on"><i class="icon-remove"
				style="cursor: pointer;"></i></span>
		</div>
	</div>
	<div class="span2">
		<a href="#" class="btn btn-primary btn-flat" id="filter">Refresh</a>
	</div>
</div>