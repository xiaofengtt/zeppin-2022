var businessLicence;
var evidence;
var idcardFace;
var idcardback;
var status="normal";//审核状态
$(function() {
    $(".main-left-item:eq(3)").children("a").addClass("color-orange").children("img").attr("src", "./img/r-4.png");
    $(".main-left-item:eq(3) ul li:eq(0) a").addClass("color-orange");
    businessLicence();
    evidence();
    idcardFace();
    idcardback();
    getInfo();
    //上传图片
    $("#resourceId").uploadFile({
        id: "1",
        url: "../rest/qcb/resource/add",
        allowedTypes: "jpg,png,jpeg,bmp,tiff,gif",
        maxFileSize: 1024 * 1024 * 1024 * 10,
        fileName: "file",
        maxFileCount: 100,
        dragDropStr: "",
        extErrorStr: "文件格式不正确，请上传指定类型类型的图片文件",
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
        		$('input[name="logo"]').val(data.data.uuid);
                $('#imageShow,#imageShows').css({
                    'background': 'url(..' + data.data.url + ') center no-repeat',
                    'background-size': 'cover'
                });
                $('#imageShows').css('margin-bottom', '34px');
//                $(".fileDiv").css("display", "none");
                $("input[type='file']").val("");
        	}
            
        }
    });
});

$(".sure").click(function() {
	if(status!="normal"){
		submitInfo();
	}else{
		layer.confirm('更新企业信息会重新进行资质审核，请确认后提交', {
	        btn: ['取消','确认' ] //按钮
	    }, function() {
	    	layer.closeAll();
	    }, function() {	        
	        layer.closeAll();
	    	submitInfo();
	    });     
	}	  
});
function submitInfo(){
	if (checkNonempty($(".companyNameEdit").val())) {
        $(".companyNameEdit").removeClass("light").siblings(".tips").fadeOut();
    } else {
    	$('html').animate({
            scrollTop: 400
        }, 500);
        $(".companyNameEdit").addClass("light").focus().siblings(".tips").fadeIn();
        return false;
    }
    if ($(".province").val() == "0" || $(".city").val() == "0" || $(".county").val() == "0") {
        $(".province").parent().siblings(".tipsBox").fadeIn();
        if ($(".province").val() == "0") {
            $(".province").addClass("light");
            $(".city").removeClass("light");
            $(".county").removeClass("light");
        } else if ($(".city").val() == "0") {
            $(".city").addClass("light");
            $(".province").removeClass("light");
            $(".county").removeClass("light");
        } else {
            $(".county").addClass("light");
            $(".province").removeClass("light");
            $(".city").removeClass("light");
        }
        $('html').animate({
            scrollTop: 200
        }, 500);
        $(".province").siblings(".tips").fadeIn();
        return false;
    } else {
        $(".province").removeClass("light");
        $(".city").removeClass("light");
        $(".county").removeClass("light");
        $(".province").siblings(".tips").fadeOut();
    }
    
    if (checkNonempty($(".companyAddressEdit").val())) {
        $(".companyAddressEdit").removeClass("light").siblings(".tips").fadeOut();
    } else {
    	$('html').animate({
            scrollTop: 400
        }, 500);
        $(".companyAddressEdit").addClass("light").focus().siblings(".tips").fadeIn();
        return false;
    }
    if (checkPhone($(".companyTelEdit").val()) || checkLandline($(".companyTelEdit").val())) {
        $(".companyTelEdit").removeClass("light").siblings(".tips").fadeOut();
    } else {
    	$('html').animate({
            scrollTop: 400
        }, 500);
        $(".companyTelEdit").addClass("light").focus().siblings(".tips").fadeIn();
        return false;
    }
    if (checkNonempty($(".companyLegalEdit").val())) {
        $(".companyLegalEdit").removeClass("light").siblings(".tips").fadeOut();
    } else {
    	$('html').animate({
            scrollTop: 400
        }, 500);
        $(".companyLegalEdit").addClass("light").focus().siblings(".tips").fadeIn();
        return false;
    }
    //校验第二部分内容
    if($(".billingNameEdit").val().replace(/(^\s*)|(\s*$)/g,"")!=""){
    	if (checkNonempty($(".billingNameEdit").val())) {
            $(".billingNameEdit").removeClass("light").siblings(".tips").fadeOut();
        } else {
        	$('html').animate({
                scrollTop: 950
            }, 500);
            $(".billingNameEdit").addClass("light").focus().siblings(".tips").fadeIn();
            return false;
        }
    }   
    if($(".billingNumberEdit").val().replace(/(^\s*)|(\s*$)/g,"")!=""){
    	if (checkNonempty($(".billingNumberEdit").val()) && checkbillingNumber($(".billingNumberEdit").val())) {
            $(".billingNumberEdit").removeClass("light").siblings(".tips").fadeOut();
        } else {
        	$('html').animate({
                scrollTop: 950
            }, 500);
            $(".billingNumberEdit").addClass("light").focus().siblings(".tips").fadeIn();
            return false;
        }
    }  
    if($(".billingAddressEdit").val().replace(/(^\s*)|(\s*$)/g,"")!=""){
    	if (checkNonempty($(".billingAddressEdit").val())) {
            $(".billingAddressEdit").removeClass("light").siblings(".tips").fadeOut();
        } else {
        	$('html').animate({
                scrollTop: 950
            }, 500);
            $(".billingAddressEdit").addClass("light").focus().siblings(".tips").fadeIn();
            return false;
        }
    }
    
    if($(".billingTelEdit").val().replace(/(^\s*)|(\s*$)/g,"")!=""){
    	if (checkLandline($(".billingTelEdit").val())) {
            $(".billingTelEdit").removeClass("light").siblings(".tips").fadeOut();
        } else {
        	$('html').animate({
                scrollTop: 950
            }, 500);
            $(".billingTelEdit").addClass("light").focus().siblings(".tips").fadeIn();
            return false;
        }
    }    
    if($(".billingAccountEdit").val().replace(/(^\s*)|(\s*$)/g,"")!=""){
    	if (checkNumber($(".billingAccountEdit").val().replace(/[ ]/g,""))) {
            $(".billingAccountEdit").removeClass("light").siblings(".tips").fadeOut();
        } else {
        	$('html').animate({
                scrollTop: 950
            }, 500);
            $(".billingAccountEdit").addClass("light").focus().siblings(".tips").fadeIn();
            return false;
        }
    }
    if($(".billingBankEdit").val().replace(/(^\s*)|(\s*$)/g,"")!=""){
    	if (checkNonempty($(".billingBankEdit").val())) {
            $(".billingBankEdit").removeClass("light").siblings(".tips").fadeOut();
        } else {
        	$('html').animate({
                scrollTop: 950
            }, 500);
            $(".billingBankEdit").addClass("light").focus().siblings(".tips").fadeIn();
            return false;
        }
    }
       
    //校验第三部分
    if (checkNonempty($(".contactNameEdit").val())) {
        $(".contactNameEdit").removeClass("light").siblings(".tips").fadeOut();
    } else {
    	$('html').animate({
            scrollTop: 1520
        }, 500);
        $(".contactNameEdit").addClass("light").focus().siblings(".tips").fadeIn();
        return false;
    }
    if (checkPhone($(".contactTelEdit").val()) || checkLandline($(".contactTelEdit").val())) {
        $(".contactTelEdit").removeClass("light").siblings(".tips").fadeOut();
    } else {
    	$('html').animate({
            scrollTop: 1520
        }, 500);
        $(".contactTelEdit").addClass("light").focus().siblings(".tips").fadeIn();
        return false;
    }
    if (checkIDnumber($(".contactIDnumberEdit").val())) {
        $(".contactIDnumberEdit").removeClass("light").siblings(".tips").fadeOut();
    } else {
    	$('html').animate({
            scrollTop: 1520
        }, 500);
        $(".contactIDnumberEdit").addClass("light").focus().siblings(".tips").fadeIn();
        return false;
    }
    if (checkEmail($(".contactEmailEdit").val())) {
        $(".contactEmailEdit").removeClass("light").siblings(".tips").fadeOut();
    } else {
    	$('html').animate({
            scrollTop: 1520
        }, 500);
        $(".contactEmailEdit").addClass("light").focus().siblings(".tips").fadeIn();
        return false;
    }
    //校验资质信息
    if ($("#bussnessLicess").val() == "") {
        $("#bussnessLicess").val(businessLicence);
    }
    if ($("#evidence").val() == "") {
        $("#evidence").val(evidence);
    }
    if ($("#idcardFace").val() == "") {
        $("#idcardFace").val(idcardFace);
    }
    if ($("#idcardback").val() == "") {
        $("#idcardback").val(idcardback);
    }
    if ($("#bussnessLicess").val() == "") {
    	layer.msg("请上传营业执照");
    	return false;
    }
    if ($("#evidence").val() == "") {
    	layer.msg("请上传企业授权材料");
    	return false;
    }
    if ($("#idcardFace").val() == "") {
    	layer.msg("请上传授权人身份证正面");
    	return false;
    }
    if ($("#idcardback").val() == "") {
    	layer.msg("请上传授权人身份证反面");
    	return false;
    }
//    if($("#idcardFace").val() != ""&&$("#idcardback").val() == ""){
//    	layer.msg("请上传身份证反面");
//    	return false;
//    }
//    if($("#idcardFace").val() == ""&&$("#idcardback").val() != ""){
//    	layer.msg("请上传身份证正面");
//    	return false;
//    }
//    if ($("#bussnessLicess").val() != "" || $("#evidence").val() != "" || $("#idcardFace").val() != "" || $("#idcardback").val() != "") {

		$(".submitBtn").addClass("loadingBtn").html("").removeClass("sure");
        var form = $("#editBase");
        $.post(form.attr("action"), form.serialize()+"&openBankCardnum="+$(".billingAccountEdit").val().replace(/[ ]/g,""), function(data) {
            if (data.status == "SUCCESS") {
                layer.msg("提交成功", {
                    time: 2000
                }, function() {
                    window.location.href = "companyMsg.jsp";
                });
            } else {
                layer.msg(data.message);
            }
	    	$(".submitBtn").removeClass("loadingBtn").html("提交").addClass("sure");
        });
        return false;
//    } else {
//        layer.msg("没有修改操作");
//    }
}
//营业执照
function businessLicence() {
    //上传营业执照
    $("#bussnessLicessId").uploadFile({
        id: "1",
        url: "../rest/qcb/resource/add",
        allowedTypes: "jpg,png,jpeg",
        maxFileSize: 1024 * 1024 * 10,
        fileName: "file",
        maxFileCount: 100,
        dragDropStr: "",
        extErrorStr: "文件格式不正确，请上传指定类型类型的图片文件",
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
        		$('input[name="businessLicence"]').val(data.data.uuid);
                $('#bussnessLicessShow').attr('src', '..' + data.data.url);
                $(".first .ajax-file-upload").css("display", "none");
                // $(".first .ajax-file-upload").find("form").each(function(index, el) {
                //     if (index != 0) {
                //         $(".first .ajax-file-upload").find("form").eq(index).remove();
                //     }
                // });
                $("input[type='file']").val("");
                $(".first .reupload").show();
        	}
            
        }
    });
}

//企业授权材料
function evidence() {
    //上传企业授权材料
    $("#evidenceId").uploadFile({
        id: "1",
        url: "../rest/qcb/resource/add",
        allowedTypes: "jpg,png,jpeg",
        maxFileSize: 1024 * 1024 * 10,
        fileName: "file",
        maxFileCount: 100,
        dragDropStr: "",
        extErrorStr: "文件格式不正确，请上传指定类型类型的图片文件",
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
        		$('input[name="evidence"]').val(data.data.uuid);
                $('#evidenceShow').attr('src', '..' + data.data.url);
                $(".second .ajax-file-upload").css("display", "none");
                // $(".second .ajax-file-upload").find("form").each(function(index, el) {
                //     if (index != 0) {
                //         $(".second .ajax-file-upload").find("form").eq(index).remove();
                //     }
                // });
                $("input[type='file']").val("");
                $(".second .reupload").show();
        	}
            
        }
    });
}

//身份证正面
function idcardFace() {
    //上传身份证正面
    $("#idcardFaceId").uploadFile({
        id: "1",
        url: "../rest/qcb/resource/add",
        allowedTypes: "jpg,png,jpeg",
        maxFileSize: 1024 * 1024 * 2,
        fileName: "file",
        maxFileCount: 100,
        dragDropStr: "",
        extErrorStr: "文件格式不正确，请上传指定类型类型的图片文件",
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
        		$('input[name="idcardFace"]').val(data.data.uuid);
                $('#idcardFaceShow').attr('src', '..' + data.data.url);
                $(".third .ajax-file-upload").css("display", "none");
                // $(".third .ajax-file-upload").find("form").each(function(index, el) {
                //     if (index != 0) {
                //         $(".third .ajax-file-upload").find("form").eq(index).remove();
                //     }
                // });
                $("input[type='file']").val("");
                $(".third .reupload").show();
        	}
            
        }
    });
}

//身份证反面
function idcardback() {
    //上传身份证发面
    $("#idcardbackId").uploadFile({
        id: "1",
        url: "../rest/qcb/resource/add",
        allowedTypes: "jpg,png,jpeg",
        maxFileSize: 1024 * 1024 * 2,
        fileName: "file",
        maxFileCount: 100,
        dragDropStr: "",
        extErrorStr: "文件格式不正确，请上传指定类型类型的图片文件",
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
        		$('input[name="idcardBack"]').val(data.data.uuid);
                $('#idcardbackShow').attr('src', '..' + data.data.url);
                $(".forth .ajax-file-upload").css("display", "none");
                // $(".forth .ajax-file-upload").find("form").each(function(index, el) {
                //     if (index != 0) {
                //         $(".forth .ajax-file-upload").find("form").eq(index).remove();
                //     }
                // });
                $("input[type='file']").val("");
                $(".forth .reupload").show();
        	}
            
        }
    });
}

$(".first .reupload").click(function() {
    $(".first input[type='file']").eq(0).click();
    //	$(this).hide();
});
$(".second .reupload").click(function() {
    $(".second input[type='file']").eq(0).click();
    //	$(this).hide();
});
$(".third .reupload").click(function() {
    $(".third input[type='file']").eq(0).click();
    //	$(this).hide();
});
$(".forth .reupload").click(function() {
    $(".forth input[type='file']").eq(0).click();
    //	$(this).hide();
});
//获取企业信息
function getInfo() {
    $.ajax({
        url: '../rest/qcb/companyAccount/get',
        type: 'get',
        data: {
        	"timestamp":new Date().getTime() 
        }
    }).done(function(r) {
        if (r.status == "SUCCESS") {
        	status = r.data.status;
            if (r.data.logo != "" && r.data.logo) {
                $('#imageShow,#imageShows').css({
                    'background': 'url(..' + r.data.logoUrl + ') center no-repeat',
                    'background-size': 'cover'
                });
            }
            $(".bkArea").val(r.data.bkArea);
            $(".companyAddress").val(r.data.address);
            //企业id
            if (r.data.merchantId) {
                $(".companyIDEdit").html(r.data.merchantId);
            } else {
                $(".companyIDEdit").html("暂无");
            }

            //头像id
            $("#logo").val(r.data.logo);
            //企业名称
            if (r.data.name) {
                $(".companyNameEdit").val(r.data.name);
            } else {
                $(".companyNameEdit").val("");
            }
            //企业地址
            if (r.data.bkAreaCN) {
                if (r.data.address != null) {
                    $(".companyAddressEdit").val(r.data.address);
                } else {
                    $(".companyAddressEdit").val("");
                }

            } else {
                $(".companyAddressEdit").val("");
            }
            editArea("province", "1", "", "", true, r.data.areaInfo.province.scode);
            editArea("city", "2", r.data.areaInfo.city.pid, "", true, r.data.areaInfo.city.scode);
            editArea("county", "3", r.data.areaInfo.county.pid, "", true, r.data.areaInfo.county.scode);
            //企业电话
            if (r.data.phone) {
                $(".companyTelEdit").val(r.data.phone);
            } else {
                $(".companyTelEdit").val("");
            }
            //法定代表人
            if (r.data.corporation) {
                $(".companyLegalEdit").val(r.data.corporation);
            } else {
                $(".companyLegalEdit").val("");
            }

            //税务识别号
            if (r.data.taxIdentificationNum) {
                $(".billingNumberEdit").val(r.data.taxIdentificationNum);
            } else {
                $(".billingNumberEdit").val("");
            }
            //企业名称
            if (r.data.taxCompany) {
                $(".billingNameEdit").val(r.data.taxCompany);
            } else {
                $(".billingNameEdit").val("");
            }
            //开票信息地址名称
            if (r.data.taxAddress) {
                $(".billingAddressEdit").val(r.data.taxAddress);
            } else {
                $(".billingAddressEdit").val("");
            }
            //开票信息电话
            if (r.data.taxPhone) {
                $(".billingTelEdit").val(r.data.taxPhone);
            } else {
                $(".billingTelEdit").val("");
            }
            //开票信息开户行
            if (r.data.openBank) {
                $(".billingBankEdit").val(r.data.openBank);
            } else {
                $(".billingBankEdit").val("");
            }
            //开票信息开户账号
            if (r.data.openBankCardnum) {
                $(".billingAccountEdit").val(r.data.openBankCardnum.replace(/\s/g,'').replace(/[^\d]/g,'').replace(/(\d{4})(?=\d)/g,'$1 '));
            } else {
                $(".billingAccountEdit").val("");
            }

            //联系人信息 联系人
            if (r.data.contacts) {
                $(".contactNameEdit").val(r.data.contacts);
            } else {
                $(".contactNameEdit").val("");
            }
            //联系人信息 联系电话
            if (r.data.contactsMobile) {
                $(".contactTelEdit").val(r.data.contactsMobile);
            } else {
                $(".contactTelEdit").val("");
            }
            //联系人信息 身份证号
            if (r.data.contactsIdcard) {
                $(".contactIDnumberEdit").val(r.data.contactsIdcard);
            } else {
                $(".contactIDnumberEdit").val("");
            }
            //联系人信息 电子邮箱
            if (r.data.contactsEmail) {
                $(".contactEmailEdit").val(r.data.contactsEmail);
            } else {
                $(".contactEmailEdit").val("");
            }
            businessLicence = r.data.businessLicence;
            evidence = r.data.evidence;
            idcardFace = r.data.idcardFace;
            idcardback = r.data.idcardBack;
            if (r.data.businessLicenceURL) {
                $("#bussnessLicessShow").attr("src", ".." + r.data.businessLicenceURL);
                $(".first .ajax-file-upload").hide();
                $(".first .ajax-upload-dragdrop").hide();
                $(".first .reupload").show();
            } else {
                $(".first .ajax-upload-dragdrop").show();
                $(".first .ajax-file-upload").css("display", "block");
                $(".first .reupload").hide();
            }
            if (r.data.evidenceURL) {
                $("#evidenceShow").attr("src", ".." + r.data.evidenceURL);
                $(".second .ajax-file-upload").hide();
                $(".second .ajax-upload-dragdrop").hide();
                $(".second .reupload").show();
            } else {
                $(".second .ajax-upload-dragdrop").show();
                $(".second .ajax-file-upload").css("display", "block");
                $(".second .reupload").hide();
            }
            if (r.data.idcardFaceURL) {
                $("#idcardFaceShow").attr("src", ".." + r.data.idcardFaceURL);
                $(".third .ajax-file-upload").hide();
                $(".third .ajax-upload-dragdrop").hide();
                $(".third .reupload").show();
            } else {
                $(".third .ajax-upload-dragdrop").show();
                $(".third .ajax-file-upload").css("display", "block");
                $(".third .reupload").hide();
            }
            if (r.data.idcardBackURL) {
                $("#idcardbackShow").attr("src", ".." + r.data.idcardBackURL);
                $(".forth .ajax-file-upload").hide();
                $(".forth .ajax-upload-dragdrop").hide();
                $(".forth .reupload").show();
            } else {
                $(".forth .ajax-upload-dragdrop").show();
                $(".forth .ajax-file-upload").css("display", "block");
                $(".forth .reupload").hide();
            }
            setTimeout("judgeImg()",1000);
            if (r.data.checkerName!=null) {
            	$(".table").append("<tr><td class='td-padding-item'>" + r.data.checktimeCN +
                        "</td><td>" + r.data.checkStatusCN + "</td><td>" + r.data.checkReason + "</td></tr>");
            } else {
                $(".table").append("<tr><td colspan='3' style='text-align:center'>暂无审核记录</td></tr>");
            }
            $(".loadingOver").show();
        	$(".loadingDiv").hide();

        } else {
        	$(".loadingDiv").hide();
            layer.msg(r.message);
            $(".first .reupload").hide();
            $(".second .reupload").hide();
            $(".third .reupload").hide();
            $(".forth .reupload").hide();
        }
    }).fail(function() {
    	$(".loadingDiv").hide();
        layer.msg("error", {
            time: 2000
        });
    });
}
//编辑地区选中已选地区
function editArea(className, level, pid, scode, edit, scodes) {
    $.ajax({
        url: '../rest/qcb/area/list',
        type: 'get',
        data: {
            "level": level,
            "pid": pid,
            "scode": scode,
            "pageNum": 1,
            "pageSize": 1000,
            "timestamp":new Date().getTime() 
        }
    }).done(function(r) {
    	if(r.status == "SUCCESS"){
    		$("." + className).html("<option value='0'>请选择</option>");
            $.each(r.data, function(index, el) {
                if (el.level == 1 && el.name == "全国") {

                } else {
                    if (edit && scodes == el.scode) {
                        $("." + className).append("<option value=" + el.uuid + " scode = " + el.scode + " selected='selected'>" + el.name + "</option>");
                    } else {
                        $("." + className).append("<option value=" + el.uuid + " scode = " + el.scode + ">" + el.name + "</option>");
                    }
                }
            });
            $(".province").change(function(event) {
                editArea("city", "2", $(this).val(), $(this).attr("scode"));
                $(".county").html("<option value='0'>请选择</option>");
            });
            $(".city").change(function(event) {
                editArea("county", "3", $(this).val(), $(this).attr("scode"));
            });
    	}else{
    		layer.msg(r.message);
    	}
        
    }).fail(function() {
        layer.msg("error", {
            time: 2000
        });
    });
}
$(".gobackBtn").click(function(){
	window.history.go(-1);
});

$(".companyNameEdit").blur(function(){
	if($(this).val().replace(/(^\s*)|(\s*$)/g, "")!=""){
		$(".companyNameEdit").removeClass("light").siblings(".tips").fadeOut();
	}
});
$(".companyAddressEdit").blur(function(){
	if($(this).val().replace(/(^\s*)|(\s*$)/g, "")!=""){
		$(".companyAddressEdit").removeClass("light").siblings(".tips").fadeOut();
	}
});
$(".province").blur(function(){
	if($(this).val()!="0"&&$(".city").val()!="0"&&$(".county").val()!="0"){
		$(".province").removeClass("light");
        $(".city").removeClass("light");
        $(".county").removeClass("light");
		$(".province").siblings(".tips").fadeOut();
	}else if($(this).val()!="0"){
		$(this).removeClass("light");
	}
});
$(".city").blur(function(){
	if($(".province").val()!="0"&&$(".city").val()!="0"&&$(".county").val()!="0"){
		$(".province").removeClass("light");
        $(".city").removeClass("light");
        $(".county").removeClass("light");
		$(".province").siblings(".tips").fadeOut();
	}else if($(this).val()!="0"){
		$(this).removeClass("light");
	}
});
$(".county").blur(function(){
	if($(".province").val()!="0"&&$(".city").val()!="0"&&$(".county").val()!="0"){
		$(".province").removeClass("light");
        $(".city").removeClass("light");
        $(".county").removeClass("light");
		$(".province").siblings(".tips").fadeOut();
	}else if($(this).val()!="0"){
		$(this).removeClass("light");
	}
});
$(".companyAddressEdit").blur(function(){
	if($(this).val().replace(/(^\s*)|(\s*$)/g, "")!=""){
		$(".companyAddressEdit").removeClass("light").siblings(".tips").fadeOut();
	}
});
$(".companyTelEdit").blur(function(){
	if($(this).val().replace(/(^\s*)|(\s*$)/g, "")!=""){
		if (checkPhone($(".companyTelEdit").val()) || checkLandline($(".companyTelEdit").val())) {
	        $(".companyTelEdit").removeClass("light").siblings(".tips").fadeOut();
	    } else {
	        $(".companyTelEdit").addClass("light").siblings(".tips").fadeIn();
	    }
	}
});
$(".companyLegalEdit").blur(function(){
	if($(this).val().replace(/(^\s*)|(\s*$)/g, "")!=""){
		$(".companyLegalEdit").removeClass("light").siblings(".tips").fadeOut();
	}
});
$(".billingNumberEdit").blur(function(){
	if($(this).val().replace(/(^\s*)|(\s*$)/g, "")!=""){
		if (checkNonempty($(".billingNumberEdit").val()) && checkbillingNumber($(".billingNumberEdit").val())) {
	        $(".billingNumberEdit").removeClass("light").siblings(".tips").fadeOut();
	    } else {
	        $(".billingNumberEdit").addClass("light").siblings(".tips").fadeIn();
	    }
	}
});
$(".billingNameEdit").blur(function(){
	if($(this).val().replace(/(^\s*)|(\s*$)/g, "")!=""){
		$(".billingNameEdit").removeClass("light").siblings(".tips").fadeOut();
	}
});
$(".billingAddressEdit").blur(function(){
	if($(this).val().replace(/(^\s*)|(\s*$)/g, "")!=""){
		$(".billingAddressEdit").removeClass("light").siblings(".tips").fadeOut();
	}
});

$(".billingTelEdit").blur(function(){
	if($(this).val().replace(/(^\s*)|(\s*$)/g, "")!=""){
		if (checkLandline($(".billingTelEdit").val())) {
	        $(".billingTelEdit").removeClass("light").siblings(".tips").fadeOut();
	    } else {
	        $(".billingTelEdit").addClass("light").siblings(".tips").fadeIn();
	    }
	}
});
$(".billingBankEdit").blur(function(){
	if($(this).val().replace(/(^\s*)|(\s*$)/g, "")!=""){
		$(".billingBankEdit").removeClass("light").siblings(".tips").fadeOut();
	}
});
$(".billingAccountEdit").blur(function(){
	if($(this).val().replace(/(^\s*)|(\s*$)/g, "")!=""){
		if (checkNumber($(".billingAccountEdit").val().replace(/[ ]/g,""))) {
	        $(".billingAccountEdit").removeClass("light").siblings(".tips").fadeOut();
	    } else {
	        $(".billingAccountEdit").addClass("light").siblings(".tips").fadeIn();
	    }
	}
});
$(".contactNameEdit").blur(function(){
	if($(this).val().replace(/(^\s*)|(\s*$)/g, "")!=""){
		$(".contactNameEdit").removeClass("light").siblings(".tips").fadeOut();
	}
});
$(".contactTelEdit").blur(function(){
	if($(this).val().replace(/(^\s*)|(\s*$)/g, "")!=""){
		if (checkPhone($(".contactTelEdit").val()) || checkLandline($(".contactTelEdit").val())) {
	        $(".contactTelEdit").removeClass("light").siblings(".tips").fadeOut();
	    } else {
	        $(".contactTelEdit").addClass("light").siblings(".tips").fadeIn();
	    }
	}
});
$(".contactIDnumberEdit").blur(function(){
	if($(this).val().replace(/(^\s*)|(\s*$)/g, "")!=""){
		if (checkIDnumber($(".contactIDnumberEdit").val())) {
	        $(".contactIDnumberEdit").removeClass("light").siblings(".tips").fadeOut();
	    } else {
	        $(".contactIDnumberEdit").addClass("light").siblings(".tips").fadeIn();
	    }
	}
});
$(".contactEmailEdit").blur(function(){
	if($(this).val().replace(/(^\s*)|(\s*$)/g, "")!=""){
		if (checkEmail($(".contactEmailEdit").val())) {
	        $(".contactEmailEdit").removeClass("light").siblings(".tips").fadeOut();
	    } else {
	        $(".contactEmailEdit").addClass("light").siblings(".tips").fadeIn();
	    }
	}
});
//判断是否有图
function judgeImg(){
	if ($("#bussnessLicessShow").attr("src")!=undefined && $("#bussnessLicessShow").attr("src")!="") {
        $(".first .ajax-file-upload").hide();
        $(".first .ajax-upload-dragdrop").hide();
        $(".first .reupload").show();
    } else {
        $(".first .ajax-upload-dragdrop").show();
        $(".first .ajax-file-upload").css("display", "block");
        $(".first .reupload").hide();
    }
	if ($("#evidenceShow").attr("src")!=undefined && $("#evidenceShow").attr("src")!="") {
        $(".second .ajax-file-upload").hide();
        $(".second .ajax-upload-dragdrop").hide();
        $(".second .reupload").show();
    } else {
        $(".second .ajax-upload-dragdrop").show();
        $(".second .ajax-file-upload").css("display", "block");
        $(".second .reupload").hide();
    }
	if ($("#idcardFaceShow").attr("src")!=undefined && $("#idcardFaceShow").attr("src")!="") {
        $(".third .ajax-file-upload").hide();
        $(".third .ajax-upload-dragdrop").hide();
        $(".third .reupload").show();
    } else {
        $(".third .ajax-upload-dragdrop").show();
        $(".third .ajax-file-upload").css("display", "block");
        $(".third .reupload").hide();
    }
	if ($("#idcardbackShow").attr("src")!=undefined && $("#idcardbackShow").attr("src")!="") {
        $(".forth .ajax-file-upload").hide();
        $(".forth .ajax-upload-dragdrop").hide();
        $(".forth .reupload").show();
    } else {
        $(".forth .ajax-upload-dragdrop").show();
        $(".forth .ajax-file-upload").css("display", "block");
        $(".forth .reupload").hide();
    }
}
