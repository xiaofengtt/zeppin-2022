var pageNum = '1';
var pagesize;
var count;
var flag = true;
var _name='';
var _sorts='';



$(".main_header a").click(function() {
	$(this).addClass("lighting").siblings().removeClass("lighting");
	pageNum = '1';
	flag=true;
	getList(_name,_sorts);
});

//导出列表
$("#download").click(function(){
	$(".easy_modal").fadeOut();
	$("#modal").fadeOut();
	var checkStatus = $(".filter-checkStatus").find(".lighting").attr("data-value");
	var type = $(".filter-type").find(".lighting").attr("data-value");
	var status = $(".filter-status").find(".lighting").attr("data-value");
	var isChief = $(".filter-isChief").find(".lighting").attr("data-value");
	var isMixed = $(".filter-isMixed").find(".lighting").attr("data-value");
	var downLoadUrl = "../admin/teacherDownloadList?checkStatus="+checkStatus+"&type="+type+"&status="+status+"&isChief="+isChief+"&isMixed="+isMixed+"&name="+$(".search").val()+"&sorts="+_sorts;
	$("#download").prop("href",downLoadUrl);
});

function searchBtn(){
	 _name = $(".search").val();
	 pageNum = '1';
	flag=true;
	getList(_name,_sorts);
	return false;
};
$(document).ready(function() {
	getList();
	
	//关闭简单的提示框
	$(".easy_modal input").eq(0).click(function(){
		$(".easy_modal").fadeOut(function(){
			$(this).css({"height":"100px"});
		});
	});
	
	
	
	function sort(sort) {	
		getList(_name,sort);
		flag = false;
	};


	//排序
	var DorA_1 = true;
	var DorA_2 = true;
	var DorA_3 = true;
	var DorA_4 = true;
	
	$(".select_bar_left li").eq(0).click(function() {
		DorA_2 = true;
		DorA_3 = true;
		DorA_4 = true;
		
		if(DorA_1==true){
			$(this).find('a').addClass('light');
			$(this).find('img').attr('src','../img/toptop_r_14.png');
			$(this).siblings().find('a').removeClass('light');
			$(this).siblings().find('img').attr('src','../img/toptop_b_14.png');
			_sorts = "name_desc";
			sort("name-desc");
			DorA_1=false;
		}else{
			$(this).find('img').attr('src','../img/bottombottom_r_14.png');
			_sorts = "name-asc";
			sort("name-asc");
			DorA_1=true;
		}

		
	});
	
	$(".select_bar_left li").eq(1).click(function() {
		DorA_1 = true;
		DorA_3 = true;
		DorA_4 = true;
		if(DorA_2==true){
			$(this).find('a').addClass('light');
			$(this).find('img').attr('src','../img/toptop_r_14.png');
			$(this).siblings().find('a').removeClass('light');
			$(this).siblings().find('img').attr('src','../img/toptop_b_14.png');
			_sorts = "sex-desc";
			sort("sex-desc");
			DorA_2=false;
		}else{
			$(this).find('img').attr('src','../img/bottombottom_r_14.png');
			_sorts = "sex-asc";
			sort("sex-asc");
			DorA_2=true;
		}

	});
	
	$(".select_bar_left li").eq(3).click(function() {
		DorA_1 = true;
		DorA_2 = true;
		DorA_4 = true;
		if(DorA_3==true){
			$(this).find('a').addClass('light');
			$(this).find('img').attr('src','../img/toptop_r_14.png');
			$(this).siblings().find('a').removeClass('light');
			$(this).siblings().find('img').attr('src','../img/toptop_b_14.png');
			_sorts = "createtime-desc";
			sort("createtime-desc");
			DorA_3=false;
		}else{
			$(this).find('img').attr('src','../img/bottombottom_r_14.png');
			_sorts = "createtime-asc";
			sort("createtime-asc");
			DorA_3=true;
		}
	});
	$(".select_bar_left li").eq(2).click(function() {
		DorA_1 = true;
		DorA_2 = true;
		DorA_3 = true;
		if(DorA_4==true){
			$(this).find('a').addClass('light');
			$(this).find('img').attr('src','../img/toptop_r_14.png');
			$(this).siblings().find('a').removeClass('light');
			$(this).siblings().find('img').attr('src','../img/toptop_b_14.png');
			_sorts = "checkStatus-desc";
			sort("checkStatus-desc");
			DorA_4=false;
		}else{
			$(this).find('img').attr('src','../img/bottombottom_r_14.png');
			_sorts = "checkStatus_asc";
			sort("checkStatus-asc");
			DorA_4=true;
		}
	});
	
	
	//排序结束
	
	
	
	
	
})





function getList(name, sorts) {
	var teacherinfo = '';
	if (typeof (name) != "undefined") {
		teacherinfo = name;
	}
	var sort = '';
	if (typeof (sorts) != "undefined") {
		sort = sorts;
	}
	var page = (typeof pageNum == 'undefined') ? 1 : pageNum;
	var pagesize = (typeof pagesize == 'undefined') ? 10 : pagesize;
	var url = '../admin/teacherList?';
	url += 'pagenum=' + page;
	url += '&pagesize=' + pagesize;
	if (teacherinfo != '') {
		url += '&name=' + teacherinfo;
	}
	if (sort != '') {
		url += '&sorts=' + sort;
	}

	var checkStatus = $(".filter-checkStatus").find(".lighting").attr("data-value");
	var type = $(".filter-type").find(".lighting").attr("data-value");
	var status = $(".filter-status").find(".lighting").attr("data-value");
	var isChief = $(".filter-isChief").find(".lighting").attr("data-value");
	var isMixed = $(".filter-isMixed").find(".lighting").attr("data-value");
	
	url += '&checkStatus=' + checkStatus;
	url += '&type=' + type;
	url += '&status=' + status;
	url += '&isChief=' + isChief;
	url += '&isMixed=' + isMixed;

	$.get(url, function(r) {
		if (r.Status == 'success') {
			if (r.TotalRecordCount > 0) {
				var template = $.templates('#queboxTpl');
				var html = template.render(r.Records);
				$('#queboxCnt').html(html);
				pagesize = r.pageSize;
				count = r.totalResultCount;
			} else {
				var html = '<div><span>没有数据！</span></div>'
				$('#queboxCnt').html(html);
			}
		} else {
			// layer.msg(r.message, {
			// time: 2000
			// })
		}
	}).done(function(r) {
		if (flag) {
			$('#select_page').Paging({
				pagesize : r.PageSize,
				count : r.TotalRecordCount,
				callback : function(page, size, count) {
					pageNum = page;
					getList(_name,_sorts);
					flag = false;
				}
			});
			$("#select_page").find(".ui-paging-container:last").siblings().remove();
		}
		$(".unauditing").unbind("change").change(function(){
			$(this).parent().siblings().find(".unauditing").prop("checked",false);
			if($(this).is(":checked")==true){
				$("#reason").val($(this).val());
			}
		})
		$("#reason").bind("input",function(){
			$(".unauditing").prop("checked",false);
		});
		
		$(".ban").click(function(){
			var _this=$(this);
			$("#reason").val('');
			$("#modal").fadeIn();
			$(".easy_modal").fadeOut(function(){
				$(this).css({"height":"100px"});
			});
			$("#submit").click(function(){
				if($("#modal textarea").val() != ''){
					$.ajax({
						type:"post",
						url:"../admin/teacherChange",
						async:true,
						data:{
							id:_this.find("input").attr("id"),
							status:0,
							reason:$("#modal textarea").val()
						},
						success:function(r){
							if(r.Status=="success"){
								$(".easy_modal p").html(r.Message);
								$(".easy_modal").fadeIn();
								$(".bg").fadeOut();
								$("#modal").fadeOut();
								$(".unauditing").prop("checked",false);
								 getList(_name,_sorts); 
							}else{
								$(".easy_modal p").html(r.Message);
								$(".easy_modal").fadeIn();
							}
						}
					});
				}else{
					$(".easy_modal p").html("请填写原因");
					$(".easy_modal").fadeIn();
				}
				
			});
		});
		$("#close").click(function(){
			$("#modal").fadeOut();
			$(".unauditing").prop("checked",false);
		})
		
		$(".click_card").unbind("click").click(function(){
			var this_index = $(this).siblings("input").val();
			if(r.Records[this_index].idcardPhoto.id == 1){
				$(".easy_modal").fadeIn().find("p").html("该教师未上传身份证图片");
			}else{
				$(".easy_modal").find("p").html("<img class='card_img' src="+r.Records[this_index].idcardPhoto.sourcePath+" />")
			}
			$(".easy_modal img").load(function(){
				$(".easy_modal").fadeIn();
				$(".easy_modal").css({"height":"100px"});
				$(".easy_modal").css({"height":$(".easy_modal").height() + $(".card_img").height()});
				
			})
		})
		
	});
}



//审核通过
function change(id,status){
	$(".easy_modal").fadeOut();
	$("#modal").fadeOut();
	$(".unauditing").prop("checked",false);
	$.ajax({
		type: "post",
		url: "../admin/teacherChange",
		async: true,
		data: {
			"id":id,
			"status": status,
			"reason":""
		},
		success: function(data) {
			if (data.Status == 'success') {
				$(".easy_modal p").html(data.Message);
				$(".easy_modal").fadeIn();
			    getList(_name,_sorts); 
			}else{
				$(".easy_modal p").html(data.Message);
				$(".easy_modal").fadeIn();
			}
		}
	});
}



