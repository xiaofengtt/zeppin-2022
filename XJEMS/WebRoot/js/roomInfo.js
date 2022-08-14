var pageNum = '1';
var pagesize;
var count;
var flag = true;
var ExamId = url("?exam");



$(document).ready(function() {
	getList();
	$(".easy_modal input").eq(0).click(function(){
		$(".easy_modal").fadeOut();
	})
	$.ajax({
		type:"get",
		url:"../admin/examGetCurrent",
		async:true,
		success:function(r){
			$(".title").html(r.Records.name);
			$(".modal_title").html(r.Records.name);
		}
	});
})

function getList(id) {
	var id = url('?id') == null ? '' : url('?id');
	var page = (typeof pageNum == 'undefined') ? 1 : pageNum;
	var mUrl = '../admin/roomList?';
	mUrl += 'pagenum=' + page;
	//	url += '&pagesize=10&exam='+id;
	mUrl += '&pagesize=10&exam='+ExamId;
	$.get(mUrl, function(r) {
		if(r.Status == 'success') {
			//			r.totalPageCount && $('.quepager').html('<span style="font-weight:normal">'+ r.pageNum +'</span>/'+ r.totalPageCount);
			if(r.TotalRecordCount > 0) {
				var template = $.templates('#queboxTpl');
				var html = template.render(r.Records);
				var title_template = $.templates("#title");
				var title_html = title_template.render(r.Records[0]);

				$('#queboxCnt').html(html);
				$("#title_msg").html(title_html);

				pagesize = r.pageSize;
				count = r.totalResultCount;

			} else {
				var html = '<tr><td colspan=11>没有数据！</td></tr>'
				$('#queboxCnt').html(html);
			}

		}else{
			var html = '<tr><td colspan=11>'+r.Message+'</td></tr>';
			$('#queboxCnt').html(html);
		}
	}).done(function(r) {
		if(flag) {
			$('#select_page').Paging({
				pagesize: r.PageSize,
				count: r.TotalRecordCount,
				callback: function(page, size, count) {
					pageNum = page;
					flag = false;
					getList();
				}
			});
			$("#select_page").find(".ui-paging-container:last").siblings().remove();
		};
		
		
		var examList;
		//修改考试信息

		//弹窗 
		$(".header_table .first_a").unbind("click").click(function() {
			var examId = $(this).parent().find("input").attr("id");
			$(".easy_modal").fadeOut();
			$(".modal_del").fadeOut();
			$(".modal_msg").fadeIn();
			//获取该教师信息
			$.ajax({
				type:"post",
				url:"../admin/roomGet",
				async:true,
				data:{
					id:examId
				},
				success:function(r){
					var template = $.templates("#modalTpl").render(r.Records);
					$("#modal_msg_main").html(template);
					
				}
			});
			
			/////////
		})

		//隐藏弹窗
		$(".modal_msg ._back").unbind("click").click(function() {
			$(".modal_msg").fadeOut();

		})
		
		
		
		
		//提交修改
		$(".modal_msg .subm").unbind("click").click(function() {
			examList=[];
			for(var i=0;i<$(".info_box").length;i++){
				examList.push(
					{
						arrivaltime:$(".info_box").eq(i).find(".arrivaltime").val(),
						examnationInformation:$(".info_box").eq(i).find(".examnationInformation").val(),
						examnationTime:$(".info_box").eq(i).find(".examnationTime").val()
					}
				);
				console.log(examList);
			}

			
			$.ajax({
				type:"post",
				url:"..admin/roomUpdate",
				async:true,
				data:{
					roomIndex:$("#roomIndex").val(),
					roomAddress:$("#roomAddress").val(),
					roomType:$("#roomType").val(),
					id:$("#hidden_msg").val(),
					examRoomInfo:JSON.stringify(examList)
				},
				success:function(data){
					if(data.Status == "success") {
					$(".easy_modal p").html(data.Message);
					$(".easy_modal").fadeIn();
					$(".modal_msg").fadeOut();
					getList();

					} else {
						$(".easy_modal p").html(data.Message);
						$(".easy_modal").fadeIn();
					}
				}
			});

		});

		//删除信息

		//弹窗
		$(".header_table .color").unbind("click").click(function() {
			$(".easy_modal").fadeOut();
			$(".modal_msg").fadeOut();
			$(".modal_del").fadeIn();
			$("#del_id").attr("value", $(this).parent().find("input").attr("id"));
		})

		//点击删除
		$(".modal_del input:nth-of-type(1)").unbind("click").click(function() {
			$.ajax({
				type: "get",
				url: "../admin/roomDelete?id=" + $("#del_id").attr("value"),
				async: true,
				success: function(data) {
					if(data.Status == "success") {
						$(".easy_modal p").html(data.Message);
						$(".easy_modal").fadeIn();
//						window.location.reload();
						$(".modal_del").fadeOut();
//						pageNum = '1';
						flag=true;
						getList();
					} else {
						$(".easy_modal p").html(data.Message);
						$(".easy_modal").fadeIn();		
					}
				}
			});
		})

		$(".modal_del input:nth-of-type(2)").unbind("click").click(function() {
			$(".modal_del").fadeOut();

		});

	}); //done

}; //getList()


//导入考场信息

var percent;
$("#file").change(function() {
	$(".easy_modal").fadeOut();
	$(".modal_msg").fadeOut();
	$(".modal_del").fadeOut();
	var reg = /(?:xls|xlsx)$/;
	if(reg.test($(this).val()) == true) { //判断上传文件是否符合 xls,xlsx 格式
		
		$(".warning").hide();
		
		$(".modal").fadeIn(function() {		//弹框，并执行回掉函数
			$(".sub").show();
			$(".finish").hide();
			$("#excel").ajaxSubmit(function(r) {		//提交表单 上传头像，并执行回掉函数		
				$.ajax({			//传给服务器文件路径，服务器导入文件并返回进度；
					type: "post",
					url: "../admin/roomAdd",
					async: true,
					data: {
						exam: ExamId,
						resourcePath: r.Records.sourcePath
					},
					success: function(r) {
						clearInterval(percent);
						$(".finish").show();
						$(".sub").hide();
						$(".finish p").html(r.Message);
						$(".progress_bar").width("100%");
						$(".progress_num").html("100%");
					},
					fail:function(r){
						$(".easy_modal p").html(r.Message);
						$(".easy_modal").fadeIn();
					}
					
				}); 				//导入文件函数结束

				setTimeout(function() {	//延迟10毫秒请求接口读取进度条
					percent = setInterval(function(){
						$.ajax({
							type: "get",
							url: "../admin/roomPercent",
							async: true,
							success: function(data) {
								//再此控制进度条
								var percentNum=data.Percent;
								console.log(data.Percent);
								if(percentNum>=70){
									percentNum=100;
									clearInterval(percent);
									$(".finish").show();
									$(".sub").hide();
								}
								$(".progress_bar").width(percentNum+"%");
								$(".progress_num").html(percentNum+"%");
								
							}
						});
					},200);
				}, 10); 					//读取进度函数  结束

				$(".modal_close").click(function() {		
					window.location.reload()
				});										//函数结束
				
				
				
			}); 										//ajaxSubmit 上传文件回调函数 结束

		}); //弹框出现后的回调函数 fadeIn

	} else { //上传文件不合符要求
		$(".warning").show();
	} //else结束

}); //change事件