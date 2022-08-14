var pageNum = (url('?pagenum') != null) ? url('?pagenum') : 1,
	pageSize = (url('?pagesize') != null) ? url('?pagesize') : 50;
var status = '';//数据状态
var flagSubmit = true;
var bannerType = {
	'type_start':'启动页广告',
	'type_top':'首页顶部广告',
	'type_eject':'弹屏广告',
	'type_login':'登录页广告',
	'type_payment':'支付页面广告'
}
var statusType = {
	'normal':'启用',
	'disable':'停用'
}
$(function(){
	$(".bannerType").html("广告位资源管理-"+bannerType[url('?type')])
	getList();	
	$(".addBanner").click(function(){
		window.location.href='bannerDetail.html?type='+url('?type')+'&pagesize='+pageSize+'&pagenum='+pageNum
	});
});
function getList(){
	$.ajax({
        url: '../back/banner/list',
        type: 'get',
        async:false,
        data: {
        	'pageSize':pageSize,
        	'pageNum':pageNum,
        	'type':url('?type'),
        	'status':status,
        	'sorts':'sort desc'
        }
    }).done(function(r) {
    		if (r.status == "SUCCESS") {
    			var tableHtml = '<tr>'+
					'<th width="15%" class="text-center">banner中文名称</th>'+
					'<th width="15%" class="text-center">banner英文名称</th>'+
					'<th width="15%" class="text-center">跳转地址</th>'+
					'<th width="15%" class="text-center">图片</th>'+
					'<th width="15%" class="text-center">有效期</th>'+
					'<th width="10%" class="text-center">状态</th>'+
					'<th class="text-center">操作</th>'+
				'</tr>';
				for(var i=0;i<r.data.length;i++){
					var status = "";//状态
					var source = "";//来源
					var opteration = "";//操作按钮
					var categoryHtml = '<a class="editBtn mr-5">编辑</a><a class="topBtn mr-5">置顶</a><a class="deleteBtn">删除</a>';//分类
					var classColor = "";
					if(r.data[i].status=="normal"){
						classColor = "color-blue"
					}else if(r.data[i].status=="disable"){
						classColor = "color-red"
					}
					tableHtml += '<tr data-url="bannerDetail.html?uuid='+r.data[i].uuid+
					'&type='+url('?type')+'&pagesize='+pageSize+'&pagenum='+pageNum+'"><td class="text-center">'+r.data[i].title+'</td><td class="text-center">'+r.data[i].code+
					'</td><td class="text-center">'+r.data[i].url+'</td><td class="text-center"><img src="..'+r.data[i].imageUrl
					+'"/></td><td class="text-center">'+formatDate(r.data[i].starttime)+'-'+formatDate(r.data[i].endtime)+'</td><td class="text-center '+classColor+'">'
					+statusType[r.data[i].status]+'</td><td class="text-center" data-id="'+r.data[i].uuid+
					'">'+categoryHtml+'</td></tr>'; 
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
					tableHtml += '<tr><td colspan="7" align="center">暂无相关数据</td></tr>';
				}
				$("table").html(tableHtml);	
				$(".deleteBtn").click(function(){
					deleteList($(this).parent().attr("data-id"));
					return false;
				});
				$(".topBtn").click(function(){
					isTop($(this).parent().attr("data-id"));
					return false;
				});
				$(".table-list tr").click(function(){
					var dataUrl = $(this).attr("data-url");
					if(dataUrl){
						window.location.href = dataUrl;
					}
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
function deleteList(obj){
	layer.confirm('确定要删除吗？', {
		btn: ['确定','取消'] //按钮
	}, function(){		
		if(flagSubmit == false) {
			return false;
		}
		flagSubmit = false;
		setTimeout(function() {
			flagSubmit = true;
		}, 3000);
		$.ajax({
	        url: '../back/banner/delete',
	        type: 'get',
	        async:false,
	        data: {
	        	"uuid": obj
	        }
	    }).done(function(r) {
	    		if (r.status == "SUCCESS") {
	    			layer.msg("删除成功", {
			            time: 2000
			        },function(){
			        	getList();
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
	}, function(){
		layer.closeAll();
	});

}
//置顶
function isTop(obj){
	$.ajax({
        url: '../back/banner/top',
        type: 'get',
        async:false,
        data: {
        	"uuid": obj
        }
    }).done(function(r) {
    		if (r.status == "SUCCESS") {
    			layer.msg("置顶成功", {
		            time: 2000
		        },function(){
		        	getList();
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
layui.use('form', function(){
	var form = layui.form;
	form.on('select(quiz)',function(data){
		status = data.value;
		pageNum=1;
		getList();
	})

});