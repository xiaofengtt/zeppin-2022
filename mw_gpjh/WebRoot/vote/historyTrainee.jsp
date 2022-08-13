<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>学员管理</title>
<link rel="stylesheet" type="text/css" media="screen"
	href="css/jquery-ui.min.css" />
<link rel="stylesheet" type="text/css" media="screen"
	href="css/ui.jqgrid.css" />
<script src="js/jquery-1.7.2.min.js" type="text/javascript"></script>
<script src="js/i18n/grid.locale-cn.js" type="text/javascript"></script>
<script src="js/jquery.jqGrid.min.js" type="text/javascript"></script>

<script src="js/jquery-ui-1.10.3.custom.min.js" type="text/javascript">
	<script src="js/jquery-ui-i18n.js" type="application/x-javascript">
</script>
<script src="js/jquery.ui.datepicker-zh-CN.js"
	type="application/x-javascript"></script>
</script>



<script src="js/historyTrainee.js" type="text/javascript"></script>

<script type="text/javascript">
	var role = '<s:property value="#parameters.role" />';
	var userid = '<s:property value="#parameters.userid" />';

</script>

</head>

<body>
	<div class="container">
		<div class="content">
			<div class="list">
				<table id="list2">
				</table>
				<div id="pager2"></div>
			</div>

			<div id="addDiv" style="display:none;">
				<div>
					<label>省份*:</label> <select name="addProvince" id="addProvince" style="width:333px;"></select>
				</div>

				<div>
					<label>所属项目*:</label> 
					<select id="addProjectParent" name="addProjectParent"  style="width:333px;">
						<option value="“国培计划（2010）”中西部项目（置换脱产）">“国培计划（2010）”中西部项目（置换脱产）</option>
						<option value="“国培计划（2010）”中西部项目（短期集中）">“国培计划（2010）”中西部项目（短期集中）</option>
						<option value="“国培计划（2011）”中西部项目（置换脱产）">“国培计划（2011）”中西部项目（置换脱产）</option>
						<option value="“国培计划（2011）”中西部项目（短期集中）">“国培计划（2011）”中西部项目（短期集中）</option>
						<option value="“国培计划（2011）”幼师国培项目（置换脱产）">“国培计划（2011）”幼师国培项目（置换脱产）</option>
						<option value="“国培计划（2011）”幼师国培项目（短期集中）">“国培计划（2011）”幼师国培项目（短期集中）</option>
						<option value="“国培计划（2011）”幼师国培项目（转岗教师）">“国培计划（2011）”幼师国培项目（转岗教师）</option>
						
						<option value="“国培计划（2012）”中西部项目（置换脱产）">“国培计划（2012）”中西部项目（置换脱产）</option>
						<option value="“国培计划（2012）”中西部项目（短期集中）">“国培计划（2012）”中西部项目（短期集中）</option>
						<option value="“国培计划（2012）”幼师国培项目（置换脱产）">“国培计划（2012）”幼师国培项目（置换脱产）</option>
						<option value="“国培计划（2012）”幼师国培项目（短期集中）">“国培计划（2012）”幼师国培项目（短期集中）</option>
						<option value="“国培计划（2012）”幼师国培项目（转岗教师）">“国培计划（2012）”幼师国培项目（转岗教师）</option>
					</select>
				</div>
				<div>
					<label>子项目*:</label> <input name="addProject"  id="addProject" style="width:333px;"></input>
				</div>
				<div>
					<label>培訓單位*:</label> <input name="addUnit" id="addUnit" style="width:333px;"></input>
				</div>
				<div>
					<label>培訓学科*:</label> <input name="addSubject" id="addSubject" style="width:333px;"></input>
				</div>
				<div>
					<label>姓名*:</label> <input name="addNamet" id="addNamet" style="width:333px;" />
				</div>
				<div>
					<label>手机*:</label> <input name="addPhone" id="addPhone" style="width:333px;" />
				</div>
				<div>
					<label>电子邮件:</label> <input id="addMail" style="width:333px;" />
				</div>
				<div>
					<label>工作单位:</label> <input id="addWorkPlace" style="width:333px;" />
				</div>
				
				<div>
					<label>办公室电话:</label> <input id="addOfficePhone" style="width:333px;" />
				</div>
				<div>
					<label>职务:</label> <input id="addZhiWu" style="width:333px;" />
				</div>
				<div>
					<label>职称:</label> <input id="addZhiCheng" style="width:333px;" />
				</div>
				<div>
					<label>证书编号:</label> <input id="addDipCode" style="width:333px;" />
				</div>
				<div>
					<label>备注:</label> <input id="addBeiZhu" style="width:333px;" />
				</div>
			</div>
			
			<div id="editDiv" style="display:none;">
				<div>
					<label>省份*:</label> <select name="editProvince" id="editProvince" style="width:333px;"></select>
				</div>

				<div>
					<label>所属项目*:</label> 
					<select name="editProjectParent" id="editProjectParent" style="width:333px;">
						<option value="“国培计划（2010）”中西部项目（置换脱产）">“国培计划（2010）”中西部项目（置换脱产）</option>
						<option value="“国培计划（2010）”中西部项目（短期集中）">“国培计划（2010）”中西部项目（短期集中）</option>
						<option value="“国培计划（2011）”中西部项目（置换脱产）">“国培计划（2011）”中西部项目（置换脱产）</option>
						<option value="“国培计划（2011）”中西部项目（短期集中）">“国培计划（2011）”中西部项目（短期集中）</option>
						<option value="“国培计划（2011）”幼师国培项目（置换脱产）">“国培计划（2011）”幼师国培项目（置换脱产）</option>
						<option value="“国培计划（2011）”幼师国培项目（短期集中）">“国培计划（2011）”幼师国培项目（短期集中）</option>
						<option value="“国培计划（2011）”幼师国培项目（转岗教师）">“国培计划（2011）”幼师国培项目（转岗教师）</option>
						
						<option value="“国培计划（2012）”中西部项目（置换脱产）">“国培计划（2012）”中西部项目（置换脱产）</option>
						<option value="“国培计划（2012）”中西部项目（短期集中）">“国培计划（2012）”中西部项目（短期集中）</option>
						<option value="“国培计划（2012）”幼师国培项目（置换脱产）">“国培计划（2012）”幼师国培项目（置换脱产）</option>
						<option value="“国培计划（2012）”幼师国培项目（短期集中）">“国培计划（2012）”幼师国培项目（短期集中）</option>
						<option value="“国培计划（2012）”幼师国培项目（转岗教师）">“国培计划（2012）”幼师国培项目（转岗教师）</option>
					</select>
				</div>
				<div>
					<label>子项目*:</label> <input name="editProject" id="editProject" style="width:333px;"></input>
				</div>
				<div>
					<label>培訓單位*:</label> <input name="editUnit" id="editUnit" style="width:333px;"></input>
				</div>
				<div>
					<label>培訓学科*:</label> <input name="editSubject" id="editSubject" style="width:333px;"></input>
				</div>
				<div>
					<label>姓名*:</label> <input name="editNamet" id="editNamet" style="width:333px;" />
				</div>
				<div>
					<label>手机*:</label> <input name="editPhone" id="editPhone" style="width:333px;" />
				</div>
				<div>
					<label>电子邮件:</label> <input id="editMail" style="width:333px;" />
				</div>
				<div>
					<label>工作单位:</label> <input id="editWorkPlace" style="width:333px;" />
				</div>
				<div>
					<label>办公室电话:</label> <input id="editOfficePhone" style="width:333px;" />
				</div>
				<div>
					<label>职务:</label> <input id="editZhiWu" style="width:333px;" />
				</div>
				<div>
					<label>职称:</label> <input id="editZhiCheng" style="width:333px;" />
				</div>
				<div>
					<label>证书编号:</label> <input id="editDipCode" style="width:333px;" />
				</div>
				<div>
					<label>备注:</label> <input id="editBeiZhu" style="width:333px;" />
				</div>
			</div>


		</div>
	</div>
</body>
</html>