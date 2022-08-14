<%@ page language="java" pageEncoding="GBK" import="enfo.crm.system.*,enfo.crm.tools.*,enfo.crm.dao.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>
<HTML>
<HEAD>
<TITLE></TITLE>
<base target="_self">
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
//jsonStrӦ�ô�fieldsdim�����menu_idȡ�����˴�д������ʱ���ٸ�
var jsonStr = {"cust_no":{"fieldName":"cust_no","titleName":"<%=LocalUtilis.language("class.customerID",clientLocale)%> ","fieldType":"1"},//�ͻ����
							 "cust_name":{"fieldName":"cust_name","titleName":"<%=LocalUtilis.language("class.customerName",clientLocale)%> ","fieldType":"1"},//�ͻ�����
							 "sex":{"fieldName":"sex","titleName":"<%=LocalUtilis.language("class.sex",clientLocale)%> ","fieldType":"1"},//�Ա�
							 "birthday":{"fieldName":"birthday","titleName":"<%=LocalUtilis.language("class.birthda",clientLocale)%> ","fieldType":"3"},//��������
							 "post_address":{"fieldName":"post_address","titleName":"<%=LocalUtilis.language("class.postAddress2",clientLocale)%> ","fieldType":"1"},//�ʼĵ�ַ
							 "card_id":{"fieldName":"card_id","titleName":"<%=LocalUtilis.language("class.customerCardID",clientLocale)%> ","fieldType":"1"},//֤������
							 "cust_tel":{"fieldName":"cust_tel","titleName":"<%=LocalUtilis.language("login.telephone",clientLocale)%> ","fieldType":"1"},//��ϵ�绰
							 "e_mail":{"fieldName":"e_mail","titleName":"E-MAIL","fieldType":"1"},
							 "mobile":{"fieldName":"mobile","titleName":"<%=LocalUtilis.language("class.mobile",clientLocale)%> ","fieldType":"1"},//�ֻ�����
							 "bp":{"fieldName":"bp","titleName":"BP","fieldType":"1"},
							 "status_name":{"fieldName":"status_name","titleName":"<%=LocalUtilis.language("class.customerStatus",clientLocale)%> ","fieldType":"1"},//�ͻ�״̬
							 "last_rg_date":{"fieldName":"last_rg_date","titleName":"<%=LocalUtilis.language("message.lastRgDate",clientLocale)%> ","fieldType":"3"},//�������ʱ��
							 "total_money":{"fieldName":"total_money","titleName":"<%=LocalUtilis.language("class.totalMoney",clientLocale)%> ","fieldType":"2"},//������
							 "current_money":{"fieldName":"current_money","titleName":"<%=LocalUtilis.language("class.current_money",clientLocale)%> ","fieldType":"2"},//�������
							 "ben_amount":{"fieldName":"ben_amount","titleName":"<%=LocalUtilis.language("class.benefitShare",clientLocale)%> ","fieldType":"2"},//������
							 "exchange_amount":{"fieldName":"exchange_amount","titleName":"<%=LocalUtilis.language("class.exchangeAmount",clientLocale)%> ","fieldType":"2"},//ת�ý��
							 "back_amount":{"fieldName":"back_amount","titleName":"<%=LocalUtilis.language("class.backAmount",clientLocale)%> ","fieldType":"2"},//�Ҹ����
							 "service_man":{"fieldName":"service_man","titleName":"<%=LocalUtilis.language("class.accountManager",clientLocale)%> ","fieldType":"1"},//�ͻ�����
							 "cust_type_name":{"fieldName":"cust_type_name","titleName":"<%=LocalUtilis.language("class.customerType",clientLocale)%> ","fieldType":"1"},
							 "grade_level_name":{"fieldName":"grade_level_name","titleName":"<%=LocalUtilis.language("class.gradeLevel",clientLocale)%> ","fieldType":"1"},//���յȼ�
							 "card_type_name":{"fieldName":"card_type_name","titleName":"<%=LocalUtilis.language("class.customerCardType",clientLocale)%> ","fieldType":"1"},//֤������
							 "class10":{"fieldName":"class10","titleName":"<%=LocalUtilis.language("class.isTransactions",clientLocale)%> ","fieldType":"1"},//�Ƿ���
							 "class11":{"fieldName":"class11","titleName":"<%=LocalUtilis.language("class.liveness",clientLocale)%> ","fieldType":"1"},//��Ծ��
							 "cust_level_name":{"fieldName":"cust_level_name","titleName":"<%=LocalUtilis.language("class.customerLevel",clientLocale)%> ","fieldType":"1"}};//�ͻ�����
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
    var result = "";
    if(obj.size()!=0){
        for(i=0;i<obj.length;i++){
            temp = obj[i].id.substring(3);
            result = result+eval("jsonStr."+temp+".fieldName")+"$";//fieldName
            alert(result);
            $('#viewstr').val(result);
        }
        document.theform.submit();
    }else{
        window.returnValue = null;
        window.close();
    }
    
}
</script>
</HEAD>
<BODY class="BODY">

<form name="theform" method="post" action="client_query_view_set_action.jsp">
<input type="hidden" name="viewstr" id="viewstr">
<%@ include file="/includes/waiting.inc"%>
<div>
    <table border="0" width="100%" cellspacing="0" cellpadding="0">
	    <tr>
		    <!--�ͻ�ͨѶ¼�����ֶ�ѡ��-->
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
            <td align="center" colspan="4"><font size="4" color="blue"><%=LocalUtilis.language("message.chooseExportFields",clientLocale)%> </font></td><!--�ͻ�ͨѶ¼������ѡ�ֶ�-->
        </tr>
        <tr>
          <td><input type="checkbox" class="flatcheckbox" id="cust_no" name="cust_no"><%=LocalUtilis.language("class.customerID",clientLocale)%> </td><!--�ͻ����-->
					<td><input type="checkbox" class="flatcheckbox" id="cust_name" name="cust_name"><%=LocalUtilis.language("class.customerName",clientLocale)%> </td><!--�ͻ�����-->
					<td><input type="checkbox" class="flatcheckbox" id="sex" name="sex"><%=LocalUtilis.language("class.sex",clientLocale)%> </td><!--�Ա�-->
					<td><input type="checkbox" class="flatcheckbox" id="birthday" name="birthday"><%=LocalUtilis.language("class.birthday",clientLocale)%> </td><!--��������-->
        </tr>                                                                   
        <tr>                                                                    
					<td><input type="checkbox" class="flatcheckbox" id="post_address" name="post_address"><%=LocalUtilis.language("class.postAddress2",clientLocale)%> </td><!--�ʼĵ�ַ-->
					<td><input type="checkbox" class="flatcheckbox" id="card_id" name="card_id"><%=LocalUtilis.language("class.customerCardID",clientLocale)%> </td><!--֤������-->
					<td><input type="checkbox" class="flatcheckbox" id="cust_tel" name="cust_tel"><%=LocalUtilis.language("login.telephone",clientLocale)%> </td><!--��ϵ�绰-->
					<td><input type="checkbox" class="flatcheckbox" id="e_mail" name="e_mail">E-MAIL</td>
        </tr>                                                                   
        <tr>                                                                    
					<td><input type="checkbox" class="flatcheckbox" id="mobile" name="mobile"><%=LocalUtilis.language("class.mobile",clientLocale)%> </td><!--�ֻ�����-->
					<td><input type="checkbox" class="flatcheckbox" id="bp" name="bp">BP</td>
					<td><input type="checkbox" class="flatcheckbox" id="status_name" name="status_name"><%=LocalUtilis.language("class.customerStatus",clientLocale)%> </td><!--�ͻ�״̬-->
					<td><input type="checkbox" class="flatcheckbox" id="last_rg_date" name="last_rg_date"><%=LocalUtilis.language("message.lastRgDate",clientLocale)%> </td><!--�������ʱ��-->
        </tr>                                                                   
        <tr>                                                                    
        	<td><input type="checkbox" class="flatcheckbox" id="total_money" name="total_money"><%=LocalUtilis.language("class.totalMoney",clientLocale)%> </td><!--������-->
					<td><input type="checkbox" class="flatcheckbox" id="current_money" name="current_money"><%=LocalUtilis.language("class.current_money",clientLocale)%> </td><!--�������-->
					<td><input type="checkbox" class="flatcheckbox" id="ben_amount" name="ben_amount"><%=LocalUtilis.language("class.benefitShare",clientLocale)%> </td><!--������-->
					<!--ת�ý��-->
                    <td><input type="checkbox" class="flatcheckbox" id="exchange_amount" name="exchange_amount"><%=LocalUtilis.language("class.exchangeAmount",clientLocale)%> </td>
        </tr>                                                                   
      	<tr>                                                                    
      		<td><input type="checkbox" class="flatcheckbox" id="back_amount" name="back_amount"><%=LocalUtilis.language("class.backAmount",clientLocale)%> </td><!--�Ҹ����-->
					<td><input type="checkbox" class="flatcheckbox" id="service_man" name="service_man"><%=LocalUtilis.language("class.accountManager",clientLocale)%> </td><!--�ͻ�����-->
					<td><input type="checkbox" class="flatcheckbox" id="cust_type_name" name="cust_type_name"><%=LocalUtilis.language("class.customerType",clientLocale)%> </td><!--�ͻ����-->
					<td><input type="checkbox" class="flatcheckbox" id="grade_level_name" name="grade_level_name"><%=LocalUtilis.language("class.gradeLevel",clientLocale)%> </td><!--���յȼ�-->
      	</tr>                                                                   
      	<tr>                                                                    
      		<td><input type="checkbox" class="flatcheckbox" id="card_type_name" name="card_type_name"><%=LocalUtilis.language("class.customerCardType",clientLocale)%> </td><!--֤������-->
					<td><input type="checkbox" class="flatcheckbox" id="class10" name="class10"><%=LocalUtilis.language("class.isTransactions",clientLocale)%> </td><!--�Ƿ���-->
					<td><input type="checkbox" class="flatcheckbox" id="class11" name="class11"><%=LocalUtilis.language("class.liveness",clientLocale)%> </td><!--��Ծ��-->
					<td><input type="checkbox" class="flatcheckbox" id="cust_level_name" name="cust_level_name"><%=LocalUtilis.language("class.customerLevel",clientLocale)%> </td><!--�ͻ�����-->
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
            <td align="center" id="titleName"><font size="4" color="blue"><%=LocalUtilis.language("message.contactsPreview",clientLocale)%> </font></td><!--�ͻ�ͨѶ¼������ʽԤ��-->
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
                <!--ȷ��-->
                <button type="button"  class="xpbutton3" onclick="javascript:styleOk();"><%=LocalUtilis.language("message.confirm",clientLocale)%> </button>&nbsp;&nbsp;&nbsp;
                <!--<button type="button"  class="xpbutton3">Ĭ��</button>&nbsp;&nbsp;&nbsp;-->
                <!--ȡ��-->
                <button type="button"  class="xpbutton3" onclick="javascript:window.close();"><%=LocalUtilis.language("message.cancel",clientLocale)%> </button>&nbsp;&nbsp;&nbsp;
           <td>
        </tr>
    </table>
</div>
<%@ include file="/includes/foot.inc"%>
</form>    
    
</BODY>
</HTML>