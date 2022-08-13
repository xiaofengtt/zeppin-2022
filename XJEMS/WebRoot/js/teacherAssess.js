var pageNum = '1';
var pagesize;
var count;
var flag=true;
var _name;
var _sorts;
function searchBtn(){
	var _this = $(".search");
	_name = _this.val();
	pageNum = '1';
	flag=true;
	getList(_name);
	return false;
}
function sort(sort) {	
	getList(_name,sort);
}

$(document).ready(function() {
	
	getList();
	$(".easy_modal input").eq(0).click(function(){
		$(".easy_modal").fadeOut();
	})
	//性别排序
	var sort_flag_0 = true;	
	var sort_flag_1 = true;	
	var sort_flag_2 = true;	
	var sort_flag_3 = true;	
	var sort_flag_4 = true;	
	var sort_flag_5 = true;	
	var sort_flag_6 = true;	
	var sort_flag_7 = true;	
	var sort_flag_8 = true;	
	var sort_flag_9 = true;
	var sort_flag_10 = true;
	
	$(".sort_th").eq(0).click(function() {
			sort_flag_1 = true;	
			sort_flag_2 = true;	
			sort_flag_3 = true;	
			sort_flag_4 = true;	
			sort_flag_5 = true;	
			sort_flag_6 = true;	
			sort_flag_7 = true;	
			sort_flag_8 = true;
			sort_flag_9 = true;
			
		if(sort_flag_0 == true){
			$(this).addClass('light');
			$(this).find('img').attr('src','../img/toptop_r_14.png');
			$(this).siblings().removeClass('light');
			$(this).siblings().find('img').attr('src','');
			sort("name-desc");
			_sorts = "name_desc";
			sort_flag_0 = false;
		}else{
			$(this).find('img').attr('src','../img/bottombottom_r_14.png');
			sort("name-asc");
			_sorts = "name-asc";
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
			sort_flag_9 = true;
		if(sort_flag_1 == true){
			$(this).addClass('light');
			$(this).find('img').attr('src','../img/toptop_r_14.png');
			$(this).siblings().removeClass('light');
			$(this).siblings().find('img').attr('src','');
			sort("ethnic-desc");
			_sorts = "ethnic-desc";
			sort_flag_1 = false;
		}else{
			$(this).find('img').attr('src','../img/bottombottom_r_14.png');
			sort("ethnic-asc");
			_sorts = "ethnic-asc";
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
			sort_flag_9 = true;
		if(sort_flag_2 == true){
			$(this).addClass('light');
			$(this).find('img').attr('src','../img/toptop_r_14.png');
			$(this).siblings().removeClass('light');
			$(this).siblings().find('img').attr('src','');
			sort("sex-desc");
			_sort = "sex-desc";
			sort_flag_2 = false;
		}else{
			$(this).find('img').attr('src','../img/bottombottom_r_14.png');
			sort("sex-asc");
			_sorts = "sex-asc";
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
			sort_flag_9 = true;
		if(sort_flag_3 == true){
			$(this).addClass('light');
			$(this).find('img').attr('src','../img/toptop_r_14.png');
			$(this).siblings().removeClass('light');
			$(this).siblings().find('img').attr('src','');
			sort("type-desc");
			_sorts = "type-desc";
			sort_flag_3 = false;
		}else{
			$(this).find('img').attr('src','../img/bottombottom_r_14.png');
			sort("type-asc");
			_sorts = "type-asc";
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
			sort_flag_9 = true;
		if(sort_flag_4 == true){
			$(this).addClass('light');
			$(this).find('img').attr('src','../img/toptop_r_14.png');
			$(this).siblings().removeClass('light');
			$(this).siblings().find('img').attr('src','');
			sort("isChief0-desc");
			_sorts = "isChief0-desc";
			sort_flag_4 = false;
		}else{
			$(this).find('img').attr('src','../img/bottombottom_r_14.png');
			sort("isChief0-asc");
			_sorts = "isChief0-asc";
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
			sort_flag_8 = true;
			sort_flag_9 = true;
		if(sort_flag_5 == true){
			$(this).addClass('light');
			$(this).find('img').attr('src','../img/toptop_r_14.png');
			$(this).siblings().removeClass('light');
			$(this).siblings().find('img').attr('src','');
			sort("isConfirm0-desc");
			_sorts = "isConfirm0-desc";
			sort_flag_5 = false;
		}else{
			$(this).find('img').attr('src','../img/bottombottom_r_14.png');
			sort("isConfirm0-asc");
			_sorts = "isConfirm0-asc";
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
			sort_flag_8 = true;
			sort_flag_9 = true;
		if(sort_flag_6 == true){
			$(this).addClass('light');
			$(this).find('img').attr('src','../img/toptop_r_14.png');
			$(this).siblings().removeClass('light');
			$(this).siblings().find('img').attr('src','');
			sort("applytime0-desc");
			_sorts = "applytime0-desc";
			sort_flag_6 = false;
		}else{
			$(this).find('img').attr('src','../img/bottombottom_r_14.png');
			sort("applytime0-asc");
			_sorts = "applytime0-asc";
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
			sort_flag_8 = true;	
			sort_flag_9 = true;	
		if(sort_flag_7 == true){
			$(this).addClass('light');
			$(this).find('img').attr('src','../img/toptop_r_14.png');
			$(this).siblings().removeClass('light');
			$(this).siblings().find('img').attr('src','');
			sort("credit0-desc");
			_sorts = "credit0-desc";
			sort_flag_7 = false;
		}else{
			$(this).find('img').attr('src','../img/bottombottom_r_14.png');
			sort("credit0-asc");
			_sorts = "credit0-asc";
			sort_flag_7 = true;
		}
	});
	
	$(".sort_th").eq(8).click(function() {
			sort_flag_0 = true;	
			sort_flag_1 = true;	
			sort_flag_2 = true;	
			sort_flag_3 = true;	
			sort_flag_4 = true;	
			sort_flag_5 = true;	
			sort_flag_6 = true;	
			sort_flag_7 = true;	
			sort_flag_9 = true;	
		if(sort_flag_8 == true){
			$(this).addClass('light');
			$(this).find('img').attr('src','../img/toptop_r_14.png');
			$(this).siblings().removeClass('light');
			$(this).siblings().find('img').attr('src','');
			sort("creditStatus0-desc");
			_sorts = "creditStatus0-desc";
			sort_flag_8 = false;
		}else{
			$(this).find('img').attr('src','../img/bottombottom_r_14.png');
			sort("creditStatus0-asc");
			_sorts = "creditStatus0-asc";
			sort_flag_8 = true;
		}
	});
	
	$(".sort_th").eq(9).click(function() {
			sort_flag_0 = true;	
			sort_flag_1 = true;	
			sort_flag_2 = true;	
			sort_flag_3 = true;	
			sort_flag_4 = true;	
			sort_flag_5 = true;	
			sort_flag_6 = true;	
			sort_flag_7 = true;	
			sort_flag_8 = true;	
		if(sort_flag_9 == true){
			$(this).addClass('light');
			$(this).find('img').attr('src','../img/toptop_r_14.png');
			$(this).siblings().removeClass('light');
			$(this).siblings().find('img').attr('src','');
			sort("status0-desc");
			_sorts = "status0-desc";
			sort_flag_9 = false;
		}else{
			$(this).find('img').attr('src','../img/bottombottom_r_14.png');
			sort("status0-asc");
			_sorts = "status0-asc";
			sort_flag_9 = true;
		}
	});
	
	
	
})//$(document).ready


function getList(name,sorts){
	var exam = url('?exam') == null ? '' : url('?exam');
	var page = (typeof pageNum == 'undefined') ? 1 : pageNum;
	var pagesize = (typeof pagesize == 'undefined') ? 50 : pagesize;
	var teacherinfo='';
	if(typeof(name)!="undefined"){
		teacherinfo=name;
	}
	var sort='';
	if(typeof(sorts)!="undefined"){
		sort=sorts;
	}
	var mUrl = '../admin/recordsConfirmList?';
	mUrl += 'exam='+exam;
	mUrl += '&pagenum='+page;
	mUrl += '&pagesize='+pagesize;
	mUrl += '&type=&isChief=';
	if(teacherinfo != ''){
		mUrl += '&teacherinfo='+teacherinfo;
	}
	if(sort != ''){
		mUrl += '&sorts='+sort;
	}
	var isCredit = $(".filter-isCredit").find(".lighting").attr("data-value");
	mUrl += '&isCredit='+isCredit;
	$.get(mUrl,function(r) {
		if(r.Status =='success') {
//			r.totalPageCount && $('.quepager').html('<span style="font-weight:normal">'+ r.pageNum +'</span>/'+ r.totalPageCount);
			if(r.TotalRecordCount > 0){
				var template = $.templates('#queboxTpl');
				var html = template.render(r.Records);
				$('#queboxCnt').html(html);
				pagesize=r.pageSize;
				count=r.totalResultCount;
			}else{
				var html = '<tr class="nodata"><td colspan="14">没有数据！</td></tr>'
				$('#queboxCnt').html(html);
			}
		}
	}).done(function(r){
		if(flag){
			$('#select_page').Paging({
				pagesize: r.PageSize,
				count: r.TotalRecordCount,
				callback: function(page, size, count) {
					pageNum = page;
					getList(_name,_sorts);
					flag = false;	
				}
			});	
			$("#select_page").find(".ui-paging-container:last").siblings().remove();
		}
		
		//筛选
		$(".filter-isCredit a").unbind("click").click(function(){
		    $(this).addClass("lighting").siblings().removeClass("lighting");
		    pageNum = '1';
			flag=true;
		    getList(_name,_sorts);
		})

		
		
		//教师评分
		
		var this_assess;
		$(".assess").unbind("click").click(function(){
			this_assess = $(this);
			$("#select").val('');
			$("#text").val('');
			$(".modal").fadeIn();
			$(".modal_1").fadeOut();
			$(".easy_modal").fadeOut();
			$.ajax({
				type:"post",
				url:"../admin/recordsGetIntgral",
				async:true,
				data:{
					"id":this_assess.siblings('input').prop('id')
				},
				success:function(r){
					$("#select").val(r.Records.credit);
					$("#text").val(r.Records.reason);
				}
			});
		});
		$("#submit").unbind("click").click(function(){
			if($("#select").val() != "" && isNaN($("#select").val())==false  && $("#select").val().length < 5){
				$.ajax({
					type:"post",
					url:"../admin/recordsIntgralUpdate",
					async:true,
					data:{
						id:this_assess.siblings('input').attr('id'),
						score:$("#select").val(),
						reason:$("#text").val()
					},
					success:function(r){
						$(".easy_modal p").html(r.Message);
						$(".easy_modal").fadeIn();
						$(".modal").fadeOut();
	//						window.location.reload();
						getList();
					}
				});
			}else{
				$(".easy_modal p").html("请正确填写分数");
				$(".easy_modal").fadeIn();
			}
			
		})
		
		//教师批量打分
		var assessNum=[];
		var assessNumStr;
		
		$(".checkbox").unbind("click").click(function(){
			var thisId = $(this).siblings('input').attr('id');
			if($(this).is(':checked')){
				assessNum.push(thisId);
				assessNumStr = assessNum.join(',');
			}else{
				if(assessNum.indexOf(thisId)!=-1){
					assessNum.splice(assessNum.indexOf(thisId),1);	
					assessNumStr = assessNum.join(',');
				}
			}
		});
		$("#select_all").unbind("click").click(function(){
			assessNum=[];
			assessNumStr='';
			for(var i = 0; i < $(".checkbox").length; i++){
				$(".checkbox").eq(i).prop('checked',true);
				assessNum.push($(".checkbox").eq(i).siblings('input').attr('id'));
				assessNumStr = assessNum.join(',');
				console.log(assessNumStr);
			}
		})
		$("#unselect_all").unbind("click").click(function(){
			for(var i = 0; i < $(".checkbox").length; i++){
				var checkboxId = $(".checkbox").eq(i).siblings('input').attr('id');
				if($(".checkbox").eq(i).is(':checked')){
					$(".checkbox").eq(i).prop('checked',false);
					assessNum.splice(assessNum.indexOf(checkboxId),1);	
					assessNumStr = assessNum.join(',');
					console.log(assessNumStr);
				}else{
					$(".checkbox").eq(i).prop('checked',true);
					assessNum.push(checkboxId);
					assessNumStr = assessNum.join(',');
					console.log(assessNumStr);
				}
			}
		});
//		
//		
//		
//		没有教师被选中时，组织弹框
		function isTure(ele,modal){
			for(var i = 0;i<$(ele).length;i++){
				if($(ele).eq(i).is(":checked")){
					$(modal).fadeIn();
					return false;
				}
			}
			$(".easy_modal p").html("请先勾线教师后在进行批量操作");
			$(".easy_modal").fadeIn();
		};
		$("#assess_all").unbind("click").click(function(){
			$(".select").val('');
			$(".text").val('');
			$(".modal").fadeOut();
			$(".easy_modal").fadeOut();
			new isTure(".checkbox",".modal_1");
			
		});
		$(".submit").unbind("click").click(function(){
			if($(".select").val() != '' && isNaN($(".select").val())==false && $(".select").val().length < 5){
				$.ajax({
					type:"post",
					url:"../admin/recordsIntgralUpdate",
					async:true,
					data:{
						id:assessNumStr,
						score:$(".select").val(),
						reason:$(".text").val()
					},
					success:function(r){
						if(r.Status == "success"){
							$(".modal_1").fadeOut();
							$(".easy_modal p").html("批量打分成功");
							$(".easy_modal").fadeIn();
							getList();
						}else{
							$(".easy_modal p").html(r.Message);
							$(".easy_modal").fadeIn();	
						}
					}
				});
			}else{
				$(".easy_modal p").html("请正确填写分数");
				$(".easy_modal").fadeIn();
			}
			
		})
		
		
		
		
		$(".close").unbind("click").click(function(){
			$(".modal_1").fadeOut();
		});
		
		$("#close").unbind("click").click(function(){
			$(".modal").fadeOut();
		});
		
	});
}
