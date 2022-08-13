<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ include file="head.jsp" %>
<%@ include file="left.jsp" %>
<script src="js/sUserInfo.js" type="text/javascript"></script>
<script type="text/javascript" src="js/tabifier.js"></script>
<script type="text/javascript" src="js/jPages.min.js"></script>

 <link rel="stylesheet" href="css/jPages.css">
 <div class="cca-main">
			  <div id="cbody" class="inn">
				  <div class="usersetting">
					  <form class="form-horizontal" action="teacher_userInfo.action" method="post">
						  <div class="control-group control-item-avatar ">
						    <label class="control-label" for="inputEmail"></label>
						    <div class="controls">
								<a href="#" class="people-avatar"><img class="img-circle" src="img/u.gif" width="60"></a>
						    </div>
						  </div>
						  <div class="control-group">
						    <label class="control-label" for="">姓名</label>
						    <div class="controls">
								<input type="text" id="" name="teacher.name" value="<s:property value="teacher.name"/>" disabled = 'disabled'>
						    </div>
						  </div>
						  <div class="control-group">
						    <label class="control-label" for="">密码</label>
						    <div class="controls">
								<input type="password" id="pwd" name="pwd"  >
						    </div>
						  </div>
						   <div class="control-group">
						    <label class="control-label" for="">确认密码</label>
						    <div class="controls">
								<input type="password" id="confirmPwd" name="confirmPwd">
						    </div>
						  </div>
						   <div class="control-group">
						    <label class="control-label" for="">身份证</label>
						    <div class="controls">
								<input type="text" id="" name="teacher.idCode" value="<s:property value="teacher.idCode"/>" >
						    </div>
						  </div>
						   <div class="control-group">
						    <label class="control-label" for="">电话</label>
						    <div class="controls">
								<input type="text" id="" name="teacher.phone" value="<s:property value="teacher.phone"/>" >
						    </div>
						  </div>
						
						  <div class="control-group">
						    <label class="control-label" for=""></label>
						    <div class="controls">
								<input class="btn submit" type="submit" value="提交" id="submit">
						    </div>
						  </div>
					  </form>
				  </div>
				 
			  </div>
		  </div>
		  
	  </div>

  </body>
</html>