<%@page import="org.apache.struts2.components.Include"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<s:action name="head_init" namespace="/admin" executeResult="true"
	ignoreContextParams="false">
</s:action>
<s:action name="left_init" namespace="/admin" executeResult="true">
	<s:param name="leftName"></s:param>
</s:action>
<link rel="stylesheet" href="../css/iframe.css">
<script src="../js/iframe.js"></script>
<script src="../js/jsrender.min.js"></script>

<link href="../css/select2.css" rel="stylesheet" />
<script src="../js/select2.js"></script>
<script src="../js/select2_locale_zh-CN.js"></script>

<div class="main">

	<div class="listwrap">
		<div class="list_bar">批量添加评审专家</div>
		<div class="list-content clearfix" style="padding-top:15px;margin-bottom:10px">	
			<div class="alert alert-danger" style="display:none;margin:0 15px 15px;"></div>
			<div class="fliter-btn-wrap">
				<a onclick="addExpertall(this)" data-url="../admin/projectApplyOpt_addExpertAll?id=<s:property value="id"/>&evaluateType=<s:property value="evaluateType"/>"  class="btn btn-primary">批量添加</a>
			</div>
		</div>
		<div id="list-content" class="list-content clearfix">
			<s:iterator value="lsProjectExpert" id="pelist">
			
				<div id="<s:property value="#pelist.id" />" class="list-item">
					<div class="list-item-hd">
						<table class="table table-bordered">
							<tbody>
								<tr>
									<td width="140px"><input class="listcheck" type="checkbox" name="expertinfo" value="<s:property value="#pelist.id" />"> 
										<span class="text-primary">ID：</span><s:property value="#pelist.id" />
									</td>
									<td width="auto" ><span class="text-primary">姓名：</span><span><s:property value="#pelist.name" /></span></td>
								</tr>
							</tbody>
						</table>
					</div>

					<div class="list-item-bd clearfix">
						<div class="list-item-col list-5-2">
							<ul>
								<li><span class="text-primary">工作单位：</span> <s:property value="#pelist.organization" /></li>
							</ul>
						</div>
						<div class="list-item-col list-5-08">
							<ul>
								<li><span class="text-primary">工作职位：</span> <s:property value="#pelist.jobTitle" /></li>
							</ul>
						</div>
						<div class="list-item-col list-5-08">
							<ul>
								<li><span class="text-primary">研究专长：</span> <s:property value="#pelist.research" /></li>
							</ul>
						</div>
						<div class="list-item-col list-5-08">
							<ul>
								<li><span class="text-primary">教学专长：</span> <s:property value="#pelist.teach" /></li>
							</ul>
						</div>

					</div>
				</div>
			</s:iterator>
		</div>
		
	</div>
</div>
<script>

</script>
<body>
</html>
