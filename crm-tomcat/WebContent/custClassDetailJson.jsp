<%@page import="java.util.*" pageEncoding="GBK" %>
<%@ include file="/includes/operator.inc" %>
<%
StringBuffer sb=new StringBuffer();
sb.append("[");
sb.append("{\"title\": \"客户评级\", \"expanded\": true, \"folder\": true, \"children\": [");
enfo.crm.customer.CustClassDefineLocal ccdf=enfo.crm.tools.EJBFactory.getCustClassDefine();
enfo.crm.vo.CustClassDefineVO fvo=new enfo.crm.vo.CustClassDefineVO();
fvo.setClass_define_id(new Integer(0));
fvo.setLevel_id(new Integer(1));
fvo.setInput_man(input_operatorCode);
List list=ccdf.query(fvo);
enfo.crm.customer.CustClassDetailLocal ccdt=enfo.crm.tools.EJBFactory.getCustClassDetail();
enfo.crm.vo.CustClassDetailVO vo=new enfo.crm.vo.CustClassDetailVO();
vo.setInput_man(input_operatorCode);
if (list!=null && !list.isEmpty()){
	String className="";
	Map map=null;
	Map map2=null;
	Map map3=null;
	Map map4=null;
	for (int i=0;i<list.size();i++){
		map=(Map)list.get(i);
		className=(String)map.get("CLASSDEFINE_NAME");
		if (className==null) className="";
		sb.append("{\"title\": \"");
		sb.append(className);
		sb.append("\", \"expanded\": true, \"folder\": true, \"children\": [");
		vo.setClass_define_id((Integer)map.get("CLASSDEFINE_ID"));
		vo.setClass_detail_id(new Integer(0));
		List list2=ccdt.query2(vo);
		if (list2!=null || !list2.isEmpty()){//第二层
			for (int j=0;j<list2.size();j++){
				map2=(Map)list2.get(j);
				className=(String)map2.get("CLASSDETAIL_NAME");
				if (className==null) className="";
				sb.append("{\"title\": \"");
				sb.append(className);
				sb.append("\", \"expanded\": true, \"folder\": true, \"children\": [");
				vo.setClass_detail_id((Integer)map2.get("CLASSDETAIL_ID"));
				List list3=ccdt.query2(vo);
				if (list3!=null && !list3.isEmpty()){//第三层
					for (int k=0;k<list3.size();k++){
						map3=(Map)list3.get(k);
						className=(String)map3.get("CLASSDETAIL_NAME");
						if (className==null) className="";
						sb.append("{\"title\": \"");
						sb.append(className);
						sb.append("\", \"expanded\": true, \"folder\": true, \"children\": [");
						vo.setClass_define_id((Integer)map3.get("CLASSDEFINE_ID"));
						vo.setClass_detail_id(new Integer(0));
						List list4=ccdt.query2(vo);
						if (list4!=null && !list4.isEmpty()){//第四层
							for (int l=0;l<list4.size();l++){
								map4=(Map)list4.get(l);
								className=(String)map4.get("CLASSDETAIL_NAME");
								if (className==null) className="";
								sb.append("{\"title\": \"");
								sb.append(className);
								sb.append("\", \"expanded\": true, \"folder\": true, \"children\": [");
								if (l==list4.size()-1)
									sb.append("]}");
								else
									sb.append("]},");
							}
						}
						if (k==list3.size()-1)
							sb.append("]}");
						else
							sb.append("]},");
					}
				}
				if (j==list2.size()-1)
					sb.append("]}");
				else
					sb.append("]},");
			}
		}
		if (i==list.size()-1)
			sb.append("]}");
		else
			sb.append("]},");
	}
	sb.append("]}]");
}
/*
enfo.crm.customer.CustClassDetailLocal ccdt=enfo.crm.tools.EJBFactory.CustClassDetail();
enfo.crm.vo.CustClassDetailVO vo=new enfo.crm.vo.CustClassDetailVO();
vo.setClass_define_id();
params[1] = vo.getClass_detail_id();
params[2] = vo.getInput_man();
List<?> list=ccd.query2(vo);
*/
%>
<%=sb.toString() %>