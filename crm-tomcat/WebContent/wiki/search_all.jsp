<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.system.*,enfo.crm.vo.*,enfo.crm.service.*,enfo.crm.dao.*,enfo.crm.tools.*,java.util.*"%>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>
<%
ProductInfoReposLocal wikiRepos = EJBFactory.getProductInfoRepos();
List colud_list = wikiRepos.getCloudKeyword();
Map map = null;
Iterator iterator = colud_list.iterator();
%>
<html> 
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<title></title>
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<LINK href="/includes/default.css" type=text/css rel=stylesheet>
<base target="_self">
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>
<style type="text/css">
<!--
div { 
	font-family: Tahoma, Verdana, Arial;
	font-size: 12px; 
}

.inputq1 {
	font-family: Tahoma, Verdana, Arial;
	font-size: 12pt;
	padding-bottom: 4px;
	padding-left: 4px;
	padding-right: 4px;
	padding-top: 4px;
	height: 32px;
	border: #AAAAAA 1px solid;
}
.inputq2 {
	font-family: Tahoma, Verdana, Arial;
	font-size: 12pt;
	padding-bottom: 4px;
	padding-left: 4px;
	padding-right: 4px;
	padding-top: 4px;
	height: 32px;
	border: #4d90fe 1px solid;
}
-->
</style>	
</head>
<script language="javascript">
	setTimeout( function(){
		try{
			document.getElementById('enfo_crm_wiki_q').focus();
			document.getElementById("productSearchDiv").style.top=document.getElementById('enfo_crm_wiki_q').offsetTop+document.getElementById('enfo_crm_wiki_q').clientHeight+20;
			document.getElementById('productSearchDiv').style.display = "block";
		} catch(e){}
	}, 200);
	function switchpsd() {
		if (document.getElementById('chkProduct').checked==true) {
			document.getElementById('productSearchDiv').style.display = "block";
		} else {
			document.getElementById('productSearchDiv').style.display = "none";
		}
	}
	function validateForm(form){
		if ((!form.chkProduct.checked) && (!form.chkWiki.checked)) {
			alert("必须选择产品库或知识库！");
			return false;
		}
    	//if(!sl_check(form.enfo_crm_wiki_q, "搜索关键字", 200, 1)) return false;
    	syncDatePicker(form.pre_date1_picker, form.pre_date1);
    	syncDatePicker(form.pre_date2_picker, form.pre_date2);
    	syncDatePicker(form.prestart_date1_picker, form.prestart_date1);
    	syncDatePicker(form.prestart_date2_picker, form.prestart_date2);
    	return true;
	}
	function search(key) {
		document.theform.enfo_crm_wiki_q.value = key;
		document.theform.submit();
	}
</script>
<body class="BODY" topmargin="0" leftmargin="0" rightmargin="0" >
<form name="theform" method="post" action="search_result.jsp" onsubmit="javascript:return validateForm(this);">
	<div id="productSearchDiv" style="position:absolute;left:5px;display:none;border:1px solid #BBBBBB;padding:4px 4px 4px 4px;top:20px;">
		<p align="center">产品搜索条件</p>
		　状态：<select size="1" name="selStatus" style="width: 105px">
			<OPTION value="0">全部</OPTION>
			<OPTION value="2">推介</OPTION>
			<OPTION value="3">存续</OPTION>
			<OPTION value="4">结束</OPTION>
		</select><BR>
		　类别：<select size="1" name="selClass1" style="width: 105px">
			<%=Argument.getClassType1Options("")%>
		</select><BR>
		推介期：<INPUT TYPE="text" NAME="pre_date1_picker" class=selecttext value="" size="12" >
				<INPUT TYPE="button" value="▼" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.pre_date1_picker,theform.pre_date1_picker.value,this);" tabindex="10">
				<INPUT TYPE="hidden" NAME="pre_date1"   value="">&nbsp;<BR>　　　&nbsp;&nbsp;
			<INPUT TYPE="text" NAME="pre_date2_picker" class=selecttext value="" size="12" >
				<INPUT TYPE="button" value="▼" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.pre_date2_picker,theform.pre_date2_picker.value,this);" tabindex="12">
				<INPUT TYPE="hidden" NAME="pre_date2"   value=""><BR>
		预约开始日期：<br>　　　&nbsp;&nbsp;&nbsp;<INPUT TYPE="text" NAME="prestart_date1_picker" class=selecttext value="" size="12" >
				<INPUT TYPE="button" value="▼" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.prestart_date1_picker,theform.prestart_date1_picker.value,this);" tabindex="14">
				<INPUT TYPE="hidden" NAME="prestart_date1"   value="">&nbsp;<BR>　　　&nbsp;&nbsp;
			<INPUT TYPE="text" NAME="prestart_date2_picker" class=selecttext value="" size="12" >
				<INPUT TYPE="button" value="▼" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.prestart_date2_picker,theform.prestart_date2_picker.value,this);" tabindex="16">
				<INPUT TYPE="hidden" NAME="prestart_date2"   value="">
	</div>
	<table border="0" height="68%" align="center">
		<tr><td width="380"><P align="center">
			<input type="checkbox" checked="true" id="chkProduct" name="chkProduct" value="1" onclick="javascript:switchpsd();" style="border:0px;" />产品库&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="checkbox" checked="true" name="chkWiki" value="1" style="border:0px;" />知识库<BR>
			<input class="inputq1" type="text" id="enfo_crm_wiki_q" name="enfo_crm_wiki_q" size="50" onmouseover="this.className='inputq2'" onmouseout="this.className='inputq1'" onfocus="this.className='inputq2'" onblur="this.className='inputq1'" /><BR><BR>
			<button value="搜索" type="submit" name="btnG">&nbsp;&nbsp;搜&nbsp;索&nbsp;&nbsp;</button>
		</P><P align="left">
<%
while(iterator.hasNext()){
	map = (Map)iterator.next();
%>
			<a href="javascript:search('<%=Utility.trimNull(map.get("KEY_WORD"))%>');"><%=Utility.trimNull(map.get("KEY_WORD"))%></a>&nbsp;
<%
}
%>
		</P>
		</td></tr>
	</table>
</form>
</body>
</html>
<%wikiRepos.remove();
%>