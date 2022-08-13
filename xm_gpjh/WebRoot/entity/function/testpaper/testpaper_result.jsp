<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>试题</title>
<link href="/entity/function/css/css.css" rel="stylesheet" type="text/css">
<script>
function saveTime()
{
	//document.cookie="TestTime=0";
}

</script>
</head>
<body leftmargin="0" topmargin="0"  background="/entity/function/images/bg2.gif" style='overflow:scroll;overflow-x:hidden'>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td valign="top" class="bg3"><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td height="86" valign="top" background="/entity/function/images/top_01.gif"><img src="/entity/function/images/zxzy.gif" width="217" height="86"></td>
              </tr>
              <tr>
                <td align="center" valign="top"><table width="800" border="1" cellspacing="0" cellpadding="0" bordercolordark="BDDFF8" bordercolorlight="#FFFFFF">
              <tr>
                      <td bgcolor="#FFFFFF"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td width="20%" background="/entity/function/images/st_01.gif" class="text3" style="padding-left:50px">作&nbsp;&nbsp;业</td>
                            <td width="75%" height="53" background="/entity/function/images/wt_03.gif" align="right"></td>
                          </tr>
                        </table></td>
                    </tr>
                    <tr> 
                      <td valign="top">
						<table width="67%" border="0" align="center" cellpadding="0" cellspacing="0">
						<s:iterator value='reslutList' id='reslut' status='stus'>
						<s:if test="#reslut[0] == 'NOTYUEDU'">
                          <tr> 
                            <td valign="middle" align="left"><s:property value='%{#stus.index + 1}'/>．<s:property value='#reslut[1]' escape='false'/></td>
                          </tr>
                          <tr> 
                            <td valign="middle" align="left"  style="font-size:12px;color:#7f7f7f;line-height:24px">标准答案：<s:property value='#reslut[2]'/></td>
                          </tr>
                          <tr> 
                            <td valign="middle" align="left" style="font-size:12px;color:#7f7f7f;line-height:24px">您的答案：<s:property value='#reslut[3]'/></td>
                          </tr>
                          <tr> 
                            <td valign="middle" align="left" style="font-size:12px;color:#7f7f7f;line-height:24px">此题得分：<s:property value='#reslut[4]'/></td>
                          </tr>
                          <tr> 
                            <td valign="middle" align="left">&nbsp;</td>
                          </tr>
                        </s:if>
                        <s:else>
                          <tr> 
                            <td valign="middle" align="left"><s:property value='%{#stus.index + 1}'/>．<s:property value='#reslut[1]' escape='false'/></td>
                          </tr>
                          <s:if test="#reslut[2]">
                       	<s:iterator value='#reslut[3]' id='yuedu' status='stas'>
                          <tr> 
                            <td valign="middle" align="left"  style="font-size:12px;color:#7f7f7f;line-height:24px">&nbsp;&nbsp;<s:property value='%{#stas.index + 1}'/>. <s:property value='#yuedu[0]' escape='false'/></td>
                          </tr>
                          <tr> 
                            <td valign="middle" align="left"  style="font-size:12px;color:#7f7f7f;line-height:24px">&nbsp;&nbsp;标准答案：<s:property value='#yuedu[1]'/></td>
                          </tr>
                          <tr> 
                            <td valign="middle" align="left" style="font-size:12px;color:#7f7f7f;line-height:24px">&nbsp;&nbsp;您的答案：<s:property value='#yuedu[2]'/></td>
                          </tr>
                          <tr> 
                            <td valign="middle" align="left" style="font-size:12px;color:#7f7f7f;line-height:24px">&nbsp;&nbsp;题目得分：<s:property value='#yuedu[3]'/></td>
                          </tr>
                        </s:iterator>
                       	</s:if>
                    	<s:else>
                          <tr> 
                            <td valign="middle" align="left" style="font-size:12px;color:#7f7f7f;line-height:24px">&nbsp;&nbsp;未答此题</td>
                          </tr>
                        </s:else>
                          <tr> 
                            <td valign="middle" align="left">题目总得分：<s:property value='#yuedu[4]'/></td>
                          </tr>
                          <tr> 
                            <td valign="middle" align="left">&nbsp;</td>
                          </tr>
                          </s:else>
                        </s:iterator>
                          <tr> 
                            <td valign="middle" align="left">试卷总得分：<s:property value='totalScore'/></td>
                          </tr>
			              <tr> 
			                <td ><table width="40%" border="0" align="center" cellpadding="0" cellspacing="0">
			                    <tr align="center"> 
			                      <!--<td class="text"><img src="/entity/function/images/bcfh.gif" width="100" height="30" onclick="window.location.href='testpaper_resultexe.jsp?id=&totalScore=<s:property value='totalScore'/>'"></td>
			                      <td class="text"><img src="/entity/function/images/zjfh.gif" width="100" height="30" onclick="window.location.href='homeworkpaper_list.jsp'"></td>-->
			                    </tr>
			                  </table></td>
			              </tr>
                        </table><br>
                      </td>
                    </tr>
                  </table></td>
                    </tr>
                  </table> 
                </td>
              </tr>
            </table></td>
        </tr>
      </table>
</body>
</html>
