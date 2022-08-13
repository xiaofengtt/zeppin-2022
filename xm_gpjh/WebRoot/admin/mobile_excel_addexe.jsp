<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="jxl.*,java.text.*,java.io.*,java.util.*,com.whaty.platform.util.*,com.whaty.platform.util.log.*"%>
<%@ page import="com.whaty.platform.entity.activity.*,com.whaty.platform.entity.activity.score.*"%>
<%@ page import="com.whaty.platform.entity.*,com.whaty.platform.entity.basic.*,com.whaty.platform.entity.config.*"%>
<%@ page import="com.whaty.platform.entity.basic.*,com.whaty.platform.entity.setup.*"%>
<%@ page import="com.whaty.platform.entity.user.*,com.whaty.platform.entity.test.*,com.whaty.platform.Exception.PlatformException"%>
<%@ include file="./pub/priv.jsp"%>

<html>
<body>
<%
try
{
	PlatformFactory platformfactory = PlatformFactory.getInstance();
	BasicRightManage rightManage = platformfactory.creatBasicRightManage();

	String scr = (String) session.getValue("scr"); 
	String T_filename = (String) session.getValue("T_filename");
	int count = 0;
	try
	{
		Workbook w     = Workbook.getWorkbook(new File(scr + T_filename));
		Sheet sheet    = w.getSheet(0);
		int   columns  = sheet.getColumns();
		int   rows     = sheet.getRows();
		Cell  cell;
		ArrayList studentList = new ArrayList();
		
		for (int i = 1 ;i < rows ;i ++)
		{

			if(sheet.getCell(0,i).getContents().trim().equals(""))
			{
				out.print("第"+(i+1)+"行数据,管理员ID为空，请查证后在导入！<br>");
				continue;
			}
			String loginId = sheet.getCell(0,i).getContents().trim();
            if(sheet.getCell(2,i).getContents().trim().equals(""))
			{
				out.print("第"+(i+1)+"行数据,移动电话号码输入为空，请查证后在导入！<br>");
				continue;
			}
			String mobile = sheet.getCell(2,i).getContents().trim();
            count += rightManage.updateAdminMobile(loginId,mobile);	
		}

				

		
		w.close();
	}
	catch(JXLException e){
%>
<script language=javascript>
		alert("上载的不是一个标准化的Excel文件，请先下载模版，然后按模版上传！�");
		history.back();
</script>
<%
	}
%><script language="javascript">
	alert("<%=count%>条数据导入完毕,请返回！");
	window.navigate("mobile_batch.jsp")
</script>
<%
	}
catch(Exception e)
{
	out.print(e.getMessage());
%>
<script language="javascript">
	if(confirm("导入有误,请返回！"))
		window.history.back();
</script>
<%
	return;
}
%>