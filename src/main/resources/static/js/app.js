var config = window.config = {};

var stompClient = {
	client : null,
	socket : null,
	connect : function() {
		this.socket = new SockJS('/cosmos/websocket');
		this.client = Stomp.over(this.socket);
		//            this.client.debug = null;
		this.client.connect({}, function(frame) {
			stompClient.client.subscribe('/topic/chatbot', function(events) {
				stompClient.consume(events);
			});

		});
	},
	consume : function(raw) {
		updateChatDash();
		//console.log("Chart DAta   "+raw);

		var response = JSON.parse(raw.body);
		debugger;
		$("#dash-title").empty();
		$("#dash-title").html(response.title);
		if (response.type == "BubbleChart") {
			$("dash-title").html(response.title);
			showChart(response.data, response.x_max, response.y_max);
		} else if (response.type == "Table") {
			showTable(response.data);
		} else if (response.type == "Message") {
			updateResponse(response.data);
		}
	},
	close : function() {

		if (this.client != null && this.client != undefined) {
			this.client.unsubscribe('/topic/chatbot');
			//	this.client.unsubscribe('/topic/count');
			this.client.disconnect();
			this.client = null;
		}
		closeChart(); // closing chart window
	}
};

// Config reference element
var $ref = $("#ref");

// Configure responsive bootstrap toolkit
config.ResponsiveBootstrapToolkitVisibilityDivs = {
	'xs' : $('<div class="device-xs 				  hidden-sm-up"></div>'),
	'sm' : $('<div class="device-sm hidden-xs-down hidden-md-up"></div>'),
	'md' : $('<div class="device-md hidden-sm-down hidden-lg-up"></div>'),
	'lg' : $('<div class="device-lg hidden-md-down hidden-xl-up"></div>'),
	'xl' : $('<div class="device-xl hidden-lg-down			  "></div>'),
};

ResponsiveBootstrapToolkit.use('Custom', config.ResponsiveBootstrapToolkitVisibilityDivs);

//validation configuration
config.validations = {
	debug : true,
	errorClass : 'has-error',
	validClass : 'success',
	errorElement : "span",

	// add error class
	highlight : function(element, errorClass, validClass) {
		$(element).parents("div.form-group")
			.addClass(errorClass)
			.removeClass(validClass);
	},

	// add error class
	unhighlight : function(element, errorClass, validClass) {
		$(element).parents(".has-error")
			.removeClass(errorClass)
			.addClass(validClass);
	},

	// submit handler
	submitHandler : function(form) {
		form.submit();
	}
}

//delay time configuration
config.delayTime = 50;

// chart configurations
config.chart = {};

config.chart.colorPrimary = tinycolor($ref.find(".chart .color-primary").css("color"));
config.chart.colorSecondary = tinycolor($ref.find(".chart .color-secondary").css("color"));
/***********************************************
*        Animation Settings
***********************************************/
function animate(options) {
	var animationName = "animated " + options.name;
	var animationEnd = "webkitAnimationEnd mozAnimationEnd MSAnimationEnd oanimationend animationend";
	$(options.selector)
		.addClass(animationName)
		.one(animationEnd,
			function() {
				$(this).removeClass(animationName);
			}
	);
}

$(function() {
	var $itemActions = $(".item-actions-dropdown");

	$(document).on('click', function(e) {
		if (!$(e.target).closest('.item-actions-dropdown').length) {
			$itemActions.removeClass('active');
		}
	});

	$('.item-actions-toggle-btn').on('click', function(e) {
		e.preventDefault();

		var $thisActionList = $(this).closest('.item-actions-dropdown');

		$itemActions.not($thisActionList).removeClass('active');

		$thisActionList.toggleClass('active');
	});
});

/***********************************************
*        NProgress Settings
***********************************************/
var npSettings = {
	easing : 'ease',
	speed : 500
}

NProgress.configure(npSettings);
$(function() {
	setSameHeights();

	var resizeTimer;

	$(window).resize(function() {
		clearTimeout(resizeTimer);
		resizeTimer = setTimeout(setSameHeights, 150);
	});
});


function setSameHeights($container) {
	$container = $container || $('.sameheight-container');

	var viewport = ResponsiveBootstrapToolkit.current();

	$container.each(function() {

		var $items = $(this).find(".sameheight-item");

		// Get max height of items in container
		var maxHeight = 0;

		$items.each(function() {
			$(this).css({
				height : 'auto'
			});
			maxHeight = Math.max(maxHeight, $(this).innerHeight());
		});


		// Set heights of items
		$items.each(function() {
			// Ignored viewports for item
			var excludedStr = $(this).data('exclude') || '';
			var excluded = excludedStr.split(',');

			// Set height of element if it's not excluded on 
			if (excluded.indexOf(viewport) === -1) {
				$(this).innerHeight(maxHeight);
			}
		});
	});
}

$(document).on('click', '.panel-heading span.icon_minim', function(e) {
	var $this = $(this);
	if (!$this.hasClass('panel-collapsed')) {
		$this.parents('.panel').find('.panel-body').slideUp();
		$this.addClass('panel-collapsed');
		$this.removeClass('glyphicon-minus').addClass('glyphicon-plus');
	} else {
		$this.parents('.panel').find('.panel-body').slideDown();
		$this.removeClass('panel-collapsed');
		$this.removeClass('glyphicon-plus').addClass('glyphicon-minus');
	}
});
$(document).on('focus', '.panel-footer input.chat_input', function(e) {
	var $this = $(this);
	if ($('#minim_chat_window').hasClass('panel-collapsed')) {
		$this.parents('.panel').find('.panel-body').slideDown();
		$('#minim_chat_window').removeClass('panel-collapsed');
		$('#minim_chat_window').removeClass('glyphicon-plus').addClass('glyphicon-minus');
	}
});
$(document).on('click', '#new_chat', function(e) {
	var size = $(".chat-window:last-child").css("margin-left");
	size_total = parseInt(size) + 400;
	alert(size_total);
	var clone = $("#chat_window_1").clone().appendTo(".container");
	clone.css("margin-left", size_total);
});
$(document).on('click', '.icon_close', function(e) {
	//$(this).parent().parent().parent().parent().remove();
	$("#chat_window_1").remove();
});

//SignupForm validation
$(function() {
	if (!$('#signup-form').length) {
		return false;
	}

	var signupValidationSettings = {
		rules : {
			firstname : {
				required : true,
			},
			lastname : {
				required : true,
			},
			email : {
				required : true,
				email : true
			},
			password : {
				required : true,
				minlength : 8
			},
			retype_password : {
				required : true,
				minlength : 8,
				equalTo : "#password"
			},
			agree : {
				required : true,
			}
		},
		groups : {
			name : "firstname lastname",
			pass : "password retype_password",
		},
		errorPlacement : function(error, element) {
			if (
				element.attr("name") == "firstname" ||
				element.attr("name") == "lastname"
			) {
				error.insertAfter($("#lastname").closest('.row'));
				element.parents("div.form-group")
					.addClass('has-error');
			} else if (
				element.attr("name") == "password" ||
				element.attr("name") == "retype_password"
			) {
				error.insertAfter($("#retype_password").closest('.row'));
				element.parents("div.form-group")
					.addClass('has-error');
			} else if (element.attr("name") == "agree") {
				error.insertAfter("#agree-text");
			} else {
				error.insertAfter(element);
			}
		},
		messages : {
			firstname : "Please enter firstname and lastname",
			lastname : "Please enter firstname and lastname",
			email : {
				required : "Please enter email",
				email : "Please enter a valid email address"
			},
			password : {
				required : "Please enter password fields.",
				minlength : "Passwords should be at least 8 characters."
			},
			retype_password : {
				required : "Please enter password fields.",
				minlength : "Passwords should be at least 8 characters."
			},
			agree : "Please accept our policy"
		},
		invalidHandler : function() {
			animate({
				name : 'shake',
				selector : '.auth-container > .card'
			});
		}
	}

	$.extend(signupValidationSettings, config.validations);

	$('#signup-form').validate(signupValidationSettings);
});
//LoginForm validation
$(function() {
	if (!$('#login-form').length) {
		return false;
	}

	var loginValidationSettings = {
		rules : {
			username : {
				required : true,
				email : true
			},
			password : "required",
			agree : "required"
		},
		messages : {
			username : {
				required : "Please enter username",
				email : "Please enter a valid email address"
			},
			password : "Please enter password",
			agree : "Please accept our policy"
		},
		invalidHandler : function() {
			animate({
				name : 'shake',
				selector : '.auth-container > .card'
			});
		}
	}

	$.extend(loginValidationSettings, config.validations);

	$('#login-form').validate(loginValidationSettings);
})
$(function() {

	$('#sidebar-menu, #customize-menu').metisMenu({
		activeClass : 'open'
	});


	$('#sidebar-collapse-btn').on('click', function(event) {
		event.preventDefault();

		$("#app").toggleClass("sidebar-open");
	});

	$("#sidebar-overlay").on('click', function() {
		$("#app").removeClass("sidebar-open");
	});

});
$(function() {
	$('.hider').click(function() {
		return $(this).parent('.message').removeClass('blur');
	});
}.call(this));

$(function() {


	function drawDashboardItemsListSparklines() {
		$(".dashboard-page .items .sparkline").each(function() {
			var type = $(this).data('type');

			// There is predefined data
			if ($(this).data('data')) {
				var data = $(this).data('data').split(',').map(function(item) {
					if (item.indexOf(":") > 0) {
						return item.split(":");
					} else {
						return item;
					}
				});
			}
			// Generate random data
			else {
				var data = [];
				for (var i = 0; i < 17; i++) {
					data.push(Math.round(100 * Math.random()));
				}
			}


			$(this).sparkline(data, {
				barColor : config.chart.colorPrimary.toString(),
				height : $(this).height(),
				type : type
			});
		});
	}

	drawDashboardItemsListSparklines();

	$(document).on("themechange", function() {
		drawDashboardItemsListSparklines();
	});
});
$(function() {

	$('.actions-list > li').on('click', '.check', function(e) {
		e.preventDefault();

		$(this).parents('.tasks-item')
			.find('.checkbox')
			.prop("checked", true);

		removeActionList();
	});

});
var modalMedia = {
	$el : $("#modal-media"),
	result : {},
	options : {},
	open : function(options) {
		options = options || {};
		this.options = options;


		this.$el.modal('show');
	},
	close : function() {
		if ($.isFunction(this.options.beforeClose)) {
			this.options.beforeClose(this.result);
		}

		this.$el.modal('hide');

		if ($.isFunction(this.options.afterClose)) {
			this.options.beforeClose(this.result);
		}
	}
};
$(function() {
	$('.nav-profile > li > a').on('click', function() {
		var $el = $(this).next();

		animate({
			name : 'flipInX',
			selector : $el
		});
	});
})
$(function() {

	// Local storage settings
	var themeSettings = getThemeSettings();

	// Elements

	var $app = $('#app');
	var $styleLink = $('#theme-style');
	var $customizeMenu = $('#customize-menu');

	// Color switcher
	var $customizeMenuColorBtns = $customizeMenu.find('.color-item');

	// Position switchers
	var $customizeMenuRadioBtns = $customizeMenu.find('.radio');


	// /////////////////////////////////////////////////

	// Initial state

	// On setting event, set corresponding options

	// Update customize view based on options

	// Update theme based on options

	/************************************************
	*				Initial State
	*************************************************/

	setThemeSettings();

	/************************************************
	*					Events
	*************************************************/

	// set theme type
	$customizeMenuColorBtns.on('click', function() {
		themeSettings.themeName = $(this).data('theme');

		setThemeSettings();
	});


	$customizeMenuRadioBtns.on('click', function() {

		var optionName = $(this).prop('name');
		var value = $(this).val();

		themeSettings[optionName] = value;

		setThemeSettings();
	});

	function setThemeSettings() {
		setThemeState()
			.delay(config.delayTime)
			.queue(function(next) {

				setThemeColor();
				setThemeControlsState();
				saveThemeSettings();

				$(document).trigger("themechange");

				next();
			});
	}

	/************************************************
	*			Update theme based on options
	*************************************************/

	function setThemeState() {
		// set theme type
		if (themeSettings.themeName) {
			$styleLink.attr('href', 'css/app-' + themeSettings.themeName + '.css');
		} else {
			$styleLink.attr('href', 'css/app.css');
		}

		// App classes
		$app.removeClass('header-fixed footer-fixed sidebar-fixed');

		// set header
		$app.addClass(themeSettings.headerPosition);

		// set footer
		$app.addClass(themeSettings.footerPosition);

		// set footer
		$app.addClass(themeSettings.sidebarPosition);

		return $app;
	}

	/************************************************
	*			Update theme controls based on options
	*************************************************/

	function setThemeControlsState() {
		// set color switcher
		$customizeMenuColorBtns.each(function() {
			if ($(this).data('theme') === themeSettings.themeName) {
				$(this).addClass('active');
			} else {
				$(this).removeClass('active');
			}
		});

		// set radio buttons
		$customizeMenuRadioBtns.each(function() {
			var name = $(this).prop('name');
			var value = $(this).val();

			if (themeSettings[name] === value) {
				$(this).prop("checked", true);
			} else {
				$(this).prop("checked", false);
			}
		});
	}

	/************************************************
	*			Update theme color
	*************************************************/
	function setThemeColor() {
		config.chart.colorPrimary = tinycolor($ref.find(".chart .color-primary").css("color"));
		config.chart.colorSecondary = tinycolor($ref.find(".chart .color-secondary").css("color"));
	}

	/************************************************
	*				Storage Functions
	*************************************************/

	function getThemeSettings() {
		var settings = (localStorage.getItem('themeSettings')) ? JSON.parse(localStorage.getItem('themeSettings')) : {};

		settings.headerPosition = settings.headerPosition || '';
		settings.sidebarPosition = settings.sidebarPosition || '';
		settings.footerPosition = settings.footerPosition || '';

		return settings;
	}

	function saveThemeSettings() {
		localStorage.setItem('themeSettings', JSON.stringify(themeSettings));
	}

});
$(function() {

	$("body").addClass("loaded");

});


/***********************************************
*        NProgress Settings
***********************************************/

// start load bar
NProgress.start();

// end loading bar 
NProgress.done();
