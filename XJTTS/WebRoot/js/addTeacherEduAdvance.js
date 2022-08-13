/**
 * Created by thanosYao on 2015/7/24.
 */
$("#header").load("header.html");
$("#footer").load("footer.html");
$('[data-toggle="tooltip"]').tooltip();
$(".submit").click(function(){
	var rs1=tirm("#graduation");
	var rs2=tirm("#major");
	var rs3=tirm("#starttime");
	var rs4=tirm("#endtime");
	var rs5=tirm("#certificate");
	var rs6=tirm2("#background");
	var flag=true;
	if(!rs1||!rs2||!rs3||!rs4||!rs5||!rs6){
		flag=false;
	}
	if(flag==true){
		$(".submit").html("提交中...");
    	$(".btn").attr("disabled", "disabled");
		$.ajax({
	        url: "../teacher/tinfo_addEduAdvance.action",
	        method: "POST",
	        data: {
	        	method:'add',
	        	graduation:graduation.value,
	        	major:major.value,
	        	starttime:starttime.value,
	        	endtime:endtime.value,
	        	certificate:certificate.value,
	        	educationbackground:1
	        },
	        success:function(data){	        	
	            if(data.Result == "OK"){
	                $(".content").html('<h4>提交成功，页面即将跳转。。。</h4>');
	                window.setInterval("location='teacherEduAdvance.html'",3000);
	            }
	            else{
	            	$(".submit").html("提交");
	            	$(".btn").removeAttr("disabled");
	                errorInfo.innerHTML= data.Message;
	                $(".alert-danger").removeClass("hide");
	            }
	        }
	    })
	}else{
		$("html,body").animate({ "scrollTop": 0 });
	}
    
})
$(document).ready(function(){
	$('.datepicker').datepicker({
		language : "zh-CN",
		format : 'yyyy-mm-dd',
		startView: 2,
		endDate : '1d',
		autoclose : true
	});
//    init();
    getEducation();
    $(".input").blur(function(){
    	tirm("#"+$(this).attr("id"));
    });
    $("select").blur(function(){
    	tirm2("#"+$(this).attr("id"));
    });
    $(".btn1").click(function(){
    	window.location.href="teacherEduAdvance.html";
    });
    var height=$(window).height();
	$(".main").css("min-height",height-$("footer").outerHeight(true)-$(".navbar").outerHeight(true)-115+"px");
	$(window).resize(function(){
		var height=$(window).height();
		$(".main").css("min-height",height-$("footer").outerHeight(true)-$(".navbar").outerHeight(true)-115+"px");	
	});
});
function getEducation(){
	
	$.ajax({
        type: "POST",
        url: "../teacher/tinfo_initAddEduAdvance.action",
        dataType: "text",
        async: false,
        success: function (data) {
        	var json = (new Function("", "return " + data.split("&&&")[0]))();
        	var Result = json.Result;
            if(Result == "OK"){
                records = json.Records;
                var background = records[0].lstBackgrounds;
                $("#background").html("");
                $("#background").append($("<option value=\"0\">－请选择－</option>"));
                for(var i=0; i<background.length; i++){
                    $("#background").append($("<option value=\""+background[i].eBackgroudId+"\">"+background[i].eBackgroudName+"</option>"));
                }
            }
            else{
                console.log(jsonx.Message);
            }
        },error:function(){
        	
        }
    })
}

//验证非空
function tirm(value){
	var values=$(value).val().replace(/(^\s*)|(\s*$)/g, "");
	if (values == "") {
		$(value).css({
			"border-color" : "#f00",
			"box-shadow" : "none"
		}).parent().find("span.errorInfo").css("display","block");
		return false;
	} else {
		$(value).removeAttr("style").parent().find("span.errorInfo").css("display","none");
		return true;
	}
}
//验证select
function tirm2(value) {
	var values =$(value).val().replace(/(^\s*)|(\s*$)/g, "");
	if (values == "0") {
		$(value).css({
			"border-color" : "#f00",
			"box-shadow" : "none"
		}).parent().find("span.errorInfo").css("display","block");
		return false;
	} else {
		$(value).removeAttr("style").parent().find("span.errorInfo").css("display","none");
		return true;
	}
}

