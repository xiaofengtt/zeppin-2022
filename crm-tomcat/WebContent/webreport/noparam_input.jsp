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

<%@ include file="/webreport/define.inc" %>	
<%
int hh = 3;//����ҳ��ʼ�����꣬��COL_PARAM_HH��Ӧ
String filename = CellHelper.trimNull(request.getParameter("filename"),request.getContextPath()+"/webreport/Cells/108.CLL");
%>

function openFile() 
{	
	var returnValue = SingleeReport.OpenFile ("<%=filename%>","");
	rp_checkFileStatus(returnValue);	
	//����������ҳ
	SingleeReport.SetCurSheet(SHEET_INPUT);	//��ȡ��������ҳ���ڴ�
	//----------------------------------------------------------
	<%
	String paramZBArray = request.getParameter("paramZBArray");
	String paramFlagArray = request.getParameter("paramFlagArray");
	String defaultValueArray = request.getParameter("defaultValueArray");
	
	String[] paramZBList = CellHelper.splitString(paramZBArray,",");	
	String[] ParamsList = Cell.getParamsList(paramFlagArray,input_operatorCode,input_bookCode);	
	//����ϵͳ��־ȡϵͳ������б�
	%>
	//�������λ��		
	var pos1,pos2;  //pos1Ϊ�б꣬pos2Ϊ�б�	
	var result;		
	
	var defaultValueList = "<%=defaultValueArray%>".split(",");	
	var cllParamArray = "<%=cllParamStr%>".split("��");//ҳ�洫��Ĳ�ѯ����
	<%
	for(int i=0;i<paramZBList.length;i++)
	{		
	%>
		pos1 = getParamPos('<%=paramZBList[i]%>');
		pos2 = getNumPos('<%=paramZBList[i]%>');
		result = "<%=ParamsList[i]%>".replace(/\t/g,"\n");
		<%if(ParamsList[i]!=null&&!ParamsList[i].equals(""))	{%>							
			SingleeReport.SetDroplistCell(pos1,pos2,SHEET_INPUT,result,4);		
		<%}%>
		//------------------���Ĭ�ϲ���ֵ-----------------------------
		SingleeReport.S(pos1,pos2,SHEET_INPUT,defaultValueList[<%=i%>]);
		//------------------���ҳ�洫�����ֵ-----------------------------
		if(defaultValueList.length == cllParamArray.length-1){
			SingleeReport.S(pos1,pos2,SHEET_INPUT,cllParamArray[<%=i%>]);
		}else{
			rp_alert("��ѯҳ�����������cll�ļ���ƥ�䣡");
			return false;
		}
	<%}%>		
	//----------------------------------------------------------------	
	if(!debug)
		SingleeReport.ShowSheetLabel (0, SHEET_INPUT);	//���ص�һ�ű�ҳ��ҳǩ		
	//���ɱ���	
	CreateReport();
}

function CleanValue()
{	
	//�������λ��		
	var pos1,pos2;  //pos1Ϊ�б꣬pos2Ϊ�б�	
	<%
	for(int i=0;i<paramZBList.length;i++)
	{	
	%>
		pos1 = getParamPos('<%=paramZBList[i]%>');
		pos2 = getNumPos('<%=paramZBList[i]%>');
		SingleeReport.ClearArea(pos1,pos2,pos1,pos2,SHEET_INPUT,1);
		//------------------------------------------------------------
	<%
	}	
	%>
}

function DefaultValue()
{
	//�������λ��		
	var pos1,pos2;  //pos1Ϊ�б꣬pos2Ϊ�б�	
	var result;		
	var defaultValueList = "<%=defaultValueArray%>".split(",");
	<%
	for(int i=0;i<paramZBList.length;i++)
	{	
	%>
		pos1 = getParamPos('<%=paramZBList[i]%>');
		pos2 = getNumPos('<%=paramZBList[i]%>');
		result = "<%=ParamsList[i]%>".replace(/\t/g,"\n");
		<%if(ParamsList[i]!=null&&!ParamsList[i].equals(""))	{%>					
			SingleeReport.SetDroplistCell(pos1,pos2,SHEET_INPUT,result,4);		
		<%}%>
		//------------------���Ĭ�ϲ���ֵ-----------------------------
		SingleeReport.S(pos1,pos2,SHEET_INPUT,defaultValueList[<%=i%>]);
		//------------------------------------------------------------
	<%
	}	
	%>
	SingleeReport.ReDraw();
}


//���ɱ���
function CreateReport()
{		
	var PARAM_NUM = COL_PARAM_HH;	//��������ҳ��ʼ�кţ�����������Ҫѭ���������¶���һ������
	var COL_SQL_HH = COL_PARAM_HH;	//SQL��䶨���к�,��Ϊ�����ҳ���õ�����û����ȫ�ֱ�����Ĭ��SQL�����ʼ�кŵ��ڲ���������ʼ�к�
	var temp,sql,procedure,other;	//����SQL����洢�������ƻ�����ϵͳ����
	var sqlArray = "";	//����SQL����洢�������������ϵͳ����	
	//------------------ѭ��ȡ�����в������ƺͲ������ֻ꣬�д��ڲ�������Ĳ������ƲŻ�ӵ�������-----------------------------------------
	var paramArray = new Array();	//������������
	var paramZBArray = new Array();	//������������
	var nullValue;	//�ǿձ�־
	var paramValue; //����¼��ҳ�ϵĲ��������Ӧ�Ĳ���ֵ
	var pos1,pos2;	//���������Ӧ���������������
	var pos ,resultValue;	//����ֵ�еĽ�ȡλ�ͷ��ص�����ֵ
	var defaultValueArray = new Array(); //���ֵ����;�Ǵ�ѡ�����Ĳ��������ҳ�����ҳ������ѯ������ʱ�õ�	
		
	var cellFlag = false;	//ͳ�Ƶ�Ԫ��־ 
	
		
		
	while(SingleeReport.GetCellString(COL_PARAM,PARAM_NUM,SHEET_DEFINE)!="")
	{	
		if(SingleeReport.GetCellString(COL_PARAM_ZB,PARAM_NUM,SHEET_DEFINE)!="")	//ֻ�в������ƶ�Ӧ�в��������ʱ��
		{

			paramArray[PARAM_NUM-COL_PARAM_HH] = SingleeReport.GetCellString(COL_PARAM,PARAM_NUM,SHEET_DEFINE); //���������ƴ������飬��"@P_PRODUCT_CODE"
			paramZBArray[PARAM_NUM-COL_PARAM_HH] = SingleeReport.GetCellString(COL_PARAM_ZB,PARAM_NUM,SHEET_DEFINE); //���������ƶ�Ӧ������������飬��"C1"
		}
		PARAM_NUM++;
	}
	//-------------------���ȸ��ݷǿձ�־�����ж�-------------------------
	for(var i=0;i<paramArray.length;i++)
	{	
		//���ݲ������������¼��ҳȡ�ö�Ӧ��ֵ
		pos1 = getParamPos(paramZBArray[i]);
		pos2 = getNumPos(paramZBArray[i]);			 
		paramValue = SingleeReport.GetCellString(pos1,pos2,SHEET_INPUT);
		defaultValueArray[i] = SingleeReport.GetCellString(pos1,pos2,SHEET_INPUT);				
		nullValue = SingleeReport.GetCellString(COL_PARAM_NULL,COL_PARAM_HH+i,SHEET_DEFINE);
		if(nullValue!=""&&paramValue=="")
		{			
			alert("������"+ SingleeReport.GetCellString(1,pos2,SHEET_INPUT) +" ����Ϊ�գ�");
			SingleeReport.MoveToCell(pos1,pos2);
			return false;
		}				
	}
	//-------------------ѭ��ȡSQL��䶨����-----------------------------------------------------------------------------------------
	while(SingleeReport.GetCellString(COL_SQL,COL_SQL_HH,SHEET_DEFINE)!="")
	{
		//�жϵ�Ԫ���Ƿ��й�ʽ���еĻ�ȡ��ʽ���ʽ
		if(SingleeReport.IsFormulaCell(COL_SQL, COL_SQL_HH, SHEET_DEFINE)>0)
			temp = SingleeReport.GetFormula(COL_SQL,COL_SQL_HH,SHEET_DEFINE);
		else
			temp = SingleeReport.GetCellString(COL_SQL,COL_SQL_HH,SHEET_DEFINE);	//ȡ�������ж���SQL��仹�Ǵ洢���̻���һ���ַ���
		temp = temp.toUpperCase();	//���ַ����е���ĸ��ת���ɴ�д��	
		if(temp.indexOf("CALL")>=0||temp.indexOf("SELECT")>=0)	//���ַ�������CALL������SELECT�����ģ����ж���ΪSQL�洢����
		{
			procedure = temp;	//ȡ�洢�������		
							
			//�����滻ϵͳĬ��ֵ����"@@BOOKCODE","@@OPERATOR"��
			procedure = procedure.replaceAll("@@BOOKCODE","<%=input_bookCode%>");
			procedure = procedure.replaceAll("@@OPERATORINT","<%=input_operatorCode%>");
			procedure = procedure.replaceAll("@@OPERATOR","<%=input_operatorName%>");			
			procedure = procedure.replaceAll("@@DATEINT","<%=CellHelper.getDateInt()%>");
			procedure = procedure.replaceAll("@@YEAR","<%=CellHelper.getYearInt()%>");
			procedure = procedure.replaceAll("@@MONTH","<%=CellHelper.getMonthInt()%>");
			procedure = procedure.replaceAll("@@YYYYMM","<%=CellHelper.getYMInt()%>");
			procedure = procedure.replaceAll("@@DATE","<%=CellHelper.getDateCn()%>");		
			
			
			for(var i=0;i<defaultValueArray.length;i++)
			{				
				if(defaultValueArray[i]=="")	
					defaultValueArray[i] = " ";			
				pos = defaultValueArray[i].indexOf("-");				
				if(pos>0)
					resultValue = defaultValueArray[i].substring(0,pos);	
				else if(pos==0)
					resultValue = " ";
				else				
					resultValue = defaultValueArray[i];										
				//���ݲ������ƽ��洢��������ж�Ӧ�Ĳ����ò��������Ӧ��ֵ�滻��					
				procedure = procedure.replace(paramArray[i],resultValue);						
			}
				
			//�õ���ִ�еĴ洢������䣬��������洢��������	
			if(sqlArray == "")		
				sqlArray =  procedure;
			else
				sqlArray = sqlArray + ";" + procedure ;	
				
				
																
		}
		else	//һ���ַ�����ʱ��
		{
			other = temp;	//ȡ����ϵͳ����
			//�����滻ϵͳĬ��ֵ����"@@BOOKCODE","@@OPERATOR"��
			other = other.replaceAll("@@BOOKCODE","<%=input_bookCode%>");
			other = other.replaceAll("@@OPERATORINT","<%=input_operatorCode%>");
			other = other.replaceAll("@@OPERATOR","<%=input_operatorName%>");			
			other = other.replaceAll("@@DATEINT","<%=CellHelper.getDateInt()%>");
			other = other.replaceAll("@@YEAR","<%=CellHelper.getYearInt()%>");
			other = other.replaceAll("@@MONTH","<%=CellHelper.getMonthInt()%>");
			other = other.replaceAll("@@YYYYMM","<%=CellHelper.getYMInt()%>");
			other = other.replaceAll("@@DATE","<%=CellHelper.getDateCn()%>");
			for(var i=0;i<defaultValueArray.length;i++)
			{							
				pos = defaultValueArray[i].indexOf("-");				
				if(pos>0)
				{
					pos = parseInt(pos) + 1;
					if(pos<defaultValueArray[i].length)
						resultValue = defaultValueArray[i].substring(pos);
					else
						resultValue = "";	
				}			
				else				
					resultValue = defaultValueArray[i];									
				//���ݲ������ƽ��洢��������ж�Ӧ�Ĳ����ò��������Ӧ��ֵ�滻��									
				other = replaceAll(other,paramArray[i],resultValue );						
			}			
			//�õ���������ϵͳ�������������������ϵͳ��������			
			if( sqlArray == "")		
				sqlArray = other;
			else
				sqlArray = sqlArray + ";" + other ;				
		}
		COL_SQL_HH++;	
			
	}
	
	//--------------------�����������(���ں���JAVA�޷��õ����������ﴫ��)--------------------------
	var SQL_NUM = COL_PARAM_HH;
	var fillZBArray = "";
	while(SingleeReport.GetCellString(COL_SQL_ZB,SQL_NUM,SHEET_DEFINE)!="")
	{		
		if(fillZBArray == "")		
			fillZBArray = SingleeReport.GetCellString(COL_SQL_ZB,SQL_NUM,SHEET_DEFINE);
		else
			fillZBArray = fillZBArray + ";" + SingleeReport.GetCellString(COL_SQL_ZB,SQL_NUM,SHEET_DEFINE) ;	
		
		SQL_NUM++;
	}
	
	//--------------------------------------------------------
	//������ߵĲ˵�	 
	//parent.centerFrame.cols="0,*";   
	//parent.leftshow = false;
	//���õ���ִ�еĴ洢�������---procedure����  
	document.theform.sqlArray.value = sqlArray;
	document.theform.fillZBArray.value = fillZBArray;
	document.theform.defaultValueArray.value = defaultValueArray; 
	document.theform.action = "noparam_result.jsp";	
	document.theform.submit();	 
}


</SCRIPT> 
</HEAD>
<BODY leftMargin=0 topMargin=0 rightmargin=0 onload="javascript:openFile();" >
<!-- <div id=tdcent style='position:relative;top:40%;left:40%;'> 
  <div id="waitting" style="position:absolute; top:0%; left:0%; z-index:10; visibility:visible"> 
    <table width="180" border="0" cellspacing="2" cellpadding="0" height="70" bgcolor="0959AF">
      <tr> 
        <td bgcolor="#FFFFFF" align="center"> <table width="160" border="0" height="50">
            <tr> 
              <td valign="top" width="40"><img src="../images/eextanim32.gif" width="29" height="32"></td>
              <td valign="top" class="g1">�������ɱ���<br>
                ���Ժ�... </td>
            </tr>
          </table></td>
      </tr>
    </table>
  </div>
</div> -->
<OBJECT id=SingleeReport style="LEFT: 0px; TOP: 0px; width=0px ; height=0px;"
				CODEBASE="../includes/cellweb5.cab#Version=5,2,4,921"
				classid="clsid:3F166327-8030-4881-8BD2-EA25350E574A" VIEWASTEXT>
				<PARAM NAME="_Version" VALUE="65536">
				<PARAM NAME="_ExtentX" VALUE="17526">
				<PARAM NAME="_ExtentY" VALUE="10774">
				<PARAM NAME="_StockProps" VALUE="0">				
</OBJECT>
<FORM name="theform" method="post" action="noparam_result.jsp">
<input type="hidden" name="filename" value="<%=filename%>">
<input type="hidden" name="cllParamStr" value="<%=cllParamStr%>">
<input type="hidden" name="sqlArray">
<input type="hidden" name="fillZBArray">
<input type="hidden" name="paramZBArray" value="<%=paramZBArray%>">
<input type="hidden" name="paramFlagArray" value="<%=paramFlagArray%>">
<input type="hidden" name="defaultValueArray">
</FORM>
</BODY>
</HTML>