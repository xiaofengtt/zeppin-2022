<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ include file="head.jsp" %>
<script src="js/sMessageInit.js" type="text/javascript"></script>
    
	  <div class="cca-content">
		  <div class="cca-side">
			  <ul>
				  <li class=" chakanxiaoxi active"><a href="student_messageInit.action">查看消息</a></li>
				 <!--   <li class=" fabuxiaoxi"><a href="teacher_message.action">发布消息</a></li> -->
				   
			  </ul>
		  </div>
		  <div class="cca-main">
			  <div id="cbody" class="inn">
				  <div class="action news-button">
					  <!--  <button class="btn btn-primary" type="button">所有消息<em class="message-count">8</em></button> -->
					 <!--   <button class="btn btn-warning" type="button">院系通知<em class="message-count">3</em></button> -->
					   <!--  <button class="btn btn-info" type="button">课程信息<em class="message-count">1</em></button> -->
						<!--  <button class="btn btn-danger" type="button">我发布的消息</button> -->
				  </div>
				  <div class="xxxviewinfo">
				  <s:iterator value="lstSmm" id="sm">
				  
				
					  <div class="mod">
						  <div class="hd">
							  <img class="img-polaroid" src="img/u.gif" width="62" alt>
						  </div>
						  <div class="bd">
							  <h3><s:property value="#sm.message.title"/></h3>
							  <span class="info-type">类型：院系通知</span>
							  <p><s:property value="#sm.message.content"/></p>	
							<div class="info-date"><s:date name="#sm.message.sendTime" format="yyyy年MM月dd日"/></div>
							<div class="info-reply"><a>删除</a><input type="hidden" value="<s:property value="#sm.id"/>"/></div>
							
						  </div>
					  </div>
					    </s:iterator>
					 
					  
				  </div>
				  
				 
			  </div>
		  </div>
		  
	  </div>

  </body>
</html>
