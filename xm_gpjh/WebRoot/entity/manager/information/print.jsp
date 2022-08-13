<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<html>
	<head>
		<title>证书打印</title>
<style type="text/css" media="screen, projection">
/* Not required for Tabs, just to make this demo look better... */
body,h1,h2 {
	font-family: "Trebuchet MS", Trebuchet, Verdana, Helvetica, Arial,
		sans-serif;
}

h1 {
	margin: 1em 0 1.5em;
	font-size: 18px;
}

h2 {
	margin: 2em 0 1.5em;
	font-size: 16px;
}

p {
	margin: 0;
}

pre,pre+p,p+p {
	margin: 1em 0 0;
}

code {
	font-family: "Courier New", Courier, monospace;
}
</style>
	</head>
	<body style="padding: 20px" onload="window.print();">
		<s:iterator value="infoList" id="info"> 
		<br><br>
		<table rules=none width="90%" height="80%" border="2" cellpadding="0" cellspacing="0" bordercolor=red align=center>
			<tr>
				<td align=center>
					<table border="0" width="90%">
						<tr>
							<td height="200px" valign="middle" align="center">
								<font color="red"><strong style="font-size:84px;">培训证明</strong></font>
							</td>
						</tr>
					</table>
				</td>
			</tr>	
			<tr valign=top>
				<td align=center>
					<table border="0" width="90%">
						<tr>
							<td height="160px">
								<font><strong style="font-size:36px;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:property value="#info[0]"/> 同志 顺利完成<s:property value="#info[2]"/>培训班'<s:property value="#info[1]"/>'的所有课程并顺利结业，特此证明。</strong></font>
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td>
					<table border="0" width="90%">
						<tr align=right>
							<td>
								<font><strong style="font-size:36px;">生殖健康咨询师培训中心</strong></font>
							</td>
						</tr>
						<tr align=right height="80px" valign=top>
							<td>
								<font><strong style="font-size:36px;"><s:property value="#info[3]"/></strong></font>
							</td>
						</tr>
					</table>
				</td>
			</tr>	
		</table>
	</s:iterator>
	</body>
</html>
