<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../../pub/priv.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>help</title>
		<% String path = request.getContextPath();%>
		<link href="<%=path%>/entity/manager/css/admincss.css" rel="stylesheet" type="text/css"/>
	
	<link href="<%=path%>/entity/teacher/images/layout.css" rel="stylesheet" type="text/css" />

<link href="../css/admincss.css" rel="stylesheet" type="text/css">
<style type="text/css">
<!--
body {
	background-color: #6797BB;
}
-->
</style>
<%!
	String fixnull(String str)
	{
	    if(str == null || str.equals("") || str.equals("null"))
			str = "";
			return str;
	
	}
%>
<%
	InteractionFactory interactionFactory = InteractionFactory.getInstance();
	InteractionManage interactionManage = interactionFactory.creatInteractionManage(interactionUserPriv);
	List itemList=interactionManage.getTheachItem(courseId);
	String item="";
		String dayi="";
		String gonggao="";
		String taolun="";
		String kaoshi="";
		String zuoye="";
		String ziyuan="";
		String zxdayi="";
	  String smzuoye="";
	  String zice="";
	  String pingjia="";
	  String daohang="";
	  String daoxue="";
	  String shiyan="";
	  String zfx="";
	  String boke="";
	  
	int width = 0;
	if(itemList!=null)
	{
	   for(int i=0;i<itemList.size();i++){
	          CourseItem techItem=(CourseItem) itemList.get(i);
	          gonggao=fixnull(techItem.getGonggao());
	          dayi=fixnull(techItem.getDayi());  
	          taolun=fixnull(techItem.getTaolun());
	          kaoshi=fixnull(techItem.getKaoshi());
	          zuoye=fixnull(techItem.getZuoye());
	          ziyuan=fixnull(techItem.getZiyuan());
	          zxdayi=fixnull(techItem.getZxdayi());
	          smzuoye=fixnull(techItem.getSmzuoye());
	          zice=fixnull(techItem.getZice());
	          pingjia=fixnull(techItem.getPingjia());
	          daoxue=fixnull(techItem.getDaoxue());
	          shiyan=fixnull(techItem.getShiyan());
	          zfx=fixnull(techItem.getZfx());
	          boke=fixnull(techItem.getBoke());
	          taolun=fixnull(techItem.getTaolun());
	  	}
	}				
 %>
<script type="text/javascript">
function openware(){
	var xx= window.document.getElementById("combo-box-link").value;
	if(xx.length<=0){
		alert("请选择课件！");
	}else{
		window.open(xx);
	}
}
// url: '/sso/other/interaction_getcoursewareList.action?open_course_id=<%=teachclass_id%>'
</script>

</head>

<body leftmargin="0" topmargin="0">
<table width="212" height="100%" border="0" cellpadding="0" cellspacing="0" onselectstart="return false" >
  <tr class="help_bg"> 
    <td align="center" valign="top" class="help_bg">
    <table width="100%" height="220" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td height="60" valign="top" class="help"><table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td height="16" colspan="3"><img src="../images/k.gif" width="1" height="16"></td>
          </tr>
          <tr>
            <td height="70" align="center"><a href="../../student_manage.jsp" target="contentBox"><img src="../images/help_bt01.gif" alt="学生管理" width="63" height="51" border="0"></a></td>
            <td width="2" align="center" background="../images/help_bt03.gif"><img src="../images/k.gif" width="2" height="1"></td>
            <td align="center"><a href="../../student_score.jsp" target="contentBox"><img src="../images/help_bt02.gif" alt="成绩管理" width="63" height="51" border="0"></a></td>
          </tr>
          <tr>
            <td height="2" colspan="3" align="center" background="../images/help_bt04.gif"><img src="../images/help_bt04.gif" width="2" height="2"></td>
          </tr>
          <%
          	if(ziyuan.equals("1"))
          	{
           %>
<%--           <tr>
            <td height="59" colspan="3" align="center"><a href="../../resource/course_resource.jsp" target="contentBox"><img src="../images/help_bt05.gif" alt="资料管理" width="177" height="48" border="0"></a></td>
          </tr>
--%>          
          <%
          	}
           %>
          <tr>
            <td height="2" colspan="3" align="center" background="../images/help_bt04.gif"><img src="../images/help_bt04.gif" width="2" height="2"></td>
          </tr>
        </table>
          <table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
              <td height="5"><img src="../images/k.gif" width="1" height="5"></td>
            </tr>
<!--
            <tr>
              <td height="20" align="center" class="time">
          <table width="84%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                <td height="20" align="left" class="time">
                <div class="study_bt1">课件</div></td>
                  <td height="20" align="left" class="time"> 
                  	<input id="combo-box-wares" name="warename" size="16"/></td>
                  	<td align="left" class="time">
                  	<div class="study_bt1" onmouseover="this.className='study_bt1'" onmouseout="this.className='study_bt1'">
                  	<input type=hidden name=link id="combo-box-link"/>
                  	<a type=button href="#" onclick="openware()" title="浏览课件"/>浏览</a></div>
                  </td>
                </tr>
              </table>
              </td>
            </tr>
-->
            <tr>
              <td height="5"><img src="../images/k.gif" width="1" height="5"></td>
            </tr>
            <tr>
               <td style=cursor:hand align="center" title=题库管理 onMouseUp="top.openPage('../../lore/lore_dir_list.jsp',this.id)"><style type="text/css">
#logo1{width:100%; border:0; filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(src='../images/01.png',sizingMethod='image')}
</style>
<div id="logo1"></div> </td>
			
            </tr>
            <tr>
              <td height="5"><img src="../images/k.gif" width="1" height="5"></td>
            </tr>
            <%
            if(taolun.equals("1")) {
             %>
             <!-- 
            <tr>
               <td style=cursor:hand align="center" title=学科论坛 onMouseUp="top.openPage('courseforum_teacher.jsp',this.id)"><style type="text/css">
#logo2{width:100%; border:0; filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(src='../images/help_bt07.gif',sizingMethod='image')}
</style>
<div id="logo2"></div> </td>
            </tr>
             -->
            <% } %>
             <tr>
              <td height="5"><img src="../images/k.gif" width="1" height="5"></td>
            </tr>
 
          </table></td>
      </tr>
    </table></td>
  </tr>
</table>
</body>
</html>