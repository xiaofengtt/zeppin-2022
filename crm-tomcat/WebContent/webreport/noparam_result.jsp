<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.webreport.*,enfo.crm.tools.*" %>
<%@ include file="/includes/operator.inc" %>
<%
String sql = request.getParameter("sqlArray");
String fillzb = request.getParameter("fillZBArray");
String[] sqlArray = CellHelper.splitString(sql,";");
String[] fillZBArray = CellHelper.splitString(fillzb,";");

StringBuffer resultBuffer = new StringBuffer();

int hh = 3;//定义页起始行坐标，与COL_PARAM_HH对应
String filename = CellHelper.trimNull(request.getParameter("filename"),request.getContextPath()+"/webreport/Cells/108.CLL");
%>
<HTML>
<HEAD>
<title>报表管理系统</title>
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
	//设置表头、页眉	
	var printRow = COL_PARAM_HH; //取参数值的行坐标
	var leftValue,rightValue;
	while(SingleeReport.GetCellString(COL_PRINT_NAME,printRow,SHEET_DEFINE)!="")
	{	
		leftValue = SingleeReport.GetCellString(COL_PRINT_NAME,printRow,SHEET_DEFINE);		
		rightValue = SingleeReport.GetCellString(COL_PRINT_PARAM,printRow,SHEET_DEFINE);
		printLeftRight(SingleeReport,leftValue,rightValue);		
		printRow++;		
	}	
	//设置打印进纸方向,十分之一毫米	
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
              <td valign="top" class="g1">正在生成报表，<br>
                请稍候... </td>
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
				<button type="button"  class="rbutton5"  name="btnPreview" title="打印预览" onclick="javascript:setPrint(1);">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;打印预览</button>&nbsp;&nbsp;
				<button type="button"  class="rbutton6"  name="btnPrintReport" title="打印报表" onclick="javascript:setPrint(0);">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;打&nbsp;&nbsp;印</button>&nbsp;&nbsp;
				<button type="button"  class="rbutton8"  name="btnExportReport" title="导出报表" onclick="javascript:exportReport();">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;导出报表</button>&nbsp;&nbsp;
				<button type="button"  class="rbutton10"  name="btnExportReport" title="关闭" onclick="javascript:window.close();">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;关&nbsp;&nbsp;闭</button>&nbsp;&nbsp;
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
	//读参数定义页
	SingleeReport.SetCurSheet(SHEET_OUTPUT);	//读取表格输出页到内存
	//-----------------------------------------------------------------
	<%
	int sqlindex;	//现改为根据逗号截取而不是SUBSTRING
	//填充方式-----现在填充方式还是没有处理！！！
	int method = 1; //默认为相对坐标
	//填充表格线样式
	int style = 7;//默认值
	//填充坐标字符串
	String content = "";
	//SQL语句执行到第几行,这个参数很重要，起始行等于填充坐标定义起始行！！！
	int rowCursor = hh;	
	%>
	<%//结果集相关参数！！！		
	sun.jdbc.rowset.CachedRowSet rowset = null;	
	java.sql.ResultSetMetaData metdata = null;	
	int colcount = 0;	//结果集记录的字段数	
	int colCursor = 1;	//结果集循环起始记录号
	int rowcount = 0;	//结果集的长度	
	%>

	//需要填充的坐标数组
	var posArray ;	//坐标数组
	//用于纵向填充
		var colArray ; //列坐标数组，用于纵向填充	
		var rowstart ;  //行坐标填充起始行，这里取的第一个填充坐标的行坐标，这个参数很重要！！！
	//用于横向填充
		var rowArray ; //行坐标数组，用于横向填充
		var colstart ;  //列坐标填充起始行，这里取的第一个填充坐标的列坐标，这个参数很重要！！！	
	var total_row_count = 0;//所有结果集的长度：包括存储过程和SQL语句，起始长度等于0
	var fillstart = 0;	//合并单元格时根据这个值定循环次数
	//新的填充方法，采用JS数组循环填充
	var xArray,yArray,xlength;  //结果集按行转换成数组
	var ColumnTypeArray ; 	//结果集返回字段的类型，这里只判断是否为金额还是字符型
	//-----------------------------------------------------------------------------
	var def_col,def_row;  //预定义参数的纵坐标和横坐标
//-----------------------执行填充：纵向填充---------------------------------
<%
for(int n=0;n<sqlArray.length;n++)
{	
	content = fillZBArray[n];
	//取填充方法
	sqlindex = content.indexOf(",");	
	method = CellHelper.parseInt(content.substring(0,sqlindex),1);		
	content = content.substring(sqlindex+1);
	//取填充表格线样式
	sqlindex = content.indexOf(",");	
	style = CellHelper.parseInt(content.substring(0,sqlindex),7);
	content = content.substring(sqlindex+1);

	//判断是SQL语句还是预定义字符串
	if(sqlArray[n].indexOf("CALL")>=0||sqlArray[n].indexOf("SELECT")>=0)
	{	
		//判断是存储过程还是SQL		
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
//					colvalue=colvalue.trim();//去字符串首尾空格
					
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
					
					resultBuffer.append("α");
				}
				resultBuffer.append("β");								
			}
			%>			
			//将结果集存入JS			
			var xArray = '<%=resultBuffer.toString()%>'.split("β");
			xlength = xArray.length - 1;
			yArray = new Array();													
			for(i=0;i<xlength;i++)
			{
				yArray[i] = xArray[i].split("α");								
			}														
<%if(method==1||method==2)//纵向填充
{%>
			//--------------------------------------------------								
			posArray = '<%=content%>'.split(",");	//将坐标字符串根据逗号分开成为一个数组
			colArray = new Array();
			for(var i=0;i<posArray.length;i++)
			{
				colArray[i] = getParamPos(posArray[i]);		//转换成列坐标数组				
			}			
			<%if(rowCursor==hh)	{%>					
				rowstart = getNumPos(posArray[0]); //这里取的第一个填充坐标的行坐标
				fillstart = getNumPos(posArray[0]); 
				total_row_count = parseInt(total_row_count) + <%=rowcount%>;
			<%} else {%>
				//执行一条存储过程后total_row_count要加上结果集的长度
				if(total_row_count>0)										
					rowstart = parseInt(getNumPos(posArray[0]))+ parseInt(total_row_count)-1;
				else
					rowstart = parseInt(getNumPos(posArray[0]));	
				total_row_count = parseInt(total_row_count) + <%=rowcount%>;
				
			<%}%>					
			//一次性插入所有行,因为有默认的空白行，所以只要插入rowcount-1行。
			<%if(method==1) {%>															
				SingleeReport.InsertRow((parseInt(rowstart)+1),<%=rowcount-1%>,SHEET_OUTPUT);
			<%}%>
			//特殊处理没有结果集返回的时候	
				<%if(rowcount==0)  {%>
					rowstart = parseInt(rowstart) +1;		//如果没有结果集返回要留一个空行					
				<%}%>	
			//填充前还要复制行高（CELL没有自动处理）
			var RowHeight = SingleeReport.GetRowHeight(0,rowstart,SHEET_OUTPUT);
			//--------------------------------						
			//--------------------循环填充数据------------------------------
			for(i=0;i<xlength;i++)
			{	
				//填充前还要复制行高（CELL没有自动处理）
				SingleeReport.SetRowHeight(0,RowHeight,rowstart,SHEET_OUTPUT);
				for(j=0;j<colArray.length;j++)
				{												
						
							//需要判断该字段的类型，如果是金额型的要在千分位加逗号
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
else if(method==0)//相对坐标填充
{%>
			//--------------------------------------------------
			posArray = '<%=content%>'.split(",");	//将坐标字符串根据逗号分开成为一个数组
			colArray = new Array();
			for(var i=0;i<posArray.length;i++)
			{				
				colArray[i] = getParamPos(posArray[i]);		//转换成列坐标数组								
			}			
			rowstart = getNumPos(posArray[0]); //这里取的第一个填充坐标的行坐标					
			//--------------------循环填充数据------------------------------
			for(i=0;i<xlength;i++)
			{	
				for(j=0;j<colArray.length;j++)
				{												
						
							//需要判断该字段的类型，如果是金额型的要在千分位加逗号
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
else if(method==33)//横向填充
{%>
			posArray = '<%=content%>'.split(",");	//将坐标字符串根据逗号分开成为一个数组
			rowArray = new Array();
			for(var i=0;i<posArray.length;i++)
			{
				rowArray[i] = getNumPos(posArray[i]);		//转换成行坐标数组				
			}				
			colstart = getParamPos(posArray[0]); //这里取的第一个填充坐标的列坐标					
			//一次性插入所有行,因为有默认的空白行，所以只要插入rowcount-1行。	
							
			SingleeReport.InsertCol((parseInt(colstart)+1),<%=rowcount-1%>,SHEET_OUTPUT);						
	
			//--------------------循环填充数据------------------------------
			for(i=0;i<xlength;i++)
			{	
				for(j=0;j<rowArray.length;j++)
				{												

							//需要判断该字段的类型，如果是金额型的要在千分位加逗号
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
		//-------------------------------还要处理“制表人”等预定义参数----------------------
	%>	
		def_col = getParamPos('<%=content%>');
		def_row = getNumPos('<%=content%>');
		<%	
		if(method==1)	{ %>	//相对坐标								
			def_row = parseInt(def_row) + parseInt(rowstart) - parseInt(fillstart) - 1;						
		<%}%>								
		SingleeReport.S(def_col,def_row,SHEET_OUTPUT,'<%=sqlArray[n]%>');	
		//有CELL公式的话还要处理公式！
		SingleeReport.SetFormula(def_col,def_row,SHEET_OUTPUT,'<%=sqlArray[n]%>');
	<%		  	
	}
	rowCursor++;	//执行完一条SQL就加1
}			
	%>	
	//---------------------------------------------------------------------------------------------	
	//处理单元格宽度	
	SetCellWidth(SingleeReport,SHEET_OUTPUT);	
	//-------------------------------填充好后还要合并相同值的单元格-------------------------------------
	//取参数定义页是否需要合并单元格
	var param_hh = COL_PARAM_HH;	//参数定义页行号	
	var mergeflag;	//汇总定义标志
	var mergeCol;   //合并的列坐标
	var mergeRow;   //合并的行坐标
	var previewRowValue,thisRowValue;  //上一行的值和这一行的值，用来判断是否需要合并
	var startRow,endRow;  //合并起始行坐标和结束行坐标	
	//当汇总定义有值的时候才进行合并
	while(SingleeReport.GetCellString(COL_SUM_START,param_hh,SHEET_DEFINE)!="")	
	{
	 	//进行判断---是一般合并还是全值合并
	 	if(SingleeReport.GetCellString(COL_SUM_START,param_hh,SHEET_DEFINE).substring(0,1)==("值"))
	 	{			
			mergeflag = SingleeReport.GetCellString(COL_SUM_START,param_hh,SHEET_DEFINE).substring(0,9);		
			mergeCol = SingleeReport.GetCellString(COL_SUM_START,param_hh,SHEET_DEFINE).substring(9);
			mergeRow = getNumPos(mergeCol);
			mergeCol = getParamPos(mergeCol);		
			//有多次合并时需要初始化起始坐标		
			startRow = mergeRow;				
			endRow = mergeRow;				
			
			//合并的话是从第二行开始进行的					
			for(var i =  (parseInt(mergeRow) - parseInt(fillstart));i<(SingleeReport.GetRows(SHEET_OUTPUT)-parseInt(mergeRow));i++)
			{
				previewRowValue = SingleeReport.GetCellString(mergeCol,startRow,SHEET_OUTPUT);
				thisRowValue = SingleeReport.GetCellString(mergeCol,endRow,SHEET_OUTPUT);
				//进行比较并合并单元格															
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
		else if(SingleeReport.GetCellString(COL_SUM_START,param_hh,SHEET_DEFINE).substring(0,1)==("全"))
		{
			mergeflag = SingleeReport.GetCellString(COL_SUM_START,param_hh,SHEET_DEFINE).substring(0,10);		
			mergeCol = SingleeReport.GetCellString(COL_SUM_START,param_hh,SHEET_DEFINE).substring(10);
			mergeRow = getNumPos(mergeCol);
			mergeCol = getParamPos(mergeCol);		
			//有多次合并时需要初始化起始坐标		
			startRow = mergeRow;				
			endRow = mergeRow;			
			//合并的话是从第二行开始进行的			
			for(var i = (parseInt(mergeRow) - parseInt(fillstart));i<(SingleeReport.GetRows(SHEET_OUTPUT)-parseInt(mergeRow));i++)
			{				
				previewRowValue = SingleeReport.GetCellString(mergeCol,startRow,SHEET_OUTPUT);
				thisRowValue = SingleeReport.GetCellString(mergeCol,endRow,SHEET_OUTPUT);
				//进行比较并合并单元格														
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
	//----------------------特殊表处理--------------------------------------------
	var vPos = "<%=filename%>".lastIndexOf(".")
	var ReportName = "<%=filename%>".substring(vPos-3,vPos);
	var TotalRows = SingleeReport.GetRows(SHEET_OUTPUT);
	var TotalCols = SingleeReport.GetCols(SHEET_OUTPUT);
	var i;	//循环参数
	var vTemp;	//特殊值	

	if(ReportName=='501'||ReportName=='611')  //针对501、611帐户交易明细表分组划粗线
	{

		for(i=1;i<TotalRows;i++)
		{
			vTemp = SingleeReport.GetCellString(1,i,SHEET_OUTPUT);	
				
			if(vTemp=="资金帐号")
			{				

				SingleeReport.DrawGridLine(1,i,TotalCols,i,5,4,-1);
				SingleeReport.DrawGridLine(1,i,1,i,3,1,-1); //最后参数值3表示清除右表格线
            			SingleeReport.DrawGridLine(2,i,2,i,3,1,-1);
            			SingleeReport.DrawGridLine(3,i,3,i,3,1,-1);
            			SingleeReport.MergeCells(4,i,9,i);
			}
			else if(vTemp=="帐户合计")
			{
				SingleeReport.DrawGridLine(1,i,TotalCols,i,5,4,-1);
				SingleeReport.SetRowHeight(1,SingleeReport.GetRowHeight(1,i,SHEET_OUTPUT)*2,i,SHEET_OUTPUT);
			}
			else if(vTemp=="日期")
				SingleeReport.DrawGridLine(1,i,TotalCols,i,5,4,-1);
			
		}	
	}
	else if(ReportName=='518'||ReportName=='618'||ReportName=='619'||ReportName=='620') //全体资产组合合计条目上面线加粗
	{
		for(i=1;i<TotalRows;i++)
		{
			vTemp = SingleeReport.GetCellString(3,i,SHEET_OUTPUT);	
			if(vTemp=="合计")
			{
				SingleeReport.DrawGridLine(1,i,TotalCols,i,4,3,-1);
          			SingleeReport.DrawGridLine(1,i,TotalCols,i,5,4,-1);			
			}
		}			
	}
	else if(ReportName=='505')	//资金帐户管理表，合计上下两条线加粗
	{
		for(i=1;i<TotalRows;i++)
		{
			vTemp = SingleeReport.GetCellString(1,i,SHEET_OUTPUT);	
			if(vTemp=="合计")
			{
				SingleeReport.DrawGridLine(1,i,TotalCols,i,4,3,-1);
          			SingleeReport.DrawGridLine(1,i,TotalCols,i,5,4,-1);			
			}
		}	
	}
	else if(ReportName=='405')	//财务报告"报告书"字样加粗
	{
		for(i=1;i<TotalRows;i++)
		{
			vTemp = SingleeReport.GetCellString(1,i,SHEET_OUTPUT);	
			if(vTemp=="报告书")
			{
				SingleeReport.SetCellFontStyle(1,i,SHEET_OUTPUT,2);			
			}
		}	
	}

	
	//---------------------------------------------------------------------------	
	SingleeReport.ShowSheetLabel (0, SHEET_OUTPUT);	//隐藏第一张表页的页签
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
