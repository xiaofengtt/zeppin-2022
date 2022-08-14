<%@page import="java.util.*,java.math.*,enfo.crm.affair.*,enfo.crm.intrust.*,enfo.crm.system.*,enfo.crm.vo.*, enfo.crm.customer.*,enfo.crm.tools.*,enfo.crm.dao.*" pageEncoding="GBK"%>
<%!
String getNextJsonStr(Integer input_operatorCode,String condition){
    StringBuffer sb2=new StringBuffer();
    try{
        sb2.append("[");
        
        CustClassDefineLocal local = EJBFactory.getCustClassDefine();
        CustClassDefineVO vo = new CustClassDefineVO();
        CustClassDetailLocal detail_local = EJBFactory.getCustClassDetail();
        CustClassDetailVO detail_vo = new CustClassDetailVO();
        
        String [] items = Utility.splitString(condition, "$");

        Integer define_id = new Integer(0);//�ּ�ID
        int level_id = 1;//���Ĳ㼶
        Integer detail_id = new Integer(0);//�ּ���ϸID
        if(items.length>0)
        {
        	define_id = Utility.parseInt(items[0], new Integer(0));
        	level_id = Utility.parseInt(items[1], 1);
        	detail_id = Utility.parseInt(items[2], new Integer(0));
        }
        
        List list = new ArrayList();

        //���level_idΪ�����������define�����level_idΪż���������detail��
        if(level_id % 2 != 0)
        {
        	vo.setClass_define_id(new Integer(0));
        	vo.setClass_define_name("");
        	if(level_id == 1)
        		vo.setLevel_id(new Integer(1));
        	else
        		vo.setLevel_id(new Integer(0));
        	vo.setParent_id(define_id);
        	vo.setParent_value(detail_id);
        	vo.setCanmodi(new Integer(0));
        	vo.setInput_man(input_operatorCode);
        	vo.setCD_no(new Integer(1));
        	list = local.query(vo);
        }else{
        	detail_vo.setClass_define_id(define_id);
        	detail_vo.setClass_detail_id(detail_id);
        	detail_vo.setInput_man(input_operatorCode);
        	list = detail_local.query(detail_vo);
        }
        level_id++;//���Ĳ㼶++
        if (list!=null && !list.isEmpty()){
            Map map=null;
            String mgName="";
            String grade="";
			String managerName="";
			boolean isLeafNode = false;
            for(int i=0;i<list.size();i++){
            	map = (Map)list.get(i);

                sb2.append("{\"title\": \"");
                //mgName=(Integer)map.get("CLASSDEFINE_ID")+"";
                StringBuffer sb_m = new StringBuffer();
            	if(level_id % 2 ==0)
            	{
            		sb_m.append(Utility.trimNull(map.get("CLASSDEFINE_NAME")));
            	}
            	else
            	{
            		if(Utility.trimNull(map.get("CLASSDEFINE_ID")).equals("10"))
            		{
            			sb_m.append(Utility.trimNull(map.get("CLASSDETAIL_NAME"))+"(�����"+Utility.trimNull(map.get("CLASS_MINVALUE"))+"��)");
            		}
            		else if(Utility.trimNull(map.get("CLASSDEFINE_ID")).equals("11"))
            		{
            			if(Utility.trimNull(map.get("CLASS_MINVALUE")) == "" && "".equals(Utility.trimNull(map.get("CLASS_MINVALUE"))))
            				sb_m.append(Utility.trimNull(map.get("CLASSDETAIL_NAME"))+"(С��"+Utility.trimNull(map.get("CLASS_MAXVALUE"))+"����)");
            			else
            				sb_m.append(Utility.trimNull(map.get("CLASSDETAIL_NAME"))+"(����"+Utility.trimNull(map.get("CLASS_MINVALUE"))+"����)");
            		}
            		else if(Utility.trimNull(map.get("CLASSDEFINE_ID")).equals("12"))
            		{
            			if(Utility.trimNull(map.get("CLASS_MAXVALUE")) == "" && "".equals(Utility.trimNull(map.get("CLASS_MAXVALUE"))))
            				sb_m.append(Utility.trimNull(map.get("CLASSDETAIL_NAME"))+"("+Utility.trimNull(Format.formatMoney(Utility.stringToDouble(Utility.trimNull(map.get("CLASS_MINVALUE")))))+"Ԫ����)");
            			else
            				sb_m.append(Utility.trimNull(map.get("CLASSDETAIL_NAME"))+"("+Utility.trimNull(Format.formatMoney(Utility.stringToDouble(Utility.trimNull(map.get("CLASS_MINVALUE")))))+"-"+Utility.trimNull(Format.formatMoney(Utility.stringToDouble(Utility.trimNull(map.get("CLASS_MAXVALUE")))))+"Ԫ)");
            		}
            		else
            			sb_m.append(map.get("CLASSDEFINE_NAME"));
            			//sb_m.append(Utility.trimNull(map.get("CLASSDETAIL_NAME")));
            	}
                
            	level_id++;//���Ĳ㼶++
            	
				mgName = (mgName == null) ? "" : mgName.trim();
				managerName = sb_m.toString();
				managerName = (managerName == null) ? "" : managerName .trim();

				// �ж��Ƿ�Ҷ�ӽڵ�
				Integer b1 = (Integer)map.get("BOTTOM_FLAG");
       			if (b1==null) b1=new Integer(0);
       			
				isLeafNode = (b1.intValue() == 2) ? true : false;
	
       			//grade = (Integer)map.get("CLASSDEFINE_ID")+"";
       			grade = Utility.parseInt(Utility.trimNull(map.get("CLASSDEFINE_ID")),null)+"|"+level_id+"|"+Utility.parseInt(Utility.trimNull(map.get("CANMODI")),0)+"|"+Utility.parseInt(Utility.trimNull(map.get("CLASSDETAIL_ID")),0)+"|"+Utility.parseInt(Utility.trimNull(map.get("CANDEL")),0)+"|"+Utility.parseInt(Utility.trimNull(map.get("CANADD")),0);
				if (!isLeafNode) // ��Ҷ�ӽڵ�
				{
	                sb2.append(mgName +  managerName);
	                // ֻ�򿪵�һ��ڵ�
	                if (1 == input_operatorCode.intValue())
	                 	sb2.append("\",\"key\":\""+grade+"\", \"expanded\": true, \"folder\": true, \"children\":[] ");
	                else
	                	sb2.append("\",\"key\":\""+grade+"\", \"expanded\": false, \"folder\": true, \"children\":[] ");
		                
	                    Integer ser=(Integer)map.get("CLASSDEFINE_ID");
	                    //sb2.append(getNextJsonStr(ser,(Integer)map.get("CLASSDEFINE_ID")+"$"+level_id+"$"+Utility.parseInt(Utility.trimNull(map.get("CLASSDETAIL_ID")), new Integer(0))));
		        }
				else
	                sb2.append(mgName + "\",\"key\":\""+grade+"\", \"expanded\": false, \"folder\": false");

				sb2.append("}");
				if (i!=list.size()-1) // ���һ��Ԫ�غ󲻼�","
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
sb.append("{\"title\": \"�ͻ�����\",\"key\":\"0\", \"expanded\": true, \"folder\": true, \"children\": ");
sb.append(getNextJsonStr(new Integer(0),""));
sb.append("}]");
%>
<%-- <%=getNextJsonStr(new Integer(0),"10$2$0")%> --%>
<%=getNextJsonStr(new Integer(0),"0$1$0")%>