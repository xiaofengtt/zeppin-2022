<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.webcall.*,enfo.crm.service.*,java.util.*,java.math.*,enfo.crm.marketing.*,enfo.crm.intrust.*,enfo.crm.system.*,enfo.crm.vo.*, enfo.crm.customer.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.callcenter.*"%>
<%
//��¼��֤
String username = Utility.trimNull(request.getParameter("username"));
String password = Utility.trimNull(request.getParameter("password"));
String uuid = Utility.trimNull(request.getParameter("uuid")); 
String webcallId = Utility.trimNull(request.getParameter("cust_no")); 
Integer login_flag = Utility.parseInt(Utility.trimNull(request.getParameter("login_flag")),new Integer(1));
//��������
TCrmWebCallVO vo = new TCrmWebCallVO();
TCrmWebCallLocal local = EJBFactory.getTCrmWebCall();
LoginService loginService = new LoginService();

int loginSuccess = 0;
boolean success = false;
String loginResult = "2|�޲���";
String CONST_ENFO_LOGINUSER = "_const_enfo_loginuser_";
String path = request.getContextPath()+"/";
String uuid2 = "";//uuid 
Integer op_code = new Integer(0);
Integer cust_id = new Integer(0);
List rsList = null;
Map rsMap = null;


//��֤�û��� ����
if(login_flag.intValue()==1){	
	try{
		loginSuccess = loginService.checkUserPass(username,password);
		System.out.println("---loginSuccess--"+loginSuccess);
		if (loginSuccess==100){			
			//��¼����
			//loginService.doLogin(request,username,password);
			//session.setAttribute(CONST_ENFO_LOGINUSER, username);	
			//loginResult = "1|��¼�ɹ�";	

			//����UUID������
			uuid2 = enfo.crm.tools.UUID.getUUID();
			op_code = new Integer(loginService.getLoginUser(username));
			System.out.println("---uuid2--"+uuid2);
			//vo ����		
			vo = new TCrmWebCallVO();
			vo.setOp_code(op_code);
			vo.setUuid(uuid2);
			vo.setInput_man(new Integer(888));
			System.out.println("---uuid2--"+vo.getUuid());
			local.appendOperatorUUID(vo);
			loginResult = "1|"+vo.getUuid();
			System.out.println("---loginResult--"+loginResult);
		}
		else{
			loginResult = "2|��֤ʧ��";
		}
	}
	catch(Exception e){
		//throw new Exception("ϵͳ���ݿ�δ��ʼ��!");
		loginResult = "2|ϵͳ���ݿ�δ��ʼ��!"+e.getMessage();
	}

	path = request.getContextPath()+"/webcall/login_webcall_result.jsp?loginResult="+loginResult;
}
else if(login_flag.intValue()==2){	
	//��ʽ��½ ��UUID
	try{
		username = loginService.getLoginUserByUUID(uuid);
		loginService.doLogin(request,username,"");//������������������޷�������
		session.setAttribute(CONST_ENFO_LOGINUSER, username);		
		loginResult = "1|��¼�ɹ�";
		path = request.getContextPath()+"/";
	}
	catch(Exception e){		
		loginResult = "2|��¼ʧ��:"+e.getMessage();
		path = request.getContextPath()+"/webcall/login_webcall_result.jsp?loginResult="+loginResult;
	}	
}
else if(login_flag.intValue()==3){
	//uuidת��Ϊopcode
	try{
		op_code = loginService.getOpCodeByUUID(uuid);
		
		if(op_code.intValue()!=0){
			loginService.saveToSession(op_code.toString(),request);

			//���촰����ת
			vo = new TCrmWebCallVO();
			vo.setWebcallId(webcallId);
		
			rsList = local.listCrmWebcall(vo);
			if(rsList!=null&&rsList.size()>0){
				rsMap = (Map)rsList.get(0);
				cust_id = Utility.parseInt(Utility.trimNull(rsMap.get("CUST_ID")),new Integer(0));
				path = request.getContextPath()+"/webcall/customer_info_webcall_detail.jsp?cust_id="+cust_id+"&webcallId="+webcallId;
			}
			else{
				path = request.getContextPath()+"/webcall/customer_info_webcall_1.jsp?webcallId="+webcallId;
			}
		}		
		else{
			loginResult = "2|uuidδ�ҵ�!";
			path = request.getContextPath()+"/webcall/login_webcall_result.jsp?loginResult="+loginResult;
		}

	}
	catch(Exception e){
		loginResult = "2|uuidת������ ��"+e.getMessage();
		path = request.getContextPath()+"/webcall/login_webcall_result.jsp?loginResult="+loginResult;
	}
}

session.setAttribute("isWebcall",new Integer(1));
%>
<html>
<head>
<title>webCall Login</title>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<script language=javascript>
window.location.href= "<%=path%>";//��ת����¼����
function testAction(){
	var url = "customer_info_webcall_1.jsp";		
	showModalDialog(url,'', 'dialogWidth:300px;dialogHeight:600px;status:0;help:0');
}
</script>
</head>
<body class="BODY">
<%=loginResult%>
</body>
</html>