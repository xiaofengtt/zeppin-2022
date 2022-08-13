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
function DetailInfo(id)
{
	//window.showModalDialog('store_question_info.jsp?id='+id,'','dialogWidth=630px;dialogHeight=550px');
}
</script>
</head>
<body leftmargin="0" topmargin="0"  background="/entity/function/images/bg2.gif" style='overflow:scroll;overflow-x:hidden'>
<table width="70%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td valign="top" class="bg3"><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td height="86" valign="top" background="/entity/function/images/top_01.gif"></td>
              </tr>
              <tr>
                <td align="center" valign="top"><table width="800" border="1" cellspacing="0" cellpadding="0" bordercolordark="BDDFF8" bordercolorlight="#FFFFFF">
              <tr>
                      <td bgcolor="#FFFFFF"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td width="20%" background="/entity/function/images/st_01.gif" class="text3" style="padding-left:50px">试&nbsp;&nbsp;题</td>
                            <td width="75%" height="53" background="/entity/function/images/wt_03.gif" align="right"></td>
                          </tr>
                        </table></td>
                    </tr>
                    <tr> 
                      <td valign="top">
						<table width="67%" border="0" align="center" cellpadding="0" cellspacing="0">
						<s:iterator value='questionList' id='question' status='stus'>
						<s:if test="#question[0] == 'NOTYUEDU'">
						
                          <!-- <tr> 
                            <td valign="middle" align="left"><s:property value='%{#stus.index + 1}'/>．<s:property value='#question[2]' escape='false'/></td>
                          </tr>-->
                          <tr> 
	                        <td valign="middle" align="left"> <span style="font-size:13px;line-height:24px"><s:property value='%{#stus.index + 1}'/>．<s:property value='#question[3]' escape='false'/></span></td>
	                      </tr>
                          <!-- <tr> 
                            <td valign="middle" align="left"  style="font-size:12px;color:#7f7f7f;line-height:24px">标准答案：<s:property value='#question[4]'/></td>
                          </tr> -->
                          <tr> 
                            <td valign="middle" align="left" style="font-size:12px;color:#7f7f7f;line-height:24px">您的答案：<s:property value='#question[5]'/></td>
                          </tr>
                          <tr> 
                            <td valign="middle" align="left"  style="font-size:12px;color:#7f7f7f;line-height:24px">题目分数：<s:property value='#question[6]'/></td>
                          </tr>
                          <tr> 
                            <td valign="middle" align="left" style="font-size:12px;color:#7f7f7f;line-height:24px">此题得分：<s:property value='#question[7]'/></td>
                          </tr>
                          <tr> 
                            <td valign="middle" align="left" style="font-size:12px;color:#7f7f7f;line-height:24px">批注：<s:property value='#question[8]'/></td>
                          </tr>
                          <tr> 
                            <td valign="middle" align="left" style="font-size:12px;color:#7f7f7f;line-height:24px">&nbsp;</td>
                          </tr>
                       </s:if>
                       <s:else>
                          <tr> 
                            <td valign="middle" align="left"><s:property value='%{#stus.index + 1}'/>．<s:property value='#question[1]' escape='false'/></td>
                          </tr>
                          <tr> 
                            <td valign="middle" align="left"  style="font-size:12px;color:#7f7f7f;line-height:24px">&nbsp;&nbsp;<s:property value='#question[2]' escape='false'/></td>
                          </tr>
                       <s:if test="#question[6]">
                       <s:iterator value='#question[7]' id='list' status='stas'>
                          <tr> 
                            <td valign="middle" align="left"  style="font-size:12px;color:#7f7f7f;line-height:24px">&nbsp;&nbsp;<s:property value='%{#stas.index + 1}'/>. <s:property value='#list[1]' escape='false'/></td>
                          </tr>
                          <tr> 
                            <td valign="middle" align="left"  style="font-size:12px;line-height:24px"><s:property value='#list[7]' escape='false'/></td>
                          </tr>
                          <tr> 
                            <td valign="middle" align="left"  style="font-size:12px;color:#7f7f7f;line-height:24px">&nbsp;&nbsp;&nbsp;&nbsp;标准答案：<s:property value='#list[2]'/></td>
                          </tr>
                          <tr> 
                            <td valign="middle" align="left" style="font-size:12px;color:#7f7f7f;line-height:24px">&nbsp;&nbsp;&nbsp;&nbsp;您的答案：<s:property value='#list[3]'/></td>
                          </tr>
                          <tr> 
                            <td valign="middle" align="left"  style="font-size:12px;color:#7f7f7f;line-height:24px">&nbsp;&nbsp;&nbsp;&nbsp;题目分数：<s:property value='#list[4]'/></td>
                          </tr>
                          <tr> 
                            <td valign="middle" align="left" style="font-size:12px;color:#7f7f7f;line-height:24px">&nbsp;&nbsp;&nbsp;&nbsp;此题得分：<s:property value='#list[5]'/></td>
                          </tr>
                          <tr> 
                            <td valign="middle" align="left" style="font-size:12px;color:#7f7f7f;line-height:24px">&nbsp;&nbsp;&nbsp;&nbsp;批注：<s:property value='#list[6]'/></td>
                          </tr>
                          <tr> 
                            <td valign="middle" align="left">&nbsp;</td>
                          </tr>
                       </s:iterator>
                       </s:if>
                       <s:else>
                          <tr> 
                            <td valign="middle" align="left" style="font-size:12px;color:#7f7f7f;line-height:24px">&nbsp;&nbsp;&nbsp;&nbsp;未做此题</td>
                          </tr>
                       </s:else>
                          <tr> 
                            <td valign="middle" align="left" style="font-size:12px;color:#7f7f7f;line-height:24px">&nbsp;&nbsp;题目总分数：<s:property value='#question[3]'/></td>
                          </tr>
                          <tr> 
                            <td valign="middle" align="left" style="font-size:12px;color:#7f7f7f;line-height:24px">&nbsp;&nbsp;题目总得分：<s:property value='#question[4]'/></td>
                          </tr>
                          <tr> 
                            <td valign="middle" align="left" style="font-size:12px;color:#7f7f7f;line-height:24px">&nbsp;&nbsp;题目总批注：<s:property value='#question[5]'/></td>
                          </tr>
                          <tr> 
                            <td valign="middle" align="left">&nbsp;</td>
                          </tr>
                       </s:else>
                       </s:iterator>
                          <tr> 
                            <td valign="middle" align="left" style="font-size:12px;color:#7f7f7f;line-height:24px">试卷总得分：<s:property value='totalScore'/></td>
                          </tr>
                          <tr> 
                            <td valign="middle" align="left" style="font-size:12px;color:#7f7f7f;line-height:24px">试卷总批注：<s:property value='totalNote'/></td>
                          </tr>
			              <tr> 
			                <td ><table width="40%" border="0" align="center" cellpadding="0" cellspacing="0">
			                    <tr align="center"> 
			                      <td class="text"><img src="/entity/function/images/gb.gif" style="cursor: pointer;" width="80" height="24" onclick="window.close()"></td><!--
			                      <td class="text"><img src="/entity/function/images/zjfh.gif" width="100" height="30" onclick="window.location.href='testpaper_list.jsp'"></td>
			                    --></tr>
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
