<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.tools.*, enfo.crm.vo.* ,enfo.crm.dao.*,enfo.crm.system.*,java.util.*" %>
<% 
List elementTdShowList = (List)request.getAttribute("elementTdShowList");
%>
<script language="javascript">
function applySubmitValidate(){
	<%for(int i=0;i<elementTdShowList.size();i++){%>
		var requiredflag = true;
		<%Map valMap = (Map)elementTdShowList.get(i);
		String catalog_codeSub = Utility.trimNull(valMap.get("CATALOG_CODE"));
		String element_code = Utility.trimNull(valMap.get("ELEMENT_CODE"));
		String element_name = Utility.trimNull(valMap.get("ELEMENT_NAME"));
		String order_no = Utility.trimNull(valMap.get("ORDER_NO"));
		String required_flag = Utility.trimNull(valMap.get("REQUIRED_FLAG"));
		String show_type =Utility.trimNull(valMap.get("SHOW_TYPE"));
		String edit_type =Utility.trimNull(valMap.get("EDIT_TYPE"));
		String inputTypeVal = ConfigUtil.getInputType(show_type,edit_type);
		if(!edit_type.equals("8")){
		if(required_flag.equals("1")){//�����ֶν��п�У��
				if(inputTypeVal.equals("checkbox")||inputTypeVal.equals("radio")){//checkbox��radio����֤
				%>
					var valElement =document.getElementsByName('<%=catalog_codeSub+element_code+order_no%>');
					for(j=0;j<valElement.length;j++){
						if(valElement[j].checked){//�Ƿ���ڹ�ѡԪ��
							requiredflag=false;
							break;
						}
					}
					if(requiredflag){
						alert('��<%=element_name%>���Ǳ����' + '����Ϊ�գ�');
						return false;
					}
				<%}else{%>//text��select�������������֤
					var elementValue = document.getElementsByName('<%=element_code%>')[0].value;
					if((elementValue == '') || (elementValue =='null')){//Ԫ�ؿ�ֵΪ��ʱ
						alert('��<%=element_name%>���Ǳ����' + '����Ϊ�գ�');
						document.getElementsByName('<%=element_code%>')[0].focus();
						return false;
					}
				<%}
			}}
		}%>
		return true;
	}
</script>