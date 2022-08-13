<%@page import="org.apache.struts2.components.Include"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<s:action name="head_init" namespace="/admin" executeResult="true"
	ignoreContextParams="false">
</s:action>
<s:action name="left_init" namespace="/admin" executeResult="true">
	<s:param name="leftName">日志记录</s:param>
</s:action>
<style>
	div.jtable-main-container table.jtable {margin-top:10px}
</style>
<div class="main">
	<div class="iconDiv">
		<a class="btn btn-lg btnMyself btn-screen">
			<span><img src="../img/sousuo.png" alt="icon">
			<b>筛选</b>
			</span>
			<p>
				<img src="../img/lanse.png" alt="蓝色三角">
				<b>筛选操作</b>
			</p>
		</a>
	</div>
	<div class="tablewrap">
		<div class="cui-menu" style="width:100%">
			<form id="reprtTask" class="form-horizontal" role="form" style="display:none;">
			<div class="clearfix">
				<div class="form-group">
					<label class="col-sm-1 control-label" for="">操作表</label>
					<div class="col-sm-5">
						<select class="form-control" name="tableName" id="tableName">
							<option value="All">全部</option>
							<s:iterator value="tableList" id="rt">
								<option value='<s:property value="#rt" />' ><s:property value="#rt" /></option>
							</s:iterator>
						</select>
					</div>
					<label class="col-sm-1 control-label" for="">操作行ID</label>
					<div class="col-sm-5">
						<input class="form-control" name="tableId" type="text">
					</div>
				</div>
				<div class="row actionbar">
					<div class="text-center" style="padding-bottom:10px">
						<button class="btn btn-primary btn-myself" id="findRecord" type="submit">查询</button>
					</div>
				</div>
	
			</div>
			</form>
		</div>
		<div id="ProjectTableContainer"></div>

		<script type="text/javascript">
		$(".btn-screen").click(function(e) {
			e.preventDefault();
			$('#reprtTask').toggle();
			if($('#reprtTask').css("display")=="block"){
				$("div.jtable-main-container table.jtable").css("margin-top","100px");
			}else{
				$("div.jtable-main-container table.jtable").css("margin-top","10px");
			}
		})
			$(document).ready(function() {
				$('#ProjectTableContainer')
						.jtable(
								{
									title : '<a href="../admin/log_initPage.action">日志记录</a> ',
									messages : zhCN, //Lozalize
									paging : true, //Enable paging
									pageSize : 50, //Set page size (default: 10)
									sorting : false, //Enable sorting
									defaultSorting : 'id ASC', //Set default sorting
									dialogShowEffect : 'drop',

									actions : {
										listAction : '../admin/log_getLogList.action'
									},
									fields : {
										id : {
											title : 'ID',
											key : true,
											width : '5%'
										},
										type : {
											title : '操作类型',
											width : '20%',
										},
										user : {
											title : '操作人',
											width : '40%',
										},
										tableName : {
											title : '操作表',
											width : '20%'
										},
										tableId : {
											title : '操作行ID',
											width : '20%'
										}
									},
									columnResizable : false,
								});

						$('#ProjectTableContainer').jtable('load');
						$('#findRecord').click(function (e) {
				            e.preventDefault();
				            $('#ProjectTableContainer').jtable('load', {
								tableName:$('select[name="tableName"]').val(),
				                tableId:$('input[name="tableId"]').val()
		               		
				            });
							return false;
				        });
								
			});
		</script>

	</div>
</div>


<jsp:include page="foot.jsp"></jsp:include>