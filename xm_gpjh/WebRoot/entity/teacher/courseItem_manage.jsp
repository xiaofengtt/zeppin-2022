<%@ page language="java"  pageEncoding="UTF-8" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.whaty.platform.entity.CourseItemManage"%>
<%@ page import="com.whaty.platform.database.oracle.standard.entity.OracleCourseItemManage"%>
<%@ page import="java.util.List"%>
<%@ page import="com.whaty.platform.entity.basic.CourseItem"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<script language="javascript">

function check() {
	if(document.getElementById('smzuoye_1').checked == false && document.getElementById('zuoye_1').checked == false ){
		alert("请设置作业形式。");
		return false;
	}
}

</script>

<%!
	//判断字符串为空的话，赋值为"不详"
	String fixnull(String str)
	{
	    if(str == null || str.equals("") || str.equals("null"))
			str = "0";
			return str;
	
	}
%>
<%
	CourseItemManage manage = new OracleCourseItemManage();
	
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
	
	String courseIds = request.getSession().getAttribute("courseIds").toString();
	String[] ids = courseIds.split(",");
	if(ids.length == 1){

		List itemList = manage.getTheachItem(ids[0]);
		
		if(itemList!=null){
		    for(int i=0;i<itemList.size();i++){
				CourseItem techItem=(CourseItem) itemList.get(i);
		        dayi=fixnull(techItem.getDayi());   //课 程 答 疑
				taolun=fixnull(techItem.getTaolun());  //讨论
				kaoshi=fixnull(techItem.getKaoshi()); //考试
				zuoye=fixnull(techItem.getZuoye());  //在线作业
				smzuoye=fixnull(techItem.getSmzuoye());  //纸质作业
		    	zice=fixnull(techItem.getZice());         //在线自测
				daohang=fixnull(techItem.getDaohang());	//学习进度参考
		    	daoxue=fixnull(techItem.getDaoxue());   //视 频 辅 导
		    	shiyan=fixnull(techItem.getShiyan());   //课 程 实 验
		    	zfx=fixnull(techItem.getZfx());        //复 习 资 料
		    	ziyuan=fixnull(techItem.getZiyuan()); //阶 段 导 学
		    	zxdayi=fixnull(techItem.getZxdayi()); //课 件 浏 览
		    	pingjia = fixnull(techItem.getPingjia());//模拟试验
		    	boke = fixnull(techItem.getBoke());//课程设计
		   	}
		}
	}

 %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>华南师范大学远程教育平台</title>
<link href="./css/css2.css" rel="stylesheet" type="text/css">

</head>
<body leftmargin="0" topmargin="0"  style="padding-top: 100">
<form action="change_item_status1.jsp" method="post" onsubmit="return check()">
      <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td valign="top"><table width="85%" border="1" align="center" cellpadding="2" cellspacing="0" bordercolorlight="94B3D6" bordercolordark="#FFFFFF">
              <tr> 
                
                <td align="center" bgcolor="#DFF5FF"  class="td1" colspan="2">设置类型</td>
              </tr>
               <tr> 
                <td  class="td2" align="center">上 传 作 业<input type="radio" name="xingshi" value="0" id="smzuoye_1" <%if(smzuoye.equals("1")){%> checked="checked" <%} %> ></td>
                <td  class="td2" align="center">在 线 作 业（题库出题）<input type="radio" name="xingshi" value="1" id="zuoye_1" <%if(zuoye.equals("1")){%> checked="checked" <%} %>></td>
              </tr> 
        </table></td>
        </tr>
        <tr>
          <td valign="middle" height="30px">
          
          <table width="90%" border="0" align="center" cellpadding="0" cellspacing="0">
               <tr align="center">
               <td align="center">
               <input type="submit" name="sure" value="确定">
               <input type="button" name="close" value="返回" onclick="javascript:window.history.back()">
               </td>
               </tr>
            </table></td>
        </tr>
      </table>
</form>
</body>

</html>
