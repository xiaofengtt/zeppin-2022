<%@ page language="java" contentType="text/html;charset=GBK" import="enfo.crm.tools.*,java.util.*"%>
<% 
List fieldShowList =(List)request.getAttribute("fieldShowList");
boolean bSuccess = ((Boolean)request.getAttribute("bSuccess")).booleanValue();
%>
<script language="javascript">
function submitValidate(){
	<%for(int i=0;i<fieldShowList.size();i++){%>
		var requiredflag = true;
		<%Map valMap = (Map)fieldShowList.get(i);
		String interfaceField_code = Utility.trimNull(valMap.get("INTERFACEFIELD_CODE"));
		String interfaceField_name = Utility.trimNull(valMap.get("INTERFACEFIELD_NAME"));
		String required_flag = Utility.trimNull(valMap.get("REQUIRED_FLAG"));
		String show_type =Utility.trimNull(valMap.get("SHOW_TYPE"));
		String edit_type =Utility.trimNull(valMap.get("EDIT_TYPE"));
		String inputTypeVal = ConfigUtil.getInputType(show_type,edit_type);
		if(required_flag.equals("1")){//�����ֶν��п�У��
				if(inputTypeVal.equals("checkbox")||inputTypeVal.equals("radio")){//checkbox��radio����֤
				%>
					var valElement =document.getElementsByName('<%=interfaceField_code%>');
					for(j=0;j<valElement.length;j++){
						if(valElement[j].checked){//�Ƿ���ڹ�ѡԪ��
							requiredflag=false;
							break;
						}
					}
					if(requiredflag){
						alert('��<%=interfaceField_name%>���Ǳ����' + '����Ϊ�գ�');
						//document.getElementsByName('<%=interfaceField_code%>').focus();
						return false;
					}
				<%}else{%>//text��select�������������֤
					var elementValue = document.getElementsByName('<%=interfaceField_code%>')[0].value;
					if((elementValue == '') || (elementValue =='null')){//Ԫ�ؿ�ֵΪ��ʱ
						alert('��<%=interfaceField_name%>���Ǳ����' + '����Ϊ�գ�');
						document.getElementsByName('<%=interfaceField_code%>')[0].focus();
						return false;
					}
				<%}
			}
		}%>
		return true;
}

</script>
