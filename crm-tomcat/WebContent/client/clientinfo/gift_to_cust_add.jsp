<%@ page language="java" pageEncoding="GBK" import="java.util.*,java.math.*,enfo.crm.web.*,enfo.crm.system.*,enfo.crm.vo.*, enfo.crm.customer.*,enfo.crm.tools.*,enfo.crm.dao.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>
<%

String giftName = Utility.trimNull(request.getParameter("giftName"));
Integer move_out_id = Utility.parseInt(Utility.trimNull(request.getParameter("move_out_id")),new Integer(0));
Integer giftNumbers = Utility.parseInt(Utility.trimNull(request.getParameter("giftNumbers")),new Integer(0));
Integer cust_id = Utility.parseInt(Utility.trimNull(request.getParameter("cust_id")),new Integer(0));
String provide =  Utility.trimNull(request.getParameter("provide"));
String post_address = Utility.trimNull(request.getParameter("post_address"));
String cust_no = Utility.trimNull(request.getParameter("cust_no"));
String cust_name = Utility.trimNull(request.getParameter("cust_name"));
Integer accountManager = Utility.parseInt(request.getParameter("accountManager"),input_operatorCode);
String cust_post_address = Utility.trimNull(request.getParameter("cust_post_address"));
Integer cust_sex = Utility.parseInt(request.getParameter("sex"),new Integer(0));
Integer provide_dade = Utility.parseInt(request.getParameter("provide_dade"),new Integer(0));
String detailInfo = Utility.trimNull(request.getParameter("detailInfo"));
Integer gift_id = new Integer(0);

boolean bSuccess=false;
CustomerLocal customer = EJBFactory.getCustomer();
CustomerVO vo = new CustomerVO();

List list=null;
List toCustlist=null;
Map giftmap=null;
if(move_out_id.intValue()!=0){
	vo.setMove_out_id(move_out_id);
	list = customer.listGifimoveoutAll(vo);
	if (list!=null && list.size() > 0) {
		giftmap = (Map)list.get(0);
		giftName = Utility.trimNull(giftmap.get("GIFT_NAME"));
		giftNumbers=Utility.parseInt(Utility.trimNull(giftmap.get("GIFT_NUMBER")),new Integer(0));
		detailInfo=Utility.trimNull(giftmap.get("DETAIL_INFO"));
		gift_id = Utility.parseInt((Integer)giftmap.get("GIFT_ID"),new Integer(0));
	}
	toCustlist=customer.listGifiToCust(vo);
}

vo.setCust_no(cust_no);
vo.setCust_name(cust_name);
vo.setService_man(accountManager);
vo.setPost_address(cust_post_address);
vo.setSex(cust_sex);
int first_flag = Utility.parseInt(request.getParameter("first_flag"),0);
IPageList pageList = first_flag!=0? customer.listProcAllExt(vo,Utility.parseInt(sPage, 1),Utility.parseInt(sPagesize, 8))
								  : new JdbcPageList();
Iterator it = first_flag!=0? pageList.getRsList().iterator(): new ArrayList().iterator(); 

sUrl += "&cust_no=" + cust_no 
				+"&cust_name ="+cust_name
				+"&accountManager="+accountManager;

if (request.getMethod().equals("POST")){

		String ss[]=request.getParameterValues("cust_id");
		String sn[]=request.getParameterValues("giftNumber");

		Integer s_cust_id=new Integer(0);
		Integer s_number=new Integer(0);
		if(ss!=null){
			for(int i=0;i<ss.length;i++){
					s_cust_id= Utility.parseInt(ss[i], null);
					s_number= Utility.parseInt(sn[i], null);
					if(s_cust_id!=null){
						vo.setCust_id(s_cust_id);
						vo.setGift_number(s_number);
						vo.setMove_out_id(move_out_id);
						vo.setProvide(provide);
						vo.setPost_address(post_address);
						vo.setInput_man(input_operatorCode);
						vo.setProvide_dade(provide_dade);
						customer.addGifitoCust(vo);
						bSuccess = true;
					}
				}
			}
		
}

 %>
<HTML>
<HEAD>
<TITLE></TITLE>
<META http-equiv=Content-Type content="text/html; charset=gbk">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js"></SCRIPT>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/interface/utilityService.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/engine.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/util.js'></script>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>
<script language=javascript>
<%if(bSuccess){%>
	sl_update_ok();
	location ="gift_info_list.jsp";
<%}%>
window.onload = function(){
		initQueryCondition();
};
/*保存*/
function SaveAction(){
		//if (! sl_check(document.theform.giftName, "礼物名称", 100, 1)) return false;
		//if(!sl_checkNum(document.theform.giftNumber, "礼物数量 ", 11, 0))	return false;
		
		var value=document.theform.giftNumbers.value;
		if(checkedCount(document.getElementsByName("cust_id")) == 0){
			sl_alert("请选择客户！");
			return false;
		}
		if(checkedCount(document.getElementsByName("cust_id"))>value){
			sl_alert("库存为"+value+"份，只能选择"+value+"个客户！");
			return false;
		}
		 syncDatePicker(document.theform.provide_dade_picker, document.theform.provide_dade);
		saveAll();
}
function toqueryCust(){
	disableAllBtn(true);
	 syncDatePicker(document.theform.provide_dade_picker, document.theform.provide_dade);
	location.search = 'gift_to_cust_add.jsp?page=1&pagesize=' + document.theform.pagesize.value 
				+'&cust_no=' + document.theform.cust_no.value 
				+'&cust_name='+document.theform.cust_name.value
				+'&accountManager='+document.theform.accountManager.value
				+'&move_out_id='+document.theform.move_out_id.value
				+'&giftNumbers='+document.theform.giftNumbers.value
				+'&provide='+document.theform.provide.value
				+'&post_address='+document.theform.post_address.value
				+'&provide_dade='+document.theform.provide_dade.value
				+'&first_flag=1';
		
}

function saveAll(){
	if (sl_confirm("<%=LocalUtilis.language("message.inputInfoSave",clientLocale)%> ")) {//保存输入信息
	disableAllBtn(true);
	document.theform.submit();
	}
}

/*取消*/
function CancelAction(){
	location ="gift_moveout_list.jsp?gift_id=<%=gift_id%>";
}

function StartQuery() {
	refreshPage();
}


function refreshPage() {	
	disableAllBtn(true);
	 syncDatePicker(document.theform.provide_dade_picker, document.theform.provide_dade);
//alert('he');
	location.search = 'page=1&pagesize=' + document.theform.pagesize.value 
				+'&cust_no=' + document.theform.cust_no.value 
				+'&cust_name='+document.theform.cust_name.value
				+'&accountManager='+document.theform.accountManager.value
				+'&move_out_id='+document.theform.move_out_id.value
				+'&giftNumbers='+document.theform.giftNumbers.value
				+'&provide='+document.theform.provide.value
				+'&post_address='+document.theform.post_address.value
				+'&provide_dade='+document.theform.provide_dade.value
				+'&first_flag=1'
				+'&cust_post_address='+document.theform.cust_post_address.value
				+'&sex='+document.theform.sex.value;
}

function newcust(){
	var url = '<%=request.getContextPath()%>/marketing/customerInfo.jsp?select_flag=2';
	v = showModalDialog(url,'','dialogWidth:720px;dialogHeight:720px;status:0;help:0;');		
	if (v != null){
		//alert(v[14]);
		location.search ='page=1&pagesize=' + document.theform.pagesize.value 
				+'&cust_no='+v[14]
				+'&move_out_id='+document.theform.move_out_id.value
				+'&giftNumbers='+document.theform.giftNumbers.value
				+'&cust_name=&accountManager=&provide=&post_address=&provide_dade=&first_flag=1';
	}
}

</script>
</HEAD>
<BODY class="BODY">
<form id="theform" name="theform" method="POST" action="gift_to_cust_add.jsp" >
<input type="hidden" name="move_out_id" value="<%=move_out_id %>">
<input type="hidden" name="first_flag" value="1">
<input type="hidden" name="giftNumbers" value="<%=giftNumbers %>">

		<div>
			<img src="<%=request.getContextPath()%>/images/member.gif"  border=0 width="32" height="28"><font color="#215dc6"><b><%=menu_info%></b></font>
			<hr noshade color="#808080" size="1">
		</div>
	<div>
		<table border="0" width="95%" cellspacing="2" cellpadding="2">
				<tr>
					<td  width="100px" align="right">礼物名称:</td>
			 		<td  width="*"><%=giftName %>
			 			<!-- <input name="giftName" disabled="disabled" onkeydown="javascript:nextKeyPress(this)" maxlength="6" size="20" value="<%=giftName %>" /> -->
			 		</td>
					<td  width="100px" align="right">数量:</td>
					<td ><%=giftNumbers %>
						<!-- <input name="giftNumbers" disabled="disabled" onkeydown="javascript:nextKeyPress(this)" maxlength="6" size="20" value="<%=giftNumbers %>" /> -->
			 		</td>
				</tr>
				<tr>
					<td  width="100px" align="right">详细信息:</td>
					<td colspan="3"><%=detailInfo %>
						<!-- <TEXTAREA name="detailInfo" disabled="disabled" cols="50" rows="5"><%=detailInfo %></TEXTAREA> -->
			 		</td>
				</tr>
		</table>
		<%if (toCustlist!=null && !toCustlist.isEmpty()){
			int toCount=0;
			Map toCustMap=null;%>
		<font color="#215dc6"><b>已保存的发放客户:</b></font>
		<table id="table3" border="0" cellspacing="1" cellpadding="2" class="tablelinecolor" width="100%" style="margin-top:5px">
			<%for (int k=0;k<toCustlist.size();k++){
				toCustMap = (Map)toCustlist.get(k);
				if (k%4==0){
					if (k!=0) out.println("</tr>");
			%>
				<tr class="tr<%=(toCount % 2)%>">
					<td><%=toCustMap.get("CUST_NAME") %></td>
			<%	}else{%>
					<td><%=toCustMap.get("CUST_NAME") %></td>
			<%	}
			} %>
			</tr>
			<tr><td class="tr0" colspan="4">合计数:<%=toCustlist.size() %></td></tr>
		</table>
		<%} %>
		<hr noshade color="#808080" size="1">
		<table border="0" width="95%" cellspacing="2" cellpadding="2">
				<tr>
					<td  width="100px" align="right">发放方式:</td>
			 		<td  width="*">
			 			<input name="provide" onkeydown="javascript:nextKeyPress(this)" maxlength="6" size="20" value="<%=provide %>" />
			 		</td>
					<td  width="100px" align="right">发放地址:</td>
					<td >
						<input name="post_address" onkeydown="javascript:nextKeyPress(this)" maxlength="20" size="50" value="<%=post_address %>" />
			 		</td>
				</tr>
				<tr>
					<td  width="100px" align="right">发放日期:</td>
					<td  width="*">
						<input type="text" name="provide_dade_picker" class=selecttext onkeydown="javascript:nextKeyPress(this)" value="<%=Format.formatDateLine(provide_dade)%>">
						<input type="button" value="▼" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.provide_dade_picker,theform.provide_dade_picker.value,this);">
						<input type="hidden" name="provide_dade" value=""/>
					</td>
				</tr>
		</table>
	</div>
<BR>
<div>
			<font color="#215dc6"><b>客户信息</b></font>
			<hr noshade color="#808080" size="1">
</div>
<div id="queryCondition" class="qcMain"	style="display: none; width: 550px">
	<table id="qcTitle" class="qcTitle" align="center" width="99%" cellspacing="0" cellpadding="2">
		<tr>
			<td><%=LocalUtilis.language("message.queryCondition",clientLocale)%> ：</td><!--查询条件-->
			<td align="right">
			<button class="qcClose" accessKey=c id="qcClose" name="qcClose"	onclick="javascript:cancelQuery();"></button>
			</td>
		</tr>
	</table>
	<table  width="99%" cellspacing="0" cellpadding="2">
		<tr>
			<td valign="bottom" align="right"><%=LocalUtilis.language("class.customerID",clientLocale)%> :</td><!--客户编号-->
			<td valign="bottom" align="left">
				<input type="text" name="cust_no" onkeydown="javascript:nextKeyPress(this)" value="<%=cust_no%>" size="25">
			</td>
			<td valign="bottom" align="right"><%=LocalUtilis.language("class.customerName",clientLocale)%> :</td><!--客户名称-->
			<td valign="bottom" align="left">
				<input type="text" maxlength="100" name="cust_name" onkeydown="javascript:nextKeyPress(this)" value="<%=cust_name%>" size="25">
			</td>
		</tr>
		<tr>
			<td valign="bottom" align="right"><%=LocalUtilis.language("class.accountManager",clientLocale)%> :</td><!--客户经理-->
			<td valign="bottom" align="left">
				<select name="accountManager" style="width: 147px">
					<%=Argument.getManager_Value(accountManager) %>
				</select>
			</td>
			<td valign="bottom" align="right">性别 :</td>
			<td valign="bottom" align="left">
				<SELECT onkeydown="javascript:nextKeyPress(this)" name="sex" style="WIDTH: 150px">
					<%=Argument.getSexOptions(cust_sex)%>
				</SELECT>
			</td>
		</tr>
		<tr>
			<td valign="bottom" align="right">客户地址 :</td>
			<td valign="bottom" align="left">
				<input type="text" maxlength="100" name="cust_post_address" onkeydown="javascript:nextKeyPress(this)" value="<%=cust_post_address%>" size="25">
			</td>
			
		</tr>
		<tr>
			<td align="center" colspan=4>
				<button class="xpbutton3" name="btnQuery" accessKey=o
					onclick="javascript:StartQuery();"><%=LocalUtilis.language("message.ok",clientLocale)%> (<u>O</u>)</button><!--确定-->
			</td>
		</tr>
	</table>
</div>

<div align="right"><button class="xpbutton5" accessKey=q id="queryButton"
						name="queryButton">合同客户查询(<u>Q</u>)</button><!-- 查询 -->
						&nbsp;&nbsp;&nbsp;
	<button class="xpbutton3" accessKey=n id="newcustbtn" name="newcustbtn" onclick="javascript:newcust();">新客户(<u>N</u>)</button>
</div>		

	<div>
		<table id="table3" border="0" cellspacing="1" cellpadding="2"
					class="tablelinecolor" width="100%" style="margin-top:5px">
				<tr class="trh">
							<td align="center">编号 </td>
							<td align="center">名称</td>
							<td align="center">手机</td>
							<td align="center">证件类型</td>
							<td align="center">证件号码</td>
							<td align="center">
								<input type="checkbox" name="btnCheckbox" class="selectAllBox" onclick="javascript:selectAllBox(document.theform.cust_id,this);">
								发放数量
							</td>
				</tr>
<%
int iCount = 0;
int iCurrent = 0;
while(it.hasNext()){
	Map map=(Map)it.next();	
 %>
				
						<tr class="tr<%=(iCurrent % 2)%>">
							<td align="center"><%=Utility.trimNull(map.get("CUST_NO"))%></td>
							<td align="center"><%=Utility.trimNull(map.get("CUST_NAME"))%> </td>
							<td align="center"><%=Utility.trimNull(map.get("MOBILE"))%></td>
							<td align="center"><%=Utility.trimNull(map.get("CARD_TYPE_NAME"))%></td>
							<td align="center"><%=Utility.trimNull(map.get("CARD_ID"))%></td>
							<td align="center">
									<table border="0" width="100%" cellspacing="0" cellpadding="0">
											<tr>
												<td width="10%">
														<input type="checkbox" name="cust_id" value="<%=map.get("CUST_ID")%>" checked="checked" class="flatcheckbox" >
												</td>
												<td width="90%" align="left">
													<INPUT name="giftNumber" value="1" type="text">
												</td>
											</tr>
									</table>
							</td>
					  </tr>
<%				iCurrent++;
				iCount++;
				}

				for(int i=0;i<(Utility.parseInt(sPagesize,8)-iCount);i++){ %>
					<tr class="tr<%=(iCurrent % 2)%>">
						<td align="center"></td>
						<td align="center"></td>
						<td align="center"></td>
						<td align="center"></td>
						<td align="center"></td>
						<td align="center"></td>
					</tr>
				<%} %>
				<tr class="trbottom">
						<!--合计--><!--项-->
                        <td class="tdh" align="left" colspan="6"><b>&nbsp;<%=LocalUtilis.language("message.total",clientLocale)%>&nbsp;<%=(pageList!=null)?pageList.getTotalSize():0%>&nbsp;<%=LocalUtilis.language("message.entries",clientLocale)%> &nbsp;</b></td>
				</tr>
		</table>
			<table border="0" width="100%">
						<tr valign="top">
							<td><%=pageList.getPageLink(sUrl,clientLocale)%></td>
						</tr>
			</table>
	</div>
		<br>
		
			<div align="right" style="margin-right:5px">
				<!-- 保存 -->
			    <button class="xpbutton3" accessKey=s id="btnSave" name="btnSave" onclick="javascript:SaveAction();"><%=LocalUtilis.language("message.save",clientLocale)%>(<u>S</u>)</button>
				&nbsp;&nbsp;&nbsp;&nbsp;
				<!-- 返回 -->
			    <button class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" onclick="javascript:CancelAction();">返回(<u>C</u>)</button>
			</div>
		
</form>
</BODY>
</HTML>
