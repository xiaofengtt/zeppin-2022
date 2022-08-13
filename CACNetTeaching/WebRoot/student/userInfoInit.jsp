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
					  <form class="form-horizontal" action="student_userInfo.action" method="post">
						  <div class="control-group control-item-avatar ">
						    <label class="control-label" for="inputEmail"></label>
						    <div class="controls">
								<img class="img-circle" src="img/u.gif" width="60">
						    </div>
						  </div>
						  <div class="control-group">
						    <label class="control-label" for="">姓名</label>
						    <div class="controls">
								<input type="text" id="" name="student.name" value="<s:property value="student.name"/>" disabled = 'disabled'>
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
								<input type="text" id="" name="student.idCode" value="<s:property value="student.idCode"/>" >
						    </div>
						  </div>
						   <div class="control-group">
						    <label class="control-label" for="">电话</label>
						    <div class="controls">
								<input type="text" id="" name="student.phone" value="<s:property value="student.phone"/>" >
						    </div>
						  </div>
						   <div class="control-group">
						    <label class="control-label" for="">家庭电话</label>
						    <div class="controls">
								<input type="text" id="" name="student.familyphone" value="<s:property value="student.familyphone"/>" >
						    </div>
						  </div>
						   <div class="control-group">
						    <label class="control-label" for="">家庭联系人</label>
						    <div class="controls">
							<input type="text" id="" name="student.familyName" value="<s:property value="student.familyName"/>" >
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