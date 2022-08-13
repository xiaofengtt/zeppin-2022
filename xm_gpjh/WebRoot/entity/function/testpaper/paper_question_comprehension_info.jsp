<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import = "com.whaty.platform.*"%>
<%@ page import="java.util.*,com.whaty.platform.util.*,com.whaty.platform.util.log.*"%>
<%@ page import="com.whaty.platform.interaction.*"%>
<%@ page import="java.util.*,com.whaty.platform.util.*,com.whaty.platform.util.log.*"%>
<%@ page import="com.whaty.platform.entity.basic.*,com.whaty.platform.entity.config.*"%>
<%@ page import="com.whaty.platform.test.*,com.whaty.platform.test.paper.*,com.whaty.platform.test.question.*"%>
<%@ page import="com.whaty.platform.test.TestManage" %>
<%@ page import="com.whaty.platform.interaction.*,com.whaty.platform.util.*"%>
<%@ include file="../pub/priv.jsp"%>
<%
	String id = request.getParameter("id");
	try {
		InteractionFactory interactionFactory = InteractionFactory.getInstance();
		InteractionManage interactionManage = interactionFactory.creatInteractionManage(interactionUserPriv);	
		TestManage testManage = interactionManage.creatTestManage();
		PaperQuestion question = testManage.getPaperQuestion(id);
		String questionCoreXml = question.getQuestionCore();
		List coreList = XMLParserUtil.parserComprehension(questionCoreXml);
		String body = (String)coreList.get(0);
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>阅读理解题信息</title>
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
                            <td background="images/wt_10.gif">
                            <table width="90%" border="0" align="center" cellpadding="0" cellspacing="0">                            
                                <tr>
                                  <td width="10%" align="center"></td>
                                  <td class="text6"><%=body%></td>
                                </tr>                        
                            </table>
                            </td>
                          </tr>
                          <%
                          	for(int i=1; i<coreList.size(); i++) {
                          		List subList = (List)coreList.get(i);
                          		String type = (String)subList.get(0);
                          		String subBody = (String)subList.get(4);
                          		String answer = (String)subList.get(subList.size()-1);
                          %>
                          <tr>
                            <td background="images/wt_10.gif">
                            <table width="90%" border="0" align="center" cellpadding="0" cellspacing="0">                            
                                <tr>
                                  <td width="10%" align="center"></td>
                                  <td class="text6"><%=i+"."+body%></td>
                                </tr>
                                <%
                                	if(type.equals(TestQuestionType.DANXUAN) || type.equals(TestQuestionType.DUOXUAN)) {
                                		for(int j=5; j<subList.size()-2; j=j+2) {
                                			String index = (String)subList.get(j);
                                			String content = (String)subList.get(j+1);
                                %>
                                <tr>
                                  <td width="10%" align="center"></td>
                                  <td class="text6"><%=index+"."+content%></td>
                                </tr>
                                <%
                                		}
                                	} else if(type.equals(TestQuestionType.PANDUAN)) {
                                		answer = answer.equals("1") ? "正确" : "错误";
                                	}
                                %> 
<%
	if("teacher".equalsIgnoreCase(userType))
	{
%>	
                                <tr>
                                  <td width="10%" align="center"></td>
                                  <td class="text6"><%="答案："+answer%></td>
                                </tr>                       
<%
	}
%>	                            	
                            </table>
                            </td>
                          </tr>
                          <%
                          	}
                          %>
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
