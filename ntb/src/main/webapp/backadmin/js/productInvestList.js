var pageNum = '1';
var flag=true;
function init(){
	pageNum = '1';
	flag=true;
}
function checkThis(t) {
	layer.confirm('确定要变更状态吗?', function(index){
		var obj = $(t);
		var uuid = obj.attr('data-uuid');
		var status = obj.attr('data-status');
		if(status=='checked'){
			$.get('../rest/backadmin/bankFinancialProductInvest/check?uuid='+uuid+'&status='+status, function(r) {
				if (r.status == 'SUCCESS') {
					layer.msg('操作成功', {
						time: 1000 
					}, function(){
						init();
						getStatusList();
						getList();
					});
				} else {
					alert('操作失败,' + r.message);
				}
			})
		}else if(status=='deleted'){
			$.get('../rest/backadmin/bankFinancialProductInvest/delete?uuid='+uuid, function(r) {
				if (r.status == 'SUCCESS') {
					layer.msg('操作成功', {
						time: 1000 
					}, function(){
						init();
						getStatusList();
						getList();
					});
				} else {
					alert('操作失败,' + r.message);
				}
			})
		}else if(status=='exception'){
			$.get('../rest/backadmin/bankFinancialProductInvest/exception?uuid='+uuid, function(r) {
				if (r.status == 'SUCCESS') {
					layer.msg('操作成功', {
						time: 1000 
					}, function(){
						init();
						getStatusList();
						getList();
					});
				} else {
					alert('操作失败,' + r.message);
				}
			})
		}
		layer.close(index);
	});
	return false;
}

function searchBtn(){
	init();
	getList();
	return false;
}

function openThis(t){
	$(t).attr("onclick","closeThis(this)");
	$(t).closest('.btn-group').find('.popover').addClass("open");
}
function closeThis(t){
	$(t).attr("onclick","openThis(this)");
	$(t).closest('.btn-group').find('.popover').removeClass("open");
}

$(".filter a").click(function(){
	$(this).addClass("statusLight").siblings().removeClass("statusLight");
	init();
	getStageList();
	getList();
});
$(".filter1 a").click(function(){
	$(this).addClass("statusLight1").siblings().removeClass("statusLight1");
	init();
	getList();
});

$(document).ready(function() {
	getStatusList();
	getList();
	$(".shortStatusDiv").css({"max-width":$(".main-contain").width()-300+"px","margin":"0"});
	$(".shortStatusDiv div").css({"max-width":$(".main-contain").width()-390+"px","margin":"0"});
});
$(window).resize(function(){
	if($(window).width()>1250){
		$(".shortStatusDiv").css({"max-width":$(".main-contain").width()-300+"px","margin":"0"});
		$(".shortStatusDiv div").css({"max-width":$(".main-contain").width()-390+"px","margin":"0"});
	}	
});
function getStatusList(){
	$("#checkedCount").html("(0)");
	$("#uncheckedCount").html("(0)");
	$("#deletedCount").html("(0)");
	$("#allCount").html("(0)");
	$.get('../rest/backadmin/bankFinancialProductInvest/statusList',function(r) {
		if(r.status =='SUCCESS') {
			var totalCount=0;
			if(r.totalResultCount > 0){
				$.each(r.data,function(i,v){
					totalCount += v.count;
					$("#"+v.status+"Count").html("("+v.count+")");
				})
				$("#allCount").html("("+totalCount+")");
			}
		} else {
			layer.msg(r.message, {
				time: 2000 
			})
		}
	});
	getStageList();
}
function getStageList(){
	var status = $(".statusLight").attr("id");
	$("#stageCount").html("(0)");
	$("#unstartCount").html("(0)");
	$("#collectCount").html("(0)");
	$("#uninvestCount").html("(0)");
	$("#finishedCount").html("(0)");
	$("#exceptionCount").html("(0)");
	if(status == "all" || status == "checked"){
		$.get('../rest/backadmin/bankFinancialProductInvest/stageList',function(r) {
			if(r.status =='SUCCESS') {
				$(".filter1").removeClass("hidden");
				var totalCount=0;
				if(r.totalResultCount > 0){
					$.each(r.data,function(i,v){
						totalCount += v.count;
						$("#"+v.status+"Count").html("("+v.count+")");
					})
				}
				$("#stageCount").html("("+totalCount+")");
			} else {
				layer.msg(r.message, {
					time: 2000 
				})
			}
		});
	}else{
		$(".filter1").addClass("hidden");
	}
}

function getList(){
	var name = $("#search").val().replace(/(^\s*)|(\s*$)/g, "");
	var page = (typeof pageNum == 'undefined') ? 1 : pageNum;
	var status = $(".statusLight").attr("id");
	var stage = $(".statusLight1").attr("id");
	if(stage != 'all'){
		status = 'checked';
	}
	var str='';
	str+='&status='+status;
	str+='&stage='+stage;
	
	$.get('../rest/backadmin/bankFinancialProductInvest/list?pageNum='+page+'&pageSize=10&name='+name+str,function(r) {
		if(r.status =='SUCCESS') {
			r.totalPageCount && $('.quepager').html('<span style="font-weight:normal">'+ r.pageNum +'</span>/'+ r.totalPageCount);
			if(r.totalResultCount > 0){
				var template = $.templates('#queboxTpl');
				var html = template.render(r.data);
				$('#queboxCnt').html(html);
			}else{
				var html = '<td colspan=7>没有数据！<td>'
				$('#queboxCnt').html(html);
			}
		} else {
			layer.msg(r.message, {
				time: 2000 
			})
		}
	}).done(function(r){
		$(".btn-edit").colorbox({
			iframe : true,
			width : "400px",
			height : "300px",
			opacity : '0.5',
			overlayClose : false,
			escKey : true
		});
		$(".btn-redeem").colorbox({
			iframe : true,
			width : "400px",
			height : "720px",
			opacity : '0.5',
			overlayClose : false,
			escKey : true
		});
		if(flag){
			$('#pageTool').Paging({pagesize:r.pageSize,count:r.totalResultCount,callback:function(page,size,count){			
				pageNum = page;
				getList();
				flag=false;
				document.body.scrollTop = document.documentElement.scrollTop = 0;
			},render:function(ops){
				
			}});
			$("#pageTool").find(".ui-paging-container:last").siblings().remove();
		};
	})
}