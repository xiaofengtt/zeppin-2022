<%@ page contentType="text/html; charset=GBK" import="enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.webreport.*,enfo.crm.tools.*"%>
<%@ include file="/includes/operator.inc" %>

<%
int hh = 3;//����ҳ��ʼ�����꣬��COL_PARAM_HH��Ӧ
String filename = CellHelper.trimNull(request.getParameter("filename"),request.getContextPath()+"/webreport/Cells/108.CLL");
%>
<HTML>
<HEAD>
<title>�������ϵͳ</title>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<LINK HREF="/includes/default.css" TYPE="text/css" REL="stylesheet">
<LINK href="/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="0">
<SCRIPT LANGUAGE="vbscript" SRC="/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="vbscript" SRC="/includes/cellrMenu.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/default.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/report.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/celledit.js"></SCRIPT>
<SCRIPT LANGUAGE=javascript>
function openFile() 
{			
	<%@ include file="/webreport/define.inc" %>

	SingleeReport.OpenFile ("<%=filename%>","");
	rp_checkOpen(SingleeReport.GetFileName());
	
	//����������ҳ
	SingleeReport.SetCurSheet(SHEET_DEFINE);	//��ȡ��������ҳ���ڴ�
	
	if(SingleeReport.GetCurSheet() != SHEET_DEFINE)
	{
		alert("��ȡ���ݶ���ҳʧ�ܣ���ʹ�ñ༭�����CELL�ļ���");
		return false;
	}
	var paramZBArray = new Array();	//������������
	var paramFlagArray = new Array();	//���������Ӧϵͳ��־
	var defaultValueArray = new Array();	//���������Ӧϵͳ��־��Ӧ��Ĭ��ֵ
	var defaulttemp; //��ʱĬ��ֵ�������滻ϵͳ�̶�����ֵ
	var pos;    //ϵͳ��־ȡǰ�����ֵ�λ��
	//�����������Ӧ��ϵͳ������ʱ��
	while(SingleeReport.GetCellString(COL_PARAM_ZB,COL_PARAM_HH,SHEET_DEFINE)!="")
	{		
			paramZBArray[COL_PARAM_HH-3] = SingleeReport.GetCellString(COL_PARAM_ZB,COL_PARAM_HH,SHEET_DEFINE); //��ԭʼ����������飬��"c1"	
			//---------------------------------------------------------------------------------------------------------		
			pos = SingleeReport.GetCellString(COL_PARAM_FLAG,COL_PARAM_HH,SHEET_DEFINE).indexOf("-"); 
			if(pos>=0)			
				paramFlagArray[COL_PARAM_HH-3] = SingleeReport.GetCellString(COL_PARAM_FLAG,COL_PARAM_HH,SHEET_DEFINE).substring(0,pos);
			else
				paramFlagArray[COL_PARAM_HH-3] = " ";
			//---------------------------------------------------------------------------------------------------------
			defaulttemp = SingleeReport.GetCellString(COL_PARAM_DEFAULT,COL_PARAM_HH,SHEET_DEFINE).toUpperCase();
			defaulttemp = defaulttemp.replace("@@BOOKCODE","<%=input_bookCode%>");
			defaulttemp = defaulttemp.replace("@@OPERATOR","<%=input_operatorName%>");
			defaulttemp = defaulttemp.replace("@@OPERATORINT","<%=input_operatorCode%>");
			defaulttemp = defaulttemp.replace("@@DATEINT","<%=CellHelper.getDateInt()%>");
			defaulttemp = defaulttemp.replace("@@YEAR","<%=CellHelper.getYearInt()%>");
			defaulttemp = defaulttemp.replace("@@MONTH","<%=CellHelper.getMonthInt()%>");
			defaulttemp = defaulttemp.replace("@@YYYYMM","<%=CellHelper.getYMInt()%>");
			defaulttemp = defaulttemp.replace("@@DATE","<%=CellHelper.getDateCn()%>");
			defaultValueArray[COL_PARAM_HH-3] = defaulttemp;	
		COL_PARAM_HH++;
	}
	document.theform.paramFlagArray.value = paramFlagArray;
	document.theform.paramZBArray.value = paramZBArray;
	document.theform.defaultValueArray.value = defaultValueArray;
//	SingleeReport.ShowSheetLabel (0, SHEET_DEFINE);	//�����Զ�����ҳǩ		
	//----------------------------------------------------------- 
}

</SCRIPT>
</HEAD>
<BODY class="BODY" onload="javascript:openFile();init_Undo(SingleeReport);InitFontname(SingleeReport)">
<table cellSpacing="1" cellPadding="2" width="100%" align="center" border="0">
	<tr>
		<td colspan="5">
			<IMG height=28 src="/images/member.gif" width=32 align=absMiddle border=0><b><%=menu_info%></b>
		</td>
	</tr>
	<tr>
		<td colspan="10">
			<hr noshade color="#808080" size="1">
		</td>
	</tr>
</table>
<OBJECT id="SingleeReport" style="LEFT: 0px; TOP: 0px; width=100% ; height=60%;" CODEBASE="../includes/CellWeb5.CAB#Version=5,2,4,921" 
classid="clsid:3F166327-8030-4881-8BD2-EA25350E574A" VIEWASTEXT>
<PARAM NAME="_Version" VALUE="65536">
<PARAM NAME="_ExtentX" VALUE="17526">
<PARAM NAME="_ExtentY" VALUE="10774">
<PARAM NAME="_StockProps" VALUE="0">
</OBJECT>
<br><br>
<table width=700 border="0" cellpadding=0 cellspacing=0 class=font1>
	<tr>
  		<td width=330>
		<fieldset style='padding:8px;width:310px;'>
  			<legend>��Ԫ�����֣�</legend>
				<table width=300 height="135" cellpadding=0 cellspacing=0 class=font1>
  					<tr>
  						<td title="�޸���ѡ��Ԫ�����������ֺš�">
  							���壺<SELECT size=1 NAME="font_family" style='WIDTH:150px' onchange="javascript:font_familyex(SingleeReport,font_family.options(font_family.selectedIndex).value)"></SELECT>&nbsp; 
							�ֺţ�<SELECT size=1 NAME="font_size" onchange="javascript:font_sizeex(SingleeReport,font_size.options(font_size.selectedIndex).value)">
									<OPTION value="10" selected>10</OPTION>
									<OPTION value="12">12</OPTION>
									<OPTION value="14">14</OPTION>
									<OPTION value="16">16</OPTION>
									<OPTION value="18">18</OPTION>
									<OPTION value="20">20</OPTION>
									<OPTION value="22">22</OPTION>
									<OPTION value="24">24</OPTION>
								</SELECT>
						</td>
					</tr>
					<tr>
						<td title="�޸���ѡ��Ԫ��������ɫ�뱳����ɫ��">
							������ɫ��<SELECT size=1 NAME="font_color" onchange="javascript:font_colorex(SingleeReport,font_color.options(font_color.selectedIndex).value)">
											<OPTION value="0" style="COLOR:black" selected>��ɫ</OPTION>
											<OPTION value="255" style="COLOR:red">��ɫ</OPTION>
											<OPTION value="65535" style="COLOR:yellow">��ɫ</OPTION>
											<OPTION value="16711680" style="COLOR:blue">��ɫ</OPTION>
											<OPTION value="32768" style="COLOR:green">��ɫ</OPTION>
											<OPTION value="12632256" style="COLOR:gray">��ɫ</OPTION>
											<OPTION value="4227327" style="COLOR:orange">�ٻ�</OPTION>
											<OPTION value="16777215" style="COLOR:white;BACKGROUND-COLOR:black">��ɫ</OPTION>
									</SELECT>&nbsp; &nbsp; 
							������ɫ:<SELECT size=1 NAME="bg_color" onchange="javascript:bg_colorex(SingleeReport,bg_color.options(bg_color.selectedIndex).value)">
											<OPTION value="14475737" style="BACKGROUND-COLOR:#F0F0F0">���ݶ���(���)</OPTION>
											<OPTION value="14016479" style="BACKGROUND-COLOR:#F0F0F0">���ݶ���(����)</OPTION>
											<OPTION value="12632256" style="BACKGROUND-COLOR:#F0F0F0">��ѯ����</OPTION>
											<OPTION value="16777215" style="BACKGROUND-COLOR:white" selected>��ɫ</OPTION>
											<OPTION value="255" style="COLOR:white;BACKGROUND-COLOR:red">��ɫ</OPTION>
											<OPTION value="65535" style="BACKGROUND-COLOR:yellow">��ɫ</OPTION>
											<OPTION value="16744448" style="COLOR:white;BACKGROUND-COLOR:blue">��ɫ</OPTION>
											<OPTION value="65280" style="COLOR:white;BACKGROUND-COLOR:green">��ɫ</OPTION>
											<OPTION value="12632256" style="COLOR:white;BACKGROUND-COLOR:gray">��ɫ</OPTION>
											<OPTION value="4227327" style="COLOR:white;BACKGROUND-COLOR:orange">�ٻ�</OPTION>
									</SELECT>
						</td>
					</tr>
  					<tr>
  						<td title="�޸���ѡ��Ԫ��������ʽ��">
							<label for="font_b"><b>�֣�</b></label><INPUT type="checkbox" id="font_b" onclick="javascript:font_style(SingleeReport,2)"> &nbsp; &nbsp; 
							<label for="font_i"><i>б��</i></label><INPUT type="checkbox" id="font_i" onclick="javascript:font_style(SingleeReport,4)"> &nbsp; &nbsp; 
							<label for="font_u"><u>�»���</u>��</label><INPUT type="checkbox" id="font_u" onclick="javascript:font_style(SingleeReport,8)">
						</td>
					</tr>
			</table>
		</fieldset>
		</td>
		<td width="250">
		<fieldset style='padding:8px;width:230px;'>
			<legend>��Ԫ�����ԣ�</legend>
				<table width="220" height="135" cellpadding="0" cellspacing="0" class="font1">
					<tr>
						<td title="����ϣ��������С��λ����ѡ��ȷ���󽫻�ı䵱ǰ�ĵ�Ԫ���С������λ��">����С��λ��
							<input id="textDig" type="text" size="3" maxlength="2"> <input class="xpbutton3" id="bOK" onclick="javascript:bOKClick(SingleeReport);" type="button" value=" ȷ�� ">
						</td>
					</tr>
					<tr>
						<td title="����ǧ��λ�ָ�������ʾ��ʽ��">ǧ��λ�ָ���
							<SELECT id="SelectSeparator" onclick="javascript:SelectSeparatorClick(SingleeReport);" size="1" style="WIDTH: 120px">
									<OPTION selected>ȱʡ����ǧ��λ��</OPTION>
									<OPTION value="1">��ǧ��λ</OPTION>
									<OPTION value="2">�ö�������ʾ</OPTION>
									<OPTION value="3">�ÿո�����ʾ</OPTION>
							</SELECT>
						</td>
					</tr>
					<tr>
						<td class="bd" title="����ָ����Ԫ������������������ͣ�">�����������
							<SELECT id="SelectInput" onclick="javascript:SelectInputClick(SingleeReport)" size="1" style="WIDTH: 120px">
								<OPTION value="0">ȱʡ(��1ͬ)</OPTION>
								<OPTION value="1" selected>�޿���</OPTION>
								<OPTION value="2">��ֵ</OPTION>
								<OPTION value="3">����</OPTION>
								<OPTION value="4">�绰����</OPTION>
								<OPTION value="5">ֻ��</OPTION>
							</SELECT>
						</td>
					</tr>
					<tr>
						<td class="bd" title="����ָ����Ԫ�����������������">������������
							<SELECT id="SelectValidChars" onclick="javascript:SelectValidCharsClick(SingleeReport)" size="1" style="WIDTH: 120px">
								<OPTION value="0">������</OPTION>
								<OPTION value="1" selected>�κ�����</OPTION>
								<OPTION value="2">���ֺ�+,-��</OPTION>
								<OPTION value="3">��ĸ</OPTION>
								<OPTION value="4">��ĸ������</OPTION>
								<OPTION value="5">��ĸ�����»���</OPTION>
							</SELECT>
						</td>
					</tr>	
				</table>
		</fieldset>	
		</td>
		<td height="135">
		<fieldset style='padding:8px;width:200px;'>
			<legend>���뷽ʽ��</legend>
			<table width="180" height="40" cellpadding=0 cellspacing=0 class=font1>
				<tr>
					<td>ˮƽ��<SELECT size=1 NAME="align_h" onchange="javascript:font_align(SingleeReport)" style="WIDTH: 120px">
									<OPTION value="1" selected>����</OPTION>
									<OPTION value="4">����</OPTION>
									<OPTION value="2">����</OPTION>
							</SELECT>
					</td>
				</tr>
  				<tr>
  					<td>��ֱ��<SELECT size=1 NAME="align_v" onchange="javascript:font_align(SingleeReport)" style="WIDTH: 120px">
									<OPTION value="8">����</OPTION>
									<OPTION value="32">����</OPTION>
									<OPTION value="16" selected>����</OPTION>
							</SELECT>
					</td>
				</tr>
			</table>
		</fieldset>
		<fieldset style='padding:8px;width:200px;'>
			<legend>��ʾ��ʽ��</legend>
				<table width="180" height="40" cellpadding=0 cellspacing=0 class=font1>
					<tr>
						<td>
							<input type=radio id=n1 name=font_view value="1" checked onclick="javascript:font_viewex(SingleeReport,this.value)"> <label for=n1 title=�ı�������ʾ�������ȳ�����Ԫ����ʱ���������ֽ����ڵ���>��ͨ</label>
						</td>
					</tr>
					<tr>
						<td>
							<input type=radio id=n2 name=font_view value="2" onclick="javascript:font_viewex(SingleeReport,this.value)"> <label for=n2 title=������ʾ���ı��Զ����У�������ʾ��>�Զ�����</label>
						</td>
					</tr>
					<tr>
						<td>
							<input type=radio id=n3 name=font_view value="3" onclick="javascript:font_viewex(SingleeReport,this.value)"> <label for=n3 title=ʡ����ʾ���ı�������ʾ��������Ԫ���ȷ�Χ���ı�����"..."�����档>ʡ����ʾ</label>
						</td>
					</tr>
				</table>
		</fieldset>
		</td>		
	</tr>
</table>
<FORM	name="theform" method="post" action="selfdefinition.jsp">
<input type="hidden" name="saveflag" value="0">
<input type="hidden" name="filename" value="<%=filename%>">
<input type="hidden" name="paramFlagArray">
<input type="hidden" name="paramZBArray">
<input type="hidden" name="defaultValueArray">
</FORM>
<script language="javascript">
	InitFontname(SingleeReport);
</script>
</BODY>
</HTML>