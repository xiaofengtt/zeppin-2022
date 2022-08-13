
<jsp:directive.page import="com.whaty.platform.standard.scorm.offline.OffLineRecordManage"/>
<jsp:directive.page import="com.whaty.platform.util.JsonUtil"/>
<%		/*---------------------------------------------
		 Function Description:	
		 Editing Time:	
		 Editor: chenjian
		 Target File:	
		 	 
		 Revise Log
		 Revise Time:  Revise Content:   Reviser:
		 -----------------------------------------------*/
%>
<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@page import = "java.util.*,com.whaty.platform.training.*"%>
<%@page import = "com.whaty.platform.standard.scorm.operation.*"%>
<%@page import = "com.whaty.platform.database.oracle.*"%>
<%@page import = "com.whaty.platform.database.oracle.standard.training.user.OracleTrainingStudentPriv,com.whaty.platform.standard.scorm.util.*,com.whaty.platform.standard.scorm.*,com.whaty.platform.standard.scorm.operation.*"%>
<%@page import = "com.whaty.platform.training.basic.*,com.whaty.platform.sso.web.action.SsoConstant,com.whaty.platform.sso.web.servlet.UserSession,com.whaty.platform.training.user.*"%>
<%@page import="com.whaty.platform.database.oracle.standard.standard.scorm.operation.*"%>
<%!
	//判断字符串为空的话，赋值为""
	String fixnull(String str)
	{
	    if(str == null || str.equals("null") || str.equals(""))
			str = "";
			return str;
	}
%>
<%  
	String courseId=request.getParameter("coursewareId");
	String c_courseId=request.getParameter("courseId");
	String user_id=request.getParameter("user_id");
	
	//System.out.println("courseId:"+courseId+",c_courseId:"+c_courseId+",user_id="+user_id);
	TrainingFactory factory=TrainingFactory.getInstance();
	 ScormFactory scormFactory=ScormFactory.getInstance();
	 
	 String courseName="";
	 dbpool db = new dbpool();
	MyResultSet rs = null;
	String sql="select name from pe_bzz_tch_course where id='"+c_courseId+"'";
	rs=db.executeQuery(sql);
	if(rs!=null && rs.next())
	{
		courseName=fixnull(rs.getString("name"));
	}
	db.close(rs);
	
	//com.whaty.dls.UserInfo user = (com.whaty.dls.UserInfo)session.getAttribute("userInfo");
	UserSession usersession=(UserSession)session.getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
	String userid = "";
	if(usersession!=null){
		userid = usersession.getId(); 
	}
	
	TrainingStudentPriv includePriv=new OracleTrainingStudentPriv();
	includePriv.setStudentId(user_id);
	
	ScormStudentPriv scormPriv=new OracleScormStudentPriv();
	scormPriv.setStudentId(user_id);
	ScormStudentManage scormStudentManage=scormFactory.creatScormStudentManage(scormPriv);
	
	//TrainingStudentOperationManage stuManage=factory.creatTrainingUserOperationManage(includePriv);
	//ScormStudentManage scormStudentManage=stuManage.getScormStudentManage();
	UserCourseData userCourseData=scormStudentManage.getUserCourseData(courseId);
	if(userCourseData == null)
	{
	 	userCourseData = new UserCourseData();
	 }
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
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/training/student/course/courseware/scorm12/js/ext-2.2/resources/css/ext-all.css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/training/student/course/courseware/scorm12/js/ext-2.2/examples/tree/column-tree.css" />
<script type="text/javascript" src="<%=request.getContextPath() %>/training/student/course/courseware/scorm12/js/ext-2.2/common.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/training/student/course/courseware/scorm12/js/ext-2.2/adapter/ext/ext-base.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/training/student/course/courseware/scorm12/js/ext-2.2/ext-all.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/training/student/course/courseware/scorm12/js/ext-2.2/examples/tree/ColumnNodeUI.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/training/student/course/courseware/scorm12/js/ext-2.2/examples/tree/column-tree.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/training/student/course/courseware/scorm12/js/ext-2.2/json.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/training/student/course/courseware/scorm12/js/ext-2.2/scoDataModel.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/training/student/course/courseware/scorm12/js/ext-2.2/scoManager.js"></script>
<style type="text/css">
<!--
body {
	margin: 0px;
	padding: 0px;
	text-align:center;
	background-color:#E0E0E0;
}
-->
</style>
<style type="text/css">
<!--
.w1 {
	font-family: "宋体";
	font-size: 12px;
	line-height: 24px;
	color: #333333;
	text-decoration: none;
}
.w2 {
	font-family: "宋体";
	font-size: 14px;
	line-height: 24px;
	font-weight: bold;
	color: #265A8E;
	text-decoration: none;
}
-->
</style>
</head>
<%
OffLineRecordManage recordManage = new OffLineRecordManage();
recordManage.getPlatformData(includePriv.getStudentId(), courseId);
String returnJson = JsonUtil.toJSONString(recordManage.getOfflineData().get(0));
//System.out.println("Jason:>>>>>>"+returnJson);
%>
<body leftmargin="0" topmargin="0" style="background-color:#E0E0E0;">
      <table width=60%  border="0" align="center" cellpadding="2" cellspacing="1" bgcolor="3F6C61" >
         <tr> 
          <td><img name="index_r1_c1" src="/training/student/course/courseware/scorm12/images/index/news_02.jpg" width="918" height="154" border="0" id="index_r1_c1" alt="" /></td>
        </tr>
       <!--  <tr bgcolor="#FFFFFF"> 
          <td colspan="3" height="11"></td>
        </tr>  -->
        <tr bgcolor="#FFFFFF"> 
          <td width="77%" bgColor=#E6EFF9  colspan="3" align="center" class="w2"><%=courseName %>课程详细学习记录</td>
        </tr>
        <tr align="center"> 
          <td colspan="3" class="t12_14_bgE3EAE9">
          <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr bgcolor="#FFFFFF"> 
                <td valign="top"> <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr bgcolor="#FFFFFF"> 
                      <td valign="middle" class="t12_14_bgE3EAE9"> <table width="532" border="0" cellpadding="0" cellspacing="0">
                          <tr bgcolor="#FFFFFF"> 
                            <td width="340" bgColor=#ffffff class="w1">&nbsp;&nbsp;&nbsp;&nbsp;课程完成情况：</td>
                            <td width="171" bgColor=#ffffff class="w1"><script language="javascript">var last0 =new CreatPro("last0", <%=userCourseData.getCompletedPercent()%>,"2.5",5000,1,10,"/training/student/");last0.stepPro()</script> 
                            </td>
                          </tr>
                        </table></td>
                    </tr>
                     <tr bgcolor="#FFFFFF"> 
                      <td valign="middle" class="t12_14_bgE3EAE9"> <table width="532" border="0" cellpadding="0" cellspacing="0">
                          <tr bgcolor="#FFFFFF"> 
                            <td width="340" bgColor=#ffffff class="w1">&nbsp;&nbsp;&nbsp;&nbsp;总学习时间：</td>
                            <td width="171" bgColor=#ffffff class="w1"><%if(userCourseData.getTotalTime().length()<8){out.println(userCourseData.getTotalTime());}else{out.println(userCourseData.getTotalTime().substring(0,8));}%></td>
                          </tr>
                        </table></td>
                    </tr>
                    <tr bgcolor="#FFFFFF"> 
                      <td valign="middle" class="t12_14_bgE3EAE9"> <table width="532" border="0" cellpadding="0" cellspacing="0">
                          <tr bgcolor="#FFFFFF"> 
                            <td width="340" bgColor=#ffffff class="w1">&nbsp;&nbsp;&nbsp;&nbsp;总学习次数：</td>
                            <td width="171" bgColor=#ffffff class="w1"><%=userCourseData.getAttemptNum() %></td>
                          </tr>
                        </table></td>
                    </tr>
                  <!--   <tr bgcolor="#FFFFFF"> 
                      <td valign="middle" class="t12_14_bgE3EAE9"> <table width="532" border="0" cellpadding="0" cellspacing="0">
                          <tr bgcolor="#FFFFFF"> 
                            <td width="340" bgColor=#ffffff class="w1">&nbsp;&nbsp;&nbsp;&nbsp;课程成绩：</td>
                            <td width="171" bgColor=#ffffff class="w1"><%=String.valueOf(scores) %></td>
                          </tr>
                        </table></td>
                    </tr>  -->
                   <tr bgcolor="#FFFFFF"> 
                      <td valign="middle" class="t12_14_bgE3EAE9"> <table width="532" border="0" cellpadding="0" cellspacing="0">
                          <tr bgcolor="#FFFFFF"> 
                            <td width="340" bgColor=#ffffff class="w1">&nbsp;&nbsp;&nbsp;&nbsp;首次进入课程时间：</td>
                            <td width="171" bgColor=#ffffff class="w1"><%=userCourseData.getFirstDate() %></td>
                          </tr>
                        </table></td>
                    </tr>
                     <tr bgcolor="#FFFFFF"> 
                      <td valign="middle" class="t12_14_bgE3EAE9"> <table width="532" border="0" cellpadding="0" cellspacing="0">
                          <tr bgcolor="#FFFFFF"> 
                            <td width="340" bgColor=#ffffff class="w1">&nbsp;&nbsp;&nbsp;&nbsp;最后离开课程时间：</td>
                            <td width="171" bgColor=#ffffff class="w1"><%=userCourseData.getLastDate() %></td>
                          </tr>
                        </table></td>
                    </tr>
                  </table></td>
              </tr>
            </table></td>
        </tr>
  <!--   </table>
   <table>--> 
        <tr bgcolor="#FFFFFF"> 
          <td width="70%" bgColor=#E6EFF9 colspan="3" align="center" class="w2">课程学习状态</td>
        </tr>
        <tr align="center"> 
          <td colspan="3" class="t12_14_bgE3EAE9"><table width=100%  border="0" align="center" bgcolor="3F6C61" cellpadding="2" cellspacing="1">
              <tr bgcolor="#FFFFFF"> 
              <td><div id="tabletree" ></div></td>
              </tr>
            </table></td>
        </tr>
      </table>
  </tr>
</table>
<script type="text/javascript">
	Ext.BLANK_IMAGE_URL = '<%=request.getContextPath()%>/training/student/course/courseware/scorm12/js/ext-2.2/resources/images/default/s.gif';
	var studyStatusObj = null;
	var studyStatusObjStr = "";
	function refreshSSObj()
	{
		try{
			studyStatusObjStr = '<%=returnJson%>';
			var tt = JSON.parse(studyStatusObjStr, function (key, value)	{
																		 	 return value;
																		  	}
								)
			studyStatusObj = scoManager.creatSSObj(tt);
			//studyStatusObj = eval('['+studyStatusObjStr+']');
			//studyStatusObj = studyStatusObj[0];
		}
		catch(err){}
	}
	refreshSSObj();
	

	//构建tree
	
	function creatTree()
	{
		var tempObj = new Ext.tree.ColumnTree({
			width		: 915,
			height		: 350,
			rootVisible	: false,
			autoScroll	: true,
			//title		: '跟踪记录',
			renderTo	: 'tabletree',
			
			//{
			//	header:'学习单元名称',
			//	width:200,
			//	dataIndex:'sid'
			//},
			columns:[
			{
				header:'学习单元标题',
				width:400,
				dataIndex:'title'
			}
			,{
				header:'学习状态',
				width:200,
				dataIndex:'status'
				,
				renderer:selectStatus
			},{
				header:'学习时长',
				width:400,
				dataIndex:'duration'
			}],
			
			//,{
			//	header:'获得成绩',
			//	width:200,
			//	dataIndex:'score',
			//	renderer:showScore
			//}
	
			loader: new Ext.tree.TreeLoader({
				//dataUrl:'column-data.json',
				uiProviders:{
					'col': Ext.tree.ColumnNodeUI
				}
			}),
			
			//指定显示的根位置
			root: new Ext.tree.AsyncTreeNode({ 
				text:'课程学习模块学习记录', 
				expanded:true,
				children: (studyStatusObj && studyStatusObj.children)?studyStatusObj.children:json
			})
    });
		return tempObj;
	}
	
	var tree = creatTree();
</script>
</body>
</html>


