<?xml version="1.0" encoding="gbk" ?>
<%@ page contentType="text/xml; charset=GBK"  import="java.util.*,java.math.*,enfo.crm.affair.*,enfo.crm.intrust.*,enfo.crm.system.*,enfo.crm.vo.*, enfo.crm.customer.*,enfo.crm.tools.*,enfo.crm.dao.*" %>
<%
Integer input_operatorCode = Utility.parseInt(request.getParameter("input_operatorCode"), new Integer(0));

CustClassDefineLocal local = EJBFactory.getCustClassDefine();
CustClassDefineVO vo = new CustClassDefineVO();
CustClassDetailLocal detail_local = EJBFactory.getCustClassDetail();
CustClassDetailVO detail_vo = new CustClassDetailVO();

String condition = Utility.trimNull(request.getParameter("sQuery"));
String [] items = Utility.splitString(condition, "$");

Integer define_id = new Integer(0);//分级ID
int level_id = 1;//树的层级
Integer detail_id = new Integer(0);//分级明细ID
if(items.length>0)
{
	define_id = Utility.parseInt(items[0], new Integer(0));
	level_id = Utility.parseInt(items[1], 1);
	detail_id = Utility.parseInt(items[2], new Integer(0));
}

List list = new ArrayList();
Map map = new HashMap();

//如何level_id为基数，则调用define表；如何level_id为偶数，则调用detail表
if(level_id == 1)
{
	vo.setClass_define_name("");
	if(level_id == 1){
		vo.setClass_define_id(new Integer(0));
		vo.setLevel_id(new Integer(1));
		vo.setParent_value(detail_id);
		vo.setParent_id(define_id);
	}else{
		vo.setClass_define_id(define_id);
		vo.setLevel_id(new Integer(0));
		//vo.setParent_value();
		//vo.setParent_id(define_id);
	}	
	
	vo.setCanmodi(new Integer(0));
	vo.setInput_man(input_operatorCode);
	vo.setCD_no(new Integer(2));
	list = local.query(vo);
}else{
	detail_vo.setClass_define_id(define_id);
	detail_vo.setClass_detail_id(detail_id);
	detail_vo.setInput_man(input_operatorCode);
	list = detail_local.query2(detail_vo);
}
level_id++;//树的层级++

StringBuffer sb = new StringBuffer(20000);
String sQuery = "";
sb.append(
	"<TreeNode NodeId=\"msdnlib587\" Href=\"/nhp/default.asp?contentid=28000519\" Title=\".NET Development\" ParentXmlSrc=\"top.xml\" NodeXmlSrc=\"client_class_define_tree.jsp\">");
for(int i = 0 ; i<list.size();i++){
	map = (Map)list.get(i);
	sb.append("<TreeNode NodeId=\"1\" Href=\"#");
	sb.append(Utility.parseInt(Utility.trimNull(map.get("CLASSDEFINE_ID")),null)+"|"+level_id+"|"+Utility.parseInt(Utility.trimNull(map.get("CANMODI")),0)+"|"+Utility.parseInt(Utility.trimNull(map.get("CLASSDETAIL_ID")),0)+"|"+Utility.parseInt(Utility.trimNull(map.get("CANDEL")),0)+"|"+Utility.parseInt(Utility.trimNull(map.get("CANADD")),0)+"|"+Utility.parseInt(Utility.trimNull(map.get("TABLE_FLAG")),new Integer(1)));
	sb.append("\"");
	sb.append(" label=\""+Utility.trimNull(map.get("CLASSDEFINE_ID"))+"\"");
	sb.append(" Title=\"");
	if(level_id ==2)
	{
		sb.append(Utility.trimNull(map.get("CLASSDEFINE_NAME")));
	}
	else
	{
		sb.append(Utility.trimNull(map.get("CLASSDETAIL_NAME")));
	}
	sb.append("\"");
	
	//设置查询条件参数
	sQuery = Utility.parseInt(Utility.trimNull(map.get("CLASSDEFINE_ID")), new Integer(0)) +"$"+ level_id
				+ "$" + Utility.parseInt(Utility.trimNull(map.get("CLASSDETAIL_ID")), new Integer(0))
				+ "$" +Utility.parseInt(Utility.trimNull(map.get("TABLE_FLAG")),new Integer(1));
	int button_flag = Utility.parseInt(Utility.trimNull(map.get("BOTTOM_FLAG")), 2);
	if(button_flag == 2)
		sb.append(" NodeXmlSrc=\"client_class_define_tree.jsp?sQuery=" + sQuery	+ "\"");
	sb.append("></TreeNode>");
}
sb.append("</TreeNode>");
local.remove();
out.print(sb.toString());
%>
