<%@page import="enfo.crm.tools.*, enfo.crm.system.*,enfo.crm.vo.* ,enfo.crm.dao.*,java.util.*" pageEncoding="GBK"%>
<%!
String getNextJsonStr(Integer depart_id){
    StringBuffer sb2=new StringBuffer();
    try{
        sb2.append("[");
        DepartmentLocal tlocal = EJBFactory.getDepartment();
        List list=tlocal.listBySql(depart_id);
        if (list!=null && !list.isEmpty()){
            Map map=null;
            String mgName="";
            String grade="";
			String managerName="";
			boolean isLeafNode = false;
            for(int i=0;i<list.size();i++){
            	map = (Map)list.get(i);
                sb2.append("{\"title\": \"");
                mgName=Utility.trimNull(map.get("DEPART_ID"))+"_"+Utility.trimNull(map.get("DEPART_NAME"));
				mgName = (mgName == null) ? "" : mgName.trim();
                
				// 判断是否叶子节点
				Integer b1 = (Integer)map.get("BOTTOM_FLAG");
       			if (b1==null) b1=new Integer(0);
       			
				isLeafNode = (b1.intValue() == 2) ? true : false;
	
       			grade = (Integer)map.get("DEPART_ID")+"";
 				if (isLeafNode) // 非叶子节点
				{
	                sb2.append(mgName);
	                // 只打开第一层节点
	                if (1 == depart_id.intValue())
	                 	sb2.append("\",\"key\":\""+grade+"\", \"expanded\": true, \"folder\": true, \"children\": ");
	                else
	                	sb2.append("\",\"key\":\""+grade+"\", \"expanded\": false, \"folder\": true, \"children\": ");
		                
	                    Integer ser=(Integer)map.get("DEPART_ID");
	                    sb2.append(getNextJsonStr(ser));
		        }
				else
	                sb2.append(mgName + "\",\"key\":\""+grade+"\", \"expanded\": false, \"folder\": false"); 
 
				sb2.append("}");
				if (i!=list.size()-1) // 最后一个元素后不加","
					sb2.append(",");
           }
        }
        sb2.append("]");

    }
    catch(Exception e){
        
    }
    return sb2.toString();
}
%>
<%
StringBuffer sb=new StringBuffer();
sb.append("[");
sb.append("{\"title\": \"经理\", \"key\":\"0\",\"expanded\": true, \"folder\": true, \"children\": ");
sb.append(getNextJsonStr(new Integer(0)));
sb.append("}]");
%>
<%=sb%>