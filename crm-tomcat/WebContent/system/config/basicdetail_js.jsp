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
		if(required_flag.equals("1")){//必输字段进行空校验
				if(inputTypeVal.equals("checkbox")||inputTypeVal.equals("radio")){//checkbox、radio框验证
				%>
					var valElement =document.getElementsByName('<%=interfaceField_code%>');
					for(j=0;j<valElement.length;j++){
						if(valElement[j].checked){//是否存在勾选元素
							requiredflag=false;
							break;
						}
					}
					if(requiredflag){
						alert('【<%=interfaceField_name%>】是必输项，' + '不能为空！');
						//document.getElementsByName('<%=interfaceField_code%>').focus();
						return false;
					}
				<%}else{%>//text、select等其他框必输验证
					var elementValue = document.getElementsByName('<%=interfaceField_code%>')[0].value;
					if((elementValue == '') || (elementValue =='null')){//元素框值为空时
						alert('【<%=interfaceField_name%>】是必输项，' + '不能为空！');
						document.getElementsByName('<%=interfaceField_code%>')[0].focus();
						return false;
					}
				<%}
			}
		}%>
		return true;
}

</script>
