<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%
//�˳�ҳ�治��Ҫд�˳��߼���Filter�������õ�logoutUrl�������˳�url����ʱ����ִ���˳���������������߼�
session.removeAttribute("card_id");
session.removeAttribute("sex" );
session.removeAttribute("birth");
session.removeAttribute("address");
session.removeAttribute("name");
session.removeAttribute("card_type");
session.removeAttribute("issueDate");
session.removeAttribute("period");
session.removeAttribute("issuePlace");
session.removeAttribute("nation");
session.removeAttribute("inputstream");
//ҵ��ϵͳ���˳��߼�����д��com.enfo.trust.utility.TrustLoginFilter.doLogout�����У������ظ���÷���
String contextPath = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+contextPath+"/";
//�������õ�����֤�������������ת��ʵ����������
response.sendRedirect(basePath); //��ת����ǰweb�ĸ�
%>
