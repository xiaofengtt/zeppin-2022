<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<%@ include file="/entity/function/pub/priv.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>中国社会科学院（研究生院） </title>
<link href="/entity/function/css/css.css" rel="stylesheet" type="text/css">
</head>

<script type="text/javascript">	
	function chkSubmit()
	{
		var score = document.getElementById('score').value;
		var reg = /^((100|[1-9]?\\d)(\\.\\d)?)$/;
		if(!reg.test(score)) {
			alert("成绩范围：0~100，一位小数。");
			return false;
		}
		return true;
	}
</script>
<body leftmargin="0" topmargin="0"  background="/entity/function/images/bg2.gif">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
<form action="/entity/workspaceTeacher/interactionHomework_pigaiExe.action" method="POST" name="frmAnnounce" onsubmit="return chkSubmit()">
<input type="hidden" value="<s:property value="homeworkHistoryId"/>" name="homeworkHistoryId">

        <tr>
          <td valign="top" class="bg3"><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td height="45" valign="top" background="/entity/function/images/top_01.gif"></td>
              </tr>
              <tr>
                <td align="center" valign="top">
                  <table width="765" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td height="65" background="/entity/function/images/table_01.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td align="center" class="text1"><img src="/entity/function/images/xb3.gif" width="17" height="15"> 
                              <strong>查看作业</strong></td>
							<td width="300">&nbsp;</td>
                          </tr>
                        </table></td>
                    </tr>
                    <tr>
                            <td class="text2" background="/entity/function/images/table_02.gif" >
                            	<table border="0" align="left" cellpadding="0" cellspacing="0" width=77%>
                            		<tr>
                            			题目：
                            			<td class="text2" width="95%"><s:property value="homeworkHistory.homeworkInfo.note" escape="false"/></td>
                            		</tr>
                            	</table>
							</td>
					</tr>
                    <tr>
                            <td class="text2" background="/entity/function/images/table_02.gif" >
                            	<table border="0" align="left" cellpadding="0" cellspacing="0" width=77%>
                            		<tr>
                            			学生提交：
                            			<td class="text2" width="95%"><s:property value="homeworkHistory.note" escape="false"/></td>
                            		</tr>
                            	</table>
							</td>
					</tr>
					<tr>
                            <td class="text2" background="/entity/function/images/table_02.gif" >
                            	<table border="0" align="left" cellpadding="0" cellspacing="0" width=77%>
                            		<tr>
                            			本次作业成绩：
                            			<td class="text2" width="95%" align="center"><s:property value="homeworkHistory.score"/>分 </td>
                            		</tr>
                            	</table>
							</td>
					</tr>
                    <tr>
        <tr>
		 <td><img src="/entity/function/images/table_03.gif" width="765" height="21"></td>
                    </tr>
		<tr>
			<td align=center><input type=button value="关闭" onclick="window.close()"></td>
		</tr>
                  </table> </td>
              </tr>
            </table></td>
        </tr>
      </table>
      </form>
</body>

</html>