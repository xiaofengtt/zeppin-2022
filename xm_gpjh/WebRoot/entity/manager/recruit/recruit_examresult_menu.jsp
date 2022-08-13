<%@ page language="java" pageEncoding="UTF-8"%>
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
				<div id="0110" class="tab_menu2" onClick="top.openPage('/entity/recruit/examachievement.action',this.id)" title="入学考试成绩">入学考试成绩</div>
				<div id="0111" class="tab_menu2" onClick="top.openPage('/entity/recruit/examachievement_batch.action',this.id)" title="入学考试成绩导入">考试成绩导入</div>
				<div id="0112" class="tab_menu2" onClick="top.openPage('/entity/manager/recruit/exam_result_calculate.jsp',this.id)" title="统计录取人数、录取比例">统计录取人数</div>
			
				<script>top.openPage('/entity/recruit/examachievement.action','0110');</script>
			</div>
		</div>
	</td>
  </tr>
</table>
  </body>
</html>
