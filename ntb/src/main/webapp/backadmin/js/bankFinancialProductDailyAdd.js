$(document).ready(function() {
	$(".datepicker").click(function(){
		laydate({
            start: laydate.now(0, "YYYY/MM/DD hh:mm"),
            istime: false,
            istoday: false,
            format: 'YYYY-MM-DD'
        });
	});
});
//提交
$('#formsubmit').submit(function() {
	var uuid = (url('?uuid') != null) ? url('?uuid') : '';
	var netValue=$("#netValue").val().replace(/(^\s*)|(\s*$)/g, "");
	var statistime=$("#statistime").val().replace(/(^\s*)|(\s*$)/g, "");
	
	if(netValue==""){
		layer.msg('净值不能为空', {
			time: 2000 
		});      
	}else if(isNaN(netValue)){
		layer.msg('净值输入错误', {
			time: 2000 
		});      
	}else if(statistime==""){
		layer.msg('添加日期不能为空', {
			time: 2000 
		});      
	}else{
		window.parent.addValue(netValue,statistime, function(e){
			if(e !=''){
				layer.msg(e, {
					time: 2000 
				})
			}else{
				layer.msg('添加成功', {
					time: 1000 
				}, function(){
					parent.$.colorbox.close();
				});
			}
		});
	}
	return false;
});