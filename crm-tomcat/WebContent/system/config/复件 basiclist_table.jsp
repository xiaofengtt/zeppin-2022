<%@ page language="java" contentType="text/html;charset=GBK" import="enfo.crm.tools.*,enfo.crm.dao.*,java.util.*"%>
<% 
String[] fieldNameStrs =(String[])request.getAttribute("fieldNameStrs");//�ֶ����Ƽ�
String sUrl = request.getParameter("sUrl");
IPageList pageList =(IPageList)request.getAttribute("pageList");//�б�������ʾ��
String[] fieldStrs = (String[])request.getAttribute("fieldStrs");//�ֶμ�
List tableFieldList =(List)request.getAttribute("tableFieldList");//�б���ʾ�ֶ����ݼ�
String checkName = request.getParameter("checkName");//��ѡ��Ҫ�жϵ��ֶ�
String checkStr = request.getParameter("checkStr");//��ѡҵ���жϷ�����������
List tabExcelList = new ArrayList();//������excel��table��6190
%>

<table id="table3" border="0" cellspacing="1" cellpadding="2"
	class="tablelinecolor" width="100%" >
	<tr class="trh">
		<%
		List trhExcelList = new ArrayList();//������excel��trͷ����6190
		for(int i=0;i<fieldNameStrs.length;i++){
			Map tdOneMap = (Map)tableFieldList.get(i);
			String mul_flagOne = Utility.trimNull(tdOneMap.get("MUL_FLAG"));
			String interface_fieldOne =Utility.trimNull(tdOneMap.get("INTERFACEFIELD_CODE")); 
			
			//����5��Ϊ����excel�ã��ж������ͼ����������excel��ʾ��6190
			String showType = Utility.trimNull(tdOneMap.get("SHOW_TYPE"));//��ʾ����
			String editType = Utility.trimNull(tdOneMap.get("EDIT_TYPE"));//�༭����
			String inputType =ConfigUtil.getInputType(showType,editType);//Ԫ������
			if(!(inputType.equals("icon"))){//��Ϊͼ���Ԫ��		
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
    List trExcelList = new ArrayList();//������excel��tr��6190
%>
					<tr class="tr<%=(iCurrent % 2)%>">
						<%for(int j=0;j<fieldStrs.length;j++){
							String showValue = Utility.trimNull(map.get(fieldStrs[j].toUpperCase()));//�ֶ�ֵ
							Map fieldMap = (Map)tableFieldList.get(j);
							String valueType = Utility.trimNull(fieldMap.get("VALUE_TYPE"));
							String showType = Utility.trimNull(fieldMap.get("SHOW_TYPE"));//��ʾ����
							String editType = Utility.trimNull(fieldMap.get("EDIT_TYPE"));//�༭����
							String inputType =ConfigUtil.getInputType(showType,editType);//Ԫ������
							String tableFieldWidth = Utility.trimNull(fieldMap.get("TABLEFIELDWIDTH"));//��ʾ����
							String mul_flagTwo = Utility.trimNull(fieldMap.get("MUL_FLAG"));
							String linkFlag = Utility.trimNull(fieldMap.get("LINK_FLAG"));
							String linkFunc = Utility.trimNull(fieldMap.get("LINK_FUNC"));
							String interface_fieldTwo = Utility.trimNull(fieldMap.get("INTERFACEFIELD_CODE"));
							
							if(!"".equals(valueType)){//���Ϊ�������ȡ������ֵ
								String value_content = Utility.trimNull(fieldMap.get("VALUE_CONTENT"));
								showValue = ConfigUtil.getParamName(valueType,showValue,value_content);
							}
							if(!(inputType.equals("icon"))){//��Ϊͼ���Ԫ��						
						%>
							<td class="tdh" align="center" width="<%=tableFieldWidth%>%">
							<table border="0"  cellspacing="0" cellpadding="0">
								<tr>
								<%if(mul_flagTwo.equals("1")){
								  if((interface_fieldTwo.equals(checkName) && (checkStr.indexOf(i+",")!=-1))){//��ѡ������ҵ���߼�
								%>
									<td align="center" width="90%">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<%=showValue%></td>
								<%}else{//��ѡ��������ҵ���߼�%>
									<td width="10%" class="tdh">
									  <input type="checkbox" name="<%=interface_fieldTwo%>DEL" value="<%=showValue%>" class="flatcheckbox">
									</td>
									<td align="center" width="90%"><%=showValue%></td>
								<%}}else{//���ǵ�ѡ�ĸ�ֵ%>
									<%if("1".equals(linkFlag) && !"".equals(linkFunc)){
										String funcName ="";//��������
										String[] paramValue={}; //����ֵ����
										if(!linkFunc.equals("")){//ͼ�꺯����Ϊ��
											String[] funcStrs= linkFunc.split("##");//�ָ��ȡ�������ƺͺ�������
											if(funcStrs.length==2){//����д��ʱ�Ĵ���
												funcName=funcStrs[0];//��������
												paramValue=ConfigUtil.iconParamDeal(map,paramValue,funcStrs[1]);//��ȡ����ֵ��
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
							trExcelList.add(showValue);//����excel�Ĵ洢��6190
						}else{//ͼ�갴ť����
							String iconfunc = Utility.trimNull(fieldMap.get("ICONFUNC"));//ͼ�꺯��
							String iconurl = Utility.trimNull(fieldMap.get("ICONURL"));//ͼ���ַ
							String interfaceField_name = Utility.trimNull(fieldMap.get("INTERFACEFIELD_NAME"));
							String funcName ="";//��������
							String[] paramValue={}; //����ֵ����
							if(!iconfunc.equals("")){//ͼ�꺯����Ϊ��
								String[] funcStrs= iconfunc.split("##");//�ָ��ȡ�������ƺͺ�������
								if(funcStrs.length==2){//����д��ʱ�Ĵ���
									funcName=funcStrs[0];//��������
									paramValue=ConfigUtil.iconParamDeal(map,paramValue,funcStrs[1]);//��ȡ����ֵ��
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
tabExcelList.add(trExcelList);//�����б�excel��6190
}
request.getSession().setAttribute("efvbankTabExcelList", tabExcelList);//�����б�excel�����鱣�浽session��6190
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
				<td class="tdh" align="center"><b>�ϼ� <%=pageList.getTotalSize()%> ��</b></td>
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