<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import = "com.whaty.platform.*"%>
<%@ page import="java.util.*,com.whaty.platform.util.*,com.whaty.platform.util.log.*"%>
<%@ page import="com.whaty.platform.entity.basic.*,com.whaty.platform.entity.config.*"%>
<%@ page import="com.whaty.platform.test.*,com.whaty.platform.test.lore.*,com.whaty.platform.test.paper.*,com.whaty.platform.test.question.*"%>
<%@ page import="com.whaty.platform.test.TestManage" %>
<%@ page import="com.whaty.platform.interaction.*,com.whaty.platform.util.*"%>
<%@ include file="../pub/priv.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>tableType_2</title>
<link href="../css/css.css" rel="stylesheet" type="text/css">
<SCRIPT>
function cfmdel(link)
{
	if(confirm("您确定要删除这道题目吗？"))
		window.navigate(link);
}

function DetailInfo(type,id,id1)
{
	if(type=='YUEDU')
		window.open('store_question_comprehension_info1.jsp?id='+id +'&id1='+id1,'');
	else 
		window.open('store_question_info.jsp?id='+id,'');
	
}
  function doCommit()
  {
  	document.add.submit();
  }
</SCRIPT>
</head>

<body leftmargin="0" topmargin="0" class="scllbar">
<%
	String paperId = request.getParameter("paperId");
	HashMap paperQuestions = (HashMap) session.getAttribute("paperQuestions");
	if(paperQuestions==null)
	{
		paperQuestions = new HashMap();
		paperQuestions.put(TestQuestionType.DANXUAN,new ArrayList());
		paperQuestions.put(TestQuestionType.DUOXUAN,new ArrayList());
		paperQuestions.put(TestQuestionType.PANDUAN,new ArrayList());
		//paperQuestions.put(TestQuestionType.TIANKONG,new ArrayList());
		paperQuestions.put(TestQuestionType.WENDA,new ArrayList());
		//paperQuestions.put(TestQuestionType.YUEDU,new ArrayList());
	}
	//判断去掉填空和阅读
	if(paperQuestions.get(TestQuestionType.TIANKONG)!=null)
		paperQuestions.remove(TestQuestionType.TIANKONG);
	if(paperQuestions.get(TestQuestionType.YUEDU)!=null)
		paperQuestions.remove(TestQuestionType.YUEDU);
		
	Set set = paperQuestions.keySet();

	//session.removeAttribute("paperQuestions");
%>
<table width="489" border="0" cellspacing="0" cellpadding="0" align="center">
  <tr> 
    <td height="42" background="images/st_05.gif" style="padding-left:53px;padding-top:8px" class="text3">题型选择添加</td>
  </tr>
<form name='add' action='testpaper_addexe_bypolicy.jsp' method='post' class='nomargin' onsubmit="">
<input type="hidden" name="paperId" value=<%=paperId%>>
  <tr> 
    <td valign="top" background="images/st_06.gif" > <table height="390" width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr> 
          <td valign="top" class="bg2"> <table width="100%" border="0" cellspacing="0" cellpadding="6">
              <tr> 
                <td valign="top" style="padding-left:30px">
				          <table width="100%" border="0" cellspacing="0" cellpadding="0">
				          <%
				          	String field = "";
				          	String typename = "";
				          	List questionList = null;
				          	int count = 1;
				          	for(Iterator it = set.iterator();it.hasNext();)
				          	{
				          		field = (String)it.next();
				          		typename = TestQuestionType.typeShow(field);
				          		questionList = (List)paperQuestions.get(field);
				          %>
				            <tr>
				              <td align="center">
								<table width="100%" border="0" cellpadding="0" cellspacing="0">
				                  <tr valign="bottom" class="mc1"> 
				                    <td colspan="2" valign="bottom" width=70%><%=typename%>：</td>
				                    <td colspan="2" width=30% ><a href="testpaper_add2_bypolicy.jsp?field=<%=field%>&paperId=<%=paperId%>" title="提交" class="button"><span unselectable="on"><font color="red">添加试题</font></span></a></span></td>
				                  </tr>
				                  <%
				                  	for(int i=0;i<questionList.size();i++)
				                  	{
				                  		PaperQuestion question = (PaperQuestion)questionList.get(i);
				                  %>
				                  <tr valign="bottom" class="mc1"> 
				                    <td width="5%" align="right" valign="bottom" style="padding-right:8px"><%=count++%>.</td>
				                    <td width="60%" valign="bottom">
				                    <a href="" title="提交" class="button" onClick="DetailInfo('<%=question.getType()%>','<%=question.getId()%>','<%=i%>'); return false;"><span unselectable="on"><%=question.getTitle()%></span></a></span> 
				                    </td>
				                    <td width="20%" valign="bottom">分值：<input type="text" name="<%=question.getId()%>score" size="2" style="text-align:center" value=<%=question.getReferenceScore()%>> </td>
				                    <td valign="bottom" width="15%">
				                      &nbsp;<a href="javascript:cfmdel('testpaper_del_bypolicy.jsp?field=<%=field%>&i=<%=i%>&paperId=<%=paperId%>')" title="提交" class="button"><span unselectable="on">删 除</span></a></span></td>
				                  </tr>
				                  <%
				                  	}
				                  %>				                  
				                </table></td>
							  </tr>
									<%
									}
									%>
							</table>
              </tr>
              <tr> 
			      <td><table width="98%" height="100%" border="0" cellpadding="0" cellspacing="0">
			        <tr>
			          <td height="4"><img src="../images/page_bottomSlip.gif" width="100%" height="2"></td>
			        </tr>
			        <tr>
			          <td align="center" valign="middle">
			          <a href="" onclick="return false;"><img src="images/OK.gif" width="80" height="24" border="0" onclick="doCommit()"></a>
			          </td>
			        </tr>
			      </table></td>
              </tr>
            </table></td>
        </tr>
      </table></td>
  </tr>
  <tr> 
    <td><img src="images/st_07.gif" width="489" height="15"></td>
  </tr>
</form>
</table>
</body>
</html>
