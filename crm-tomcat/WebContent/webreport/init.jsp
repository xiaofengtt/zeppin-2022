<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.webreport.*,enfo.crm.tools.*" %>
<%@ include file="/includes/operator.inc" %>
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
	SingleeReport.OpenFile ("<%=filename%>","");
	rp_checkOpen(SingleeReport.GetFileName());
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
<!-- <div id=tdcent style='position:relative;top:40%;left:40%;'> 
  <div id="waitting" style="position:absolute; top:0%; left:0%; z-index:10; visibility:visible"> 
    <table width="180" border="0" cellspacing="2" cellpadding="0" height="70" bgcolor="0959AF">
      <tr> 
        <td bgcolor="#FFFFFF" align="center"> <table width="160" border="0" height="50">
            <tr> 
              <td valign="top" width="40"><img src="../images/eextanim32.gif" width="29" height="32"></td>
              <td valign="top" class="g1">��������У�<br>
                ���Ժ�... </td>
            </tr>
          </table></td>
      </tr>
    </table>
  </div>
</div> -->
<OBJECT id=SingleeReport style="WIDTH: 100%;HEIGHT:800px;" CODEBASE="../includes/CellWeb5.CAB#Version=5,2,4,921" 
classid="clsid:3F166327-8030-4881-8BD2-EA25350E574A" VIEWASTEXT>
<PARAM NAME="_Version" VALUE="65536">
<PARAM NAME="_ExtentX" VALUE="17526">
<PARAM NAME="_ExtentY" VALUE="10774">
<PARAM NAME="_StockProps" VALUE="0"></OBJECT>
<FORM	name="theform" method="post" action="input.jsp">
<input type="hidden" name="filename" value="<%=filename%>">
<input type="hidden" name="paramFlagArray">
<input type="hidden" name="paramZBArray">
<input type="hidden" name="defaultValueArray">
</FORM>
</BODY>
</HTML>
<script type="text/javascript">
	document.getElementById("SingleeReport").style.width = 100 + "%";
	document.getElementById("SingleeReport").style.height = 800 + "px";
</script>
