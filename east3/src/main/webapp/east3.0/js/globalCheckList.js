var pageNum = 1,
	pageSize = 10,
	listType = $("#listType").val(),
	idArr = [];
$(document).ready(function() {
    getList(listType);

    $("#status-select").change(function() {
        pageNum = 1;
        getList(listType);
    });
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

}); //$(document).ready
$('.form-product').change(function(){
	getList(listType);
});
function getList(listType) {
    $.ajax({
            url: "../rest/backadmin/" + listType + "/checkList",
            type: 'get',
            data: {
            	"dataproduct":$("select.form-product").val(),
                "pageNum": pageNum,
                "pageSize": pageSize
            }
        })
        .done(function(r) {
            if (r.status == "SUCCESS") {
                if (r.totalResultCount != 0) {
                    var template = $.templates("#queboxTpl");
                    var html = template.render(r.data);
                    $("#queboxCnt").html(html);
                    
                    $(".xtxmbh").each(function(){
                    	$(this).html(productMap[$(this).html()]);
                    });
                    $(".updateTime").each(function(){
                    	$(this).html(getTime(Number($(this).html())));
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
                    $("#queboxCnt").html("<tr style='text-align:center;'><td colspan='8'>没有待审核数据</td></tr>");
                }
            	if(flagEdit == "true"){
            		$(".edit-button").removeClass("hidden");
            	}
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
$(".checkBtn").click(function() {
	if(idArr.length==0){
		$("#myModal").modal('hide');
		layer.msg("请选择要审核的内容");
		return false;
	}else{
		modalType = "check";
	    $("#btnSubmit").attr("data-status", $(this).attr("data-status"));
	}    
});

//审核
function check(_this) {
	var checkStatus = $(_this).attr("data-status");
    $.ajax({
            url: '../rest/backadmin/' + listType + '/checkCheck',
            type: 'get',
            traditional: "true",
            data: {
            	"status": checkStatus,
                "id": idArr
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

$("#btnSubmit").click(function() {
    if (flagSubmit == false) {
        return false;
    }
    flagSubmit = false;
    setTimeout(function() {
        flagSubmit = true;
    }, 3000);
    check($(this));
});

