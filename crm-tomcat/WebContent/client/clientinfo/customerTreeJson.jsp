<%@ page pageEncoding="GBK" import="java.util.*,java.math.*,enfo.crm.affair.*,enfo.crm.intrust.*,enfo.crm.system.*,enfo.crm.vo.*, enfo.crm.customer.*,enfo.crm.tools.*,enfo.crm.dao.*" %>
<%!
String getNextJsonStr(Integer group_id,Integer input_operatorCode){
    StringBuffer sb2=new StringBuffer();
    try{
        sb2.append("[");
        
        CustGroupLocal local = EJBFactory.getCustGroup();
        CustGroupVO vo = new CustGroupVO();
        vo.setGroupId(group_id);
        vo.setInputMan(input_operatorCode);
        List list = local.queryAll(vo);
        
        if (list!=null && !list.isEmpty()){
            Map map=null;
            String mgName="";
            String grade="";
			boolean isLeafNode = false;
            for(int i=0;i<list.size();i++){
            	map = (Map)list.get(i);
                sb2.append("{\"title\": \"");
                mgName=(Integer)map.get("GroupID")+"_"+(String)map.get("GroupName");
 				mgName = (mgName == null) ? "" : mgName.trim();

				// 判断是否叶子节点
				Integer b1 = (Integer)map.get("LEFT_ID");
       			if (b1==null) b1=new Integer(0);
               		Integer b2 = (Integer)map.get("RIGHT_ID");
                if (b2==null) b2=new Integer(0);
               
				isLeafNode = (b1.intValue()+1 == b2.intValue()) ? true : false;
	
       			grade = (Integer)map.get("GroupID")+"";
				if (!isLeafNode) // 非叶子节点
				{
	                sb2.append(mgName);
	                // 只打开第一层节点
	                if (1 == group_id.intValue())
	                 	sb2.append("\",\"key\":\""+grade+"\", \"expanded\": true, \"folder\": true, \"children\": ");
	                else
	                	sb2.append("\",\"key\":\""+grade+"\", \"expanded\": false, \"folder\": true, \"children\": ");
		                
	                    Integer ser=(Integer)map.get("GroupID");
	                    sb2.append(getNextJsonStr(ser,input_operatorCode));
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
sb.append("{\"title\": \"客户群组\",\"key\":\"0\", \"expanded\": true, \"folder\": true, \"children\": ");
sb.append(getNextJsonStr(new Integer(1),new Integer(0)));
sb.append("}]");
%>
<%=sb%>