<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.webreport.*,enfo.crm.tools.*"%>
<%@ include file = "/includes/operator.inc" %>
<%
int hh = 3;//����ҳ��ʼ�����꣬��COL_PARAM_HH��Ӧ
String filename = CellHelper.trimNull(request.getParameter("filename"),request.getContextPath()+"/webreport/Cells/108.CLL");
%>
<html>

<head>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META name=VI60_defaultClientScript content=VBScript>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<meta name="GENERATOR" content="Microsoft FrontPage 4.0">
<meta name="ProgId" content="FrontPage.Editor.Document">

<!--Style Sheets First one to adjust fonts on input fields.-->

<LINK HREF="/includes/default.css" TYPE="text/css" REL="stylesheet">
<SCRIPT LANGUAGE="vbscript" SRC="/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/default.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/report.js"></SCRIPT>
<SCRIPT  LANGUAGE=VBSCRIPT src="eSReport/control/function.vbs"></SCRIPT>
<SCRIPT  LANGUAGE=JAVASCRIPT src="eSReport/control/buttons.js"></SCRIPT>

<title>����༭</title>
<SCRIPT LANGUAGE=javascript>
<%@ include file="/webreport/define.inc" %>
function openFile() 
{			
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
<SCRIPT LANGUAGE=javascript>
<!--
function InitFontname(){
	strFontnames = SingleeReport.GetDisplayFontnames();
	var arrFontname = strFontnames.split('|');
	arrFontname.sort();
	var i;
	var sysFont;
	if( SingleeReport.GetSysLangID () == 2052)
		sysFont = "����";
	else sysFont = "Arial";
		

	for( i =0; i < arrFontname.length;i++ ){
		var oOption = document.createElement("OPTION");
		FontNameSelect.options.add(oOption);
		oOption
		oOption.innerText = arrFontname[i];
		oOption.value = arrFontname[i];
		if( arrFontname[i] == sysFont ) oOption.selected = true;
	}
}

function setPrint(flag)
{	
	SingleeReport.PrintSetOrient(0)
	//���ñ�ͷ��ҳü	
	var printRow = COL_PARAM_HH; //ȡ����ֵ��������
	var leftValue,rightValue;
	while(SingleeReport.GetCellString(COL_PRINT_NAME,printRow,SHEET_DEFINE)!="")
	{	
		leftValue = SingleeReport.GetCellString(COL_PRINT_NAME,printRow,SHEET_DEFINE);		
		rightValue = SingleeReport.GetCellString(COL_PRINT_PARAM,printRow,SHEET_DEFINE);
		printLeftRight(SingleeReport,leftValue,rightValue);		
		printRow++;		
	}	
	//���ô�ӡ��ֽ����,ʮ��֮һ����	
	var cols = SingleeReport.GetCols(SHEET_OUTPUT);	
	var total_width = 0;
	for(var i=1;i<=cols;i++)
	{
	     total_width = SingleeReport.GetColWidth(0,i,SHEET_OUTPUT) + parseInt(total_width);
	}

	if(total_width>SingleeReport.PrintGetPaperWidth(SHEET_OUTPUT))
		SingleeReport.PrintSetOrient(1);
	else
		SingleeReport.PrintSetOrient(0);
	SingleeReport.PrintSetPaper(9);
		
	if(flag==1)		
		PrintPreview();
	else
		PrintReport();	
}
//*/
function window_onresize() {
	var lWidth = document.body.offsetWidth;
	if( lWidth <= 0) lWidth = 1;
	SingleeReport.style.width = lWidth;
	Menu1.style.width = lWidth;

	var lHeight = document.body.offsetHeight - parseInt(SingleeReport.style.top);
	if( lHeight <= 0 ) lHeight = 1;
	SingleeReport.style.height = lHeight;
}

function window_onload() {
	Menu1.style.left = 0;
	Menu1.style.top = 0;

	SingleeReport.EnableUndo(true);
	SingleeReport.Mergecell = true;
	SingleeReport.border = 0;
	SingleeReport.style.left = 0;	
	
	SingleeReport.style.top = idTBDesign.offsetTop + idTBDesign.offsetHeight;
	var lWidth = document.body.offsetWidth;
	if( lWidth <= 0) lWidth = 1;
	SingleeReport.style.width = lWidth;
	Menu1.style.width = lWidth;

	
	var lHeight = document.body.offsetHeight - parseInt(SingleeReport.style.top);
	if( lHeight <= 0 ) lHeight = 1;
	SingleeReport.style.height = lHeight;

	var href = window.location.pathname;
	href = unescape(href);
	start = href.indexOf("/");
	end = href.lastIndexOf("\\");
	href = href.substring(start + 1, end + 1);
	href = href + "emenu.clm";	
	//href = "http://www.cellsoft.cc/china/chanpin/jiazu/eSReport/emenu.clm";
	//alert(href);
	Menu1.ReadMenuFromRemoteFile('emenu.clm');
	
	SingleeReport.style.display="";
	//InitFontname();
}

//-->
</SCRIPT>	

<!--BUTTON-->
<SCRIPT FOR="cbButton" EVENT="onmousedown()"	LANGUAGE="JavaScript" >
	return onCbMouseDown(this);
</SCRIPT>

<SCRIPT FOR="cbButton" EVENT="onclick()"		LANGUAGE="JavaScript" >
	return onCbClickEvent(this);
</SCRIPT>

<SCRIPT FOR="cbButton" EVENT="oncontextmenu()"	LANGUAGE="JavaScript" >
	return(event.ctrlKey);
</SCRIPT>

<SCRIPT ID=clientEventHandlersVBS LANGUAGE=vbscript>
<!--
sub Menu1_ClickMenuItem( byval name)
	name = ucase(name)
	select case name
	case "FILENEW"
	   mnuFileNew_click
	case "FILEOPEN"
	   mnuFileOpen_click
	case "FILEWEBOPEN"
	   mnuFileWebOpen_click
	case "FILESAVE"
	   mnuFileSave_click
	case "FILESHEETSAVEAS"
	   mnuFileSheetSaveAs_click
	case "FILEIMPORTTEXT"
	   mnuFileImportText_click
	case "FILEIMPORTCSV"
	   mnuFileImportCSV_click      
	case "FILEIMPORTEXCEL"
	   mnuFileImportExcel_click
	case "FILEEXPORTTEXT"
	   mnuFileExportText_click
	case "FILEEXPORTCSV"
	   mnuFileExportCSV_click   
	case "FILEEXPORTEXCEL"
	   mnuFileExportExcel_click
	case "FILEEXPORTPDF"
	   mnuFileExportPDF_click   
	case "FILEPAGESETUP"
	   mnuFilePageSetup_click
	case "FILEPRINTPREVIEW"
	   mnuFilePrintPreview_click
	case "FILEPRINT"
	   mnuFilePrint_click
	case "FILEEXIT"
	   mnuFileExit_click
	case "EDITUNDO"
	   mnuEditUndo_click
	case "EDITREDO"
	   mnuEditRedo_click
	case "EDITCUT"
	   mnuEditCut_click
	case "EDITCOPY"
	   mnuEditCopy_click
	case "EDITPASTE"
	   mnuEditPaste_click
	case "EDITPASTESPECIAL"
	   mnuEditPasteSpecial_Click 
	case "EDITFIND"
	   mnuEditFind_click
	case "EDITREPLACE"
	   mnuEditReplace_click
	case "EDITGOTO"
	   mnuEditGoto_click
	case "EDITSELECTALL"
	   mnuEditSelectAll_click
	   flag = Menu1.GetMenuItemCheck( "EditSelectAll" )
	   menu1.SetMenuItemCheck "EditSelectAll" ,flag
	case "EDITFILLV"
	   mnuEditFillV_click
	case "EDITINSERTSPECHAR"
	   mnuEditInsertSpeChar_click
	case "EDITHYPERLINK"
	   mnuEditHyperlink_click
	case "VIEWFREEZED"
	   mnuViewFreezed_click
	case "VIEWSHEETLABEL"
	   mnuViewSheetLabel_click
	   flag = Menu1.GetMenuItemCheck( "ViewSheetLabel" )
	   menu1.SetMenuItemCheck "ViewSheetLabel" ,flag
	case "VIEWROWLABEL"
	   mnuViewRowLabel_click
	   flag = Menu1.GetMenuItemCheck( "ViewRowLabel" )
	   menu1.SetMenuItemCheck "ViewRowLabel" ,flag
	case "VIEWCOLLABEL"
	   mnuViewColLabel_click
	   flag = Menu1.GetMenuItemCheck( "ViewColLabel" )
	   menu1.SetMenuItemCheck "ViewColLabel" ,flag
	case "VIEWHSCROLL"
	   mnuViewHScroll_click
	   flag = Menu1.GetMenuItemCheck( "ViewHScroll" )
	   menu1.SetMenuItemCheck "ViewHScroll" ,flag
	case "VIEWVSCROLL"
	   mnuViewVScroll_click
	   flag = Menu1.GetMenuItemCheck( "ViewVScroll" )
	   menu1.SetMenuItemCheck "ViewVScroll" ,flag
	case "FORMATCELLPROPERTY"
	   mnuFormatCellProperty_click
	case "FORMATDRAWBORDER"
	   mnuFormatDrawborder_click
	case "FORMATINSERTPIC"
	   mnuFormatInsertPic_click
	case "FORMATREMOVEPIC"
	   mnuFormatRemovePic_click
	case "FORMATMERGECELL"
	   mnuFormatMergeCell_click
	case "FORMATUNMERGECELL"
	   mnuFormatUnMergeCell_click
	case "ROWINSERT"
	   mnuRowInsert_click
	case "ROWDELETE"
	   mnuRowDelete_click
	case "ROWAPPEND"
	   mnuRowAppend_click
    case "ROWHEIGHT"
       mnuRowHeight_click
    case "ROWHIDE"
       mnuRowHide_click
    case "ROWUNHIDE"
       mnuRowUnhide_click
    case "ROWBESTHEIGHT"
       mnuRowBestHeight_click
    case "COLINSERT"
       mnuColInsert_click
    case "COLDELETE"
       mnuColDelete_click
    case "COLAPPEND"
       mnuColAppend_click
    case "COLWIDTH"
       mnuColWidth_click
    case "COLHIDE"
       mnuColHide_click
    case "COLUNHIDE"
       mnuColUnhide_click
    case "COLBESTWIDTH"
       mnuColBestWidth_click
    case "SHEETRENAME"
       mnuSheetRename_click
    case "SHEETSIZE"
       mnuSheetSize_click
    case "SHEETPROPTECT"
       mnuSheetProptect_click
    case "SHEETINSERT"
       mnuSheetInsert_click
    case "SHEETDELETE"
       mnuSheetDelete_click
    case "SHEETAPPEND"
       mnuSheetAppend_click
    case "SHEETSORTSTYLE"
       mnuSheetSortStyle_click
    case "SHEETSORTVALUE"
       mnuSheetSortValue_click
    case "FORMULAINPUT"
       mnuFormulaInput_click
    case "FORMULABATCHINPUT"
       mnuFormulaBatchInput_click
    case "FORMULASERIAL"
       mnuFormulaSerial_click
    case "FORMULACELLSHOW"
       mnuFormulaCellShow_click
    case "FORMULACELLCOLOR"
       mnuFormulaCellColor_click
    case "FORMULALIST"
       mnuFormulaList_click
    case "FORMULARECALC"
       mnuFormulaReCalc_click
    case "USERFUNCDEFINE"
       mnuUserFuncDefine_click 
    case "USERFUNCADD"
       mnuUserFuncAdd_click
    case "USERFUNCDELETE"
       mnuUserFuncDelete   
    case "USERFUNCMODIFY"
       mnuUserFuncModify_click
    case "DATARANGEROTATE"
       mnuDataRangeRotate_click
    case "DATARANGEBLANCE"
       mnuDataRangeBlance_click
    case "DATARANGESORT"
       mnuDataRangeSort_click
    case "DATARANGECLASSSUM"
       mnuDataRangeClassSum_click
    case "DATARANGEQUERY"
       mnuDataRangeQuery_click
    case "DATARANGE3DEASYSUM"
       mnuDataRange3DEasySum_click
    case "DATARANGE3DSUM"
       mnuDataRange3DSum_click
    case "DATARANGE3DVIEW"
       mnuDataRange3DView_click
    case "DATARANGE3DQUERY"
       mnuDataRange3DQuery_click
    case "DATAWZDBARCODE"
       mnuDataWzdBarcode_click
    case "DATAWZDCHART"
       mnuDataWzdChart_click
    case "MENUADDROWCOMPAGE"
       menuAddRowCompage_click
    case "MENUDELROWCOMPAGE"
       menuDelRowCompage_click
    case "MENUADDCOLCOMPAGE"
       menuAddColCompage_click
    case "MENUDELCOLCOMPAGE"
       menuDelColCompage_click
    case "MENUDELALLCOMPAGE"
       menuDelAllCompage_click
    case "DEFINEVARDLG"
       menuDefineVarDlg_click
    end select
		
	
End Sub

-->
</SCRIPT>
</HEAD>
<BODY id="mainbody" class="mainBody" LANGUAGE=javascript onresize="return window_onresize()" onload="javascript:openFile();InitFontname(SingleeReport);window_onload();">
<OBJECT id=Menu1 style="LEFT: 0px; WIDTH: 927px; TOP: -1px; HEIGHT: 19px" 
height=19 width=927 classid=clsid:F82DB98D-842D-4DAB-9312-E478D8255720 CODEBASE="eSReport/Menu.ocx#version=1,0,0,1"><PARAM NAME="_Version" VALUE="65536"><PARAM NAME="_ExtentX" VALUE="24527"><PARAM NAME="_ExtentY" VALUE="503"><PARAM NAME="_StockProps" VALUE="0"></OBJECT>
<!--Top Toolbar-->
<TABLE class="cbToolbar" id="idTBGeneral" cellpadding='2' cellspacing='1' width="100%">
	<TR>
	<TD NOWRAP><A class=tbButton id=cmdFileNew title=�½� href="#" name=cbButton><IMG align=absMiddle src="eSReport/general/new.gif" width="16" height="16"></A></TD>
	<TD NOWRAP><A class=tbButton id=cmdOpen title=�򿪱����ĵ� href="#" name=cbButton><IMG align=absMiddle src="eSReport/general/open.gif" width="16" height="16"></A></TD>
	<TD NOWRAP><A class=tbButton id=cmdFileOpen title=��Զ���ĵ� href="#" name=cbButton><IMG align=absMiddle src="eSReport/general/openweb.gif" width="16" height="16"></A></TD>
	<TD NOWRAP><A class=tbButton id=cmdFileSave title=���� href="#" name=cbButton><IMG align=absMiddle src="eSReport/general/save.gif" width="16" height="16"></A></TD>
	<TD NOWRAP><A class=tbButton id=cmdFilePrint title=��ӡ href="#" name=btnPrintReport onclick="javascript:setPrint(0);"><IMG align=absMiddle src="eSReport/general/print.gif" width="16" height="16"></A></TD>
	<TD class="tbDivider" NOWRAP><A class=tbButton id=cmdFilePrintPreview title=��ӡԤ�� href="#" name=btnPreview onclick="javascript:setPrint(1);"><IMG align=absMiddle src="eSReport/general/printpreview.gif" width="16" height="16"></A></TD>
	<TD NOWRAP><A class=tbButton id=cmdEditCut title=���� href="#" name=cbButton><IMG align=absMiddle src="eSReport/general/cut.gif" width="16" height="16"></A></TD>
	<TD NOWRAP><A class=tbButton id=cmdEditCopy title=���� href="#" name=cbButton><IMG align=absMiddle src="eSReport/general/copy.gif" width="16" height="16"></A></TD>
	<TD NOWRAP><A class=tbButton id=cmdEditPaste title=ճ�� href="#" name=cbButton><IMG align=absMiddle src="eSReport/general/paste.gif" width="16" height="16"></A></TD>
	<TD class="tbDivider" NOWRAP><A class=tbButton id=cmdEditFind title=���� href="#" name=cbButton><IMG align=absMiddle src="eSReport/general/find.gif" width="16" height="16"></A></TD>
	<TD NOWRAP><A class=tbButton id=cmdEditUndo title=���� href="#" name=cbButton><IMG align=absMiddle src="eSReport/general/undo.gif" width="16" height="16"></A></TD>
	<TD class="tbDivider" NOWRAP><A class=tbButton id=cmdEditRedo title=���� href="#" name=cbButton  sticky="true"><IMG align=absMiddle src="eSReport/general/redo.gif" width="16" height="16"></A></TD>
	<TD NOWRAP><A class=tbButton id=cmdViewFreeze title=���������� href="#" name=cbButton><IMG align=absMiddle src="eSReport/general/freeze.gif" width="16" height="16"></A></TD>
	<TD NOWRAP><A class=tbButton id=cmdFormatPainter title=��ʽˢ href="#" name=cbButton><IMG align=absMiddle src="eSReport/general/painter.gif" width="16" height="16"></A></TD>
	<TD NOWRAP><A class=tbButton id=cmdSortAscending title=�������� href="#" name=cbButton><IMG align=absMiddle src="eSReport/general/sorta.gif" width="16" height="16"></A></TD>
	<TD class="tbDivider" NOWRAP><A class=tbButton id=cmdSortDescending title=�������� href="#" name=cbButton><IMG align=absMiddle src="eSReport/general/sortd.gif" width="16" height="16"></A></TD>
	<TD NOWRAP><A class=tbButton id=cmdFormulaInput title=���빫ʽ href="#" name=cbButton><IMG align=absMiddle src="eSReport/general/formula.gif" width="16" height="16"></A></TD>
	<TD class="tbDivider" NOWRAP><A class=tbButton id=cmdFormulaSerial title=��䵥Ԫ��ʽ���� href="#" name=cbButton><IMG align=absMiddle src="eSReport/general/formulaS.gif" width="16" height="16"></A></TD>
	<TD NOWRAP><A class=tbButton id=cmdFormulaSumH title=ˮƽ��� href="#" name=cbButton><IMG align=absMiddle src="eSReport/design/sumh.gif" width="16" height="16"></A></TD>
	<TD NOWRAP><A class=tbButton id=cmdFormulaSumV title=��ֱ��� href="#" name=cbButton><IMG align=absMiddle src="eSReport/design/sumv.gif" width="16" height="16"></A></TD>
	<TD class="tbDivider" NOWRAP><A class=tbButton id=cmdFormulaSumHV title=˫����� href="#" name=cbButton><IMG align=absMiddle src="eSReport/design/sum.gif" width="16" height="16"></A></TD>
	<TD NOWRAP><A class=tbButton id=cmdChartWzd title=ͼ���� href="#" name=cbButton><IMG align=absMiddle src="eSReport/general/chartw.gif" width="16" height="16"></A></TD>
	<TD NOWRAP><A class=tbButton id=cmdInsertPic title=����ͼƬ href="#" name=cbButton><IMG align=absMiddle src="eSReport/design/insertpic.gif" width="16" height="16"></A></TD>
	<TD NOWRAP><A class=tbButton id=cmdHyperlink title=�������� href="#" name=cbButton><IMG align=absMiddle src="eSReport/design/hyperlink.gif" width="16" height="16"></A></TD>
	<TD class="tbDivider" NOWRAP><A class=tbButton id=cmdWzdBarcode title=�������� href="#" name=cbButton><IMG align=absMiddle src="eSReport/design/barcode.gif" width="16" height="16"></A></TD>

	<TD class="tbDivider" NOWRAP id="cmdViewScale" Title="��ʾ����">
		<SELECT name="viewScaleSelect" style="WIDTH: 89px; HEIGHT: 23px" onChange="changeViewScale(viewScaleSelect.value)" ACCESSKEY="v" size="1" 
>
          <option value="200">200%</option>
          <option value="150">150%</option>
          <option value="120">120%</option>
          <option value="110">110%</option>
          <option selected value="100">100%</option>
          <option value="90">90%</option>
          <option value="80">80%</option>
          <option value="70">70%</option>
          <option value="50">50%</option>
          <option value="30">30%</option>
          <option value="20">20%</option>
          <option value="15">15%</option>
          <option value="10">10%</option>
          <option value="5">5%</option>
          <option value="3">3%</option>
          <option value="1">1%</option>
        </SELECT>
	</TD>


	<TD NOWRAP><A class=tbButton id=cmdShowGridline title=��ʾ/���ر��������� href="#" name=cbButton><IMG align=absMiddle src="eSReport/general/gridline.gif" width="16" height="16"></A></TD>
	<TD NOWRAP><A class=tbButton id=cmdVPagebreak title=��ֱ�ָ��� href="#" name=cbButton><IMG align=absMiddle src="eSReport/general/vpagebreak.gif" width="16" height="16"></A></TD>
	<TD NOWRAP><A class=tbButton id=cmdHPagebreak title=ˮƽ�ָ��� href="#" name=cbButton><IMG align=absMiddle src="eSReport/general/hpagebreak.gif" width="16" height="16"></A></TD>
	<TD class="tbDivider" NOWRAP><A class=tbButton id=cmdShowPagebreak title=��ʾ/���طָ��� href="#" name=cbButton><IMG align=absMiddle src="eSReport/general/pagebreak.gif" width="16" height="16"></A></TD>
	<!--<TD class="tbDivider" NOWRAP><A class=tbButton id=cmdAbout title=���ڻ����� href="#" name=cbButton><IMG align=absMiddle src="eSReport/general/about.gif" width="16" height="16"></A></TD>-->
	<TD NOWRAP width="100%"></TD>
	</TR>
</TABLE>
<TABLE class="cbToolbar" id="idTBFormat" cellpadding='0' cellspacing='0' width="100%">
	<TR>
	<TD NOWRAP id="cmdFontName" Title="����">
		<SELECT name="FontNameSelect" style="WIDTH: 225px; HEIGHT: 23px" onChange="changeFontName(FontNameSelect.value)" ACCESSKEY="v" size="1">
        &nbsp; </SELECT>
	</TD>
	<TD NOWRAP class="tbDivider" id="cmdFontSize" Title="�ֺ�">
		<SELECT name="FontSizeSelect" style="WIDTH: 67px; HEIGHT: 23px" onChange="changeFontSize(FontSizeSelect.value)" ACCESSKEY="v" size="1">
          <option value="5">5</option>
          <option value="6">6</option>
          <option value="7">7</option>
          <option value="8">8</option>
          <option value="9">9</option>
          <option selected value="10">10</option>
          <option value="11">11</option>
          <option value="12">12</option>
          <option value="14">14</option>
          <option value="16">16</option>
          <option value="18">18</option>
          <option value="20">20</option>
          <option value="22">22</option>
          <option value="24">24</option>
          <option value="26">26</option>
          <option value="28">28</option>
          <option value="30">30</option>
          <option value="36">36</option>
          <option value="42">42</option>
          <option value="48">48</option>
          <option value="72">72</option>
          <option value="100">100</option>
          <option value="150">150</option>
          <option value="300">300</option>
          <option value="500">500</option>
          <option value="800">800</option>
          <option value="1200">1200</option>
          <option value="2000">2000</option>
        </SELECT>
	</TD>

	<TD NOWRAP><A class=tbButton id=cmdBold title=���� href="#" name=cbButton><IMG align=absMiddle src="eSReport/format/bold.gif" width="16" height="16"></A></TD>
	<TD NOWRAP><A class=tbButton id=cmdItalic title=б�� href="#" name=cbButton><IMG align=absMiddle src="eSReport/format/italic.gif" width="16" height="16"></A></TD>
	<TD NOWRAP><A class=tbButton id=cmdUnderline title=�»��� href="#" name=cbButton><IMG align=absMiddle src="eSReport/format/underline.gif" width="16" height="16"></A></TD>
	<TD NOWRAP><A class=tbButton id=cmdBackColor title=����ɫ href="#" name=cbButton><IMG align=absMiddle src="eSReport/format/backcolor.gif" width="16" height="16"></A></TD>
	<TD class="tbDivider" NOWRAP><A class=tbButton id=cmdForeColor title=ǰ��ɫ href="#" name=cbButton><IMG align=absMiddle src="eSReport/format/forecolor.gif" width="16" height="16"></A></TD>
	<TD NOWRAP><A class=tbButton id=cmdWordWrap title=�Զ����� href="#" name=cbButton><IMG align=absMiddle src="eSReport/format/wordwrap.gif" width="16" height="16"></A></TD>
	<TD NOWRAP><A class=tbButton id=cmdAlignLeft title=������� href="#" name=cbButton><IMG align=absMiddle src="eSReport/format/alignleft.gif" width="16" height="16"></A></TD>
	<TD NOWRAP><A class=tbButton id=cmdAlignCenter title=���ж��� href="#" name=cbButton><IMG align=absMiddle src="eSReport/format/aligncenter.gif" width="16" height="16"></A></TD>
	<TD NOWRAP><A class=tbButton id=cmdAlignRight title=���Ҷ��� href="#" name=cbButton><IMG align=absMiddle src="eSReport/format/alignright.gif" width="16" height="16"></A></TD>
	<TD NOWRAP><A class=tbButton id=cmdAlignTop title=���϶��� href="#" name=cbButton><IMG align=absMiddle src="eSReport/format/aligntop.gif" width="16" height="16"></A></TD>
	<TD NOWRAP><A class=tbButton id=cmdAlignMiddle title=��ֱ���� href="#" name=cbButton  sticky="true"><IMG align=absMiddle src="eSReport/format/alignmiddle.gif" width="16" height="16"></A></TD>
	<TD class="tbDivider" NOWRAP><A class=tbButton id=cmdAlignBottom title=���¶��� href="#" name=cbButton><IMG align=absMiddle src="eSReport/format/alignbottom.gif" width="16" height="16"></A></TD>
	<TD NOWRAP id="cmdBoderType" Title="�߿�����">
		<SELECT name="BorderTypeSelect" style="WIDTH: 109px; HEIGHT: 23px" ACCESSKEY="v" size="1" 
     >
          <option value="2" selected>ϸ��</option>
          <option value="3">����</option>
          <option value="4">����</option>
          <option value="5">����</option>
          <option value="6">����</option>
          <option value="7">�㻮��</option>
          <option value="8">��㻮��</option>
          <option value="9">�ֻ���</option>
          <option value="10">�ֵ���</option>
          <option value="11">�ֵ㻮��</option>
          <option value="12">�ֵ�㻮��</option>
          
        </SELECT>
	</TD>
	<TD NOWRAP><A class=tbButton id=cmdDrawBorder title=���߿��� href="#" name=cbButton><IMG align=absMiddle src="eSReport/format/border.gif" width="16" height="16"></A></TD>
	<TD NOWRAP><A class=tbButton id=cmdEraseBorder title=Ĩ�߿��� href="#" name=cbButton><IMG align=absMiddle src="eSReport/format/erase.gif" width="16" height="16"></A></TD>
	<TD NOWRAP><A class=tbButton id=cmdCurrency title=���ҷ��� href="#" name=cbButton><IMG align=absMiddle src="eSReport/format/currency.gif" width="16" height="16"></A></TD>
	<TD NOWRAP><A class=tbButton id=cmdPercent title=�ٷֺ� href="#" name=cbButton><IMG align=absMiddle src="eSReport/format/percent.gif" width="16" height="16"></A></TD>
	<TD class="tbDivider" NOWRAP><A class=tbButton id=cmdThousand title=ǧ��λ href="#" name=cbButton><IMG align=absMiddle src="eSReport/format/thousand.gif" width="16" height="16"></A></TD>
	<TD class="tbDivider" NOWRAP width="100%"></TD>
	</TR>
</TABLE>
<TABLE class="cbToolbar" id="idTBDesign" cellpadding='0' cellspacing='0' width="100%">
	<TR>
	<TD NOWRAP width="21"><A class=tbButton id=cmdInsertCol title=������ href="#" name=cbButton><IMG align=absMiddle src="eSReport/design/insertcol.gif" width="16" height="16"></A></TD>
	<TD NOWRAP width="21"><A class=tbButton id=cmdInsertRow title=������ href="#" name=cbButton><IMG align=absMiddle src="eSReport/design/insertrow.gif" width="16" height="16"></A></TD>
	<TD NOWRAP width="21"><A class=tbButton id=cmdAppendCol title=׷���� href="#" name=cbButton><IMG align=absMiddle src="eSReport/design/appendcol.gif" width="16" height="16"></A></TD>
	<TD class="tbDivider" NOWRAP width="21"><A class=tbButton id=cmdAppendRow title=׷���� href="#" name=cbButton><IMG align=absMiddle src="eSReport/design/appendrow.gif" width="16" height="16"></A></TD>
	<TD NOWRAP width="21"><A class=tbButton id=cmdDeleteCol title=ɾ���� href="#" name=cbButton><IMG align=absMiddle src="eSReport/design/deletecol.gif" width="16" height="16"></A></TD>
	<TD NOWRAP width="21"><A class=tbButton id=cmdDeleteRow title=ɾ���� href="#" name=cbButton><IMG align=absMiddle src="eSReport/design/deleterow.gif" width="16" height="16"></A></TD>
	<TD class="tbDivider" NOWRAP width="21"><A class=tbButton id=cmdSheetSize title=��ҳ�ߴ� href="#" name=cbButton><IMG align=absMiddle src="eSReport/design/sheetsize.gif" width="16" height="16"></A></TD>
	<TD NOWRAP width="21"><A class=tbButton id=cmdMergeCell title=��ϵ�Ԫ�� href="#" name=cbButton><IMG align=absMiddle src="eSReport/design/mergecell.gif" width="16" height="16"></A></TD>
	<TD NOWRAP width="21"><A class=tbButton id=cmdUnMergeCell title=ȡ����Ԫ����� href="#" name=cbButton><IMG align=absMiddle src="eSReport/design/unmergecell.gif" width="16" height="16"></A></TD>
	<TD NOWRAP width="21"><A class=tbButton id=cmdMergeRow title=����� href="#" name=cbButton><IMG align=absMiddle src="eSReport/design/mergerows.gif" width="16" height="16"></A></TD>
	<TD class="tbDivider" NOWRAP width="21"><A class=tbButton id=cmdMergeCol title=����� href="#" name=cbButton><IMG align=absMiddle src="eSReport/design/mergecols.gif" width="16" height="16"></A></TD>
	<TD NOWRAP width="21"><A class=tbButton id=cmdReCalcAll title=����ȫ�� href="#" name=cbButton><IMG align=absMiddle src="eSReport/design/calculateall.gif" width="16" height="16"></A></TD>
	<TD NOWRAP width="21"><A class=tbButton id=cmdFormulaSum3D title=���û��ܹ�ʽ href="#" name=cbButton><IMG align=absMiddle src="eSReport/design/sum3d.gif" width="16" height="16"></A></TD>
	<TD class="tbDivider" NOWRAP width="21"><A class=tbButton id=cmdReadOnly title=��Ԫ��ֻ�� href="#" name=cbButton><IMG align=absMiddle src="eSReport/design/readonly.gif" width="16" height="16"></A></TD>
	<TD NOWRAP id="cmdFillType" Title="��䷽ʽ">
		<SELECT name="FillTypeSelect" style="WIDTH: 102px; HEIGHT: 23px" onChange="changeFillType(FillTypeSelect.value)" ACCESSKEY="v" size="1">
          <option value="1" selected 
       >�������</option>
          <option value="2">�������</option>
          <option value="4">�������</option>
          <option value="8">�������</option>
          <option value="16">�ظ����</option>
          <option value="32">�Ȳ����</option>
          <option value="64">�ȱ����</option>
        </SELECT>
	</TD>
	<TD class="tbDivider" NOWRAP width="21"><A class=tbButton id=cmdFillSerial title=������� href="#" name=cbButton><IMG align=absMiddle src="eSReport/design/fillserial.gif" width="16" height="16"></A></TD>
	
	<TD NOWRAP id="cmdDateType" Title="��������">
		<SELECT name="DateTypeSelect" style="WIDTH: 191px; HEIGHT: 23px" onChange="changeDateType(DateTypeSelect.value)" ACCESSKEY="v" size="1">
          <option value="1" selected>1997-3-4</option>
          <option value="2">1997-03-04 13:30:12</option>
          <option value="3">1997-3-4 1:30 PM</option>
          <option value="4">1997-3-4 13:30</option>
          <option value="5">97-3-4</option>
          <option value="6">3-4-97</option>
          <option value="7">03-04-97</option>
          <option value="8">3-4</option>
          <option value="9">һ�ž�������������</option>
          <option value="10">һ�ž���������</option>
          <option value="11">��������</option>
          <option value="12">1997��3��4��</option>
          <option value="13">1997��3��</option>
          <option value="14">3��4��</option>
          <option value="15">���ڶ�</option>
          <option value="16">��</option>
          <option value="17">4-Mar</option>
          <option value="18">4-Mar-97</option>
          <option value="19">04-Mar-97</option>
          <option value="20">Mar-97</option>
          <option value="21">March-97</option>
	  <option value="22">1997-03-04</option>
        </SELECT>
	</TD>
	<TD class="tbDivider" NOWRAP id="cmdTimeType" Title="ʱ������">
		<SELECT name="TimeTypeSelect" style="WIDTH: 154px; HEIGHT: 23px" onChange="changeTimeType(TimeTypeSelect.value)" ACCESSKEY="v" size="1">
          <option value="1" selected 
       >1:30</option>
          <option value="2">1:30 PM</option>
          <option value="3">13:30:00</option>
          <option value="4">1:30:00 PM</option>
          <option value="5">13ʱ30��</option>
          <option value="6">13ʱ30��00��</option>
          <option value="7">����1ʱ30��</option>
          <option value="8">����1ʱ30��00��</option>
          <option value="9">ʮ��ʱ��ʮ��</option>
          <option value="10">����һʱ��ʮ��</option>
        </SELECT>
	</TD>

	<TD NOWRAP width="100%"></TD>
	</TR>
</TABLE>

<div style="LEFT: 0px; POSITION: relative">����װ�ػ�����ģ��......</div>

<p>

<p>
<OBJECT id=CommonDialog1 style="DISPLAY: none;  POSITION: relative" height=32 
width=32 classid=clsid:3F166327-8030-4881-8BD2-EA25350E574A>
<PARAM NAME="_ExtentX" VALUE="688">
<PARAM NAME="_ExtentY" VALUE="688">
<PARAM NAME="_Version" VALUE="393216">
<PARAM NAME="CancelError" VALUE="1">
<PARAM NAME="Color" VALUE="0">
<PARAM NAME="Copies" VALUE="1">
<PARAM NAME="DefaultExt" VALUE="">
<PARAM NAME="DialogTitle" VALUE="">
<PARAM NAME="FileName" VALUE="">
<PARAM NAME="Filter" VALUE="">
<PARAM NAME="FilterIndex" VALUE="0">
<PARAM NAME="Flags" VALUE="0">
<PARAM NAME="FontBold" VALUE="0">
<PARAM NAME="FontItalic" VALUE="0">
<PARAM NAME="FontName" VALUE="">
<PARAM NAME="FontSize" VALUE="8">
<PARAM NAME="FontStrikeThru" VALUE="0">
<PARAM NAME="FontUnderLine" VALUE="0">
<PARAM NAME="FromPage" VALUE="0">
<PARAM NAME="HelpCommand" VALUE="0">
<PARAM NAME="HelpContext" VALUE="0">
<PARAM NAME="HelpFile" VALUE="">
<PARAM NAME="HelpKey" VALUE="">
<PARAM NAME="InitDir" VALUE="">
<PARAM NAME="Max" VALUE="0">
<PARAM NAME="Min" VALUE="0">
<PARAM NAME="MaxFileSize" VALUE="260">
<PARAM NAME="PrinterDefault" VALUE="1">
<PARAM NAME="ToPage" VALUE="0">
<PARAM NAME="Orientation" VALUE="1">
</OBJECT>
<OBJECT id=SingleeReport 
				style="DISPLAY: none; LEFT: 184px; POSITION: absolute; TOP: 190px" height=183 width=367 
				CODEBASE="/includes/cellweb5.cab#Version=5,2,4,921"
				classid="clsid:3F166327-8030-4881-8BD2-EA25350E574A" VIEWASTEXT>
				<PARAM NAME="_Version" VALUE="65536">
				<PARAM NAME="_ExtentX" VALUE="9710">
				<PARAM NAME="_ExtentY" VALUE="4842">
				<PARAM NAME="_StockProps" VALUE="0">				
</OBJECT>
</p>
<FORM	name="theform" method="post" action="selfdefinition.jsp">
<input type="hidden" name="saveflag" value="0">
<input type="hidden" name="filename" value="<%=filename%>">
<input type="hidden" name="paramFlagArray">
<input type="hidden" name="paramZBArray">
<input type="hidden" name="defaultValueArray">
</FORM>
</BODY>
</HTML>
