var tableTh = [];
var uuid;
var smsCode = true;

//工资明细key
var wagesInfoKey = [];

//工资明细value
var wagesInfoValue = [];

//工资明细title
var wagesInfoTitle = "";

//工资明细激励话语
var wagesInfoRemark = "";
$(function() {
    $(".main-left-item:eq(2)").children("a").addClass("color-orange").children("img").attr("src", "./img/r-3.png");
    $(".main-left-item:eq(2) ul li:eq(0) a").addClass("color-orange");
    $(".iframe").css({
        "height": $(window).height()
    });
    $(".closeIframe").click(function() {
    	clearTimeout(t); 
        $(".iframe").hide();
    });
    //上传文件
    $("#bussnessLicessId").uploadFile({
        id: "1",
        url: "../rest/qcb/resource/add",
        allowedTypes: "xlsx,xls",
        maxFileSize: 1024 * 1024 * 1024 * 10,
        fileName: "file",
        maxFileCount: 100,
        dragDropStr: "",
        extErrorStr: "文件格式不正确，请上传指定类型类型的文件",
        multiple: true,
        showDone: false,
        showDelete: false,
        deletelStr: '删除',
        doneStr: "完成",
        showAbort: false,
        showStatusAfterSuccess: false,
        maxFileCountErrorStr: "文件数量过多，请先删除。",
        onSuccess: function(files, data, xhr) {
        	if(data.status=="ERROR"){
        		if(data.errorCode=="301"){
        			layer.msg(data.message, {
                        time: 2000
                    },function(){
            			window.location.href="login.jsp";
                    });     
        		}else{
        			layer.msg(data.message);
        		}
        		
        	}else{
        		layer.msg("上传成功！");
                $('#bussnessLicess').val(data.data.uuid);
                // $(".ajax-file-upload").find("form").each(function(index, el) {
                //     if (index != 0) {
                //         $(".ajax-file-upload").find("form").eq(index).remove();
                //     }
                // });
                // $(".uploadFile").html("已上传");
                $("input[type='file']").val("");
                $(".uploadFile").html("").addClass("loadingBtn").removeClass("uploadFile");
                var form = $("#upload");
                $.post(form.attr("action"), form.serialize() + "&CSRFToken=" +
                    $('input[name="CSRFToken"]').val() + "&file=" + data.data.uuid,
                function(data) {
	            	$('html').animate({
	                    scrollTop: 0
	                }, 500);
                    if (data.status == "SUCCESS") {
                        $(".module-1").hide();
                        $(".module-2").show();
                        $(".module-5").hide();
                        uuid = data.data.datasMap.uuid;
                        getSalaryListTh(data.data.datasMap.uuid);
                    }else if (data.status == "WARNING") {
                        $(".module-1").hide();
                        $(".module-2").hide();
                        $(".module-5").show();
//                            uuid = data.data.datasMap.uuid;
//                            getSalaryListTh(data.data.datasMap.uuid);
                    	getWarn(data);
                    } else {
                    	$(".uploadSalary").removeClass("loadingBtn").html("上传福利表").addClass("uploadFile");
                        layer.msg(data.message);
                    }
                });
        	}
            
            return false;
        }
    });
    $("#reUpload").click(function() {
        $(".module-1").show();
        $(".module-2").hide();
        $(".module-5").hide();
        $(".uploadSalary").removeClass("loadingBtn").html("上传福利表").addClass("uploadFile");
    });
    $("#reUploads").click(function() {
        $(".module-1").show();
        $(".module-2").hide();
        $(".module-5").hide();
        $(".uploadSalary").removeClass("loadingBtn").html("上传福利表").addClass("uploadFile");
    });
});
$(document).ready(function() {
    $(".bg").css({
        "height": $(window).height(),
        "width": $(window).width()
    });
    $(window).resize(function() {
        $(".bg").css({
            "height": $(window).height(),
            "width": $(window).width()
        });
    });

    $("#modal-close").click(function() {
        $(".bg").hide();
        $(".sureBtn").removeClass("loadingBtn").html("确认").addClass("distributionBtn");
        $(".smsCode").removeClass("light").siblings(".tip").fadeOut();
    });
    $("#confirm").click(function() {
        wechatFlag();
    });
    $(".module-2-form-item-switch").click(function() {
        var this_position = $(this).find('.switchbar').css("left");
        if (this_position == "0px") {
            $(this).find('.switchbar').css({
                "left": "68px"
            });
            $(this).find(".switch-true").css({
                "color": "#000000"
            });
            $(this).find(".switch-false").css({
                "color": "#FFFFFF"
            });
            if($(this).hasClass("module-2-form-item-switch-flagSms")){
            	$(this).find('.switchbar').attr("data-boolean", "true");
            }else{
            	$(this).find('.switchbar').attr("data-boolean", "false");
            }
        } else {
            $(this).find('.switchbar').css({
                "left": "0"
            });
            $(this).find(".switch-true").css({
                "color": "#FFFFFF"
            });
            $(this).find(".switch-false").css({
                "color": "#000000"
            });
            if($(this).hasClass("module-2-form-item-switch-flagSms")){
            	$(this).find('.switchbar').attr("data-boolean", "false");
            }else{
            	$(this).find('.switchbar').attr("data-boolean", "true");
            }
        }
    });
    $("#collectStarttime").click(function() {
        var start = laydate.now(0, "YYYY/MM/DD hh:mm:ss"),
            start = start;
        laydate({
            min: laydate.now(0, "YYYY/MM/DD"),
            start: start,
            istime: true,
            istoday: false,
            format: 'YYYY-MM-DD hh:mm:ss',
            choose:function(){
            	if($("#collectStarttime").val().replace(/(^\s*)|(\s*$)/g, "")!=""){
            		$("#upload input[name='payTime']").removeClass("light");
            	}
            }
        });
    });
});
$(".uploadFile").unbind().click(function() {
    if (checkNonempty($("#upload input[name='title']").val())) {
        $("#upload input[name='title']").removeClass("light");
    } else {
        $("#upload input[name='title']").addClass("light").focus();
        $('html').animate({
            scrollTop: 200
        }, 500);
        return false;
    }
    if (checkNonempty($("#upload input[name='payTime']").val())) {
        $("#upload input[name='payTime']").removeClass("light");
    } else {
        $("#upload input[name='payTime']").addClass("light").focus();
        $('html').animate({
            scrollTop: 200
        }, 500);
        return false;
    }
    if ($("#upload select[name='type']").val() != "") {
        $("#upload select[name='type']").removeClass("light");
    } else {
        $("#upload select[name='type']").addClass("light").focus();
        $('html').animate({
            scrollTop: 200
        }, 500);
        return false;
    }
    $(".ajax-file-upload").find("input[type='file']").css({
    	"display":"block !important"
    });
    $(".ajax-file-upload").find("input[type='file']").eq(0).click();
});

//薪资发放列表
function getSalaryList(uuid) {
    $.ajax({
        url: '../rest/qcb/companyPayrollRecords/list',
        type: 'get',
        data: {
            "qcbCompanyPayroll": uuid,
            "pageNum": 1,
            "pageSize": 10000,
            "status": "draft",
            "timestamp":new Date().getTime() 
        }
    }).done(function(r) {
        if (r.status == "SUCCESS") {
            var th = '';
            var tr = '';
            var option = '<option value="">请选择特殊奖励列...</option>';
            var modale3Table = '<tr class="first-tr"><th class="td-padding-item" width="10%">姓名</th>' +
                '<th width="20%">身份证</th><th width="15%">手机</th><th width="10%">实发工资</th></tr>';
            var totlePrice = 0;
            for (var i = 0; i < tableTh.length; i++) {
                th += '<th width="100px">' + tableTh[i] + '</th>';
                option += '<option value="' + i + '">' + tableTh[i] + '</option>';
            }
            var html = '<tr class="first-tr"><th width="100px" class="td-padding-item">姓名</th>' +
                '<th width="200px">身份证</th><th width="140px">手机</th><th width="100px">实发工资</th>' + th + '</tr>';
            for (var i = 0; i < r.data.length; i++) {
                tr = '<td class="td-padding-item">' + r.data[i].qcbEmployeeName + '</td><td>' + r.data[i].qcbEmployeeIdcard +
                    '</td><td>' + r.data[i].qcbEmployeeMobile + '</td><td>' + 
                    r.data[i].price.toFixed(2).split('').reverse().join('').replace(/(\d{3})/g,'$1,').replace(/\,$/,'').split('').reverse().join('') + '</td>';
                modale3Table += '<tr class="msg-item"><td class="td-padding-item">' + r.data[i].qcbEmployeeName + '</td><td>' 
                + r.data[i].qcbEmployeeIdcard +
                    '</td><td>' + r.data[i].qcbEmployeeMobile + '</td><td class="msg-item-price">' + 
                    r.data[i].price.toFixed(2).split('').reverse().join('').replace(/(\d{3})/g,'$1,').replace(/\,$/,'').split('').reverse().join('') 
                    + '</td><input type="hidden" value=' + r.data[i].uuid + ' /></tr>';
                totlePrice += r.data[i].price;
                for (var j = 0; j < r.data[i].valueList.length; j++) {
                	if(r.data[i].valueList[j].replace(/(^\s*)|(\s*$)/g, "")!=""){
                		var value = isNaN(Number(r.data[i].valueList[j])) ? r.data[i].valueList[j] : Number(r.data[i].valueList[j]).toFixed(2).split('').reverse().join('').replace(/(\d{3})/g,'$1,').replace(/\,$/,'').split('').reverse().join('');
                        tr += '<td>' + value + '</td>';
                	}else{
                		tr += '<td></td>';
                	}
                	
                }
                tr = '<tr>' + tr + '</tr>';
                html += tr;
            }
            $(".module-2-tablebox table").html(html);
            $(".totalCount span").html(r.data.length);
            $(".module-2-form-item select").html(option);
            $(".module-3-bottom span.color-orange").html(totlePrice.toFixed(2).split('').reverse().join('').replace(/(\d{3})/g,'$1,').replace(/\,$/,'').split('').reverse().join(''));
            $(".module-3-bottom-left-tablebox table").html(modale3Table);
            wagesInfoValue=[];
            $.each(r.data, function(index, el) {
                wagesInfoValue.push(el.valueList);
            });

        } else {
        	layer.msg(r.message);
        }
    }).fail(function(r) {
        layer.msg("error", {
            time: 2000
        });
    });
}



//薪资发放列表表头
function getSalaryListTh(uuid) {
    $.ajax({
        url: '../rest/qcb/companyPayroll/get',
        type: 'get',
        data: {
            "uuid": uuid,
            "timestamp":new Date().getTime() 
        }
    }).done(function(r) {
        if (r.status == "SUCCESS") {
            tableTh = r.data.columnList;
            getSalaryList(uuid);
            wagesInfoKey = r.data.columnList;
            wagesInfoTitle = r.data.title;
            wagesInfoRemark = r.data.remark;
        } else {
            layer.msg(r.message);
        }
    }).fail(function(r) {
        layer.msg("error", {
            time: 2000
        });
    });
}

//预览发送
$(".preview").click(function() {
    var reward;
    if ($(".module-2-form-item select").val().replace(/(^\s*)|(\s*$)/g, "") != "") {
        reward = "&reward=" + $(".module-2-form-item select").val()
    } else {
        reward = "";
    }
    var form = $("#uploadForm");
    $.post(form.attr("action"), "uuid=" + uuid +
        reward +
        "&flagSms=" + $(".flagSms").attr("data-boolean") +
        "&flagHide=" + $(".flagHide").attr("data-boolean") +
        "&CSRFToken=" + $('input[name="CSRFToken"]').val(),
        function(data) {
            if (data.status == "SUCCESS") {
                $(".module-2").hide();
                $(".module-3").show();


                //点击列表，手机显示 start
                $(".phone-top h3").html(wagesInfoTitle);
                $(".phone-top p span").html($(".msg-item").eq(0).find(".msg-item-price").html());
                $(".phone-top > span").html(wagesInfoRemark);
                $(".msg-listDiv").html("");
                $.each(wagesInfoKey, function(index, el) {
                    $(".msg-listDiv").append("<p class='msg-list'>" + el + "<span></span></p>");
                });
                $.each(wagesInfoValue[0], function(index, el) {
                	var value;
                	if(el.replace(/(^\s*)|(\s*$)/g, "")!=""){
                		value = isNaN(Number(el)) ? el : Number(el).toFixed(2).split('').reverse().join('').replace(/(\d{3})/g,'$1,').replace(/\,$/,'').split('').reverse().join('');
                	}else{
                		value = "";
                	}                	
                    $(".phone-box .msg-list").eq(index).find("span").html(value);
                });

                $('.msg-item').click(function() {
                    $(".phone-top p span").html($(this).find(".msg-item-price").html());
                    $.each(wagesInfoValue[$(this).index(".msg-item")], function(index, el) {
                    	var value;
                    	if(el.replace(/(^\s*)|(\s*$)/g, "")!=""){
                    		value = isNaN(Number(el)) ? el : Number(el).toFixed(2).split('').reverse().join('').replace(/(\d{3})/g,'$1,').replace(/\,$/,'').split('').reverse().join('');
                    	}else{
                    		value = "";
                    	}
                        $(".phone-box .msg-list").eq(index).find("span").html(value);
                    });
                });

                //点击列表，手机显示 end
            } else {
                layer.msg(data.message);
            }
        });
    return false;
});
$(".stepPre").click(function() {
    $(".module-2").show();
    $(".module-3").hide();
});

$(".iframeInner .getCode").click(function() {
    if (checkNonempty($(".oldphone").val())) {
        $(".oldphone").removeClass("light").siblings(".tip").fadeOut();
    } else {
        $(".oldphone").addClass("light").siblings(".tip").fadeIn();
        return false;
    }
    if (smsCode) {
        smsCode = false;
        $(".getCode").html("<span>60</span>s后，重新获取");
        invokeSettime();
        var form = $("#sendCodeToCheck");
        $.post(form.attr("action"), "type=qcb_payroll&CSRFToken=" + $('input[name="CSRFToken"]').val(), function(data) {
            if (data.status == "SUCCESS") {
                layer.msg(data.message);
            } else {
                layer.msg(data.message);
                clearTimeout(timer);
                smsCode = true;
                $(".getCode").html("重新获取验证码");
            }
        });
        return false;
    }
});
var timer;
//短信验证码倒计时
function invokeSettime() {
    var delay = Number($(".getCode span").html());
    timer = setTimeout("invokeSettime()", 1000);
    if (delay > 1) {
        delay--;
        $(".getCode span").html(delay);
    } else {
        clearTimeout(timer);
        smsCode = true;
        $(".getCode").html("重新获取验证码");
    }
}

$(".distributionBtn").click(function() {
    if ($(".smsCode").val().replace(/(^\s*)|(\s*$)/g, "")!="") {
        $(".smsCode").removeClass("light").siblings(".tip").fadeOut();
    } else {
        $(".smsCode").addClass("light").focus().siblings(".tip").fadeIn();
        return false;
    }
	$(".sureBtn").addClass("loadingBtn").html("").removeClass("distributionBtn");
    var form = $("#sureSend");
    $.post(form.attr("action"), "uuid=" + uuid +
        "&code=" + $(".smsCode").val().replace(/(^\s*)|(\s*$)/g, "") +
        "&CSRFToken=" + $('input[name="CSRFToken"]').val(),
        function(data) {
            if (data.status == "SUCCESS") {
                $(".module-4").show();
                $(".bg").hide();
                $(".module-3").hide();
                $(".grantSuccess").html($("input[name='title']").val().replace(/(^\s*)|(\s*$)/g, "") + "，已发放！");
            } else {
                layer.msg(data.message);
            }
            $(".sureBtn").removeClass("loadingBtn").html("确认").addClass("distributionBtn");
        });
    return false;
});
$(window).resize(function() {
    $(".iframe").css({
        "height": $(window).height()
    });
});

function wechatFlag() {
    $.ajax({
        url: '../rest/qcb/admin/getWechatFlag',
        type: 'get',
        async:false,
        data: {
            "timestamp":new Date().getTime() 
        }
    }).done(function(r) {
        if (r.status == "SUCCESS") {
            if (r.data == true) {
                $(".submitGrant").show();
//                $("#wechatBtn").attr("href", "safetyCenterIframe.jsp?type=payroll&uuid=" + uuid);
                window.open("safetyCenterIframe.jsp?type=payroll&uuid=" + uuid);
//                document.getElementById("wechatBtn").click();
                grantFlag();
            } else {
                $(".bg").fadeIn();
            }
        } else {
            layer.msg(r.message);
        }
    }).fail(function(r) {
        layer.msg("error", {
            time: 2000
        });
    });
}

var t;

function grantFlag() {
    t = setTimeout("grantFlag()", 1000);
    $.ajax({
        url: '../rest/qcb/companyPayroll/get',
        type: 'get',
        data: {
        	"uuid": uuid,
            "timestamp":new Date().getTime() 
        }
    }).done(function(r) {
        if (r.status == "SUCCESS") {
            if (r.data.status != "draft") {
                clearTimeout(t);
                $(".iframe").hide();
                layer.msg("发放成功", {
                    time: 2000
                }, function() {
                	$(".module-4").show();
                    $(".bg").hide();
                    $(".module-3").hide();
                    $(".grantSuccess").html($("input[name='title']").val().replace(/(^\s*)|(\s*$)/g, "") + "，已发放！");
                });

            } else {
                /*  layer.msg("绑定失败，请重新绑定");  */
            }
        } else {
            layer.msg(r.message);
        }
    }).fail(function(r) {
        layer.msg("error", {
            time: 2000
        });
    });
}
//warning情况错误枚举
function getWarn(data){
	//错误列表
	var th = '';
	var modale3Table = '<tr class="first-tr"><th class="td-padding-item" width="10%">姓名</th>' +
    '<th width="20%">身份证</th><th width="15%">手机</th><th width="10%">实发工资</th></tr>';
	var totlePrice = 0;
	for (var i = 0; i < data.data.cloumn.length; i++) {
	    th += '<th width="100px">' + data.data.cloumn[i] + '</th>';
	}
	var html = '<tr class="first-tr"><th width="70px" class="td-padding-item">序号</th><th width="100px">姓名</th>' +
	    '<th width="200px">身份证</th><th width="140px">手机</th><th width="100px">实发工资</th>' + th + '</tr>';
	for(var key in data.data.datasMap){
		var classname;
		for(var i=0;i<data.error.length;i++){
			if(key==Number(data.error[i].index)){
				classname = "color-red";
			}else{
				classname = "";
			}
		}
		 tr = '<td class="td-padding-item">' + key + '</td><td>' + data.data.datasMap[key].realname + '</td><td>' + data.data.datasMap[key].idcard +
	        '</td><td>' + data.data.datasMap[key].mobile + '</td><td>' +
	        Number(data.data.datasMap[key].price).toFixed(2).split('').reverse().join('').replace(/(\d{3})/g,'$1,').replace(/\,$/,'').split('').reverse().join('') 
	        + '</td>';
		for (var j = 0; j < data.data.datasMap[key].value.length; j++) {
			if(data.data.datasMap[key].value[j].replace(/(^\s*)|(\s*$)/g, "")!=""){
				var value = isNaN(Number(data.data.datasMap[key].value[j])) ? data.data.datasMap[key].value[j] : Number(data.data.datasMap[key].value[j]).toFixed(2).split('').reverse().join('').replace(/(\d{3})/g,'$1,').replace(/\,$/,'').split('').reverse().join('');
		        tr += '<td>' + value + '</td>';
			}else{
				tr += '<td></td>';
			}
			
	    }
	    tr = '<tr class='+classname+'>' + tr + '</tr>';
	    html += tr;
	}
	$(".errorList .tableContent").html(html);
	var warnStr = '<tr class="first-tr"><th class="td-padding-item" width="30%">所在行数</th><th width="70%">错误原因</th></tr>';
	//错误枚举
	for(var i=0;i<data.error.length;i++){
		warnStr += "<tr><td class='td-padding-item'>第"+ data.error[i].index +"行</td><td>" + data.error[i].data + "</td></tr>";
//		$(".errorList table tr:eq("+data.error[i].index+")").addClass("color-red");
	}
	$(".errorBox .tableContent").html(warnStr);
	if(data.error.length>1){
		$(".packDown").show();	
		$(".packUp").click(function(){
			$(this).hide();
			$(".packDown").show();
			$(".errorBox").css({"max-height":"110px","overflow":"hidden"});
			$(".errorBox").scrollTop(0);
			$("html").scrollTop(0);
		});
		$(".packDown").click(function(){
			$(this).hide();
			$(".packUp").show();
			$(".errorBox").css({"max-height":"390px","overflow":"auto"});
			
		});
	}else{
		$(".lookMore").hide();
	}
}
$("#upload input[name='title']").blur(function(){
	if($(this).val().replace(/(^\s*)|(\s*$)/g, "")!=""){
		$("#upload input[name='title']").removeClass("light");
	}
});

$("#upload select[name='type']").blur(function(){
	if($(this).val()!=""){
		$("#upload select[name='type']").removeClass("light");
	}
});



