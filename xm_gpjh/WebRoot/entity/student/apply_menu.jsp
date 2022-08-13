<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link href="/entity/manager/pub/images/layout.css" rel="stylesheet" type="text/css" />
</head>
<body>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td>
		<div id="main_content">
			<div id="tab_bg">
				<div id="0110" class="tab_menu2" onClick="parent.openPage('/entity/workspaceStudent/student_toChangeStudent.action',this.id)" title="">学籍变更申请</div>
				<div id="0111" class="tab_menu2" onClick="parent.openPage('/entity/workspaceStudent/examAvoid_toAvoidExam.action',this.id)" title="免修申请">免考免修申请</div>
				<s:if test="edutype!=null">
				<div id="0112" class="tab_menu2" onClick="parent.openPage('/entity/workspaceStudent/uniteExamAvoid_toMenu.action',this.id)" title="统考报名免试申请">统考免试申请</div>
				<div id="0113" class="tab_menu2" onClick="parent.openPage('/entity/workspaceStudent/apply_toDegreeEnglish.action',this.id)" title="学位英语申请">学位英语申请</div>
				<div id="0114" class="tab_menu2" onClick="parent.openPage('/entity/workspaceStudent/apply_toPaperReapply.action',this.id)" title="论文重修申请">论文重修申请</div>
				<div id="0115" class="tab_menu2" onClick="parent.openPage('/entity/workspaceStudent/mainCourseScoreChangeApply_toMainCourseChangeApply.action',this.id)" title="主干课复查">主干课复查</div>
				</s:if>
				<div id="0116" class="tab_menu2" onClick="parent.openPage('/entity/workspaceStudent/applyExcellent_toApplyExcellent.action',this.id)" title="评优申请">评优申请</div>
				<div id="0117" class="tab_menu2" onClick="parent.openPage('/entity/workspaceStudent/apply_turnToGraduateApply.action',this.id)" title="毕业申请">毕业申请</div>
				<div id="0118" class="tab_menu2" title"X超学分减免">X超学分减免</div>
				<div id="0119" class="tab_menu2" onClick="parent.openPage('/entity/workspaceStudent/examScoreChangeApply_toExamChangeApply.action',this.id)" title="期末成绩复查">期末成绩复查</div>
				
				<script>parent.openPage('/entity/workspaceStudent/student_toChangeStudent.action','0110');</script>
			</div>
		</div>
	</td>
  </tr>
</table>
</body>
</html>