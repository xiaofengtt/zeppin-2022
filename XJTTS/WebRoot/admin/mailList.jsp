<%@page import="org.apache.struts2.components.Include"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<s:action name="head_init" namespace="/admin" executeResult="true"
	ignoreContextParams="false">
</s:action>
<s:action name="left_init" namespace="/admin" executeResult="true">
	<s:param name="leftName">收件箱列表</s:param>
</s:action>
<link href="../css/iframe.css" rel="stylesheet">
<link href="../css/mainList.css" rel="stylesheet">

<!-- 收件箱 -->
<div class="main">
	<div class="tablewrap">	
		<div id="searchbox" class="searchbox">
			<form class="search search_form" id="searchForm" action="#" method="POST">
				<fieldset>
					<label for="searchheader" class="placeholder overlabel">模糊搜索</label>
					<input autocomplete="off" id="searchheader" value="" type="text" name="q">
					<input type="hidden" name="" value="">
					<div class="show_dropdown">
						<div class="dropdown" style="display: block;">
							<ul>
								<li><label for="type_title"></label><input type="radio" id="type_title" name="stype" value="content" 
									checked="checked">
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
	</div>
	
	<div class="list_bar" style="height:58px;">收件箱</div>
	<div class="stateDiv">
			<label>状态&nbsp;：</label> <a class="light" value="">全部<span id="span1">(0)</span></a> <a class=""
				value="0">已读<span id="span2">(0)</span></a> <a class="" value="1">未读<span id="span3">(0)</span></a>
		</div>
	<div class="container">
		
		<table class="theadTable table">
			<tr><th width="26.6%">发件人</th><th width="36.2%">主题</th><th width="18.6%">时间</th>
				<th width="18.6%" class="borderNone">操作</th></tr>
		</table>
		<table class="table table-bordered tableContent">
			<tr class="fir"><td width="26.6%">乌鲁木齐教育局；新市区教育局；</td><td width="36.2%">关于做好新一轮远程教师培训工作的通知</td>
			<td width="18.6%">2016-08-29</td><td width="18.6%"><a class="view">查看</a>&nbsp;&nbsp;&nbsp;&nbsp;
			<a>转发</a>&nbsp;&nbsp;&nbsp;&nbsp;<a>删除</a></td></tr>
			<tr class="sec"><td colspan="4"><div>简介：这些准则需要大家的共同努力得以实现。互联网的开放和参与需要大家的个人贡献，团队协作，和发挥领导作用。
				Mozilla基金会承诺推行Mozilla宣言里制定的准则。我们邀请更多的同仁加入我们，共同奋斗使互联网变得更好。推行Mozilla宣言里的准则有多种途径。
				我们欢迎广泛的活动并期待参与者发挥像在Mozilla其他项目中那样的创造性。对于尚未深入 参与 Mozilla项目其他项目中那样的创造性。对于尚未深入 
				参与 Mozilla项目</div></td></tr>
		</table>
		<table class="table table-bordered tableContent">
			<tr class="fir"><td width="26.6%">乌鲁木齐教育局；新市区教育局；</td><td width="36.2%">关于做好新一轮远程教师培训工作的通知</td>
			<td width="18.6%">2016-08-29</td><td width="18.6%"><a class="view">查看</a>&nbsp;&nbsp;&nbsp;&nbsp;
			<a>转发</a>&nbsp;&nbsp;&nbsp;&nbsp;<a>删除</a></td></tr>
			<tr class="sec"><td colspan="4"><div>简介：这些准则需要大家的共同努力得以实现。互联网的开放和参与需要大家的个人贡献，团队协作，和发挥领导作用。
				Mozilla基金会承诺推行Mozilla宣言里制定的准则。我们邀请更多的同仁加入我们，共同奋斗使互联网变得更好。推行Mozilla宣言里的准则有多种途径。
				我们欢迎广泛的活动并期待参与者发挥像在Mozilla其他项目中那样的创造性。对于尚未深入 参与 Mozilla项目其他项目中那样的创造性。对于尚未深入 
				参与 Mozilla项目</div></td></tr>
		</table>
		<table class="table table-bordered tableContent">
			<tr class="fir"><td width="26.6%">乌鲁木齐教育局；新市区教育局；</td><td width="36.2%">关于做好新一轮远程教师培训工作的通知</td>
			<td width="18.6%">2016-08-29</td><td width="18.6%"><a class="view">查看</a>&nbsp;&nbsp;&nbsp;&nbsp;
			<a>转发</a>&nbsp;&nbsp;&nbsp;&nbsp;<a>删除</a></td></tr>
			<tr class="sec"><td colspan="4"><div>简介：这些准则需要大家的共同努力得以实现。互联网的开放和参与需要大家的个人贡献，团队协作，和发挥领导作用。
				Mozilla基金会承诺推行Mozilla宣言里制定的准则。我们邀请更多的同仁加入我们，共同奋斗使互联网变得更好。推行Mozilla宣言里的准则有多种途径。
				我们欢迎广泛的活动并期待参与者发挥像在Mozilla其他项目中那样的创造性。对于尚未深入 参与 Mozilla项目其他项目中那样的创造性。对于尚未深入 
				参与 Mozilla项目</div></td></tr>
		</table>
		<table class="table table-bordered tableContent">
			<tr class="fir"><td width="26.6%">乌鲁木齐教育局；新市区教育局；</td><td width="36.2%">关于做好新一轮远程教师培训工作的通知</td>
			<td width="18.6%">2016-08-29</td><td width="18.6%"><a class="view">查看</a>&nbsp;&nbsp;&nbsp;&nbsp;
			<a>转发</a>&nbsp;&nbsp;&nbsp;&nbsp;<a>删除</a></td></tr>
			<tr class="sec"><td colspan="4"><div>简介：这些准则需要大家的共同努力得以实现。互联网的开放和参与需要大家的个人贡献，团队协作，和发挥领导作用。
				Mozilla基金会承诺推行Mozilla宣言里制定的准则。我们邀请更多的同仁加入我们，共同奋斗使互联网变得更好。推行Mozilla宣言里的准则有多种途径。
				我们欢迎广泛的活动并期待参与者发挥像在Mozilla其他项目中那样的创造性。对于尚未深入 参与 Mozilla项目其他项目中那样的创造性。对于尚未深入 
				参与 Mozilla项目</div></td></tr>
		</table>
		<table class="table table-bordered tableContent">
			<tr class="fir"><td width="26.6%">乌鲁木齐教育局；新市区教育局；</td><td width="36.2%">关于做好新一轮远程教师培训工作的通知</td>
			<td width="18.6%">2016-08-29</td><td width="18.6%"><a class="view">查看</a>&nbsp;&nbsp;&nbsp;&nbsp;
			<a>转发</a>&nbsp;&nbsp;&nbsp;&nbsp;<a>删除</a></td></tr>
			<tr class="sec"><td colspan="4"><div>简介：这些准则需要大家的共同努力得以实现。互联网的开放和参与需要大家的个人贡献，团队协作，和发挥领导作用。
				Mozilla基金会承诺推行Mozilla宣言里制定的准则。我们邀请更多的同仁加入我们，共同奋斗使互联网变得更好。推行Mozilla宣言里的准则有多种途径。
				我们欢迎广泛的活动并期待参与者发挥像在Mozilla其他项目中那样的创造性。对于尚未深入 参与 Mozilla项目其他项目中那样的创造性。对于尚未深入 
				参与 Mozilla项目</div></td></tr>
		</table>
		<table class="table table-bordered tableContent">
			<tr class="fir"><td width="26.6%">乌鲁木齐教育局；新市区教育局；</td><td width="36.2%">关于做好新一轮远程教师培训工作的通知</td>
			<td width="18.6%">2016-08-29</td><td width="18.6%"><a class="view">查看</a>&nbsp;&nbsp;&nbsp;&nbsp;
			<a>转发</a>&nbsp;&nbsp;&nbsp;&nbsp;<a>删除</a></td></tr>
			<tr class="sec"><td colspan="4"><div>简介：这些准则需要大家的共同努力得以实现。互联网的开放和参与需要大家的个人贡献，团队协作，和发挥领导作用。
				Mozilla基金会承诺推行Mozilla宣言里制定的准则。我们邀请更多的同仁加入我们，共同奋斗使互联网变得更好。推行Mozilla宣言里的准则有多种途径。
				我们欢迎广泛的活动并期待参与者发挥像在Mozilla其他项目中那样的创造性。对于尚未深入 参与 Mozilla项目其他项目中那样的创造性。对于尚未深入 
				参与 Mozilla项目</div></td></tr>
		</table>
		
	</div>	
</div>
<script src="../js/mailList.js"></script>
<jsp:include page="foot.jsp"></jsp:include>