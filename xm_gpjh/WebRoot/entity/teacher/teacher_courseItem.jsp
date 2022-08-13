<%@ page language="java"  pageEncoding="UTF-8" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*,com.whaty.platform.util.*,com.whaty.platform.util.log.*" %>
<%@ page import="com.whaty.platform.entity.activity.*,com.whaty.platform.entity.activity.score.*" %>
<%@ page import="com.whaty.platform.entity.*,com.whaty.platform.entity.basic.*,com.whaty.platform.entity.config.*" %>
<%@ page import="com.whaty.platform.entity.recruit.*,com.whaty.platform.entity.setup.*" %>
<%@ page import="com.whaty.platform.entity.user.*,com.whaty.platform.entity.test.*" %>
<%@ page import="com.whaty.platform.info.*" %>

<%@ include file="pub/priv.jsp" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">


<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>华南师范大学现代远程教育平台</title>
<link href="./css/css2.css" rel="stylesheet" type="text/css">
<script language="javascript">


function change_status(id,status,item)
{
	var a="";
	var newstatus;
	if(status==0){
		a="您确定要设置为显示吗？";
		newstatus=1;
	}
	if(status==1){
		a="您确定要设置为不显示吗？";
		newstatus=0;		
	}
	var truthBeTold = window.confirm(a);
	if (truthBeTold) {
		window.location = "change_item_status.jsp?teachclass_id="+id+"&status="+status+"&item="+item;
	} 
}

</script>

</head>
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
	String teachclass_id=request.getParameter("teachclass_id");
	
	 
    List itemList=teacherOperationManage.getTheachItem(teachclass_id);
    
    String[] stat=new String[2];
    	stat[0]="不显示";
    	stat[1]="显示";
    
%>
<body leftmargin="0" topmargin="0">
      <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td valign="top"><table width="85%" border="1" align="center" cellpadding="2" cellspacing="0" bordercolorlight="94B3D6" bordercolordark="#FFFFFF">
              <tr> 
                <td width="70%" align="center" bgcolor="#DFF5FF" class="td1">交流园地栏目</td>
                <td align="center" bgcolor="#DFF5FF"  class="td1">设置是否显示</td>
              </tr>
<%
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
 if(itemList!=null){
 
    for(int i=0;i<itemList.size();i++){
           	CourseItem techItem=(CourseItem) itemList.get(i);
           	dayi=fixnull(techItem.getDayi());   //在线答疑<
           	gonggao=fixnull(techItem.getGonggao()); //公告
           	taolun=fixnull(techItem.getTaolun());  //讨论
           	kaoshi=fixnull(techItem.getKaoshi()); //考试
           	zuoye=fixnull(techItem.getZuoye());  //作业
           	ziyuan=fixnull(techItem.getZiyuan()); //>资源下载
    		zxdayi=fixnull(techItem.getZxdayi());  //视频答疑
    		smzuoye=fixnull(techItem.getSmzuoye());  //书面作业
    		zice=fixnull(techItem.getZice());         //在线自测
    		pingjia=fixnull(techItem.getPingjia());  //评价
    		daohang=fixnull(techItem.getDaohang());
    		daoxue=fixnull(techItem.getDaoxue());   //导学与学习
    		shiyan=fixnull(techItem.getShiyan());   //课程实验
    		zfx=fixnull(techItem.getZfx());        //总复习
    		boke=fixnull(techItem.getBoke());      //课程博客
   	}
   }

%>
				<tr> 
                <td  class="td2" align="center">导学与学习</td>
                <td class="time" align="center" ><b><% item="daoxue";%><a href="javascript:change_status('<%=teachclass_id%>','<%=daoxue%>','<%=item%>')"><%=stat[Integer.parseInt((daoxue).trim())] %></a></b></td>
              </tr> 
              <tr> 
                <td  class="td2" align="center">在线答疑</td>
                <td class="time" align="center" ><b><% item="dayi";%><a href="javascript:change_status('<%=teachclass_id%>','<%=dayi%>','<%=item%>')"><%=stat[Integer.parseInt((dayi).trim())] %></a></b></td>
              </tr> 
              <tr> 
                <td  class="td2" align="center">视频答疑</td>
                <td class="time" align="center" ><b><% item="zxdayi";%><a href="javascript:change_status('<%=teachclass_id%>','<%=zxdayi%>','<%=item%>')"><%=stat[Integer.parseInt((zxdayi).trim())] %></a></b></td>
              </tr>
               <tr> 
                <td  class="td2" align="center">书面作业</td>
                <td class="time" align="center" ><b><% item="smzuoye";%><a href="javascript:change_status('<%=teachclass_id%>','<%=smzuoye%>','<%=item%>')"><%=stat[Integer.parseInt((smzuoye).trim())] %></a></b></td>
              </tr>
             <tr> 
                <td  class="td2" align="center">在线作业</td>
                <td class="time" align="center"><%item="zuoye";%><a href="javascript:change_status('<%=teachclass_id%>','<%=zuoye%>','<%=item%>')"><%=stat[Integer.parseInt((zuoye).trim())] %></a></td>
              </tr> 
              <tr> 
                <td  class="td2" align="center">在线自测</td>
                <td class="time" align="center"><%item="zice";%><a href="javascript:change_status('<%=teachclass_id%>','<%=zice%>','<%=item%>')"><%=stat[Integer.parseInt((zice).trim())] %></a></td>
              </tr> 
              <tr> 
                <td  class="td2" align="center">在线考试</td>
                <td class="time" align="center"><%item="kaoshi";%><a href="javascript:change_status('<%=teachclass_id%>','<%=kaoshi%>','<%=item%>')"><%=stat[Integer.parseInt((kaoshi).trim())] %></a></td>
              </tr> 
              <tr> 
                <td  class="td2" align="center">课程实验</td>
                <td class="time" align="center"><%item="shiyan";%><a href="javascript:change_status('<%=teachclass_id%>','<%=shiyan%>','<%=item%>')"><%=stat[Integer.parseInt((shiyan).trim())] %></a></td>
              </tr> 
              <tr> 
                <td  class="td2" align="center">总复习</td>
                <td class="time" align="center"><%item="zfx";%><a href="javascript:change_status('<%=teachclass_id%>','<%=zfx%>','<%=item%>')"><%=stat[Integer.parseInt((zfx).trim())] %></a></td>
              </tr> 
               <tr> 
                <td  class="td2" align="center">评价与建议</td>
                <td class="time" align="center"><%item="pingjia";%><a href="javascript:change_status('<%=teachclass_id%>','<%=pingjia%>','<%=item%>')"><%=stat[Integer.parseInt((pingjia).trim())] %></a></td>
              </tr> 
              <tr> 
                <td  class="td2" align="center">公告</td>
                <td class="time" align="center"><% item="gonggao";%><a href="javascript:change_status('<%=teachclass_id%>','<%=gonggao%>','<%=item%>')"><%=stat[Integer.parseInt((gonggao).trim())] %></a></td>
              </tr> 
				
              <tr> 
                <td  class="td2" align="center">资源下载</td>
                <td class="time" align="center"><%item="ziyuan";%><a href="javascript:change_status('<%=teachclass_id%>','<%=ziyuan%>','<%=item%>')"><%=stat[Integer.parseInt((ziyuan).trim())] %></a></td>
              </tr> 
              <tr> 
                <td  class="td2" align="center">课程博客</td>
                <td class="time" align="center"><%item="boke";%><a href="javascript:change_status('<%=teachclass_id%>','<%=boke%>','<%=item%>')"><%=stat[Integer.parseInt((boke).trim())] %></a></td>
              </tr> 
              <tr> 
                <td  class="td2" align="center">学科论坛</td>
                <td class="time" align="center"><%item="taolun";%><a href="javascript:change_status('<%=teachclass_id%>','<%=taolun%>','<%=item%>')"><%=stat[Integer.parseInt((taolun).trim())] %></a></td>
              </tr> 
                <tr> 
                <td  class="td2" align="center">辅导课件</td>
                <td class="time" align="center"><%item="taolun";%><a href="javascript:change_status('<%=teachclass_id%>','<%=taolun%>','<%=item%>')"><%=stat[Integer.parseInt((taolun).trim())] %></a></td>
              </tr> 
        </table></td>
        </tr>
        <tr>
          <td valign="middle" height="30px">
          
          <table width="90%" border="0" align="center" cellpadding="0" cellspacing="0">
               <tr align="center">
               <td align="center">
               <input type="button" name="close" value="关闭" onclick="javascript:window.close()">
               </td>
               </tr>
            </table></td>
        </tr>
      </table>
</body>

</html>
