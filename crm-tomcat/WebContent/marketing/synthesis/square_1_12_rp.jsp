<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.web.*,java.io.*"%>
<%@ page import="enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.marketing.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>
<%
DeployLocal deploy = EJBFactory.getDeploy();

String sQuery = request.getParameter("sQuery");
String[] paras = Utility.splitString(sQuery, "$");

String sy_type1="";
String sy_type2="";
String sy_type3="";
String sy_type4="";
String sy_type5="";
String sy_type6="";
String sy_type9="";
String bank_id ="";
String jk_type="";
String sy_date ="";
int prov_flag =0;
String prov_level ="";
String contract_sub_bh = "";
int query_flag = 0;
String cust_name ="";
int fp_flag = 0;
Integer sub_product_id =new Integer(0);
Integer bonus_flag = new Integer(0);
Integer link_man = new Integer(0);
Integer product_id = new Integer(0);
if (paras.length>0 && !"".equals(sQuery)){
	product_id = Utility.parseInt(paras[0].trim(), new Integer(0));
	sy_type1=paras[1].trim();
	sy_type2=paras[2].trim();
	sy_type3=paras[3].trim();
	sy_type4=paras[4].trim();
	sy_type5=paras[5].trim();
	sy_type6=paras[6].trim();
	sy_type9=paras[7].trim();
	bank_id =paras[8].trim();
	jk_type=paras[9].trim();
	sy_date =paras[10].trim();
	prov_flag =Utility.parseInt(paras[11].trim(),0);
	prov_level =paras[12].trim();
	contract_sub_bh = paras[13].trim();
	cust_name =paras[14].trim();
	fp_flag = Utility.parseInt(paras[15].trim(),0);
	sub_product_id = Utility.parseInt(paras[16].trim(),new Integer(0));
	query_flag = Utility.parseInt(paras[17].trim(),0);
	bonus_flag =Utility.parseInt(paras[18].trim(),new Integer(0));
	link_man =Utility.parseInt(paras[19].trim(),new Integer(0));
}

int firstFlag = Utility.parseInt(request.getParameter("firstFlag"),0);

String sy_type =  sy_type1+" $"+sy_type2+" $"+sy_type3+" $"+sy_type4+" $"+sy_type5+" $"+sy_type6+" $"+sy_type9+" $";
DeployVO vo = new DeployVO();
vo.setBook_code(input_bookCode);
vo.setBank_id(bank_id);
vo.setProduct_id(product_id);
vo.setSy_type(sy_type);
vo.setJk_type(jk_type);
//2005-7-27
vo.setFp_flag(new Integer(fp_flag));
vo.setInput_man(input_operatorCode);
//deploy.setSy_date(sy_date);
vo.setProv_flag(new Integer(prov_flag)); 
vo.setProv_level(prov_level);
vo.setCust_name(cust_name);
vo.setContract_sub_bh(contract_sub_bh);
vo.setSub_product_id(sub_product_id);
Utility.debug("guifeng:"+bonus_flag);
vo.setBonus_flag(bonus_flag);
vo.setLink_man(link_man);
sun.jdbc.rowset.CachedRowSet rowset = null;	
java.sql.ResultSetMetaData metdata = null;	
//int query_flag = Utility.parseInt(request.getParameter("query_flag"),0);
//2005-7-26 
vo.setInput_man(input_operatorCode);
if (query_flag == 1)
	rowset=deploy.getDeployOutputResult(vo, sy_date);
%>
<HTML>
<HEAD>
<TITLE>收益分配数据导出</TITLE>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<LINK href="/includes/default.css" type=text/css rel=stylesheet>

</HEAD>
<SCRIPT LANGUAGE="vbscript" SRC="/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/default.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/calendar.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/audit.js"></SCRIPT>
<script language=javascript>

function advancedQuery()
{
	var ret = showModalDialog('shyfphb_query_condition.jsp?sQuery=<%=sQuery%>&product_id=<%=product_id%>', '', 'dialogWidth:750px;dialogHeight:450px;status:0;help:0');
	if(ret != null)
	{
	   disableAllBtn(true);
	    
	   	location = 'square_1_12_rp.jsp?firstFlag=1&sQuery='+ret ;
	}
}

function selectAll1()
{
	if(document.theform.sy_type6.checked=true)
	{
		document.theform.sy_type1.checked=true;
		document.theform.sy_type2.checked=true;
		document.theform.sy_type3.checked=true;
		document.theform.sy_type4.checked=true;
		document.theform.sy_type5.checked=true;
	}
}
function StartQuery()
{	
	if(!sl_checkChoice(document.theform.product_id, "产品名称")) return false;
	product_id=document.theform.product_id.value;
	if(document.theform.sy_type1.checked)
  		document.theform.sytype1.value="111601";
  	if(document.theform.sy_type2.checked)
  		document.theform.sytype2.value="111602";
  	if(document.theform.sy_type3.checked)
  		document.theform.sytype3.value="111603";
  	if(document.theform.sy_type4.checked)
  		document.theform.sytype4.value="111604";
  	if(document.theform.sy_type5.checked)
  		document.theform.sytype5.value="111605";			
	bank_id=document.theform.bank_id.value;
	jk_type = document.theform.jk_type.value;
	if(document.theform.sy_date_picker.value!="")
	{
		if(!sl_checkDate(document.theform.sy_date_picker,"分配日期"))	return false;
		syncDatePicker(document.theform.sy_date_picker, document.theform.sy_date);
		
	}
	sy_date = document.theform.sy_date.value;
	prov_level = document.theform.prov_level.value;
	document.theform.btnQuery.disabled = true;
	waitting.style.visibility='visible';
	location = 'square_1_12_rp.jsp?product_id='+product_id+'&sytype1='+document.theform.sytype1.value+'&sytype2='+document.theform.sytype2.value+'&sytype3='+document.theform.sytype3.value+'&sytype4='+document.theform.sytype4.value+'&sytype5='+document.theform.sytype5.value+'&bank_id='+bank_id+'&jk_type='+jk_type+'&sy_date='+sy_date+'&prov_level='+prov_level
	                            +"&contract_sub_bh="+document.theform.contract_sub_bh.value+"&query_flag=1&cust_name="+document.theform.cust_name.value+"&fp_flag="+document.theform.fp_flag.value;
	return true;
}
function refreshPage()
{
	StartQuery();
}
function load_row(obj)
{
	obj.S(1,4,0,"合同编号");
	obj.S(2,4,0,"受益编号");
	obj.S(3,4,0,"受益人名称");
	obj.S(4,4,0,"受益金额");
	obj.S(5,4,0,"证件类别");
	obj.S(6,4,0,"证件号码");
	obj.S(7,4,0,"收款银行");
	obj.S(8,4,0,"银行帐户");
	obj.S(9,4,0,"收益帐户名称");
	obj.S(10,4,0,"发行期利息");
	obj.S(11,4,0,"申购期利息");
	obj.S(12,4,0,"中间收益");
	obj.S(13,4,0,"到期收益");
	obj.S(14,4,0,"到期本金");
	obj.S(15,4,0,"兑付期利息");
	obj.S(16,4,0,"扣税");	
	obj.S(17,4,0,"赎回金额");	
	obj.S(18,4,0,"收益合计金额");		
}
function exportReport()
{	
    
	var obj = document.theform.DCellWeb1;
	var name = obj.GetCellString(1,1,0);	
	showModalDialog('transaction_export.jsp?name='+ name,obj,'dialogTop:120px;dialogLeft:500px;dialogWidth:360px;dialogHeight:250px;status:0;help:0');					
}
function refreshPage()
{
	StartQuery();
}
</script>
<BODY class="BODY" <%if(firstFlag==0){%> 
onLoad="javascript:advancedQuery();" <%}%>
>
<%@ include file="/includes/waiting.inc"%>	
<form name="theform">
<input type="hidden" name="subflag" value="">
<input type="hidden" name="book_code" value="<%=input_bookCode%>">
<input type="hidden" name="outporttype" value="2">
<input type=hidden name='sytype1' value=''>
<input type=hidden name='sytype2' value=''>
<input type=hidden name='sytype3' value=''>
<input type=hidden name='sytype4' value=''>
<input type=hidden name='sytype5' value=''>

<TABLE height="100%" cellSpacing=0 cellPadding=0 width="100%" border=0>
	<TR>
		<TD vAlign=top align=left>
		<TABLE cellSpacing=0 cellPadding=4 width="100%" align=center border=0>

			<TR>
				<TD>
				<table border="0" width="100%" cellspacing="0" cellpadding="0">
					<tr>
						<td><IMG height=28 src="/images/member.gif" width=32 align=absMiddle border=0><b><%=menu_info%></b></td>						
					</tr>
					
					<!--<tr>
				        <td align=right>
						全选</td>
						<td colspan="3" align="left"><input <%if(sy_type1.equals("111601")&&sy_type2.equals("111602")&&sy_type3.equals("111603")&&sy_type4.equals("111604")&&sy_type5.equals("111605")) out.print("checked");%> onkeydown="javascript:nextKeyPress(this)" type="checkbox" class="flatcheckbox" onFocus="javascript:selectAll1();"  name="sy_type6">&nbsp;&nbsp;&nbsp;
					      发行期利息<input <%if(sy_type1.equals("111601")) out.print("checked");%>  onkeydown="javascript:nextKeyPress(this)" type="checkbox" class="flatcheckbox" value="111601" name="sy_type1" >&nbsp;&nbsp;&nbsp;
					      中间收益<input <%if(sy_type2.equals("111602")) out.print("checked");%> onkeydown="javascript:nextKeyPress(this)" type="checkbox" class="flatcheckbox" value="111602" name="sy_type2" >&nbsp;&nbsp;&nbsp;
					      到期收益<input <%if(sy_type3.equals("111603")) out.print("checked");%> onkeydown="javascript:nextKeyPress(this)" type="checkbox" class="flatcheckbox" value="111603" name="sy_type3" >&nbsp;&nbsp;&nbsp;
					      兑付期利息<input <%if(sy_type4.equals("111604")) out.print("checked");%> onkeydown="javascript:nextKeyPress(this)" type="checkbox" class="flatcheckbox" value="111604" name="sy_type4" >&nbsp;&nbsp;&nbsp;
					      到期本金<input <%if(sy_type5.equals("111605")) out.print("checked");%> onkeydown="javascript:nextKeyPress(this)" type="checkbox" class="flatcheckbox" value="111605" name="sy_type5" >&nbsp;&nbsp;&nbsp;
					  </td>
						 <td  align="right">兑付标志:
						</td>
						<td  align="left">
							<SELECT style="width: 120" onkeydown="javascript:nextKeyPress(this)" size="1" name="fp_flag">
							<%=Argument.getFpFlagOptions(fp_flag)%>
						</SELECT>
						</td>
					</tr>
					
					<tr>
						<td align=right>
						银行:</td>
						<td  align="left"><SELECT onkeydown="javascript:nextKeyPress(this)" size="1" style="width: 142" name="bank_id">
							<%=Argument.getBankOptions(bank_id)%>
						</SELECT>&nbsp;</td>
						 <td  align="right">
						付款方式:</td>
						<td  align="left"><SELECT style="width: 120" onkeydown="javascript:nextKeyPress(this)" size="1" name="jk_type">
							<%=Argument.getJkTypeOptions(jk_type)%>
						</SELECT>
						</td>
						<td  align="right">
						客户名称:</td>
						<td  align="left">
							<input type="text" name="cust_name" value="<%=cust_name%>" onkeydown="javascript:nextKeyPress(this);" size="20">
						</td></tr>
						<tr>
				        <td align="right" >分配日期:</td>
						<td  align="left"><INPUT TYPE="text" style="width: 120" NAME="sy_date_picker" <%if(sy_date!=null){%> value=""<%}%>>
						<INPUT TYPE="button" value="▼" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.sy_date_picker,theform.sy_date_picker.value,this);" tabindex="13">
						<INPUT TYPE="hidden" NAME="sy_date"   value="">&nbsp;&nbsp;
				        </td>
						<td  align="right">受益级别:</td>
						<td  align="left"><SELECT size="1" name="prov_level" onkeydown="javascript:nextKeyPress(this)" style="width: 120px">
									<%=Argument.getProvlevelOptions(prov_level)%>
								</SELECT>&nbsp;&nbsp;</td>
						 <td  align="right">
						合同编号:</td>
						<td  align="left">
							<input type="text" name="contract_bh" value="<%//=contract_bh%>" onkeydown="javascript:nextKeyPress(this);" size="20">
						</td>
					</tr>
				-->
					
					<tr>
						<td  align=right>
						<%if (input_operator.hasFunc(menu_id, 108)){%>
						<button type="button"  class="xpbutton4" name="btnQuery" accessKey=f onClick="javascript:advancedQuery();">查询(<u>F</u>)</button>&nbsp;&nbsp;
						<%}%>
						<%if (rowset != null){%>
						<%if (input_operator.hasFunc(menu_id, 107)){%>
						<button type="button"  class="xpbutton4"  name="btnPreview" title="打印预览" onclick="javascript:setPrint(document.theform.DCellWeb1,1);">打印预览</button>&nbsp;&nbsp;
						<%}%>
						<%if (input_operator.hasFunc(menu_id, 100)){%>
						<button type="button"  class="xpbutton3"  name="btnExportReport" title="导出报表" onclick="javascript:exportReport();">导出</button>&nbsp;&nbsp;
						<%}}%>
						</td>
					</tr>
				</table>
				<tr>
						<td >
						<hr noshade color="#808080" size="1">
						</td>
					</tr>
				<TR>
					<TD>
				<%if(query_flag == 1){%>
<P>
<OBJECT id=DCellWeb1 style="left: 10px; top: 65px" width="100%" 
	classid=clsid:3F166327-8030-4881-8BD2-EA25350E574A height="500" CODEBASE="/includes/cellweb5.cab#Version=5,2,4,921">
    <param name="_Version" value="65536">
    <param name="_ExtentX" value="22728">
    <param name="_ExtentY" value="13070">
    <param name="_StockProps" value="0"></OBJECT>
</P>
<SCRIPT LANGUAGE='javascript'>
var ColumnTypeArray ; 	//结果集返回字段的类型，这里只判断是否为金额还是字符型
var colArray ; //列坐标数组，用于纵向填充	
var xArray,yArray,xlength;  //结果集按行转换成数组
var rowstart=5 ;  //行坐标填充起始行，这里取的第一个填充坐标的行坐标，这个参数很重要！！！
<%
int colcount = 0;	//结果集记录的字段数	
int rowcount = 0;	//结果集的长度	
int colCursor = 1;	//结果集循环起始记录号
	
int rowCursor = 1;
StringBuffer resultBuffer = new StringBuffer();

if (rowset!=null && rowset.getMetaData()!=null) {
	metdata = rowset.getMetaData();
	colcount = metdata.getColumnCount();
	/*
	BusiBean
	//	计算记录数
	public int getRows() throws Exception {
		rowset.last();
		rowCount = rowset.getRow(); // rowCount 总共记录的行数
		rowset.beforeFirst();
		return rowCount;
	}
	*/
	rowset.last();
	int i = rowset.getRow();
	rowset.beforeFirst();

	rowcount = i;			
%>
ColumnTypeArray = new Array(); 
<%
	for(colCursor=1;colCursor<=colcount;colCursor++) {
		int numtype=7;
		if(colCursor==3)
			numtype=1;
			
		if(colCursor==5 || colCursor==11 ||colCursor==12 || colCursor==13 ||colCursor==14 ||colCursor==15 ||colCursor==16 ||colCursor==17||colCursor==18||colCursor==19) 
			numtype=6;
%>
	ColumnTypeArray[<%=colCursor-1%>] = '<%=numtype%>';		
<%}		
		resultBuffer = new StringBuffer();
		int icurrent=0;
		try {		
			String colvalue="";int nIndex=-1;int rIndex=-1;
			
			while (rowset.next()) {	
				for(colCursor=1;colCursor<=colcount;colCursor++) {   //if(colCursor!=9)
					    colvalue=Utility.trimNull(rowset.getString(colCursor));
					//else
					    //colvalue=Format.formatBankNo(Utility.trimNull(rowset.getString(colCursor)));
					colvalue=colvalue.trim();
					
					StringBuffer tempBuffer = new StringBuffer(colvalue);
					
					nIndex=colvalue.indexOf("\n");
					rIndex=colvalue.indexOf("\r");
					
					if (nIndex>0)
						tempBuffer.deleteCharAt(nIndex);	
					if (rIndex>0)
						tempBuffer.deleteCharAt(rIndex);

					colvalue=tempBuffer.toString();
					
					resultBuffer.append(colvalue);
					resultBuffer.append("α");
				}
				resultBuffer.append("β");								
			}
			rowset.close();
			rowset = null;	
		} catch (Exception e)	 {
		
		}
%>			
			//将结果集存入JS			
			var xArray = '<%=resultBuffer.toString()%>'.split("β");
			xlength = xArray.length - 1;
			yArray = new Array();													
			for (i=0;i<xlength;i++) {
				yArray[i] = xArray[i].split("α");								
			}
<%			
}
if (rowCursor==1) {%>					
			loadThree(document.theform.DCellWeb1,'收益分配表',19,'','<%=Argument.getProductName(product_id)%>');
			load_row(document.theform.DCellWeb1);
			document.theform.DCellWeb1.SetRows(7,0);
			document.theform.DCellWeb1.InsertRow((document.theform.DCellWeb1.GetRows(0)),<%=rowcount-1%>,0);				
<%
}%>
			colArray = new Array();
			
			for(var i=0;i<document.theform.DCellWeb1.getCols(0);i++) 
				colArray[i] = i+1;

			//alert(colArray.length);
			//--------------------循环填充数据------------------------------
			for(i=0;i<xlength;i++) {	
				//填充前还要复制行高（CELL没有自动处理）
				document.theform.DCellWeb1.SetRowHeight(1,25,rowstart,0);							
				
				for(j=1;j<colArray.length;j++)
				{
					//复制单元格格式
					if(i>0)
						document.theform.DCellWeb1.SetCellNumType(colArray[j],rowstart,0,document.theform.DCellWeb1.GetCellNumType(colArray[j],rowstart,0));			
							//需要判断该字段的类型，如果是金额型的要在千分位加逗号
					//alert("ColumnTypeArray["+j+"]="+ColumnTypeArray[j]+"colArray["+j+"]="+colArray[j]);
					if(ColumnTypeArray[j]=='6' && yArray[i][j]!="")	
					{
						if(yArray[i][j]=='0.00')
						document.theform.DCellWeb1.S(colArray[j-1],rowstart,0,"");
						else
						document.theform.DCellWeb1.D(colArray[j-1],rowstart,0,parseFloat(yArray[i][j]));		
					}
					else if(ColumnTypeArray[j]=='1' && yArray[i][j]!="")
					{
						document.theform.DCellWeb1.D(colArray[j-1],rowstart,0,parseInt(yArray[i][j]));						
					}
					else 
					{							
						document.theform.DCellWeb1.S(colArray[j-1],rowstart,0,yArray[i][j]);						
					}											
					
					if((j>=10 && j<=17) || j==4)
					{
						document.theform.DCellWeb1.SetCellAlign (j,i+5,0,2);	
						document.theform.DCellWeb1.SetCellNumType(j,i+5,0,1);
						document.theform.DCellWeb1.SetCellDigital(j,i+5,0,2);
						document.theform.DCellWeb1.SetCellSeparator(j,i+5,0,2);
					}
					if(j>2 && j<10 && j!=4)
					{
						document.theform.DCellWeb1.SetCellAlign (j,i+5,0,1);	
					}
				}								
				rowstart++;
			 }
			 for(j=0;j<colArray.length;j++)
			{
				document.theform.DCellWeb1.SetColWidth(1,document.theform.DCellWeb1.GetColBestWidth(colArray[j]),colArray[j],0)	;
			}
		</script><%}%>
				</TD>
				</TR>
		</TABLE>
		</TD>
	</TR>
</TABLE>
</form><%@ include file="/includes/foot.inc"%>
</BODY>
</HTML>
<%deploy.remove();%>