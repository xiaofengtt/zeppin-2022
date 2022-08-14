var date = new Date();
var day = new Date(date.getFullYear(), date.getMonth(), 0).getDate();
var firstday = new Date(date.getFullYear(), date.getMonth(), 1).getDate();
var enddate = new Date(new Date().getFullYear(), new Date().getMonth()-1, day);//上个月最后一天
var startdate = new Date(new Date().getFullYear(), new Date().getMonth()-1, firstday);//上个月第一天
var idArr = [];
var types="";
$(function(){
	lay("input[name='time'],input[name='endtime']").each(function(){
		laydate.render({
    		elem: this,
    		value: enddate,
    		showBottom: false,
    		format: "yyyy-MM-dd",
    		done:function(){
//    			$(".batchExport").attr("href","../rest/backadmin/submit/submit?time="+$("input[name='time']").val()+types+"&starttime="+$("input[name='starttime']").val()+"&endtime="+$("input[name='endtime']").val());

    		}
    	});	
	});
	lay("input[name='starttime']").each(function(){
		laydate.render({
    		elem: this,
    		value: startdate,
    		showBottom: false,
    		format: "yyyy-MM-dd",
    		done:function(){
//    			$(".batchExport").attr("href","../rest/backadmin/submit/submit?time="+$("input[name='time']").val()+types+"&starttime="+$("input[name='starttime']").val()+"&endtime="+$("input[name='endtime']").val());
    		}
    	});	
	});
	//选中
    $(".check").unbind("change").change(function() {
        var checkFlag = true,
            _this = "";
        idArr = [];
        types="";
        $(".check").each(function(index, el) {
            _this = $(".check").eq(index);
            if (_this.prop("checked") == true) {
                idArr.push(_this.attr("data-id"));
                types =types+"&types="+$(this).next().val(); 
            } else {
                if (idArr.indexOf(_this.attr("data-id")) != -1) {
                    idArr.splice(idArr.indexOf(_this.attr("data-id")), 1);
                }
                checkFlag = false;
            }
        });
//        $(".batchExport").attr("href","../rest/backadmin/submit/submit?time="+$("input[name='time']").val()+types+"&starttime="+$("input[name='starttime']").val()+"&endtime="+$("input[name='endtime']").val());
        if (checkFlag) {
            $("#allCheck").prop("checked", true);
        } else {
            $("#allCheck").prop("checked", false);
        }
    });
	//全选
    $("#allCheck").on("change", function() {
        if ($(this).is(":checked") == true) {
            idArr = [];
            types="";
            $(".check").prop("checked", true);
            $(".check").each(function(index, el) {
                idArr.push($(".check").eq(index).attr("data-id"));
                types =types+"&types="+$(this).next().val(); 
            });
        } else {
            $(".check").prop("checked", false);
            idArr = [];
            types="";
        }
//        $(".batchExport").attr("href","../rest/backadmin/submit/submit?time="+$("input[name='time']").val()+types+"&starttime="+$("input[name='starttime']").val()+"&endtime="+$("input[name='endtime']").val());
    });
});
$(".exportText").click(function(){
	var index = layer.load(1, {shade: false});
	$(".layui-layer").append("<span style='line-height:40px;'>正在导出，请稍后</span>");
	var src="../rest/backadmin/submit/submit?types="+$(this).parent().parent().find("input[name='types']").val()+
			"&time="+$("input[name='time']").val()+"&starttime="+$("input[name='starttime']").val()+"&endtime="+$("input[name='endtime']").val();
	$("#box_paint_container").contents().find("html").remove();
	$("iframe").attr("src",src);
	layer.closeAll();
	$("#iframeDiv").show();
	setTimeout('$("#iframeDiv").hide();',2000);
});
$(".batchExport").click(function(){
	var startTime = $("input[name='starttime']").val().replace(/-/g, "");
	var endTime = $("input[name='endtime']").val().replace(/-/g, "");
	if(Number(startTime)>Number(endTime)){
		layer.msg("时间区间不合法");
		return false;
	}
	if(types==""){
		layer.msg("请选择要导出的数据");
	}else{
		var index = layer.load(1, {shade: false});
		$(".layui-layer").append("<span style='line-height:40px;'>正在导出，请稍后</span>");
		var src="../rest/backadmin/submit/submit?time="+$("input[name='time']").val()+types+"&starttime="+$("input[name='starttime']").val()+"&endtime="+$("input[name='endtime']").val();
		$("#box_paint_container").contents().find("html").remove();
		$("iframe").attr("src",src);
		layer.closeAll();
		$("#iframeDiv").show();
		setTimeout('$("#iframeDiv").hide();',2000);
	}
});

