package enfo.crm.service;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.directwebremoting.WebContext;

import enfo.crm.affair.RemindersLocal;
import enfo.crm.dao.BusiException;
import enfo.crm.dao.CrmDBManager;
import enfo.crm.system.DictparamLocal;
import enfo.crm.system.InputMan;
import enfo.crm.system.LogListLocal;
import enfo.crm.system.MenuInfoLocal;
import enfo.crm.system.OperatorLocal;
import enfo.crm.system.SystemInfoLocal;
import enfo.crm.tools.Argument;
import enfo.crm.tools.EJBFactory;
import enfo.crm.tools.LocalUtilis;
import enfo.crm.tools.Utility;
import enfo.crm.vo.DictparamVO;
import enfo.crm.vo.OperatorVO;
import enfo.crm.vo.RemindersVO;
import enfo.crm.vo.TCrmWebCallVO;

/**
 * ajaxʹ�õĵ�½�����
 * @author Felix
 * @since 2008-5-27
 * @version 1.0
 * ��������������ע�͵�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
 */
public class LoginService extends HttpServlet{
	
	//�ж��Ƿ��д˲���Ա				
	public String getLoginUser(String loginuser) throws Exception
	{
		OperatorLocal local = null;
		OperatorVO vo = new OperatorVO();
		List rsList = null;
		Map rowMap = null;
		String input_opCode = "";
		
		local= EJBFactory.getOperator();
		
		if(loginuser == null || loginuser == "")
		{
			return null;
		}
		
		vo.setLogin_user(loginuser);
		rsList = local.listOpCodeByUser(vo);
		
		if(rsList.size() <= 0)
		{
			return null;
		}
		else
		{
			rowMap = (Map)rsList.get(0);
			input_opCode = Utility.trimNull(rowMap.get("OP_CODE"));
		}
		local.remove();
		return input_opCode;
	}
	
	/**
	 * ��֤����Ա������
	 */
	public int checkUserPass(String loginUser, String password) throws Exception{
		int ret = 0;
		final String sqlStr = "SELECT OP_CODE,OP_NAME FROM TOPERATOR WHERE LOGIN_USER = '"+loginUser+"' AND pwdcompare('"+password+"',ENCRYPT_PASSWORD) = 1";
		Connection conn = CrmDBManager.getConnection();
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.createStatement();
			conn.prepareCall(sqlStr, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			try {
				rs = stmt.executeQuery(sqlStr);
				try {
					while (rs.next()) {
						ret = 100;
						break;
					}					
				} finally {
					rs.close();
				}
			} finally {
				stmt.close();
			}
		} 
		catch (SQLException e) {
			//errorMsg = LocalUtilis.language("login.message.opAccountError",clientLocale);//�Բ�����������Ĳ���Ա�ʺŲ����ڣ�
			throw new Exception("��֤����Ա����:" + e.getMessage());
		}
		finally{
			conn.close();			
		}
		return ret;		
	}
	
	public String getOpName(Integer op_code) throws Exception{
		return "";
	}
	
	public String saveToSession(String op_code, HttpServletRequest request) throws Exception
	{
		DictparamLocal dict = EJBFactory.getDictparam();
		DictparamVO dictVO = new DictparamVO();
		OperatorVO opVO = new OperatorVO();
		List dictList = null;
		Map dictMap = null;
		Integer projectAccessPower = new Integer(0);
		
		opVO.setOp_code(new Integer(op_code));
		InputMan OPERATOR = loginInputMan(opVO);
		//��ѯϵͳ���ز���
		dictVO.setFlag_type("DB00001");
		dictVO.setIs_modi(new Integer(0));
		dictList = dict.listSysControl(dictVO);
		dictMap = (Map)dictList.get(0);
		Integer dictValue = Utility.parseInt(Utility.trimNull(dictMap.get("VALUE")),null);
		Integer currentBookCode = new Integer(0);
		if(!"".equals(Utility.trimNull(request.getSession().getServletContext().getAttribute("INTRUST_ADDRESS"))) && Utility.parseInt(OPERATOR.getIs_intrust(), new Integer(0)).intValue() == 1)
			projectAccessPower = new Integer(1);
		
		//��Session�и�ֵ
		HttpSession session = request.getSession();
		session.setAttribute("OPERATOR",OPERATOR);
		session.setAttribute("LOG0001",new Integer(Argument.getSyscontrolValue("LOG0001")));
		session.setAttribute("WEBFLAG","XTWEB");
		session.setAttribute("DBDRIVER",dictValue);
		session.setAttribute("currentBookCode",currentBookCode);
		session.setAttribute("projectAccessPower",projectAccessPower); //�������Ȩ�� 0�� 1��

		//ϵͳ����
		String apply_name = "";
		Integer languageFlag = new Integer(0);
		Locale clientLocale = null;
		if(Argument.getSyscontrolValue("Global")!=0){
			languageFlag = new Integer(Argument.getSyscontrolValue("Global"));		
		}
		session.setAttribute("languageFlag",languageFlag);
		if(languageFlag.intValue()==0){
			clientLocale = request.getLocale();//�õ����ػ���Ϣ		
		}
		else if(languageFlag.intValue()==1){
			clientLocale = new Locale("zh","CN");
		}
		else if(languageFlag.intValue()==2){
			clientLocale = new Locale("en","US");
		}

		SystemInfoLocal systeminfo = EJBFactory.getSystemInfo();
		Map map= null;
		try{
			map=(Map)systeminfo.listBySig1(new Integer(1)).get(0);	
		}
		catch(Exception e){
			throw new Exception(LocalUtilis.language("login.message.dataBaseError",clientLocale));//ϵͳ���ݿ�δ��ʼ��!
		}
		apply_name = Utility.trimNull(map.get("APPLICATION_NAME"));
		
		ServletContext application = null;
		application = session.getServletContext();
		if(apply_name!=null || apply_name!="")
			application.setAttribute("APPLICATION_NAME",apply_name);
		else
			application.setAttribute("APPLICATION_NAME","ӯ��ͻ���ϵ����ϵͳ");
		
		application.setAttribute("COMPANY_NAME", map.get("USER_NAME"));
		application.setAttribute("USER_ID",map.get("USER_ID"));

		return "1";
	}
	
	public InputMan loginInputMan(OperatorVO vo) throws Exception
	{
		InputMan inputMan = new InputMan();
		List opList = null;
		Map opMap = null;
		OperatorLocal op = EJBFactory.getOperator();
		
		opList = op.listOpAll(vo);
		if(opList.size() <= 0)
		{
			return null;	
		}else
		{
			opMap = (Map)opList.get(0);
			inputMan.setBook_code(Utility.parseInt(Utility.trimNull(opMap.get("BOOK_CODE")),new Integer(0)));
			inputMan.setDepart_id(Utility.parseInt(Utility.trimNull(opMap.get("DEPART_ID")),null));
			inputMan.setIp_address(Utility.trimNull(opMap.get("IP_ADDRESS")));
			inputMan.setOp_code(Utility.parseInt(Utility.trimNull(opMap.get("OP_CODE")),null));
			inputMan.setOp_name(Utility.trimNull(opMap.get("OP_NAME")));
			inputMan.setLogin_time(Utility.parseTimestamp(Utility.trimNull(opMap.get("LOGIN_TIME")),null));
			inputMan.setLogout_time(Utility.parseTimestamp(Utility.trimNull(opMap.get("LOGOUT_TIME")),null));
			inputMan.setLogin_user(Utility.trimNull(opMap.get("LOGIN_USER")));
			//��CRM�в���Ա����ӵ����������ϵͳ��Ȩ�ޣ����ǲ���Ա������ϵͳ�д��ڡ�1���� 0������
			inputMan.setIs_intrust(Utility.parseInt(Utility.trimNull(opMap.get("IS_INTRUST")),new Integer(0)));
			inputMan.setO_tel(Utility.trimNull(opMap.get("O_TEL")));
			inputMan.setMobile(Utility.trimNull(opMap.get("MOBILE")));
			inputMan.listRights();
			return inputMan;
		}		
	}
	
	/**
	 * 
	 */
	public int doLogin(HttpServletRequest request, String loginUser, String password) throws Exception {
		//�������
		String input_opCode = "";
		Locale clientLocale = null;

		request.setCharacterEncoding("UTF-8");
		//ϵͳ����
		OperatorVO opVO = new OperatorVO();	
		OperatorLocal op = EJBFactory.getOperator();
		//��ʼ����½����Ĳ���op_code,password,ip
		input_opCode = getLoginUser(loginUser);
		opVO.setOp_code(new Integer(input_opCode));
		opVO.setIp_address(request.getRemoteAddr());
		//��½
		try{
			op.loginSystem2(opVO);
		}catch (Exception e){
			throw new Exception(e.getMessage());
		}
		//ϵͳ��ʼ����������ʾinput_operatorCode
		try{
			op.updateBookCode(new Integer(input_opCode));
		}catch(Exception e){
			throw new Exception(LocalUtilis.language("login.message.sysInitError:",clientLocale)+e.getMessage());//ϵͳ��ʼ��ʧ�ܣ�
		}
		saveToSession(input_opCode, request);
		return 100;
	}
	
	/**
	 * 
	 * @param op_code
	 * @return
	 * @throws Exception
	 */
	public String doLogOut(Integer op_code) throws Exception{
		if(op_code == null || op_code.intValue() == 0){
			return "0";
		}
		try{
			WebContext context = org.directwebremoting.WebContextFactory.get();
			context.getSession().removeAttribute("OPERATOR");
			OperatorLocal op = EJBFactory.getOperator();
			op.logOut(op_code);
			return "1";
		}catch(Exception e){
			return "0";
		}
	}
	
	public String modiPasswd(String oldPasswd,String newPasswd,Integer input_operatorCode) throws Exception
	{
		String returnValue = "";
		OperatorLocal op = EJBFactory.getOperator();
		OperatorVO vo = new OperatorVO();
		
		vo.setOld_password(oldPasswd);
		vo.setNew_password(newPasswd);
		
		returnValue = op.modiPassWord(vo,input_operatorCode);
		return returnValue;
	}
	
	public String modiLoginUser(String oldUser,String newUser) throws Exception
	{
		String returnValue = "";
		OperatorLocal op = EJBFactory.getOperator();
		OperatorVO vo = new OperatorVO();
		
		vo.setLogin_user(oldUser);
		vo.setNew_login_user(newUser);
		
		returnValue = op.modiLoginUser(vo);
		return returnValue;
	}
	
	
	//�ɲ˵�����ʱȡ��Menuid��Menuinfo����session�У�ʱҳ���ȡsessionʱ��ȡȨ��
	/**
	 * @deprecated add by tsg 2008-11-17 ȡ��������Ϣ
	 * */
	public String alertString(String id,String log,Integer input_operatorCode) throws Exception{
		WebContext context = org.directwebremoting.WebContextFactory.get();
		HttpSession session = context.getSession();
		HttpServletRequest request = context.getHttpServletRequest();
		
		//���ػ���Ϣ
		Integer languageFlag = Utility.parseInt(Utility.trimNull(session.getAttribute("languageFlag")),Integer.valueOf("0"));
		String languageType = "zh_CN";
		java.util.Locale clientLocale = null;

		//�������Ի���
		if(languageFlag.intValue()==0){
			clientLocale = request.getLocale();//�õ����ػ���Ϣ
			languageType = clientLocale.getLanguage()+"_"+clientLocale.getCountry();
			
			if(languageType.equals("zh_CN")){
				languageFlag = Integer.valueOf("1");
			}
			else{
				languageFlag = Integer.valueOf("2");
			}
		}
		else if(languageFlag.intValue()==1){
			clientLocale = new java.util.Locale("zh","CN");
			languageType = "zh_CN";
		}
		else if(languageFlag.intValue()==2){
			clientLocale = new java.util.Locale("en","US");
			languageType = "en_US";
		}
		
		String menu_id = Utility.trimNull(id);
		String log0001 = Utility.trimNull(log);
		
		MenuInfoLocal menu = EJBFactory.getMenuInfo();

		List menuURLList = menu.listMenuInfo(menu_id,languageFlag);
		Map menuURLMap = (Map)menuURLList.get(0);
		
		if(log0001.equals("1")){
			LogListLocal logList = EJBFactory.getLogList();
			logList.addLog(new Integer(91001),"����˵�",input_operatorCode,Utility.trimNull(menuURLMap.get("MENU_NAME")));
		}
		
		String MENU_ID = Utility.trimNull(menuURLMap.get("MENU_ID"));
		String MENU_INFO = Utility.trimNull(menuURLMap.get("MENU_INFO"));
		String MENU_URL = Utility.trimNull(menuURLMap.get("MENU_URL"));
		
		session.setAttribute("menu_id",MENU_ID);
		session.setAttribute("menu_info",MENU_INFO);
		session.setAttribute("menu_url",MENU_URL);

		return "1";
	}
	
	/**
	 * �޸ĳ��ù���̨ģ��
	 * @param opCode
	 * @param workbench
	 * @return
	 * @throws Exception
	 */
	public String modiWorkBench(Integer opCode,String workbench) throws Exception{
		String sqlStr = "UPDATE TOPERATOR SET WORK_BENCH ='"+ workbench +"' WHERE OP_CODE = "+opCode;
		
		Connection conn = null;
		Statement stmt = null;
		
		try {
			conn = CrmDBManager.getConnection();
			stmt = conn.createStatement();
			stmt.executeUpdate(sqlStr);
		}
		catch (SQLException e) {
			throw new BusiException("�޸ĳ��ù���̨ģ��:" + e.getMessage());
		}		
		finally{
			stmt.close();
			conn.close();			
		}
		
		return "100";
	}
	/**
	 * ������±����ڱ���¼��
	 * @param opCode
	 * @param content
	 * @return
	 */
	public String addReminder(Integer opCode,String content){
		Integer schedule_date = new Integer(Utility.getCurrentDate());
		RemindersVO vo = new RemindersVO();
		RemindersLocal local = null;
		String ret = "";
		try {
			local = EJBFactory.getReminders();
			
			vo.setSchedule_date(schedule_date);
			vo.setContent(content);
			vo.setInput_man(opCode);
			
			local.append(vo);
			ret = "1|����ɹ�";
		} catch (Exception e) {
			ret = "2|"+e.getMessage();
			e.printStackTrace();
		}
		return ret;		
	}
	/**
	 * ��ѯOP_CODE ͨ��UUID
	 * @param uuid
	 * @return
	 * @throws BusiException
	 */
	public Integer getOpCodeByUUID(String uuid) throws BusiException{
		List list = null;//�����
		Map map = null;
		Integer op_code = new Integer(0);
		enfo.crm.webcall.TCrmWebCallLocal local = null;//����
		TCrmWebCallVO vo = new TCrmWebCallVO();
		
		try{
			vo.setUuid(uuid);
			local = EJBFactory.getTCrmWebCall();
			list = local.listOperatorUUID(vo);
			
			if(list!=null){
				map = (Map) list.get(0);
				op_code = Utility.parseInt(Utility.trimNull(map.get("OP_CODE")),new Integer(0));
			}
		}
		catch(Exception e){
			throw new BusiException("ͨ��UUID��ѯ����ԱID:" + e.getMessage());
		}

		return op_code;
	}
	/**
	 * ��ѯLoginUser ͨ��UUID
	 * @param uuid
	 * @return
	 * @throws BusiException
	 */
	public String getLoginUserByUUID(String uuid) throws BusiException{
		List list = null;//�����
		Map map = null;
		String loginUser = "";
		enfo.crm.webcall.TCrmWebCallLocal local = null;//����
		TCrmWebCallVO vo = new TCrmWebCallVO();
		
		try{
			vo.setUuid(uuid);
			local = EJBFactory.getTCrmWebCall();
			list = local.listOperatorUUID(vo);
			
			if(list!=null&&list.size()>0){
				map = (Map) list.get(0);
				loginUser = Utility.trimNull(map.get("LOGIN_USER"));
			}
		}
		catch(Exception e){
			throw new BusiException("ͨ��UUID��ѯ����ԱID:" + e.getMessage());
		}

		return loginUser;
	}
	
	/**
	 * ��ѯpagesize ͨ��login_user
	 * @param login_user
	 * @return
	 * @throws BusiException
	 */
	public void getpageSize(String login_user,HttpServletRequest request) throws Exception{
		
		HttpSession session = request.getSession();
		String sql = "SELECT PAGESIZE FROM TOPERATOR WHERE LOGIN_USER ='" + login_user + "'";
		Connection conn = CrmDBManager.getConnection();
		Statement stat = conn.createStatement();
		ResultSet rs = stat.executeQuery(sql);
		while(rs.next()){
			session.setAttribute("defaultPagesize",Integer.toString(rs.getInt("PAGESIZE")));
		}
		
		conn.close();
		stat.close();
		rs.close();
	}
}