<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<%@ include file="/entity/function/pub/priv.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>中国社会科学院（研究生院） </title>
<link href="/entity/function/css/css.css" rel="stylesheet" type="text/css">
<script>
function cfmdel(link)
{
	if(confirm("您确定要删除此结果吗？"))
		delform.homeworkHistoryId.value=link;
		document.delform.submit();
}
function DetailInfo(tsId)
{
	window.open('homeworkhistory_info.jsp?tsId='+tsId);
}

</script>
</head>

<body leftmargin="0" topmargin="0"  background="/entity/function/images/bg2.gif">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td valign="top" class="bg3"><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td valign="top" background="/entity/function/images/top_01.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td width="217" height="104" rowspan="2" valign="top"><img src="/entity/function/images/zxzy.gif" width="217" height="86"></td>
                      <td height="46">&nbsp;</td>
                    </tr>
                    <tr> 
                      <td align="right" valign="top"> 
                        <table width="70%" border="0" cellpadding="0" cellspacing="0">
                          <tr>
                            <td align="center"><a href="/entity/workspaceTeacher/interactionHomework_toHomeworkList.action?teachclassId=<%=teachclass_id %>" class="tj">[返回作业列表]</a></td>
                            <td width="35">&nbsp;</td>
                          </tr>
                        </table>
                      </td>
                    </tr>
                  </table></td>
              </tr>
              <tr>
                <td align="center">
				<table width="812" border="0" cellspacing="0" cellpadding="0">
                 <tr>
                   <td height="26" background="/entity/function/images/tabletop.gif">
                	<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">				
                    <tr>
                      <td width="5%" height="17">&nbsp;</td>
                      <td width="24%" align="center" class="title">作业名称</td>
					  <td width="1%" align="center" valign="bottom"><img src="/entity/function/images/topxb.gif"></td>
                      <td width="16%" align="center" class="title">上交人</td>
					  <td width="1%" align="center" valign="bottom"><img src="/entity/function/images/topxb.gif"></td>
                      <td width="16%" align="center" class="title">上交时间</td>
                      <td width="1%" align="center" valign="bottom"><img src="/entity/function/images/topxb.gif"></td>
                      <td width="16%" align="center" class="title">状态</td>
					  <td width="1%" align="center" valign="bottom"><img src="/entity/function/images/topxb.gif"></td>
                      <td width="15%" align="center" class="title">操作</td>
                    </tr>                    
                  	</table>
                  </td>
                 </tr>
        	<form name="delform" action="/entity/workspaceTeacher/interactionHomework_delHomeworkHistory.action">
        	<input type="hidden" name="homeworkHistoryId">
		<s:iterator value="homeworkHistoryList" status="stuts">
                    <tr>
					<td align="center" background="/entity/function/images/tablebian.gif">
						<table width="99%" border="0" cellspacing="0"
							cellpadding="0" class="mc1">
							<s:if test="#stuts.odd == true">
								<tr bgcolor="#E8F9FF">
							</s:if>
							<s:else>
								<tr bgcolor="#ffffff">
							</s:else>
                            <td width="5%" align="center" valign="middle" class="td1"><img src="images/xb2.gif" width="8" height="8" border="0"></td>
                            <td width="24%"  class="td1""><s:property value="homeworkInfo.title"/> </td>
                            <td width="18%" align="center"  class="td1"><s:property value="peStudent.trueName"/> </td>
                            <td width="17%" align="center"  class="td1"><s:date name="testdate" format="yyyy-MM-dd hh:mm:ss"/> </td>
                            <td width="17%" align="center"  class="td1"><s:property value="enumConstByFlagIscheck.name"/> </td>
                            <td width="15%" align="center"  class="td1">
                   
                            <a href="/entity/workspaceTeacher/interactionHomework_toPigai.action?homeworkHistoryId=<s:property value="id"/>" class="button">批改</a>
                            <a href="javascript:cfmdel('<s:property value="id"/>')" class="button">删除</a>

							</td>
                          </tr>
                        </table>
                      </td>
                    </tr>
		</s:iterator>
			</form>
                    <tr>
                      <td><img src="/entity/function/images/tablebottom.gif" width="812" height="4"></td>
                    </tr>
                  </table><br>
                </td>
              </tr>
              <tr>
                <td align="center">
<table width="806" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td><img src="/entity/function/images/bottomtop.gif" width="806" height="7"></td>
                    </tr>
                    <tr>
                      <td background="/entity/function/images/bottom02.gif">
                      	
                      	<%@ include file="/entity/function/pub/struts_dividepage.jsp"%>
                      </td>
                    </tr>
                    <tr>
                      <td><img src="/entity/function/images/bottom03.gif" width="806" height="7"></td>
                    </tr>
                  </table>
                </td>
              </tr>
            </table></td>
        
        </tr>
      </table>
</body>
</html>