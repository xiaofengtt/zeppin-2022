<%@ page language="java" pageEncoding="GBK" import="java.util.*,java.math.*,enfo.crm.system.*,enfo.crm.vo.*, enfo.crm.customer.*,enfo.crm.tools.*,enfo.crm.dao.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>
<%
int cell_flag = Utility.parseInt(request.getParameter("cell_flag"),1);
Integer cell_id = Utility.parseInt(request.getParameter("cell_id"),new Integer(0));
String arrayCustId = Utility.trimNull(request.getParameter("cust_id"));
String charValue = Utility.trimNull(request.getParameter("charValue"),"0��0��0��0��0��0��");
Integer product_id = Utility.parseInt(request.getParameter("product_id"),new Integer(0));
int x_name = Utility.parseInt(request.getParameter("x_name"),1);
int y_name = Utility.parseInt(request.getParameter("y_name"),1);
int sexType = Utility.parseInt(request.getParameter("sexType"),1);
int changeFlag = Utility.parseInt(request.getParameter("changeFlag"),1);
CustomerLocal local = EJBFactory.getCustomer();
Integer request_start_date = Utility.parseInt(request.getParameter("rg_begin_date"),new Integer(2001));
Integer request_end_date = Utility.parseInt(request.getParameter("rg_end_date"),new Integer(2011)); 
List list = null;
//x��Ϊ �ͻ����� 
if(sexType!=3){//������״ͼ ����ͼ
	if(x_name==1||x_name==3 ||x_name==4 ||x_name==8||x_name==5){
	 list = local.queryChart2(product_id,arrayCustId,x_name,y_name,cell_flag,cell_id);
	}else if(x_name==2){ 
	 //y���ȡֵ y_name ���ж�
	 list = local.queryChart(product_id,arrayCustId,y_name,charValue);
	}else if(x_name==6||x_name==7||x_name==9){
	 list = local.queryChart3(product_id,arrayCustId,x_name,charValue);   
	}
}else{
	list = local.queryChart4(product_id,arrayCustId,changeFlag,y_name,request_start_date,request_end_date); 
}
 //����ͼ���� �漰ʱ��Ľ�ȡ��

int start_date_flag = 0;
int end_date_flag = 0;
int year_flag = Utility.parseInt(request.getParameter("year_flag"),1);
String[] yearArray = null;
%>

<html>
  <head>
  	<TITLE></TITLE>
  	<meta http-equiv="X-UA-Compatible" content="IE=7" >
	<META http-equiv=Content-Type content="text/html; charset=gbk">
	<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
	<META HTTP-EQUIV="Expires" CONTENT="0">
	<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
	<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css
		rel=stylesheet>
	<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
	<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
	<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>
	<script type="text/javascript">
		function queryChart()
		{
			var x_name = document.theform.x_name.value
		   	var y_name = document.theform.y_name.value
		   	
			var mytable = document.getElementById("mybody");   
		    var len = mytable.rows.length; 
		    var charValue = '';
		    if(x_name==2||x_name==6||x_name==7||x_name==9)//���䣬�Ϲ����������
		    {
		    	
			    for (var j = 0; j < len; j++) 
			    {   
			        if(document.getElementById("x_start_"+j).value=='' || document.getElementById("x_end_"+j).value==''){
			        	sl_alert("����α��������ݲ���!");
			        	return false;
			        }
			        charValue = document.getElementById("x_start_"+j).value+'��'+document.getElementById("x_end_"+j).value+'��'+charValue;   
			    }
			}		    
			var sexType = 0;
			var changeFlag = 0;
			if(document.theform.sexType[0].checked)
				sexType = 1;
			if(document.theform.sexType[1].checked)
				sexType = 2;
			if(document.theform.sexType[2].checked)//����
			{
				sexType = 3;
				if(document.theform.changeFlag[0].checked)
					changeFlag= 1;
				if(document.theform.changeFlag[1].checked)
					changeFlag=2;  	
				if(document.theform.changeFlag[2].checked)
					changeFlag=3;
					
				syncDatePicker(theform.birthday_picker, theform.x_start_year);
				syncDatePicker(theform.endTime_picker, theform.x_end_year);	
				if(document.getElementById("x_start_year").value=='' || document.getElementById("x_end_year").value=='')
				{
		        	sl_alert("����ϱ��������ݲ���!");
		        	return false;
		        }	
			}	
			location = 'view_chart.jsp?product_id=<%=product_id%>&charValue='+charValue+'&x_name='+x_name
				+'&y_name='+y_name+'&sexType='+sexType+'&cust_id='+document.theform.cust_id.value+'&changeFlag='+changeFlag
				+'&rg_begin_date='+document.theform.x_start_year.value
				+'&rg_end_date='+document.theform.x_end_year.value
				+'&cell_flag=<%=cell_flag%>&cell_id=<%=cell_id%>';
		}
		
		function changeX(flag)
		{ 
			if(flag==1 || flag==2)
			{
				document.getElementById("cavre").style.display='none';
				document.getElementById("notCavre").style.display='';
				document.getElementById("year_section").style.display = 'none';
			}else{
				document.getElementById("cavre").style.display='';
				document.getElementById("notCavre").style.display='none';
				document.getElementById("sectionID").style.display = '';
				document.getElementById("year_section").style.display = '';
				document.getElementById("mybody").style.display = 'none';
			}
		}
		
		//��״ͼ����ͼ��ʱ����ʾ���䣬�Ϲ��������� ��Ҫ�����
		function changeSection(flag)
		{   
			if(flag==2||flag==9)
			{  
				document.getElementById("sectionID").style.display = '';
				document.getElementById("y_shaft").style.display = '';
				document.getElementById("year_section").style.display = 'none';
				document.getElementById("mybody").style.display = '';
			}else if(flag==6 || flag==7){
				document.getElementById("sectionID").style.display = '';
				document.getElementById("y_shaft").style.display = 'none';
				document.getElementById("year_section").style.display = 'none';
				document.getElementById("mybody").style.display = '';
			}else{
				document.getElementById("sectionID").style.display = 'none';
				document.getElementById("y_shaft").style.display = '';
				document.getElementById("year_section").style.display = 'none';
				document.getElementById("mybody").style.display = 'none';
			}
		}
		
		//���������
		function addRow()
		{
			var oTrArr = document.getElementById("mybody").rows;
			var curRowCount;
			curRowCount = oTrArr.length
			var oTr = document.getElementById("mybody").insertRow(oTrArr.length);//�ڵ�ǰ������ĩβ-����һ��
			//���ò����е���ʽ
			oTr.setAttribute("id","tr_"+curRowCount);
			oTr.setAttribute("className","tr0");
			
			var oTdA = oTr.insertCell(0);//Tr�еĵ�1����Ԫ��-AATypeno�����
			var oTdB = oTr.insertCell(1);//Tr�еĵ�2����Ԫ��-AADetailno�����

			
			//Ϊ2����Ԫ����������
		
			//д�������Ԫ���ڲ��������
			oTdA.innerHTML = "��<input type=\"text\" id=\"x_start_" + curRowCount + "\" name=\"x_start_" + curRowCount + "\" size=\"20\" value=\"\">";
			oTdB.innerHTML = "&nbsp;��<input type=\"text\" id=\"x_end_" + curRowCount + "\" name=\"x_end_" + curRowCount + "\" size=\"20\" value=\"\">&nbsp;<a id=\"remove_" + curRowCount + "\" name=\"remove_" + curRowCount + "\" href=\"javascript:removeRow('tr_" + curRowCount + "');\" onkeydown=\"javascript:nextKeyPress(this);\"><img src=\"/images/dev_remove.gif\" width=\"24\" height=\"24\" title=\"ɾ��\"></a>";
		}
		
		//ɾ����
		function removeRow(ObjID){
			var oTr = document.getElementById(ObjID);
			var oParent = oTr.parentNode;
			oParent.removeChild(oTr);
		}
		
		window.onload =function(){	
			<%if(sexType==3){%>
				document.getElementById("notCavre").style.display ="none" ;
			<%}%>
		
		}
	</script>
  </head>
  <body>
  <form name="theform" action="">
  	<input type="hidden" name="cust_id" value="<%=arrayCustId%>">
    <fieldset>
    	<legend>ͼ��ѡ��</legend>
    	<span>
    		<input type="radio" class="flatcheckbox" name="sexType" value="1" onclick="javascript:changeX(this.value);" <%if(sexType==1) out.print("checked");%>>��&nbsp;ͼ&nbsp;&nbsp;
    		<input type="radio" class="flatcheckbox" name="sexType" value="2" onclick="javascript:changeX(this.value);" <%if(sexType==2) out.print("checked");%>>��״ͼ&nbsp;&nbsp;
    		<input type="radio" class="flatcheckbox" name="sexType" value="3" onclick="javascript:changeX(this.value);" <%if(sexType==3) out.print("checked");%>>����ͼ
    		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    		<!--����-->
				<button type="button"  class="xpbutton3" accessKey=b id="btnSave" name="btnSave"
                    onclick="javascript:window.returnValue=null;location.href='client_query_list.jsp';"><%=LocalUtilis.language("message.back",clientLocale)%> (<u>B</u>)</button>
    	</span>
    </fieldset>

   <fieldset>
   		<legend>X��,Y��Ķ���:</legend>
   		<div>
	   		<span id="notCavre" <%if(sexType==3){%>style="display: none;"<%} %>>
	   			<font color="red" size="4">X��</font>��
	   			<select name="x_name" style="width: 120px;" onchange="javascript:changeSection(this.value);">
   					<option value="1" <%if(x_name==1) out.print("selected");%>>�ͻ����</option>
   					<option value="2" <%if(x_name==2) out.print("selected");%>>�ͻ�����</option>
   					<option value="3" <%if(x_name==3) out.print("selected");%>>���յȼ�</option>
   					<option value="4" <%if(x_name==4) out.print("selected");%>>��&nbsp;&nbsp;��</option>
   					<option value="5" <%if(x_name==5) out.print("selected");%>>���򻮷�</option>
   					<option value="6" <%if(x_name==6) out.print("selected");%>>������</option>
   					<option value="7" <%if(x_name==7) out.print("selected");%>>�������</option>
   					<option value="8" <%if(x_name==8) out.print("selected");%>>��&nbsp;&nbsp;Ʒ</option>
   					<option value="9" <%if(x_name==9) out.print("selected");%>>�ۼƵ÷�</option>
   				</select>
	   		</span>
	   		<span id="cavre" <%if(sexType!=3){%>style="display: none;"<%} %>>
	   			<font color="red" size="4">X��</font>��
	   			<input type="radio" class="flatcheckbox" name="changeFlag" value="1" <%if(changeFlag==1) out.print("checked");%>/>��&nbsp;&nbsp;��
	   			<input type="radio" class="flatcheckbox" name="changeFlag" value="2" <%if(changeFlag==2) out.print("checked");%>/>��&nbsp;&nbsp;��
	   			<input type="radio" class="flatcheckbox" name="changeFlag" value="3" <%if(changeFlag==3) out.print("checked");%>/>��&nbsp;&nbsp;��
	   		</span>
	   		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	   		<span id="y_shaft">
	   			<font color="red" size="4">Y��</font>��
   				<select name="y_name" style="width: 120px;">
   					<option value="1" <%if(y_name==1) out.print("selected");%>>������</option>
   					<option value="2" <%if(y_name==2) out.print("selected");%>>�������</option>
   					<option value="3" <%if(y_name==3) out.print("selected");%>>�ۼƵ÷�</option>
   				</select>
	   		</span>
	   		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	   		<button type="button"  class="xpbutton3" name="btnQuery" accessKey="q"
				onclick="javascript:queryChart();">�鿴(<u>Q</u>)</button>
   		</div>
   </fieldset>
   <fieldset id="sectionID"  <%if((x_name==1||x_name==3||x_name==4||x_name==5||x_name==8)&&sexType!=3){%>style="display: none;"<%} %>>
   		<legend>X�������</legend>
   		<input type="hidden" name="maxID" value="3">
		<table id="mybody" <%if(sexType==3){%>style="display: none;"<%} %>>
		<%String[] valueAll  = Utility.splitString(charValue,"��");
		 String[] x_interval_field = new String[2]; 
		 for(int p=0;p<valueAll.length;p++){
		 	x_interval_field = Utility.splitString(valueAll[p],"��");
		%>
			<tr id="tr_<%=p%>" class="tr0">
				<td>��<input type="text" id="x_start_<%=p%>" name="x_start_<%=p%>" value="<%=Utility.trimNull(x_interval_field[0])%>" size="20"/></td>
				<td>&nbsp;��<input type="text" id="x_end_<%=p%>" name="x_end_<%=p%>" value="<%=Utility.trimNull(x_interval_field[1])%>" size="20"/>
					&nbsp;
					<%if(p==0){%>
					<a id="addRowCount" name="addRowCount" href="javascript:addRow()" onkeydown="javascript:nextKeyPress(this);">
						<img src="/images/dev_add.gif" width="24" height="24" title="ɾ��">
					</a>
					<%}if(p>2){%>
					<a id="remove_<%=p%>" name="remove_<%=p%>" href="javascript:removeRow('tr_')" onkeydown="javascript:nextKeyPress(this);">
						<img src="/images/dev_add.gif" width="24" height="24" title="ɾ��">
					</a>
					<%}%>
				</td>
			</tr>
		<%} %>				
		</table>
		<span id="year_section" <%if(sexType!=3){%>style="display: none;"<%} %>>
			��
			<INPUT TYPE="text" NAME="birthday_picker" class=selecttext	 
				onkeydown="javascript:nextKeyPress(this)" value="<%=Format.formatDateLine(request_start_date) %>">
			<INPUT TYPE="button" value="��" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.birthday_picker,theform.birthday_picker.value,this);" tabindex="13">
			<input type="hidden" id="x_start_year" name="x_start_year"/>
			&nbsp;��
			<INPUT TYPE="text" NAME="endTime_picker" class=selecttext	 
				onkeydown="javascript:nextKeyPress(this)" value="<%=Format.formatDateLine(request_end_date) %>">
			<INPUT TYPE="button" value="��" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.endTime_picker,theform.endTime_picker.value,this);" tabindex="13">
			<INPUT TYPE="hidden" d="x_end_year" NAME="x_end_year">
		</span>
   </fieldset>
   <fieldset>
   		<legend>ͼ����ʾ</legend>
   		<div>
			<%
			  String[] xAxisValue = new String[list.size()];
			  String[] yAxisValue = new String[list.size()];
			  String messager_1 = "test";
			  
			 //����
			 String[] ItemName = new String[1];
			 String[] yAxisValue2 = new String[list.size()];
			 String[][] xAxisValue2 = new String[list.size()][1];
			 BigDecimal test1 = new BigDecimal(0.00);
			
			if(x_name==1)
				messager_1 = "�ͻ����";	
			else if(x_name==2)
				messager_1 = "�ͻ�����";
			else if(x_name==3)
				messager_1 = "���յȼ�";
			else if(x_name==4)
				messager_1 = "�Ա�";
			else if(x_name==5)
				messager_1 = "���򻮷�";
			else if(x_name==6)
				messager_1 = "������";
			else if(x_name==7)
				messager_1 = "�������";
			else if(x_name==8)
				messager_1 = "��Ʒ";
			else if(x_name==9)
				messager_1 = "�ۼƵ÷�";
			for(int i=0;i<list.size();i++){
				Map map = (Map)list.get(i);
				if(sexType==1||sexType==2){ //��ͼ ����״ͼ
					xAxisValue[i] = Utility.trimNull(map.get("INTERVAL_FIELD")+"("+map.get("CUST_NUM")+"��)");
					yAxisValue[i] = Format.formatMoneyD(Utility.trimNull(map.get("TOTAL_MONEY")));
				}else{
					if(changeFlag==2){
						yAxisValue2[i] = Utility.trimNull(map.get("INTERVAL_FIELD")+"-��"+map.get("CUST_NUM")+"����");
						messager_1 = "������ͳ�� "+request_start_date+"-"+request_end_date;
					}if(changeFlag==1){
						messager_1 = "����ͳ�� "+request_start_date+"-"+request_end_date;
						yAxisValue2[i] = Utility.trimNull(map.get("INTERVAL_FIELD"));
					}if(changeFlag==3){
						messager_1 = "����ͳ�� "+request_start_date+"-"+request_end_date;
						yAxisValue2[i] = Utility.trimNull(map.get("INTERVAL_FIELD"));
					}						 
					xAxisValue2[i][0] = Utility.trimNull(map.get("TOTAL_MONEY")); 
				}	 							
			} 

			FusionCharts Chart = new FusionCharts();
			FusionChartsGanerate ChartCreator = new FusionChartsGanerate();
			//��״ͼ
			String XMLStr = ChartCreator.ganerateColumn3D(xAxisValue,yAxisValue,"","","��"+messager_1+"ͳ��","");
			int height = 51*10 + 27; 
			String chartHTMLCode = Chart.createChartHTML(request.getContextPath()+"/includes/charts/Column3D.swf","",XMLStr,"ENFO",880,height,false);	
			
			//��ͼ
			String XMLStr1 = ChartCreator.ganeratePie3D(xAxisValue,yAxisValue,yAxisValue,"��"+messager_1+"ͳ��","","");
			String chartHTMLCode1 = Chart.createChartHTML(request.getContextPath()+"/includes/charts/Pie3D.swf","",XMLStr1,"ENFO",660,height,false); 
			
			//����ͼ
			String XMLStr2 = ChartCreator.generateMutiLine(ItemName,yAxisValue2,xAxisValue2,"1",messager_1,"");
			String chartHTMLCode2 = Chart.createChartHTML(request.getContextPath()+"/includes/charts/MSLine.swf","",XMLStr2,"ENFO",860,height,false);
					  			
   			if(sexType==1){
   			%> 
   				<%=chartHTMLCode1 %>
   			<%}else if(sexType==2){%>  			
   				<%=chartHTMLCode %>
   			<%}else if(sexType==3){ %>
   				<%=chartHTMLCode2%>
   			<%} %>
   		</div>
   </fieldset>
  </form>   	
  </body>
</html>
