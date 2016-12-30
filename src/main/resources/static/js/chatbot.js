/**
 * chat-bot
 */

var app_properties = {
	/*title : "Cognitive Maintenance Assistant",
	sub_title : "Sense – Think – Predict –Cognitize –Optimize - React"*/
		title : "AI Assistance",
		sub_title : ""
}

var username = "user";
var password = "user"
var isChatInit = true;
var l = 1; //index for chat bot
var r = 1; // index for chat bot

$(document).ready(function() {

	$('#app-title-container').html("<div id='app-title'>" + app_properties.title + "</div><div id='app-sub-title'>" + app_properties.sub_title + "</div>")

	/**
	 * page intial loading settings
	 */
	$('.chat-bot-icon').fadeIn();
	$(".sidebar-footer").empty();
	$("#event-more").click(function() {
		$('html, body').animate({
			scrollTop : $("#event-table").offset().top
		}, 2000);
	});

	$(".show-alert").click(function() {
		$('#chat-container').empty();
		var x = $(".show-alert").text();
		//alert("hello "+x);
		isChatInit = false;
		openChatbot();
		sendMessage(x);
	});
	function _fetchKPIData(uri, username, password) {
		var xhr = new XMLHttpRequest();
		xhr.open("GET", uri, true);
		xhr.withCredentials = true;
		xhr.setRequestHeader("Authorization", 'Basic ' + btoa(username + ':' + password));
		xhr.onload = function() {
			updateKPIData(JSON.parse(xhr.responseText));
		}
		xhr.send();
	}

	_fetchKPIData("hana/odata/kpi", username, password);

	function updateKPIData(data) {
		$.each(data.data, function(key, value) {
			// console.log(key, value);wC
			var id = "#" + key + "-value";
			var sub = "";
			if (key == "downtime") {
				sub = " Hr";
			} else if (key == "utilization") {
				sub = "%";
			} else if (key == "production") {
				//sub = " / Day";
			} else if (key == "headcount") {
				//sub = " / Week";
			}
			$(id).text(value + sub);
		});
	}




	var modal = $('#chat-bot-dailog');

	$("#disconnect").click(function() {
		// disconnect chat bot once done with conversation
		stompClient.close();
	});
	// invoke connect method while initiating chat bot
	$("#connect").click(function() {
		stompClient.connect();
	});

	closeChart = function() {
		$('#chart svg').css('display', 'none');
		stompClient.close();
	}

	/**/
	var barChartInit = true;
	updateChatDash = function() {
	//	$("#ws-dash-board").empty();
		//$("#ws-dash-board").append("<div id='utilization-chart'></div>");
		$('#utilization-chart').empty();
		//$('#utilization-chart').css('display', 'block');
		$('#utilization-chart').append("<svg></svg>");
	}


	showTable = function(tableData) {
		$('#utilization-chart').empty();
		$('#utilization-chart').append('<table id="table-data" class="display" width="100%"></table>');

		/*  $('#table-data tfoot th').each( function () {
		       var title = $(this).text();
		       $(this).html( '<input type="text" placeholder="Search '+title+'" />' );
		   } );*/

		var table = $('#table-data').DataTable({
			data : tableData,
			scrollY : 250,
			paging : false,
			columns : [
				{
					title : "Pump No"
				},
				{
					title : "Name"
				},
				{
					title : "RUL"
				},
				{
					title : "Pressure"
				},
				{
					title : "Flowrate"
				},
				{
					title : "Speed RPM"
				}

			]
		});
	/*  
	  // Apply the search
	  table.columns().every( function () {
	      var that = this;
	 
	      $( 'input', this.footer() ).on( 'keyup change', function () {
	          if ( that.search() !== this.value ) {
	              that
	                  .search( this.value )
	                  .draw();
	          }
	      } );
	  } );*/
	}

	showChart = function(data, x_max, y_max) {
		//debugger;
		// When the user clicks on <span> (x), close the modal
		/*$('#pop-up-close').click(function() {
			$('#utilization-chart').css('display', 'none');
		});
*/
		/*// When the user clicks anywhere outside of the modal, close it
		window.onclick = function(event) {
			if (event.target == $('#chat-bot-dailog')) {
				$('#chat-bot-dailog').css('display', 'none');
			}
		}*/
		//	
		var chart = nv.models.scatterChart()
			.showDistX(false)
			.showDistY(false)
			.showLegend(false)
			.xDomain([ 0, x_max ])
			.yDomain([ 0, y_max ])
			.color(d3.scale.category10().range());
		chart.xAxis.tickFormat(d3.format('.01f'));
		chart.yAxis.tickFormat(d3.format('f'));
		//chart.yAxis.tickValues(['2', '4', '8', '16', '32']);
		chart.xAxis
			.axisLabel('Remaing Useful Life (Days)');

		chart.yAxis
			.axisLabel('Cost ($)')
			.tickFormat(d3.format('f'));
		//,engine_no,name
		chart.tooltipContent(function(key, x, y, name) {
			return '<h6>  Engine No:' + name.point.engine_no + '</br> Name: ' + name.point.name + ' </br>RUL: ' + x + '</br> COST:' + y + '</h6>';
		});


		d3.select('#utilization-chart svg')
			.datum(data)
			.transition().duration(500)
			.call(chart);
		var y_mid = y_max / 2;
		var line1 = d3.select('#utilization-chart svg')
			.append('line')
			.attr({
				x1 : 75 + chart.xAxis.scale()(0),
				y1 : 30 + chart.yAxis.scale()(y_mid),
				x2 : 75 + chart.xAxis.scale()(x_max),
				y2 : 30 + chart.yAxis.scale()(y_mid)
			})
			.style("stroke", "#000");
		var line1 = d3.select('#utilization-chart svg')
			.append('line')
			.attr({
				x1 : 75 + chart.xAxis.scale()(x_max / 2),
				y1 : 30 + chart.yAxis.scale()(0),
				x2 : 75 + chart.xAxis.scale()(x_max / 2),
				y2 : 30 + chart.yAxis.scale()(y_max)
			})
			.style("stroke", "#000");

		nv.utils.windowResize(function() {
			chart.update();
		});


	}

	$("#btn-chat").click(function() {
		var query = $('#chat-msg').val();
		//updateResponse("Hi, How I can help you !");
		sendMessage(query);

	});
	$('#chat-msg').keypress(function(event) {
		var keycode = (event.keyCode ? event.keyCode : event.which);
		if (keycode == '13') {
			var query = $('#chat-msg').val();
			//updateResponse("Hi, How I can help you !");
			sendMessage(query);
		}
	});


	function sendMessage(message) {
		r = r + 1;
		//<h7 class="media-heading"><b>Admin</b></h7>
		var time_x = new Date($.now());
		var message_el = '<p> ' + message + ' </p><p style="text-align:right">' + formatAMPM(time_x) + '</p>';
		var chat_con_right_el = '<div class="media right-media" id="chat-conv-r' + r + '"> </div>';
		//alert(chat_con_right_el);
		//var chat_head_right_el = '<div class="media-right"><img src="images/user.png" class="media-object" style="width:60px"></div>';
		var msg_body_el = ' <div class="media-body" id="msg-container-r' + r + '"> </div>';
		$("#chat-container").append(chat_con_right_el);
		$("#chat-conv-r" + r).append(msg_body_el);
		//	$("#chat-conv-r" + i).append(chat_head_right_el);
		$("#msg-container-r" + r).append(message_el);
		updateScroll("chat-container");
		$('#chat-msg').val('');
		invokeChatAPI(message);
	}

	function formatAMPM(date) {
		var hours = date.getHours();
		var minutes = date.getMinutes();
		var ampm = hours >= 12 ? 'pm' : 'am';
		hours = hours % 12;
		hours = hours ? hours : 12; // the hour '0' should be '12'
		minutes = minutes < 10 ? '0' + minutes : minutes;
		var strTime = hours + ':' + minutes + ' ' + ampm;
		return strTime;
	}

	updateResponse = function(message) {
		l = l + 1;
		var time_x = new Date($.now());
		//<h4 class="media-heading"><b>Chat Bot</b></h4>
		var message_el = '<p> ' + message + ' </p> <p style="text-align:right">' + formatAMPM(time_x) + '</p>';
		var chat_con_left_el = '<div class="media left-media" id="chat-conv' + l + '"></div>';
		//var chat_head_left_el = '<div class="media-left"> <img src="images/bot.png" class="media-object" style="width:60px"></div>';
		var msg_body_el = ' <div class="media-body" id="msg-container' + l + '"></div>';

		$("#chat-container").append(chat_con_left_el);
		$("#chat-conv" + l).append(msg_body_el);
		$("#msg-container" + l).append(message_el);

		$('#chat-msg').val('');
		updateScroll("chat-container");


	}

	function updateScroll(id) {
		var scrolled = false;
		if (!scrolled) {
			var element = document.getElementById(id);
			element.scrollTop = element.scrollHeight;
		}
	}

	function invokeChatAPI(query) {
		//stompClient.connect();
		var xhr = new XMLHttpRequest();
		xhr.open("GET", "nlp/chatbot/" + query, true);
		xhr.withCredentials = true;
		xhr.setRequestHeader("Authorization", 'Basic ' + btoa('user:user'));
		console.log("abc");
		xhr.onload = function() {
			console.log("loading");
			console.log(xhr.responseText);
			updateResponse(xhr.responseText);

		}
		xhr.send();
	}
	$("#chat-container").on('scroll', function() {
		scrolled = true;
	});
	/**
	 * Chat Bot -- Manage hide and show functionality
	 */

	$("#chat-bot-close").click(function() {
	
		$('#chat-bot').addClass('col-xl-0').removeClass('col-xl-3');
		$('#chat-bot').hide();
		$("utilization-chart").hide();
		$('#main-container').addClass('col-xl-12').removeClass('col-xl-9');
		$("utilization-chart").show();
		showUtilizationChart(historicalBarChart);
		$('.chat-bot-icon').fadeIn();
		//togglePage(false);
		//stompClient.close();

	});

	$('.chat-bot-icon').click(function() {
		openChatbot();
	});
	openChatbot = function() {
		
		//$("utilization-chart").hide();
		$('#main-container').addClass('col-xl-9').removeClass('col-xl-12');
		//$("utilization-chart").show();
		showUtilizationChart(historicalBarChart);
		$('#chat-bot').show();
		$('#chat-bot').addClass('col-xl-3').removeClass('col-xl-0');
		$('.chat-bot-icon').fadeOut();
		stompClient.connect();
		updateScroll("chat-bot-main");
		if (isChatInit) {
			invokeChatAPI("chat-init");
			isChatInit = false;
		}
		$('#chat-msg').focus();
		//togglePage(true);
	}

	togglePage = function(isChatOpened) {
		if (isChatOpened) {
			//$('#header-container').hide();
			$('#header-container').css('left', '1%');
			//$('#header-container').show();
			
			//$('.footer').hide();
			$('.footer').css('left', '1%');
			//$('.footer').show();
			$('.sidebar').css('width', '1%');
			//$('.app').hide();
			$('.app').css('padding-left', '1%');
			//$('.app').show();
		} else {
			$('#header-container').hide();
			$('#header-container').css('left', '12%');
			$('#header-container').show();
			$('.footer').hide();
			$('.footer').css('left', '12%');
			$('.footer').show();
			$('.sidebar').css('width', '12%');
			$('.app').hide();
			$('.app').css('padding-left', '12%');
			$('.app').show();
		}

	}
	var historicalBarChart = [];
	fetchBarChartData();
	function fetchBarChartData() {
		var xhr = new XMLHttpRequest();
		xhr.open("GET", "hana/odata/utilization/barchart", true);
		xhr.withCredentials = true;
		xhr.setRequestHeader("Authorization", 'Basic ' + btoa('user:user'));
		xhr.onload = function() {
			//console.log("loading");
			//console.log(JSON.stringify(JSON.parse(xhr.responseText)));
			historicalBarChart = JSON.parse(xhr.responseText);
			showUtilizationChart(historicalBarChart);
		}
		xhr.send();
	}

	function showUtilizationChart(data) {
		updateChatDash();
		//$("#utilization-chart").append('<div class="container"  data-toggle="popover" title="Popover Header" data-content="Some content inside the popover">abc</div>');
		/*
		  <h3>Popover Example</h3>
		  <a href="#" data-toggle="popover" title="Popover Header" data-content="Some content inside the popover">Toggle popover</a>
		</div>*/
		$("#dash-title").empty();
		$("#dash-title").html("Plant Utilization");

		var chart = nv.models.discreteBarChart()
			//.useInteractiveGuideline(true)
			.x(function(d) {
				return d.Months
			})
			.y(function(d) {
				return d.Utilization
			});
		chart.margin({
			"left" : 70
		});
		var width = $("svg").parent().width();
		var height = $("svg").parent().height();
	//	alert("height"+height+"width "+width);
		chart.width(width);
		chart.height(height);
		chart.xAxis
			.axisLabel('Month');

  
		chart.yAxis
			.axisLabel('Utilization(%)')
			.tickFormat(d3.format('f'));
		
		d3.select('#utilization-chart svg')
			.datum(data)
			.transition().duration(500)
			.call(chart);
		nv.utils.windowResize(function() {
			chart.update();
		});

		

	}

	$.simpleWeather({
		woeid : '2357536', //2357536
		location : 'Houston, TX 77024',
		unit : 'f',
		success : function(weather) {
			html = '<h2>' + weather.temp + '&deg;' + weather.units.temp + '</h2>';
			html += '<ul><li>' + weather.city + ', ' + weather.region + '</li>';
			html += '<li class="currently">' + weather.currently + '</li>';
			html += '<li>' + weather.alt.temp + '&deg;C</li></ul>';
			//	      
			//	      for(var i=0;i<weather.forecast.length;i++) {
			//	        html += '<p>'+weather.forecast[i].day+': '+weather.forecast[i].high+'</p>';
			//	      }
			//	  
			$("#weather").html(html);
		},
		error : function(error) {
			// this.success(this.weather);
			$("#weather").html('<p>' + error + '</p>');
		}
	});
	
	/**
	 * popvoer
	 */
	/*$('#ex1').slider({
		formatter: function(value) {
			return 'Current value: ' + value;
		}
	});
	 $('#bnt-settings').popover({placement : 'bottom',content:"<input id='ex1' data-slider-id='ex1Slider' type='text' data-slider-min='0' data-slider-max='20' data-slider-step='1' data-slider-value='14'/>", html: true});
*/
});