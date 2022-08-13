<%@page import="org.apache.struts2.components.Include"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<s:action name="head_init" namespace="/admin" executeResult="true"
	ignoreContextParams="false">
</s:action>
<s:action name="left_init" namespace="/admin" executeResult="true">
	<s:param name="leftName">项目管理员管理</s:param>
</s:action>
	<style>
		div.jtable-main-container table.jtable {margin-top:60px}
		.stateDiv label{width:100px;}
		div.jtable-main-container > div.jtable-title div.jtable-title-text{line-height:58px;font-size:18px;}
		.cui-menu{top:60px;}
		.cui-menu-add{position: absolute;top:70px;z-index: 2;right:10px;}
		div.jtable-main-container > table.jtable > thead th{text-align:center;}
		html{min-width:1020px;}
		div.jtable-main-container > table.jtable > tbody > tr.jtable-data-row > td{text-align:center;}
		div.jtable-main-container > table.jtable > thead th.jtable-column-header div.jtable-column-header-container{height:auto;}
	</style>
<div class="main">

	<div class="tablewrap">

		<div class="cui-menu-add">
			<a class="btn btn-primary btn-create" data-fancybox-type="iframe"
				href="../admin/projectAdm_initPage.action">+ 添加项目管理员</a>&nbsp;&nbsp;
			<a class="btn btn-primary" href="../admin/projectAdmin_outputAdminList.action">+ 导出项目管理员</a>
		</div>
		<div id="searchbox" class="searchbox" style="top:15px;">
			<form class="search search_form" id="searchForm" action="#" method="POST">
				<fieldset>
					<label for="searchheader" class="placeholder overlabel">姓名</label>
					<input autocomplete="off" id="searchheader" value="" type="text" name="q">
					<input type="hidden" name="" value="">
					<div class="show_dropdown">
						<div class="dropdown" style="display: block;">
							<ul>
								<li><label for="type_title">按姓名</label><input type="radio" id="type_title" name="stype" value="name" checked="checked">
								</li>
								<li><label for="type_organization">按工作单位</label><input type="radio" id="type_organization" name="stype" value="organization">
								</li>
								<li><label for="type_idcard">按身份证号</label><input type="radio" id="type_idcard" name="stype" value="idcard">
								</li>
								
							</ul>
							<span class="bl"></span>
							<span class="br"></span>
						</div>
					</div>
					<button type="submit" id="searchBtn" class="search-button">Search</button>
				</fieldset>
			</form>
		</div>
		<div class="cui-menu">
			<div class="stateDiv" style="margin-top:15px;">
				<label>培训状态：</label>
				<a class="light" value="0">全部<span id="span1">(0)</span></a>					
				<a class="" value="1">正常<span id="span2">(0)</span></a>
				<a class="" value="2">停用<span id="span3">(0)</span></a>
			</div>
		</div>

		<div id="ProjectTableContainer"></div>

		<script type="text/javascript">
			$(document).ready(function() {
				$('#ProjectTableContainer')
						.jtable(
								{
									title : '<a href="../admin/projectAdmin_initPage.action">项目管理员管理</a> ',
									messages : zhCN, //Lozalize
									paging : true, //Enable paging
									pageSize : 10, //Set page size (default: 10)
									sorting : true, //Enable sorting
									defaultSorting : 'id ASC', //Set default sorting
									dialogShowEffect : 'drop',

									actions : {
										listAction : '../admin/projectAdmin_getProjectAdminList.action',
										
										deleteAction : '../admin/projectAdmin_opProjectAdmin?method=delete'

									},
									fields : {
										id : {
											title : 'ID',
											key : true,
											width : '5%'
										},
										name : {
											title : '姓名',
											width : '10%',
											inputClass : 'test',
											visibility : 'fixed'
										},
										sex : {
											title : '性别',
											width : '6%',
											options : {
												'0' : '无',
												'1' : '男',
												'2' : '女'
											}
										},
										ethnic : {
											title : '民族',
											width : '6%'
										},
										organization : {
											title : '工作单位',
											width : '13%',
											list : true
										},
										department : {
											title : '工作部门',
											width : '13%',
											list : true
										},
										jobDuty : {
											title : '工作职务',
											width : '11%',
											list : true
										},
										phone : {
											title : '办公电话',
											width : '5%',
											list : true
										},
										mobile : {
											title : '手机',
											width : '5%',
											list : true
										},
										status : {
											title : '状态',
											options : {
												'1' : '正常',
												'2' : '停用'
											},
											sorting : false,
											width : '6%'
										},
										customedit : {
											width : '6%',
											sorting : false,
											edit : false,
											create : false,
											visibility : 'fixed',
											display : function(data) {
												var html = '<a class="ifrbox jtable-cmd-btn" data-fancybox-type="iframe" href="../admin/projectAdm_initPage.action?id='
														+ data.record.id
														+ '">编辑</a>';
												return html;
											}
										
										},
										passwordedit : {
											title : '操作',
											width : '6%',
											sorting : false,
											visibility : 'fixed',
											display : function(data) {
												var html = '<a href="../admin/projectAdmin_opPassword.action?id='
														+ data.record.id
														+ '">重置密码</a>';
												return html;
											}
										
										}
										
									},
									columnResizable : false,
									deleteConfirmation : function(data) {
										data.deleteConfirmMessage = '确定要停用 <b style="color:#d9534f">'
												+ data.record.name
												+ '</b> 管理员身份?';
										$('#DeleteDialogButton').html('停用');
									},
									recordsLoaded :function (data) {
										$(".ifrbox").colorbox({
											iframe : true,
											width : "860px",
											height: "620px",
											opacity : '0.5',
											overlayClose : false,
											escKey : true
										});
										
										//删除按钮改成停用
									    $('#'+data.target.id).find('.jtable-delete-command-button').each(function(){
											$(this).attr('title','停用');
											$(this).find('span').text('停用');
										})  
										
									}

								});

						$('#ProjectTableContainer').jtable('load');
						
						//搜索
				        $('#searchForm').submit(function (e) {
				            e.preventDefault();
				            $('#ProjectTableContainer').jtable('load', {
								stype:$('input[name="stype"]:checked').val(),
				                q: $('#searchheader').val(),
				                status : $('.stateDiv a.light').attr("value")
				               
				            });
				            getStatusCount();
				            getNumber();
							return false;
				        });
						
				        $(".stateDiv a").click(function(e){
							$(this).addClass("light").siblings().removeClass("light");
							e.preventDefault();
							$('#ProjectTableContainer').jtable('load', {
								stype:$('input[name="stype"]:checked').val(),
				                q: $('#searchheader').val(),
				                status : $('.stateDiv a.light').attr("value")
				            });
				            getStatusCount();
							return false;
						})	
								
			});
					
			$(function() {//添加按钮
				$(".btn-create").colorbox({
					iframe : true,
					width : "860px",
					height : "620px",
					opacity : '0.5',
					overlayClose : false,
					escKey : true
				});
				
			
			})
		</script>





	</div>
</div>

<script>
$(function(){
	getNumber();
})
function getStatusCount(){
	var stype = $('input[name="stype"]:checked').val();
	var q = $('#searchheader').val();
	$.getJSON('../admin/projectAdmin_getProjectAdminStatusList.action?', {
		stype : stype,
		q : q,
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
function getNumber(){
	var stype = $('input[name="stype"]:checked').val();
	var q = $('#searchheader').val();
	$.getJSON('../admin/projectAdmin_getProjectAdminStatusList.action?', {
		stype : stype,
		q : q,
	}, function(r) {
		$("#span1").html("(" +r.TotalCount+ ")");
		$("#span2").html("(" +r.CountNormal+ ")");
		$("#span3").html("(" +r.CountDisable+ ")");
	})
}
</script>
<jsp:include page="foot.jsp"></jsp:include>