
<%@ page language="java" contentType="text/csv; charset=gbk" import="enfo.crm.web.*,enfo.crm.tools.*"%>
<%
try{
int flag = Utility.parseInt(Utility.trimNull(request.getParameter("flag")), 0); //标志，表示掉那个方法

Integer sqFlag =
	Utility.parseInt(
		Utility.trimNull(request.getParameter("sqFlag")),
		new Integer(0));
//帐套
Integer book_code =
	Utility.parseInt(
		Utility.trimNull(request.getParameter("book_code")),
		new Integer(1));
//操作员
Integer input_man =
	Utility.parseInt(
		Utility.trimNull(request.getParameter("input_man")),
		new Integer(888));

//操作员
String menu_id = Utility.trimNull(request.getParameter("menu_id"));
//列表所拥有的菜单
String viewStr = Utility.trimNull(request.getParameter("viewStr"));
//列表中的查询条件
String sQuery = Utility.trimNull(request.getParameter("sQuery"));

DocumentFile excel = new DocumentFile(pageContext);

if (flag == 0) 
	excel.exportPurchase(book_code,  input_man, menu_id, viewStr, sQuery, "客户认购信息", flag);
else if (flag == 1) //受益人查询
	excel.exportBenifiter(book_code,  input_man, menu_id, viewStr, sQuery, "受益人信息", flag);
else if (flag == 2) //缴款查询
	excel.exportMoneyDetail(book_code,  input_man, menu_id, viewStr, sQuery, "缴款信息", flag);
else if (flag == 3) //受益账户修改查询
	excel.exportBenifiterModi(book_code,  input_man, menu_id, viewStr, sQuery, "受益账户修改信息", flag);
else if (flag == 4) //合同变更明细查询
	excel.exportContractList(book_code,  input_man, menu_id, viewStr, sQuery, "合同变更明细信息", flag);
else if (flag == 5) //受益权转让查询
	excel.exportContractExchange(book_code,  input_man, menu_id, viewStr, sQuery, "受益权转让信息", flag);
else if (flag == 6) //收益分配汇总查询
	excel.exportSquare(book_code,  input_man, menu_id, viewStr, sQuery, "收益分配汇总信息", flag);

}catch(Exception e){
	e.printStackTrace();
} 
%>
