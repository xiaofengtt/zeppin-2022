var pageNum = '1';
var pagesize;
var count;
var flag=true;
var exam = url("?exam");
var roomId;

var _sorts_1;
var _name_1;
var _sorts_2;
var _name_2;

function sort(sort) {
	getList(_name_1,sort);
}

function sort_t(sort) {
	getList_r(_name_2,sort);
}
$(document).ready(function() {
	getList();
	$(".easy_modal input").eq(0).click(function(){
		$(".easy_modal").fadeOut();
	})
	$(".main_right .slide_p").click(function(){
		if($(".main_right .main_header").height()==0){
			$(".main_right .main_header").height("175px");
			$(this).html("收起筛选项");
		}else{
			$(".main_right .main_header").height("0");
			$(this).html("查看筛选项");
		}
	});
	
	
	//微信推送
	$(".wechat_msg").click(function(){
		$(".history").fadeOut();
		$(".easy_modal").fadeOut();
		$("#edit_modal").fadeIn();
		$.ajax({
			type:"get",
			url:"../admin/examGet?id="+exam,
			async:true,
			success:function(r){
				invigilationNotice.ready(function() {
					invigilationNotice.setContent(r.Records.invigilationNotice);
				})
			}
		});
	});
	$("#submit_edit").click(function(){
		if(invigilationNotice.getContent() != ""){
			$.ajax({
				type:"post",
				url:"../admin/recordsSend",
				async:true,
				data:{
					exam:exam,
					invigilationNotice:invigilationNotice.getContent()
				},
				success:function(r){
					if(r.Status=='success'){
						$("#bg").hide();
						$("#edit_modal").fadeOut();
						$(".easy_modal").fadeIn().find('p').html('消息发送成功！');
					}else{
						$("#bg").hide();
						$(".easy_modal").fadeIn().find('p').html("消息发送失败，请稍后再试");
					}
				},
				beforeSend:function(){
					$("#bg").show();
				}
			});
		}else{
			$(".easy_modal").fadeIn().find('p').html("请填写监考注意事项！");
		}
	})
	$("#close").click(function(){
		$("#edit_modal").fadeOut();
	})
	
	
	
	$(".main_header a").click(function(){
	    $(this).addClass("lighting").siblings().removeClass("lighting");
	    pageNum_r = '1';
		flag_r=true;
	    getList_r();
	});
	
	
	//左侧考场排序
	var DorA_1 = true;
	var DorA_2 = true;
	var DorA_3 = true;
	
	$(".select_bar_left li").eq(0).click(function() {
		DorA_2 = true;
		DorA_3 = true;
		
		if(DorA_1==true){
			$(this).find('a').addClass('light');
			$(this).find('img').attr('src','../img/toptop_r_14.png');
			$(this).siblings().find('a').removeClass('light');
			$(this).siblings().find('img').attr('src','../img/toptop_b_14.png');
			sort("roomIndex-desc");
			_sorts_1 = "roomIndex-desc";
			DorA_1=false;
		}else{
			$(this).find('img').attr('src','../img/bottombottom_r_14.png');
			sort("roomIndex-asc");
			_sorts_1 = "roomIndex-asc";
			DorA_1=true;
		}
	});
	
	$(".select_bar_left li").eq(1).click(function() {
		DorA_1 = true;
		DorA_3 = true;
		if(DorA_2==true){
			$(this).find('a').addClass('light');
			$(this).find('img').attr('src','../img/toptop_r_14.png');
			$(this).siblings().find('a').removeClass('light');
			$(this).siblings().find('img').attr('src','../img/toptop_b_14.png');
			sort("roomAddress-desc");
			_sorts_1 = "roomAddress-desc";
			DorA_2=false;
		}else{
			$(this).find('img').attr('src','../img/bottombottom_r_14.png');
			sort("roomAddress-asc");
			_sorts_1 = "roomAddress-asc";
			DorA_2=true;
		}

	});
	
	$(".select_bar_left li").eq(2).click(function() {
		DorA_1 = true;
		DorA_2 = true;
		
		if(DorA_3==true){
			$(this).find('a').addClass('light');
			$(this).find('img').attr('src','../img/toptop_r_14.png');
			$(this).siblings().find('a').removeClass('light');
			$(this).siblings().find('img').attr('src','../img/toptop_b_14.png');
			sort("examnationInformation-desc");
			_sorts_1 = "examnationInformation-desc"
			DorA_3=false;
		}else{
			$(this).find('img').attr('src','../img/bottombottom_r_14.png');
			sort("examnationInformation-asc");
			_sorts_1 = "examnationInformation-asc"
			DorA_3=true;
		}
	});
	
	
	
	//右侧教师排序
	var sort_flag_0 = true;	
	var sort_flag_1 = true;	
	var sort_flag_2 = true;	
	var sort_flag_3 = true;	
	var sort_flag_4 = true;	
	var sort_flag_5 = true;	
	var sort_flag_6 = true;	
	var sort_flag_7 = true;	
	var sort_flag_8 = true;	
	
	$(".sort_th").eq(0).click(function() {
			sort_flag_1 = true;	
			sort_flag_2 = true;	
			sort_flag_3 = true;	
			sort_flag_4 = true;	
			sort_flag_5 = true;	
			sort_flag_6 = true;	
			sort_flag_7 = true;	
			sort_flag_8 = true;
		if(sort_flag_0 == true){
			$(this).addClass('light');
			$(this).find('img').attr('src','../img/toptop_r_14.png');
			$(this).siblings().removeClass('light');
			$(this).siblings().find('img').attr('src','');
			sort_t("name-desc");
			_sorts_2 = "name_desc";
			sort_flag_0 = false;
		}else{
			$(this).find('img').attr('src','../img/bottombottom_r_14.png');
			sort_t("name-asc");
			_sorts_2 = "name-asc";
			sort_flag_0 = true;
		}
	});
	
	$(".sort_th").eq(1).click(function() {
			sort_flag_0 = true;	
			sort_flag_2 = true;	
			sort_flag_3 = true;	
			sort_flag_4 = true;	
			sort_flag_5 = true;	
			sort_flag_6 = true;	
			sort_flag_7 = true;	
			sort_flag_8 = true;
		if(sort_flag_1 == true){
			$(this).addClass('light');
			$(this).find('img').attr('src','../img/toptop_r_14.png');
			$(this).siblings().removeClass('light');
			$(this).siblings().find('img').attr('src','');
			sort_t("sex-desc");
			_sorts_2 = "sex-desc";
			sort_flag_1 = false;
		}else{
			$(this).find('img').attr('src','../img/bottombottom_r_14.png');
			sort_t("sex-asc");
			_sorts_2 = "sex-asc";
			sort_flag_1 = true;
		}
	});
	
	$(".sort_th").eq(2).click(function() {
			sort_flag_0 = true;	
			sort_flag_1 = true;	
			sort_flag_3 = true;	
			sort_flag_4 = true;	
			sort_flag_5 = true;	
			sort_flag_6 = true;	
			sort_flag_7 = true;	
			sort_flag_8 = true;
		if(sort_flag_2 == true){
			$(this).addClass('light');
			$(this).find('img').attr('src','../img/toptop_r_14.png');
			$(this).siblings().removeClass('light');
			$(this).siblings().find('img').attr('src','');
			sort_t("type-desc");
			_sorts_2 = "type-desc";
			sort_flag_2 = false;
		}else{
			$(this).find('img').attr('src','../img/bottombottom_r_14.png');
			sort_t("type-asc");
			_sorts_2 = "type-asc";
			sort_flag_2 = true;
		}
	});
	
	
	$(".sort_th").eq(3).click(function() {
			sort_flag_0 = true;	
			sort_flag_1 = true;	
			sort_flag_2 = true;	
			sort_flag_4 = true;	
			sort_flag_5 = true;	
			sort_flag_6 = true;	
			sort_flag_7 = true;	
			sort_flag_8 = true;
		if(sort_flag_3 == true){
			$(this).addClass('light');
			$(this).find('img').attr('src','../img/toptop_r_14.png');
			$(this).siblings().removeClass('light');
			$(this).siblings().find('img').attr('src','');
			sort_t("intgral-desc");
			_sorts_2 = "intgral-desc";
			sort_flag_3 = false;
		}else{
			$(this).find('img').attr('src','../img/bottombottom_r_14.png');
			sort_t("intgral-asc");
			_sorts_2 = "intgral-asc";
			sort_flag_3 = true;
		}
	});
	
	$(".sort_th").eq(4).click(function() {
			sort_flag_0 = true;	
			sort_flag_1 = true;	
			sort_flag_2 = true;	
			sort_flag_3 = true;	
			sort_flag_5 = true;	
			sort_flag_6 = true;	
			sort_flag_7 = true;	
			sort_flag_8 = true;
		if(sort_flag_4 == true){
			$(this).addClass('light');
			$(this).find('img').attr('src','../img/toptop_r_14.png');
			$(this).siblings().removeClass('light');
			$(this).siblings().find('img').attr('src','');
			sort_t("invigilateCampus-desc");
			_sorts_2 = "invigilateCampus-desc";
			sort_flag_4 = false;
		}else{
			$(this).find('img').attr('src','../img/bottombottom_r_14.png');
			sort_t("invigilateCampus-asc");
			_sorts_2 = "invigilateCampus-asc";
			sort_flag_4 = true;
		}
	});
	
	
	$(".sort_th").eq(5).click(function() {
			sort_flag_0 = true;	
			sort_flag_1 = true;	
			sort_flag_2 = true;	
			sort_flag_3 = true;	
			sort_flag_4 = true;	
			sort_flag_6 = true;	
			sort_flag_7 = true;	
		if(sort_flag_5 == true){
			$(this).addClass('light');
			$(this).find('img').attr('src','../img/toptop_r_14.png');
			$(this).siblings().removeClass('light');
			$(this).siblings().find('img').attr('src','');
			sort_t("invigilateType-desc");
			_sorts_2 = "invigilateType-desc";
			sort_flag_5 = false;
		}else{
			$(this).find('img').attr('src','../img/bottombottom_r_14.png');
			sort_t("invigilateType-asc");
			_sorts_2 = "invigilateType-asc";
			sort_flag_5 = true;
		}
	});
	
	
	$(".sort_th").eq(6).click(function() {
			sort_flag_0 = true;	
			sort_flag_1 = true;	
			sort_flag_2 = true;	
			sort_flag_3 = true;	
			sort_flag_4 = true;	
			sort_flag_5 = true;	
			sort_flag_7 = true;
		if(sort_flag_6 == true){
			$(this).addClass('light');
			$(this).find('img').attr('src','../img/toptop_r_14.png');
			$(this).siblings().removeClass('light');
			$(this).siblings().find('img').attr('src','');
			sort_t("invigilateCount-desc");
			_sorts_2 = "invigilateCount-desc";
			sort_flag_6 = false;
		}else{
			$(this).find('img').attr('src','../img/bottombottom_r_14.png');
			sort_t("invigilateCount-asc");
			_sorts_2 = "invigilateCount-asc";
			sort_flag_6 = true;
		}
	});
	
	
	$(".sort_th").eq(7).click(function() {
			sort_flag_0 = true;	
			sort_flag_1 = true;	
			sort_flag_2 = true;	
			sort_flag_3 = true;	
			sort_flag_4 = true;	
			sort_flag_5 = true;	
			sort_flag_6 = true;	
		if(sort_flag_7 == true){
			$(this).addClass('light');
			$(this).find('img').attr('src','../img/toptop_r_14.png');
			$(this).siblings().removeClass('light');
			$(this).siblings().find('img').attr('src','');
			sort_t("mobile-desc");
			_sorts_2 = "mobile-desc";
			sort_flag_7 = false;
		}else{
			$(this).find('img').attr('src','../img/bottombottom_r_14.png');
			sort_t("mobile-asc");
			_sorts_2 = "mobile-asc";
			sort_flag_7 = true;
		}
	});
	//教师列表排序结束
	
	
	//导出教师考场安排
	function downloadTeacherInfo() {
		var exam = url('?exam') == null ? '' : url('?exam');
		var mUrl = '../admin/documentDownloadTeacherInfo?';
		mUrl += 'exam=' + exam
		window.location.href = mUrl;
	}
	$(".load_result").click(function(){
		downloadTeacherInfo();
	})
	
	//导出考场（按维度）
	$("#load_room").prop('href',"../admin/documentDownloadExamRoom?exam="+url('?exam'));
	
})








function searchBtn(){
	var _this = $(".search");
	_name_1 = _this.val();
	pageNum = '1';
	flag=true;
	getList(_name_1,_sorts_1);
	return false;
}
function searchBtn_1(){
	_name_2 = $("#search1").val();
	pageNum_r = '1';
	flag_r=true;
	getList_r(_name_2,_sorts_2);
	return false;

}
//页面左侧数据
function getList(name,sorts){
	$("#bg").show();
	var exam = url('?exam') == null ? "" : url('?exam');
	if(exam == ''){
		var html = '<tr class="nodata"><td colspan=11>数据获取错误！请返回重新获取</td></tr>'
		$('#queboxCnt').html(html);
		return false;
	}
	
	var roominfo='';
	if(typeof(name)!="undefined"){
		roominfo=name;
	}
	var sort='';
	if(typeof(sorts)!="undefined"){
		sort=sorts;
	}
	var page = (typeof pageNum == 'undefined') ? 1 : pageNum;
	var pagesize = (typeof pagesize == 'undefined') ? 10 : pagesize;
	var urls = '../admin/recordsDistributeRoomList?';
	urls += 'pagenum='+page;
	urls += '&pagesize='+pagesize;
	urls += '&exam='+exam;
	urls += '&roomId='+roomId;
	urls += "&tsorts=isChief0-desc";
	if(roominfo != ''){
		urls += '&roominfo='+roominfo;
	}
	
	if(sort != ''){
		urls += '&sorts='+sort;
	}
	
	$.get(urls,function(r) {
		if(r.Status =='success') {
			if(r.TotalRecordCount > 0){
				var template = $.templates('#queboxTpl');
				var html = template.render(r.Records);
				$('#queboxCnt').html(html);
				pagesize=r.pageSize;
				count=r.totalResultCount;
			}else{
				var html = '<tr class="nodata"><td colspan=11>没有数据！</td></tr>'
				$('#queboxCnt').html(html);
			}
		} else {
			var html = '<tr class="nodata"><td colspan=11>'+r.Message+'！</td></tr>'
			$('#queboxCnt').html(html);
		}
	}).done(function(r){
		$("#bg").hide();
		$("#bg").height($('body').height());   
		
		//点击左侧考场加载右侧教师列表
		$(".main_left_msg").unbind("click").click(function(){
			$(this).addClass("border_lighting").siblings().removeClass("border_lighting");
			roomId = $(this).find("input").attr("id");
			getList_r();
			$(".tip_tr").hide();
		});
		
		//分页
		if(flag){
			$('#select_page').Paging({
				pagesize: r.PageSize,
				count: r.TotalRecordCount,
				callback: function(page, size, count) {
					pageNum = page;
					getList(_name_1,_sorts_1);
					flag = false;
				}
			});
			$("#select_page").find(".ui-paging-container:last").siblings().remove();
		}
		
		//点击红叉删除该监考老师
		$(".main_left_msg i").unbind("click").click(function(){
			var _this=$(this);
			removeTeacher();
			return false
			function removeTeacher(){
				$.ajax({
					type:"post",
					url:"../admin/recordsCancle",
					async:true,
					data:{
						"id":_this.siblings("input").attr("id")
					},
					beforeSend:function(){
						$("#bg").show();
					},
					success:function(){
						getList(_name_1,_sorts_1);
						getList_r(_name_2,_sorts_2);
					}
				});
			}
			return false
		});
		
	});
}



//-------------------------
//页面右侧数据


var pageNum_r = '1';
var pagesize_r;
var count_r;
var flag_r=true;

var teacherStatus;

function getList_r(name,sorts){
	var exam = url('?exam') == null ? "" : url('?exam');
	if(exam == ''){
		var html = '<tr class="nodata"><td colspan=8>数据获取错误！请返回重新获取</td></tr>'
		$('#teacherList').html(html);
		return false;
	}
	
	
	var teacherinfo='';
	if(typeof(name)!="undefined"){
		teacherinfo=name;
	}
	var sort='';
	if(typeof(sorts)!="undefined"){
		sort=sorts;
	}
	var type = $(".filter-type").find(".lighting").attr("data-value");
	var isChief = $(".filter-isChief").find(".lighting").attr("data-value");
	var isMixed = $(".filter-isMixed").find(".lighting").attr("data-value");
	var invigilateCampus = $(".filter-invigilateCampus").find(".lighting").attr("data-value");
	var invigilateType = $(".filter-invigilateType").find(".lighting").attr("data-value");

	
	var page = (typeof pageNum_r == 'undefined') ? 1 : pageNum_r;
	var pagesize = (typeof pagesize_r == 'undefined') ? 10 : pagesize_r;
	var urls = '../admin/recordsDistributeTeacherList?';
	urls += 'pagenum='+page;
	urls += '&pagesize='+pagesize;
	urls += '&exam='+exam;
	urls += '&roomId='+roomId;
	
	urls += '&type='+type;
	urls += '&isChief='+isChief;
	urls += '&isMixed='+isMixed;
	urls += '&invigilateCampus='+invigilateCampus;
	urls += '&invigilateType='+invigilateType;
	
	if(teacherinfo != ''){
		urls += '&teacherinfo='+teacherinfo;
	}
	if(sort != ''){
		urls += '&sorts='+sort;
	}else{
		urls += '&sorts=room0-desc';
	}
	
	$.get(urls,function(r) {
		if(r.Status =='success') {

			if(r.TotalRecordCount > 0){
//				$("#bg").show();
				
				var template = $.templates('#teacherListTpl');
				var html = template.render(r.Records);
				$('#teacherList').html(html);
				pagesize=r.pageSize;
				count=r.totalResultCount;
			}else{
				var html = '<tr class="nodata"><td colspan=11>没有数据！</td></tr>'
				$('#teacherList').html(html);
			}
		} else {
			var html = '<tr class="nodata"><td colspan=11>'+r.Message+'！</td></tr>'
			$('#teacherList').html(html);
		}
	}).done(function(r){
		$("#bg").height($('body').height());            
		if(flag_r){
			$('#select_page_r').Paging({
				pagesize: r.PageSize,
				count: r.TotalRecordCount,
				callback: function(page, size, count) {
					pageNum_r = page;
					getList_r(_name_2,_sorts_2);
					$("#bg").hide();
					flag_r = false;

				}
			});
			$("#select_page_r").find(".ui-paging-container:last").siblings().remove();
		}
		
		//点击checkbox 发送请求添加主考/副考教师
		$(".checkbox").click(function(){
			var _this=$(this);
			$(this).attr("disabled",true);				//点击checkbox后，禁用该对象
			$(this).siblings().attr("checked",false);	//相邻的兄弟checkbox对象取消禁用，点击状态为false
			$(this).siblings().attr("disabled",false);
			var checkedArr = $(this).parent().find(".checkbox");		//获取点击的当前教师对应的 主/副考 checkedbox 对象
			for(var i = 0;i<checkedArr.length;i++){					//循环checkedbox对象
				if(checkedArr.eq(i).is(":checked")){					//如果其中一个为被选中状态
					if(i==0){										//再次判断哪一个被选中
						teacherStatus='1';							//如果是0，则第一个被选中 为主考
						addTeacher();
					}else{
						teacherStatus='0';							//反之，则第二个被选中，为副考
						addTeacher();
					}
				}
			}
			function addTeacher(){
				$.ajax({
					type:"post",
					url:"../admin/recordsDistribute",
					async:true,
					data:{
						"id":_this.parent().find(".teacher_id").attr("id"),
						"room":roomId,
						"isChief":teacherStatus
					},
					beforeSend:function(){
						$("#bg").show();	
					},
					success:function(){
						getList(_name_1,_sorts_1);
					}
				});
			};
		});
	
		//点击教师姓名查看历史监考纪录
		$(".click_name").click(function(){
			$("#edit_modal").fadeOut();
			$(".easy_modal").fadeOut();
			$.ajax({
				type:"post",
				url:"../admin/teacherHistoryList",
				async:true,
				data:{
					id:$(this).find('.t_id').attr('id'),
					pagesize:'5',
					pagenum:'1'
				},
				success:function(r){
					if(r.Status=='success'){
						if(r.Records!=""){
							var template = $.templates('#historyTpl');
							var html = template.render(r.Records);
							$("#history_msg").html(html);
						}else{
							$("#history_msg").html("<tr><td colspan='6'>该教师暂无历史记录</td></tr>");
						}
					}
				}
			});
			$(".history").fadeIn();
			$(".history a").click(function(){
				$(".history").fadeOut();
			})
		})
	})//.done
};