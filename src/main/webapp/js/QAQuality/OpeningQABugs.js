var domain='CS Platform';

$(drawChart=function () {
	var seriesOptions =[],
	seriesCounter = 0,
	names = ['CS Platform'];
	Highcharts.setOptions({
		global:{
			useUTC:false,
		},
		colors:[ '#49afcd' ]
	});
	
	function columnClick(event){
		var severity=event.point.category;
		
		$.ajax({
				url:'Detail!getQADetail.action',
				data:'domain='+domain+'&severity='+severity+'&status=Opening',
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
	
	function createChart(){
		
	    $('#container').highcharts({
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
		        	categories: ['1-Critical','2-Major','3-Average','4-Minor'],
		        	crosshair: true,
		        	title: {
		                text: 'Severity'
		            }
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
		        plotOptions: {
					column: {
						stacking:'normal',
						pointWidth:70,
						point:{
							cursor:'pointer',
							events:{
								click:function(event){
									columnClick(event);
								}
							}
						},
		            }
		        },
		        series: seriesOptions
		    });
	}; 
	
	$.each(names,function(i,name){
		$.getJSON('QAQuality!OpeningQABug.action?domain='+domain,function(data){

			seriesOptions[i] ={
				name : data.domain,
				data : data.data
			};
			
			seriesCounter += 1;
			if(seriesCounter ==names.length){
				if (data.data[0]==0 && data.data[1]==0 && data.data[2]==0 && data.data[3]==0) {
					seriesOptions[i] = {name:data.domain, data: []};
				}
				createChart();
			}
			
		});
	});
	
});

function setDomain(value){
	if(value=="CSPlatform"){
		domain="CS Platform";
	} else if(value=="CSAgentDesktop") {
		domain="CS Agent";
	} else if(value=="CSGenesys") {
		domain="CS Genesys";
	} else if(value=="CSSiebel") {
		domain="CS Siebel";
	} else if(value=="CSOCS") {
		domain="OCS";
	} else if(value=="GCXCommunicator") {
		domain="Communicator";
	} else{
		domain=value;
	}

	drawChart();
}