<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.dao.*,enfo.crm.intrust.*,enfo.crm.webreport.*,enfo.crm.tools.*,enfo.crm.vo.*,enfo.crm.system.*,java.math.*,java.util.*" %>
<%@ include file="/includes/operator.inc" %>
<%

//��ö��󼰽����
BenifitorLocal benifitor = EJBFactory.getBenifitor();
BenifitorVO vo = new BenifitorVO();
DictparamLocal dicparam = EJBFactory.getDictparam();
DictparamVO dicvo = new DictparamVO();

String temp_content;
List list;
Map map;
//���ҳ�洫�ݱ���
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
//���ĵ�Ԫ��
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
String[] serial_no_total = coldata[0].split("��");
String[] contract_sub_bh_total = coldata[1].split("��");
String[] cust_no_total = coldata[2].split("��");
String[] cust_name_total = coldata[3].split("��");
String[] card_id_total = coldata[4].split("��");
String[] bank_id_total = coldata[5].split("��");
String[] bank_sub_name_total = coldata[6].split("��");
String[] bank_province_total = coldata[7].split("��");
String[] bank_city_total =  coldata[8].split("��");
String[] bank_acct_total = coldata[9].split("��");
String[] cust_acct_name_total = coldata[10].split("��");
String[] acct_chg_reason_total = coldata[11].split("��");

for(int i=0;i<serial_no_total.length;i++){//ѭ��������Ҫ���µ�����
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
<title>�����˻���������༭</title>
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
	//����������ҳ
	SingleeReport.SetCurSheet(1);	//��ȡ������ҳ���ڴ�
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
	int rowCursor = 3;	
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
	content = fillZBArray[n];//��ѯ���
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
		if(sqlArray[n].indexOf("CALL")>=0){
			rowset =  Cell.queryProc(sqlArray[n]);
		}else{
			rowset =  Cell.querySql(sqlArray[n]);
		}
		if(rowset.getMetaData()!=null)
		{
			metdata = rowset.getMetaData();
			colcount = metdata.getColumnCount();//ȡ�ò�ѯ�������е�����
			rowcount = Cell.getRowCount();//ȡ�ò�ѯ����������
			%>
			SingleeReport.SetRows(<%=rowcount+3%>,1);
			ColumnTypeArray = new Array(); 			
			<%for(colCursor=1;colCursor<=colcount;colCursor++)
			{%>
				ColumnTypeArray[<%=colCursor-1%>] = '<%=metdata.getColumnType(colCursor)%>';//��ȡ���е���������		4 |12	
			<%
			}
			//
			resultBuffer = new StringBuffer();
			String colvalue="";
			int nIndex=-1;
			int rIndex=-1;
			while(rowset.next())//��װ����
			{		  
				for(colCursor=1;colCursor<=colcount;colCursor++)
				{
					colvalue=Utility.trimNull(rowset.getString(colCursor));//�õ���Ԫ�����е�����
					colvalue=colvalue.trim();
					//����\N�ָ��ַ� ȥ���س�����


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
					
					resultBuffer.append("��");
				}
				resultBuffer.append("��");	
			}
			%>			
			//�����������JS			
			var xArray = '<%=resultBuffer.toString()%>'.split("��");//���зָ�
			xlength = xArray.length - 1;
			yArray = new Array();													
			for(i=0;i<xlength;i++)
			{
				yArray[i] = xArray[i].split("��");//���ݽ����
				
			}														
<%if(method==1)//�������
{%>
			//--------------------------------------------------								
			posArray = '<%=content%>'.split(",");	//�������ַ������ݶ��ŷֿ���Ϊһ������---��Ҫ���ı�λ��
			colArray = new Array();
			for(var i=0;i<posArray.length;i++)
			{
				colArray[i] = getParamPos(posArray[i]);		//ת��������������				
			}
			<%if(rowCursor==3)	{%>					
				rowstart = getNumPos(posArray[0]); //����ȡ�ĵ�һ����������������
				fillstart = getNumPos(posArray[0]); 
				total_row_count = parseInt(total_row_count) + <%=rowcount%>;
			<%} %>	
			
			//���⴦��û�н�������ص�ʱ��	
				<%if(rowcount==0)  {%>
					rowstart = parseInt(rowstart) +1;		//���û�н��������Ҫ��һ������					
				<%}%>

			//���ǰ��Ҫ�����иߣ�CELLû���Զ�����
			var RowHeight = SingleeReport.GetRowHeight(0,rowstart,1);
			//--------------------------------						
			//--------------------ѭ���������------------------------------
			for(i=0;i<xlength;i++)
			{	
				//���ǰ��Ҫ�����иߣ�CELLû���Զ�����
				SingleeReport.SetRowHeight(0,RowHeight,rowstart,1);
				for(j=0;j<colArray.length;j++)
				{	
					var colnum = j+1;//ʵ����������ӵ���
					var rownum = i+3;//ʵ����������ӵ���
					SingleeReport.S(colnum,rownum,1,yArray[i][j]);
					if(colnum==6){//���渶������
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
					}else if(colnum==8){//����������ʡ
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
	//����Ԫ����	
	
	//---------------------------------------------------------------------------	
	//---------------------------------------------------------------------------	
	SingleeReport.ShowSheetLabel (0, SHEET_OUTPUT);	//���ص�һ�ű�ҳ��ҳǩ
	SingleeReport.style.visibility="visible";
<%}%>
function save(){
	var totalCols = SingleeReport.GetCols(1);//���е�����
	var totalRows = SingleeReport.GetRows(1);//���е�����
	var dataArray ='';
	for(i = 1;i<totalCols;i++){	//�����У���ÿһ�е��ֶ�д��һ��������ȥ
		for(j = 3;j<totalRows;j++){
				dataArray = dataArray +SingleeReport.GetCellString(i,j,1)+" ��" ;
		}
			dataArray = dataArray + "@";
	}
//��@�ָ�ûһ�е�����
	document.theform.dataarray.value = dataArray;
	document.theform.submit();
}
</SCRIPT>

<TABLE border="0" width="100%">
	<TBODY>
		<TR>
			<td align="right">			
				
				<button type="button"  class="rbutton4"  name="btnCreateReport" id="btnCreateReport"  title="����"  onclick="javascript:save();">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;����</button>&nbsp;&nbsp;
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