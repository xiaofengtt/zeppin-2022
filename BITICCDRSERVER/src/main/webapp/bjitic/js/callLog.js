//$.ui.dialog.prototype._allowInteraction = function(e) {
//  return !!$(e.target).closest('.ui-dialog, .ui-datepicker, .select2-drop').length;
//};

//添加数组IndexOf方法
if (!Array.prototype.indexOf){
  Array.prototype.indexOf = function(elt /*, from*/){
    var len = this.length >>> 0;

    var from = Number(arguments[1]) || 0;
    from = (from < 0)
         ? Math.ceil(from)
         : Math.floor(from);
    if (from < 0)
      from += len;

    for (; from < len; from++){
      if (from in this && this[from] === elt)
        return from;
    }
    return -1;
  };
}

$(document).ready(function() {
	var idArr = [],
		tcPhoneArr = [],
		dataList = {
            "pageNum": 1,
			"pageSize": 10,
			"toMobile":"",
			"toTel":"",
			"tcPhone":"",
			"tcTel":"",
            "oname":"",
            "name":""
		}

	getList(dataList);


	$("#search").click(function(){
		$.each(dataList,function(Key,val){
			if(Key != "pageNum" && Key != "pageSize"){
				dataList[Key] = $("#"+ Key + "-name").val();
			}
		});
		dataList.pageNum = 1;
		getList(dataList);
	});

	$("#clear").click(function(){
		dataList = {
            "pageNum": 1,
			"pageSize": 10,
			"toMobile":"",
			"toTel":"",
			"tcPhone":"",
			"tcTel":"",
            "oname":"",
            "name":""
		}
		$(".top-item").find("input").val("");
		console.log(dataList);
		getList(dataList);
	});

	function getList(data) {
		$.ajax({
				type: "get",
				url: "../rest/backadmin/mslist/search",
				async: true,
				cache: false,
				data: data
			})
			.done(function(r) {
				if(r.status == "SUCCESS") {
					if(r.totalResultCount != 0){
						var template = $.templates("#queboxTpl");
						var html = template.render(r.data);
						$("#queboxCnt").html(html);
					}else{
						$("#queboxCnt").html("<tr><td colspan='16'>未找到符合条件的结果</td></tr>");
						return false;
					}


                    $('#pageTool').Paging({
						prevTpl: "<",
						nextTpl: ">",
						pagesize: dataList.pageSize,
						count: r.totalResultCount,
						current: dataList.pageNum,
						toolbar: true,
						pageSizeList: [10, 20, 50, 100],
						callback: function(page, size, count) {
							dataList.pageNum = page;
							getList(dataList);
							flag = false;
							document.body.scrollTop = document.documentElement.scrollTop = 0;
						}
					});
					$("#pageTool").find(".ui-paging-container:last").siblings().remove();
                    $("#pageTool").find(".ui-paging-container ul").append("<li class='totalResultCount'>总共有"+ r.totalResultCount +"条记录</li>");

					$(".ui-select-pagesize").unbind("change").change(function() {
						dataList.pageSize = $('.ui-select-pagesize').val();
						dataList.pageNum = 1;
						getList(dataList);
					});
				} else {
					alert(r.message);
				}
			})
			.fail(function() {
				alert("error");
			});
	}

});
