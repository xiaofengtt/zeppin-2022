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
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>tableType_1</title>
<link href="../css/css.css" rel="stylesheet" type="text/css">
<script language="JavaScript">
	function listSelect(listId) {
	var form = document.forms[listId + '_form'];
	for (var i = 0 ; i < form.elements.length; i++) {
		if ((form.elements[i].type == 'checkbox') && (form.elements[i].name == 'ids')) {
			if (!(form.elements[i].value == 'DISABLED' || form.elements[i].disabled)) {
				form.elements[i].checked = form.listSelectAll.checked;
			}
		}
	}
	return true;
}
function cfmdel(link)
{
	if(confirm("您确定要删除本组卷策略吗？"))
		window.navigate(link);
}

function DetailInfo(type,id)
{
	if(type=='YUEDU')
		window.open('store_question_comprehension_info.jsp?id='+id,'','dialogWidth=350px;dialogHeight=330px');
	else 
		window.open('store_question_info.jsp?id='+id,'','dialogWidth=350px;dialogHeight=330px');
	
}
</script>
</head>
<%	
	String field = request.getParameter("field");
	String paperId = request.getParameter("paperId");
	HashMap paperQuestions = (HashMap) session.getAttribute("paperQuestions");
	if(paperQuestions==null)
	{
		paperQuestions = new HashMap();
		paperQuestions.put(TestQuestionType.DANXUAN,new ArrayList());
		paperQuestions.put(TestQuestionType.DUOXUAN,new ArrayList());
		paperQuestions.put(TestQuestionType.PANDUAN,new ArrayList());
		paperQuestions.put(TestQuestionType.TIANKONG,new ArrayList());
		paperQuestions.put(TestQuestionType.WENDA,new ArrayList());
		paperQuestions.put(TestQuestionType.YUEDU,new ArrayList());
	}
	List questionList = (List)paperQuestions.get(field);
	
	try {
		InteractionFactory interactionFactory = InteractionFactory.getInstance();
		InteractionManage interactionManage = interactionFactory.creatInteractionManage(interactionUserPriv);	
		TestManage testManage = interactionManage.creatTestManage();
		int totalItems = testManage.getOtherQuestionsByPaperPolicyNum(openCourse.getBzzCourse().getId(),field,questionList,"KAOSHI");
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
			String link="&field="+field+"&paperId="+paperId;
			//----------分页结束---------------
		List paperQuestionList = testManage.getOtherQuestionsByPaperPolicy(pageover,openCourse.getBzzCourse().getId(),field,questionList,"KAOSHI");
%>

<body leftmargin="0" topmargin="0"  background="images/bg2.gif">
<form action="testpaper_add3_bypolicy.jsp" method="post" name="del_form">
<input type="hidden" name="field" value=<%=field%>>
<input type="hidden" name="paperId" value=<%=paperId%>>
 <table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td valign="top" class="bg3"><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td valign="top" background="images/top_01.gif">
                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td width="217" height="104" rowspan="2" valign="top"><img src="images/zxzc.gif" width="217" height="86"></td>
                      <td height="46">&nbsp;</td>
                    </tr>
                    <tr> 
                      <td align="left" valign="top"> 
                        <table width="70%" border="0" cellpadding="0" cellspacing="0">
                          <tr>                            
                            <td align="left"><img src="images/xb.gif" width="48" height="32"></td>
                            <td align="center" class="mc1"></td>
                            <td align="center"></td>
                            <td align="center"></td>
                            <td align="right" class="mc1"><a onclick='document.del_form.submit();'><IMG src="./images/OK.gif"/></a></td>
                            <td><img src="images/fh.gif" width="80" height="24" onclick="location.href='testpaper_add1_bypolicy.jsp?pageInt=<%=pageInt %>'"></td>
                          </tr>
                        </table>
                      </td>
                      <td height="46">&nbsp;</td>
                    </tr>
                </table>
                </td>
              </tr>
              <tr>
                <td align="center">
			<table width="812" border="0" cellspacing="0" cellpadding="0">
              <tr>
                      
                <td height="26" background="images/tabletop.gif">
	              <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">				
                    <tr>
                      <td width="5%" height="17">&nbsp;</td>
                      <td width="24%" align="center" class="title">名&nbsp;&nbsp;&nbsp;&nbsp;称</td>
					  <td width="1%" align="center" valign="bottom"><img src="images/topxb.gif"></td>
                      <td width="16%" align="center" class="title">发布人</td>
					  <td width="1%" align="center" valign="bottom"><img src="images/topxb.gif"></td>
                      <td width="16%" align="center" class="title">发布时间</td>
					  <td width="1%" align="center" valign="bottom"><img src="images/topxb.gif"></td>
                      <td width="20%" align="center" class="title">题目类型</td>
                      <td width="1%" align="center" valign="bottom"><img src="images/topxb.gif"></td>
                      <td width="15%" align="center" class="title"><input type='checkbox' class='checkbox' name='listSelectAll' value='true'  onClick="listSelect('del')"></td>
                    </tr>                    
                  </table>
                </td>
                </tr>
                    <%
						for(int i=0;i<paperQuestionList.size();i++) {				 
							PaperQuestion question = (PaperQuestion)paperQuestionList.get(i);
							String edit_url = "";		
							String url = "store_question_info.jsp";		 
							String questionId = question.getId();
							String title = question.getTitle();
							String creatuser = question.getCreatUser();
							String creatdate = question.getCreatDate();
							String type = question.getType();
							String type_str = TestQuestionType.typeShow(question.getType());
							//out.print(type);
							if(type.equals(TestQuestionType.DANXUAN))
								edit_url = "store_question_single_edit.jsp";
							if(type.equals(TestQuestionType.DUOXUAN))
								edit_url = "store_question_multi_edit.jsp";
							if(type.equals(TestQuestionType.PANDUAN))
								edit_url = "store_question_judge_edit.jsp";
							if(type.equals(TestQuestionType.TIANKONG))
								edit_url = "store_question_blank_edit.jsp";
							if(type.equals(TestQuestionType.WENDA))
								edit_url = "store_question_answer_edit.jsp";
							if(type.equals(TestQuestionType.YUEDU)) {
								edit_url = "store_question_comprehension_edit.jsp";
								url = "store_question_comprehension_info.jsp";
							}
					%>
                    <tr>
                      <td align="center" background="images/tablebian.gif">
						<table width="99%" border="0" cellspacing="0" cellpadding="0" class="mc1">
                          <tr> 
                            <td width="5%" align="center" valign="middle" class="td1"><img src="images/xb2.gif" width="8" height="8"></td>
                            <td width="24%"  class="td1"><a href="#" onclick="window.open('<%=url%>?id=<%=questionId%>','题目信息','height=300,width=600,top=0,left=0,toolbar=no,menubar=no,scrollbars=yes, resizable=no,location=no, status=no')"><%=title%></a>&nbsp;&nbsp;&nbsp;&nbsp;<img src="images/new.gif"></td>
                            <td width="18%" align="center"  class="td1"><%=creatuser%></td>
                            <td width="17%" align="left"  class="td1"><%=creatdate%></td>
                            <td width="21%" align="center"  class="td1"><%=type_str%></td>
                            <td width="15%" align="center"  class="td1"><input type='checkbox' class='checkbox' name='ids' value='<%=question.getId()%>'></td>
                          </tr>
                        </table>
                      </td>
                    </tr>
                    <%
                    	}
                    %>
                    <tr>
                      <td><img src="images/tablebottom.gif" width="812" height="4"></td>
                    </tr>
                  </table><br>
                </td>
              </tr>
              <tr>
                <td align="center">
<table width="806" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td><img src="images/bottomtop.gif" width="806" height="7"></td>
                    </tr>
                    <tr>
                      <td background="images/bottom02.gif">
                      	<!--<table width="90%" border="0" align="center" cellpadding="0" cellspacing="0">
                          <tr> 
                            <td align="center" valign="middle" class="mc1">页次:1/1页 
                              共:3条 </td>
                            <td align="center" valign="middle" class="mc1"> 上一页 
                              下一页 </td>
                            <td align="center" valign="middle" class="mc1">转到第 页</td>
                            <td valign="m??????ā8?iddle" class="mc1"><a href="#"><img src="images/go.gif" width="52" height="22" border="0"></a></td>
                            <td align="center" valign="middle" class="mc1">当前每页显示 
                              条</td>
                          </tr>
                        </table>
                       -->
                         </form>
                       <%@ include file="../pub/dividepage.jsp" %>
                      </td>
                    </tr>
                    <tr>
                      <td><img src="images/bottom03.gif" width="806" height="7"></td>
                    </tr>
                  </table>
                </td>
              </tr>
            </table></td>
        </tr>
      </table>
  
</body>


<%
	}
catch(Exception e)
{
	out.print(e.getMessage());
	return;
}
	
%>
</html>
