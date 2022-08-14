<%@ page language="java" pageEncoding="GBK" import="java.util.*,java.math.*,enfo.crm.web.*,enfo.crm.system.*,enfo.crm.vo.*, enfo.crm.customer.*,enfo.crm.tools.*,enfo.crm.dao.*" %>
<%@ page import="enfo.crm.intrust.*" %>
<%@ include file="/includes/operator.inc" %>
<%
String iQuery = Utility.trimNull(request.getParameter("iQuery"));
CustomerInfoLocal customer = EJBFactory.getCustomerInfo();
Integer cust_id = Utility.parseInt(request.getParameter("cust_id"),null);
CustomerInfoVO vo = new CustomerInfoVO();
vo.setCust_id(cust_id);
vo.setInput_man(input_operatorCode);
List list = customer.load(vo);
Map map = new HashMap();
if (list.size()>0)
	map = (Map)list.get(0);
customer.remove();

String qs = Utility.getQueryString(request, new String[]{"page","pagesize","name","no",
				"product_id","product_name", "is_link", "card_id", "is_deal", "cust_type",
				"group_id", "q_money_source", "q_money_source_name", "q_country", "cust_source", 
				"invest_field", "mobile", "post_address", "true_flag", "serv_man"
				,"min_times","max_times","min_total_money","max_total_money","rg_start_date","rg_end_date"});
%>
<HTML>
<HEAD>
<TITLE></TITLE>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<LINK href="/includes/default.css" type=text/css rel=stylesheet>
<LINK href="/includes/jQuery/FormValidate/css/css.css" type=text/css rel=stylesheet>
<style>
a, a:link, a:visited, a:active {
    text-decoration: none;
}
a, a:link, a:visited, a:hover {
    color: #006091;
}
a, a:link, a:visited, a:active {
    text-decoration: none;
}
a, a:link, a:visited, a:hover {
    color: #006091;
}
a {
    cursor: pointer;
    outline: medium none;
    text-decoration: none;
}
</style>
<SCRIPT LANGUAGE="javascript" SRC="/includes/default.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/calendar.js"></SCRIPT>
<script type='text/javascript' src='/dwr/interface/CustomerInfo.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/interface/utilityService.js'></script>
<script type='text/javascript' src='/dwr/engine.js'></script>
<script type='text/javascript' src='/dwr/util.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/includes/jQuery/tree/script/jquery-1.4.2.min.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/includes/jQuery/FormValidate/js/FormValidate.js'></script>
<script language="javascript" src="<%=request.getContextPath()%>/includes/jQuery/js/ajaxfileupload.js"></script>
<script type="text/javascript">
var j$ = jQuery.noConflict();
j$(function(){    
    j$("form[name='theform']").FormValidate({        
        btnSubmit:"#btnSave",
        twiceFlag:1,
        checkFunc:{
        },
        executeFunc:{
            card_idExecute:function(){
                var obj = j$("[name='card_id']");
                var card_id = j$.trim(obj.val());
                var r18  = /^[1-9]\d{5}(\d{8})\d{3}[A-Za-z0-9]$/;
	            var r15  = /^[1-9]\d{5}(\d{6})\d{3}$/;
	            
                if(j$("[name='card_type']").val()==110801){
                    if(r18.test(card_id)||r15.test(card_id)){
                        var date = "";                        
                        if(card_id.length==18){
                            date = card_id.substring(6,14);    
                        }else if(card_id.length==15){
                            date = "19"+card_id.substring(6,12);      
                        }                        
                        j$("[name='birthday_picker']").val(date.substring(0,4)+"-"+date.substring(4,6)+"-"+date.substring(6,8)).css("background-color","#fff");
                        j$("[name='age']").val((new Date()).getFullYear()-date.substring(0,4)).css("background-color","#fff");
                    }        
                }
            },
            birthday_pickerExecute:function(){
                j$("[name='age']").val((new Date()).getFullYear()-j$("[name='birthday_picker']").val().substring(0,4)).css("background-color","#fff");        
            }  
        },
        init:{
            normal:eval('<%=Argument.getRequiredItemJson(menu_id,"10000",new Integer(1))%>'),
            twice:eval('<%=Argument.getRequiredItemJson(menu_id,"10000",new Integer(3))%>')
        },
        dataInit:function(){
			if(!sl_checkChoice(theform.cust_name, "客户名称")){ return false;}
			if(!sl_checkChoice(theform.card_type, "证件类型")){ return false;}
			if(!sl_checkChoice(theform.card_id, "证件号码")){ return false;}
			if(!sl_checkChoice(theform.cust_source, "客户来源")){ return false;}
			if(!sl_checkChoice(theform.grade_level, "风险等级")){ return false;}
			if(!sl_checkChoice(theform.touch_type, "联系方式")){ return false;}
			if (theform.touch_type.value==110901){//联系方式为电话
				if(theform.h_tel.value==''&& theform.mobile.value==''){
					alert('当选择联系方式为电话时，联系电话或者手机，至少填写其一');
					theform.h_tel.focus();
					return false;
				}
			}else if (theform.touch_type.value==110902){//联系方式为邮寄
				if(theform.post_address.value==''){
					alert('当选择联系方式为邮寄时，邮寄地址必需填写');
					theform.post_address.focus();
					return false;
				}
			}else if (theform.touch_type.value==110903){//联系方式为Email
				if(theform.e_mail.value==''){
					alert('当选择联系方式为Email时，Email必需填写');
					theform.e_mail.focus();
					return false;
				}
			}else if (theform.touch_type.value==110904){//联系方式为传真
				if(theform.fax.value==''){
					alert('当选择联系方式为传真时，传真必需填写');
					theform.fax.focus();
					return false;
				}
			}else if (theform.touch_type.value==110905||theform.touch_type.value==110906){//联系方式为短信,手机
				if(theform.mobile.value==''){
					alert('当选择联系方式为短信或者手机时，手机必需填写');
					theform.mobile.focus();
					return false;
				}
			}
            if(confirm('您确定要保存客户信息吗？')){
                disableAllBtn(true);    
            }else{
                return false;    
            }
            syncDatePicker(document.theform.vip_date_picker, document.theform.vip_date); 
	        syncDatePicker(document.theform.valid_picker, document.theform.valid);
		    syncDatePicker(document.theform.birthday_picker, document.theform.birthday);
		    if(document.theform.is_link.checked)
        		document.theform.is_link.value=1;
        	else
        		document.theform.is_link.value=2;
        	if(document.theform.complete_flag.checked == true)
        		document.theform.complete_flag.value = 1;
        	else
        		document.theform.complete_flag.value = 2;
        	if(document.theform.print_deploy_bill.checked == true)
        		document.theform.print_deploy_bill.value = 1;
        	else
        		document.theform.print_deploy_bill.value = 2;
        	if(document.theform.print_post_info.checked == true)
        		document.theform.print_post_info.value = 1;
        	else
        		document.theform.print_post_info.value = 2;	
        			
            var dataJson = {
                "cust_id":<%=cust_id%>,
                "cust_name":escape(j$("[name='cust_name']").val()),
                "post_address":escape(j$("[name='post_address']").val()),
                "post_code":escape(j$("[name='post_code']").val()),
                "card_type":escape(j$("[name='card_type']").val()),
                "card_id":escape(j$("[name='card_id']").val()),
                "age":escape(j$("[name='age']").val()),
                "sex":escape(j$("[name='sex']").val()),
                "o_tel":escape(j$("[name='o_tel']").val()),
                "h_tel":escape(j$("[name='h_tel']").val()),
                "mobile":escape(j$("[name='mobile']").val()),
                "bp":escape(j$("[name='bp']").val()),
                "fax":escape(j$("[name='fax']").val()),
                "e_mail":escape(j$("[name='e_mail']").val()),
                "cust_type":escape(j$("[name='cust_type']").val()),
                "touch_type":escape(j$("[name='touch_type']").val()),
                "cust_source":escape(j$("[name='cust_source']").val()),
                "summary":escape(j$("[name='summary']").val()),
                "cust_no":escape(j$("[name='cust_no']").val()),
                "birthday":escape(j$("[name='birthday']").val()),
                "post_address2":escape(j$("[name='post_address2']").val()),
                "post_code2":escape(j$("[name='post_code2']").val()),
                "print_deploy_bill":escape(j$("[name='print_deploy_bill']").val()),
                "print_post_info":escape(j$("[name='print_post_info']").val()),
                "is_link":escape(j$("[name='is_link']").val()),
                "service_man":escape(j$("[name='service_man']").val()),
                "vip_card_id":escape(j$("[name='vip_card_id']").val()),
                "vip_date":escape(j$("[name='vip_date']").val()),
                "hgtzr_bh":escape(j$("[name='hgtzr_bh']").val()),
                "voc_type":escape(j$("[name='zyhy_type']").val()),
                "card_valid_date":escape(j$("[name='valid']").val()),
                "country":escape(j$("[name='country']").val()),
                "money_source":escape(j$("[name='money_source']").val()),
                "link_type":escape(j$("[name='link_type']").val()),
                "link_gd_money":escape(sl_parseFloat(j$("[name='link_gd_money']").val())),
                "grade_level":escape(j$("[name='grade_level']").val()),
                "complete_flag":escape(j$("[name='complete_flag']").val()),
                "gov_prov_regional":escape(j$("[name='gov_prov_regional']").val()),
                "gov_regional":escape(j$("[name='gov_regional']").val()),
                "issued_org":escape(j$("[name='issued_org']").val()),
                "industry_post":escape(j$("[name='industry_post']").val()),
                "cust_industry":escape(j$("[name='cust_industry']").val()),
                "cust_corp_nature":escape(j$("[name='cust_corp_nature']").val()),
                "corp_branch":escape(j$("[name='corp_branch']").val()),
                "service_man_tel":escape(j$("[name='service_man_tel']").val()),
                "grade_score":escape(j$("[name='grade_score']").val()),
                "summary1":escape(j$("[name='summary1']").val()),
                "summary2":escape(j$("[name='summary2']").val())
            };                       
            j$.ajaxFileUpload({
                data:dataJson,
                url:'customer_info_jh_modi_action.jsp',
                fileElementId:'file_info',
                dataType: 'String',
                success: function (data, status){
    				if(isNaN(Number(data))){	    
    				    var rets = data.split("#");
    				    if(rets[0]!="1"){
    				        if(rets[1].indexOf("<BR>")!=-1)
    				            alert(rets[1].substring(0,rets[1].indexOf("<BR>"))); 
    				        else
    				            alert(rets[1]); 
    				    }else{
    				        alert("客户信息保存成功，但所属附件保存失败");
    				    }   
    			    }else{    	
    			        alert("客户信息保存成功"); 
    			        location = "client_info_list.jsp?<%=qs%>";
    				}
    			},
    			type:"POST"
    		});     
        }                 
    });  
});

function changeCustType(cust_type){
    if(cust_type==1){
        location = "customer_info_jh_add.jsp";    
    }else{
        location = "customer_info_ent_jh.jsp";
    }
}

function showLength(obj){    
    document.getElementById(obj.name+"_length").innerHTML = "("+obj.value.length+"位)";    
}

function showLinkInfo(obj){
    if(obj.checked){
        document.getElementById("link_info").style.display = "";
    }else{
        document.getElementById("link_info").style.display = "none";
    }    
}

function setGovRegional(val){
    if(val != "null" && val != "" && val.length > 0){	
		utilityService.getGovRegionalJson(9999, val,function(data){
		    DWRUtil.removeAllOptions ("gov_regional");
		    DWRUtil.addOptions("gov_regional",{"0":"请选择"});
		    var optionsJson = eval(data);
		    for(i=0;i<optionsJson.length;i++){
		        DWRUtil.addOptions("gov_regional",optionsJson[i]);        
		    }
		});
	}    
}

function confirmRemoveAttach(obj,serial_no)
{
	if(confirm('您确定要删除选定的附件吗？'))
	{		
		var updatehref = '/system/systemparam/attach_remove.jsp?serial_no='+serial_no;
    	if(showModalDialog(updatehref, '', 'dialogWidth:0px;dialogHeight:0px;dialogLeft:0px;dialogTop:0px;status:0;help:0') != null)
		{
		}
		if(obj!=null)
			obj.style.display="none";
	}
}
function calculateAge(){
    if (event.keyCode == 13 || event.keyCode == 9){
        if (document.theform.card_type.value != '110801')	return;
    
        var r18  = /^[1-9]\d{5}(\d{8})\d{3}[A-Za-z0-9]$/;
        var r15  = /^[1-9]\d{5}(\d{6})\d{3}$/;
        cardID = cTrim(document.theform.card_id.value,0);
        var current_date = new Date();
    
        if (r18.test(cardID)){
            var t = cardID.match(r18)[1];
            var card_date = getDateByMask(t, "yyyyMMdd");
    
           if (card_date != null ){
				
                if(card_date.getYear() >= 2000)
                    document.theform.age.value =( current_date.getYear() - card_date.getYear());
						
                else
                    document.theform.age.value =( current_date.getYear() - (1900+card_date.getYear()));
            }else
			{
                document.theform.age.value = "";
            }
            var flag = cardID.charAt(16);
            if(parseInt(flag) % 2 == 0)
                document.theform.sex.value = '2';
            else
                document.theform.sex.value = '1';
            document.theform.birthday_picker.value = cardID.substring(6,10)+"-"+cardID.substring(10,12)+"-"+cardID.substring(12,14);	
        }else if (r15.test(cardID)){

            var t = cardID.match(r15)[1];
            var card_date = getDateByMask(t,"yyMMdd");
		
            if (card_date != null ){
                if(card_date.getYear()>= 2000){
                    document.theform.age.value =( current_date.getYear() - (2000+card_date.getYear()));				
                    document.theform.birthday_picker.value = (2000+card_date.getYear())+"-"+cardID.substring(8,10)+"-"+cardID.substring(10,12);				
                }else{
                    document.theform.age.value =( current_date.getYear() - (1900+card_date.getYear()));
                    document.theform.birthday_picker.value = (1900+card_date.getYear())+"-"+cardID.substring(8,10)+"-"+cardID.substring(10,12);				
                }	
            }else{
                document.theform.age.value = "";
				
            }
            var flag = cardID.charAt(14);
            if(parseInt(flag) % 2 == 0)
                document.theform.sex.value = '2';
            else
                document.theform.sex.value = '1';		
        }else{
            sl_alert("身份证号码格式不合法！");
            event.keyCode = 0;
            document.theform.card_id.focus();
        }
  }

}
function calculateAge2(){
    if (document.theform.card_type.value != '110801')	return;

    var r18  = /^[1-9]\d{5}(\d{8})\d{3}[A-Za-z0-9]$/;
    var r15  = /^[1-9]\d{5}(\d{6})\d{3}$/;
    cardID = cTrim(document.theform.card_id.value,0);
    var current_date = new Date();

    if (r18.test(cardID)){
        var t = cardID.match(r18)[1];
        var card_date = getDateByMask(t, "yyyyMMdd");
        
        if (card_date != null ){
            if(card_date.getYear() >= 2000)
                document.theform.age.value =( current_date.getYear() - card_date.getYear());
            else
                document.theform.age.value =( current_date.getYear() - (1900+card_date.getYear()));
        }else{
            document.theform.age.value = "";
        }
        var flag = cardID.charAt(16);
        if(parseInt(flag) % 2 == 0)
            document.theform.sex.value = '2';
        else
            document.theform.sex.value = '1';
        document.theform.birthday_picker.value = cardID.substring(6,10)+"-"+cardID.substring(10,12)+"-"+cardID.substring(12,14);	
    }else if (r15.test(cardID)){
        var t = cardID.match(r15)[1];
        var card_date = getDateByMask(t,"yyMMdd");
        if (card_date != null ){
            if(card_date.getYear()>= 2000){
                document.theform.age.value =( current_date.getYear() - (2000+card_date.getYear()));				
                document.theform.birthday_picker.value = (2000+card_date.getYear())+"-"+cardID.substring(8,10)+"-"+cardID.substring(10,12);				
            }else{
                document.theform.age.value =( current_date.getYear() - (1900+card_date.getYear()));
                document.theform.birthday_picker.value = (1900+card_date.getYear())+"-"+cardID.substring(8,10)+"-"+cardID.substring(10,12);				
            }	
        }else{
            document.theform.age.value = "";
        }
        var flag = cardID.charAt(14);
        if(parseInt(flag) % 2 == 0)
            document.theform.sex.value = '2';
        else
            document.theform.sex.value = '1';		
    }
	document.getElementById('did1').style.display='none'
}
function getDateByMask(s, m) {
	if (s.length!=m.length)	return false;

	var t = m.replace(/d/ig,"\\d").replace(/m/ig,"\\d").replace(/y/ig,"\\d")
	var r = new RegExp("^"+t+"$");
	if (! r.test(s)) return null;
	try
	{
		m=m.replace(/Y/g,"y").replace(/D/g,"d");
		if (m.indexOf("yyyy")>-1)
			return new Date(s.substr(m.indexOf("yyyy"),4),s.substr(m.indexOf("MM"),2)-1,s.substr(m.indexOf("dd"),2));
		else
			return new Date(s.substr(m.indexOf("yy"),2),s.substr(m.indexOf("MM"),2)-1,s.substr(m.indexOf("dd"),2));
	}catch (e)
	{
		return null;
	}
}
</script>
</HEAD>
<BODY  class="BODY">
<%@ include file="/includes/waiting.inc"%>
<form name="theform" id="theform" method="post" action="customer_info_jh_add.jsp">
<table cellSpacing=0 cellPadding=0 width="100%" border=0>
	<tr>
		<td vAlign=top align=center>
    		<table border="0" width="100%" cellspacing="0" cellpadding="1">
    			<tr>
    				<td><img border="0" src="/images/member.gif"><b>客户信息 - 个人</b></td>
    			</tr>
    			<tr>
    				<td align="right">
    					<%if(cust_id != null){%>
    					<button type="button"  class="xpbutton5"  name="btnNew" title="附加信息" onclick="javascript:disableAllBtn(true);location='/client/appraise/amcust_info.jsp?from=jh_cust&flag=5&cust_id=<%=cust_id%>&iQuery2=<%=iQuery%>&tel=<%=Utility.trimNull(map.get("MOBILE"))%>';">反洗钱附加信息</button>
    					<%}%>
    				</td>
    			</tr>
    			<tr>
    				<td><hr noshade color="#808080" size="1"></td>
    			</tr>
    		</table>
		    <table border="0" cellpadding="3" cellspacing="1" width="100%" align="center" style="background-color: whitesmoke;">
    			<tr style="text-align: left; COLOR: #0076C8; BACKGROUND-COLOR: #F4FAFF; font-weight: bold; height: 20px;">
    		    	<td colspan="6">基本信息</td>
    		   	</tr>
    		   	<tr bgcolor='#FFFFFF'>
    		   	    <td align="right">客户类别:</td>
    				<td >				
        				<input type="text" name="cust_type_name" value="个人" class="edline"> 
        				<input type="hidden" name="cust_type" value="<%=Utility.trimNull(map.get("CUST_TYPE"))%>">   
    			    </td>
    			    <td align="right">客户编号:</td>
    			    <td>
    			        <input name="cust_no" value="<%=Utility.trimNull(map.get("CUST_NO"))%>" class="edline">
    			    </td>			        
    		   	</tr>
    		   	<tr bgcolor='#FFFFFF'>
    		   	    <td align="right"><font color="red">*</font>客户名称:</td>
    				<td>
    				    <input name="cust_name" onkeydown="javascript:nextKeyPress(this)" maxlength="100" size="40" value="<%=Utility.trimNull(map.get("CUST_NAME"))%>">
    				</td>
    		   	</tr>
    		   	<tr bgcolor='#FFFFFF'>
    				<td align="right"><font color="red">*</font>证件类型:</td>
    				<td>
    					<select size="1" onkeydown="javascript:nextKeyPress(this)"  name="card_type" style="WIDTH: 120px">
    						<%=Argument.getCardTypeOptions2(Utility.trimNull(map.get("CARD_TYPE")))%>
    					</select>
    				</td>
    				<td align="right"><font color="red">*</font>证件号码:</td>
    				<td>
    				    <input name="card_id" onkeydown="javascript:calculateAge();nextKeyPress(this)" onblur="javascript:calculateAge2();" onkeyup="javascript:showLength(this)" maxlength="40" value="<%=Utility.trimNull(map.get("CARD_ID"))%>" size="25">
    					<span id="card_id_length" class="span"></span>
						<DIV id="did1" style="display: none"><font color="red">*客户如果是个人，输入身份证号完成后回车自动显示客户对应的性别，生日，年龄</font></DIV>
    				</td>
    			</tr>	
    			<tr bgcolor='#FFFFFF'>
    			    <td align="right">出生日期:</td>
    				<td>
    					<input TYPE="text" value="<%=Format.formatDateLine(Utility.parseInt((Integer)map.get("BIRTHDAY"),null))%>" NAME="birthday_picker" class=selecttext onkeydown="javascript:nextKeyPress(this)">
    					<input TYPE="button" value="▼" class=selectbtn onkeydown="javascript:nextKeyPress(this)" onclick="javascript:CalendarWebControl.show(theform.birthday_picker,theform.birthday_picker.value,this);">
    					<input TYPE="hidden" NAME="birthday"  value="">
    				</td>
    				<td align="right">年龄:</td>
    				<td>
    				    <input name="age" onkeydown="javascript:nextKeyPress(this)" size="20" maxlength="4" value="<%=Utility.trimNull(map.get("AGE"))%>">
    				</td>
    			</tr>
    			<tr bgcolor='#FFFFFF'>
    				
    				<td align="right">性别:</td>
    				<td>
    				    <select size="1" onkeydown="javascript:nextKeyPress(this)" name="sex" style="WIDTH: 120px">
    					    <%=Argument.getSexOptions(Utility.parseInt((Integer)map.get("SEX"),null))%>
    				    </select>
    				</td>
    			</tr>
    			<tr bgcolor='#FFFFFF'>
    			    <td align="right">联系电话:</td>
    				<td>
    				    <input name="h_tel" onkeydown="javascript:nextKeyPress(this)" maxlength="20" size="20" value="<%=Utility.trimNull(map.get("H_TEL"))%>">
    				</td>
    				<td align="right">手机:</td>
    				<td>
    				    <input name="mobile" onkeydown="javascript:nextKeyPress(this)" maxlength="20" size="20" value="<%=Utility.trimNull(map.get("MOBILE"))%>">
    				</td>
    			</tr>
    			<tr bgcolor='#FFFFFF'>
    				<td align="right">邮寄地址:</td>
    				<td>
    				    <input name="post_address" size="40" onkeydown="javascript:nextKeyPress(this)" maxlength="60" value="<%=Utility.trimNull(map.get("POST_ADDRESS"))%>">
    				</td>
    				<td align="right">邮政编码:</td>
    				<td>
    				    <input name="post_code" onkeydown="javascript:nextKeyPress(this)" size="20" maxlength="6" value="<%=Utility.trimNull(map.get("POST_CODE"))%>">
    				</td>
    				
    			</tr>
    		   	<tr style="text-align: left; COLOR: #0076C8; BACKGROUND-COLOR: #F4FAFF; font-weight: bold; height: 20px;">
    		    	<td colspan="6">附加信息</td>
    		   	</tr>
    		   	<tr bgcolor='#FFFFFF'>
    		   	    <td align="right"><font color="red">*</font>客户来源:</td>
    				<td>
    				    <select size="1" onkeydown="javascript:nextKeyPress(this)" name="cust_source" style="WIDTH: 120px">
    					    <%=Argument.getCustomerSourceOptions(Utility.trimNull(map.get("CUST_SOURCE")))%>
    				    </select>
    				</td>
    				<td align="right">客户经理:</td> 
    				<td>
    				    <select size="1" name="service_man" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 120px">
    					    <%=Argument.getManager_Value(Utility.parseInt((Integer)map.get("SERVICE_MAN"),new Integer(0)))%>
    					</select>
    				</td>	
    		   	</tr>
    		   	<tr bgcolor='#FFFFFF'>
    		   	    <td align="right">网点所在地:</td>
    		   	    <td><input onkeydown="javascript:nextKeyPress(this)" name="corp_branch" value="<%=Utility.trimNull(map.get("CORP_BRANCH"))%>"></td>
    		   	    <td align="right">客户经理手机:</td>
    		   	    <td><input onkeydown="javascript:nextKeyPress(this)" name="service_man_tel" value="<%=Utility.trimNull(map.get("SERVICE_MAN_TEL"))%>"></td>
    		   	</tr>
    		   	<tr bgcolor='#FFFFFF'>
    		   	    <td align="right">客户国籍:</td>
    				<td >
    					<select size="1" onkeydown="javascript:nextKeyPress(this)" name="country" style="WIDTH:120px">
    						<%=Argument.getCountry(Utility.trimNull(map.get("COUNTRY")))%>
    					</select>
    				</td>	
    		   	</tr>
    		   	<tr bgcolor='#FFFFFF'>
    		   	    <td align="right">证件有效期限:</td>
    				<td >
    					<input TYPE="text" value="<%=Format.formatDateLine(Utility.parseInt((Integer)map.get("CARD_VALID_DATE"),null))%>" NAME="valid_picker" class=selecttext onkeydown="javascript:nextKeyPress(this)">
    					<input TYPE="button" value="▼" class=selectbtn onkeydown="javascript:nextKeyPress(this)" onclick="javascript:CalendarWebControl.show(theform.valid_picker,theform.valid_picker.value,this);" tabindex="13">
    					<input TYPE="hidden" NAME="valid"   value="">
    				</td>
    				<td align="right">发证机关:</td>	
    				<td><input name="issued_org" onkeydown="javascript:nextKeyPress(this)" size="20" value="<%=Utility.trimNull(map.get("ISSUED_ORG"))%>"></td>
    		   	</tr>
    		   	<tr bgcolor='#FFFFFF'>
    		   	    <td align="right">行业:</td>
    				<td >
    					<select size="1" onkeydown="javascript:nextKeyPress(this)" name="cust_industry" style="WIDTH:120px">
    						<%=Argument.getDictParamOptions(2144,Utility.trimNull(map.get("CUST_INDUSTRY")))%>
    					</select>
    				</td>
    				<td align="right">工作单位性质:</td>
    				<td >
    					<select size="1" onkeydown="javascript:nextKeyPress(this)" name="cust_corp_nature" style="WIDTH:120px">
    						<%=Argument.getDictParamOptions_intrust(2145,Utility.trimNull(map.get("CUST_CORP_NATURE")))%>
    					</select>
    				</td>
    		   	</tr>
    		   	<tr bgcolor='#FFFFFF'>
    		   	    <td align="right">岗位:</td>
    				<td >
    					<input onkeydown="javascript:nextKeyPress(this)" name="industry_post" value="<%=Utility.trimNull(map.get("INDUSTRY_POST"))%>">
    				</td>
    				<td align="right">信托资金来源:</td>
    				<td><select size="1" onkeydown="javascript:nextKeyPress(this)" name="money_source" style="WIDTH: 120px">
    					<%=Argument.getJr_Money_Source(Utility.trimNull(map.get("MONEY_SOURCE")))%>
    				</select></td>
    		   	</tr>
    		   	<tr bgcolor='#FFFFFF'>
    		   	    <td align="right">风险测评得分:</td>
    		   	    <td><input name="grade_score" value="<%=Utility.trimNull(map.get("GRADE_SCORE"))%>" onkeydown="javascript:nextKeyPress(this)"></td>
    		   	    <td align="right"><font color="red">*</font>风险等级:</td>   
    				<td>
    				    <select name="grade_level" style="WIDTH: 120px" onkeydown="javascript:nextKeyPress(this)">
    				        <%=Argument.getDictParamOptions(3203, Utility.trimNull(map.get("GRADE_LEVEL"))) %>
    				    </select>
    			    </td>	
    		   	</tr>
    		   	<tr bgcolor='#FFFFFF'>
    		   	    <td align="right">备注1:</td>
    				<td>
    				    <input name="summary1" onkeydown="javascript:nextKeyPress(this)" maxlength="200" value="<%=Utility.trimNull(map.get("SUMMARY1"))%>">
    				</td>
    				<td align="right">备注2:</td>
    				<td>
    				    <input name="summary2" onkeydown="javascript:nextKeyPress(this)" maxlength="200" value="<%=Utility.trimNull(map.get("SUMMARY2"))%>">
    				</td>
    		   	</tr>
    		   	<tr style="text-align: left; COLOR: #0076C8; BACKGROUND-COLOR: #F4FAFF; font-weight: bold; height: 20px;">
    		    	<td colspan="6">其他信息</td>
    		   	</tr>
    		   	<tr bgcolor='#FFFFFF'>
    		   	    <td align="right">合格投资人编号:</td>
    				<td>
    				    <input name="hgtzr_bh" onkeydown="javascript:nextKeyPress(this)" size="20" value="<%=Utility.trimNull(map.get("HGTZR_BH"))%>">
    				</td>
    		   	</tr>
    		   	<tr bgcolor='#FFFFFF'>
    		   	    <td align="right">VIP卡编号:</td>
    				<td>
    				    <input name="vip_card_id" onkeydown="javascript:nextKeyPress(this)" maxlength="100" size="40" value="<%=Utility.trimNull(map.get("VIP_CARD_ID"))%>">
    				</td>
    				<td align="right">VIP发卡日期:</td>
    				<td>
    					<input TYPE="text" value="<%=Format.formatDateLine(Utility.parseInt((Integer)map.get("VIP_DATE"),null))%>" NAME="vip_date_picker" class=selecttext value="" onkeydown="javascript:nextKeyPress(this)">
    					<input TYPE="button" value="▼" onkeydown="javascript:nextKeyPress(this)" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.vip_date_picker,theform.vip_date_picker.value,this);" tabindex="13">
    					<input TYPE="hidden" NAME="vip_date"   value="">
    				</td>
    		   	</tr>
    		   	<tr bgcolor='#FFFFFF'>
    		   	    <td align="right">职业:</td>
    				<td colspan="3">			
    					<select size="1" onkeydown="javascript:nextKeyPress(this)"  name="zyhy_type" style="WIDTH: 238px" >
    						<%=Argument.getGrzyOptions2(Utility.trimNull(map.get("VOC_TYPE")))%>
    					</select>			
    				</td>
    		   	</tr>
    		   	<tr bgcolor='#FFFFFF'>
    		   	    <td align="right">公司电话:</td>
    				<td>
    				    <input name="o_tel" onkeydown="javascript:nextKeyPress(this)" maxlength="20" size="20" value="<%=Utility.trimNull(map.get("O_TEL"))%>">
    				</td>
    				<td align="right">手机2:</td>
    				<td><input name="bp" onkeydown="javascript:nextKeyPress(this)" size="20" maxlength="20" value="<%=Utility.trimNull(map.get("BP"))%>"></td>
    		   	</tr>
    		   	<tr bgcolor='#FFFFFF'>
    		   	    <td align="right">传真:</td>
    				<td><input name="fax" onkeydown="javascript:nextKeyPress(this)" size="20" maxlength="20" value="<%=Utility.trimNull(map.get("FAX"))%>"></td>
    				<td align="right">Email:</td>
    				<td><input name="e_mail" onkeydown="javascript:nextKeyPress(this)" maxlength="30" size="25" value="<%=Utility.trimNull(map.get("E_MAIL"))%>"></td>
    		   	</tr>
    		   	<tr bgcolor='#FFFFFF'>
    		   	    <td align="right"><font color="red">*</font>联系方式:</td>
    				<td>
    				    <select size="1" name="touch_type" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 120px">
    					    <%=Argument.getTouchTypeOptions(Utility.trimNull(map.get("TOUCH_TYPE")))%>
    				    </select>
    				</td>
    				<td align="right">备注信息:</td>
    				<td>
    				    <input name="summary" onkeydown="javascript:nextKeyPress(this)" maxlength="200" value="<%=Utility.trimNull(map.get("SUMMARY"))%>">
    				</td>
    		   	</tr>
    		   	<tr bgcolor='#FFFFFF'>
    				<td align="right">备用地址:</td>
    				<td>
    				    <input name="post_address2" size="40" onkeydown="javascript:nextKeyPress(this)" maxlength="60" value="<%=Utility.trimNull(map.get("POST_ADDRESS2"))%>">
    				</td>
    				<td align="right">邮政编码:</td>
    				<td>
    				    <input name="post_code2" onkeydown="javascript:nextKeyPress(this)" size="20" maxlength="6" value="<%=Utility.trimNull(map.get("POST_CODE2"))%>">
    				</td>
    			</tr>
    		   	<tr bgcolor='#FFFFFF'>
    				<td align="right">打印客户对帐单:</td>
    				<td >
    				    <input class="flatcheckbox" type="checkbox" name="print_deploy_bill" <%="1".equals(Utility.trimNull(map.get("PRINT_DEPLOY_BILL")))?"checked":""%>>
    				</td>
    				<td align="right">关联标志：</td>
    				<td>
    				    <input type="checkbox" name="is_link" value="" class="flatcheckbox" onclick="javascript:showLinkInfo(this);" <%="1".equals(Utility.trimNull(map.get("IS_LINK")))?"checked":""%>>
    				</td>
    			</tr>
    			<tr bgcolor='#FFFFFF' id="link_info" style="display:<%="1".equals(Utility.trimNull(map.get("IS_LINK")))?"":"none"%>">
    			    <td  align="right">关联类型:</td>
    				<td >
    			    	<select  onkeydown="javascript:nextKeyPress(this)" size="1" name="link_type" style="width: 400px">
    						<%=Argument.getLinkTypeOptions(Utility.parseInt((Integer)map.get("LINK_TYPE"),new Integer(0)))%>
    					</select>
    			    </td>
    			    <td align="right">投资信托公司金额:</td>
    				<td>
    					<input onkeydown="javascript:nextKeyPress(this)" value="<%=Format.formatMoney(Utility.parseBigDecimal((BigDecimal)map.get("LINK_GD_MONEY"),new BigDecimal("0.00")))%>" onkeyup="javascript:showCnMoney(this.value,link_gd_money_cn)" name="link_gd_money"   size="20" maxlength="60" >
    					<br><span id="link_gd_money_cn" class="span"></span>
    				</td>
    			</tr>
    		   	<tr bgcolor='#FFFFFF'>
    				<td  align="right">打印披露信息:</td>
    				<td colspan=3>
    				    <input class="flatcheckbox" type="checkbox" name="print_post_info" <%="1".equals(Utility.trimNull(map.get("PRINT_POST_INFO")))?"checked":""%>>
    				</td>														
    			</tr>
    		   	<tr bgcolor='#FFFFFF'>
    				<td align="right">省级行政区域 :</td>
         			<td>
    					<select size="1"  name="gov_prov_regional" style="width: 120px" onkeydown="javascript:nextKeyPress(this)" onchange="javascript:setGovRegional(this.value);">
    						<%=Argument.getCustodianNameLis(new Integer(9999), "", new Integer(0),Utility.trimNull(map.get("GOV_PROV_REGIONAL")))%>
    					</select>    					
         			</td>
    				<td align="right">行政区域 :</td>
         			<td>
						<select size='1' id="gov_regional"  name='gov_regional' style='width: 120px' onkeydown='javascript:nextKeyPress(this)'>
							<%=Argument.getCustodianNameLis(new Integer(9999),Utility.trimNull(map.get("GOV_PROV_REGIONAL")), new Integer(0),Utility.trimNull(map.get("GOV_REGIONAL")))%>
						</select>
         			</td>
    			</tr>
    			<%
					AttachmentLocal attachment = EJBFactory.getAttachment();
					AttachmentVO atta_vo = new AttachmentVO();
        			atta_vo.setDf_talbe_id(new Integer(4));
              	    atta_vo.setDf_serial_no(cust_id);
              	    List attaList = attachment.load(atta_vo);
    			%>
    			<tr bgcolor='#FFFFFF'>
    			    <td align="right"><%if(attaList.size() > 0){%>附件:<%}%></td>    
    			    <td  colspan="3">
    			<%
                  for (int i=0; i<attaList.size(); i++) {
						Map attaMap = (Map)attaList.get(i);
                %>
            	        <div style="border:1px;" id="divattach<%=attaMap.get("ATTACHMENTID")%>" style="display:">
            	            <a title="查看附件" href="/system/systemparam/downloadattach.jsp?file_name=<%=Utility.replaceAll((String)attaMap.get("SAVE_NAME"),"\\","/")%>&name=<%=attaMap.get("ORIGIN_NAME")%>" ><%=attaMap.get("ORIGIN_NAME")%></a>
            	            <a href="#" onclick="javascript:confirmRemoveAttach(divattach<%=attaMap.get("ATTACHMENTID")%>,'<%=attaMap.get("ATTACHMENTID")%>$<%=Utility.replaceAll((String)attaMap.get("SAVE_NAME"),"\\","/")%>');">删除</a>
            	        </div>
			    <%
			        }	
					attachment.remove();
			    %>
    			    </td>
    			</tr>
    		   	<tr id="reader2" style="display:" bgcolor='#FFFFFF'>
    	          	<td class="paramTitle"align="right">添加附件:</td>
    	            <td colspan="2">
    	            	<input type="file" id="file_info" name="file_info" size="60">&nbsp;<!--<img title="选择附加文件" src=/images/minihelp.gif">-->
    	            </td>
    				<td><input type="checkbox" name="complete_flag" class="flatcheckbox" <%="1".equals(Utility.trimNull(map.get("COMPLETE_FLAG")))?"checked":""%>><font color="red">资料完整</font></td>
    	        </tr>
    		</table>
        </td>
    </tr>
    <tr>
        <td colspan="4">
            <table border="0" width="100%">
                <tr>
                    <td align="right">
						<button type="button"  class="xpbutton3" accessKey=s id="btnSave" name="btnSave">保存(<u>S</u>)</button>
						&nbsp;&nbsp;
						<button type="button"  class="xpbutton3" accessKey=b id="btnReturn" name="btnReturn" onclick="javascript:disableAllBtn(true);history.back();">返回(<u>B</u>)</button>
						&nbsp;&nbsp;
				    </td>
				</tr>
            </table>
        </td>
    </tr>
</table>
</form>
<%@ include file="/includes/foot.inc"%>
</BODY>
</HTML>
