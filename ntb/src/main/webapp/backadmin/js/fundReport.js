var pageNum = '1';
var pagesize;
var count;
var flag=true;
var tArraynetValue=[];
var tArraytime=[];

function deleteThis(t) {
	layer.confirm('确定要删除该条记录吗?', function(index){
		var obj = $(t);
		var uuid = obj.attr('data-uuid');
		$.get('../rest/backadmin/fund/netvalueDelete?uuid='+uuid, function(r) {
			if (r.status == 'SUCCESS') {
				layer.msg('操作成功', {
					time: 1000 
				}, function(){
					getList();
				});
			} else {
				alert('删除失败,' + r.message);
			}
		})
		 layer.close(index);
	});
	return false;
}
var uuid = (url('?uuid') != null) ? url('?uuid') : '';
function getList(){
	var page = (typeof pageNum == 'undefined') ? 1 : pageNum;	
	var bankFinancialProduct = '';
	$.get('../rest/backadmin/fund/get?uuid='+uuid,function(r){
		if(r.status =='SUCCESS') {
			$('#fundName').html(r.data.name);
		}
	})
	$.get('../rest/backadmin/fund/netvaluelist?pageNum='+page+'&pageSize=10&uuid='+uuid+'&deadline=all',function(r) {
		if(r.status =='SUCCESS') {
			r.totalPageCount && $('.quepager').html('<span style="font-weight:normal">'+ r.pageNum +'</span>/'+ r.totalPageCount);
			if(r.totalResultCount > 0){
				bankFinancialProduct = r.data[0].bankFinancialProduct;
				var template = $.templates('#queboxTpl');
				var html = template.render(r.data);
				$('#queboxCnt').html(html);
				pagesize=r.pageSize;
				count=r.totalResultCount;
			}else{
				bankFinancialProduct = r.message;
				var html = '<tr class="nodata"><td colspan=5>没有数据！</td></tr>'
				$('#queboxCnt').html(html);
			}
			$('#edit').html(bankFinancialProduct);
			$("#edit").attr('href','fundEdit.jsp?uuid='+uuid);
			$(".addNew").attr('href','fundDailyAdd.jsp?uuid='+uuid);
			$(".editDaily").colorbox({
				iframe : true,
				width : "400px",
				height : "420px",
				opacity : '0.5',
				overlayClose : false,
				escKey : true
			});
		} else {
			layer.msg(r.message, {
				time: 2000 
			})
		}
	}).done(function(r){
		if(flag){
			$('#pageTool').Paging({pagesize:pagesize,count:count,callback:function(page,size,count){			
				pageNum = page;
				getList();
				flag=false;
				document.body.scrollTop = document.documentElement.scrollTop = 0;
			},render:function(ops){
				
			}});
			$("#pageTool").find(".ui-paging-container:last").siblings().remove();
		}
	})
}

$(function () {
	getList();
	getEchart();
	$("#duration").change(function(t){
		$("#chart-title").html($("#duration").find("option:selected").text());
	})
	
	$(".addNew").colorbox({
		iframe : true,
		width : "400px",
		height : "420px",
		opacity : '0.5',
		overlayClose : false,
		escKey : true
	});
	
    
});

//折线图
function getEchart(){
	$.get('../rest/backadmin/fund/netvaluelist?pageNum=1&pageSize=100000000&uuid='+uuid+'&deadline=all',function(r) {
		if(r.status =='SUCCESS') {
			if(r.totalResultCount > 0){
				for(var i=0;i<r.data.length;i++){
					tArraynetValue[i]=r.data[i].netValue;
					tArraytime[i]=r.data[i].statistime;
				}
			}else{
				
			}			
		} else {
			
		}
	}).done(function(){
		$('#container').highcharts({
        title: {
            text: ''
        },
        xAxis: {
            categories: tArraytime
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
            valueSuffix: '万元'
        },
        legend: {
            layout: 'vertical',
            align: 'right',
            verticalAlign: 'middle',
            borderWidth: 0,
            enabled: false
        },
        series: [{
            name: '净值',
            data: tArraynetValue
        }]
    });
	});
}