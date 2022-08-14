var pageSize = 20;
var pageNum = 1;
var resetId;
var resetDatatype;
$(function(){
	getList();
	operable();
});
//查询列表
function getList(){
	$.ajax({
	    url: '../rest/backadmin/log/checkList',
	    type: 'get',
	    data: {
	    	"datatype":$(".operableTable").val(),
	    	"pageNum": pageNum,
	        "pageSize": pageSize,
            "dataproduct":$("select.form-product").val()
	    }
	}).done(function(r) {
		if(r.status=="SUCCESS"){
			var tableHtml="";
			if (r.totalResultCount != 0) {				
				for(var i=0;i<r.data.length;i++){
					var type;
					if(r.data[i].olddata){
						type = "修改";
					}else{
						type = "添加";
					}
					tableHtml+="<tr><td>"+Number(pageSize*(pageNum-1)+Number(i+1))+
					"</td><td>"+getUpper(r.data[i].datatype)+
					"</td><td>"+getdataType(r.data[i].datatype)+
					"</td><td>"+type+"</td><td>"+(r.data[i].creatorName?r.data[i].creatorName:'')+"</td><td>"+getTime(r.data[i].createtime)+
					"</td><td><a dataType='"+r.data[i].datatype+"' id='"+r.data[i].id+"' onclick='checkRollback(this);'>撤销审核</a></td></tr>";					
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
                $("#queboxCnt").html("<tr style='text-align:center;'><td colspan='7'>没有数据</td></tr>");
                $("#pageTool").html("");
            }
		}else{
			layer.msg(r.message);
		}
	});
}
//撤销审核
function checkRollback(obj){
	$('#myModal').modal('show');
	resetId=$(obj).attr("id");
	resetDatatype=$(obj).attr("dataType");
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
	    url: '../rest/backadmin/'+resetDatatype+'/checkRollback',
	    type: 'get',
	    data: {
	    	"id": resetId
	    }
	}).done(function(r) {
		if (r.status == "SUCCESS") {
            layer.msg(r.message, {
                time: 2000
            }, function() {
            	location.reload();
            });
        } else {
        	layer.msg(r.message, {
                time: 2000
            }, function() {
            	$('#myModal').modal('hide');
            });
        }
	});
}
//可操作表名
function operable(){
	$.ajax({
	    url: '../rest/backadmin/cross/tableList',
	    type: 'get',
	    data: {
	    	
	    }
	}).done(function(r) {
		if (r.status == "SUCCESS") {
			var option="";
            for(var i=0;i<r.data.length;i++){
            	option+="<option value='"+r.data[i].key+"'>"+r.data[i].value+"</option>";
            }
            $(".operableTable").append(option);
        }
	});
}
$('.form-product').change(function(){
	getList();
});