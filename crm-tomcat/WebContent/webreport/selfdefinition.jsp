<%@ page contentType="text/html; charset=GBK" import="enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.webreport.*,enfo.crm.tools.*"%>
<%@ include file="/includes/operator.inc" %>

<%
int hh = 3;//定义页起始行坐标，与COL_PARAM_HH对应
String filename = CellHelper.trimNull(request.getParameter("filename"),request.getContextPath()+"/webreport/Cells/108.CLL");
%>
<HTML>
<HEAD>
<title>报表管理系统</title>
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
	
	//读参数定义页
	SingleeReport.SetCurSheet(SHEET_DEFINE);	//读取参数定义页到内存
	
	if(SingleeReport.GetCurSheet() != SHEET_DEFINE)
	{
		alert("获取数据定义页失败，请使用编辑器检查CELL文件！");
		return false;
	}
	var paramZBArray = new Array();	//参数坐标数组
	var paramFlagArray = new Array();	//参数坐标对应系统标志
	var defaultValueArray = new Array();	//参数坐标对应系统标志对应的默认值
	var defaulttemp; //临时默认值，用来替换系统固定参数值
	var pos;    //系统标志取前面数字的位置
	//当参数坐标对应有系统参数的时候
	while(SingleeReport.GetCellString(COL_PARAM_ZB,COL_PARAM_HH,SHEET_DEFINE)!="")
	{		
			paramZBArray[COL_PARAM_HH-3] = SingleeReport.GetCellString(COL_PARAM_ZB,COL_PARAM_HH,SHEET_DEFINE); //将原始定义存入数组，如"c1"	
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
//	SingleeReport.ShowSheetLabel (0, SHEET_DEFINE);	//隐藏自定义表的页签		
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
  			<legend>单元格文字：</legend>
				<table width=300 height="135" cellpadding=0 cellspacing=0 class=font1>
  					<tr>
  						<td title="修改所选单元格文字体与字号。">
  							字体：<SELECT size=1 NAME="font_family" style='WIDTH:150px' onchange="javascript:font_familyex(SingleeReport,font_family.options(font_family.selectedIndex).value)"></SELECT>&nbsp; 
							字号：<SELECT size=1 NAME="font_size" onchange="javascript:font_sizeex(SingleeReport,font_size.options(font_size.selectedIndex).value)">
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
						<td title="修改所选单元格文字颜色与背景颜色。">
							文字颜色：<SELECT size=1 NAME="font_color" onchange="javascript:font_colorex(SingleeReport,font_color.options(font_color.selectedIndex).value)">
											<OPTION value="0" style="COLOR:black" selected>黑色</OPTION>
											<OPTION value="255" style="COLOR:red">红色</OPTION>
											<OPTION value="65535" style="COLOR:yellow">黄色</OPTION>
											<OPTION value="16711680" style="COLOR:blue">蓝色</OPTION>
											<OPTION value="32768" style="COLOR:green">绿色</OPTION>
											<OPTION value="12632256" style="COLOR:gray">灰色</OPTION>
											<OPTION value="4227327" style="COLOR:orange">橘黄</OPTION>
											<OPTION value="16777215" style="COLOR:white;BACKGROUND-COLOR:black">白色</OPTION>
									</SELECT>&nbsp; &nbsp; 
							背景颜色:<SELECT size=1 NAME="bg_color" onchange="javascript:bg_colorex(SingleeReport,bg_color.options(bg_color.selectedIndex).value)">
											<OPTION value="14475737" style="BACKGROUND-COLOR:#F0F0F0">数据定义(青灰)</OPTION>
											<OPTION value="14016479" style="BACKGROUND-COLOR:#F0F0F0">数据定义(暗灰)</OPTION>
											<OPTION value="12632256" style="BACKGROUND-COLOR:#F0F0F0">查询条件</OPTION>
											<OPTION value="16777215" style="BACKGROUND-COLOR:white" selected>白色</OPTION>
											<OPTION value="255" style="COLOR:white;BACKGROUND-COLOR:red">红色</OPTION>
											<OPTION value="65535" style="BACKGROUND-COLOR:yellow">黄色</OPTION>
											<OPTION value="16744448" style="COLOR:white;BACKGROUND-COLOR:blue">蓝色</OPTION>
											<OPTION value="65280" style="COLOR:white;BACKGROUND-COLOR:green">绿色</OPTION>
											<OPTION value="12632256" style="COLOR:white;BACKGROUND-COLOR:gray">灰色</OPTION>
											<OPTION value="4227327" style="COLOR:white;BACKGROUND-COLOR:orange">橘黄</OPTION>
									</SELECT>
						</td>
					</tr>
  					<tr>
  						<td title="修改所选单元格文字样式。">
							<label for="font_b"><b>粗：</b></label><INPUT type="checkbox" id="font_b" onclick="javascript:font_style(SingleeReport,2)"> &nbsp; &nbsp; 
							<label for="font_i"><i>斜：</i></label><INPUT type="checkbox" id="font_i" onclick="javascript:font_style(SingleeReport,4)"> &nbsp; &nbsp; 
							<label for="font_u"><u>下划线</u>：</label><INPUT type="checkbox" id="font_u" onclick="javascript:font_style(SingleeReport,8)">
						</td>
					</tr>
			</table>
		</fieldset>
		</td>
		<td width="250">
		<fieldset style='padding:8px;width:230px;'>
			<legend>单元格属性：</legend>
				<table width="220" height="135" cellpadding="0" cellspacing="0" class="font1">
					<tr>
						<td title="输入希望保留的小数位数，选择确定后将会改变当前的单元格的小数保留位数">保留小数位数
							<input id="textDig" type="text" size="3" maxlength="2"> <input class="xpbutton3" id="bOK" onclick="javascript:bOKClick(SingleeReport);" type="button" value=" 确定 ">
						</td>
					</tr>
					<tr>
						<td title="设置千分位分隔符的显示方式．">千分位分隔符
							<SELECT id="SelectSeparator" onclick="javascript:SelectSeparatorClick(SingleeReport);" size="1" style="WIDTH: 120px">
									<OPTION selected>缺省（无千分位）</OPTION>
									<OPTION value="1">无千分位</OPTION>
									<OPTION value="2">用逗号来表示</OPTION>
									<OPTION value="3">用空格来表示</OPTION>
							</SELECT>
						</td>
					</tr>
					<tr>
						<td class="bd" title="设置指定单元格允许输入的数据类型．">设置输入控制
							<SELECT id="SelectInput" onclick="javascript:SelectInputClick(SingleeReport)" size="1" style="WIDTH: 120px">
								<OPTION value="0">缺省(与1同)</OPTION>
								<OPTION value="1" selected>无控制</OPTION>
								<OPTION value="2">数值</OPTION>
								<OPTION value="3">整数</OPTION>
								<OPTION value="4">电话号码</OPTION>
								<OPTION value="5">只读</OPTION>
							</SELECT>
						</td>
					</tr>
					<tr>
						<td class="bd" title="设置指定单元格中允许输入的内容">允许输入类型
							<SELECT id="SelectValidChars" onclick="javascript:SelectValidCharsClick(SingleeReport)" size="1" style="WIDTH: 120px">
								<OPTION value="0">不控制</OPTION>
								<OPTION value="1" selected>任何数字</OPTION>
								<OPTION value="2">数字和+,-号</OPTION>
								<OPTION value="3">字母</OPTION>
								<OPTION value="4">字母和数字</OPTION>
								<OPTION value="5">字母数字下划线</OPTION>
							</SELECT>
						</td>
					</tr>	
				</table>
		</fieldset>	
		</td>
		<td height="135">
		<fieldset style='padding:8px;width:200px;'>
			<legend>对齐方式：</legend>
			<table width="180" height="40" cellpadding=0 cellspacing=0 class=font1>
				<tr>
					<td>水平：<SELECT size=1 NAME="align_h" onchange="javascript:font_align(SingleeReport)" style="WIDTH: 120px">
									<OPTION value="1" selected>居左</OPTION>
									<OPTION value="4">居中</OPTION>
									<OPTION value="2">居右</OPTION>
							</SELECT>
					</td>
				</tr>
  				<tr>
  					<td>垂直：<SELECT size=1 NAME="align_v" onchange="javascript:font_align(SingleeReport)" style="WIDTH: 120px">
									<OPTION value="8">居上</OPTION>
									<OPTION value="32">居中</OPTION>
									<OPTION value="16" selected>居下</OPTION>
							</SELECT>
					</td>
				</tr>
			</table>
		</fieldset>
		<fieldset style='padding:8px;width:200px;'>
			<legend>显示方式：</legend>
				<table width="180" height="40" cellpadding=0 cellspacing=0 class=font1>
					<tr>
						<td>
							<input type=radio id=n1 name=font_view value="1" checked onclick="javascript:font_viewex(SingleeReport,this.value)"> <label for=n1 title=文本单行显示，当长度超过单元格宽度时，超过部分将被遮挡。>普通</label>
						</td>
					</tr>
					<tr>
						<td>
							<input type=radio id=n2 name=font_view value="2" onclick="javascript:font_viewex(SingleeReport,this.value)"> <label for=n2 title=多行显示，文本自动换行，多行显示。>自动换行</label>
						</td>
					</tr>
					<tr>
						<td>
							<input type=radio id=n3 name=font_view value="3" onclick="javascript:font_viewex(SingleeReport,this.value)"> <label for=n3 title=省略显示，文本单行显示，超出单元格宽度范围的文本将被"..."所代替。>省略显示</label>
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