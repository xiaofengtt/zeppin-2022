var pageNum = (url('?pagenum') != null) ? url('?pagenum') : 1,
    pageSize = (url('?pagesize') != null) ? url('?pagesize') : 50;
var flagSubmit = true;
var statusArr = {
	'true':'开启',
	'false':'关闭'
}
$(function(){
	countryList();
	versionList();
	getList();
	$(".addFund").click(function(){
		layer.open({
		  type: 2, 
		  title:false,
		  area: ['500px', '450px'],
		  content:['fundDetail.html?pagesize='+pageSize+'&pagenum='+pageNum,'no']
		}); 
	});
	$(".changeStatusStart").click(function(){
		changeStaus('normal');
	});
	$(".changeStatusStop").click(function(){
		changeStaus('disable');
	});
})

// 获取国家列表
function countryList(){
	$.ajax({
	    url: '../back/country/list',
	    type: 'get',
	    async:false,
	    data:{
			'status':'normal',
	    	'pageSize':'10000',
	    	'pageNum':'1'
	    }
	}).done(function(r) {
			if (r.status == "SUCCESS") {
				var html = '<option value="">全部</option>';
				for(var i = 0;i<r.data.length;i++){
					html += '<option value="'+r.data[i].uuid+'">'+ r.data[i].name+'</option>';
				}
				$("select[name='country']").html(html);
			} else {
				
			}
	}).fail(function(r) {
	    
	}); 
}
// 获取渠道版本列表
function versionList(){
	$.ajax({
	    url: '../back/version/list',
	    type: 'get',
	    async:false,
	    data:{
			'status':'normal',
	    	'pageSize':'10000',
	    	'pageNum':'1'
	    }
	}).done(function(r) {
			if (r.status == "SUCCESS") {
				var html = '<option value="">全部</option>';
				for(var i = 0;i<r.data.length;i++){
					html += '<option value="'+r.data[i].uuid+'">'+r.data[i].channel+'-'+r.data[i].name+'</option>';
				}
				$("select[name='version']").html(html);
			} else {
				
			}
	}).fail(function(r) {
	    
	}); 
}
// 提现控制列表
function getList(){
	$.ajax({
	        url: '../back/control/list',
	        type: 'get',
	        async:false,
	        data: {
	        	'pageSize':pageSize,
	        	'pageNum':pageNum,
	        	'internationalInfo':$("select[name='country']").val(),
	        	'version':$("select[name='version']").val()
	        }
	    }).done(function(r) {
	    		if (r.status == "SUCCESS") {
	    			var tableHtml = '<tr>'+
									'<th width="5%;" class="text-center"><input type="checkbox" id="allCheck" name="" value=""></th>'+
									'<th width="5%" class="text-center">序号</th>'+
									'<th width="15%" class="text-center">国家</th>'+
									'<th width="15%" class="text-center">渠道</th>'+
									'<th width="15%" class="text-center">版本号</th>'+
									'<th width="15%" class="text-center">开关状态</th>'+
									'<th width="15%" class="text-center">操作</th>'+
								'</tr>';
					for(var i=0;i<r.data.length;i++){
						var status = "";//状态
						var source = "";//来源
						var opteration = "";//操作按钮
						var img = "";//商品列表图
						var categoryHtml = '<a class="editBtn mr-5">编辑</a><a class="deleteBtn">删除</a>';//分类
						tableHtml += '<tr data-url="goodsAdd.html?uuid='+r.data[i].uuid+
						'"><td class="text-center"><input class="check" type="checkbox" name="" value="'+r.data[i].uuid+
	    				'"></td><td class="text-center">'+(pageSize*(pageNum-1)+i+1)+
						'</td><td class="text-center">'+r.data[i].internationalInfoName+
						'</td><td class="text-center">'+r.data[i].channel+
						'</td><td class="text-center">'+r.data[i].versionName+
						'</td><td class="text-center">'+statusArr[r.data[i].flagWithdraw]+
						'</td><td class="text-center" data-id="'+r.data[i].uuid+
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
					allCheck();
					$(".deleteBtn").click(function(){
						deleteList($(this).parent().attr("data-id"));
						return false;
					});				
					$(".editBtn").click(function(){
						layer.open({
						  type: 2, 
						  title:false,
						  area: ['500px', '450px'],
						  content:['fundDetail.html?pagesize='+pageSize+'&pagenum='+pageNum+'&uuid='+$(this).parent().attr("data-id"),'no']
						});
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
layui.use(['form'], function(){
	  var form = layui.form 
});
//删除
function deleteList(uuid){
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
        url: '../back/control/changeStatus',
        type: 'post',
        async:false,
        data: {
        	'uuids':uuid,
			'status':'delete'
        }
    }).done(function(r) {
    		if (r.status == "SUCCESS") {
    			layer.msg("删除成功", {
		            time: 2000
		        },function(){
		        	getList();	
		        });
    		}else {
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
    })
	}, function(){
		layer.closeAll();
	});
}
//批量启用停用
function changeStaus(status){
	var uuid = "";
	$(".check.light").each(function(){
		uuid += $(this).val() + ",";
	});
	if(uuid==""){
		layer.msg("请选择要批量操作的数据");
		return false;
	}else{
		uuid = uuid.substring(0,uuid.length-1);
	}
	$.ajax({
        url: '../back/control/changeStatus',
        type: 'post',
        async:false,
        data: {
        	'uuids':uuid,
        	'status':status
        }
    }).done(function(r) {
    		if (r.status == "SUCCESS") {
    			layer.msg("设置成功", {
		            time: 2000
		        },function(){
		        	getList();
		        });
    			return true;
    		}else {
    			if(r.errorCode=="302"){
    				layer.msg(r.message, {
    		            time: 2000
    		        },function(){
    		        	window.location.href="login.html";
    		        });
    			}else{
    				layer.msg(r.message);
    			}
    			return false;
    		}
    })
}