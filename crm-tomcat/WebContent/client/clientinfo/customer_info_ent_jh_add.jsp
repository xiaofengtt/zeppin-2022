<%@ page language="java" pageEncoding="GBK" import="java.util.*,java.math.*,enfo.crm.web.*,enfo.crm.system.*,enfo.crm.vo.*, enfo.crm.customer.*,enfo.crm.tools.*,enfo.crm.dao.*" %>
<%@ include file="/includes/operator.inc" %>
<%
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
            if(!sl_checkChoice(theform.cust_name, "客户名称"))	
				return false;
			if(!sl_checkChoice(theform.card_type, "证件类型"))	
				return false;
			if(!sl_checkChoice(theform.card_id, "证件号码"))
					return false;
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
            CustomerInfo.findSameCustInfo(document.theform.cust_name.value,1,0,function(findInfo){
                if(findInfo!=""){
                    if(confirm("系统中已存在名为["+findInfo+"]的客户，你可以单击确认按钮继续保存")){
                        disableAllBtn(false);
                        return false;    
                    }      
                }
                syncDatePicker(document.theform.vip_date_picker, document.theform.vip_date); 
    	        syncDatePicker(document.theform.valid_picker, document.theform.valid);
    		    syncDatePicker(document.theform.fc_valid_picker, document.theform.fc_valid);
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
                    "card_valid_date":escape(j$("[name='valid']").val()),
                    "country":escape(j$("[name='country']").val()),
                    "money_source":escape(j$("[name='money_source']").val()),
                    "link_type":escape(j$("[name='link_type']").val()),
                    "link_gd_money":escape(sl_parseFloat(j$("[name='link_gd_money']").val())),
                    "grade_level":escape(j$("[name='grade_level']").val()),
                    "complete_flag":escape(j$("[name='complete_flag']").val()),
                    "gov_prov_regional":escape(j$("[name='gov_prov_regional']").val()),
                    "gov_regional":escape(j$("[name='gov_regional']").val()),
                    "corp_branch":escape(j$("[name='corp_branch']").val()),
                    "service_man_tel":escape(j$("[name='service_man_tel']").val()),
                    "summary1":escape(j$("[name='summary1']").val()),
                    "summary2":escape(j$("[name='summary2']").val()),
                    "contact_man":escape(j$("[name='contact_man']").val()),
                    "fact_controller":escape(j$("[name='fact_controller']").val()),
                    "legal_man":escape(j$("[name='legal_man']").val()),
                    "legal_address":escape(j$("[name='legal_address']").val()),
                    "legal_tel":escape(j$("[name='legal_tel']").val()),
                    "legal_mobile":escape(j$("[name='legal_mobile']").val()),
                    "fc_card_type":escape(j$("[name='fc_card_type']").val()),
                    "fc_card_id":escape(j$("[name='fc_card_id']").val()),
                    "fc_card_valid_date":escape(j$("[name='fc_valid']").val()),
                    "jg_cust_type":escape(j$("[name='jg_cust_type']").val()),
                    "voc_type":escape(j$("[name='zyhy_type']").val()),
                    "legal_post_code":escape(j$("[name='legal_post_code']").val()),
                    "trans_name":escape(j$("[name='trans_name']").val()),
                    "trans_tel":escape(j$("[name='trans_tel']").val()), 
                    "trans_mobile":escape(j$("[name='trans_mobile']").val()), 
                    "trans_address":escape(j$("[name='trans_address']").val()), 
                    "trans_post_code":escape(j$("[name='trans_post_code']").val()), 
                    "register_address":escape(j$("[name='register_address']").val()), 
                    "register_post_code":escape(j$("[name='register_post_code']").val())
    
                };                       
                j$.ajaxFileUpload({
                    data:dataJson,
                    url:'customer_info_jh_add_action.jsp',
                    fileElementId:'file_info',
                    dataType: 'String',
                    success: function (data, status){
        				if(isNaN(Number(data))){	    
        				    var rets = data.split("#");
        				    if(rets[0]!="1"){
        				        alert(rets[1].substring(0,rets[1].indexOf("<BR>")));    
        				        disableAllBtn(false);
        				    }else{
        				        alert("客户信息保存成功，但所属附件保存失败");
        				        disableAllBtn(false);
        				    }   
        			    }else{    	
        			        alert("客户信息保存成功"); 
        			        location = "client_info_list.jsp?<%=qs%>";
        				}
        			},
        			type:"POST"
        		});
            });     
        }                 
    });  
});

function changeCustType(cust_type){
    if(cust_type==1){
        location = "customer_info_jh_add.jsp?<%=qs%>";    
    }else{
        location = "customer_info_ent_jh.jsp?<%=qs%>";
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

function back() {
	location.href = "client_info_list.jsp?<%=qs%>";
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
    				<td><img border="0" src="/images/member.gif"><b>客户信息 - 机构</b></td>
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
        				<select size="1" name="cust_type" onkeydown="javascript:nextKeyPress(this)" onchange="javascript:changeCustType(this.value);" style="WIDTH: 120px">
        					<%=Argument.getCustTypeOptions2(new Integer(2))%>
        				</select>    
    			    </td>
    			    <td align="right">客户编号:</td>
    			    <td>
    			        <input name="cust_no" value="" class="edline">
    			    </td>			        
    		   	</tr>
    		   	<tr bgcolor='#FFFFFF'>
    		   	    <td align="right"><font color="red">*</font>客户名称:</td>
    				<td>
    				    <input name="cust_name" onkeydown="javascript:nextKeyPress(this)" maxlength="100" size="40" value="">
    				</td>
    		   	</tr>
    		   	<tr bgcolor='#FFFFFF'>
    				<td align="right"><font color="red">*</font>证件类型:</td>
    				<td>
    					<select size="1" onkeydown="javascript:nextKeyPress(this)"  name="card_type" style="WIDTH: 120px">
    						<%=Argument.getCardTypeJgOptions2("")%>
    					</select>
    				</td>
    				<td align="right"><font color="red">*</font>证件号码:</td>
    				<td>
    				    <input name="card_id" onkeydown="javascript:nextKeyPress(this)" title="客户如果是个人，输入完成后回车自动显示客户对应的性别，生日，年龄" onkeyup="javascript:showLength(this)" maxlength="40" value="" size="25">
    					<span id="card_id_length" class="span"></span>
    				</td>
    			</tr>	
    			<tr bgcolor='#FFFFFF'>
    			    <td align="right">联系电话:</td>
    				<td>
    				    <input name="h_tel" onkeydown="javascript:nextKeyPress(this)" maxlength="20" size="20" value="">
    				</td>
    			</tr>
    			<tr bgcolor='#FFFFFF'>
    				<td align="right">邮寄地址:</td>
    				<td>
    				    <input name="post_address" size="40" onkeydown="javascript:nextKeyPress(this)" maxlength="60" value="">
    				</td>
    				<td align="right">邮政编码:</td>
    				<td>
    				    <input name="post_code" onkeydown="javascript:nextKeyPress(this)" size="20" maxlength="6" value="">
    				</td>
    				
    			</tr>
    			<tr bgcolor='#FFFFFF'>
    			    <td align="right">联系人:</td>
    				<td>
    				    <input name="contact_man" onkeydown="javascript:nextKeyPress(this)" maxlength="20" size="20" value="">
    				</td>
    				<td align="right">电话:</td>
    				<td>
    				    <input name="mobile" onkeydown="javascript:nextKeyPress(this)" maxlength="20" size="20" value="">
    				</td>
    			</tr>
    			
    		   	<tr style="text-align: left; COLOR: #0076C8; BACKGROUND-COLOR: #F4FAFF; font-weight: bold; height: 20px;">
    		    	<td colspan="6">附加信息</td>
    		   	</tr>
    		   	<tr bgcolor='#FFFFFF'>
    		   	    <td align="right">客户来源:</td>
    				<td>
    				    <select size="1" onkeydown="javascript:nextKeyPress(this)" name="cust_source" style="WIDTH: 120px">
    					    <%=Argument.getCustomerSourceOptions("")%>
    				    </select>
    				</td>
    				<td align="right">客户经理:</td> 
    				<td>
    				    <select size="1" name="service_man" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 120px">
    					    <%=Argument.getManager_Value(input_operatorCode)%>
    					</select>
    				</td>	
    		   	</tr>
    		   	<tr bgcolor='#FFFFFF'>
    		   	    <td align="right">网点所在地:</td>
    		   	    <td><input onkeydown="javascript:nextKeyPress(this)" name="corp_branch" value=""></td>
    		   	    <td align="right">客户经理手机:</td>
    		   	    <td><input onkeydown="javascript:nextKeyPress(this)" name="service_man_tel" value=""></td>
    		   	</tr>
    		   	<tr bgcolor='#FFFFFF'>
    		   	    <td align="right">客户国籍:</td>
    				<td >
    					<select size="1" onkeydown="javascript:nextKeyPress(this)" name="country" style="WIDTH:120px">
    						<%=Argument.getCountry("9997CHN")%>
    					</select>
    				</td>	
    		   	    <td align="right">证件有效期限:</td>
    				<td >
    					<input TYPE="text" NAME="valid_picker" class=selecttext onkeydown="javascript:nextKeyPress(this)">
    					<input TYPE="button" value="▼" class=selectbtn onkeydown="javascript:nextKeyPress(this)" onclick="javascript:CalendarWebControl.show(theform.valid_picker,theform.valid_picker.value,this);" tabindex="13">
    					<input TYPE="hidden" NAME="valid"   value="">
    				</td>
    		   	</tr>
    		   	<tr bgcolor='#FFFFFF'>
    		   	    <td align="right">注册地址:</td>
    		   	    <td>
    		   	        <input name="register_address" size="40" onkeydown="javascript:nextKeyPress(this)" maxlength="60">
    		   	    </td>
    		   	    <td align="right">邮政编码:</td>
    		   	    <td>
    		   	        <input name="register_post_code" onkeydown="javascript:nextKeyPress(this)" maxlength="6">
    		   	    </td>
    		   	</tr>
    		   	<tr bgcolor='#FFFFFF'>
    		   	    <td align="right">控股股东或实际控制人:</td>
    		   	    <td>
    		   	        <input name="fact_controller" onkeydown="javascript:nextKeyPress(this)">
    		   	    </td>
    		   	    <td align="right">证件类型:</td>
    				<td>
    					<input onkeydown="javascript:nextKeyPress(this)"  name="fc_card_type" value="">
    				</td>
    				
    		   	</tr>
    		   	<tr bgcolor='#FFFFFF'>
    		   	    <td align="right">证件号码:</td>
    				<td>
    				    <input name="fc_card_id" onkeydown="javascript:nextKeyPress(this)" title="客户如果是个人，输入完成后回车自动显示客户对应的性别，生日，年龄" onkeyup="javascript:showLength(this)" maxlength="40" value="" size="25">
    					<span id="fc_card_id_length" class="span"></span>
    				</td>
    		   	    <td align="right">证件有效期限:</td>
    				<td >
    					<input TYPE="text" NAME="fc_valid_picker" class=selecttext onkeydown="javascript:nextKeyPress(this)">
    					<input TYPE="button" value="▼" class=selectbtn onkeydown="javascript:nextKeyPress(this)" onclick="javascript:CalendarWebControl.show(theform.fc_valid_picker,theform.fc_valid_picker.value,this);" tabindex="13">
    					<input TYPE="hidden" NAME="fc_valid"   value="">
    				</td>
    		   	</tr>
    		   	<tr bgcolor='#FFFFFF'>
    		   	    <td align="right">法定代表人姓名:</td>
    		   	    <td>
    		   	        <input name="legal_man" onkeydown="javascript:nextKeyPress(this)">
    		   	    </td>
    		   	    <td align="right"></td>
    		   	    <td>
    		   	    </td>
    		   	</tr>
    		   	<tr bgcolor='#FFFFFF'>
    		   	    <td align="right">法定代表人固定电话:</td>
    		   	    <td>
                        <input name="legal_tel" onkeydown="javascript:nextKeyPress(this)">
    		   	    </td>
    		   	    <td align="right">法定代表人手机:</td>
    		   	    <td>
    		   	        <input name="legal_mobile" onkeydown="javascript:nextKeyPress(this)">
    		   	    </td>
    		   	</tr>
    		   	<tr bgcolor='#FFFFFF'>
    		   	    <td align="right">法定代表人联系地址:</td>
    		   	    <td>
    		   	        <input name="legal_address" size="40" onkeydown="javascript:nextKeyPress(this)" maxlength="60">    
    		   	    </td>
    		   	    <td align="right">邮政编码:</td>
    		   	    <td>
    		   	        <input name="legal_post_code" onkeydown="javascript:nextKeyPress(this)">
    		   	    </td>
    		   	</tr>
    		   	<tr bgcolor='#FFFFFF'>
    		   	    <td align="right">授权办理人姓名:</td>
    		   	    <td>
    		   	        <input name="trans_name" onkeydown="javascript:nextKeyPress(this)">
    		   	    </td>
    		   	    <td align="right"></td>
    		   	    <td>
    		   	    </td>
    		   	</tr>
    		   	<tr bgcolor='#FFFFFF'>
    		   	    <td align="right">授权办理人固话:</td>
    		   	    <td>
    		   	        <input name="trans_tel" onkeydown="javascript:nextKeyPress(this)">
    		   	    </td>
    		   	    <td align="right">授权办理人手机:</td>
    		   	    <td>
    		   	        <input name="trans_mobile" onkeydown="javascript:nextKeyPress(this)">
    		   	    </td>
    		   	</tr>
    		   	<tr bgcolor='#FFFFFF'>
    		   	    <td align="right">授权办理人联系地址:</td>
    		   	    <td>
    		   	        <input name="trans_address" size="40" onkeydown="javascript:nextKeyPress(this)" maxlength="60">    
    		   	    </td>
    		   	    <td align="right">邮政编码:</td>
    		   	    <td>
    		   	        <input name="trans_post_code" onkeydown="javascript:nextKeyPress(this)">
    		   	    </td>
    		   	</tr>
    		   	<tr bgcolor='#FFFFFF'>
    		   	    <td align="right">信托资金来源:</td>
    				<td>
    				    <select size="1" onkeydown="javascript:nextKeyPress(this)" name="money_source" style="WIDTH: 120px">
    					    <%=Argument.getJg_Money_Source("")%>
    				    </select>
    				</td>
                    <td align="right">风险等级:</td>   
    				<td>
    				    <select name="grade_level" style="WIDTH: 120px" onkeydown="javascript:nextKeyPress(this)">
    				        <%=Argument.getDictParamOptions(3203, "") %>
    				    </select>
    			    </td>	
    		   	</tr>
    		   	<tr bgcolor='#FFFFFF'>
    		   	    <td align="right">备注1:</td>
    				<td>
    				    <input name="summary1" onkeydown="javascript:nextKeyPress(this)" maxlength="200" value="">
    				</td>
    				<td align="right">备注2:</td>
    				<td>
    				    <input name="summary2" onkeydown="javascript:nextKeyPress(this)" maxlength="200" value="">
    				</td>
    		   	</tr>
    		   	<tr style="text-align: left; COLOR: #0076C8; BACKGROUND-COLOR: #F4FAFF; font-weight: bold; height: 20px;">
    		    	<td colspan="6">其他信息</td>
    		   	</tr>
    		   	<tr bgcolor='#FFFFFF'>
     			    <td align="right">机构客户类型:</td>
    				<td >
    					<select size="1" onkeydown="javascript:nextKeyPress(this)" name="jg_cust_type" style="WIDTH:120px">
    						<%=Argument.getJgCustType("")%>
    					</select>
    				</td>
    				<td align="right">行业:</td>
    			    <td>
    			        <select size="1" onkeydown="javascript:nextKeyPress(this)"  name="zyhy_type" style="WIDTH: 238px" >
    			            <%=Argument.getJghyOptions2("0")%>
    			        </select>
    			    </td>
    		   	</tr>
    		   	<tr bgcolor='#FFFFFF'>
    		   	    <td align="right">合格投资人编号:</td>
    				<td>
    				    <input name="hgtzr_bh" onkeydown="javascript:nextKeyPress(this)" size="20" value="">
    				</td>
    		   	</tr>
    		   	<tr bgcolor='#FFFFFF'>
    		   	    <td align="right">VIP卡编号:</td>
    				<td>
    				    <input name="vip_card_id" onkeydown="javascript:nextKeyPress(this)" maxlength="100" size="40" value="">
    				</td>
    				<td align="right">VIP发卡日期:</td>
    				<td>
    					<input TYPE="text" NAME="vip_date_picker" class=selecttext value="" onkeydown="javascript:nextKeyPress(this)">
    					<input TYPE="button" value="▼" onkeydown="javascript:nextKeyPress(this)" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.vip_date_picker,theform.vip_date_picker.value,this);" tabindex="13">
    					<input TYPE="hidden" NAME="vip_date"   value="">
    				</td>
    		   	</tr>
    		   	<tr bgcolor='#FFFFFF'>
    		   	    <td align="right">公司电话:</td>
    				<td>
    				    <input name="o_tel" onkeydown="javascript:nextKeyPress(this)" maxlength="20" size="20" value="">
    				</td>
    				<td align="right">手机2:</td>
    				<td><input name="bp" onkeydown="javascript:nextKeyPress(this)" size="20" maxlength="20" value=""></td>
    		   	</tr>
    		   	<tr bgcolor='#FFFFFF'>
    		   	    <td align="right">传真:</td>
    				<td><input name="fax" onkeydown="javascript:nextKeyPress(this)" size="20" maxlength="20" value=""></td>
    				<td align="right">Email:</td>
    				<td><input name="e_mail" onkeydown="javascript:nextKeyPress(this)" maxlength="30" size="25" value=""></td>
    		   	</tr>
    		   	<tr bgcolor='#FFFFFF'>
    		   	    <td align="right">联系方式:</td>
    				<td>
    				    <select size="1" name="touch_type" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 120px">
    					    <%=Argument.getTouchTypeOptions("")%>
    				    </select>
    				</td>
    				<td align="right">备注信息:</td>
    				<td>
    				    <input name="summary" onkeydown="javascript:nextKeyPress(this)" maxlength="200" value="">
    				</td>
    		   	</tr>
    		   	<tr bgcolor='#FFFFFF'>
    				<td align="right">备用地址:</td>
    				<td>
    				    <input name="post_address2" size="40" onkeydown="javascript:nextKeyPress(this)" maxlength="60" value="">
    				</td>
    				<td align="right">邮政编码:</td>
    				<td>
    				    <input name="post_code2" onkeydown="javascript:nextKeyPress(this)" size="20" maxlength="6" value="">
    				</td>
    			</tr>
    		   	<tr bgcolor='#FFFFFF'>
    				<td align="right">打印客户对帐单:</td>
    				<td >
    				    <input class="flatcheckbox" type="checkbox" name="print_deploy_bill"  value="1">
    				</td>
    				<td align="right">关联标志：</td>
    				<td>
    				    <input type="checkbox" name="is_link" value="" class="flatcheckbox" onclick="javascript:showLinkInfo(this);">
    				</td>
    			</tr>
    			<tr bgcolor='#FFFFFF' id="link_info" style="display:none">
    			    <td  align="right">关联类型:</td>
    				<td >
    			    	<select  onkeydown="javascript:nextKeyPress(this)" size="1" name="link_type" style="width: 400px">
    						<%=Argument.getLinkTypeOptions(new Integer(0))%>
    					</select>
    			    </td>
    			    <td align="right">投资信托公司金额:</td>
    				<td>
    					<input onkeydown="javascript:nextKeyPress(this)" onkeyup="javascript:showCnMoney(this.value,link_gd_money_cn)" name="link_gd_money"   size="20" maxlength="60" value="">
    					<br><span id="link_gd_money_cn" class="span"></span>
    				</td>
    			</tr>
    		   	<tr bgcolor='#FFFFFF'>
    				<td  align="right">打印披露信息:</td>
    				<td colspan=3>
    				    <input class="flatcheckbox" type="checkbox" name="print_post_info" value="1">
    				</td>														
    			</tr>
    		   	<tr bgcolor='#FFFFFF'>
    				<td align="right">省级行政区域 :</td>
         			<td>
    					<select size="1"  name="gov_prov_regional" style="width: 120px" onkeydown="javascript:nextKeyPress(this)" onchange="javascript:setGovRegional(this.value);">
    						<%=Argument.getCustodianNameLis(new Integer(9999), "", new Integer(0),"")%>
    					</select>    					
         			</td>
    				<td align="right">行政区域 :</td>
         			<td>
						<select size='1' id="gov_regional"  name='gov_regional' style='width: 120px' onkeydown='javascript:nextKeyPress(this)'>
							<option value="">请选择</option>
						</select>
         			</td>
    			</tr>
    		   	<tr id="reader2" style="display:" bgcolor='#FFFFFF'>
    	          	<td class="paramTitle"align="right">添加附件:</td>
    	            <td colspan="2">
    	            	<input type="file" id="file_info" name="file_info" size="60">&nbsp;<!--<img title="选择附加文件" src=/images/minihelp.gif">-->
    	            </td>
    				<td><input type="checkbox" name="complete_flag" class="flatcheckbox" value="2"><font color="red">资料完整</font></td>
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
						<button type="button"  class="xpbutton3" accessKey=b id="btnReturn" name="btnReturn" onclick="javascript:disableAllBtn(true);back();">返回(<u>B</u>)</button>
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
