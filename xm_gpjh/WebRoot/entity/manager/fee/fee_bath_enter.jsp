<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>tableType_2</title>
<link href="<%=request.getContextPath()%>/entity/manager/css/admincss.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/js/extjs/css/ext-all.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/js/extjs/pub/adapter/ext/ext-base.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/extjs/pub/ext-all.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/extjs/pub/ext-lang-zh_CN.js"></script>
<script type="text/javascript">

<!--
Ext.onReady(function(){
	var feeTypeDataStore = new Ext.data.Store({
				proxy: new Ext.data.HttpProxy({
		            url: "/test/myList.action?sql=select id,name from enum_const where namespace='FlagFeeType' and code in ('0','1')"
		        }),
		
				reader: new Ext.data.JsonReader({
				            root: 'models'
				        },
				        [{name:'id'}, {name:'name'}]),
		        remoteSort: true
		    });
		    feeTypeDataStore.setDefaultSort('id', 'asc');
		    feeTypeDataStore.load();
			
			var feeTypeCombo =new Ext.form.ComboBox({
				id:'feeType',
				name:'bean.enumConstByFlagFeeType.id',
				store: feeTypeDataStore,
				displayField:'name',
				valueField: 'id' ,
				typeAhead: true,
				mode: 'local',
				triggerAction: 'all',
				emptyText:'请选择交费类型...',
				applyTo: 'combo-box-feeType',
				editable: true,
				forceSelection: true,
				selectOnFocus:true
			});
			Ext.get("zlb_content_start").addListener("mouseover",function(){
				if(feeTypeCombo.getValue()!=feeTypeCombo.getRawValue()){
					document.getElementById("combo-box-feeType-id").value=feeTypeCombo.getValue();
				}
			});
	});
//--!> 
</script>
<script>
	//文件类型(*.xls)
	function FileTypeCheck(){

  var box =document.getElementById('combo-box-feeType');
  if(box.value=='' || box.value =='请选择交费类型...'){
  	alert('请选择交费类型！');
  	return false;
  }	
		var obj =document.getElementById('src');
		if(obj.value==null || obj.value ==''){
			alert('文件格式不正确！');
			this.focus()
			return false;
  		}
		var length = obj.value.length;
		var charindex = obj.value.lastIndexOf(".");
		var ExtentName = obj.value.substring(charindex,charindex+4);
		if(!(ExtentName == ".xls" )){
			alert('文件格式不正确！');
			this.focus()
			return false;
		}
		return true;
	}
</script>
</head>
<body leftmargin="0" topmargin="0" class="scllbar">
<s:form name = "batch" action="entity/fee/prFeeDetailIn_batchexe.action" method="POST"  enctype="multipart/form-data" onsubmit ="return FileTypeCheck();">
<table width="80%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr> 
    <td><div class="content_title" id="zlb_title_start">批量导入交费明细</div></td>
  </tr>
    
            
  <tr> 
    <td valign="top" class="pageBodyBorder_zlb" align="center"> 
        <div class="cntent_k" id="zlb_content_start">
          <p>&nbsp;</p>
          <table width="68%" border="0" cellpadding="0" cellspacing="0">
             <tr valign="bottom" class="postFormBox"> 
              <td valign="bottom"><span class="name">交费类型*:</span></td>
              <td valign="bottom"><input type="text" name="bean.enumConstByFlagFeeType.name" value="<s:property value='bean.enumConstByFlagFeeType.name'/>" size="25" id="combo-box-feeType"/> <input type="hidden" name="bean.enumConstByFlagFeeType.id"  value="<s:property value='bean.enumConstByFlagFeeType.id'/>" id="combo-box-feeType-id"/> </td>
            </tr>
            <tr valign="bottom" class="postFormBox"> 
              <td width="23%"  valign="bottom"><span class="name">下载标准格式:</span></td>
              <td width="77%"> <span class="link" help="下载Excel格式的模版表格"><img src='<%=request.getContextPath()%>/entity/manager/images/buttons/excel.png' alt='Print' width="20" height="20" title='Print'>&nbsp;<a href='<%=request.getContextPath()%>/entity/manager/fee/fee_batch_input.xls' target=_blank>Excel报表</a></span></td>
            </tr>
            <tr valign="bottom" class="postFormBox"> 
              <td valign="bottom"><span class="name">上载交费明细:</span></td>
              <td valign="bottom"><input type=file name="_upload" id="src"/></td>
            </tr>
                      
            
            <tr class="postFormBox">
              <td ></td>
              <td><input type=submit value = "提交" /></td>
            </tr>
         </table>
      </div>

    </td>
  </tr>
 <tr> 
    <td><div class="content_title" >使用说明</div></td>
  </tr>  
  <tr>
    <td >
        <div class="cntent_k">
          1、如果是预交费，在学生号码栏填写学生的准考证号，其他填写学生的学号即可，在姓名栏填写上学生的姓名。
          <br>2、交费时间栏填写时间，时间格式为 2008-01-01，其他格式会出错。
          <br>3、交费金额栏写所要交费的金额，单位元，表格中只填写数字即可。
          <br>4、填写好之后，选择交费类型提交。
         </div>
     </td>
  </tr>
</table>
</s:form>
</body>
</html>