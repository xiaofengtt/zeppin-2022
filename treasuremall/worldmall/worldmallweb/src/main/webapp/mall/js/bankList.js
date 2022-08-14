var pageNum = (url('?pagenum') != null) ? url('?pagenum') : 1,
	pageSize = (url('?pagesize') != null) ? url('?pagesize') : 50;
var flagSubmit = true;

$(function(){
	getList();
	$(".addBank").click(function(){
		layer.open({
		  type: 2, 
		  title:false,
		  area: ['500px', '400px'],
		  content:['bankDetail.html?pagesize='+pageSize+'&pagenum='+pageNum]
		}); 
	});
});

//获取列表
function getList(){
	$.ajax({
        url: '../back/bank/list',
        type: 'get',
        async:false,
        data: {
        	"pageSize": pageSize,
        	"pageNum": pageNum
        }
    }).done(function(r) {
		if (r.status == "SUCCESS") {
			var tableHtml = '<tr>'+
							'<th class="text-center" width="20%">图标</th>'+
							'<th class="text-center" width="20%">名称</th>'+
							'<th class="text-center" width="20%">简称</th>'+
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
				var categoryHtml = '<a class="editBtn">修改</a>';
				tableHtml += '<tr>'
				+'<td class="text-center"><img src="..'+r.data[i].logoUrl+'" class="touxiang" /></td>'
				+'<td class="text-center"><b>'+r.data[i].name+'</b></td>'
				+'<td class="text-center">'+r.data[i].shortName+'</td>'
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
				  content:['bankDetail.html?uuid='+$(this).parent().attr("data-id")+'&pagesize='+pageSize+'&pagenum='+pageNum]
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