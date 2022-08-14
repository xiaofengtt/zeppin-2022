var pageNum = 1,
    pageSize = 10;
var menuList = 0;
$(document).ready(function() {
    $(".main-left-item:eq(4)").children("a").addClass("color-orange").children("img").attr("src", "./img/r-5.png");
    $(".main-left-item:eq(4) ul li:eq(1) a").addClass("color-orange");
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
    getList(pageNum, pageSize);
    getJurisdiction();
});

//管理员列表
function getList(pageNum, pageSize) {
    $.ajax({
        url: '../rest/qcb/companyAdmin/list',
        type: 'get',
        data: {
            "pageNum": pageNum,
            "pageSize": pageSize,
            "timestamp":new Date().getTime() 
        }
    }).done(function(r) {
        if (r.status == "SUCCESS") {
            var html = '<tr class="first-tr"><th width="13%" class="td-padding-item">姓名</th>' +
                '<th width="18%">手机</th><th width="15%">角色</th><th width="40%">权限</th><th>操作</th></tr>';
            if (r.data.length > 0) {
                $.each(r.data, function(index, el) {
                    if (el.menu == "全部") {
                        if (el.flagAdmin == true) {
                            html += '<tr><td class="td-padding-item">' + el.name + '</td><td>' + el.mobile +
                                '</td><td>' + el.role + '</td><td class="color-green">' + el.menu + '</td><td><a class="edit color-gray" uuid="' + el.uuid +
                                '" style="cursor:not-allowed">修改</a><a class="color-gray" uuid="' +
                                el.uuid + '" style="cursor:not-allowed">删除</a></td></tr>';

                        } else {
                            html += '<tr><td class="td-padding-item">' + el.name + '</td><td>' + el.mobile +
                                '</td><td>' + el.role + '</td><td class="color-green">' + el.menu + '</td><td><a class="edit" uuid="' + el.uuid +
                                '" onclick="getEmployee(this);">修改</a><a uuid="' + el.uuid + '" onclick="deleteEmployee(this);">删除</a></td></tr>';
                        }
                    } else {
                    	var menu;
                    	if(el.menu==null){
                    		menu = "未设置";
                    	}else{
                    		menu = el.menu;
                    	}
                        if (el.flagAdmin == true) {
                            html += '<tr><td class="td-padding-item">' + el.name + '</td><td>' + el.mobile +
                                '</td><td>' + el.role + '</td><td>' + menu + '</td><td><a class="edit color-gray" uuid="' + el.uuid +
                                '" style="cursor:not-allowed">修改</a><a class="color-gray" uuid="' +
                                el.uuid + '" style="cursor:not-allowed">删除</a></td></tr>';

                        } else {
                            html += '<tr><td class="td-padding-item">' + el.name + '</td><td>' + el.mobile +
                                '</td><td>' + el.role + '</td><td>' + menu + '</td><td><a class="edit" uuid="' + el.uuid +
                                '" onclick="getEmployee(this);">修改</a><a uuid="' + el.uuid + '" onclick="deleteEmployee(this);">删除</a></td></tr>';
                        }

                    }
                });
            } else {
                html += '<tr><td colspan="5" style="text-align:center">暂无管理员</td></tr>';
            }

            $(".table").html(html);
            if (r.totalPageCount!=0) {
    	        $('#pageTool').Paging({
    	            prevTpl: "<",
    	            nextTpl: ">",
    	            pagesize: pageSize,
    	            count: r.totalResultCount,
    	            current: pageNum,
    	            toolbar: true,
    	            pageSizeList: [10, 20, 50],
    	            callback: function(page, size, count) {
    	                pageNum = page;
    	                getList(page, size);
    	                flag = false;
    	                document.body.scrollTop = document.documentElement.scrollTop = 0;
    	            }
    	        });
    	        $("#pageTool").find(".ui-paging-container:last").siblings().remove();
            }
            $(".ui-select-pagesize").unbind("change").change(function() {
                pageSize = $(this).val();
                getList('1', pageSize);
            });
            $(".loadingOver").show();
        	$(".loadingDiv").hide();
        } else {
        	$(".loadingDiv").hide();
            layer.msg(r.message);
        }


    }).fail(function() {
    	$(".loadingDiv").hide();
        layer.msg("error", {
            time: 2000
        });
    });
}

//权限
function getJurisdiction() {
    $.ajax({
        url: '../rest/qcb/menu/list',
        type: 'get',
        data: {
        	"timestamp":new Date().getTime() 
        }
    }).done(function(r) {
        if (r.status == "SUCCESS") {
            var html = '';
            var liHtml = '';
            for (var key in r.data) {
                liHtml = '';
                for (var index in r.data[key].childMenu) {
                    liHtml += '<li uuid="' + r.data[key].childMenu[index].uuid + '"><a class="check"></a>' + r.data[key].childMenu[index].title + '</li>';
                }
                html += '<div class="JurisdictionBox' + menuList + '" uuid="' + r.data[key].uuid + '"><h5 class="check-box-title"><a class="check-all"></a>' +
                    r.data[key].title + '</h5><ul>' + liHtml + '</ul></div>';
                menuList++;
            }
            $(".check-box").html(html);
        } else {
            layer.msg(r.message);
        }
        $("#add").click(function() {
            $(".bg-add").fadeIn();
        });
        $("#modal-close").click(function() {
            $(".bg-add").hide();
            $(".bg-add input[type='button']").attr("id","modal-add");
            $(this).parent().parent().find("input[type='text']").val("");
            $(this).parent().parent().find(".check").removeClass("light");
            $(this).parent().parent().find(".check-all").removeClass("light");
        });

        $(".edit").click(function() {
            $(".bg-edit").fadeIn();
        });
        $("#modal-close-edit").click(function() {
            $(".bg-edit").hide();
            $(".bg-edit input[type='button']").attr("id","modal-edit");
            $(this).parent().parent().find("input[type='text']").val("");
            $(this).parent().parent().find(".check").removeClass("light");
            $(this).parent().parent().find(".check-all").removeClass("light");
        });
        //选择权限逻辑
        $(".check-box-title").click(function() {
            $(this).siblings("ul").toggle();
            if ($(this).siblings("ul").css("display") == "none") {
                $(this).css({
                    "background-image": "url(img/bottomright.png)",
                    "background-size":"11px"
                });
            } else {
                $(this).css({
                    "background-image": "url(img/bottombottom.png)",
                    "background-size":"12px"
                });
            }
        });
        $(".check-all").click(function(event) {
            if (!$(this).hasClass("light")) {
                $(this).addClass("light");
                $(this).parent().parent().find(".check").addClass("light");
            } else {
                $(this).removeClass("light");
                $(this).parent().parent().find(".check").removeClass("light");
            }
            event.stopPropagation();
        });
        $(".check").parent().click(function() {
            var checkFlag = true;
            var checkList = $(this).parent().find(".check");
            var checkAll = $(this).parent().parent().find(".check-all");
            if (!$(this).find(".check").hasClass("light")) {
                $(this).find(".check").addClass("light");
            } else {
                $(this).find(".check").removeClass("light");
                checkAll.removeClass("light");
            }
            checkList.each(function(index, el) {
                if (!checkList.eq(index).hasClass("light")) {
                    checkFlag = false;
                }
            });
            if (checkFlag == true) {
                checkAll.addClass("light");
            }
        });
    }).fail(function() {
        layer.msg("error", {
            time: 2000
        });
    });
}

//添加管理员
$("#modal-add").click(function() {
    var Jurisdiction = '';
    for (var i = 0; i < menuList; i++) {
        var JurisdictionItem = '';
        for (var j = 0; j < $("#add-check-box .JurisdictionBox" + i + " li").length; j++) {
            if ($("#add-check-box .JurisdictionBox" + i + " li:eq(" + j + ")").find("a").hasClass("light")) {
                JurisdictionItem = JurisdictionItem + $("#add-check-box .JurisdictionBox" + i + " li:eq(" + j + ")").attr("uuid") + ',';
            }
        }
        if (JurisdictionItem != "") {
            Jurisdiction = Jurisdiction + JurisdictionItem + $("#add-check-box .JurisdictionBox" + i).attr("uuid") + ',';
        }
    }
    if (checkNonempty($(".bg-add input[name='name']").val())) {
        $(".bg-add input[name='name']").removeClass("lights").siblings(".tips").fadeOut();
    } else {
        $(".bg-add input[name='name']").addClass("lights").focus().siblings(".tips").fadeIn();
        return false;
    }
    if (checkPhone($(".bg-add input[name='mobile']").val())) {
        $(".bg-add input[name='mobile']").removeClass("lights").siblings(".tips").fadeOut();
    } else {
        $(".bg-add input[name='mobile']").addClass("lights").focus().siblings(".tips").fadeIn();
        return false;
    }
    if (checkNonempty($(".bg-add input[name='role']").val())) {
        $(".bg-add input[name='role']").removeClass("lights").siblings(".tips").fadeOut();
    } else {
        $(".bg-add input[name='role']").addClass("lights").focus().siblings(".tips").fadeIn();
        return false;
    }
    if (Jurisdiction != "") {
    	$("#add-check-box").siblings(".tips").fadeOut();
    } else {
    	$("#add-check-box").siblings(".tips").fadeIn();
        return false;
    }
    $("#modal-add").addClass("loadingBtn").val("").removeAttr("id");
    var form = $("#addForm");
    $.post(form.attr("action"), form.serialize() + "&authority=" + Jurisdiction, function(data) {
        if (data.status == "SUCCESS") {
            $(".bg-add").hide();
            layer.msg("添加成功", {
                time: 2000
            }, function() {
            	window.location.href = "superAdminList.jsp";
            });
        } else {
            layer.msg(data.message);
        }
        $(".bg-add input[type='button']").removeClass("loadingBtn").val("保存").attr("id","modal-add");
    });
    return false;
});

//获取一条员工信息
function getEmployee(obj) {
    $(".bg-edit").fadeIn();
    var uuid = $(obj).attr("uuid");
    $.ajax({
        url: '../rest/qcb/companyAdmin/get',
        type: 'get',
        data: {
            "uuid": uuid,
            "timestamp":new Date().getTime() 
        }
    }).done(function(r) {
        if (r.status == "SUCCESS") {
            $("input[name='uuid']").val(r.data.uuid);
            $(".nameEdit").val(r.data.name);
            $(".mobileEdit").val(r.data.mobile);
            $(".roleEdit").val(r.data.role);
            for (var i = 0; i < menuList; i++) {
                for (var j = 0; j < $("#edit-check-box .JurisdictionBox" + i + " li").length; j++) {
                    for (var key in r.data.listMenu) {
                        for (var index in r.data.listMenu[key].childMenu) {
                            if ($("#edit-check-box .JurisdictionBox" + i + " li:eq(" + j + ")").attr("uuid") == r.data.listMenu[key].childMenu[index].uuid &&
                                r.data.listMenu[key].childMenu[index].flagOwn == true) {
                                $("#edit-check-box .JurisdictionBox" + i + " li:eq(" + j + ")").find("a").addClass("light");
                            }
                        }
                    }

                }
                var checkList = $("#edit-check-box .JurisdictionBox" + i + " li").parent().find(".check");
                var checkFlag = true;
                var checkAll = $("#edit-check-box .JurisdictionBox" + i + " li").parent().parent().find(".check-all");
                checkList.each(function(index, el) {
                    if (!checkList.eq(index).hasClass("light")) {
                        checkFlag = false;
                    }
                });
                if (checkFlag == true) {
                    checkAll.addClass("light");
                }
            }
        } else {
            layer.msg(r.message, {
                time: 2000
            });
        }
    }).fail(function(r) {
        layer.msg("error", {
            time: 2000
        });
    });

}

//编辑保存
$("#modal-edit").click(function() {
    var Jurisdiction = '';
    for (var i = 0; i < menuList; i++) {
        var JurisdictionItem = '';
        for (var j = 0; j < $("#edit-check-box .JurisdictionBox" + i + " li").length; j++) {
            if ($("#edit-check-box .JurisdictionBox" + i + " li:eq(" + j + ")").find("a").hasClass("light")) {
                JurisdictionItem = JurisdictionItem + $("#edit-check-box .JurisdictionBox" + i + " li:eq(" + j + ")").attr("uuid") + ',';
            }
        }
        if (JurisdictionItem != "") {
            Jurisdiction = Jurisdiction + JurisdictionItem + $("#edit-check-box .JurisdictionBox" + i).attr("uuid") + ',';
        }
    }
    if (checkNonempty($(".bg-edit input[name='role']").val())) {
        $(".bg-edit input[name='role']").removeClass("lights").siblings(".tips").fadeOut();
    } else {
        $(".bg-edit input[name='role']").addClass("lights").focus().siblings(".tips").fadeIn();
        return false;
    }
    if (Jurisdiction != "") {
    	$("#edit-check-box").siblings(".tips").fadeOut();
    } else {
    	$("#edit-check-box").siblings(".tips").fadeIn();
        return false;
    }
    $("#modal-edit").addClass("loadingBtn").val("").removeAttr("id");
    var form = $("#editForm");
    $.post(form.attr("action"), form.serialize() + "&authority=" + Jurisdiction, function(data) {
        if (data.status == "SUCCESS") {
            $(".bg-edit").hide();
            layer.msg("修改成功", {
                time: 2000
            }, function() {
                window.location.href = "superAdminList.jsp";
            });
        } else {
            layer.msg(data.message);
        }
        $(".bg-edit input[type='button']").removeClass("loadingBtn").val("保存").attr("id","modal-edit");
    });
    return false;
});

//删除一条管理员
function deleteEmployee(obj) {
    layer.confirm('确认删除此条管理员吗？', {
        btn: ['取消', '确定'] //按钮
    }, function() {
    	layer.closeAll();       
    }, function() {
    	var uuid = $(obj).attr("uuid");
        $.ajax({
            url: '../rest/qcb/companyAdmin/delete',
            type: 'get',
            data: {
                "uuid": uuid,
                "timestamp":new Date().getTime() 
            }
        }).done(function(r) {
            if (r.status == "SUCCESS") {
                layer.msg("删除成功", {
                    time: 2000
                }, function() {
                    window.location.href = "superAdminList.jsp";
                });
            } else {
                layer.msg(r.message);
            }
        }).fail(function(r) {
            layer.msg("error", {
                time: 2000
            });
        });
    });
}
$(".roleEdit").blur(function(){
	if($(this).val().replace(/(^\s*)|(\s*$)/g, "")!=""){
		$(".bg-edit input[name='role']").removeClass("lights").siblings(".tips").fadeOut();
	}
});

$(".bg-add input[name='name']").blur(function(){
	if($(this).val().replace(/(^\s*)|(\s*$)/g, "")!=""){
		$(".bg-add input[name='name']").removeClass("lights").siblings(".tips").fadeOut();
	}	
});
$(".bg-add input[name='mobile']").blur(function(){
	if($(this).val().replace(/(^\s*)|(\s*$)/g, "")!=""){
		if (checkPhone($(".bg-add input[name='mobile']").val())) {
	        $(".bg-add input[name='mobile']").removeClass("lights").siblings(".tips").fadeOut();
	    } else {
	        $(".bg-add input[name='mobile']").addClass("lights").siblings(".tips").fadeIn();
	    }
	}	
});

$(".bg-add input[name='role']").blur(function(){
	if($(this).val().replace(/(^\s*)|(\s*$)/g, "")!=""){
		$(".bg-add input[name='role']").removeClass("lights").siblings(".tips").fadeOut();
	}	
});


