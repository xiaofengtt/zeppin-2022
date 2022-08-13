<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<body>
	   <table width="80%" class="postFormBox" >
	   		<tr class="postFormBox"><td  id="info" colspan="9"></td></tr>
	   		<tr class="postFormBox" >
	   			<td ></td>
	   			<td align="center" class="pageBottomBorder" nowrap="nowrap"><span class="name"><label>学号</label></span></td>
	   			<td align="center" nowrap="nowrap"><span class="name"><label>姓名</label></span></td>
	   			<td align="center" class="pageBottomBorder" nowrap="nowrap"><span class="name"><label>证件号码</label></span></td>
	   			<td align="center" nowrap="nowrap"><span class="name"><label>座位号</label></span></td>
	   			<td align="center" class="pageBottomBorder" nowrap="nowrap"><span class="name"><label>考试成绩</label></span></td>
	   			<td align="center" nowrap="nowrap"><span class="name"><label>成绩状态</label></span></td>
	   			<td ></td>
	   			<td ></td>
	   		</tr>
	   		<s:iterator value="getstuList()" status="num">
	   		<tr class="postFormBox" >
	   			<s:iterator status="i">
	   				<%--获取（id，学号，姓名，证件号码，座位号，考试成绩，成绩状态，已录入成绩，已录入成绩状态）信息--%>
	   				<s:if test="#i.index==0"><s:set name="exambookid"/></s:if>
	   				<s:elseif test="#i.index==1"><s:set name="regno"/></s:elseif>
	   				<s:elseif test="#i.index==2"><s:set name="stuname"/></s:elseif>
	   				<s:elseif test="#i.index==3"><s:set name="idcard"/></s:elseif>
	   				<s:elseif test="#i.index==4"><s:set name="seatno"/></s:elseif>
	   				<s:elseif test="#i.index==5"><s:set name="inputscore"/></s:elseif>
	   				<s:elseif test="#i.index==6"><s:set name="inputscorestatus"/></s:elseif>
	   				<s:elseif test="#i.index==7"><s:set name="oldscore"/></s:elseif>
	   				<s:elseif test="#i.index==8"><s:set name="oldscorestatus"/></s:elseif>
	   				<%--
	   				<s:if test="#i.index==0"><td ><input type="hidden" name="ids" value="<s:property />"/></td></s:if>
	   				<s:elseif test="#i.index==5">
	   					<td align="center" class="pageBottomBorder" nowrap="nowrap">
	   						<input onkeypress="checkkey(this)"  onblur="checkab('<s:property value='#num.index' />')" value="<s:property />" size="4" maxlength="5" id="sc1_<s:property value='#num.index' />" type="text" name="score" />
	   					</td>
	   				</s:elseif><s:elseif test="#i.index==6">
	   					<td align="center" nowrap="nowrap">
		   				<select name="scorestatus" id="ss1_<s:property value='#num.index' />" onblur="checkab('<s:property value='#num.index' />')">
		   					<s:set name="status"/>
		   					<s:iterator value="getScoreStatus()">
		   						<s:if test="#status.equals(id)"><option value="<s:property value='id' />" selected ><s:property value='name' /></option></s:if>
		   						<s:else><option value="<s:property value='id' />" ><s:property value='name' /></option></s:else>
		   					</s:iterator>
		   				</select></td>
	   				</s:elseif><s:elseif test="#i.index==7">
	   					<td ><input id="sc2_<s:property value='#num.index' />" type="hidden" value="<s:property />"/></td>
	   				</s:elseif><s:elseif test="#i.index==8">
	   					<td ><input id="ss2_<s:property value='#num.index' />" type="hidden" value="<s:property />"/></td>
	   				</s:elseif><s:else>
	   					<td align="center" class="<s:if test='#i.index%2==1'>pageBottomBorder</s:if>" nowrap="nowrap"><s:property /></td>
	   				</s:else>
	   				--%>
	   			</s:iterator>
	   			<td ><input type="hidden" name="ids" value="<s:property value='#exambookid' />"/></td>
	   			<td align="center" class="pageBottomBorder" nowrap="nowrap"><s:property value="#regno"/></td>
	   			<td align="center" nowrap="nowrap"><s:property value="#stuname"/></td>
	   			<td align="center" class="pageBottomBorder" nowrap="nowrap"><s:property value="#idcard"/></td>
	   			<td align="center" nowrap="nowrap"><s:property value="#seatno"/></td>
	   			<td align="center" class="pageBottomBorder" nowrap="nowrap">
					<input onkeypress="checkkey(this)" onblur="checkab('<s:property value='#num.index' />')" value="<s:property value='#inputscore' />" size="4" maxlength="5" id="sc1_<s:property value='#num.index' />" type="text" name="score" />
				</td>
	   			<td align="center" nowrap="nowrap">
	   				<select name="scorestatus" onchange="changestatus(this,<s:property value='#num.index' />)" id="ss1_<s:property value='#num.index' />" onblur="checkab('<s:property value='#num.index' />')">
	   					<s:set name="status"/>
	   					<s:iterator value="getScoreStatus()">
	   						<s:if test="#status.equals(id)"><option value="<s:property value='id' />" selected ><s:property value='name' /></option></s:if>
	   						<s:else><option value="<s:property value='id' />" ><s:property value='name' /></option></s:else>
	   					</s:iterator>
	   				</select>
				</td>
	   			<td ><input id="sc2_<s:property value='#num.index' />" type="hidden" value="<s:property value='#oldscore' />"/></td>
	   			<td ><input id="ss2_<s:property value='#num.index' />" type="hidden" value="<s:property value='#oldscorestatus' />"/></td>
	   		</tr>
	   		</s:iterator>
	   </table>
	</body>
</html>