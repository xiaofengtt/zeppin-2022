<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.webreport.*,enfo.crm.tools.*" %>
<%@ include file="/includes/operator.inc" %>
<%
String sql = request.getParameter("sqlArray");
String fillzb = request.getParameter("fillZBArray");
String[] sqlArray = CellHelper.splitString(sql,";");
String[] fillZBArray = CellHelper.splitString(fillzb,";");

StringBuffer resultBuffer = new StringBuffer();

int hh = 3;//����ҳ��ʼ�����꣬��COL_PARAM_HH��Ӧ
String filename = CellHelper.trimNull(request.getParameter("filename"),request.getContextPath()+"/webreport/Cells/108.CLL");
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


function exportReport()
{	
	var obj = SingleeReport;
	var name = SingleeReport.GetCellString(1,1,SHEET_OUTPUT);
	showModalDialog('export.jsp?name='+ name,obj,'dialogTop:120px;dialogLeft:500px;dialogWidth:360px;dialogHeight:250px;status:0;help:0');					
}

function InputValue()
{
	document.theform.submit();
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
</SCRIPT>
</HEAD>
<BODY leftMargin=0 topMargin=0 rightmargin=0>
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
<TABLE border="0" width="100%">
	<TBODY>
		<TR>
			<TD>
				<button type="button"  class="rbutton5"  name="btnPreview" title="��ӡԤ��" onclick="javascript:setPrint(1);">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;��ӡԤ��</button>&nbsp;&nbsp;
				<button type="button"  class="rbutton6"  name="btnPrintReport" title="��ӡ����" onclick="javascript:setPrint(0);">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;��&nbsp;&nbsp;ӡ</button>&nbsp;&nbsp;
				<button type="button"  class="rbutton8"  name="btnExportReport" title="��������" onclick="javascript:exportReport();">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;��������</button>&nbsp;&nbsp;
				<button type="button"  class="rbutton10"  name="btnExportReport" title="�ر�" onclick="javascript:window.close();">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;��&nbsp;&nbsp;��</button>&nbsp;&nbsp;
			</TD>
		</TR>
	</TBODY>
</TABLE>
<OBJECT id=SingleeReport style="LEFT: 0px; TOP: 0px; width=100% ; height=90%;visibility:hidden" 
				CODEBASE="<%=request.getContextPath()%>/includes/cellweb5.cab#Version=5,2,4,921"
				classid="clsid:3F166327-8030-4881-8BD2-EA25350E574A" VIEWASTEXT>
				<PARAM NAME="_Version" VALUE="65536">
				<PARAM NAME="_ExtentX" VALUE="17526">
				<PARAM NAME="_ExtentY" VALUE="10774">
				<PARAM NAME="_StockProps" VALUE="0">				
</OBJECT>
<script  language="javascript">
	var returnValue = SingleeReport.OpenFile ("<%=filename%>","");
	rp_checkFileStatus(returnValue);
	//����������ҳ
	SingleeReport.SetCurSheet(SHEET_OUTPUT);	//��ȡ������ҳ���ڴ�
	//-----------------------------------------------------------------
	<%
	int sqlindex;	//�ָ�Ϊ���ݶ��Ž�ȡ������SUBSTRING
	//��䷽ʽ-----������䷽ʽ����û�д�������
	int method = 1; //Ĭ��Ϊ�������
	//���������ʽ
	int style = 7;//Ĭ��ֵ
	//��������ַ���
	String content = "";
	//SQL���ִ�е��ڼ���,�����������Ҫ����ʼ�е���������궨����ʼ�У�����
	int rowCursor = hh;	
	%>
	<%//�������ز���������		
	sun.jdbc.rowset.CachedRowSet rowset = null;	
	java.sql.ResultSetMetaData metdata = null;	
	int colcount = 0;	//�������¼���ֶ���	
	int colCursor = 1;	//�����ѭ����ʼ��¼��
	int rowcount = 0;	//������ĳ���	
	%>

	//��Ҫ������������
	var posArray ;	//��������
	//�����������
		var colArray ; //���������飬�����������	
		var rowstart ;  //�����������ʼ�У�����ȡ�ĵ�һ���������������꣬�����������Ҫ������
	//���ں������
		var rowArray ; //���������飬���ں������
		var colstart ;  //�����������ʼ�У�����ȡ�ĵ�һ���������������꣬�����������Ҫ������	
	var total_row_count = 0;//���н�����ĳ��ȣ������洢���̺�SQL��䣬��ʼ���ȵ���0
	var fillstart = 0;	//�ϲ���Ԫ��ʱ�������ֵ��ѭ������
	//�µ���䷽��������JS����ѭ�����
	var xArray,yArray,xlength;  //���������ת��������
	var ColumnTypeArray ; 	//����������ֶε����ͣ�����ֻ�ж��Ƿ�Ϊ�����ַ���
	//-----------------------------------------------------------------------------
	var def_col,def_row;  //Ԥ���������������ͺ�����
//-----------------------ִ����䣺�������---------------------------------
<%
for(int n=0;n<sqlArray.length;n++)
{	
	content = fillZBArray[n];
	//ȡ��䷽��
	sqlindex = content.indexOf(",");	
	method = CellHelper.parseInt(content.substring(0,sqlindex),1);		
	content = content.substring(sqlindex+1);
	//ȡ���������ʽ
	sqlindex = content.indexOf(",");	
	style = CellHelper.parseInt(content.substring(0,sqlindex),7);
	content = content.substring(sqlindex+1);

	//�ж���SQL��仹��Ԥ�����ַ���
	if(sqlArray[n].indexOf("CALL")>=0||sqlArray[n].indexOf("SELECT")>=0)
	{	
		//�ж��Ǵ洢���̻���SQL		
		if(sqlArray[n].indexOf("CALL")>=0)		
			rowset =  Cell.queryProc(sqlArray[n]);
		else
			rowset =  Cell.querySql(sqlArray[n]);
		
		if(rowset.getMetaData()!=null)
		{
			metdata = rowset.getMetaData();
			colcount = metdata.getColumnCount();
			rowcount = Cell.getRowCount();
			%>
				ColumnTypeArray = new Array(); 			
			<%for(colCursor=1;colCursor<=colcount;colCursor++)
			{%>
				ColumnTypeArray[<%=colCursor-1%>] = '<%=metdata.getColumnType(colCursor)%>';			
			<%}
			//
			resultBuffer = new StringBuffer();
			String colvalue="";int nIndex=-1;int rIndex=-1;
			
			while(rowset.next())
			{		  
				for(colCursor=1;colCursor<=colcount;colCursor++)
				{
					colvalue=Utility.trimNull(rowset.getString(colCursor));
//					colvalue=colvalue.trim();//ȥ�ַ�����β�ո�
					
					StringBuffer tempBuffer = new StringBuffer(colvalue);
					
					nIndex=colvalue.indexOf("\n");
					rIndex=colvalue.indexOf("\r");
					
					if(nIndex>0 )
					{
						tempBuffer.deleteCharAt(nIndex);
					}	
					if(rIndex>0 )
					{
						tempBuffer.deleteCharAt(rIndex);
					}
					colvalue=tempBuffer.toString();
					
					resultBuffer.append(colvalue);
					
					resultBuffer.append("��");
				}
				resultBuffer.append("��");								
			}
			%>			
			//�����������JS			
			var xArray = '<%=resultBuffer.toString()%>'.split("��");
			xlength = xArray.length - 1;
			yArray = new Array();													
			for(i=0;i<xlength;i++)
			{
				yArray[i] = xArray[i].split("��");								
			}														
<%if(method==1||method==2)//�������
{%>
			//--------------------------------------------------								
			posArray = '<%=content%>'.split(",");	//�������ַ������ݶ��ŷֿ���Ϊһ������
			colArray = new Array();
			for(var i=0;i<posArray.length;i++)
			{
				colArray[i] = getParamPos(posArray[i]);		//ת��������������				
			}			
			<%if(rowCursor==hh)	{%>					
				rowstart = getNumPos(posArray[0]); //����ȡ�ĵ�һ����������������
				fillstart = getNumPos(posArray[0]); 
				total_row_count = parseInt(total_row_count) + <%=rowcount%>;
			<%} else {%>
				//ִ��һ���洢���̺�total_row_countҪ���Ͻ�����ĳ���
				if(total_row_count>0)										
					rowstart = parseInt(getNumPos(posArray[0]))+ parseInt(total_row_count)-1;
				else
					rowstart = parseInt(getNumPos(posArray[0]));	
				total_row_count = parseInt(total_row_count) + <%=rowcount%>;
				
			<%}%>					
			//һ���Բ���������,��Ϊ��Ĭ�ϵĿհ��У�����ֻҪ����rowcount-1�С�
			<%if(method==1) {%>															
				SingleeReport.InsertRow((parseInt(rowstart)+1),<%=rowcount-1%>,SHEET_OUTPUT);
			<%}%>
			//���⴦��û�н�������ص�ʱ��	
				<%if(rowcount==0)  {%>
					rowstart = parseInt(rowstart) +1;		//���û�н��������Ҫ��һ������					
				<%}%>	
			//���ǰ��Ҫ�����иߣ�CELLû���Զ�����
			var RowHeight = SingleeReport.GetRowHeight(0,rowstart,SHEET_OUTPUT);
			//--------------------------------						
			//--------------------ѭ���������------------------------------
			for(i=0;i<xlength;i++)
			{	
				//���ǰ��Ҫ�����иߣ�CELLû���Զ�����
				SingleeReport.SetRowHeight(0,RowHeight,rowstart,SHEET_OUTPUT);
				for(j=0;j<colArray.length;j++)
				{												
						
							//��Ҫ�жϸ��ֶε����ͣ�����ǽ���͵�Ҫ��ǧ��λ�Ӷ���
							if(ColumnTypeArray[j]==3&&yArray[i][j]!="")	
							{						
								SingleeReport.D(colArray[j],rowstart,SHEET_OUTPUT,parseFloat(yArray[i][j]));
							}
							else 
							{							
								SingleeReport.S(colArray[j],rowstart,SHEET_OUTPUT,yArray[i][j]);
							}						
				}								
			 	rowstart++;
			 }	
			
<%}
else if(method==0)//����������
{%>
			//--------------------------------------------------
			posArray = '<%=content%>'.split(",");	//�������ַ������ݶ��ŷֿ���Ϊһ������
			colArray = new Array();
			for(var i=0;i<posArray.length;i++)
			{				
				colArray[i] = getParamPos(posArray[i]);		//ת��������������								
			}			
			rowstart = getNumPos(posArray[0]); //����ȡ�ĵ�һ����������������					
			//--------------------ѭ���������------------------------------
			for(i=0;i<xlength;i++)
			{	
				for(j=0;j<colArray.length;j++)
				{												
						
							//��Ҫ�жϸ��ֶε����ͣ�����ǽ���͵�Ҫ��ǧ��λ�Ӷ���
							if(ColumnTypeArray[j]==3&&yArray[i][j]!="")	
							{						
								SingleeReport.D(colArray[j],rowstart,SHEET_OUTPUT,parseFloat(yArray[i][j]));
							}
							else 
							{							
								SingleeReport.S(colArray[j],rowstart,SHEET_OUTPUT,yArray[i][j]);								
							}	
				
						
				}				
			 	rowstart++;
			 }
<%}
else if(method==33)//�������
{%>
			posArray = '<%=content%>'.split(",");	//�������ַ������ݶ��ŷֿ���Ϊһ������
			rowArray = new Array();
			for(var i=0;i<posArray.length;i++)
			{
				rowArray[i] = getNumPos(posArray[i]);		//ת��������������				
			}				
			colstart = getParamPos(posArray[0]); //����ȡ�ĵ�һ����������������					
			//һ���Բ���������,��Ϊ��Ĭ�ϵĿհ��У�����ֻҪ����rowcount-1�С�	
							
			SingleeReport.InsertCol((parseInt(colstart)+1),<%=rowcount-1%>,SHEET_OUTPUT);						
	
			//--------------------ѭ���������------------------------------
			for(i=0;i<xlength;i++)
			{	
				for(j=0;j<rowArray.length;j++)
				{												

							//��Ҫ�жϸ��ֶε����ͣ�����ǽ���͵�Ҫ��ǧ��λ�Ӷ���
							if(ColumnTypeArray[j]==3&&yArray[i][j]!="")	
							{						
								SingleeReport.D(colstart,rowArray[j],SHEET_OUTPUT,parseFloat(yArray[i][j]));
							}
							else 
							{							
								SingleeReport.S(colstart,rowArray[j],SHEET_OUTPUT,yArray[i][j]);
							}	
				
						
				}				
			 	colstart++;
			 }
			 
<%}			
		}	
	}
	else
	{
		//-------------------------------��Ҫ�����Ʊ��ˡ���Ԥ�������----------------------
	%>	
		def_col = getParamPos('<%=content%>');
		def_row = getNumPos('<%=content%>');
		<%	
		if(method==1)	{ %>	//�������								
			def_row = parseInt(def_row) + parseInt(rowstart) - parseInt(fillstart) - 1;						
		<%}%>								
		SingleeReport.S(def_col,def_row,SHEET_OUTPUT,'<%=sqlArray[n]%>');	
		//��CELL��ʽ�Ļ���Ҫ����ʽ��
		SingleeReport.SetFormula(def_col,def_row,SHEET_OUTPUT,'<%=sqlArray[n]%>');
	<%		  	
	}
	rowCursor++;	//ִ����һ��SQL�ͼ�1
}			
	%>	
	//---------------------------------------------------------------------------------------------	
	//����Ԫ����	
	SetCellWidth(SingleeReport,SHEET_OUTPUT);	
	//-------------------------------���ú�Ҫ�ϲ���ֵͬ�ĵ�Ԫ��-------------------------------------
	//ȡ��������ҳ�Ƿ���Ҫ�ϲ���Ԫ��
	var param_hh = COL_PARAM_HH;	//��������ҳ�к�	
	var mergeflag;	//���ܶ����־
	var mergeCol;   //�ϲ���������
	var mergeRow;   //�ϲ���������
	var previewRowValue,thisRowValue;  //��һ�е�ֵ����һ�е�ֵ�������ж��Ƿ���Ҫ�ϲ�
	var startRow,endRow;  //�ϲ���ʼ������ͽ���������	
	//�����ܶ�����ֵ��ʱ��Ž��кϲ�
	while(SingleeReport.GetCellString(COL_SUM_START,param_hh,SHEET_DEFINE)!="")	
	{
	 	//�����ж�---��һ��ϲ�����ȫֵ�ϲ�
	 	if(SingleeReport.GetCellString(COL_SUM_START,param_hh,SHEET_DEFINE).substring(0,1)==("ֵ"))
	 	{			
			mergeflag = SingleeReport.GetCellString(COL_SUM_START,param_hh,SHEET_DEFINE).substring(0,9);		
			mergeCol = SingleeReport.GetCellString(COL_SUM_START,param_hh,SHEET_DEFINE).substring(9);
			mergeRow = getNumPos(mergeCol);
			mergeCol = getParamPos(mergeCol);		
			//�ж�κϲ�ʱ��Ҫ��ʼ����ʼ����		
			startRow = mergeRow;				
			endRow = mergeRow;				
			
			//�ϲ��Ļ��Ǵӵڶ��п�ʼ���е�					
			for(var i =  (parseInt(mergeRow) - parseInt(fillstart));i<(SingleeReport.GetRows(SHEET_OUTPUT)-parseInt(mergeRow));i++)
			{
				previewRowValue = SingleeReport.GetCellString(mergeCol,startRow,SHEET_OUTPUT);
				thisRowValue = SingleeReport.GetCellString(mergeCol,endRow,SHEET_OUTPUT);
				//���бȽϲ��ϲ���Ԫ��															
				if((thisRowValue!=previewRowValue))
				{						
					if(((endRow-1)!=startRow))
					{						
						SingleeReport.MergeCells(mergeCol,startRow,mergeCol,(endRow-1));
					}
					startRow = endRow;	
					endRow++;				
				}
				else
				{						
					endRow++;
				}	
			}
		}		
		else if(SingleeReport.GetCellString(COL_SUM_START,param_hh,SHEET_DEFINE).substring(0,1)==("ȫ"))
		{
			mergeflag = SingleeReport.GetCellString(COL_SUM_START,param_hh,SHEET_DEFINE).substring(0,10);		
			mergeCol = SingleeReport.GetCellString(COL_SUM_START,param_hh,SHEET_DEFINE).substring(10);
			mergeRow = getNumPos(mergeCol);
			mergeCol = getParamPos(mergeCol);		
			//�ж�κϲ�ʱ��Ҫ��ʼ����ʼ����		
			startRow = mergeRow;				
			endRow = mergeRow;			
			//�ϲ��Ļ��Ǵӵڶ��п�ʼ���е�			
			for(var i = (parseInt(mergeRow) - parseInt(fillstart));i<(SingleeReport.GetRows(SHEET_OUTPUT)-parseInt(mergeRow));i++)
			{				
				previewRowValue = SingleeReport.GetCellString(mergeCol,startRow,SHEET_OUTPUT);
				thisRowValue = SingleeReport.GetCellString(mergeCol,endRow,SHEET_OUTPUT);
				//���бȽϲ��ϲ���Ԫ��														
				if((thisRowValue!=previewRowValue))
				{						
					if(((endRow-1)!=startRow))
					{						
						MergeLeftRight(mergeCol,startRow,(endRow-1));		
					}
					startRow = endRow;
				}					
				endRow++;					
			}
		}
		param_hh++;
	}	
	//---------------------------------------------------------------------------	
	//----------------------�������--------------------------------------------
	var vPos = "<%=filename%>".lastIndexOf(".")
	var ReportName = "<%=filename%>".substring(vPos-3,vPos);
	var TotalRows = SingleeReport.GetRows(SHEET_OUTPUT);
	var TotalCols = SingleeReport.GetCols(SHEET_OUTPUT);
	var i;	//ѭ������
	var vTemp;	//����ֵ	

	if(ReportName=='501'||ReportName=='611')  //���501��611�ʻ�������ϸ����黮����
	{

		for(i=1;i<TotalRows;i++)
		{
			vTemp = SingleeReport.GetCellString(1,i,SHEET_OUTPUT);	
				
			if(vTemp=="�ʽ��ʺ�")
			{				

				SingleeReport.DrawGridLine(1,i,TotalCols,i,5,4,-1);
				SingleeReport.DrawGridLine(1,i,1,i,3,1,-1); //������ֵ3��ʾ����ұ����
            			SingleeReport.DrawGridLine(2,i,2,i,3,1,-1);
            			SingleeReport.DrawGridLine(3,i,3,i,3,1,-1);
            			SingleeReport.MergeCells(4,i,9,i);
			}
			else if(vTemp=="�ʻ��ϼ�")
			{
				SingleeReport.DrawGridLine(1,i,TotalCols,i,5,4,-1);
				SingleeReport.SetRowHeight(1,SingleeReport.GetRowHeight(1,i,SHEET_OUTPUT)*2,i,SHEET_OUTPUT);
			}
			else if(vTemp=="����")
				SingleeReport.DrawGridLine(1,i,TotalCols,i,5,4,-1);
			
		}	
	}
	else if(ReportName=='518'||ReportName=='618'||ReportName=='619'||ReportName=='620') //ȫ���ʲ���Ϻϼ���Ŀ�����߼Ӵ�
	{
		for(i=1;i<TotalRows;i++)
		{
			vTemp = SingleeReport.GetCellString(3,i,SHEET_OUTPUT);	
			if(vTemp=="�ϼ�")
			{
				SingleeReport.DrawGridLine(1,i,TotalCols,i,4,3,-1);
          			SingleeReport.DrawGridLine(1,i,TotalCols,i,5,4,-1);			
			}
		}			
	}
	else if(ReportName=='505')	//�ʽ��ʻ�������ϼ����������߼Ӵ�
	{
		for(i=1;i<TotalRows;i++)
		{
			vTemp = SingleeReport.GetCellString(1,i,SHEET_OUTPUT);	
			if(vTemp=="�ϼ�")
			{
				SingleeReport.DrawGridLine(1,i,TotalCols,i,4,3,-1);
          			SingleeReport.DrawGridLine(1,i,TotalCols,i,5,4,-1);			
			}
		}	
	}
	else if(ReportName=='405')	//���񱨸�"������"�����Ӵ�
	{
		for(i=1;i<TotalRows;i++)
		{
			vTemp = SingleeReport.GetCellString(1,i,SHEET_OUTPUT);	
			if(vTemp=="������")
			{
				SingleeReport.SetCellFontStyle(1,i,SHEET_OUTPUT,2);			
			}
		}	
	}

	
	//---------------------------------------------------------------------------	
	SingleeReport.ShowSheetLabel (0, SHEET_OUTPUT);	//���ص�һ�ű�ҳ��ҳǩ
	SingleeReport.style.visibility="visible";
</script>
<FORM name="theform" method="post" action="noparam_input.jsp">
<input type="hidden" name="filename" value="<%=filename%>">
<input type="hidden" name="paramZBArray" value="<%=request.getParameter("paramZBArray")%>">
<input type="hidden" name="paramFlagArray" value="<%=request.getParameter("paramFlagArray")%>">
<input type="hidden" name="defaultValueArray" value="<%=request.getParameter("defaultValueArray")%>">
</FORM>
</BODY>
</HTML>
