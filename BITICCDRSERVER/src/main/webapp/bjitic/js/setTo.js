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
            "name":"",
			"toMobile":"",
			"toTel":""
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
            "name":"",
			"toMobile":"",
			"toTel":""
		}
		$(".top-item").find("input").val("");
		console.log(dataList);
		getList(dataList);
	});



	function get(id){
		$.ajax({
			type:"get",
			url:"../rest/backadmin/operator/getno",
			async:true,
			cache:false,
			data:{
				"id":id
			}
		})
		.done(function(r){
			if(r.status == "SUCCESS"){
				getToTel("#edit-form");
                $("#edit-form").find(".opCode").val(r.data.opName);

                if(r.data.toMobile != ""){
                    $("#edit-form").find(".toMobile").append("<option data-tel="+ r.data.toTel +" value="+ r.data.toMobile +">" + r.data.toMobile + "</option>");
                    $("#edit-form").find(".toTel").val(r.data.toTel);
                    $("#edit-form").find(".toMobile").val(r.data.toMobile);
                }
				$("#edit-form").find("#id").val(r.data.id);

			}else{
				alert(r.message)
			}
		})
		.fail(function(){
			alert("error")
		});

	}


    //获取未分配呼出号码
	function getToTel(form){
		$.ajax({
			type:"get",
			url:"../rest/backadmin/operator/gettoTel",
			async:false,
			cache:false,
			data:{
				"pageNum":1,
				"pageSize":20
			}
		})
		.done(function(r){
            $(form).find(".toMobile").html("");
            $(form).find(".toTel").html("");
            $(form).find(".toMobile").append("<option value=''>释放</option>");
            each_arr(form,r.data);
		})
		.fail(function(){
			alert("error");
		});
	}

    function each_arr(form,arr){
        $.each(arr,function(index, el) {
            $(form).find(".toMobile").append("<option data-tel="+ el.toTel +" value="+ el.toMobile +">" + el.toMobile + "</option>");
        });
        // $.each(arr.toTel,function(index, el) {
        //     $(form).find(".toTel").append("<option value="+ arr.toTel[index] +">" + arr.toTel[index] + "</option>");
        // });
    }




    $(".toMobile").change(function(event) {
        if($(this).val() != ""){
            $(this).parent().parent().find('.toTel').val($(this).find('option:selected').attr('data-tel'));
        }else{
            $(this).parent().parent().find('.toTel').val("");
        }
    });

    // $(".toTel").change(function(event) {
    //     if($(this).val() == ""){
    //         $(this).parent().parent().find('.toMobile').val("");
    //     }
    // });

	function getList(data) {
		$.ajax({
				type: "get",
				url: "../rest/backadmin/operator/searchno",
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
						$("#queboxCnt").html("<tr><td colspan='6'>未找到符合条件的结果</td></tr>");
						return false;
					}



					//修改
					$(".edit").unbind("click").click(function() {
						get($(this).attr("data-id"));
						$("#edit-box").dialog({
							modal: true,
							buttons: {
								"确定": function() {
                                    if(test("#edit-form")){
                                        $("#edit-form").ajaxSubmit(function(r){
    										if(r.status == "SUCCESS"){
    											alert(r.message);
    											getList(dataList);
                                                $("#edit-box").dialog("close");
    										}else{
    											alert(r.message);
    										}
    									});
                                    }

								},
								"取消": function() {
									$(this).dialog("close");
								}
							}
						});
					});

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

	$(".date").click(function() {
		laydate({
			//			elem:".date",
			istime: true,
			istoday: true,
			format: "YYYY-MM-DD hh:mm:ss"
		});
	})



	$("#add").click(function() {
         getToTel("#add-form");
		$("#add-box").dialog({
			modal: true,
			buttons: {
				"确定": function() {
					if(test("#add-form")) {
						$("#add-form").ajaxSubmit(function(r){
                            if(r.status == "SUCCESS"){
                                alert(r.message);
                                $("#add-box").dialog("close");
                                getList(dataList);
                                $("#add-form").find(".selectManager").html("<option value='' selected>请选择经理名称</option>");
                                select2_manager();
                                // $("#add-form").find(".toMobile").val("");
                                // $("#add-form").find(".tcPhone").val("");
                                // $("#add-form").find(".toTel").val("");
                                // $("#add-form").find(".tcTel").val("");

                            }else{
                                alert(r.message);
                            }
                        });
					}
				},
				"取消": function() {
					$(this).dialog("close");
				}
			}
		});
	});

	//表单验证
	function test(form) {

        if($(form).find(".opCode").val() == ""){
            alert("请选择经理名称");
			return false;
        }

        return true;
    }
});
