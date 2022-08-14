/**
 * 商品管理
 */
var pageNum = 1,
    pageSize = 50;
var classname = (url('?classname') != null) ? url('?classname') : '';
var tab = url('?tab');
var type = url('?type');
var flagSubmit = true;
var typeObj = {
	'cashcoupon':'现金卡券',
	'goldjewelry':'黄金珠宝',
	'brandmobile':'手机数码',
	'equipment':'家用电器',
	'makeups':'美妆大牌',
	'necessities':'生活用品',
	'foodstuff':'食品饮料'
}
$(function(){
	getList();	
});
function getList(){
	$.ajax({
        url: '../back/voucher/list',
        type: 'get',
        async:false,
        data: {
        	'pageSize':pageSize,
        	'pageNum':pageNum
        }
    }).done(function(r) {
    		if (r.status == "SUCCESS") {
    			var tableHtml = '<tr>'+
    			'<th width="8%" class="text-center">序号</th>'+
				'<th width="12%" class="text-center">金券名称</th>'+
				'<th width="8%" class="text-center">金券面值</th>'+
				'<th width="10%" class="text-center">使用最低限额</th>'+
				'<th width="15%" class="text-center">限制描述</th>'+
				'<th width="10%" class="text-center">开始时间</th>'+
				'<th width="10%" class="text-center">结束时间</th>'+
				'<th width="10%" class="text-center">操作</th>'+
				'</tr>';
			for(var i=0;i<r.data.length;i++){
				var starttime='-', endtime='长期';
				if(r.data[i].starttime != null){
					starttime = r.data[i].starttime.length > 10 ? formatDate(r.data[i].starttime) : r.data[i].starttime;
				}
				if(r.data[i].endtime != null){
					endtime = r.data[i].endtime.length > 10 ? formatDate(r.data[i].endtime) : r.data[i].endtime;
				}
				var categoryHtml = '<a class="chooseBtn mr-5">选择</a>';//分类
				tableHtml += '<tr>'
					+'<td class="text-center">'+(pageSize*(pageNum-1)+i+1)+'</td>'
					+'<td class="text-center">'+r.data[i].title+'</td>'
					+'<td class="text-center">'+(r.data[i].dAmount).toFixed(2)+'</td>'
					+'<td class="text-center">'+(r.data[i].payMin).toFixed(2)+'</td>'
					+'<td class="text-center">'+r.data[i].discription+'</td>'
					+'<td class="text-center">'+starttime+'</td>'
					+'<td class="text-center">'+endtime+'</td>'
					+'<td class="text-center" data-id="'+encodeURI(JSON.stringify(r.data[i]))+
					'">'+categoryHtml+'</td>'
					+'</tr>'; 
			}
			if (r.totalPageCount!=0) {
			    $('#pageTool').Paging({
			        prevTpl: "<",
			        nextTpl: ">",
			        pagesize: pageSize,
			        count: r.totalResultCount,
			        current: pageNum,
			        toolbar: true,
			        pageSizeList: [50, 200, 1000],
			        callback: function(page, size, count) {
			            pageNum = page;
			            getList();
			            flag = false;
			            document.body.scrollTop = document.documentElement.scrollTop = 0;
			        }
			    });
			    $(".ui-paging-container .ui-paging-toolbar").append("<b class='paging-total'>共"+r.totalResultCount+"条</b>")
			    $("#pageTool").show().find(".ui-paging-container:last").siblings().remove();
			}else{
				$("#pageTool").hide();
			}
			$(".ui-select-pagesize").unbind("change").change(function() {
			    pageSize = $(this).val();
			    pageNum = 1;
			    getList();
			});
			if(r.data.length==0){
				tableHtml += '<tr><td colspan="8" align="center">暂无相关数据</td></tr>';
			}
			$("table").html(tableHtml);

				$(".chooseBtn").click(function(){
					var data = JSON.parse(decodeURI($(this).parent().attr("data-id")))
					window.localStorage.setItem("goodsId",data.uuid);
					var index = parent.layer.getFrameIndex(window.name); 
					if(classname!=''){
						window.localStorage.setItem("goodsName",data.title);
						window.parent.goodsItem(classname,tab,type);
					}else{
	    				parent.location.reload();
					}
    				parent.layer.close(index);
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
}
