<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>国培计划学员工作室</title>
<link href="/entity/bzz-students/css.css" rel="stylesheet" type="text/css" />
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
</style>
<script type="text/javascript">
	window.onload = function() {
		window.location.href('/entity/first/firstPeVotePaper_toVote.action?bean.id=<s:property value="peVotePaper.id"/>');
	}
</script>
</head>

<body bgcolor="#E0E0E0">
<script type="text/javascript">
	//window.location.href('/entity/first/firstPeVotePaper_toVote.action?bean.id=<s:property value="peVotePaper.id"/>');
	//document.write("hehe ");
	//document.write("id:" + <s:property value="peVotePaper.id"/>);
</script>
<!-- 
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
        <td height="17" align="left" class="twocentop"><img src="/entity/bzz-students/images/two/1.jpg" width="11" height="12" /> 当前位置：调查问卷</td>
      </tr>
       <tr>
            <td align="left"><img src="/entity/bzz-students/images/two/vote.jpg" width="124" height="25" /></td>
          </tr>
      <tr align="center">
        <td height="29" background="/entity/bzz-students/images/two/two2_r15_c5.jpg">
        <table width="96%" border="0" cellpadding="0" cellspacing="0" class="twocencetop">
          <tr>
            <td width="10%" align="center">&nbsp;</td>
            <td width="52%" align="left">名称</td>
            <td width="8%" align="right">                                   发布时间                  </td>
            <td width="30%" align="center">操作</td>
          </tr>
        </table>
        </td>
      </tr>
      <tr valign="top" align="center">
        <td ><table width="96%" border="0" cellpadding="0" cellspacing="0" class="twocencetop">
        <s:iterator id="" value="peVotePaperList" status="pt">
          <tr>
            <td width="10%" align="center"><img src="/entity/bzz-students/images/two/4.jpg" width="9" height="9" /></td>
            <td width="52%" align="left"><a target="_blank" href="/entity/first/firstPeVotePaper_toVote.action?bean.id=<s:property value="id"/>"><s:property value="title"/></a></td>
            <td width="8%" align="left"><s:date name="foundDate" format="yyyy-MM-dd" /></td>
            <td width="30%"><a target="_blank" href="/entity/first/firstPeVotePaper_toVote.action?bean.id=<s:property value="id"/>" title="开始投票"><img src="/entity/bzz-students/images/kctp.jpg" width="100" height="22" border="0" /></a>
            </td>
          </tr>
          <tr>
            <td colspan="4"><img src="/entity/bzz-students/images/two/7.jpg" width="752" height="4" /></td>
          </tr>
         </s:iterator>
         
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
						            		<a class="twocen1" href="/entity/workspaceStudent/bzzstudent_getVoteList.action?curPage=1">首页</a>
						            		<a class="twocen1" href="/entity/workspaceStudent/bzzstudent_getVoteList.action?curPage=<s:property value="%{curPage-1}"/>">上一页</a>
						            	</s:else>
						            	<s:if test="curPage == totalPage">
						            		<font color="gray">下一页 尾页</font>
						            	</s:if>
						            	<s:else>
						            		<a class="twocen1" href="/entity/workspaceStudent/bzzstudent_getVoteList.action?curPage=<s:property value="%{curPage+1}"/>">下一页</a>
						            		<a class="twocen1" href="/entity/workspaceStudent/bzzstudent_getVoteList.action?curPage=<s:property value="totalPage"/>">尾页</a>
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
 -->
</body>
</html>