<%@ page language="java" pageEncoding="GBK" import="java.util.*,java.math.*,enfo.crm.system.*,enfo.crm.vo.*, enfo.crm.customer.*,enfo.crm.tools.*,enfo.crm.dao.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>

<%
//获得GET参数
Integer define_id = Utility.parseInt(request.getParameter("define_id"), new Integer(0));//分级ID
String parent_define_name = Utility.trimNull(request.getParameter("parent_define_name"));//父级名称
int level_id = Utility.parseInt(request.getParameter("level_id"), 0);//节点数
Integer detail_id = Utility.parseInt(request.getParameter("detail_id"), new Integer(0));//分级ID

//获得保存单数
String define_name = Utility.trimNull(request.getParameter("define_name"));
String summary = Utility.trimNull(request.getParameter("summary"));
boolean bSuccess = false;

CustClassDefineLocal define_local = EJBFactory.getCustClassDefine();
CustClassDefineVO define_vo = new CustClassDefineVO();
CustClassDetailLocal detail_local = EJBFactory.getCustClassDetail();
CustClassDetailVO detail_vo = new CustClassDetailVO();

//保存客户分级信息
if(request.getMethod().equals("POST"))
{
	//添加基数节点,即添加到CustClassDefine表中
	if(level_id != 0 && (level_id % 2 != 0))
	{
		//CLASSDEFINE_ID=现有分级ID的最大值+10
		//如何是添加1级节点，则获得最大的ID+10作为该分级ID
		int class_define_id = 0;
		if(level_id == 1){
			define_vo.setInput_man(input_operatorCode);
			define_vo.setLevel_id(new Integer(1));//一级菜单
			List define_list = define_local.query(define_vo);
			int index = define_list.size() - 1;
			Map define_map = (Map)define_list.get(index);
			int max_define_id = Utility.parseInt(Utility.trimNull(define_map.get("CLASSDEFINE_ID")), 0);
			if(max_define_id != 0){
				class_define_id = max_define_id + 10;
			}
		}else{
			define_vo.setParent_id(define_id);
			define_vo.setParent_value(detail_id);
			define_vo.setInput_man(input_operatorCode);
			List define_list = define_local.query(define_vo);
			if(define_list.size() > 0)
			{
				Map define_map = (Map)define_list.get(define_list.size() - 1); 
				class_define_id = Utility.parseInt(Utility.trimNull(define_map.get("CLASSDEFINE_ID")), 0)*100 + 1;
			}else
			{	
				class_define_id = define_id.intValue()*100 + 1;
			}
		}
		if(class_define_id != 0)
		{
			define_vo.setClass_define_id(new Integer(class_define_id));
			define_vo.setClass_define_name(define_name);
			define_vo.setSummary(summary);
			define_vo.setParent_id(define_id);
			define_vo.setParent_value(detail_id);
			define_vo.setCD_no(new Integer(1));
			define_local.append(define_vo);
		}
	}else{
		Integer min_value = Utility.parseInt(request.getParameter("min_value"),null);
		Integer max_value = Utility.parseInt(request.getParameter("max_value"),null);
		//添加偶数节点,即添加到CustClassDetail表中
		//先判断1级节点下有没有子节点
		detail_vo.setClass_define_id(define_id);
		detail_vo.setInput_man(input_operatorCode);
		List detail_list = detail_local.query(detail_vo);
		int class_detail_id = 0;
		if(detail_list.size() > 0)
			class_detail_id = Utility.parseInt(Utility.trimNull(((Map)detail_list.get(detail_list.size() - 1)).get("CLASSDETAIL_ID")), 0) + 1;
		else
			class_detail_id = define_id.intValue() * 100 + 1;
		detail_vo.setClass_detail_id(new Integer(class_detail_id));
		detail_vo.setClass_detail_name(define_name);
		detail_vo.setClass_minvalue(min_value);
		detail_vo.setClass_maxvalue(max_value);
		detail_local.append(detail_vo);
	}
	bSuccess = true;
}
%>
<HTML>
<HEAD>
<TITLE></TITLE>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<LINK HREF="<%=request.getContextPath()%>/includes/default.css" TYPE="text/css" REL="stylesheet">
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css
	rel=stylesheet>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js"></SCRIPT>
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="0">
<base target="_self">
<script language="vbscript" src="<%=request.getContextPath()%>/includes/default.vbs"></script>
<script language="javascript" src="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></script>
<script language="javascript" src="<%=request.getContextPath()%>/includes/financing.js"></script>
<script language="javascript">
<%if (bSuccess)
{
%>
	window.returnValue = 1;
	window.close();
<%}%>

//表单验证
function validateForm(form)
{	
	if(!sl_check(document.getElementsByName("define_name")[0], "<%=LocalUtilis.language("class.ratingName",clientLocale)%> ", 60, 1))		return false; //nvarchar（60）//评级名称
	if(sl_confirm("<%=LocalUtilis.language("message.saveUpdate",clientLocale)%> "))//保存修改
	{
		document.theform.submit();
	}	
}
</script>
</HEAD>
<BODY leftMargin=0 topMargin=0 rightmargin="0" bottommargin="0"
	onkeydown="javascript:chachEsc(window.event.keyCode)" class="BODY">
<form name="theform" method="post" action="client_class_define_new.jsp" onsubmit="javascript:return validateForm(this);">
<input type="hidden" name="define_id" value="<%=define_id%>">
<input type="hidden" name="parent_define_name" value="<%=parent_define_name%>">
<input type="hidden" name="level_id" value="<%=level_id%>">
<input type="hidden" name="detail_id" value="<%=detail_id%>">
<div style="height: 10px;"></div>
<div align="left" class="page-title">
	<font color="#215dc6"><b><%=LocalUtilis.language("menu.customerRating",clientLocale)%> </b></font><!--客户评级-->
</div>
<div align="center">
	<table border="0" width="100%" cellspacing="5" cellpadding="0">
		<tr>
			<td align="right" ><%=LocalUtilis.language("class.parentName",clientLocale)%> ：</td><!--上级群组名称-->
			<td >
				<input maxlength="100" readonly class='edline'  name="parent_name" size="50" onkeydown="javascript:nextKeyPress(this);" value="<%=parent_define_name%>">	
			</td>
		</tr>
		<tr>
			<td align="right">*<%=LocalUtilis.language("class.ratingName",clientLocale)%> ：</td><!--评级名称-->
			<td>
				<input type="text" name="define_name" size="50" value="" onkeydown="javascript:nextKeyPress(this)">
			</td>
		</tr>
		<tr <%if(level_id != 0 && (level_id % 2 != 0)) {out.print("style='display: none;'");}%>>
			<td align="right"><%=LocalUtilis.language("class.min",clientLocale)%> ：</td><!--最小值-->
			<td><input type="text" name="min_value" size="30" value="" onkeydown="javascript:nextKeyPress(this)"></td>
		</tr>
		<tr <%if(level_id != 0 && (level_id % 2 != 0)) {out.print("style='display: none;'");}%>>
			<td align="right"><%=LocalUtilis.language("class.max",clientLocale)%> ：</td><!--最大值-->
			<td><input type="text" name="max_value" size="30" value="" onkeydown="javascript:nextKeyPress(this)"></td>
		</tr>
		<tr <%if(level_id != 0 && (level_id % 2 == 0)) {out.print("style='display: none;'");}%>>
			<td align="right"><%=LocalUtilis.language("class.description",clientLocale)%> ：</td><!--备注-->
			<td>
				<textarea rows="3" name="summary" onkeydown="javascript:nextKeyPress(this)" cols="50"><%=summary%></textarea>
			</td>
		</tr>
	</table>
</div>

<div align="right">
	<br>
	<!--保存-->
    <button type="button"  class="xpbutton3" accessKey=s id="btnSave" name="btnSave" onclick="javascript:if(document.theform.onsubmit()){ document.theform.submit();}"><%=LocalUtilis.language("message.save",clientLocale)%> (<u>S</u>)</button>
	&nbsp;&nbsp;&nbsp;&nbsp;
	<!--取消-->
    <button type="button"  class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" onclick="javascript:window.returnValue=null;window.close();"><%=LocalUtilis.language("message.cancel",clientLocale)%> (<u>C</u>)</button>
	&nbsp;&nbsp;
</div>
</form>
</BODY>
</HTML>
