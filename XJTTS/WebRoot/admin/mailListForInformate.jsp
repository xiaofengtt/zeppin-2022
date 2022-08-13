<%@page import="org.apache.struts2.components.Include"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<s:action name="head_init" namespace="/admin" executeResult="true"
	ignoreContextParams="false">
</s:action>
<s:action name="left_init" namespace="/admin" executeResult="true">
	<s:param name="leftName">查看信息</s:param>
</s:action>
<link href="../css/iframe.css" rel="stylesheet">
<link href="../css/mainList.css" rel="stylesheet">
<script type="text/javascript" src="../js/layer-v2.2/layer/layer.js"></script>
<!-- 查看信息 -->
<div class="main">
	<div class="list_bar" style="height:58px;">查看信息</div>
	<div class="iconDiv">
		<a class="btn btnMyself forwarded"> <span><img
				src="../img/forwarded.png" alt="icon"> <b>转发</b> </span>
			<p>
				<img src="../img/lanse.png" alt="蓝色三角"> <b>转发邮件</b>
			</p>
		</a>
		<a class="btn btnMyself delete"> <span><img
				src="../img/delete.png" alt="icon"> <b>删除</b> </span>
			<p>
				<img src="../img/lanse.png" alt="蓝色三角"> <b>删除邮件</b>
			</p>
		</a>
		<a class="btn btnMyself return"> <span><img
				src="../img/return.png" alt="icon"> <b>返回</b> </span>
			<p>
				<img src="../img/lanse.png" alt="蓝色三角"> <b>返回上一页</b>
			</p>
		</a>
	</div>
	<div class="container">
		<div class="noticeDiv">
			<p class="bg"></p>
			<p class="title text-center"></p>
			<p class="letterType text-center"></p>
			<p class="content contents"></p> 
			<p class="content cards"></p><!--左侧祝福语  -->
			<div class="inscribeDiv">
				<p class="content inscribe text-center"></p>
				<p class="content data text-center"></p>
			</div>
			<div class="clear"></div>
			<div class="attachmentDiv">
				<div>附件</div>
			</div>
			<div class="reply">
				<b>回复信息</b>
				<div class="repliedItems">
					<div class="answerDiv" style="border:none"><input class="answerInputs" value=""/>
            			<div class="operation"><a class="sendBtn" onclick="answer(this)">发送</a>
            			<a class="cancelBtn">取消</a></div>
            		</div>
            		<div class="replayDiv">
            		
            		</div>
				</div>
			</div>
		</div>
	</div>
</div>


<script src="../js/mailListForInfomate.js"></script>
<jsp:include page="foot.jsp"></jsp:include>