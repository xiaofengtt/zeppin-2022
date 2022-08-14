<%@ page contentType="text/html; charset=GBK"  %>
<%@ page import="java.math.*,java.util.*,enfo.crm.system.*,enfo.crm.dao.*,enfo.crm.tools.*, enfo.crm.vo.*, enfo.crm.intrust.*" %>
<%@ page import="java.math.BigDecimal" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>
<%
java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//��ö���
ProductLocal product = EJBFactory.getProduct();
ProductVO vo = new ProductVO();
ContractLocal contract = EJBFactory.getContract();
ContractVO c_vo = new ContractVO();

//��ò���
Integer product_id = new Integer(Utility.parseInt(request.getParameter("product_id"), 0));
Integer item_id = new Integer(Utility.parseInt(request.getParameter("item_id"), 0));
Integer cust_id = new Integer(0);
int subflag=Utility.parseInt(request.getParameter("subflag"),0);

//��������
List list = null;
List list_rg = null;
List list_cont = null;
List list_prov = null;
Map map = new HashMap();
Map map_rg = new HashMap();
Map map_cont = new HashMap();
Map map_prov = new HashMap(); 
String intrust_type_list="";
String[] totalColumn = new String[0];

Integer intrust_flag1=new Integer(1);
String strdisplay="style='display:'";
int intrust_flag3 = 0;
int intrsut_flag2 = 0;
int intrust_flag4 = 0;
vo.setProduct_id(product_id);
list = product.load(vo);	
if(list.size() != 0){
	map = (Map) list.get(0);
	intrust_flag3 =Utility.parseInt(Utility.trimNull(map.get("INTRUST_FLAG3")),0);
	intrust_flag4 =Utility.parseInt(Utility.trimNull(map.get("INTRUST_FLAG4")),0);
	intrsut_flag2 =Utility.parseInt(Utility.trimNull(map.get("INTRUST_FLAG2")),0);
}
String iii = Utility.trimNull(map.get("PRODUCT_CODE"));
Integer depart_id = Utility.parseInt(Utility.trimNull(map.get("DEPART_ID")),new Integer(0));

if (product_id.intValue() != 0 && (subflag ==0 || subflag==1)){
	list_rg = product.rg_list(vo);
	if(list.size()!= 0){
		map_rg = (Map)list_rg.get(0);
	}
	Integer intrust_flag11 = Utility.parseInt(Utility.trimNull(map.get("INTRUST_FLAG1")),new Integer(0));
	if(intrust_flag11!=null)
		intrust_flag1=intrust_flag11;	
	if(intrust_flag1.intValue()==1)
		strdisplay="style='display:none'";
}
BigDecimal trade_tax_rate = Utility.parseDecimal(Utility.trimNull(map.get("TRADE_TAX_RATE")),new BigDecimal(0));

if(trade_tax_rate!=null)
	trade_tax_rate = trade_tax_rate.multiply(new BigDecimal(100)).setScale(3,1); 

int iCount = 0;
int iCurrent = 0;
scaption=enfo.crm.tools.LocalUtilis.language("message.switch",clientLocale);//�л�
%>

<HTML>
<HEAD>
<TITLE><%=LocalUtilis.language("menu.productListDetail",clientLocale)%> </TITLE>
<!--��Ʒ��ϸ-->
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="0">
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<!--TABLE ��ͬjs-->
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/jQuery/js/jquery-1.3.2.min.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/jQuery/tablespan/tablespan.js"></SCRIPT>
<base target="_self">
<style>
.headdarkbutton
{
	cursor:hand;
	BORDER-RIGHT: 0px solid;
    BORDER-TOP: 0px solid;
    BACKGROUND-IMAGE: url(<%=request.getContextPath()%>/images/headdark_00_01.gif);
    PADDING-BOTTOM: 0px;
    BORDER-LEFT: 0px solid;
    WIDTH: 80px;
    PADDING-TOP: 0px;
    BACKGROUND-COLOR:white;		
    BORDER-BOTTOM: 0px solid;
    HEIGHT: 20px;
}

.headbutton
{
	cursor:hand;
	BORDER-RIGHT: 0px solid;
    BORDER-TOP: 0px solid;
    BACKGROUND-IMAGE: url(<%=request.getContextPath()%>/images/head_00_01.gif);
    PADDING-BOTTOM: 0px;
    BORDER-LEFT: 0px solid;
    WIDTH: 80px;
    PADDING-TOP: 0px;
    BACKGROUND-COLOR:white;		
    BORDER-BOTTOM: 0px solid;
    HEIGHT: 20px;
}
</style>
</HEAD>
<script language=javascript>
function opencity(product_id)
{
	showModalDialog('product_city_tjd.jsp?check_flag=2&product_id=' + product_id, '', 'dialogWidth:600px;dialogHeight:500px;status:0;help:0');
	//location = 'product_city_tjd.jsp?check_flag=2&product_id=' + product_id
}
function openPeriod(product_id)
{
if(showModelessDialog('product_period_yjf.jsp?product_id=' + product_id, '', 'dialogWidth:350px;dialogHeight:420px;status:0;help:0') != null)
	{
		//sl_update_ok();
		location.reload();
	}
}
function openPeriod2(product_id)
{
if(showModalDialog('product_period_plx.jsp?product_id=' + product_id, '', 'dialogWidth:350px;dialogHeight:420px;status:0;help:0') != null)
	{
		location.reload();
	}
}
function opendate(product_id,trade_type,trade_type_name,readonly)
{
	showModalDialog('product_date2.jsp?product_id=' + product_id+'&trade_type=' + trade_type+'&trade_type_name='+trade_type_name+'&readonly='+readonly, '', 'dialogWidth:600px;dialogHeight:500px;status:0;help:0');
}
function show(parm)
{
   for(i=0;i<9;i++)
  {  
     if(i != parm)
     {	
     	eval("document.getElementById('d" + i + "').className='headdarkbutton'");
     	//eval("document.getElementById('d" + i + "').background = '<%=request.getContextPath()%>/images/headdark_00_01.gif'");
        eval("document.getElementById('r" + i + "').style.display = 'none'");
	 }
	 else
	 {	
	 	eval("document.getElementById('d" + i + "').className='headbutton'");
	    //eval("document.getElementById('d"+i+"').background = '<%=request.getContextPath()%>/images/head_00_01.gif'");
		eval("document.getElementById('r" + i + "').style.display = ''");
	 } 
  }
}
/*function showInfo(file_id)  
{
	showModalDialog('message_info.jsp?file_id=' + file_id, '', 'dialogWidth:640px;dialogHeight:600px;status:0;help:0');
}*/

function openitem(product_id)
{
	showModalDialog('product_stage_item2.jsp?readonly=1&product_id='+product_id, '', 'dialogWidth:600px;dialogHeight:500px;status:0;help:0');
}

function openitem2(product_id)
{
	showModalDialog('product_stage_item3.jsp?readonly=1&product_id='+product_id, '', 'dialogWidth:600px;dialogHeight:500px;status:0;help:0');
}
/*�����ղ�ѯ*/
function opendate5(){
	showModalDialog('topenDate_list.jsp?product_id=<%=product_id%>', '', 'dialogWidth:400px;dialogHeight:350px;status:0;help:0');
}
/*
 *���ú�ͬtable�ķ���
*/
$(document).ready(function(){  
	_w_table_lefttitle_rowspan("#table7",1);
	_w_table_lefttitle_rowspan("#table7",7);
});

function exprotExcel(tableid) //��ȡ�����ÿ����Ԫ��EXCEL��  
{  
    var curTbl = document.getElementById(tableid);  
    var oXL = new ActiveXObject("Excel.Application");  
    //����AX����excel  
    var oWB = oXL.Workbooks.Add();  
    //��ȡworkbook����  
    var oSheet = oWB.ActiveSheet;  
    //���ǰsheet  
    var Lenr = curTbl.rows.length;  
    //ȡ�ñ������  
    for (i = 0; i < Lenr; i++)  
    {  
        var Lenc = curTbl.rows(i).cells.length;  
        //ȡ��ÿ�е�����  
        for (j = 0; j < Lenc; j++)  
        {  
            oSheet.Cells(i + 1, j + 1).value = curTbl.rows(i).cells(j).innerText;  
            //��ֵ  
        }  
    }  
    oXL.Visible = true;  
    //����excel�ɼ�����  
} 

</script>
<BODY leftMargin=0 topMargin=0 rightmargin="0" class="popup-body">

<form name="theform" action="product_list_detail.jsp">  
<input type="hidden" name="subflag" value="<%=subflag%>">
<input type="hidden" name="product_id" value="<%=product_id%>">
<input type="hidden" name="item_id" value="<%=item_id%>">

<TABLE height="100%" cellSpacing=0 cellPadding=0 width="100%" border=0>
<!--  	<TR>
		<TD vAlign=top align=left>
-->
		<table border="0" width="100%" cellspacing="0" cellpadding="10">
<TBODY>
			<tr>
				<td>
				<table border="0" width="100%" cellspacing="1" cellpadding="4">
					<tr>
						<td class="popup-title">
						    <b><%=Utility.trimNull(map.get("PRODUCT_CODE"))%>-<%=Utility.trimNull(map.get("PRODUCT_NAME"))%></b>
						</td>
					</tr>
					
			</table>
		<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
        <TBODY>
        <TR  >
          
          <TD align="center" class="td-tabs" >
          	<button type="button"     id="d0"  name="btnMenu" class="<%if(subflag==0) out.print("headbutton");else out.print("headdarkbutton");%>" 
			    onclick="javascript:show(0)">&nbsp;<%=LocalUtilis.language("message.basicInformation",clientLocale)%> 
			</button><!--������Ϣ-->			
          	<button type="button"     id="d1"  name="btnMenu" class="<%if(subflag==1) out.print("headbutton");else out.print("headdarkbutton");%>" 
			    onclick="javascript:show(1)">&nbsp;<%=LocalUtilis.language("message.productIntroduction",clientLocale)%> 
			</button><!--��Ʒ���-->
          	<button type="button"     id="d2"  name="btnMenu" class="<%if(subflag==2) out.print("headbutton");else out.print("headdarkbutton");%>" 
			    onclick="javascript:show(2)">&nbsp;<%=LocalUtilis.language("message.operatingIncome",clientLocale)%> 
			</button><!--��������-->
          	<button type="button"     id="d3"  name="btnMenu" class="<%if(subflag==3) out.print("headbutton");else out.print("headdarkbutton");%>" 
			    onclick="javascript:show(3)">&nbsp;<%=LocalUtilis.language("message.contractInfo",clientLocale)%> 
			</button><!--��ͬ��Ϣ-->
          	<button type="button"     id="d4"  name="btnMenu" class="<%if(subflag==4) out.print("headbutton");else out.print("headdarkbutton");%>" 
			    onclick="javascript:show(4)">&nbsp;<%=LocalUtilis.language("message.changeDetail",clientLocale)%> 
			</button><!--�����ϸ-->			
          	<button type="button"     id="d5"  name="btnMenu" class="<%if(subflag==5) out.print("headbutton");else out.print("headdarkbutton");%>" 
			    onclick="javascript:show(5)">&nbsp;<%=LocalUtilis.language("message.PromotionInterest",clientLocale)%> 
			</button><!--�ƽ�������-->
          	<button type="button"    id="d6"  name="btnMenu" class="<%if(subflag==6) out.print("headbutton");else out.print("headdarkbutton");%>" 
			    onclick="javascript:show(6)">&nbsp;<%=LocalUtilis.language("message.subscribeConstract",clientLocale)%> 
			</button><!--�Ϲ���ͬ-->
          	<button type="button"     id="d7"  name="btnMenu" class="<%if(subflag==7) out.print("headbutton");else out.print("headdarkbutton");%>" 
			    onclick="javascript:show(7)">&nbsp;�ֲ�ͳ�� 
			</button><!--�ֲ�ͳ��-->
			<button type="button"     id="d8"  name="btnMenu" class="<%if(subflag==8) out.print("headbutton");else out.print("headdarkbutton");%>" 
			    onclick="javascript:show(8)">&nbsp;<%=LocalUtilis.language("class.formatDateLine",clientLocale)%> 
			</button><!--��Ϣ��¶-->
          </td>          
          </TR>
		
		</TBODY>
		</TABLE>
        <br/>
        <TABLE cellSpacing=1 cellPadding=1 width="100%" bgColor=#ffffff  borderColorLight=#ffffff border=0>
        <TBODY>
        
        <TR id=r0 <%if(subflag==0) out.print("style='display:'");else out.print("style='display:none'");%> bgColor=#ffffff>
          <TD height='400' valign="top">
            <TABLE class="table-popup" cellSpacing=1 cellPadding=2 width="100%" bgColor=#666666  borderColorLight=#ffffff border=0>
              <TBODY>
              <TR bgColor=#ffffff>
                <TD align=right><%=LocalUtilis.language("class.type",clientLocale)%> </TD><!--����-->
                <TD COLSPAN=3><%if(intrust_flag1.intValue()==1) out.print("��һ"); else out.print("����");%>-
				<%if(intrsut_flag2==1) out.print("ָ��"); else out.print("����");%>-
				<%=Utility.trimNull(map.get("INTRUST_TYPE1_NAME"))%>-<%=Utility.trimNull(map.get("INTRUST_TYPE_NAME"))%>-
				<%=Utility.trimNull(map.get("INTRUST_TYPE2_NAME"))%>-<%=Utility.trimNull(map.get("MANAGERTYPE_NAME"))%>
                </TD><!-- (intrust_flag1.equals(new Integer(1))?"��һ":"����") --><!-- (Utility.trimNull(map.get("INTRUST_FLAG2")).equals(new Integer(1))?"ָ��":"����") -->
			  </TR>
              <TR bgColor=#ffffff>
                <TD align=right><%=LocalUtilis.language("class.intrustFlag3",clientLocale)%> :</TD> <!--������ʽ-->
                <TD><%if(intrust_flag3==1) out.print("˽ļ"); else out.print("��ļ");%></TD>
                <TD align=right><%=LocalUtilis.language("class.instrutFlag4",clientLocale)%> :</TD><!--����Ŀ��-->
                <TD><%if(intrust_flag4==1) out.print("˽��"); else out.print("����");%></TD>&nbsp;
             </TD><!-- if(Utility.trimNull(map.get("INTRUST_FLAG4"))!=null){out.print((Utility.trimNull(map.get("INTRUST_FLAG4")).equals(new Integer(1))?"˽��":"����"));} -->
             </TR>
             <tr bgColor=#ffffff <%if(Utility.trimNull(map.get("INTRUST_TYPE1"))!=null){if(Utility.trimNull(map.get("INTRUST_TYPE1")).equals("113801")) out.print("style='display:none'");} else out.print("style='display:none'");%>>
				
				<td align="right"><%=LocalUtilis.language("class.dealTypeName",clientLocale)%> :</td><!--�Ʋ����÷�ʽ-->
				<td><%=Utility.trimNull(map.get("DEAL_TYPE_NAME"))%></td>
				<td align="right" ></td>
				<td></td>
			 </tr>
			 <tr bgColor=#ffffff>
					<td align="right"><%=LocalUtilis.language("class.currency_name",clientLocale)%> :</td><!--����-->
					<td><%=Argument.getCurrencyName1(Utility.trimNull(map.get("CURRENCY_ID")))%></td>
					<td align="right"><%=LocalUtilis.language("class.productJC",clientLocale)%> :</td><!--���-->
					<td><%=Utility.trimNull(map.get("PRODUCT_JC"))%></td>
			 </tr>
			  <tr bgColor=#ffffff>	
					<td align="right"><%=LocalUtilis.language("class.preNum",clientLocale)%> :</td><!--Ԥ�ڷ��з���-->
					<td><%=Utility.trimNull(map.get("PRE_NUM"))%></td>
					<td align="right"><%=LocalUtilis.language("class.preMoney",clientLocale)%> :</td><!--Ԥ�ڷ��н��-->
					<td><%=Utility.trimNull(Format.formatMoney(Utility.parseDecimal(Utility.trimNull(map.get("PRE_MONEY")),new BigDecimal(0))))%></td>
			</tr>	
            <tr bgColor=#ffffff>		
					<td align="right"><%=LocalUtilis.language("class.preMaxRate",clientLocale)%> :</td><!--ԤԼ������-->
					<td><%=Utility.trimZero((Utility.parseDecimal(Utility.trimNull(map.get("PRE_MAX_RATE")),new BigDecimal(0))).multiply(new BigDecimal(100)))%>%</td>
					<td align="right"><%=LocalUtilis.language("class.minMoney",clientLocale)%> :</td><!--��ͷ��н��-->
					<td><%=Utility.trimNull(Format.formatMoney(Utility.parseDecimal(Utility.trimNull(map.get("MIN_MONEY")),new BigDecimal(0))))%></td>
			</tr>
			<tr bgColor=#ffffff >
					<td align="right"><%=LocalUtilis.language("class.preStartDate",clientLocale)%> :</td><!--�ƽ���ʼ����-->
					<td><%=Format.formatDateCn(Utility.parseInt(Utility.trimNull(map.get("PRE_START_DATE")),new Integer(0)))%></td>
					<td align="right"><%=LocalUtilis.language("class.preEndDate",clientLocale)%> :</td><!--�ƽ���ֹ����-->
					<td><%=Format.formatDateCn(Utility.parseInt(Utility.trimNull(map.get("PRE_END_DATE")),new Integer(0)))%></td>
			</tr>
					
			<tr bgColor=#ffffff>
					<td align="right"><%=LocalUtilis.language("class.preCode",clientLocale)%> :</td><!--��ͬ���ǰ׺-->
					<td><%=Utility.trimNull(map.get("PRE_CODE"))%></td>
					<td align="right"><%=LocalUtilis.language("class.serviceStatusName2",clientLocale)%> :</td><!--״̬-->
					<td><%=Utility.trimNull(map.get("PRODUCT_STATUS_NAME"))%></td>
			</tr>
			<tr bgColor=#ffffff>	
					<td align="right"><%=LocalUtilis.language("class.adminManager",clientLocale)%> :</td><!--ִ�о���-->
					<td><%=Argument.getInturstOperatorName(Utility.parseInt(Utility.trimNull(map.get("ADMIN_MANAGER")),new Integer(0)))%></td>
					<td align="right"><%=LocalUtilis.language("class.periodUnit",clientLocale)%> :</td><!--��Ʒ����-->
					<td><%if(Utility.parseInt(Utility.trimNull(map.get("PERIOD_UNIT")),new Integer(0))!=null)
					        {if(Utility.parseInt(Utility.trimNull(map.get("PERIOD_UNIT")),new Integer(0)).intValue()!=0){%>
					    <%=Utility.trimNull(Utility.parseInt(Utility.trimNull(map.get("VALID_PERIOD")),new Integer(0)))%>
						<%=Argument.getProductUnitName(Utility.parseInt(Utility.trimNull(map.get("PERIOD_UNIT")),new Integer(0)))%>
						<%}else{%><%=Argument.getProductUnitName(Utility.parseInt(Utility.trimNull(map.get("PERIOD_UNIT")),new Integer(0)))%><%}}%>
					</td>
			</tr>
			<%if(user_id.intValue()==4){%>
			<tr bgColor=#ffffff>
					<td align="right"><%=LocalUtilis.language("class.adminManager",clientLocale)%> B:</td><!--ִ�о���B-->
					<td><%=Utility.trimNull(Argument.getOpName(Utility.parseInt(Utility.trimNull(map.get("ADMIN_MANAGER2")),new Integer(0))))%></td>
					<td align="right"><%=LocalUtilis.language("class.matainManager",clientLocale)%> :</td><!--�йܾ���-->
					<td><%=Utility.trimNull(Argument.getOpName(Utility.parseInt(Utility.trimNull(map.get("MATAIN_MANAGER")),new Integer(0))))%></td>
			</tr>
			<%}%>
			<tr bgColor=#ffffff>
						<td align="right"><%=LocalUtilis.language("class.infoPeriod",clientLocale)%> :</td><!--��Ϣ��¶����-->
						<td><%=Utility.trimNull(Utility.parseInt(Utility.trimNull(map.get("INFO_PERIOD")),new Integer(0)))%>
						    <%=LocalUtilis.language("message.monthes",clientLocale)%> <!--��-->&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						    <button type="button"    class="xpbutton4" accessKey=r id="btnSetcity" name="btnSetcity" onclick="javascript:openPeriod2(<%=product_id%>);">
						        <%=LocalUtilis.language("menu.infoDisclosureTime",clientLocale)%> 
							</button><!--�鿴��¶ʱ��-->
						</td> 
						<td align="right"><%=LocalUtilis.language("class.tradeTaxRate",clientLocale)%> :</td><!--Ӫҵ˰��-->
						<td><%=Utility.trimNull(trade_tax_rate)%>%</td>
					</tr>
					<%if(user_id.intValue()==4){%>
					<tr bgColor=#ffffff>
						<td align="right"><%=LocalUtilis.language("class.trustRewardCollectDate",clientLocale)%> :</td><!--���б�����ȡʱ��-->
						<td><button type="button"    class="xpbutton2" accessKey=r id="btnSetcity" name="btnSetcity" 
						    onclick="javascript:opendate('<%=product_id%>','211001','<%=enfo.crm.tools.LocalUtilis.language("class.trustRewardCollectDate",clientLocale)%>','1');"><!--���б�����ȡʱ��-->
						    <%=LocalUtilis.language("message.view",clientLocale)%> <!--�鿴--></button>
						</td>
						<td align="right"><%=LocalUtilis.language("class.incomeDistributionDate",clientLocale)%> :</td><!--�������ʱ��-->
						<td><button type="button"    class="xpbutton2" accessKey=r id="btnSetcity" name="btnSetcity" 
						    onclick="javascript:opendate('<%=product_id%>','211002','<%=enfo.crm.tools.LocalUtilis.language("class.incomeDistributionDate",clientLocale)%>','1');"><!--�������ʱ��-->
						    <%=LocalUtilis.language("message.view",clientLocale)%> <!--�鿴--></button>						
						</td>
					</tr>
					<tr bgColor=#ffffff>
						<td align="right"><%=LocalUtilis.language("class.formatDateLine",clientLocale)%> :</td><!--��Ϣ��¶ʱ��-->
						<td><button type="button"    class="xpbutton2" accessKey=r id="btnSetcity" name="btnSetcity" 
						        onclick="javascript:opendate('<%=product_id%>','211003','<%=enfo.crm.tools.LocalUtilis.language("class.formatDateLine",clientLocale)%>','1');"><!--��Ϣ��¶ʱ��-->
						        <%=LocalUtilis.language("message.view",clientLocale)%> 
							</button><!--�鿴-->
						</td>
						<td align="right"><%=LocalUtilis.language("class.taxCollectDate",clientLocale)%> :</td><!--˰����ȡʱ��-->
						<td><button type="button"    class="xpbutton2" accessKey=r id="btnSetcity" name="btnSetcity" 
						        onclick="javascript:opendate('<%=product_id%>','211004','<%=enfo.crm.tools.LocalUtilis.language("class.taxCollectDate",clientLocale) %>','1');"><!--˰����ȡʱ��-->
						        <%=LocalUtilis.language("message.view",clientLocale)%> <!--�鿴-->
						    </button>						
						</td>
					</tr>
					<%}%>		
					<tr bgColor=#ffffff>
						<td align="right"><%=LocalUtilis.language("class.benPeriod",clientLocale)%> :</td><!--�����������-->
						<td><%=Utility.trimNull(Utility.parseInt(Utility.trimNull(map.get("BEN_PERIOD")),new Integer(0)))%>
						    <%=LocalUtilis.language("message.monthes",clientLocale)%> <!--��-->&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						    <button type="button"    class="xpbutton4" accessKey=r id="btnSetcity" name="btnSetcity" onclick="javascript:openPeriod(<%=product_id%>);">
						        <%=LocalUtilis.language("class.toDistributionTime",clientLocale)%> 
							</button>
						</td><!--Ԥ�Ʒ���ʱ��-->
						<td align="right"><%=LocalUtilis.language("class.fxFee",clientLocale)%> :</td><!--���з���-->
						<td><%=Format.formatMoney(Utility.parseDecimal(Utility.trimNull(map.get("FX_FEE")),new BigDecimal(0)))%></td>
					</tr>
					<tr bgColor=#ffffff>
						<td align="right"><%=LocalUtilis.language("class.tgBankName",clientLocale)%> :</td><!--�ʽ𿪻�����-->
						<td ><%=Utility.trimNull(map.get("TG_BANK_NAME"))+Utility.trimNull(map.get("TG_BANK_SUB_NAME"))%></td>
						<td align="right"><%=LocalUtilis.language("class.tgBankAcct",clientLocale)%> :</td><!--�ʽ𿪻��˻�-->
						<td><%=Utility.trimNull(map.get("TG_BANK_ACCT"))%></td>						
					</tr>
					
					<tr bgColor=#ffffff>
						<td align="right"><%=LocalUtilis.language("class.trustRewardCollectType",clientLocale)%> :</td><!--�����˱�����ȡ��ʽ-->
						<td><%=Utility.trimNull(map.get("FPFS_NAME"))%>
						&nbsp;&nbsp;<button type="button"    name="trbtnitem" style="display: <%if (!"110504".equals(Utility.trimNull(map.get("FPFS")))){out.print("none");}%>" 
						        class="xpbutton2" accessKey=i onclick="javascript:openitem('<%=product_id%>');"><%=LocalUtilis.language("message.view",clientLocale)%> <!--�鿴-->
							</button>
						&nbsp;&nbsp;<button type="button"    name="trbtnitem2" style="display: <%if (!"110505".equals(Utility.trimNull(map.get("FPFS")))){out.print("none");}%>" 
						        class="xpbutton2" accessKey=i onclick="javascript:openitem2('<%=product_id%>');"><%=LocalUtilis.language("message.view",clientLocale)%> <!--�鿴-->
							</button>
						</td>
						<%BigDecimal manager_rate = Utility.parseDecimal(Utility.trimNull(map.get("MANAGE_RATE")),new BigDecimal(0),2,"100"); %>
						<td id="td_rate1" align="right" >
						    <%if ("110501".equals(map.get("FPFS"))) out.print(enfo.crm.tools.LocalUtilis.language("class.trustReward",clientLocale)+":");/*�����˱���*/
							  else if ("110502".equals(map.get("FPFS")) || "110503".equals(map.get("FPFS")) || "110504".equals(map.get("FPFS")) 
							  || "110512".equals(map.get("FPFS")) || "110505".equals(map.get("FPFS")) || "110509".equals(map.get("FPFS"))) out.print(enfo.crm.tools.LocalUtilis.language("class.rewardRate",clientLocale)+":");/*������*/%>
						</td>
						<td id="td_rate2" > <%if ("110501".equals(map.get("FPFS"))){%>
						<%=Format.formatMoney(Utility.parseDecimal(Utility.trimNull(map.get("MANAGE_FEE")),new BigDecimal(0)))%> 
						<%} else if ("110502".equals(map.get("FPFS")) || "110503".equals(map.get("FPFS")) || "110504".equals(map.get("FPFS")) 
						  || "110512".equals(map.get("FPFS")) || "110505".equals(map.get("FPFS")) || "110509".equals(map.get("FPFS"))){%>
						<%=Utility.trimZero(manager_rate)%>%<%}%>
						</td>
					</tr>
					<tr bgColor=#ffffff>	
						<td align="right"><%=LocalUtilis.language("class.departName",clientLocale)%> :</td><!--��Ʋ���-->
						<td ><%=Argument.getInturstDepartmentName(depart_id)%></td> 
					
						<td align="right"><%=LocalUtilis.language("class.expRate",clientLocale)%> :</td><!--Ԥ������-->
<%if(Utility.parseDecimal(Utility.trimNull(map.get("EXP_RATE1")),new BigDecimal(0))!= null && Utility.parseDecimal(
    Utility.trimNull(map.get("EXP_RATE1")),new BigDecimal(0)).toString().equals("0.0000")){%>
						<td ><%="0%"%>-<%=Format.formatPercent(Utility.parseDecimal(Utility.trimNull(map.get("EXP_RATE2")),new BigDecimal(0)),1,false)%></td>
<%}else{%>
						<td ><%=Format.formatPercent(Utility.parseDecimal(Utility.trimNull(map.get("EXP_RATE1")),new BigDecimal(0)),1,false)%>
						-<%=Format.formatPercent(Utility.parseDecimal(Utility.trimNull(map.get("EXP_RATE2")),new BigDecimal(0)),1,false)%>
						</td>
<%}%>					
					</tr>
					<tr bgColor=#ffffff>
						<td align="right"><%=LocalUtilis.language("class.createDate",clientLocale)%> :</td><!--��������-->
						<td><%=Format.formatDateCn(Utility.parseInt(Utility.trimNull(map.get("START_DATE")),new Integer(0)))%></td>
						<td align="right"><%=LocalUtilis.language("class.endDate4",clientLocale)%> :</td><!--��������-->							
						<td>
<%if((Utility.parseInt(Utility.trimNull(map.get("END_DATE")),new Integer(0)))!=null && (Utility.parseInt(Utility.trimNull(map.get("END_DATE")),new Integer(0))).intValue()>=20990101){
	out.println(enfo.crm.tools.LocalUtilis.language("message.noEndDate",clientLocale));//������
}else{ 							
	out.println(Format.formatDateCn(Utility.parseInt(Utility.trimNull(map.get("END_DATE")),new Integer(0))));
}%>						</td>								
						
					</tr>
					
					<tr bgColor=#ffffff>
						<td align="right">
							<input type="radio" value="1" disabled onkeydown="javascript:nextKeyPress(this)" name="open_flag" 
							<%if((Utility.parseInt(Utility.trimNull(map.get("OPEN_FLAG")),new Integer(0)))!=null 
							&& Utility.parseInt(Utility.trimNull(map.get("OPEN_FLAG")),new Integer(0)).intValue()==1) out.print("checked");%> 
							class="flatcheckbox"><%=LocalUtilis.language("message.open",clientLocale)%> 
							<!--����ʽ-->
						</td>
						<td>
							<input type="radio" value="2" disabled onkeydown="javascript:nextKeyPress(this)" name="open_flag" class="flatcheckbox" 
							<%if((Utility.parseInt(Utility.trimNull(map.get("OPEN_FLAG")),new Integer(0)))!=null 
							&& Utility.parseInt(Utility.trimNull(map.get("OPEN_FLAG")),new Integer(0)).intValue()!=1) out.print("checked");%>>
							<%=LocalUtilis.language("message.enclosed",clientLocale)%> 
							<!--���ʽ-->
							&nbsp;&nbsp;<%if((Utility.parseInt(Utility.trimNull(map.get("OPEN_FLAG")),new Integer(0)))!=null 
							&& Utility.parseInt(Utility.trimNull(map.get("OPEN_FLAG")),new Integer(0)).intValue()==1){%>
							<button type="button"     class="xpbutton4" accessKey=r id="btnSetcity" name="btnSetcity" onclick="javascript:opendate5();">
							<%=LocalUtilis.language("message.openDateView",clientLocale)%> </button><%}%>	
						</td><!--�����ղ�ѯ-->
						<td align="right"><input type="radio" disabled value="1" onkeydown="javascript:nextKeyPress(this)" 
						<%if((Utility.parseInt(Utility.trimNull(map.get("PRODUCT_FROM")),new Integer(0)))!=null){
						    if(Utility.parseInt(Utility.trimNull(map.get("IS_LOCAL")),new Integer(0)).intValue()==1){out.print("checked");}}%> 
						  name="product_address" checked class="flatcheckbox"><%=LocalUtilis.language("message.local",clientLocale)%> 
						</td><!--����-->
						<td><input type="radio" disabled value="2" onkeydown="javascript:nextKeyPress(this)" 
						<%if((Utility.parseInt(Utility.trimNull(map.get("PRODUCT_FROM")),new Integer(0)))!=null){
						    if(Utility.parseInt(Utility.trimNull(map.get("IS_LOCAL")),new Integer(0)).intValue()==2){out.print("checked");}}%> 
							name="product_address" class="flatcheckbox" ><%=LocalUtilis.language("message.notLocal",clientLocale)%> &nbsp;&nbsp;<!--���-->
						    <button type="button"    class="xpbutton4" accessKey=r id="btnSetcity" name="btnSetcity" onclick="javascript:opencity(<%=product_id%>);">
						       <%=LocalUtilis.language("menu.tjdView",clientLocale)%> 
							</button><!--�ƽ�ز鿴-->
						</td>
					</tr>
					<tr bgColor=#ffffff>
						<td align="right"><%=LocalUtilis.language("class.qualityLevelName",clientLocale)%> :</td><!--��������-->
						<td ><%=Utility.trimNull(map.get("QUALITY_LEVEL_NAME"))%></td>
						<td align="right"><%=LocalUtilis.language("class.summary",clientLocale)%> :</td><!--��ע��Ϣ-->
						<td ><%=Utility.trimNull(map.get("SUMMARY"))%></td>
					</tr>
		 </TBODY>
        </TABLE>
        </TD>
        </TR>   
         <TR id=r1 <%if(subflag==1) out.print("style='display:'");else out.print("style='display:none'");%> bgColor=#ffffff>
          <TD height='400' valign="top">
            <TABLE cellSpacing=1 cellPadding=4 width="100%" bgColor=#666666  borderColorLight=#ffffff border=0>
              <TBODY> 
					<%if(map.get("PRODUCT_INFO")!=null){%>
					<tr bgColor=#ffffff>						
						<td><%=Utility.trimNull(map.get("PRODUCT_INFO"))%></td>
					</tr><%}%>
				</TBODY>
        </TABLE>
        </TD>
        </TR>   
        <TR id=r2 <%if(subflag==2) out.print("style='display:'");else out.print("style='display:none'");%> bgColor=#ffffff>
         <TD height='400' valign="top">
         <div><b><%=LocalUtilis.language("message.syTotal",clientLocale)%> </b></div><!--�������-->
            <table class="tablelinecolor" id="table3" border="0" cellspacing="1" cellpadding="2" bgColor=#666666  borderColorLight=#ffffff width="100%">
					<tr class="trh">
						<td align="center" height="25"><%=LocalUtilis.language("class.allocationDate",clientLocale)%> </td><!--��������-->
						<td align="center" height="25"><%=LocalUtilis.language("class.syTypeName",clientLocale)%> </td><!--�������-->
						<td align="center" height="25" sort="num"><%=LocalUtilis.language("class.syRate",clientLocale)%> </td><!--ÿ������-->
						<td align="center" height="25" sort="num"><%=LocalUtilis.language("class.syMoney",clientLocale)%> </td><!--���η�����-->
					</tr>
					<%
iCount = 0;
Map map_qbp = new HashMap();
vo.setProduct_id(product_id);
IPageList pageList = product.queryByProductID1(vo,Utility.parseInt(sPage,1),Utility.parseInt(sPagesize,8));
List list_qbp = pageList.getRsList();
iCurrent = 0;
Iterator iterator = list_qbp.iterator();
while (iterator.hasNext())
{
	map_qbp = (Map)iterator.next();
%>
					<tr class="tr<%=(iCurrent % 2)%>">
						<td align="center" height="25"><%=Format.formatDateCn(Utility.parseInt(Utility.trimNull(map_qbp.get("SY_DATE")),new Integer(0)))%></td>
						<td class="tdh" align="center" height="25"><%=Utility.trimNull(map_qbp.get("SY_TYPE_NAME"))%></td>
						<td align="center" height="25"><%=Format.formatPercent(Utility.parseDecimal(Utility.trimNull(map_qbp.get("SY_RATE")),new BigDecimal(0)),1,false)%></td>
						<td align="right" height="25"><%=Utility.trimNull(Format.formatMoney(Utility.parseDecimal(Utility.trimNull(map_qbp.get("SY_MONEY")),new BigDecimal(0))))%></td>
					</tr>
					<%iCurrent++;
iCount++;
}%>
					<tr class="trbottom">
						<td class="tdh" align="left" height="25" colspan="4">
						<b>&nbsp;<%=LocalUtilis.language("message.total",clientLocale)%><!--�ϼ�-->&nbsp;<%=iCount%>
						&nbsp;<%=LocalUtilis.language("message.entries",clientLocale)%> <!--��--></b></td>
						<!-- <td align="center" height="25">-</td>
						<td align="center" height="25">-</td>
						<td align="center" height="25">-</td> -->
					</tr>				
				</table>
				<table border="0" width="100%">
					<tr valign="top">
						<td></td>
						<td align="right"></td>
					</tr>
				</table>
				<br/>
                <div><b><%=LocalUtilis.language("message.oprateDetails",clientLocale)%> </b></div><!--������ϸ-->
				<table class="tablelinecolor" border="0" cellspacing="1" cellpadding="2" width="100%">
					<tr class="trh" >
						<td align="center" height="25"><%=LocalUtilis.language("class.busiName",clientLocale)%> </td><!--ҵ�����-->
						<td align="center" height="25"><%=LocalUtilis.language("class.custName",clientLocale)%> </td><!--���׶Լ�-->						
						<td align="center" height="25"><%=LocalUtilis.language("class.contractID",clientLocale)%> </td><!--��ͬ���-->
						<td align="center" height="25"><%=LocalUtilis.language("class.htMoney",clientLocale)%> </td><!--�������-->
						<td align="center" height="25"><%=LocalUtilis.language("class.cwMoney",clientLocale)%> </td><!--��ǰ���-->
					</tr>
                 <%
                 	vo.setProduct_id(product_id);	
                 	List list_yz = product.querytz1(vo);
                 	Map map_yz = new HashMap();
                 	Iterator iterator_yz = list_yz.iterator();
                    while (iterator_yz.hasNext())
                    {
        				map_yz = (Map)iterator_yz.next();
                   %>
					    <tr class="tr1">
						   <td class="tdh" align="center" height="25"><%=Utility.trimNull(map_yz.get("BUSI_NAME"))%></td>
						   <td align="center" height="25"><%=Utility.trimNull(map_yz.get("CUST_NAME"))%></td>
						   <td align="center" height="25"><%=Utility.trimNull(map_yz.get("CONTRACT_SUB_BH"))%></td>
						   <td align="right" height="25"><%=Format.formatMoney(Utility.parseDecimal(Utility.trimNull(map_yz.get("HT_MONEY")),new BigDecimal(0)))%></td>						
						   <td align="right" height="25"><%=Format.formatMoney(Utility.parseDecimal(Utility.trimNull(map_yz.get("CW_MONEY")),new BigDecimal(0)))%></td>						
					    </tr>
                    <%}%>									
			    </table>
				
			</TD>
        </TR>  
        <TR id=r3 <%if(subflag==3) out.print("style='display:'");else out.print("style='display:none'");%> bgColor=#ffffff>
          <TD height='400' valign="top">
            <div>
            <b><%=LocalUtilis.language("class.salesInfo",clientLocale)%> </b>
            </div>
            <TABLE cellSpacing=1 cellPadding=4 width="100%" bgColor=#666666  borderColorLight=#ffffff border=0>
					<tr bgColor=#ffffff>
						<td align="right" width="25%"><%=LocalUtilis.language("class.factPreNum",clientLocale)%> :</td><!--ԤԼ��-->
						<td width="25%"><%if((Utility.parseInt(Utility.trimNull(map.get("FACT_PRE_NUM")),new Integer(0)))!=null){if(Utility.parseInt(Utility.trimNull(map.get("FACT_PRE_NUM")),new Integer(0)).intValue()>0){%> <%=Utility.trimNull(Utility.parseInt(Utility.trimNull(map.get("FACT_PRE_NUM")),new Integer(0)))%><%}}%></td>
						<td align="right" width="25%"><%=LocalUtilis.language("class.factPreNum2",clientLocale)%> :</td><!--ԤԼ���-->
						<td width="25%"><%=Format.formatMoney(Utility.parseDecimal(Utility.trimNull(map.get("FACT_PRE_NUM")),new BigDecimal(0)))%></td>
					</tr>
					<tr bgColor=#ffffff>
						<td align="right" width="25%"><%=LocalUtilis.language("class.totolMoney",clientLocale)%> :</td><!--��Ʒ���ʲ�-->
						<td width="25%"><%=Utility.trimNull(Format.formatMoney(Utility.parseDecimal(Utility.trimNull(map.get("TOTAL_MONEY")),new BigDecimal(0))))%></td>					
						
						<td width="25%" align="right"><%=LocalUtilis.language("class.factProductNum",clientLocale)%> :</td><!--ʵ���Ϲ�����-->
						<td width="25%"><%if((Utility.parseInt(Utility.trimNull(map.get("FACT_PRODUCT_NUM")),new Integer(0)))!=null){if(Utility.parseInt(Utility.trimNull(map.get("FACT_PRODUCT_NUM")),new Integer(0)).intValue()>0){%><%=Utility.trimNull(Utility.parseInt(Utility.trimNull(map.get("FACT_PRODUCT_NUM")),new Integer(0)))%><%}}%></td>
					</tr>
					<tr bgColor=#ffffff>
						<td width="25%" align="right"><%=LocalUtilis.language("class.factNum2",clientLocale)%> :</td><!--ʵ�ʷ��з���-->
						<td width="25%"><%=Utility.trimNull(Utility.parseInt(Utility.trimNull(map.get("FACT_NUM")),new Integer(0)))%></td>				
						<td width="25%" align="right"><%=LocalUtilis.language("class.factMoney",clientLocale)%> :</td><!--ʵ�ʷ����ܽ��-->
						<td width="25%"><%=Utility.trimNull(Format.formatMoney(Utility.parseDecimal(Utility.trimNull(map.get("FACT_MONEY")),new BigDecimal(0))))%></td>
					</tr>					
					<tr bgColor=#ffffff>
						<td align="right"><%=LocalUtilis.language("class.grCount2",clientLocale)%> <!--��Ȼ�˺�ͬ��-->:</td>
						<td><%if(Utility.parseInt(Utility.trimNull(map_rg.get("gr_count")),new Integer(0))!=null){if(Utility.parseInt(Utility.trimNull(map_rg.get("gr_count")),new Integer(0)).intValue()>0){%><%=Utility.trimNull(map_rg.get("gr_count"))%><%}}%></td>
						<td align="right"><%=LocalUtilis.language("class.grMoney",clientLocale)%> :</td><!--��Ȼ���Ϲ����-->
						<td><%=Utility.trimNull(Format.formatMoney(Utility.parseDecimal(Utility.trimNull(map_rg.get("gr_money")),new BigDecimal(0))))%></td>
					</tr>
					<tr bgColor=#ffffff>
						<td align="right"><%=LocalUtilis.language("class.jgCount2",clientLocale)%> :</td><!--������ͬ��-->
						<td><%if(Utility.parseInt(Utility.trimNull(map_rg.get("jg_count")),new Integer(0))!=null){if(Utility.parseInt(Utility.trimNull(map_rg.get("jg_count")),new Integer(0)).intValue()>0){%><%=Utility.trimNull(map_rg.get("jg_count"))%><%}}%></td>
						<td align="right"><%=LocalUtilis.language("class.jgMoney2",clientLocale)%> :</td><!--�����Ϲ����-->
						<td><%=Format.formatMoney(Utility.parseDecimal(Utility.trimNull(map_rg.get("jg_money")),new BigDecimal(0)))%></td>
					</tr>
					<tr bgColor=#ffffff>
						<td align="right">�ϸ�Ͷ�������� :</td><!--�ϸ�Ͷ��������-->
						<td><%if(Utility.parseInt(Utility.trimNull(map_rg.get("qualified_count")),new Integer(0))!=null){if(Utility.parseInt(Utility.trimNull(map_rg.get("qualified_count")),new Integer(0)).intValue()>0){%><%=Utility.trimNull(map_rg.get("qualified_count"))%><%}}%></td>
						<td align="right">�ϸ�Ͷ���˽�� :</td><!--�ϸ�Ͷ���˽��-->
						<td><%=Format.formatMoney(Utility.parseDecimal(Utility.trimNull(map_rg.get("qualified_money")),new BigDecimal(0)))%></td>
					</tr>
					<tr bgColor=#ffffff>
						<td align="right"><%=LocalUtilis.language("class.totalAmount",clientLocale)%> :</td><!--��Ʒ�ܷݶ�-->
						<td><%=Utility.trimNull(Format.formatMoney(Utility.parseDecimal(Utility.trimNull(map.get("TOTAL_AMOUNT")),new BigDecimal(0))))%></td>
						<td align="right"><%=LocalUtilis.language("class.navPrice",clientLocale)%> :</td><!--��ǰ��ֵ-->
						<td><%=Format.formatMoney(Utility.parseDecimal(Utility.trimNull(map.get("NAV_PRICE")),new BigDecimal(0)))%></td>
						</tr>
				</table>
				
				<table  border="0" cellspacing="1" cellpadding="2" bgColor=#666666  borderColorLight=#ffffff width="100%">
					<tr class="trh"  style="font-weight: bold;background-color:#75b2f0;">
						<td align="center" height="25" rowspan=2><%=LocalUtilis.language("class.serialNumber",clientLocale)%> </td><!--���-->
						<td align="center" height="25" rowspan=2><%=LocalUtilis.language("class.prov_level",clientLocale)%> </td><!--���漶��-->						
						<td align="center" height="25" rowspan=2><%=LocalUtilis.language("class.contractNum",clientLocale)%> </td><!--��ͬ����-->
						<td align="center" height="25" rowspan=2><%=LocalUtilis.language("class.benNum",clientLocale)%> </td><!--�������-->
						<td align="center" height="25" rowspan=2><%=LocalUtilis.language("class.benMoney",clientLocale)%> </td><!--������-->
						<td align="center" height="25" rowspan=2><%=LocalUtilis.language("class.benAmount",clientLocale)%> </td><!--����ݶ�-->
						<td align="center" height="25" colspan=3><%=LocalUtilis.language("class.grCustomer",clientLocale)%> </td><!--��Ȼ�˿ͻ�-->
						<td align="center" height="25" colspan=3><%=LocalUtilis.language("class.jgCustomer",clientLocale)%> </td><!--�����ͻ�-->
					</tr>
					<tr class="trh"  style="font-weight: bold;background-color:#75b2f0;">						
						<td align="center" height="25"><%=LocalUtilis.language("class.benNum",clientLocale)%> </td><!--�������-->
						<td align="center" height="25"><%=LocalUtilis.language("class.benMoney",clientLocale)%> </td><!--������-->
						<td align="center" height="25"><%=LocalUtilis.language("class.benAmount",clientLocale)%> </td><!--����ݶ�-->
						<td align="center" height="25"><%=LocalUtilis.language("class.benNum",clientLocale)%> </td><!--�������-->
						<td align="center" height="25"><%=LocalUtilis.language("class.benMoney",clientLocale)%> </td><!--������-->
						<td align="center" height="25"><%=LocalUtilis.language("class.benAmount",clientLocale)%> </td><!--����ݶ�-->
					</tr>
<%
//if(subflag==3)
{
vo.setBook_code(new Integer(1));
vo.setProduct_id(product_id);
vo.setInput_man(input_operatorCode);

Map map_sy = new HashMap();
List list_sy = product.list_sy(vo);
Iterator iterator_sy = list_sy.iterator();
int contract_num = 0 ; //��ͬ����
int ben_num = 0 ; //�������
int allgr_num=0;
int alljg_num=0;
BigDecimal ben_amount = new BigDecimal(0.000);   //����ݶ�
BigDecimal allgr_money=new BigDecimal(0.000);
BigDecimal alljg_money=new BigDecimal(0.000);
BigDecimal ben_money = new BigDecimal(0.000);   //������
BigDecimal allgr_amount=new BigDecimal(0.000);
BigDecimal alljg_amount=new BigDecimal(0.000);
while (iterator_sy.hasNext())
{
	map_sy = (Map)iterator_sy.next();
	if(Utility.parseInt(Utility.trimNull(map_sy.get("CONTRACT_NUM")),new Integer(0))!=null)
		contract_num = contract_num + Utility.parseInt(Utility.trimNull(map_sy.get("CONTRACT_NUM")),new Integer(0)).intValue();
	if(Utility.parseInt(Utility.trimNull(map_sy.get("BEN_NUM")),new Integer(0))!=null)
		ben_num = ben_num + Utility.parseInt(Utility.trimNull(map_sy.get("BEN_NUM")),new Integer(0)).intValue();
    if(Utility.parseDecimal(Utility.trimNull(map_sy.get("BEN_AMOUNT")),new BigDecimal(0))!=null)
		ben_amount = ben_amount.add(Utility.parseDecimal(Utility.trimNull(map_sy.get("BEN_AMOUNT")),new BigDecimal(0)));	
	if(Utility.parseInt(Utility.trimNull(map_sy.get("GR_NUM")),new Integer(0))!=null)	
	    allgr_num=allgr_num	+ Utility.parseInt(Utility.trimNull(map_sy.get("GR_NUM")),new Integer(0)).intValue();
	if(Utility.parseInt(Utility.trimNull(map_sy.get("JG_NUM")),new Integer(0))!=null)    
	    alljg_num=alljg_num + Utility.parseInt(Utility.trimNull(map_sy.get("JG_NUM")),new Integer(0)).intValue();
	if(Utility.parseDecimal(Utility.trimNull(map_sy.get("GR_MONEY")),new BigDecimal(0))!=null)    
	   allgr_money= allgr_money.add(Utility.parseDecimal(Utility.trimNull(map_sy.get("GR_MONEY")),new BigDecimal(0)));
	if(Utility.parseDecimal(Utility.trimNull(map_sy.get("JG_MONEY")),new BigDecimal(0))!=null)
	    alljg_money=alljg_money.add(Utility.parseDecimal(Utility.trimNull(map_sy.get("JG_MONEY")),new BigDecimal(0)));
	if(Utility.parseDecimal(Utility.trimNull(map_sy.get("GR_AMOUNT")),new BigDecimal(0))!=null)    
	   allgr_amount= allgr_amount.add(Utility.parseDecimal(Utility.trimNull(map_sy.get("GR_AMOUNT")),new BigDecimal(0)));
	if(Utility.parseDecimal(Utility.trimNull(map_sy.get("JG_AMOUNT")),new BigDecimal(0))!=null)    
	   alljg_amount= alljg_amount.add(Utility.parseDecimal(Utility.trimNull(map_sy.get("JG_AMOUNT")),new BigDecimal(0)));
	if(Utility.parseDecimal(Utility.trimNull(map_sy.get("BEN_MONEY")),new BigDecimal(0))!=null)
	   ben_money = ben_money.add(Utility.parseDecimal(Utility.trimNull(map_sy.get("BEN_MONEY")),new BigDecimal(0)));	
%>
					<tr class="tr1">
						<td class="tdh" align="center" height="25"><%if(map_sy.get("PROV_LEVEL")!=null){out.print(Utility.trimNull(map_sy.get("PROV_LEVEL")).substring(4,6));}%></td>
						<td align="center" height="25"><%=Utility.trimNull(map_sy.get("PROV_LEVEL_NAME"))%></td>						
						<td align="center" height="25"><%if(Utility.parseInt(Utility.trimNull(map_sy.get("CONTRACT_NUM")),new Integer(0))!=null){if(Utility.parseInt(Utility.trimNull(map_sy.get("CONTRACT_NUM")),new Integer(0)).intValue()>0){%><%=Utility.trimNull(Utility.parseInt(Utility.trimNull(map_sy.get("CONTRACT_NUM")),new Integer(0)))%><%}}%></td>
						<td align="center" height="25"><%if(Utility.parseInt(Utility.trimNull(map_sy.get("BEN_NUM")),new Integer(0))!=null){if(Utility.parseInt(Utility.trimNull(map_sy.get("BEN_NUM")),new Integer(0)).intValue()>0){%><%=Utility.trimNull(Utility.parseInt(Utility.trimNull(map_sy.get("BEN_NUM")),new Integer(0)))%><%}}%></td>
					    <td align="right" height="25"><%=Format.formatMoney(Utility.parseDecimal(Utility.trimNull(map_sy.get("BEN_MONEY")),new BigDecimal(0)))%></td>
					    <td align="right" height="25"><%=Format.formatMoney(Utility.parseDecimal(Utility.trimNull(map_sy.get("BEN_AMOUNT")),new BigDecimal(0)))%></td>
					    <td align="center" height="25"><%=Utility.parseInt(Utility.trimNull(map_sy.get("GR_NUM")),new Integer(0))%></td>
						<td align="right" height="25"><%=Utility.parseDecimal(Utility.trimNull(map_sy.get("GR_MONEY")),new BigDecimal(0))==null?"0":Format.formatMoney(Utility.parseDecimal(Utility.trimNull(map_sy.get("GR_MONEY")),new BigDecimal(0)))%></td>
						<td align="right" height="25"><%=Utility.parseDecimal(Utility.trimNull(map_sy.get("GR_AMOUNT")),new BigDecimal(0))==null?"0":Format.formatMoney(Utility.parseDecimal(Utility.trimNull(map_sy.get("GR_AMOUNT")),new BigDecimal(0)))%></td>
						<td align="center" height="25"><%=Utility.parseInt(Utility.trimNull(map_sy.get("JG_NUM")),new Integer(0))%></td>
						<td align="right" height="25"><%=Utility.parseDecimal(Utility.trimNull(map_sy.get("JG_MONEY")),new BigDecimal(0))==null?"0":Format.formatMoney(Utility.parseDecimal(Utility.trimNull(map_sy.get("JG_MONEY")),new BigDecimal(0)))%></td>
						<td align="right" height="25"><%=Utility.parseDecimal(Utility.trimNull(map_sy.get("JG_AMOUNT")),new BigDecimal(0))==null?"0":Format.formatMoney(Utility.parseDecimal(Utility.trimNull(map_sy.get("JG_AMOUNT")),new BigDecimal(0)))%></td>
					</tr>					
<%}%>
					<tr class="trbottom">
						<td class="tdh" align="left" height="25" colspan="2">
							<b>&nbsp;<%=LocalUtilis.language("message.total",clientLocale)%> <!--�ϼ�-->
							&nbsp;<%=pageList.getTotalSize()%>&nbsp;<%=LocalUtilis.language("message.entries",clientLocale)%><!--��--></b>
						</td>						
						<!-- <td align="center" height="25">-</td> -->
						<td align="center" height="25"><%=contract_num%></td>
						<td align="center" height="25"><%=ben_num%></td>	
						<td  align="right" height="25"><%=Format.formatMoney(ben_money)%></td>
						<td  align="right" height="25"><%=Format.formatMoney(ben_amount)%></td>
						<td align="center" height="25"><%=allgr_num%></td>
						<td align="center" height="25"><%=Format.formatMoney(allgr_money)%></td>
						<td align="center" height="25"><%=Format.formatMoney(allgr_amount)%></td>
						<td align="center" height="25"><%=alljg_num%></td>
						<td align="center" height="25"><%=Format.formatMoney(alljg_money)%></td>
						<td align="center" height="25"><%=Format.formatMoney(alljg_amount)%></td>
					</tr>	
<%}%>									
				</table>
				<br/>
				<div><b><%=LocalUtilis.language("menu.benefitChangeCollect",clientLocale)%> </b></div><!--����������-->
				<table class="tablelinecolor" border="0" cellspacing="1" cellpadding="2" width="100%">
					<tr class="trh" >
						<td align="center" height="25"><%=LocalUtilis.language("class.serviceStatusName2",clientLocale)%> </td><!--״̬-->
						<td align="center" height="25"><%=LocalUtilis.language("class.benCount",clientLocale)%> </td><!--��ͬ��-->
						<td align="center" height="25"><%=LocalUtilis.language("class.money",clientLocale)%> </td><!--���-->						
					</tr>
<%
Map map_sybg = new HashMap();
vo.setBook_code(input_bookCode);
vo.setProduct_id(product_id);
List list_sybg = product.listStatus1(vo);
Iterator iterator_sybg = list_sybg.iterator();
while (iterator_sybg.hasNext())
{
	map_sybg = (Map)iterator_sybg.next();		
%>
					<tr class="tr1">
						<td class="tdh" align="center" height="25"><%=Utility.trimNull(map_sybg.get("BEN_STATUS_NAME"))%></td>
						<td align="center" height="25"><%=Utility.parseInt(Utility.trimNull(map_sybg.get("BEN_COUNT")),new Integer(0))%></td>
						<td align="right" height="25"><%=Format.formatMoney(Utility.parseDecimal(Utility.trimNull(map_sybg.get("BEN_AMOUNT")),new BigDecimal(0)))%></td>						
					</tr>
<%}%>									
				</table>								
		   </TD>
        </TR>   			 
		<TR id=r4 <%if(subflag==4) out.print("style='display:'");else out.print("style='display:none'");%> bgColor=#ffffff>
          <TD height='400' valign="top">
          <b><%=LocalUtilis.language("menu.prodSalesContractChange",clientLocale)%> </b><!--��Ʒ���ۺ�ͬ���-->	
          <br>
          <table class="tablelinecolor" border="0" cellspacing="1" cellpadding="2" bgColor=#666666  borderColorLight=#ffffff width="100%">
					<tr class="trh">
						<td align="center" height="25"><%=LocalUtilis.language("class.contractID",clientLocale)%> </td><!--��ͬ���-->
						<td align="center" height="25" sort="num"><%=LocalUtilis.language("class.fromCustName",clientLocale)%> </td><!--ԭ������-->
						<td align="center" height="25" sort="num"><%=LocalUtilis.language("class.toCustName",clientLocale)%> </td><!--��������-->
						<td align="center" height="25"><%=LocalUtilis.language("class.changeTypeName",clientLocale)%> </td><!--�����ʽ-->
						<td align="center" height="25" sort="num"><%=LocalUtilis.language("class.toAmount",clientLocale)%> </td><!--����ݶ�-->
						<td align="center" height="25" ><%=LocalUtilis.language("class.changeDate",clientLocale)%> </td><!--�������-->
						<td align="center" height="25"><%=LocalUtilis.language("class.customerSummary",clientLocale)%> </td><!--��ע-->
					</tr>
					<%
//if(subflag==4)
{
vo.setBook_code(input_bookCode);
vo.setInput_man(input_operatorCode);
vo.setProduct_id(product_id);
vo.setContract_bh("");

Map map_cb = new HashMap();
IPageList pageList_cb = product.list_cb(vo,Utility.parseInt(sPage,1),Utility.parseInt(sPagesize,8));
List list_cb = pageList_cb.getRsList();		
Iterator iterator_cb = list_cb.iterator();	
int currentRow = 0;
int count = 0;
int check_flag=1;
while (iterator_cb.hasNext())
{
	map_cb = (Map)iterator_cb.next();
	if(Utility.parseInt(Utility.trimNull(map_cb.get("CHECK_FLAG")),new Integer(0))!=null)
		check_flag=	Utility.parseInt(Utility.trimNull(map_cb.get("CHECK_FLAG")),new Integer(0)).intValue();	
%>
					<tr class="tr<%=currentRow % 2%>">
						<td class="tdh" align="center" height="25">											
								<%=Utility.trimNull(map_cb.get("CONTRACT_BH"))%>
						</td>
						<td align="left" height="25"><%=Utility.trimNull(map_cb.get("FROM_CUST_NAME"))%> </td>
						<td align="left" height="25"><%=Utility.trimNull(map_cb.get("TO_CUST_NAME"))%> </td>
						<td align="center" height="25"><%=Utility.trimNull(map_cb.get("TRANS_TYPE_NAME"))%></td>
						<td align="right" height="25"><%=Utility.trimNull(Format.formatMoney(Utility.parseDecimal(Utility.trimNull(map_cb.get("TO_AMOUNT")),new BigDecimal(0))))%></td>
						<td align="center" height="25"><%=Utility.trimNull(Format.formatDateCn(Utility.parseInt(Utility.trimNull(map_cb.get("CHANGE_DATE")),new Integer(0))))%></td>
						<td align="left" height="25"><%=Utility.trimNull(map_cb.get("SUMMARY"))%></td>
						</tr>
					<%currentRow++;
count++;
}%>			
					<tr class="trbottom">
						<td class="tdh" align="left" height="25" colspan="7">
							<b>&nbsp;<%=LocalUtilis.language("message.total",clientLocale)%><!--�ϼ�-->
							&nbsp;<%=count%>&nbsp;<%=LocalUtilis.language("message.entries",clientLocale)%> <!--��--></b>
						</td>
						<!-- <td align="center" height="25">-</td>
						<td align="center" height="25">-</td>
						<td align="center" height="25">-</td>
						<td align="center" height="25">-</td>
						<td align="center" height="25">-</td>
						<td align="center" height="25">-</td> -->
					</tr>
<%}%>					
				</table>
				<br/>
				<b><%=LocalUtilis.language("menu.prodBaseInfoInput",clientLocale)%> </b><!--��Ʒ������Ϣ�޸�-->
				<br>
<%			
vo.setProduct_id(product_id);
Map map_xg = new HashMap();
List list_xg = product.list_xg(vo);
Iterator iterator_xg = list_xg.iterator();	
%>
<table bgColor=#666666 class="tablelinecolor"  borderColorLight=#ffffff  border="0" cellpadding="2" cellspacing="1" width="100%">
							<tr>
								<td bgcolor="ffffff" align="center"><%=LocalUtilis.language("menu.inputMan",clientLocale)%> </td><!--�޸���-->
								<td bgcolor="ffffff" align="center"><%=LocalUtilis.language("menu.inputDate",clientLocale)%> </td><!--�޸�����-->
								<td bgcolor="ffffff" align="center"><%=LocalUtilis.language("class.fieldCNName",clientLocale)%> </td><!--�޸�������-->
								<td bgcolor="ffffff" align="center"><%=LocalUtilis.language("class.oldFieldInfo",clientLocale)%> </td><!--������ԭֵ-->
								<td bgcolor="ffffff" align="center"><%=LocalUtilis.language("class.newFieldInfo",clientLocale)%> </td><!--��������ֵ-->
							</tr>
<%int jCurrent = 0;
Integer serial_no;
while (iterator_xg.hasNext())
{
	map_xg = (Map)iterator_xg.next();
	//serial_no = hcustomer.getSerial_no();
	%>
							<tr>
								<td bgcolor="ffffff" align="center"><%=Utility.trimNull(Argument.getOpName(Utility.parseInt(Utility.trimNull(map_xg.get("INPUT_MAN")),new Integer(0))))%></td>
								<td bgcolor="ffffff" align="center"><%=format.format(map_xg.get("INPUT_TIME"))%></td>
								<td bgcolor="ffffff" align="center"><%=Utility.trimNull(map_xg.get("FIELD_CN_NAME"))%></td>
								<td bgcolor="ffffff" align="center"><%=Utility.trimNull(map_xg.get("OLD_FIELD_INFO"))%></td>
								<td bgcolor="ffffff" align="center"><%=Utility.trimNull(map_xg.get("NEW_FIELD_INFO"))%></td>
							</tr>
							<%jCurrent++;
}%>
						</table>
				<br>
				<table border="0" width="100%">
					<tr valign="top">
						<td></td>
						<td align="right">
						</td>
					</tr>
				</table>
			</TD>
        </TR> 
         <TR id=r5 <%if(subflag==5) out.print("style='display:'");else out.print("style='display:none'");%> bgColor=#ffffff>
          <TD height='400' valign="top">        
<%
Map map_tl = new HashMap();
vo.setProduct_id(product_id);
List list_tl = product.list_tl(vo);
Iterator iterator_tl = list_tl.iterator();		
%>
						<table id="table3" class="tablelinecolor" border="0" cellspacing="1" cellpadding="1" bgColor=#666666  borderColorLight=#ffffff width="100%">
							<tr class="trh">
								<td align="center" height="25"><%=LocalUtilis.language("class.beginDate",clientLocale)%> </td><!--��ʼ����-->
								<td align="center" height="25"><%=LocalUtilis.language("class.endDate3",clientLocale)%> </td><!--��ֹ����-->
								<td align="center" height="25"><%=LocalUtilis.language("class.prov_level",clientLocale)%> </td><!--���漶��-->
								<td align="center" height="25" sort="num"><%=LocalUtilis.language("class.rate",clientLocale)%> </td><!--����-->
							</tr>
							<%
while (iterator_tl.hasNext())
{
	map_tl = (Map)iterator_tl.next();	
%>
							<tr class="tr<%=(iCurrent % 2)%>">
								
								<td align="center" height="25"><%=Format.formatDateCn(Utility.parseInt(Utility.trimNull(map_tl.get("BEGIN_DATE")),new Integer(0)))%></td>
								<td align="center" height="25"><%=Format.formatDateCn(Utility.parseInt(Utility.trimNull(map_tl.get("END_DATE")),new Integer(0)))%></td>
								<td align="center" height="25"><%=Utility.trimNull(map_tl.get("PROC_LEVEL_NAME"))%></td>
								<td align="right" height="25"><%=Format.formatPercent(Utility.parseDecimal(Utility.trimNull(map_tl.get("RATE")),new BigDecimal(0)),1,false)%></td>
								
							</tr>
							<%iCurrent++;
iCount++;
}
for (; iCurrent < 10; iCurrent++)
{
%>
							<tr class="tr<%=(iCurrent % 2)%>">
								<td align="center" height="25"></td>
								<td align="center" height="25"></td>
								<td align="center" height="25"></td>
								<td align="center" height="25"></td>
							</tr>
							<%}
%></table>
				</TD>
        	</TR>    
        	<TR id=r6 <%if(subflag==6) out.print("style='display:'");else out.print("style='display:none'");%> bgColor=#ffffff>
        		<TD height='400' valign="top">
<% 
Integer to_bank_date = new Integer(0);
String contract_bh = "";

c_vo.setProduct_id(product_id);
c_vo.setBook_code(new Integer(1));
c_vo.setInput_man(input_operatorCode);
c_vo.setCheck_flag(new Integer(0));

IPageList pageList_cont = contract.queryPurchanseContract(c_vo,totalColumn,1,-1);
list_cont = pageList_cont.getRsList();
Iterator iterator_cont = list_cont.iterator();	
%>        
						<input type="button" onclick="javascript:exprotExcel('table6');" value="���뵽EXCEL"> 
						<table id="table6" class="tablelinecolor" border="0" cellspacing="1" cellpadding="1" bgColor=#666666  borderColorLight=#ffffff width="100%">
							<tr class="trh">
								<td width="9%" align="center" height="25"><%=LocalUtilis.language("class.contractID",clientLocale)%> </td><!--��ͬ���-->
								<td width="9%" align="center" height="25"><%=LocalUtilis.language("class.customerID",clientLocale)%> </td><!--�ͻ����-->
								<td width="9%" align="center" height="25"><%=LocalUtilis.language("class.customerName",clientLocale)%> </td><!--�ͻ�����-->
								<td width="10%" align="center" height="25" sort="num"><%=LocalUtilis.language("class.rg_money",clientLocale)%> </td><!--�Ϲ����-->
								<td width="9%" align="center" height="25"><%=LocalUtilis.language("class.qsDate",clientLocale)%> </td><!--ǩ������-->
								<td width="9%" align="center" height="25"><%=LocalUtilis.language("class.toBankDate",clientLocale)%> </td><!--�ʽ�������-->
								<td width="9%" align="center">�������</td>
								<td width="9%" align="center">��������</td>
								<td width="9%" align="center">����������ʽ</td>
								<td width="9%" align="center">�ͻ�����</td>
								<td width="9%" align="center" height="25"><%=LocalUtilis.language("class.serviceStatusName2",clientLocale)%> </td><!--״̬-->	
							</tr>  
<%
iCount =0;
iCurrent = 0;
while(iterator_cont.hasNext()){
	map_cont = (Map)iterator_cont.next();
	cust_id = Utility.parseInt(Utility.trimNull(map_cont.get("CUST_ID")),new Integer(0));
	contract_bh = Utility.trimNull(map_cont.get("CONTRACT_BH"));;
	to_bank_date = contract.getToBankDate(cust_id,product_id,contract_bh);
	String channel_type_name = Utility.trimNull(map_cont.get("CHANNEL_TYPE_NAME"));
	String channel_name = Utility.trimNull(map_cont.get("CHANNEL_NAME"));
	String channel_coopertype_name = Utility.trimNull(map_cont.get("CHANNEL_COOPERTYPE_NAME"));
	String service_man_name = Utility.trimNull(map_cont.get("SERVICE_MAN_NAME"));
%>		
							<tr class="tr<%=(iCurrent%2)%>">
								<td align="center" height="25"><%=Utility.trimNull(map_cont.get("CONTRACT_SUB_BH"))%></td>
								<td align="center" height="25"><%=Utility.trimNull(map_cont.get("CUST_NO"))%></td>
								<td align="center" height="25"><%=Utility.trimNull(map_cont.get("CUST_NAME"))%></td>
								<td align="right" height="25"><%=Format.formatMoney(Utility.parseDecimal(Utility.trimNull(map_cont.get("RG_MONEY")),new BigDecimal(0),2,"1"))%></td>
								<td align="center" height="25"><%=Format.formatDateCn(Utility.parseInt(Utility.trimNull(map_cont.get("QS_DATE")),new Integer(0)))%></td>
								<td align="center" height="25"><%=Format.formatDateCn(to_bank_date.intValue())%></td>
								<td align="left"><%=Utility.trimNull(channel_type_name) %></td>
								<td align="left"><%=Utility.trimNull(channel_name) %></td>
								<td align="left"><%=Utility.trimNull(channel_coopertype_name) %></td>
								<td align="left"><%=Utility.trimNull(service_man_name) %></td>
								<td align="center" height="25"><%=Argument.getCheckFlagName(Utility.parseInt(Utility.trimNull(map_cont.get("CHECK_FLAG")),new Integer(0)))%></td>
							</tr>
<%
iCurrent++;
iCount++;}
for (; iCurrent < 10; iCurrent++){
%>
							<tr class="tr<%=(iCurrent % 2)%>">
								<td align="center" height="25"></td>
								<td align="center" height="25"></td>
								<td align="center" height="25"></td>
								<td align="center" height="25"></td>
								<td align="center" height="25"></td>
								<td align="center" height="25"></td>
								<td align="center" height="25"></td>
								<td align="center" height="25"></td>
								<td align="center" height="25"></td>
								<td align="center" height="25"></td>
								<td align="center" height="25"></td>
							</tr>
							<%}
%>			
						</table>
		        </TD>
			</TR>  
			<TR id=r7 <%if(subflag==7) out.print("style='display:'");else out.print("style='display:none'");%> bgColor=#ffffff>
          		<TD height='400' valign="top">
						<table id="table7" class="tablelinecolor" border="0" cellspacing="1" cellpadding="1" bgColor=#666666  borderColorLight=#ffffff width="100%">
							<tr class="trh">
								<td align="center" height="25">������� </td><!--�������-->
								<td align="center" height="25">���</td><!--�������-->
								<td align="center" height="25">��ͬ����</td><!--��ͬ����-->
								<td align="center" height="25">��ͬ���(��Ԫ)</td><!--��ͬ���-->
								<td align="center" height="25">����</td><!--����-->
								<td align="center" height="25">������ </td><!--������-->
								<td align="center" height="25">ƽ�������� </td><!--ƽ��������-->	
							</tr>  
<%
vo.setProduct_id(product_id);
list_prov = product.StatSubProductByProv(vo);
Iterator iterator_prov = list_prov.iterator();	
iCount =0;
iCurrent = 0;

if(list_prov.size()>1){
	while(iterator_prov.hasNext()){
		map_prov = (Map)iterator_prov.next();
		String prov_level_name = Utility.trimNull(map_prov.get("PROV_LEVEL_NAME"));
		String list_name7 = Utility.trimNull(map_prov.get("LIST_NAME"));
 %>
		<tr class="tr<%=(iCurrent%2)%>">
			<td align="center" height="25"><%=prov_level_name%></td>
			<td align="center" height="25"><%=list_name7%></td>
			<td align="center" height="25"><%=Utility.parseInt(Utility.trimNull(map_prov.get("CONTRACT_COUNT")),new Integer(0))%></td>
			<td align="right" height="25"><%=Format.formatMoney(Utility.parseDecimal(Utility.trimNull(map_prov.get("CONTRACT_MONEY")),new BigDecimal(0),2,"1"))%></td>
			<td align="center" height="25"><%=Utility.trimNull(map_prov.get("VALID_PERIOD_UNIT"))%></td>
			<td align="center" height="25"><%if(!list_name7.equals("�ϼ�")&&!prov_level_name.equals("�ϼ�")){%><%=Utility.parseDecimal(Utility.trimNull(map_prov.get("EXP_RATE1")), new BigDecimal(0.00),2,"100")%>%<%}%></td>
			<td align="center" height="25"><%if(!list_name7.equals("�ϼ�")&&!prov_level_name.equals("�ϼ�")){%><%=Utility.parseDecimal(Utility.trimNull(map_prov.get("AVERAGE_RATE")), new BigDecimal(0.00),2,"100")%>%<%}else if(prov_level_name.equals("�ϼ�")){%>&nbsp;<%}%></td>
		</tr>
<%
	iCurrent++;
	iCount++;}
}
else{
%>
	<tr class="tr<%=(iCurrent%2)%>">
		<td colspan="7">û�жԲ�Ʒ���зֲ㣬��ͳ������</td>
	</tr>
<%
}
%>		
						</table>
  		        </TD>
			</TR>
			<TR id=r8 <%if(subflag==8) out.print("style='display:'");else out.print("style='display:none'");%> bgColor=#ffffff>
          		<TD height='400' valign="top">
						<table id="table8" class="tablelinecolor" border="0" cellspacing="1" cellpadding="1" bgColor=#666666  borderColorLight=#ffffff width="100%">
							<tr class="trh">
								<td align="center" height="25">��¶ʱ�� </td>
								<td align="center" height="25">��¶����</td>
							</tr>  
<%
vo.setProduct_id(product_id);
vo.setTask_type(new Integer(103));

List list_task = product.listTask(vo);
Iterator iterator_task = list_task.iterator();	
iCount =0;
iCurrent = 0;

if(list_task.size()>0){
	while(iterator_task.hasNext()){
		Map map_task = (Map)iterator_task.next();
 %>
		<tr class="tr<%=(iCurrent%2)%>">

			<td align="center" height="25"><%=Utility.parseInt(Utility.trimNull(map_task.get("TASK_DATE")),new Integer(0))%></td>
			<td align="center" height="25"><%=Utility.trimNull(map_task.get("SUMMARY"))%></td>
		</tr>
<%
	iCurrent++;
	iCount++;}

}
%>		
						</table>
  		        </TD>
			</TR>  
        	<tr bgColor=#ffffff>
        	<td align=right>             		
        	<button type="button"    class="xpbutton3" accessKey=c id="btnSave" name="btnSave" onclick="javascript:window.returnValue=null;window.close();"><%=LocalUtilis.language("message.close",clientLocale)%> (<u>C</u>)</button>
			<!--�ر�-->
        	</td>
        	</tr>
        	</TBODY>
		  </TABLE>
		</TD>
	</TR>
	</TBODY>
	</TABLE>
</TABLE>
</form>
</BODY>
</HTML>
<%
product.remove();
contract.remove();
%>
