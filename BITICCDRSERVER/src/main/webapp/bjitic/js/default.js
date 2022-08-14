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
            "name":"",
            "status":""
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
            "name":"",
            "status":""
		}
		$(".top-item").find("input").val("");
        $(".top-item").find("select").val("");
		getList(dataList);
	});



	function get(id){
		$.ajax({
			type:"get",
			url:"../rest/backadmin/number/getnr",
			async:true,
			cache:false,
			data:{
				"id":id
			}
		})
		.done(function(r){
			if(r.status == "SUCCESS"){


                $("#edit-form").find(".opCode").val(r.data.opName);
                $("#edit-form").find("[name=opCode]").val(r.data.opCode);

				$("#edit-form").find(".custId").val(r.data.custName);
                $("#edit-form").find("[name=custId]").val(r.data.custId);

                $("#edit-form").find(".toMobile").val(r.data.toMobile);

                if(r.data.tcPhone != ""){
                    $("#edit-form").find(".tcPhone").val(r.data.tcPhone);
                }else{
                    $("#edit-form").find(".tcPhone").val("");
                    $("#edit-form").find(".tcPhone").removeAttr('readonly');
                }

				$("#edit-form").find(".tcPhone").val(r.data.tcPhone);
				$("#edit-form").find(".toTel").val(r.data.toTel);

                getTcTel(r.data.opCode,"#edit-form");

                if(r.data.tcTel != ""){
                    $("#edit-form").find(".tcTel").append("<option value="+ r.data.tcTel +">" + r.data.tcTel + "</option>");
    				$("#edit-form").find(".tcTel").val(r.data.tcTel);
                }

                if($("#edit-form").find(".tcTel").html() == ""){
                    $("#edit-form").find(".tcTel").append("<option value=''>该经理没有可分配的呼入号码</option>");
                }


				$("#edit-form").find("#id").val(r.data.id);

				searchTono(r.data.opCode,"#edit-form")
			}else{
				alert(r.message)
			}
		})
		.fail(function(){
			alert("error")
		});

	}


    //根据经理查询主叫和呼入号
	function searchTono(opCode,form){
		$.ajax({
			type:"get",
			url:"../rest/backadmin/number/searchtono",
			async:true,
			cache:false,
			data:{
				"opCode":opCode
			}
		})
		.done(function(r){
			if(r.status == "SUCCESS"){
                if(r.data.status == "normal"){
                    $(form).find(".toMobile").val(r.data.toMobile);
    				$(form).find(".toTel").val(r.data.toTel);
    				$(form).find(".tono").val(r.data.id);
                }else{
                    $(form).find(".toMobile").val("");
    				$(form).find(".toTel").val("");
    				$(form).find(".tono").val("");
                    alert("请先设置客户经理的主叫号码与呼入号码");
                }
			}else if(r.status == "FAILED"){
				$(form).find(".toMobile").val("");
				$(form).find(".toTel").val("");
				$(form).find(".tono").val("");
                alert("请先设置客户经理的主叫号码与呼入号码");
			}else{
                alert(r.message);
            }
//			searchTc(opCode,form);
		})
		.fail(function(){
			alert("error");
		});
	}

    //获取未分配呼出号码
	function getTcTel(opCode,form){
		$.ajax({
			type:"get",
			url:"../rest/backadmin/number/gettcTel",
			async:false,
			cache:false,
			data:{
				"opCode":opCode,
				"pageNum":1,
				"pageSize":20
			}
		})
		.done(function(r){
            $(form).find(".tcTel").html("");
			$.each(r.data,function(index,el){
				$(form).find(".tcTel").append("<option value="+ el +">" + el + "</option>");
			});

		})
		.fail(function(){
			alert("error");
		});
	}

    //初始化selete2插件
    function select2_manager(){
        $(".selectManager").select2({
    		ajax: {
    			delay: 250,
    		    url: '../rest/backadmin/number/searchto',
    		    data: function (params) {
    		      var query = {
    		        name: params.term,
    		        pageNum:1,
    		        pageSize:20
    		      }
    		      return query;
    		    },
    		    processResults: function (data) {
    				var arr = [];
    				$.each(data.data, function(index,el) {
    					arr.push({
    						id:el.opCode,
    						text:el.opName
    					});
    				});
    				return {
    					results: arr
    			    };
    		    }
    		}
    	});
    }
    function select2_customer(){
    	$(".selectCustomer").select2({
    		ajax: {
    			delay: 250,
    		    url: '../rest/backadmin/number/searchtc',
    		    data: function (params) {
    		      var query = {
    		        name: params.term,
    		        opCode:$(this).parent().parent().find(".selectManager").val() ? $(this).parent().parent().find(".selectManager").val() : "999999999",
    		        pageNum:1,
    		        pageSize:20
    		      }
    		      return query;
    		    },
    		    processResults: function (data) {
    				var arr = [];
    				$.each(data.data, function(index,el) {
    					arr.push({
    						id:el.custId,
    						text:el.custName
    					});
    					tcPhoneArr.push({
    						id:el.custId,
    						custTel:el.mobile
    					});
    				});
    				console.log(tcPhoneArr);
    				return {
    					results: arr
    			    };
    		    }
    		}
    	});
    }
    select2_manager();
    select2_customer();

	$(".selectManager").change(function(){
		searchTono($(this).val(),$(this).parent().parent().parent());
		getTcTel($(this).val(),$(this).parent().parent().parent());
		$(this).parent().parent().find(".selectCustomer").html("<option value=''>请选择客户名称</option>");
        select2_customer();
	});

	$(".selectCustomer").change(function(){
		var _this = $(this)
		$.each(tcPhoneArr,function(index,el){
			if(el.id == _this.val()){
                _this.parent().parent().find(".tcPhone").val(el.custTel);
				console.log(el.custTel)
				return false;
			}
		});
	})

	function getList(data) {
		$.ajax({
				type: "get",
				url: "../rest/backadmin/number/searchnr",
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
						$("#queboxCnt").html("<tr><td colspan='11'>未找到符合条件的结果</td></tr>");
						return false;
					}

					//选中
					$(".check").unbind("change").change(function() {
						var checkFlag = true,
							_this = "";
						idArr = [];
						$(".check").each(function(index, el) {
							_this = $(".check").eq(index);
							if(_this.prop("checked") == true) {
								idArr.push(_this.attr("data-id"));
							} else {
								if(idArr.indexOf(_this.attr("data-id")) != -1) {
									idArr.splice(idArr.indexOf(_this.attr("data-id")), 1);
								}
								checkFlag = false;
							}
						});
						console.log(idArr);
						if(checkFlag) {
							$("#allCheck").prop("checked", true);
						} else {
							$("#allCheck").prop("checked", false);
						}
					});

					//修改
					$(".edit").unbind("click").click(function() {
						get($(this).attr("data-id"));
						$("#edit-box").dialog({
							modal: true,
                            width:"330px",
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

					//删除
					$(".delete").unbind("click").click(function() {
                        idArr = [];
                        $(".check").prop("checked",false);
                        idArr.push($(this).attr("data-id"));
						$(".delete-box").dialog({
							modal: true,
							buttons: {
								"确定": function() {
                                    remove(".delete-box");
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
		$("#add-box").dialog({
			modal: true,
            width:"330px",
			buttons: {
				"确定": function() {
					if(test("#add-form")) {
						$("#add-form").ajaxSubmit(function(r){
                            if(r.status == "SUCCESS"){
                                alert(r.message);
                                $("#add-box").dialog("close");
                                getList(dataList);
                                $("#add-form").find(".selectManager").html("<option value='' selected>请选择经理名称</option>");
                                $("#add-form").find(".selectCustomer").html("<option value='' selected>请选择客户名称</option>");
                                select2_manager();
                                select2_customer();
                                $("#add-form").find(".toMobile").val("");
                                $("#add-form").find(".tcPhone").val("");
                                $("#add-form").find(".toTel").val("");
                                $("#add-form").find(".tcTel").val("");

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

	$("#adds").click(function() {
		$("#adds-box").dialog({
			modal: true,
			buttons: {
				"确定": function() {
					alert("ok");
				},
				"取消": function() {
					$(this).dialog("close");
				}
			}
		});
	})

	//全选
	$("#allCheck").on("change", function() {
		if($(this).is(":checked") == true) {
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

	//批量删除
	$("#deletes").click(function() {
		if(idArr.length == 0) {
			alert("请先勾选条目");
		} else {
			$(".deletes-box").dialog({
				modal: true,
				buttons: {
					"确定": function() {
                        remove(".deletes-box");
					},
					"取消": function() {
						$(this).dialog("close");
					}
				}
			});
		}

	});

    //删除请求
    function remove(el){
        $.ajax({
            type:"get",
            url:"../rest/backadmin/number/deletenr",
            async:true,
            cache:false,
            data:{
                "id":idArr.join(",")
            }
        })
        .done(function(r){
            if(r.status == "SUCCESS"){
                alert(r.message);
                idArr = [];
                $(el).dialog("close");
                getList(dataList);
            }else{
                alert(r.message);
            }
        })
        .fail(function(){
            alert("error");
        });
    }

	//表单验证
	function test(form) {
		var NumReg = /^[0-9]*$/;
        if($(form).find(".opCode").val() == "0" || $(form).find(".opCode").val() == ""){
            alert("请选择经理名称");
			return false;
        }

        if(NumReg.test($(form).find(".toMobile").val()) == false || $(form).find(".toMobile").val() == "") {
			alert("请正确填写主叫号码");
			return false;
		}

        if(NumReg.test($(form).find(".toTel").val()) == false || $(form).find(".toTel").val() == "") {
			alert("请正确填写呼出号码");
			return false;
		}

        if($(form).find(".custId").val() == "0" || $(form).find(".custId").val() == ""){
            alert("请选择客户名称");
			return false;
        }

		if(NumReg.test($(form).find(".tcPhone").val()) == false || $(form).find(".tcPhone").val().length != 11) {
			alert("请正确填写客户联系方式");
			return false;
		}

		if($(".tcTe").val() == 0) {
			alert("请选择呼入号码");
			return false;
		}

		return true;
	}
});
