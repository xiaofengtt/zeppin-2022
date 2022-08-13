<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="../../pub/priv.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>menu</title>
<link href="../css/admincss.css" rel="stylesheet" type="text/css">
<style type="text/css">
<!--
body {
	background-image: url();
}
-->
</style></head>
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
<body leftmargin="0" topmargin="0" rightmargin="0" bottommargin="0">
<table width="100%" height="100" border="0" cellpadding="0" cellspacing="0" background="../images/menu_bgM1.gif" onselectstart="return false">
  <tr> 
    <td width="11" background="../images/split_bgM.gif">&nbsp;</td>
    <td background="../images/menu_bgM1.gif" class="meau_bg"><div class="menuSclBar" onMouseOver="top.setBright(this)" onMouseOut="top.setGray(this)">
      <%
      	if(gonggao.equals("1"))
      	{
       %>
      <div class="bigMenu">
        <table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td align="center"><table width="54" height="54" border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <td><img id="0101" src="../images/menu_ico01.gif" width="54" height="54" class="bigMenuImg" onClick="top.openPage('../../announce/announce_list.jsp',this.id)" onMouseOver="top.showHelpInfo('多幅撒')" onMouseOut="top.showHelpInfo()"></td>
                </tr>
            </table></td>
          </tr>
          <tr>
            <td align="center" class="bigMenuText">通知公告</td>
          </tr>
        </table>
      </div>
      <%
      	}
       %>
     <!-- <div class="bigMenu">
        <table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td align="center"><table width="54" height="54" border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <td><img id="0102" src="../images/menu_ico02.gif" width="54" height="54" class="bigMenuImg" onClick="top.openPage('../../sms/sms_list.jsp',this.id)" onMouseOver="top.showHelpInfo('多幅撒')" onMouseOut="top.showHelpInfo()"></td>
                </tr>
            </table></td>
          </tr>
          <tr>
            <td align="center" class="bigMenuText">短信管理</td>
          </tr>
        </table>
      </div> --> 
      <!-- 
      <div class="bigMenu">
        <table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td align="center"><table width="54" height="54" border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <td><img id="0103" src="../images/menu_ico03.gif" width="54" height="54" class="bigMenuImg" onClick="top.openPage('../../commonforum_whaty.jsp',this.id)" ></td>
                </tr>
            </table></td>
          </tr>
          <tr>
            <td align="center" class="bigMenuText">公共论坛</td>
          </tr>
        </table>
      </div>
       -->
      <!--div class="bigMenu">
        <table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td align="center"><table width="54" height="54" border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <td><img id="0104" src="../images/menu_ico04.gif" width="54" height="54" class="bigMenuImg" onClick="top.openNewPage('http://xiazai.upol.cn/media/web/campus/xywh/index.htm',this.id)" onMouseOver="top.showHelpInfo('多幅撒')" onMouseOut="top.showHelpInfo()"></td>
                </tr>
            </table></td>
          </tr>
          <tr>
            <td align="center" class="bigMenuText">网上校园</td>
          </tr>
        </table>
      </div> --> 
      <%--
      <div class="bigMenu">
        <table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td align="center"><table width="54" height="54" border="0" cellpadding="0" cellspacing="0">
			   <tr>
                  <td><img id="0105" src="../images/menu_ico05.gif" width="54" height="54" class="bigMenuImg" onClick="top.openNewPage('../../../teacher/teacher_resource_manage.jsp',this.id)" onMouseOver="top.showHelpInfo('多幅撒')" onMouseOut="top.showHelpInfo()"></td>
                </tr>
                
            </table></td>
          </tr>
          <tr>
            <td align="center" class="bigMenuText">学习工具</td>
          </tr>
        </table>
      </div>
      --%>
      <%--
      <div class="bigMenu">
        <table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td align="center"><table width="54" height="54" border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <td><img id="0106" src="../images/menu_ico06.gif" width="54" height="54" class="bigMenuImg" onClick="top.openPage('http://baidu.com',this.id)" onMouseOver="top.showHelpInfo('多幅撒')" onMouseOut="top.showHelpInfo()"></td>
                </tr>
            </table></td>
          </tr>
          <tr>
            <td align="center" class="bigMenuText">学习帮助</td>
          </tr>
        </table>
      </div>
	--%>      
</td>
    <td width="2" background="../images/content_rightM.gif"></td>
  </tr>
</table>
</body>
</html>
