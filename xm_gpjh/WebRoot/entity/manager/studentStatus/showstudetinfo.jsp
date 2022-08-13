<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>学生综合信息</title>
		<% String path = request.getContextPath();%>
		<link href="<%=path%>/entity/manager/css/admincss.css" rel="stylesheet" type="text/css"/>
	</head>
	<body>
	<div id="main_content">
	<s:if test="true">
			   <div class="content_title">基本信息(<s:property value="bean.enumConstByFlagStudentStatus.name"/><font color="red"><s:if test="bean.enumConstByFlagAdvanced.code==1">[<s:property value="bean.enumConstByFlagAdvanced.name"/>]</s:if></font>)
			   </div>
			   <div class="cntent_k" align="center">
			   <div class="k_cc">
			   <table width="90%">
			   		<tr>
			   			<td nowrap="nowrap">
			   				<div align="left" class="postFormBox"><span class="name"><label>学号</label></span></div>
			   			</td>
			   			<td nowrap="nowrap">
			   				<div align="left" class="postFormBox"><s:property value="bean.regNo"/></div>
			   			</td>
			   			<td nowrap="nowrap">
			   				<div align="left" class="postFormBox"><span class="name"><label>年级</label></span></div>
			   			</td>
			   			<td nowrap="nowrap">
			   				<div align="left" class="postFormBox" ><s:property value="bean.peGrade.name"/></div>
			   			</td>
			   			<td nowrap="nowrap">
			   				<div align="left" class="postFormBox"><span class="name"><label>学习中心</label></span></div>
			   			</td>
			   			<td nowrap="nowrap">
			   				<div align="left" class="postFormBox"><s:property value="bean.peSite.name"/></div>
			   			</td>
			   		</tr>
			   		<tr>
			   			<td nowrap="nowrap">
			   				<div align="left" class="postFormBox"><span class="name"><label>姓名</label></span></div>
			   			</td>
			   			<td nowrap="nowrap">
			   				<div align="left" class="postFormBox"><s:property value="bean.trueName"/></div>
			   			</td>
			   			<td nowrap="nowrap">
			   				<div align="left" class="postFormBox"><span class="name"><label>专业</label></span></div>
			   			</td>
			   			<td nowrap="nowrap">
			   				<div align="left" class="postFormBox"><s:property value="bean.peMajor.name"/></div>
			   			</td>
			   			<td colspan="2" rowspan="4" class="postFormBox">
			   				<s:if test="bean.prStudentInfo.photoLink!=null">
			   					<img src="<s:property value='bean.prStudentInfo.photoLink'/>" width="66px" height="96px"/>
			   				</s:if><s:else>
			   					<img src="<s:property value='bean.peRecStudent.photoLink'/>" width="66px" height="96px"/>
			   				</s:else>
			   			</td>
			   		</tr>
			   		<tr>
			   			<td nowrap="nowrap">
			   				<div align="left" class="postFormBox"><span class="name"><label>性别</label></span></div>
			   			</td>
			   			<td nowrap="nowrap">
			   				<div align="left" class="postFormBox"><s:property value="bean.prStudentInfo.gender"/></div>
			   			</td>
			   			<td nowrap="nowrap">
			   				<div align="left" class="postFormBox"><span class="name"><label>层次</label></span></div>
			   			</td>
			   			<td nowrap="nowrap">
			   				<div align="left" class="postFormBox" ><s:property value="bean.peEdutype.name"/></div>
			   			</td>
			   		</tr>
			   		<tr>
			   			<td nowrap="nowrap">
			   				<div align="left" class="postFormBox"><span class="name"><label>出生日期</label></span></div>
			   			</td>
			   			<td nowrap="nowrap">
			   				<div align="left" class="postFormBox"><s:date name="bean.prStudentInfo.birthday" format="yyyy-MM-dd" />
			   				</div>
			   			</td>
			   			<td nowrap="nowrap">
			   				<div align="left" class="postFormBox"><span class="name"><label>政治面貌</label></span></div>
			   			</td>
			   			<td nowrap="nowrap">
			   				<div align="left" class="postFormBox"><s:property value="bean.prStudentInfo.zzmm"/></div>
			   			</td>
			   		</tr>
			   		<tr>
			   			<td nowrap="nowrap">
			   				<div align="left" class="postFormBox"><span class="name"><label>生源地</label></span></div>
			   			</td>
			   			<td nowrap="nowrap">
			   				<div align="left" class="postFormBox"><s:property value="bean.prStudentInfo.province"/>省<s:property value="bean.prStudentInfo.city"/>市</div>
			   			</td>
			   			<td nowrap="nowrap">
			   				<div align="left" class="postFormBox"><span class="name"><label>移动电话</label></span></div>
			   			</td>
			   			<td nowrap="nowrap">
			   				<div align="left" class="postFormBox"><s:property value="bean.getPrStudentInfo().getMobilephone()"/></div>
			   			</td>
			   		</tr>
			   		<tr>
			   			<td nowrap="nowrap">
			   				<div align="left" class="postFormBox"><span class="name"><label><s:property value="bean.prStudentInfo.cardType"/>号</label></span></div>
			   			</td>
			   			<td nowrap="nowrap">
			   				<div align="left" class="postFormBox"><s:property value="bean.prStudentInfo.cardNo"/></div>
			   			</td>
			   			<td nowrap="nowrap">
			   				<div align="left" class="postFormBox"><span class="name"><label>民族</label></span></div>
			   			</td>
			   			<td nowrap="nowrap">
			   				<div align="left" class="postFormBox"><s:property value="bean.prStudentInfo.fork"/></div>
			   			</td>
			   			<td nowrap="nowrap">
			   				<div align="left" class="postFormBox"><span class="name"><label>婚姻状态</label></span></div>
			   			</td>
			   			<td nowrap="nowrap">
			   				<div align="left" class="postFormBox"><s:property value="bean.prStudentInfo.marriage"/></div>
			   			</td>
			   		</tr>
			   		<tr>
			   		</tr>
			   		<tr>
			   			<td nowrap="nowrap">
			   				<div align="left" class="postFormBox"><span class="name"><label>联系电话</label></span></div>
			   			</td>
			   			<td nowrap="nowrap">
			   				<div align="left" class="postFormBox"><s:property value="bean.getPrStudentInfo().getPhone()"/></div>
			   			</td>
			   			<td nowrap="nowrap">
			   				<div align="left" class="postFormBox"><span class="name"><label>电子邮件</label></span></div>
			   			</td>
			   			<td nowrap="nowrap">
			   				<div align="left" class="postFormBox"><s:property value='bean.prStudentInfo.email'/></div>
			   			</td>
			   			<td nowrap="nowrap">
			   				<div align="left" class="postFormBox"><span class="name"><label>职业</label></span></div>
			   			</td>
			   			<td nowrap="nowrap">
			   				<div align="left" class="postFormBox"><s:property value="bean.prStudentInfo.occupation" /></div>
			   			</td>
			   		</tr>
			   		<tr>
			   			<td nowrap="nowrap">
			   				<div align="left" class="postFormBox"><span class="name"><label>通信地址</label></span></div>
			   			</td>
			   			<td colspan="3">
			   				<div align="left" class="postFormBox"><s:property value="bean.prStudentInfo.address"/></div>
			   			</td>
			   			<td nowrap="nowrap">
			   				<div align="left" class="postFormBox"><span class="name"><label>邮政编码</label></span></div>
			   			</td>
			   			<td nowrap="nowrap">
			   				<div align="left" class="postFormBox"><s:property value="bean.prStudentInfo.zip"/></div>
			   			</td>
			   		</tr>
			   		<tr>
			   			<td nowrap="nowrap">
			   				<div align="left" class="postFormBox"><span class="name"><label>工作单位</label></span></div>
			   			</td>
			   			<td colspan="2">
			   				<div align="left" class="postFormBox"><s:property value="bean.prStudentInfo.workplace" /></div>
			   			</td>
			   			<td nowrap="nowrap">
			   				<div align="left" class="postFormBox"><span class="name"><label>原毕业学校</label></span></div>
			   			</td>
			   			<td colspan="2">
			   				<div align="left" class="postFormBox"><s:property value="bean.prStudentInfo.graduateSchool"/></div>
			   			</td>
			   		</tr>
			   		<tr>
			   		<!-- <td nowrap="nowrap">
			   				<div align="left" class="postFormBox"><span class="name"><label>原毕业学校代码</label></span></div>
			   			</td>
			   			<td nowrap="nowrap">
			   				<div align="left" class="postFormBox"><s:property value="bean.prStudentInfo.graduateSchoolCode"/></div>
			   			</td>  -->	
			   			<td nowrap="nowrap">
			   				<div align="left" class="postFormBox"><span class="name"><label>原毕业时间</label></span></div>
			   			</td>
			   			<td nowrap="nowrap">
			   				<div align="left" class="postFormBox"><s:property value="bean.prStudentInfo.graduateYear"/></div>
			   			</td>
			   			<td nowrap="nowrap">
			   				<div align="left" class="postFormBox"><span class="name"><label>原毕业证书号</label></span></div>
			   			</td>
			   			<td nowrap="nowrap">
			   				<div align="left" class="postFormBox"><s:property value="bean.prStudentInfo.graduateCode"/></div>
			   			</td>
			   		</tr>
			   </table>
			   </div>
		   </div>
	</s:if>
	<s:if test="bean.enumConstByFlagDisobey.code>='0'">
			   <div class="content_title">违纪信息</div>
			   <div class="cntent_k" align="center">
			   <div class="k_cc">
			   <table width="90%">
			   		<tr>
			   			<td nowrap="nowrap">
			   				<div align="left" class="postFormBox"><span class="name"><label><s:property value="bean.enumConstByFlagDisobey.name"/></label></span></div>
			   			</td>
			   			<td nowrap="nowrap">
			   				<div align="left" class="postFormBox"><s:property value="bean.disobeyNote"/></div>
			   			</td>
			   		</tr>
			   </table>
			   </div>
		   </div>
	</s:if>
	<s:if test="tchProgram!=null">
	<div class="content_title">
		<s:iterator value="tchProgram">
			<div>该学生的教学计划是
			<s:iterator status="name">
				<s:if test="#name.index==0">
				"<s:property/>"
				</s:if>
				<s:if test="#name.index==1">
				（毕业时应得总学分<s:property/>）
				</s:if>
			</s:iterator>
			其所学课程如下：</div>
		</s:iterator>
		<s:set value="%{@com.whaty.platform.util.Const@mustscore}" name="mustscore"/>
		<s:if test="coursegroup!=null">
		<s:iterator value="coursegroup">
			<s:set name="coursegroupid"/>
			<s:if test="getCoursesInfos(#coursegroupid)!=null">
			<s:iterator value="getCoursesInfos(#coursegroupid)" status="name">
				<s:if test="#name.index==0">
				<div class="content_title">
					<s:iterator>
						<s:iterator status="i">
							<s:if test="#i.index==0">
									<s:property/>
							</s:if>
							<s:if test="#i.index==1">
									(毕业应得学分：<s:property/>)
							</s:if>
						</s:iterator>
					</s:iterator>
				</div>
				</s:if>
				<s:if test="#name.index==1">
				<div class="cntent_k" align="center">
			    <div class="k_cc">
				<s:set name="courses"/>
				<s:if test="#courses!=null&&#courses.size()>0">
			    <table width="95%">
			    	<tr>
			   			<td nowrap="nowrap">
			   				<div align="left" class="postFormBox"><span class="name">课程编号</span></div>
			   			</td>
			   			<td nowrap="nowrap">
			   				<div align="left" class="postFormBox"><span class="name">课程名称</span></div>
			   			</td>
			   			<td nowrap="nowrap">
			   				<div align="left" class="postFormBox"><span class="name">选课时间</span></div>
			   			</td>
			   			<td nowrap="nowrap">
			   				<div align="left" class="postFormBox"><span class="name">总评成绩</span></div>
			   			</td>
			   			<td nowrap="nowrap">
			   				<div align="left" class="postFormBox"><span class="name">平时成绩</span></div>
			   			</td>
			   			<td nowrap="nowrap">
			   				<div align="left" class="postFormBox"><span class="name">作业成绩</span></div>
			   			</td>
			   			<td nowrap="nowrap">
			   				<div align="left" class="postFormBox"><span class="name">考试成绩</span></div>
			   			</td>
			   			<td nowrap="nowrap">
			   				<div align="left" class="postFormBox"><span class="name">系统成绩</span></div>
			   			</td>
			   			<td nowrap="nowrap">
			   				<div align="left" class="postFormBox"><span class="name">获得学分</span></div>
			   			</td>
			   			<td nowrap="nowrap">
			   				<div align="left" class="postFormBox"><span class="name">在线时长</span></div>
			   			</td>
			   			<td nowrap="nowrap">
			   				<div align="left" class="postFormBox"><span class="name">成绩状态</span></div>
			   			</td>
			   		</tr>
					<s:iterator>
					<tr>
						<s:iterator status="i">
							<s:if test="#i.index==2">
								<s:set name="code"/>
							</s:if>
							<s:elseif test="#i.index==9">
							<td nowrap="nowrap"><div align="left" class="postFormBox"><span class="name">
								<s:if test='((#code!=null&&#code.equals("1"))&&#score>#mustscore)||#code.equals("5")'>								
						 			<s:property/>(课程学分<s:property/>)
								</s:if>
						 		<s:else>0(课程学分<s:property/>)</s:else>
								</span></div></td>
							</s:elseif>
							<s:elseif test="#i.index==4">
								<s:set name="score"/>
							<td nowrap="nowrap"><div align="left" class="postFormBox"><span class="name">
						 		<s:property/>
								</span></div></td>
							</s:elseif>
							<s:elseif test="#i.index==11">
								<s:set name="select"/>
							<td nowrap="nowrap"><div align="left" class="postFormBox"><span class="name">
						 		<s:if test="#select!=null&&#select.lenght()>0">还没有选课</s:if>
								<s:else><s:property/></s:else>
								</span></div></td>
							</s:elseif>
							<s:else>
						 <td nowrap="nowrap"><div align="left" class="postFormBox"><span class="name">
						 		<s:property/>
								</span></div></td>
							</s:else>
						</s:iterator>
					</tr>
					</s:iterator>
				</table></s:if><s:else>
		        没有课程信息
		        </s:else>
			    </div>
		        </div>
				</s:if>				
			</s:iterator>
		</s:if>
		</s:iterator>
		</s:if></div>
	</s:if>
		 <div align="center">		
		<input type="button" name="close" value="关闭" onclick="javascript:window.close();"/></div> 
	</div>
	</body>
</html>