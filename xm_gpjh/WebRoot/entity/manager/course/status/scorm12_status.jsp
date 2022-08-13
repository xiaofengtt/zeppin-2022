
<%		/*---------------------------------------------
		 Function Description:	
		 Editing Time:	
		 Editor: chenjian
		 Target File:	
		 	 
		 Revise Log
		 Revise Time:  Revise Content:   Reviser:
		 -----------------------------------------------*/
%>
<%@ page language="java"  pageEncoding="GBK"%>
<%@ page contentType="text/html; charset=GBK"%>
<%@page import = "java.util.*,com.whaty.platform.training.*"%>
<%@page import = "com.whaty.platform.training.basic.*"%>
<%@page import = "com.whaty.platform.standard.scorm.operation.*"%>
<%  

	String courseId=request.getParameter("coursewareId");
	String studentId = request.getParameter("studentId");

	TrainingFactory factory=TrainingFactory.getInstance();
	
	
	TrainingStudentOperationManage stuManage=factory.creatTrainingUserOperationManage();
	ScormStudentManage scormStudentManage=stuManage.getScormStudentManage(studentId);
	UserCourseData userCourseData=scormStudentManage.getUserCourseData(courseId);
	if(userCourseData == null)
	 	userCourseData = new UserCourseData();
	List userScos=scormStudentManage.getUserScos(courseId);
	int scores = 0;
	for(int i=0;i<userScos.size();i++)
	{
		String val = ((UserScoData)userScos.get(i)).getCore().getScore().getRaw().getValue() ;
		if(val != null)
			scores += Double.valueOf(val).intValue();
	}
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<link href="<%=request.getContextPath()%>/work/manager/css/style.css" rel="stylesheet" type="text/css">
<script language="javascript" src="<%=request.getContextPath() %>/training/student/js/pro.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>学习记录</title>
<style type="text/css">
<!--
-->
</style>
</head>

<body leftmargin="0" topmargin="0" style="background-color:transparent">
      <table width=96%  border="0" align="center" cellpadding="2" cellspacing="1" bgcolor="3F6C61">
        <tr bgcolor="#FFFFFF"> 
          <td colspan="3" height="11"></td>
        </tr>
        <tr bgcolor="#FFFFFF"> 
          <td width="77%" class="t12_14_bgE3EAE9" colspan="3" align="center">课程编号：<%=userCourseData.getCourseId() %></td>
        </tr>
        <tr align="center"> 
          <td colspan="3" class="t12_14_bgE3EAE9">
          <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr bgcolor="#FFFFFF"> 
                <td valign="top"> <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr bgcolor="#FFFFFF"> 
                      <td valign="middle" class="t12_14_bgE3EAE9"> <table width="532" border="0" cellpadding="0" cellspacing="0">
                          <tr bgcolor="#FFFFFF"> 
                            <td width="340" class="t12_14_bgE3EAE9">课程完成情况：</td>
                            <td width="171" class="t12_14_bgE3EAE9"><script language="javascript">var last0 =new CreatPro("last0", <%=userCourseData.getCompletedPercent()%>,"2.5",5000,1,10,"../../");last0.stepPro()</script> 
                            </td>
                          </tr>
                        </table></td>
                    </tr>
                     <tr bgcolor="#FFFFFF"> 
                      <td valign="middle" class="t12_14_bgE3EAE9"> <table width="532" border="0" cellpadding="0" cellspacing="0">
                          <tr bgcolor="#FFFFFF"> 
                            <td width="340" class="t12_14_bgE3EAE9">总学习时间：</td>
                            <td width="171" class="t12_14_bgE3EAE9"><%if(userCourseData.getTotalTime().length()<8){out.println(userCourseData.getTotalTime());}else{out.println(userCourseData.getTotalTime().substring(0,8));}%></td>
                          </tr>
                        </table></td>
                    </tr>
                    <tr bgcolor="#FFFFFF"> 
                      <td valign="middle" class="t12_14_bgE3EAE9"> <table width="532" border="0" cellpadding="0" cellspacing="0">
                          <tr bgcolor="#FFFFFF"> 
                            <td width="340" class="t12_14_bgE3EAE9">总学习次数：</td>
                            <td width="171" class="t12_14_bgE3EAE9"><%=userCourseData.getAttemptNum() %></td>
                          </tr>
                        </table></td>
                    </tr>
                    <tr bgcolor="#FFFFFF"> 
                      <td valign="middle" class="t12_14_bgE3EAE9"> <table width="532" border="0" cellpadding="0" cellspacing="0">
                          <tr bgcolor="#FFFFFF"> 
                            <td width="340" class="t12_14_bgE3EAE9">课程成绩：</td>
                            <td width="171" class="t12_14_bgE3EAE9"><%=String.valueOf(scores) %></td>
                          </tr>
                        </table></td>
                    </tr>
                   <tr bgcolor="#FFFFFF"> 
                      <td valign="middle" class="t12_14_bgE3EAE9"> <table width="532" border="0" cellpadding="0" cellspacing="0">
                          <tr bgcolor="#FFFFFF"> 
                            <td width="340" class="t12_14_bgE3EAE9">首次进入课程时间：</td>
                            <td width="171" class="t12_14_bgE3EAE9"><%=userCourseData.getFirstDate() %></td>
                          </tr>
                        </table></td>
                    </tr>
                     <tr bgcolor="#FFFFFF"> 
                      <td valign="middle" class="t12_14_bgE3EAE9"> <table width="532" border="0" cellpadding="0" cellspacing="0">
                          <tr bgcolor="#FFFFFF"> 
                            <td width="340" class="t12_14_bgE3EAE9">最后离开课程时间：</td>
                            <td width="171" class="t12_14_bgE3EAE9"><%=userCourseData.getLastDate() %></td>
                          </tr>
                        </table></td>
                    </tr>
                  </table></td>
              </tr>
            </table></td>
        </tr>
      </table>
      
     
      
      <table width=96%  border="0" align="center" cellpadding="2" cellspacing="1" bgcolor="3F6C61">
        <tr bgcolor="#FFFFFF"> 
          <td width="70%" class="t12_14_bgE3EAE9" colspan="3" align="center">课程学习状态</td>
        </tr>
        <tr align="center"> 
          <td colspan="3" class="t12_14_bgE3EAE9"><table width=100%  border="0" align="center" bgcolor="3F6C61" cellpadding="2" cellspacing="1">
              <tr bgcolor="#FFFFFF"> 
                 
                      <td width="150" class="t12_14_bgE3EAE9">学习单元名称</td>
                      <td width="284" class="t12_14_bgE3EAE9">学习单元标题</td>
                      <td width="100" class="t12_14_bgE3EAE9">学习状态</td>
                      <td width="80" class="t12_14_bgE3EAE9">分配时间</td>
                      <td width="80" class="t12_14_bgE3EAE9">获得成绩</td>
                      <!--  <td width="66" class="t12_14_bgE3EAE9">细节</td>-->
                    
              </tr>
              
              
                    <%for(int i=0;i<userScos.size();i++)
                    { %>
                    <tr bgcolor="#FFFFFF"> 
                       
                            <td width="150" class="t12_14_bgE3EAE9"><%=((UserScoData)userScos.get(i)).getSystemId() %></td>
                            <td width="284" class="t12_14_bgE3EAE9"><%=((UserScoData)userScos.get(i)).getTitle()%></td>
                            <td width="100" class="t12_14_bgE3EAE9"><%=((UserScoData)userScos.get(i)).getCore().getLessonStatus().getValue() %></td>
                            <td width="80" class="t12_14_bgE3EAE9"><%=((UserScoData)userScos.get(i)).getCore().getTotalTime().getValue() %></td>
                            <td width="66" class="t12_14_bgE3EAE9"><%=((UserScoData)userScos.get(i)).getCore().getScore().getRaw().getValue() %></td>
                          	<!--  <td width="66"><a href="myCourseDetailStatus.jsp?auSystemId=<%=((UserScoData)userScos.get(i)).getSystemId() %>&courseId=<%=courseId%>" target=_blank>[查看细节]</a> </td>-->
                        
                    </tr>
                    <%
                    }
                    %>
                   
                 
              
            </table></td>
        </tr>
      </table>
  </tr>
</table>
</body>
</html>


