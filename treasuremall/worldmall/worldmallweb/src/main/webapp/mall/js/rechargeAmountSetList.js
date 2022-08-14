var pageNum = (url('?pagenum') != null) ? url('?pagenum') : 1,
	pageSize = (url('?pagesize') != null) ? url('?pagesize') : 50;
var flagSubmit = true;

$(function(){
	getList();
	$(".addSet").click(function(){
		layer.open({
		  type: 2, 
		  title:false,
		  area: ['500px', '400px'],
		  content:['rechargeAmountSetDetail.html?pagesize='+pageSize+'&pagenum='+pageNum]
		}); 
	});
});

//获取列表
function getList(){
	$.ajax({
        url: '../back/amountSet/list',
        type: 'get',
        async:false,
        data: {
        	"pageSize": pageSize,
        	"pageNum": pageNum
        }
    }).done(function(r) {
		if (r.status == "SUCCESS") {
			var tableHtml = '<tr>'+
							'<th class="text-center" width="8%">序号</th>'+
							'<th class="text-center" width="13%">充值金额</th>'+
							'<th class="text-center" width="13%">到账金币</th>'+
							'<th class="text-center" width="13%">是否优惠</th>'+
							'<th class="text-center" width="13%">优惠金币</th>'+
							'<th class="text-center" width="13%">排序</th>'+
							'<th class="text-center" width="13%">状态</th>'+
							'<th class="text-center" width="13%">操作</th>'+
						'</tr>';
			for(var i=0;i<r.data.length;i++){
				var status = "";//状态
				var role = "";
				if(r.data[i].status=="normal"){
					status = "正常";
				}else if(r.data[i].status=="disable"){
					status = "停用";
				}
				var statusBtn = "";
				if(r.data[i].status!="normal"){
					statusBtn = '<label class="switch" data-id="'+r.data[i].uuid
					+'"><input type="checkbox"><div class="slider round"></div></label>'
				}else {
					statusBtn = '<label class="switch" data-id="'+r.data[i].uuid
					+'"><input type="checkbox" checked><div class="slider round"></div></label>'
				}
				
				var isPreferential = r.data[i].isPreferential ? "是":"否";
				var categoryHtml = '<a class="editBtn mr-5">修改</a><a class="deleteBtn">删除</a>';
				tableHtml += '<tr>'
				+'<td class="text-center">'+(pageSize*(pageNum-1)+i+1)+'</td>'
				+'<td class="text-center">'+r.data[i].amount+'</td>'
				+'<td class="text-center"><b>'+r.data[i].dAmount+'</b></td>'
				+'<td class="text-center">'+isPreferential+'</td>'
				+'<td class="text-center"><b>'+r.data[i].giveDAmount+'</b></td>'
				+'<td class="text-center">'+r.data[i].sort+'</td>'
				+'<td class="text-center">'+statusBtn+'</td>'
				+'<td class="text-center" data-id="'+r.data[i].uuid+'">'+categoryHtml+'</td>'
				+'</tr>';
			}
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
                    getList(parentid);
                    flag = false;
                    document.body.scrollTop = document.documentElement.scrollTop = 0;
                }
            });
            $(".ui-paging-container .ui-paging-toolbar").append("<b class='paging-total'>共"+r.totalResultCount+"条</b>")
            $("#pageTool").show().find(".ui-paging-container:last").siblings().remove();
            
            $(".ui-select-pagesize").unbind("change").change(function() {
                pageSize = $(this).val();
                pageNum = 1;
                getList(parentid);
            });
            if(r.data.length==0){
				tableHtml += '<tr><td colspan="8" align="center">暂无相关数据</td></tr>';
			}
			$(".table-list").html(tableHtml);
			$(".editBtn").click(function(){
				layer.open({
				  type: 2, 
				  title:false,
				  area: ['500px', '400px'],
				  content:['rechargeAmountSetDetail.html?uuid='+$(this).parent().attr("data-id")+'&pagesize='+pageSize+'&pagenum='+pageNum,'no']
				}); 
				return false;
			});
			$(".deleteBtn").click(function(){
				deleteList($(this).parent().attr("data-id"));
				return false;
			});	
			$(".switch").click(function(){
				if(flagSubmit){
					if($(this).find("input").prop("checked")){
						if(changeStatus($(this).attr("data-id"),"disable")){
							$(this).find("input").prop("checked",false);
						}	
					}else{
						if(changeStatus($(this).attr("data-id"),"normal")){
							$(this).find("input").prop("checked",true);
						}
					}
					flagSubmit = false;
					setTimeout(function() {
						flagSubmit = true;
					}, 3000);
				}
				
				return false;
			})
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
        	
        });
    });   	
}

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
        url: '../back/amountSet/delete',
        type: 'post',
        async:false,
        data: {
        	'uuid':uuid
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

//设置包尾
function changeStatus(uuid,status){
	$.ajax({
        url: '../back/amountSet/changeStatus',
        type: 'post',
        async:false,
        data: {
        	'uuid':uuid,
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
layui.use(['form', 'element'], function(){
	var form = layui.form
	,layer = layui.layer
	,$ = layui.jquery
	,element = layui.element;
})