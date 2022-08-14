<%@ page contentType="text/html; charset=GBK" import="enfo.crm.web.*,java.io.*"%>
<%@ page import="enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.marketing.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>

<%
try{
   String sQuery = request.getParameter("rets");
   String[] paras = Utility.splitString(sQuery + " ", "$");
   Integer sProduct_idS = Utility.parseInt(request.getParameter("product_id"),new Integer(0));
   String cust_name = Utility.trimNull(request.getParameter("cust_name"));
   String productName=request.getParameter("product_name");

  Integer service_man=Utility.parseInt(request.getParameter("service_man"),new Integer(0));
  String city_name = Utility.trimNull(request.getParameter("city_name"));

  String cust_no = Utility.trimNull(request.getParameter("cust_no"));
  String card_id = Utility.trimNull(request.getParameter("card_id"));
  String query_contract_sub_bh = Utility.trimNull(request.getParameter("query_contract_sub_bh"));
  Integer cust_type = Utility.parseInt(request.getParameter("cust_type"),new Integer(0));
  String prov_level = Utility.trimNull(request.getParameter("prov_level"));
  BigDecimal min_rg_money = Utility.parseDecimal(request.getParameter("min_rg_money"), null);
  BigDecimal max_rg_money = Utility.parseDecimal(request.getParameter("max_rg_money"), null);
  Integer sub_product_id = Utility.parseInt(request.getParameter("sub_product_id"),new Integer(0));

Integer depart_id = new Integer(0) ;
Integer cell_flag = new Integer(1);
Integer cell_id = new Integer(0);
Integer sq_start_date = Utility.parseInt(request.getParameter("sq_start_date"),null);
Integer sq_end_date = Utility.parseInt(request.getParameter("sq_end_date"),null);
String admin_manager = Utility.trimNull(request.getParameter("admin_manager"));


if(paras.length>1){
   
   sProduct_idS = Utility.parseInt(paras[13].trim(),new Integer(0));
   cust_name = Utility.trimNull(paras[3].trim());
   productName=Utility.trimNull(paras[14].trim());
   service_man=Utility.parseInt(paras[10].trim(),new Integer(0));
   city_name = Utility.trimNull(paras[11].trim());
   cust_no = Utility.trimNull(paras[6].trim());
   card_id = Utility.trimNull(paras[4].trim());
   query_contract_sub_bh = Utility.trimNull(paras[1].trim());
   cust_type = Utility.parseInt(paras[2].trim(),new Integer(0));
   prov_level = Utility.trimNull(request.getParameter("prov_level"));
   min_rg_money = Utility.parseDecimal(paras[8].trim(), null);
   max_rg_money = Utility.parseDecimal(paras[9].trim(), null);

	depart_id= Utility.parseInt(paras[17].trim(),new Integer(0));
	cell_flag = Utility.parseInt(paras[15].trim(),new Integer(1));
	cell_id = Utility.parseInt(paras[16].trim(),new Integer(0));
    sq_start_date = Utility.parseInt(paras[18].trim().trim(),null);
    sq_end_date = Utility.parseInt(paras[19].trim(),null);
    sub_product_id = Utility.parseInt(paras[20].trim(),null);
    admin_manager = Utility.trimNull(request.getParameter("admin_manager"));

}

//int treeFlag = Argument.getSyscontrolValue("TREE0001");//选择用哪个版本的树 1旧版本,2新版本



%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<meta http-equiv="Pragma" content="no-cache">
<title>查询条件设置</title>
<link href="/includes/default.css" type=text/css rel=stylesheet>
<base target="_self">
</head>
<script src="/includes/default.vbs" language="vbscript"></script>
<script src="/includes/default.js" language="javascript"></script>
<SCRIPT LANGUAGE="javascript" SRC="/includes/calendar.js"></SCRIPT>
<script type='text/javascript' src='/dwr/engine.js'></script>
<script type='text/javascript' src='/dwr/util.js'></script>
<script type='text/javascript' src='/dwr/interface/utilityService.js'></script>
<script language="javascript">
function setProduct(value)
{
	prodid=0;
	if (event.keyCode == 13 && value != "")
	{
        j = value.length;
		for(i=0;i<document.theform.product_id.options.length;i++)
		{
			if(document.theform.product_id.options[i].text.substring(0,j)==value)
			{
				document.theform.product_id.options[i].selected=true;
				prodid = document.theform.product_id.options[i].value;
				break;
			}	
		}
		if (prodid==0)   
		{
			sl_alert('输入的产品编号不存在！');
			document.theform.productid.value="";
			document.theform.product_id.options[0].selected=true;	
		}			
	}
	nextKeyPress(this);
}
function op_save(){
		var vproduct_id=0 ;
		var vproductid=0 ;
		var vproduct_name='' ;
		if(document.theform.cell_flag.value==1){
			vproduct_id = document.theform.product_id.value;
			vproductid = document.theform.productid.value;
			vproduct_name = document.theform.product_name.value;
		}

		if(document.theform.qs_start_date_picker.value!="")
		{
			if(!sl_checkDate(document.theform.qs_start_date_picker,"签署起始日期"))	return false;
				syncDatePicker(document.theform.qs_start_date_picker, document.theform.qs_start_date);	
		}	

		if(document.theform.qs_end_date_picker.value!="")
		{
			if(!sl_checkDate(document.theform.qs_end_date_picker,"签署结束日期"))	return false;
				syncDatePicker(document.theform.qs_end_date_picker, document.theform.qs_end_date);	
		}		

        var sub_product_id = 0;
        if(document.theform.sub_product_id)
        sub_product_id = document.theform.sub_product_id.value;
		
		var ret = " "+" $ "+document.theform.query_contract_sub_bh.value+" $ "+document.theform.cust_type.value+
			" $ "+document.theform.cust_name.value;
		
		ret = ret + " $ " + document.theform.card_id.value+" $ ";	
		ret = ret + " $ " + document.theform.cust_no.value+" $ ";	
		ret = ret + " $ " + document.theform.min_rg_money.value+" $ "+document.theform.max_rg_money.value+
			" $ "+document.theform.service_man.value;	
			
		ret = ret + " $ " + document.theform.city_name.value+" $ "+vproductid+
			" $ "+vproduct_id;
		ret = ret + " $ " + vproduct_name ;	
		ret = ret + " $ " + document.theform.cell_flag.value;//-------------
		ret = ret + " $ " + document.theform.cell_id.value;
		ret = ret + " $ " + document.theform.depart_id.value ;
		ret = ret + " $ " + document.theform.qs_start_date.value ;
		ret = ret + " $ " + document.theform.qs_end_date.value;
		ret = ret + " $ " + sub_product_id + " $ ";
        ret = ret + " $ " + document.theform.admin_manager.value;
		
		window.returnValue = ret;

		window.close();
}

function setProduct(value)
{
	prodid=0;
	if (event.keyCode == 13 && value != "")
	{
        j = value.length;
		for(i=0;i<document.theform.product_id.options.length;i++)
		{
			if(document.theform.product_id.options[i].text.substring(0,j)==value)
			{
				document.theform.product_id.focus();
				document.theform.product_id.options[i].selected=true;
				prodid = document.theform.product_id.options[i].value;
				break;
			}	
		}
		if (prodid==0)
		{
			sl_alert('输入的产品编号不存在!');
			document.theform.productid.value="";
			document.theform.product_id.options[0].selected=true;	
		}		
	}
}

function SelectCellFlag(value){
	if(value == "1")
	{
		tr1.style.display="";
		tr2.style.display="";
		tr4.style.display="";
		tr3.style.display="none";
		if(document.theform.data.value==1){
				tr_sub_product_id.style.display="";
			}else{
				tr_sub_product_id.style.display="none";
			}
	}else if(value == "2")
	{
		tr1.style.display="none";
		tr2.style.display="none";
		tr4.style.display="none";
		tr3.style.display="";
        tr_sub_product_id.style.display="none";
	}
	document.theform.cell_flag.value=value;
}

/**  
 * 选择产品单元树 
 */
function openReportTree() {
	var v = showModalDialog('<%=request.getContextPath()%>/client/analyse/report_tree.jsp', '', 'dialogWidth:500px;dialogHeight:550px;status:0;help:0');
	if (v != null)
		document.theform.cell_id.value = v;
}

<%--
function openReportTree()
{
<%if(treeFlag == 2) {%>
	var v = showModalDialog('report_tree2.jsp', '', 'dialogWidth:550px;dialogHeight:650px;status:0;help:0');
<%}else{%>
	var v = showModalDialog('report_tree.jsp', '', 'dialogWidth:500px;dialogHeight:550px;status:0;help:0');
<%}%>
	if (v != null)
	{
		document.theform.cell_id.value = v;
	}
}--%>

function productChange(product_id){
	var _prodcut_id = product_id;
	var tr_sub_product_id;
	var sub_product_id;
	
	sub_product_id = document.theform.sub_product_id;
	tr_sub_product_id = document.getElementById("tr_sub_product_id");

	DWRUtil.removeAllOptions(sub_product_id);
	utilityService.getProductFlag(product_id,'sub_flag',function(data){
	document.theform.data.value = data;
		if(data==1){
				tr_sub_product_id.style.display="";
			}else{
				tr_sub_product_id.style.display="none";
			}
	});
	utilityService.getSubProductJson(product_id,3,function(data){
		var json = eval(data);
		DWRUtil.addOptions(sub_product_id,{"0":"请选择"});
		for(i=0;i<json.length;i++){
			DWRUtil.addOptions(sub_product_id,json[i]);
	  }
	  DWRUtil.setValue('sub_product_id',<%=sub_product_id%>);
  });
}

window.onload = function(){
    if(<%=sProduct_idS%> == document.theform.product_id.value)
	productChange(<%=sProduct_idS%>);
};
</script>

<body class="BODY" onkeydown="chachEsc(window.event.keyCode)">
<form name="theform">
<input type="hidden" name="cell_id" value="<%=cell_id %>">
<input type="hidden" name="cell_flag"  value="<%=cell_flag.intValue() %>">
<input type="hidden" name="data">
<table border="0" width="100%" cellspacing="0" cellpadding="10">
	<tr>
		<td>
		<table border="0" width="100%" cellspacing="0" cellpadding="4" style="border-collapse: collapse" bordercolor="#111111">
			<tr>
				<td colspan="4" background="/images/headerbg.gif" height="11"><font color="#FFFFFF"><b>查询条件：</b></font></td>
			</tr>
			
			<tr>					
						<td  align="right">
						合同编号:
						</td>
						<td align="left">
						 <input type="text" onkeydown="javascript:nextKeyPress(this)" name="query_contract_sub_bh" size="15" value="<%=query_contract_sub_bh%>">&nbsp;&nbsp;						
						</td>
						<td  align="right" >客户类别:</td>
							<td align="left"> 
								<SELECT size="1" name="cust_type" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 150px">
									<%=Argument.getCustTypeOptions(cust_type)%>
								</SELECT>
						</td>
						</tr>
						<tr>
						<td  align="right">
						客户名称: 
						</td>
						<td align="left">
							<input type="text" name="cust_name" value="<%=cust_name%>" onkeydown="javascript:nextKeyPress(this);" size="25">
						</td>
						<td  align="right">
						证件号码:
						</td>
						<td align="left"> 						
							<input type="text" name="card_id" value="<%=card_id%>" onkeydown="javascript:nextKeyPress(this);"  size="25">
						</td>
					</tr>
					<tr>	
						
						<td  align="right" >
						客户编号: 
						</td>
						<td align="left">
							<input type="text" name="cust_no" value="<%=cust_no%>" onkeydown="javascript:nextKeyPress(this);" size="25">
						</td>
						<td align="right">设计部门:</td>
						<td>
							<select name="depart_id" onkeydown="javascript:nextKeyPress(this)"  style="WIDTH: 150px">	
				               	<%=Argument.getDepartOptions(depart_id)%>
				            </select>
						</td>			
					</tr>
					<tr><td align="right">
							认购金额从:
						 </td>
						 <td  align="left"  colspan=3>
							<input type="text" name="min_rg_money" onkeydown="javascript:nextKeyPress(this)" value="<%=Utility.trimNull(min_rg_money)%>" size="23">元
							到:<input type="text" name="max_rg_money" onkeydown="javascript:nextKeyPress(this)" value="<%=Utility.trimNull(max_rg_money)%>" size="23">元&nbsp;&nbsp;
						</td>
						</tr>
					<tr>
					<td align="right">客户经理:</td>
					<td><select size="1" name="service_man" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 120px">
						<%=Argument.getRoledOperatorOptions(input_bookCode, 2,service_man)%>
					</select></td>
					<td  align="right" >
						推介地: 
						</td>
						<td align="left" >
							<input type="text" name="city_name" value="<%=city_name%>" onkeydown="javascript:nextKeyPress(this);" size="25">
						</td>
					</tr>

					<tr >
						<td align=right >产品选择方式:</td>
						<td colspan="3" >
						<input type="radio" value=1 onclick="javascript:SelectCellFlag(this.value);" <%if(cell_flag!=null && cell_flag.intValue()==1) out.print("checked");%> onkeydown="javascript:nextKeyPress(this)" name="cellflag" class="flatcheckbox" checked>独立产品&nbsp;&nbsp;
						<input type="radio" value=2 onclick="javascript:SelectCellFlag(this.value);" <%if(cell_flag!=null && cell_flag.intValue()==2) out.print("checked");%> onkeydown="javascript:nextKeyPress(this)" name="cellflag" class="flatcheckbox" >产品单元
		
						</td>
					</tr>
		
					<tr id = "tr1" <%if(cell_flag!=null && cell_flag.intValue()==1)out.print("style='display:'");else out.print("style='display:none'");%>>
						<td align="right">产品编号:</td >
						<td align="left"  colspan="3">
						<input type="text" maxlength="16" name="productid" value="" onkeydown="javascript:setProduct(this.value);nextKeyPress(this);" maxlength=8 size="15">&nbsp;<button type="button"  class="searchbutton" onkeydown="javascript:nextKeyPress(this)" onclick="javascript:searchProduct(document.theform.productid.value);" /></button>
						</td >
					</tr>
					<tr id = "tr2" <%if(cell_flag!=null && cell_flag.intValue()==1)out.print("style='display:'");else out.print("style='display:none'");%>>
						<td align="right">选择产品:</td >
						<td align="left"  colspan="3">
                         <SELECT name="product_id" style="WIDTH: 440px" class="productname" onchange="javascript:productChange(this.value)" onkeydown="javascript:nextKeyPress(this)"><%=Argument.getProductListOptions(input_bookCode, sProduct_idS, "",input_operatorCode,0)%></SELECT>
						</td>
					</tr>
			        <tr id="tr_sub_product_id" style="display:none">			
						<td align="right">子产品:</td>
						<td align="left" colspan="3" id="td_sub_product_id">
							<SELECT name="sub_product_id" onkeydown="javascript:nextKeyPress(this)" class="productname">
									<%=Argument.getSubProductOptions2(overall_product_id, new Integer(0),sub_product_id,0,"")%> 
							</SELECT>
						</td>
					</tr>
					<tr id = "tr4" <%if(cell_flag!=null && cell_flag.intValue()==1)out.print("style='display:'");else out.print("style='display:none'");%>>
                  		<td align="right">产品名称:</td >
                      	<td align="left" colspan=3>
                       		<INPUT type="text" name="product_name" size="58"  onkeydown="javascript:nextKeyPress(this)" <%if (productName!=null)%> value=<%=productName%>>
                       	</td>
					</tr>
					<tr id = "tr3" <%if(cell_flag!=null && cell_flag.intValue()==2)out.print("style='display:'");else out.print("style='display:none'");%>>
						<td align = "right">产品单元:</td >
						<td align="left"  colspan="3">
						<button type="button"  class="xpbutton3" name="btnQuery1" onclick="javascript:openReportTree();">请选择...</button>
						</td>
					</tr>

                    <tr> 
                      <td align="right">签署日期区间:</td >
                      <td align="left" colspan=3>
						<INPUT TYPE="text" NAME="qs_start_date_picker" class=selecttext onkeydown="javascript:nextKeyPress(this)" value="<%=Format.formatDateLine(sq_start_date)%>" >
						<INPUT TYPE="button" value="▼" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.qs_start_date_picker,theform.qs_start_date_picker.value,this);" tabindex="13">
						<INPUT TYPE="hidden" NAME="qs_start_date" value="">至
						<INPUT TYPE="text" NAME="qs_end_date_picker" class=selecttext onkeydown="javascript:nextKeyPress(this)" value="<%=Format.formatDateLine(sq_end_date)%>" >
						<INPUT TYPE="button" value="▼" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.qs_end_date_picker,theform.qs_end_date_picker.value,this);" tabindex="13">
						<INPUT TYPE="hidden" NAME="qs_end_date" value="">
                       </td>

                    </tr>
                    <tr> 
                      <td align="right">执行经理:</td >
                      <td align="left" colspan=3>
                        <input type="text" size="58"  name="admin_manager" value=<%=admin_manager%>  >
                       </td>

                    </tr>

			<tr>
			
				<td colspan="4" width="100%" height="1">
				<table border="0" width="100%">
					<tr>
                       <td align="left"></td>
                       <td align="right"><button type="button"  class="xpbutton3" accessKey=o id="btnDel" name="btnDel" onclick="javascript:op_save();">确定(<u>O</u>)</button>&nbsp;&nbsp;&nbsp;
        			   <button type="button"  class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" onclick="javascript:window.returnValue=null;window.close();">取消(<u>C</u>)</button></td>
					</tr>
				</table>
				</td>
			</tr>
			   <%--<tr>
			
				<td colspan="4" width="100%" height="1">
				<table border="0" width="100%">
					<tr>
						<td align="right">
						<button type="button"  class="xpbutton3" accessKey=f id="btnDel" name="btnDel" onclick="javascript:op_save();">查询(<u>F</u>)</button>
						&nbsp;&nbsp;
						<button type="button"  class="xpbutton3" accessKey=c id="btnSave" name="btnSave" onclick="javascript:window.returnValue=null;window.close();">取消(<u>C</u>)</button>
						</td>
					</tr>
				</table>
				</td>
			</tr>--%>
		</table>
		</td>
	</tr>
</table>
</form>
</body>
</html>
<%
}catch(Exception e){throw e;}
 %>

