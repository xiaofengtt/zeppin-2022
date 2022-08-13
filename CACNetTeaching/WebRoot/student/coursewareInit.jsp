<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ include file="head.jsp" %>
<%@ include file="left.jsp" %>
<div class="cca-main">
			  <div id="cbody" class="inn">
				   <div class="cca-table cca-table-padding">
				     <div class="cca-table-hd">课程名称：<s:property value="coursedesign.dicAcademy.name"/>--<s:property value="coursedesign.subject.name"/>(开课时间：<s:date name="coursedesign.startTime" format="yyyy-MM-dd " />)</div>
					  
					  <div class="myclass">
					
					  
					  <div class="zuoye clearfix">
					  <s:iterator value='lstccwm' id="cc" >
						  <div class="grid slight">
							  <p>
								 名称：<a href="<s:property value="#cc.accessory.filePath"/>/<s:property value="#cc.accessory.name"/><s:property value="#cc.accessory.fileType"/>"><s:property value="#cc.accessory.oraName"/><s:property value="#cc.accessory.fileType"/></a><br>
								 大小：<s:property value="#cc.accessory.fileSize"/>(k)<br>
								 上传人:<s:property value="#cc.teacher.name"/>
							  </p>
							
						  </div>
						  </s:iterator>
					  </div>
					  </div>
				  </div>
			  </div>
		  </div>
  </body>
</html>