<%@ page contentType="text/html; charset=GBK" import="enfo.crm.callcenter.*,enfo.crm.web.*,enfo.crm.tools.*,enfo.crm.affair.*,enfo.crm.customer.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>
<% 
//传入参数
String phoneNumber = Utility.trimNull(request.getParameter("phoneNumber"));
String my_status = Utility.trimNull(request.getParameter("my_status"));
Integer target_custid = Utility.parseInt(Utility.trimNull(request.getParameter("target_custid")),new Integer(0));//目标客户
Integer input_flag =  Utility.parseInt(Utility.trimNull(request.getParameter("input_flag")),new Integer(0));//传入参数标识 0.dialog 1.iframe
Integer input_flag2 =  Utility.parseInt(Utility.trimNull(request.getParameter("input_flag2")),new Integer(0));//传入参数标识 1:被动；2:主动；
String closeStr = "关闭";
if(input_flag.intValue()==1){
	closeStr = "返回";	
}
%>
<HTML>
<HEAD>
	<TITLE>通话中记录</TITLE>
	<meta http-equiv="X-UA-Compatible" content="IE=7" >
	<META http-equiv=Content-Type content="text/html; charset=gbk">
	<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
	<META HTTP-EQUIV="Expires" CONTENT="0">
	<BASE TARGET="_self">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/widgets/ext/resources/css/ext-all.css"></link>
	<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
	<script type="text/javascript" src='<%=request.getContextPath()%>/widgets/ext/ext-base.js'></script>
	<script type="text/javascript" src='<%=request.getContextPath()%>/widgets/ext/ext-all.js'></script>
	<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
	<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default.js"></SCRIPT>
	<script type='text/javascript' src='/dwr/interface/ccService.js'></script>	
	<script type='text/javascript' src='/dwr/engine.js'></script>
	<script type='text/javascript' src='/dwr/util.js'></script>
<script language="javascript">
	oState = {};//常量
	/*初始化方法*/
	window.onload = function(){
		var phoneNumber = document.getElementById("phoneNumber").value;
		var target_custid = document.getElementById("target_custid").value;
		var input_flag = document.getElementById("input_flag").value;
		var input_flag2 = document.getElementById("input_flag2").value;
		var cc_extension = document.getElementById("cc_extension").value;
		var input_operatorCode = document.getElementById("input_operatorCode").value;
		var tel_show = hiddenTel(phoneNumber);
		if(cc_extension=='null'){
			cc_extension = 0;
		}
		//保存到常量
		oState.phoneNumber = phoneNumber.toString();
		oState.target_custid = target_custid;
		oState.input_flag = input_flag;
		oState.input_flag2 = input_flag2;
		oState.input_operatorCode = input_operatorCode;
		oState.cc_extension = cc_extension;

		

		sl_alert('您要拨打的电话号码是:'+tel_show+'，请在摘机后单击确认按钮');
		var my_status = '<%=my_status%>';
		//alert(my_status);
		//if(my_status=="st_picked"){
			if(target_custid!=0){
			//document.getElmentById("trCustList").style.display="none";
				ccService.getCustInfoById(target_custid,refreshCustInfoCallBack);			
			}
			else{
				initCustInfo();
			}
			var ccVO = {direction:input_flag2,callTime:0,callLength:0,managerID:input_operatorCode,extension:cc_extension,cust_id:target_custid,contactID:0,phoneNumStr:phoneNumber,businessID:0,content:'',status:0,callCenterID:0,input_man:input_operatorCode};
			ccService.appendCallRecord(ccVO,{callback:function(data){
				document.getElementById("callRecordID").value = data;
				setCallRecordID(data);
			}});
			callOut();
		//}
		//else{
			//sl_alert("未摘机,请确认摘机");
			//cancelAction();
		//}
	};
	//隐藏电话号码
	function hiddenTel(tel){
		var tel_hidden = '';
		if(tel.length>4){
			for(i=0;i<tel.length-4;i++){
				tel_hidden = tel_hidden +"*";
			}
			tel_hidden = tel_hidden + tel.substring(tel.length-4,tel.length);
		}
		else{
			tel_hidden = "****";
		}
		return tel_hidden;
	};
	//呼出电话号码
	function callOut(){
		//if(oState.input_flag==1){
			document.parentWindow.parent.callout('<%=cc_extension%>',oState.phoneNumber);
		//}
		//else if(oState.input_flag==0){
			//window.dialogArguments.top.callout('<%=cc_extension%>',oState.phoneNumber);
		//}
	};
	//撤销
	function cancelAction(){
		if(oState.input_flag==1){
			//history.back(1);
			var url = '/affair/sms/cust_tel.jsp?input_flag=1&target_custid=<%=target_custid%>&my_status=<%=my_status%>';
			location = url ;	
		}
		else if(oState.input_flag==0){
			window.close();
		}
		else if(oState.input_flag==2){
			document.parentWindow.parent.dialogHidden();
		}
	};
	//设置callRecordID
	function setCallRecordID(callRecordID){
		if(oState.input_flag==1){
			document.parentWindow.parent.document.getElementById("callRecordID").value = callRecordID;
		}
		else if(oState.input_flag==0){
			window.dialogArguments.top.document.getElementById("callRecordID").value = callRecordID;
		}
	};
	function saveCallInInfo(){
		var callRecordID = document.getElementById("callRecordID").value;
		var input_operatorCode = document.getElementById("input_operatorCode").value;
		var callin_record_content = document.getElementById("callin-record").value;
		var updateSql = "UPDATE TCALLRECORDS SET CONTENT='"+callin_record_content+"' WHERE SERIAL_NO="+callRecordID;
		if(callRecordID!=0){
			ccService.updateCallRecord(updateSql,input_operatorCode,callRecordID,{callback:function(data){
				var rsList = data.split("|");
				sl_alert(rsList[1]);			
			}});
		}
	};
	function showInfo(){
		var custid = document.getElementById("target_custid").value;
		if(custid==0||custid==null||custid==''){
			sl_alert('未指定客户');
		}
		else{
			var url = '/client/clientinfo/client_query_info.jsp?showflag=1&cust_id='+custid;
			showModalDialog(url,window,'dialogWidth:800px;dialogHeight:600px;status:0;help:0');
			showWaitting(0);
		}
	}
	/*************************************刷新客户信息**************************************************************************/
	function refreshCustInfo(){
		var custId = Ext.getDom("cc_cust_name").value;
		document.getElementById("target_custid").value = custId
		ccService.getCustInfoById(custId,refreshCustInfoCallBack)
	}
	function refreshCustInfoCallBack(data){
		var custJsonStr = data;
		custJson = eval(custJsonStr);
		if(custJson.length>0){
			DWRUtil.removeAllOptions("cc_cust_name");
			DWRUtil.addOptions("cc_cust_name", custJson, "CUST_ID", "CUST_NAME");
			//Ext.getDom("cc_cust_no").value = Ext.util.Format.undef(custJson[0].CUST_NO);
			Ext.getDom("select_cc_cust_name").value = Ext.util.Format.undef(custJson[0].CUST_NAME);
			Ext.getDom("cc_cust_name2").value = Ext.util.Format.undef(custJson[0].CUST_NAME);
			Ext.getDom("cc_cust_type").value = Ext.util.Format.undef(custJson[0].CUST_TYPE_NAME);
			Ext.getDom("cc_cust_grade").value = Ext.util.Format.undef(custJson[0].CUST_LEVEL_NAME);
			Ext.getDom("cc_rg_money").value = Ext.util.Format.undef(custJson[0].TOTAL_MONEY);
			Ext.getDom("cc_zr_money").value = Ext.util.Format.undef(custJson[0].EXCHANGE_AMOUNT);
			Ext.getDom("cc_df_money").value = Ext.util.Format.undef(custJson[0].BACK_AMOUNT);
			Ext.getDom("cc_sy_money").value = Ext.util.Format.undef(custJson[0].BEN_AMOUNT);
			Ext.getDom("cc_buy_date").value = Ext.util.Format.undef(custJson[0].LAST_RG_DATE);	
		}else{
			Ext.MessageBox.alert('警告','没有找到匹配的用户信息');
		}
	}
	/*****************************************初始化用户列表**********************************************************************/
	function initCustInfo(){
		var cc_tel_num = Ext.getDom("phoneNumber").value;
		
		ccService.getCustList(cc_tel_num,oState.input_operatorCode,initCustInfoCallback);
    }
	function initCustInfoCallback(data){
		var custJsonStr = data;
		custJson = eval(custJsonStr);
		if(custJson.length>0){
			DWRUtil.removeAllOptions("cc_cust_name");
			DWRUtil.addOptions("cc_cust_name", custJson, "CUST_ID", "CUST_NAME");
			//Ext.getDom("cc_cust_no").value = Ext.util.Format.undef(custJson[0].CUST_NO);
			Ext.getDom("select_cc_cust_name").value = Ext.util.Format.undef(custJson[0].CUST_NAME);
			Ext.getDom("cc_cust_name2").value = Ext.util.Format.undef(custJson[0].CUST_NAME);
			Ext.getDom("cc_cust_type").value = Ext.util.Format.undef(custJson[0].CUST_TYPE_NAME);
			Ext.getDom("cc_cust_grade").value = Ext.util.Format.undef(custJson[0].CUST_LEVEL_NAME);
			Ext.getDom("cc_rg_money").value = Ext.util.Format.undef(custJson[0].TOTAL_MONEY);
			Ext.getDom("cc_zr_money").value = Ext.util.Format.undef(custJson[0].EXCHANGE_AMOUNT);
			Ext.getDom("cc_df_money").value = Ext.util.Format.undef(custJson[0].BACK_AMOUNT);
			Ext.getDom("cc_sy_money").value = Ext.util.Format.undef(custJson[0].BEN_AMOUNT);
			Ext.getDom("cc_buy_date").value = Ext.util.Format.undef(custJson[0].LAST_RG_DATE);
			Ext.getDom("cc_card_type_name").value = Ext.util.Format.undef(custJson[0].CARD_TYPE_NAME);
	        Ext.getDom("cc_card_id").value = Ext.util.Format.undef(custJson[0].CARD_ID);
	        Ext.getDom("cc_manager_man_id").value = Ext.util.Format.undef(custJson[0].SERVICE_MAN);
	        Ext.getDom("cc_extension").value = Ext.util.Format.undef(custJson[0].EXTENSION);
		}else{
			Ext.MessageBox.alert('警告','没有找到匹配的用户信息');
		}
	}
</script>
</HEAD>
<BODY>
<input type="hidden" name="input_operatorCode" id="input_operatorCode" value="<%=input_operatorCode%>">
<input type="hidden" name="target_custid" id="target_custid" value='<%=target_custid%>' />
<input type="hidden" name="phoneNumber" id="phoneNumber" value='<%=phoneNumber%>' />
<input type="hidden" name="input_flag" id="input_flag" value='<%=input_flag%>' />
<input type="hidden" name="input_flag2" id="input_flag2" value='<%=input_flag2%>' />
<input type="hidden" name="cc_extension" id="cc_extension" value='<%=cc_extension%>' />
<input type="hidden" name="callRecordID" id="callRecordID" value='' />
<input type="hidden" name="cc_tel_num" id="cc_tel_num" value=""/>

<div id="callin-cust-info" align="center" style="margin-top:5px;">
	<form id="callin-cust-info-form" class="x-form" method=post>
		<fieldset id="callin-cust-info-fs" class="x-form-label-right" style="height:130px;width:98%;">
			<legend>来电客户基本信息</legend>
					<div id="cc_cust_info"></div>
					<table style="width:100%;border-collapse:3px;border-spacing:3px;" >
						<tr id="trCustList">
							<td align="right" height="25" width="80px">客户名称：</td>
							<td colspan="5" valign="top">
								<select id="cc_cust_name" name="cc_cust_name" style="width:290px;" onchange="javascript:refreshCustInfo();">
								</select>&nbsp;&nbsp;
								<input readonly class="edline" id="select_cc_cust_name" name="select_cc_cust_name" value="" style="visibility:hidden"/>
							</td>
						</tr>
						<tr>
							<td align="right" height="25" width="80px">客户名称：</td>
							<td><input readonly class="edline" size="20" id="cc_cust_name2" name="cc_cust_name2" value=""/><span>[<a title="点击查看客户信息" href="javascript:showInfo();" class="a2">详细</a>]</span></td>
							<td align="right" width="80px">客户类别：</td>
							<td><input readonly class="edline" size="25" id="cc_cust_type" name="cc_cust_type" value=""/></td>
							<td align="right" width="80px">客户级别：</td>
							<td><input readonly class="edline" size="25" id="cc_cust_grade" name="cc_cust_grade" value=""/></td>
						</tr>
						<tr>
							<td align="right" height="25">认购金额：</td>
							<td><input readonly class="edline" size="25" id="cc_rg_money" name="cc_rg_money" value=""/></td>
							<td align="right">转让金额：</td>
							<td><input readonly class="edline" size="25" id="cc_zr_money" name="cc_zr_money" value=""/></td>
							<td align="right">兑付金额：</td>
							<td><input readonly class="edline" size="25" id="cc_df_money" name="cc_df_money" value=""/></td>
						</tr>
						<tr>
							<td align="right" height="25">受益金额：</td>
							<td><input readonly class="edline" size="25" id="cc_sy_money" name="cc_sy_money" value=""/></td>
							<td align="right">最近购买：</td>
							<td><input readonly class="edline" size="25" id="cc_buy_date" name="cc_buy_date" value=""/></td>
						</tr>
					</table>	
		</fieldset>

		<div id="record" style="margin-top:5px;">
			<fieldset  id="callin-record-fs" class="x-form-label-right" style="height:225px;width:98%;">
				<legend>话务记事</legend>
				<textarea id="callin-record" name="callin-record" class=" x-form-textarea x-form-field " style="width:99%;height:190px;overflow:auto"></textarea>
			</fieldset>
			<div align="right" style="margin-top:7px; margin-right:7px;">
				<button type="button" class="xpbutton2" id="btnClose" name="btnClose" onclick="javascript:cancelAction();"><%=closeStr%></button>&nbsp;&nbsp;&nbsp;&nbsp;
				<button type="button" class="xpbutton2" id="btnSave" name="btnSave" onclick="javascript:saveCallInInfo();">保存</button>
			</div>
		</div>
	</form>
</BODY>

</HTML>
