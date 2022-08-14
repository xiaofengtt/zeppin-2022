var pageNum = 1,
    pageSize = 10,
	listType = $("#listType").val(),
	deleteId='',//删除id
	idArr = []; //勾选checkbox用的数组
var xtxmbm = $("#xtxmbhName").val();
$(document).ready(function() {    
    getList(listType);
    //全选
    $("#allCheck").on("change", function() {
        if ($(this).is(":checked") == true) {
            idArr = [];
            $(".check").prop("checked", true);
            $(".check").each(function(index, el) {
                idArr.push($(".check").eq(index).attr("data-id"));
            });
            console.log(idArr);
        } else {
            $(".check").prop("checked", false);
            idArr = [];
            console.log(idArr);
        }
    });
    $("#fileOutput").attr("href","excel/"+getdataType(listType)+".xlsx");
  //上传文件
    $("#uploadFileId").uploadFile({
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
                    url: "../rest/backadmin/" + listType + "/addInput",
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
    $(".dataOutput").attr("href","dataOutput.jsp?listType="+listType);
    $(".dataOutput").colorbox({
		iframe: true,
		width: "500px",
		height: "300px",
		opacity: "0.5",
		overlayClose: false,
		escKey: true
	});
}); //$(document).ready
//上传文件
function fileAdd(){
	$(".ajax-file-upload").find("input[type='file']").eq(0).click();
}
//warning情况错误枚举
function getWarn(data){
	//错误列表
	var th = '';
	var totlePrice = 0;
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
$('.form-product').change(function(){
	getList(listType);
});
function getList(listType) {
	var data = {};
	data[xtxmbm]=$("select.form-product").val();
	data['pageNum']=pageNum;
	data['pageSize']=pageSize;
    $.ajax({
            url: "../rest/backadmin/" + listType + "/list",
            type: 'get',
            data: data
        })
        .done(function(r) {
            if (r.status == "SUCCESS") {
                if (r.totalResultCount != 0) {
                    var template = $.templates("#queboxTpl");
                    var html = template.render(r.data);
                    $("#queboxCnt").html(html);
                    
                    $(".xtxmbh").each(function(){
                    	$(this).html(productMap[$(this).html()]);
                    })
                    
                    //点击列表中的删除，会把删除按钮的data-id 赋值给 模态框中的确定按钮 的data-id；
                    $(".delete").unbind('"click"').click(function() {
                        $("#deleteSubmit").attr("data-id", $(this).attr("data-id"));
                    });
                    
                    $.each(r.data, function(index, el) {
                        $(".createTime").eq(index).html(getTime(el.createtime));
                        if (el.updatetime != null) {
                            $(".updateTime").eq(index).html(getTime(el.updatetime));
                        }
                    });


                    //选中
                    $(".check").unbind("change").change(function() {
                        var checkFlag = true,
                            _this = "";
                        idArr = [];
                        $(".check").each(function(index, el) {
                            _this = $(".check").eq(index);
                            if (_this.prop("checked") == true) {
                                idArr.push(_this.attr("data-id"));
                            } else {
                                if (idArr.indexOf(_this.attr("data-id")) != -1) {
                                    idArr.splice(idArr.indexOf(_this.attr("data-id")), 1);
                                }
                                checkFlag = false;
                            }
                        });
                        console.log(idArr);
                        if (checkFlag) {
                            $("#allCheck").prop("checked", true);
                        } else {
                            $("#allCheck").prop("checked", false);
                        }
                    });

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
                            getList(listType);
                            flag = false;
                            document.body.scrollTop = document.documentElement.scrollTop = 0;
                        }
                    });
                    $("#pageTool").find(".ui-paging-container:last").siblings().remove();
                    $("#pageTool").find(".ui-paging-container ul").append("<li class='totalResultCount'>总共有" + r.totalResultCount + "条记录</li>");

                    $(".ui-select-pagesize").unbind("change").change(function() {
                        pageSize = $('.ui-select-pagesize').val();
                        pageNum = 1;
                        getList(listType);
                    });
                } else {
                	var i=0;
                	$("table th").each(function(){
                		i++;
                	})
                    $("#queboxCnt").html("<tr><td colspan='"+i+"' class='text-center'>没有数据</td></tr>");
                }
            	if(flagCheck == "true"){
            		$(".check-button").removeClass("hidden");
            	}
            } else {
                layer.msg(r.message, {
                    time: 2000
                });
            }
        })
        .fail(function() {
            layer.msg("error", {
                time: 2000
            });
        });
} //getList
//删除
function deleteList(id){
	deleteId = id;
	$("#myModal .modal-body h4").html("确定删除此条数据吗？");
	$("#myModal").modal('show');
}
$("#btnSubmit").click(function() {
    if (flagSubmit == false) {
        return false;
    }
    flagSubmit = false;
    setTimeout(function() {
        flagSubmit = true;
    }, 3000);
    deleteSure($(this));
});
function deleteSure(_this){
	var checkStatus = $(_this).attr("data-status");
    $.ajax({
            url: '../rest/backadmin/' + listType + '/delete',
            type: 'get',
            data: {
                "id": deleteId
            }
        })
        .done(function(r) {
            if (r.status == "SUCCESS") {
                layer.msg(r.message, {
                    time: 2000
                }, function() {
                    location.href = location.href;
                });
            } else {
                layer.msg(r.message, {
                    time: 2000
                });
                $("#myModal").modal('hide');
            }
        })
        .fail(function() {
            layer.msg("error", {
                time: 2000
            });
        });
}
