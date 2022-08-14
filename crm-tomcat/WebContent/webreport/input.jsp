<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.webreport.*,enfo.crm.tools.*" %>
<%@ include file="/includes/operator.inc" %>
<HTML>
<HEAD>
<title>报表管理系统</title>
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
int hh = 3;//定义页起始行坐标，与COL_PARAM_HH对应
String filename = CellHelper.trimNull(request.getParameter("filename"),request.getContextPath()+"/webreport/Cells/108.CLL");
%>

function openFile() 
{	
 	var i =	SingleeReport.OpenFile ("<%=filename%>","");
	rp_checkOpen(SingleeReport.GetFileName());	
	//读参数定义页
	SingleeReport.SetCurSheet(SHEET_INPUT);	//读取参数定义页到内存

	if(i==1)//（对打开的文件进行判断，如果文件打开返回"1"那么说明文件时正确的，否则“生产报表”按钮变灰色）
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
	//根据系统标志取系统定义的列表
	%>
	//定义参数位置		
	var pos1,pos2;  //pos1为列标，pos2为行标	
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
		//------------------填充默认参数值-----------------------------
		SingleeReport.S(pos1,pos2,SHEET_INPUT,defaultValueList[<%=i%>]);
		//------------------------------------------------------------
	<%
	}	
	%>	
	
	//----------------------------------------------------------------	
	if(!debug)
		SingleeReport.ShowSheetLabel (0, SHEET_INPUT);	//隐藏第一张表页的页签	
}

function CleanValue()
{	
	//定义参数位置		
	var pos1,pos2;  //pos1为列标，pos2为行标	
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
	//定义参数位置		
	var pos1,pos2;  //pos1为列标，pos2为行标	
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
		//------------------填充默认参数值-----------------------------
		SingleeReport.S(pos1,pos2,SHEET_INPUT,defaultValueList[<%=i%>]);
		//------------------------------------------------------------
	<%
	}	
	%>
	SingleeReport.ReDraw();
}


var CELL_ID = <%=Utility.parseInt((Integer)session.getAttribute("SESSION_CELL_ID"),0)%>;	//产品单元全局变量

/**  
 * 选择产品单元树 
 */
function openTree()
{

	var v = showModalDialog('<%=request.getContextPath()%>/client/analyse/report_tree.jsp', '', 'dialogWidth:500px;dialogHeight:550px;status:0;help:0');

	if (v != null)
	{
		CELL_ID = v;
	}
}

//生成报表
function CreateReport()
{		
	var PARAM_NUM = COL_PARAM_HH;	//参数定义页起始行号，由于下面需要循环所以重新定义一个变量
	var COL_SQL_HH = COL_PARAM_HH;	//SQL语句定义行号,因为就这个页面用到所以没放入全局变量。默认SQL语句起始行号等于参数定义起始行号
	var temp,sql,procedure,other;	//定义SQL语句或存储过程名称或其他系统参数
	var sqlArray = "";	//定义SQL语句或存储过程数组或其他系统参数	
	//------------------循环取得所有参数名称和参数坐标，只有存在参数坐标的参数名称才会加到数组中-----------------------------------------
	var paramArray = new Array();	//参数名称数组
	var paramZBArray = new Array();	//参数坐标数组
	var nullValue;	//非空标志
	var paramValue; //参数录入页上的参数坐标对应的参数值
	var pos1,pos2;	//参数坐标对应的列坐标和行坐标
	var pos ,resultValue;	//参数值中的截取位和返回的有用值
	var defaultValueArray = new Array(); //这个值的用途是传选择过后的参数至结果页，结果页按“查询条件”时用到	
		
	var cellFlag = false;	//统计单元标志 
	if(SingleeReport.GetCellString(COL_PRINT_PARAM,PARAM_NUM,SHEET_DEFINE)=="统计单元")
	{
		
		cellFlag = true;
		if(CELL_ID==0)
		{
			sl_alert("请选择产品单元！");
			return false;
		}
	}
		
		
	while(SingleeReport.GetCellString(COL_PARAM,PARAM_NUM,SHEET_DEFINE)!="")
	{	
		if(SingleeReport.GetCellString(COL_PARAM_ZB,PARAM_NUM,SHEET_DEFINE)!="")	//只有参数名称对应有参数坐标的时候
		{

			paramArray[PARAM_NUM-COL_PARAM_HH] = SingleeReport.GetCellString(COL_PARAM,PARAM_NUM,SHEET_DEFINE); //将参数名称存入数组，如"@P_PRODUCT_CODE"
			paramZBArray[PARAM_NUM-COL_PARAM_HH] = SingleeReport.GetCellString(COL_PARAM_ZB,PARAM_NUM,SHEET_DEFINE); //将参数名称对应的坐标存入数组，如"C1"
		}
		PARAM_NUM++;
	}
	//-------------------首先根据非空标志进行判断-------------------------
	for(var i=0;i<paramArray.length;i++)
	{	
		//根据参数坐标从数据录入页取得对应的值
		pos1 = getParamPos(paramZBArray[i]);
		pos2 = getNumPos(paramZBArray[i]);			 
		paramValue = SingleeReport.GetCellString(pos1,pos2,SHEET_INPUT);
		defaultValueArray[i] = SingleeReport.GetCellString(pos1,pos2,SHEET_INPUT);				
		nullValue = SingleeReport.GetCellString(COL_PARAM_NULL,COL_PARAM_HH+i,SHEET_DEFINE);
		if(nullValue!=""&&paramValue=="")
		{			
			alert("参数："+ SingleeReport.GetCellString(1,pos2,SHEET_INPUT) +" 不能为空！");
			SingleeReport.MoveToCell(pos1,pos2);
			return false;
		}				
	}
	
	//-------------------循环取SQL语句定义列-----------------------------------------------------------------------------------------
	while(SingleeReport.GetCellString(COL_SQL,COL_SQL_HH,SHEET_DEFINE)!="")
	{
		//判断单元格是否含有公式，有的话取公式表达式
		if(SingleeReport.IsFormulaCell(COL_SQL, COL_SQL_HH, SHEET_DEFINE)>0)
			temp = SingleeReport.GetFormula(COL_SQL,COL_SQL_HH,SHEET_DEFINE);
		else
			temp = SingleeReport.GetCellString(COL_SQL,COL_SQL_HH,SHEET_DEFINE);	//取参数并判断是SQL语句还是存储过程还是一般字符串
	    
	    if(temp.indexOf("dbo.")<0)	 //针对中原数据库系统区别大小写，当调用函数时，脚本保持cell里内容，不进行大写转换
		   temp = temp.toUpperCase();	//将字符串中的字母都转换成大写的	 
		//alert(temp+"    "+temp.indexOf("SELECT"));
		if(temp.indexOf("CALL")>=0||temp.indexOf("SELECT")>=0)	//该字符串中有CALL字样或SELECT字样的，则判断它为SQL存储过程
		{
			procedure = temp;	//取存储过程语句		
							
			//首先替换系统默认值，如"@@BOOKCODE","@@OPERATOR"等
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
				sl_alert("请先选择产品单元！");
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
				//支持数值为负数的情况
				//else if(pos==0)
				//	resultValue = " ";
				else				
					resultValue = defaultValueArray[i];										
				//根据参数名称将存储过程语句中对应的参数用参数坐标对应的值替换掉					
				procedure = procedure.replaceAll(paramArray[i],resultValue);						
			}
				
			//得到可执行的存储过程语句，将它存入存储过程数组	
			if(sqlArray == "")		
				sqlArray =  procedure;
			else
				sqlArray = sqlArray + ";" + procedure ;	
				
				
																
		}
		else	//一般字符串的时候
		{
			other = temp;	//取其他系统参数
			//首先替换系统默认值，如"@@BOOKCODE","@@OPERATOR"等
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
				sl_alert("请先选择产品单元！");
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
				//根据参数名称将存储过程语句中对应的参数用参数坐标对应的值替换掉									
				other = replaceAll(other,paramArray[i],resultValue );						
			}			
			//得到最终其他系统参数，将它存入或其他系统参数数组			
			if( sqlArray == "")		
				sqlArray = other;
			else
				sqlArray = sqlArray + ";" + other ;				
		}
		COL_SQL_HH++;	
			
	}
	
	//--------------------填充坐标数组(由于后面JAVA无法得到所以由这里传入)--------------------------
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
	//隐藏左边的菜单	
	//parent.centerFrame.cols="5,*";   
	//parent.leftshow = false;
	//最后得到可执行的存储过程语句---procedure数组
	document.theform.sqlArray.value = sqlArray;
	document.theform.fillZBArray.value = fillZBArray;
	document.theform.defaultValueArray.value = defaultValueArray; 
	
	/*if(cellFlag)	//多表页特殊处理，转到result_mulit_sheet.jsp
	{		
		
		document.theform.action = "result_multi_sheet.jsp";
	}
	else*///此文件不存在，先注释掉
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
			<td >			
				<button type="button"  class="rbutton0"  name="btnSelectReport" title="选择报表" onclick="javascript:Select();">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;选择报表</button>&nbsp;&nbsp;
				<button type="button"  class="rbutton1"  name="btnInputValue" title="查询条件" disabled onclick="javascript:history.back();">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;查询条件</button>&nbsp;&nbsp;
				<button type="button"  class="rbutton2"  name="btnCleanValue" title="清除条件" onclick="javascript:CleanValue();">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;清除条件</button>&nbsp;&nbsp;
				<button type="button"  class="rbutton3"  name="btnDefaultValue" title="默认条件" onclick="javascript:DefaultValue();">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;默认条件</button>&nbsp;&nbsp;
				<button type="button"  class="rbutton4"  name="btnCreateReport" id="btnCreateReport"  title="生成报表"  onclick="javascript:CreateReport();">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;生成报表</button>&nbsp;&nbsp;
				<%if (input_operator.hasFunc(menu_id, 150))
{%>	<button type="button"  class="rbutton5"  name="btnPreview" title="打印预览" disabled onclick="javascript:setPrint(1);">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;打印预览</button>&nbsp;&nbsp;
				<button type="button"  class="rbutton6"  name="btnPrintReport" disabled title="打印报表" onclick="javascript:setPrint(0);">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;打&nbsp;&nbsp;印</button>&nbsp;&nbsp;<%}%>
				<button type="button"  class="rbutton7"  name="btnChooseProduct" title="产品单元"  onclick="javascript:openTree();">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;产品单元</button>&nbsp;&nbsp;
				<%if (input_operator.hasFunc(menu_id, 160))
{%>		<button type="button"  class="rbutton8"  name="btnExportReport" title="导出报表"  disabled onclick="javascript:exportReport();">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;导出报表</button>&nbsp;&nbsp;<%}%>
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
