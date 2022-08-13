<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@page import="com.whaty.platform.test.question.PaperQuestion"%>
<%@page import="com.whaty.platform.test.question.TestQuestionType"%>
<%@ include file="../pub/priv.jsp"%>
<%
	String id = request.getParameter("id");
	try {
		InteractionFactory interactionFactory = InteractionFactory.getInstance();
		InteractionManage interactionManage = interactionFactory.creatInteractionManage(interactionUserPriv);	
		TestManage testManage = interactionManage.creatTestManage();
		PaperQuestion question = testManage.getPaperQuestion(id);
		String type = question.getType();
		if(type.equalsIgnoreCase(TestQuestionType.YUEDU))
		{
			%>
			<script>
			location.replace='paper_question_comprehension_info.jsp?id=<%=id%>';
			</script>
			<%
			return;
		}		
		String questionCore = "";
		if(type.equalsIgnoreCase(TestQuestionType.DANXUAN) || type.equalsIgnoreCase(TestQuestionType.DUOXUAN))
			questionCore = XMLParserUtil.getSingleMultiContent(question.getQuestionCore());
		if(type.equalsIgnoreCase(TestQuestionType.TIANKONG) || type.equalsIgnoreCase(TestQuestionType.WENDA))
			questionCore = XMLParserUtil.getBlankAnswerContent(question.getQuestionCore());
		if(type.equalsIgnoreCase(TestQuestionType.PANDUAN))
			questionCore = XMLParserUtil.getJudgContent(question.getQuestionCore());
		if("student".equalsIgnoreCase(userType) && questionCore.length()>0)
		{
			questionCore = questionCore.substring(0,questionCore.indexOf("答案:"));
		}
		//com.whaty.util.string.WhatyStrManage strManage = new com.whaty.util.string.WhatyStrManage(questionCore);
		//questionCore = strManage.htmlDecode();
		//out.print("abc"+questionCore);
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>题目信息</title>
<link href="../css/css.css" rel="stylesheet" type="text/css">
</head>
<body leftmargin="0" topmargin="0"  background="images/bg2.gif">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td valign="top" class="bg3"><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td height="86" valign="top" background="images/top_01.gif"><img src="images/zxzc.gif" width="217" height="86"></td>
              </tr>
              <tr>
                <td align="center" valign="top"><table width="608" border="1" cellspacing="0" cellpadding="0" bordercolordark="BDDFF8" bordercolorlight="#FFFFFF">
                    <tr>
                      <td bgcolor="#FFFFFF"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td width="78"><img src="images/wt_02.gif" width="78" height="52"></td>
                            <td width="100" valign="top" class="text3" style="padding-top:25px">题目信息</td>
                            <td background="images/wt_03.gif">&nbsp;</td>
                          </tr>
                        </table></td>
                    </tr>
                    <tr> 
                      <td><img src="images/wt_01.gif" width="605" height="11"></td>
                    </tr>
                  <tr>
                            
                      <td><img src="images/wt_04.gif" width="604" height="13"></td>
                          </tr>
                          <tr>
                            <td background="images/wt_05.gif"><table width="95%" border="0" align="center" cellpadding="0" cellspacing="0">
                          <tr>
                            <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                  <td width="38" valign="bottom">&nbsp;</td>
                                  <td valign="bottom" class="text1"></td>
                                </tr>
                              </table></td>
                          </tr>
                          <tr>
                            <td><img src="images/wt_08.gif" width="572" height="11"></td>
                          </tr>
                          <tr>
                            <td background="images/wt_10.gif"><table width="90%" border="0" align="center" cellpadding="0" cellspacing="0">
                            
                                <tr>
                                  <td width="10%" align="center"></td>
                                  <td class="text6"><%=questionCore%></td>
                                </tr>
                        
                              </table></td>
                          </tr>
                          <tr>
                            <td><img src="images/wt_09.gif" width="572" height="11"></td>
                          </tr>
                        </table></td>
                          </tr>
                          <tr>
                            
                      <td><img src="images/wt_06.gif" width="604" height="11"></td>
                          </tr>
                    <tr>
                      <td align="center"></td>
                    </tr>
                  </table></td>
                    </tr>
                  </table> <br>
                </td>
              </tr>

            </table></td>
        </tr>
      </table>
</body>
<%
	} catch (Exception e) {
		out.print(e.toString());
	}
%>
</html>
