var xCategories = ['CS Platform','CBT','Communicator','ASAC'];

if($('#global').hasClass('active')) {
	xCategories = ['CS Platform','AD','CS Genesys','Siebel','OCS', 'Communicator'];
}

$(function () {
	var seriesOptions =[],
	seriesCounter = 0,
	names = ['1-Critical','2-Major','3-Average','4-Minor'];
	Highcharts.setOptions({
		global:{
			useUTC:false,
		},
		colors:[ '#da4f49','#faa732','#006dcc','#49afcd','#5bb75b', '#363636']
	});	
	
    function columnClick(event){
		var severity=event.point.series.name;
		var domain=event.point.category;
		if(domain=="AD") {
			domain = "CS Agent";
		}
		if(domain=="Siebel") {
			domain = "CS Siebel";
		}
		$.ajax({
				url:'Detail!getProductionDetail.action',
				data:'domain='+domain+'&severity='+severity+'&status=NotClosed'+'&isOOSLA=total',
				success:function(data){
					dataSet=data.detail;
					var table=$("#MyGird").dataTable();
					table.fnClearTable();
					table.fnAddData(dataSet);
					$(".modal").modal('show');
				}
		});
	}
	
	function createChart(){
		
	  var chart = new Highcharts.Chart({
		        chart: {
		        	renderTo: 'container1',
		            type: 'bar'
		        },
		        credits:{
	                       enabled: true,
	                       href: "index.jsp",
	                       text: 'ebay.com'
	            },
		        title: {
		            text: ''
		        },
		        xAxis: {
		            categories: xCategories
		        },
		        exporting:{
		        	enabled:true
		        },
		        yAxis: {
		        	allowDecimals:false,
		            min:0 ,
		            title: {
		                text: 'Bug Count'
		            }
		        },
		        lang:{
		        	noData: 'No Data For This Query!'
		        },
		        tooltip: {
		            positioner: function() {
	        	        return {
	        	            x: 0,
	        	            y: 0
	        	        }
	        	    },
	        	    pointFormat: '<span style="color:{series.color};font-weight:bold;">{series.name}</span> : <span style="font-weight:bold;">{point.y}</span><br/>Total: {point.stackTotal}'
		        },
		        plotOptions: {
           		 bar: {
               		 stacking: 'normal',
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
		        series: seriesOptions
		    });
	}; 
	
	$.each(names,function(i,name){
		$.getJSON('ProductionQuality!OOSLAAll.action?severity='+name,function(data){
			console.log(data);
			seriesOptions[i] ={
				name : data.severity,
				data : data.data
			};
			
			seriesCounter += 1;
			if(seriesCounter == names.length){
				console.log(data);
		
				createChart();
			}
			
		});
	});
	
});

$(function () {
	var seriesOptions =[],
	seriesCounter = 0,
	names = ['1-Critical','2-Major','3-Average','4-Minor'];
	Highcharts.setOptions({
		colors:[ '#da4f49','#faa732','#006dcc','#49afcd','#5bb75b', '#363636']
	});
	
	function columnClick(event){
		var severity=event.point.series.name;
		var domain=event.point.category;
		if(domain=="AD") {
			domain = "CS Agent";
		}
		if(domain=="Siebel") {
			domain = "CS Siebel";
		}
		$.ajax({
				url:'Detail!getProductionDetail.action',
				data:'domain='+domain+'&severity='+severity+'&status=Opening',
				success:function(data){
					dataSet=data.detail;
					var table=$("#MyGird").dataTable();
					table.fnClearTable();
					table.fnAddData(dataSet);
					$(".modal").modal('show');
				}
		});
	}
		
	function createChart(){
		
	  var chart = new Highcharts.Chart({
		        chart: {
		        	renderTo: 'container2',
		            type: 'column'
		        },
		        credits:{
	                       enabled: true,
	                       href: "index.jsp",
	                       text: 'ebay.com'
	            },
		        title: {
		            text: ''
		        },
		        xAxis: {
		            categories: xCategories
		        },
		        exporting:{
		        	enabled:true
		        },
		        yAxis: {
		        	allowDecimals:false,
		            min:0,
		            title: {
		                text: 'Bug Count'
		            }
		        },
		        lang:{
		        	noData: 'No Data For This Query!'
		        },
		        tooltip: {
		            positioner: function() {
	        	        return {
	        	            x: 0,
	        	            y: 0
	        	        }
	        	    },
	        	    pointFormat: '<span style="color:{series.color};font-weight:bold;">{series.name}</span> : <span style="font-weight:bold;">{point.y}</span><br/>Total: {point.stackTotal}'
		        },
		        plotOptions: {
		        	column: {
	               		 stacking: 'normal',
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
		        series: seriesOptions
		    });
	}; 
	
	$.each(names,function(i,name){
		$.getJSON('ProductionQuality!OpeningProductionBugAll.action?severity='+name,function(data){
			console.log(data);
			seriesOptions[i] ={
				name : data.severity,
				data : data.data
			};
			
			seriesCounter += 1;
			if(seriesCounter ==names.length){
				var allEmpty = true;
				for (var k =0; k < seriesOptions.length; k++) {
					var data = seriesOptions[k].data;
					if (data[0]!=0 || data[1]!=0 || data[2]!=0 || data[3]!=0) {	
						if(data.length <= 4) {
							allEmpty = false;
						} else if (data[4]!=0 || data[5]!=0) {
							allEmpty = false;
						}		
					}
				}
				
				if (allEmpty) {
					seriesOptions = [];
				}
				
				createChart();
			}
	    
		});
	});
	
});

$(function () {
	var seriesOptions =[],
	seriesCounter = 0,
	names = ['1-Critical','2-Major','3-Average','4-Minor'];
	Highcharts.setOptions({
		colors:[ '#da4f49','#faa732','#006dcc','#49afcd', '#363636','#5bb75b']
	});
	
	function columnClick(event){
		var severity=event.point.series.name;
		var domain=event.point.category;
		if(domain=="AD") {
			domain = "CS Agent";
		}
		if(domain=="Siebel") {
			domain = "CS Siebel";
		}
		$.ajax({
				url:'Detail!getQADetail.action',
				data:'domain='+domain+'&severity='+severity+'&status=NotClosed'+'&isOOSLA=total',
				success:function(data){
					dataSet=data.detail;
					var table=$("#MyGird").dataTable();
					table.fnClearTable();
					table.fnAddData(dataSet);
					$(".modal").modal('show');
				}
		});
	}
	
	function createChart(){
		
	    $('#container3').highcharts({
		        chart: {
		            type: 'bar'
		        },
		        credits:{
	                       enabled: true,
	                       href: "index.jsp",
	                       text: 'ebay.com'
	            },
		        title: {
		            text: ''
		        },
		        xAxis: {
		            categories: xCategories,
		            crosshair: true
		        },
		        exporting:{
		        	enabled:true
		        },
		        yAxis: {
		        	allowDecimals:false,
		            min:0,
		            title: {
		                text: 'Bug Count'
		            }
		        },
		        lang:{
		        	noData: 'No Data For This Query!'
		        },
		        tooltip: {
		            positioner: function() {
	        	        return {
	        	            x: 0,
	        	            y: 0
	        	        }
	        	    },
	        	    pointFormat: '<span style="color:{series.color};font-weight:bold;">{series.name}</span> : <span style="font-weight:bold;">{point.y}</span><br/>Total: {point.stackTotal}'
		        },
				plotOptions: {
						 bar: {
				  		 stacking: 'normal',
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
		        series: seriesOptions
		    });
	}; 
	
	$.each(names,function(i,name){
		$.getJSON('QAQuality!OOSLAAll.action?severity='+name,function(data){
			console.log(data);
			seriesOptions[i] ={
				name : data.severity,
				data : data.data
			};
			
			seriesCounter += 1;
			if(seriesCounter ==names.length){
				createChart();
			}
			
		});
	});
	
});

$(function () {
	var seriesOptions =[],
	seriesCounter = 0,
	names = ['1-Critical','2-Major','3-Average','4-Minor'];
	Highcharts.setOptions({
		colors:[ '#da4f49','#faa732','#006dcc','#49afcd', '#363636','#5bb75b']
	});
	
	function columnClick(event) {
		var severity=event.point.series.name;
		var domain=event.point.category;
		if(domain=="AD") {
			domain = "CS Agent";
		}
		if(domain=="Siebel") {
			domain = "CS Siebel";
		}
		$.ajax({
				url:'Detail!getQADetail.action',
				data:'domain='+domain+'&severity='+severity+'&status=Opening',
				success:function(data){
					dataSet=data.detail;
					var table=$("#MyGird").dataTable();
					table.fnClearTable();
					table.fnAddData(dataSet);
					$(".modal").modal('show');
				}
		});
	}
	
	function createChart(){	
	    $('#container4').highcharts({
		        chart: {
		            type: 'column'
		        },
		        credits:{
	                       enabled: true,
	                       href: "index.jsp",
	                       text: 'ebay.com'
	            },
		        title: {
		            text: ''
		        },
		        xAxis: {
		            categories: xCategories,
		            crosshair: true
		        },
		        exporting:{
		        	enabled:true
		        },
		        yAxis: {
		        	allowDecimals:false,
		            min:0,
		            title: {
		                text: 'Bug Count'
		            }
		        },
		        lang:{
		        	noData: 'No Data For This Query!'
		        },
		        tooltip: {
		            positioner: function() {
	        	        return {
	        	            x: 0,
	        	            y: 0
	        	        }
	        	    },
	        	    pointFormat: '<span style="color:{series.color};font-weight:bold;">{series.name}</span> : <span style="font-weight:bold;">{point.y}</span><br/>Total: {point.stackTotal}'
		        },
				plotOptions: {
					column: {
				  		 stacking: 'normal',
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
		        series: seriesOptions
		    });
	}; 
	
	$.each(names,function(i,name){
		$.getJSON('QAQuality!OpeningQABugAll.action?severity='+name,function(data){
			console.log(data);
			seriesOptions[i] ={
				name : data.severity,
				data : data.data
			};
			
			seriesCounter += 1;
			if(seriesCounter ==names.length){
				createChart();
			}
			
		});
	});
	
});
