<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>无标题文档</title>
<link href="/entity/teacher/images/layout.css" rel="stylesheet" type="text/css" />
</head>

<body>
<div id="main_content">
<div class="student_cntent_k">
<div class="k_cc">
<table border="0" cellpadding="1" cellspacing="1" align="center" >
	<tr>
    	<td colspan="2" align="center">下载学生毕业论文开题报告</td>
	</tr>
	<s:if test="paperContentList.size==0">
		<tr>
			<td colspan="2" align="center">学生还未上传开题报告</td>
		</tr>
	</s:if>	
	<s:iterator value="paperContentList">
		<tr>
	    	<td align="left">
	    		<a href="<s:property value='getUrl()'/>" target="_blank">
					<font color="blue"> <u> <s:property value="getUrl().substring(25, getUrl().length())"/> </u> </font>
				</a>
			</td>
	    	<td align="left"><s:date name="getActionDate()" format="yyyy年MM月dd日"/> </td>
		</tr>
		
    </s:iterator>
    <tr class="table_bg1">
	    	<td colspan="5">
	    		<p align="center">
					<a href="javascript:window.close()">[关闭]</a>
				</p>
			</td>
	    </tr>
</table>
    </div>
    </div>
</div>
</body>
</html>
