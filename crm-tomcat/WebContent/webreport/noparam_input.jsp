<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.webreport.*,enfo.crm.tools.*" %>
<%@ include file="/includes/operator.inc" %>
<%
String cllParamStr = request.getParameter("cllParamStr");
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
<%
int hh = 3;//定义页起始行坐标，与COL_PARAM_HH对应
String filename = CellHelper.trimNull(request.getParameter("filename"),request.getContextPath()+"/webreport/Cells/108.CLL");
%>

function openFile() 
{	
	var returnValue = SingleeReport.OpenFile ("<%=filename%>","");
	rp_checkFileStatus(returnValue);	
	//读参数定义页
	SingleeReport.SetCurSheet(SHEET_INPUT);	//读取参数定义页到内存
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
	var cllParamArray = "<%=cllParamStr%>".split("β");//页面传入的查询参数
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
		//------------------填充页面传入参数值-----------------------------
		if(defaultValueList.length == cllParamArray.length-1){
			SingleeReport.S(pos1,pos2,SHEET_INPUT,cllParamArray[<%=i%>]);
		}else{
			rp_alert("查询页面参数个数与cll文件不匹配！");
			return false;
		}
	<%}%>		
	//----------------------------------------------------------------	
	if(!debug)
		SingleeReport.ShowSheetLabel (0, SHEET_INPUT);	//隐藏第一张表页的页签		
	//生成报表	
	CreateReport();
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
		temp = temp.toUpperCase();	//将字符串中的字母都转换成大写的	
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
				//根据参数名称将存储过程语句中对应的参数用参数坐标对应的值替换掉					
				procedure = procedure.replace(paramArray[i],resultValue);						
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
	//parent.centerFrame.cols="0,*";   
	//parent.leftshow = false;
	//最后得到可执行的存储过程语句---procedure数组  
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
              <td valign="top" class="g1">正在生成报表，<br>
                请稍候... </td>
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