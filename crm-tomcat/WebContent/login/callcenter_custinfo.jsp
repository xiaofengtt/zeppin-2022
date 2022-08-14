<%@ page contentType="text/html; charset=GBK" import="enfo.crm.callcenter.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ include file="/includes/operator.inc" %>


<% String phone=Utility.trimNull(request.getParameter("phone"));
Integer callid=Utility.parseInt(request.getParameter("callid"),new Integer(0));
Integer custid=Utility.parseInt(request.getParameter("custid"),new Integer(0));
Integer saveflag=Utility.parseInt(request.getParameter("saveflag"),new Integer(0));
Integer call_custid=new Integer(0);
Integer new_cc_id=new Integer(0);
enfo.crm.callcenter.CallCenterLocal callcenter=EJBFactory.getCallCenter();
Map map=new HashMap();
List list=null;
CCVO vo=new CCVO();
if (!"".equals(phone)){
	vo.setPhoneNumStr(phone);
	vo.setInputMan(input_operatorCode);
	vo.setInput_man(input_operatorCode);
	IPageList pageList= callcenter.listCustByPhone2(vo,1,50);
	if (pageList!=null){
		list=pageList.getRsList();
		if (list!=null && list.size()==1){
			map=(Map)list.get(0);
			call_custid=Utility.parseInt((Integer)map.get("CUST_ID"),new Integer(0));
		}
	}
	if (saveflag.intValue()==0){
		vo.setContactID(new Integer(0));
		vo.setBusinessID(new Integer(0));
		vo.setDirection(new Integer(1));
		vo.setManagerID(input_operatorCode);
		vo.setExtension(Utility.parseInt(cc_extension,new Integer(0)));
		vo.setCust_id(call_custid);
		vo.setStatus("1");
		vo.setCallCenterID(callid);
		vo.setCallLength(new Integer(0));
		new_cc_id=callcenter.addCallRecords(vo);
	}
}
if (custid.intValue()>0){
	enfo.crm.customer.CustomerLocal custBean=EJBFactory.getCustomer();
	List list2=custBean.queryCustAllInfo(custid,new Integer(0),input_operatorCode);
	if (list2 !=null && !list2.isEmpty()){
		map=(Map)list2.get(0);
	}
}
if (saveflag.intValue()==3){
	vo.setSerial_no(Utility.parseInt(request.getParameter("new_cc_id"),new Integer(0)));
	vo.setContent(Utility.trimNull(request.getParameter("callin-record")));
	vo.setStatus("1");
	vo.setCallCenterID(callid);
	callcenter.modiCallRecords(vo);
}
%>
<!DOCTYPE html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=7">
<LINK HREF="<%=request.getContextPath()%>/includes/default.css" TYPE="text/css" REL="stylesheet">
<script type="text/javascript" src="../lib/js/plugins/jquery-1.8.2.js"></script>
<script type="text/javascript">
	var j$ = jQuery.noConflict();
</script>
<script type="text/javascript" >

function showCustDetail(custid){
	//j$("#callcenterArea").show();
	if(custid != null && custid != ''){
		window.open('<%=request.getContextPath()%>/client/clientinfo/client_query_info.jsp?cust_id='+custid);
	}
}
function refreshCustInfo(){
	location="callcenter_custinfo.jsp?phone=<%=phone%>&custid="+j$("#cc_cust_name").val();
}
function saveCallInInfo(){
	var contentr=j$("#callin-record").val();//alert(contentr);
	//contentr=encodeURI(contentr,"UTF-8");alert(contentr);
	var url2="callcenter_custinfo_action.jsp?new_cc_id=<%=new_cc_id%>&callinrecord="+contentr;
	//var url2="callcenter_custinfo_action.jsp"
	j$.ajax({
 		url:url2,
 		type:'post',
 		//data:{"new_cc_id":<%=new_cc_id%>,"callinrecord":j$("#callin-record").val()},
 		//dataType: "json",
 		//async : false, //默认为true 异步
 		//contentType: "application/x-www-form-urlencoded; charset=UTF-8",
 		error:function(data){
    		alert("保存出错");
 		},
 		success:function(data){
 			if (j$.trim(data)==""){
 				alert("保存成功");
 			}else{
 				alert(data);
 			}
 		}
	 });
}
</script>
<style>
		.phonenum{position:absolute; right:30px; top:30px; background-color:#fff;}
		.the-table tr td{white-space:nowrap; padding:5px;}
		.the-table tr td input{width:170px;}
</style>
</head>
<body>
<form id="callin-cust-info-form" class="x-form" method=post name="theform" action="callcenter_custinfo.jsp">
<div class="page-title"><b>客户来电</b>
	<font color='red' style='float:right; color:#197fe6;font-weight: bold;'>来电号码：<span id="cc_tel_num_str"><%=phone %></span></font>
</div>
<div id="callin-dlg">
	<div class="x-dlg-bd">
		<div id="callininfo" class="x-layout-inactive-content" style="padding:10px;">
			<div id="callin-cust-info">
					<input type="hidden" name="saveflag" id="saveflag" value="2">
					<div>
						<div style="float: left; text-align: right; width: 460px; display: none;" id="update_cust_tel">
							<table>
                                <tr>
                                    <td align="right" class="phonenum-parent"><font color='red' style='font-weight: bold;'>注意：号码未匹配到客户[</font>
                                    <input type="radio" name="cust_update_flag" id="cust_update_flag1" value="1" class="flatcheckbox" onclick="javascript:showConditionTel(this);">
                                    </td>
                                    <td>更新联系方式&nbsp;&nbsp;<font color='red' style='font-weight: bold;'>]</font></td>
                                    <td align="right">
									<!--  
									<input type="radio" name="cust_update_flag" id="cust_update_flag2" value="2" class="flatcheckbox" onclick="javascript:showConditionTel(this);"></td>
                                    <td>添加联系方式
                                    </td>
									-->
                                </tr>
                            </table>
						</div>
					</div>
					
					<style>
					.info{ color: #8a6d3b;
						background-color: #fcf8e3;
						padding: 15px;
						margin-bottom: 20px;
						border: 1px solid #faebcc;
						border-radius: 4px;
						font-size:14px;
						}	
						.the-table{border-spacing:0; border-collapse:collapse; width:100%;}	
						.the-table tr td{border:1px dotted #aaa;}
						.the-table tr td select,
						.the-table tr td .edline{width:100%; height:24px; padding:3px; border:1px solid #aaa!important; }
						.sub-title{font-size:14px; padding:10px 5px;margin-top:20px;  color:#197fe6; font-weight:bold;}
					</style>	
						<div class="sub-title" style="margin:0;"><%if (list==null || list.isEmpty()){%><font>注意！号码未匹配到客户</font><%}else{ %>匹配到的客户信息<%} %>
						<input type="button" value="客户详细" class="xpbutton6" style="float:right;" id="btnSave" name="btnSave" onclick="javascript:showCustDetail();">
						
						<!--  
						<div style="padding-left: 30px;">
						<div id="zj_server_man" style="display: none; float: left; padding-top: 3px;"><a href="#" onclick="javascript:telePhoneSwitch();"><font color="red"><b>电话转接</b></font></a>&nbsp;|&nbsp;</div>
						<div id="customer_details" style="float: right; padding: 5px; "><a href="#" ><font><b></b></font></a></div>
                                    </div>-->
						</div>
							<div id="cc_cust_info"></div>
							<table class="the-table" style="width:100%;border-collapse:0px;border-spacing:0px;white-space:nowrap;" >
								<tr>
									<td align="right" height="25">客户名称：</td>
									<td colspan="5" valign="top">
										<select id="cc_cust_name" name="cc_cust_name" style="width:200px;" onchange="javascript:refreshCustInfo();">
										<% if (list!=null && list.size()>0){
											Map map2=new HashMap();
											for (int i=0;i<list.size();i++){
												map2=(Map)list.get(i);
										%>
												<option value="<%=map2.get("CUST_ID")%>"><%=map2.get("CUST_NAME") %></option>
										<%	}
										}
										%>
										</select>&nbsp;&nbsp;
										<!-- <img src="/images/search.gif" style="cursor:hand"  onclick="javascript:showCondition()"/> -->
										<input readonly class="edline" id="select_cc_cust_name" name="select_cc_cust_name" value="" style="visibility:hidden"/>
									</td>
									<!-- 
									<td height="25">联系电话：<input readonly class="edline" size="15" id="cc_cust_tel_num" name="cc_cust_tel_num" value=""/>
									</td>
									-->	
								</tr>
								<tr>
									<td align="right" height="25">客户编号：</td>
									<td><input readonly class="edline"  id="cc_cust_no" name="cc_cust_no" value="<%=Utility.trimNull(map.get("CUST_NO"))%>"/>			
										<input type="hidden" id="cc_cust_id" name="cc_cust_id" value="<%=map.get("CUST_ID")%>"/>									
									</td>
									<td align="right">客户姓名：</td>
									<td><input readonly class="edline"  id="cc_cust_name" name="cc_cust_name" value="<%=Utility.trimNull(map.get("CUST_NAME")) %>"/></td>
									<td align="right" id="customer_sex_title">性别：</td>
									<td ><input readonly class="edline"  id="customer_sex_content" name="customer_sex_content" value="<%=Utility.trimNull(map.get("SEX_NAME")) %>"/></td>
								</tr>
								<tr>
									<td align="right" height="25">证件类型：</td>
									<td><input readonly class="edline"  id="cc_card_type_name" name="cc_card_type_name" value="<%=Utility.trimNull(map.get("CARD_TYPE_NAME")) %>"/></td>
									<td align="right">证件号码：</td>
									<td><input readonly class="edline"  id="cc_card_id" name="cc_card_id" value="<%=Utility.trimNull(map.get("CARD_ID")) %>"/></td>
									<td align="right">客户经理：</td>
									<td>
										<input readonly class="edline"  id="cc_manager_man" name="cc_manager_man" value="<%=Utility.trimNull(map.get("SERVICE_MAN")) %>"/>
										<input type="hidden" id="cc_manager_man_id" name="cc_manager_man_id" value=""/>
										<input type="hidden" id="cc_extension" name="cc_extension" value=""/>
										<input type="hidden" id="user_id" name="user_id" value=""/>
										<input type="hidden" id="cc_is_team" name="cc_is_team" value=""/>
									</td>
								</tr>
								<tr>
									<td align="right" height="25">联系方式：</td>
									<td><input readonly class="edline"  id="cc_rg_money" name="cc_rg_money" value="<%=Utility.trimNull(map.get("TOUCH_TYPE_NAME")) %>"/></td>
									<td align="right">手机1：</td><!-- 转让金额 -->
									<td><input readonly class="edline"  id="cc_zr_money" name="cc_zr_money" value="<%=Utility.trimNull(map.get("MOBILE")) %>"/></td>
									<td align="right">手机2：</td><!-- 兑付金额 -->
									<td><input readonly class="edline"  id="cc_df_money" name="cc_df_money" value="<%=Utility.trimNull(map.get("BP")) %>"/></td>
								</tr>
								<tr>
									<td align="right" height="25">公司电话：</td><!-- 受益金额 -->
									<td><input readonly class="edline"  id="cc_sy_money" name="cc_sy_money" value="<%=Utility.trimNull(map.get("O_TEL")) %>"/></td>
									<td align="right">家庭电话：</td><!-- 最近购买 -->
									<td><input readonly class="edline"  id="cc_buy_date" name="cc_buy_date" value="<%=Utility.trimNull(map.get("H_TEL")) %>"/></td>
									<td align="right">EMAIL：</td>
									<td><input readonly class="edline"  id="cc_cust_emial" name="cc_cust_emial" value="<%=Utility.trimNull(map.get("E_MAIL")) %>"/></td>
								</tr>
                                <tr>
									<td align="right" height="25">邮寄地址：</td>
									<td><input readonly class="edline"  id="cc_post_address" name="cc_sy_money" value="<%=Utility.trimNull(map.get("POST_ADDRESS")) %>"/></td>
									<td align="right">邮编：</td>
									<td><input readonly class="edline"  id="cc_post_code" name="cc_buy_date" value="<%=Utility.trimNull(map.get("POST_CODE")) %>"/></td>
									<td align="right"></td>
									<td></td>
								</tr>
								<tr>
									<td align="right" height="25">邮寄地址2：</td>
									<td><input readonly class="edline"  id="cc_post_address2" name="cc_sy_money" value="<%=Utility.trimNull(map.get("POST_ADDRESS2")) %>"/></td>
									<td align="right">邮编2：</td>
									<td><input readonly class="edline"  id="cc_post_code2" name="cc_buy_date" value="<%=Utility.trimNull(map.get("POST_CODE2")) %>"/></td>
									<td align="center" colspan="2">
                                        
                                    </td>
								</tr>
							</table>	
			</div>
		</div>
	</div>
</div>
<div>
	<input type="hidden" name="new_cc_id" value="<%=new_cc_id%>">
	<div class="sub-title">话务记事</div>
	<textarea id="callin-record" name="callin-record" class=" x-form-textarea x-form-field " style="width:99%;height:80px;overflow:auto"></textarea>
	<br />
	
</div>
<%out.flush(); %>
<div class="sub-title">来电号码历史话务
		<input type="button" value="保存话务记事" class="xpbutton6" style="float:right;" id="btnSave" name="btnSave" onclick="javascript:saveCallInInfo();">
</div>
<table class="the-table">
	<tr class="trh">
		<td align="center">拨打时间</td>
		<td align="center">话务方向</td>
		<td align="center">分机号码</td>
		<td align="center">外呼号码</td>
		<td align="center">记事内容</td>
	</tr>
	<%
	if (!"".equals(phone)){
		CCVO vo2=new CCVO();
		vo2.setPhoneNumStr(phone);
		vo2.setInputMan(input_operatorCode);
		IPageList pageList_his= callcenter.listCallRecords(vo2,1,50);
		Map map_his=null;
		if (pageList_his!=null){
			List list_his=pageList_his.getRsList();
			if (list_his!=null){
				String directionName="";
				Integer direction=new Integer(0);
				for (int i=0;i<list_his.size();i++){
					map_his=(Map)list_his.get(i);
					if (map_his==null) continue;
					direction=(Integer)map_his.get("Direction");
					if (direction==null) directionName="";
					else if (direction.intValue()==1) directionName="被叫接听";
					else if (direction.intValue()==2) directionName="主叫拨打";
	%>
	<tr><td align="center"><%=Utility.trimNull(map_his.get("CallTime"))%></td>
		<td align="center"><%=directionName %></td>
		<td align="center"><%=Utility.trimNull(map_his.get("Extension")) %></td>
		<td align="center"><%=Utility.trimNull(map_his.get("PhoneNumber")) %></td>
		<td align="center"><%=Utility.trimNull(map_his.get("Content")) %></td>
	</tr>
	<%
				}
			}
		}
	}
	%>
</table>
</form>
</body>
<%callcenter.remove();
%>