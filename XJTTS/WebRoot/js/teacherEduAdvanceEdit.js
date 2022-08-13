/**
 * Created by thanosYao on 2015/7/24.
 */
$("#header").load("header.html");
$("#footer").load("footer.html");
$('[data-toggle="tooltip"]').tooltip();
$(".submit").click(function(){
	var id = url("?id");
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
	        	method:'edit',
	        	id:id,
	        	graduation:graduation.value,
	        	major:major.value,
	        	starttime:starttime.value,
	        	endtime:endtime.value,
	        	certificate:certificate.value,
	        	educationbackground:1
	        },
	        success:function(data){
	            if(data.Result == "OK"){
	                alert("编辑成功");
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
    getAvatar();
//    init();
    $(".input").blur(function(){
    	tirm("#"+$(this).attr("id"));
    });
    $("select").blur(function(){
    	tirm2("#"+$(this).attr("id"));
    });
    $(".resubmit").click(function(){
    	$(this).css("display","none");
    	$("#eivdence").html("");
    	$(".uploadDiv").css("display","block");
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
function getAvatar(){
	var id = url("?id")
    $.ajax({
        type: "post",
        url: "../teacher/tinfo_loadRecordInfo.action",
        data:{id:id},
        success: function (data) {
            if(data.Result == "OK"){
            	var background = data.Records.lstBackgrounds;
            	$("#background").html("");
            	$("#background").append($("<option value=\""+data.Records.educationBackgroundid+"\">"+data.Records.educationBackground+"</option>"));
                $("#background").append($("<option value=\"0\">－请选择－</option>"));
                for(var i=0; i<background.length; i++){
                    $("#background").append($("<option value=\""+background[i].eBackgroudId+"\">"+background[i].eBackgroudName+"</option>"));
                }
                $("#graduation").val(data.Records.graduation);
                $("#major").val(data.Records.major);
                $("#starttime").val(data.Records.starttime);
                $("#endtime").val(data.Records.endtime);
                $("#certificate").val(data.Records.certificate);
                
                var images = data.Records.eivdence;
                if(data.Records.eivdence.length > 0){
                	$("#eivdence").html("");
                    var imagestr = '';
                    for(var i=0; i<images.length; i++){
                    	imagestr += '<div style="position:relative;margin-bottom:10px;"><img alt="" src="..'+data.Records.eivdence[i].path+'" style=""><a class="closeBtn">删除</a></div>';
                    }
                    $("#eivdence").html(imagestr)
                }
                
                
//                if(data.Records.headImgPath == '0'){
//                    $("#bigAvatar").attr("src","../images/userInfomation/default.png");
//                }
//                else{
//                    $("#bigAvatar").attr("src",data.Records.headImgPath);
//                }
//                userName.innerHTML     = data.Records.name;
            }
            else{
                console.log(data);
            }
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