<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ include file="head.jsp" %>
<%@ include file="left.jsp" %>
<script src="js/shomework.js" type="text/javascript"></script>
<script type="text/javascript" src="js/tabifier.js"></script>
<script type="text/javascript" src="js/jPages.min.js"></script>
 <link rel="stylesheet" href="css/jPages.css">
<div class="cca-main">
			  <div id="cbody" class="inn">
				   <div class="cca-table cca-table-padding">
				     <div class="cca-table-hd">课程名称：<s:property value="coursedesign.dicAcademy.name"/>--<s:property value="coursedesign.subject.name"/>(开课时间：<s:date name="coursedesign.startTime" format="yyyy-MM-dd " />)</div>
					  
					  <div class="myclass">
					<div id="scheduleHead" class="ui-jqgrid-titlebar ui-jqgrid-caption ui-widget-header ui-corner-top ui-helper-clearfix">课程表点击展开</div>
					   <div class="zuoye clearfix" id="scheduleContent">
					  <s:iterator value='lstCourseschedules' id="schedule" >
						  <div class="grid slight">
							  <p><s:date name="#schedule.StartTime" format="yyyy年MM月dd日" nice="false"/>-<s:property value="#schedule.datetype"/><br>
								  <s:property value="#schedule.classroom"/><br>
								  周数：<s:property value="#schedule.weeks"/><br>
								  任课教师：<s:property value="#schedule.teacherByFkTeacher.name"/><br>
								 备注：<s:property value="#schedule.remark"/>
							  </p>
							
						  </div>
						  </s:iterator>
					  </div>
					 
					 	 <div class="xxxviewinfo">
					 	<s:iterator value="lsthwe" id="hw">
					  <div class="mod">
						 
						  <div class="bd">
							  <h5>标题：<s:property value="#hw.homework.title"/></h5>
							  <p>作业要求：<s:property value="#hw.homework.content"/></p>
							  <h5>是否大作业：<s:property value="#hw.isEnd"/></h5>
							  <h5>作业提交截止日期:<s:date name="#hw.homework. endTime" format="yyyy年MM月dd日"/></h5>	
							<h5>发布日期:<s:date name="#hw.homework.startTime" format="yyyy年MM月dd日"/></h5>
							<h5>发布人：<s:property value="#hw.homework.teacher.name"/></h5>
							 <form class="info-attachment" action="uploadHomework.action?id=<s:property value="#hw.homework.id"/>&coursedesignId=<s:property value="coursedesign.id"/>" enctype="multipart/form-data" method="post">
						  <div class="clearfix">
							  <div class="half">
								  <div class="control-group">
								      <label class="control-label" for="inputEmail">提交作业：</label>
								      <div class="controls">
								      <input type="file" name ="fileupload" class="uploadFile" id="uploadFile<s:property value="#hw.homework.id"/>"/><br>
								      </div>
								  </div>
							  </div>
							  
						  </div>
						  <div class="cca-tr" style="float:left"><button class="submit" class="btn btn-success" type="submit" id="<s:property value="#hw.homework.id"/>">上传</button> </div>
					  </form><br>
							<div class="info-attachment">
								<h4>已提交的作业：</h4>		
							</div>
							  <div class="zuoye clearfix">
					  <s:iterator value='#hw.lstAccessoriex' id="cc" >
						  <div class="grid slight">
							  <p>
								 名称：<a href="<s:property value="#cc.accessory.filePath"/>/<s:property value="#cc.accessory.name"/><s:property value="#cc.accessory.fileType"/>"><s:property value="#cc.accessory.oraName"/><s:property value="#cc.accessory.fileType"/></a><br>
								 大小：<s:property value="#cc.accessory.fileSize"/>(k)<br>
								 上传人:<s:property value="#cc.userName"/><br>
								作业状态:<s:property value="#cc.homworkState"/>
							  </p>
							
						  </div>
						  </s:iterator>
					  </div>
						  </div>
					
					  </div><br>
					  </s:iterator> 
					  </div>
				  </div>
			  </div>
		  </div>
  </body>
</html>