$(document).ready(function() {
	$('.carousel').carousel();
	$('#myCarousel').swipe({
		swipeLeft: function() {
			$(this).carousel('next');
		},
		swipeRight: function() {
			$(this).carousel('prev');
		},
	});
	$(".apk").popover({
		trigger: 'hover', //鼠标以上时触发弹出提示框
		html: true, //开启html 为true的话，data-content里就能放html代码了
		content: "<img src='./img/microQr.png' style='width:200px;'>"
	});
	$(".ipa").popover({
		trigger: 'hover', //鼠标以上时触发弹出提示框
		html: true, //开启html 为true的话，data-content里就能放html代码了
		content: "<img src='./img/二维码.qricon.min.jpg'>"
	});
	$(".wxgzh").popover({
		trigger: 'hover', //鼠标以上时触发弹出提示框
		html: true, //开启html 为true的话，data-content里就能放html代码了
		content: "<img src='./img/公众号.png'>",
		placement:"top"
	});
//	$(".wxxcx").popover({
//		trigger: 'hover', //鼠标以上时触发弹出提示框
//		html: true, //开启html 为true的话，data-content里就能放html代码了
//		content: "<img src='./img/二维码.qricon.min.jpg'>",
//		placement:"top"
//	});
	$(function() {
		if($(window).width() > 768) {
			$(".dl").show();
			$(".phonedl").hide();
			$(".logo-group").show();
			$(".logo-group1").hide();
			$(".wxgzh-mobile").hide();
			$(".wxxcx-mobile").hide();
			$(".wxgzh").show();
			$(".wxxcx").show();
			//					$(".list-item").removeClass("col-xs-4");
			//					$(".red-pkg").hide();
			//					$(".red-pkg2").show();
		} else {
			$(".dl").hide();
			$(".phonedl").show();
			$(".logo-group").hide();
			$(".logo-group1").show();
			$(".wxgzh-mobile").show();
			$(".wxxcx-mobile").show();
			$(".wxgzh").hide();
			$(".wxxcx").hide();
			//					$(".list-item").addClass("col-xs-4");
			//					$(".red-pkg").show();
			//					$(".red-pkg2").hide();
		}
		$(window).resize(function() {
			if($(window).width() > 768) {
				$(".dl").show();
				$(".phonedl").hide();
				$(".logo-group").show();
				$(".logo-group1").hide();
				$(".wxgzh-mobile").hide();
				$(".wxxcx-mobile").hide();
				$(".wxgzh").show();
				$(".wxxcx").show();
				//						$(".list-item").removeClass("col-xs-4");
				//						$(".red-pkg").hide();
				//						$(".red-pkg2").show();
			} else {
				$(".dl").hide();
				$(".phonedl").show();
				$(".logo-group").hide();
				$(".logo-group1").show();
				$(".wxgzh-mobile").show();
				$(".wxxcx-mobile").show();
				$(".wxgzh").hide();
				$(".wxxcx").hide();
				//						$(".list-item").addClass("col-xs-4");
				//						$(".red-pkg").show();
				//						$(".red-pkg2").hide();
			}
		});
	})
});