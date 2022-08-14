<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.dao.*,enfo.crm.intrust.*,enfo.crm.webreport.*,enfo.crm.tools.*,enfo.crm.vo.*,enfo.crm.system.*,java.math.*,java.util.*" %>
<%@ include file="/includes/operator.inc" %>
<%

//获得对象及结果集
BenifitorLocal benifitor = EJBFactory.getBenifitor();
BenifitorVO vo = new BenifitorVO();
DictparamLocal dicparam = EJBFactory.getDictparam();
DictparamVO dicvo = new DictparamVO();

String temp_content;
List list;
Map map;
//获得页面传递变量
String q_contract_sub_bh = Utility.trimNull(request.getParameter("q_contract_sub_bh"));
String q_cust_no = Utility.trimNull(request.getParameter("q_cust_no"));
String q_cust_name = Utility.trimNull(request.getParameter("q_cust_name"));
String q_productCode = Utility.trimNull(request.getParameter("q_productCode"));
String q_product_name=Utility.trimNull(request.getParameter("q_product_name"));
Integer q_productId = Utility.parseInt(request.getParameter("q_productId"),overall_product_id);
Integer begin_date = Utility.parseInt(request.getParameter("begin_date"),new Integer(0));
Integer end_date = Utility.parseInt(request.getParameter("end_date"),new Integer(0));

String sql="CALL SP_QUERY_TBENIFITOR_MODIUNCHECK(100,1,0,,,"+ q_productId +","+q_product_name+","+input_operatorCode+","+q_cust_name+
			","+q_contract_sub_bh+",0,"+begin_date+","+end_date+")";
//String fillzb = request.getParameter("fillZBArray");
//填充的单元格
String fillzb="1,7,a3,b3,c3,d3,e3,f3,g3,h3,i3,j3,k3,l3";
String[] sqlArray = CellHelper.splitString(sql,";");
String[] fillZBArray = CellHelper.splitString(fillzb,";");
StringBuffer resultBuffer = new StringBuffer();
String filename1 = request.getParameter("filename");
filename1 = "/webreport/Cells/" +filename1;
boolean bSuccess = false;

if(request.getMethod().equals("POST")){
	String result = request.getParameter("dataarray");
	System.err.println(result+"==========================resutlt");
	String[] coldata = result.split("@");
String[] serial_no_total = coldata[0].split("α");
String[] contract_sub_bh_total = coldata[1].split("α");
String[] cust_no_total = coldata[2].split("α");
String[] cust_name_total = coldata[3].split("α");
String[] card_id_total = coldata[4].split("α");
String[] bank_id_total = coldata[5].split("α");
String[] bank_sub_name_total = coldata[6].split("α");
String[] bank_province_total = coldata[7].split("α");
String[] bank_city_total =  coldata[8].split("α");
String[] bank_acct_total = coldata[9].split("α");
String[] cust_acct_name_total = coldata[10].split("α");
String[] acct_chg_reason_total = coldata[11].split("α");

for(int i=0;i<serial_no_total.length;i++){//循环更新需要更新的数据
	vo.setSerial_no( new Integer(serial_no_total[i].trim()));

	dicvo.setType_id(new Integer(1103));
	list = dicparam.listDictparamAllIntrust(dicvo);
	for(int j=0;j<list.size();j++){
		map = (Map)list.get(j);
		temp_content = map.get("TYPE_CONTENT")+"-"+map.get("TYPE_VALUE");
		
		if(bank_id_total[i].trim().equals(temp_content)){
			vo.setBank_id(Utility.trimNull(map.get("TYPE_VALUE")));
			break;
		}
	}
	
	dicvo.setType_id(new Integer(9999));
	list = dicparam.listDictparamAllIntrustProvince(dicvo);	
	for(int j=0;j<list.size();j++){
		map = (Map)list.get(j);
		temp_content = map.get("TYPE_CONTENT")+"-"+map.get("TYPE_VALUE");
		if(bank_province_total[i].trim().equals(temp_content)){
			vo.setBank_province(Utility.trimNull(map.get("TYPE_VALUE")));
			vo.setBank_id(Utility.trimNull(map.get("TYPE_VALUE")));
			break;
		}
	}
	vo.setBank_sub_name(bank_sub_name_total[i].trim());
	vo.setBank_acct(bank_acct_total[i].trim());
	vo.setCust_acct_name(cust_acct_name_total[i].trim());
	vo.setModi_bank_date(new Integer(Utility.getCurrentDate()));
	vo.setAcct_chg_reason(acct_chg_reason_total[i].trim());
	vo.setBank_province(bank_province_total[i].trim());
	vo.setBank_city("");
	vo.setBonus_flag(new Integer(1));
	vo.setBonus_rate(new BigDecimal(0.00));
	vo.setInput_man(input_operatorCode);
	benifitor.save1(vo);
}
	bSuccess = true;
}

%>
<HTML>
<HEAD>
<title>收益账户变更批量编辑</title>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<LINK href="/includes/default.css" type=text/css rel=stylesheet>
<BASE TARGET="_self">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="0">
<SCRIPT LANGUAGE="vbscript" SRC="/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/default.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/report.js"></SCRIPT>
</HEAD>
<BODY leftMargin=0 topMargin=0 rightmargin=0>
<OBJECT id=SingleeReport style="LEFT: 0px; TOP: 0px; width=100% ; height=90%;visibility:hidden" 
				CODEBASE="/includes/cellweb5.cab#Version=5,2,4,921"
				classid="clsid:3F166327-8030-4881-8BD2-EA25350E574A" VIEWASTEXT>
				<PARAM NAME="_Version" VALUE="65536">
				<PARAM NAME="_ExtentX" VALUE="17526">
				<PARAM NAME="_ExtentY" VALUE="10774">
				<PARAM NAME="_StockProps" VALUE="0">				
</OBJECT>

<script language=javascript>
<% 
if (bSuccess)
{%>
window.returnValue = 1;
window.close();
<%
}else{
%>
<%@ include file="/webreport/define.inc" %>
	SingleeReport.OpenFile ("<%=filename1%>","");
	rp_checkOpen(SingleeReport.GetFileName());
	//读参数定义页
	SingleeReport.SetCurSheet(1);	//读取表格输出页到内存
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
	int rowCursor = 3;	
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
	content = fillZBArray[n];//查询语句
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
		if(sqlArray[n].indexOf("CALL")>=0){
			rowset =  Cell.queryProc(sqlArray[n]);
		}else{
			rowset =  Cell.querySql(sqlArray[n]);
		}
		if(rowset.getMetaData()!=null)
		{
			metdata = rowset.getMetaData();
			colcount = metdata.getColumnCount();//取得查询出来的列的数量
			rowcount = Cell.getRowCount();//取得查询出来的行数
			%>
			SingleeReport.SetRows(<%=rowcount+3%>,1);
			ColumnTypeArray = new Array(); 			
			<%for(colCursor=1;colCursor<=colcount;colCursor++)
			{%>
				ColumnTypeArray[<%=colCursor-1%>] = '<%=metdata.getColumnType(colCursor)%>';//获取列中的数据类型		4 |12	
			<%
			}
			//
			resultBuffer = new StringBuffer();
			String colvalue="";
			int nIndex=-1;
			int rIndex=-1;
			while(rowset.next())//封装数据
			{		  
				for(colCursor=1;colCursor<=colcount;colCursor++)
				{
					colvalue=Utility.trimNull(rowset.getString(colCursor));//得到单元格列中的数据
					colvalue=colvalue.trim();
					//按照\N分隔字符 去掉回车符。


			java.util.regex.Pattern p = java.util.regex.Pattern.compile("\\s*|\t|\r|\n");
            java.util.regex.Matcher m = p.matcher(colvalue);

            colvalue = m.replaceAll("");

			System.err.println(colvalue+"============colvalue");

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
			var xArray = '<%=resultBuffer.toString()%>'.split("β");//按行分割
			xlength = xArray.length - 1;
			yArray = new Array();													
			for(i=0;i<xlength;i++)
			{
				yArray[i] = xArray[i].split("α");//数据结果集
				
			}														
<%if(method==1)//纵向填充
{%>
			//--------------------------------------------------								
			posArray = '<%=content%>'.split(",");	//将坐标字符串根据逗号分开成为一个数组---需要填充的表位置
			colArray = new Array();
			for(var i=0;i<posArray.length;i++)
			{
				colArray[i] = getParamPos(posArray[i]);		//转换成列坐标数组				
			}
			<%if(rowCursor==3)	{%>					
				rowstart = getNumPos(posArray[0]); //这里取的第一个填充坐标的行坐标
				fillstart = getNumPos(posArray[0]); 
				total_row_count = parseInt(total_row_count) + <%=rowcount%>;
			<%} %>	
			
			//特殊处理没有结果集返回的时候	
				<%if(rowcount==0)  {%>
					rowstart = parseInt(rowstart) +1;		//如果没有结果集返回要留一个空行					
				<%}%>

			//填充前还要复制行高（CELL没有自动处理）
			var RowHeight = SingleeReport.GetRowHeight(0,rowstart,1);
			//--------------------------------						
			//--------------------循环填充数据------------------------------
			for(i=0;i<xlength;i++)
			{	
				//填充前还要复制行高（CELL没有自动处理）
				SingleeReport.SetRowHeight(0,RowHeight,rowstart,1);
				for(j=0;j<colArray.length;j++)
				{	
					var colnum = j+1;//实际往表中添加的列
					var rownum = i+3;//实际往表中添加的行
					SingleeReport.S(colnum,rownum,1,yArray[i][j]);
					if(colnum==6){//受益付款银行
						SingleeReport.SetDroplistCell(colnum, rownum, 1, "<%=Argument.getDictParamSelect(1103,"{call SP_QUERY_TDICTPARAM(?)}").replaceAll("\n", "\\\\n")%>", 4);
						<%
						dicvo.setType_id(new Integer(1103));
						list = dicparam.listDictparamAllIntrust(dicvo);
						for(int i=0;i<list.size();i++){
						map = (Map)list.get(i);
						temp_content = map.get("TYPE_CONTENT")+"-"+map.get("TYPE_VALUE");%>
							if(yArray[i][j]==<%=map.get("TYPE_VALUE")%>){
								SingleeReport.S(colnum,rownum,1,"<%=temp_content%>");							
							}
						<%}%>
					}else if(colnum==8){//开户行所在省
						SingleeReport.SetDroplistCell(colnum, rownum, 1,"<%=Argument.getDictParamSelect(9999,"{call SP_QUERY_TDICTPARAM_9999(?)}").replaceAll("\n", "\\\\n")%>", 4);
						<%
						dicvo.setType_id(new Integer(9999));
						list = dicparam.listDictparamAllIntrustProvince(dicvo);	
						for(int i=0;i<list.size();i++){
						map = (Map)list.get(i);
						temp_content = map.get("TYPE_CONTENT")+"-"+map.get("TYPE_VALUE");%>
							if(yArray[i][j]==<%=map.get("TYPE_VALUE")%>){
								SingleeReport.S(colnum,rownum,1,"<%=temp_content%>");							
							}
						<%}%>
					}					
				}
			 }
<%
}			
		}	
	}
}			
	%>	
	//---------------------------------------------------------------------------------------------	
	//处理单元格宽度	
	
	//---------------------------------------------------------------------------	
	//---------------------------------------------------------------------------	
	SingleeReport.ShowSheetLabel (0, SHEET_OUTPUT);	//隐藏第一张表页的页签
	SingleeReport.style.visibility="visible";
<%}%>
function save(){
	var totalCols = SingleeReport.GetCols(1);//所有的列数
	var totalRows = SingleeReport.GetRows(1);//所有的行数
	var dataArray ='';
	for(i = 1;i<totalCols;i++){	//遍历列，将每一列的字段写到一个数组中去
		for(j = 3;j<totalRows;j++){
				dataArray = dataArray +SingleeReport.GetCellString(i,j,1)+" α" ;
		}
			dataArray = dataArray + "@";
	}
//按@分割没一列的数据
	document.theform.dataarray.value = dataArray;
	document.theform.submit();
}
</SCRIPT>

<TABLE border="0" width="100%">
	<TBODY>
		<TR>
			<td align="right">			
				
				<button type="button"  class="rbutton4"  name="btnCreateReport" id="btnCreateReport"  title="保存"  onclick="javascript:save();">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;保存</button>&nbsp;&nbsp;
				</td>
		</TR>
	</TBODY>
</TABLE>
<FORM name="theform" method="post" action="account_change_batch_edit.jsp">
<input type="hidden" name="dataarray" value="">
<input type="hidden" name="filename" value="<%=filename1%>">
<input type="hidden" name="paramZBArray" value="<%=request.getParameter("paramZBArray")%>">
<input type="hidden" name="paramFlagArray" value="<%=request.getParameter("paramFlagArray")%>">
<input type="hidden" name="defaultValueArray" value="<%=request.getParameter("defaultValueArray")%>">
</FORM>
</BODY>
</HTML>
<%benifitor.remove();dicparam.remove();%>