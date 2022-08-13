<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*,com.whaty.platform.util.*,com.whaty.platform.util.log.*"%>
<%@ page import="com.whaty.platform.info.*,com.whaty.platform.entity.basic.*,com.whaty.platform.entity.config.*"%>
<%@ page import="com.whaty.platform.entity.basic.*,com.whaty.platform.entity.setup.*"%>
<%@ page import="com.whaty.platform.entity.user.*,com.whaty.platform.entity.test.*"%>
<%@ page import="com.whaty.platform.entity.activity.*,com.whaty.platform.entity.activity.score.*"%>
<%@ page import="com.whaty.platform.entity.*,com.whaty.platform.entity.basic.*,com.whaty.platform.entity.config.*"%>
<%@ page import="com.whaty.platform.entity.basic.*,com.whaty.platform.entity.setup.*"%>
<%@ page import="com.whaty.platform.entity.user.*,com.whaty.platform.entity.test.*"%>
<%@ page import="com.whaty.platform.interaction.*,com.whaty.platform.interaction.answer.*"%>
<%@ include file="../pub/priv.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>生殖健康咨询师培训网</title>
<link href="../css/css.css" rel="stylesheet" type="text/css">
</head>

<body leftmargin="0" topmargin="0"  background="../images/bg2.gif">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td valign="top" class="bg3"><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td valign="top" background="../images/top_01.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td width="217" height="104" rowspan="2" valign="top"><img src="../images/dy.gif" width="217" height="86"></td>
                      <td height="46">&nbsp;</td>
                    </tr>
                    <tr> 
                      <td align="right" valign="top"> 
                      	<form name = "search" method="post" action="answer_list.jsp">
                        <table width="100%" border="0" cellpadding="0" cellspacing="0">
                          <tr>
                            <td align="right"><a href="question_add.jsp" class="tj">[答疑]</a></td>
                            <!-- td align="center"><img src="../images/xb.gif" width="48" height="32"></td>
                            <td align="center" class="mc1">按名称搜索：</td>
                            <td align="center">
<input name="title" type="text" size="20" maxlength="50"></td>
                            <td align="center" class="mc1">按姓名搜索：</td>
                            <td align="center">
<input name="name" type="text" size="20" maxlength="50"></td>
                            <td align="center"><a href="javascript:document.search.submit()"><img src="../images/search.gif" width="99" height="19"></a></td>
                            <td width="35">&nbsp;</td -->
                          </tr>
                        </table>
                        </form>
                      </td>
                    </tr>
                  </table></td>
              </tr>
              
              
              
              <tr>
                <td align="center">
<table width="812" border="0" cellspacing="0" cellpadding="0">
              <tr>
                      
                <td height="26" background="../images/tabletop.gif"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
                    <tr>
                      <td width="5%" height="17">&nbsp;</td>
                      <td width="58%" align="center" class="title">问&nbsp;&nbsp;&nbsp;&nbsp;题</td>                 
                      <td width="18%" align="center" class="title">发布人</td>
                      <td align="center" class="title">发布时间</td>
                      <td width="4%">&nbsp;</td>
                    </tr>
                  </table></td>
                    </tr>
                    <tr>
                      <td align="center" background="../images/tablebian.gif">
<table width="99%" border="0" cellspacing="0" cellpadding="0" class="mc1">
  
<%  
String question_id = request.getParameter("id");
String title = request.getParameter("title");
String name = request.getParameter("name");         
List searchProperty = new ArrayList();
searchProperty.add(new SearchProperty("question_id", question_id, "="));
	try
{
		InteractionFactory interactionFactory = InteractionFactory.getInstance();
		InteractionManage interactionManage = interactionFactory.creatInteractionManage(interactionUserPriv);	
		
		//int totalItems = interactionManage.getQuestionsNum(searchProperty);
		int totalItems = interactionManage.getAnswersNum(searchProperty);
		//----------分页开始---------------
		String spagesize = (String) session.getValue("pagesize");
		String spageInt = request.getParameter("pageInt");
		Page pageover = new Page();
		pageover.setTotalnum(totalItems);
		pageover.setPageSize(spagesize);
		pageover.setPageInt(spageInt);
		int pageNext = pageover.getPageNext();
		int pageLast = pageover.getPagePre();
		int maxPage = pageover.getMaxPage();
		int pageInt = pageover.getPageInt();
		int pagesize = pageover.getPageSize();
		String link = "";
			//----------分页结束---------------		
		List answerList = interactionManage.getAnswers(pageover, searchProperty, null);
		
		Iterator it = answerList.iterator();
		boolean bg = false;
		String bgcolor = "";
		while(it.hasNext())
		{
			Answer answer = (Answer)it.next();
			if(bg == true)
			{
				bgcolor = " bgcolor=\"E8F9FF\"";
				bg = false;
			}
			else
			{
				bgcolor = "";
				bg = true;
			}
%>                                                                                                
              <tr<%=bgcolor%>>                  
                <td width="5%" align="center" valign="middle"  class="td1"><img src="../../images/xb2.gif" width="8" height="8"></td>
                <td width="58%"  class="td1"><a href="Answer_edit.jsp?id=<%=answer.getId()%>">标题<a>&nbsp;<a href="answer_list.jsp?id=<%=answer.getId()%>">[答案列表]</a></td>
                <td width="18%" align="center"  class="td1"><%=answer.getReuserName()%></td>
                <td width="40" align="center"  class="td1"><%=answer.getDate()%></td>
                <td>&nbsp;</td>
              </tr>
                          
<%
		}
%>           
                        </table>
                      </td>
                    </tr>
                    <tr>
                      <td><img src="../images/tablebottom.gif" width="812" height="4"></td>
                    </tr>
                  </table><br>
                </td>
              </tr>
			                           
              <tr>
                <td align="center">
					<table width="806" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td>
				                <%@ include file="../pub/dividepage.jsp"%>
			                </td>
		                </tr>
            		</table>
	            </td>
       		 </tr>
        
      </table>
</body>
</html>
<%
}
catch(Exception e)
{
	out.print(e.getMessage());
	return;
}
%>

 