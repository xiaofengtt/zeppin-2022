var date = new Date();
var day = new Date(date.getFullYear(), date.getMonth(), 0).getDate();
var firstday = new Date(date.getFullYear(), date.getMonth(), 1).getDate();
var enddate = new Date(new Date().getFullYear(), new Date().getMonth()-1, day);//上个月最后一天
var startdate = new Date(new Date().getFullYear(), new Date().getMonth()-1, firstday);//上个月第一天
var listType = (url('?listType') != null) ? url('?listType') : '';
$(function(){
	lay("input[name='endtime']").each(function(){
		laydate.render({
    		elem: this,
    		value: enddate,
    		showBottom: true,
    		format: "yyyy-MM-dd",
    		done:function(){
    		}
    	});	
	});
	lay("input[name='starttime']").each(function(){
		laydate.render({
    		elem: this,
    		value: startdate,
    		showBottom: true,
    		format: "yyyy-MM-dd",
    		done:function(){
    		}
    	});	
	});
});
$(".sureBtn").click(function(){
	var startTime = $("input[name='starttime']").val().replace(/-/g, "");
	var endTime = $("input[name='endtime']").val().replace(/-/g, "");
	if(Number(startTime)>Number(endTime)){
		layer.msg("时间区间不合法");
		return false;
	}
	var index = layer.load(1, {shade: false});
	$(".layui-layer").append("<span style='line-height:40px;'>正在导出，请稍后</span>");
	var src="../rest/backadmin/" + listType + "/addOutput?starttime="+$("input[name='starttime']").val()+"&endtime="+$("input[name='endtime']").val();
	$(this).attr("href",src);
	layer.closeAll();
});
