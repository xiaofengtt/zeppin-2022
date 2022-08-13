<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>案例分析题</title>
<link href="/entity/function/css/css.css" rel="stylesheet" type="text/css">
<script>
	function onCommit(t,b)
	{
		var dire = b;
		for(k=1;k<=t;k++)
		{
			var select = false;
			var answer = document.getElementsByName("answer_"+k);
			//alert("answer_"+k+"  "+answer.length);
			var i=0;
			if(answer.length>1)
			{
				for(;i<answer.length;i++)
				{
					if(answer[i].checked)
					{
						select = true;
						break;
					}
				}
			}
			else
			{
				if(answer[i].value.length>0)
				{
					select = true;
				}
			}
			if(!select)
			{
				alert("请填写答案！");
				answer[i].focus();
				return;
			}
		}
		
		document.answers.action = "/entity/studyZone/courseResources_saveResultAndDirect.action?direction="+dire;
		document.answers.submit();
	}
	
</script>
</head>
<body>
<table width="98%" border="0" cellspacing="0" cellpadding="0">
  <tr> 
    <td height="42"  style="padding-left:53px;padding-top:8px" class="text3">案例分析题</td>
  </tr>
<form id="answers" name="answers" action="" method="post">
<input type="hidden" name="qNum" value="<s:property value='qNum'/>">
<input type="hidden" name="type" value="<s:property value='type'/>">
  <tr> 
    <td valign="top"  align=center> <table height="390" border="0" cellpadding="0" cellspacing="0" align=center>
        <tr> 
          <td valign="top" class="bg2"> <table width="100%" border="0" cellspacing="0" cellpadding="6">
              <tr> 
                <td class="content1"><s:property value='title' escape='false'/></td>
              </tr>
            <s:iterator value='comprehensionObjectList' id='comp'>
            <s:if test="#comp[4] == 'DANXUAN'">
              <tr> 
                <td valign="top" style="padding-left:30px">
                <table width="80%" border="0" cellspacing="0" cellpadding="0">
                	<tr> 
					  <td class="mc1"><s:property value='#comp[0]'/>．<s:property value='#comp[1]' escape='false'/></td>
                	</tr>
                	<tr> 
					  <td class="mc1">&nbsp;&nbsp;<s:property value='#comp[2]' escape='false'/></td>
                	</tr>
					<tr> 
						<td valign="top" style="padding-left:30px">
						<table width="80%" border="0" cellspacing="0" cellpadding="0">
			              <s:iterator value='#comp[5]' id='danxuan'>
							<tr> 
						      <td width="10%" class="mc1"><s:property value='#danxuan[0]' escape='false'/>
						      	<input type="radio" name="answer_<s:property value='#comp[0]'/>" value="<s:property value='#danxuan[0]'/>" <s:if test="#comp[3] == #danxuan[0]">checked</s:if> >
						      </td>
						      <td class="mc1"><s:property value='#danxuan[1]'/></td>
						    </tr>
					      </s:iterator>
						</table>
						</td>
					</tr>
                </table>
                </td>
              </tr>
            </s:if>
			<s:if test="#comp[4] == 'DUOXUAN'">
              <tr> 
                <td valign="top" style="padding-left:30px">
                <table width="80%" border="0" cellspacing="0" cellpadding="0">
                	<tr> 
					  <td class="mc1"><s:property value='#comp[0]'/>．<s:property value='#comp[1]' escape='false'/></td>
                	</tr>
                	<tr> 
					  <td class="mc1">&nbsp;&nbsp;<s:property value='#comp[2]' escape='false'/></td>
                	</tr>
					<tr> 
						<td valign="top" style="padding-left:30px">
						<table width="80%" border="0" cellspacing="0" cellpadding="0">
			              <s:iterator value='#comp[5]' id='duoxuan'>
							<tr> 
						      <td width="10%" class="mc1"><s:property value='#duoxuan[0]' escape='false'/>
						      	<input type="checkbox" name="answer_<s:property value='#comp[0]'/>" value="<s:property value='#duoxuan[0]'/>" <s:if test="#comp[3].contains(#duoxuan[0])">checked</s:if> >
						      </td>
						      <td class="mc1"><s:property value='#duoxuan[1]'/></td>
						    </tr>
					      </s:iterator>
						</table>
						</td>
					</tr>
                </table>
                </td>
              </tr>
			</s:if>
			<s:if test="#comp[4] == 'PANDUAN'">
              <tr> 
                <td valign="top" style="padding-left:30px">
                <table width="80%" border="0" cellspacing="0" cellpadding="0">
                	<tr> 
					  <td class="mc1"><s:property value='#comp[0]'/>．<s:property value='#comp[1]' escape='false'/></td>
                	</tr>
                	<tr> 
					  <td class="mc1">&nbsp;&nbsp;<s:property value='#comp[2]' escape='false'/></td>
                	</tr>
					<tr> 
						<td valign="top" style="padding-left:30px">
						<table width="80%" border="0" cellspacing="0" cellpadding="0">
							<tr> 
						      <td width="10%" class="mc1">
						      	<input type="radio" name="answer_<s:property value='#comp[0]'/>" value="正确" <s:if test="#comp[3] == '正确'">checked</s:if> >正确&nbsp;
						      	<input type="radio" name="answer_<s:property value='#comp[0]'/>" value="错误" <s:if test="#comp[3] == '错误'">checked</s:if> >错误
						      </td>
						    </tr>
						</table>
						</td>
					</tr>
                </table>
                </td>
              </tr>
			</s:if>
			<s:if test="#comp[4] == 'TIANKONG'">
              <tr> 
                <td valign="top" style="padding-left:30px">
                <table width="80%" border="0" cellspacing="0" cellpadding="0">
                	<tr> 
					  <td class="mc1"><s:property value='#comp[0]'/>．<s:property value='#comp[1]' escape='false'/></td>
                	</tr>
                	<tr> 
					  <td class="mc1">&nbsp;&nbsp;<s:property value='#comp[2]' escape='false'/></td>
                	</tr>
					<tr> 
						<td valign="top" style="padding-left:30px">
						<table width="80%" border="0" cellspacing="0" cellpadding="0">
							<tr> 
						      <td width="10%" class="mc1">
						      	<textarea name="answer_<s:property value='#comp[0]'/>" cols="40" rows="5"><s:property value='#comp[3]'/></textarea>
						      </td>
						    </tr>
						</table>
						</td>
					</tr>
                </table>
                </td>
              </tr>
			</s:if>
			<s:if test="#comp[4] == 'WENDA'">
              <tr> 
                <td valign="top" style="padding-left:30px">
                <table width="80%" border="0" cellspacing="0" cellpadding="0">
                	<tr> 
					  <td class="mc1"><s:property value='#comp[0]'/>．<s:property value='#comp[1]' escape='false'/></td>
                	</tr>
                	<tr> 
					  <td class="mc1">&nbsp;&nbsp;<s:property value='#comp[2]' escape='false'/></td>
                	</tr>
					<tr> 
						<td valign="top" style="padding-left:30px">
						<table width="80%" border="0" cellspacing="0" cellpadding="0">
							<tr> 
						      <td width="10%" class="mc1">
						      	<textarea name="answer_<s:property value='#comp[0]'/>" cols="40" rows="5"><s:property value='#comp[3]'/></textarea>
						      </td>
						    </tr>
						</table>
						</td>
					</tr>
                </table>
                </td>
              </tr>
            </s:if>
            </s:iterator>
              </table></td></tr>
              <tr> 
                <td ><table width="60%" border="0" align="center" cellpadding="0" cellspacing="0">
                    <tr align="center"> 
                      <td>
                      <s:if test="hasPre">
                      	<input type=button value="上一题" style="font-size:12px;height:23;background-color:#ece9d8;border-width:1;" border=0 onclick="javascript:onCommit('<s:property value='%{comprehensionObjectList.size()-1}'/>','last')">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                      </s:if>
                     <s:if test="hasNext">
                       <input type=button value="下一题" style="font-size:12px;height:23;background-color:#ece9d8;border-width:1;" border=0 onclick="javascript:onCommit('<s:property value='%{comprehensionObjectList.size()-1}'/>','next')">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                     </s:if>
                     <s:else>
                        <input type=button value="完成自测" style="font-size:12px;height:23;background-color:#ece9d8;border-width:1;" border=0 onclick="javascript:onCommit('<s:property value='%{comprehensionObjectList.size()-1}'/>','final')">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    </s:else>
                        <input type=reset value="重填" style="font-size:12px;height:23;background-color:#ece9d8;border-width:1;" border=0>&nbsp;&nbsp;
                      <!-- img src="/entity/function/images/OK.gif" width="80" height="24" onclick="onCommit()"></td>
                      <td><img src="/entity/function/images/reset.gif" width="80" height="24" onclick="reset()"--></td>
                    </tr>
                  </table></td>
              </tr>
            </table></td>
        </tr>
      </table></td>
  </tr>
  <tr> 
    <td><img src="/entity/function/images/st_07.gif" width="489" height="15"></td>
  </tr>
</table>
<input type="hidden" name="totalNum" value="<s:property value='comprehensionObjectList.size()'/>">
</form>
</body>
</html>