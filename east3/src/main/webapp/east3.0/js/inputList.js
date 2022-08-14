$(document).ready(function() {
	$(".fileOutput").each(function(){
    	$(this).attr("href","excel/"+getdataType($(this).attr("data-type"))+".xlsx");
    });
    $(".tableNameUpper").each(function(){
    	$(this).html(getUpper($(this).html()));
    });
    $(".tableNameCN").each(function(){
    	$(this).html(getdataType($(this).html()));
    });
    $(".serialNumber").each(function(index,value){
    	$(this).html(index+1);
    });
  //上传文件
    $(".uploadFile").each(function(){
    	var thisType = $(this).attr("data-type");
    	$(this).uploadFile({
	        id: "1",
	        url: "../rest/backadmin/file/add",
	        allowedTypes: "xlsx,xls",
	        maxFileSize: 1024 * 1024 * 1024 * 10,
	        fileName: "file",
	        maxFileCount: 100,
	        dragDropStr: "",
	        extErrorStr: "文件格式不正确，请上传指定类型类型的文件",
	        multiple: true,
	        showDone: false,
	        showDelete: false,
	        deletelStr: '删除',
	        doneStr: "完成",
	        showAbort: false,
	        showStatusAfterSuccess: false,
	        maxFileCountErrorStr: "文件数量过多，请先删除。",
	        onSuccess: function(files, data, xhr) {
	        	if(data.status!="SUCCESS"){
	        		layer.msg(data.message);
	        	}else{
	                $('#uploadFile').val(data.data.id);
	                $("input[type='file']").val("");
	                $.ajax({
	                    url: "../rest/backadmin/" + thisType + "/addInput",
	                    type: 'post',
	                    data: {
	                        "file": data.data.id
	                    }
	                })
	                .done(function(r) {
	                	if(r.status=="WARNING"){
	                		$("#errorModal").modal('show');
	                		getWarn(r);
	                	}else if(r.status=="SUCCESS"){
	                		layer.msg("上传成功");
	                	}else{
	                		layer.msg(r.message);
	                	}
	                });
	        	}           
	            return false;
	        }
	    });
    });
	//多表上传文件
    $(".MultiuploadFile").each(function(){    	
    	$(this).uploadFile({
	        id: "1",
	        url: "../rest/backadmin/file/add",
	        allowedTypes: "xlsx,xls",
	        maxFileSize: 1024 * 1024 * 1024 * 10,
	        fileName: "file",
	        maxFileCount: 100,
	        dragDropStr: "",
	        extErrorStr: "文件格式不正确，请上传指定类型类型的文件",
	        multiple: true,
	        showDone: false,
	        showDelete: false,
	        deletelStr: '删除',
	        doneStr: "完成",
	        showAbort: false,
	        showStatusAfterSuccess: false,
	        maxFileCountErrorStr: "文件数量过多，请先删除。",
	        onSuccess: function(files, data, xhr) {
	        	if(data.status!="SUCCESS"){
	        		layer.msg(data.message);
	        	}else{
	                $('#MultiuploadFile').val(data.data.id);
	                $("input[type='file']").val("");
	                $.ajax({
	                    url: "../rest/backadmin/input/input",
	                    type: 'post',
	                    data: {
	                        "file": data.data.id
	                    }
	                })
	                .done(function(r) {
	                	if(r.status=="WARNING"){
	                		$("#errorModal").modal('show');
	                		getWarn(r);
	                	}else if(r.status=="SUCCESS"){
	                		layer.msg("上传成功");
	                	}else{
	                		layer.msg(r.message);
	                	}
	                });
	        	}           
	            return false;
	        }
	    });
    });
})

function fileAdd(t){
	var thisType = $(t).attr("data-type");
	$(".tr"+thisType + " .ajax-file-upload").find("input[type='file']").eq(0).click();
}
$(".MultiExport").click(function(){
	$(".Multi .ajax-file-upload").find("input[type='file']").eq(0).click();
});

//warning情况错误枚举
function getWarn(data){
	//错误列表
	var th = '';
	var totlePrice = 0;
	$(".model-title-tableName").html("数据列表--"+data.data.type);
	for (var i = 0; i < data.data.cloumnCN.length; i++) {
	    th += '<th>' + data.data.cloumnCN[i] + '</th>';
	}
	var html = '<tr class="first-tr"><th>行数</th>' + th + '</tr>';
	for(var key in data.data.datasMap){
		var classname;
		var redFlag=false;
		for(var i=0;i<data.error.length;i++){
			if(key==Number(data.error[i].index)){
				redFlag=true;
			}else{
				
			}
		}		
		tr = '';
		for(var j = 0; j < data.data.cloumn.length; j++){
		 	var flag = false;
			for (var item in data.data.datasMap[key]) {
				if(!flag){
					if(item==data.data.cloumn[j]){
						flag = true;
					}else{
						flag = false;
					}
				}						
			}
			if(flag){
				tr += '<td>' + data.data.datasMap[key][data.data.cloumn[j]] + '</td>';
			}else{
				tr += '<td></td>';
			}
		}
		if(redFlag){
			classname = 'red-color';
		}else{
			classname = '';
		}
	    tr = '<tr class='+classname+'><td>'+key+'</td>' + tr + '</tr>';
	    html += tr;
	}
	$(".errorList .tableContent").html(html);
	var warnStr = '<tr class="first-tr"><th class="td-padding-item" width="8%">所在行数</th><th width="92%">错误原因</th></tr>';
	//错误枚举
	for(var i=0;i<data.error.length;i++){
		warnStr += "<tr><td class='td-padding-item'>第"+ data.error[i].index +"行</td><td><div class='errorTableTr' title='"+ data.error[i].data + "'>" 
		+ data.error[i].data + "</div></td></tr>";
	}
	$(".errorBox .tableContent").html(warnStr);
}
