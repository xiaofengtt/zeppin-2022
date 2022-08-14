/**
 * 汇率管理详情
 */
var status = (url('?status') != null) ? url('?status') : 'disable',
	date = (url('?date') != null) ? url('?date') : '',
	uuid = (url('?uuid') != null) ? url('?uuid') : '';

$(function(){
	$(".exchangeRateDate").html(date)
	if(status=='normal'){
		$(".exchangeRateOpe").html('确认')
	}else{
		$(".exchangeRateOpe").html('查看')
		$(".lay-submit").hide()
	}
	dataList();
	$(".lay-cancle").click(function(){
		var index = parent.layer.getFrameIndex(window.name);
		window.parent.getList();
		parent.layer.close(index);
		window.localStorage.removeItem("exchangeRate")
		return false;
	})
})
//获取数据
function dataList(){
	var data = JSON.parse(window.localStorage.getItem("exchangeRate"))
	var tableHtml = ''
	for(var i=0;i<data.length;i++){
		var className = '',realRateHtml = '';
		if(data[i].baseRate!=data[i].realRate){
			className = 'color-red'
		}
		if(status=='normal'){
			realRateHtml = '<input class="layui-input" onkeyup="handleNum(this)" value="'
				+data[i].realRate+'" type="text" />'
		}else{
			realRateHtml = data[i].realRate
		}
		tableHtml+='<tr data-data="'+encodeURIComponent(JSON.stringify(data[i]))
					+'"><td width="10%" class="text-center">'+data[i].currencyCode
					+'</td><td width="30%" class="text-center">'+data[i].currencyName
					+'</td><td width="10%" class="text-center">'+data[i].baseCurrency
					+'</td><td width="25%" class="text-center">'+data[i].baseRate
					+'</td><td width="25%" class="text-center '+className+'">'+realRateHtml
					+'</td></tr>';
	}
	if(data.length==0){
		tableHtml += '<tr><td colspan="5" align="center">暂无相关数据</td></tr>';
	}
	$(".tableBox .table-list").html(tableHtml);	
}
function handleNum(obj){
	obj.value = obj.value.replace(/[^\d.]/g,"");
	obj.value = obj.value.replace(/^\./g,"");
	obj.value = obj.value.replace(/\.{2,}/g,".");
	obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");
	obj.value = obj.value.replace(/^\D*(\d*(?:\.\d{0,8})?).*$/g, '$1')
}
// 确认
function handleSure(){
	var dataInfo = [];
	for(var m=0;m<$(".tableBox .table-list tr").length;m++){
		detailItem = {}
		if($(".tableBox .table-list tr:eq("+m+")").find("input").val()==""){
			layer.msg("有未填写确认汇率的选项");
			return false;
		}else{
			var dataItem = JSON.parse(decodeURIComponent($(".tableBox .table-list tr:eq("+m+")").attr('data-data')))
			detailItem['baseCurrency'] = dataItem.baseCurrency
			detailItem['baseRate'] = dataItem.baseRate
			detailItem['currencyCode'] = dataItem.currencyCode
			detailItem['currencyName'] = dataItem.currencyName
			detailItem['realRate'] = $(".tableBox .table-list tr:eq("+m+")").find("input").val()
			dataInfo.push(detailItem)
		}
	}
	$.ajax({
        url: '../back/exchangeRate/check',
        type: 'post',
        data: {
        	'uuid': uuid,
        	'dataInfo': JSON.stringify(dataInfo)
        }
    }).done(function(r) {
		if (r.status == "SUCCESS") {
			layer.msg('处理成功', {
				time: 1000 
			}, function(){
				var index = parent.layer.getFrameIndex(window.name);
				console.log(window.nam)
				window.parent.getList();
//    				parent.location.reload();
				parent.layer.close(index);
				window.localStorage.removeItem("exchangeRate")
			}); 
		} else {
			if(r.errorCode=="302"){
				layer.msg(r.message, {
		            time: 2000
		        },function(){
		        	window.location.href="login.html";
		        });
			}else{
				layer.msg(r.message);
			}
		}
    }).fail(function(r) {
        layer.msg("error", {
            time: 2000
        },function(){
        	window.location.href=window.location.href;
        });
    }); 
	return false;
}