var pageNum = '1';
var pagesize;
var count;
var flag = true;

function deleteThis(t) {
	layer.titleConfirm('确认要删除吗?', "删除确认", function(index) {
		var obj = $(t),
			cUrl = obj.attr('data-url');
		$.get(cUrl, function(ret) {
			if(ret.status == 'SUCCESS') {
				flag = true;
				getList();
			} else {
				alert('失败,' + ret.message);
			}
		})
		layer.close(index);
	});
	return false;
}

$(document).ready(function() {
//	$('#examinfo').submit(
//		function() {
//			var str = $(this).serialize();
//			$.post('../admin/examAdd', str, function(data) {
//				var Result = data.Status;
//				var message = data.Message;
//				if(Result == "success") {
//					window.location.href = '../admin/main.jsp';
//				} else {
//					alert('失败，' + message);
//				}
//			})
//			return false;
//		});
})


function getList(name, sorts) {
	var teacherinfo = '';
	if(typeof(name) != "undefined") {
		teacherinfo = name;
	}
	var sort = '';
	if(typeof(sorts) != "undefined") {
		sort = sorts;
	}
	var page = (typeof pageNum == 'undefined') ? 1 : pageNum;
	var url = '../admin/recordsConfirmList?';
	url += 'pagenum=' + page;
	url += '&pagesize=10&exam=1&type=&isChief=';
	if(teacherinfo != '') {
		url += '&teacherinfo=' + teacherinfo;
	}
	if(sort != '') {
		url += '&sorts=' + sort;
	}

	$.get(url, function(r) {
		if(r.Status == 'success') {
			//			r.totalPageCount && $('.quepager').html('<span style="font-weight:normal">'+ r.pageNum +'</span>/'+ r.totalPageCount);
			if(r.TotalRecordCount > 0) {
				var template = $.templates('#queboxTpl');
				var html = template.render(r.Records);
				$('#queboxCnt').html(html);
				pagesize = r.pageSize;
				count = r.totalResultCount;
			} else {
				var html = '<tr class="nodata"><td colspan=11>没有数据！</td></tr>'
				$('#queboxCnt').html(html);
			}
		} else {
			layer.msg(r.message, {
				time: 2000
			})
		}
	}).done(function(r) {
		//		if(flag){
		//			$('#pageTool').Paging({pagesize:pagesize,count:count,callback:function(page,size,count){			
		//				pageNum = page;
		//				getList();
		//				flag=false;
		//				document.body.scrollTop = document.documentElement.scrollTop = 0;
		//			},render:function(ops){
		//				
		//			}});
		//			$("#pageTool").find(".ui-paging-container:last").siblings().remove();
		//		}
		//		$(".table tbody tr").each(function(){
		//			//$(this).find("td:eq(1) span img").css("margin-top",(42-$(this).find("td:eq(1) span img").height())/2+"px");
		//			$("tr:even td span").css("color","rgba(43,43,43,0.8)");
		//		});
	});
}

$(document).ready(function() {
	//添加模板
	$.ajax({
		type:"get",
		url:"../admin/examTemplateList",
		async:true,
		success:function(data){
				for(var i=0;i<data.Records.length;i++){
					$(".select_msg").append("<option>"+data.Records[i].title+"</option>");
					
				}
				$(".select_msg").change(function(){
					statement.setContent(data.Records[$(this).get(0).selectedIndex-1].content);
					$("#msg_id").val(data.Records[$(this).get(0).selectedIndex-1].id);
				})
			
		}
	});
	$(".back").click(function(){
		window.location.href="../admin/main.jsp";
	})
	$(".sub_publish").click(function() {
		if($("#name").val()==''){
			$("#modal").fadeIn().find('p').html("请正确填写姓名");
			return false;
		}
		
		if($("#starttime").val()==''){
			$("#modal").fadeIn().find('p').html("请正确填写考试开始时间");
			return false;
		}
		
		if($("#endtime").val()==''){
			$("#modal").fadeIn().find('p').html("请正确填写考试结束");
			return false;
		}
		
		if($("#integral").val() == '' || isNaN($("#integral").val()) == true){
			$("#modal").fadeIn().find('p').html("请正确填写监考积分");
			return false;
		}
		
		if($("#applyEndTime").val()==''){
			$("#modal").fadeIn().find('p').html("请正确填写申报截止时间");
			return false;
		}
		
		if($("#checkEndTime").val()==''){
			$("#modal").fadeIn().find('p').html("请正确填写确认截止时间");
			return false;
		}
		
		if($("#isSend").val()==''){
			$("#modal").fadeIn().find('p').html("请正确填写是否群发消息");
			return false;
		}
		
		if(statement.getContent() == ""){
			$("#modal").fadeIn().find('p').html("请正确填写监考责任书");
			return false;
		}
		
		if(info.getContent() == ""){
			$("#modal").fadeIn().find('p').html("请正确填写考试信息");
			return false;
		}
		
		if(applyNotice.getContent() == ""){
			$("#modal").fadeIn().find('p').html("请正确填写申报注意事项");
			return false;
		}
		
		$("#examinfo").ajaxSubmit(function(r) {
			if(r.Status=="success"){
				$(".modal").fadeIn();
			}else{
				$("#modal").fadeIn().find('p').html(r.Message);
			}
		});
	})
	$(".modal input").eq(1).click(function(){
		$.ajax({
			type: "post",
			url: "../admin/examAddTemplate",
			async: true,
			data: {
				"id":$("#msg_id").val(),
				"content": statement.getContent(),
				"title":$("#m_name").val()
			},
			success: function(data) {
				window.location.href="../admin/menu.jsp";
			},
			fail: function() {
				$("#modal").fadeIn().find('p').html("失败！请重试");
			}
		});
	});
	$(".back").click(function(){
		$(".modal").fadeOut();
	})
	$("#modal input").click(function(){
		$("#modal").fadeOut();
	})
	
	
//	$(".sub_publish").click(function() {
//		
//	})
	$(".modal input").eq(2).click(function(){
		window.location.href="../admin/menu.jsp";
	});
	
	
	
	
	//表单验证
	
	new input_test(":text");
	new input_test_nanl("#integral");
	
	
});