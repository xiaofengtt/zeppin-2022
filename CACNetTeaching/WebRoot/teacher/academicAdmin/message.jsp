<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ include file="head.jsp" %>

<script type="text/javascript" src="js/Validform-min.js"></script>


	  <div class="cca-content">
		  <div class="cca-side">
			  <ul>
				  <li class=" chakanxiaoxi "><a href="admin_messageInit.action">查看消息</a></li>
				   <li class=" fabuxiaoxi active"><a href="admin_message.action">发布消息</a></li>
				   
			  </ul>
		  </div>
		  <div class="cca-main">
			  <div id="cbody" class="inn">
				  <form class="form-horizontal" action="admin_addMessage.action" method="post">
				    <div class="control-group">
				      <label class="control-label" for="inputEmail">信息类型</label>
				      <div class="controls">
						  <select name="message.type">
						    <option value="1">全体人员</option>
						    <option value="2">全体教师</option>
						    <option value="3">全体学生</option>
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
				       <textarea rows="5" class="span6" name="message.content"datatype="*"></textarea>
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
<script type="text/javascript">

	$(".form-horizontal").Validform();
	</script>
  </body>
</html>
