<%@page import="java.util.*" pageEncoding="GBK"%>
<%!
String getNextJsonStr(Integer serialNo){
    StringBuffer sb2=new StringBuffer();
    try{
        sb2.append("[");
        enfo.crm.affair.TcustmanagersLocal tlocal=enfo.crm.tools.EJBFactory.getTcustmanagers();
        List list=tlocal.listBySql(serialNo);
        if (list!=null && !list.isEmpty()){
            Map map=null;
            String mgName="";
            String grade="";
			String managerName="";
			boolean isLeafNode = false;
            for(int i=0;i<list.size();i++){
            	map = (Map)list.get(i);

                sb2.append("{\"title\": \"");
                mgName=(String)map.get("LEVEL_NO")+"_"+(String)map.get("LEVEL_NAME");
				managerName = (String)map.get("MANAGERNAME");
				mgName = (mgName == null) ? "" : mgName.trim();
				managerName = (managerName == null) ? "" : managerName .trim();

				// 判断是否叶子节点
				Integer b1 = (Integer)map.get("LEFT_ID");
       			if (b1==null) b1=new Integer(0);
               		Integer b2 = (Integer)map.get("RIGHT_ID");
                if (b2==null) b2=new Integer(0);
               
				isLeafNode = (b1.intValue()+1 == b2.intValue()) ? true : false;
	
       			grade = (Integer)map.get("SERIAL_NO")+"_"+(Integer)map.get("MANAGERID");
				if (!isLeafNode) // 非叶子节点
				{
	                sb2.append(mgName + " (" + managerName + ")");
	                // 只打开第一层节点
	                if (1 == serialNo.intValue())
	                 	sb2.append("\",\"key\":\""+grade+"\", \"expanded\": true, \"folder\": true, \"children\": ");
	                else
	                	sb2.append("\",\"key\":\""+grade+"\", \"expanded\": false, \"folder\": true, \"children\": ");
		                
	                    Integer ser=(Integer)map.get("SERIAL_NO");
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
sb.append("{\"title\": \"客户经理\",\"key\":\"0\", \"expanded\": true, \"folder\": true, \"children\": ");
sb.append(getNextJsonStr(new Integer(1)));
sb.append("}]");
%>
<%=sb%>