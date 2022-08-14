<%@ page language="java" pageEncoding="GBK" import="java.util.*,java.math.*,enfo.crm.system.*,enfo.crm.vo.*, enfo.crm.marketing.*,enfo.crm.tools.*,enfo.crm.dao.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>

<%
String sQuery = "";
String[] totalValue = Utility.splitString(sQuery,"$");
Iterator it = null;
Map map = null;
BenifiterQueryLocal benifitor = EJBFactory.getBenifiterQuery(); 
BenifitorVO vo = new BenifitorVO();

String contract_sub_bh = Utility.trimNull(request.getParameter("contract_sub_bh"));
String cust_name = Utility.trimNull(request.getParameter("cust_name"));
String card_id = Utility.trimNull(request.getParameter("card_id"));
String sy_cust_no = Utility.trimNull(request.getParameter("sy_cust_no"));
String prov_level = Utility.trimNull(request.getParameter("prov_level"));
String ben_status = Utility.trimNull(request.getParameter("ben_status"));
Integer cust_type=Utility.parseInt(request.getParameter("cust_type"),new Integer(0));
Integer product_id=Utility.parseInt(request.getParameter("product_id"),new Integer(0));
if(product_id.intValue()==0){
	product_id = overall_product_id;
}
Integer input_man=Utility.parseInt(input_operatorCode,new Integer(0));
Integer team_id = Utility.parseInt(Utility.trimNull(request.getParameter("team_id")),new Integer(0));
vo.setContract_sub_bh(contract_sub_bh);
vo.setProduct_id(product_id);
vo.setBook_code(input_bookCode);
vo.setCust_no(card_id);
vo.setSy_cust_name(cust_name); 
vo.setProv_level(prov_level);
vo.setInput_man(input_operatorCode);
vo.setSy_cust_no(sy_cust_no);
vo.setBen_status(ben_status);
vo.setCust_type(cust_type);
vo.setInput_man(input_man);
vo.setTeam_id(team_id);

sQuery = contract_sub_bh+" $ "+cust_name+" $ "+card_id+" $ "+sy_cust_no+" $ "+prov_level+" $ "+ben_status+" $ "+cust_type+" $ "+product_id+" $ "+team_id+" $ ";

//��ÿͻ���Ϣ
IPageList pageList = benifitor.listbySqlSYRAll(vo,totalValue, Utility.parseInt(sPage, 1), Utility.parseInt(sPagesize, 8));
if(pageList != null)
	it = pageList.getRsList().iterator();

String viewStr = "PRODUCT_NAME$CONTRACT_SUB_BH$CUST_NAME$TO_MONEY$TO_AMOUNT$BEN_AMOUNT$FROZEN_MONEY$BEN_DATE$BEN_END_DATE$BEN_STATUS_NAME";//Ĭ����
//��ø�Ա����ӵ�еĲ˵�
String tempView = Argument.getMyMenuViewStr("38003",input_operatorCode);
//�����Ա��û�����ò˵�����ΪĬ�ϲ˵�
if(tempView!=null&&!tempView.equals("")){
	viewStr = tempView;
}

//��ӵ�еĲ˵������õ����в˵�����ƥ��
Map fieldsMap = Argument.getMenuViewMap("38003",viewStr);
if(fieldsMap == null||fieldsMap.isEmpty()){
	fieldsMap = new HashMap(); 
	viewStr = "";
}
StringBuffer serialIdSAll = new StringBuffer();
//URL����
sUrl = sUrl + "&product_id=" + product_id 
			 + "&contract_sub_bh=" + contract_sub_bh 
			 + "&cust_name=" + cust_name 
			 + "&card_id=" + card_id 
			 + "&sy_cust_no=" + sy_cust_no 
			 + "&prov_level=" + prov_level 
			 + "&ben_status=" + ben_status 
			 + "&cust_type=" + cust_type 
			 + "&team_id=" + team_id;
%>
<HTML>
<HEAD>
<TITLE></TITLE>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css
	rel=stylesheet>
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js"></SCRIPT>
<script language=javascript>
var js_product_id1=0;

window.onscroll = function() {
	var toolbar = document.getElementById("toolbar");
	var width = 100 + document.body.scrollLeft/document.body.clientWidth*100;
	toolbar.width = width+"%";
};

function checkProductID(obj,p_id){
	if (checkedCount(document.theform.serial_no) == 1){
		js_product_id1=p_id;
	}else if (p_id != js_product_id1){
		alert("��ѡ����ͬ��Ʒ��������!");
		obj.checked=false;
		return false;
	}
}

//�޸��������ڡ���������(������¼)
function showInfo(product_id){
	if(product_id == 0 ){
		sl_alert("����ѡ���Ʒ�ٽ����޸�!");
		return;
	}
	if (checkedCount(document.theform.serial_no) == 0){
		alert("��ѡ��������!");
		return;
	}
	var ret = showModalDialog('benifiter_modi_valid_preiod.jsp?serial_no='+checkedValue(document.theform.serial_no)+'&product_id='+product_id, '', 'dialogWidth:540px;dialogHeight:280px;status:0;help:0');
	if (ret == 0){
		sl_alert("<%=LocalUtilis.language("message.saveOk",clientLocale)%> ");//����ɹ�
		location.reload();
	}
}
//�޸��������ڡ���������(������¼)
function showModi(urlPara){
	var ret = showModalDialog('benifiter_modi_valid_preiod.jsp?'+urlPara, '', 'dialogWidth:540px;dialogHeight:400px;status:0;help:0');
	if (ret == 0){
		sl_alert("<%=LocalUtilis.language("message.saveOk",clientLocale)%> ");//����ɹ�
		location.reload();
	}
}

function refreshPage()
{
	var condition = "contract_sub_bh=<%=contract_sub_bh%>&product_id=<%=product_id%>&cust_name=<%=cust_name%>&card_id=<%=card_id%>&prov_level=<%=prov_level%>&sy_cust_no=<%=sy_cust_no%>&ben_status=<%=ben_status%>&cust_type=<%=cust_type%>";
	disableAllBtn(true);
	location = 'benifiter_query.jsp?page=1&pagesize=' + document.theform.pagesize.value+"&"+condition;
}
//��ѯ
function StartQuery()
{
	var condition = "contract_sub_bh=<%=contract_sub_bh%>&product_id=<%=product_id%>&cust_name=<%=cust_name%>&card_id=<%=card_id%>&prov_level=<%=prov_level%>&sy_cust_no=<%=sy_cust_no%>&ben_status=<%=ben_status%>&cust_type=<%=cust_type%>";
	var ret = showModalDialog('benifiter_query_query_condition.jsp?'+condition, '', 'dialogWidth:550px;dialogHeight:280px;status:0;help:0');
	if(ret!=null)
	{		
		disableAllBtn(true);
		location="benifiter_query.jsp?"+ret;	
	}
}

/*��ʾ�趨*/
function setView(){
	result = showModalDialog('menu_view_set.jsp?menu_id_v=38003','','dialogWidth:800px;dialogHeight:580px;status:0;help:0');	
	if(result!=null)
	location.reload();
}

/*��������
 *�������˵����
 *1��BOOK_CODE ����
 *2��INPUT_MAN ����Ա
 *3��MENU_ID �˵�ID
 *4��viewStr �ò���Ա��ӵ�е���
 *5��sQuery ��ѯ����
*/
function exportInfo()
{
	if(checkedCount(document.theform.serial_no) == 0){
		sl_alert("��ѡ��Ҫ�����ļ�¼��");
		return false;
	}else{
	    if(sl_confirm("<%=LocalUtilis.language("message.exportDataTip",clientLocale)%> "))//ȷ��Ҫ����������
		{

	  	setWaittingFlag(false);
		document.theform.method = "post";
	 	//var url = 'set_export_info.jsp
		document.theform.action ="set_export_info.jsp?flag=1&book_code=<%=input_bookCode%>&input_man=<%=input_operatorCode%>&menu_id=38003&viewStr=<%=viewStr%>&sQuery=<%=sQuery%>";
		document.theform.submit();
//	 	location = url;
		}
	}

}

function viewBenTranDetail(productId, contractBh, contractSubBh){
	// 330px + 120px + 120px + 330px + 120px = 
	showModalDialog("bentran_detail_view.jsp?product_id="+productId+"&contract_bh="+contractBh+"&contract_sub_bh="+contractSubBh, '', 
			'dialogWidth:1020px;dialogHeight:300px;status:0;help:0');
}
</script>
</HEAD>
<BODY class="BODY"  <%if(menuIn!=0){%> onload="javascript:StartQuery();" <%}%>>
<%@ include file="/includes/waiting.inc"%> 
<form name="theform"  method="post">
<input type="hidden" name="product_id" value="<%=product_id%>">

<table id="toolbar" border="0" width="100%" cellspacing="0" cellpadding="0">
	<tr>
		<td colspan=2 class="page-title"><b><%=menu_info%></b></td>
	</tr>
	<tr>
		<td align="left"></td>
		<td align="right">
			<div class="btn-wrapper">
		<%if (input_operator.hasFunc(menu_id, 108) && user_id.intValue()!=21) {%>
		<button type="button"  class="xpbutton3" accessKey=m name="btnQuery" onclick="javascript:showInfo('<%=product_id %>');">��������(<u>M</u>)</button>&nbsp;&nbsp;&nbsp; 
		<%} %>
		<%if (input_operator.hasFunc(menu_id, 108)) {%>
		<button type="button"  class="xpbutton3" accessKey=q name="btnQuery" onclick="javascript:StartQuery();">��ѯ(<u>Q</u>)</button>&nbsp;&nbsp;&nbsp; 
		<%} %>
		<button type="button"  class="xpbutton3" onclick="javascript:setView()" title="��ʾ�趨">��ʾ�趨</button>&nbsp;&nbsp;&nbsp; 
		<button type="button"  class="xpbutton4" id="exportButton" name="exportButton" onclick="javascript:exportInfo();" title="����Excel����">��������</button>
		</div>
		<br/>
		</td>
	</tr>
</table>

<TABLE cellSpacing=0 cellPadding=0 width="2000px" border=0 >
	<TR>
		<TD vAlign=top align=left>
		<TABLE cellSpacing=0 cellPadding=4 width="100%" align=center border=0>
			
			<TR>
				<TD height="100%">
				<table id="table3" border="0" cellspacing="1" cellpadding="2" class="tablelinecolor" width="100%">
					<tr class="trh">
						<!--��ʾ�ò���Աӵ�е��ֶ�-->
						<%
						String[] fieldsArr =Utility.splitString(viewStr,"$");
						String str = "";
						for(int j=0;j<fieldsArr.length;j++){
							if((Map)fieldsMap.get(fieldsArr[j])==null)continue;
							if(str==""){str = fieldsArr[j];}
							else{str =  str + "$" + fieldsArr[j];}					
						}
						fieldsArr = Utility.splitString(str,"$");
						for(int j=0;j<fieldsArr.length;j++){
							
							String setWithName = Utility.trimNull(((Map)fieldsMap.get(fieldsArr[j])).get("FIELD_NAME"));
							String setWith = "";
							if("��Ʒ����".equals(setWithName)/*|| "ί����".equals(setWithName) || "������".equals(setWithName)*/)
								setWith = "330px;";
							else
								setWith = "120px;";
						
							int field_type = Utility.parseInt(Utility.trimNull(((Map)fieldsMap.get(fieldsArr[j])).get("FIELD_TYPE")), 1);
							if(j==0){
							%>
							<td align="center" width="<%=Utility.trimNull(setWith)%>"><input type="checkbox" name="btnCheckbox" class="selectAllBox" onclick="javascript:selectAllBox(document.theform.serial_no,this);">
							<%=((Map)fieldsMap.get(fieldsArr[j])).get("FIELD_NAME")%>
							</td>
							<%}
							else{%>
							<td align="center" width="<%=Utility.trimNull(setWith)%>" <%if(field_type==2 || field_type==4) out.print("sort='num'");%>><%=((Map)fieldsMap.get(fieldsArr[j])).get("FIELD_NAME")%></td><!--����ֶε�Map�����ڻ�����-->
							<%}
						}
						%>
						<TD align="center" width="100px;">�������鿴</TD>
					<%if (user_id.intValue()==2) {//2����Ͷ %>
						<TD align="center" width="100px;">���������޸�</TD>	
					<%}%>
					</tr>
					<%
					int index = 0;
					Integer serial_no;
					
					BigDecimal to_money = new BigDecimal("0.000");
					BigDecimal to_amount = new BigDecimal("0.000");
					BigDecimal ben_amount = new BigDecimal("0.000");
					BigDecimal frozen_money = new BigDecimal("0.000");
					String fee_jk_type = "";
					
					int iCount = 0;
					int iCurrent = 0;
					while(it!= null && it.hasNext())
					{
						map = (Map) it.next();
						serial_no = Utility.parseInt(Utility.trimNull(map.get("SERIAL_NO")), new Integer(0));
						Integer product_id2 = Utility.parseInt(Utility.trimNull(map.get("PRODUCT_ID")), new Integer(0));
						serialIdSAll.append(Utility.trimNull(map.get("SERIAL_NO")));
						if(iCount + 1 < pageList.getRsList().size())
							serialIdSAll.append(",");
					%>

					<tr class="tr<%=(iCurrent % 2)%>">
						<!--��ʾ�ò���Աӵ�е��ֶ�����-->
						<%
						for(int j=0;j<fieldsArr.length;j++){
							
							//��ʾ�н�����
						  	if(((Integer)((Map)fieldsMap.get(fieldsArr[j])).get("FIELD_TYPE")).intValue() == 2)
						  	{
						  		//�Ϲ����
						  		if(((Map)fieldsMap.get(fieldsArr[j])).get("FIELD").equals("TO_MONEY") && ((Map)fieldsMap.get(fieldsArr[j])).get("FIELD") != null)
						  		{
						  			to_money = to_money.add(Utility.stringToDouble(Utility.trimNull(map.get(fieldsArr[j]))));
						  		}
						  		//ԭʼ���
						  		if(((Map)fieldsMap.get(fieldsArr[j])).get("FIELD").equals("TO_AMOUNT") && ((Map)fieldsMap.get(fieldsArr[j])).get("FIELD") != null)
						  		{
						  			to_amount = to_amount.add(Utility.stringToDouble(Utility.trimNull(map.get(fieldsArr[j]))));
						  		}
						  		//��ǰȨ��
						  		if(((Map)fieldsMap.get(fieldsArr[j])).get("FIELD").equals("BEN_AMOUNT") && ((Map)fieldsMap.get(fieldsArr[j])).get("FIELD") != null)
						  		{
     								ben_amount = ben_amount.add(Utility.stringToDouble(Utility.trimNull(map.get(fieldsArr[j]))));
     							}
     							//����Ȩ��
     							if(((Map)fieldsMap.get(fieldsArr[j])).get("FIELD").equals("FROZEN_MONEY") && ((Map)fieldsMap.get(fieldsArr[j])).get("FIELD") != null)
     							{
     								frozen_money = frozen_money.add(Utility.stringToDouble(Utility.trimNull(map.get(fieldsArr[j]))));
     							}
						  	}

							if(j==0){
								if(((Map)fieldsMap.get(fieldsArr[j])).get("FIELD").equals("FEE_JK_TYPE") && ((Map)fieldsMap.get(fieldsArr[j])).get("FIELD") != null)
     							{
     								
									int iFeeJkType = Utility.parseInt(Utility.trimNull(map.get(fieldsArr[j])),new Integer(0)).intValue();
									switch(iFeeJkType){
										case 0:
											fee_jk_type = "�������";
										break;
										case 1:
											fee_jk_type = "�ӱ���۳�";
										break;
										case 2:
											fee_jk_type = "�������";
										break;
										default:
										break;
									}
								%>
								<td class="tdh" align="center" >
								<table border="0" width="100%" cellspacing="0" cellpadding="0">
									<tr>
										<td width="10%"><input type="checkbox" name="serial_no" value="<%=serial_no%>" class="flatcheckbox" onclick="javascript:checkProductID(this,<%=product_id2 %>);"></td>
										<td width="90%" align="left"><%=fee_jk_type%></td>
									</tr>
								</table>
								</td>
								<%
 							}else{
							%>
								<td class="tdh" align="center" >
								<table border="0" width="100%" cellspacing="0" cellpadding="0">
									<tr>
										<td width="10%"><input type="checkbox" name="serial_no" value="<%=serial_no%>" class="flatcheckbox" onclick="javascript:checkProductID(this,<%=product_id2 %>);"></td>
										<td width="90%" align="left"><%=map.get(fieldsArr[j])%></td>
									</tr>
								</table>
								</td>
							<%	
								}
							}else{
								if(((Map)fieldsMap.get(fieldsArr[j])).get("FIELD").equals("FEE_JK_TYPE") && ((Map)fieldsMap.get(fieldsArr[j])).get("FIELD") != null)
     							{
     								
									int iFeeJkType = Utility.parseInt(Utility.trimNull(map.get(fieldsArr[j])),new Integer(0)).intValue();
									switch(iFeeJkType){
										case 0:
											fee_jk_type = "�������";
										break;
										case 1:
											fee_jk_type = "�ӱ���۳�";
										break;
										case 2:
											fee_jk_type = "�������";
										break;
										default:
										break;
									}
									%>
									<td align="left" ><%=fee_jk_type%></td>	
									<%
     							}else{
							int iType = ((Integer)((Map)fieldsMap.get(fieldsArr[j])).get("FIELD_TYPE")).intValue();
							switch (iType){
							case 1:
							%>
							<td align="left" ><%=Utility.trimNull(map.get(fieldsArr[j]))%></td>	
							<%
										break;
										case 2:
							%>
							<td align="right" >
								<%=Utility.trimNull(Format.formatMoney(Utility.stringToDouble(Utility.trimNull(map.get(fieldsArr[j])))))%>
							</td>	
							<%			
										break;
										case 3:
							%>
							<td align="center" ><%=Format.formatDateLine(Utility.parseInt(Utility.trimNull(map.get(fieldsArr[j])), new Integer(0)))%></td>	
							<%				
										break;
										case 4:
							%>
							<td>
								<%=Utility.trimNull(Utility.parseInt(Utility.trimNull(map.get(fieldsArr[j])), null))%>
							</td>
							<%
										break;
										case 5:
							%>
							<td>
								<%=Utility.trimNull(Argument.getTintegerparamValue(Utility.parseInt(Utility.trimNull((((Map)fieldsMap.get(fieldsArr[j])).get("PARAM_TYPE_ID"))), new Integer(0)),Utility.parseInt(Utility.trimNull(map.get(fieldsArr[j])), new Integer(0))))%>
							</td>
							<%
										break;
										default:
										break;
									}
								}
							}
							
						}%>
						<TD align="center"><button type="button"  class="xpbutton3" name="btnViewBenTran" 
							onclick="javascript:viewBenTranDetail(<%=product_id2%>,'<%=map.get("CONTRACT_BH")%>','<%=map.get("CONTRACT_SUB_BH")%>');">�����ϸ</button> </TD>
					<%if (user_id.intValue()==2) {//2����Ͷ %>
						<TD align="center"><button type="button"  class="xpbutton3" name="btnQuery" 
							onclick="javascript:showModi('<%="single=1&serial_no="+serial_no+"&product_id="+product_id2+"&cust_acct_name="+Utility.trimNull(map.get("CUST_NAME")) +"&ben_date="+Utility.trimNull(map.get("BEN_DATE"))+"&ben_amount="+Utility.trimNull(map.get("BEN_AMOUNT"))  %>');">�޸�</button> </TD>
					<%} %>
					</tr>
					<%
					iCurrent++;
					iCount++;
					}

					int page_size = (pageList!=null)?pageList.getPageSize():Utility.parseInt(sPagesize, 8);
					for (; iCurrent < page_size; iCurrent++)
					{
					%>
					<tr class="tr<%=(iCurrent % 2)%>">
					<%for(int j=0;j<fieldsArr.length;j++){
					%>
						<td align="center"></td>
					<%
					}					
					%>
						<td align="center"></td>
					<%if (user_id.intValue()==2) {//2����Ͷ %>
						<td align="center"></td>
					<%} %>
					</tr>
					<%}
					%>
					<tr class="trbottom">
						<td class="tdh" align="center" ><b>�ϼ� <%=(pageList!=null)?pageList.getTotalSize():0%> ��</b></td>
						<%for(int j=1;j<fieldsArr.length;j++){
						%>
						<%if(((Integer)((Map)fieldsMap.get(fieldsArr[j])).get("FIELD_TYPE")).intValue() == 2){
					  		if(((Map)fieldsMap.get(fieldsArr[j])).get("FIELD").equals("TO_MONEY") && ((Map)fieldsMap.get(fieldsArr[j])).get("FIELD") != null)
					  		{%>
					  		<!--�Ϲ����-->
							<td align="right" ><%=Format.formatMoney(to_money)%></td>
							<%}if(((Map)fieldsMap.get(fieldsArr[j])).get("FIELD").equals("TO_AMOUNT") && ((Map)fieldsMap.get(fieldsArr[j])).get("FIELD") != null){%>
							<!--ԭʼ���-->
							<td align="right" ><%=Format.formatMoney(to_amount)%></td>
							<%}if(((Map)fieldsMap.get(fieldsArr[j])).get("FIELD").equals("BEN_AMOUNT") && ((Map)fieldsMap.get(fieldsArr[j])).get("FIELD") != null){%>
							<!--��ǰȨ��-->
							<td align="right" ><%=Format.formatMoney(ben_amount)%></td>
							<!--����Ȩ��-->
							<%}if(((Map)fieldsMap.get(fieldsArr[j])).get("FIELD").equals("FROZEN_MONEY") && ((Map)fieldsMap.get(fieldsArr[j])).get("FIELD") != null){%>
							<td align="right" ><%=Format.formatMoney(frozen_money)%></td>
							<%}%>
						<%}else{%>
						<td align="center" ></td>
						<%}%>
						<%
						}
						%>
						<td align="center" ></td>
					<%if (user_id.intValue()==2) {//2����Ͷ %>
						<td align="center" ></td>
					<%} %>
					</tr>
				</table>
				<br>

				<table border="0" width="100%" class="page-link">
					<tr valign="top">
						<td><%=(pageList!=null)?pageList.getPageLink(sUrl,clientLocale):""%></td>						
					</tr>
				</table>
		</TABLE>
		</TD>
</TABLE>
<input type="hidden" name="t2" value="<%=serialIdSAll%>">
</form>
<%@ include file="/includes/foot.inc"%>
</BODY>
</HTML>
<%benifitor.remove();
%>
