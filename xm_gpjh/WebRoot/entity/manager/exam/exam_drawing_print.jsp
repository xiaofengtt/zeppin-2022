<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>试卷签领表</title>
<style type="text/css">
<!--
.STYLE13 {font-size: 12pt}
.STYLE18 {
	font-size: 16pt;
	font-weight: bold;
}
-->
</style>
</head>
<body>
<s:set name="semester" value="currentSemName"/>
<s:iterator value="getExamNo(#semester)" status="examnonum">
<s:set name="examNoid" value="id"/>
<s:set name="sequence" value="sequence"/>
<s:set name="paperType" value="paperType"/>
<s:iterator value="getExamSite(#examNoid)" status="sitenum">
<s:if test="#sitenum.index+#examnonum.index!=0"><div style='page-break-after: always;'></div></s:if>
<s:set name="examSiteid" value="id"/>
<s:set name="examSitename" value="name"/>
<s:set name="page" value="1"/>
<s:set name="row" value="3"/>
<table align="center" width="580" border="0">
  <tr>
    <td align="center" ><span class="STYLE18"><s:property value="#semester"/>学期期末考试第&nbsp;<s:property value="#sequence"/>&nbsp;场试卷签领表</span></td>
  </tr>
  <tr>
    <td><table width="100%" border="0"><tr>
        <td width="70%" align="left" ><strong class="STYLE13"><s:property value="#examSitename"/></strong></td>
        <td width="30%" align="center"><span class="STYLE13">第<s:property value="#page"/>页</span></td>
     	</tr>
     	</table>
	</td>
  </tr>
</table>
<s:iterator value="getExamRoom(#examNoid,#examSiteid)">
<s:set name="examRoomid" value="id"/>
<s:if test="#row>40">
<s:set name="row" value="3"/><s:set name="page" value="#page+1"/>
<div style='page-break-after: always;'></div>
<table align="center" width="580" border="0">
  <tr>
    <td align="center" ><span class="STYLE18"><s:property value="#semester"/>学期期末考试第&nbsp;<s:property value="#sequence"/>&nbsp;场试卷签领表</span></td>
  </tr>
  <tr>
    <td><table width="100%" border="0"><tr>
        <td width="70%" align="left" ><strong class="STYLE13"><s:property value="#examSitename"/></strong></td>
        <td width="30%" align="center"><span class="STYLE13">第<s:property value="#page"/>页</span></td>
     	</tr>
     	</table>
	</td>
  </tr>
</table>
</s:if>
<table align="center" width="580" border="0"> 
  <tr><s:set name="row" value="#row+1"/>
    <td width="50%"><s:property value="classroom"/></td>
    <td width="25%"><span class="STYLE13">领卷人：</span></td>
    <td width="25%"><span class="STYLE13">回卷人：</span></td>
  </tr>
</table>
<table align="center" width="580" border="0"> 
  <tr>
    <td>
      <table width="100%" border="1"  cellpadding="0"   cellspacing="0"  borderColor=#000000
        rules=all style="BORDER-RIGHT:   #000000   1px   solid;   BORDER-TOP:   #000000   1px   solid;   BORDER-LEFT:   #000000   1px   solid;   WIDTH:   100%;   
        BORDER-BOTTOM:   #000000   1px   solid;   BORDER-COLLAPSE:   collapse;  ">
        <tr><s:set name="row" value="#row+1"/>
             <td nowrap="nowrap" width="7%" align="center"><span class="STYLE13">顺序号</span></td>
          <td nowrap="nowrap" align="center"><span class="STYLE13">科目代码</span></td>
          <td nowrap="nowrap" width="40%" align="center"><span class="STYLE13">考试科目</span></td>
          <td nowrap="nowrap" width="12%" align="center"><span class="STYLE13">试卷类型</span></td>
          <td nowrap="nowrap" width="6%" align="center"><span class="STYLE13">份数</span></td>
          <td nowrap="nowrap" width="13%" align="center"><span class="STYLE13">领卷核对</span></td>
          <td nowrap="nowrap" width="13%" align="center"><span class="STYLE13">回卷核对</span></td>
        </tr>
        <s:set name="examCourse" value="getExamCourse(#examRoomid,#examSiteid)"/>
        <s:set name="maxc" value="#examCourse.length"/>
        <s:iterator value="#examCourse" status="c">
        	<s:set name="courseinfo"/>
        <tr><s:set name="row" value="#row+1"/>
          <td nowrap="nowrap" width="7%" align="center"><span class="STYLE13"><s:property value="#c.index+1"/></span></td>
          <td nowrap="nowrap" align="center"><span class="STYLE13"><s:property value="#courseinfo[0]"/></span></td>
          <td nowrap="nowrap" width="40%" align="center"><span class="STYLE13"><s:property value="#courseinfo[1]"/></span></td>
          <td nowrap="nowrap" width="12%" align="center"><span class="STYLE13"><s:property value="#paperType"/></span></td>
          <td nowrap="nowrap" width="6%" align="center"><span class="STYLE13">
          <s:if test="#courseinfo[2]>=30"><s:property value="#courseinfo[2]+2"/></s:if><s:else><s:property value="#courseinfo[2]"/></s:else>
          </span></td>
          <td nowrap="nowrap" width="13%" align="center">&nbsp;</td>
          <td nowrap="nowrap" width="13%" align="center">&nbsp;</td>
        </tr>
        <s:if test="#row>42">
<s:set name="row" value="3"/><s:set name="page" value="#page+1"/>
        </table>
	</td>
  </tr>
</table>
<div style='page-break-after: always;'></div>
<table align="center" width="580" border="0">
  <tr>
    <td align="center" ><span class="STYLE18"><s:property value="#semester"/>学期期末考试第&nbsp;<s:property value="#sequence"/>&nbsp;场试卷签领表</span></td>
  </tr>
  <tr>
    <td><table width="100%" border="0"><tr>
        <td width="70%" align="left" ><strong class="STYLE13"><s:property value="#examSitename"/></strong></td>
        <td width="30%" align="center"><span class="STYLE13">第<s:property value="#page"/>页</span></td>
     	</tr>
     	</table>
	</td>
  </tr>
</table>
<s:if test="#c.index+1<#maxc">
<table align="center" width="580" border="0"> 
  <tr><s:set name="row" value="#row+1"/>
    <td width="50%"><s:property value="classroom"/></td>
    <td width="25%"><span class="STYLE13">领卷人：</span></td>
    <td width="25%"><span class="STYLE13">回卷人：</span></td>
  </tr>
</table>
<table align="center" width="580" border="0"> 
  <tr>
    <td>
      <table width="100%" border="1"  cellpadding="0"   cellspacing="0"  borderColor=#000000
        rules=all style="BORDER-RIGHT:   #000000   1px   solid;   BORDER-TOP:   #000000   1px   solid;   BORDER-LEFT:   #000000   1px   solid;   WIDTH:   100%;   
        BORDER-BOTTOM:   #000000   1px   solid;   BORDER-COLLAPSE:   collapse;  ">
		<tr><s:set name="row" value="#row+1"/>
             <td nowrap="nowrap" width="7%" align="center"><span class="STYLE13">顺序号</span></td>
          <td nowrap="nowrap" align="center"><span class="STYLE13">科目代码</span></td>
          <td nowrap="nowrap" width="40%" align="center"><span class="STYLE13">考试科目</span></td>
          <td nowrap="nowrap" width="12%" align="center"><span class="STYLE13">试卷类型</span></td>
          <td nowrap="nowrap" width="6%" align="center"><span class="STYLE13">份数</span></td>
          <td nowrap="nowrap" width="13%" align="center"><span class="STYLE13">领卷核对</span></td>
          <td nowrap="nowrap" width="13%" align="center"><span class="STYLE13">回卷核对</span></td>
        </tr>
</s:if>
        </s:if>
        </s:iterator>
      </table>
     </td>
   </tr>
 </table>
</s:iterator>
</s:iterator>
</s:iterator>
</body>
</html>
