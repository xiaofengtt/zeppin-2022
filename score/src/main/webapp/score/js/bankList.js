var pageNum = 1,
    pageSize = 20;
var flagSubmit = true;

$(function(){
	getList();
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
    							'<th width="15%">图标</th>'+
								'<th width="25%">名称</th>'+
								'<th width="25%">简称</th>'+
								'<th width="15%">状态</th>'+
								'<th width="20%" class="text-right">操作</th>'+
							'</tr>';
    			for(var i=0;i<r.data.length;i++){
    				var status = "";//状态
    				if(r.data[i].status=="normal"){
    					status = "正常";
    				}else if(r.data[i].status=="disable"){
    					status = "停用";
    				}
    				tableHtml += '<tr>'
    				+'<td class="table-Logo"><img src="'+(r.data[i].logoUrl?".."+r.data[i].logoUrl:"img/default-logo.png")+'" /></td>'
    				+'<td>'+r.data[i].name+'</td>'
    				+'<td>'+r.data[i].shortName+'</td>'
    				+'<td>'+status+'</td>'
    				+'<td class="text-right" data-id="'+r.data[i].uuid+'"><a class="" href="bankDetail.html?uuid='+r.data[i].uuid+'">修改</a></td>'
    				+'</tr>';
    			}
                $('#pageTool').Paging({
                    prevTpl: "<",
                    nextTpl: ">",
                    pagesize: pageSize,
                    count: r.totalResultCount,
                    current: pageNum,
                    toolbar: true,
                    pageSizeList: [10, 20, 50],
                    callback: function(page, size, count) {
                        pageNum = page;
                        getList();
                        flag = false;
                        document.body.scrollTop = document.documentElement.scrollTop = 0;
                    }
                });
                $("#pageTool").show().find(".ui-paging-container:last").siblings().remove();
                
                $(".ui-select-pagesize").unbind("change").change(function() {
                    pageSize = $(this).val();
                    pageNum = 1;
                    getList();
                });
                if(r.data.length==0){
    				tableHtml += '<tr><td colspan="7" align="center">暂无相关数据</td></tr>';
    			}
    			$(".table-list").html(tableHtml);
    			allCheck();
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
