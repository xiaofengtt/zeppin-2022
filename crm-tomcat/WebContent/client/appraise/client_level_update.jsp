<%@ page contentType="text/html; charset=GBK"  import="java.util.*,java.math.*,enfo.crm.system.*,enfo.crm.vo.*, enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>


<%
GradeStandardLocal gs_local = EJBFactory.getGradStandard();
GradeStandardVO gs_vo = new GradeStandardVO();
//获得地址栏参数
Integer grade_id = Utility.parseInt(request.getParameter("grade_id"), new Integer(0));//评级体系ID
Integer serial_no = Utility.parseInt(request.getParameter("serial_no"), new Integer(0));//评级标准序号
int newflag = Utility.parseInt(request.getParameter("newflag"), 0);//1新增；0修改
//获得提交参数
Integer gradeId = Utility.parseInt(request.getParameter("gradeId"), new Integer(0));//评级体系ID
String grade_level = Utility.trimNull(request.getParameter("grade_level"));//等级分类
String grade_level_name = Utility.trimNull(request.getParameter("grade_level_name"));//等级分类说明
String grade_level_sub = Utility.trimNull(request.getParameter("grade_level_sub"));
Integer min_value = Utility.parseInt(request.getParameter("min_value"), new Integer(0));
Integer max_value = Utility.parseInt(request.getParameter("max_value"), new Integer(0));
String grade_info = Utility.trimNull(request.getParameter("grade_info"));//等级说明
//设置参数
boolean bSuccess = false;
Integer serial_no_new = new Integer(0);
Integer grade_id_new = new Integer(0);
String grade_level_new ="";
String grade_level_name_new ="";
String grade_level_sub_new = "";
Integer min_val_new = new Integer(0);
Integer max_val_new = new Integer(0);
String grade_info_new = "";
List list = new ArrayList();
Map map = new HashMap();

if (request.getMethod().equals("POST")) {
	gs_vo.setGrade_id(grade_id);
	gs_vo.setGrade_level(grade_level);
	gs_vo.setGrade_level_name(grade_level_name);
	gs_vo.setGrade_level_sub(grade_level_sub);
	gs_vo.setMin_value(min_value);
	gs_vo.setMax_value(max_value);
	gs_vo.setGrade_info(grade_info);
	gs_vo.setInput_man(input_operatorCode);
	try{
		//新建
		if (newflag == 1)
			gs_local.addGradeState(gs_vo);
		//修改
		else if (newflag == 0)	{
			gs_vo.setGrade_id(gradeId);
			gs_vo.setSerial_no(serial_no);
			gs_local.modiGradeState(gs_vo);
		}	
		bSuccess = true;
	}catch(Exception e){
		out.println("<script type=\"text/javascript\">alert('"+e.getMessage()+"')</script>");
		out.println("<script type=\"text/javascript\">window.close();</script>");
		return;
	}

}
else
{
	//编辑查询单条记录
	if (newflag == 0) {
		gs_vo.setGrade_id(grade_id);
		gs_vo.setSerial_no(serial_no);
		list = gs_local.queryGradeStandByGidorSno(gs_vo);
		Iterator it = list.iterator();
		if(it.hasNext())
		{
			map = (Map)it.next();
			serial_no_new = Utility.parseInt(map.get("SERIAL_NO").toString(), new Integer(0));
			grade_id_new = Utility.parseInt(map.get("GRADE_ID").toString(), new Integer(0));
			grade_level_new = Utility.trimNull(map.get("GRADE_LEVEL"));
			grade_level_name_new = Utility.trimNull(map.get("GRADE_LEVEL_NAME"));
			grade_level_sub_new = Utility.trimNull(map.get("GRADE_LEVEL_SUB"));
			min_val_new = Utility.parseInt(map.get("MIN_VALUE").toString(), new Integer(0));
			max_val_new = Utility.parseInt(map.get("MAX_VALUE").toString(), new Integer(0));
			grade_info_new = Utility.trimNull(map.get("GRADE_INFO"));
		}
	}
}
%>

<HTML>
<HEAD>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="0">
<base target="_self">
<title><%=LocalUtilis.language("menu.ratingCriteriaSet",clientLocale)%> </title><!--评级标准细则设置-->
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>

<script language=javascript>
<%if (bSuccess) {
%>
	window.returnValue = 1;
	window.close();
<%}%>

function validateForm(form)
{
	if(!sl_checkChoice(form.grade_id, "<%=LocalUtilis.language("class.crmGrade",clientLocale)%> "))	return false;	//评分体系
	if(!sl_check(form.grade_level, "<%=LocalUtilis.language("class.gradeLevel4",clientLocale)%> ", 20, 1))		return false;//等级分类
	if(!sl_check(form.grade_level_name, "<%=LocalUtilis.language("class.gradeLevelName",clientLocale)%> ", 100, 1))		return false;//等级分类说明
	if(!sl_checkDecimal(form.max_value, "<%=LocalUtilis.language("class.dfMax",clientLocale)%> ", 16, 0, 0))	return false;//最大得分
	if(!sl_checkDecimal(form.min_value, "<%=LocalUtilis.language("class.dfMin",clientLocale)%> ", 16, 0, 0))	return false;	//最小得分
	var maxV=new Number(form.max_value.value);
	var minV=new Number(form.min_value.value);
	if(maxV<minV){
		sl_alert("<%=LocalUtilis.language("message.scoreError",clientLocale)%> ！");//最大得分小于最小得分，请重新输入
		return false;
	}
	if(!sl_check(form.grade_info, "<%=LocalUtilis.language("class.gradeInfo",clientLocale)%> ", 100, 1))		return false;//等级说明
	
	return sl_check_update();
}
</script>
</HEAD>
<BODY class="BODY body-nox">
<form name="theform" method="POST"
	action="client_level_update.jsp"
	onsubmit="javascript:return validateForm(this);">
	<input type="hidden" name="newflag" value="<%=newflag%>">
	<input type="hidden" name="serial_no" value="<%=serial_no%>">
	<input type="hidden" name="gradeId" value="<%=grade_id%>">	
<TABLE height="100%" cellSpacing=0 cellPadding=0 border=0 width="297">
	<TBODY>
		<TR>
			<TD vAlign=top align=left width="375">
			<TABLE cellSpacing=0 cellPadding=4 align=center border=0 width="394">
				<TBODY>
					<TR>
						<TD align="center" width="384">
						<table border="0" width="100%" cellspacing="0" cellpadding="0">
							<tr>
								<td class="page-title"><font color="#215dc6"><b><%=LocalUtilis.language("menu.ratingCriteriaSet",clientLocale)%> </b></font></td><!--评级标准细则设置-->
							</tr>
						</table>
						<br/>
				<table border="0" width="100%" cellspacing="0" class="table-popup">
					<tr>
						<td align="right" noWrap><%=LocalUtilis.language("class.crmGrade",clientLocale)%> :</td><!--评分体系-->
						<td><select size="1" name="grade_id" <%if(newflag==0) out.print("disabled");%>
							onkeydown="javascript:nextKeyPress(this)" style="width: 120px">
							<%=Argument.getCrmGradeIDOptions(grade_id)%>
						</select></td>
						<td>
						</td>
					</tr>
					<tr>
						<td align="right" noWrap><%=LocalUtilis.language("class.gradeLevel4",clientLocale)%> :</td><!--等级分类-->
						<td><input name="grade_level" size="20"
							value="<%=Utility.trimNull(grade_level_new)%>" maxlength="15"
							onkeydown="javascript:nextKeyPress(this)"></td>				
					</tr>
					<tr>
						<td align="right" noWrap><%=LocalUtilis.language("class.gradeLevelName",clientLocale)%> :</td><!--等级分类说明-->
						<td><TEXTAREA name="grade_level_name"
							onkeydown="javascript:nextKeyPress(this)"
							style="width: 200px; height: 30px"><%=grade_level_name_new%></TEXTAREA></td>						
					</tr>					
					<tr>
						<td align="right"><%=LocalUtilis.language("class.dfMax",clientLocale)%> :</td><!--最大得分-->
						<td><input name="max_value" size="20"
							value="<%=Utility.trimNull(max_val_new)%>" maxlength="15"
							onkeydown="javascript:nextKeyPress(this)"></td>
					</tr>
					<tr>
						<td align="right"><%=LocalUtilis.language("class.dfMin",clientLocale)%> :</td><!--最小得分-->
						<td><input name="min_value" size="20"
							value="<%=Utility.trimNull(min_val_new)%>" maxlength="15"
							onkeydown="javascript:nextKeyPress(this)"></td>

					</tr>
					<tr>
						<td align="right"><%=LocalUtilis.language("class.gradeInfo",clientLocale)%> :</td><!--等级说明-->
						<td><TEXTAREA name="grade_info"
							onkeydown="javascript:nextKeyPress(this)"
							style="width: 200px; height: 50px"><%=grade_info_new%></TEXTAREA></td>
					</tr>					
				</table>
				<br/>
				<table border="0" width="100%">
					<tr>
						<td align="right">
						<!-- 保存 -->
						<button type="button"  class="xpbutton3" accessKey=s id="btnSave" name="btnSave"
                            onclick="javascript:if(document.theform.onsubmit()){document.theform.submit();}"><%=LocalUtilis.language("message.save",clientLocale)%> (<u>S</u>)</button>
						&nbsp;&nbsp;
						<!--取消-->
						<button type="button"  class="xpbutton3" accessKey=c id="btnCancel"
							name="btnCancel"
                            onclick="javascript:window.returnValue=null;window.close();"><%=LocalUtilis.language("message.cancel",clientLocale)%> (<u>C</u>)</button>
						&nbsp;&nbsp;</td>
					</tr>
				</table>
				</TBODY>
			</TABLE>
			</TD>
		</TR>
	</TBODY>
</TABLE>
</form>
</BODY>
</HTML>
<%gs_local.remove();%>
