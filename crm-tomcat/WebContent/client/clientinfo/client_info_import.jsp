<%@ page language="java" pageEncoding="GBK" import="enfo.crm.system.*,enfo.crm.tools.*,enfo.crm.dao.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>

<HTML>
<HEAD>
<TITLE><%=LocalUtilis.language("menu.custImportWizard",clientLocale)%> </TITLE><!--�ͻ�������-->
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/io.js"></SCRIPT>
<script language="javascript" src="<%=request.getContextPath()%>/includes/jQuery/js/jquery-1.3.2.min.js"></SCRIPT>
<script language=javascript>
var importStep = 0;//0:ѡ���ļ�1:ӳ���ֶ�2:��ɵ���
var totalFieldsNum = 17;//���̲��������̹�18������ȥinput_man��17��
var jsonStr = {
'cust_name' : {'method' : 'setCust_name', 'name' : '�ͻ�����', 'dataType' : '1', 'maxLength' : 100},//�ͻ�����
'cust_type_name':{'method' : 'setCust_type_name', 'name':'�ͻ����','dataType':'1', 'maxLength' : 10},//�ͻ����
'card_type_name':{'method':'setCard_type_name', 'name':'֤������','dataType':'1', 'maxLength' : 30},//֤������
'card_id' : {'method' : 'setCard_id', 'name' : '֤������', 'dataType' : '1', 'maxLength' : 40},//֤������
'post_address':{'method':'setPost_address','name':'�ʼĵ�ַ','dataType':'1', 'maxLength': 60},//�ʼĵ�ַ
'post_code' : {'method' : 'setPost_code', 'name' : '��������', 'dataType':'1', 'maxLength': 6},//��������
'mobile':{'method':'setMobile','name':'�ֻ�1','dataType':'1', 'maxLength': 100},//�ֻ�
'cust_tel':{'method':'setCust_tel','name':'�ֻ�2','dataType':'1', 'maxLength': 40},//�̻�
'e_mail':{'method':'setE_mail','name':'�����ʼ�','dataType':'1', 'maxLength': 60},//�����ʼ�
'contact_man':{'method':'setContact_man','name':'��ϵ��','dataType':'1', 'maxLength': 60},//��ϵ��
'service_man_name':{'method':'setService_man_name','name':'�ͻ�����','dataType':'1', 'maxLength': 64},//�ͻ�����
'isclient':{'method':'setIsClient','name':'�Ƿ�ί����','dataType':'1', 'maxLength': 10},//�Ƿ�ί����
'touch_type_name':{'method':'setTouch_type_name','name':'��ϵ��ʽ','dataType':'1', 'maxLength': 30},//��ϵ��ʽ
'cust_source_name':{'method':'setCust_source_name','name':'�ͻ�������Դ����','dataType':'1', 'maxLength': 30},//�ͻ�������Դ����
'age':{'method':'setAge','name':'����','dataType':'4'},//����
'potenital_money':{'method':'setPotenital_money','name':'Ǳ�ڹ�����','dataType':'2'},//Ǳ�ڹ�����
'cust_id' : {'method' : 'setCust_id', 'name' : "�ͻ�ID", "dataType" : "4"},				//below carlos add
'birthday' : {'method' : 'setBirthday', 'name' : "��������", "dataType" : "4"}	,
'sex_name' : {'method' : 'setSex_name', 'name' : '�Ա�', 'dataType' : '1', 'maxLength': 10},
'voc_type_name' : {'method' : 'setVoc_type_name', 'name' : 'ְҵ/��ҵ', 'dataType' : '1', 'maxLength': 30},//ְҵ/��ҵ
'h_tel' : {'method' : 'setH_tel', 'name' : '��ͥ�绰', 'dataType' : '1', 'maxLength': 60},		//��ͥ�绰
'o_tel' : {'method' : 'setO_tel', 'name' : '��˾�绰', 'dataType' : '1', 'maxLength': 60},		//��˾�绰
'mobile2' : {'method' : 'setMobile2', 'name' : '�ֻ�2', 'dataType' : '1', 'maxLength': 60}, 	//�ֻ�2
'fax' : {'method' : 'setFax', 'name' : '����', 'dataType' : '1', 'maxLength': 60},			//����
'post_address2' : {'method' : 'setPost_address2', 'name' : '�ʼĵ�ַ2', 'dataType' : '1', 'maxLength': 60},	//��ַ2
'post_code2' : {'method' : 'setPost_code2', 'name' : '��������2', 'dataType' : '1', 'maxLength': 6}, 	//�ʱ�2
'legal_man' : {'method' : 'setLegal_man', 'name' : '��������', 'dataType' : '1', 'maxLength': 30}, 	//��������

'company_unit' : {'method' : 'setCompany_unit', 'name' : '���ڵ�λ', 'dataType' : '1', 'maxLength': 60}, 	//���ڵ�λ
'company_depart' : {'method' : 'setCompany_depart', 'name' : '���ڲ���', 'dataType' : '1', 'maxLength': 30}, 	//���ڲ���
'company_position' : {'method' : 'setCompany_position', 'name' : 'ְλ', 'dataType' : '1', 'maxLength': 60}, 	//ְλ

'cust_aum' : {'method' : 'setCust_aum', 'name' : '�ͻ�AUM', 'dataType' : '1', 'maxLength': 60},//�ͻ�AUM
'inves_experinc' : {'method' : 'setInves_experinc', 'name' : '����Ͷ�ʾ���', 'dataType' : '1', 'maxLength': 60}, 	//����Ͷ�ʾ���
'gov_prov_regional_name' : {'method' : 'setGov_prov_regional_name', 'name' : 'ʡ', 'dataType' : '1', 'maxLength': 60},//ʡ
'gov_regional_name' : {'method' : 'setGov_regional_name', 'name' : '��', 'dataType' : '1', 'maxLength': 60},//��
'risk_pref' : {'method' : 'setRisk_pref', 'name' : '����ƫ��', 'dataType' : '1', 'maxLength': 60},//����ƫ��
'hobby_pref' : {'method' : 'setHobby_pref', 'name' : '��Ȥ����', 'dataType' : '1', 'maxLength': 60},//��Ȥ����
'cust_source_explain' : {'method' : 'setCust_source_explain', 'name' : '�ͻ�������Դ����', 'dataType' : '1', 'maxLength': 60},//�ͻ�������Դ����
'cust_age_group' : {'method' : 'setCust_age_group', 'name' : '�����', 'dataType' : '1', 'maxLength': 60}//�����
};

var tempArr = {};

function importAction(step){
    showWaitting(1);
    if(importStep==0){
        var fullPath = $('#importFile').val();
        var fileType = fullPath.substring(fullPath.lastIndexOf(".")+1);
        if(fileType=="xls"){
            if($('#headFlag').attr("checked")==true){
                //�����excel�ļ����б�����
                var fieldCount = getExcelColsCount(fullPath);
                var fieldNames = readExcelbyRow(fullPath,"",1);
                var fieldNameArr = fieldNames.split("#");
                for(i=0;i<fieldCount;i++){
                    if($('#tbl_field tr:last').attr('class')==null||$('#tbl_field tr:last').attr('class')=='tr1')
    				    trClass = 'tr0';
        			else
        				trClass = 'tr1';
        			var trinfo="<tr id='tr_"+i+"'><td width='50%'><input type='checkbox' id='field_"+i+"' name='field_"+i+"' value=''>"+fieldNameArr[i]+"</td><td id='id_"+i+ "' width='50%'></td></tr>";
        			$('#tbl_field').append(trinfo);
        			$("#field_"+i).attr('class','flatcheckbox');
        			$('#tr_'+i).attr('class',trClass).css('cursor','hand').dblclick(function(){selectField(this.id);});
        			$('#field_'+i).click(function(){selectField2(this)});
					//fiter by Carlos
					autoFilterDataField();
                }//*/
            }else{
                //�����excel�ļ�û�б�����
                var fieldCount = getExcelColsCount(fullPath);
                for(i=0;i<fieldCount;i++){
                    if($('#tbl_field tr:last').attr('class')==null||$('#tbl_field tr:last').attr('class')=='tr1')
    				    trClass = 'tr0';
        			else
        				trClass = 'tr1';
        			/*�� ��*/
                    var trinfo="<tr id='tr_"+i+"'><td width='50%'><input type='checkbox' id='field_"+i+"' name='field_"+i+"' value=''><%=LocalUtilis.language("message.no4",clientLocale)%>"+(i+1)+"<%=LocalUtilis.language("message.cols",clientLocale)%> </td><td id='id_"+i+ "' width='50%'></td></tr>";
        			$('#tbl_field').append(trinfo);
        			$("#field_"+i).attr('class','flatcheckbox');
        			$('#tr_'+i).attr('class',trClass).css('cursor','hand').dblclick(function(){selectField(this.id);});
        			$('#field_'+i).click(function(){selectField2(this)});
                }
            }

            $('#promptTitle').html("<%=LocalUtilis.language("message.mapInputField",clientLocale)%> :");//ӳ�������ֶ�
            $('#btnNext').html("<%=LocalUtilis.language("message.finish",clientLocale)%> ");//���
            $('#div_file').css("visibility","hidden");
            $('#div_field').css("visibility","visible");
            $('#btnBack').attr("disabled","");

        }else{
            alert("<%=LocalUtilis.language("message.fileError",clientLocale)%> ");//��ȷ��ѡ������ȷ���ļ�����ǰֻ֧��EXCEL
             showWaitting(0);
            return false;
        }
    }else if(importStep==1){
        if(step>0){
            if($("#tbl_field tr input:checked").size()>0){
                var checkBoxObj = $("#tbl_field tr input:checked");
                var fieldArr = new Array();
                var fieldMethod = "";
                var fieldDataType = "";
                var objPath = $('#importFile').val();;
                var dropFlag;
                var fieldDatas = "";
                if($('#headFlag').attr("checked")==true)
                    dropFlag = 1;
                else
                    dropFlag = 0;

                for(i=0;i<checkBoxObj.length;i++){
                    fieldArr[i] = Number(checkBoxObj[i].id.substring(checkBoxObj[i].id.indexOf("_")+1))+1;
                    fieldMethod = fieldMethod + eval("jsonStr."+checkBoxObj[i].value+".method")+"$";
                    fieldDataType = fieldDataType + eval("jsonStr."+checkBoxObj[i].value+".dataType")+"$";
                }
                var fieldData = readExcelByFields(objPath,fieldArr,dropFlag, "");
                for(j=0;j<fieldData.length;j++){
                    //alert(fieldData[j]);
                    fieldDatas = fieldDatas + fieldData[j] +"$"
                }
                var retVal = new Array();

                retVal[0] = fieldDatas;
                retVal[1] = fieldMethod;
                retVal[2] = fieldDataType;

                window.returnValue = retVal;
                window.close();

            }else{
                alert("<%=LocalUtilis.language("message.noMapfield",clientLocale)%> ��");//û��ӳ���κ��ֶΣ���ӳ����ٲ���
                return false;
            }
        }else{
            $('#promptTitle').html("<%=LocalUtilis.language("message.selectFile",clientLocale)%> :");//��ѡ��Ҫ������ļ�
            $('#btnNext').html('<%=LocalUtilis.language("message.next",clientLocale)%> ');//��һ��
            $('#div_file').css("visibility","visible");
            $('#div_field').css("visibility","hidden");
            $('#btnBack').attr("disabled","disabled");
            $('#tbl_field tr').remove();
        }
    }else if(importStep==2){

    }

    importStep = importStep+step;
    showWaitting(0);
}

function autoFilterDataField(){
	$("#tbl_field td").filter(":even").each(function(){
		for(var item in jsonStr){
			if(jsonStr[item].name == $(this).text()){
				$(this).find("input[type='checkbox']").val(item).attr("checked", "checked");
				$(this).siblings().html(jsonStr[item].name);
				tempArr[item] = jsonStr[item].name;
				continue;
			}
		}
	});
}

function selectField(findFlag){

    var findFlag = findFlag.substring(findFlag.lastIndexOf("_"));
    var fieldName = $('#tr'+findFlag+' td:first').html();
    fieldName = fieldName.substring(fieldName.lastIndexOf(">")+1);
    var result = showModalDialog('client_info_import_field.jsp?fieldName='+fieldName,'','dialogWidth:350px;dialogHeight:200px;status:0;help:0');
    var results;

    if(result!=null){
        results = result.split("$")
        if(results[0]==$("#field"+findFlag).val()){
            return false;
        }else{
            tempArr[$("#field"+findFlag).val()] = "";
        }
        if(tempArr[results[0]]!=null&&tempArr[results[0]]!=""){
            //��ǰ//�ֶ��Ѿ�ӳ����ֶΣ�������ѡ��
            alert("<%=LocalUtilis.language("message.current",clientLocale)%>  \""+tempArr[results[0]]+"\"  <%=LocalUtilis.language("message.fieldmapped",clientLocale)%> ��");
            return false;
        }
        $("#field"+findFlag).val(results[0]);
        $("#id"+findFlag).html(results[1]);
        $("#field"+findFlag).attr("checked","checked");
        tempArr[results[0]] = fieldName;
    }else{
        $("#field"+findFlag).attr("checked","");
    }
}

function selectField2(obj){
    var objId= obj.id;
    if($("#"+objId).attr("checked")==false){
        tempArr[$("#"+objId).val()]="";
        $("#"+objId).val("");
        $("#id"+objId.substring(objId.indexOf("_"))).html("");
    }else{
        selectField(objId);
    }

}
</script>
</HEAD>
<BODY class="BODY body-nox">

<form name="theform">

<%@ include file="/includes/waiting.inc"%>
<div>
    <table border="0" width="100%" cellspacing="0" cellpadding="0" >
	    <tr>
		    <!--�ͻ���Ϣ����-->
            <td class="page-title"><b><font color="#215dc6"><%=menu_info%>>><%=LocalUtilis.language("message.importCustInfo",clientLocale)%> </b></td>
	    </tr>
	    
    </table>
</div>
<div>
    <div id="div_file">
        <table id="tbl_file" class="table-popup" border="0" width="100%" cellspacing="0" cellpadding="0">
            <tr>
	            <td align="left" id="promptTitle"><%=LocalUtilis.language("message.selectFile",clientLocale)%> :</td><!--��ѡ��Ҫ������ļ�-->
	        </tr>
            <tr>
                <td>
                    <input style="width:95%" type="file" name="importFile" id="importFile">
                </td>
            </tr>
            <tr>
                <td>
                    <!--�Ƿ��б�����-->
                    <input type="checkbox" class="flatcheckbox" name="headFlag" id="headFlag" value=""><%=LocalUtilis.language("message.isContHeaderRows",clientLocale)%>
                </td>
            </tr>
        </table>
    </div>
    <div id="div_field" style="height:300;visibility:hidden">
        <div>
            <table class="tablelinecolor" border="0" width="100%" cellspacing="0" cellpadding="0">
                <tr class="trh">
                    <td><%=LocalUtilis.language("message.importFields",clientLocale)%> </td><!--�����ֶ�-->
                    <td><%=LocalUtilis.language("message.mappingField",clientLocale)%> </td><!--ӳ���ֶ�-->
                </tr>
            </table>
        </div>
        <div style="height:200px;overflow-y:auto">
            <table class="tablelinecolor" id="tbl_field" border="0" width="100%" cellspacing="1" cellpadding="2">
            </table>
        </div>
    </div>
</div>
<br><br><br>
<div>
    <table border="0" width="100%" cellspacing="0" cellpadding="0">
        <tr>
            <td align="right">
                <!--��һ��-->
                <button type="button"  id="btnBack" disabled class="xpbutton3" onclick="javascript:importAction(-1)"><%=LocalUtilis.language("message.up",clientLocale)%> </button>&nbsp;&nbsp;&nbsp;
                <!--��һ��-->
                <button type="button"  id="btnNext"class="xpbutton3" onclick="javascript:importAction(1)"><%=LocalUtilis.language("message.next",clientLocale)%> </button>&nbsp;&nbsp;&nbsp;
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