<%@ page contentType="text/html; charset=GBK" import="enfo.crm.system.*,enfo.crm.dao.*,enfo.crm.tools.*,enfo.crm.vo.*,java.util.*" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>
<%
//获取页面参数
String catalog_code = Utility.trimNull(request.getParameter("catalog_code"));
String catalog_name = Utility.trimNull(request.getParameter("catalog_name"));

DictparamLocal dictparam = EJBFactory.getDictparam();
DictparamVO vo = new DictparamVO();

vo.setCatalog_code(catalog_code);

IPageList printTemplateSetPageList = dictparam.getPrintTemplate(vo,-1,-1);
List printTemplateSetList = printTemplateSetPageList.getRsList();

boolean bsuccess = false;
if(request.getMethod().equals("POST")){
	String[] do_s_id = request.getParameterValues("do_s_id");
	String[] donot_s_id = request.getParameterValues("donot_s_id");
	if (do_s_id != null)
	{
		for(int i = 0;i < do_s_id.length; i++)
		{
			Integer do_template_id = Utility.parseInt(do_s_id[i], new Integer(0));
	        if(do_template_id.intValue() != 0)
			{
				vo.setTemplate_id(do_template_id);
				vo.setFlag(new Integer(2));
				vo.setInput_man(input_operatorCode);
				dictparam.modiPrintTemplateEnable(vo);
			} 
		}
	}
	if (donot_s_id != null)
	{
		for(int i = 0;i < donot_s_id.length; i++)
		{
			Integer donot_template_id = Utility.parseInt(donot_s_id[i], new Integer(0));
	        if(donot_template_id.intValue() != 0)
			{
				vo.setTemplate_id(donot_template_id);
				vo.setFlag(new Integer(1));
				vo.setInput_man(input_operatorCode);
				dictparam.modiPrintTemplateEnable(vo);
			}
		}
	}
	if(do_s_id != null || donot_s_id != null)
		bsuccess = true;
}
%>
<HTML>
<HEAD>
<TITLE></TITLE>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<LINK href="/includes/default.css" type=text/css rel=stylesheet>
<LINK href="/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="0">

<SCRIPT LANGUAGE="vbscript" SRC="/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/default.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/queryEx/scripts/queryEx.js"></SCRIPT>
<script language=javascript>
<%if(bsuccess){%>
	alert("启动标志修改成功!");
	location = "printtemplateset_list.jsp?catalog_code=<%=catalog_code%>";
<%}%>


function showInfo(template_id){
    location = "printtemplateset_set.jsp?template_id="+template_id;    
}

function newPrintTemplate(){
    location = "printtemplateset_new.jsp?catalog_code=<%=catalog_code%>&catalog_name=<%=catalog_name%>";    
}

function enableDemo(){
	if(checkedCount(document.theform.do_s_id) == 0 && checkedCount(document.theform.donot_s_id) == 0){
		sl_alert("请选定模版！");
		return false;
	}
	disableAllBtn(true);
	document.theform.submit();
}
</script>
<BODY class="BODY" >
<form name="theform" method="POST" action="">
<table border="0" width="100%" cellspacing="0" cellpadding="0">
	<tr>
		<td><IMG src="/images/member.gif" align=absBottom border=0
			width="32" height="28"><b><%=menu_info%>>><%=catalog_name%>打印模板设置</b></td>
		<td>
	</tr>
	<tr>
		<td align="right">
			<%if (input_operator.hasFunc(menu_id, 100))	{%>
			<button type="button"  class="xpbutton3" accessKey=a onclick="javascript:newPrintTemplate();">新建(<u>A</u>)</button>
			&nbsp;&nbsp;&nbsp;<%}%>
			<%if (input_operator.hasFunc(menu_id, 101))	{%>
			<button type="button"  class="xpbutton4" accessKey=e onclick="javascript:enableDemo();">启用设置(<u>E</u>)</button>
			&nbsp;&nbsp;&nbsp;<%}%>
		</td>
	</tr>
	<tr>
		<td colspan="2">
		<hr noshade color="#808080" size="1">
		</td>
	</tr>
</table>
<table id="table3" border="0" cellspacing="1" cellpadding="2" class="tablelinecolor" width="100%" >
	<tr class="trh">
		<td align="left" width="10%"><input type="checkbox" class="selectAllBox" onclick="javascript:selectAllBox(document.theform.do_s_id,this);">启用</td>
		<td align="left" width="10%"><input type="checkbox" class="selectAllBox" onclick="javascript:selectAllBox(document.theform.donot_s_id,this);">不启用</td>
		<td>模板代码</td>
		<td>模板名称</td>
		<td>备注</td>
		<td>打印格式设置</td>
		<!--<td>删除</td>-->
	</tr>        					
	<%
	Map map = null;
	for(int i=0;printTemplateSetList!=null&&i<printTemplateSetList.size();i++){
	    map = (Map)printTemplateSetList.get(i);
	%>
	<tr class="tr<%=i%2%>">
		<td align="left">
			<%if(!Utility.trimNull(map.get("FLAG")).equals("2")){%>
				<input type="checkbox" name="do_s_id" class="selectAllBox" value="<%=map.get("TEMPLATE_ID")%>">
			<%}else{%>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;已启用
			<%}%>
		</td>
		<td align="left">
			<%if(Utility.trimNull(map.get("FLAG")).equals("2")){%>
				<input type="checkbox" name="donot_s_id" class="selectAllBox" value="<%=map.get("TEMPLATE_ID")%>">
			<%}else{%>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;未启用
			<%}%>
		</td>
	    <td align="center"><%=Utility.trimNull(map.get("TEMPLATE_CODE"))%></td>
		<td align="center"><%=Utility.trimNull(map.get("TEMPLATE_NAME"))%></td>
		<td><%=Utility.trimNull(map.get("SUMMARY"))%></td>
		<td align="center">
		    <button type="button"  class="xpbutton2" onclick="javascript:showInfo('<%=map.get("TEMPLATE_ID")%>')">>></button>
		</td>
	</tr>
	<%}%>
</table>
<br>
<table>
</form>
<%@ include file="/includes/foot.inc"%>	
</BODY>
</HTML>
<%
dictparam.remove();
%>