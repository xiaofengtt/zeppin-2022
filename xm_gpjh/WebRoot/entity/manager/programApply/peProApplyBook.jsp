<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>提交申报材料</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<link href="/entity/manager/css/admincss.css" rel="stylesheet" type="text/css">
	<link rel="stylesheet" type="text/css" href="/js/extjs/css/ext-all.css" />
		<script type="text/javascript" src="/js/extjs/pub/adapter/ext/ext-base.js"></script>
		<script type="text/javascript" src="/js/extjs/pub/ext-all.js"></script>
		<script type="text/javascript" src="/js/extjs/pub/ext-lang-zh_CN.js"></script>

	
  </head>
  
  <script type="text/javascript">

  Ext.onReady(function(){
  //生成学科下拉列表
			var subjectDataStore = new Ext.data.Store({
				proxy: new Ext.data.HttpProxy({
		            url: '/entity/first/firstPageCombo_getSubject.action'
		        }),
				reader: new Ext.data.JsonReader({
				            root: 'subject'
				        },
				        [{name:'id'}, {name:'name'}]),
		        remoteSort: true
		    });
		    subjectDataStore.setDefaultSort('id', 'asc');
		    subjectDataStore.load();
			
			var subjectCombo = new Ext.form.ComboBox({
				store: subjectDataStore,
				displayField:'name',
				valueField: 'id' ,
				//id:'applyno',
				typeAhead: true,
				mode: 'local',
				triggerAction: 'all',
				emptyText:'请选择申报学科...',
				applyTo: 'combo-box-subject',
				editable: false,
				forceSelection: true,
				selectOnFocus:true
			});
			var peUnitId = document.getElementById('peUnitId').value;
		//生成申报批次下拉列表
			var siteDataStore = new Ext.data.Store({
				proxy: new Ext.data.HttpProxy({
		            url: '/entity/first/firstPageCombo_getApplyno.action?unitId='+peUnitId
		        }),
				reader: new Ext.data.JsonReader({
				            root: 'applyno'
				        },
				        [{name:'id'}, {name:'name'}]),
		        remoteSort: true
		    });
		    siteDataStore.setDefaultSort('id', 'asc');
		    siteDataStore.load();
			
			var siteCombo = new Ext.form.ComboBox({
				store: siteDataStore,
				displayField:'name',
				valueField: 'id' ,
				//id:'applyno',
				typeAhead: true,
				mode: 'local',
				triggerAction: 'all',
				emptyText:'请选择培训项目...',
				applyTo: 'combo-box-applyno',
				editable: false,
				forceSelection: true,
				selectOnFocus:true
			});
 });
 
 function checkSubject(){
 	var flag=true;
 	peSubjectInfo.innerHTML='';
 	peProApplynoInfo.innerHTML='';
 	uploadInfo.innerHTML='';
 	upload2Info.innerHTML='';
 	if(document.getElementById('combo-box-subject').value=='请选择申报学科...'){
		flag=false;
		peSubjectInfo.innerHTML='<div class="checkError">&nbsp;&nbsp;申报学科不能为空！</div>';
	}
	if(document.getElementById('combo-box-applyno').value=='请选择培训项目...'){
		flag=false;
		peProApplynoInfo.innerHTML='<div class="checkError">&nbsp;&nbsp;培训项目不能为空！</div>';
	}
	if(document.getElementById('combo-box-applyno').value=='当前暂没有可选的培训项目'){
		flag=false;
		peProApplynoInfo.innerHTML='<div class="checkError">&nbsp;&nbsp;培训项目错误！</div>';
	}
	if(document.getElementById('upload').value=='' || document.getElementById('upload').value.trim() == ''){
		flag=false;
		uploadInfo.innerHTML='<span class="checkError">&nbsp;&nbsp;申报书不能为空！</span>';
	}else{
		var uploadstr = document.getElementById('upload').value;
		var endstr = uploadstr.substring(uploadstr.length-4,uploadstr.length).toLowerCase();
		if(endstr != '.doc'&& endstr!='.pdf'){
			uploadInfo.innerHTML='<span class="checkError">&nbsp;&nbsp;请上传doc或pdf格式的申报书！</span>';
			flag=false;
		}
	}
	if(document.getElementById('upload2').value=='' || document.getElementById('upload2').value.trim() == ''){
		flag=false;
		upload2Info.innerHTML='<span class="checkError">&nbsp;&nbsp;实施方案不能为空！</span>';
	}else{
		var uploadstr = document.getElementById('upload2').value;
		var endstr = uploadstr.substring(uploadstr.length-4,uploadstr.length).toLowerCase();
		if(endstr != '.doc'&& endstr!='.pdf'){
			upload2Info.innerHTML='<span class="checkError">&nbsp;&nbsp;请上传doc或pdf格式的实施方案！</span>';
			flag=false;
		}
	}
 	return flag;
 }
</script>
  
  <body topmargin="0" leftmargin="0"  bgcolor="#FAFAFA">


<div id="main_content">
    <div class="content_title">提交申报材料</div>
    <div class="cntent_k">
   	  <div class="k_cc">
<form id='teacherInfo' action="/entity/programApply/peProApplyBook_saveBookInfo.action" method="post" enctype="multipart/form-data" onsubmit="return checkSubject();">
<table width="654" border="0" align="center" cellpadding="0" cellspacing="0">
                          
                          <tr>
                            <td colspan="3" height="26" align="center" valign="middle" ><!--<div class="" align="center"><s:property value="getMsg()" escape="false"/></div>--></td>
                          </tr>
						 
                                  <tr valign="middle"> 
                                  <td width="140" height="30" align="left" class="postFormBox" style="padding-left:50px"><span class="name">单&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;位：</span></td>
                                  <td class="postFormBox" style="padding-left:10px"><input type="text" id="peUnit.name" name="peUnit.name" value="<s:property value="peUnit.name"/>" readonly="readonly"/>  </td>
                                  <td class="postFormBox" style="padding-left:10px" >&nbsp;</td>
                                </tr>
                                 <tr valign="middle">
                       			 <td width="140" height="30" align="left" class="postFormBox" style="padding-left:50px"><span class="name">培&nbsp;训&nbsp;项&nbsp;目*：</span></td>
                       				<td class="postFormBox" style="padding-left:10px"><input type="text" id="combo-box-applyno" name="peProApplyno.name" size="52"  />                        </td>
                       			 	<td class="postFormBox" style="padding-left:10px;color:#ff0000" id="peProApplynoInfo" >&nbsp;</td>
                     			 </tr>
                                  <tr valign="middle">
                       			 <td width="140" height="30" align="left" class="postFormBox" style="padding-left:50px"><span class="name">申&nbsp;报&nbsp;学&nbsp;科*：</span></td>
                       				<td class="postFormBox" style="padding-left:10px"><input type="text" id="combo-box-subject" name="peSubject.name" size="52"  />                        </td>
                       			 	<td class="postFormBox" style="padding-left:10px;color:#ff0000" id="peSubjectInfo">&nbsp;</td>
                     			 </tr>
                                <tr valign="middle"> 
                                  <td width="140" height="30" align="left" class="postFormBox" style="padding-left:50px"><span class="name">申&nbsp;&nbsp;&nbsp;&nbsp;报&nbsp;&nbsp;&nbsp;书*：</span></td>
                                  <td class="postFormBox" style="padding-left:10px" colspan="2"><input type="file" id='upload' name="upload" />  
                                  <span class="postFormBox" style="padding-left:10px;color:#ff0000" id="uploadInfo"> 限doc和pdf格式！ </span></td>
                                </tr>
                                
                                <tr valign="middle"> 
                                  <td width="140" height="30" align="left" class="postFormBox" style="padding-left:50px"><span class="name">实&nbsp;施&nbsp;方&nbsp;案*：</span></td>
                                  <td class="postFormBox" style="padding-left:10px" colspan="2"><input type="file" id='upload2' name="upload2" /> 
                                  <span class="postFormBox" style="padding-left:10px;color:#ff0000" id="upload2Info"> 限doc和pdf格式！ </span></td>
                                </tr>
                           <tr>
                            <td height="50" align="center" colspan="3">
                              <input type="submit" value="确定"/></td>
						  </tr>
						    <tr>
                            <td  height="10"> </td>
                          </tr>
        </table>
        <s:if test="proApplyList.size()>0">
 		 <table width="900" border="0" align="center" cellpadding="0" cellspacing="0">
						 
                                <tr valign="middle"> 
                                  <td  height="30" width="29%" align="left" class="postFormBox" style="padding-left:12px;font-weight:bold">培训项目</td>
                                  <td  width="12%" class="postFormBox" style="padding-left:12px;font-weight:bold">申报学科  </td>
                                  <td width="12%" class="postFormBox" style="padding-left:12px;font-weight:bold"> 提交时间 </td>
                                  <td width="10%" class="postFormBox" style="padding-left:12px;font-weight:bold"> 省厅审核结果 </td>
                                  <td width="15%" class="postFormBox" style="padding-left:12px;font-weight:bold"> 项目执行办审核结果 </td>
                                  <td  width="9%"class="postFormBox" style="padding-left:12px;font-weight:bold">项目申报书</td>
                                  <td  width="8%" class="postFormBox" style="padding-left:12px;font-weight:bold">实施方案</td>
                                  <td  width="5%" class="postFormBox" style="padding-left:12px;font-weight:bold">操作</td>
                                </tr>
                             <s:iterator value="proApplyList" id="apply">
                                <tr valign="middle"> 
                                  <td  align="left"   class="postFormBox" style="padding-left:10px"><s:property value="#apply.peProApplyno.name"></s:property></td>
                                  <td class="postFormBox"  style="padding-left:10px"> <s:property value="#apply.peSubject.name"></s:property> </td>
                                  <td  class="postFormBox" style="padding-left:10px"><s:date name="#apply.declareDate" format="yyyy-MM-dd HH:mm"/></td>
                                  <td align="center" class="postFormBox" style="padding-left:10px"> <s:property value="#apply.enumConstByFkCheckResultProvince.name"></s:property> </td>
                                  <td align="center" class="postFormBox" style="padding-left:10px"> <s:property value="#apply.enumConstByFkCheckNational.name"></s:property> </td>
                                  <td class="postFormBox" style="padding-left:10px"><a href="<s:property value="#apply.declaration"/>" target="blank">下载查看</a></td>
                                  <td class="postFormBox" style="padding-left:10px"><a href="<s:property value="#apply.scheme"/>" target="blank">下载查看</a></td>
                                  <td class="postFormBox" style="padding-left:10px"><a href="/entity/programApply/peProApplyBook_delPeProApplyBook.action?ids=<s:property value="#apply.id"></s:property>" onClick="return confirm('您确定要删除吗？')">删除</a></td>
                                </tr>
                             </s:iterator>
        </table>
        </s:if>
        <input type="hidden" id="peUnitId" name="peUnitId" value="<s:property value="peUnit.id"/>" />
</form>
	  </div>
    </div>
</div>
<div class="clear"></div>
<s:if test="msg!=null">
<script>
alert('<s:property value="msg"/>');
  <s:if test="succ">
		if(top.frames.length==0){
			top.location.href="/entity/manager/index.jsp";
		}
  </s:if>
</script>
</s:if>
</body>
</html>
