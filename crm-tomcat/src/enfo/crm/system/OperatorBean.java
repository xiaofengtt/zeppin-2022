 
package enfo.crm.system;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.CrmDBManager;
import enfo.crm.dao.IPageList;
import enfo.crm.tools.Utility;
import enfo.crm.vo.CustLevelAuthVO;
import enfo.crm.vo.OperatorVO;
import enfo.crm.vo.TOperatorVO;
import enfo.crm.vo.TcustmanagersVO;

@Component(value="operator")
public class OperatorBean extends enfo.crm.dao.CrmBusiExBean implements OperatorLocal {
	
	
	/**
	 * 查询intrust操作员
	 */	
	private static final String listOperatorIntrustSql ="{call SP_QUERY_TOPERATOR_INTRUST (?)}";
	/**
	 * 添加员工基础信息
	 */
	private static final String appendSql = "{?=call SP_ADD_TOPERATOR(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	/**
	 * 查询操作员信息
	 */
	private static final String listOperatorSql = "{call SP_QUERY_TOPERATOR (?,?,?,?,?)}";
	/**
	 * 修改操作员信息
	 */
	private static final String modiSql = "{?= call SP_MODI_TOPERATOR(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	
	
	/**
	 * 修改操作员映射表
	 */
	private static final String modiToperMap = "{?= call SP_MODI_TOPERATOR_MAP(?,?,?,?,?)}";
	
	/** 
	 * 删除操作员信息
	 */
	private static final String deleteSql =
		"{?= call SP_DEL_TOPERATOR(?,?)}";

	/**
	 * 查询该用户的角色
	 */
	private static final String lisSernoSql = "{call SP_QUERY_TOPROLE(?,?,?)}";
	/**
	 * 增加员工角色
	 */
	private static final String appendOperatorSql2 =	"{?=call SP_ADD_TOPROLE(?,?,?,?)}";
	/**
	 * 查询当前在线的用户
	 */
	private static final String listAllOpSql = "{call SP_QUERY_TOPERATOR_VALID}";
	/**
	 * 初始化报警
	 */
	private static final String updatebookCodeSql = "{?=call SP_LOGIN_INI(?)}";
	/**
	 * 退出登陆
	 */
	private static final String logoutSql = "{?=call SP_LOGOUT(?)}";
	private static final String listOperatorCodeSql = "{call SP_QUERY_TOPERATOR_BYUSER(?)}";	//根据登陆帐号查询OpCode
	private static final String loginSql = "{?=call SP_LOGIN (?,?,?,?)}";	//登陆
	private static final String chpassSql = "{?=call SP_MODI_PASSWORD (?,?,?,?)}";//修改密码
	private static final String batchSql = "{?=call SP_ADD_TOPRIGHT_ALL (?,?,?,?,?,?)}";	//
	private static final String checkMenu = "{?=call SP_QUERY_MAIN_MENURIGHT(?,?,?)}";//查询是否具有菜单权限
	private static final String modiPassWordSql = "{?=call SP_MODI_PASSWORD(?,?,?,?)}";//修改密码
	private static final String modiLoginUserSql = "{?=call SP_MODI_PASSWORD(?,?,?)}";//修改登录名称
	private static final String listInsideServiceTasksSql = "{call SP_QUERY_InsideServiceTasks(?)}";//查询待处理的内部事务
	private static final String listOutsideServiceTasksSql = "{call SP_QUERY_OutsideServiceTasks(?)}";//查询待处理的外部事务
	private static final String listScheDulesSql = "{call SP_QUERY_TSCHEDULES(?,?,?,?,?,?)}";//查询日程表事务
	//查询当前操作员在售产品统计
	private static final String sql_query_product_marketing_total = "{call SP_QUERY_TPRODUCT_MARKETING_TOTAL(?,?,?)}";
	
	/* (non-Javadoc)
	 * @see enfo.crm.system.OperatorLocal#appendOperator(enfo.crm.vo.TOperatorVO, java.lang.Integer)
	 */
	@Override
	public void appendOperator(TOperatorVO vo, Integer inputOperatorCode)
		throws BusiException {

		Object[] params = new Object[14];

		params[0] = Utility.parseInt(vo.getOp_code(), new Integer(0));
		params[1] = vo.getOp_name();
		params[2] = Utility.parseInt(vo.getDepart_id(), new Integer(0));
		params[3] = vo.getRole_id();
		params[4] = vo.getSummary();
		params[5] = inputOperatorCode;
		params[6] = vo.getAddress();
		params[7] = vo.getEmail();
		params[8] = vo.getTelephone();
		params[9] = vo.getMobile();
		params[10] = vo.getLogin_user();
		params[11] = vo.getCard_type();
		params[12] = vo.getCard_id();
		params[13] = vo.getCard_valid_date();

		super.cudProc(appendSql, params);
	}

	/* (non-Javadoc)
	 * @see enfo.crm.system.OperatorLocal#listOpAll(enfo.crm.vo.TOperatorVO)
	 */
	@Override
	public List listOpAll(TOperatorVO vo) throws BusiException {
		List rsList = null;
		Object[] params = new Object[5];
		params[0] = Utility.parseInt(vo.getOp_code(), new Integer(0));
		params[1] = Utility.parseInt(vo.getDepart_id(), new Integer(0));
		params[2] = Utility.parseInt(vo.getRole_id(), new Integer(0));
		params[3] = Utility.trimNull(vo.getOp_name());
		params[4] = Utility.parseInt(vo.getStatus(), new Integer(0));
		rsList = super.listProcAll(listOperatorSql, params);
		return rsList;
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.system.OperatorLocal#getToperIntrust(enfo.crm.vo.TOperatorVO)
	 */
	@Override
	public List getToperIntrust(TOperatorVO vo) throws BusiException {
		List rsList = null;
		Object[] params = new Object[1];
		params[0] = Utility.parseInt(vo.getFlag(), new Integer(0));
		rsList = super.listProcAll(listOperatorIntrustSql, params);
		return rsList;
	}

	/* (non-Javadoc)
	 * @see enfo.crm.system.OperatorLocal#modiOperator(enfo.crm.vo.TOperatorVO, java.lang.Integer)
	 */
	@Override
	public void modiOperator(
		TOperatorVO vo,
		Integer inputOperatorCode)
		throws BusiException {
		Object[] params = new Object[15];

		params[0] = Utility.parseInt(vo.getOp_code(), new Integer(0));
		params[1] = vo.getOp_name();
		params[2] = Utility.parseInt(vo.getDepart_id(), new Integer(0));
		params[3] = vo.getRole_id();
		params[4] = vo.getSummary();
		params[5] = inputOperatorCode;
		params[6] = vo.getAddress();
		params[7] = vo.getEmail();
		params[8] = vo.getTelephone();
		params[9] = vo.getMobile();
		params[10] = vo.getIniflag();
		params[11] = vo.getLogin_user();
		params[12] = vo.getCard_type();
		params[13] = vo.getCard_id();
		params[14] = vo.getCard_valid_date();

		super.cudProc(modiSql, params);
	}
	
	
	/* (non-Javadoc)
	 * @see enfo.crm.system.OperatorLocal#modiTopertorMap(enfo.crm.vo.TOperatorVO, java.lang.Integer)
	 */
	@Override
	public void modiTopertorMap(TOperatorVO vo, Integer inputOperatorCode)
		throws BusiException {
		Object[] params = new Object[5];

		params[0] = Utility.parseInt(vo.getOp_code(), new Integer(0));
		params[1] = Utility.parseInt(vo.getIntrust_op_code(), new Integer(0));
		params[2] = Utility.parseInt(vo.getIntrust_bookcode(), new Integer(1));
		params[3] = Utility.parseInt(vo.getEtrust_Operator(), new Integer(0));
		params[4] = inputOperatorCode; 

		super.cudProc(modiToperMap, params);
	}
	
	
	/* (non-Javadoc)
	 * @see enfo.crm.system.OperatorLocal#delete(enfo.crm.vo.TOperatorVO, java.lang.Integer)
	 */
	@Override
	public void delete(TOperatorVO vo, Integer input_man)
		throws BusiException {
		Object[] params = new Object[2];
		params[0] = Utility.parseInt(vo.getOp_code(), new Integer(0));
		params[1] = Utility.parseInt(input_man, new Integer(0));
		super.cudProc(deleteSql, params);
	}

	/* (non-Javadoc)
	 * @see enfo.crm.system.OperatorLocal#listProcPage(enfo.crm.vo.TOperatorVO, java.lang.String[], int, int)
	 */
	@Override
	public IPageList listProcPage(
		TOperatorVO vo,
		String[] totalColumn,
		int pageIndex,
		int pageSize)
		throws BusiException {
		IPageList list = null;
		Object[] params = new Object[5];
		;
		params[0] = Utility.parseInt(vo.getOp_code(), new Integer(0));
		params[1] = Utility.parseInt(vo.getDepart_id(), new Integer(0));
		params[2] = Utility.parseInt(vo.getRole_id(), new Integer(0));
		params[3] = Utility.trimNull(vo.getOp_name());
		params[4] = Utility.parseInt(vo.getStatus(), new Integer(0));
		list =
			super.listProcPage(
				listOperatorSql,
				params,
				totalColumn,
				pageIndex,
				pageSize);
		return list;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.system.OperatorLocal#listSerno(enfo.crm.vo.OperatorVO)
	 */
	@Override
	public List listSerno(OperatorVO vo) throws BusiException {
		List list = null;
		Object[] params = new Object[3];
		params[0] = Utility.parseInt(vo.getSerial_no(), new Integer(0));
		params[1] = Utility.parseInt(vo.getOp_code(), new Integer(0));
		params[2] = Utility.parseInt(vo.getRole_id(), new Integer(0));
		list = super.listBySql(lisSernoSql, params);
		return list;
	}

	/* (non-Javadoc)
	 * @see enfo.crm.system.OperatorLocal#appendOperator2(enfo.crm.vo.OperatorVO, java.lang.Integer)
	 */
	@Override
	public void appendOperator2(OperatorVO vo, Integer input_man)
		throws BusiException {
		Object[] params = new Object[4];
		params[0] = Utility.parseInt(Utility.trimNull(vo.getOp_code()), null);
		params[1] = Utility.parseInt(Utility.trimNull(vo.getRole_id()), null);
		params[2] = Utility.parseInt(Utility.trimNull(vo.getFlag()), null);
		params[3] = Utility.parseInt(Utility.trimNull(input_man), null);
		super.cudProc(appendOperatorSql2, params);
	}

	/* (non-Javadoc)
	 * @see enfo.crm.system.OperatorLocal#changePass(java.lang.Integer, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void changePass(
		Integer opcode,
		String old_password,
		String new_pass1,
		String new_pass2)
		throws Exception {
		if (!new_pass1.equals(new_pass2)) {
			throw new BusiException("两次输入新密码不符，请重新输入！");
		}
		Integer ret = new Integer(0);
		Object[] params = new Object[3];
		params[0] = opcode;
		params[1] = old_password;
		params[2] = new_pass1;
		ret =
			(Integer) super.cudProc(
				"{?=call SP_MODI_PASSWORD(?,?,?,?)}",
				params,
				5,
				Types.INTEGER);
		CrmDBManager.handleResultCode(ret.intValue()); //抛出错误结果信息
	}

	/* (non-Javadoc)
	 * @see enfo.crm.system.OperatorLocal#changeLoginUser(java.lang.String, java.lang.String)
	 */
	@Override
	public void changeLoginUser(String old_login_user, String new_login_user)
		throws Exception {

		//Integer ret = new Integer(0);
		Object[] params = new Object[2];
		params[0] = old_login_user;
		params[1] = new_login_user;
		
		super.cudProc("{?=call SP_MODI_LOGIN_USER(?,?)}",params);
		//CrmDBManager.handleResultCode(ret.intValue()); //抛出错误结果信息
	}

	/* (non-Javadoc)
	 * @see enfo.crm.system.OperatorLocal#listOnLineAllOp(int, int)
	 */
	@Override
	public IPageList listOnLineAllOp(int pageIndex, int pageSize)
		throws BusiException {
		IPageList rsList = null;
		Object[] params = new Object[0];
		rsList = super.listProcPage(listAllOpSql, params, pageIndex, pageSize);
		return rsList;
	}

	/* (non-Javadoc)
	 * @see enfo.crm.system.OperatorLocal#updateBookCode(java.lang.Integer)
	 */
	@Override
	public void updateBookCode(Integer op_code) throws Exception {
		Object[] params = new Object[1];
		params[0] = Utility.parseInt(op_code, null);
		super.cudProc(updatebookCodeSql, params);
	}

	/* (non-Javadoc)
	 * @see enfo.crm.system.OperatorLocal#logOut(java.lang.Integer)
	 */
	@Override
	public void logOut(Integer op_code) throws Exception {
		Object[] params = new Object[1];
		params[0] = Utility.parseInt(op_code, null);
		super.cudProc(logoutSql, params);
	}

	//---------------------------------------------------------------------------

	/* (non-Javadoc)
	 * @see enfo.crm.system.OperatorLocal#listOpCodeByUser(enfo.crm.vo.OperatorVO)
	 */
	@Override
	public List listOpCodeByUser(OperatorVO vo) throws BusiException {
		List rsList = null;
		Object[] params = new Object[1];
		params[0] = Utility.trimNull(vo.getLogin_user());
		rsList = super.listProcAll(listOperatorCodeSql, params);
		return rsList;
	}

	/* (non-Javadoc)
	 * @see enfo.crm.system.OperatorLocal#loginSystem(enfo.crm.vo.OperatorVO)
	 */
	@Override
	public Integer loginSystem(OperatorVO vo) throws BusiException {
		Object[] params = new Object[3];
		int postInt = 5;
		int typeInt = java.sql.Types.INTEGER;

		Object tempArray = null;
		try {

			params[0] = Utility.parseInt(vo.getOp_code(), new Integer(0));
			params[1] = Utility.trimNull(vo.getPassword(), null);
			params[2] = Utility.trimNull(vo.getIp_address(), null);

			tempArray = super.cudProc(loginSql, params, postInt, typeInt);

		} catch (Exception e) {
			throw new BusiException(e.getMessage());
		}

		Integer returnArray = (Integer) tempArray;
		return returnArray;
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.system.OperatorLocal#loginSystem2(enfo.crm.vo.OperatorVO)
	 */
	@Override
	public void loginSystem2(OperatorVO vo) throws BusiException{
		String summary = "操作员登录，操作员编号："+ vo.getOp_code();
		String loglistSql = "INSERT INTO TLOGLIST(BUSI_FLAG,BUSI_NAME,OP_CODE,SUMMARY) VALUES(40308,N'操作员登录',"
			+vo.getOp_code()+",N'"+summary+"')";
		String checkSql = "SELECT 1 FROM TOPERATOR WHERE OP_CODE = " + vo.getOp_code() +" AND STATUS = 1";//该操作员没有系统操作权限或操作员不存在
		List opList = null;
			
		try {
			opList = super.listBySql(checkSql);
		} catch (BusiException e) {
			throw new BusiException("Login Error 1:" + e.getMessage());
		}
		//操作员存在
		if(opList!=null&&opList.size()>0){
			StringBuffer sqlStr = new StringBuffer("UPDATE TOPERATOR");
			sqlStr.append(" SET ");
			sqlStr.append(" LOGIN_TIME = CURRENT_TIMESTAMP ,");
			sqlStr.append(" IP_ADDRESS = '"+vo.getIp_address()+"',");
			sqlStr.append(" ONLINE =1");
			sqlStr.append(" WHERE OP_CODE = "+ vo.getOp_code());
			
			try {
				super.executeSql(sqlStr.toString());
				super.executeSql(loglistSql);
			} catch (BusiException e) {
				throw new BusiException("Login Error 2:" + e.getMessage());
			}
		}
		else{
			//该操作员没有系统操作权限或操作员不存在
			throw new BusiException(-10104005);
		}
	}

	/* (non-Javadoc)
	 * @see enfo.crm.system.OperatorLocal#checkMainMenu(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean checkMainMenu(String menu_id, String op_code)
		throws Exception {
		Integer iret;
		Object[] oparams = new Object[2];
		oparams[0] = op_code;
		oparams[1] = "" + menu_id;
		iret =
			(Integer) super.cudProc(
				checkMenu,
				oparams,
				4,
				java.sql.Types.INTEGER);
		if (iret.intValue() == 1)
			return true;
		return false;
	}

	/* (non-Javadoc)
	 * @see enfo.crm.system.OperatorLocal#modiPassWord(java.lang.Integer, java.lang.String, java.lang.String)
	 */
	@Override
	public Integer modiPassWord(
		Integer op_code,
		String oldPassWord,
		String newPassWord)
		throws Exception {
		Object[] params = new Object[3];
		params[0] = Utility.parseInt(op_code, null);
		params[1] = Utility.trimNull(oldPassWord);
		params[2] = Utility.trimNull(newPassWord);
		return (Integer) super.cudProc(
			chpassSql,
			params,
			5,
			java.sql.Types.INTEGER);
	}

	/* (non-Javadoc)
	 * @see enfo.crm.system.OperatorLocal#modiPassWord(enfo.crm.vo.OperatorVO, java.lang.Integer)
	 */
	@Override
	public String modiPassWord(OperatorVO vo, Integer input_operatorCode)
		throws BusiException {
		Object[] params = new Object[3];
		Integer returnValue = new Integer(0);
		String returnString = "";

		params[0] = Utility.parseInt(input_operatorCode, new Integer(0));
		params[1] = Utility.trimNull(vo.getOld_password());
		params[2] = Utility.trimNull(vo.getNew_password());
		returnValue =
			(Integer) super.cudProc(
				modiPassWordSql,
				params,
				5,
				java.sql.Types.INTEGER);

		if (returnValue.intValue() == 100) {
			returnString = "修改成功！";
		} else if (returnValue.intValue() == -10601001) {
			returnString = "操作失败，员工编号不存在！";
		} else if (returnValue.intValue() == -10601002) {
			returnString = "操作失败，原密码输入错误！";
		} else if (returnValue.intValue() == -10601003) {
			returnString = "操作失败，原登录号不存在！";
		} else if (returnValue.intValue() == -10601003) {
			returnString = "操作失败，原登录号不存在！";
		} else if (returnValue.intValue() == -100) {
			returnString = "操作失败，修改不成功！";
		} else {
			returnString = "系统错误，操作失败！";
		}
		return returnString;
	}

	/* (non-Javadoc)
	 * @see enfo.crm.system.OperatorLocal#modiLoginUser(enfo.crm.vo.OperatorVO)
	 */
	@Override
	public String modiLoginUser(OperatorVO vo) throws BusiException {
		Object[] params = new Object[3];
		Integer returnValue = new Integer(0);
		String returnString = "";

		params[0] = Utility.parseInt(vo.getLogin_user(), new Integer(0));
		params[1] = Utility.trimNull(vo.getNew_login_user());
		returnValue =
			(Integer) super.cudProc(
				modiLoginUserSql,
				params,
				4,
				java.sql.Types.INTEGER);

		if (returnValue.intValue() == 100) {
			returnString = "修改成功！";
		} else if (returnValue.intValue() == -10601001) {
			returnString = "操作失败，员工编号不存在！";
		} else if (returnValue.intValue() == -10601002) {
			returnString = "操作失败，原密码输入错误！";
		} else if (returnValue.intValue() == -10601003) {
			returnString = "操作失败，原登录号不存在！";
		} else if (returnValue.intValue() == -10601003) {
			returnString = "操作失败，原登录号不存在！";
		} else if (returnValue.intValue() == -100) {
			returnString = "操作失败，修改不成功！";
		} else {
			returnString = "系统错误，操作失败！";
		}
		return returnString;
	}

	/* (non-Javadoc)
	 * @see enfo.crm.system.OperatorLocal#listCodeAcount(java.lang.Integer)
	 */
	@Override
	public List listCodeAcount(Integer opcode) throws BusiException {
		List list = null;
		Object[] param = new Object[1];
		param[0] = Utility.parseInt(opcode, null);
		list = super.listBySql("{call SP_QUERY_OP_BOOKRIGHT(?)}", param);
		return list;
	}

	/* (non-Javadoc)
	 * @see enfo.crm.system.OperatorLocal#listOpAll(enfo.crm.vo.OperatorVO)
	 */
	@Override
	public List listOpAll(OperatorVO vo) throws BusiException {
		List rsList = null;
		Object[] params = new Object[5];
		params[0] = Utility.parseInt(vo.getOp_code(), new Integer(0));
		params[1] = Utility.parseInt("", new Integer(0));
		params[2] = Utility.parseInt(vo.getRole_id(), new Integer(0));
		params[3] = Utility.trimNull(vo.getOp_name());
		params[4] = Utility.parseInt(vo.getStatus(), new Integer(0));
		rsList = super.listProcAll(listOperatorSql, params);
		return rsList;
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.system.OperatorLocal#listInsideServiceTasks(enfo.crm.vo.OperatorVO, int, int)
	 */
	@Override
	public IPageList listInsideServiceTasks(OperatorVO vo,int pageIndex, int pageSize) throws BusiException{
		IPageList list = null;
		Object[] params = new Object[1];
		params[0] = vo.getOp_code();
		list = super.listProcPage(listInsideServiceTasksSql,params,pageIndex,pageSize);
		return list;
	}
	
	
	/* (non-Javadoc)
	 * @see enfo.crm.system.OperatorLocal#listOutsideServiceTasks(enfo.crm.vo.OperatorVO, int, int)
	 */
	@Override
	public IPageList listOutsideServiceTasks(OperatorVO vo,int pageIndex, int pageSize) throws BusiException{
		IPageList list = null;
		Object[] params = new Object[1];
		params[0] = vo.getOp_code();
		list = super.listProcPage(listOutsideServiceTasksSql,params,pageIndex,pageSize);
		return list;
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.system.OperatorLocal#listScheDule(enfo.crm.vo.OperatorVO, int, int)
	 */
	@Override
	public IPageList listScheDule(OperatorVO vo,int pageIndex, int pageSize) throws BusiException{
		IPageList list = null;
		Object[] params = new Object[6];

		params[0] = null;
		params[1] = Utility.parseInt(vo.getOp_code(),null);
		params[2] = Utility.parseInt(vo.getBegin_date(),null);
		params[3] = Utility.parseInt(vo.getEnd_date(),null);
		params[4] = null;
		params[5] = null;
	
		list = super.listProcPage(listScheDulesSql,params,pageIndex,pageSize);
		return list;
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.system.OperatorLocal#listSysMessage(enfo.crm.vo.OperatorVO, int, int)
	 */
	@Override
	public IPageList listSysMessage(OperatorVO vo,int pageIndex, int pageSize) throws BusiException{
		Object[] params = new Object[8];
		params[0] = vo.getSerial_no();
		params[1] = Utility.trimNull(vo.getMsg_title());
		params[2] = vo.getFrom_op_code();
		params[3] = vo.getTo_op_code();
		params[4] = vo.getBegin_date();
		params[5] = vo.getEnd_date();
		params[6] = vo.getIs_msg_read();
		params[7] = vo.getOp_code();
		return super.listProcPage("{call SP_QUERY_TSYSMESSAGE(?,?,?,?,?,?,?,?)}",params,pageIndex,pageSize);
	}
	
	
	/* (non-Javadoc)
	 * @see enfo.crm.system.OperatorLocal#addToperator_map(enfo.crm.vo.TOperatorVO, java.lang.Integer)
	 */
	@Override
	public void addToperator_map(TOperatorVO vo,Integer inputOperatorCode)throws Exception{
		Object[] params = new Object[5];
		params[0] = vo.getOp_code();
		params[1] = vo.getIntrust_op_code();
		params[2] = vo.getIntrust_bookcode();
		params[3] = vo.getEtrust_Operator();
		params[4] = inputOperatorCode;
		try {
			super.cudProc("{?=call SP_ADD_TOPERATOR_MAP(?,?,?,?,?)}",params);
		} catch (BusiException e) {
			throw new BusiException("添加操作员失败："+e.getMessage());
		}		
	}
	/* (non-Javadoc)
	 * @see enfo.crm.system.OperatorLocal#addSysMessage(enfo.crm.vo.OperatorVO)
	 */
	@Override
	public void addSysMessage(OperatorVO vo)throws Exception{
		Object[] params = new Object[4];
		params[0] = vo.getInvolved_cust_id();
		params[1] = vo.getMsg_title();
		params[2] = vo.getMsg_text();
		params[3] = vo.getOp_code();
		
		try {
			super.cudProc("{?=call SP_ADD_TSYSMESSAGE(?,?,?,?)}",params);
		} catch (BusiException e) {
			throw new BusiException("权限设置失败："+e.getMessage());
		}		
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.system.OperatorLocal#addCustLevelAuth(enfo.crm.vo.CustLevelAuthVO)
	 */
	@Override
	public void addCustLevelAuth(CustLevelAuthVO vo)throws Exception{
		Object[] params = new Object[4];
		params[0] = vo.getOp_code();
		params[1] = vo.getProduct_id();
		params[2] = vo.getLevel_value_id();
		params[3] = vo.getInput_man();
		
		try {
			super.cudProc("{?=call SP_ADD_TCUSTLEVELAUTH(?,?,?,?)}",params);
		} catch (BusiException e) {
			throw new BusiException("权限设置失败："+e.getMessage());
		}
		
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.system.OperatorLocal#delCustLevelAuth(enfo.crm.vo.CustLevelAuthVO)
	 */
	@Override
	public void delCustLevelAuth(CustLevelAuthVO vo)throws Exception{
		Object[] params = new Object[4];
		params[0] = vo.getOp_code();
		params[1] = vo.getProduct_id();
		params[2] = vo.getLevel_value_id();
		params[3] = vo.getInput_man();

		try {
			super.cudProc("{?=call SP_DEL_TCUSTLEVELAUTH(?,?,?,?)}",params);
		} catch (BusiException e) {
			throw new BusiException("权限取消失败："+e.getMessage());
		}
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.system.OperatorLocal#listCustLevelAuth(enfo.crm.vo.CustLevelAuthVO, int, int)
	 */
	@Override
	public IPageList listCustLevelAuth(CustLevelAuthVO vo,int pageIndex, int pageSize)throws Exception{
		Object[] params = new Object[6];
		
		params[0] = vo.getOp_code();
		params[1] = vo.getProduct_id();
		params[2] = vo.getLevel_id();
		params[3] = vo.getLevel_value_id();
		params[4] = vo.getLevel_value_name();
		params[5] = vo.getInput_man();
		
		try {
			return super.listProcPage("{call SP_QUERY_TCUSTLEVELAUTH(?,?,?,?,?,?)}",params,pageIndex,pageSize);
		} catch (BusiException e) {
			throw new BusiException("查询权限失败："+e.getMessage());
		}
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.system.OperatorLocal#addMenuView(java.lang.String, java.lang.Integer, java.lang.String)
	 */
	@Override
	public void addMenuView(String menu_id,Integer op_code,String vierStr) throws Exception{
		Object[] params = new Object[3];
		
		params[0] = menu_id;
		params[1] = op_code;
		params[2] = vierStr;

		try {
			super.execProc("{?=call SP_ADD_TMENUVIEWDIM(?,?,?)}",params);
		} catch (BusiException e) {
			throw new BusiException("自定义页面显示字段设置失败："+e.getMessage());
		}
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.system.OperatorLocal#listMenuView(java.lang.String, java.lang.Integer)
	 */
	@Override
	public String listMenuView(String menu_id,Integer op_code)throws Exception{
		Object[] params = new Object[2];
		List list  = null;
		
		params[0] = menu_id;
		params[1] = op_code;
		
		try {
			list = super.listProcAll("{call SP_QUERY_TMENUVIEWDIM(?,?)}",params);
			Map map = null;
			if(list!=null&&list.size()==1){
				map = (Map)list.get(0);
				return Utility.trimNull(map.get("VIEWSTR")).toUpperCase();
			}else{
				return "";
			}
		} catch (BusiException e) {
			throw new BusiException("自定义页面显示字段查询失败："+e.getMessage());
		}
		
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.system.OperatorLocal#getWorkBench(java.lang.Integer)
	 */
	@Override
	public String getWorkBench(Integer opCode) throws Exception{
		String workbench = "";
		String sqlStr = "SELECT WORK_BENCH FROM TOPERATOR WHERE OP_CODE="+opCode;
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			conn = CrmDBManager.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sqlStr);
			while (rs.next()) {
				workbench = Utility.trimNull(rs.getObject("WORK_BENCH"));
				break;
			}
		} 
		catch (SQLException e) {
			throw new BusiException("查询常用工作台模块:" + e.getMessage());
		}
		finally{
			rs.close();
			stmt.close();
			conn.close();			
		}
		
		if(workbench.length()==0){
			workbench = "intro|widget2|widget3|widget4|widget5|widget6|widget7|widget8|widget9|widget10";
		}
		
		return workbench;		
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.system.OperatorLocal#modiWorkBench(java.lang.Integer, java.lang.String)
	 */
	@Override
	public void modiWorkBench(Integer opCode,String workbench) throws Exception{
		String sqlStr = "UPDATE TOPERATOR SET WORK_BENCH ='"+ workbench +"' WHERE OP_CODE = "+opCode;
		
		Connection conn = null;
		Statement stmt = null;
		
		try {
			conn = CrmDBManager.getConnection();
			stmt = conn.createStatement();
			stmt.executeUpdate(sqlStr);
		}
		catch (SQLException e) {
			throw new BusiException("修改常用工作台模块:" + e.getMessage());
		}		
		finally{
			stmt.close();
			conn.close();			
		}
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.system.OperatorLocal#syschroOperator(java.lang.Integer)
	 */
	@Override
	public void syschroOperator(Integer opCode) throws BusiException{
		String procSql = "{?=call SP_SYNCHRO_TOperator(?)}";
		Object[] params = new Object[1];
		params[0] = opCode;
		
		super.cudProc(procSql, params);
	}
	/* (non-Javadoc)
	 * @see enfo.crm.system.OperatorLocal#queryProductMarketing(enfo.crm.vo.TcustmanagersVO, int, int)
	 */
	@Override
	public IPageList queryProductMarketing(TcustmanagersVO vo,int pageIndex, int pageSize) throws BusiException{
		IPageList rsList = null;		
		Object[] params = new Object[3];

		params[0] = Utility.parseInt(vo.getProduct_id(),new Integer(0));
		params[1] = Utility.parseInt(vo.getLink_man(),new Integer(0));
		params[2] = Utility.parseInt(vo.getInput_man(),new Integer(0));

		rsList = super.listProcPage(sql_query_product_marketing_total,params,pageIndex,pageSize);		
		return rsList;		
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.system.OperatorLocal#queryProductMarketing1(enfo.crm.vo.TcustmanagersVO)
	 */
	@Override
	public List queryProductMarketing1(TcustmanagersVO vo) throws BusiException{
		List rsList = null;		
		Object[] params = new Object[3];

		params[0] = Utility.parseInt(vo.getProduct_id(),new Integer(0));
		params[1] = Utility.parseInt(vo.getLink_man(),new Integer(0));
		params[2] = Utility.parseInt(vo.getInput_man(),new Integer(0));

	rsList = super.listBySql(sql_query_product_marketing_total,params);		
		return rsList;		
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.system.OperatorLocal#listCustDue(java.lang.Integer, int, int)
	 */
	@Override
	public IPageList listCustDue(Integer opCode,int pageIndex,int pageSize)throws BusiException{
		String listSql = "SELECT A.CUST_ID, B.CUST_NAME, A.BEN_AMOUNT, C.PRODUCT_NAME, C.PRODUCT_JC, A.BEN_END_DATE, D.RG_MONEY "
						+ "FROM INTRUST..TBENIFITOR A, INTRUST..TCUSTOMERINFO B, INTRUST..TPRODUCT C, "
						+ "(SELECT CUST_ID, SUM(RG_MONEY) AS RG_MONEY FROM INTRUST..TCONTRACT GROUP BY CUST_ID) D "
						+ "WHERE A.CUST_ID = B.CUST_ID AND A.PRODUCT_ID = C.PRODUCT_ID AND A.CUST_ID = D.CUST_ID AND A.BEN_AMOUNT > 0 "
						+ "AND A.BEN_END_DATE BETWEEN dbo.GETDATEINT(GETDATE()) AND dbo.GETDATE(dbo.GETDATEINT(GETDATE()),30) "
						+ "AND B.SERVICE_MAN = " + opCode;
		
		IPageList pageList = super.listSqlPage(listSql,pageIndex,pageSize);
		return pageList;
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.system.OperatorLocal#listEfficientCust(java.lang.Integer)
	 */
	@Override
	public List listEfficientCust(Integer opCode) throws BusiException{
		String listSql = "SELECT TOP 10 A.CUST_ID, B.CUST_NAME, SUM(A.BEN_AMOUNT) AS BEN_AMOUNT "
						+" FROM INTRUST..TBENIFITOR A, INTRUST..TCUSTOMERINFO B"
						+" WHERE A.CUST_ID = B.CUST_ID AND B.SERVICE_MAN =  " + opCode
						+" GROUP BY A.CUST_ID, B.CUST_NAME ORDER BY BEN_AMOUNT DESC             ";
		
		List rsList = super.listBySql(listSql);
		return rsList;	
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.system.OperatorLocal#listCustManagersRank(java.lang.Integer)
	 */
	@Override
	public List listCustManagersRank(Integer opCode) throws BusiException{
		String listSql = "SELECT TOP 10 A.LINK_MAN, C.OP_NAME, ROUND(SUM(A.RG_MONEY)/10000,2) AS RG_MONEY   "
						+"FROM INTRUST..TCONTRACT A, INTRUST..TOPERATOR C, EFCRM..TCustManagers D "
						+"WHERE A.LINK_MAN = C.OP_CODE AND A.LINK_MAN = D.ManagerID         "
						+"GROUP BY A.LINK_MAN, C.OP_NAME ORDER BY RG_MONEY DESC				";
		List rsList = super.listBySql(listSql);
		return rsList;	
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.system.OperatorLocal#StatTeamRank(enfo.crm.vo.TOperatorVO)
	 */
	@Override
	public List StatTeamRank(TOperatorVO vo) throws BusiException{
		String listProc = "{call SP_STAT_TEAMSTAT(?,?,?,?)}";
		Object[] params = new Object[4];

		params[0] = Utility.parseInt(vo.getTeam_id(),new Integer(0));
		params[1] = Utility.trimNull(vo.getManager_name());
		params[2] = Utility.parseInt(vo.getOp_code(),new Integer(0));
		params[3] = Utility.parseInt(vo.getQuery_date(),new Integer(0));
		
		return super.listProcAll(listProc,params);
	}
    /* (non-Javadoc)
	 * @see enfo.crm.system.OperatorLocal#changeProduct(enfo.crm.vo.TOperatorVO)
	 */
    @Override
	public void changeProduct(TOperatorVO vo) throws BusiException {
        Object[] params = new Object[2];
        params[0] = Utility.parseInt(vo.getOp_code(), new Integer(0));
        params[1] = Utility.parseInt(vo.getProduct_id(), new Integer(0));
        
        try {
        	super.cudProc("{?=call SP_MODI_TOPERATOR_PRODUCT_ID_CRM(?,?) }", params);
        } catch (Exception e) {
            throw new BusiException("更新操作员选择的产品失败:: " + e.getMessage());
        }
    }
    
    /* (non-Javadoc)
	 * @see enfo.crm.system.OperatorLocal#changePagesize(java.lang.Integer, java.lang.String)
	 */
    @Override
	public void changePagesize(Integer op_code,String pagesize) throws BusiException {
    	String sql = "UPDATE TOPERATOR SET PAGESIZE = " + pagesize + " WHERE OP_CODE ="+ op_code;
        try {
        	super.executeSql(sql);
        } catch (Exception e) {
            throw new BusiException("更新显示行数失败: " + e.getMessage());
        }
    }
}