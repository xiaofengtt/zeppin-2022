
<%@ page language="java" contentType="text/csv; charset=gbk" import="enfo.crm.web.*,enfo.crm.tools.*"%>
<%
try{
int flag = Utility.parseInt(Utility.trimNull(request.getParameter("flag")), 0); //��־����ʾ���Ǹ�����

Integer sqFlag =
	Utility.parseInt(
		Utility.trimNull(request.getParameter("sqFlag")),
		new Integer(0));
//����
Integer book_code =
	Utility.parseInt(
		Utility.trimNull(request.getParameter("book_code")),
		new Integer(1));
//����Ա
Integer input_man =
	Utility.parseInt(
		Utility.trimNull(request.getParameter("input_man")),
		new Integer(888));

//����Ա
String menu_id = Utility.trimNull(request.getParameter("menu_id"));
//�б���ӵ�еĲ˵�
String viewStr = Utility.trimNull(request.getParameter("viewStr"));
//�б��еĲ�ѯ����
String sQuery = Utility.trimNull(request.getParameter("sQuery"));

DocumentFile excel = new DocumentFile(pageContext);

if (flag == 0) 
	excel.exportPurchase(book_code,  input_man, menu_id, viewStr, sQuery, "�ͻ��Ϲ���Ϣ", flag);
else if (flag == 1) //�����˲�ѯ
	excel.exportBenifiter(book_code,  input_man, menu_id, viewStr, sQuery, "��������Ϣ", flag);
else if (flag == 2) //�ɿ��ѯ
	excel.exportMoneyDetail(book_code,  input_man, menu_id, viewStr, sQuery, "�ɿ���Ϣ", flag);
else if (flag == 3) //�����˻��޸Ĳ�ѯ
	excel.exportBenifiterModi(book_code,  input_man, menu_id, viewStr, sQuery, "�����˻��޸���Ϣ", flag);
else if (flag == 4) //��ͬ�����ϸ��ѯ
	excel.exportContractList(book_code,  input_man, menu_id, viewStr, sQuery, "��ͬ�����ϸ��Ϣ", flag);
else if (flag == 5) //����Ȩת�ò�ѯ
	excel.exportContractExchange(book_code,  input_man, menu_id, viewStr, sQuery, "����Ȩת����Ϣ", flag);
else if (flag == 6) //���������ܲ�ѯ
	excel.exportSquare(book_code,  input_man, menu_id, viewStr, sQuery, "������������Ϣ", flag);

}catch(Exception e){
	e.printStackTrace();
} 
%>
