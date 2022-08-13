<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
  <link href="/entity/manager/pub/images/layout.css" rel="stylesheet" type="text/css" />
</head>
  
  <body>
    <table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td width="6" bgcolor="278abe" style="background-color:#278abe;"></td>
    <td>
		<div id="main_content">
			<div id="tab_bg">
				<div id="0110" class="tab_menu2" onClick="top.openPage('/entity/recruit/examcourseset.action',this.id)" title="入学考试课程设置">考试课程设置</div>
				<div id="0111" class="tab_menu2" onClick="top.openPage('/entity/recruit/eduTypeMajorExamSet.action',this.id)" title="层次专业考试课程设置">层次专业考试</div>
				<div id="0112" class="tab_menu2" onClick="top.openPage('/entity/recruit/eduExamTimeSet.action',this.id)" title="考试时间设置">考试时间设置</div>
			
				<script>top.openPage('/entity/recruit/examcourseset.action','0110');</script>
			</div>
		</div>
	</td>
  </tr>
</table>
  </body>
</html>
