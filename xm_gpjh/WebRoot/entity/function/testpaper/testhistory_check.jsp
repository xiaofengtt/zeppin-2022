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
	function isNum(x)
	{
		var isRight=true;
		var count = 0;
		for (var i=0;i<x.length;i++)
		{
			var k = x.charAt(i);
			if ((k<'0' && k!='.') || (k>'9' && k!='.' ))
			{
				isRight=false;
				break;
			}
			if(k=='.')
				count++;
		}
		if(count>1)
		{
			isRight=false;
		}	
		return isRight;
	}
	
	function checkscore(name,score,sscore)
	{
		if(!isNum(score))
		{
			alert("分数请输入数字！");
			document.getElementById(name).focus();
			document.getElementById(name).select();
			return;
		}
		if(score*1>sscore*1)
		{
			alert("评分不能大于题目分数！");
			document.getElementById(name).focus();
			document.getElementById(name).select();
			return;
		}
	}

</script>
</head>
<body leftmargin="0" topmargin="0"  background="/entity/function/images/bg2.gif" style='overflow:scroll;overflow-x:hidden'>
<form action="/entity/studyZone/courseResources_checkPaper.action" method="post" name="check">
<input type="hidden" name="history_id" value=<s:property value='history_id'/> >
<input type="hidden" name="paper_id" value=<s:property value='paper_id'/> >
<input type="hidden" name="paperUser_id" value=<s:property value='paperUser_id'/> >
<input type="hidden" name="curPage" value=<s:property value='curPage'/> >
<table width="70%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td valign="top" class="bg3"><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td height="86" valign="top" background="/entity/function/images/top_01.gif"><img src="/entity/function/images/zxzc.gif" width="217" height="86"></td>
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
						<s:iterator value='resultList' id='result' status='stus'>
						<s:if test="#result[0] == 'NOTYUEDU'">
                          <tr> 
                            <td valign="middle" align="left"><s:property value='%{#stus.index + 1}'/>．<s:property value='#result[1]' escape='false'/>&nbsp;&nbsp; <s:property value='#result[2]' escape='false'/></td>
                          </tr>
                          <tr> 
                            <td valign="middle" align="left"  style="font-size:12px;color:#7f7f7f;line-height:24px">标准答案：<s:property value='#result[4]'/></td>
                          </tr>
                          <tr> 
                            <td valign="middle" align="left" style="font-size:12px;color:#7f7f7f;line-height:24px">您的答案：<s:property value='#result[5]'/></td>
                          </tr>
                          <tr> 
                            <td valign="middle" align="left"  style="font-size:12px;color:#7f7f7f;line-height:24px">题目分数：<s:property value='#result[6]'/></td>
                          </tr>
                          <tr> 
                            <td valign="middle" align="left" style="font-size:12px;color:#7f7f7f;line-height:24px">此题得分：<input type="text" size=2 style="text-align:center" id="<s:property value='%{#result[9] + "_score"}'/>" name="<s:property value='%{#result[9] + "_score"}'/>" value="<s:property value='#result[7]'/>" onblur="checkscore('<s:property value='%{#result[9] + "_score"}'/>',this.value,'<s:property value='#result[6]'/>')"></td>
                          </tr>
                          <tr> 
                            <td valign="middle" align="left" style="font-size:12px;color:#7f7f7f;line-height:24px">批注：<textarea name="<s:property value='%{#result[9] + "_note"}'/>"><s:property value='#result[8]'/></textarea></td>
                          </tr>
                          <tr> 
                            <td valign="middle" align="left" style="font-size:12px;color:#7f7f7f;line-height:24px">&nbsp;</td>
                          </tr>
                        </s:if>
                        <s:else>
                          <tr> 
                            <td valign="middle" align="left"><s:property value='%{#stus.index + 1}'/>．<s:property value='#result[1]' escape='false'/>&nbsp;&nbsp;&nbsp;&nbsp; <s:property value='#result[2]' escape='false'/> <s:property value='#result[3]' escape='false'/></td>
                          </tr>
                        <s:if test="#result[8]==true">
                        <s:iterator value='#result[10]' id='list' status='stas'>
                          <tr> 
                            <td valign="middle" align="left"  style="font-size:12px;color:#7f7f7f;line-height:24px">&nbsp;&nbsp;<s:property value='%{#stas.index + 1}'/>. <s:property value='#list[0]' escape='false'/>&nbsp;&nbsp; <s:property value='#list[1]' escape='false'/></td>
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
                            <td valign="middle" align="left" style="font-size:12px;color:#7f7f7f;line-height:24px">&nbsp;&nbsp;&nbsp;&nbsp;此题得分：<input type="text" size=2 style="text-align:center" id="<s:property value='%{#result[7] + "_" + #stas.index + "_score"}'/>" name="<s:property value='%{#result[7] + "_" + #stas.index + "_score"}'/>" value="<s:property value='#list[5]'/>" onblur="checkscore('<s:property value='%{#result[7] + "_score"}'/>',this.value,'<s:property value='#list[4]'/>');totalScore();"></td>
                          </tr>
                          <tr> 
                            <td valign="middle" align="left" style="font-size:12px;color:#7f7f7f;line-height:24px">&nbsp;&nbsp;&nbsp;&nbsp;批注：<textarea name="<s:property value='%{#result[7] + "_" + #stas.index + "_note"}'/>"><s:property value='#list[6]'/></textarea></td>
                          </tr>
                          <tr> 
                            <td valign="middle" align="left">&nbsp;</td>
                          </tr>
                        </s:iterator>
                       	</s:if>
                       	<s:else>
                          <tr> 
                            <td valign="middle" align="left" style="font-size:12px;color:#7f7f7f;line-height:24px">&nbsp;&nbsp;&nbsp;&nbsp;未答此题</td>
                          </tr>
                        </s:else>
                        <input type="hidden" name="<s:property value='%{#result[7] + "_totalnum"}'/>" value="<s:property value='#result[9]'/>">
                          <tr> 
                            <td valign="middle" align="left" style="font-size:12px;color:#7f7f7f;line-height:24px">&nbsp;&nbsp;题目总分数：<s:property value='#result[4]'/></td>
                          </tr>
                          <tr> 
                            <td valign="middle" align="left" style="font-size:12px;color:#7f7f7f;line-height:24px">&nbsp;&nbsp;题目总得分：<input type="text" size=2 style="text-align:center" name="<s:property value='%{#result[7] + "_score"}'/>" value="<s:property value='#result[5]'/>" ></td>
                          </tr>
                          <tr> 
                            <td valign="middle" align="left" style="font-size:12px;color:#7f7f7f;line-height:24px">&nbsp;&nbsp;题目总批注：<textarea name="<s:property value='%{#result[7] + "_note"}'/>"><s:property value='#result[6]'/></textarea></td>
                          </tr>
                          <tr> 
                            <td valign="middle" align="left">&nbsp;</td>
                          </tr>
                          </s:else>
                       </s:iterator>
                          <tr> 
                            <td valign="middle" align="left" style="font-size:12px;color:#7f7f7f;line-height:24px">试卷总得分：<input type="text" size=2 style="text-align:center" name="totalScore" value=<s:if test="totalScore != null and totalScore != 'null'"><s:property value='totalScore'/></s:if><s:else>0</s:else> ></td>
                          </tr>
                          <tr> 
                            <td valign="middle" align="left" style="font-size:12px;color:#7f7f7f;line-height:24px">试卷总批注：<textarea name="totalNote"><s:property value='totalNote'/></textarea></td>
                          </tr>
			              <tr> 
			                <td ><table width="40%" border="0" align="center" cellpadding="0" cellspacing="0">
			                    <tr align="center"> 
			                      <td class="text"><img src="/entity/function/images/OK.gif" style='cursor: pointer;' width="80" height="24" onclick="document.check.submit()"></td>
			                      <td class="text"><img src="/entity/function/images/tlfh.gif" style='cursor: pointer;' width="80" height="24" onclick="window.location.href='/entity/studyZone/courseResources_viewTestHistoryPaper.action?course_id=<s:property value='course_id'/>&test_id=<s:property value='test_id'/>&paper_id=<s:property value='paper_id'/>'"></td>
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
</form>
</body>
</html>
<script>
	function totalScore()
	{
		var tags = document.getElementsByTagName("INPUT");
		var total = 0;
		for(var i=0;i<tags.length;i++)
		{
			var t = tags[i].type;
			alert(t);
			var name = tags[i].name;
			if(t == 'text' && name.indexOf("score") != -1 && name.indexOf("total") < 0)
			{
				total += parseInt(tags[i].value);
			}
		}
		document.check.totalScore.value = total;
	}
	//totalScore();
</script>