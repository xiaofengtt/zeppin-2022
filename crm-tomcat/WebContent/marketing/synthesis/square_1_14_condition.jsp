<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.web.*,java.io.*"%>
<%@ page import="enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.marketing.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>

<%
   String sQuery = request.getParameter("rets");
   String[] paras = Utility.splitString(sQuery + " ", "$");
   
   String prov_level = Utility.trimNull(request.getParameter("prov_level"));
   String cust_name = Utility.trimNull(request.getParameter("cust_name"));
   String contract_sub_bh = Utility.trimNull(request.getParameter("contract_sub_bh"));
   String sy_type1=Utility.trimNull(request.getParameter("sytype1"));
   String sy_type2=Utility.trimNull(request.getParameter("sytype2"));
   String sy_type3=Utility.trimNull(request.getParameter("sytype3"));
   String sy_type4=Utility.trimNull(request.getParameter("sytype4"));
   String sy_type5=Utility.trimNull(request.getParameter("sytype5"));
   Integer beginDate=Utility.parseInt(request.getParameter("beginDate"),new Integer(0));
   Integer endDate=Utility.parseInt(request.getParameter("endDate"),new Integer(0));
   Integer sub_product_id=Utility.parseInt(request.getParameter("sub_product_id"),new Integer(0));
   Integer link_man=Utility.parseInt(request.getParameter("link_man"),new Integer(0));
       
 if(paras.length>1){
    
   sy_type1=Utility.trimNull(paras[9].trim());
   sy_type2=Utility.trimNull(paras[10].trim());
   sy_type3=Utility.trimNull(paras[11].trim());
   sy_type4=Utility.trimNull(paras[12].trim());
   sy_type5=Utility.trimNull(paras[13].trim());
   
   
   contract_sub_bh=Utility.trimNull(paras[3].trim());
   overall_product_id= Utility.parseInt(paras[5].trim(),new Integer(0));
   prov_level=Utility.trimNull(paras[1].trim());
   beginDate=Utility.parseInt(paras[2].trim(),new Integer(0));
   endDate=Utility.parseInt(paras[8].trim(),new Integer(0));
   cust_name=Utility.trimNull(paras[7]);
   sub_product_id=Utility.parseInt(paras[14].trim(),new Integer(0));
   link_man=Utility.parseInt(paras[15].trim(),new Integer(0));
}


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
function selectAll1(){
   var sy_type1=document.theform.sy_type1;
   var sy_type2=document.theform.sy_type2;
   var sy_type3=document.theform.sy_type3;
   var sy_type4=document.theform.sy_type4;
   var sy_type5=document.theform.sy_type5;
   var sy_type6=document.theform.sy_type6;
   //alert(sy_type6.checked);
   if(sy_type6.checked){
        //alert("全选");
        sy_type6.checked=true;
		sy_type1.checked=true;
		sy_type2.checked=true;
		sy_type3.checked=true;
		sy_type4.checked=true;
		sy_type5.checked=true;
   }else{
        //alert("取消");
        sy_type6.checked=false
       	sy_type1.checked=false;
		sy_type2.checked=false;
		sy_type3.checked=false;
		sy_type4.checked=false;
		sy_type5.checked=false; 
   }
}

function selectSingle(){
    var sy_type6=document.theform.sy_type6;
     sy_type6.checked=false
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

       //if(!sl_checkChoice(document.theform.product_id, "产品名称")) return false;
	
	   syncDatePicker(document.theform.begin_date_picker, document.theform.begin_date);
	   syncDatePicker(document.theform.end_date_picker, document.theform.end_date);
	

  	   if(document.theform.sy_type1.checked)
  		  document.theform.sy_type1.value="111601";
  	   if(document.theform.sy_type2.checked)
  		  document.theform.sy_type2.value="111602";
  	   if(document.theform.sy_type3.checked)
  		  document.theform.sy_type3.value="111605";
  	   if(document.theform.sy_type4.checked)
  	 	 document.theform.sy_type4.value="111603";
  	  if(document.theform.sy_type5.checked)
  		 document.theform.sy_type5.value="111604";	

      var sub_product_id = 0;
      if(document.theform.sub_product_id)
      sub_product_id = document.theform.sub_product_id.value;

		var ret = " "+" $ "+document.theform.prov_level.value+" $ "+document.theform.begin_date.value;
		
		ret = ret + " $ " + document.theform.contract_sub_bh.value+" $ ";	
		ret = ret + " $ " + document.theform.product_id.value+" $ ";	
		ret = ret + " $ " + document.theform.cust_name.value;
		ret = ret + " $ " + document.theform.end_date.value;
		ret = ret + " $ " + document.theform.sy_type1.value+" $ "+document.theform.sy_type2.value+" $ "+document.theform.sy_type3.value+" $ "+document.theform.sy_type4.value+" $ "+document.theform.sy_type5.value
        ret = ret + " $ " +	sub_product_id;
		ret = ret + " $ " +	document.theform.link_man.value;

		window.returnValue = ret;
		window.close();
}
function searchProduct(value)
{
	prodid=0;
	if (value != "")
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
		document.theform.product_id.focus();					
	}	
}

function productChange(product_id){
	var _prodcut_id = product_id;
	var tr_sub_product_id;
	var sub_product_id;
	
	sub_product_id = document.theform.sub_product_id;
	tr_sub_product_id = document.getElementById("tr_sub_product_id");

	DWRUtil.removeAllOptions(sub_product_id);
	
	utilityService.getProductFlag(product_id,'sub_flag',function(data){
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
if(<%=overall_product_id%>==document.theform.product_id.value)
productChange(<%=overall_product_id%>);
};
</script>

<body class="BODY" onkeydown="chachEsc(window.event.keyCode)">
<form name="theform">
<table border="0" width="100%" cellspacing="0" cellpadding="10">
	<tr>
		<td>
		<table border="0" width="100%" cellspacing="0" cellpadding="4" style="border-collapse: collapse" bordercolor="#111111">
			<tr>
				<td colspan="4" background="/images/headerbg.gif" height="11"><font color="#FFFFFF"><b>查询条件：</b></font></td>
			</tr>
			<tr>
						
						<td align="right">优先级别:</td>
						<td><SELECT size="1" name="prov_level" onkeydown="javascript:nextKeyPress(this)" style="width: 120px">
									<%=Argument.getProvlevelOptions(prov_level)%>
								</SELECT>
						</td>
						
						<td align=right></td>
						<td align=right></td>
						<td align=right></td>
						<td align=right></td>
			</tr>				
			<tr>
						<td align=right>结算日期:</td>
						<td align="left">
						<INPUT TYPE="text" NAME="begin_date_picker" class=selecttext  value="<%=Format.formatDateLine(beginDate)%>">
					    <INPUT TYPE="button" value="▼" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.begin_date_picker,theform.begin_date_picker.value,this);" tabindex="13">
						<INPUT TYPE="hidden" NAME="begin_date" value="">
						</td>
						<td align="right">到 </td>
						<td align="left">
							<INPUT TYPE="text" NAME="end_date_picker" class=selecttext  value="<%=Format.formatDateLine(endDate)%>">
								 <INPUT TYPE="button" value="▼" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.end_date_picker,theform.end_date_picker.value,this);" tabindex="13">
								<INPUT TYPE="hidden" NAME="end_date" value="">
						</td>
					  
					  </tr>
					  <tr>  
					   <td align="right">合同编号:</td>
						<td align="left">
							<input type="text" name="contract_sub_bh" value="<%=contract_sub_bh%>" onkeydown="javascript:nextKeyPress(this);" size="25">
						</td></tr>
		 
		  <tr>
			 <td align=right>全选</td>
			 <td colspan="3" align="left">
			 <input <%if(sy_type1.equals("111601")&&sy_type2.equals("111602")&&sy_type3.equals("111605")&&sy_type4.equals("111603")&&sy_type5.equals("111604")) out.print("checked");%>
			  onkeydown="javascript:nextKeyPress(this)" type="checkbox" class="flatcheckbox" onClick="javascript:selectAll1();"  name="sy_type6">&nbsp;&nbsp;&nbsp;
					      发行期利息<input <%if(sy_type1.equals("111601")) out.print("checked");%>  onkeydown="javascript:nextKeyPress(this)" type="checkbox" class="flatcheckbox" value="" name="sy_type1"  onClick="javascript:selectSingle();">&nbsp;&nbsp;&nbsp;
					      中间收益<input <%if(sy_type2.equals("111602")) out.print("checked");%> onkeydown="javascript:nextKeyPress(this)" type="checkbox" class="flatcheckbox" value="" name="sy_type2" onClick="javascript:selectSingle();">&nbsp;&nbsp;&nbsp;
					      到期本金<input <%if(sy_type3.equals("111605")) out.print("checked");%> onkeydown="javascript:nextKeyPress(this)" type="checkbox" class="flatcheckbox" value="" name="sy_type3" onClick="javascript:selectSingle();">&nbsp;&nbsp;&nbsp;
					      到期收益<input <%if(sy_type4.equals("111603")) out.print("checked");%> onkeydown="javascript:nextKeyPress(this)" type="checkbox" class="flatcheckbox" value="" name="sy_type4" onClick="javascript:selectSingle();">&nbsp;&nbsp;&nbsp;
					      兑付期利息<input <%if(sy_type5.equals("111604")) out.print("checked");%> onkeydown="javascript:nextKeyPress(this)" type="checkbox" class="flatcheckbox" value="" name="sy_type5" onClick="javascript:selectSingle();">&nbsp;&nbsp;&nbsp;
			 </td>	
			
		</tr>
		<tr style='display:'>			
          <td align="right">产品编号:</td >
          <td align="left">
            <input type="text" maxlength="16" name="productid" value="" onkeydown="javascript:setProduct(this.value);" maxlength=8 size="10">
           &nbsp;<button type="button"  class="searchbutton" onclick="javascript:searchProduct(document.theform.productid.value);" /></button>
         </td >
          <td  align="right">客户名称:
				<input type="text" name="cust_name" value="<%=cust_name%>" onkeydown="javascript:nextKeyPress(this);" size="25">
			</td>
        </tr>
         
        <tr>  
          <td align="right">产品选择:</td >
          <td align="left" colspan=2>
             <SELECT name="product_id" class="productname" onchange="javascript:productChange(this.value)" onkeydown="javascript:nextKeyPress(this)"><%=Argument.getProductListOptions(input_bookCode, overall_product_id, "",input_operatorCode,status)%></SELECT>
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
		<tr>
			<td align="right">合同销售人员:</td>
			<td colspan="3">
				<select name="link_man" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 120px">
					<%=Argument.getIntrustRoledOperatorOptions(input_bookCode, 2,link_man)%>
				</select>
			</td>
		</tr>
        <tr>			
         <td align="right" colspan="4"><button type="button"  class="xpbutton3" name="btnQuery" accessKey=o onclick="javascript:op_save();">确定(<u>O</u>)</button>
         &nbsp;&nbsp;&nbsp;
		<button type="button"  class="xpbutton3" name="btnQuery" accessKey=b
		onclick="javascript:window.close();">返回(<u>B</u>)</button>
         </td>
       </tr>	
	
		</table>
		</td>
	</tr>
</table>
</form>
</body>
</html>

