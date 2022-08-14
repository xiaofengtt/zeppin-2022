<%@ page language="java" contentType="text/html;charset=GBK" import="enfo.crm.tools.*,enfo.crm.dao.*,java.util.*"%>
<% 
String[] fieldNameStrs =(String[])request.getAttribute("fieldNameStrs");//字段名称集
String sUrl = request.getParameter("sUrl");
IPageList pageList =(IPageList)request.getAttribute("pageList");//列表数据显示集
String[] fieldStrs = (String[])request.getAttribute("fieldStrs");//字段集
List tableFieldList =(List)request.getAttribute("tableFieldList");//列表显示字段数据集
String checkName = request.getParameter("checkName");//点选需要判断的字段
String checkStr = request.getParameter("checkStr");//点选业务判断符合条件的行
List tabExcelList = new ArrayList();//导出到excel的table，6190
%>

<table id="table3" border="0" cellspacing="1" cellpadding="2"
	class="tablelinecolor" width="100%" >
	<tr class="trh">
		<%
		List trhExcelList = new ArrayList();//导出到excel的tr头部，6190
		for(int i=0;i<fieldNameStrs.length;i++){
			Map tdOneMap = (Map)tableFieldList.get(i);
			String mul_flagOne = Utility.trimNull(tdOneMap.get("MUL_FLAG"));
			String interface_fieldOne =Utility.trimNull(tdOneMap.get("INTERFACEFIELD_CODE")); 
			
			//以下5行为导出excel用，判断如果是图标类型则不再excel显示，6190
			String showType = Utility.trimNull(tdOneMap.get("SHOW_TYPE"));//显示类型
			String editType = Utility.trimNull(tdOneMap.get("EDIT_TYPE"));//编辑类型
			String inputType =ConfigUtil.getInputType(showType,editType);//元素类型
			if(!(inputType.equals("icon"))){//不为图标的元素		
				trhExcelList.add(fieldNameStrs[i]);//6190
			}
		%>
		<td class="tdh" align="center">
		<table border="0" cellspacing="0" cellpadding="0">
			<tr>
			<%if(mul_flagOne.equals("1")){%>
				<td width="10%">
					<input type="checkbox" name="<%=interface_fieldOne%><%=i%>" class="selectAllBox" onclick="javascript:selectAll(document.theform.<%=interface_fieldOne%>DEL);">
				</td>
			<%}%>
			<td align="center"><font style="font-weight: bold"><%=fieldNameStrs[i]%></font></td>
			</tr>
		</table>
		</td>
		<%
		}
		tabExcelList.add(trhExcelList);//6190
		%>
	</tr>
<%
int iCount = 0;
int iCurrent = 0;
List list = pageList.getRsList();
Map map = null;
for(int i=0;i<list.size();i++)
{
    map = (Map)list.get(i);
    List trExcelList = new ArrayList();//导出到excel的tr，6190
%>
					<tr class="tr<%=(iCurrent % 2)%>">
						<%for(int j=0;j<fieldStrs.length;j++){
							String showValue = Utility.trimNull(map.get(fieldStrs[j].toUpperCase()));//字段值
							Map fieldMap = (Map)tableFieldList.get(j);
							String valueType = Utility.trimNull(fieldMap.get("VALUE_TYPE"));
							String showType = Utility.trimNull(fieldMap.get("SHOW_TYPE"));//显示类型
							String editType = Utility.trimNull(fieldMap.get("EDIT_TYPE"));//编辑类型
							String inputType =ConfigUtil.getInputType(showType,editType);//元素类型
							String tableFieldWidth = Utility.trimNull(fieldMap.get("TABLEFIELDWIDTH"));//显示类型
							String mul_flagTwo = Utility.trimNull(fieldMap.get("MUL_FLAG"));
							String linkFlag = Utility.trimNull(fieldMap.get("LINK_FLAG"));
							String linkFunc = Utility.trimNull(fieldMap.get("LINK_FUNC"));
							String interface_fieldTwo = Utility.trimNull(fieldMap.get("INTERFACEFIELD_CODE"));
							
							if(!"".equals(valueType)){//如果为下拉框获取下拉框值
								String value_content = Utility.trimNull(fieldMap.get("VALUE_CONTENT"));
								showValue = ConfigUtil.getParamName(valueType,showValue,value_content);
							}
							if(!(inputType.equals("icon"))){//不为图标的元素						
						%>
							<td class="tdh" align="center" width="<%=tableFieldWidth%>%">
							<table border="0"  cellspacing="0" cellpadding="0">
								<tr>
								<%if(mul_flagTwo.equals("1")){
								  if((interface_fieldTwo.equals(checkName) && (checkStr.indexOf(i+",")!=-1))){//点选并符合业务逻辑
								%>
									<td align="center" width="90%">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<%=showValue%></td>
								<%}else{//点选但不符合业务逻辑%>
									<td width="10%" class="tdh">
									  <input type="checkbox" name="<%=interface_fieldTwo%>DEL" value="<%=showValue%>" class="flatcheckbox">
									</td>
									<td align="center" width="90%"><%=showValue%></td>
								<%}}else{//不是点选的赋值%>
									<%if("1".equals(linkFlag) && !"".equals(linkFunc)){
										String funcName ="";//函数名称
										String[] paramValue={}; //参数值集合
										if(!linkFunc.equals("")){//图标函数不为空
											String[] funcStrs= linkFunc.split("##");//分割获取函数名称和函数参数
											if(funcStrs.length==2){//正常写法时的处理
												funcName=funcStrs[0];//函数名称
												paramValue=ConfigUtil.iconParamDeal(map,paramValue,funcStrs[1]);//获取参数值集
											}
										}									
									%>
										<td align="center" width="90%"><a href="javascript:<%=funcName%>(
											<%for(int n=0;n<paramValue.length;n++){
												if(n==0){
											%>
											'<%=paramValue[n]%>'
											<%}else{%>
											,'<%=paramValue[n]%>'
											<%}}%>
											)"><%=showValue%>
											</a></td>
									<%}else{
										if(showValue!=null && showValue.length()>20 &&"7".equals(editType)){
									%>
										<td align="center" width="90%"><%=showValue.substring(0,20)+"..."%></td>
										<%}else{%>
										 <td align="center" width="90%"><%=showValue%></td>
									<% }
									}%>
								<%}%>
								</tr>
							</table>
							</td>
						<%
							trExcelList.add(showValue);//导出excel的存储，6190
						}else{//图标按钮处理
							String iconfunc = Utility.trimNull(fieldMap.get("ICONFUNC"));//图标函数
							String iconurl = Utility.trimNull(fieldMap.get("ICONURL"));//图标地址
							String interfaceField_name = Utility.trimNull(fieldMap.get("INTERFACEFIELD_NAME"));
							String funcName ="";//函数名称
							String[] paramValue={}; //参数值集合
							if(!iconfunc.equals("")){//图标函数不为空
								String[] funcStrs= iconfunc.split("##");//分割获取函数名称和函数参数
								if(funcStrs.length==2){//正常写法时的处理
									funcName=funcStrs[0];//函数名称
									paramValue=ConfigUtil.iconParamDeal(map,paramValue,funcStrs[1]);//获取参数值集
								}
							}
						%>
						<td align="center" width="<%=tableFieldWidth%>%">
							<a href="javascript:void(0);" name="href999" onclick="javascript:<%=funcName%>(
							<%for(int n=0;n<paramValue.length;n++){
								if(n==0){
							%>
							'<%=paramValue[n]%>'
							<%}else{%>
							,'<%=paramValue[n]%>'
							<%}}%>
							);return false;">
								<img src="<%=iconurl%>" width="16" height="16" title="<%=interfaceField_name%>" />
							</a>
						</td>
						<%}}%>	
					</tr>
<%
iCurrent++;
iCount++;
tabExcelList.add(trExcelList);//导出列表到excel，6190
}
request.getSession().setAttribute("efvbankTabExcelList", tabExcelList);//导出列表到excel的数组保存到session，6190
%>

<%
for (int i=0;i < pageList.getBlankSize(); i++)
{
%>
			<tr class="tr<%=(i % 2)%>">
				<td class="tdh" align="center"></td>
				<%for(int j=0;j<fieldNameStrs.length-1;j++){ %>
					<td></td>
				<% }%>
			</tr>
<%}
%>
			<tr class="tr1">
				<td class="tdh" align="center"><b>合计 <%=pageList.getTotalSize()%> 项</b></td>
				<%for(int j=0;j<fieldNameStrs.length-1;j++){ %>
					<td align="center" class="tdh"></td>
				<% }%>
			</tr>
		</table>
		<br>
		<table border="0" width="100%" id=tableBottom99>
			<tr valign="top">
				<td><%=pageList.getPageLink(sUrl)%></td>
			</tr>
		</table>