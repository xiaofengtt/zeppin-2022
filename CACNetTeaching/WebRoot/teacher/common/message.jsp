<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ include file="head.jsp" %>
<script src="js/tMessage.js" type="text/javascript"></script>
 <script type="text/javascript" src="js/Validform-min.js"></script>

	  <div class="cca-content">
		  <div class="cca-side">
			  <ul>
				  <li class=" chakanxiaoxi "><a href="teacher_messageInit.action">查看消息</a></li>
				   <li class=" fabuxiaoxi active"><a href="teacher_message.action">发布消息</a></li>
				   
			  </ul>
		  </div>
		  <div class="cca-main">
			  <div id="cbody" class="inn">
				  <form class="form-horizontal" action="teacher_addMessage.action" method="post">
				    <div class="control-group">
				      <label class="control-label" for="inputEmail">选择授课班级</label>
				      <div class="controls">
						  <select name="coursedesign.id">
						  <s:iterator value="lstCdtMaps" id="te">
						    <option value="<s:property value="#te.coursedesign.id"/>"><s:property value="#te.coursedesign.dicAcademy.name"/>--<s:property value="#te.coursedesign.subject.courseName"/></option>
						   </s:iterator>
						  </select>
				      </div>
				    </div> 
				  
				    <div class="control-group">
				      <label class="control-label" >标题</label>
				      <div class="controls">
				        <input type="text" class="span6"  name="message.title" datatype="*">
				      </div>
				    </div>
				    <div class="control-group">
				    
				      <div class="controls">
				       <textarea rows="5" class="span6" name="message.content" datatype="*"></textarea>
				      </div>
				    </div>
				    <div class="control-group">
				      <div class="controls">
				       
				        <button type="submit" class="btn btn-success">发布</button>
				      </div>
				    </div>
				  </form>
				 
			  </div>
		  </div>
		  
	  </div>

  </body>
</html>
