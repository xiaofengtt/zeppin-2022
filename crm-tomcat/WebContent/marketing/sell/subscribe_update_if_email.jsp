<%@ page contentType="text/html; charset=GBK" import="enfo.crm.customer.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>
<%
boolean bSuccess = false;

Integer serial_no = Utility.parseInt(request.getParameter("serial_no"),new Integer(0));
Integer if_mail = Utility.parseInt(request.getParameter("if_mail"),new Integer(0));

ContractLocal contract = EJBFactory.getContract();
CustomerLocal customer = EJBFactory.getCustomer();
ProductLocal product = EJBFactory.getProduct();
ContractVO vo = new ContractVO();
CustomerVO custvo = new CustomerVO();
ProductVO productvo = new ProductVO();

Map contractMap =new HashMap();
Map custMap = new HashMap();
Map productMap = new HashMap();
vo.setSerial_no(serial_no);
vo.setInput_man(input_operatorCode);


//保存信息
if(request.getMethod().equals("POST")){	
	ContractVO post_vo = new ContractVO();
	
    post_vo.setSerial_no(serial_no);
//	post_vo.setIf_mail(if_mail);
	post_vo.setInput_man(input_operatorCode);

//	SimpleFactory.updateContractByIfMail(post_vo);
	bSuccess = true;
}

List contractList = contract.load(vo);

if(contractList!=null&&contractList.size()==1){
	contractMap = (Map)contractList.get(0);
}

custvo.setCust_id((Integer)contractMap.get("CUST_ID"));
custvo.setInput_man(input_operatorCode);
productvo.setProduct_id((Integer)contractMap.get("PRODUCT_ID"));

List custList = customer.listCustomerLoad(custvo);
List productList = product.load(productvo);
if(custList!=null&&custList.size()==1){	
    custMap = (Map)custList.get(0);
}
if(productList!=null&&productList.size()==1){ 
    productMap = (Map)productList.get(0);
}

if_mail = Utility.parseInt(Utility.trimNull(contractMap.get("IF_MAIL")), new Integer(0));

%>
<HTML>
<HEAD>
<TITLE>编辑</TITLE>
<META http-equiv=Content-Type content="text/html; charset=gbk">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>

<base target="_self">


<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<script language=javascript>
    
    <%if (bSuccess){%>
	   window.returnValue = true;
	   window.close();
    <%}%>
    

    function showBenifiter(contract_id) {
       location.href = "benifiter_check.jsp?from_flag_benifitor=1&contract_id=" + contract_id + '&page=<%//=sPage%>&pagesize=' + document.theform.pagesize.value;
    }

    /*客户信息*/
    function getMarketingCustomer(cust_id){
        var url = '<%=request.getContextPath()%>/marketing/customerInfo_details_view.jsp?cust_id=' + cust_id + '&readonly=1'+'&ifcheck=1' ;	
        var v = showModalDialog(url,'','dialogWidth:720px;dialogHeight:600px;status:0;help:0;');	
    }
    
    /*验证数据*/
	function validateForm(form){
        
        if(!sl_checkChoice(form.if_mail, "合同是否寄回"))		return false;
        return sl_check_update();
	}
    
    
</script>
</HEAD>
<BODY class="BODY">
<form name="theform" method="post"  onsubmit="javascript:return validateForm(this);">
    <input name="serial_no" type="hidden" value="<%=serial_no%>">

    <table cellSpacing=0 cellPadding=4 width="100%" align=center border=0>
			<tr>
				<td>
					<table cellSpacing=0 cellPadding=2 width="100%" border=0>
						<tr>
							<td><img border="0" src="<%=request.getContextPath()%>/images/member.gif" align="absmiddle" width="32" height="28"><font color="#215dc6"><b><%=menu_info%></b></font></td>
						</tr>
					</table>
					<table border=0 cellSpacing=0 cellPadding=0 width="100%">
						<tr>
							<td>
								<hr size=1>
							</td>
						</tr>
					</table>
					<table border=0 cellSpacing=0 cellPadding=3 width="100%">
						<tr>
							<td align=right>客户编号 :</td>
							<td><INPUT class=edline value='<%=Utility.trimNull(custMap.get("CUST_NO"))%>' readOnly name="cust_no"></td>
							<td align=right>客户名称 :</TD>
							<td>
								<input class=edline value='<%=Utility.trimNull(custMap.get("CUST_NAME"))%>'  readOnly size="25" name="cust_name">
							</td>
						</tr>
						
						<tr>
							<td align="right">合同序号 :</td>
							<td><INPUT readonly class="edline" name="contract_bh" size="20" value="<%=Utility.trimNull(contractMap.get("CONTRACT_BH"))%>"></td>
							<td align="right">合同编号 :</td>
							<td ><input readonly class="edline" name="contract_sub_bh" size="40" maxlength=40 onkeydown="javascript:nextKeyPress(this)" value="<%=Utility.trimNull(contractMap.get("CONTRACT_SUB_BH"))%>"></td>
						</tr>
						<tr>
							<td align="right">产品名称 :</td>
							<td><input readonly class="edline" name="product_name" size="40" readonly value="<%=Utility.trimNull(contractMap.get("PRODUCT_NAME"))%>"></td>
							<td align="right">预约编号 :</td>
							<td><input readonly class="edline" name="pre_code" size="20" value="<%=Utility.trimNull(contractMap.get("PRE_CODE"))%>"></td>
						</tr>
						<tr>
                            <td align=right><font color="red">*</font>合同是否寄回 : </td>
                            <td>
                                <select size="1" name="if_mail" onkeydown="javascript:nextKeyPress(this)" style="width:120px;">
                                    <option value="0" <%if(if_mail.intValue() == 0){%>selected<%} %>>请选择</option>
                                    <option value="1" <%if(if_mail.intValue() == 1){%>selected<%} %>>是</option>
                                    <option value="2" <%if(if_mail.intValue() == 2){%>selected<%} %>>否</option>
                                </select>
                            </td>
                        </tr>
				
						<tr>
				            <td colspan="4">
				                <table border="0" width="100%">
                                    <tr>
                                        <td align="right">
                                        <button class="xpbutton3" accessKey=s id="btnSave" name="btnSave" onclick="javascript:if(document.theform.onsubmit()){ document.theform.btnSave.disabled='true';document.theform.submit();}">保存(<u>S</u>)</button>
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
                                        <button class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" onclick="javascript:window.returnValue=null;window.close();">取消(<u>C</u>)</button>
								&nbsp;&nbsp;
                                        </td>
                                    </tr>
					            </table>
                            </td>
                        </tr> 
                    </table>                      
			</td>
		</tr>
	</table>
	
</form>
</BODY>
</HTML>
<%
contract.remove();
customer.remove();
product.remove();
%>