<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.dao.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.vo.*,java.util.*,java.math.*"  %>
<%@ include file="/includes/operator.inc" %>
<%
try{
Integer cust_id = null;
Integer query_cust_id = Utility.parseInt(request.getParameter("customer_cust_id"), null);
Integer sub_product_id = Utility.parseInt(request.getParameter("sub_product_id"),new Integer(0));

int readonly=0;
String strButton="��ѡ��";
CustomerInfoLocal customer = EJBFactory.getCustomerInfo();
CustomerInfoVO cVO = new CustomerInfoVO();

BenifitorLocal benifitor = EJBFactory.getBenifitor();
BenifitorVO bVO = new BenifitorVO();
String period_unit="��";
BigDecimal gain_rate = new BigDecimal(0);

boolean bSuccess = false;
Integer serial_no = Utility.parseInt(request.getParameter("serial_no"), null);
String contract_bh = request.getParameter("contract_bh");
Integer product_id = Utility.parseInt(request.getParameter("product_id"), null);
Integer prov_flag2 = Utility.parseInt(Utility.trimNull(request.getParameter("prov_flag")),null);
Integer prov_change_date = Utility.parseInt(Utility.trimNull(request.getParameter("prov_change_date")), new Integer(Utility.getCurrentDate()));
ProductLocal product=EJBFactory.getProduct();
ProductVO pVO = new ProductVO();

if(product_id!=null && serial_no==null)
{		
	pVO.setProduct_id(product_id);
	List pList = product.load(pVO);
	if(pList.size()>0){
		Map map = (Map)pList.get(0);
		period_unit=Argument.getProductUnitName(Utility.parseInt(Utility.trimNull(map.get("PERIOD_UNIT")),new Integer(0)));
		bVO.setValid_period(Utility.parseInt(Utility.trimNull(map.get("VALID_PERIOD")),new Integer(0)));
		bVO.setBen_date(Utility.parseInt(Utility.trimNull(map.get("START_DATE")),new Integer(0)));
		bVO.setBen_end_date(Utility.parseInt(Utility.trimNull(map.get("END_DATE")),new Integer(0)));
	}
}

bVO.setContract_bh(contract_bh);
bVO.setProduct_id(product_id);
bVO.setBook_code(input_bookCode);

String provChecked = "";
int check_flag = Utility.parseInt(request.getParameter("check_flag"), 0);
String show="";
String show1="";

if(check_flag==2 || check_flag==3){
show = "readonly class='edline'";
show1="disabled";}

if (serial_no!=null && !request.getMethod().equals("POST"))
{
	readonly=1;
	strButton="�鿴";
	
	bVO.setSerial_no(serial_no);	
    List bList2 = benifitor.load(bVO);
    if(bList2.size()>0){
    	Map map = (Map)bList2.get(0);
    	bVO.setBen_amount(Utility.parseDecimal(Utility.trimNull(map.get("BEN_AMOUNT")),new BigDecimal(0.00)));
    	bVO.setJk_type(Utility.trimNull(map.get("JK_TYPE")));
    	bVO.setJk_type_name(Utility.trimNull(map.get("JK_TYPE_NAME")));
    	bVO.setBen_date(Utility.parseInt(Utility.trimNull(map.get("BEN_DATE")),new Integer(0)));
    	bVO.setBen_end_date(Utility.parseInt(Utility.trimNull(map.get("BEN_END_DATE")),new Integer(0)));
    	bVO.setCust_acct_name(Utility.trimNull(map.get("CUST_ACCT_NAME")));
    	bVO.setBank_acct(Utility.trimNull(map.get("BANK_NAME")));
    	bVO.setBank_name(Utility.trimNull(map.get("BANK_NAME")));
    	bVO.setBank_sub_name(Utility.trimNull(map.get("BANK_SUB_NAME")));
    	bVO.setValid_period(Utility.parseInt(Utility.trimNull(map.get("VALID_PERIOD")),new Integer(0)));
    	bVO.setProv_flag(Utility.parseInt(Utility.trimNull(map.get("PROV_FLAG")),new Integer(1)));
    	bVO.setProv_level_name(Utility.trimNull(map.get("PROV_LEVEL_NAME")));

    	bVO.setCust_id(Utility.parseInt(Utility.trimNull(map.get("CUST_ID")),new Integer(0)));
		pVO.setProduct_id(Utility.parseInt(Utility.trimNull(map.get("PRODUCT_ID")),new Integer(0)));	
		//��ʾ������,�Ȳ���
		List level_gain_rate = CrmDBManager.listBySql("SELECT TOP 1 B.GAIN_RATE FROM INTRUST..TGAINLEVEL A LEFT JOIN INTRUST..TGAINLEVELRATE B ON A.SERIAL_NO = B.DF_SERIAL_NO WHERE A.PRODUCT_ID = ? AND A.PROV_FLAG = ? AND A.PROV_LEVEL = ? ORDER BY B.START_DATE DESC", new Object[]{
				pVO.getProduct_id(),
				bVO.getProv_flag(),
				Utility.trimNull(map.get("PROV_LEVEL"))
		});		
		if(level_gain_rate != null && level_gain_rate.size() >0){
			Map map_gain_rate = (Map)level_gain_rate.get(0);
			gain_rate = Utility.parseDecimal(Utility.trimNull(map_gain_rate.get("GAIN_RATE")),new BigDecimal(0.00));
			gain_rate = gain_rate.multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP);
		}
		//�Ȳ���END
		
		List pList2 = product.load(pVO);
		if(pList2.size()>0){
			Map pmap = (Map)pList2.get(0);
			period_unit=Argument.getProductUnitName(Utility.parseInt(Utility.trimNull(pmap.get("PERIOD_UNIT")),new Integer(0)));
		}		
		cVO.setCust_id(bVO.getCust_id());
		cVO.setInput_man(input_operatorCode);
		List cList = customer.load(cVO);
		if(cList.size()>0){
			Map cmap = (Map)cList.get(0);
			cust_id=Utility.parseInt(Utility.trimNull(cmap.get("CUST_ID")),new Integer(0));
			cVO.setCust_name(Utility.trimNull(cmap.get("CUST_NAME")));
			cVO.setCard_id(Utility.trimNull(cmap.get("CARD_ID")));
			cVO.setCust_type_name(Utility.trimNull(cmap.get("CUST_TYPE_NAME")));
		}
    }
}

if (request.getMethod().equals("POST"))
{
	bVO.setSerial_no(serial_no);
	bVO.setProv_level(request.getParameter("prov_level"));
	bVO.setInput_man(input_operatorCode);
	
    bVO.setProv_flag(prov_flag2);
	bVO.setProv_change_date(prov_change_date);
	bVO.setInput_man(input_operatorCode);
	benifitor.modi_prov_level(bVO);
	bSuccess = true;
}
Integer prov_flag = Utility.parseInt(bVO.getProv_flag(),new Integer(1));
String sProv_flag  = null;

if(prov_flag.intValue() == 2)
	sProv_flag ="һ��";
else if(prov_flag.intValue() == 3)
	sProv_flag ="�Ӻ�";
else
	sProv_flag ="����";
%>

<HTML>
<HEAD>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META HTTP-EQUIV="Expires" CONTENT="0">
<BASE TARGET="_self">
<LINK href="/includes/default.css" type=text/css rel=stylesheet>
<title>��������Ϣ</title>

<SCRIPT LANGUAGE="vbscript" SRC="/includes/default.vbs"></SCRIPT>

<script type="text/javascript" src="<%=request.getContextPath()%>/includes/jQuery/js/jquery-1.6.2.min.js"></script>
<script type="text/javascript">
	var j$ = jQuery.noConflict();
</script>

<SCRIPT LANGUAGE="javascript" SRC="/includes/default.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/investment.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/calendar.js"></SCRIPT>
<script type='text/javascript' src='/dwr/engine.js'></script>
<script type='text/javascript' src='/dwr/util.js'></script>
<script type='text/javascript' src='/dwr/interface/utilityService.js'></script>

<script language=javascript>

<%if (bSuccess)
{
%>
	window.returnValue = 1;
	window.close();
<%}%>

function changeProv_level(_productid,prov_level){
	j$("#span_gain_rate")[0].innerHTML = "";
	j$.ajax({
		url: "benifiter_level_info_t_do.jsp",
		type : "get",
		cache:false,
		data: {
			product_id : _productid,
			prov_flag : j$("#prov_flag").val(),
			prov_level : prov_level
		},
		success: function(data){
			j$("#span_gain_rate")[0].innerHTML = data;
		}
	});
}

function validateForm(form)
{	
	if(theform.prov_level.value==theform.old_prov_level.value){
		window.returnValue=null;
		window.close();
		return false;
	
	}
	
	if(form.serial_no.value=="")
	{
		if(form.customer_cust_id.value=="")	
		{
			sl_alert("��ѡ�������ˣ�");
			return false;
		}	
	}
	
	if(theform.prov_level.value==""){
	 alert("��ѡ�����漶��");
	 return false;
	}
	return sl_check_update();
}

function showAcctNum(value)
{	
	if (trim(value) == "")
		bank_acct_num.innerText = "";
	else
		bank_acct_num.innerText = "(" + showLength(value) + " λ )";
}

//�鿴�ͻ���Ϣ
function getCustomer(cust_id){
	var url = '<%=request.getContextPath()%>/marketing/customerInfo2.jsp?cust_id='+ cust_id ;	
	var v = showModalDialog(url,'','dialogWidth:720px;dialogHeight:720px;status:0;help:0;');
}
function provFlagChange(product_id,sub_product_id,prov_flag,prov_level_id){
	var _prodcut_id = product_id;
	var _sub_product_id = sub_product_id;
	var _prov_flag = prov_flag;
	var prov_level;
	
	prov_level = document.getElementById(prov_level_id);
	
	DWRUtil.removeAllOptions(prov_level);
	
	utilityService.getProvLevelJson(product_id,_sub_product_id,prov_flag,function(__data){
		var __json = eval(__data);
		DWRUtil.addOptions(prov_level,{"":"��ѡ��"});
		for(i=0;i<__json.length;i++){
			DWRUtil.addOptions(prov_level,__json[i]);
		}		
	});
	
	//gain_rate change
	changeProv_level(product_id,j$("#prov_level").val());
}

//Description:  ���п���LuhmУ��

    //LuhmУ�����16λ���п��ţ�19λͨ�ã�:
    
    // 1.��δ��У��λ�� 15����18��λ���Ŵ������α�� 1 �� 15��18����λ������λ���ϵ����ֳ��� 2��
    // 2.����λ�˻��ĸ�ʮλȫ����ӣ��ټ�������ż��λ�ϵ����֡�
    // 3.���ӷ��ͼ���У��λ�ܱ� 10 ������
function luhmCheck(bankno){
	bankno = trimString(bankno);
	if (bankno.length < 16 || bankno.length > 19 || bankno.length<0) {
		alert("���п��ų��ȱ�����16��19֮��");
		document.getElementById("bank_acct").style.color ="red";
		return false;
	}
	var num = /^\d*$/;  //ȫ����
	if (!num.exec(bankno)) {
		alert("���п��ű���ȫΪ����");
		document.getElementById("bank_acct").style.color ="red";
		return false;
	}
	//alert("------1------");
	//��ͷ6λ
	var strBin="10,18,30,35,37,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,58,60,62,65,68,69,84,87,88,94,95,98,99";    
	if (strBin.indexOf(bankno.substring(0, 2))== -1) {
		alert("���п��ſ�ͷ6λ�����Ϲ淶");
		document.getElementById("bank_acct").style.color ="red";
		return false;
	}
    var lastNum=bankno.substr(bankno.length-1,1);//ȡ�����һλ����luhm���бȽϣ�

    var first15Num=bankno.substr(0,bankno.length-1);//ǰ15��18λ
    var newArr=new Array();
    for(var i=first15Num.length-1;i>-1;i--){    //ǰ15��18λ����������
        newArr.push(first15Num.substr(i,1));
    }
    var arrJiShu=new Array();  //����λ*2�Ļ� <9
    var arrJiShu2=new Array(); //����λ*2�Ļ� >9
    
    var arrOuShu=new Array();  //ż��λ����
    for(var j=0;j<newArr.length;j++){
        if((j+1)%2==1){//����λ
            if(parseInt(newArr[j])*2<9)
            arrJiShu.push(parseInt(newArr[j])*2);
            else
            arrJiShu2.push(parseInt(newArr[j])*2);
        }
        else //ż��λ
        arrOuShu.push(newArr[j]);
    }
    //alert("------2------");
    var jishu_child1=new Array();//����λ*2 >9 �ķָ�֮��������λ��
    var jishu_child2=new Array();//����λ*2 >9 �ķָ�֮�������ʮλ��
    for(var h=0;h<arrJiShu2.length;h++){
        jishu_child1.push(parseInt(arrJiShu2[h])%10);
        jishu_child2.push(parseInt(arrJiShu2[h])/10);
    }        
    
    var sumJiShu=0; //����λ*2 < 9 ������֮��
    var sumOuShu=0; //ż��λ����֮��
    var sumJiShuChild1=0; //����λ*2 >9 �ķָ�֮��������λ��֮��
    var sumJiShuChild2=0; //����λ*2 >9 �ķָ�֮�������ʮλ��֮��
    var sumTotal=0;
    for(var m=0;m<arrJiShu.length;m++){
        sumJiShu=sumJiShu+parseInt(arrJiShu[m]);
    }
    
    for(var n=0;n<arrOuShu.length;n++){
        sumOuShu=sumOuShu+parseInt(arrOuShu[n]);
    }
    
    for(var p=0;p<jishu_child1.length;p++){
        sumJiShuChild1=sumJiShuChild1+parseInt(jishu_child1[p]);
        sumJiShuChild2=sumJiShuChild2+parseInt(jishu_child2[p]);
    }      
    //�����ܺ�
    sumTotal=parseInt(sumJiShu)+parseInt(sumOuShu)+parseInt(sumJiShuChild1)+parseInt(sumJiShuChild2);
    //alert("------3------");
    //����Luhmֵ
    var k= parseInt(sumTotal)%10==0?10:parseInt(sumTotal)%10;        
    var luhm= 10-k;
    //alert(lastNum+"----"+luhm);
    if(lastNum==luhm){
    //alert("������֤ͨ��");
	document.getElementById("bank_acct").style.color ="";
    return true;
    }
    else{
    alert("���п���У�鲻ͨ��");
	document.getElementById("bank_acct").style.color ="red";
    return false;
    }        
}

</script>
<BODY class="BODY body-nox" onkeydown="javascript:chachEsc(window.event.keyCode)">
<%@ include file="/includes/waiting.inc"%>
<form name="theform" method="post" action="benifiter_level_info.jsp" onsubmit="javascript:return validateForm(this);">
<input type=hidden name="serial_no" value="<%=Utility.trimNull(serial_no)%>">
<input type=hidden name="product_id" value="<%=Utility.trimNull(product_id)%>">
<input type=hidden name="ben_amount1" value="<%=bVO.getBen_amount()%>">
<input type=hidden name="check_flag" value="<%=check_flag%>">
<input type="hidden" name="customer_cust_id" value="<%=Utility.trimNull(cust_id)%>">
<input type="hidden" name="old_prov_level" value="<%=bVO.getProv_level() %>">
<TABLE cellSpacing=0 cellPadding=0 border=0 width="100%"  >
	
		<TR>
			<TD vAlign=top align=left>
			<TABLE cellSpacing=0 cellPadding=1 border=0 width="100%">
				
					<TR>
						<TD align=center valign=top >
						<table border="0" width="100%" cellspacing="0" cellpadding="0">
							<tr>
								<td height="9" class="page-title"><b>��������Ϣ</b></td>
							</tr>
						</table>
						<br/>
						<table border="0" width="100%" cellspacing="3" class="product-list">
							<tr>
								<td align="right" height="27">��ͬ���:</td>
								<td height="27" colspan="3"><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" size="20" name="contract_bh" value="<%=contract_bh%>"></td>
							</tr>
					<tr>
				<td align="right">����������:</td>
				<td>
					<input maxlength="100" readonly class='edline'  name="customer_cust_name" size="38" onkeydown="javascript:nextKeyPress(this);" value="<%=Utility.trimNull(cVO.getCust_name())%>">&nbsp;&nbsp;&nbsp;
<%if (input_operator.hasFunc(menu_id,116) ){%>					
<button type="button"   class="xpbutton2" accessKey=e name="btnEdit" title="�ͻ���Ϣ" onclick="javascript:getCustomer(<%=cust_id %>);"><%=strButton%></button><%}%>
				</td>

				<td align="right">���������:</td>
				<td ><INPUT readonly class='edline' name="customer_cust_type_name" size="20" value="<%=Utility.trimNull(cVO.getCust_type_name())%>" onkeydown="javascript:nextKeyPress(this);"></td>
			</tr>	
			<tr>
				<td align="right">֤������:</td>
				<td><input readonly class='edline' name="customer_card_id" onkeydown="javascript:nextKeyPress(this);"  maxlength="40" value="<%=Utility.trimNull(cVO.getCard_id())%>" size="20"></td>

								<td align="right" height="22">������:</td>
								<td height="22"><input type="text"  <%=show%> onkeydown="javascript:nextKeyPress(this)" onkeyup="javascript:showCnMoney(this.value,ben_amount_cn)" name="ben_amount" size="20" value="<%=Utility.trimNull(Format.formatMoney(bVO.getBen_amount()==null?0:bVO.getBen_amount().doubleValue(),2))%>"></td>
							</tr>
							<tr>
								<td align="right" height="26">���渶������:</td>
								<td height="26" ><input type="text" <%=show%> onkeydown="javascript:nextKeyPress(this)" name="bank_sub_name" size="50" value="<%=Utility.trimNull(bVO.getBank_name())%><%=Utility.trimNull(bVO.getBank_sub_name())%>">
								
								</td>
								<td align="right" height="29">���渶�ʽ:</td>
								<td height="29" >
								<input type="text"  <%=show%> name="jk_type_name" size="20" value="<%=Utility.trimNull(bVO.getJk_type_name())%>">
								<input type="hidden"  <%=show1%> name="jk_type" size="20" value="<%=Utility.trimNull(bVO.getJk_type())%>">
								</td>
							</tr>
							<tr>
								<td align="right" height="24">�����������ʻ�����:</td>
							   <td height="24"><input <%=show%>  name="cust_acct_name" type="text" onkeydown="javascript:nextKeyPress(this)" size=50  value=<%=Utility.trimNull(bVO.getCust_acct_name())%>>  </td>
								<td align="right" height="21">�����˺�:</td>
								<td height="21"><input type="text" <%=show%> onkeydown="javascript:nextKeyPress(this)" onblur="luhmCheck(this.value);" onkeyup="javascript:showAcctNum(this.value)" name="bank_acct" size="25" value="<%=Utility.trimNull(bVO.getBank_acct())%>"><span id="bank_acct_num" class="span"></span></td>
							</tr>
							<tr>
								
							</tr>
							<tr>
								<td align="right">������������:</td>
								<td>
								<INPUT type="text" NAME="ben_date_picker"  <%=show%> size="50"	value="<%=Utility.trimNull(Format.formatDateLine(bVO.getBen_date()))%>��<%=Utility.trimNull(Format.formatDateLine(bVO.getBen_end_date()))%>" onkeydown="javascript:nextKeyPress(this)">
								</td>
								<td align="right" height="22">��������:</td>
								<td height="22" colspan="3"><INPUT  NAME="valid_period"  <%=show%> size="10"   value="<%=Utility.trimNull(bVO.getValid_period())%>"><%=Utility.trimNull(period_unit)%></td>
							</tr>
							<tr>
								<td align="right" height="22"><font color=red><b>*�������ȼ�:</b></font></td>
								<td height="24">
									<!-- 
									<input <%=show%> name="old_prov_flag_name" value="<%=Utility.trimNull(sProv_flag)%>" size="10">
									<input type="hidden" name="old_prov_flag" value="<%=Utility.trimNull(prov_flag)%>" size="10">
									-->
									<SELECT <%if(check_flag != 3) out.print(show1);%> size="1" id="prov_flag" name="prov_flag" onkeydown="javascript:nextKeyPress(this)" style="width: 80px" onchange="javascript:provFlagChange(<%=product_id%>,<%=sub_product_id%>,this.value,'prov_level');">
									<%
										out.println(Argument.getProductProvFlag(product_id,sub_product_id,bVO.getProv_flag()));
									%>
								</SELECT></td>
								<td align="right" height="24"><font color=red><b>*���漶��:</b></font></td>
								<td height="24">
									<!-- 
									<input <%=show%> name="old_prov_level_name" value="<%=bVO.getProv_level_name()%>" size="10">
									-->
									<SELECT <%if(check_flag != 3) out.print(show1);%> size="1" id="prov_level" name="prov_level" style="width: 80px" onchange="javascript:changeProv_level('<%=product_id%>',this.value);">
									
									<%
										out.println(Argument.getProductProvLevel(product_id,sub_product_id,prov_flag,bVO.getProv_level()));
									%>
								</SELECT>
								<label>�����ʣ�<span id="span_gain_rate"><%=gain_rate %></span> %</label>
								</td>
							</tr>
							
							<tr>
                        	    <td align="right">*�������ڣ�</td>
                        	    <td>
                        	        <INPUT TYPE="text" NAME="prov_change_date_picker" onkeydown="javascript:nextKeyPress(this)"  class=selecttext 
                        		        value="<%=Format.formatDateLine(prov_change_date)%>">
                        		    <INPUT TYPE="button" value="��" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.prov_change_date_picker,theform.prov_change_date_picker.value,this);" tabindex="13">
                        		    <INPUT TYPE="hidden" NAME="prov_change_date"   value="<%=prov_change_date%>">    
                        	    </td>
                        	</tr>
						</table>
						<table border="0" width="100%" >
							<tr valign =top>
								<td align="right" height="15">
								<br/>
								<button type="button"  class="xpbutton3"  accessKey=s onclick="javascript:if(document.theform.onsubmit()){ disableAllBtn(true);document.theform.submit();}">����(<u>S</u>)</button>
								&nbsp;&nbsp;
								<button type="button"  class="xpbutton3" accessKey=c onclick="javascript:window.returnValue=null;window.close();">ȡ��(<u>C</u>)</button>
								&nbsp;&nbsp;
								</td>
							</tr>
						</table>
						</TD>
					</TR>
			</TABLE>
			</TD>
		</TR>
</TABLE>
</form>
<%@ include file="/includes/foot.inc"%>
</BODY>
</HTML>
<%benifitor.remove();
customer.remove();
}catch(Exception e){
	throw e;
}
%>