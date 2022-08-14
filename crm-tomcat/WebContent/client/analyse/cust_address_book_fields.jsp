<%@ page language="java" pageEncoding="GBK" import="enfo.crm.system.*,enfo.crm.tools.*,enfo.crm.dao.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>
<HTML>
<HEAD>
<TITLE></TITLE>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<script language="javascript" src="<%=request.getContextPath()%>/includes/jQuery/js/jquery-1.3.2.min.js"></SCRIPT>
<script language=javascript>
//客户编号、客户名称、住宅电话、公司电话、手机号码、传真、邮寄地址、邮政编码、项目名称
var jsonStr = {"cust_no":{"titleName":"<%=LocalUtilis.language("class.customerID",clientLocale)%>", "fieldName":"cust_no", "fieldType":"1"},
"cust_name":{"titleName":"<%=LocalUtilis.language("class.customerName",clientLocale)%>", "fieldName":"cust_name", "fieldType":"1"},
"h_tel":{"titleName":"<%=LocalUtilis.language("class.telephone",clientLocale)%>", "fieldName":"h_tel", "fieldType":"1"},
"o_tel":{"titleName":"<%=LocalUtilis.language("class.companyPhone",clientLocale)%>", "fieldName":"o_tel", "fieldType":"1"},
"mobile":{"titleName":"<%=LocalUtilis.language("class.mobile",clientLocale)%>1", "fieldName":"mobile", "fieldType":"1"},
"cust_tel":{"titleName":"<%=LocalUtilis.language("class.mobile",clientLocale)%>2", "fieldName":"cust_tel", "fieldType":"1"},
"fax":{"titleName":"<%=LocalUtilis.language("class.fax",clientLocale)%>", "fieldName":"fax", "fieldType":"1"},
"e_mail":{"titleName":"E-MAIL", "fieldName":"e_mail", "fieldType":"1"},
"post_address":{"titleName":"<%=LocalUtilis.language("class.postcode",clientLocale)%><fmt:message key='class.postAddress2'/>", "fieldName":"post_address", "fieldType":"1"},
"post_code":{"titleName":"<%=LocalUtilis.language("class.postcode",clientLocale)%>", "fieldName":"post_code", "fieldType":"1"},
"product_name":{"titleName":"<%=LocalUtilis.language("class.productName2",clientLocale)%>", "fieldName":"product_name", "fieldType":"1"}};
$(function(){
    $("input[type='checkbox']").click(function(){
            refreshStyle(this.id);
        });
});

function refreshStyle(objId){
    if($('#td_'+objId).size()==0){
        $('#titleStyle').append("<td id='td_"+objId+"'>"+eval("jsonStr."+objId+".titleName")+"</td>");    
    }else{
        $('#td_'+objId).remove();
    }        
}

function styleOk(){
    var obj = $('#titleStyle td');
    var result = new Array("","","");
    if(obj.size()!=0){
        for(i=0;i<obj.length;i++){
            temp = obj[i].id.substring(3);
            result[0] = result[0]+eval("jsonStr."+temp+".titleName")+"$"+i+"$1$"+i+"$1"+"#";//titleName
            result[1] = result[1]+eval("jsonStr."+temp+".fieldName")+"#";//fieldName
            result[2] = result[2]+eval("jsonStr."+temp+".fieldType")+"#";//fieldType
        }
        window.returnValue = result;
        window.close();
    }else{
        alert("<%=LocalUtilis.language("message.exportFieldsTip",clientLocale)%> ");//未选中任何导出字段
        window.returnValue = null;
        window.close();
    }
    
}
</script>
</HEAD>
<BODY class="BODY">

<form name="theform">
<%@ include file="/includes/waiting.inc"%>
<div>
    <table border="0" width="100%" cellspacing="0" cellpadding="0">
	    <tr>
		    <!--客户通讯录导出字段选择-->
            <td><img src="<%=request.getContextPath()%>/images/member.gif" align="absBottom" border=0 width="32" height="28"><b><%=menu_info%>>><%=LocalUtilis.language("message.exportFieldChoose",clientLocale)%> </b></td>
	    </tr>
	    <tr>
	        <td>
	            <hr/>
	        </td>
	    </tr>
    </table>
</div>
<div>
    <table border="0" width="100%" cellspacing="0" cellpadding="0">
        <tr>
            <!--客户通讯录导出可选字段-->
            <td align="center" colspan="4"><font size="4" color="blue"><%=LocalUtilis.language("message.chooseExportFields",clientLocale)%> </font></td>
        </tr>
        <tr>
            <td>
                <input class=flatcheckbox type="checkbox" name="cust_no" id="cust_no"><%=LocalUtilis.language("class.customerID",clientLocale)%> <!--客户编号-->
            </td>
            <td>
                <input class=flatcheckbox type="checkbox" name="cust_name" id="cust_name"><%=LocalUtilis.language("class.customerName",clientLocale)%> <!--客户名称-->
            </td>
            <td>
                <input class=flatcheckbox type="checkbox" name="h_tel" id="h_tel"><%=LocalUtilis.language("class.telephone",clientLocale)%> <!--住宅电话-->
            </td>
            <td>
                <input class=flatcheckbox type="checkbox" name="o_tel" id="o_tel"><%=LocalUtilis.language("class.companyPhone",clientLocale)%> <!--公司电话-->
            </td>
        </tr>
        <tr>
            <td>
                <input class=flatcheckbox type="checkbox" name="mobile" id="mobile"><%=LocalUtilis.language("class.mobile",clientLocale)%> 1<!--手机号码-->
            </td>
            <td>
                <input class=flatcheckbox type="checkbox" name="cust_tel" id="cust_tel"><%=LocalUtilis.language("class.mobile",clientLocale)%> 2<!--手机号码-->
            </td>
            <td>
                <input class=flatcheckbox type="checkbox" name="fax" id="fax"><%=LocalUtilis.language("class.fax",clientLocale)%> <!--传真-->
            </td>
            <td>
                <input class=flatcheckbox type="checkbox" name="e_mail" id="e_mail">E-MAIL
            </td>
        </tr>
        <tr>
            <td>
                <input class=flatcheckbox type="checkbox" name="post_address" id="post_address"><%=LocalUtilis.language("class.postAddress2",clientLocale)%> <!--邮寄地址-->
            </td>
            <td>
                <input class=flatcheckbox type="checkbox" name="post_code" id="post_code"><%=LocalUtilis.language("class.postcode",clientLocale)%> <!--邮政编码-->
            </td>
            <td>
                <input class=flatcheckbox type="checkbox" name="product_name" id="product_name"><%=LocalUtilis.language("class.productName2",clientLocale)%> <!--项目名称-->
            </td>
        </tr>
        <tr>
	        <td colspan="4">
	            <hr/>
	        </td>
	    </tr>
    </table>
</div>
<br><br><br>
<div>
    <table border="0" width="100%" cellspacing="0" cellpadding="0">
        <tr>
            <td align="center" id="titleName"><font size="4" color="blue"><%=LocalUtilis.language("message.contactsPreview",clientLocale)%> </font></td><!--客户通讯录导出样式预览-->
        </tr>
        <tr>
            <table border="0" width="100%" border="0" cellspacing="1" cellpadding="2" class="tablelinecolor">
                <tr class="trh" id="titleStyle">
                </tr>
            </table>
        </tr>
    </table>
</div>
<br><br><br>
<div>
    <table border="0" width="100%" cellspacing="0" cellpadding="0">
        <tr>
            <td align="right">
                <!--确定-->
                <button type="button"  class="xpbutton3" onclick="javascript:styleOk();"><%=LocalUtilis.language("message.confirm",clientLocale)%> </button>&nbsp;&nbsp;&nbsp;
                <!--<button type="button"  class="xpbutton3">默认</button>&nbsp;&nbsp;&nbsp;-->
                <!--取消-->
                <button type="button"  class="xpbutton3" onclick="javascript:window.close();"><%=LocalUtilis.language("message.cancel",clientLocale)%> </button>&nbsp;&nbsp;&nbsp;
           <td>
        </tr>
    </table>
</div>
<%@ include file="/includes/foot.inc"%>
</form>    
    
</BODY>
</HTML>