<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>问答题</title>
<link href="/entity/function/css/css.css" rel="stylesheet" type="text/css">
 
</head>

<body>
<table width="98%" border="0" cellspacing="0" cellpadding="0">
<s:if test="singleObjectList != null and singleObjectList.size() > 0">
	<tr> 
    	<td height="42" style="padding-left:23px;padding-top:8px" class="text3">单项选择题</td>
 	</tr>
	<s:iterator value='singleObjectList' id='single'>
	<tr> 
    	<td valign="top" style="padding-left:23px;padding-top:8px"> <table border="0" cellpadding="0" cellspacing="0">
        <tr> 
          <td valign="top" class="bg2"> <table width="100%" border="0" cellspacing="0" cellpadding="6">
              <tr> 
                <td width="100%" align="left" class="content1"><s:property value='#single[0]' escape='false'/></td>
              </tr>
              <tr> 
                <td width="100%" valign="top" align="left" style="padding-left:10px">
                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                	<s:iterator value='#single[2]' id='indexcontent'>
	                    <tr> 
	                      <td width="10%" class="mc1"><input type="radio" name="single_<s:property value='#single[1]'/>" value="<s:property value='#indexcontent[0]'/>"></td>
	                      <td width="90%" class="mc1" nowrap><s:property value='#indexcontent[1]' escape='false'/></td>
	                    </tr>
                    </s:iterator>
                  </table></td>
              </tr>
            </table></td>
        </tr>
      </table></td>
  </tr>
	</s:iterator>
</s:if>
<s:if test="multiObjectList != null and multiObjectList.size() > 0">
  <tr> 
    <td height="42" style="padding-left:23px;padding-top:8px" class="text3">多项选择题</td>
  </tr>
  <s:iterator value='multiObjectList' id='multi'>
  <tr> 
    <td valign="top" style="padding-left:23px;padding-top:8px"> <table border="0" cellpadding="0" cellspacing="0">
        <tr> 
          <td valign="top" class="bg2"> <table width="100%" border="0" cellspacing="0" cellpadding="6">
              <tr> 
                <td width="100%" align="left" class="content1"><s:property value='#multi[0]' escape='false'/></td>
              </tr>
              <tr> 
                <td width="100%" valign="top" align="left" style="padding-left:10px">
                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                	<s:iterator value='#multi[2]' id='indexcontent'>
                    <tr> 
                      <td width="10%" class="mc1"><input type="checkbox" name="multi_<s:property value='#multi[1]'/>" value="<s:property value='#indexcontent[0]'/>"></td>
                      <td width="90%" class="mc1" nowrap><s:property value='#indexcontent[1]' escape='false'/></td>
                    </tr>
                    </s:iterator>
                  </table></td>
              </tr>
            </table></td>
        </tr>
      </table></td>
  </tr>
  </s:iterator>
</s:if>
<s:if test="judgeObjectList != null and judgeObjectList.size() > 0">
  <tr> 
    <td height="42" style="padding-left:23px;padding-top:8px" class="text3">判断题</td>
  </tr>
<s:iterator value='judgeObjectList' id='judge'>
  <tr> 
    <td valign="top" style="padding-left:23px;padding-top:8px"> <table border="0" cellpadding="0" cellspacing="0">
        <tr> 
          <td valign="top" class="bg2"> <table width="100%" border="0" cellspacing="0" cellpadding="6">
              <tr> 
                <td width="100%" align="left" class="content1"><s:property value='#judge[0]' escape='false'/></td>
              </tr>
              <tr> 
                <td width="100%" valign="top" align="left" style="padding-left:10px">
                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td width="10%" class="mc1"><input type="radio" name="judge_<s:property value='#judge[1]'/>" value="1"></td>
                      <td width="90%" class="mc1">正确</td>
                    </tr>
                    <tr> 
                      <td width="10%" class="mc1"><input type="radio" name="judge_<s:property value='#judge[1]'/>" value="0"></td>
                      <td width="90%" class="mc1">错误</td>
                    </tr>
                  </table></td>
              </tr>
            </table></td>
        </tr>
      </table></td>
  </tr>
  </s:iterator>
 </s:if>
<s:if test="blankObjectList != null and blankObjectList.size() > 0">
  <tr> 
    <td height="42" style="padding-left:23px;padding-top:8px" class="text3">填空题</td>
  </tr>
<s:iterator value='blankObjectList' id='blank'>
  <tr> 
    <td valign="top" style="padding-left:23px;padding-top:8px"> <table border="0" cellpadding="0" cellspacing="0">
        <tr> 
          <td valign="top" class="bg2"> <table width="100%" border="0" cellspacing="0" cellpadding="6">
              <tr> 
                <td width="100%" align="left" class="content1"><s:property value='#blank[0]' escape='false'/></td>
              </tr>
              <tr> 
                <td width="100%" valign="top" align="left" style="padding-left:10px">
                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td width="0%" class="mc1"></td>
                      <td width="100%" class="mc1"><textarea name="blank_<s:property value='blank[1]'/>"></textarea></td>
                    </tr>
                  </table></td>
              </tr>
            </table></td>
        </tr>
      </table></td>
  </tr>
  </s:iterator>
  </s:if>
<s:if test="answerObjectList != null and answerObjectList.size() > 0">
  <tr> 
    <td height="42" style="padding-left:23px;padding-top:8px" class="text3">问答题</td>
  </tr>
<s:iterator value='answerObjectList' id='answer'>
  <tr> 
    <td valign="top" style="padding-left:23px;padding-top:8px"> <table border="0" cellpadding="0" cellspacing="0">
        <tr> 
          <td valign="top" class="bg2"> <table width="100%" border="0" cellspacing="0" cellpadding="6">
              <tr> 
                <td width="100%" align="left" class="content1"><s:property value='answer[0]' escape='false'/></td>
              </tr>
              <tr> 
                <td width="100%" valign="top" align="left" style="padding-left:10px">
                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td width="0%" class="mc1"></td>
                      <td width="30%" class="mc1"><textarea name="answer_<s:property value='answer[1]'/>"></textarea></td>
                      <td style="padding-left:5px"></td>
					  <td width="70%" valign="bottom" style="padding-left:5px"></td>
                    </tr>
                  </table></td>
              </tr>
            </table></td>
        </tr>
      </table></td>
  </tr>
  </s:iterator>
 </s:if>
<s:if test="comprehensionObjectList != null and comprehensionObjectList.size() > 0">
  <tr> 
    <td height="42" style="padding-left:23px;padding-top:8px" class="text3">案例分析题</td>
  </tr>
  <s:iterator value='comprehensionObjectList' id='comp' status='stus'>
  <input type="hidden" name="comp_<s:property value='#comp[0]'/>_totalNum" value="<s:property value='#comp[1]'/>">	
  <tr> 
    <td valign="top" style="padding-left:23px;padding-top:8px"> <table border="0" cellpadding="0" cellspacing="0">
        <tr> 
          <td valign="top" class="bg2"> <table width="100%" border="0" cellspacing="0" cellpadding="6">
              <tr> 
                <td class="content1" width="100%"><s:property value='#comp[2]' escape='false'/></td>
              </tr>
              <s:iterator value='#comp[3]' id='sub' status='stas'>
			  	<s:if test="#sub[2] == 'DANXUAN'">
              <tr> 
                <td width="100%" valign="top" style="padding-left:10px">
                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                	<tr> 
					  <td width="100%" class="mc1"><s:property value='%{#stas.index + 1}'/>．<s:property value='#sub[0]' escape='false'/></td>
                	</tr>
                	<tr> 
					  <td width="100%" class="mc1">&nbsp;&nbsp;<s:property value='#sub[1]' escape='false'/></td>
                	</tr>
					<tr> 
						<td width="100%" valign="top" style="padding-left:10px">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
			              	<s:iterator value='#sub[3]' id='subsub'>
							<tr> 
						      <td width="10%" class="mc1">
						      	<input type="radio" name="comp_<s:property value='#comp[0]'/>_<s:property value='%{#stas.index + 1}'/>" value="<s:property value='#subsub[0]'/>">
						      </td>
						      <td width="90%" class="mc1"><s:property value='#subsub[1]'/></td>
						    </tr>
					        </s:iterator>
						</table>
						</td>
					</tr>
                </table>
                </td>
              </tr>
              </s:if>
			  <s:elseif test="#sub[2] == 'DUOXUAN'">
              <tr> 
                <td width="100%" valign="top" style="padding-left:10px">
                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                	<tr> 
					  <td width="100%" class="mc1"><s:property value='%{#stas.index + 1}'/>．<s:property value='#sub[0]' escape='false'/></td>
                	</tr>
                	<tr> 
					  <td width="100%" class="mc1">&nbsp;&nbsp;<s:property value='#sub[1]' escape='false'/></td>
                	</tr>
					<tr> 
						<td width="100%" valign="top" style="padding-left:10px">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
			              	<s:iterator value='#sub[3]' id='subsub2'>
							<tr> 
						      <td width="10%" class="mc1">
						      	<input type="checkbox" name="comp_<s:property value='#comp[0]'/>_<s:property value='%{#stas.index + 1}'/>" value="<s:property value='#subsub2[0]'/>">
						      </td>
						      <td width="90%" class="mc1"><s:property value='#subsub2[1]'/></td>
						    </tr>
					        </s:iterator>
						</table>
						</td>
					</tr>
                </table>
                </td>
              </tr>
              </s:elseif>
              <s:elseif test="#sub[2] == 'PANDUAN'">
              <tr> 
                <td width="100%" valign="top" style="padding-left:10px">
                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                	<tr> 
					  <td width="100%" class="mc1"><s:property value='%{#stas.index + 1}'/>．<s:property value='#sub[0]' escape='false'/></td>
                	</tr>
                	<tr> 
					  <td width="100%" class="mc1">&nbsp;&nbsp;<s:property value='#sub[1]' escape='false'/></td>
                	</tr>
					<tr> 
						<td width="100%" valign="top" style="padding-left:10px">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr> 
						      <td width="100%" class="mc1">
						      	<input type="radio" name="comp_<s:property value='#comp[0]'/>_<s:property value='%{#stas.index + 1}'/>" value="正确">正确&nbsp;<input type="radio" name="comp_<s:property value='#comp[0]'/>_<s:property value='%{#stas.index + 1}'/>" value="错误">错误
						      </td>
						    </tr>
						</table>
						</td>
					</tr>
                </table>
                </td>
              </tr>
              </s:elseif>
              <s:elseif test="#sub[2] == 'TIANKONG'">
              <tr> 
                <td width="100%" valign="top" style="padding-left:10px">
                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                	<tr> 
					  <td width="100%" class="mc1"><s:property value='%{#stas.index + 1}'/>．<s:property value='#sub[0]' escape='false'/></td>
                	</tr>
                	<tr> 
					  <td width="100%" class="mc1">&nbsp;&nbsp;<s:property value='#sub[1]' escape='false'/></td>
                	</tr>
					<tr> 
						<td width="100%" valign="top" style="padding-left:10px">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr> 
						      <td width="100%" class="mc1">
						      	<textarea name="comp_<s:property value='#comp[0]'/>_<s:property value='%{#stas.index + 1}'/>" cols="40" rows="5"></textarea>
						      </td>
						    </tr>
						</table>
						</td>
					</tr>
                </table>
                </td>
              </tr>
              </s:elseif>
              <s:elseif test="#sub[2] == 'WENDA'">
              <tr> 
                <td width="100%" valign="top" style="padding-left:10px">
                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                	<tr> 
					  <td width="100%" class="mc1"><s:property value='%{#stas.index + 1}'/>．<s:property value='#sub[0]' escape='false'/></td>
                	</tr>
                	<tr> 
					  <td width="100%" class="mc1">&nbsp;&nbsp;<s:property value='#sub[1]' escape='false'/></td>
                	</tr>
					<tr> 
						<td width="100%" valign="top" style="padding-left:10px">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr> 
						      <td width="100%" class="mc1">
						      	<textarea name="comp_<s:property value='#comp[0]'/>_<s:property value='%{#stas.index + 1}'/>" cols="40" rows="5"></textarea>
						      </td>
						    </tr>
						</table>
						</td>
					</tr>
                </table>
                </td>
              </tr>
              </s:elseif>
          </s:iterator>
              </table></td></tr>
              </tr>
            </table></td>
        </tr>
      </table></td>
  	</tr>
  </s:iterator>
</s:if>
</table>
</body>
</html>
