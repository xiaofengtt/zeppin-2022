<%@ page contentType="text/html; charset=GBK"  import="java.util.*,java.math.*,enfo.crm.system.*,enfo.crm.vo.*, enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>

<%
GradeIndexLocal gi_local = EJBFactory.getGradIndex();
GradeIndexVO gi_vo = new GradeIndexVO();

//��õ�ַ������
Integer grade_id = Utility.parseInt(request.getParameter("grade_id"), new Integer(0));
Integer grade_id1 = Utility.parseInt(request.getParameter("grade_id1"), new Integer(0));
Integer index_id = Utility.parseInt(request.getParameter("index_id"), new Integer(0));
int newflag = Utility.parseInt(request.getParameter("newflag"), 0);

//����ύ����
Integer xh = Utility.parseInt(request.getParameter("xh"), new Integer(0));
String index_type = Utility.trimNull(request.getParameter("index_type"));
String index_type_name =
	Utility.trimNull(request.getParameter("index_type_name"));
String index_name = Utility.trimNull(request.getParameter("index_name"));
Integer value_flag =
	Utility.parseInt(request.getParameter("value_flag"), new Integer(3));
String value_unit = Utility.trimNull(request.getParameter("value_unit"));
String value_info = Utility.trimNull(request.getParameter("value_info"));
BigDecimal st_value = Utility.parseDecimal(request.getParameter("st_value"), null);
BigDecimal zb_right =
	Utility.parseDecimal(request.getParameter("zb_right"), null);
String df_gs = Utility.trimNull(request.getParameter("df_gs"));
BigDecimal df_max = Utility.parseDecimal(request.getParameter("df_max"), null);
BigDecimal df_min = Utility.parseDecimal(request.getParameter("df_min"), null);
String df_info = Utility.trimNull(request.getParameter("df_info"));
Integer valid_flag =
	Utility.parseInt(request.getParameter("valid_flag"), new Integer(1));

boolean bSuccess = false;
Map map = new HashMap();
Integer value_flag1 = new Integer(0);
String valid_flag1 = "false";
Integer valid_flag2 = new Integer(0);

if (request.getMethod().equals("POST")) {
	gi_vo.setGrade_id(grade_id);
	//����Ǳ༭����IDΪ����ֵ
	if (newflag == 0){
		gi_vo.setGrade_id(grade_id1);
	}
	gi_vo.setXh(xh);
	gi_vo.setIndex_type(index_type);
	gi_vo.setIndex_type_name(index_type_name);
	gi_vo.setIndex_name(index_name);
	gi_vo.setValue_flag(value_flag);
	gi_vo.setValue_unit(value_unit);
	gi_vo.setValue_info(value_info);
	gi_vo.setSt_value(st_value);
	gi_vo.setZb_right(zb_right);
	gi_vo.setDf_gs(df_gs);
	gi_vo.setDf_max(df_max);
	gi_vo.setDf_min(df_min);
	gi_vo.setDf_info(df_info);
	gi_vo.setInput_man(input_operatorCode);
	gi_vo.setValid_flag(valid_flag);
	gi_vo.setOp_code(input_operatorCode);

	//�½�
	if (newflag == 1)
		gi_local.appendGradeIndex(gi_vo);
	//�޸�
	else if (newflag == 0){
		gi_vo.setIndex_id(index_id);
		gi_local.modiGradeIndex(gi_vo);
	}	
	bSuccess = true;
}
else
{
	if (newflag == 0) {
		gi_vo.setGrade_id(grade_id);
		gi_vo.setIndex_id(index_id);	
		List list = gi_local.queryGradeIndex(gi_vo);//�޸�ʱ��øö���
		map = (Map)list.get(0);
		value_flag1 = new Integer(map.get("VALUE_FLAG").toString());
		valid_flag1 = map.get("VALID_FLAG").toString();
		if(valid_flag1 == "true")
			valid_flag2 = new Integer(1);
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
<title><%=LocalUtilis.language("menu.gradingRuleSet",clientLocale)%> </title><!--���ֱ�׼ϸ������-->
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>

<script language=javascript>
<%if (bSuccess) {
%>
	window.returnValue = 1;
	window.close();
<%}%>

//����֤
function validateForm(form)
{	
	if(!sl_checkChoice(form.grade_id, "<%=LocalUtilis.language("class.crmGrade",clientLocale)%> "))	return false;//������ϵ
	if(!sl_check(form.xh, "<%=LocalUtilis.language("class.serialNumber",clientLocale)%> ", 20, 1))		return false;//���
	if(!sl_check(form.index_type, "<%=LocalUtilis.language("class.typeName",clientLocale)%> ", 20, 1))		return false;//�������
	if(!sl_check(form.index_type_name, "<%=LocalUtilis.language("class.indexTypeName",clientLocale)%> ", 100, 1))		return false;//���˵��
	if(!sl_check(form.index_name, "<%=LocalUtilis.language("class.indexName",clientLocale)%> ", 60, 1))		return false;//ϸ������
	if(form.zb_right.value!=""&&new Number(form.zb_right.value)==0){
		sl_alert("<%=LocalUtilis.language("message.zbRightTip",clientLocale)%> ");//Ȩֵ����Ϊ��
	}
	var max=new Number(form.df_max.value);
	var min=new Number(form.df_min.value);
	if(max<min){
		sl_alert("<%=LocalUtilis.language("message.scoreError",clientLocale)%> ��");//���÷�С����С�÷֣�����������
		return false;
	}
	if(!sl_check(form.df_info, "<%=LocalUtilis.language("message.scoreShow",clientLocale)%> ", 100, 1))		return false;//�÷ּ���˵��
	return sl_check_update();
}
</script>
</HEAD>
<BODY class="BODY body-nox">
<form name="theform" method="POST"
	action="client_index_update.jsp"
	onsubmit="javascript:return validateForm(this);">
	<input type="hidden" name="newflag" value="<%=newflag%>">
	<input type="hidden" name="index_id" value="<%=index_id%>">
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
								<td class="page-title"><font color="#215dc6"><b><%=LocalUtilis.language("menu.gradingRuleSet",clientLocale)%> </b></font></td><!--���ֱ�׼ϸ������-->
							</tr>
						</table>
						<br/>
				<table border="0" width="100%" cellspacing="0" class="table-popup">
					<tr>
						<td align="right" noWrap><%=LocalUtilis.language("class.crmGrade",clientLocale)%> :</td><!--������ϵ-->
						<td><select size="1" name="grade_id"<%if(newflag==0) out.print("disabled");%> 
							onkeydown="javascript:nextKeyPress(this)" style="width: 120px">
							<%=Argument.getCrmGradeIDOptions(grade_id)%>
						</select></td>
						<td><INPUT type="hidden" name="grade_id1" value="<%=grade_id%>">
						</td>
					</tr>
					<tr>
						<td align="right"><%=LocalUtilis.language("class.serialNumber",clientLocale)%> :</td><!--���-->
						<td><input name="xh" size="10" <%if(newflag==0) out.print("readonly");%> value="<%=Utility.trimNull(map.get("XH"))%>" maxlength="15"
							onkeydown="javascript:nextKeyPress(this)"></td>

					</tr>
					<tr>
						<td align="right"><%=LocalUtilis.language("class.typeName",clientLocale)%> :</td><!--�������-->
						<td><input name="index_type" size="20" value="<%=Utility.trimNull(map.get("INDEX_TYPE"))%>"
							maxlength="15" onkeydown="javascript:nextKeyPress(this)"></td>
					</tr>
					<tr>
						<td align="right"><%=LocalUtilis.language("class.indexTypeName",clientLocale)%> :</td><!--���˵��-->
						<td><TEXTAREA name="index_type_name"
							onkeydown="javascript:nextKeyPress(this)"
							style="width: 250px; height: 30px"><%=Utility.trimNull(map.get("INDEX_TYPE_NAME"))%></TEXTAREA></td>
					</tr>
					<tr>
						<td align="right"><%=LocalUtilis.language("class.indexName",clientLocale)%> :</td><!--ϸ������-->
						<td><TEXTAREA name="index_name"
							onkeydown="javascript:nextKeyPress(this)"
							style="width: 250px; height: 30px"><%=Utility.trimNull(map.get("INDEX_NAME"))%></TEXTAREA></td>
					</tr>
					<tr>
						<td align="right"><%=LocalUtilis.language("class.flagName1",clientLocale)%> :</td><!--ֵ��Դ-->
						<td>
							<INPUT type="radio" name="value_flag" class="flatcheckbox"
							value="1" <%if (value_flag1.equals(new Integer(1))) {%> checked
                            <%}%>><%=LocalUtilis.language("message.manual Entry",clientLocale)%>  <!--�ֹ�¼��--><INPUT type="radio" name="value_flag"
							class="flatcheckbox" value="2"
							<%if (value_flag1.equals(new Integer(2))) {%> checked <%}%>><%=LocalUtilis.language("message.calculate",clientLocale)%>  <!--ͨ������--><INPUT
							type="radio" name="value_flag" class="flatcheckbox" value="3"
							<%if (value_flag1.equals(new Integer(3))) {%> checked <%}%>
							><%=LocalUtilis.language("message.boolean",clientLocale)%> <!--������-->
						</td>
					</tr>
					<tr>
						<td align="right"><%=LocalUtilis.language("class.valueUnit",clientLocale)%> :</td><!--ֵ��λ-->
						<td><input name="value_unit" size="10" value="<%=Utility.trimNull(map.get("VALUE_UNIT"))%>"
							maxlength="15" onkeydown="javascript:nextKeyPress(this)"></td>

					</tr>
					<tr>
						<td align="right"><%=LocalUtilis.language("class.valueInfo",clientLocale)%> :</td><!--ֵ����˵��-->
						<td><TEXTAREA name="value_info"
							onkeydown="javascript:nextKeyPress(this)"
							style="width: 250px; height: 30px"><%=Utility.trimNull(map.get("VALUE_INFO"))%></TEXTAREA></td>

					</tr>
					<tr>
						<td align="right"><%=LocalUtilis.language("class.isValid2",clientLocale)%> :</td><!--�Ƿ���Ч-->
						<td>
						<INPUT type="radio" name="valid_flag" class="flatcheckbox"
							value="1" <%if (valid_flag2.equals(new Integer(1))) {%> checked
							<%}%>><%=LocalUtilis.language("message.valid",clientLocale)%>  <!--��Ч--><INPUT type="radio" name="valid_flag"
							class="flatcheckbox" value="0"
							<%if (valid_flag2.equals(new Integer(0))) {%> checked <%}%>><%=LocalUtilis.language("message.invalid",clientLocale)%> <!--��Ч-->
						</td>
					</tr>
					<tr>
						<td align="right"><%=LocalUtilis.language("class.stValue",clientLocale)%> :</td><!--��׼ֵ-->
						<td><input name="st_value" size="10" 
							value='<%=Format.formatMoney0(Utility.stringToDouble(Utility.trimNull(map.get("ST_VALUE"))))%>'
							maxlength="15" onkeydown="javascript:nextKeyPress(this)"></td>

					</tr>
					<tr>
						<td align="right"><%=LocalUtilis.language("class.zbRight",clientLocale)%> :</td><!--Ȩֵ-->
						<td><input name="zb_right" size="10"
							value='<%=Format.formatMoney0(Utility.stringToDouble(Utility.trimNull(map.get("ST_VALUE"))))%>' maxlength="15"
							onkeydown="javascript:nextKeyPress(this)"></td>

					</tr>
					<tr>
						<td align="right"><%=LocalUtilis.language("class.dfMax",clientLocale)%> :</td><!--���÷�-->
						<td><input name="df_max" size="20"
							value='<%=Format.formatDouble(Utility.stringToDouble(Utility.trimNull(map.get("DF_MAX"))))%>' maxlength="15"
							onkeydown="javascript:nextKeyPress(this)"></td>

					</tr>
					<tr>
						<td align="right"><%=LocalUtilis.language("class.dfMin",clientLocale)%> :</td><!--��С�÷�-->
						<td><input name="df_min" size="20"
							value='<%=Format.formatDouble(Utility.stringToDouble(Utility.trimNull(map.get("DF_MIN"))))%>' maxlength="15"
							onkeydown="javascript:nextKeyPress(this)"></td>

					</tr>
					<tr>
						<td align="right"><%=LocalUtilis.language("message.scoreShow",clientLocale)%> :</td><!--�÷ּ���˵��-->
						<td><TEXTAREA name="df_info"
							onkeydown="javascript:nextKeyPress(this)"
							style="width: 250px; height: 30px"><%=Utility.trimNull(map.get("DF_INFO"))%></TEXTAREA></td>

					</tr>
					<tr>
						<td align="right"><%=LocalUtilis.language("class.dfgs",clientLocale)%> :</td><!--�÷ֹ�ʽ-->
						<td><TEXTAREA name="df_gs"
							onkeydown="javascript:nextKeyPress(this)"
							style="width: 250px; height: 50px"><%=Utility.trimNull(map.get("DF_GS"))%></TEXTAREA></td>
					</tr>					
				</table>
				<br/>
				<table border="0" width="100%">
					<tr>
						<td align="right">
						<!--����-->
						<button type="button"  class="xpbutton3" accessKey=s id="btnSave" name="btnSave"
                            onclick="javascript:if(document.theform.onsubmit()){document.theform.submit();}"><%=LocalUtilis.language("message.save",clientLocale)%> (<u>S</u>)</button>
						&nbsp;&nbsp;
						<!--ȡ��-->
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
<%gi_local.remove();%>
