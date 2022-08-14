var menuWidth = "", //声明左侧菜单宽度
    mainWidth = "", //声明页面主体宽度
    contentWidth = "";//声明content的宽度
$(document).ready(function() {
    $.get('../rest/backadmin/opManage/all', function(r) {
        if (r.status == 'SUCCESS') {
            var html = '';
            if (r.data.length > 0) {
                $.each(r.data, function(i, v) {
                    html += '<option value="' + v.id + '">' + v.name + '</option>';
                })
            }
            $('#operatorSelect').append(html);
            $('#operatorSelect').change(function() {
                $('#contain-all').removeClass('hidden');
                $('#operatorName').html($(this).find("option:selected").text());
                $('#operator').val($(this).val());
                getList();
                mainWidth = $("#main").width();
                contentWidth = mainWidth - menuWidth-15;
                $("#content").css({
                    "width": contentWidth + "px"
                });
            });
            //select 模糊查询
            $('#operatorSelect').selectpicker({  
                'selectedText': 'cat'  
            });
        } else {
            layer.msg(r.message, {
                time: 2000
            })
        }
    });
    menuWidth = $("#super-menu").width(); //赋值左侧菜单宽度
    mainWidth = $("#main").width(); //赋值页面主体宽度
    contentWidth = mainWidth - menuWidth-15; //赋值content的宽度
  //content宽度

    $("#content").css({
        "width": contentWidth + "px"
    });
    $(window).resize(function() {
        mainWidth = $("#main").width();
        contentWidth = mainWidth - menuWidth-15;
        $("#content").css({
            "width": contentWidth + "px"
        });
    });
})
$('.operablePower').change(function(){
	$('#contain-all').removeClass('hidden');
    $('#operatorName').html($(this).find("option:selected").text());
    getList("1");
    mainWidth = $("#main").width();
    contentWidth = mainWidth - menuWidth-15;
    $("#content").css({
        "width": contentWidth + "px"
    }); 
});
function selectAll(obj) {	
	if($(obj).text()=="全选"){
		$('.main-contain .contain-body input[type="checkbox"]').prop("checked", true);
		$(obj).text("全不选");
	}else{
		$('.main-contain .contain-body input[type="checkbox"]').prop("checked", false);
		$(obj).text("全选");
	}
}

function selectChildren(obj) {
    var id = $(obj).attr("id")
    if ($(obj).is(":checked")) {
        $("[parent='" + id + "']").prop("checked", true);
    } else {
        $("[parent='" + id + "']").prop("checked", false);
    }
}

function getList(obj) {
    var operator = $('#operatorSelect').val();
    if (operator != '') {
        $.get('../rest/backadmin/operatorMethod/all', function(r) {
            if (r.status == 'SUCCESS') {
                var leftHtml = '';
                var rightHtml = '';
                $.each(r.data, function(i, v) {
                    leftHtml += '<li data-id="' + v.id + '"';
                    if (i == 0) {
                        leftHtml += ' class="selected"';
                    }
                    leftHtml += '><div><input class="hidden" type="checkbox" id="' + v.id + '" name="controller" value="' + v.id + '" onchange="selectChildren(this)"/><label class="checkbox-img" for="' + v.id + '"></label><span>' + v.description + '</span></div></li>';
                    rightHtml += '<div id="method_' + v.id + '" ';
                    if (i != 0) {
                        rightHtml += ' class="hidden containRightDiv"';
                    }
                    rightHtml += '><ul>';
                    $.each(v.methodList, function(j, w) {
                    	var editClass = "";
                    	var examClass = "";
                        if($(".operablePower").val()=="0"){
                        	editClass = "showClass";
                        	examClass = "showClass";
                        }else if($(".operablePower").val()=="1"){
                        	editClass = "showClass";
                        	examClass = "hideClass";
                        }else if($(".operablePower").val()=="2"){
                        	editClass = "hideClass";
                        	examClass = "showClass";
                        }
                    	if(w.description=="审核"){
                    		rightHtml += '<li class="'+examClass+' examClass"><div><input class="hidden" type="checkbox" id="' + w.id + 
                    		'" parent ="' + w.controller + '" name="method" value="' + w.id + 
                    		'"/><label class="checkbox-img checkbox-examine" for="' + w.id + '"></label><span>' + w.description + '</span></div></li>';
                    	}else{
                            rightHtml += '<li class="'+editClass+' editClass"><div><input class="hidden" type="checkbox" id="' + w.id + 
                            '" parent ="' + w.controller + '" name="method" value="' + w.id + 
                            '"/><label class="checkbox-img" for="' + w.id + '"></label><span>' + w.description + '</span></div></li>';
                    	}
                    })
                    rightHtml += '</ul></div>';
                })
                $('#left-ul').html(leftHtml);
                $('#contain-body-right').html(rightHtml);
                //勾中审核 自动勾选修改
                $(".checkbox-examine").click(function(){
                	if($(this).parent().find("input[type='checkbox']").is(':checked')==false
                		&&$(this).parent().parent().prev().find("input[type='checkbox']").is(':checked')==false){
                		$(this).parent().parent().prev().find("label").click();
                	}
                	
                });
                $('#left-ul li').click(function() {
                    $('#left-ul li').removeClass("selected");
                    $(this).addClass("selected");
                    var data_id = $(this).attr("data-id");
                    $('#contain-body-right>div').addClass("hidden");
                    $('#method_' + data_id).removeClass("hidden");
                })
            } else {
                layer.msg(r.message, {
                    time: 2000
                })
            }
        }).done(function(r) {
            if (r.totalResultCount > 0) {
                $.get('../rest/backadmin/operatorMethod/get?operator=' + operator, function(ret) {
                    if (ret.status == 'SUCCESS') {
                        if (ret.totalResultCount > 0) {
                            $.each(ret.data.list, function(i, v) {
                                $('#' + v.method).prop("checked", true);
                                $('#' + v.controller).prop("checked", true);
                            });
                        }
                        if(!obj){
                        	if(ret.data.type=="check"){
                            	$(".operablePower").find("option:contains('审核')").attr("selected",true);
                            	$(".examClass").show().addClass("showClass").removeClass("hideClass");
                            	$(".editClass").hide().addClass("hideClass").removeClass("showClass");
                            }else{
                            	$(".operablePower").find("option:contains('编辑')").attr("selected",true);
                            	$(".examClass").hide().addClass("hideClass").removeClass("showClass");
                            	$(".editClass").show().addClass("showClass").removeClass("hideClass");;
                            }
                        }
                        
                    }
                })
            }
            if(obj){
            	selectAll();
            }
        })
    } else {
        $('#contain-all').addClass('hidden');
        $('#nodata').removeClass('hidden');
    }
}

$('#formsubmit').submit(function() {
    if (flagSubmit == false) {
        return false;
    }
    flagSubmit = false;
    setTimeout(function() {
        flagSubmit = true;
    }, 3000);
    var str = $(this).serialize();
    var methods=[];
    var controllers=[];
    $(".left-ul input[type='checkbox']").each(function(){
    	if($(this).is(":checked")){
    		controllers.push($(this).val());
    	}
    });
    $(".showClass input[type='checkbox']").each(function(){
    	if($(this).is(":checked")){
    		methods.push($(this).val());
    	}
    });
    if(methods.length==0){
    	layer.msg("请配置权限");
    }else{
    	$.ajax({
            url: '../rest/backadmin/operatorMethod/edit',
            type: 'post',
            traditional: "true",
            data: {
            	"operator":$("input[name='operator']").val(),
            	"controller":$("input[name='controller']").val(),
            	"CSRFToken": $("input[name='CSRFToken']").val(),
                "method": methods
            }
        })
        .done(function(data) {
        	if (data.status == "SUCCESS") {
                layer.msg('修改成功', {
                    time: 1000
                }, function() {
                    location.reload();
                });
            } else {
                layer.msg(data.message, {
                    time: 2000
                })
            }
        })
        .fail(function() {
            layer.msg("error", {
                time: 2000
            });
        });
    }
    
    return false;
});