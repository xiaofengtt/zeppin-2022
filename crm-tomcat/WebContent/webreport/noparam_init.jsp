<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.webreport.*,enfo.crm.tools.*" %>
<%@ include file="/includes/operator.inc" %>
<%
String cllParamStr = request.getParameter("cllParamStr");
%>
<HTML>
<HEAD>
<title>�������ϵͳ</title>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="0">
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/report.js"></SCRIPT>
<SCRIPT LANGUAGE=javascript>
<%
int hh = 3;//����ҳ��ʼ�����꣬��COL_PARAM_HH��Ӧ
String filename = CellHelper.trimNull(request.getParameter("filename"),request.getContextPath()+"/webreport/Cells/108.CLL");
%>

function openFile() 
{			
	<%@ include file="/webreport/define.inc" %>
	var returnValue = SingleeReport.OpenFile ("<%=filename%>","");
	rp_checkFileStatus(returnValue);
	//����������ҳ
	SingleeReport.SetCurSheet(SHEET_DEFINE);	//��ȡ��������ҳ���ڴ�
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
	document.theform.submit();	
	//----------------------------------------------------------- 
}
</SCRIPT>
</HEAD>
<BODY onload="javascript:openFile();">
<OBJECT id=SingleeReport style="LEFT: 0px; WIDTH: 0px; TOP: 50px; HEIGHT:0px" CODEBASE="../includes/CellWeb5.CAB#Version=5,2,4,921" 
classid="clsid:3F166327-8030-4881-8BD2-EA25350E574A" VIEWASTEXT>
<PARAM NAME="_Version" VALUE="65536">
<PARAM NAME="_ExtentX" VALUE="17526">
<PARAM NAME="_ExtentY" VALUE="10774">
<PARAM NAME="_StockProps" VALUE="0"></OBJECT>
<FORM	name="theform" method="post" action="noparam_input.jsp">
<input type="hidden" name="filename" value="<%=filename%>">
<input type="hidden" name="cllParamStr" value="<%=cllParamStr%>">
<input type="hidden" name="paramFlagArray">
<input type="hidden" name="paramZBArray">
<input type="hidden" name="defaultValueArray">
</FORM>
</BODY>
</HTML>
