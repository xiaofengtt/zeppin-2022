<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*,java.net.*"%>
<jsp:directive.page import="com.whaty.platform.entity.bean.PeManager" />
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<%@ include file="./pub/priv.jsp"%>

<html>
	<head>
		<link href="<%=request.getContextPath()%>/admin/css.css"
			rel="stylesheet" type="text/css">
		<title><s:property value="%{title}"/>权限组管理</title>
		<link rel="stylesheet" type="text/css" href="/js/extjs/css/ext-all.css" />
		<script type="text/javascript" src="/js/extjs/pub/adapter/ext/ext-base.js"></script>
		<script type="text/javascript" src="/js/extjs/pub/ext-all.js"></script>
		<script type="text/javascript" src="/js/extjs/pub/ext-lang-zh_CN.js"></script> 
		<script type="text/javascript">
		
		Ext.onReady(function(){
			
			 var loginId = new Ext.form.TextField({
			        fieldLabel: '<s:text name="登陆ID"/>',
			        name: 'loginId',
			        id:'loginId'
			    });
			    
			var s_search = new Ext.Button({
		        text: '<s:text name="搜索"/>',
		        width:100,
		        handler: function() {
		        			   location.href='<s:property value="%{url}"/>'+'?type=stu'+'&loginId='+Ext.get('loginId').dom.value;
				          }
		    }); 
		    
		    
		    var back = new Ext.Button({
		        text: '<s:text name="test.back"/>',
		        width:100,
		        handler: function() {
		        			 location.href=' /sso/admin/managerOper_showManagerList.action';
				          }
		    }); 
		    
		    
		   var s_formPanel =new Ext.FormPanel({
		   		buttonAlign:'center',    
		   		width:1000,
		        labelAlign: 'center',
		        labelWidth: 120,
		        frame:true,
		        title: '<s:property value="%{title}"/><s:text name="权限组管理"/>',
		        collapsible:true,
		        items: [{
		        	layout:'column',
		        	items:[{
		                columnWidth:.3,
		                layout: 'form',
		                items: [loginId]
		        	},{
		        		 columnWidth:.3,
		                layout: 'form',
		                items: [s_search]
		        	},{
		        		columnWidth:.3,
		                layout: 'form',
		                items: [back]
		        	}]
		        }
		    	]
		    });
			    
			   s_formPanel.render("main_content");	
		});

		
		</script>
	</head>

	<body>
	<hr>
	<table cellPadding=2 cellSpacing=1 border="0" bgcolor=#3F6C61
			align=center width="100%">
			<tr>
				<td align="left" width="10%" bgcolor="#D4E4EB" class="zhengwen">
					<div align="center">
						登录ID
					</div>
				</td>
				<td align="left" width="10%" bgcolor="#D4E4EB" class="zhengwen">
					<div align="center">
						名称
					</div>
				</td>
				<td align="left" width="10%" bgcolor="#D4E4EB" class="zhengwen">
					<div align="center">
						权限组
					</div>
				</td>
				<td align="left" width="10%" bgcolor="#D4E4EB" class="zhengwen">
					<div align="center">
						操作
					</div>
				</td>
			</tr>
			<s:iterator value="list">
			<tr>
				<td align="left" width="10%" bgcolor="#D4E4EB" class="zhengwen">
				<div align="center">
					<s:property value="ssoUser.loginId"/>
					</div>
				</td>
				<td align="left" width="10%" bgcolor="#D4E4EB" class="zhengwen">
					<div align="center">
					<s:property value="name"/>
					</div>
				</td>
				<td align="left" width="10%" bgcolor="#D4E4EB" class="zhengwen">
					<div align="center">
					<s:if test="%{ssoUser.pePriRole==null}">
						<font color="red">未设置权限组</font>
					</s:if>
					<s:else>
						<s:property value="ssoUser.pePriRole.name"/>
					</s:else>
					</div>
				</td>
				<td align="left" width="10%" bgcolor="#D4E4EB" class="zhengwen">
				<div align="center">
					<s:if test="%{'stu'.equals(type)}">
											<s:a href='/sso/admin/teacherStudentManagerOper_changeStudentGroup.action?managerId=%{id}'>设定权限组</s:a>
					</s:if>
					<s:else>
						<s:a href='/sso/admin/teacherStudentManagerOper_changeAdminGroup.action?managerId=%{id}'>设定权限组</s:a>
					</s:else>
					
				</div>
				</td>
			</tr>
			</s:iterator>
	</table>
	<br>
	</div>
	<div class="main_content" id="main_content">
	</body>
</html>