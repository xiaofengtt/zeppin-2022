<%@page import="org.apache.struts2.components.Include"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<s:action name="head_init" namespace="/admin" executeResult="true"
	ignoreContextParams="false">
</s:action>
<s:action name="left_init" namespace="/admin" executeResult="true">
	<s:param name="leftName">教师调入调出管理</s:param>
</s:action>
<link rel="stylesheet" href="../css/iframe.css">
	<style>
		div.jtable-main-container table.jtable {margin-top:60px}
		.stateDiv{margin-top:0;}
		div.jtable-main-container > div.jtable-title div.jtable-title-text{line-height:58px;font-size:18px;}
		.cui-menu{top:60px;}
		.select2-container .select2-choice > .select2-chosen{line-height:34px;}		
		div.jtable-main-container > table.jtable > thead th{text-align:center;}
		div.jtable-main-container > table.jtable > tbody > tr.jtable-data-row > td{text-align:center;}
		div.jtable-main-container > table.jtable > thead th.jtable-column-header div.jtable-column-header-container{height:auto;}
	</style>

<script src="../js/iframe.js"></script>
<link href="../css/select2.css" rel="stylesheet" />
<script src="../js/select2.js"></script>
<script src="../js/select2_locale_zh-CN.js"></script>
<div class="main">
<div class="iconDiv">
			<a class="btn btnMyself btn-screen">
				<span><img src="../img/sousuo.png" alt="icon">
					<b>筛选</b>
				</span>
				<p>
					<img src="../img/lanse.png" alt="蓝色三角">
					<b>筛选教师</b>
				</p>
			</a>
</div>
	<div class="listwrap">
		<div class="list-content clearfix" >
			<div class="alert alert-danger"
				style="display:none;margin:0 15px 15px;"></div>
			
			<div class="tablewrap">
				<div class="cui-menu" style="width:100%">
<!-- 					<a class="btn btn-primary btn-screen" style="margin-bottom:5px"> -->
<!-- 					筛选教师 </a> -->
					<form id="aduReplace" class="form-horizontal" role="form" style="display:none;padding-top:15px;">
						<div class="row clearfix">
							<div class="col-md-6">
								<div class="form-group">
									<label class="col-sm-2 control-label" for="">调出学校</label>
									<div class="controls">
										<input name="organization" data-val="true" data-val-number="" data-name="" id="organization" type="text" class="span3"  value=''>
									</div>
								</div>
							
							</div>
							
							<div class="col-md-6">
								<div class="form-group">
									<label class="col-sm-2 control-label" for="">姓名/身份证号</label>
									<div class="col-sm-9">
										<input class="form-control" name="teacherName" type="text">
									</div>
								</div>

							</div>

<!-- 							<div class="col-md-6"> -->
<!-- 								<div class="form-group"> -->
<!-- 									<label class="col-sm-2 control-label" for="">状态</label> -->
<!-- 									<div class="col-sm-9"> -->
<%-- 										<select class="form-control" id="aduStatus" name="status"> --%>
<!-- 											<option value="-1">全部</option> -->
<!-- 											<option value="0">未确认</option> -->
<!-- 											<option value="1">已通过</option> -->
<!-- 											<option value="2">未通过</option> -->
<%-- 										</select> --%>
<!-- 									</div> -->
<!-- 								</div> -->
<!-- 							</div> -->
							
						</div>
						<div class="row actionbar">
							<div class="text-center" style="padding-bottom:10px">
								<button class="btn btn-primary btn-myself" id="findRecord" type="button">查询</button>
							</div>
						</div>
					</form>
					<div class="stateDiv" style="margin-top:15px;">
						<label>状态&nbsp;：</label>
						<a class="light" value="-1">全部<span id="span1">(0)</span></a>
						<a class="" value="0">未确认<span id="span2">(0)</span></a>
						<a class="" value="1">已通过<span id="span3">(0)</span></a>
						<a class="" value="2">未通过<span id="span4">(0)</span></a>
					</div>
				</div>
				<div id="ProjectTableContainer"></div>
				
				<script type="text/javascript">
				
				$(".btn-screen").click(function(e){
					e.preventDefault();
					$('#aduReplace').toggle();
					if($('#aduReplace').css("display")=="block"){
						$("div.jtable-main-container table.jtable").css("margin-top","180px");
					}else{
						$("div.jtable-main-container table.jtable").css("margin-top","60px");
					}
				})
					$(document).ready(function() {
						$('#ProjectTableContainer')
								.jtable(
										{
											title : '<a href="../admin/teacherAdjust.jsp">教师调入调出管理</a> ',
											messages : zhCN, //Lozalize
											paging : true, //Enable paging
											pageSize : 10, //Set page size (default: 10)
											sorting : false, //Enable sorting
											defaultSorting : 'id ASC', //Set default sorting
											dialogShowEffect : 'drop',

											actions : {
												listAction : '../admin/teacherAdjust_getList.action'


											},
											fields : {
												id : {
													title : 'ID',
													key : true,
													width : '8%'
												},
												name : {
													title : '调出教师',
													width : 'auto',
													
													visibility : 'fixed'
												},
												idcard : {
													title : '身份证号',
													width : 'auto',
													
												},
												oorganization : {
													title : '调出学校',
													width : 'auto'
												},
												norganization : {
													title : '调入学校',
													width : 'auto',
													list : true
												},
												creator : {
													title : '申请人',
													width : 'auto',
													list : true
												},
												 creattime : {		
													 title : '申请时间',		
													 width : 'auto',		
													 list : true		
												},
												status : {
													title : '状态',
													width : '5%',
													list : true
												},
												isAdd : {
													title : '审核权限',
													width : 'auto',
													options:{"0":"无","1":"有"},
													list : false
												},
												custom : {
													title:'操作',
													width:'10%',
													sorting : false,
													edit : false,
													create : false,
													visibility : 'fixed',
													display : function(data) {
														var html = '';
														if(data.record.isAdd == 1){
															html += '<a data-fancybox-type="iframe" class="btn btn-create" href="../admin/teacherAdjust_initCheckpage.action?id='
																+ data.record.id
																+ '">调入并审核</a>';
														}
														
														html += '<a href="javascript:void(0)" onclick="audlist1(this)" data-url="../admin/teacherAdjust_getCheckList.action?id='
															+ data.record.id
															+ '">操作记录</a>'
														return html;
													}
												}
										
											},
											columnResizable : false,
											recordsLoaded :function (data) {
												$(".btn-create").colorbox({
													iframe : true,
													width : "865px",
													height : "300px",
													maxWidth : '1600px',
													opacity : '0.5',
													overlayClose : false,
													escKey : true
												});
											}
										});
	
								$('#ProjectTableContainer').jtable('load');
								//搜索
						        $('#findRecord').click(function (e) {
						            e.preventDefault();
						            $('#ProjectTableContainer').jtable('load', {
						            	organization:$('#organization').val(),
						            	teacher : $('input[name="teacherName"]').val(),
						            	assStatus : $('.stateDiv a.light').attr("value")
						            });
						            getStatusCount();
						            getNumber();
									return false;
						        });	
						        $(".stateDiv a").click(function(e){
									$(this).addClass("light").siblings().removeClass("light");
									e.preventDefault();
						            $('#ProjectTableContainer').jtable('load', {
						            	organization:$('#organization').val(),
						            	teacher : $('input[name="teacherName"]').val(),
						            	assStatus : $('.stateDiv a.light').attr("value")
						            });
						            getStatusCount();
									return false;
								})
						        
					});
					
					
				</script>
				
				
			</div>
		</div>

	</div>
</div>
<div class="recordlist left">
	<div class="arrow"></div>
	<div class="bd" id="recordlistcnt"></div>
</div>

<script>

	function audlist1(t) {
		var obj = $(t);
		var cUrl = obj.attr('data-url');
		var tt_offset = obj.offset();
		var tt_top = tt_offset.top;
		var tt_left = tt_offset.left;

		$.getJSON(
			cUrl,
			function(ret) {
				if (ret.Result == 'OK') {
					var html = '<table class="table table-striped"><thead><tr><th width="80">序号</th><th width="160">审核时间</th><th>详细</th></tr></thead><tbody>';
					if (ret.rows.length > 0) {
						$.each(ret.rows, function(i, c) {
							html += '<tr><td><b>' + c.id + '</b></td><td>'
									+ c.time + '</td><td>' + c.info
									+ '</td></tr>';
						})
					} else {
						html += '<tr><td colspan=3>暂无审核记录</td></tr>';
					}
					html += '</tbody></table>'
					$('#recordlistcnt').html(html);
					var new_div_left = tt_left
							- $('.recordlist').outerWidth() - 16;
					var new_div_top = tt_top
							- $('.recordlist').outerHeight() / 2 + 6;
					$('.recordlist').css({
						'top' : new_div_top + 'px',
						'left' : new_div_left + 'px'
					}).toggle();

				} else {
					alert('失败,' + ret.Message);
				}
			})

	}
	
	function getStatusCount(){
    	var organization = $('#organization').val();
    	var teacher = $('input[name="teacherName"]').val();
		$.getJSON('../admin/teacherAdjust_getStatusCount.action?', {
			organization : organization,
			teacher : teacher
		}, function(r) {
			var options = {
				currentPage : r.currentPage,
				totalPages : r.totalPage,
				shouldShowPage:function(type, page, current){
	                switch(type)
	                {
	                   default:
	                      return true;
	                }
	            },
				onPageClicked : function(e, originalEvent, type, page) {
					window.location = updateURLParameter(url(), 'page', page);
				}

			}
			$('#pagination').bootstrapPaginator(options);
		})
	}
	$(function(){
		getNumber();
	})
	//获取数值
	function getNumber(){
		var organization = $('#organization').val();
    	var teacher = $('input[name="teacherName"]').val();
		$.getJSON('../admin/teacherAdjust_getStatusCount.action?', {
			organization : organization,
			teacher : teacher
		}, function(r) {
			$("#span1").html("(" +r.totalCount+ ")");
			$("#span2").html("(" +r.noCheck+ ")");
			$("#span3").html("(" +r.checkPass+ ")");
			$("#span4").html("(" +r.checkNoPass+ ")");
		})
	}
	$(function(){
		$(document).on("click", function(e) {
			if (!$(e.target).parents().is('.recordlist'))
				$('.recordlist').hide();
		});
		function movieFormatResult(Options) {
			var html = Options.DisplayText;
			return html;
			
		}
		function movieFormatSelection(Options) {
			return Options.DisplayText;
		}
		$("#organization").select2({
		    placeholder: "请输入学校名称或拼音首字母",
		    minimumInputLength: 0,
			quietMillis : 1000,
			allowClear : true,
		    ajax: { 
		        url: "../base/organization_searchSchool.action",
		        dataType: 'json',
		        data: function (term, page) {
		            return {
		                search: term, // search term
		                page_limit: 10
		            };
		        },
		        results: function (data, page) {
		            return {results: data.Options};
					
		        }
		    },
			
			initSelection: function (element, callback) {
                element = $(element);
            	  var data = {id: element.val(), DisplayText: element.attr('data-name')};
			    callback(data);
            },
		    formatResult: movieFormatResult, 
		   formatSelection: movieFormatSelection, 
			dropdownCssClass: "bigdrop",
		    escapeMarkup: function (m) { return m; } 
		})
	})
	
</script>

<jsp:include page="foot.jsp"></jsp:include>