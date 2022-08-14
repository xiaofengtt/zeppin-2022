var uuid = '';
$(function(){
	$(".main-left-item:eq(2)").children("a").addClass("color-orange").children("img").attr("src","./img/r-3.png");
	$(".main-left-item:eq(2) ul li:eq(2) a").addClass("color-orange");
	$(".iframe").css({"height":$(window).height()});
	$(".closeIframe").click(function(){
		$(".iframe").hide();
		$(".sureBtn").removeClass("loadingBtn").html("修改").addClass("modifyBtn");
	});
	
	getInfo();
	$(".loadingOver").show();
	$(".loadingDiv").hide();

});
$(window).resize(function(){
	$(".iframe").css({"height":$(window).height()});
});

//财税服务申请信息
function getInfo(){
	$.ajax({
        url: '../rest/qcb/finance/get',
        type: 'get',
        data: {
        	"timestamp":new Date().getTime() 
        }
    }).done(function(r) {
    	if(r.status == "SUCCESS"){
    		if(r.data.flagApply==true){
        		$(".openIframe").html("已申请");
        		uuid = r.data.uuid;
        		$(".contactName").val(r.data.contacts);
        		$(".contactTel").val(r.data.contactsMobile);
        		$(".iframe .sureBtn").html("修改");
        	}else{
        		
        	}
    	}else{
    		layer.msg(r.message);
    	}
    	
    }).fail(function(r){
    	layer.msg("error", {
            time: 2000
        });
    });
	
}

$(".iframe .modifyBtn").click(function(){
	if(checkNonempty($(".contactName").val())){
		$(".contactName").removeClass("light").siblings(".tip").fadeOut();				
	}else{
		$(".contactName").addClass("light").focus().siblings(".tip").fadeIn();
		return false;
	}
	if(checkNonempty($(".contactTel").val())){
		$(".contactTel").removeClass("light").siblings(".tip").fadeOut();				
	}else{
		$(".contactTel").addClass("light").focus().siblings(".tip").fadeIn();
		return false;
	}
	$(".sureBtn").addClass("loadingBtn").html("").removeClass("modifyBtn");
	if(uuid==""){
		var form = $("#addForm");
		$.post(form.attr("action"),"contacts="+$(".contactName").val().replace(/(^\s*)|(\s*$)/g,"")
				+"&contactsMobile="+$(".contactTel").val().replace(/(^\s*)|(\s*$)/g,"")
				+"&CSRFToken="+$('input[name="CSRFToken"]').val(), function(data) {
			if (data.status == "SUCCESS") {
				$(".iframe").hide();
				layer.msg("申请成功！", {
		             time: 2000
		         },function(){
		        	 window.location.href=window.location.href;
		         });
			} else {
				layer.msg(data.message);
			}
			$(".sureBtn").removeClass("loadingBtn").html("申请").addClass("modifyBtn");
		});		
		return false;
	}else{
		var form = $("#editForm");
		$.post(form.attr("action"),"contacts="+$(".contactName").val().replace(/(^\s*)|(\s*$)/g,"")
				+"&contactsMobile="+$(".contactTel").val().replace(/(^\s*)|(\s*$)/g,"")
				+"&uuid="+uuid
				+"&CSRFToken="+$('input[name="CSRFToken"]').val(), function(data) {
			if (data.status == "SUCCESS") {
				$(".iframe").hide();
				layer.msg("修改成功！", {
		             time: 2000
		         },function(){
		        	 window.location.href=window.location.href;
		         });
			} else {
				layer.msg(data.message);
			}
			$(".sureBtn").removeClass("loadingBtn").html("修改").addClass("modifyBtn");
		});		
		return false;
	}
	
	
});
$(".openIframe").click(function(){
	$(".iframe").show();
});
$(".contactName").blur(function(){
	if($(this).val().replace(/(^\s*)|(\s*$)/g, "")!=""){
		$(".contactName").removeClass("light").siblings(".tip").fadeOut();		
	}
});
$(".contactTel").blur(function(){
	if($(this).val().replace(/(^\s*)|(\s*$)/g, "")!=""){
		$(".contactTel").removeClass("light").siblings(".tip").fadeOut();		
	}
});

