<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*,com.whaty.platform.util.*,com.whaty.platform.util.log.*"%>
<%@ page import="com.whaty.platform.entity.activity.*,com.whaty.platform.entity.activity.score.*"%>
<%@ page import="com.whaty.platform.entity.*,com.whaty.platform.entity.basic.*,com.whaty.platform.entity.config.*"%>
<%@ page import="com.whaty.platform.entity.recruit.*,com.whaty.platform.entity.setup.*"%>
<%@ page import="com.whaty.platform.entity.user.*,com.whaty.platform.entity.test.*"%>
<%@ page import="com.whaty.platform.interaction.*,com.whaty.platform.interaction.forum.*"%>

<%@ include file="../pub/priv.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>主题列表</title>
<link href="../css/css.css" rel="stylesheet" type="text/css">
</head>

<%
	String forumId = request.getParameter("forumId");
	String threadId = request.getParameter("threadId");
	
	try {
		InteractionFactory interactionFactory = InteractionFactory.getInstance();
		InteractionManage interactionManage = interactionFactory.creatInteractionManage(interactionUserPriv);
		Forum thread = interactionManage.getForum(threadId);
		
%>

<body leftmargin="0" topmargin="0"  background="../images/bg2.gif">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td valign="top" class="bg3"><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td valign="top" background="../images/top_01.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td width="217" height="104" rowspan="2" valign="top"><img src="../images/tlq.gif" width="217" height="86"></td>
                      <td height="46">&nbsp;</td>
                    </tr>
                    <tr> 
                      <td align="right" valign="top"> 
                        <table width="70%" border="0" cellpadding="0" cellspacing="0">
                          <tr>
                            <td align="center"></td>
                            <td align="center"></td>
                            <td></td>
                            <td align="center"></td>
                            <td width="35">&nbsp;</td>
                          </tr>
                        </table>
                      </td>
                    </tr>
                  </table>
                  </td>
              </tr>
              <tr>
                <td align="center">
				<table width="812" border="0" cellspacing="0" cellpadding="0">
                   <tr>
                      
                <td height="26" background="../images/tabletop.gif">
                	<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
                    	<tr>
                      	<td width="15%" height="17">&nbsp;</td>
                      	<td width="48%" align="center" class="title"></td>
                      	<td width="18%" align="center" class="title"></td>
                      	<td align="center" class="title"></td>
                      	<td width="4%">&nbsp;</td>
                    	</tr>
                  	</table>
                  </td>
                 </tr>
                    
                 <tr>
                 	<td align="center" background="../images/tablebian.gif">
                      	<FORM name="add_form" action="forumlist_elite_addexe.jsp" method="post">
                      	<input type="hidden" name="forumId" value="<%=forumId %>">
                      	<input type="hidden" name="threadId" value="<%=threadId %>">
						<table width="99%" border="0" cellspacing="0" cellpadding="0" class="mc1">                       
                         <tr> 
                            <td width="15%" align="center" valign="middle"  class="td1"></td>
                            <td width="48%" align="center" class="td1">语言：<input type="text" name="language"></td>
                            <td width="18%" align="center"  class="td1"></td>
                            <td align="center"  class="td1"></td>
                            <td width="40" align="center"  class="td1">&nbsp;</td>
                          </tr>     
                          <tr> 
                            <td width="15%" align="center" valign="middle"  class="td1"></td>
                            <td width="48%" align="center" class="td1">描述：<input type="text" name="description"></td>
                            <td width="18%" align="center"  class="td1"></td>
                            <td align="center"  class="td1"></td>
                            <td width="40" align="center"  class="td1">&nbsp;</td>
                          </tr>
                          <tr> 
                            <td width="15%" align="center" valign="middle"  class="td1"></td>
                            <td width="48%" align="center" class="td1">关键字：<input type="text" name="keyword"></td>
                            <td width="18%" align="center"  class="td1"></td>
                            <td align="center"  class="td1"></td>
                            <td width="40" align="center"  class="td1">&nbsp;</td>
                          </tr> 
                          <tr> 
                            <td colspan="5" align="center" class="td1"><input type="submit" value="添加"></td>
                          </tr>                   
                        </table>
                        </FORM>
                      </td>
                    </tr>
                    <tr>
                      <td><img src="../images/tablebottom.gif" width="812" height="4"></td>
                    </tr>
                  </table><br>
                </td>
              </tr>
              <tr>
                <td align="center"></td>
              </tr>
            </table></td>
        </tr>
      </table>
</body>
</html>
<%
	} catch(Exception e) {
		out.print(e.getMessage());
		return;
	}
%>
