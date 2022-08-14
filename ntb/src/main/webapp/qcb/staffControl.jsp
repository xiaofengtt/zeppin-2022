<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="shortcut icon" href="./favicon.ico" type="image/x-icon" />
		<link rel="stylesheet" href="./css/base.css" />
		<link rel="stylesheet" href="./css/paging.css" />
		<link rel="stylesheet" href="./css/page.css" />
		<link rel="stylesheet" href="./css/table.css" />
		<link rel="stylesheet" href="css/uploadfile.css" />
		<link rel="stylesheet" href="./css/staffControl.css" />

		<!--[if lte IE 8]>
			<script src="js/html5shiv.js"></script>
			<script src="js/respond.js"></script>
		<![endif]-->

		<style type="text/css">
			::-webkit-input-placeholder {
				/* WebKit browsers */
				color: #CCCCCC;
			}
			
			:-moz-placeholder {
				/* Mozilla Firefox 4 to 18 */
				color: #CCCCCC;
			}
			
			::-moz-placeholder {
				/* Mozilla Firefox 19+ */
				color: #CCCCCC;
			}
			
			:-ms-input-placeholder {
				/* Internet Explorer 10+ */
				color: #CCCCCC;
			}
		</style>
</head>
<body>
	<input id="pageId" type="hidden" value="b092aca4-ff27-11e7-ac6d-fcaa14314cbe"/>
	<jsp:include page="header.jsp"/>
	<main>
		<jsp:include page="contentLeft.jsp"/>
		<div class="main-right">
			<div class="main-right-body box loadingOver module-1">
				<form:form id="searchForm" action="../rest/qcb/employee/export" method="post">
				<div class="search-bar">
					<div class="search-box">
						<input type="text" name="name" id="" value="" placeholder="姓名/手机号/身份证号" />
						<img src="./img/search.png" alt="" onclick="searchEmployee();" />
					</div>
					<div class="btn-box">
						<a class="btn" id="add">单个录入</a>
						<button class="btn" id="batchExport" type="submit">批量导出</button>
						<a class="btn batchImportBtn" id="batchImport" style="height:33px;">批量导入</a>
					</div>
					<a href="../resource/employeesModel.xlsx" target="_blank" class="download-excel color-gray">下载导入模版</a>					
				</div>
				</form:form>
				<div class="uploadLogo" style="text-align:center;border:none;display:none">
					<img id="bussnessLicessShow" style="border:0;max-width:100%;max-height:150px;margin:15px 0;display:none;">
					<div id="bussnessLicessId" style="border:0;max-width:100%;max-height:150px;margin:15px 0;display:none;">

					</div>
				</div>
				<div id="bussnessLicessAdd"><input type="hidden" name="file" id="bussnessLicess" value=""></div>
				<table cellspacing="0" cellpadding="0" class="table" style="margin-top:54px;">
					
				</table>
				<div id="pageTool">

				</div>
			</div>
			<div class="box module-5">
				<!-- 错误枚举 -->
				<h3 class="box-title">
					错误信息
				</h3>
				<div class="errorBox module-2-tablebox">					
					<table class="table tableContent">
						<tr class="first-tr fixTr"><th class="td-padding-item" width="30%">所在行数</th><th width="70%">错误原因</th></tr>
					</table>
				</div>
				<a class="lookMore packDown" title="点击查看更多"></a>
				<a class="lookMore packUp" title="点击收起"></a>
			</div>
			<div class="box module-5">
				<!-- 错误枚举 -->
				<h3 class="box-title">员工管理<small>-核对数据</small></h3>
				<div class="errorList module-2-tablebox">
					<table class="table tableContent">
						
					</table>
					
				</div>
				<div class="btn-g">
					<input type="button" class="btn-g-button reUploadsBtn" value="重新上传" id="reUploads" />
				</div>
			</div>
			<!-- loading -->
			<div class="loadingDiv"></div>
		</div>
	</main>
	<div class="bg bg-add">
			<div class="modal">
				<div class="modal-header">
					<h3 class="box-title iframe-title">录入员工信息</h3>
					<a id="modal-close"></a>
				</div>
				<div class="modal-body">
					<form:form id="addForm" action="../rest/qcb/employee/add" method="post">
						<div class="item">
							<span class="item-title">
							<i class="color-red">*</i>
							姓名：
						</span>
							<input type="text" name="name" placeholder="请输入姓名" />
							<p class="tips color-red">姓名不能为空</p>
						</div>
						<div class="item">
							<span class="item-title">
							<i class="color-red">*</i>
							身份证号：
						</span>
							<input type="text" name="idcard" placeholder="请输入身份证号" />
							<p class="tips color-red">请输入正确的身份证号</p>
						</div>
						<div class="item">
							<span class="item-title">
							<i class="color-red">*</i>
							手机：
						</span>
							<input type="text" name="mobile" placeholder="请输入手机号" />
							<p class="tips color-red">请输入正确的手机号</p>
						</div>
						<div class="item">
							<span class="item-title">
							<i class="color-red">*</i>
							所在部门：
						</span>
							<input type="text" name="department" placeholder="请输入所在部门" />
							<p class="tips color-red">请输入正确的部门</p>
						</div>
						<div class="item">
							<span class="item-title">
							<i class="color-red">*</i>
							所在职位：
						</span>
							<input type="text" name="duty" placeholder="请输入所在职位" />
							<p class="tips color-red">请输入正确的职位</p>
						</div>
						<div class="item">
							<span class="item-title">
							<i class="color-red">*</i>
							在职情况：
						</span>
							<select name="status">
								<option value="normal">在职</option>
								<option value="disable">离职</option>
							</select>
						</div>
					</form:form>
				</div>
				<input type="button" value="保存" class="sub" id="modal-add" />
			</div>
		</div>

		<div class="bg bg-edit">
			<div class="modal">
				<div class="modal-header">
					<h3 class="box-title iframe-title">修改员工信息</h3>
					<a id="modal-close-edit"></a>
				</div>
				<div class="modal-body">
					<form:form id="editBase" action="../rest/qcb/employee/edit" method="post">
						<input type="hidden" name="uuid" value="" />
						<div class="item">
							<span class="item-title">
							<i class="color-red">*</i>
							姓名：
						</span>
							<input type="text" class="realnameEdit" readonly="readonly" style="background:#eee;" />
						</div>
						<div class="item">
							<span class="item-title">
							<i class="color-red">*</i>
							身份证号：
						</span>
							<input type="text" class="idcardEdit" readonly="readonly" style="background:#eee;" />
						</div>
						<div class="item">
							<span class="item-title">
							<i class="color-red">*</i>
							手机：
						</span>
							<input type="text" class="mobileEdit" name="mobile" placeholder="请输入手机号" />
							<p class="tips color-red">请输入正确的手机号</p>
						</div>
						<div class="item">
							<span class="item-title">
							<i class="color-red">*</i>
							所在部门：
						</span>
							<input type="text" class="departmentEdit" name="department" placeholder="请输入所在部门" />
							<p class="tips color-red">请输入正确的部门</p>
						</div>
						<div class="item">
							<span class="item-title">
							<i class="color-red">*</i>
							所在职位：
						</span>
							<input type="text" class="dutyEdit" name="duty" placeholder="请输入所在职位" />
							<p class="tips color-red">请输入正确的职位</p>
						</div>
						<div class="item">
							<span class="item-title">
							<i class="color-red">*</i>
							在职情况：
						</span>
							<select name="status" class="statusEdit">
								<option value="normal">在职</option>
								<option value="disable">离职</option>
							</select>
						</div>
					</form:form>
				</div>
				<input type="button" value="保存" class="sub" id="modal-edit" />
			</div>
		</div>
	<jsp:include page="footer.jsp"/>
	<script src="./js/query.js"></script>
	<script src="./js/paging.js"></script>
	<script type="text/javascript" src="js/jquery.uploadfile.min.js" ></script>
	<script src="./js/staffControl.js?v=<%=Math.random()%>"></script>
</body>
</html>