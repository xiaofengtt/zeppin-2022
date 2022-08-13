<%@ page language="java"  pageEncoding="UTF-8"%>
<%@page import="com.whaty.platform.sso.web.servlet.UserSession"%>
<%@page import="com.whaty.platform.sso.web.action.SsoConstant"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>分配任务</title>
<link href="/entity/manager/css/admincss.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" type="text/css" media="all" href="entity/manager/js/calendar/calendar-win2k-cold-1.css" title="win2k-cold-1">
<script language="javascript" src="/entity/manager/js/check.js"></script>		
<script src="<%=request.getContextPath()%>/FCKeditor/fckeditor.js"></script>
	<link rel="stylesheet" type="text/css" href="/js/extjs/css/ext-all.css" />
	<link rel="stylesheet" type="text/css" href="/js/extjs/examples/Datetime/datetime.css"></link>
		<script type="text/javascript" src="/js/extjs/pub/adapter/ext/ext-base.js"></script>
		<script type="text/javascript" src="/js/extjs/pub/ext-all.js"></script>
		<script type="text/javascript" src="/js/extjs/pub/ext-lang-zh_CN.js"></script>
<script language="javascript" src="/entity/manager/info/js/information.js"></script>
<script>
Ext.onReady(function(){
		var datefield = new Ext.form.DateField({   
        			 id: 'diliveryDate',                      
       				  format: 'Y-m-d',                         
        			 maxValue: '2100-01-01',                  
        			 minValue: '1900-01-01',                  
       				// disabledDays: [0, 6],                    
       				// disabledDaysText: '禁止选择该日期',       
       				 fieldLabel: '选择日期*',                  
       				 width:180,                                
       				 showToday:true ,                          
        			 allowBlank:false, 
        			 applyTo: 'startDate',
          			blankText:'不能为空，请填写' 
      })  ; 
      		var datefield2 = new Ext.form.DateField({   
        			 id: 'diliveryDate',                      
       				  format: 'Y-m-d',                         
        			 maxValue: '2100-01-01',                  
        			 minValue: '1900-01-01',                  
       				// disabledDays: [0, 6],                    
       				// disabledDaysText: '禁止选择该日期',       
       				 fieldLabel: '选择日期*',                  
       				 width:180,                                
       				 showToday:true ,                          
        			 allowBlank:false, 
        			 applyTo: 'finishDate',
          			blankText:'不能为空，请填写' 
      })  ; 
      var objname = new Ext.form.TextField({
   			//	 width: 300,
  				  name: 'TextBox',
  				applyTo: 'jobName',
   			 allowBlank: true
		});
		
  });
	function doCommit() {		
		var content;
		if(FCKeditor_IsCompatibleBrowser()){
			var oEditor = FCKeditorAPI.GetInstance("jobNote"); 
			content=oEditor.GetXHTML(); 
		}else{
			alert("表单正在下载");
			return false;
		}
		
		if(isNull(document.getElementById("jobName").value)){ 
		}else{
			alert("请填任务名称!");
			document.getElementById("jobName").focus();

			return false;
		}
		
		if(document.getElementById("startDate").value=="") {
				alert("开始时间不能为空!");
				return false;
			}
			
		if(document.getElementById("finishDate").value=="") {
				alert("结束时间不能为空!");
				return false;
			}
			
		if(!checkRight(document.getElementById("startDate").value,document.getElementById("finishDate").value)) {
				alert("开始结束时间不合理！");
				return false;
			}
	
		var oEditor = FCKeditorAPI.GetInstance('jobNote') ;
    	var acontent=oEditor.GetXHTML();
       	if(acontent.length > 2000)
			{
				alert("任务备注内容过多，请重新填写!");
				return false;
			}
	var plen =document.getElementsByName("person");
		var n=0;
		for(k=plen.length-2;k<plen.length;k++){
			if(plen[k].checked){
			 n++;
			}
		}
		var m = theCheckedBoxes();
		if(n==0&&m==0){
			alert("请选择任务人员范围！");
			return false;
		}
		if(content.length <1){
			alert("任务内容为空!");
			return false;
		}
		
		var s=document.getElementById('information');
		s.submit();
	}
	function checkRight(startDateStr, finishDateStr) {
			var startArr = startDateStr.split("-");
			var finishArr = finishDateStr.split("-");
			var startDate = new Date(startArr[0],startArr[1],startArr[2]);
			var finishDate = new Date(finishArr[0],finishArr[1],finishArr[2]);
			if(startDate > finishDate)
				return false;
			return true;
		}
	
	function onload_edit(){
		
		var str = "<s:property value='peJob.scopeString'/>";
		onloadObject(str);
		var bean_id = "<s:property value='peJob.id'/>";
		if(bean_id==null||bean_id.length<=0){
			document.getElementById('zlb_title_start').innerHTML='分配任务';
			//window.document.info_news_add.action='/entity/information/peBulletin_addNotice.action';
		}else{
			document.getElementById('zlb_title_start').innerHTML='修改任务';
			//window.document.info_news_add.action='/entity/information/peBulletin_editexe.action';
			window.document.getElementById('hiddenBean').innerHTML="<input type='hidden' name='bean.id' value='"+bean_id+"' />";
			window.document.getElementById('back').innerHTML="<a href='#' title='返回' class='button'><span unselectable='on' class='norm'  style='width:46px;height:15px;padding-top:3px' onclick=window.navigate('/entity/information/peJob.action') onMouseOver=\"className='over'\" onMouseOut=\"className='norm'\" onMouseDown=\"className='push'\" onMouseUp=\"className='over'\"><span class='text'>返回</span></span></a>";
		}
	}
</script>
</head>
<body leftmargin="0" topmargin="0" class="scllbar" onLoad="onload_edit();">
  <form name='information' method='post' id='job' action="/entity/information/peJob_addJob.action" class='nomargin'>
<table width="86%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr> 
    <td><div class="content_title" id="zlb_title_start">分配任务</div></td>
  </tr>
  <tr> 
    <td valign="top" class="pageBodyBorder_zlb"> 
        <div class="cntent_k" id="zlb_content_start">
          <table width="85%" border="0" cellpadding="0" cellspacing="0">
            <tr valign="bottom" class="postFormBox"> 
              <td valign="bottom" width="91" nowrap><span class="name">任务名称:*&nbsp;&nbsp;&nbsp;&nbsp;</span></td>
            <td colspan="5"> <input name="jobName"   value="<s:property value='peJob.name'/>" type="text" class="selfScale"  size="50" maxlength='50'>            </tr>
                        
            <tr valign="bottom" class="postFormBox"> 
              <td valign="bottom"><span class="name">任务重要性:*</span></td>
              <td colspan="5"> <s:iterator value="jobPriority" id="priority">
           <input type="radio" id="<s:property value="#priority[0]"/>" name="enum1" value="<s:property value="#priority[0]"/>" <s:if test="peJob.enumConstByFkJobPriority.id==#priority[0]">checked</s:if><s:elseif test="peJob==null&&#priority[0]=='4028808c2a8f7627012a8f8758cb0004'">checked </s:elseif>   >  <s:property value="#priority[1]"/>:
            </s:iterator>            </tr>
            <tr valign="bottom" class="postFormBox">
									<td valign="bottom">
										<span class="name">开始时间:*&nbsp;&nbsp;&nbsp;&nbsp;</span>									</td>
									<td colspan="5">
										<input type=text name="startDate" id="startDate" value="<s:date name="peJob.startDate" format="yyyy-MM-dd" />" class=selfScale >
											</td>
								</tr>
			<tr valign="bottom" class="postFormBox">
									<td valign="bottom">
										<span class="name">结束时间:*&nbsp;&nbsp;&nbsp;&nbsp;</span>								</td>
									<td colspan="5">
										<input type="text" name="finishDate" id="finishDate" value="<s:date name="peJob.finishDate" format="yyyy-MM-dd" />"  class=selfScale >
										
								</td>
								</tr>
            
			<tr valign="bottom" class="postFormBox"> 
              <td  rowspan="2" valign="middle"><span class="name">人员范围:*&nbsp;&nbsp;&nbsp;&nbsp;</span> </td>
              <td width="18%" valign="top">
			  示范性承办单位
			    <input type="checkbox" value="cbunit_" id="CBS" name="person" onClick="clickCB()">
		       <input id="CB_button" type="button" name="cb" onClick="showDiv('cnt1')" value="请选择" style="display:none;position:relative;font-size:12px;height:23;background-color:#ece9d8;border-width:1;">
              <div id="cnt1" style="width:350px; height:180px; position:absolute; border:1px solid #006699; background-color:#EFF3F5; padding=6px; overflow:auto;display:none">
              
                <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
                <tr>
                  <td height="22"><input id="cb_id_all" type="checkbox" name="cb_all" value="" onclick=listSelectCB();>全选</td>
              </tr>
              	<s:iterator value="cbScop"  id="cb_">
                	<tr>
                    <td height="22"><input id="cb_id_check" type="checkbox" name="cb_id" value="<s:property value="#cb_[0]"/>"><s:property value="#cb_[1]"/></td>
                    </tr>
                </s:iterator>
                <tr><td><input type='button' onClick="showDiv('cnt1')" value="确定" style="font-size:12px;height:23;background-color:#ece9d8;border-width:1;"></td></tr>
                </table>
              </div>	  		  </td>
              <td width="18%" valign="top">
			  中西部承办单位
              <input type="checkbox" value="zxbunit_" id="ZXB" name="person" onClick="clickZXB()">
			   <input id="zxb_button" type="button" name="zxb" onClick="showDiv('zxbDiv')" value="请选择" style="display:none;position:relative;font-size:12px;height:23;background-color:#ece9d8;border-width:1;">
              <div id="zxbDiv" style="width:350px; height:180px; position:absolute; border:1px solid #006699; background-color:#EFF3F5; padding=6px; overflow:auto;display:none">
              
                <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
                <tr>
                  <td height="22"><input id="zxb_id_all" type="checkbox" name="zxb_all" value="" onclick=listSelectZXB();>全选</td>
              </tr>
              	<s:iterator value="zxbScop"  id="zxb_">
                	<tr>
                    <td height="22"><input id="zxb_id_check" type="checkbox" name="zxb_id" value="<s:property value="#zxb_[0]"/>"><s:property value="#zxb_[1]"/></td>
                    </tr>
                </s:iterator>
                <tr><td><input type='button' onClick="showDiv('zxbDiv')" value="确定" style="font-size:12px;height:23;background-color:#ece9d8;border-width:1;"></td></tr>
                </table>
              </div>			  </td>
              <td width="16%" valign="top">中西部师范处
                <input type="checkbox" value="zxbst_" id="ZXSTS" name="person" onClick="clickST()">
			   <input id="st_button" type="button" name="st" onClick="showDiv('cnt2')" value="请选择" style="display:none;position:relative;font-size:12px;height:23;background-color:#ece9d8;border-width:1;">
              <div id="cnt2" style="width:350px; height:180px; position:absolute; border:1px solid #006699; background-color:#EFF3F5; padding=6px; overflow:auto;display:none">
              
                <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
                <tr>
                  <td height="22"><input id="st_id_all" type="checkbox" name="st_all" value="" onclick=listSelectST();>全选</td>
              </tr>
              	<s:iterator value="stScop"  id="st_">
                	<tr>
                    <td height="22"><input id="st_id_check" type="checkbox" name="st_id" value="<s:property value="#st_[0]"/>"><s:property value="#st_[1]"/></td>
                    </tr>
                </s:iterator>
                <tr><td><input type='button' onClick="showDiv('cnt2')" value="确定" style="font-size:12px;height:23;background-color:#ece9d8;border-width:1;"></td></tr>
                </table>
              </div> </td>
              <td width="15%" valign="top">其他师范处
                <input type="checkbox" value="qtst_" id="QTSTS" name="person" onClick="clickST2()">
			   <input id="st2_button" type="button" name="st2" onClick="showDiv('cnt3')" value="请选择" style="display:none;position:relative;font-size:12px;height:23;background-color:#ece9d8;border-width:1;">
              <div id="cnt3" style="width:350px; height:180px; position:absolute; border:1px solid #006699; background-color:#EFF3F5; padding=6px; overflow:auto;display:none">
              
                <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
                <tr>
                  <td height="22"><input id="st2_id_all" type="checkbox" name="st2_all" value="" onclick=listSelectST2();>全选</td>
              </tr>
              	<s:iterator value="qtstScop"  id="qtst_">
                	<tr>
                    <td height="22"><input id="st2_id_check" type="checkbox" name="st2_id" value="<s:property value="#qtst_[0]"/>"><s:property value="#qtst_[1]"/></td>
                    </tr>
                </s:iterator>
                <tr><td><input type='button' onClick="showDiv('cnt3')" value="确定" style="font-size:12px;height:23;background-color:#ece9d8;border-width:1;"></td></tr>
                </table>
              </div>				  </td>
              <td width="33%" valign="top">
			  其他
                <input type="checkbox" value="other_" id="OTHER" name="person" onClick="clickOther()">
			   <input id="other_button" type="button" name="other" onClick="showDiv('otherDiv')" value="请选择" style="display:none;position:relative;font-size:12px;height:23;background-color:#ece9d8;border-width:1;">
              <div id="otherDiv" style="width:350px; height:180px; position:absolute; border:1px solid #006699; background-color:#EFF3F5; padding=6px; overflow:auto;display:none">
              
                <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
                <tr>
                  <td height="22"><input id="other_id_all" type="checkbox" name="other_all" value="" onclick=listSelectOther();>全选</td>
              </tr>
              	<s:iterator value="otherScop"  id="other_">
                	<tr>
                    <td height="22"><input id="other_id_check" type="checkbox" name="other_id" value="<s:property value="#other_[0]"/>"><s:property value="#other_[1]"/></td>
                    </tr>
                </s:iterator>
                <tr><td><input type='button' onClick="showDiv('otherDiv')" value="确定" style="font-size:12px;height:23;background-color:#ece9d8;border-width:1;"></td></tr>
                </table>
              </div>
			  </td>
            </tr>
			<tr valign="bottom" class="postFormBox">
			  <!--  td valign="top">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;评审专家<input type="checkbox" value="valueExpert"  id="PSS" name="person"></td-->
		      <td valign="top">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;项目执行办<input type="checkbox" value="xmzxb_" id="XMS" name="person" ></td>
			  <td valign="top">师范司项目办 <input type="checkbox" value="sfsxmb_" id="SFS" name="person" ></td>
			  <td valign="top">&nbsp;
			  子项目办
                <input type="checkbox" value="jspx_" id="JSPX" name="person" onClick="clickJSPX()">
			   <input id="jiaoshi_button" type="button" name="jiaoshi" onClick="showDiv('jsdiv')" value="请选择" style="display:none;position:relative;font-size:12px;height:23;background-color:#ece9d8;border-width:1;">
              <div id="jsdiv" style="width:350px; height:180px; position:absolute; border:1px solid #006699; background-color:#EFF3F5; padding=6px; overflow:auto;display:none">
              
                <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
                <tr>
                  <td height="22"><input id="jiaoshi_id_all" type="checkbox" name="jiaoshi_all" value="" onclick=listSelectJSPX();>全选</td>
              </tr>
              	<s:iterator value="jiaoshiScop"  id="jiaospx_">
                	<tr>
                    <td height="22"><input id="jiaoshi_id_check" type="checkbox" name="jiaoshi_id" value="<s:property value="#jiaospx_[0]"/>"><s:property value="#jiaospx_[1]"/></td>
                    </tr>
                </s:iterator>
                <tr><td><input type='button' onClick="showDiv('jsdiv')" value="确定" style="font-size:12px;height:23;background-color:#ece9d8;border-width:1;"></td></tr>
                </table>
              </div>	
			  </td>
			  <td valign="top">&nbsp;</td>
			  <td valign="top">&nbsp;</td>
			</tr>
		   
             <tr valign="bottom" class="postFormBox"> 
              <td valign="top"><span class="name">任务备注:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span></td>
              <td colspan="5"> 
              <textarea class="smallarea"  name="jobNote" cols="70" rows="12" id="bean.note"><s:property value='peJob.note'/></textarea>			  </td>
            </tr>
  <tr valign="bottom" class="postFormBox"> 
	<td  valign="bottom" colspan="6">
		<span class="name">是否给联系人发邮件：</span>
	&nbsp;&nbsp;&nbsp;&nbsp;
		<select name="send" id="send" class="input6303">
			<option value="0" selected="selected">
				否			</option>
			<option value="1">
				是			</option>
		</select> 
&nbsp;&nbsp;&nbsp;	</td>
</tr>
          </table> 
      </div>
   </td>
  </tr>
  <tr> 
    <td align="center" id="pageBottomBorder_zlb"><div class="content_bottom"> <table width="98%" height="100%" border="0" cellpadding="0" cellspacing="0"><tr>
       <td  align="center" valign="middle">
       	<a href="#" title="提交" class="button">
       		<span unselectable="on" class="norm"  style="width:46px;height:15px;padding-top:3px" onClick="doCommit()" onMouseOver="className='over'" onMouseOut="className='norm'" onMouseDown="className='push'" onMouseUp="className='over'">
       			<span class="text">提交</span>       			
       		</span>
       	</a>
       	</td>
       <td  align="center" id="back" valign="middle">
       	</td>            
       	</tr>
      </table></td>
  </tr>
</table>
<div id="hiddenBean"></div>
</form>


<script type="text/javascript"> 
var oFCKeditor = new FCKeditor( 'jobNote' ) ; 
oFCKeditor.Height = 300 ; 
oFCKeditor.Width  = 700 ; 

oFCKeditor.Config['ImageBrowserURL']=oFCKeditor.BasePath+'editor/filemanager/browser/default/browser.html?Type=Image&Connector=connectors/jsp/connector';
oFCKeditor.Config['ImageUploadURL']=oFCKeditor.BasePath+'editor/filemanager/upload/whatyuploader?Type=Image';

oFCKeditor.Config['LinkBrowserURL']=oFCKeditor.BasePath+'editor/filemanager/browser/default/browser.html?Connector=connectors/jsp/connector';
oFCKeditor.Config['LinkUploadURL']=oFCKeditor.BasePath+'editor/filemanager/upload/whatyuploader?Type=File';

oFCKeditor.Config['FlashBrowserURL']=oFCKeditor.BasePath+'editor/filemanager/browser/default/browser.html?Type=Flash&Connector=connectors/jsp/connector';
oFCKeditor.Config['FlashUploadURL']=oFCKeditor.BasePath+'editor/filemanager/upload/whatyuploader?Type=Flash';


oFCKeditor.Value = 'This is some <strong>sample text</strong>. You are using FCKeditor.' ; 
oFCKeditor.ReplaceTextarea() ; 
//--> 
</script> 
<script>bLoaded=true;</script>

</body>

</html>




