<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
	<head>
		<title></title>
		<link href="/entity/manager/css/admincss.css" rel="stylesheet" type="text/css">
		<link rel="stylesheet" type="text/css" href="/js/extjs/css/ext-all.css" />
		<link rel="stylesheet" type="text/css" href="/js/extjs/css/ext-ext.css" />
		<link rel="stylesheet" type="text/css" href="/js/extjs/examples/Datetime/datetime.css"></link>
		<script type="text/javascript" src="/js/extjs/pub/adapter/ext/ext-base.js"></script>
		<script type="text/javascript" src="/js/extjs/pub/ext-all.js"></script>
		<script type="text/javascript" src="/js/extjs/examples/Datetime/Datetime.js"></script>
		<script type="text/javascript" src="/js/extjs/pub/ext-lang-zh_CN.js"></script> 
		<script type="text/javascript" src="/FCKeditor/fckeditor.js"></script>
		<script type="text/javascript">
		Ext.onReady(function(){
			Ext.QuickTips.init();
			Ext.form.Field.prototype.msgTarget = 'side';
			var pb = new Ext.form.TextField({
		       fieldLabel: '接收人',
		       allowBlank: false,
		       name: 'title',
		       anchor: '90%' 
		   });
		   
			var title = new Ext.form.TextField({
		       fieldLabel: '标题',
		       allowBlank: false,
		       name: 'shortTitle',
		       anchor: '90%'  
		   });  
		   
		   var body = new Ext.form.TextArea({
		    	fieldLabel: '内容',  
		        xtype: 'textarea',
		        hideLabel: true,
		        name: 'body',
		        id:'body',
		        anchor: '100% -30'  // anchor width by percentage and height by raw adjustment 
		    });
		   var formPanel = new Ext.form.FormPanel({
			    frame:true,
			       labelWidth: 100,
			      	defaultType: 'textfield',
				autoScroll:true,
			       items: [
			           pb,title,body
				]
		   });
		   var addUserWin = new Ext.Window({
		       title: '新建公文',
		       width: 900,
		       height: 450,
		       minWidth: 300,
		       minHeight: 250,
		       layout: 'fit',
		       plain:true,
		       bodyStyle:'padding:5px;',
		       buttonAlign:'center',
		       items: formPanel,
		       buttons: [{
			            text: '发送', 
			            handler: function() {
			                 Ext.get('body').dom.value=editorInstance.GetXHTML( true );
			                
			                // check form value 
			                if (formPanel.form.isValid()) {
				 		        formPanel.form.submit({
				 		        	url:'/entity/information/peDocument_save.action',
						            waitMsg:'正在发送',
		
									success: function(form, action) {
									    var responseArray = action.result;
									    if(responseArray.success=='true'){
									    	Ext.MessageBox.alert('信息', '发送成功&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;');
									    	store.load({params:{start:0,limit:0}});
							    } else {
							    	Ext.MessageBox.alert('错误',  '发送失败&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;');
							    }
							}
				        });                   
	                } else{
						Ext.MessageBox.alert('错误', '请先修正错误');
					}             
		        }
	        },{
	            text: '取消',
	            handler: function(){window.close();}
	        }]
       
       });
       addUserWin.show();
        var oFCKeditor = new FCKeditor( 'body' ) ; 
oFCKeditor.Height = 300 ; 
oFCKeditor.Width  = 900 ; 

oFCKeditor.Config['ImageBrowserURL']=oFCKeditor.BasePath+'editor/filemanager/browser/default/browser.html?Type=Image&Connector=connectors/jsp/connector';
oFCKeditor.Config['ImageUploadURL']=oFCKeditor.BasePath+'editor/filemanager/upload/whatyuploader?Type=Image';

oFCKeditor.Config['LinkBrowserURL']=oFCKeditor.BasePath+'editor/filemanager/browser/default/browser.html?Connector=connectors/jsp/connector';
oFCKeditor.Config['LinkUploadURL']=oFCKeditor.BasePath+'editor/filemanager/upload/whatyuploader?Type=File';

oFCKeditor.Config['FlashBrowserURL']=oFCKeditor.BasePath+'editor/filemanager/browser/default/browser.html?Type=Flash&Connector=connectors/jsp/connector';
oFCKeditor.Config['FlashUploadURL']=oFCKeditor.BasePath+'editor/filemanager/upload/whatyuploader?Type=Flash';
oFCKeditor.Value = '' ; 
oFCKeditor.ReplaceTextarea() ; 
		});
		var editorInstance;   
						/**  
						 * FCKEditor
						 * @param {Object} editorInstance  
						 */  
						function FCKeditor_OnComplete( instance ) {   
						    editorInstance=instance;   
						};  
		</script>
	</head>
	<body id="main_content" leftmargin="0" topmargin="0" rightmargin="0" bottommargin="0">
		<table width="100%" height="100%">
			<tr>
				<td colspan="3" height="8"></td>
			</tr>
			<tr>
				<td width="3%"></td>
				<td valign="top">
					<div id="user-defined-content"></div>
					<div id="searchtool"></div>
					<div id="model-grid"></div>
				</td>
				<td width="3%"></td>
			</tr>
			<tr>
				<td colspan="3" height="8"></td>
			</tr>
		</table>
	</body>
</html>
