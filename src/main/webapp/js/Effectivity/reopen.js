var datarange="month",
from='',
to='',
names = ['CS Platform'];

$(drawChart=function(){
	var seriesOptions=[],
	seriesCounter=0;
	Highcharts.setOptions({
		global:{
			useUTC:false,
		},
		colors:[ '#49afcd' ]
	});
	
	function columnClick(event){
			var reopen=event.point.series.name;
			var time=event.point.category;
			var domain = names[0];
			console.log(domain);
			$.ajax({
					url:'Detail!getReopenDetail.action',
					data:'reopen='+reopen+'&time='+time+'&datarange='+datarange+'&domain='+domain,
					success:function(data){
						dataSet=data.detail;
						var table=$("#MyGird").dataTable({
							"aoColumnDefs": [
							                 {"aTargets": [0],
							                  "mRender": function (data, display, row) {
							                   return "<a href=\"https://jirap.corp.ebay.com/browse/"+data+"\" target=\"_blank\">"+data+"</a>";
							                   }
							                 }],
							retrieve: true,            
						});
						table.fnClearTable();
						table.fnAddData(dataSet);
						table.fnFilter("");
						$(".modal").modal('show');
					}
			});
		}
	
	function createChart(datarange){
		console.log(seriesOptions);
		console.log(datarange);
		
	    var Interval = 86400000;
		
		var chart = new Highcharts.Chart({
			
		 	chart: {renderTo: 'container', type: 'column'},
		 	navigator : {enabled : false,
		 				adaptToUpdatedData: true
		 	},
		 	credits:{
	              enabled: true,
	              href: "index.jsp",
	              text: 'ebay.com'
	        },
	        title: {
	                text: ''
	        },
			scrollbar: {
      			  enabled: false
    		},
	        rangeSelector: {
	        		enabled: false,
			 		buttonSpacing:5,
				 	buttons: [{
				     	type: 'month',
				     	count: 1,
				     	text: 'W'
				    }],
					inputEnabled: false,
			     	selected: 0,
			 },
			 xAxis: {
    			 	type: 'datetime',
    			 	tickWidth:10,
    			 	lineWidth:4,
    			 	labels:{
    			 		formatter:function(){
    			 			var time = new Date(this.value);
						    var timeStr = time.toDateString();
						    var month = timeStr.split(" ")[1];
						    var year = timeStr.split(" ")[3];

						    var result = '';
							if(datarange=="month"){		
								result = month + "/" + year;	
							} else if(datarange=="quater"){		
							    var quarter = '';
							    switch(month) {
							        case 'Jan': quarter = 'Q1'; break;
							        case 'Apr': quarter = 'Q2'; break;
							        case 'Jul': quarter = 'Q3'; break;
							        case 'Oct': quarter = 'Q4'; break;
							    }
							    result = quarter + "/" + year;						    
							} else if(datarange=="year") {
								result = year;
							}					
		
							return result;
    			 		}
    			 	}
    		},
    		yAxis: {  
				    allowDecimals:false,
				    min:0,
				    labels: {
				    	formatter: function () {
				    		return this.value;
				    	}
				    },
    		 title: {
	                text: 'Reopen Bug'
	            }
			},	
			navigator:{	enabled:false	},
			legend: {
    			    enabled: true,
    			    align:'center'
    		},
    		tooltip:{
    					valueDecimals: 2,
    					formatter: function() {													
							var time = new Date(this.x);
						    var timeStr = time.toDateString();
						    var month = timeStr.split(" ")[1];
						    var year = timeStr.split(" ")[3];

						    var result = '<span style="color:'+ this.series.color + ';font-weight:bold;">' + this.series.name + '</span>' + '<br/>';
							if(datarange=="month"){		
								result += month + '/' + year;	
							} else if(datarange=="quater"){		
							    var quarter = '';
							    switch(month) {
							        case 'Jan': quarter = 'Q1'; break;
							        case 'Apr': quarter = 'Q2'; break;
							        case 'Jul': quarter = 'Q3'; break;
							        case 'Oct': quarter = 'Q4'; break;
							    }
							    result += quarter + '/' + year;						    
							} else if(datarange=="year") {
								result += year;
							}					
							result += ': ' + '<span style="font-weight:bold;">'+ this.y + '</span>';
							
							return result;
						},
			},
			plotOptions: {
	            column: {
					point:{
						cursor:'pointer',
						events:{
							click:function(event){
								columnClick(event);
								//alert(event.point.category+', y='+event.point.y+","+event.point.series.name);
							}
						}
					},
	            }
	        },
    		series:seriesOptions,
		});
	}
	
	function getData(){
		seriesCounter=0;
		$.each(names,function(i,domain){
			$.getJSON('Effective!reopen.action?domain='+domain+'&datarange='+datarange+'&from='+from+'&to='+to,function(data){
				if(datarange=="month")
				    data.data.pop();
				
				seriesOptions[i] = {
	            	name: data.domain,
	            	data: data.data
	        	};
	   		    seriesCounter += 1;
	            if (seriesCounter === names.length) {
	                createChart(datarange);
	            }
			});
		});
	}	

	//datarange select
	$('.zoom_controls a').click(function(e){
			e.preventDefault();
		    datarange = $(this).attr('data-range');
		    from = '';
		    to = '';
		    
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
		    
			var chart = $('#container').highcharts();
        	getData();
	});
	
	$('#filter').click(function(e){
		e.preventDefault();
		from = $('#from').val();
	    to = $('#to').val();
	    
	    if(from=='' && to!='') {
	    	$('#noFrom').css("visibility", "visible");
	    } else {
	    	$('#noFrom').css("visibility", "hidden");
	        drawChart();
	    }
	});
	
	$.each(names,function(i,domain){
			$.getJSON('Effective!reopen.action?domain='+domain+'&datarange='+datarange+'&from='+from+'&to='+to,function(data){

				if(datarange=="month")
				    data.data.pop();
				
				seriesOptions[i] = {
	            	name: data.domain,
	            	data: data.data
	        	};
	   		    seriesCounter += 1;
	            if (seriesCounter === names.length) {
	                createChart(datarange);
	            }
			});
	});
});

function setDomain(value){
	if(value=="CSPlatform"){
		names = ['CS Platform'];
	} else if(value=="CSAgentDesktop") {
		names = ['CS Agent'];
	} else if(value=="CSGenesys") {
		names = ['CS Genesys'];
	} else if(value=="CSSiebel") {
		names = ['CS Siebel'];
	} else if(value=="CSOCS") {
		names = ['OCS'];
	} else if(value=="GCXCommunicator") {
		names = ['Communicator'];
	} else{
		names = [value];
	}
	
	from = $('#from').val();
    to = $('#to').val();	
    $('#noFrom').css("visibility", "hidden");
	drawChart();
}