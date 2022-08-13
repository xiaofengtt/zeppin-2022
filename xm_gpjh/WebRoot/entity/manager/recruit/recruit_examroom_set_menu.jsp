<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>TabMenu</title>
<link href="/entity/manager/pub/images/layout.css" rel="stylesheet" type="text/css" />
</head>
<body>
<!--内容区-->
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td width="6" bgcolor="278abe" style="background-color:#278abe;"></td>
    <td>
		<div id="main_content">
			<div id="tab_bg">
                <div id="0110" class="tab_menu2" onClick="top.openPage('/entity/recruit/examstucourse.action',this.id)" title="考生课程明细">考生课程明细</div>
				<div id="0111" class="tab_menu2" onClick="top.openPage('/entity/manager/recruit/recruit_autoallotcourse.jsp',this.id)" title="自动分配考试课程">自动分配课程</div>
                <div id="0112" class="tab_menu2" onClick="top.openPage('/entity/manager/recruit/recruit_autoallotroom.jsp',this.id)" title="自动分配考场">自动分配考场</div>
                <div id="0113" class="tab_menu2" onClick="top.openPage('/entity/recruit/examstuticket.action',this.id)" title="打印准考证">打印准考证</div>
				<div id="0114" class="tab_menu2" onClick="top.openPage('/entity/recruit/examinvigilator.action',this.id)" title="监考安排查询">监考查询</div>
				<div id="0115" class="tab_menu2" onClick="top.openPage('/entity/recruit/examInspector.action',this.id)" title="巡考安排查询">巡考查询</div>
				<div id="0116" class="tab_menu2" onClick="top.openPage('/entity/manager/recruit/recruit_autoallotInvigilator.jsp',this.id)" title="监巡考自动安排">监巡考安排</div>
                <div id="0117" class="tab_menu2" onClick="top.openPage('/entity/recruit/examfenpeistat_turntoStat.action',this.id)" title="分配统计">分配统计</div>
                <div id="0118" class="tab_menu2" onClick="top.openPage('/entity/recruit/peRecRoom.action',this.id)" title="考场设置">考场设置</div>
                <div id="0119" class="tab_menu2" onClick="top.openPage('/entity/recruit/examStuRoom.action',this.id)" title="考场分配结果">考场分配结果</div>
                
                <script>top.openPage('/entity/recruit/examstucourse.action','0110');</script>
				
			</div>
		</div>
	</td>
  </tr>
</table>
</body>
</html>

