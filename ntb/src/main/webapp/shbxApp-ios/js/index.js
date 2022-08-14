$(".closeLayer").click(function(){
	$(".layerDiv").hide();
});

$(".hotline").click(function(){
	$(".layerDiv").show();
	_czc.push(["_trackEvent", "咨询热线", "点击查看"]);
});
function loading(){
	$("body").show();
	var mySwiper1 = new Swiper('.banner .swiper-container',{
	    pagination: '.banner .pagination',
	    loop:true,    
	    grabCursor: true,
	    effect : 'fade',
	    paginationClickable: true,
	    autoplayDisableOnInteraction : false,//手指滑动后继续滑动
	    autoplay: {
	        delay: 5000,
	        stopOnLastSlide: false,
	        disableOnInteraction: false,
        },
        pagination: {
            el: '.banner .swiper-pagination',
        }
	});
	var mySwiper2 = new Swiper('.userStories .swiper-container',{
	    pagination: '.userStories .pagination',
	    loop:true,    
	    grabCursor: true,
	    paginationClickable: true,
	    autoplayDisableOnInteraction : false,//手指滑动后继续滑动
	    autoplay: {
	        delay: 5000,
	        stopOnLastSlide: false,
	        disableOnInteraction: false,
        },
        pagination: {
            el: '.userStories .swiper-pagination',
        }
	});
}
