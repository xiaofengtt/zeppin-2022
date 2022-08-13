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

<script src="js/jquery-ui-1.10.3.custom.min.js" type="text/javascript"></script>
	<script src="js/jquery-ui-i18n.js" type="application/x-javascript">
</script>
	
<link href="css/datepicker3.css" rel="stylesheet" />
<script src="js/bootstrap-datepicker.js"></script>
<script src="js/bootstrap-datepicker.zh-CN.js"></script>

<script src="js/adminTraineeManager.js" type="text/javascript"></script>

<script type="text/javascript">
	var role = '<s:property value="#parameters.role" />';
	var userid = '<s:property value="#parameters.userid" />';

	function uploadFileOK() {
		
		$("#trainFrom").attr('action', 'trainee_trainUploadFile.action');
		$("#trainFrom").submit();
		var addDlg = $("#impDiv");
		addDlg.dialog().dialog("close");
	}
	$(document).ready(function() {     
	      $('.datepicker').datepicker({
				language : "zh-CN",
				format : 'yyyy-mm-dd',
				startView: 2,
				endDate : "1d",
				autoclose : true
			})
	  });
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
					<label>所属地区（省）*:</label> <select id="addProvince" onchange="changeArea(this,'province');" style="width:333px;"></select>
				</div>
				<div>
					<label>所属地区（市）*:</label> <select id="addCity" onchange="changeArea(this,'city');" style="width:333px;"></select>
				</div>
				<div>
					<label>所属地区（县）*:</label> <select id="addCounty" style="width:333px;"></select>
				</div>

				<div>
					<label>所属项目*:</label> <select id="addProjectParent"
						style="width:333px;"></select>
				</div>
				<div>
					<label>子项目*:</label> <select id="addProject" style="width:333px;"></select>
				</div>
				<div>
					<label>培訓單位*:</label> <select id="addUnit" style="width:333px;"></select>
				</div>
				<div>
					<label>培訓学科*:</label> <select id="addSubject" style="width:333px;"></select>
				</div>
				<div>
					<label>姓名*:</label> <input id="addNamet" style="width:333px;" />
				</div>
				<div>
					<label>身份证号*:</label> <input id="addIdcard" style="width:333px;" />
				</div>
				<div>
					<label>手机*:</label> <input id="addPhone" style="width:333px;" />
				</div>
				<div>
					<label>电子邮件*:</label> <input id="addMail" style="width:333px;" />
				</div>
				<div>
					<label>电话*:</label> <input id="addTelephone" style="width:333px;" />
				</div>
				<div>
					<label>工作单位*:</label> <input id="addWorkPlace" style="width:333px;" />
				</div>
				<div>
					<label>学校所在区域*:</label> <select id="addUnitAttribute" style="width:333px;"></select>
				</div>
				<div>
					<label>学校类别*:</label> <select id="addUnitType" style="width:333px;"></select>
				</div>
				<div>
					<label>民族*:</label> <select id="addFolk" style="width:333px;"></select>
				</div>
				<div>
					<label>职称*:</label> <select id="addJobTitle" style="width:333px;"></select>
				</div>
				<div>
					<label>主要任教学段*:</label> <select id="addMainGrade" style="width:333px;"></select>
				</div>
				<div>
					<label>主要任教学科*:</label> <select id="addMainSubject" style="width:333px;"></select>
				</div>
				<div>
					<label>最高学历*:</label> <select id="addEducation" style="width:333px;"></select>
				</div>
				<div>
					<label>毕业院校*:</label> <input id="addGraduation" style="width:333px;" />
				</div>
				<div>
					<label>所学专业*:</label> <input id="addMajor" style="width:333px;" />
				</div>
<!-- 				<div> -->
<!-- 					<label>入职时间*:</label> <input id="addHiredate" type="text" data-provide="datepicker" class="datepicker" style="width:333px;" readonly="readonly" /> -->
<!-- 				</div> -->
				<div>
					<label>证书编号*:</label> <input id="addDipcode" style="width:333px;" />
				</div>
			</div>
			
			<div id="editDiv" style="display:none;">
				<div>
					<label>所属地区（省）*:</label> <select id="editProvince" onchange="changeArea2(this,'province');" style="width:333px;"></select>
				</div>
				<div>
					<label>所属地区（市）*:</label> <select id="editCity" onchange="changeArea2(this,'city');" style="width:333px;"></select>
				</div>
				<div>
					<label>所属地区（县）*:</label> <select id="editCounty" style="width:333px;"></select>
				</div>

				<div>
					<label>所属项目*:</label> <select id="editProjectParent" disabled="disabled"
						style="width:333px;"></select>
				</div>
				<div>
					<label>子项目*:</label> <select id="editProject" style="width:333px;" disabled="disabled" ></select>
				</div>
				<div>
					<label>培訓單位*:</label> <select id="editUnit" style="width:333px;" disabled="disabled" ></select>
				</div>
				<div>
					<label>培訓学科*:</label> <select id="editSubject" style="width:333px;" disabled="disabled" ></select>
				</div>
				<div>
					<label>姓名*:</label> <input id="editNamet" style="width:333px;" />
				</div>
				<div>
					<label>身份证号*:</label> <input id="editIdcard" style="width:333px;" />
				</div>
				<div>
					<label>手机*:</label> <input id="editPhone" style="width:333px;" />
				</div>
				<div>
					<label>电子邮件*:</label> <input id="editMail" style="width:333px;" />
				</div>
				<div>
					<label>工作单位*:</label> <input id="editWorkPlace" style="width:333px;" />
				</div>
				<div>
					<label>学校所在区域*:</label> <select id="editUnitAttribute" style="width:333px;"></select>
				</div>
				<div>
					<label>学校类别*:</label> <select id="editUnitType" style="width:333px;"></select>
				</div>
				<div>
					<label>民族*:</label> <select id="editFolk" style="width:333px;"></select>
				</div>
				<div>
					<label>职称*:</label> <select id="editJobTitle" style="width:333px;"></select>
				</div>
				<div>
					<label>主要任教学段*:</label> <select id="editMainGrade" style="width:333px;"></select>
				</div>
				<div>
					<label>主要任教学科*:</label> <select id="editMainSubject" style="width:333px;"></select>
				</div>
				<div>
					<label>最高学历*:</label> <select id="editEducation" style="width:333px;"></select>
				</div>
				<div>
					<label>毕业院校*:</label> <input id="editGraduation" style="width:333px;" />
				</div>
				<div>
					<label>所学专业*:</label> <input id="editMajor" style="width:333px;" />
				</div>
<!-- 				<div> -->
<!-- 					<label>入职时间*:</label> <input id="editHiredate" type="text" data-provide="datepicker" class="datepicker" style="width:333px;" readonly="readonly" /> -->
<!-- 				</div> -->
				<div>
					<label>证书编号*:</label> <input id="editDipcode" style="width:333px;" />
				</div>
				</div>
				
			</div>


		</div>
</body>
</html>