var pageNum = 1,
    pageSize = 10;
$(document).ready(function() {
    $(".main-left-item:eq(3)").children("a").addClass("color-orange").children("img").attr("src", "./img/r-4.png");
    $(".main-left-item:eq(3) ul li:eq(1) a").addClass("color-orange");
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

    getList('', pageNum, pageSize);
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
        		$('#bussnessLicess').val(data.data.uuid);
                $("input[type='file']").val("");
                $("#batchImport").html("").addClass("loadingBtn").removeAttr("id");
                $(".reUploadsBtn").val("").addClass("loadingBtn").removeAttr("id");
                $.ajax({
                    url: '../rest/qcb/employee/upload',
                    type: 'post',
                    async:false,
                    data: {
                    	"CSRFToken" :$('input[name="CSRFToken"]').val(),
                    	"file": data.data.uuid
                    }
                }).done(function(data) {
                	$(".batchImportBtn").html("批量导入").removeClass("loadingBtn").attr("id","#batchImport");
                	$(".reUploadsBtn").val("重新上传").removeClass("loadingBtn").attr("id","#reUploads");
                    if (data.status == "SUCCESS") {
                    	layer.msg("上传成功", {
                            time: 2000
                        },function(){
                        	getList('', '1', pageSize);
                        	$(".module-5").hide();
                        });               	
                    }else if (data.status == "WARNING") {
                    	layer.msg("上传信息有误");
                        $(".main-right-body").hide();
                        $(".module-5").show();
                    	getWarn(data);
                    } else {
                        layer.msg(data.message);
                    }
                }).fail(function(r) {
                    layer.msg("error", {
                        time: 2000
                    },function(){
                    	window.location.href=window.location.href;
                    });
                });
        	}
//            layer.msg("上传成功！");
            
            return false;
        }
    });
});

//获取列表
function getList(name, pageNum, pageSize) {
    $.ajax({
        url: '../rest/qcb/employee/list',
        type: 'get',
        data: {
            "name": name,
            "pageNum": pageNum,
            "pageSize": pageSize,
            "sorts":"status desc",
            "timestamp":new Date().getTime() 
        }
    }).done(function(r) {
        if (r.status == "SUCCESS") {
            var html = '<tr class="first-tr"><th width="16%" class="td-padding-item">姓名</th>' +
                '<th width="20%">身份证</th><th width="20%">手机</th><th>职位</th><th>在职情况</th><th>操作</th></tr>';
            if (r.data.length > 0) {
                $.each(r.data, function(index, el) {
                	var duty;
                	if(el.duty==null){
                		duty="未设置";
                	}else{
                		duty=el.duty;
                	}
                    if (el.status == "normal") {
                        html += '<tr><td class="td-padding-item">' + el.realname + '</td><td>' + el.idcard +
                            '</td><td>' + el.mobile + '</td><td>' + duty + '</td><td>' +
                            el.statusCN + '</td><td><a class="edit" uuid="' + el.uuid +
                            '" onclick="getEmployee(this);">修改</a><a uuid="' + el.uuid + '" onclick="deleteEmployee(this);">删除</a></td></tr>';
                    } else {
                        html += '<tr><td class="td-padding-item">' + el.realname + '</td><td>' +
                            el.idcard + '</td><td>' + el.mobile + '</td><td>' +
                            duty + '</td><td class="color-gray">' + el.statusCN + '</td><td><a class="edit" uuid="' + el.uuid +
                            '" onclick="getEmployee(this);">修改</a><a uuid="' + el.uuid + '" onclick="deleteEmployee(this);">删除</a></td></tr>';
                    }
                });
            } else {
                html += '<tr><td colspan="6" style="text-align:center">暂无员工记录</td></tr>';
            }

            $(".module-1 .table").html(html);
            $(".loadingOver").show();
        	$(".loadingDiv").hide();
        } else {
        	$(".loadingDiv").hide();
            layer.msg(r.message);
        }
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
	                getList('', page, size);
	                flag = false;
	                document.body.scrollTop = document.documentElement.scrollTop = 0;
	            }
	        });
	        $("#pageTool").find(".ui-paging-container:last").siblings().remove();
        }
        $(".ui-select-pagesize").unbind("change").change(function() {
            pageSize = $(this).val();
            pageNum = 1;
            getList('', pageNum, pageSize);
        });
        // $(".ui-paging-container li.ui-paging-toolbar a").click(function(){
        // 	pageNum = $(".ui-paging-container li.ui-paging-toolbar input").val();
        // 	getList('',pageNum,pageSize);
        // });
        $("#add").click(function() {
            $(".bg-add").fadeIn();
        });
        $("#modal-close").click(function() {
            $(".bg-add").hide();
            $(".bg-add input[type='button']").attr("id","modal-add").removeClass("loadingBtn").val("保存");
        });
        $("#modal-close-edit").click(function() {
            $(".bg-edit").hide();
            $(".bg-edit input[type='button']").attr("id","modal-edit").removeClass("loadingBtn").val("保存");
        });
    }).fail(function() {
    	$(".loadingDiv").hide();
        layer.msg("error", {
            time: 2000
        });
    });
}

//获取一条员工信息
function getEmployee(obj) {
    $(".bg-edit").fadeIn();
    var uuid = $(obj).attr("uuid");
    $.ajax({
        url: '../rest/qcb/employee/get',
        type: 'get',
        data: {
            "uuid": uuid,
            "timestamp":new Date().getTime() 
        }
    }).done(function(r) {
        if (r.status == "SUCCESS") {
            $("input[name='uuid']").val(r.data.uuid);
            $(".realnameEdit").val(r.data.realname);
            $(".idcardEdit").val(r.data.idcard);
            $(".mobileEdit").val(r.data.mobile);
            $(".departmentEdit").val(r.data.department);
            $(".dutyEdit").val(r.data.duty);
            if (r.data.status == "normal") {
                $(".statusEdit option[value='normal']").attr("selected", "selected");
            } else {
                $(".statusEdit option[value='disable']").attr("selected", "selected");
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
//编辑保存
$("#modal-edit").click(function() {
	if (checkPhone($(".bg-edit input[name='mobile']").val())) {
        $(".bg-edit input[name='mobile']").removeClass("light").siblings(".tips").fadeOut();		
    } else {
        $(".bg-edit input[name='mobile']").addClass("light").focus().siblings(".tips").fadeIn();
        return false;
    }
    if (checkNonempty($(".departmentEdit").val())) {
        $(".departmentEdit").removeClass("light").siblings(".tips").fadeOut();
    } else {
        $(".departmentEdit").addClass("light").focus().siblings(".tips").fadeIn();
        return false;
    }
    if (checkNonempty($(".dutyEdit").val())) {
        $(".dutyEdit").removeClass("light").siblings(".tips").fadeOut();
    } else {
        $(".dutyEdit").addClass("light").focus().siblings(".tips").fadeIn();
        return false;
    }
    $("#modal-edit").addClass("loadingBtn").val("").removeAttr("id");
    var form = $("#editBase");
    $.post(form.attr("action"), form.serialize(), function(data) {
        if (data.status == "SUCCESS") {
            $(".bg-edit").hide();
            layer.msg("修改成功", {
                time: 2000
            }, function() {
                window.location.href = "staffControl.jsp";
            });
        } else {
        	$(".bg-edit input[type='button']").attr("id","modal-edit").removeClass("loadingBtn").val("保存");;
            layer.msg(data.message);
        }
    });
    return false;
});

//录入员工信息
$("#modal-add").click(function() {
    if (checkNonempty($(".bg-add input[name='name']").val())) {
        $(".bg-add input[name='name']").removeClass("light").siblings(".tips").fadeOut();
    } else {
        $(".bg-add input[name='name']").addClass("light").focus().siblings(".tips").fadeIn();
        return false;
    }
    if (checkIDnumber($(".bg-add input[name='idcard']").val())) {
        $(".bg-add input[name='idcard']").removeClass("light").siblings(".tips").fadeOut();
    } else {
        $(".bg-add input[name='idcard']").addClass("light").focus().siblings(".tips").fadeIn();
        return false;
    }
    if (checkPhone($(".bg-add input[name='mobile']").val())) {
        $(".bg-add input[name='mobile']").removeClass("light").siblings(".tips").fadeOut();
    } else {
        $(".bg-add input[name='mobile']").addClass("light").focus().siblings(".tips").fadeIn();
        return false;
    }
    if (checkNonempty($(".bg-add input[name='department']").val())) {
        $(".bg-add input[name='department']").removeClass("light").siblings(".tips").fadeOut();
    } else {
        $(".bg-add input[name='department']").addClass("light").focus().siblings(".tips").fadeIn();
        return false;
    }
    if (checkNonempty($(".bg-add input[name='duty']").val())) {
        $(".bg-add input[name='duty']").removeClass("light").siblings(".tips").fadeOut();
    } else {
        $(".bg-add input[name='duty']").addClass("light").focus().siblings(".tips").fadeIn();
        return false;
    }
    $("#modal-add").addClass("loadingBtn").val("").removeAttr("id");
    var form = $("#addForm");
    $.post(form.attr("action"), form.serialize(), function(data) {
        if (data.status == "SUCCESS") {
            $(".bg-add").hide();
            layer.msg("添加成功", {
                time: 2000
            }, function() {
                window.location.href = "staffControl.jsp";
            });
        } else {
        	$(".bg-add input[type='button']").attr("id","modal-add").removeClass("loadingBtn").val("保存");
            layer.msg(data.message);
        }
    });
    return false;
});

//删除一条员工
function deleteEmployee(obj) {
	layer.confirm('确认删除此条员工吗？', {
		  btn: ['取消','确定'] //按钮
		}, function(){
			layer.closeAll();			 
		}, function(){
			var uuid = $(obj).attr("uuid");
		    $.ajax({
		        url: '../rest/qcb/employee/delete',
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
		                window.location.href = "staffControl.jsp";
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

function searchEmployee() {
    getList($(".search-box input").val().replace(/(^\s*)|(\s*$)/g, ""), pageNum, pageSize);
}
$(document).keypress(function(e) {
    // 回车键事件
    if (e.which == 13) {
        searchEmployee();
        return false;
    }
});
$(".mobileEdit").blur(function(){
	if($(this).val().replace(/(^\s*)|(\s*$)/g, "")!=""){
		if (checkPhone($(".bg-edit input[name='mobile']").val())) {
	        $(".bg-edit input[name='mobile']").removeClass("light").siblings(".tips").fadeOut();		
	    } else {
	        $(".bg-edit input[name='mobile']").addClass("light").siblings(".tips").fadeIn();
	    }
	}	
});
$(".departmentEdit").blur(function(){
	if($(this).val().replace(/(^\s*)|(\s*$)/g, "")!=""){
		$(".departmentEdit").removeClass("light").siblings(".tips").fadeOut();
	}	
});
$(".dutyEdit").blur(function(){
	if($(this).val().replace(/(^\s*)|(\s*$)/g, "")!=""){
		$(".dutyEdit").removeClass("light").siblings(".tips").fadeOut();
	}	
});

$(".bg-add input[name='name']").blur(function(){
	if($(this).val().replace(/(^\s*)|(\s*$)/g, "")!=""){
		$(".bg-add input[name='name']").removeClass("light").siblings(".tips").fadeOut();
	}	
});
$(".bg-add input[name='idcard']").blur(function(){
	if($(this).val().replace(/(^\s*)|(\s*$)/g, "")!=""){
		if (checkIDnumber($(".bg-add input[name='idcard']").val())) {
	        $(".bg-add input[name='idcard']").removeClass("light").siblings(".tips").fadeOut();
	    } else {
	        $(".bg-add input[name='idcard']").addClass("light").siblings(".tips").fadeIn();
	    }
	}	
});

$(".bg-add input[name='mobile']").blur(function(){
	if($(this).val().replace(/(^\s*)|(\s*$)/g, "")!=""){
		if (checkPhone($(".bg-add input[name='mobile']").val())) {
	        $(".bg-add input[name='mobile']").removeClass("light").siblings(".tips").fadeOut();		
	    } else {
	        $(".bg-add input[name='mobile']").addClass("light").siblings(".tips").fadeIn();
	    }
	}	
});
$(".bg-add input[name='department']").blur(function(){
	if($(this).val().replace(/(^\s*)|(\s*$)/g, "")!=""){
		$(".bg-add input[name='department']").removeClass("light").siblings(".tips").fadeOut();
	}	
});
$(".bg-add input[name='duty']").blur(function(){
	if($(this).val().replace(/(^\s*)|(\s*$)/g, "")!=""){
		$(".bg-add input[name='duty']").removeClass("light").siblings(".tips").fadeOut();
	}	
});

//批量导入按钮
$("#batchImport").unbind().click(function() {
    $(".ajax-file-upload").find("input[type='file']").css({
    	"display":"block !important"
    });
    $(".ajax-file-upload").find("input[type='file']").eq(0).click();
});
//warning情况错误枚举
function getWarn(data){
	//错误列表
	var th = '';
	var totlePrice = 0;
	var html = '<tr class="first-tr"><th width="10%" class="td-padding-item">序号</th><th width="15%">姓名</th>' +
	    '<th width="30%">身份证</th><th width="20%">手机</th><th width="15%">部门</th><th width="10%">职位</th></tr>';
	for(var key in data.data.datasMap){
		var duty;
		var department;
		var classname;
		if(data.data.datasMap[key].duty==""){
			duty = "未设置";
		}else{
			duty = data.data.datasMap[key].duty;
		}
		if(data.data.datasMap[key].department==""){
			department = "未设置";
		}else{
			department = data.data.datasMap[key].department;
		}
		for(var i=0;i<data.error.length;i++){
			if(key==Number(data.error[i].index)){
				classname = "color-red";
			}else{
				classname = "";
			}
		}
		 tr = '<td class="td-padding-item">' + key + '</td><td>' + data.data.datasMap[key].realname + '</td><td>' + data.data.datasMap[key].idcard +
	        '</td><td>' + data.data.datasMap[key].mobile + '</td><td>' + department + 
	        '</td><td>' + duty + '</td>';
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
			$(".errorBox").css({"max-height":"155px","overflow":"hidden"});
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
//重新上传
$("#reUploads").click(function(){
	$(".ajax-file-upload").find("input[type='file']").css({
    	"display":"block !important"
    });
    $(".ajax-file-upload").find("input[type='file']").eq(0).click();
});
