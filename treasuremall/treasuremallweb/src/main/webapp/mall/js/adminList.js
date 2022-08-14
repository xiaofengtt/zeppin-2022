var pageNum = 1,
	pageSize = 50;
var flagSubmit = true;

$(function(){
	getList();
	$(".addAdmin").click(function(){
		layer.open({
		  type: 2, 
		  title:false,
		  area: ['500px', '400px'],
		  content:['adminDetail.html']
		}); 
	});
});

//获取列表
function getList(){
	$.ajax({
        url: '../back/admin/list',
        type: 'get',
        async:false,
        data: {
        	"pageSize": pageSize,
        	"pageNum": pageNum
        }
    }).done(function(r) {
		if (r.status == "SUCCESS") {
			var tableHtml = '<tr>'+
							'<th class="text-center" width="20%">账号</th>'+
							'<th class="text-center" width="20%">真实名称</th>'+
							'<th class="text-center" width="20%">角色</th>'+
							'<th class="text-center" width="20%">状态</th>'+
							'<th class="text-center" width="20%">操作</th>'+
						'</tr>';
			for(var i=0;i<r.data.length;i++){
				var status = "";//状态
				var role = "";
				var classColor = "";
				if(r.data[i].status=="normal"){
					status = "正常";
					classColor = "color-blue"
				}else if(r.data[i].status=="disable"){
					status = "停用";
					classColor = "color-red"
				}
				if(r.data[i].role=="002170ff-082d-412f-a8a4-b021183fa365"){
					role = "管理员";
				}else if(r.data[i].role=="502170ff-082d-412f-a8a4-b021183fa316"){
					role = "运营经理";
				}else if(r.data[i].role=="402170ff-082d-412f-a8a4-b021183fa317"){
					role = "运营专员";
				}else if(r.data[i].role=="302170ff-082d-412f-a8a4-b021183fa318"){
					role = "财务经理";
				}else if(r.data[i].role=="202170ff-082d-412f-a8a4-b021183fa319"){
					role = "财务专员";
				}else if(r.data[i].role=="102170ff-082d-412f-a8a4-b021183fa330"){
					role = "技术支持";
				}else if(r.data[i].role=="602170ff-082d-412f-a8a4-b021183fa342"){
					role = "BOSS";
				}
				
				var categoryHtml = '<a class="resetPwdBtn mr-5">重置密码</a><a class="editBtn mr-5">修改</a><a class="deleteBtn">删除</a>';
				tableHtml += '<tr>'
				+'<td class="text-center"><b>'+r.data[i].username+'</b></td>'
				+'<td class="text-center">'+r.data[i].realname+'</td>'
				+'<td class="text-center">'+role+'</td>'
				+'<td class="text-center '+classColor+'">'+status+'</td>'
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
				tableHtml += '<tr><td colspan="5" align="center">暂无相关数据</td></tr>';
			}
			$(".table-list").html(tableHtml);
			$(".editBtn").click(function(){
				layer.open({
				  type: 2, 
				  title:false,
				  area: ['500px', '400px'],
				  content:['adminDetail.html?uuid='+$(this).parent().attr("data-id"),'no'],
				  end:function() {
				        location.reload();
			      }
				}); 
				return false;
			});
			$(".deleteBtn").click(function(){
				deleteList($(this).parent().attr("data-id"));
				return false;
			});
			$(".resetPwdBtn").click(function(){
				layer.open({
				  type: 2, 
				  title:false,
				  area: ['500px', '300px'],
				  content:['adminResetPwd.html?uuid='+$(this).parent().attr("data-id")]
				}); 
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
	        url: '../back/admin/delete',
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
layui.use(['form', 'element'], function(){
	var form = layui.form
	,layer = layui.layer
	,$ = layui.jquery
	,element = layui.element;
})