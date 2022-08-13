<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="java.util.*,com.whaty.platform.util.*,com.whaty.platform.util.log.*"%>
<%@ page import="com.whaty.platform.entity.activity.*,com.whaty.platform.entity.activity.score.*"%>
<%@ page import="com.whaty.platform.entity.*,com.whaty.platform.entity.basic.*,com.whaty.platform.entity.config.*"%>
<%@ page import="com.whaty.platform.entity.recruit.*,com.whaty.platform.entity.setup.*"%>
<%@ page import="com.whaty.platform.entity.user.*,com.whaty.platform.entity.test.*"%>
<%@ page import="com.whaty.platform.interaction.*,com.whaty.platform.interaction.answer.*"%>
<%@ include file="../pub/priv.jsp"%>

<%
	String question_id = request.getParameter("id");
	String xml = "";
	try {
		InteractionFactory interactionFactory = InteractionFactory.getInstance();
		InteractionManage interactionManage = interactionFactory.creatInteractionManage(interactionUserPriv);		
		
		Question question = interactionManage.getQuestion(question_id);   


		
/*	//	xml = "<faq><author>" + question.getSubmituserName() + "--abXL8kdikvkLMbd9T--"+question_id+ "</author><time>" 
	//		+ question.getDate() + "</time><body>" + question.getBody() + "</body>";
*/				
		xml = "<faq><author>" + question.getSubmituserName() + "</author><time>" 
			+ question.getDate() + "</time><body>" + question_id + "</body>";
//		System.out.println("faq_add->xml1->"+xml);
		List searchProperty = new ArrayList();
		searchProperty.add(new SearchProperty("question_id", question_id, "="));
		List answerList = interactionManage.getAnswers(null, searchProperty, null);	        
        for(int i=0;i<answerList.size();i++) {
	    	Answer answer = (Answer)answerList.get(i);
	    	xml = xml + "<answer><reauthor>" + answer.getReuserName()+"</reauthor><retime>"
	    		+ answer.getDate() + "</retime><rebody>" + answer.getBody() + "</rebody></answer>";
	    }
	    xml = xml + "</faq>";
//	    System.out.println("faq_add->xml2->"+xml);
	    //out.print("XML:" + xml);
%>

<html>
<head>
<title>生殖健康咨询师培训网</title>
<link href="../css/css.css" rel="stylesheet" type="text/css">
</head>
<body leftmargin="0" topmargin="10"  background="../images/bg2.gif" style='overflow:scroll;overflow-x:hidden'>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td valign="top" class="bg3">
          	<table width="100%" border="0" cellspacing="0" cellpadding="0">              
              <tr>
                <td align="center" valign="top">
                <table width="680" border="1" cellspacing="0" cellpadding="0" bordercolordark="BDDFF8" bordercolorlight="#FFFFFF">
              	<tr>
              	<td bgcolor="#FFFFFF">
              	<table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td valign="top"><img src="../images/answer_02.gif" width="680" height="10"></td>
                    </tr>
                    <tr> 
                      <td valign="top" background="../images/answer_03.gif">
                      <table width="94%" border="0" align="center" cellpadding="0" cellspacing="0">
                          <tr>
                            <td width="22%"><img src="../images/answer_04.gif" width="132" height="93"></td>
                            <td valign="top" class="text2">
                            <form name="add_form" action="faq_addexe.jsp" method="post">
                         
                            <input type="hidden" name="xml" value=" <%=xml.replace("\"","'")%> ">
                           
                            <input type="hidden" name="title" value=" <%=question.getTitle() %> ">
                           
                            <table border="0" align="center" cellpadding="0" cellspacing="0">
                            	<tr>
                            		<td class="text4">语  言：</td>
                            		<td class="text4"><input type="text" name="language"></td>
                            	</tr>
                            	<tr>
                            		<td class="text4">描  述：</td>
                            		<td class="text4"><input type="text" name="description"></td>
                            	</tr>
                            	<tr>
                            		<td class="text4">关键字：</td>
                            		<td class="text4"><input type="text" name="keyword"></td>
                            	</tr>
                            	<tr>
                            		<td colspan="2" align="center"><input type="submit" value="添加">&nbsp;<input type="button" value="返回" onclick="window.navigate('question_detail.jsp?id=<%=question_id%>')">
</td>
                            	</tr>                            	
                           	</table>
                           	</form>
                            </td>
                          </tr>
                      </table>
                      </td>
                    </tr>
                    <tr> 
                      <td valign="top"><img src="../images/answer_05.gif" width="680" height="12"></td>
                    </tr>
                    <tr> 
                      <td height="23" align="center" background="../images/answer_03.gif"></td>
                    </tr>
                    <tr> 
                      <td valign="top"><img src="../images/answer_07.gif" width="680" height="19"></td>
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
<%
	} catch (Exception e) {
		out.print(e.getMessage());
		return;
	}
%>
