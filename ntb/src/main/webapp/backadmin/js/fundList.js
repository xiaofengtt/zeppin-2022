var pageNum = '1';
var flag=true;
function init(){
	pageNum = '1';
	flag=true;
}

function checkThis(t) {
	layer.confirm('确定要变更审核状态吗?', function(index){
		var obj = $(t);
		var uuid = obj.attr('data-uuid');
		var status = obj.attr('data-status');
		$.get('../rest/backadmin/fund/check?uuid='+uuid+'&status='+status, function(r) {
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
		 layer.close(index);
	});
	return false;
}

function searchBtn(){
	init();
	getList();
	getStatusList();
	return false;
}

//$(".btn-add").colorbox({
//	iframe : true,
//	width : "1000px",
//	height : "750px",
//	opacity : '0.5',
//	overlayClose : false,
//	escKey : true
//});

$(".statusDiv a").click(function(){
	init();
	$(this).addClass("statusLight").siblings().removeClass("statusLight");
	getList();
});

$(document).ready(function() {
	getStatusList();
	getList();
})
function getStatusList(){
	$("#checkedCount").html("(0)");
	$("#uncheckedCount").html("(0)");
	$("#unpassedCount").html("(0)");
	$.get('../rest/backadmin/fund/statusList',function(r) {
		if(r.status =='SUCCESS') {
			if(r.totalResultCount > 0){
				$.each(r.data,function(i,v){
					$("#"+v.status+"Count").html("("+v.count+")");
				})
			}
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
	var status = $(".statusLight").attr("id");
	$.get('../rest/backadmin/fund/list?pageNum='+page+'&pageSize=10&name='+name+'&status='+status,function(r) {
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
		if(flag){
			$('#pageTool').Paging({pagesize:r.pageSize,count:r.totalResultCount,callback:function(page,size,count){			
				pageNum = page;
				getList();
				flag=false;
				document.body.scrollTop = document.documentElement.scrollTop = 0;
			},render:function(ops){
				
			}});
			$("#pageTool").find(".ui-paging-container:last").siblings().remove();
		}
	    $('#container').highcharts({
	        title: {
	            text: '',
	            x: -20 //center
	        },
	        subtitle: {
	            text: '',
	            x: -20
	        },
	        xAxis: {
	            categories: ['Jan', 'Feb', 'Mar']
	        },
	        yAxis: {
	            title: {
	                text: ''
	            },
	            plotLines: [{
	                value: 0,
	                width: 1,
	                color: '#808080'
	            }]
	        },
	        tooltip: {
	            valueSuffix: '%'
	        },
	        legend: {
	            layout: 'vertical',
	            align: 'right',
	            verticalAlign: 'middle',
	            borderWidth: 0,
	            enabled: false
	        },
	        series: [{
	            name: 'Tokyo',
	            data: [7.0, 6.9, 9.5],
	            itemStyle: {normal: {areaStyle: {color:'rgba(205,245,255,0.86',type: 'default'}}},//线条颜色和填充颜色
	        }]
	    });
	})
}