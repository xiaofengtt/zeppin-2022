<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.whaty.platform.interaction.*" %>
<%@ page import="com.whaty.platform.entity.basic.*" %>
<%@ page import="com.whaty.platform.interaction.forum.*,com.whaty.platform.courseware.basic.*,com.whaty.platform.courseware.*" %>
<%@ include file="./pub/priv.jsp"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">生殖健康咨询师培训网itle>生殖健康咨询师培训网</title>
<link href="./css/css.css" rel="stylesheet" type="text/css">
</head>
<%
try{
	InteractionFactory interactionFactory = InteractionFactory.getInstance();
	InteractionManage interactionManage = interactionFactory.creatInteractionManage(interactionUserPriv);		
		
	List cwareList=interactionManage.getCoursewares(teachclass_id);
%>
<script language="javascript">
function delete_this(id){
	var i=confirm('确定要删除此课件吗？');
	if(i==false){
		return;
	}else{
		window.location = "courseware_delexe.jsp?courseware_id="+id;
	}
}
</script>
<body leftmargin="0" topmargin="0"  background="./images/bg2.gif">
<br>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td valign="top" class="bg3"><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <!-- tr>
                <td height="86" valign="top" background="./images/top_01.gif"><img src="./images/tlq.gif" width="217" height="86"></td>
              </tr-->
              <tr>
                <td align="center" valign="top"><table width="750" border="1" cellspacing="0" cellpadding="0" bordercolordark="BDDFF8" bordercolorlight="#FFFFFF">
              <tr>
                      <td bgcolor="#FFFFFF"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td width="50" align="center" valign="bottom"><img src="./images/tlq_04.gif" width="40" height="44"></td>
                            <td width="150" valign="top" class="text3" style="padding-top:25px"><%= openCourse.getCourse().getName() %> 课件列表</td>
                            <td background="./images/wt_03.gif">&nbsp;</td>
                          </tr>
                        </table></td>
                    </tr>
                    <tr> 
                      <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td width="8"><img src="./images/tlq_01.gif" width="8" height="11"></td>
                            <td width="733" background="./images/tlq_02.gif"> </td>
                            <td width="9" align="right"><img src="./images/tlq_03.gif" width="9" height="11"></td>
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
                            <td><img src="./images/tlq_05.gif" width="31" height="10"></td>
                            <td><a href="courseware_add.jsp" class="tj">[建立新课件]</a>                            
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
                            
                            <td width="40%" class="title">课件名称</td>
                            <td width="20%" class="title">建立时间</td>
                            <td width="12%" class="title">作　　者</td>
                            <td width="20%" class="title">操　　作</td>
                            <td width="1%" align="right"><img src="./images/tlq_07.gif" width="7" height="30"></td>
                          </tr>
                   <%
                   		if(cwareList.size()!=0){	
	                   		for(int i=0;i<cwareList.size();i++){
	                   			Courseware cware = (Courseware)cwareList.get(i);
                   %>  
                   
                          <tr> 
                            
                            <td class="td1">
                            <a href="courseware_info.jsp?id=<%=cware.getId()%>" target=_blank><%=cware.getName()%></a>&nbsp;
                            </td>
                            <td align="center" class="td1"><%=cware.getFoundDate() %></td>
                            <td align="center" class="td1"><%=cware.getAuthor() %></td>
                            <td align="center" class="td1"><a href="./enter_cware.jsp?courseware_id=<%=cware.getId()%>&enter_type=view" target=_blank>浏览</a>&nbsp;&nbsp;
                            <%
                      			if("teacher".equalsIgnoreCase(userType)) {
                      		%> 
                      		<a href="./enter_cware.jsp?courseware_id=<%=cware.getId()%>&enter_type=teacher" target=_blank>管理</a>&nbsp;&nbsp;<a href="javascript:delete_this('<%=cware.getId()%>')">删除</a>
                      		<%} %>
                      		</td>
                            <td class="td1">&nbsp;</td>
                          </tr>
                    <%
                    		}
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