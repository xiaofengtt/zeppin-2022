/**
 * 管理员管理
 */
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
        url: '../system/admin/list',
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
				if(r.data[i].status=="normal"){
					status = "正常";
				}else if(r.data[i].status=="disable"){
					status = "停用";
				}
				if(r.data[i].role=="002170ff-082d-412f-a8a4-b021183fa365"){
					role = "管理员";
				}else if(r.data[i].role=="002170ff-082d-412f-a8a4-b021183fa332"){
					role = "审核人";
				}else if(r.data[i].role=="002170ff-082d-412f-a8a4-b021183fa311"){
					role = "编辑";
				}else if(r.data[i].role=="002170ff-082d-412f-a8a4-b021183fa342"){
					role = "运营";
				}
				
				var processHtml = '<a class="editBtn mr-5">修改</a>';
				tableHtml += '<tr>'
				+'<td class="text-center"><b>'+r.data[i].username+'</b></td>'
				+'<td class="text-center">'+r.data[i].realname+'</td>'
				+'<td class="text-center">'+role+'</td>'
				+'<td class="text-center">'+status+'</td>'
				+'<td class="text-center" data-id="'+r.data[i].uuid+'">'+processHtml+'</td>'
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