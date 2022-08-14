var swiper = new Swiper('.swiper-container', {
      slidesPerView: 3,
      spaceBetween: 30,
	  centeredSlides: true,
	  loop: true,
	  autoplay:true,
      pagination: {
        el: '.swiper-pagination',
        clickable: true,
      },
});


$(".submit-btn").click(function(){
	var input1 = $(".contact-name").val().replace(/^\s+|\s+$/g, '');
	var input2 = $(".contact-email").val().replace(/^\s+|\s+$/g, '');
	var input3 = $(".contact-content").val().replace(/^\s+|\s+$/g, '');
	var emailReg = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
	if(input1 == ""){
		$(".contact-tips").html("*请填写姓名");
		return false;
	}else if(input2 == ""){
		$(".contact-tips").html("*请填写邮箱");
		return false;
	}else if(!emailReg.test(input2)){
		$(".contact-tips").html("*请填写正确的邮箱地址");
		return false;
	}else if(input3 == ""){
		$(".contact-tips").html("*请填写内容");
		return false;
	}else{
		$(".contact-tips").html("");
		$(".contact-box").hide();
		$(".contact-success").show();
	}
});
