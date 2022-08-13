var pageNum = '1';
var flag=true;
var order = 'desc';
function init(){
	pageNum = '1';
	flag=true;
}
function filterControl(t){
	if($('#moreFilter').hasClass('hide')){
		$('#moreFilter').removeClass('hide');
		$('#filterController').html('收起');
	}else{
		$('#moreFilter').addClass('hide');
		$('#filterController').html('展开');
	}
	init();
	getList();
}
function checkThis(t) {
	layer.confirm('确定要变更状态吗?', function(index){
		var obj = $(t);
		var uuid = obj.attr('data-uuid');
		var status = obj.attr('data-status');
		if(status=='deleted'){
			$.get('../rest/backadmin/bankFinancialProductPublish/delete?uuid='+uuid, function(r) {
				if (r.status == 'SUCCESS') {
					layer.msg('操作成功', {
						time: 1000 
					}, function(){
						init();
						getStageList();
						getList();
					});
				} else {
					alert('操作失败,' + r.message);
				}
			})
		}else if(status=='exception'){
			$.get('../rest/backadmin/bankFinancialProductPublish/exception?uuid='+uuid, function(r) {
				if (r.status == 'SUCCESS') {
					layer.msg('操作成功', {
						time: 1000 
					}, function(){
						init();
						getStageList();
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

$(".filter1 a").click(function(){
	$(this).addClass("statusLight1").siblings().removeClass("statusLight1");
	init();
	getList();
});

$(".sort .sorting-btns li").click(function(){
	$(this).addClass("light").siblings().removeClass("light");
	$(this).removeClass("asc").removeClass("desc");
	order = order == 'desc'?'asc':'desc';
	$(this).addClass(order).siblings().removeClass("asc").removeClass("desc");
	getList();
});

$(document).ready(function() {
	getBankList();
	getStageList();
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
function getBankList(){
	$.get('../rest/backadmin/bank/list?pageNum=1&pageSize=1000',function(r) {
		if(r.status =='SUCCESS') {
			var htmls = '';
			for(var i=0; i<r.totalResultCount; i++){
				htmls+= '<a id="'+r.data[i].uuid+'">'+r.data[i].name+'</a>';
			}
			$('#custodians').append(htmls);
			$(".filter a").click(function(){
				$(this).addClass("statusLight").siblings().removeClass("statusLight");
				init();
				getList();
			});
		} else {
			layer.msg(r.message, {
				time: 2000 
			})
		}
	})
}
function getStageList(){
	var status = 'checked';
	$("#stageCount").html("(0)");
	$("#unstartCount").html("(0)");
	$("#collectCount").html("(0)");
	$("#uninvestCount").html("(0)");
	$("#finishedCount").html("(0)");
	$("#exceptionCount").html("(0)");
	$.get('../rest/backadmin/bankFinancialProductPublish/stageList',function(r) {
		if(r.status =='SUCCESS') {
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
}

function getList(){
	var name = $("#search").val().replace(/(^\s*)|(\s*$)/g, "");
	var page = (typeof pageNum == 'undefined') ? 1 : pageNum;
	var status = 'checked';
	var stage = $(".statusLight1").attr("id");
	var sort = $(".light a").attr("data-name")+"-"+order;
	if(stage != 'all'){
		status = 'checked';
	}
	var str='';
	str+='&status='+status;
	str+='&stage='+stage;
	if(!$('#moreFilter').hasClass('hide')){
		var term = $(".filter-term").find(".statusLight").attr("id");
		var type = $(".filter-type").find(".statusLight").attr("id");
		var riskLevel = $(".filter-riskLevel").find(".statusLight").attr("id");
		var custodian = $(".filter-custodian").find(".statusLight").attr("id");
		var income = $(".filter-income").find(".statusLight").attr("id");
		var redeem = $(".filter-redeem").find(".statusLight").attr("id");
		str+='&term='+term;
		str+='&type='+type;
		str+='&riskLevel='+riskLevel;
		str+='&custodian='+custodian;
		str+='&income='+income;
		str+='&redeem='+redeem;
	}
	str+='&sorts='+sort
	
	$.get('../rest/backadmin/bankFinancialProductPublish/list?pageNum='+page+'&pageSize=10&name='+name+str,function(r) {
		if(r.status =='SUCCESS') {
			r.totalPageCount && $('.quepager').html('<span style="font-weight:normal">'+ r.pageNum +'</span>/'+ r.totalPageCount);
			if(r.totalResultCount > 0){
				var template = $.templates('#queboxTpl');
				var html = template.render(r.data);
				$('#queboxCnt').html(html);
			}else{
				var html = '<div class="nodata">没有数据！</div>'
				$('#queboxCnt').html(html);
			}
		} else {
			layer.msg(r.message, {
				time: 2000 
			})
		}
	}).done(function(r){
		$(".btn-add").colorbox({
			iframe : true,
			width : "400px",
			height : "300px",
			opacity : '0.5',
			overlayClose : false,
			escKey : true
		});
		$(".btn-publish").colorbox({
			iframe : true,
			width : "1212px",
			height : "920px",
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