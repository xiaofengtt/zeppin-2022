<%@page import="org.apache.struts2.components.Include"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<s:action name="head_init" namespace="/admin" executeResult="true"
	ignoreContextParams="false">
</s:action>
<s:action name="left_init" namespace="/admin" executeResult="true">
	<s:param name="leftName">教师考试成绩记录</s:param>
</s:action>
<style>
		div.jtable-main-container table.jtable {margin-top:150px}
	</style>
<div class="main">
<div class="tablewrap">
<div class="cui-menu" style="width:100%">
	<form id="teacherExamRecords" class="form-horizontal">
		<div class="row clearfix">
			<div class="col-md-6">
				<div class="form-group">
					<label class="col-sm-2 control-label" for="">年份</label>
					<div class="col-sm-9">
						<select class="form-control" id="year" name="year" onchange="changeYear(this)">
							<option value="0">全部</option>
							<s:iterator value="searchYear" id="yt">
								<option value='<s:property value="#yt" />'><s:property value="#yt" /></option>
							</s:iterator>
						</select>
					</div>
				</div>
			</div>

			<div class="col-md-6">
				<div class="form-group">
					<label class="col-sm-2 control-label" for="">考试名称</label>
					<div class="col-sm-9">
						<select class="form-control" name="exam" id="exam">
							<option value="0">全部</option>
							<s:iterator value="searchExam" id="sp">
								<option value='<s:property value="sp" />'><s:property value="sp" /></option>
							</s:iterator>
						</select>
					</div>
				</div>
			</div>
		</div>
		<div class="row actionbar">
			<div class="text-center" style="padding-bottom:10px;margin-top:20px">			
				<button class="btn btn-primary" id="findRecord" type="button">查询</button>
				<a href="examList.xls" id="output" class="btn btn-primary">导出模板</a>
				<a class="ifrmbox" href="../admin/teacherExamRecords_uploadInit.action" data-fancybox-type="iframe" >
					<button type="button" id="upload" class="btn btn-primary">上传考试记录</button>
				</a>
			</div>
		</div>
	</form>				
	</div>

		<div id="examTableContainer">
		
		</div>

		<script type="text/javascript">
		$(function() {
			$(".ifrmbox").colorbox({
				iframe : true,
				width : "860px",
				height : "500px",
				opacity : '0.5',
				overlayClose : false,
				escKey : true
			});
		})
		
			$(document).ready(function() {
				$('#examTableContainer').jtable({
					title : '<a href="../admin/teacherExamRecords_initPage.action">教师考试成绩记录</a>',
					messages : zhCN, //Lozalize
					paging : true, //Enable paging
					pageSize : 10, //Set page size (default: 10)
					sorting : false, //Enable sorting
					defaultSorting : 'year DESC', //Set default sorting
					dialogShowEffect : 'drop',
					selecting: true, //Enable selecting
					selectOnRowClick : false,
		            multiselect: true, //Allow multiple selecting

					actions : {
						listAction : '../admin/teacherExamRecords_getList.action'

					},
					fields : {
						id : {
							title : 'ID',
							key : true,
							sorting:false,
							list:false
						},
						name : {
							title : '姓名',
							width : '10%',
							list : true,
							sorting:false
						},
						idcard : {
							title : '身份证',
							width : '10%',
							list : true,
							sorting:false
						},
						exam : {
							title : '考试名称',
							width : '10%',
							list : true,
							sorting:false
						},
						score : {
							title : '考试分数',
							list : true,
							width: '5%'

						}
					},
					columnResizable : false
				});
				$('#examTableContainer').jtable('load');
				//搜索
		        $('#findRecord').click(function (e) {
		            e.preventDefault();
		            $('#examTableContainer').jtable('load', {
		            	year: $('#year').val(),
		            	exam:$('select[name="exam"]').val(),
		            });
					return false;
		        });
			});
		</script>
	</div>
</div>
<script>
	function changeYear(t){
		var year = $(t).val();
		$.getJSON('../admin/teacherExamRecords_getExamList.action?year=' + year, function(data){
			if (data.exam.length > 0) {
				var cLis = '';
				$.each(data.exam, function(i, c) {
					cLis += '<option value="' +c.id + '">' + c.name	+ '</option>';
				});
				$('#exam').html(cLis);
			} else {
				$('#exam').html('<option value="0">暂无数据</option>');
			}
		})
	}
	
</script>

<jsp:include page="foot.jsp"></jsp:include>