<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.webreport.*,enfo.crm.tools.*" %>
<%@ include file="/includes/operator.inc" %>
<HTML>
<HEAD>
<title>�������ϵͳ</title>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<LINK href="/includes/default.css" type=text/css rel=stylesheet>
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="0">
<SCRIPT LANGUAGE="vbscript" SRC="/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/default.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/report.js"></SCRIPT>
<SCRIPT LANGUAGE=javascript>
<%@ include file="/webreport/define.inc" %>	
<%
int hh = 3;//����ҳ��ʼ�����꣬��COL_PARAM_HH��Ӧ
String filename = CellHelper.trimNull(request.getParameter("filename"),request.getContextPath()+"/webreport/Cells/108.CLL");
%>

function openFile() 
{	
 	var i =	SingleeReport.OpenFile ("<%=filename%>","");
	rp_checkOpen(SingleeReport.GetFileName());	
	//����������ҳ
	SingleeReport.SetCurSheet(SHEET_INPUT);	//��ȡ��������ҳ���ڴ�

	if(i==1)//���Դ򿪵��ļ������жϣ�����ļ��򿪷���"1"��ô˵���ļ�ʱ��ȷ�ģ���������������ť���ɫ��
	{	
		document.getElementById("btnCreateReport").disabled=false;
	}else{
		document.getElementById("btnCreateReport").disabled=true;
	}
	
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
	
	//----------------------------------------------------------------	
	if(!debug)
		SingleeReport.ShowSheetLabel (0, SHEET_INPUT);	//���ص�һ�ű�ҳ��ҳǩ	
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


var CELL_ID = <%=Utility.parseInt((Integer)session.getAttribute("SESSION_CELL_ID"),0)%>;	//��Ʒ��Ԫȫ�ֱ���

/**  
 * ѡ���Ʒ��Ԫ�� 
 */
function openTree()
{

	var v = showModalDialog('<%=request.getContextPath()%>/client/analyse/report_tree.jsp', '', 'dialogWidth:500px;dialogHeight:550px;status:0;help:0');

	if (v != null)
	{
		CELL_ID = v;
	}
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
	if(SingleeReport.GetCellString(COL_PRINT_PARAM,PARAM_NUM,SHEET_DEFINE)=="ͳ�Ƶ�Ԫ")
	{
		
		cellFlag = true;
		if(CELL_ID==0)
		{
			sl_alert("��ѡ���Ʒ��Ԫ��");
			return false;
		}
	}
		
		
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
	    
	    if(temp.indexOf("dbo.")<0)	 //�����ԭ���ݿ�ϵͳ�����Сд�������ú���ʱ���ű�����cell�����ݣ������д�дת��
		   temp = temp.toUpperCase();	//���ַ����е���ĸ��ת���ɴ�д��	 
		//alert(temp+"    "+temp.indexOf("SELECT"));
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
			
			if(procedure.indexOf("@@CELL_ID")>0&&CELL_ID==0)
			{
				sl_alert("����ѡ���Ʒ��Ԫ��");
				return false;
			}
			else
			{
				procedure = procedure.replaceAll("@@CELL_ID",CELL_ID);		
			}	
			
			for(var i=0;i<defaultValueArray.length;i++)
			{				
				if(defaultValueArray[i]=="")	
					defaultValueArray[i] = " ";			
				pos = defaultValueArray[i].indexOf("-");				
				if(pos>0)
					resultValue = defaultValueArray[i].substring(0,pos);	
				//modify by zhangmy 20100505
				//֧����ֵΪ���������
				//else if(pos==0)
				//	resultValue = " ";
				else				
					resultValue = defaultValueArray[i];										
				//���ݲ������ƽ��洢��������ж�Ӧ�Ĳ����ò��������Ӧ��ֵ�滻��					
				procedure = procedure.replaceAll(paramArray[i],resultValue);						
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
			
			if(other.indexOf("@@CELL_ID")>0&&CELL_ID==0)
			{
				sl_alert("����ѡ���Ʒ��Ԫ��");
				return false;
			}
			else
			{
				other = other.replaceAll("@@CELL_ID",CELL_ID);		
			}	
			
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
	//parent.centerFrame.cols="5,*";   
	//parent.leftshow = false;
	//���õ���ִ�еĴ洢�������---procedure����
	document.theform.sqlArray.value = sqlArray;
	document.theform.fillZBArray.value = fillZBArray;
	document.theform.defaultValueArray.value = defaultValueArray; 
	
	/*if(cellFlag)	//���ҳ���⴦��ת��result_mulit_sheet.jsp
	{		
		
		document.theform.action = "result_multi_sheet.jsp";
	}
	else*///���ļ������ڣ���ע�͵�
	{
		document.theform.action = "result.jsp";	
	}
	document.theform.submit();	
}

function Select()
{	
	if(parent.leftshow)
	{
		parent.centerFrame.cols="5,*";	
		parent.leftshow = false;			
	}
	else
	{
		parent.centerFrame.cols="200,*";
		parent.leftshow = true;		
	}
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
              <td valign="top" width="40"><img src="../images/wait.gif" width="29" height="32"></td>
              <td valign="top" class="g1">�������ɱ���<br>
                ���Ժ�... </td>
            </tr>
          </table></td>
      </tr>
    </table>
  </div>
</div> -->
<TABLE border="0" width="100%">
	<TBODY>
		<TR>
			<td >			
				<button type="button"  class="rbutton0"  name="btnSelectReport" title="ѡ�񱨱�" onclick="javascript:Select();">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;ѡ�񱨱�</button>&nbsp;&nbsp;
				<button type="button"  class="rbutton1"  name="btnInputValue" title="��ѯ����" disabled onclick="javascript:history.back();">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;��ѯ����</button>&nbsp;&nbsp;
				<button type="button"  class="rbutton2"  name="btnCleanValue" title="�������" onclick="javascript:CleanValue();">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;�������</button>&nbsp;&nbsp;
				<button type="button"  class="rbutton3"  name="btnDefaultValue" title="Ĭ������" onclick="javascript:DefaultValue();">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Ĭ������</button>&nbsp;&nbsp;
				<button type="button"  class="rbutton4"  name="btnCreateReport" id="btnCreateReport"  title="���ɱ���"  onclick="javascript:CreateReport();">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;���ɱ���</button>&nbsp;&nbsp;
				<%if (input_operator.hasFunc(menu_id, 150))
{%>	<button type="button"  class="rbutton5"  name="btnPreview" title="��ӡԤ��" disabled onclick="javascript:setPrint(1);">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;��ӡԤ��</button>&nbsp;&nbsp;
				<button type="button"  class="rbutton6"  name="btnPrintReport" disabled title="��ӡ����" onclick="javascript:setPrint(0);">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;��&nbsp;&nbsp;ӡ</button>&nbsp;&nbsp;<%}%>
				<button type="button"  class="rbutton7"  name="btnChooseProduct" title="��Ʒ��Ԫ"  onclick="javascript:openTree();">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;��Ʒ��Ԫ</button>&nbsp;&nbsp;
				<%if (input_operator.hasFunc(menu_id, 160))
{%>		<button type="button"  class="rbutton8"  name="btnExportReport" title="��������"  disabled onclick="javascript:exportReport();">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;��������</button>&nbsp;&nbsp;<%}%>
			</td>
		</TR>
	</TBODY>
</TABLE>
<OBJECT id="SingleeReport" 
				style="left:0;top:0;width:100%;height:800px;"
				CODEBASE="/includes/cellweb5.cab#Version=5,2,4,921"
				classid="clsid:3F166327-8030-4881-8BD2-EA25350E574A" VIEWASTEXT>
				<PARAM NAME="_Version" VALUE="65536">
				<PARAM NAME="_ExtentX" VALUE="17526">
				<PARAM NAME="_ExtentY" VALUE="10774">
				<PARAM NAME="_StockProps" VALUE="0">				
</OBJECT>
<FORM name="theform" method="post" action="result.jsp">
<input type="hidden" name="filename" value="<%=filename%>">
<input type="hidden" name="sqlArray">
<input type="hidden" name="fillZBArray">
<input type="hidden" name="paramZBArray" value="<%=paramZBArray%>">
<input type="hidden" name="paramFlagArray" value="<%=paramFlagArray%>">
<input type="hidden" name="defaultValueArray">
</FORM>
</BODY>
</HTML>
