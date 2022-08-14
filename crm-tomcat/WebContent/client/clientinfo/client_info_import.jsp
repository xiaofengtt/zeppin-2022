<%@ page language="java" pageEncoding="GBK" import="enfo.crm.system.*,enfo.crm.tools.*,enfo.crm.dao.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>

<HTML>
<HEAD>
<TITLE><%=LocalUtilis.language("menu.custImportWizard",clientLocale)%> </TITLE><!--客户导入向导-->
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
var importStep = 0;//0:选择文件1:映射字段2:完成导入
var totalFieldsNum = 17;//过程参数，过程共18个，除去input_man共17个
var jsonStr = {
'cust_name' : {'method' : 'setCust_name', 'name' : '客户名称', 'dataType' : '1', 'maxLength' : 100},//客户名称
'cust_type_name':{'method' : 'setCust_type_name', 'name':'客户类别','dataType':'1', 'maxLength' : 10},//客户类别
'card_type_name':{'method':'setCard_type_name', 'name':'证件类型','dataType':'1', 'maxLength' : 30},//证件类型
'card_id' : {'method' : 'setCard_id', 'name' : '证件号码', 'dataType' : '1', 'maxLength' : 40},//证件号码
'post_address':{'method':'setPost_address','name':'邮寄地址','dataType':'1', 'maxLength': 60},//邮寄地址
'post_code' : {'method' : 'setPost_code', 'name' : '邮政编码', 'dataType':'1', 'maxLength': 6},//邮政编码
'mobile':{'method':'setMobile','name':'手机1','dataType':'1', 'maxLength': 100},//手机
'cust_tel':{'method':'setCust_tel','name':'手机2','dataType':'1', 'maxLength': 40},//固话
'e_mail':{'method':'setE_mail','name':'电子邮件','dataType':'1', 'maxLength': 60},//电子邮件
'contact_man':{'method':'setContact_man','name':'联系人','dataType':'1', 'maxLength': 60},//联系人
'service_man_name':{'method':'setService_man_name','name':'客户经理','dataType':'1', 'maxLength': 64},//客户经理
'isclient':{'method':'setIsClient','name':'是否委托人','dataType':'1', 'maxLength': 10},//是否委托人
'touch_type_name':{'method':'setTouch_type_name','name':'联系方式','dataType':'1', 'maxLength': 30},//联系方式
'cust_source_name':{'method':'setCust_source_name','name':'客户资料来源类型','dataType':'1', 'maxLength': 30},//客户资料来源类型
'age':{'method':'setAge','name':'年龄','dataType':'4'},//年龄
'potenital_money':{'method':'setPotenital_money','name':'潜在购买力','dataType':'2'},//潜在购买力
'cust_id' : {'method' : 'setCust_id', 'name' : "客户ID", "dataType" : "4"},				//below carlos add
'birthday' : {'method' : 'setBirthday', 'name' : "出生日期", "dataType" : "4"}	,
'sex_name' : {'method' : 'setSex_name', 'name' : '性别', 'dataType' : '1', 'maxLength': 10},
'voc_type_name' : {'method' : 'setVoc_type_name', 'name' : '职业/行业', 'dataType' : '1', 'maxLength': 30},//职业/行业
'h_tel' : {'method' : 'setH_tel', 'name' : '家庭电话', 'dataType' : '1', 'maxLength': 60},		//家庭电话
'o_tel' : {'method' : 'setO_tel', 'name' : '公司电话', 'dataType' : '1', 'maxLength': 60},		//公司电话
'mobile2' : {'method' : 'setMobile2', 'name' : '手机2', 'dataType' : '1', 'maxLength': 60}, 	//手机2
'fax' : {'method' : 'setFax', 'name' : '传真', 'dataType' : '1', 'maxLength': 60},			//传真
'post_address2' : {'method' : 'setPost_address2', 'name' : '邮寄地址2', 'dataType' : '1', 'maxLength': 60},	//地址2
'post_code2' : {'method' : 'setPost_code2', 'name' : '邮政编码2', 'dataType' : '1', 'maxLength': 6}, 	//邮编2
'legal_man' : {'method' : 'setLegal_man', 'name' : '法人姓名', 'dataType' : '1', 'maxLength': 30}, 	//法人姓名

'company_unit' : {'method' : 'setCompany_unit', 'name' : '所在单位', 'dataType' : '1', 'maxLength': 60}, 	//所在单位
'company_depart' : {'method' : 'setCompany_depart', 'name' : '所在部门', 'dataType' : '1', 'maxLength': 30}, 	//所在部门
'company_position' : {'method' : 'setCompany_position', 'name' : '职位', 'dataType' : '1', 'maxLength': 60}, 	//职位

'cust_aum' : {'method' : 'setCust_aum', 'name' : '客户AUM', 'dataType' : '1', 'maxLength': 60},//客户AUM
'inves_experinc' : {'method' : 'setInves_experinc', 'name' : '信托投资经验', 'dataType' : '1', 'maxLength': 60}, 	//信托投资经验
'gov_prov_regional_name' : {'method' : 'setGov_prov_regional_name', 'name' : '省', 'dataType' : '1', 'maxLength': 60},//省
'gov_regional_name' : {'method' : 'setGov_regional_name', 'name' : '市', 'dataType' : '1', 'maxLength': 60},//市
'risk_pref' : {'method' : 'setRisk_pref', 'name' : '风险偏好', 'dataType' : '1', 'maxLength': 60},//风险偏好
'hobby_pref' : {'method' : 'setHobby_pref', 'name' : '兴趣爱好', 'dataType' : '1', 'maxLength': 60},//兴趣爱好
'cust_source_explain' : {'method' : 'setCust_source_explain', 'name' : '客户资料来源名称', 'dataType' : '1', 'maxLength': 60},//客户资料来源名称
'cust_age_group' : {'method' : 'setCust_age_group', 'name' : '年龄段', 'dataType' : '1', 'maxLength': 60}//年龄段
};

var tempArr = {};

function importAction(step){
    showWaitting(1);
    if(importStep==0){
        var fullPath = $('#importFile').val();
        var fileType = fullPath.substring(fullPath.lastIndexOf(".")+1);
        if(fileType=="xls"){
            if($('#headFlag').attr("checked")==true){
                //导入的excel文件含有标题行
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
                //导入的excel文件没有标题行
                var fieldCount = getExcelColsCount(fullPath);
                for(i=0;i<fieldCount;i++){
                    if($('#tbl_field tr:last').attr('class')==null||$('#tbl_field tr:last').attr('class')=='tr1')
    				    trClass = 'tr0';
        			else
        				trClass = 'tr1';
        			/*第 列*/
                    var trinfo="<tr id='tr_"+i+"'><td width='50%'><input type='checkbox' id='field_"+i+"' name='field_"+i+"' value=''><%=LocalUtilis.language("message.no4",clientLocale)%>"+(i+1)+"<%=LocalUtilis.language("message.cols",clientLocale)%> </td><td id='id_"+i+ "' width='50%'></td></tr>";
        			$('#tbl_field').append(trinfo);
        			$("#field_"+i).attr('class','flatcheckbox');
        			$('#tr_'+i).attr('class',trClass).css('cursor','hand').dblclick(function(){selectField(this.id);});
        			$('#field_'+i).click(function(){selectField2(this)});
                }
            }

            $('#promptTitle').html("<%=LocalUtilis.language("message.mapInputField",clientLocale)%> :");//映射输入字段
            $('#btnNext').html("<%=LocalUtilis.language("message.finish",clientLocale)%> ");//完成
            $('#div_file').css("visibility","hidden");
            $('#div_field').css("visibility","visible");
            $('#btnBack').attr("disabled","");

        }else{
            alert("<%=LocalUtilis.language("message.fileError",clientLocale)%> ");//请确定选择了正确的文件，当前只支持EXCEL
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
                alert("<%=LocalUtilis.language("message.noMapfield",clientLocale)%> 。");//没有映射任何字段，请映射后再操作
                return false;
            }
        }else{
            $('#promptTitle').html("<%=LocalUtilis.language("message.selectFile",clientLocale)%> :");//请选择要导入的文件
            $('#btnNext').html('<%=LocalUtilis.language("message.next",clientLocale)%> ');//下一步
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
            //当前//字段已经映射该字段，请重新选择
            alert("<%=LocalUtilis.language("message.current",clientLocale)%>  \""+tempArr[results[0]]+"\"  <%=LocalUtilis.language("message.fieldmapped",clientLocale)%> 。");
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
		    <!--客户信息导入-->
            <td class="page-title"><b><font color="#215dc6"><%=menu_info%>>><%=LocalUtilis.language("message.importCustInfo",clientLocale)%> </b></td>
	    </tr>
	    
    </table>
</div>
<div>
    <div id="div_file">
        <table id="tbl_file" class="table-popup" border="0" width="100%" cellspacing="0" cellpadding="0">
            <tr>
	            <td align="left" id="promptTitle"><%=LocalUtilis.language("message.selectFile",clientLocale)%> :</td><!--请选择要导入的文件-->
	        </tr>
            <tr>
                <td>
                    <input style="width:95%" type="file" name="importFile" id="importFile">
                </td>
            </tr>
            <tr>
                <td>
                    <!--是否含有标题行-->
                    <input type="checkbox" class="flatcheckbox" name="headFlag" id="headFlag" value=""><%=LocalUtilis.language("message.isContHeaderRows",clientLocale)%>
                </td>
            </tr>
        </table>
    </div>
    <div id="div_field" style="height:300;visibility:hidden">
        <div>
            <table class="tablelinecolor" border="0" width="100%" cellspacing="0" cellpadding="0">
                <tr class="trh">
                    <td><%=LocalUtilis.language("message.importFields",clientLocale)%> </td><!--导入字段-->
                    <td><%=LocalUtilis.language("message.mappingField",clientLocale)%> </td><!--映射字段-->
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
                <!--上一步-->
                <button type="button"  id="btnBack" disabled class="xpbutton3" onclick="javascript:importAction(-1)"><%=LocalUtilis.language("message.up",clientLocale)%> </button>&nbsp;&nbsp;&nbsp;
                <!--下一步-->
                <button type="button"  id="btnNext"class="xpbutton3" onclick="javascript:importAction(1)"><%=LocalUtilis.language("message.next",clientLocale)%> </button>&nbsp;&nbsp;&nbsp;
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