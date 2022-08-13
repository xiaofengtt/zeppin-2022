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
				<div id="0110" class="tab_menu2" onClick="top.openPage('/entity/teaching/elelctiveManage_turntoSearch.action',this.id)" title="代学生选课">代学生选课</div>
				<div id="0111" class="tab_menu2" onClick="top.openPage('/entity/teaching/electiveInfoManage.action',this.id)" title="学生选课管理">学生选课管理</div>
				<div id="0112" class="tab_menu2" onClick="top.openPage('/entity/teaching/electiveStat.action',this.id)" title="选课统计">选课统计</div>
				<div id="0113" class="tab_menu2" onClick="top.openPage('/entity/teaching/unElectiveStuList.action',this.id)" title="未选课学生列表">未选课学生</div>
				<div id="0114" class="tab_menu2" onClick="top.openPage('/entity/teaching/courseOrderStat.action',this.id)" title="教材征订管理">教材征订管理</div>
			
				<script>top.openPage('/entity/teaching/elelctiveManage_turntoSearch.action','0110');</script>
			</div>
		</div>
	</td>
  </tr>
</table>
  </body>
</html>
