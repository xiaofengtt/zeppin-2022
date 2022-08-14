var pageSize = 20;
var pageNum = 1;
$(function(){
	operable();
	getList();
	$.get('../rest/backadmin/opManage/all', function(r) {
        if (r.status == 'SUCCESS') {
            var html = '';
            if (r.data.length > 0) {
                $.each(r.data, function(i, v) {
                    html += '<option value="' + v.id + '">' + v.name + '</option>';
                })
            }
            $('#operatorSelect').append(html);
        } else {
            layer.msg(r.message, {
                time: 2000
            })
        }
	});
	lay(".form-date").each(function(){
		laydate.render({
    		elem: this,
    		format: "yyyy-MM-dd"
    	});
	});
});
$('.form-product').change(function(){
	getList();
});
//获取列表信息
function getList(){
	$.ajax({
	    url: '../rest/backadmin/log/list',
	    type: 'get',
	    data: {
	    	"dataproduct":$("select.form-product").val(),
	    	"datatype":$("select[name='datatype']").val(),
	    	"creator":$("#operatorSelect").val(),
	    	"starttime":$("input[name='starttime']").val(),
	    	"endtime":$("input[name='endtime']").val(),
	        "pageNum": pageNum,
	        "pageSize": pageSize,
	        "timestamp":new Date().getTime()
	    }
	}).done(function(r) {
		if(r.status=="SUCCESS"){
			var tableHtml="";
			if (r.totalResultCount != 0) {				
				for(var i=0;i<r.data.length;i++){
					var Explain = "";
					if(r.data[i].type!="edit"){
						Explain=gettype(r.data[i].type);
					}else{
						var arrNew = [];
						var arrOld = [];
						var copyPerson={};
						for(var item in r.data[i].olddata){
							 copyPerson[item]= r.data[i].olddata[item]; 
						}
						for(var item in copyPerson){
							if(item!="updatetime"&&item!="ctltime"&&item!="status"){
								if(r.data[i].newdata[item]!=r.data[i].olddata[item]){
									Explain +=item+",";
								}
							}
						}
						Explain ="修改了"+Explain.substring(0,Explain.length-1)+"字段值";
					}
					tableHtml+="<tr><td>"+Number(pageSize*(pageNum-1)+Number(i+1))+
					"</td><td>"+(r.data[i].datatype?getUpper(r.data[i].datatype):'')+
					"</td><td>"+(r.data[i].datatype?getdataType(r.data[i].datatype):'')+
					"</td><td>"+(r.data[i].type?gettype(r.data[i].type):'')+"</td><td>"+Explain+
					"</td><td>"+(r.data[i].creatorName?r.data[i].creatorName:'')+"</td><td>"+getTime(r.data[i].createtime)+"</td></tr>";
					
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
                $("#queboxCnt").html("<tr style='text-align:center;'><td colspan='6'>没有数据</td></tr>");
                $("#pageTool").html("");
            }
		}else{
    		layer.msg(r.message);
    	}
	}).fail(function() {
        layer.msg("error", {
            time: 2000
        });
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


