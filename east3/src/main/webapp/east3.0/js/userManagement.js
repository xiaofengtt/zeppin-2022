var pageSize = 20;
var pageNum = 1;
var resetId;
$(function(){
	getList();
});
//查询列表
function getList(){
	$.ajax({
	    url: '../rest/backadmin/opManage/list',
	    type: 'get',
	    data: {
	    	"name":$("input.userName").val(),
	    	"pageNum": pageNum,
	        "pageSize": pageSize,
            "dataproduct":$("select.form-product").val()
	    }
	}).done(function(r) {
		if(r.status=="SUCCESS"){
			var tableHtml="";
			if (r.totalResultCount != 0) {				
				for(var i=0;i<r.data.length;i++){
					tableHtml+="<tr><td>"+Number(pageSize*(pageNum-1)+Number(i+1))+
					"</td><td>"+r.data[i].code+"</td><td>"+r.data[i].name+"</td><td>"+(r.data[i].department?r.data[i].department:'')+
					"</td><td><a id='"+r.data[i].id+"' onclick='resetPassword(this);'>重置密码</a></td></tr>";					
				}
				$("#queboxCnt").html(tableHtml);				
				$('#pageTool').Paging({
	                prevTpl: "<",
	                nextTpl: ">",
	                pagesize: pageSize,
	                count: r.totalResultCount,
	                current: pageNum,
	                toolbar: true,
	                pageSizeList: [10, 20, 50, 100],
	                callback: function(page, size, count) {
	                    pageNum = page;
	                    getList();
	                    flag = false;
	                    document.body.scrollTop = document.documentElement.scrollTop = 0;
	                }
	            });
				$("#pageTool").find(".ui-paging-container:last").siblings().remove();
                $("#pageTool").find(".ui-paging-container ul").append("<li class='totalResultCount'>总共有" + r.totalResultCount + "条记录</li>");

				$(".ui-select-pagesize").unbind("change").change(function() {
	                pageSize = $('.ui-select-pagesize').val();
	                pageNum = 1;
	                getList();
	            });
			}else {
                $("#queboxCnt").html("<tr style='text-align:center;'><td colspan='5'>没有数据</td></tr>");
                $("#pageTool").html("");
            }
		}else{
			layer.msg(r.message);
		}
	});
}
//重置密码
function resetPassword(obj){
	$('#myModal').modal('show');
	resetId=$(obj).attr("id");
}
$("#btnSubmit").click(function() {
    if (flagSubmit == false) {
        return false;
    }
    flagSubmit = false;
    setTimeout(function() {
        flagSubmit = true;
    }, 3000);
    operate();
});
function operate(){
	$.ajax({
	    url: '../rest/backadmin/opManage/reset',
	    type: 'get',
	    data: {
	    	"id": resetId
	    }
	}).done(function(r) {
		if (r.status == "SUCCESS") {
            layer.msg(r.message, {
                time: 2000
            }, function() {
            	$('#myModal').modal('hide');
            });
        } else {
            layer.msg(r.message, {
                time: 2000
            });
        }
	});
}

