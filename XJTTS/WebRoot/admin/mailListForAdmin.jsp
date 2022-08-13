<%@page import="org.apache.struts2.components.Include"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<s:action name="head_init" namespace="/admin" executeResult="true"
	ignoreContextParams="false">
</s:action>
<s:action name="left_init" namespace="/admin" executeResult="true">
	<s:param name="leftName">收件箱列表</s:param>
</s:action>
<div class="main">

	<div class="tablewrap">
		
		<div class="cui-menu">
			<a class="btn btn-primary" href="../admin/mail_getInboxList.action?">收件箱</a>&nbsp;&nbsp;
			<a class="btn btn-primary" href="../admin/mail_getOutBoxList.action?">发件箱</a>
		</div>
		
		<div id="searchbox" class="searchbox">
			<form class="search search_form" id="searchForm" action="#" method="POST">
				<fieldset>
					<label for="searchheader" class="placeholder overlabel">模糊搜索</label>
					<input autocomplete="off" id="searchheader" value="" type="text" name="q">
					<input type="hidden" name="" value="">
					<div class="show_dropdown">
						<div class="dropdown" style="display: block;">
							<ul>
								<li><label for="type_title"></label><input type="radio" id="type_title" name="stype" value="content" checked="checked">
								</li>
							</ul>
							<span class="bl"></span>
							<span class="br"></span>
						</div>
					</div>
					<button type="submit" id="searchBtn" class="search-button">Search</button>
				</fieldset>
			</form>
			
		</div>

		<div id="ProjectTableContainer"></div>
	
	</div>
</div>


<jsp:include page="foot.jsp"></jsp:include>