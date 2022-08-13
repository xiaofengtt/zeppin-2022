<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>tableType_2</title>
<% String path = request.getContextPath();%>
<link href="<%=path%>/entity/manager/css/admincss.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" href="<%=path%>/js/extjs/css/ext-all.css" />
<script type="text/javascript" src="<%=path%>/js/extjs/pub/adapter/ext/ext-base.js"></script>
<script type="text/javascript" src="<%=path%>/js/extjs/pub/ext-all.js"></script>
<script type="text/javascript" src="<%=path%>/js/extjs/pub/ext-lang-zh_CN.js"></script>
<script >
	 function check()
  {

  var obj =document.getElementById('combo-box-feeType');
  if(obj.value=='' || obj.value =='请选择减免类型...'){
  	alert('请选择减免类型！');
  	return false;
  }
  var regNo =document.getElementById('regNo');
  if(regNo.value==null || regNo.value ==''){
  	alert('请输入学号！');
  	return false;
  }
  var name =document.getElementById('name');
  if(name.value==null || name.value ==''){
  	alert('请输入姓名！');
  	return false;
  }  
    var fee =document.getElementById('fee');
  if(fee.value==null || fee.value ==''){
  	alert('请输入金额！');
  	return false;
  }
  				var myreg=/^\d{1,8}(\.\d{1,2})?$/;
				if(!myreg.test(fee.value))
				{
					alert('金额输入格式：1到8位整数 0到2位小数');
					return false;
				}
				
  	return true;
  }
</script >
<script type="text/javascript">

<!--
Ext.onReady(function(){
	var feeTypeDataStore = new Ext.data.Store({
				proxy: new Ext.data.HttpProxy({
		            url: "/test/myList.action?sql=select id,name from enum_const where namespace='FlagFeeType' and code in ('8','9')"
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
				emptyText:'请选择减免类型...',
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
			var inputDate = new Ext.form.DateField({
			        fieldLabel: '交费日期',           
			        name: 'bean.inputDate',
			        id:'inputDate',
			        format:'Y-m-d',
			        allowBlank:false, 
			        readOnly:true,
			        anchor: '29%'
			    }); 
			var date;
			<s:if test='bean.inputDate != null'>
				date='<s:date name="bean.inputDate" format="yyyy-MM-dd" />' ;
			</s:if><s:else>
				date=(new Date).format('Y-m-d');
			</s:else>    
			inputDate.on('render',function showvalue(p,_record,_options){
					inputDate.setValue(date);
					inputDate.setRawValue(date);
			},inputDate);
			
			inputDate.render('showinputDate'); 
	});
//--!> 
</script>
</head>
<body leftmargin="0" topmargin="0" class="scllbar">
<s:form name = "feeform" action="/entity/fee/prFeeDetailReduce_addexe.action" method="get" onsubmit="return check();">
<table width="80%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr> 
    <td><div class="content_title" id="zlb_title_start">单个添加学生减免信息</div></td>
  </tr>
    
            
  <tr> 
    <td valign="top" class="pageBodyBorder_zlb" align="center"> 
        <div class="cntent_k" id="zlb_content_start">
          <p><font color="red"><s:property value='msg'/></font></p>
          <table width="68%" border="0" cellpadding="0" cellspacing="0">
                   
            <tr valign="bottom" class="postFormBox"> 
              <td valign="bottom"><span class="name">减免类型*</span></td>
              <td valign="bottom"><input type="text" name="bean.enumConstByFlagFeeType.name" value="<s:property value='bean.enumConstByFlagFeeType.name'/>" size="25" id="combo-box-feeType"/> <input type="hidden" name="bean.enumConstByFlagFeeType.id"  value="<s:property value='bean.enumConstByFlagFeeType.id'/>" id="combo-box-feeType-id"/> </td>
            </tr>
            
            <tr valign="bottom" class="postFormBox"> 
              <td width="23%"  valign="bottom"><span class="name" id="studentNo">学&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;号*</span></td>
              <td width="77%"><input type="text" id="regNo" name="bean.peStudent.regNo" size="25" value="<s:property value='bean.peStudent.regNo'></s:property>"/></td>
            </tr>
            <tr valign="bottom" class="postFormBox"> 
              <td width="23%"  valign="bottom"><span class="name" id="studentNo">姓&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名*</span></td>
              <td width="77%"><input type="text" id="name" name="bean.peStudent.trueName" size="25" value="<s:property value='bean.peStudent.trueName'></s:property>"/></td>
            </tr>
            <tr valign="bottom" class="postFormBox"> 
              <td valign="bottom"><span class="name">减免金额*</span></td>
              <td valign="bottom"><input type="text" name="bean.feeAmount" id="fee" size="25"  value="<s:property value='bean.feeAmount'></s:property>"/></td>
            </tr>
            <tr valign="bottom" class="postFormBox"> 
              <td valign="bottom"><span class="name">添加日期*</span></td>
              <td valign="bottom"><div align="left" class="postFormBox" id = "showinputDate"></div></td>
            </tr>
            <tr valign="bottom" class="postFormBox"> 
              <td valign="bottom"><span class="name">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注&nbsp;&nbsp;</span></td>
              <td valign="bottom"><textarea name="bean.note" rows="4" cols="25"><s:property value='bean.note'></s:property></textarea></td>
            </tr>     
            
            <tr class="postFormBox">
              <td ></td>
              <td><div id="onsubmit">
              <s:if test='msg.indexOf("成功")>=0'><input type="button" value ="返回" onclick="javascript:window.navigate('/entity/fee/prFeeDetailReduce.action');" /></s:if>
              <s:else><input type="submit" value = "提交" /></s:else>
              </div>
              
              </td>
            </tr>
         </table>
      </div>

    </td>
  </tr>
 
</table>
</s:form>
</body>
</html>