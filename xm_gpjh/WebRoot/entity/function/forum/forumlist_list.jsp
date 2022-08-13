<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.whaty.platform.interaction.*" %>
<%@ page import="com.whaty.platform.entity.basic.*" %>
<%@ page import="com.whaty.platform.interaction.forum.*" %>
<%@ include file="../pub/priv.jsp"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">生殖健康咨询师培训网itle>生殖健康咨询师培训网</title>
<link href="../css/css.css" rel="stylesheet" type="text/css">
</head>
<%
try{
	InteractionFactory interactionFactory = InteractionFactory.getInstance();
	InteractionManage interactionManage = interactionFactory.creatInteractionManage(interactionUserPriv);		
	
	String course_id= teachclass_id;
	List forumlist_list=interactionManage.getForumLists(course_id);
%>
<script language="javascript">
function delete_this(id){
	var i=confirm('确定要删除当前项吗？');
	if(i==false){
		return;
	}else{
		window.navigate("forumlist_delexe.jsp?id="+id);
	}
}
</script>
<body leftmargin="0" topmargin="0"  background="../images/bg2.gif">
<br>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td valign="top" class="bg3"><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <!-- tr>
                <td height="86" valign="top" background="../images/top_01.gif"><img src="../images/tlq.gif" width="217" height="86"></td>
              </tr-->
              <tr>
                <td align="center" valign="top"><table width="750" border="1" cellspacing="0" cellpadding="0" bordercolordark="BDDFF8" bordercolorlight="#FFFFFF">
              <tr>
                      <td bgcolor="#FFFFFF"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td width="50" align="center" valign="bottom"><img src="../images/tlq_04.gif" width="40" height="44"></td>
                            <td width="150" valign="top" class="text3" style="padding-top:25px"><%= openCourse.getCourse().getName() %> 讨论区列表</td>
                            <td background="../images/wt_03.gif">&nbsp;</td>
                          </tr>
                        </table></td>
                    </tr>
                    <tr> 
                      <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td width="8"><img src="../images/tlq_01.gif" width="8" height="11"></td>
                            <td width="733" background="../images/tlq_02.gif"> </td>
                            <td width="9" align="right"><img src="../images/tlq_03.gif" width="9" height="11"></td>
                          </tr>
                        </table></td>
                    </tr>
                    <tr> 
                      <td align="right" style="padding-right:20px">
                      <%
                      	if("teacher".equalsIgnoreCase(userType)) {
                      %> 
                      <table border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td><img src="../images/tlq_05.gif" width="31" height="10"></td>
                            <td><a href="forumlist_add.jsp" class="tj">[建立新版]</a>                            
                            </td>
                          </tr>
                      </table>
                      <%
                      	}
                      %>
                      </td>
                    </tr>
                    <tr> 
                      <td>
                        <table width="95%" border="0" align="center" cellpadding="0" cellspacing="0">
                          <tr align="center" bgcolor="#4582B1"> 
                            <td height="30" colspan="2" background="../images/tlq_06.gif" class="title">讨论区</td>
                            <td width="25%" class="title">建立时间</td>
                            <td width="12%" class="title">主题总数</td>
                            <td width="12%" class="title">文章总数</td>
                        <%--    <td width="12%" class="title">版 主</td>  --%>
                            <td width="1%" align="right"><img src="../images/tlq_07.gif" width="7" height="30"></td>
                          </tr>
                   <%
                   		if(forumlist_list.size()!=0){	
	                   		for(int i=0;i<forumlist_list.size();i++){
	                   			ForumList temp_forumlist = (ForumList)forumlist_list.get(i);
	                   			String temp_id = temp_forumlist.getId();
                   %>  
                   
                          <tr> 
                            <td width="3%" align="center" class="td1"><img src="../images/ct_09.gif" width="14" height="14"></td>
                            <td width="47%" class="td1">
                            <a href="forum_list.jsp?forumlist_id=<%=temp_forumlist.getId()%>"><%=temp_forumlist.getName()%></a>&nbsp;
                            </td>
                            <td align="center" class="td1"><%=temp_forumlist.getDate() %></td>
                            <td align="center" class="td1"><%=interactionManage.getTopicForumsNum(temp_id)%></td>
                            <td align="center" class="td1"><%=interactionManage.getTotalForumsNum(temp_id)%></td>
                            <td class="td1">&nbsp;</td>
                          </tr>
                    <%
                    		}
                    	} else {
                    %>
                          <tr> 
                            <td>&nbsp;</td>
                            <td height="50" colspan="5" align="center" class="text1"><img src="../images/tlq_08.gif" width="20" height="20">&nbsp;对不起，目前还没有任何版可以发表文章!</td>
                            <td>&nbsp;</td>
                          </tr>
                      <%
                      	}
                      %>
                        </table>
                     
                      </td>
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
</html>
<%
	} catch(Exception e) {
		out.print(e.getStackTrace());
	}	
%>