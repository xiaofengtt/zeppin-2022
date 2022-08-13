<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>查看我的培训级别申请</title>
<link href="/entity/bzz-students/css.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
<!--
function del(){
	var s=document.getElementsByName('applyId');
	var str="";
	for(var i=0;i<s.length;i++){
		if(s[i].checked){
			str=str+s[i].value+",";
		}
	}
	if(str.length<1){
		alert('请先选择要删除的申请');
		return false;
	}
	window.location.href="/entity/study/peTrainingTypeApplyAction_delApply.action?ids="+str;
}
var win;
function subUp(){
   	if(win==null || win.closed){
     win=window.open('/entity/study/peApply_addApply.action?applyType=shengji','','left=500,top=200,resizable=yes,scrollbars=no,height=200,width=400,location=no');
     }else{
     alert("当前申请尚未提交！");
     } 
}

function subGet(){
   	if(win==null || win.closed){
     win=window.open('/entity/study/peApply_addApply.action?applyType=zhengshu','','left=500,top=200,resizable=yes,scrollbars=no,height=200,width=400,location=no');
     } else{
     alert("当前申请尚未提交！");
     }
}

function sub(){
   	if(win==null || win.closed){
     win=window.open('/entity/study/peApply_addApply.action?applyType=jieye','','left=500,top=200,resizable=yes,scrollbars=no,height=200,width=400,location=no');
     } else{
     alert("当前申请尚未提交！");
     }
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

a:link { text-decoration: none;color: #000000}
a:active { text-decoration:blink}
a:hover { text-decoration:underline;color: red} 
a:visited { text-decoration: none;color: #000000}

-->
</style></head>

<body bgcolor="#E0E0E0">
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
    <td width="237" valign="top"><IFRAME NAME="leftA" width=237 height=520 frameborder=0 marginwidth=0 marginheight=0 SRC="/entity/bzz-students/pub/left.jsp" scrolling=no allowTransparency="true"></IFRAME></td>
   <td width="752" valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr align="center" valign="top">
        <td height="17" align="left" class="twocentop"><img src="/entity/bzz-students/images/two/1.jpg" width="11" height="12" /> 当前位置：学习申请</td>
      </tr>
      <tr>
          <td style="font-size:12px;color:red"><br>
          	<s:if test="canUpTrain">&nbsp;&nbsp;可以申请升级培训级别</s:if>
          	<s:if test="canGetCertificate">&nbsp;&nbsp;可以申请证书</s:if>
          	<s:if test="canEndCourse">&nbsp;&nbsp;可以申请结业</s:if>
          <br></td>
      </tr>
       <!-- <tr>
            <td align="left"><img src="/entity/bzz-students/images/two/vote.jpg" width="124" height="25" onclick="window.open('/entity/study/peTrainingTypeApplyAction_addApply.action','','left=500,top=200,resizable=yes,scrollbars=no,height=200,width=400,location=no')"/></td>
          </tr>
           -->
      <tr>
      	<td>
      		<input type="button" value="申请升级培训" <s:if test="noUpTrain">disabled</s:if> onclick="subUp()" style="font-size:12px;height:23;background-color:#ece9d8;border-width:1;"/>
      		<input type="button" value="申请证书" <s:if test="noCertificate">disabled</s:if> onclick="subGet()" style="font-size:12px;height:23;background-color:#ece9d8;border-width:1;"/>
      		<input type="button" value="申请结业" <s:if test="noCompletion">disabled</s:if> onclick="sub()" style="font-size:12px;height:23;background-color:#ece9d8;border-width:1;"/>
      	</td>
      </tr>
      <tr align="center">
        <td height="29" background="/entity/bzz-students/images/two/two2_r15_c5.jpg">
        <table width="96%" border="0" cellpadding="0" cellspacing="0" class="twocencetop">
          <tr>
            <td width="5%" align="center" height="30"></td>
            <td width="15%" align="left">申请类型</td>
            <td width="25%" align="center">申请日期</td>
            <td width="15%" align="center">申请状态</td>
            <td width="25%" align="center">审核日期</td>
            <td width="15%" align="center">查看详细</td>
          </tr>
        </table>
        </td>
      </tr>
      <tr valign="top" align="center">
        <td><table width="96%" border="0" cellpadding="0" cellspacing="0" class="twocencetop" style="word-break:break-all;">
        <s:iterator id="apply" value="applyList" status="s">
          <tr style="color:<s:if test="#apply.enumConstByFlagApplyStatus.code==1">green</s:if><s:else>red</s:else>">
          	<td width="5%" align="center" height="30"><img src="/entity/bzz-students/images/two/4.jpg" width="9" height="9" /></td>
            <td width="15%" align="left"><s:property value="#apply.enumConstByApplyType.name"/></td>
            <td width="25%" align="center"><s:date name="#apply.applyDate" format="yyyy-MM-dd" /></td>
            <td width="15%" align="center"><s:property value="#apply.enumConstByFlagApplyStatus.name"/></td>
            <td width="25%" align="center"><s:date name="#apply.checkDate" format="yyyy-MM-dd" /></td>
            <td width="15%" align="center"><a href="/entity/study/peApply_getDetail.action?applyId=<s:property value="#apply.id"/>" target="_blank">查看详细</a></td>
          </tr>
          <tr>
            <td colspan="6"><img src="/entity/bzz-students/images/two/7.jpg" width="752" height="4" /></td>
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
						            		<a class="twocen1" href="/entity/study/peApply_getList.action?curPage=1">首页</a>
						            		<a class="twocen1" href="/entity/study/peApply_getList.action?curPage=<s:property value="%{curPage-1}"/>">上一页</a>
						            	</s:else>
						            	<s:if test="curPage == totalPage">
						            		<font color="gray">下一页 尾页</font>
						            	</s:if>
						            	<s:else>
						            		<a class="twocen1" href="/entity/study/peApply_getList.action?curPage=<s:property value="%{curPage+1}"/>">下一页</a>
						            		<a class="twocen1" href="/entity/study/peApply_getList.action?curPage=<s:property value="totalPage"/>">尾页</a>
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
<!--
	alert('<s:property value="msg"/>');
//-->
</script>
</s:if>
</html>