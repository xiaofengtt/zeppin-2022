<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="java.util.Set" %>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>生殖健康咨询师网络培训课堂</title>
<link href="/entity/bzz-students/css.css" rel="stylesheet" type="text/css" />
<script language="javascript" src="/entity/bzz-students/js/pro.js"></script>
<script type="text/JavaScript">
<!--
function MM_preloadImages() { //v3.0
  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}

function MM_swapImgRestore() { //v3.0
  var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
}

function MM_findObj(n, d) { //v4.01
  var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
    d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
  if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
  for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
  if(!x && d.getElementById) x=d.getElementById(n); return x;
}

function MM_swapImage() { //v3.0
  var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
   if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
}
function chk(){
	var title=document.getElementById('quesTitle');
	var detail=document.getElementById('quesDetail');
	if(title.value==''){
		alert('标题不能为空');
		title.focus();
		return false;
	}if(detail.value==''){
		alert('内容不能为空');
		detail.focus();
		return false;
	}
	if(detail.value.length>500){
	alert('您输入的字符数超过了指定长度，请检查重新输入！');
		detail.focus();
		return false;
	
	}
	document.getElementById('quesform').submit();
}
//-->
</script>
<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
.STYLE1 {
	color: #FF0000;
	font-weight: bold;
}
.STYLE2 {
	color: #FF5F01;
	font-weight: bold;
}
-->
</style></head>

<body bgcolor="#E0E0E0" onLoad="MM_preloadImages('/entity/bzz-students/images/jckc1.jpg','/entity/bzz-students/images/jckc1_over.jpg','/entity/bzz-students/images/tskc2.jpg','/entity/bzz-students/images/tskc2_over.jpg')">
<table width="1002" border="0" align="center" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF">
 <tr>
   <td>
<table width="1002" border="0" align="center" cellpadding="0" cellspacing="0">
  <IFRAME NAME="top" width=100% height=200 frameborder=0 marginwidth=0 marginheight=0 SRC="/entity/bzz-students/pub/top.jsp" scrolling=no allowTransparency="true"></IFRAME>
  <tr>
    <td height="13"></td>
  </tr>
</table>
<table width="1002" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td width="237" valign="top">
    <table><tr><IFRAME NAME="leftA" width=237 height=520 frameborder=0 marginwidth=0 marginheight=0 SRC="/entity/bzz-students/pub/left.jsp" scrolling=no allowTransparency="true"></IFRAME></td></tr>
    	<tr><form action="/entity/basic/messageBoardAction_insertMessages.action" method="post" id='quesform'><!-- parent.location.href=parent.location.href; -->
              <td align="left" class="twoleft" style="">
              		<div style="text-align:center;color:red;font-size:14px;margin-top:0px;font-weight:bold;" >我要提问</div>
					标题<input type="text" name="title" id='quesTitle'maxlength="18"/><br>
					内容<textarea name="detail" rows="6" cols="18" id='quesDetail'></textarea>
					<div style="text-align:center;"><input type="button" onclick='chk()' value="提交"/></div>
			  </td>
			  </form></table>
    </td>
   <td width="752" valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr align="center" valign="top">
        <td height="17" align="left" class="twocentop"><img src="/entity/bzz-students/images/two/1.jpg" width="11" height="12" /> 当前位置：查看提问</td>
      </tr>
      <tr>
          <td><br><!-- <img name="two2_r5_c5" src="images/two/two2_r5_c5.jpg" width="750" height="72" border="0" id="two2_r5_c5" alt="" /> --></td>
      </tr>
       <tr>
            <td align="left"><!-- img border="0" src="/entity/bzz-students/images/jckc1.jpg" width="124" height="25"  name="Image1" onMouseOver="MM_swapImage('Image1','','/entity/bzz-students/images/jckc1_over.jpg',1)" onMouseOut="MM_swapImgRestore()"/ --> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <!-- <a href="/entity/workspaceStudent/bzzstudent_toZiceTishengCourses.action"><img border="0" src="/entity/bzz-students/images/tskc2.jpg" width="124" height="25"  name="Image2" onMouseOver="MM_swapImage('Image2','','/entity/bzz-students/images/tskc2_over.jpg',1)" onMouseOut="MM_swapImgRestore()"/></a> -->
            </td>
          </tr>
      <tr align="center">
        <td height="29" background="/entity/bzz-students/images/two/two2_r15_c5.jpg"><table width="96%" border="0" cellpadding="0" cellspacing="0" class="twocencetop">
          <tr>
          	<td width="2%" align="center">&nbsp;</td>
            <td width="10%" align="center">标题</td>
            <td width="15%" align="center">提问者</td>
            <td width="15%" align="center">提问时间</td>
            <td width="12%" align="center">是否被回答</td>
            <td width="10%" align="center">回答者</td>
            <td width="15%" align="center">回答时间</td>
            <td width="21%" align="center">操作</td>
          </tr>
        </table></td>
      </tr>
      <tr valign="top">
        <td ><table width="96%" border="0" cellpadding="0" cellspacing="0" class="twocen">
        	<s:iterator id="msg" value="messageList" status="sts">
          <tr>
            <td width="2%" height="30" align="center"><img src="/entity/bzz-students/images/two/4.jpg" width="9" height="9" /></td>
            <td width="14%"><s:property value="sub(#msg[1],15)"/><!-- title --></td>
            <td width="10%" align="center"><s:property value="#msg[2]"/><!-- q_name --></td>
            <td width="20%" align="center"><s:property value="#msg[3]"/></td>
            <td width="8%"><s:property value="#msg[4]"/><!-- weather answered --></td>
            <td width="10%" align="center"><s:property value="#msg[5]"/></td>
            <td width="20%"><s:property value="#msg[6]"/>
            </td>
            <td width="16%" align=center>
           	<a href="#" onclick="window.open('/entity/basic/messageBoardAction_getMsgDetail.action?ids=<s:property value="#msg[0]"/>','','height=300,width=600,left=350,top=200')" onfocus="this.blur()" titile="查看详细内容" class="button" ><img src="/entity/bzz-students/images/two/5.jpg" width="59" titile="查看详细内容" height="22" border=0/></a>
           	<s:if test="#msg[4]=='未回答'"><a href="#" onclick="if(confirm('确定删除该问题吗？')){window.location.href='/entity/basic/messageBoardAction_delQuesOnPage.action?ids=<s:property value="#msg[0]"/>';}" onfocus="this.blur()" title="删除该问题" class="button" ><img src="/entity/function/images/multi_delete.png" height="18" border=0/></a></s:if>
            </td>
          </tr>
           <tr>
            <td colspan="8"><img src="/entity/bzz-students/images/two/7.jpg" width="752" height="4" /></td>
          </tr>
          </s:iterator>
         <tr>
            <td><br/></td>
            </tr>
         	<tr align="center">
         		<td height="29" background="/entity/bzz-students/images/two/two2_r15_c5.jpg" colspan="8">
         			<table width="96%" border="0" cellpadding="0" cellspacing="0" class="twocencetop">
         					<tr>
         						<td align="right" style="font-size:12px">
			                      	共 <s:property value="totalPage"/> 页 <s:property value="totalSize"/> 条记录 | 
						            	<s:if test="curPage == 1">
						            		<font color="gray">首页 上一页</font>
						            	</s:if>
						            	<s:else>
						            		<a class="twocen1" href="/entity/basic/messageBoardAction_getMessages.action?curPage=1">首页</a>
						            		<a class="twocen1" href="/entity/basic/messageBoardAction_getMessages.action?curPage=<s:property value="%{curPage-1}"/>">上一页</a>
						            	</s:else>
						            	<s:if test="curPage == totalPage">
						            		<font color="gray">下一页 尾页</font>
						            	</s:if>
						            	<s:else>
						            		<a class="twocen1" href="/entity/basic/messageBoardAction_getMessages.action?curPage=<s:property value="%{curPage+1}"/>">下一页</a>
						            		<a class="twocen1" href="/entity/basic/messageBoardAction_getMessages.action?curPage=<s:property value="totalPage"/>">尾页</a>
						            	</s:else>
			                      </td>
         					</tr>
         			</table>
         		</td>
         	</tr>
          </table></td>
      </tr>
      <tr>
       <td width="13">&nbsp;</td>
      </tr>
       
    </table></td>
    <td width="13">&nbsp;</td>
  </tr>
</table>
<IFRAME NAME="top" width=1002 height=73 frameborder=0 marginwidth=0 marginheight=0 SRC="/entity/bzz-students/pub/bottom.jsp" scrolling=no allowTransparency="true" align=center></IFRAME>
</td>
  </tr>
</table>
</body>
<s:if test="msg!=null">
<script type="text/javascript">
alert('<s:property value="msg"/>');
</script>
</s:if>
</html>