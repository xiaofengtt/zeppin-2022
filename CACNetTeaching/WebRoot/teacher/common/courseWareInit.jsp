<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ include file="head.jsp" %>
<%@ include file="left.jsp" %>
<script src="js/courseWare.js" type="text/javascript"></script>
<script src="js/jquery.ui.datepicker-zh-CN.js" type="text/javascript"></script>
<div class="cca-main">
			  <div id="cbody" class="inn">
				   <div class="cca-table cca-table-padding">
				     <div class="cca-table-hd">课程名称：<s:property value="coursedesign.dicAcademy.name"/>--<s:property value="coursedesign.subject.name"/>(开课时间：<s:date name="coursedesign.startTime" format="yyyy-MM-dd " />)</div>
					  <form class="form-horizontal" action="uploadFile.action?id=<s:property value="coursedesign.id"/>&opt=add" enctype="multipart/form-data" method="post">
						  <div class="clearfix">
							  <div class="half">
								  <div class="control-group">
								      <label class="control-label" for="inputEmail">选择课件</label>
								      <div class="controls">
								      <input type="file" name ="fileupload" id="file"/><br>
								   
		
								      
								      </div>
								  </div>
								  
								  <div class="control-group">
								      
								      <div class="controls">
								        
								      </div>
								  </div>
								  
							  </div>
							  
						
						  </div>
						  <div class="cca-tr"><button id="submit" class="btn btn-success" type="submit">上传</button> </div>
					  </form>
					  <div class="myclass">
					
					  
					  <div class="zuoye clearfix">
					  <s:iterator value='lstccwm' id="cc" >
						  <div class="grid slight">
							  <p>
								 名称：<a href="<s:property value="#cc.accessory.filePath"/>/<s:property value="#cc.accessory.name"/><s:property value="#cc.accessory.fileType"/>"><s:property value="#cc.accessory.oraName"/><s:property value="#cc.accessory.fileType"/></a><br>
								 大小：<s:property value="#cc.accessory.fileSize"/>(k)<br>
								 上传人:<s:property value="#cc.teacher.name"/>
							  </p>
							  <span  style="cursor:pointer;" class="xx" >&times;<input type="hidden" value="<s:property value="#cc.accessory.id"/>"/></span>
						  </div>
						  </s:iterator>
					  </div>
					  </div>
				  </div>
			  </div>
		  </div>
  </body>
</html>